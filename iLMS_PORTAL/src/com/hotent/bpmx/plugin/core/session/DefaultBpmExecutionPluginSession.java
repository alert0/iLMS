package com.hotent.bpmx.plugin.core.session;

import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.plugin.core.session.BpmExecutionPluginSession;

public class DefaultBpmExecutionPluginSession extends AbstractBpmPluginSession implements BpmExecutionPluginSession{
	private BpmDelegateExecution bpmDelegateExecution;
	
	private EventType eventType;

	public BpmDelegateExecution getBpmDelegateExecution() {
		return bpmDelegateExecution;
	}

	public void setBpmDelegateExecution(BpmDelegateExecution bpmDelegateExecution) {
		this.bpmDelegateExecution = bpmDelegateExecution;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
}
