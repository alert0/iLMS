package com.hotent.bpmx.plugin.core.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.constant.LogicType;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.UserAssignRule;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.plugin.core.context.PluginParse;
import com.hotent.bpmx.api.plugin.core.context.UserCalcPluginContext;
import com.hotent.bpmx.plugin.core.context.AbstractUserCalcPluginContext;
import com.jamesmurty.utils.XMLBuilder;
/**
 * 
 * <pre> 
 * 描述：对用户分配规则元素的解析器
 * 构建组：x5-bpmx-plugin
 * 作者：winston yan
 * 邮箱:yancm@jee-soft.cn
 * 日期:2014-4-16-下午3:19:29
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class UserAssignRuleParser {
	
	private final static class EL_NAME{
		public final static String USER_RULE="userRule";
		public final static String NAME="name";
		public final static String GROUP_NO="groupNo";
		public final static String DESCRIPTION="description";
		public final static String CONDITION="condition";
		public final static String CONDITION_MODE="conditionMode";
		public final static String CALCS="calcs";
		public final static String PARENT_FLOW_KEY="parentFlowKey";
	}
	
	
	/**
	 * 根据JSONObject 解析UserAssignRule 对象。
	 * @param jsonObj
	 * @return UserAssignRule
	 */
	public static UserAssignRule getUserAssignRule(JSONObject jsonObj){
		UserAssignRule rule=new UserAssignRule();
		
		String condition=JsonUtil.getString(jsonObj, "condition");
		
		rule.setCondition(condition);
		
		String conditionMode=JsonUtil.getString(jsonObj, "conditionMode");
		rule.setConditionMode(conditionMode);
		
		String description=JsonUtil.getString(jsonObj, "description");
		rule.setDescription(description);
		
		int groupNo=JsonUtil.getInt(jsonObj, "groupNo",1);
		
		rule.setGroupNo(groupNo);
		
		JSONArray calcAry=jsonObj.getJSONArray("calcs");
		
		List<UserCalcPluginContext> calcPluginContextList=new ArrayList<UserCalcPluginContext>();
		
		for(Object obj:calcAry){
			JSONObject calcObj=(JSONObject)obj;
			String pluginContext=JsonUtil.getString(calcObj, "pluginType") + BpmPluginContext.PLUGINCONTEXT;
			//获得一个新的实例。
			AbstractUserCalcPluginContext ctx=(AbstractUserCalcPluginContext) AppUtil.getBean(pluginContext);
			//调用解析。
			ctx.parse(calcObj.toString());
			
			calcPluginContextList.add(ctx);
		}
		
		rule.setCalcPluginContextList(calcPluginContextList);
		
		return rule;
	}
	
	
	/**
	 * 将Element代码解析成UserAssignRule列表。
	 * @param el
	 * @return  List&lt;UserAssignRule>
	 */
	public static List<UserAssignRule> parse(Element el) {
		List<UserAssignRule> ruleList = new ArrayList<UserAssignRule>();
		if(el==null || el.getChildNodes().getLength()==0){
			return ruleList;
		}
		NodeList nodeList = el.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			if (!(nodeList.item(i) instanceof Element)){
				continue;
			}			
			Element ruleNode = (Element) nodeList.item(i);
			String tagName = ruleNode.getTagName();
			//是userRule标签才处理
			if(StringUtil.isEmpty(tagName) || !EL_NAME.USER_RULE.equals(tagName)) continue;
			
			UserAssignRule userRule=getUserRule(ruleNode);

			//加到集合中
			ruleList.add(userRule);				
			
		}
		return ruleList;
	}
	
	/**
	 * 根据Element 节点获取UserAssignRule对象。
	 * @param ruleNode
	 * @return UserAssignRule
	 */
	private static UserAssignRule getUserRule(Element ruleNode){
		UserAssignRule userRule = new UserAssignRule();
		//处理name
		String name = ruleNode.getAttribute(EL_NAME.NAME);
		userRule.setName(name);
		//处理groupNo
		int groupNo = Integer.parseInt(ruleNode.getAttribute(EL_NAME.GROUP_NO));
		userRule.setGroupNo(groupNo);
		//处理description
		Element descriptionNode = XmlUtil.getChildNodeByName(ruleNode, EL_NAME.DESCRIPTION);
		if(descriptionNode!=null){
			userRule.setDescription(descriptionNode.getTextContent());
		}
		//处理condition
		Element conditionNode = XmlUtil.getChildNodeByName(ruleNode, EL_NAME.CONDITION);
		if(conditionNode!=null){
			userRule.setCondition(conditionNode.getTextContent());
		}
		//处理condition
		Element conditionModeNode = XmlUtil.getChildNodeByName(ruleNode, EL_NAME.CONDITION_MODE);
		if(conditionModeNode!=null){
			userRule.setConditionMode(conditionModeNode.getTextContent());
		}
		//处理parentFlowKey
		Element parentFlowKey = XmlUtil.getChildNodeByName(ruleNode, EL_NAME.PARENT_FLOW_KEY);
		if(parentFlowKey!=null){
			userRule.setParentFlowKey(parentFlowKey.getTextContent());
		}
		//处理calcs
		Element calcsNode = XmlUtil.getChildNodeByName(ruleNode, EL_NAME.CALCS);								
		List<UserCalcPluginContext> calcPluginContextList = PluginContextUtil
				.getUserCalcPluginContexts(calcsNode);
		userRule.setCalcPluginContextList(calcPluginContextList);
		
		return userRule;
	}
	
	
	/**
	 * 处理人员的JsonConfig。
	 * @param config	
	 * @param ruleList 
	 * void
	 */
	public static void handJsonConfig(JsonConfig config,List<UserAssignRule> ruleList){
		config.registerJsonValueProcessor(ExtractType.class, new EnumTypeProcessor() );
		config.registerJsonValueProcessor(LogicType.class, new EnumTypeProcessor() );
		config.registerJsonPropertyNameProcessor(UserAssignRule.class, new ProperNameProcessor());
		
		if(BeanUtils.isEmpty(ruleList)) return;
		
		for(UserAssignRule rule:ruleList){
			List<UserCalcPluginContext> list=rule.getCalcPluginContextList();
			if(BeanUtils.isEmpty(list)) continue;
			for(UserCalcPluginContext ctx:list){
				config.registerJsonBeanProcessor(ctx.getClass(),new UserCalcContextProcessor());
			}
		}
	} 
	
	
	/**
	 * 获取人员计算插件的xml序列化bulider。
	 * @param userCalcPluginContext
	 * @return  XMLBuilder
	 */
	public static XMLBuilder getCalcXmlBuilder(UserCalcPluginContext userCalcPluginContext){
		try{
			PluginParse calcPluginXmlBuilder = (PluginParse)userCalcPluginContext;
			String calcXml = calcPluginXmlBuilder.getPluginXml();
			StringReader sr = new StringReader(calcXml);
			InputSource is = new InputSource(sr);
			XMLBuilder calcXmlBuilder = XMLBuilder.parse(is);
			return calcXmlBuilder;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将List&lt;UserAssignRule> 序列换成XML。
	 * @param xmlBuilder
	 * @param userRuleList 
	 * void
	 */
	public static void handXmlBulider(XMLBuilder xmlBuilder, List<UserAssignRule> userRuleList){
	
		for(UserAssignRule userAssignRule:userRuleList){				
			if(userAssignRule.getCalcPluginContextList().size()==0) continue;

			xmlBuilder = xmlBuilder.e("userRule")
					.a("xmlns", "http://www.jee-soft.cn/bpm/plugins/userCalc/base")
					.a("groupNo",String.valueOf(userAssignRule.getGroupNo()))						
					.e("description").t(userAssignRule.getDescription()).up()
					.e("condition").t(userAssignRule.getCondition()).up()
					.e("conditionMode").t(userAssignRule.getConditionMode()).up()	
					.e("parentFlowkey").t(userAssignRule.getParentFlowKey()).up()
					.e("calcs");				
			for(UserCalcPluginContext userCalcPluginContext:userAssignRule.getCalcPluginContextList()){
				if(!(userCalcPluginContext instanceof PluginParse)) continue;
				
				XMLBuilder calcXmlBuilder = getCalcXmlBuilder(userCalcPluginContext);
				if(calcXmlBuilder==null) continue;

				xmlBuilder = xmlBuilder.importXMLBuilder(calcXmlBuilder);
			}
			xmlBuilder = xmlBuilder.up().up();
		}
	
	}
	
	

	

}
