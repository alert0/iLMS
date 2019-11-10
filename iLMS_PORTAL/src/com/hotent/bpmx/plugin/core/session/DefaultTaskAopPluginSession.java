package com.hotent.bpmx.plugin.core.session;

import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.plugin.core.session.TaskAopPluginSession;

public class DefaultTaskAopPluginSession extends AbstractBpmPluginSession
		implements TaskAopPluginSession {

	private TaskFinishCmd taskFinishCmd;
	
	@Override
	public TaskFinishCmd getTaskFinishCmd() {
		return taskFinishCmd;
	}

	public void setTaskFinishCmd(TaskFinishCmd taskFinishCmd) {
		this.taskFinishCmd = taskFinishCmd;
	}

	
}
