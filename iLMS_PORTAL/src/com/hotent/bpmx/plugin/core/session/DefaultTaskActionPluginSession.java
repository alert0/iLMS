package com.hotent.bpmx.plugin.core.session;

import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.plugin.core.session.TaskActionPluginSession;

public class DefaultTaskActionPluginSession extends AbstractBpmPluginSession
		implements TaskActionPluginSession {

	private TaskFinishCmd taskFinishCmd;
	private BpmDelegateTask bpmDelegateTask;
	@Override
	public TaskFinishCmd getTaskFinishCmd() {
		return taskFinishCmd;
	}

	public void setTaskFinishCmd(TaskFinishCmd taskFinishCmd) {
		this.taskFinishCmd = taskFinishCmd;
	}

	public BpmDelegateTask getBpmDelegateTask() {
		return bpmDelegateTask;
	}

	public void setBpmDelegateTask(BpmDelegateTask bpmDelegateTask) {
		this.bpmDelegateTask = bpmDelegateTask;
	}

}
