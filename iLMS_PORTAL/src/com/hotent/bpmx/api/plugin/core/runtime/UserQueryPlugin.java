package com.hotent.bpmx.api.plugin.core.runtime;

import java.util.List;

import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;

public interface UserQueryPlugin extends RunTimePlugin<BpmUserCalcPluginSession, BpmPluginDef, List<BpmIdentity>>{

}
