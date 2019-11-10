package com.hotent.bpmx.plugin.execution.webservice.context;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.w3c.dom.Element;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractBpmExecutionPluginContext;
import com.hotent.bpmx.plugin.execution.webservice.def.WebServiceNodePluginDef;
import com.hotent.bpmx.plugin.execution.webservice.plugin.WebServiceTaskPlugin;
import com.jamesmurty.utils.XMLBuilder;

public class WebServicePluginContext extends AbstractBpmExecutionPluginContext{

	@Override
	public List<EventType> getEventTypes() {
		List<EventType> list=new ArrayList<EventType>();
		list.add(EventType.AUTO_TASK_EVENT);
		return list;
	}

	@Override
	public Class<? extends RunTimePlugin> getPluginClass() {
		return WebServiceTaskPlugin.class;
	}

	@Override
	public String getPluginXml() {
		WebServiceNodePluginDef def = (WebServiceNodePluginDef) this.getBpmPluginDef();
		XMLBuilder xmlBuilder;
		try {
			xmlBuilder = XMLBuilder.create("webService")
						.a("xmlns", "http://www.jee-soft.cn/bpm/plugins/execution/webService")  
						.a("name",def.getName())
						.a("alias",def.getAlias())  
			 
			.e("params").d(def.getParams()).up()
			.e("outPutScript").d(def.getOutPutScript()); 
			
		return xmlBuilder.asString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "";
	}
	
	@Override
	protected BpmPluginDef parseElement(Element element) {
		WebServiceNodePluginDef def = new WebServiceNodePluginDef();
		def.setName(element.getAttribute("name"));
		def.setAlias(element.getAttribute("alias"));
		
		Element inputEl=XmlUtil.getChildNodeByName(element, "params");
		String params = inputEl.getTextContent();
		def.setParams(params);
		
		Element outputEl=XmlUtil.getChildNodeByName(element, "outPutScript");
		String outputScript = outputEl.getTextContent();
		def.setOutPutScript(outputScript);
		
		return def;
	}

	@Override
	public String getJson() {
		WebServiceNodePluginDef def = (WebServiceNodePluginDef) this.getBpmPluginDef();
		JSONObject object = JSONObject.fromObject(def);
		return object.toString();
	}

	@Override
	protected BpmPluginDef parseJson(String pluginJson) {
		WebServiceNodePluginDef bpmPluginDef = new WebServiceNodePluginDef();
		JSONObject object = JSONObject.fromObject(pluginJson);
		
		bpmPluginDef.setName(JsonUtil.getString(object, "name"));
		bpmPluginDef.setAlias(JsonUtil.getString(object, "alias"));
		
		bpmPluginDef.setParams(JsonUtil.getString(object, "params"));
		bpmPluginDef.setOutPutScript(JsonUtil.getString(object, "outPutScript"));
		
		return bpmPluginDef;
	}

	@Override
	public String getTitle() {
		return "webService";
	}
	

}
