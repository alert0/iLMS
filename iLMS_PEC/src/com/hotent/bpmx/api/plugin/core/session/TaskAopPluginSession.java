package com.hotent.bpmx.api.plugin.core.session;

import com.hotent.bpmx.api.cmd.TaskFinishCmd;

public interface TaskAopPluginSession extends BpmPluginSession {
	public TaskFinishCmd getTaskFinishCmd();
}
