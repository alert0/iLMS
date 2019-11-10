package com.hotent.bpmx.plugin.task.restful.context;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.w3c.dom.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.model.process.def.Restful;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractBpmTaskPluginContext;
import com.hotent.bpmx.plugin.task.restful.def.RestfulInvokePluginDef;
import com.hotent.bpmx.plugin.task.restful.entity.ObjectFactory;
import com.hotent.bpmx.plugin.task.restful.entity.RestFuls;
import com.hotent.bpmx.plugin.task.restful.plugin.RestfulInvokePlugin;
             
public class RestFulsPluginContext extends AbstractBpmTaskPluginContext{
	private static final long serialVersionUID = -5879623294061566500L;

	@Override
	public List<EventType> getEventTypes() {
		List<EventType> list=new ArrayList<EventType>();
		list.add(EventType.TASK_CREATE_EVENT);
		list.add(EventType.TASK_COMPLETE_EVENT);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends RunTimePlugin> getPluginClass() {
		return (Class<? extends RunTimePlugin>) RestfulInvokePlugin.class;
	}

	@Override
	public String getPluginXml() {
		RestfulInvokePluginDef pluginDef = (RestfulInvokePluginDef) this.getBpmPluginDef();
		String xml = "";
		try {
			if(BeanUtils.isEmpty(pluginDef.getRestfulList()))return xml;
			xml = JAXBUtil.marshall(RestfulInvokePluginDef.getRestfulExt(pluginDef), ObjectFactory.class);
			xml = xml.replace("encoding=\"utf-8\"", "encoding=\"UTF-8\"");
			xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n", "");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	@Override
	public String getJson() {
		RestfulInvokePluginDef pluginDef=(RestfulInvokePluginDef) this.getBpmPluginDef();
		return JSON.toJSONString(pluginDef);
	}
	
	

	@Override
	protected BpmPluginDef parseJson(String pluginJson) {
		JSONArray array = JSON.parseArray(pluginJson) ;
		List<Restful> restFuls = new ArrayList<Restful>();
		for (Object object : array) {
			String objStr = JSONObject.toJSONString(object);
			Restful restFul = JSONObject.toJavaObject(JSONObject.parseObject(objStr), Restful.class);
			if(StringUtil.isNotEmpty(restFul.getHeader())){
				try {
					restFul.setHeader(com.hotent.base.core.encrypt.Base64.getBase64(restFul.getHeader()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			restFuls.add(restFul);
		}
		RestfulInvokePluginDef def = new RestfulInvokePluginDef();
		def.setRestfulList(restFuls);
		return def;
	}

	@Override
	protected BpmPluginDef parseElement(Element element) {
		String xml = XmlUtil.getXML(element);
		RestfulInvokePluginDef def ;
		 try {
			RestFuls restfuls = (RestFuls) JAXBUtil.unmarshall(xml,ObjectFactory.class);
			def =RestfulInvokePluginDef.getRestfuls(restfuls);
			return def;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@Override
	public String getTitle() {
		return "restFul接口事件";
	}
	
	
	public String getJsonByParentFlowKey(String flowKey) {
		if(StringUtil.isEmpty(flowKey))  flowKey = BpmConstants.LOCAL;
		RestfulInvokePluginDef pluginDef=(RestfulInvokePluginDef) this.getBpmPluginDef();
		List<Restful> restFuls = pluginDef.getRestfulList();
		if(BeanUtils.isEmpty(restFuls)) return "[]";
		List<Restful> restFulList = new ArrayList<Restful>();
		for (Restful restful : restFuls) {
			if(StringUtil.isNotEmpty(restful.getHeader())){
				try {
					restful.setHeader(com.hotent.base.core.encrypt.Base64.getFromBase64(restful.getHeader()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if(StringUtil.isEmpty(restful.getParentDefKey()))  restful.setParentDefKey(BpmConstants.LOCAL); //如果为空，改为local_
			if(restful.getParentDefKey().equals(flowKey))  restFulList.add(restful);
		}
		if(restFulList.size() ==0) return "[]";
		return JSON.toJSONString(restFulList);
	}
	

}
