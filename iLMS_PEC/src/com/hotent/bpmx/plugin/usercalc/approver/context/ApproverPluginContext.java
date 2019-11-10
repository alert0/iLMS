package com.hotent.bpmx.plugin.usercalc.approver.context;

import org.w3c.dom.Element;

import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractUserCalcPluginContext;
import com.hotent.bpmx.plugin.usercalc.approver.def.ApproverPluginDef;
import com.hotent.bpmx.plugin.usercalc.approver.runtime.ApproverPlugin;

import net.sf.json.JSONObject;

/**
 * 流程实例审批人
 * @author Administrator
 *
 */
public class ApproverPluginContext extends AbstractUserCalcPluginContext {

	@Override
	public String getDescription() {
		
		return "流程实例审批人";
	}

	@Override
	public String getTitle() {
		return "流程实例审批人";
	}

	
	@Override
	public Class<? extends RunTimePlugin> getPluginClass() {
		return ApproverPlugin.class;
	}

	@Override
	public String getPluginXml() {
		ApproverPluginDef def=(ApproverPluginDef)getBpmPluginDef();
		if(def==null) return "";
		return "<approver xmlns=\"http://www.jee-soft.cn/bpm/plugins/userCalc/approver\"" + 
			  "	logicCal=\""+ def.getLogicCal().getKey() +"\" extract=\""+def.getExtract().getKey() +"\"/>";
	}

	@Override
	protected BpmPluginDef parseElement(Element element) {
		ApproverPluginDef def=new ApproverPluginDef();
		return def;
	}

	@Override
	protected BpmPluginDef parseJson(JSONObject pluginJson) {
		ApproverPluginDef def=new ApproverPluginDef();
		return def;
	}

}
