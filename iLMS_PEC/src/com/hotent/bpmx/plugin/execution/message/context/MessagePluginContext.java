package com.hotent.bpmx.plugin.execution.message.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.w3c.dom.Element;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.constant.LogicType;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.UserAssignRule;
import com.hotent.bpmx.api.plugin.core.context.UserCalcPluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractBpmExecutionPluginContext;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleParser;
import com.hotent.bpmx.plugin.execution.message.def.HtmlSetting;
import com.hotent.bpmx.plugin.execution.message.def.MessagePluginDef;
import com.hotent.bpmx.plugin.execution.message.def.PlainTextSetting;
import com.hotent.bpmx.plugin.execution.message.entity.Message;
import com.hotent.bpmx.plugin.execution.message.entity.Message.Html;
import com.hotent.bpmx.plugin.execution.message.entity.Message.PlainText;
import com.hotent.bpmx.plugin.execution.message.plugin.MessagePlugin;
import com.hotent.bpmx.plugin.execution.script.context.ScriptNodePluginContext;
import com.hotent.bpmx.plugin.task.userassign.context.PluginContextPluginTypeProcessor;
import com.hotent.bpmx.plugin.usercalc.cusers.context.CusersPluginContext;
import com.hotent.bpmx.plugin.usercalc.cusers.def.CusersPluginDef;
import com.jamesmurty.utils.XMLBuilder;

/**
 * 消息插件。
 * <pre> 
 * 构建组：x5-bpmx-plugin
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-21-下午10:17:47
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class MessagePluginContext extends AbstractBpmExecutionPluginContext {

	public List<EventType> getEventTypes() {
		List<EventType> list=new ArrayList<EventType>();
		list.add(EventType.AUTO_TASK_EVENT);
		return list;
	}

	public Class<? extends RunTimePlugin> getPluginClass() {
		return MessagePlugin.class;
	}
	
	private static MessagePluginDef getMessageDef(){
		List<UserAssignRule> ruleList=new ArrayList<UserAssignRule>();
		MessagePluginDef def=new MessagePluginDef();
		HtmlSetting htmlSetting=new HtmlSetting();
		
		UserAssignRule rule=new UserAssignRule();
		rule.setCondition("aaa>0");
		rule.setGroupNo(1);
		
		CusersPluginContext ctx=new CusersPluginContext();
		CusersPluginDef cdef=new CusersPluginDef();
		cdef.setAccount("zhangyg");
		cdef.setExtract(ExtractType.EXACT_NOEXACT);
		cdef.setSource("spec");
		cdef.setUserName("zhangyg");
		cdef.setLogicCal(LogicType.OR);
		ctx.setBpmPluginDef(cdef);
		
		List<UserCalcPluginContext> calcPluginContextList=new ArrayList<UserCalcPluginContext>();
		
		calcPluginContextList.add(ctx);
		
		rule.setCalcPluginContextList(calcPluginContextList);
		
		ruleList.add(rule);
		htmlSetting.setRuleList(ruleList);
		
		
		
		PlainTextSetting pSetting=new PlainTextSetting();
		def.setExternalClass("com.hotent.Demo");
		def.setHtmlSetting(htmlSetting);
		def.setPlainTextSetting(pSetting);
		
		return def;
	}
	
//	public static void main(String[] args) {
		
		
//		JsonConfig config=new JsonConfig();
//		
//		MessagePluginDef def= getMessageDef();
//		
//		List<UserAssignRule> ruleList=new ArrayList<UserAssignRule>();
//		if(def.getHtmlSetting().getRuleList()!=null){
//			ruleList.addAll(def.getHtmlSetting().getRuleList());
//		}
//		
//		
//		UserAssignRuleParser.handJsonConfig(config, ruleList);
//		
//		JSON json= JSONSerializer.toJSON(def,config);
//		
//		System.out.println(json.toString());
		
//		MessagePluginContext ctx=new MessagePluginContext();
//		String xml=ctx.getPluginXml();
//		System.out.println(xml);
//	}


	/**
	 * 插件的XML格式。
	 *<pre>
	 *&lt;?xml version="1.0" encoding="UTF-8"?>
	*&lt;message xmlns="http://www.jee-soft.cn/bpm/plugins/execution/message" >
    *&lt;html msgType="">
    *    &lt;userRule xmlns="http://www.jee-soft.cn/bpm/plugins/userCalc/base" groupNo="1">
    *        &lt;calcs>&lt;/calcs>
    *    &lt;/userRule>
    *    &lt;subject>&lt;/subject>
    *    &lt;content>&lt;/content>
    *&lt;/html>
    *&lt;plainText msgType="">
    *    &lt;userRule xmlns="http://www.jee-soft.cn/bpm/plugins/userCalc/base" groupNo="1">
    *        &lt;calcs>&lt;/calcs>
    *    &lt;/userRule>
    *    &lt;content>&lt;/content>
    *&lt;/plainText>
	*&lt;/message>
	*</pre>
	 */
	@Override
	public String getPluginXml() {
		MessagePluginDef pluginDef=(MessagePluginDef) this.getBpmPluginDef();
//		MessagePluginDef pluginDef=getMessageDef();
		try {
			XMLBuilder xmlBuilder = XMLBuilder.create("message")
					.a("xmlns", "http://www.jee-soft.cn/bpm/plugins/execution/message");	
			
			if(StringUtil.isNotEmpty(pluginDef.getExternalClass()))
				xmlBuilder.a("externalClass", pluginDef.getExternalClass());
			
			HtmlSetting setting=pluginDef.getHtmlSetting();
			xmlBuilder=xmlBuilder.e("html").a("msgType", setting.getMsgType())
			.e("subject").d(setting.getSubject()).up()
			.e("content").d(setting.getContent()).up(); 
			
			UserAssignRuleParser.handXmlBulider(xmlBuilder, setting.getRuleList());
			
			xmlBuilder=xmlBuilder.up();
			
			PlainTextSetting textSetting=pluginDef.getPlainTextSetting();
			xmlBuilder=xmlBuilder.e("plainText").a("msgType", textSetting.getMsgType())
					.e("content").d(textSetting.getContent()).up();
			
			UserAssignRuleParser.handXmlBulider(xmlBuilder, textSetting.getRuleList());

			return xmlBuilder.asString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	
	}

	@Override
	public String getJson() {
		MessagePluginDef pluginDef=(MessagePluginDef)this.getBpmPluginDef();
		
		List<UserAssignRule> assignRules=new ArrayList<UserAssignRule>();
		if(pluginDef.getHtmlSetting()!=null){
			HtmlSetting setting=pluginDef.getHtmlSetting();
			assignRules.addAll(setting.getRuleList());
		}
		if(pluginDef.getPlainTextSetting()!=null){
			PlainTextSetting setting=pluginDef.getPlainTextSetting();
			assignRules.addAll(setting.getRuleList());
		}
	
		JsonConfig config=new JsonConfig();
		
		config.registerJsonBeanProcessor(this.getClass(),new PluginContextPluginTypeProcessor());
		
		UserAssignRuleParser.handJsonConfig(config, assignRules);
		
		JSON json= JSONSerializer.toJSON(pluginDef, config);
		
		return json.toString();
	}
	
	/**
	* {"externalClass":"com.hotent.Demo","htmlSetting":{"content":"","msgType":[],
	*"ruleList":[{"calcs":[{"account":"zhangyg","extract":"no","logicCal":"or","pluginName":"",
	*	"source":"spec","userName":"zhangyg","var":"","pluginType":"cusers","description":"zhangyg"}],
	*"condition":"aaa>0","conditionMode":"","description":"","groupNo":1,"name":""}],"subject":""},
	*"plainTextSetting":{"content":"","msgType":"","ruleList":[]},"pluginName":""}
	 */
	@Override
	protected BpmPluginDef parseJson(String pluginJson) {
		JSONObject jsonObject=JSONObject.fromObject(pluginJson);

		MessagePluginDef pluginDef=new MessagePluginDef();
		
		String externalClass = JsonUtil.getString(jsonObject, "externalClass");
		
		handHtmlSetting(pluginDef,jsonObject);
		
		handPlainTextSetting(pluginDef,jsonObject);
	
		pluginDef.setExternalClass(externalClass);
		return pluginDef;
	}
	
	/**
	 * 处理消息节点的PlainTextSetting部分。
	 * @param pluginDef
	 * @param jsonObject 
	 * void
	 */
	private void handPlainTextSetting(MessagePluginDef pluginDef,JSONObject jsonObject){
		JSONObject handPlainJsonObject = jsonObject.getJSONObject("plainTextSetting");
		PlainTextSetting plainTextSetting=new PlainTextSetting();
		handPublicSetting(handPlainJsonObject,plainTextSetting);
		pluginDef.setPlainTextSetting(plainTextSetting);
	}
	
	private void handHtmlSetting(MessagePluginDef pluginDef,JSONObject jsonObject){
		JSONObject htmlJsonObject = jsonObject.getJSONObject("htmlSetting");
		String subject = JsonUtil.getString(htmlJsonObject, "subject", "");
		HtmlSetting htmlSetting=new HtmlSetting();
		handPublicSetting(htmlJsonObject,htmlSetting);
		htmlSetting.setSubject(subject);
		pluginDef.setHtmlSetting(htmlSetting);
	}
	
	/**
	 * 处理公共部分的JSON。
	 * @param jsonObject
	 * @param plainTextSetting 
	 * void
	 */
	private void handPublicSetting(JSONObject jsonObject,PlainTextSetting plainTextSetting){
		String msgType=JsonUtil.getString(jsonObject, "msgType");
		if("".equals(msgType)) return; 
		String content=jsonObject.getString("content");
		
		JSONArray rulesAry=jsonObject.getJSONArray("ruleList");
		
		List<UserAssignRule> ruleList=getRulesByJsonArray(rulesAry);
		
		plainTextSetting.setContent(content);
		plainTextSetting.setMsgType(msgType);
		plainTextSetting.setRuleList(ruleList);
	}
	
	/**
	 * 根据JSONArray返回用户规则列表。
	 * @param jsonAry
	 * @return  List&lt;UserAssignRule>
	 */
	private List<UserAssignRule> getRulesByJsonArray(JSONArray jsonAry){
		List<UserAssignRule> rules=new ArrayList<UserAssignRule>();
		if(BeanUtils.isEmpty(jsonAry)) return rules;
		
		for(Object obj:jsonAry){
			UserAssignRule rule= UserAssignRuleParser.getUserAssignRule((JSONObject) obj);
			rules.add(rule);
		}
		return rules;
		
	}

	@Override
	protected BpmPluginDef parseElement(Element element) {
		String xml = XmlUtil.getXML(element);
		MessagePluginDef pluginDef=new MessagePluginDef();
		try {
			Message message = (Message)JAXBUtil.unmarshall(xml,com.hotent.bpmx.plugin.execution.message.entity.ObjectFactory.class);
			//外部数据获取类。
			String externalClass=message.getExternalClass();
			 
			if(StringUtil.isNotEmpty(externalClass)){
				pluginDef.setExternalClass(externalClass);
			}
			
			PlainText plainText= message.getPlainText();
			Html html= message.getHtml();
			
			if(plainText!=null){
				PlainTextSetting plain=new PlainTextSetting();
				plain.setMsgType(plainText.getMsgType());
				plain.setContent(plainText.getContent());
				
				Element el= XmlUtil.getChildNodeByName(element, "plainText");
				
				List<UserAssignRule> list=UserAssignRuleParser.parse(el);
				
				plain.setRuleList(list);
				
				pluginDef.setPlainTextSetting(plain);
			}
			
			if(html!=null){
				HtmlSetting htmlSetting=new HtmlSetting();
				htmlSetting.setSubject(html.getSubject());
				htmlSetting.setMsgType(html.getMsgType());
				htmlSetting.setContent(html.getContent());
				
				Element el= XmlUtil.getChildNodeByName(element, "html");
				
				
				List<UserAssignRule> list=UserAssignRuleParser.parse(el);
				
				htmlSetting.setRuleList(list);
				
				pluginDef.setHtmlSetting(htmlSetting);
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pluginDef;
	}

	@Override
	public String getTitle() {
		
		return "消息节点";
	}
	
	

}
