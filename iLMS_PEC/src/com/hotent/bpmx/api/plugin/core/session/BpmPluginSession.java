package com.hotent.bpmx.api.plugin.core.session;

import com.hotent.bpmx.api.engine.BpmxEngine;
import com.hotent.org.api.service.IOrgService;


public interface BpmPluginSession {
	public BpmxEngine getBpmxEngine();
	
	public IOrgService getOrgEngine();
}
