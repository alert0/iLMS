package com.hotent.bpmx.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmTaskTrans;

public interface BpmTaskTransManager extends Manager<String, BpmTaskTrans>{
	
	
	/**
	 * 根据任务获取会签情况的配置。
	 * @param taskId
	 * @return BpmTaskTrans
	 */
	BpmTaskTrans getByTaskId(String taskId);
	
	
	
	
}
