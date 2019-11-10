package com.hotent.bpmx.plugin.task.restful.plugin;

import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.model.process.def.Restful;
import com.hotent.bpmx.api.plugin.core.def.BpmTaskPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmTaskPluginSession;
import com.hotent.bpmx.api.service.RestfulService;
import com.hotent.bpmx.core.engine.identity.DefaultBpmIdentityService;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmTaskPlugin;
import com.hotent.bpmx.plugin.task.restful.def.RestfulInvokePluginDef;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.call.ICallLogService;

/**
 * restful接口调用插件
 * @author heyifan
 *
 */
public class RestfulInvokePlugin extends AbstractBpmTaskPlugin{
	private static Logger log = LoggerFactory.getLogger(RestfulInvokePlugin.class);
	
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
	public Void execute(BpmTaskPluginSession pluginSession,BpmTaskPluginDef pluginDef) {
		RestfulInvokePluginDef restfulPluginDef = (RestfulInvokePluginDef) pluginDef;
		List<Restful> restfuls = restfulPluginDef.getRestfulList();
		return BeanUtils.isNotEmpty(restfuls)?restfulService.taskPluginExecute(pluginSession, pluginDef,restfuls):null;
	}
	
}