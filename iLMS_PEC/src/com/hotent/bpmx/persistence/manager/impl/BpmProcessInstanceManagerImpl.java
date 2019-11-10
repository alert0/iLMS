package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.ProcessInstCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.ProcessInstanceStatus;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.core.engine.inst.DefaultProcessInstCmd;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.core.util.MessageUtil;
import com.hotent.bpmx.natapi.inst.NatProInstanceService;
import com.hotent.bpmx.persistence.constants.ProcDefTestStatus;
import com.hotent.bpmx.persistence.dao.ActTaskDao;
import com.hotent.bpmx.persistence.dao.BpmCheckOpinionDao;
import com.hotent.bpmx.persistence.dao.BpmExeStackDao;
import com.hotent.bpmx.persistence.dao.BpmExeStackRelationDao;
import com.hotent.bpmx.persistence.dao.BpmProcessInstanceDao;
import com.hotent.bpmx.persistence.manager.ActExecutionManager;
import com.hotent.bpmx.persistence.manager.ActTaskManager;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmDefAuthorizeManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackExecutorManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmSignDataManager;
import com.hotent.bpmx.persistence.manager.BpmTaskCandidateManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskReadManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.manager.CopyToManager;
import com.hotent.bpmx.persistence.model.ActExecution;
import com.hotent.bpmx.persistence.model.ActTask;
import com.hotent.bpmx.persistence.model.AuthorizeRight;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.bpmx.persistence.model.BpmExeStack;
import com.hotent.bpmx.persistence.model.BpmExeStackExecutor;
import com.hotent.bpmx.persistence.model.BpmExeStackRelation;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProStatus;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskCandidate;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;



@Service("bpmProcessInstanceManager")
public class BpmProcessInstanceManagerImpl extends AbstractManagerImpl<String, DefaultBpmProcessInstance> implements BpmProcessInstanceManager
{

	protected static final Logger LOGGER = LoggerFactory.getLogger(BpmProcessInstanceManagerImpl.class);
	@Resource
	BpmExeStackDao bpmExeStackDao;
	@Resource
	BpmProcessInstanceDao bpmProcessInstanceDao;
	@Resource
	NatProInstanceService natProInstanceService;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmTaskCandidateManager bpmTaskCandidateManager;
	@Resource
	BpmProStatusManager bpmProStatusManager;
	@Resource
	CopyToManager copyToManager;
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager;
	@Resource
	BpmSignDataManager bpmSignDataManager;
	@Resource
	BpmTaskReadManager bpmTaskReadManager;
	@Resource
	ActExecutionManager actExecutionManager;
	@Resource
	ActTaskDao actTaskDao;
	@Resource
	BpmDefAuthorizeManager bpmDefAuthorizeManager;

	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	ActTaskManager actTaskManager;
	@Resource
	IUserService userServiceImpl;
	@Resource
	BpmExeStackManager bpmExeStackManager;
	@Resource
	BpmFormService bpmFormService;
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	@Resource
	BpmTaskActionService bpmTaskActionService;
	@Resource
	IUserService userService;
    @Resource
    BpmDefinitionManager bpmDefinitionManager;

	@Override
	protected Dao<String, DefaultBpmProcessInstance> getDao()
	{
		return bpmProcessInstanceDao;
	}

	@Override
	public String getSubject(BpmProcessDef<BpmProcessDefExt> bpmDefinition, ProcessInstCmd processInstCmd, DefaultBpmProcessInstance defaultBpmProcessInstance)
	{
		
		// 若设置了标题，则直接返回该标题，否则按后台的标题规则返回
		if (StringUtils.isNotEmpty(processInstCmd.getSubject())){
			return processInstCmd.getSubject();
		}
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmDefinition.getProcessDefExt();
		String rule = defExt.getExtProperties().getSubjectRule();
 	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", bpmDefinition.getName());
		map.put("startorName", ContextUtil.getCurrentUser().getFullname());
		map.put("startDate", DateUtil.getCurrentTime("yyyy-MM-dd"));
		map.put("startTime", DateUtil.getCurrentTime());
		map.put("businessKey", processInstCmd.getBusinessKey());
		map.putAll(((DefaultProcessInstCmd) processInstCmd).getVariables());

		
		Map<String,BoData> boMap=BpmContextUtil.getBoFromContext();
		if(BeanUtils.isNotEmpty(boMap)){
			Collection<BoData> dataObjects = boMap.values();
			for (BoData boData : dataObjects){
				BaseBoDef bodef=boData.getBoDef();
				String boName=bodef.getAlias();
				Map<String,Object> dataMap= boData.getData();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					map.put(boName +"." + entry.getKey(),  entry.getValue());
				}
			}
		}
		
		rule = BpmUtil.getTitleByRule(rule, map);
		// 如果不是正式, 显示测试标题带测试状态 
		 DefaultBpmDefinition bpmDef = this.bpmDefinitionManager.getById(bpmDefinition.getProcessDefinitionId());
		if(ProcDefTestStatus.TEST.getKey().equalsIgnoreCase(bpmDef.getStatus())){
			rule = ProcDefTestStatus.TEST.getName() + " -- " + rule;
		}
		return rule;

	}

	


	@Override
	public void remove(String processInstId){

		DefaultBpmProcessInstance inst = (DefaultBpmProcessInstance) get(processInstId);
		// 草稿
		if (ProcessInstanceStatus.STATUS_DRAFT.getKey().equals(inst.getStatus())){
			super.remove(processInstId);
		} 
		else{
			BpmProcessInstance topInstance = getTopBpmProcessInstance(processInstId);

			String topInstId = topInstance.getId();

			List<DefaultBpmProcessInstance> instList = getByParentId(topInstId, true);

			String topBpmnInstId = topInstance.getBpmnInstId();

			List<String> instIdList = getInstList(instList);

			List<String> bpmnInstList = bpmProcessInstanceDao.getBpmnByInstList(instIdList);
			// 删除
			removeCascade(instIdList);
			// 删除流程数据。
			actExecutionManager.delByInstList(bpmnInstList);
			// 删除关联的实例。
			actExecutionManager.remove(topBpmnInstId);
		}
	}

	private List<String> getInstList(List<DefaultBpmProcessInstance> instList)
	{
		List<String> list = new ArrayList<String>();
		for (DefaultBpmProcessInstance instance : instList)
		{
			list.add(instance.getId());
		}
		return list;
	}

	/**
	 * 删除任务数据 删除任务人员数据 删除实例数据 删除抄送数据 状态数据 TASK_READ BPM_TASK_SIGNDATA
	 * BPM_TASK_TURN
	 * 
	 * @param instList
	 *            void
	 */
	private void removeCascade(List<String> instList)
	{
		// 删除意见数据
		bpmCheckOpinionManager.delByInstList(instList);
		// 删除候选人数据
		bpmTaskCandidateManager.delByInstList(instList);
		// 删除任务
		bpmTaskManager.delByInstList(instList);
		// 删除状态数据
		bpmProStatusManager.delByInstList(instList);
		// 抄送删除
		copyToManager.delByInstList(instList);
		// 任务转办代理
		bpmTaskTurnManager.delByInstList(instList);
		// 会签数据
		bpmSignDataManager.delByInstList(instList);
		// 是否阅读
		bpmTaskReadManager.delByInstList(instList);

		for (String id : instList)
		{
			super.remove(id);
		}

	}

	/**
	 * 更新时如果状态为结束或者手工结束，则删除运行实例数据，更新历史数据。
	 */
	@Override
	public void update(DefaultBpmProcessInstance entity)
	{

		String status = entity.getStatus();
		// 流程结束时，删除当前实例数据并归档。
		if (ProcessInstanceStatus.STATUS_END.getKey().equals(status) || ProcessInstanceStatus.STATUS_MANUAL_END.getKey().equals(status))
		{
			// bpmProcessInstanceDao.remove(entity.getId());
			super.update(entity);
			bpmProcessInstanceDao.createHistory(entity);
		} else
		{
			super.update(entity);
		}
	}

	@Override
	public DefaultBpmProcessInstance getByBpmnInstId(String bpmnInstId)
	{
		return bpmProcessInstanceDao.getBpmnInstId(bpmnInstId);
	}

	@Override
	public DefaultBpmProcessInstance getBpmProcessInstanceHistory(String procInstId)
	{
		return bpmProcessInstanceDao.getBpmProcessInstanceHistory(procInstId);
	}

	@Override
	public DefaultBpmProcessInstance getBpmProcessInstanceHistoryByBpmnInstId(String bpmnInstId)
	{
		return bpmProcessInstanceDao.getBpmProcessInstanceHistoryByBpmnInstId(bpmnInstId);
	}

	@Override
	public List<DefaultBpmProcessInstance> getByUserId(String userId)
	{
		return bpmProcessInstanceDao.getByUserId(userId);
	}

	@Override
	public List<DefaultBpmProcessInstance> getByUserId(String userId, Page page)
	{
		return bpmProcessInstanceDao.getByUserId(userId, page);
	}

	@Override
	public void updateStatusByBpmnInstanceId(String processInstanceId, String status)
	{
		bpmProcessInstanceDao.updateStatusByBpmnInstanceId(processInstanceId, status);
	}

	@Override
	public void updateStatusByInstanceId(String processInstanceId, String status)
	{
		bpmProcessInstanceDao.updateStatusByInstanceId(processInstanceId, status);
	}

	@Override
	public List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId, List<IGroup> groupList)
	{
		return bpmProcessInstanceDao.getByUserIdGroupList(userId, groupList);
	}

	@Override
	public List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId, List<IGroup> groupList, Page page)
	{
		return bpmProcessInstanceDao.getByUserIdGroupList(userId, groupList, page);
	}

	@Override
	public List<DefaultBpmProcessInstance> getByAttendUserId(String usreId)
	{
		return bpmProcessInstanceDao.getByAttendUserId(usreId);
	}

	@Override
	public PageList<DefaultBpmProcessInstance> getByAttendUserId(String usreId, Page page)
	{
		return (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceDao.getByAttendUserId(usreId, page);
	}

	@Override
	public List<DefaultBpmProcessInstance> getListByBpmnDefKey(String bpmDefKey)
	{
		List<DefaultBpmProcessInstance> list = bpmProcessInstanceDao.getListByBpmnDefKey(bpmDefKey);
		return list;
	}

	@Override
	public List<DefaultBpmProcessInstance> getByAttendUserId(String userId, QueryFilter queryFilter)
	{
		return bpmProcessInstanceDao.getByAttendUserId(userId, queryFilter);
	}

	@Override
	public List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId, List<IGroup> groupList, QueryFilter queryFilter)
	{
		return bpmProcessInstanceDao.getByUserIdGroupList(userId, groupList, queryFilter);
	}

	@Override
	public List<DefaultBpmProcessInstance> getByUserId(String userId, QueryFilter queryFiler)
	{

		return bpmProcessInstanceDao.getByUserId(userId, queryFiler);
	}

	@Override
	public List<DefaultBpmProcessInstance> getMyRequestByUserId(String userId, QueryFilter queryFilter)
	{
		return bpmProcessInstanceDao.getMyRequestByUserId(userId, queryFilter);
	}

	@Override
	public List<DefaultBpmProcessInstance> getMyCompletedByUserId(String userId, QueryFilter queryFilter)
	{
		return bpmProcessInstanceDao.getMyCompletedByUserId(userId, queryFilter);
	}

	@Override
	public List<DefaultBpmProcessInstance> getDraftsByUserId(String userId, QueryFilter queryFilter)
	{
		return bpmProcessInstanceDao.getDraftsByUserId(userId, queryFilter);
	}

	@Override
	public List<Map<String,Object>> getHandledByUserId(String userId, QueryFilter queryFilter)
	{
		return bpmProcessInstanceDao.getHandledByUserId(userId, queryFilter);
	}

	@Override
	public List<DefaultBpmProcessInstance> getCompletedByUserId(String userId, QueryFilter queryFilter)
	{
		return bpmProcessInstanceDao.getCompletedByUserId(userId, queryFilter);
	}

	@Override
	public void updForbiddenByDefKey(String defKey, Integer isForbidden)
	{
		bpmProcessInstanceDao.updForbiddenByDefKey(defKey, isForbidden);
	}

	@Override
	public void updForbiddenByInstId(String instId, Integer isForbidden)
	{
		bpmProcessInstanceDao.updForbiddenByInstId(instId, isForbidden);

	}

	@Override
	public void removeTestInstByDefKey(String defKey)
	{
		List<DefaultBpmProcessInstance> list = bpmProcessInstanceDao.getByDefKeyFormal(defKey, BpmProcessInstance.FORMAL_NO);
		for (DefaultBpmProcessInstance instance : list)
		{
			this.remove(instance.getId());
		}
	}

	@Override
	public List<DefaultBpmProcessInstance> getByParentId(String parentId, boolean includeSelf)
	{

		List<DefaultBpmProcessInstance> list = new ArrayList<DefaultBpmProcessInstance>();
		if (includeSelf)
		{
			DefaultBpmProcessInstance instance = bpmProcessInstanceDao.get(parentId);
			list.add(instance);
		}
		List<DefaultBpmProcessInstance> instances = bpmProcessInstanceDao.getByParentId(parentId);

		if (BeanUtils.isEmpty(instances)) return list;

		for (DefaultBpmProcessInstance instance : instances){
			recursionByParent(instance, list);
		}

		return list;
	}

	private void recursionByParent(DefaultBpmProcessInstance parentInst, List<DefaultBpmProcessInstance> list)
	{
		list.add(parentInst);
		List<DefaultBpmProcessInstance> instances = bpmProcessInstanceDao.getByParentId(parentInst.getId());
		if (BeanUtils.isEmpty(instances)) 	return;
		
		for (DefaultBpmProcessInstance instance : instances){
			recursionByParent(instance, list);
		}

	}

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
	 */
	@SuppressWarnings("unused")
	@Override
	public ResultMessage revokeInstance(String instanceId, String informType, String cause)
	{
		ResultMessage resultMessage = canRevokeToStart(instanceId);
		// 检查不符合撤销条件则返回。
		if (ResultMessage.ERROR == resultMessage.getResult()) return resultMessage;
		
		// 获取流程第一个节点
		BpmNodeDef bpmNodeDef = (BpmNodeDef) resultMessage.getVars().get("bpmNodeDef");

		List<DefaultBpmProcessInstance> instList = getByParentId(instanceId, true);

		DefaultBpmProcessInstance mainInstance = getMainInstance(instList, instanceId);
		ActionCmd actionCmd = ContextThreadUtil.getActionCmd();

		if (actionCmd == null){
			DefaultProcessInstCmd actionCmd2 = new DefaultProcessInstCmd();
			actionCmd2.setInstId(instanceId);
			actionCmd2.addTransitVars("IsUnused", true);
			ContextThreadUtil.setActionCmd(actionCmd2);

		} 
		else{
			actionCmd.addTransitVars("IsUnused", true);
		}

		// 获取流程实例列表。
		List<String> includeIdList = getIdList(instList, instanceId, true, false);
		// 获取BPMN流程实例列表。
		List<String> includeBpmnIdList = getIdList(instList, instanceId, true, true);
		List<String> notIncludeBpmnIdList = getIdList(instList, instanceId, false, true);

		List<IUser> users = getNotifyUsers(includeIdList);

		// 根据实例删除流程候选人
		bpmTaskCandidateManager.delByInstList(includeIdList);
		// 删除任务
		bpmTaskManager.delByInstList(includeIdList);

		// 任务转办代理
		bpmTaskTurnManager.delByInstList(includeIdList);
		// 会签数据
		bpmSignDataManager.delByInstList(includeIdList);
		// 是否阅读
		bpmTaskReadManager.delByInstList(includeIdList);

		// 删除execution。
		if (BeanUtils.isNotEmpty(notIncludeBpmnIdList)){
			actExecutionManager.delByInstList(notIncludeBpmnIdList);
		}
		// 删除指定的流程变量。
		actTaskDao.delSpecVarsByInstList(includeBpmnIdList);
		// 删除流程候选人
		actTaskDao.delCandidateByInstList(includeBpmnIdList);
		// 删除关联流程任务
		actTaskDao.delByInstList(includeBpmnIdList);
		// 创建任务与流程进行关联。
		ActExecution actExecution = actExecutionManager.get(mainInstance.getBpmnInstId());
		// 创建ACT_RU_TASK任务与之关联。
		ActTask actTask = actTaskManager.createTask(actExecution, mainInstance, bpmNodeDef);
		// 更新流程实例状态。
		mainInstance.setStatus(ProcessInstanceStatus.STATUS_REVOKE_TOSTART.getKey());
		bpmProcessInstanceDao.update(mainInstance);
		// 更新主execution。
		updActExecution(actExecution, bpmNodeDef.getNodeId());

		// 更新节点状态
		updProStatus(instanceId, bpmNodeDef.getNodeId(), includeIdList);

		// 添加意见
		updOpinion(includeIdList, actTask, mainInstance, cause);
		// 退出堆栈
		bpmExeStackManager.popStartStack(mainInstance.getId(), "", BpmExeStack.HAND_MODE_NORMAL);

		// 删除堆栈关系
		bpmExeStackDao.removeBpmExeStackRelation(instanceId, "");
		// 删除堆栈
		String targetNodePath = bpmExeStackDao.getInitStack(instanceId).getNodePath();
		bpmExeStackDao.removeExeStackExceptParentId(instanceId, "0");

		// 发送通知 。
		notifyUsers(users, mainInstance, informType, cause);

		return resultMessage;
	}

	/**
	 * 撤回任务
	 * 
	 * @param instanceId
	 * @param informType
	 * @param cause
	 * @return
	 */
	@SuppressWarnings("unused")
	@Override
	public ResultMessage revokeTask(String instId, String informType, String cause)
	{
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);
		BpmExeStackExecutorManager bpmExeStackExecutorManager = AppUtil.getBean(BpmExeStackExecutorManager.class);
		BpmExeStackRelationDao relationDao = AppUtil.getBean(BpmExeStackRelationDao.class);
		BpmCheckOpinionDao opinionDao = AppUtil.getBean(BpmCheckOpinionDao.class);

		// 找到此流程实例的任务列表，如果有多个任务只要有只有在所有人未处理时才能撤回
		// 1.找出当前流程实例所在节点，当前节点如果是会签不充许撤回
		List<DefaultBpmTask> list = bpmTaskManager.getByInstId(instId);
		DefaultBpmTask runningTask = list.get(0);
		String prcoDefId = runningTask.getProcDefId();
		String nodeId = runningTask.getNodeId();
		String taskId = runningTask.getTaskId();
		List<BpmNodeDef> nodeDefs = bpmDefinitionAccessor.getStartNodes(prcoDefId);
		BpmNodeDef node = bpmDefinitionAccessor.getBpmNodeDef(prcoDefId, nodeId);
		NodeType type = node.getType();
		BpmProcessInstance instance = bpmProcessInstanceDao.get(instId);
		String status = instance.getStatus();
		// 流程状态。
		if (ProcessInstanceStatus.STATUS_REVOKE_TOSTART.getKey().equals(status) || ProcessInstanceStatus.STATUS_BACK_TOSTART.getKey().equals(status))
		{
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "流程已处于第一个节点!");
			return message;

		}
		// 验证任务是否已经在发起节点。
		boolean rtn = validTask(instance.getBpmnInstId(), nodeDefs.get(0));
		if (!rtn)
		{
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "任务已在发起节点,不能再撤销!");
			return message;
		}

		if (!type.getKey().equals(NodeType.USERTASK.getKey()))
		{
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "撤回失败，非用户任务节点不允许撤回");
			return message;
		}
		// 2.找到所在节点的前继节点
		List<BpmNodeDef> inList = node.getIncomeNodes();
		BpmExeStackExecutor stackExecutor = bpmExeStackExecutorManager.getByTaskId(taskId);
		BpmExeStackRelation relation = relationDao.getByStackId(stackExecutor.getStackId(), "to");
		if (!relation.getFromNodeType().equals("userTask") && !NodeType.EXCLUSIVEGATEWAY.getKey().equals(relation.getFromNodeType()))
		{
			// 是谁发过来的如果不是用户任务节点，不充许撤回
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "撤回失败，有网关节点不允许撤回");
			return message;
		}
		while(NodeType.EXCLUSIVEGATEWAY.getKey().equals(relation.getFromNodeType())){
			relation = relationDao.getByStackId(relation.getFromStackId(), "to");
		}

		// 4.判断前继节点的处理人是否为当前登录者
		List<DefaultBpmCheckOpinion> listOpinions = opinionDao.getByInstNodeIdAndAgree(instId,relation.getFromNodeId());
		while(BeanUtils.isEmpty(listOpinions) && BeanUtils.isNotEmpty(relation) ){
			relation = relationDao.getByStackId(relation.getFromStackId(), "to");
			if(BeanUtils.isNotEmpty(relation)){
				listOpinions = opinionDao.getByInstNodeIdAndAgree(instId,relation.getFromNodeId());
			}
		}
		boolean isCanRecall = false;
		for (DefaultBpmCheckOpinion defaultBpmCheckOpinion : listOpinions){
			if (defaultBpmCheckOpinion.getAuditor().equals(ContextUtil.getCurrentUser().getUserId())){
				isCanRecall = true;
				break;
			}
		}
		if (isCanRecall){
			BpmIdentity bpmIdentity = DefaultBpmIdentity.getIdentityByUserId(ContextUtil.getCurrentUser().getUserId(), ContextUtil.getCurrentUser().getFullname());
			// 调用驳回方式撤回
			DefaultTaskFinishCmd cmd = getCmdFromRecall(taskId, "reject", "撤回 " + cause, "normal", relation.getFromNodeId(), bpmIdentity);

			// 判断是否允许按流程图执行进行驳回
			List<BpmNodeDef> listBpmNodeDef = BpmStackRelationUtil.getHistoryListBpmNodeDef(instId, nodeId, "pre");
			List<BpmNodeDef> bpmExeStacksGoMapUserNode = new ArrayList<BpmNodeDef>();
			boolean isCanReject = false;
			List<BpmExeStackRelation> relationList= relationDao.getListByProcInstId(instId);
			for (BpmNodeDef itemNode : listBpmNodeDef){
				if (!itemNode.getType().equals(NodeType.USERTASK)) continue;
				
				boolean isHavePre = BpmStackRelationUtil.isHaveAndOrGateway(instId, node.getNodeId(), "pre",relationList);
				boolean isHaveAfter = BpmStackRelationUtil.isHaveAndOrGateway(instId, node.getNodeId(), "after",relationList);
				isCanReject = !(isHavePre && isHaveAfter) && relation.getFromNodeId().equals(itemNode.getNodeId());
				if (isCanReject) 	break;

			}
			if (!isCanReject){
				ResultMessage message = new ResultMessage(ResultMessage.FAIL, "撤回失败，当前节点状态下不允许撤回");
				return message;
			}
			boolean result = bpmTaskActionService.finishTask(cmd);
			DefaultBpmProcessInstance processInstance = bpmProcessInstanceDao.get(instId);
			List<IUser> listUsers = new ArrayList<IUser>();
			IUser user = userService.getUserById(runningTask.getOwnerId());
			listUsers.add(user);
			BpmProcessInstanceManagerImpl.notifyUsers(listUsers, instance, informType, cause);
			// 更新流程实例状态。
			processInstance.setStatus(ProcessInstanceStatus.STATUS_REVOKE.getKey());
			bpmProcessInstanceDao.update(processInstance);
			ResultMessage message = new ResultMessage(ResultMessage.SUCCESS, "撤回成功");
			return message;
		} 
		else{
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "撤回失败，下个节点任务已被处理，不可撤回！");
			return message;
		}

	}

	// 撤回命令
	private DefaultTaskFinishCmd getCmdFromRecall(String taskId, String actionName, String opinion, String backHandMode, String toNodeId, BpmIdentity bpmIdentity)
	{
		DefaultTaskFinishCmd cmd = new DefaultTaskFinishCmd();
		// 驳回到指定节点
		cmd.setDestination(toNodeId);
		cmd.setTaskId(taskId);
		cmd.setActionName(actionName);
		// 已办中撤消
		cmd.addTransitVars("IsDoneUnused", true);
		// 设置表单意见。
		cmd.setApprovalOpinion(opinion);
		cmd.setDataMode(ActionCmd.DATA_MODE_BO);
		// 设置流程驳回时跳转模式。
		cmd.addTransitVars(BpmConstants.BACK_HAND_MODE, backHandMode);
		// 设置目标节点映射----------------------------------------------------------------------------------------------------
		List<BpmIdentity> list = new ArrayList<BpmIdentity>();
		list.add(bpmIdentity);
		Map<String, List<BpmIdentity>> nodeIdentityMap = new HashMap<String, List<BpmIdentity>>();
		nodeIdentityMap.put(toNodeId, list);
		cmd.setBpmIdentities(nodeIdentityMap);
		return cmd;
	}

	/**
	 * 更新ACT_RU_EXECUTION表。
	 * 
	 * @param actExecution
	 *            void
	 */
	private void updActExecution(ActExecution actExecution, String currentNode)
	{
		actExecution.setActId(currentNode);
		actExecution.setIsActive((short) 1);

		actExecutionManager.update(actExecution);

	}

	/**
	 * 更新意见数据。
	 * 
	 * <pre>
	 * 	1.更新为审批的意见数据为撤销到发起人，修改意见为意见。
	 * 	2.增加意见。
	 * </pre>
	 * 
	 * void
	 */
	private void updOpinion(List<String> includeIdList, ActTask actTask, BpmProcessInstance mainInstance, String cause)
	{
		// 更新为审批的意见状态为撤销到发起人
		List<DefaultBpmCheckOpinion> opinionList = bpmCheckOpinionManager.getByInstIdsAndWait(includeIdList);
		long completeTime = System.currentTimeMillis();
		IUser user = ContextUtil.getCurrentUser();
		for (DefaultBpmCheckOpinion opinion : opinionList){
			opinion.setStatus(OpinionStatus.REVOKER_TO_START.getKey());
			long startTime = opinion.getCreateTime().getTime();
			long durms = completeTime - startTime;
			opinion.setDurMs(durms);
			opinion.setAuditor(user.getUserId());
			opinion.setAuditorName(user.getFullname());
			opinion.setOpinion(cause);
			opinion.setCompleteTime(new Date());
			bpmCheckOpinionManager.update(opinion);
		}

		DefaultBpmCheckOpinion checkOpinion = BpmCheckOpinionUtil.buildBpmCheckOpinion(actTask, mainInstance.getParentInstId(), mainInstance.getId());

		bpmCheckOpinionManager.create(checkOpinion);

	}

	/**
	 * 更新节点状态。
	 * 
	 * @param instanceId
	 * @param nodeId
	 * @param includeIdList
	 *            void
	 */
	private void updProStatus(String instanceId, String nodeId, List<String> includeIdList){

		bpmProStatusManager.updStatusByInstList(includeIdList, NodeStatus.RECOVER_TO_START);

		DefaultBpmProStatus proStatus = bpmProStatusManager.getByInstNodeId(instanceId, nodeId);
		proStatus.setStatus(NodeStatus.PENDING.getKey());
		bpmProStatusManager.update(proStatus);
	}

	/**
	 * 通知相应的人员。
	 * 
	 * @param recievers
	 * @param instance
	 * @param informType
	 * @param cause
	 *            void
	 */
	public static void notifyUsers(List<IUser> recievers, BpmProcessInstance instance, String informType, String cause)
	{
		if (BeanUtils.isEmpty(recievers))
			return;
		Map<String, Object> vars = new HashMap<String, Object>();

		vars.put(TemplateConstants.TEMP_VAR.TASK_SUBJECT, instance.getSubject());
		vars.put(TemplateConstants.TEMP_VAR.CAUSE, cause);

		try{
			MessageUtil.sendMsg(TemplateConstants.TYPE_KEY.BPMN_RECOVER, informType, recievers, vars);
		} 
		catch (Exception e){
			// 记录日志
			LOGGER.debug(e.getMessage());
		}
	}

	/**
	 * 获取通知任务执行人。
	 * 
	 * <pre>
	 * 获取任务的执行人，获取任务的候选人。
	 * </pre>
	 * 
	 * @param includeIdList
	 *            流程实例ID列表。
	 * @return List&lt;User>
	 */
	private List<IUser> getNotifyUsers(List<String> includeIdList)
	{
		Set<IUser> userSet = new HashSet<IUser>();
		List<DefaultBpmTask> bpmTasks = bpmTaskManager.getByInstList(includeIdList);

		for (DefaultBpmTask bpmTask : bpmTasks){
			String assigneeId = bpmTask.getAssigneeId();
			if (StringUtil.isNotZeroEmpty(assigneeId)){
				userSet.add(userServiceImpl.getUserById(assigneeId));
			}
		}

		List<DefaultBpmTaskCandidate> candidates = bpmTaskCandidateManager.getByInstList(includeIdList);
		for (DefaultBpmTaskCandidate candidate : candidates){
			String executorId = candidate.getExecutor();
			if (BpmIdentity.TYPE_USER.equals(candidate.getType())){
				userSet.add(userServiceImpl.getUserById(executorId));
			} 
			else{
				userSet.addAll(userServiceImpl.getUserListByGroup(candidate.getType(), executorId));
			}
		}
		// 为空的情况直接返回。
		if (BeanUtils.isEmpty(userSet)) return null;
		List<IUser> users = new ArrayList<IUser>();
		users.addAll(userSet);

		return users;
	}

	private List<String> getIdList(List<DefaultBpmProcessInstance> instList, String instanceId, boolean includeSelf, boolean isBpmnId)
	{
		List<String> includeIdList = new ArrayList<String>();
		for (DefaultBpmProcessInstance instance : instList){
			if (!includeSelf && instanceId.equals(instance.getId())) continue;

			if (isBpmnId){
				includeIdList.add(instance.getBpmnInstId());
			} 
			else{
				includeIdList.add(instanceId);
			}

		}
		return includeIdList;
	}

	private DefaultBpmProcessInstance getMainInstance(List<DefaultBpmProcessInstance> instList, String instanceId){
		for (DefaultBpmProcessInstance instance : instList){
			if (instance.getId().equals(instanceId)){
				return instance;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmProcessInstance> queryList(QueryFilter queryFilter){
		//增加流程分管授权查询判断
		IUser user = ContextUtil.getCurrentUser();
		String userId = user.getUserId();			
		Map<String,JSONObject> authorizeRightMap = null;
		
		boolean isAdmin=user.isAdmin();
		
		queryFilter.addParamsFilter("isAdmin", isAdmin?1:0);
		
		if(!isAdmin){
			//获得流程分管授权与用户相关的信息
			Map<String,Object> actRightMap= bpmDefAuthorizeManager.getActRightByUserId(userId, BPMDEFAUTHORIZE_RIGHT_TYPE.INSTANCE,true,true);
			//获得流程分管授权与用户相关的信息集合的流程KEY
			String defKeys = (String) actRightMap.get("defKeys");
			if(StringUtil.isNotEmpty(defKeys)){
				queryFilter.addParamsFilter("defKeys", defKeys);
			}
			//获得流程分管授权与用户相关的信息集合的流程权限内容
			authorizeRightMap = (Map<String,JSONObject>) actRightMap.get("authorizeRightMap");
		}
		
		//查询列表
		PageList<DefaultBpmProcessInstance> bpmProcessInstanceList=(PageList<DefaultBpmProcessInstance>)query(queryFilter);
		
		//把前面获得的流程分管授权的权限内容设置到流程管理列表
		for (DefaultBpmProcessInstance instance : bpmProcessInstanceList){
			JSONObject rightJson=null;
			String defKey=instance.getProcDefKey();
			if(instance.getDuration()== null || instance.getDuration()<1){
				instance.setDuration(new Date().getTime()-instance.getCreateTime().getTime());
			}
			if(authorizeRightMap==null){
				rightJson=AuthorizeRight.getAdminRight();
			}
			else{
				rightJson=authorizeRightMap.get(defKey);
			}
			instance.setAuthorizeRight(rightJson);
		}
		
		return bpmProcessInstanceList;
	}

	

	@Override
	public ResultMessage canRevokeToStart(String instanceId)
	{

		BpmProcessInstance instance = bpmProcessInstanceDao.get(instanceId);
		String defId = instance.getProcDefId();
		List<BpmNodeDef> nodeDefs = bpmDefinitionAccessor.getStartNodes(defId);

		ResultMessage message = new ResultMessage();

		IUser user = ContextUtil.getCurrentUser();
		if (user == null){
			message.setResult(ResultMessage.ERROR);
			message.setMessage("请先设置当前执行人!");
			return message;
		}

		if (nodeDefs.size() > 1){
			message.setResult(ResultMessage.ERROR);
			message.setMessage("发起节点后有多个节点!");
			return message;
		}

		String status = instance.getStatus();
		// 流程状态。
		if (ProcessInstanceStatus.STATUS_REVOKE_TOSTART.getKey().equals(status) 
				|| ProcessInstanceStatus.STATUS_BACK_TOSTART.getKey().equals(status)){
			message.setResult(ResultMessage.ERROR);
			message.setMessage("流程已处于第一个节点!");
			return message;
		}

		if (!user.getUserId().equals(instance.getCreateBy())){
			message.setResult(ResultMessage.ERROR);
			message.setMessage("当前执行人和流程发起人不是同一个人!");
			return message;
		}
		// 验证任务是否已经在发起节点。
		boolean rtn = validTask(instance.getBpmnInstId(), nodeDefs.get(0));
		if (!rtn){
			message.setResult(ResultMessage.ERROR);
			message.setMessage("任务已在发起节点,不能再撤销!");
			return message;
		}

		// 添加流程节点。
		message.addVariable("bpmNodeDef", nodeDefs.get(0));
		message.setResult(ResultMessage.SUCCESS);
		return message;
	}

	private boolean validTask(String bpmnInstId, BpmNodeDef nodeDef){
		String nodeId = nodeDef.getNodeId();
		List<ActTask> list = actTaskDao.getByInstId(bpmnInstId);

		for (ActTask task : list){
			if (nodeId.equals(task.getTaskDefKey())){
				return false;
			}
		}
		return true;
	}

	@Override
	public ResultMessage canRevoke(String instanceId, String nodeId){
		ResultMessage message = ResultMessage.getSuccess();

		BpmProcessInstance processInstance = bpmProcessInstanceDao.get(instanceId);
		// 判断实例状态。
		message = checkInstance(processInstance);

		if (message.getResult() == ResultMessage.ERROR){
			return message;
		}
		

		return message;
	}

	

	private ResultMessage checkInstance(BpmProcessInstance processInstance){
		ResultMessage message = ResultMessage.getSuccess();
		String status = processInstance.getStatus();
		if (ProcessInstanceStatus.STATUS_RUNNING.getKey().equals(status))
		{
			return message;
		}
		message.setResult(ResultMessage.ERROR);
		String msg = "";
		if (ProcessInstanceStatus.STATUS_BACK.getKey().equals(status)){
			msg = "流程被驳回";
		} 
		else if (ProcessInstanceStatus.STATUS_BACK_TOSTART.getKey().equals(status)){
			msg = "流程被驳回到发起人";
		} 
		else if (ProcessInstanceStatus.STATUS_END.getKey().equals(status)){
			msg = "流程实例已结束";
		} else if (ProcessInstanceStatus.STATUS_END.getKey().equals(status))
		{
			msg = "流程实例被人工终止";
		} else if (ProcessInstanceStatus.STATUS_DRAFT.getKey().equals(status))
		{
			msg = "流程实例为草稿状态";
		} else if (ProcessInstanceStatus.STATUS_REVOKE.getKey().equals(status))
		{
			msg = "流程实例为撤销状态";
		} else if (ProcessInstanceStatus.STATUS_REVOKE_TOSTART.getKey().equals(status))
		{
			msg = "流程实例为撤销状态";
		}
		message.setMessage(msg);
		return message;
	}

	@Override
	public BpmProcessInstance getTopBpmProcessInstance(String proceInstId)
	{
		BpmProcessInstance instance = get(proceInstId);
		
		return  getTopBpmProcessInstance(instance);
	}

	@Override
	public List<DefaultBpmProcessInstance> getListByDefId(String defId)
	{
		return bpmProcessInstanceDao.getListByDefId(defId);
	}

	@Override
	public BpmProcessInstance getTopBpmProcessInstance(BpmProcessInstance instance)
	{
		while (StringUtil.isNotZeroEmpty(instance.getParentInstId())){
			instance = bpmProcessInstanceDao.get(instance.getParentInstId());
		}
		return instance;
	}

	@Override
	public List<BpmProcessInstance> getBpmProcessByParentIdAndSuperNodeId(String parentInstId, String superNodeId) {
		return bpmProcessInstanceDao.getBpmnByParentIdAndSuperNodeId(parentInstId,superNodeId);
	}
	
	@Override
	public List<BpmProcessInstance> getHiBpmProcessByParentIdAndSuperNodeId(String parentInstId, String superNodeId) {
		return bpmProcessInstanceDao.getHiBpmnByParentIdAndSuperNodeId(parentInstId,superNodeId);
	}

	@Override
	public DefaultBpmProcessInstance getByBusinessKey(String businessKey) {
		return bpmProcessInstanceDao.getByBusinessKey(businessKey);
	}
	
}
