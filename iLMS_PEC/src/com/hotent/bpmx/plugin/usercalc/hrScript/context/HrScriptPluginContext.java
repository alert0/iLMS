package com.hotent.bpmx.plugin.usercalc.hrScript.context;

import org.w3c.dom.Element;

import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractUserCalcPluginContext;
import com.hotent.bpmx.plugin.usercalc.cusers.def.CusersPluginDef;
import com.hotent.bpmx.plugin.usercalc.hrScript.def.HrScriptPluginDef;
import com.hotent.bpmx.plugin.usercalc.hrScript.runtime.HrScriptPlugin;

import net.sf.json.JSONObject;

public class HrScriptPluginContext extends AbstractUserCalcPluginContext {

	private static final long serialVersionUID = -2353875054502587417L;

	@Override
	public String getDescription() {
		HrScriptPluginDef def=(HrScriptPluginDef) this.getBpmPluginDef();
		if(def==null) return "";
		return def.getDescription();
	}
	@Override
	public Class<? extends RunTimePlugin> getPluginClass() {
		return HrScriptPlugin.class;
	}
	@Override
	public String getTitle() {
		return "人员脚本";
	}
	@Override
	protected BpmPluginDef parseElement(Element element) {
		HrScriptPluginDef hrScriptPluginDef = new HrScriptPluginDef();
		Element el = XmlUtil.getChildNodeByName(element, "content");
		Element descEL = XmlUtil.getChildNodeByName(element, "description");
		Element idEl= XmlUtil.getChildNodeByName(element, "scriptId");
		Element paramsEl= XmlUtil.getChildNodeByName(element, "params");
		hrScriptPluginDef.setScript(el.getTextContent());
		hrScriptPluginDef.setDescription(descEL==null?"人员脚本":descEL.getTextContent());
		hrScriptPluginDef.setScriptId(idEl==null?"":idEl.getTextContent());
		hrScriptPluginDef.setParams(paramsEl==null?"":paramsEl.getTextContent());
		return hrScriptPluginDef;
	}
	// <hrScript xmlns="http://www.jee-soft.cn/bpm/plugins/userCalc/hrScript" logicCal=""
	// extract="">
	// <content>
	// <![CDATA[
	//
	// ]]>
	// </content>
	// </hrScript>
	@Override
	public String getPluginXml() {
		HrScriptPluginDef def = (HrScriptPluginDef) this.getBpmPluginDef();
		if (def == null)
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append("<hrScript xmlns=\"http://www.jee-soft.cn/bpm/plugins/userCalc/hrScript\" ");
		sb.append("  logicCal=\"" + def.getLogicCal().getKey() + "\" extract=\"" + def.getExtract().getKey() + "\">");
		sb.append("<content>");
		sb.append("<![CDATA[");
		sb.append(def.getScript());
		sb.append("]]>");
		sb.append("</content>");
		
		sb.append("<description>");
		sb.append("<![CDATA[");
		sb.append(def.getDescription());
		sb.append("]]>");
		sb.append("</description>");
		
		sb.append("<scriptId>");
		sb.append("<![CDATA[");
		sb.append(def.getScriptId());
		sb.append("]]>");
		sb.append("</scriptId>");
		
		sb.append("<params>");
		sb.append("<![CDATA[");
		sb.append(def.getParams());
		sb.append("]]>");
		sb.append("</params>");
		
		sb.append("</hrScript>");
		return sb.toString();
	}
	@Override
	protected BpmPluginDef parseJson(JSONObject pluginJson) {
		HrScriptPluginDef def = new HrScriptPluginDef();
		String script = pluginJson.getString("script");
		String description=JsonUtil.getString(pluginJson, "description","人员脚本"); 
		def.setScript(script);
		def.setDescription(description);
		if(pluginJson.containsKey("scriptId")){
			String scriptId = pluginJson.getString("scriptId");
			def.setScriptId(scriptId);
		}
		if(pluginJson.containsKey("params")){
			String params = pluginJson.getString("params");
			def.setParams(params);
		}
		return def;
	}
}
