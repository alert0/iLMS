package com.hotent.bpmx.plugin.execution.webservice.plugin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.exception.WorkFlowException;
import com.hotent.bpmx.api.plugin.core.def.BpmExecutionPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmExecutionPluginSession;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmExecutionPlugin;
import com.hotent.bpmx.plugin.execution.webservice.def.WebServiceNodePluginDef;
import com.hotent.service.api.handler.ServiceClient;
import com.hotent.service.api.model.InvokeCmd;
import com.hotent.service.api.model.InvokeResult;
import com.hotent.service.model.impl.DefaultInvokeCmd;
import com.hotent.service.parse.WebServiceParamHandler;
import com.hotent.sys.persistence.dao.SysServiceSetDao;
import com.hotent.sys.persistence.model.SysServiceSet;

public class WebServiceTaskPlugin  extends AbstractBpmExecutionPlugin{
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	@Resource
	ServiceClient serviceClient;
	@Resource
	SysServiceSetDao sysServiceSetDao;
	@Override
	public Void execute(BpmExecutionPluginSession pluginSession, BpmExecutionPluginDef pluginDef) {
		WebServiceNodePluginDef webServiceDef = (WebServiceNodePluginDef) pluginDef;
		SysServiceSet serviceSet = sysServiceSetDao.getByAlias(webServiceDef.getAlias());
		//脚本需要的变量
		Map<String, Object> variables = pluginSession.getBpmDelegateExecution().getVariables();
		Map<String,BoData> boMap= BpmContextUtil.getBoFromContext();
		if(BeanUtils.isNotEmpty(boMap)){
			variables.putAll(boMap); 
		}
		
		InvokeCmd invokeCmd = new DefaultInvokeCmd();
		invokeCmd.setAddress(serviceSet.getAddress());
		invokeCmd.setOperatorName(serviceSet.getMethodName());
		invokeCmd.setOperatorNamespace(serviceSet.getNamespace());
		invokeCmd.setNeedPrefix(serviceSet.getSoapAction()=='Y');
		
		String params = getJsonParam(webServiceDef,variables,serviceSet.getInputSet());
		
		invokeCmd.setJsonParam(params);
		
		InvokeResult invokeResult = serviceClient.invoke(invokeCmd);
		handleResult(invokeResult,pluginSession,webServiceDef,variables);
		return null;
	}
	
	private String getJsonParam(WebServiceNodePluginDef webServiceDef,Map<String, Object> variables,String inputSet){
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(inputSet);
		JsonObject jsonParamObj = new JsonObject();
		variables.putAll(getConfParam(webServiceDef, variables));
		
		if(jsonElement.isJsonArray()){
			WebServiceParamHandler.buildJsonParam(jsonElement.getAsJsonArray(),jsonParamObj,variables,WebServiceParamHandler.INPUT);
		}
		String ss = jsonParamObj.toString();
		return ss;
	}
	/***
	 * 拼装请求参数
	 * @return
	 */
	private Map<String,Object> getConfParam(WebServiceNodePluginDef webServiceDef,Map<String, Object> variables) {
		Map<String,Object> p = new HashMap<String, Object>();

		String inputStr =  webServiceDef.getParams(); 
		JSONArray params = JSONArray.parseArray(inputStr);
		if(BeanUtils.isEmpty(params))return p;
		try {
			for (int i = 0; i < params.size(); i++) {
				JSONObject param = params.getJSONObject(i);
				String bindType = param.getString("bindType");
				String bindValue = param.getString("bindValue");
				String key = param.getString("name");
				Object value ;
				
				if("var".equals(bindType)){
					value = variables.get(bindValue);
				}else if("bo".equals(bindType)){
					String [] boStr = bindValue.split("\\.");
					BoData bo =(BoData) variables.get(boStr[0]);
					value = bo.getByKey(boStr[1]);
				}else{
					value = groovyScriptEngine.executeObject(bindValue, variables);
				}
			  p.put(key, value);
			}
		} catch (Exception e) {
			throw new RuntimeException("webService 节点解析 输入参数异常！    详细信息："+inputStr,e);
		}
		return p;
	}
	/**
	 * 处理结果
	 * @param invokeResult
	 * @param pluginSession
	 * @param webServiceDef
	 * @param variables 
	 */
	private void handleResult(InvokeResult invokeResult, BpmExecutionPluginSession pluginSession,WebServiceNodePluginDef webServiceDef, Map<String, Object> variables) {
		if(invokeResult.isFault()){
			throw new WorkFlowException("webService 调用异常！");
		}
		String script = webServiceDef.getOutPutScript();
		if(StringUtil.isEmpty(script)) return;
		String jsonStr = invokeResult.getJson();
		
		variables.put("invokeResult", invokeResult);
		variables.put("pluginSession", pluginSession);
		if(!invokeResult.isVoid() && StringUtil.isNotEmpty(jsonStr))
			variables.put("data", JSONObject.parse(jsonStr));
		
		groovyScriptEngine.execute(script,variables);
	}
}