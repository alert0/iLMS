package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmTaskRead;


public interface BpmTaskReadDao extends Dao<String, BpmTaskRead> {
	
	/**
	  * 根据流程实例列表删除任务。
	  * @param instList 
	  * void
	  */
	 void delByInstList(List<String> instList);
}
