package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.persistence.model.DefaultBpmProStatus;

public interface BpmProStatusManager extends Manager<String, DefaultBpmProStatus>{
	
	
	List<DefaultBpmProStatus> queryHistorys(String procInstId);
	
	/**
	 * 根据流程实例ID归档状态数据。
	 * @param procInstId 
	 * void
	 */
	void archiveHistory(String procInstId);
	
	/**
	 * 添加或更新流程实例数据。
	 * @param instId		流程实例ID
	 * @param defId			流程定义ID
	 * @param nodeId		节点ID
	 * @param nodeName		节点名称
	 * @param nodeStatus 	节点状态
	 * void
	 */
	void createOrUpd(String instId,String defId,String nodeId,String nodeName,NodeStatus nodeStatus);
	
	
	/**
	 * 根据流程实例列表删除数据。
	 * @param instList 
	 * void
	 */
	void delByInstList(List<String> instList);
	
	/**
	 * 根据实例和节点获取节点状态。
	 * @param instId
	 * @param nodeId
	 * @return DefaultBpmProStatus
	 */
	DefaultBpmProStatus getByInstNodeId(String instId,String nodeId);
	
	
	/**
	 * 更新待审批的节点为指定状态。
	 * @param list
	 * @param status 
	 * void
	 */
	void updStatusByInstList(List<String> list,NodeStatus status);
}
