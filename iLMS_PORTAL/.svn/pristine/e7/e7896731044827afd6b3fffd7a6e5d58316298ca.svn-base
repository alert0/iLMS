package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.persistence.model.DefaultBpmProStatus;


public interface BpmProStatusDao extends Dao<String, DefaultBpmProStatus> {
	
	
	
	public List<DefaultBpmProStatus> queryHistorys(String procInstId);
	
	/**
	 * 根据流程实例ID归档。
	 * @param procInstId 
	 * void
	 */
	public void archiveHistory(String procInstId);
	
	
	/**
	 * 返回流程状态数据。
	 * @param instId
	 * @param nodeId
	 * @return  int
	 */
	DefaultBpmProStatus getByInstNodeId(String instId,String nodeId);
	
	
	/**
	 * 根据流程实例列表删除数据。
	 * @param instList 
	 * void
	 */
	void delByInstList(List<String> instList);
	
	
	/**
	 * 更新待审批的节点为指定状态。
	 * @param list
	 * @param status 
	 * void
	 */
	void updStatusByInstList(List<String> list,NodeStatus status);
}
