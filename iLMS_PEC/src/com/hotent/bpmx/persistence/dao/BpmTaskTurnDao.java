package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.org.api.model.IUser;


public interface BpmTaskTurnDao extends Dao<String, DefaultBpmTaskTurn> {
	
	/**
	 * 根据任务ID和状态获取转办的数据。
	 * @param taskId	任务ID
	 * @param status
	 * @return DefaultBpmTaskTurn
	 */
	DefaultBpmTaskTurn getByTaskId(String taskId);
	
	/**
	 * 根据taskId 和被授权人ID 获取该转办任务
	 * @param taskId
	 * @param assigneeId
	 * @return
	 */
	List<DefaultBpmTaskTurn> getByTaskIdAndAssigneeId(String taskId,String assigneeId);
	
	/**
	 * 完成任务时执行。
	 * @param taskId
	 * @param user 
	 * void
	 */
	void updComplete(String taskId,IUser user);
	
	
	/**
	* 根据流程实例列表删除任务。
	* @param instList 
	* void
	*/
	void delByInstList(List<String> instList);

	/**通过用户查询自己转发出去的流程**/
	List<DefaultBpmTaskTurn> getMyDelegate(String userId,QueryFilter filter);
	
}
