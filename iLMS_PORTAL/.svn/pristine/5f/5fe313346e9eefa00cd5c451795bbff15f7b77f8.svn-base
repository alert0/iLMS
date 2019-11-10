package com.hotent.mini.controller.system.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
 
import com.hotent.mini.controller.system.manager.SysServiceSetManager;
 
//import com.hotent.service.api.handler.ServiceClient;
import com.hotent.service.api.model.InvokeCmd;
import com.hotent.service.api.model.InvokeResult;
import com.hotent.service.model.impl.DefaultInvokeCmd;
import com.hotent.service.parse.WebServiceParamHandler;
import com.hotent.sys.persistence.dao.SysServiceParamDao;
import com.hotent.sys.persistence.dao.SysServiceSetDao;
import com.hotent.sys.persistence.model.SysServiceParam;
import com.hotent.sys.persistence.model.SysServiceSet;

@Service("sysServiceSetManager")
public class SysServiceSetManagerImpl extends AbstractManagerImpl<String, SysServiceSet> implements SysServiceSetManager{
	@Resource
	SysServiceSetDao sysServiceSetDao;
	@Resource
	SysServiceParamDao sysServiceParamDao;
//	@Resource
//	ServiceClient serviceClient;
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	
	@Override
	protected Dao<String, SysServiceSet> getDao() {
		return sysServiceSetDao;
	}
	/**
	 * 创建实体包含子表实体
	 */
	public void create(SysServiceSet sysServiceSet){
    	super.create(sysServiceSet);
    	String setId = sysServiceSet.getId();
    	sysServiceParamDao.delByMainId(setId);
    	List<SysServiceParam> sysServiceParamList=sysServiceSet.getSysServiceParamList();
    	for(SysServiceParam sysServiceParam:sysServiceParamList){
    		sysServiceParam.setSetId(setId);
    		sysServiceParamDao.create(sysServiceParam);
    	}
    }
	
	/**
	 * 删除记录包含子表记录
	 */
	public void remove(String entityId){
    	sysServiceParamDao.delByMainId(entityId);
    	super.remove(entityId);
	}
	
	/**
	 * 批量删除包含子表记录
	 */
	public void removeByIds(String[] entityIds){
		for(String id:entityIds){
			this.remove(id);
		}
	}
    
	/**
	 * 获取实体
	 */
    public SysServiceSet get(String entityId){
    	SysServiceSet sysServiceSet=super.get(entityId);
    	List<SysServiceParam> sysServiceParamList=sysServiceParamDao.getByMainId(entityId);
    	sysServiceSet.setSysServiceParamList(sysServiceParamList);
    	return sysServiceSet;
    }
    
    /**
     * 更新实体同时更新子表记录
     */
    public void update(SysServiceSet sysServiceSet){
    	super.update(sysServiceSet);
    	String setId = sysServiceSet.getId();
    	sysServiceParamDao.delByMainId(setId);
    	List<SysServiceParam> sysServiceParamList=sysServiceSet.getSysServiceParamList();
    	for(SysServiceParam sysServiceParam:sysServiceParamList){
    		sysServiceParam.setSetId(setId);
    		sysServiceParamDao.create(sysServiceParam);
    	}
    }
    
	public List<SysServiceParam> getParams(String setId) {
		return sysServiceParamDao.getByMainId(setId);
	}
	
	//获取参数值map
	private Map<String,String> getParamValueMap(String params){
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(params);
		Map<String,String> map = new HashMap<String, String>();		
		if(jsonElement.isJsonArray()){
			JsonArray jarray = jsonElement.getAsJsonArray();
			for(JsonElement jelement : jarray){
				JsonObject jobject = jelement.getAsJsonObject();
				String name = jobject.get("name").getAsString();
				String val = "";
				JsonElement valElement = jobject.get("val");
				if(valElement!=null){
					val = valElement.getAsString();
					String type = jobject.get("type").getAsString();
					if("date".equals(type)){
						val = getDateString(val);
					}
				}
				map.put(name, val);
			}
		}
		return map;
	}
	
	private String getDateString(String val){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time;
		try {
			time = formatDate.parse(val);
			return DateFormatUtils.format(time, "yyyy-MM-dd'T'HH:mm:ss.sssZZ");
		} catch (ParseException e) {
			e.printStackTrace();
			return val;
		}
	}
	
	//处理泛型
	private void handlerGenerics(JsonArray childrenAry,JsonElement jsonElement,Map<String,String> params){
		JsonElement jelement;
		if(jsonElement.isJsonArray()){
			jelement = childrenAry.get(0);
		}
		else{
			jelement = childrenAry.get(1);
		}
		if(!jelement.isJsonObject())return;
		JsonObject jobject = jelement.getAsJsonObject();
		
		String type = jobject.get("type").getAsString();
		Boolean isGenerics = false;					
		JsonElement genericsValue = jobject.get("generics");
		if(BeanUtils.isNotEmpty(genericsValue)){
			isGenerics = genericsValue.getAsBoolean();
		}
		
		JsonElement generics;
		
		if("Bean".equals(type)){
			generics = new JsonObject();
			JsonArray children = jobject.get("children").getAsJsonArray();
			//继续构建jsonParam
			buildJsonParam(children, generics,params);
		}
		//泛型
		else if(isGenerics){
			generics = new JsonObject();
			JsonArray children = jobject.get("children").getAsJsonArray();
			//继续构建jsonParam
			handlerGenerics(children, generics,params);
		}
		else{
			generics = new JsonPrimitive("");
		}
		
		if(jsonElement.isJsonArray()){
			JsonArray ja = jsonElement.getAsJsonArray();
			if(ja.size()==0){
				ja.add(generics);
			}
		}
		else{
			String key = "";
			JsonElement jsonKey = childrenAry.get(0);
			if(jsonKey.isJsonArray()||jsonKey.isJsonObject()){
				key = jsonKey.toString();
			}
			else if(jsonKey.isJsonPrimitive()){
				key = jsonKey.getAsString();
			}
			jsonElement.getAsJsonObject().add(key, generics);
		}
	}
	
	//递归构建jsonParam
	private void buildJsonParam(JsonArray jarray,JsonElement jsonParamObj,Map<String,String> params){
		for(JsonElement jelement : jarray){
			if(!jelement.isJsonObject())continue;
			JsonObject jobject = jelement.getAsJsonObject();
			String key = jobject.get("key").getAsString();
			String type = jobject.get("type").getAsString();
			Boolean generics = false;					
			JsonElement genericsValue = jobject.get("generics");
			if(BeanUtils.isNotEmpty(genericsValue)){
				generics = genericsValue.getAsBoolean();
			}
			if("Bean".equals(type)){
				JsonArray children = jobject.get("children").getAsJsonArray();
				JsonElement beanJsonObject = new JsonObject();
				JsonElement bind = jobject.get("bind");
				if(BeanUtils.isNotEmpty(bind)){
					beanJsonObject = handlerBind(bind.getAsJsonObject(), beanJsonObject, params);
				}
				buildJsonParam(children, beanJsonObject,params);
				if(jsonParamObj.isJsonObject()){
					jsonParamObj.getAsJsonObject().add(key,beanJsonObject);
				}
			}
			//泛型
			else if(generics){
				JsonArray children = jobject.get("children").getAsJsonArray();
				int count = children.size();
				JsonElement genericsJsonElement; 
				if(count==1){
					//一种类型的泛型为 集合
					genericsJsonElement = new JsonArray();
				}
				else{
					//两种类型额泛型为 对象
					genericsJsonElement = new JsonObject();
				}
				JsonElement bind = jobject.get("bind");
				if(BeanUtils.isNotEmpty(bind)){
					genericsJsonElement = handlerBind(bind.getAsJsonObject(), genericsJsonElement, params);
				}
				handlerGenerics(children,genericsJsonElement,params);
				if(jsonParamObj.isJsonObject()){
					jsonParamObj.getAsJsonObject().add(key,genericsJsonElement);
				}
			}
			else{
				JsonElement jsonPrimitive = new JsonPrimitive("");
				JsonElement bind = jobject.get("bind");
				if(BeanUtils.isNotEmpty(bind)){
					jsonPrimitive = handlerBind(bind.getAsJsonObject(), jsonPrimitive, params);
				}
				if(jsonParamObj.isJsonObject()){
					jsonParamObj.getAsJsonObject().add(key,jsonPrimitive);
				}
			}
		}
	}
	
	//处理参数绑定
	private JsonElement handlerBind(JsonObject bind,JsonElement jsonElement,Map<String,String> params){
		int type = bind.get("type").getAsInt();
		String value = bind.get("value").getAsString();
		JsonElement jsonVal = null;
		switch(type){
			//固定值
			case 1:
				jsonVal = new JsonPrimitive(value);
				break;
			//参数
			case 2:
				jsonVal = new JsonPrimitive(params.get(value));
				break;
			//脚本
			case 3:
				Gson gson = new Gson();
				//TODO 在流程中调用时，传入流程运行上下文参数
				Object obj = groovyScriptEngine.executeObject(value,new HashMap<String,Object>());
				jsonVal = GsonUtil.toJsonTree(obj);
				break;
		}
		if(BeanUtils.isEmpty(jsonVal))return jsonElement;
		if(jsonElement.isJsonArray()){
			if(!jsonVal.isJsonArray()){
				JsonArray jarray = new JsonArray();
				jarray.add(jsonVal);
				jsonVal = jarray;
			}
		}
		return jsonVal;
	}
	
	private String getJsonParam(String params,SysServiceSet serviceSet){
		String inputSet = serviceSet.getInputSet();
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(inputSet);
		JsonObject jsonParamObj = new JsonObject();
		JsonObject jsonParamObj2 = new JsonObject();
		if(jsonElement.isJsonArray()){
			WebServiceParamHandler.buildJsonParam(jsonElement.getAsJsonArray(), jsonParamObj, getParamValueMap(params),WebServiceParamHandler.INPUT);
//			buildJsonParam(jsonElement.getAsJsonArray(), jsonParamObj2, getParamValueMap(params));
		}
	//	System.err.println(jsonParamObj.toString());
	//	System.err.println(jsonParamObj2.toString());
		String ss = jsonParamObj.toString();
		return ss;
	}
	
	public InvokeResult invokeService(String serviceSetId, String params) {
//		SysServiceSet serviceSet = this.get(serviceSetId);
//		InvokeCmd invokeCmd = new DefaultInvokeCmd();
//		invokeCmd.setAddress(serviceSet.getAddress());
//		invokeCmd.setJsonParam(getJsonParam(params, serviceSet));
//		invokeCmd.setOperatorName(serviceSet.getMethodName());
//		invokeCmd.setOperatorNamespace(serviceSet.getNamespace());
//		invokeCmd.setNeedPrefix(serviceSet.getSoapAction()=='Y');
//		return serviceClient.invoke(invokeCmd);
		return null;
	}
	@Override
	public SysServiceSet getByAlias(String alias) {
		return sysServiceSetDao.getByAlias(alias);
	}
}
