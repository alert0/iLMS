package com.hotent.bpmx.plugin.usercalc.samenode.context;

import org.w3c.dom.Element;

import com.hotent.base.core.util.JsonUtil;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractUserCalcPluginContext;
import com.hotent.bpmx.plugin.usercalc.samenode.def.SameNodePluginDef;
import com.hotent.bpmx.plugin.usercalc.samenode.runtime.SameNodePlugin;

import net.sf.json.JSONObject;

public class SameNodePluginContext extends AbstractUserCalcPluginContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 919433269116580830L;

	@Override
	public String getDescription() {
		SameNodePluginDef def = (SameNodePluginDef) this.getBpmPluginDef();
		if(def==null) return "";
		return "节点："+def.getNodeId();
	}

	@Override
	public String getTitle() {
		return "相同节点执行人";
	}


	@Override
	public Class<? extends RunTimePlugin> getPluginClass() {
		return SameNodePlugin.class;
	}
	
//	<sameNode xmlns="http://www.jee-soft.cn/bpm/plugins/userCalc/sameNode"
//		    nodeId="" logicCal="" extract=""/>

	@Override
	public String getPluginXml() {
		SameNodePluginDef def=(SameNodePluginDef)this.getBpmPluginDef();
		if(def==null) return "";
		String xml="<sameNode xmlns=\"http://www.jee-soft.cn/bpm/plugins/userCalc/sameNode\"  " +
				" nodeId=\""+ def.getNodeId() +"\" logicCal=\""+ def.getLogicCal().getKey() +"\"" +
						" extract=\""+ def.getExtract().getKey() +"\"/>";
		return xml;
	}

	@Override
	protected BpmPluginDef parseElement(Element element) {
		SameNodePluginDef def=new SameNodePluginDef();
		String nodeId=element.getAttribute("nodeId");
		def.setNodeId(nodeId);
		return def;
	}

	@Override
	protected BpmPluginDef parseJson(JSONObject pluginJson) {
		SameNodePluginDef def=new SameNodePluginDef();
		String nodeId=JsonUtil.getString(pluginJson, "nodeId");
		def.setNodeId(nodeId);
		return def;
	}

}
