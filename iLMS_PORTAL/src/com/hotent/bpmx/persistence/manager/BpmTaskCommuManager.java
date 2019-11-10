package com.hotent.bpmx.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmTaskCommu;

public interface BpmTaskCommuManager extends Manager<String, BpmTaskCommu>{
	
	BpmTaskCommu getByTaskId(String taskId);
	
}
