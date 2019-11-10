package com.hotent.bpmx.plugin.task.tasknotify.def;

import java.util.HashMap;
import java.util.Map;

import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.plugin.core.plugindef.AbstractBpmTaskPluginDef;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyVo;

public class TaskNotifyPluginDef extends AbstractBpmTaskPluginDef {
	
	/**
	 * 信息配置集合
	 */
	Map<EventType,NotifyVo> notifyVos = new HashMap<EventType,NotifyVo>();	
	
	public void addNotifyVo(EventType eventType,NotifyVo notifyVo){
		notifyVo.setEventType(eventType);
		notifyVos.put(eventType, notifyVo);
	}

	public Map<EventType, NotifyVo> getNotifyVos() {
		return notifyVos;
	}
}
