package com.hotent.bpmx.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.ProcessInstCmd;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.org.api.model.IGroup;


public interface BpmTaskDao  extends Dao <String, DefaultBpmTask>{
	
	/**
	 * 根据bpmn的任务ID获取任务。
	 * @param relateTaskId
	 * @return 
	 * BpmTask
	 */
	DefaultBpmTask getByRelateTaskId(String relateTaskId);
	
	/**
	 * 删除关联任务ID。 
	 * @param taskId 
	 * void
	 */
	void removeByTaskId(String taskId);
	
	/**
	 * 获取某用户的所有待办列表
	 * @param userId
	 * @return 
	 * List<DefaultBpmTask>
	 */
	 List<DefaultBpmTask> getByUserId(String userId,Map<String,String> groupMap);
	
	/**
	 * 获取某用户的所有待办列表
	 * @param userId
	 * @return 
	 * List&lt;DefaultBpmTask>
	 */
	 List<DefaultBpmTask> getByUserId(String userId,Map<String,String> groupMap,Page page);
	 
	 /**
	  * 查询我的待办，并且按条件进行组合查询
	  * @param userId
	  * @param groupMap
	  * @param queryFilter
	  * @return 
	  * List<DefaultBpmTask>
	  */
	 List<DefaultBpmTask> getByUserId(String userId,Map<String,String> groupMap,QueryFilter queryFilter);
	
	/**
	 * 根据流程实例ID获取任务列表。
	 * @param instId
	 * @return 
	 * List&lt;? extends BpmTask>
	 */
	 List<DefaultBpmTask> getByInstId(String instId);
	
	 List<DefaultBpmTask> getByExeIdAndNodeId(String instId,String nodeId);	 
	
	/**
	 * 根据流程实例Id和用户获取任务列表。
	 * @param instId
	 * @param userId
	 * @return 
	 * List&lt;? extends BpmTask>
	 */
	 List<DefaultBpmTask> getByInstUser(String instId,String userId);
	 
	 /**
	  * 按用户ID，实例Id 用户组列表查找任务
	  * @param bpmnInstId
	  * @param userId
	  * @param groupList
	  * @return
	  */
	 List<DefaultBpmTask> getByBpmInstIdUserIdGroupList(String bpmnInstId,String userId,List<IGroup> groupList);
	 
	 /**
	  * 通过ID更新执行Id
	  * @param taskId bpm_task表的主键
	  * @param userId 用户ID 
	  * void
	  */
	 void updateAssigneeById(String taskId,String userId);
	 /**
	  * 通过任务ID更新其所属人、执行人
	  * @param taskId
	  * @param ownerId
	  * @param assigneeId 
	  * void
	  */
	 void updateAssigneeOwnerId(String taskId,String ownerId,String assigneeId);
	 
	 
	 /**
	  * 根据流程实例列表删除任务。
	  * @param instList 
	  * void
	  */
	 void delByInstList(List<String> instList);
	 
	 /**
	  * 根据父ID删除任务。
	  * @param parentId 
	  * void
	  */
	 void delByParentId(String parentId);
	 /**
	  * 根据父ID获取任务列表。
	  * @param taskId
	  * @return 
	  * List&lt;DefaultBpmTask>
	  */
	 List<DefaultBpmTask> getByParentId(String taskId);
	 
	 /**
	  * 根据用户ID获取流转任务。
	  * @param userId
	  * @param queryFilter
	  * @return 
	  * PageList&lt;DefaultBpmTask>
	  */
//	 List<DefaultBpmTask> getTransByUserId(String userId,QueryFilter queryFilter);

	 /**
	  * 取得未到期的任务
	  * @return
	  */
	 List<DefaultBpmTask> getReminderTask();
	 
	 
	 /**
	  * 根据实例ID列表获取任务列表。
	  * @param instIds
	  * @return 
	  * List&lt;DefaultBpmTask>
	  */
	 List<DefaultBpmTask> getByInstList(List<String> instIds);
	 
	 /**
	  * 根据用户ID获取其流转出去的任务列表。
	  * @param userId
	  * @param queryFilter
	  * @return 
	  * PageList&lt;DefaultBpmTask>
	  */
	 PageList<DefaultBpmTask> getMyTransTask(String userId,QueryFilter queryFilter);
	 // 修改任务的紧急程度
	void updateTaskPriority(String taskId, Long priority);
	
	/**
	 * 根据executeId 查询关联的任务，这个主要是在会签场景中使用。
	 * @param executeId		执行ID
	 * @param nodeId		节点ID
	 * @return
	 */
	List<DefaultBpmTask>  getByExecuteAndNodeId(String executeId,String nodeId);
	 

	List<DefaultBpmTask> getByNeedPendMeetingUserId(String userId, Map<String, String> groupMap,
			QueryFilter queryFilter);
	 
}


