package com.hotent.bpmx.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.org.api.model.IGroup;


public interface BpmProcessInstanceDao extends Dao<String, DefaultBpmProcessInstance> {
	
	/**
	 * 添加流程实例。
	 * @param processInstance 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void createHistory(DefaultBpmProcessInstance processInstance);
	
	
	/**
	 * 更新流程实例。
	 * @param processInstance 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void updateHistory(DefaultBpmProcessInstance processInstance);
	
	DefaultBpmProcessInstance getBpmProcessInstanceHistory(String procInstId);
	DefaultBpmProcessInstance getBpmProcessInstanceHistoryByBpmnInstId(String bpmnInstId);
	/**
	 * 根据activiti实例查询流程运行实例。
	 * @param bpmnInstId
	 * @return 
	 * DefaultBpmProcessInstance
	 * @exception 
	 * @since  1.0.0
	 */
	DefaultBpmProcessInstance getBpmnInstId(String bpmnInstId);
	
	List<DefaultBpmProcessInstance> getByUserId(String userId);
	
	PageList<DefaultBpmProcessInstance> getByUserId(String userId,Page page);
	
	List<DefaultBpmProcessInstance> getByUserId(String userId,QueryFilter queryFilter);
	
	void updateStatusByInstanceId(String processInstanceId,String status);
	
	void updateStatusByBpmnInstanceId(String processInstanceId,String status);
	/**
	 * 按用户Id及组列表查找实例
	 * @param userId
	 * @param groupList
	 * @return
	 */
	List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId,List<IGroup> groupList);
	/**
	 * 按用户Id及组列表查找实例并且分页
	 * @param userId
	 * @param groupList
	 * @param page
	 * @return
	 */
	List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId,List<IGroup> groupList,Page page);
	
	/**
	 * 按用户Id及组列表和QueryFilter查找实例并且分页
	 * @param userId
	 * @param groupList
	 * @param page
	 * @return
	 */
	List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId,List<IGroup> groupList,QueryFilter queryFilter);
	/**
	 * 按人员查找其参与的流程实例
	 * @param usreId
	 * @return 
	 * List<DefaultBpmProcessInstance>
	 * @exception 
	 * @since  1.0.0
	 */
	List<DefaultBpmProcessInstance> getByAttendUserId(String usreId);
	/**
	 * 按人员查找其参与的流程实例并分页返回结果
	 * @param usreId
	 * @param page
	 * @return 
	 * List<DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getByAttendUserId(String usreId,Page page);
	
	
	/**
	 * 根据流程实例获取流程实例ID列表。
	 * @param instList
	 * @return 
	 * List&lt;String>
	 */
	List<String> getBpmnByInstList(List<String> instList);
	
	/**
	 * 根据流程定义获取流程实例列表。 
	 * @param bpmnDefId		流程定义ID
	 * @return 
	 * List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getListByBpmnDefKey(String bpmnDefKey);
	
	
	
	/**
	 * 查询参与的流程。
	 * @param userId
	 * @param queryFilter
	 * @return 
	 * List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getByAttendUserId(String userId, QueryFilter queryFilter);
	
	/**
	 * 获取我的请求。
	 * @param userId
	 * @param queryFilter
	 * @return  List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getMyRequestByUserId(String userId,
			QueryFilter queryFilter);
	
	/**
	 * 获取我的办结。
	 * @param userId
	 * @param queryFilter
	 * @return  List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getMyCompletedByUserId(String userId,
			QueryFilter queryFilter);
	/**
	 *  获取我发起的草稿。
	 * @param userId
	 * @param queryFilter
	 * @return  List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getDraftsByUserId(String userId,QueryFilter queryFilter);
	
	/**
	 *  获取已办事宜。
	 * @param userId
	 * @param queryFilter
	 * @return  List&lt;DefaultBpmProcessInstance>
	 */
	List<Map<String,Object>> getHandledByUserId(String userId,
			QueryFilter queryFilter);

	/**
	 *  获取办结事宜。
	 * @param userId
	 * @param queryFilter
	 * @return  List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getCompletedByUserId(String userId,
			QueryFilter queryFilter);
	
	/**
	 * 更新流程实例是否禁止。
	 * @param defId
	 * @param isForbidden 
	 * void
	 */
	void updForbiddenByDefKey(String defKey,Integer isForbidden);
	
	/**
	 * 根据流程实例ID更新流程实例是否禁止。
	 * @param instId
	 * @param isForbidden 
	 * void
	 */
	void updForbiddenByInstId(String instId, Integer isForbidden);
	
	/**
	 * 根据流程键和是否正式获取流程实例。
	 * @param defKey
	 * @param formal
	 * @return 
	 * List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getByDefKeyFormal(String defKey,String formal);
	
	
	/**
	 * 根据父实例ID获取流程实例列表。
	 * @param parentInstId
	 * @return 
	 * List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getByParentId(String parentInstId);
	
	
	/**
	 * 根据流程定义ID
	 * @param procDefId
	 * @return 
	 * List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getListByDefId(String procDefId);
 
	/**
	 * 根据父流程实例ID和节点定义ID查子流程实例ＩＤ
	 * @Title: getBpmnByParentIdAndSuperNodeId 
	 * @Description: TODO
	 * @param parentInstId
	 * @param superNodeId
	 * @return
	 * @return: BpmProcessInstance
	 */
	List<BpmProcessInstance> getBpmnByParentIdAndSuperNodeId(String parentInstId,String superNodeId);
	
	List<BpmProcessInstance> getHiBpmnByParentIdAndSuperNodeId(String parentInstId,String superNodeId);
	/**
	 * 根据业务主键获取流程实例。
	 * @param businessKey
	 * @return
	 */
	DefaultBpmProcessInstance getByBusinessKey(String businessKey);
}
