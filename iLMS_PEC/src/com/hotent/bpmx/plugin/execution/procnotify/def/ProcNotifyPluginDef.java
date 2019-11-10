package com.hotent.bpmx.plugin.execution.procnotify.def;

import java.util.HashMap;
import java.util.Map;

import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.plugin.core.plugindef.AbstractBpmExecutionPluginDef;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyVo;

public class ProcNotifyPluginDef extends AbstractBpmExecutionPluginDef{
	/**
	 * 信息配置集合
	 */
	Map<EventType,NotifyVo> notifyVoMap = new HashMap<EventType,NotifyVo>();	
	
	public void addNotifyVo(EventType eventType,NotifyVo notifyVo){
		notifyVo.setEventType(eventType);
		notifyVoMap.put(eventType, notifyVo);
	}
	
	

	public Map<EventType, NotifyVo> getNotifyVoMap() {
		return notifyVoMap;
	}
}
