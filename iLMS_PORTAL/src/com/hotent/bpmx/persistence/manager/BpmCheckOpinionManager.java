package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;

public interface BpmCheckOpinionManager extends Manager<String, DefaultBpmCheckOpinion>{
	/**
	 * 根据任务ID，查询对应的审批意见（只对应一条）
	 * @param taskId
	 * @return 
	 * BpmCheckOpinion
	 */
	 DefaultBpmCheckOpinion getByTaskId(String taskId);	
	
	
	/**
	 * 归档意见历史。
	 * @param instId 
	 * void
	 */
	void archiveHistory(String instId);
	
	
	/**
	 * 根据流程实例ID获取流程意见。
	 * @param instId
	 * @return 
	 * List&lt;DefaultBpmCheckOpinion>
	 */
	List<DefaultBpmCheckOpinion> getByInstId(String instId);
	
	/**
	 * 根据流程实例Id获取表单的意见数据,用于在表单展示意见。
	 * @param instId
	 * @return List&lt;DefaultBpmCheckOpinion>
	 */
	List<DefaultBpmCheckOpinion> getFormOpinionByInstId(String instId);
	
	/**
	 * 根据流程实例取得关联的流程实例ID列表。
	 * @param instId	流程实例ID
	 * @return  List&lt;String>
	 */
	List<String> getListByInstId(String instId);
	
	/**
	  * 根据流程实例列表删除意见数据。
	  * @param instList 
	  * void
	  */
	 void delByInstList(List<String> instList);
	 
	 /**
	  * 向上查询得到顶级的流程实例。
	  * @param instId 流程实例ID
	  * @return  String
	  */
	 String getTopInstId(String instId);
	
	 /**
	 * 根据流程实例，节点 获取 流程意见
	 * @param instId
	 * @param nodeId
	 * @return
	 */
	 List<DefaultBpmCheckOpinion> getByInstNodeId(String instId,String nodeId);
	 
	 
	 /**
	  * 更新待审批节点状态。
	  * @param taskId	任务ID	
	  * @param status	状态
	  */
	void updStatusByWait(String taskId,String status);
	
	/**
	 * 获取未审批的意见列表。
	 * @param list
	 * void
	 */
	List<DefaultBpmCheckOpinion> getByInstIdsAndWait(List<String> list);

	/**
	 * 通过TaskId 和action类型获取意见
	 * @param taskAction 
	 * */
	DefaultBpmCheckOpinion getByTaskIdStatus(String taskId, String taskAction);

	 
}
