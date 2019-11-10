package com.hotent.bpmx.plugin.task.tasknotify.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.processors.PropertyNameProcessor;

import org.w3c.dom.Element;

import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.constant.LogicType;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.UserAssignRule;
import com.hotent.bpmx.api.plugin.core.context.PluginParse;
import com.hotent.bpmx.api.plugin.core.context.UserCalcPluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractBpmTaskPluginContext;
import com.hotent.bpmx.plugin.core.util.EnumTypeProcessor;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleParser;
import com.hotent.bpmx.plugin.task.tasknotify.def.TaskNotifyPluginDef;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyItem;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyVo;
import com.hotent.bpmx.plugin.task.tasknotify.entity.OnComplete;
import com.hotent.bpmx.plugin.task.tasknotify.entity.OnCreate;
import com.hotent.bpmx.plugin.task.tasknotify.entity.TaskNotify;
import com.hotent.bpmx.plugin.task.tasknotify.plugin.TaskNotifyPlugin;
import com.hotent.bpmx.plugin.task.tasknotify.util.NotifyUtil;
import com.hotent.bpmx.plugin.usercalc.cusers.context.CusersPluginContext;
import com.hotent.bpmx.plugin.usercalc.cusers.def.CusersPluginDef;
import com.jamesmurty.utils.XMLBuilder;

public class TaskNotifyPluginContext extends AbstractBpmTaskPluginContext {
	
	
	
	public Class<? extends RunTimePlugin> getPluginClass() {
		return TaskNotifyPlugin.class;
	}		
	

	public List<EventType> getEventTypes() {
		List<EventType> eventTypes = new ArrayList<EventType>();
		eventTypes.add(EventType.TASK_POST_CREATE_EVENT);
		eventTypes.add(EventType.TASK_COMPLETE_EVENT);
		return eventTypes;
	}

	private NotifyVo convert(OnCreate onCreate,Element pluginEl){
		NotifyVo notifyVo = new NotifyVo();
		notifyVo.setEventType(EventType.TASK_POST_CREATE_EVENT);					
		Element onCreateEl = XmlUtil.getChildNodeByName(pluginEl, "onCreate");
		List<NotifyItem> notifyItems = NotifyUtil.parseNotifyItems(onCreateEl);
		notifyVo.setNotifyItemList(notifyItems);
		
		return notifyVo;
	}
	
	private NotifyVo convert(OnComplete onComplete,Element pluginEl){
		NotifyVo notifyVo = new NotifyVo();
		notifyVo.setEventType(EventType.TASK_COMPLETE_EVENT);
		Element onCompleteEl = XmlUtil.getChildNodeByName(pluginEl, "onComplete");
		List<NotifyItem> notifyItems = NotifyUtil.parseNotifyItems(onCompleteEl);
		notifyVo.setNotifyItemList(notifyItems);
		return notifyVo;
	}

	/**
	 * 任务通知。
	 * <pre>
	 *&lt;taskNotify xmlns="http://www.jee-soft.cn/bpm/plugins/task/taskNotify">
     *&lt;onCreate>
     *   &lt;notify xmlns="http://www.jee-soft.cn/bpm/plugins/task/baseNotify" msgTypes=""  >
     *       &lt;userRule xmlns="http://www.jee-soft.cn/bpm/plugins/userCalc/base" groupNo="">
     *           &lt;calcs>&lt;/calcs>
     *       &lt;/userRule>            
     *   &lt;/notify>
     *&lt;/onCreate>
     *&lt;onComplete>
     *   &lt;notify xmlns="http://www.jee-soft.cn/bpm/plugins/task/baseNotify" msgTypes=""></notify>
     *&lt;/onComplete>
	 *&lt;/taskNotify>
	 *</pre>
	 */
	@Override
	public String getPluginXml() {
		
		TaskNotifyPluginDef def=(TaskNotifyPluginDef) this.getBpmPluginDef();
		try {
			XMLBuilder xmlBuilder = XMLBuilder.create("taskNotify")
					.a("xmlns", "http://www.jee-soft.cn/bpm/plugins/task/taskNotify");
			
			xmlBuilder= xmlBuilder.e("onCreate");
			
			NotifyVo createVo=def.getNotifyVos().get(EventType.TASK_POST_CREATE_EVENT);
			if(createVo!=null){
				NotifyUtil.handXmlBuilder(createVo,xmlBuilder);
			}
			
			xmlBuilder=xmlBuilder.up();
			xmlBuilder=xmlBuilder.e("onComplete");
			
			NotifyVo completeVo=def.getNotifyVos().get(EventType.TASK_COMPLETE_EVENT);
			if(completeVo!=null){
				NotifyUtil.handXmlBuilder(completeVo,xmlBuilder);
				
			}
			return xmlBuilder.asString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	public static void main(String[] args) {
		TaskNotifyPluginContext ctx=new TaskNotifyPluginContext();
		TaskNotifyPluginDef def=new TaskNotifyPluginDef();
		ctx.setBpmPluginDef(def);
		
		Map<EventType, NotifyVo> map= def.getNotifyVos();
		
		NotifyVo createVo=new NotifyVo();
		NotifyVo completeVo=new NotifyVo();
		
		createVo.setEventType(EventType.TASK_POST_CREATE_EVENT);
		NotifyItem notifyItem=new NotifyItem();
		List<NotifyItem> itemList=new ArrayList<NotifyItem>();
		itemList.add(notifyItem);
		
		List<UserAssignRule> assignRules=new ArrayList<UserAssignRule>();
		
		UserAssignRule rule=new UserAssignRule();
		rule.setCondition("aaa>0");
		rule.setGroupNo(1);
		
		CusersPluginContext cuserctx=new CusersPluginContext();
		CusersPluginDef cdef=new CusersPluginDef();
		cdef.setAccount("zhangyg");
		cdef.setExtract(ExtractType.EXACT_NOEXACT);
		cdef.setSource("spec");
		cdef.setUserName("zhangyg");
		cdef.setLogicCal(LogicType.OR);
		cuserctx.setBpmPluginDef(cdef);
		
		rule.getCalcPluginContextList().add(cuserctx);
		
		assignRules.add(rule);
	//	assignRules.add(rule);
		
		
		notifyItem.setMsgTypes("sms,mail");
		notifyItem.setUserAssignRules(assignRules);
		
		createVo.setNotifyItemList(itemList);
		
		completeVo.setEventType(EventType.TASK_COMPLETE_EVENT);
		//completeVo.setNotifyItemList(itemList);
	
		map.put(EventType.TASK_POST_CREATE_EVENT, createVo);
		map.put(EventType.TASK_COMPLETE_EVENT, completeVo);
		
		//NotifyVo notifyVo=map.get(EventType.TASK_POST_CREATE_EVENT);
		
		String xml=ctx.getPluginXml();
		
		//System.err.println(xml);
		System.err.println(ctx.getJson());
	}

	@Override
	public String getJson() {
		TaskNotifyPluginDef def=(TaskNotifyPluginDef) this.getBpmPluginDef();
		
		Collection<NotifyVo> assignRules=def.getNotifyVos().values();
		
		JsonConfig config=new JsonConfig();
		
		NotifyUtil. getJsonConfig(config,assignRules);
		
		JSON json= JSONSerializer.toJSON(def, config);
		
		return json.toString();
	}
	
	

	/**
	 * 传入的JSON格式。
	 * <pre>
	 * {"postTaskCreate":
	 *	[{"messageTypes":"sms,mail","rules":
	 *		[
	 *			{"calcs":
	 *				[{"account":"zhangyg","extract":"no","logicCal":"or","pluginName":"","source":"spec","userName":"zhangyg","var":"","pluginType":"cusers","description":"zhangyg"}]
	 *				,"condition":"aaa>0","conditionMode":"","description":"","groupNo":1,"name":""}
	 *		]
	 *	}]
	 *,
	 *"taskComplete":[]
	 *}
	 *</pre>
	 */
	@Override
	protected BpmPluginDef parseJson(String pluginJson) {
		TaskNotifyPluginDef notifyPluginDef = new TaskNotifyPluginDef();
		
		JSONObject jsonObject=new JSONObject();
		JSONArray createJson=jsonObject.getJSONArray(EventType.TASK_POST_CREATE_EVENT.getKey());
		JSONArray completeJson=jsonObject.getJSONArray(EventType.TASK_COMPLETE_EVENT.getKey());
		
		NotifyVo createVo= NotifyUtil . getNotifyVo(createJson);
		NotifyVo completeVo= NotifyUtil . getNotifyVo(completeJson);
		
		notifyPluginDef.addNotifyVo(EventType.TASK_POST_CREATE_EVENT, createVo);
		notifyPluginDef.addNotifyVo(EventType.TASK_COMPLETE_EVENT, completeVo);
		
		return notifyPluginDef;
	}
	
	

	@Override
	protected BpmPluginDef parseElement(Element element) {
		String xml = XmlUtil.getXML(element);
		TaskNotifyPluginDef notifyPluginDef = new TaskNotifyPluginDef();
		try {
			TaskNotify taskNotify = (TaskNotify)JAXBUtil.unmarshall(xml,com.hotent.bpmx.plugin.task.tasknotify.entity.ObjectFactory.class);
			List<NotifyVo> notifyVoList = new ArrayList<NotifyVo>();
			OnCreate onCreate=taskNotify.getOnCreate();
			if(onCreate!=null){
				NotifyVo notityVoOnCreate = convert(onCreate,element);									
				notifyVoList.add(notityVoOnCreate);
				notifyPluginDef.getNotifyVos().put(EventType.TASK_POST_CREATE_EVENT, notityVoOnCreate);
			}			
			OnComplete onComplete = taskNotify.getOnComplete();
			if(onComplete!=null){
				NotifyVo notifyVoOnComplete = convert(onComplete,element);
				notifyVoList.add(notifyVoOnComplete);		
				notifyPluginDef.getNotifyVos().put(EventType.TASK_COMPLETE_EVENT, notifyVoOnComplete);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return notifyPluginDef;
	}


	@Override
	public String getTitle() {
		return "任务通知操送";
	}
}
