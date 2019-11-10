package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;



public interface BpmCheckOpinionDao extends Dao<String, DefaultBpmCheckOpinion> {
	/**
	 * 根据任务ID查询审批意见（只有一条记录）
	 * @param taskId
	 * @return  BpmCheckOpinion
	 */
	 DefaultBpmCheckOpinion getByTaskId(String taskId);
	
	
	 void archiveHistory(String procInstId);
	
	 /**
	  * 根据实例ID查询意见列表。
	  * @param instIdList
	  * @return 
	  * List&lt;DefaultBpmCheckOpinion>
	  */
	 List<DefaultBpmCheckOpinion> getByInstIds(List<String> instIdList);
	
	 
	 /**
	  * 根据父实例ID查询子实例ID列表。
	  * @param supInstId
	  * @return  List&lt;DefaultBpmCheckOpinion>
	  */
	 List<String> getBySupInstId(String supInstId);
	 
	 /**
	  * 查询父实例ID。
	  * @param instId
	  * @return DefaultBpmCheckOpinion
	  */
	 String getSupInstByInstId(String instId);
	 
	 
	 /**
	  * 根据流程实例列表删除意见数据。
	  * @param instList 
	  * void
	  */
	 void delByInstList(List<String> instList);

	 /**
	  * 根据流程实例，节点ID 获取该节点审批意见
	  * @param instId
	  * @param nodeId
	  * @return
	  */
	List<DefaultBpmCheckOpinion> getByInstNodeId(String instId, String nodeId);
	
	/**
	 *  更新待审批节点状态。
	 * @param taskId		任务ID
	 * @param status		状态
	 */
	void updStatusByWait(String taskId,String status);
	
	
	/**
	 * 获取未审批的意见列表。
	 * @param list
	 * void
	 */
	List<DefaultBpmCheckOpinion> getByInstIdsAndWait(List<String> list);


	DefaultBpmCheckOpinion getByTaskIdStatus(String taskId, String taskAction);


	List<DefaultBpmCheckOpinion> getByInstNodeIdAndAgree(String instId,
			String fromNodeId);

	
}
