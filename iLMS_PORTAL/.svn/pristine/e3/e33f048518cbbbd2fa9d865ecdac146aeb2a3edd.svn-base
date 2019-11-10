package com.hotent.bpmx.plugin.task.reminders.context;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.w3c.dom.Element;

import com.alibaba.fastjson.JSON;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractBpmExecutionPluginContext;
import com.hotent.bpmx.plugin.core.context.AbstractBpmTaskPluginContext;
import com.hotent.bpmx.plugin.task.reminders.def.RemindersPluginDef;
import com.hotent.bpmx.plugin.task.reminders.entity.ObjectFactory;
import com.hotent.bpmx.plugin.task.reminders.entity.Reminders;
import com.hotent.bpmx.plugin.task.reminders.plugin.RemindersPlugin;

/**
 * 脚本节点。
 * <pre> 
 * 构建组：x5-bpmx-plugin
 * 作者：miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-7-26
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class RemindersPluginContext extends AbstractBpmTaskPluginContext {

	public List<EventType> getEventTypes() {
		List<EventType> list=new ArrayList<EventType>();
		list.add(EventType.TASK_CREATE_EVENT);
		list.add(EventType.TASK_COMPLETE_EVENT);
		return list;
	}

	public Class<? extends RunTimePlugin> getPluginClass() {
		return RemindersPlugin.class;
	}

	
	@Override
	public String getPluginXml() {
		RemindersPluginDef pluginDef=(RemindersPluginDef) this.getBpmPluginDef();
		String xml = "";
		try {
			if(BeanUtils.isEmpty(pluginDef.getReminderList()))return xml;
			xml = JAXBUtil.marshall(RemindersPluginDef.getReminderExt(pluginDef), ObjectFactory.class);
			xml = xml.replace("encoding=\"utf-8\"", "encoding=\"UTF-8\"");
			xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n", "");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}

	@Override
	public String getJson() {
		RemindersPluginDef pluginDef=(RemindersPluginDef) this.getBpmPluginDef();
		return JSON.toJSONString(pluginDef);
	}
	
	

	@Override
	protected BpmPluginDef parseJson(String pluginJson) {
		RemindersPluginDef def = JSON.parseObject(pluginJson, RemindersPluginDef.class);
		return def;
	}

	@Override
	protected BpmPluginDef parseElement(Element element) {
		String xml = XmlUtil.getXML(element);
		RemindersPluginDef def ;
		 try {
			Reminders reminders = (Reminders) JAXBUtil.unmarshall(xml,ObjectFactory.class);
			def =RemindersPluginDef.getReminders(reminders);
			return def;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@Override
	public String getTitle() {
		return "任务催办";
	}

}
