package com.hotent.bpmx.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.cmd.BaseActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.ProcessInstanceStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.event.ProcessInstanceEndEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.core.engine.inst.DefaultProcessInstCmd;
import com.hotent.bpmx.persistence.dao.BpmSignDataDao;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;


/**
 * 流程结束监听器。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-17-下午4:51:21
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class ProcessInstEndListener implements ApplicationListener<ProcessInstanceEndEvent>,Ordered {

	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmProStatusManager bpmProStatusManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	BpmSignDataDao bpmSignDataDao;
	
	
	@Override
	public void onApplicationEvent(ProcessInstanceEndEvent ev) {
		BpmDelegateExecution execution=(BpmDelegateExecution) ev.getSource();
		//更新流程实例状态。
		updProcessInstance(execution);
		
		Integer instCount=(Integer) execution.getSupperVariable(BpmConstants.NUMBER_OF_INSTANCES);
		Integer completeInstCount=(Integer) execution.getSupperVariable(BpmConstants.NUMBER_OF_COMPLETED_INSTANCES);
		//外部子流程的情况需要传递变量。
		//外部子流程结束时构建CMD。
		if(StringUtil.isNotZeroEmpty(execution.getSupperExecutionId())){
			//单实例的情况。//多实例结束
			if(instCount==null || instCount.equals(completeInstCount)){
				Map<String,Object> commuVars_=execution.getVariables();
				ContextThreadUtil.setCommuVars(commuVars_);
				
				String parentProcInstId=(String) commuVars_.get(BpmConstants.PROCESS_PARENT_INST_ID);
				converCmd(parentProcInstId);
			}
		}
	}

	/**
	 * 转换CMD。
	 * @param parentProcInstId
	 * @param instId 
	 * void
	 */
	private void converCmd(String parentProcInstId){
		BaseActionCmd baseCmd=(BaseActionCmd)ContextThreadUtil.getActionCmd();
		
		BpmProcessInstance parentProcessInst=bpmProcessInstanceManager.get(parentProcInstId);
		DefaultProcessInstCmd cmd=new DefaultProcessInstCmd();
		cmd.setInstId(parentProcInstId);
		cmd.setActionName(baseCmd.getActionName());
		cmd.setBpmIdentities(baseCmd.getBpmIdentities());
		cmd.addTransitVars(BpmConstants.PROCESS_INST,parentProcessInst);
		ContextThreadUtil.setActionCmd(cmd);
	}
	
	/**
	 * 
	 *更新流程实例状态。
	 * @param bpmnInstId 
	 * void
	 */
	private void updProcessInstance(BpmDelegateExecution execution){
		String instId=(String) execution.getVariable(BpmConstants.PROCESS_INST_ID);
		BaseActionCmd cmd=(BaseActionCmd)ContextThreadUtil.getActionCmd();
		DefaultBpmProcessInstance instance= bpmProcessInstanceManager.get(instId);
		String procInstId = instance.getId();
		
		//添加多一个结束的审核意见记录
		DefaultBpmCheckOpinion entity=BpmCheckOpinionUtil.buildBpmCheckOpinion(execution,instId,true);
		bpmCheckOpinionManager.create(entity);
		
		//将审批意见归档为历史，并删除流程实例的审批意见
		bpmCheckOpinionManager.archiveHistory(procInstId);
		
		//更新流程实例状态
		updateStatus(instance, cmd.getActionName());
		
		bpmProStatusManager.archiveHistory(procInstId);
		
		//流程结束时，清除会签结果数据。
		List<String> instList=new ArrayList<String>();
		instList.add(procInstId);
		bpmSignDataDao.delByInstList(instList);
	}

	private void updateStatus(DefaultBpmProcessInstance instance,String actionName){
		instance.setStatus(ProcessInstanceStatus.STATUS_END.getKey());
		instance.setDuration(getDuration(instance.getCreateTime()));
		instance.setResultType(actionName);
		instance.setEndTime(new Date());
		bpmProcessInstanceManager.update(instance);
	}
	
	private Long getDuration(Date createTime){
		Long duration=new Date().getTime() - createTime.getTime();
		return duration;
	}
	
	@Override
	public int getOrder() {
		return 1;
	}

}
