package com.hotent.bpmx.plugin.execution.globalRestful.plugin;

import java.util.List;

import javax.annotation.Resource;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.model.process.def.Restful;
import com.hotent.bpmx.api.plugin.core.def.BpmExecutionPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmExecutionPluginSession;
import com.hotent.bpmx.api.service.RestfulService;
import com.hotent.bpmx.core.engine.identity.DefaultBpmIdentityService;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmExecutionPlugin;
import com.hotent.bpmx.plugin.execution.globalRestful.def.GlobalRestfulInvokePluginDef;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.call.ICallLogService;

/**
 * restful接口调用插件
 * @author heyifan
 *
 */
public class GlobalRestfulInvokePlugin extends AbstractBpmExecutionPlugin{
	
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	IUserService userServiceImpl;
	@Resource
	DefaultBpmIdentityService bpmIdentityService;
	@Resource
	ICallLogService iCallLogService;
	@Resource
	RestfulService restfulService;
	
	@Override
	public Void execute(BpmExecutionPluginSession pluginSession,
			BpmExecutionPluginDef pluginDef) {
		GlobalRestfulInvokePluginDef restfulPluginDef = (GlobalRestfulInvokePluginDef) pluginDef;
		List<Restful> restfuls = restfulPluginDef.getRestfulList();
		return BeanUtils.isNotEmpty(restfuls)?restfulService.executionPluginExecute(pluginSession, pluginDef,restfuls):null;
	}
	
}