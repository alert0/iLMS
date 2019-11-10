package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmExeStackExecutor;


public interface BpmExeStackExecutorDao extends Dao<String, BpmExeStackExecutor> {
	
	/**
	 * 根据任务Id获取堆栈执行人。
	 * @param taskId	任务ID
	 * @return 
	 * BpmExeStackExecutor
	 */
	public BpmExeStackExecutor getByTaskId(String taskId);
	
	
	/**
	 * 根据堆栈ID获取堆栈执行人。
	 * @param stackId	堆栈ID
	 * @return 
	 * List&lt;BpmExeStackExecutor>
	 */
	public  List<BpmExeStackExecutor> getByStackId(String stackId);

	/***
	 * 根据堆栈ID删除执行人
	 * @param stackId
	 */
	public void deleteByStackId(String stackId);

	/***
	 * 根据堆栈path 删除所有执行人
	 * @param stackPath
	 */
	public void deleteByStackPath(String stackPath);


}
