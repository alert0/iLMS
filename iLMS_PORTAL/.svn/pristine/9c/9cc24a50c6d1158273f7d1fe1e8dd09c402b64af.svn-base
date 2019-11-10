package com.hotent.bpmx.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.NoExecutorModel;
import com.hotent.bpmx.api.event.TaskCreateEvent;
import com.hotent.bpmx.api.exception.NoTaskUserException;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.service.BpmAgentService;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.persistence.dao.BpmExeStackDao;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.manager.BpmTaskDueTimeManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.BpmTaskDueTime;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.sys.api.calendar.ICalendarService;

/**
 * 任务创建时的动作。
 * 
 * <pre>
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-3-25-下午10:02:23
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class TaskCreateEventListener implements ApplicationListener<TaskCreateEvent>, Ordered
{

	@Resource
	BpmExeStackDao bpmExeStackDao;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	BpmProStatusManager bpmProStatusManager;
	@Resource
	BpmAgentService bpmAgentService;
	@Resource
	BpmExeStackManager bpmExeStackManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	ICalendarService iCalendarService;
	@Resource 
	BpmTaskDueTimeManager bpmTaskDueTimeManager;
	

	@Override
	public int getOrder()
	{
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(TaskCreateEvent ev)
	{
		BpmDelegateTask delegateTask = (BpmDelegateTask) ev.getSource();
		String instId = (String) delegateTask.getVariable(BpmConstants.PROCESS_INST_ID);
		String subject = (String) delegateTask.getVariable(BpmConstants.SUBJECT);
		String nodeId = delegateTask.getTaskDefinitionKey();

		ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
		List<BpmIdentity> identityList = new ArrayList<BpmIdentity>();
		// 从流程变量中获取是否设置了人员，如果设置则从流程变量中获取。
		Map<String,List< BpmIdentity>> nodeUsers=(Map<String,List< BpmIdentity>>) taskCmd.getTransitVars(BpmConstants.BPM_NODE_USERS);
		// 正常跳转指定的执行人
		if(taskCmd.getTransitVars(BpmConstants.BPM_NEXT_NODE_USERS)!=null){
			identityList = (List<BpmIdentity>) taskCmd.getTransitVars(BpmConstants.BPM_NEXT_NODE_USERS);
		}
		// 已有指定执行人
		if(nodeUsers!=null && nodeUsers.containsKey(nodeId)){
			identityList=nodeUsers.get(nodeId);
		}
		// 先从任务的Excutors中获取，如果获取不到再从CMD中获取。
		if (BeanUtils.isEmpty(identityList))
		{
			identityList = delegateTask.getExecutors();
		}
		// 如果在上下文指定了人员，则取上下文的人员。 
		if (BeanUtils.isEmpty(identityList))
		{
			Map<String, List<BpmIdentity>> identityMap = taskCmd.getBpmIdentities();
			identityList = identityMap.get(nodeId);
		}

		BpmProcessInstance instance = (BpmProcessInstance) taskCmd.getTransitVars(BpmConstants.PROCESS_INST);

		//boolean isAllowEmptyIdentity = BpmUtil.isAllowEmptyIdentity(instance, nodeId);//判断是否允许执行人为空，先去掉
		boolean isAllowEmptyIdentity =  BpmUtil.isAllowEmptyIdentity(instance, nodeId);//判断执行人为空时跳过任务
		// 如果不允许执行人为空，并且人员为空的情况抛出异常。
		if (isAllowEmptyIdentity == false && BeanUtils.isEmpty(identityList))
		{
			NoExecutorModel noExcutor = NoExecutorModel.getNoExecutorModel(delegateTask.getId(), delegateTask.getProcessInstanceId(), subject, delegateTask.getTaskDefinitionKey(), delegateTask.getName(), delegateTask.getBpmnDefId());

			BpmUtil.publishNoExecutorEvent(noExcutor);

			throw new NoTaskUserException("没有任务执行人");
		}
		// 分配人员
		bpmTaskManager.assignUser(delegateTask, identityList);
		// 添加意见
		addOpinion(delegateTask, identityList);
		// 修改节点状态节点为待审批。
		bpmProStatusManager.createOrUpd(instId, delegateTask.getBpmnDefId(), nodeId, delegateTask.getName(), NodeStatus.PENDING);

		// 加入堆栈数据。
		bpmExeStackManager.pushStack(delegateTask);
		
		setDueTime(delegateTask,identityList,instance);
		
	}

	private void setDueTime(BpmDelegateTask delegateTask,List<BpmIdentity> identityList,BpmProcessInstance instance) {
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
		
		if(StringUtil.isNotZeroEmpty(instance.getParentInstId())){
			ContextThreadUtil.addTask(bpmTask);
		}
		
	}

	/**
	 * 添加意见。
	 * 
	 * @param delegateTask
	 * @param identityList
	 *            void
	 */
	private void addOpinion(BpmDelegateTask delegateTask, List<BpmIdentity> identityList)
	{
		String ids = "";
		String names = "";
		if (BeanUtils.isNotEmpty(identityList))
		{
			ids = BpmCheckOpinionUtil.getIdentityIds(identityList);
			names = BpmCheckOpinionUtil.getIdentityNames(identityList);
		}

		String instId = (String) delegateTask.getVariable(BpmConstants.PROCESS_INST_ID);
		DefaultBpmCheckOpinion bpmCheckOpinion = BpmCheckOpinionUtil.buildBpmCheckOpinion(delegateTask, instId);
		bpmCheckOpinion.setQualfieds(ids);
		bpmCheckOpinion.setQualfiedNames(names);
		bpmCheckOpinionManager.create(bpmCheckOpinion);
	}

	

}
