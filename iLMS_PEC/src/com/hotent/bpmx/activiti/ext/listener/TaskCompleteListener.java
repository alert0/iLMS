package com.hotent.bpmx.activiti.ext.listener;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.event.NotifyTaskModel;
import com.hotent.bpmx.api.event.TaskCompleteEvent;
import com.hotent.bpmx.api.event.TaskNotifyEvent;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.service.BpmIdentityService;
import com.hotent.org.api.model.IUser;


/**
 * 任务完成监听器。
 * @author ray
 *
 */
public class TaskCompleteListener extends AbstractTaskListener  {
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 6844821899585103714L;
	
	
	@Resource
	BpmIdentityService bpmIdentityService;  
	@Resource
	BpmIdentityExtractService bpmIdentityExtractService;
	

	@Override
	public EventType getBeforeTriggerEventType() {
		return EventType.TASK_COMPLETE_EVENT;
	}

	@Override
	public EventType getAfterTriggerEventType() {
		return EventType.TASK_POST_COMPLETE_EVENT;
	}

	@Override
	public void beforePluginExecute(BpmDelegateTask delegateTask) {
		ActionType actionType=ActionType.APPROVE;
		String instId=(String) delegateTask.getVariable(BpmConstants.PROCESS_INST_ID);
		ActionCmd cmd=ContextThreadUtil.getActionCmd();
		
		TaskFinishCmd finishCmd=(TaskFinishCmd)cmd;
		
		/**
		 * 撤销的时候通知执行人。
		 */
		if(ActionType.RECOVER.equals(finishCmd.getActionType())){
			
			String subject=(String) delegateTask.getVariable(BpmConstants.SUBJECT);
			
			Map<String, Object> vars=delegateTask.getVariables();
			
			List<BpmIdentity> idlist=bpmIdentityService.queryByTask(delegateTask.getId());
			
			List<IUser> userList= bpmIdentityExtractService.extractUser(idlist);
			
			NotifyTaskModel model=NotifyTaskModel.getNotifyModel(delegateTask.getId(), delegateTask.getProcessInstanceId(), instId,
					subject, delegateTask.getTaskDefinitionKey(), delegateTask.getName(), delegateTask.getBpmnDefId(),
					vars, userList, actionType, finishCmd.getActionName(), finishCmd.getApprovalOpinion());
			
			TaskNotifyEvent ev=new TaskNotifyEvent(model);
			
			AppUtil.publishEvent(ev);
			
			
		}
	}

	@Override
	public void triggerExecute(BpmDelegateTask task) {
		TaskCompleteEvent ev=new TaskCompleteEvent(task);
		AppUtil.publishEvent(ev);
	}

	@Override
	public void afterPluginExecute(BpmDelegateTask delegateTask) {
		
	}

	@Override
	protected ScriptType getScriptType() {
		return ScriptType.COMPLETE;
	}	
	


}
