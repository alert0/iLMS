package com.hotent.bpmx.plugin.execution.procnotify.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.w3c.dom.Element;

import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractBpmExecutionPluginContext;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleParser;
import com.hotent.bpmx.plugin.execution.procnotify.def.ProcNotifyPluginDef;
import com.hotent.bpmx.plugin.execution.procnotify.entity.OnEnd;
import com.hotent.bpmx.plugin.execution.procnotify.entity.ProcNotify;
import com.hotent.bpmx.plugin.execution.procnotify.plugin.ProcNotifyPlugin;
import com.hotent.bpmx.plugin.task.tasknotify.def.TaskNotifyPluginDef;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyItem;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyVo;
import com.hotent.bpmx.plugin.task.tasknotify.util.NotifyUtil;
import com.jamesmurty.utils.XMLBuilder;

public class ProcNotifyPluginContext extends AbstractBpmExecutionPluginContext{
	
	
	public List<EventType> getEventTypes() {
		List<EventType> eventTypes = new ArrayList<EventType>();
		eventTypes.add(EventType.END_EVENT);
		return eventTypes;
	}

	public Class<? extends RunTimePlugin> getPluginClass() {
		return ProcNotifyPlugin.class;
	}

	private NotifyVo convert(OnEnd onEnd,Element pluginEl){
		NotifyVo notifyVo = new NotifyVo();
		notifyVo.setEventType(EventType.END_EVENT);
		Element onEndEl = XmlUtil.getChildNodeByName(pluginEl, "onEnd");		
		List<NotifyItem> notifyItems = NotifyUtil.parseNotifyItems(onEndEl);
		notifyVo.setNotifyItemList(notifyItems);		
		return notifyVo;
	}

	/**
	 * 构建插件XML。
	 * xml格式如下。
	 * <pre>
	 * &lt;procNotify xmlns="http://www.jee-soft.cn/bpm/plugins/execution/procNotify" >
	 * &lt;onEnd >
     * &lt;notify xmlns="http://www.jee-soft.cn/bpm/plugins/task/baseNotify" msgTypes="sms">
     *   &lt;userRule xmlns="http://www.jee-soft.cn/bpm/plugins/userCalc/base" groupNo="1">
     *        &lt;calcs>&lt;/calcs>
     *   &lt;/userRule>
     *&lt;/notify>
     *&lt;notify xmlns="http://www.jee-soft.cn/bpm/plugins/task/baseNotify" msgTypes="mail">&lt;/notify>
	 *&lt;/onEnd>
	 *&lt;/procNotify>
	 *</pre>
	 */
	@Override
	public String getPluginXml() {
		ProcNotifyPluginDef def=(ProcNotifyPluginDef)this.getBpmPluginDef();
		try {
			XMLBuilder xmlBuilder = XMLBuilder.create("procNotify")
					.a("xmlns", "http://www.jee-soft.cn/bpm/plugins/execution/procNotify");
			
			xmlBuilder= xmlBuilder.e("onEnd");
			NotifyVo createVo=def.getNotifyVoMap().get(EventType.END_EVENT);
			if(createVo!=null){
				NotifyUtil.handXmlBuilder(createVo,xmlBuilder);
			}
			return xmlBuilder.asString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getJson() {
		ProcNotifyPluginDef def=(ProcNotifyPluginDef) this.getBpmPluginDef();
		Collection<NotifyVo> assignRules=def.getNotifyVoMap().values();
		JsonConfig config=new JsonConfig();
		NotifyUtil.getJsonConfig(config,assignRules);
		JSONObject jsonObject = JSONObject.fromObject(def, config);
		Object json = jsonObject.getJSONObject("notifyVoMap").getJSONObject("endEvent").getJSONArray("notify");
		//JSON json= JSONSerializer.toJSON(def, config);
		return json.toString();
	}

	@Override
	protected BpmPluginDef parseJson(String pluginJson) {
		ProcNotifyPluginDef procNotifyPluginDef = new ProcNotifyPluginDef();
		
		JSONArray endJson = JSONArray.fromObject(pluginJson);
		//JSONArray endJson=jsonObject.getJSONArray(EventType.END_EVENT.getEventName());
		NotifyVo createVo= NotifyUtil.getNotifyVo(endJson);
		
		
		procNotifyPluginDef.addNotifyVo(EventType.END_EVENT, createVo);
		
		return procNotifyPluginDef;
	}

	@Override
	protected BpmPluginDef parseElement(Element element) {
		String xml = XmlUtil.getXML(element);
		ProcNotifyPluginDef procNotifyPluginDef = new ProcNotifyPluginDef();
		try {
			ProcNotify procNotify = (ProcNotify)JAXBUtil.unmarshall(xml,com.hotent.bpmx.plugin.execution.procnotify.entity.ObjectFactory.class);
			List<NotifyVo> notifyVoList = new ArrayList<NotifyVo>();
			OnEnd onEnd=procNotify.getOnEnd();
			if(onEnd!=null){
				NotifyVo notifyVoOnEnd = convert(onEnd,element);									
				notifyVoList.add(notifyVoOnEnd);
				procNotifyPluginDef.getNotifyVoMap().put(EventType.END_EVENT, notifyVoOnEnd);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return procNotifyPluginDef;
	}

	@Override
	public String getTitle() {
		
		return "抄送通知";
	}
}
