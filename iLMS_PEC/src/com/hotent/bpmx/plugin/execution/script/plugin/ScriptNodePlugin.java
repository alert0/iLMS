package com.hotent.bpmx.plugin.execution.script.plugin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.plugin.core.def.BpmExecutionPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmExecutionPluginSession;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmExecutionPlugin;
import com.hotent.bpmx.plugin.execution.script.def.ScriptNodePluginDef;

/**
 * 脚本节点插件运行时。
 * @author ray
 *
 */
public class ScriptNodePlugin extends AbstractBpmExecutionPlugin{
	
	@Resource
	GroovyScriptEngine groovyScriptEngine  ;

	public Void execute(BpmExecutionPluginSession pluginSession,
			BpmExecutionPluginDef pluginDef) {
		ScriptNodePluginDef nodeDef=(ScriptNodePluginDef)pluginDef;
		BpmDelegateExecution execution= pluginSession.getBpmDelegateExecution();
		
		Map<String, Object> vars=new HashMap<String, Object>();
		vars.put(BpmConstants.BPMN_EXECUTION_ID, execution.getId());
		vars.put(BpmConstants.BPMN_INST_ID, execution.getBpmnInstId());
		vars.putAll( execution.getVariables());
		
		//从上下文获取bo实体数据。
		Map<String,BoData> boDatas= BpmContextUtil.getBoFromContext();
		if(BeanUtils.isNotEmpty(boDatas)){
			vars.putAll(boDatas);
		}
		
		
		String script=nodeDef.getScript();
		groovyScriptEngine.execute(script, vars);
		return null;
	}

}
