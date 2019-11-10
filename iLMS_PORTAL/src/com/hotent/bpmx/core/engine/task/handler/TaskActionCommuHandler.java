package com.hotent.bpmx.core.engine.task.handler;

import javax.annotation.Resource;

import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.plugin.core.def.TaskActionHandlerDef;
import com.hotent.bpmx.api.plugin.core.runtime.TaskActionHandler;
import com.hotent.bpmx.api.plugin.core.session.TaskActionPluginSession;
import com.hotent.bpmx.api.service.TaskCommuService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;

/**
 * 沟通退回处理。
 * @author yongguo
 *
 */
public class TaskActionCommuHandler implements TaskActionHandler{
	
	@Resource
	TaskCommuService taskCommuService;
	

	@Override
	public Boolean execute(TaskActionPluginSession pluginSession,
			TaskActionHandlerDef pluginDef) {
		
		DefaultTaskFinishCmd finishCmd=(DefaultTaskFinishCmd)pluginSession.getTaskFinishCmd();
		
		String taskId=(String) finishCmd.getTaskId(); 
		
		String notifyType=finishCmd.getNotifyType();
		
		String opinion=finishCmd.getApprovalOpinion();
		
		taskCommuService.completeTask(taskId, notifyType, opinion);
		
		return true;
	}

	@Override
	public boolean isNeedCompleteTask() {
		return false;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.COMMU;
	}

}
