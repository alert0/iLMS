package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.persistence.model.BpmExeStackExecutor;

public interface BpmExeStackExecutorManager extends Manager<String, BpmExeStackExecutor>{
	
	/**
	 * 根据任务ID获取执行人。
	 * @param taskId
	 * @return 
	 * BpmExeStackExecutor
	 */
	BpmExeStackExecutor getByTaskId(String taskId);
	
	/**
	 * 根据StackId 获取Executor。
	 * @param taskId
	 * @return 
	 * BpmExeStackExecutor
	 */
	List<BpmExeStackExecutor> getByStackId(String exeStackId);

	List<BpmIdentity> getBpmIdentitysByStackId(String exeStackId);
	
	/***
	 * 通过 stackId 删除执行人
	 * @param stackId
	 */
	void deleteByStackId(String stackId);
	/***
	 * 通过path 删除执行人
	 * @param stackPath
	 */
	void deleteByStackPath(String stackPath);
	
}
