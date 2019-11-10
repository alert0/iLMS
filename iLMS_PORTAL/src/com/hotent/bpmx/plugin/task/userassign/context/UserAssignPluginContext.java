package com.hotent.bpmx.plugin.task.userassign.context;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.UserAssignRule;
import com.hotent.bpmx.api.plugin.core.context.UserQueryPluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractBpmTaskPluginContext;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleParser;
import com.hotent.bpmx.plugin.task.userassign.def.UserAssignPluginDef;
import com.hotent.bpmx.plugin.task.userassign.plugin.UserAssignPlugin;
import com.hotent.bpmx.plugin.task.userassign.plugin.UserQueryPlugin;
import com.jamesmurty.utils.XMLBuilder;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

/**
 * 流程用户解析插件。
 * <pre> 
 * 构建组：x5-bpmx-plugin
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-6-25-上午9:19:35
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class UserAssignPluginContext extends  AbstractBpmTaskPluginContext implements UserQueryPluginContext{

	
	public Class getPluginClass() {
		return UserAssignPlugin.class;
	}
	
	public Class<? extends RunTimePlugin> getUserQueryPluginClass() {
		return UserQueryPlugin.class;
	}
		

	public List<EventType> getEventTypes() {
		List<EventType> eventTypes = new ArrayList<EventType>();
		eventTypes.add(EventType.TASK_CREATE_EVENT);
		return eventTypes;
	}

	@Override
	public String getPluginXml() {		
		UserAssignPluginDef def =(UserAssignPluginDef) getBpmPluginDef();
		if(def.getRuleList().size()==0){
			return "";
		}
		try {
			XMLBuilder xmlBuilder = XMLBuilder.create("userAssign")
					.a("xmlns", "http://www.jee-soft.cn/bpm/plugins/task/userAssign");	
			
			UserAssignRuleParser.handXmlBulider(xmlBuilder, def.getRuleList());

			return xmlBuilder.asString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	


	/**
	 * 获取插件的JSON数据。
	 * [
	 *{"calcs":[
	 *					{"extractType":"no","groupKeys":"zhu","groupNames":"广州","groupType":"org","logicType":"or","pluginName":"","pluginType":"group"},
	 *					{"extractType":"no","logicType":"or","pluginName":"","script":"return false","pluginType":"hrScript"}
	 *				 ],
	 *				 "condition":"total>1","conditionMode":"1","description":"","groupNo":1,"name":""}
	 *]
	 */
	@Override
	public String getJson() {
		return getJsonByParentFlowKey(BpmConstants.LOCAL);
	}
	/***
	 *  通过 flowKey 获取指定类型的用户抽取列表
	 * @param FlowKey
	 * @return
	 */
	public String getJsonByParentFlowKey(String flowKey){
		if(StringUtil.isEmpty(flowKey))  flowKey = BpmConstants.LOCAL;
		
		List<UserAssignRule> ruleList=((UserAssignPluginDef)this.getBpmPluginDef()).getRuleList();
		if(BeanUtils.isEmpty(ruleList)) return "[]";
		
		List<UserAssignRule> rules = new ArrayList<UserAssignRule>();
		for(UserAssignRule rule : ruleList){
			if(StringUtil.isEmpty(rule.getParentFlowKey()))  rule.setParentFlowKey(BpmConstants.LOCAL); //如果为空，改为local_
			
			if(rule.getParentFlowKey().equals(flowKey))  rules.add(rule);
		}
		if(rules.size() ==0) return "[]";
		
		JsonConfig config=new JsonConfig();
		UserAssignRuleParser.handJsonConfig(config, rules);
		JSON json= JSONSerializer.toJSON(rules,config);
		
		return json.toString();
	}
	

	@Override
	protected BpmPluginDef parseElement(Element element) {
		UserAssignPluginDef userAssignPluginDef=new UserAssignPluginDef();
		List<UserAssignRule> userAssignRules = UserAssignRuleParser.parse(element);
		userAssignPluginDef.setRuleList(userAssignRules);
		return userAssignPluginDef;
	}

	
	/**
	 * 根据JSON 解析插件定义。
	 */
	@Override
	protected BpmPluginDef parseJson(String pluginJson) {
		
		UserAssignPluginDef def=new UserAssignPluginDef();
		if(StringUtil.isEmpty(pluginJson)) return def;
		JSONArray jsonArray=JSONArray.fromObject(pluginJson);
		List<UserAssignRule> ruleList=new ArrayList<UserAssignRule>();
		for(Object obj:jsonArray){
			JSONObject jsonObj=(JSONObject)obj;
			UserAssignRule rule=UserAssignRuleParser.getUserAssignRule(jsonObj);
			ruleList.add(rule);
		}
		def.setRuleList(ruleList);
		return def;
	}

	@Override
	public String getTitle() {
		return "用户分配插件";
	}
}
