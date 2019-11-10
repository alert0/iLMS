package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.ActTask;


public interface ActTaskDao extends Dao<String, ActTask> {
	
	/**
	 * 根据流程实例ID列表删除相关的任务。
	 * @param list 
	 * void
	 */
	void delByInstList(List<String> list) ;
	
	/**
	 * 根据流程实例ID列表删除相关候选人。
	 * @param list 
	 * void
	 */
	void delCandidateByInstList(List<String> list);
	
	
	/**
	 * 删除指定的流程变量。
	 * @param list 
	 * void
	 */
	void delSpecVarsByInstList(List<String> list);
	
	/**
	 * 根据流程实例ID获取流程任务列表。
	 * @param instId
	 * @return  List&lt;ActTask>
	 */
	List<ActTask> getByInstId(String instId);
}
