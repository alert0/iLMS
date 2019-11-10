package com.hotent.bpmx.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.BaseActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.constant.MultiInstanceType;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.TaskSignCreateEvent;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.manager.BpmSignDataManager;
import com.hotent.bpmx.persistence.manager.BpmTaskDueTimeManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.BpmSignData;
import com.hotent.bpmx.persistence.model.BpmTaskDueTime;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.sys.api.calendar.ICalendarService;

/**
 * 处理TaskSignCreateEvent会签任务创建监听器。 1.添加流程意见。 2.分配用户。 3.添加会签数据。 4.修改节点状态。
 * 5.更新会签数据
 * 
 * <pre>
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-3-31-下午3:30:01
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class TaskSignCreateEventListener implements ApplicationListener<TaskSignCreateEvent>, Ordered
{

	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmSignDataManager bpmSignDataManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	BpmProStatusManager bpmProStatusManager;
	@Resource
	BpmExeStackManager bpmExeStackManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	ICalendarService iCalendarService;
	@Resource 
	BpmTaskDueTimeManager bpmTaskDueTimeManager;
	@Resource
	BpmIdentityExtractService extractService;
	

	@Override
	public int getOrder()
	{
		return 1;
	}

	@Override
	public void onApplicationEvent(TaskSignCreateEvent event)
	{
		BpmDelegateTask delegateTask = (BpmDelegateTask) event.getSource();
		
		DelegateTask actTask= (DelegateTask) delegateTask.getProxyObj();
		
		
		BpmTask task = BpmUtil.convertTask(delegateTask);
		//设置上级的executeid
		task.setExecId(actTask.getExecution().getParentId());
		if (MultiInstanceType.PARALLEL.equals(delegateTask.multiInstanceType())){
			task.setExecId(delegateTask.getParentExecuteId(2));
		}
		bpmTaskManager.create((DefaultBpmTask) task);

		String instId = task.getProcInstId();

		BpmIdentity taskExecutor = (BpmIdentity) delegateTask.getVariable(BpmConstants.ASIGNEE);
		
		// 分配执行人。
		List<BpmIdentity> identityList = new ArrayList<BpmIdentity>();
		identityList.add(taskExecutor);
		
		// 二次抽取
		if(ExtractType.EXACT_EXACT_DELAY.equals(taskExecutor.getExtractType())){
			identityList = extractService.extractBpmIdentity(identityList);
		}

		bpmTaskManager.assignUser(delegateTask, identityList);
		// 添加意见
		addOpinion(delegateTask, identityList, instId);
		// 期限设置
		setDueTime(delegateTask, identityList);

		// 修改节点状态节点为待审批。
		bpmProStatusManager.createOrUpd(instId, delegateTask.getBpmnDefId(), delegateTask.getTaskDefinitionKey(), delegateTask.getName(), NodeStatus.PENDING);

		// 添加会签数据。
		Integer loopCounter = (Integer) delegateTask.getVariable(BpmConstants.NUMBER_OF_LOOPCOUNTER);
		if (loopCounter == null)
			loopCounter = 0;
		//流程变量名称。
		String resultVarName=BpmConstants.SIGN_RESULT + delegateTask.getTaskDefinitionKey();
		
		MultiInstanceType instanceType = delegateTask.multiInstanceType();
		// 并行会签
		if (MultiInstanceType.PARALLEL.equals(instanceType)){
			//清除之前的会签结果
			delegateTask.removeVariable(resultVarName);
			//并行往上查找两级。
			String executeId= delegateTask.getParentExecuteId(2);
			
			addSignData(task,executeId, loopCounter);
		} 
		else{
			// 串行一次加入。
			if (loopCounter == 0){
				//清除之前的会签结果
				delegateTask.removeVariable(resultVarName);
				
				String executeId= actTask.getExecution().getParentId();
				
				addSignData(task,executeId);
			}
		}
		
		// 会签任务 创建堆栈时， 不作为网关节点去创建堆栈信息  SubProcessMultiInstanceStartEventListener
		ActionCmd cmd = ContextThreadUtil.getActionCmd();
		cmd.addTransitVars("SubProcessMultiStartOrEndEvent", null);

		// 加入堆栈数据。
		bpmExeStackManager.pushStack(delegateTask);

	}

	/**
	 * 添加审批意见。
	 * 
	 * @param delegateTask
	 * @param identityList
	 * @param instId
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void addOpinion(BpmDelegateTask delegateTask, List<BpmIdentity> identityList, String instId)
	{
		DefaultBpmCheckOpinion opinion = BpmCheckOpinionUtil.buildBpmCheckOpinion(delegateTask, instId);
		String ids = BpmCheckOpinionUtil.getIdentityIds(identityList);
		String names = BpmCheckOpinionUtil.getIdentityNames(identityList);
		opinion.setQualfieds(ids);
		opinion.setQualfiedNames(names);
		bpmCheckOpinionManager.create(opinion);
	}

	/**
	 * 添加会签数据。
	 * 
	 * @param bpmTask
	 * @param taskExecutor
	 *            void
	 */
	private void addSignData(BpmTask bpmTask,String executeId, Integer index)
	{
		String nodeId = bpmTask.getNodeId();

		BaseActionCmd actionCmd = (BaseActionCmd) ContextThreadUtil.getActionCmd();
		List<BpmIdentity> idList = actionCmd.getBpmIdentities().get(nodeId);
		if (BeanUtils.isEmpty(idList))
			return;

		// 生成投票的数据
		BpmIdentity bpmIdentity = idList.get(index);
		BpmSignData signData = bpmSignDataManager.getSignData(bpmTask,executeId, bpmIdentity);

		signData.setIndex(index.shortValue());
		bpmSignDataManager.create(signData);

	}

	private void addSignData(BpmTask bpmTask,String executeId)
	{
		String nodeId = bpmTask.getNodeId();

		BaseActionCmd actionCmd = (BaseActionCmd) ContextThreadUtil.getActionCmd();
		List<BpmIdentity> idList = actionCmd.getBpmIdentities().get(nodeId);
		if (BeanUtils.isEmpty(idList))
			return;

		// 生成投票的数据
		for (short i = 0; i < idList.size(); i++)
		{
			BpmIdentity bpmIdentity = idList.get(i);
			BpmSignData signData = bpmSignDataManager.getSignData(bpmTask,executeId, bpmIdentity);
			signData.setIndex(i);
			bpmSignDataManager.create(signData);
		}

	}
	
	private void setDueTime(BpmDelegateTask delegateTask,List<BpmIdentity> identityList) {
		DefaultBpmTask bpmTask = bpmTaskManager.getByRelateTaskId(delegateTask.getId());
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(bpmTask.getProcDefId(), bpmTask.getNodeId());
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef =  bpmDefinitionAccessor.getBpmProcessDef(bpmTask.getProcDefId());
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
		// 设置任务到期时间
		NodeProperties nodeProperties=bpmNodeDef.getLocalProperties();
   		Date dueTime = null;
   		String userId = "",userName = "";
   		
   		
   		int dueTimeMin = 0;
   		String dateTpye = "";
		if( nodeProperties.getDueTime()!=0 ){
			dueTimeMin = nodeProperties.getDueTime();
			dateTpye = nodeProperties.getDateType();
		}else{
			dueTimeMin = defExt.getExtProperties().getDueTime();
			dateTpye = defExt.getExtProperties().getDateType();
		}
		
		if(dueTimeMin==0) return;

		if("caltime".equals(dateTpye)){
			dueTime = new Date(TimeUtil.getNextTime(TimeUtil.MINUTE, dueTimeMin,bpmTask.getCreateTime().getTime()));
		}else{
			// 设置第一个执行人的工作日
			if(BeanUtils.isNotEmpty(identityList)){
				BpmIdentity bpmIdentity = identityList.get(0);
				if(BpmIdentity.TYPE_USER.equals(bpmIdentity.getType())){
					userId = bpmIdentity.getId();
					userName = bpmIdentity.getName();
					dueTime = iCalendarService.getEndTimeByUser(identityList.get(0).getId(), bpmTask.getCreateTime(), dueTimeMin);
				}
			}
		}
   		bpmTask.setDueTime(dueTime);
		bpmTaskManager.update(bpmTask);
		
		BpmTaskDueTime bpmTaskDueTime = new BpmTaskDueTime();
		bpmTaskDueTime.setId(UniqueIdUtil.getSuid());
		bpmTaskDueTime.setDateType(dateTpye);
		bpmTaskDueTime.setDueTime(dueTimeMin);
		bpmTaskDueTime.setRemainingTime(dueTimeMin);
		bpmTaskDueTime.setExpirationDate(dueTime);
		bpmTaskDueTime.setInstId(bpmTask.getProcInstId());
		bpmTaskDueTime.setTaskId(bpmTask.getTaskId());
		bpmTaskDueTime.setStartTime(bpmTask.getCreateTime());
		bpmTaskDueTime.setUserId(userId);
		bpmTaskDueTime.setUserName(userName);
		bpmTaskDueTime.setIsNew((short)1);
		bpmTaskDueTime.setStatus((short)0);
		
		bpmTaskDueTimeManager.create(bpmTaskDueTime);
		
	}

}
