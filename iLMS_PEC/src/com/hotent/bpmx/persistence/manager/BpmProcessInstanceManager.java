package com.hotent.bpmx.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.api.cmd.ProcessInstCmd;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.org.api.model.IGroup;

public interface BpmProcessInstanceManager extends Manager<String, DefaultBpmProcessInstance>
{
	/**
	 * 构建流程定义标题
	 * 
	 * @param bpmDefinition
	 * @param processInstCmd
	 * @return String
	 */
	String getSubject(BpmProcessDef<BpmProcessDefExt> bpmDefinition, ProcessInstCmd processInstCmd, DefaultBpmProcessInstance defaultBpmProcessInstance);

	/**
	 * 根据BPMN流程实例获取流程实例数据。
	 * 
	 * @param bpmnInstId
	 * @return DefaultBpmProcessInstance
	 */
	DefaultBpmProcessInstance getByBpmnInstId(String bpmnInstId);

	DefaultBpmProcessInstance getBpmProcessInstanceHistory(String procInstId);

	DefaultBpmProcessInstance getBpmProcessInstanceHistoryByBpmnInstId(String bpmnInstId);

	/**
	 * 根据用户ID获取流程运行实例。
	 * @param userId
	 * @return
	 */
	List<DefaultBpmProcessInstance> getByUserId(String userId);

	/**
	 * 根据用户ID获取分页数据。
	 * @param userId
	 * @param page
	 * @return
	 */
	List<DefaultBpmProcessInstance> getByUserId(String userId, Page page);

	List<DefaultBpmProcessInstance> getByUserId(String userId, QueryFilter queryFiler);

	void updateStatusByInstanceId(String processInstanceId, String status);

	void updateStatusByBpmnInstanceId(String processInstanceId, String status);

	/**
	 * 按用户Id及组列表查找实例
	 * 
	 * @param userId
	 * @param groupList
	 * @return
	 */
	List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId, List<IGroup> groupList);

	/**
	 * 按用户Id及组列表查找实例并且分页
	 * 
	 * @param userId
	 * @param groupList
	 * @param page
	 * @return
	 */
	List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId, List<IGroup> groupList, Page page);

	/**
	 * 按用户Id及组列表查找实例并且分页
	 * 
	 * @param userId
	 * @param groupList
	 * @param page
	 * @return
	 */
	List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId, List<IGroup> groupList, QueryFilter queryFilter);

	/**
	 * 按人员查找其参与的流程实例
	 * 
	 * @param usreId
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getByAttendUserId(String usreId);

	/**
	 * 按人员查找其参与的流程实例并分页返回结果
	 * 
	 * @param usreId
	 * @param page
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	PageList<DefaultBpmProcessInstance> getByAttendUserId(String usreId, Page page);

	/**
	 * 根据人员查询并返回查询结果。
	 * 
	 * @param usreId
	 * @param queryFilter
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getByAttendUserId(String usreId, QueryFilter queryFilter);

	/**
	 * 根据流程定义获取流程实例列表。
	 * 
	 * @param bpmnDefId
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getListByBpmnDefKey(String defKey);
	
	

	/**
	 * 获取我的请求。
	 * 
	 * @param userId
	 * @param queryFilter
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getMyRequestByUserId(String userId, QueryFilter queryFilter);

	/**
	 * 获取我的办结。
	 * 
	 * @param userId
	 * @param queryFilter
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getMyCompletedByUserId(String userId, QueryFilter queryFilter);

	/**
	 * 获取我发起的草稿。
	 * 
	 * @param userId
	 * @param queryFilter
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getDraftsByUserId(String userId, QueryFilter queryFilter);

	/**
	 * 获取已办事宜。
	 * 
	 * @param userId
	 * @param queryFilter
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<Map<String,Object>> getHandledByUserId(String userId, QueryFilter queryFilter);

	/**
	 * 获取办结事宜。
	 * 
	 * @param userId
	 * @param queryFilter
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getCompletedByUserId(String userId, QueryFilter queryFilter);

	/**
	 * 更新根据流程定义更新流程实例状态。
	 * 
	 * @param defId
	 * @param isForbidden
	 *            void
	 */
	void updForbiddenByDefKey(String defKey, Integer isForbidden);

	/**
	 * 根据流程实例ID更新流程实例是否禁止。
	 * 
	 * @param instId
	 * @param isForbidden
	 *            void
	 */
	void updForbiddenByInstId(String instId, Integer isForbidden);

	/**
	 * 根据流程定义key删除测试用例。
	 * 
	 * <pre>
	 * 1.根据流程定义KEY找到所有的状态为测试的实例ID.
	 * 2.根据实例ID删除相关数据。
	 * </pre>
	 * 
	 * @param defKey
	 *            void
	 */
	void removeTestInstByDefKey(String defKey);

	/**
	 * 根据父ID获取所有的实例子列表。
	 * 
	 * @param parentId
	 *            父ID。
	 * @param includeSelf
	 *            是否包括当前实例数据。
	 * @return List&lt;String>
	 */
	List<DefaultBpmProcessInstance> getByParentId(String parentId, boolean includeSelf);

	/**
	 * 流程发起人撤销流程实例。
	 * 
	 * <pre>
	 * 	1.根据流程实例ID查找所有的子实例。
	 * 	2.查找相关的任务数据和Execution数据。
	 *  3.保留主Execution。
	 * 	4.创建新任务指向主流程实例。
	 * 
	 * </pre>
	 * 
	 * @param instanceId
	 * @return ResultMessage
	 */
	ResultMessage revokeInstance(String instanceId, String informType, String cause);

	/**
	 * 撤回任务
	 * 
	 * <pre>
	 * 	1.根据流程实例ID查找所有的子实例。
	 * 	2.查找相关的任务数据和Execution数据。
	 *  3.保留主Execution。
	 * 	4.创建新任务指向主流程实例。
	 * 
	 * </pre>
	 * 
	 * @param instanceId
	 * @return ResultMessage
	 */
	ResultMessage revokeTask(String instId, String informType, String cause);

	/**
	 * 判断根据流程实是否例撤销到发起人。
	 * 
	 * @param instanceId
	 * @return boolean
	 */
	ResultMessage canRevokeToStart(String instanceId);

	/**
	 * 是否可以追回。
	 * 
	 * <pre>
	 * 1.根据
	 * </pre>
	 * 
	 * @param instanceId
	 * @return ResultMessage
	 */
	ResultMessage canRevoke(String instanceId, String nodeId);

	/**
	 * 按用户的授权内容去查询列表
	 * 
	 * @param queryFilter
	 * @return
	 */
	List<DefaultBpmProcessInstance> queryList(QueryFilter queryFilter);

	/**
	 * 根据流程实例ID查询顶级的流程实例。
	 * <pre>
	 *  根据父实例向上查找，只到找到父实例为0的实例为止。
	 * </pre>
	 * @param instance
	 * @return BpmProcessInstance
	 */
	BpmProcessInstance getTopBpmProcessInstance(String instanceId);

	/**
	 * 根据流程实例查询顶级的流程实例。
	 * 
	 * @param instance
	 * @return BpmProcessInstance
	 */
	BpmProcessInstance getTopBpmProcessInstance(BpmProcessInstance instance);

	/**
	 * 根据流程定义ID获取实例列表。
	 * 
	 * @param defId
	 * @return List&lt;DefaultBpmProcessInstance>
	 */
	List<DefaultBpmProcessInstance> getListByDefId(String defId);
 
	/**
	 * 根据父流程实例ID和节点定义ID查子流程实例ＩＤ
	 * @Title: getBpmnByParentIdAndSuperNodeId 
	 * @Description: TODO
	 * @param parentInstId
	 * @param superNodeId
	 * @return
	 * @return: BpmProcessInstance
	 */
	List<BpmProcessInstance> getBpmProcessByParentIdAndSuperNodeId(String parentInstId,String superNodeId);
	
	List<BpmProcessInstance> getHiBpmProcessByParentIdAndSuperNodeId(String parentInstId,String superNodeId);
	
 
	/**
	 * 根据业务主键获取流程实例数据。
	 * @param businessKey
	 * @return
	 */
	DefaultBpmProcessInstance getByBusinessKey(String businessKey) ;

 
}
