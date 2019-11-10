package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.ActExecution;


public interface ActExecutionDao extends Dao<String, ActExecution> {
	
	/**
	 * 根据parentId获取流程实例列表。
	 * @param parentId
	 * @return 
	 * List&lt;String>
	 */
	List<String> getByParentsId(String parentId);
	
	/**
	 * 根据superID获取流程实例列表。
	 * @param superID
	 * @return 
	 * List&lt;String>
	 */
	List<String> getBySupperId(String superID);
	
	/**
	 * 根据流程实例列表删除关联任务。
	 * @param instList 
	 * void
	 */
	void delTaskByByInstList(List<String> instList);
	
	/**
	 * 根据实例列表删除关联任务。
	 * @param instList 
	 * void
	 */
	void delCandidateByInstList(List<String> instList);

	/**
	 * 根据实例列表删除事件订阅。
	 * @param instList 
	 * void
	 */
	void delEventSubByInstList(List<String> instList);

	/**
	 * 根据流程实例列表删除变量。
	 * @param instList 
	 * void
	 */
	void delVarsByInstList(List<String> instList);
	
	/**
	 * 根据流程实例列表删除历史变量。
	 * @param instList 
	 * void
	 */
	void delHiVarByInstList(List<String> instList);
	
	/**
	 * 根据流程实例列表删除历史任务。
	 * @param instList 
	 * void
	 */
	void delHiTaskByInstList(List<String> instList);
	
	
	/**
	 * 根据流程实例列表删除历史实例。
	 * @param instList 
	 * void
	 */
	void delHiProcinstByInstList(List<String> instList);


	/**
	 * 删除历史任务候选人。
	 * @param instList 
	 * void
	 */
	void delHiCandidateByInstList(List<String> instList);


	/**
	 * 根据实例删除历史实例 。
	 * @param instList 
	 * void
	 */
	void delHiActInstByInstList(List<String> instList);
	
	/**
	 * 根据流程实例删除Excution。
	 * @param instList 
	 * void
	 */
	void delExectionByInstList(List<String> instList);


}
