package com.hotent.bpmx.core.engine.task.handler;

import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.plugin.core.def.TaskActionHandlerDef;
import com.hotent.bpmx.api.plugin.core.session.TaskActionPluginSession;

public class TaskActionRecoverHandler extends AbstractTaskActionHandler{

	@Override
	public boolean isNeedCompleteTask() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void preActionHandler(TaskActionPluginSession pluginSession,
			TaskActionHandlerDef def) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterActionHandler(TaskActionPluginSession pluginSession,
			TaskActionHandlerDef def) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionType getActionType() {
		
		return null;
	}

}
