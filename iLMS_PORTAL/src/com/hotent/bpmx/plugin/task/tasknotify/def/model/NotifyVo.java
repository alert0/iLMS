package com.hotent.bpmx.plugin.task.tasknotify.def.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.bpmx.api.constant.EventType;

public class NotifyVo {
	private EventType eventType;

	private List<NotifyItem> notifyItemList = new ArrayList<NotifyItem>(); 


	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public List<NotifyItem> getNotifyItemList() {
		return notifyItemList;
	}
	
	public void setNotifyItemList(List<NotifyItem> notifyItemList) {
		this.notifyItemList = notifyItemList;
	}
	public String toString(){
    	return new ToStringBuilder(this).append("eventType")
    			.toString();
    }
    
}
