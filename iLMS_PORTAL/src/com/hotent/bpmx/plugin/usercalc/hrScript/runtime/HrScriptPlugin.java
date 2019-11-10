package com.hotent.bpmx.plugin.usercalc.hrScript.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.plugin.core.runtime.AbstractUserCalcPlugin;
import com.hotent.bpmx.plugin.usercalc.hrScript.def.HrScriptPluginDef;

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
public class HrScriptPlugin extends AbstractUserCalcPlugin{
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	@Resource
	BoDataService boDataService;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Override
	public List<BpmIdentity> queryByPluginDef(BpmUserCalcPluginSession pluginSession, BpmPluginDef pluginDef) {
		HrScriptPluginDef def=(HrScriptPluginDef)pluginDef;
	 
		String script=def.getScript();
		if(StringUtil.isEmpty(script)){
			return Collections.EMPTY_LIST;
		}
		//获取流程变量
		Map<String, Object> variables= pluginSession.getVariables();
		Map<String,BoData > boMap= BpmContextUtil.getBoFromContext();
		
		ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
		if(BeanUtils.isEmpty(actionCmd)){
			String instanceId = (String) variables.get("instanceId_");
			DefaultBpmProcessInstance bpmProcessInstance = bpmProcessInstanceManager.get(instanceId);
			List<BoData> boDatas = boDataService.getDataByInst(bpmProcessInstance);
			boMap=new HashMap<String, BoData>();
			
			for(BoData data:boDatas){
				String code=data.getBoDef().getAlias();
				boMap.put(code, data);
			}
			
			DefaultTaskFinishCmd taskCmd = new DefaultTaskFinishCmd();
			taskCmd.setVariables(variables);
			
			
			ContextThreadUtil.setActionCmd(taskCmd);
			
		} 
		
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
