package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmSignData;

/**
 * 会签数据访问。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-3-30-下午11:16:48
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface BpmSignDataDao extends Dao<String, BpmSignData> {
	
	
	/**
	 * 根据实例ID获取会签结果。
	 * @param executeId
	 * @param nodeId
	 * @param isActive
	 * @return
	 */
	List<BpmSignData> getVoteByExecuteNode(String executeId,String nodeId,Integer isActive);
	
	
	
	
	/**
	* 根据流程实例列表删除任务。
	* @param instList 
	* void
	*/
	void delByInstList(List<String> instList) ;
	
	
	/**
	 * 根据运行实例ID,节点ID和节点索引。
	 * @param executeId
	 * @param nodeId
	 * @param index
	 * @return  BpmSignData
	 */
	BpmSignData getByExeNodeIndex(String executeId,String nodeId,Short index);
	
	/**
	 * 会签完成更新会签数据状态为不活动。
	 * @param executeId
	 * @param nodeId
	 */
	void updByNotActive(String executeId,String nodeId);
	
	/**
	 * 删除非活动的会签数据。
	 * @param executeId
	 * @param nodeId
	 */
	void removeByNotActive(String executeId,String nodeId);
	
	/**
	 * 根据流程实例id和用户id获取会签数据
	 * @param instancId
	 * @param userId
	 * @return
	 */
	BpmSignData getByInstanIdAndUserId(String instancId,String userId);
	
}
