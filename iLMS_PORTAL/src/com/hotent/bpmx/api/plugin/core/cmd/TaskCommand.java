package com.hotent.bpmx.api.plugin.core.cmd;

import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;

public interface TaskCommand {
	public void execute(EventType eventType, BpmDelegateTask bpmDelegateTask);
}
