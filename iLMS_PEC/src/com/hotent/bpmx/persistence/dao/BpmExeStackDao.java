package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmExeStack;


public interface BpmExeStackDao extends Dao<String, BpmExeStack> {
	
	
	
	
	/**
	 * 根据流程实例Id,节点ID和token获取堆栈对象。 
	 * @param instId	流程ID
	 * @param nodeId	节点ID
	 * @param token		令牌
	 * @return  BpmExeStack
	 */
	List<BpmExeStack> getByInstNodeToken(String instId,String nodeId,String token);
	
	
	/**
	 * 根据流程实例ID获取发起堆栈数据。
	 * @param instId
	 * @return BpmExeStack
	 */
	BpmExeStack getInitStack(String instId);
	
	
	/**
	 * 根据路径删除堆栈数据。
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removeByPath(String instId,String path);
	
	/**
	 * 根据路径删除任务候选人
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removeBpmTaskCandidateByPath(String instId,String path);
	
	/**
	 * 根据路径删除任务的执行更改
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removeBpmTaskTurnByPath(String instId,String path);
	
	/**
	 * 根据路径删除任务
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removeBpmTaskByPath(String instId,String path);

	
	/**
	 * 根据路径删除堆栈的关系数据
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removeBpmExeStackRelation(String instId,String path);
	
	/**
	 * 根据路径删除Activiti的执行运行时
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removeActRuExeCutionByPath(String instId,String path,String notIncludeExecuteIds);
	
	/**
	 * 根据路径删除Activiti的执行运行时的任务
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removeActRuTaskByPath(String instId,String path,String notIncludeExecuteIds);
	
	/**
	 * 根据路径删除Activiti的执行运行时的变量
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removeActRuVariableByPath(String instId,String path,String notIncludeExecuteIds);
	
 
	/**
	 * 按流程图执行多实例退回时调整Bpm任务表
	 * @param rejectAfterExecutionId
	 */
	void multipleInstancesRejectAdjustOnBpmTask(String rejectAfterExecutionId);
	
	/**
	 * 按流程图执行多实例退回时调整Act任务表
	 * @param rejectAfterExecutionId
	 */
	void multipleInstancesRejectAdjustOnActTask(String rejectAfterExecutionId);
	
	/**
	 *  按流程图执行多实例退回时调整Act执行表
	 * @param actProcInstanceId
	 */
	void multipleInstancesRejectAdjustOnActExecution(String actProcInstanceId);
	
	/**
	 * 根据路径删除活动状态
	 * @param instId	流程实例ID
	 * @param path 		堆栈路径
	 * void
	 */
	void removebpmProStatusPath(String instId,String path);
	
	
	List<BpmExeStack> getByIds(String[] ids);
	
	
	/**
	 * 删除堆栈，除了初始的第一个不删除，即parentid为0的不删除
	 * @param instId
	 * @param path
	 */
	void removeExeStackExceptParentId(String instId,String parentId);
	
}
