package com.hotent.bpmx.plugin.core.cmd;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.plugin.core.cmd.TaskCommand;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.plugin.core.context.PluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.def.BpmTaskPluginDef;
import com.hotent.bpmx.api.plugin.core.factory.BpmPluginFactory;
import com.hotent.bpmx.api.plugin.core.factory.BpmPluginSessionFactory;
import com.hotent.bpmx.api.plugin.core.runtime.BpmTaskPlugin;
import com.hotent.bpmx.api.plugin.core.session.BpmTaskPluginSession;
import com.hotent.bpmx.plugin.core.session.DefaultBpmTaskPluginSession;


//@Service
public class PluginTaskCommand implements TaskCommand{

	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	
	@Resource
	BpmPluginFactory bpmPluginFactory;
	
	@Resource
	BpmPluginSessionFactory bpmPluginSessionFactory;

	@Override
	public void execute(EventType eventType, BpmDelegateTask bpmDelegateTask) {
		//改造执行插件的会话数据
		BpmTaskPluginSession bpmTaskPluginSession = bpmPluginSessionFactory.buildBpmTaskPluginSession(bpmDelegateTask);
		//将当前事件设置给session
		((DefaultBpmTaskPluginSession)bpmTaskPluginSession).setEventType(eventType);
		
		//执行节点的任务类插件
		BpmNodeDef bpmNodeDef= bpmDefinitionAccessor.getBpmNodeDef((String)bpmDelegateTask.getVariable(BpmConstants.PROCESS_DEF_ID), bpmDelegateTask.getTaskDefinitionKey());
		for(BpmPluginContext bpmPluginContext:bpmNodeDef.getBpmPluginContexts()){
			//事件为空则跳过。
			if(BeanUtils.isEmpty(bpmPluginContext.getEventTypes())) continue;
			
			BpmPluginDef bpmPluginDef = bpmPluginContext.getBpmPluginDef();
			if(bpmPluginDef instanceof BpmTaskPluginDef){
				BpmTaskPluginDef bpmTaskPluginDef = (BpmTaskPluginDef)bpmPluginDef;
				BpmTaskPlugin bpmTaskPlugin = bpmPluginFactory.buildTaskPlugin(bpmPluginContext, eventType);
				if(bpmTaskPlugin==null) continue;
				
				if(bpmPluginContext.getEventTypes().contains(eventType)){
					bpmTaskPlugin.execute(bpmTaskPluginSession, bpmTaskPluginDef);
				}
			
			}
		}
	}
	
	
	
}
