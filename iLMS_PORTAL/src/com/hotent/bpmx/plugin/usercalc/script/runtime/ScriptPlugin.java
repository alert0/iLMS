package com.hotent.bpmx.plugin.usercalc.script.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.plugin.core.runtime.AbstractUserCalcPlugin;
import com.hotent.bpmx.plugin.usercalc.script.def.ScriptPluginDef;

/**
 * 人员脚本获取插件。
 * <pre> 
 * 构建组：x5-bpmx-plugin
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-6-16-下午3:06:18
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class ScriptPlugin extends AbstractUserCalcPlugin{
	
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	@Override
	public List<BpmIdentity> queryByPluginDef(BpmUserCalcPluginSession pluginSession, BpmPluginDef pluginDef) {
		ScriptPluginDef def=(ScriptPluginDef)pluginDef;
	 
		String script=def.getScript();
		if(StringUtil.isEmpty(script)){
			return Collections.EMPTY_LIST;
		}
		
		//获取流程变量
		Map<String, Object> variables= pluginSession.getVariables();
		
		
		Map<String,BoData > boMap= BpmContextUtil.getBoFromContext();
		if(BeanUtils.isNotEmpty(boMap)){
			variables.putAll(boMap); 
		}
		 
		Set<BpmIdentity> set=(Set<BpmIdentity>) groovyScriptEngine.executeObject(script,variables);
		List<BpmIdentity> list=new ArrayList<BpmIdentity>();
		
		list.addAll(set);
		return list;
	}

	@Override
	public boolean supportPreView() {
		return false;
	}

}
