package com.hotent.bpmx.plugin.task.tasknotify.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.processors.PropertyNameProcessor;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.UserAssignRule;
import com.hotent.bpmx.plugin.core.util.EnumTypeProcessor;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleParser;
import com.hotent.bpmx.plugin.task.tasknotify.def.TaskNotifyPluginDef;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyItem;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyVo;
import com.jamesmurty.utils.XMLBuilder;
/**
 * 
 * <pre> 
 * 描述：通知插件工具类
 * 构建组：x5-bpmx-plugin
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-5-6-下午5:38:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class NotifyUtil {
	/**
	 * 
	 * 根据notify节点的父节点（如onCreate、onComplete、onEnd等节点）来解析该父节点包含的所有notify节点，
	 * 结果解析为NotifyItem的集合。
	 * @param parentOfNotifyElement
	 * @return 
	 * List<NotifyItem>
	 */
	public static List<NotifyItem> parseNotifyItems(Element parentOfNotifyElement){
		List<NotifyItem> notifyItems = new ArrayList<NotifyItem>();
		NodeList nodeList= parentOfNotifyElement.getChildNodes();
		if(nodeList==null || nodeList.getLength()==0) return notifyItems;
		for(int i = 0; i<nodeList.getLength();i++){
			Object obj = nodeList.item(i);
			if(obj instanceof Element && ((Element) obj).getTagName().equals("notify")){
				Element notifyEl = (Element)obj;				
				
				List<UserAssignRule> userAssignRules = UserAssignRuleParser.parse(notifyEl);
				String msgTypes = notifyEl.getAttribute("msgTypes");				
			
				NotifyItem notifyItem = new NotifyItem();
				notifyItem.setUserAssignRules(userAssignRules);
				notifyItem.setMsgTypes(msgTypes);
					
				
				notifyItems.add(notifyItem);
			}			
		}		
		return notifyItems;
	}
	
	/**
	 * 根据JSON获取notifyvo。
	 * @param jsonAry
	 * @return 
	 * NotifyVo
	 */
	public static NotifyVo getNotifyVo(JSONArray jsonAry){
		NotifyVo vo=new NotifyVo();
		if(jsonAry.size()==0) return vo;
		List<NotifyItem> notifys=new ArrayList<NotifyItem>();
		
		for(Object obj:jsonAry){
			JSONObject notifyItemJson=(JSONObject)obj;
			NotifyItem notifyItem= getNotifyItemByJson(notifyItemJson);
			notifys.add(notifyItem);
		}
		vo.setNotifyItemList(notifys);
		return vo;
		
	}
	
	/**
	 * 根据json 获取 NotifyItem。
	 * @param obj
	 * @return 
	 * NotifyItem
	 */
	public static NotifyItem getNotifyItemByJson(JSONObject obj){
		NotifyItem notifyItem=new NotifyItem();
		String messageTypes=obj.getString("msgTypes");
		JSONArray rules=obj.getJSONArray("userAssignRules");
		notifyItem.setMsgTypes(messageTypes);
		
		List<UserAssignRule> ruleList=new ArrayList<UserAssignRule>();
		for(Object ruleObj:rules){
			JSONObject jsonObj=(JSONObject)ruleObj;
			UserAssignRule rule=UserAssignRuleParser.getUserAssignRule(jsonObj);
			ruleList.add(rule);
		}
		notifyItem.setUserAssignRules(ruleList);
		
		return notifyItem;
	}
	
	/**
	 * 构建XMLnotify 部分。
	 * @param completeVo
	 * @param xmlBuilder 
	 * void
	 */
	public static void handXmlBuilder(NotifyVo completeVo,XMLBuilder xmlBuilder){
		List<NotifyItem> notifyItems= completeVo.getNotifyItemList();
		for(NotifyItem item:notifyItems){
			xmlBuilder=xmlBuilder.e("notify").a("xmlns", "http://www.jee-soft.cn/bpm/plugins/task/baseNotify")
			.a("msgTypes", item.getMessageTypes());
			
			List<UserAssignRule> rules= item.getUserAssignRules();
			UserAssignRuleParser.handXmlBulider(xmlBuilder, rules);
			xmlBuilder = xmlBuilder.up();
		}
	}
	
	/**
	 * 获取通知的所有用户规则。
	 * @param listVo
	 * @return 
	 * List&lt;UserAssignRule>
	 */
	public static List<UserAssignRule> getRules(Collection<NotifyVo> listVo){
		List<UserAssignRule> list=new ArrayList<UserAssignRule>();
		for(NotifyVo vo:listVo){
			List<NotifyItem> notifyItems= vo.getNotifyItemList();
			for(NotifyItem item:notifyItems){
				list.addAll(item.getUserAssignRules());
			}
		}
		return list;
	}
	
	
	public static void  getJsonConfig(JsonConfig config, Collection<NotifyVo> assignRules){
 		List<UserAssignRule> ruleList= NotifyUtil.getRules(assignRules);
		
		
		config.registerJsonValueProcessor(EventType.class, new EnumTypeProcessor());
		
		config.registerPropertyExclusions(NotifyItem.class, new String[]{"msgTypes"});
		config.registerPropertyExclusions(NotifyVo.class, new String[]{"eventType"});
		
		PropertyNameProcessor processor=new PropertyNameProcessor() {
			@Override
			public String processPropertyName(Class cls, String property) {
				if(NotifyItem.class==cls){
					if("messageTypes".equals(property)){
						return "msgTypes";
					}
				}
				else if(NotifyVo.class==cls){ 
					if("notifyItemList".equals(property)){
						return "notify";
					}
				}
				return property;
			}
		};
		
		config.registerJsonPropertyNameProcessor(NotifyVo.class,processor);
		config.registerJsonPropertyNameProcessor(NotifyItem.class,processor);
		
		config.registerJsonBeanProcessor(TaskNotifyPluginDef.class, new JsonBeanProcessor() {
			@Override
			public JSONObject processBean(Object obj, JsonConfig config) {
				TaskNotifyPluginDef def=(TaskNotifyPluginDef)obj;
				Map<EventType, NotifyVo> map= def.getNotifyVos();
				Map<EventType, List<NotifyItem>> rtnMap=convertNotifys(map);
				return JSONObject.fromObject(rtnMap,config);
			}
		});
		
		
		config.setAllowNonStringKeys(true);
		UserAssignRuleParser.handJsonConfig(config, ruleList);
		
	}
	
	private static Map<EventType, List<NotifyItem>> convertNotifys(Map<EventType, NotifyVo> map){
		Map<EventType, List<NotifyItem>> rtnMaps=new HashMap<EventType, List<NotifyItem>>();
		Set<EventType> set=map.keySet();
		for(Iterator<EventType> it=set.iterator();it.hasNext();){
			EventType eventType=it.next();
			NotifyVo notifyVo=map.get(eventType);
			rtnMaps.put(eventType, notifyVo.getNotifyItemList());
		}
		return rtnMaps;
	}
	
}
