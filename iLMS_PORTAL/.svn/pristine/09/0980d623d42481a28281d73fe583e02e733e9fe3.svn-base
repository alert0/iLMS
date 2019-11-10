package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.BpmTaskTurn;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.bpmx.persistence.model.TaskTurnAssign;
import com.hotent.org.api.model.IUser;

public interface BpmTaskTurnManager extends Manager<String, DefaultBpmTaskTurn>{
	
	/**
	* 根据流程实例列表删除任务。
	* @param instList 
	* void
	*/
	void delByInstList(List<String> instList);
	
	
	/**
	 * 完成任务时执行。
	 * @param taskId
	 * @param user 
	 * void
	 */
	void updComplete(String taskId,IUser user);

	/**
	 * 根据taskId 获取该转办任务
	 * @param taskId
	 * @param status
	 * @return
	 */
	BpmTaskTurn getByTaskId(String taskId);

	/***
	 * 通过用户ID
	 * @param userId
	 * @param queryFilter
	 * @return
	 */
	List<DefaultBpmTaskTurn> getMyDelegate(String userId, QueryFilter queryFilter);
	/***
	 * 通过Id获取转办人员
	 * @param taskTurnId
	 * @return
	 */
	List<TaskTurnAssign> getTurnAssignByTaskTurnId(String taskTurnId);

	/**
	 * 根据taskId 和被授权人ID 获取该转办任务
	 * @param taskId
	 * @param assigneeId
	 * @return
	 */
	List<DefaultBpmTaskTurn> getByTaskIdAndAssigneeId(String taskId,String assigneeId);
	
	/***
	 * 创建一个转办
	 * @param bpmTask
	 * @param owner 当前人
	 * @param agent 代理人
	 * @param typeAgent 转办类型
	 * @param option  意见
	 */
	void add(DefaultBpmTask bpmTask, IUser owner,IUser agent, String option, String typeAgent);

	/**添加一份代办人*/
	void addTurnAssign(String turnId, IUser user, String opinion);
	
	
}
