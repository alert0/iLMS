package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.MultiInstanceType;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.ProcessInstanceStatus;
import com.hotent.bpmx.api.constant.TaskType;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.NoExecutorModel;
import com.hotent.bpmx.api.event.NotifyTaskModel;
import com.hotent.bpmx.api.event.TaskNotifyEvent;
import com.hotent.bpmx.api.helper.identity.BpmIdentityConverter;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.BpmTaskTurn;
import com.hotent.bpmx.api.service.BpmAgentService;
import com.hotent.bpmx.api.service.BpmIdentityService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.core.util.MessageUtil;
import com.hotent.bpmx.natapi.inst.NatProInstanceService;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.dao.BpmTaskDao;
import com.hotent.bpmx.persistence.manager.ActExecutionManager;
import com.hotent.bpmx.persistence.manager.ActTaskManager;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmDefAuthorizeManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmSignDataManager;
import com.hotent.bpmx.persistence.manager.BpmTaskCandidateManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.model.ActTask;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.bpmx.persistence.model.BpmSignData;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskCandidate;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.calendar.ICalendarService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

@Service("bpmTaskManager")
public class BpmTaskManagerImpl extends AbstractManagerImpl<String, DefaultBpmTask> implements BpmTaskManager{
	@Resource
	BpmTaskDao bpmTaskDao;
	@Resource
	BpmTaskCandidateManager bpmTaskCandidateManager;
	
	
	@Resource
	IUserGroupService defaultUserGroupService;
	
	@Resource
	IUserService userServiceImpl;
	@Resource
	ActTaskManager actTaskManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmSignDataManager bpmSignDataManager;
	@Resource
	NatProInstanceService natProInstanceService;
	@Resource
	BpmIdentityExtractService bpmIdentityExtractService;
	@Resource
	BpmAgentService bpmAgentService;  
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager ;
	@Resource
	BpmDefAuthorizeManager bpmDefAuthorizeManager;
	@Resource
	BpmIdentityService bpmIdentityService;
	@Resource
	NatTaskService natTaskService;
	@Resource
	ActExecutionManager actExecutionManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmProStatusManager bpmProStatusManager;
	@Resource 
	ICalendarService iCalendarService;
	
 
	
	private Map<String,Object> actRightMap;
	
	@Override
	protected Dao<String, DefaultBpmTask> getDao() {
		return bpmTaskDao;
	}
	
	@Override
	public DefaultBpmTask getByRelateTaskId(String relateTaskId) {
		return bpmTaskDao.getByRelateTaskId(relateTaskId);
	}

	@Override
	public void delByRelateTaskId(String relateTaskId) {
		bpmTaskCandidateManager.removeByTaskId(relateTaskId);
		bpmTaskDao.removeByTaskId(relateTaskId);
	}
	
	@Override
	public List<DefaultBpmTask> getByUserId(String userId) {
		//IUser user = userService.getById(userId);
		List<IGroup> list= defaultUserGroupService.getGroupsByUserId(userId);
		
		Map<String,String> groupMap=convertGroupList(list);
		
		return bpmTaskDao.getByUserId(userId,groupMap);
	}
	
	private Map<String,String> convertGroupList(List<IGroup> list){
		Map<String,String> map=new HashMap<String, String>();
		if(BeanUtils.isEmpty(list)) return map;
		for(IGroup group:list){
			String type=group.getGroupType();
			if(map.containsKey(type)){
				String groupId=map.get(type);
				groupId+=",'" + group.getGroupId() +"'";
				map.put(type,groupId);
			}
			else{
				map.put(type,"'"+ group.getGroupId()  +"'");
			}
		}
		return map;
	}
	
	@Override
	public List<DefaultBpmTask> getByUserId(String userId,
			QueryFilter queryFilter) {
		List<IGroup> list= defaultUserGroupService.getGroupsByUserId(userId);
		Map<String,String> groupMap=convertGroupList(list);
		
		return bpmTaskDao.getByUserId(userId,groupMap, queryFilter);
	}
	
	@Override
	public List<DefaultBpmTask> getByUserId(String userId, Page page) {
		List<IGroup> list= defaultUserGroupService.getGroupsByUserId(userId);
		
		Map<String,String> groupMap=convertGroupList(list);
		return bpmTaskDao.getByUserId(userId,groupMap, page);
	}

	@Override
	public void assignUser(BpmDelegateTask delegateTask, List<BpmIdentity> identityList) {

		//修改任务执行人。
		DefaultBpmTask bpmTask= getByRelateTaskId(delegateTask.getId());
		//设置当前执行人是否为空。
		bpmTask.setIdentityEmpty(BeanUtils.isEmpty(identityList));
		
		if(BeanUtils.isEmpty(identityList)){
			ContextThreadUtil.addTask(bpmTask);
			//没有执行人时抛出事件。
			NoExecutorModel noExcutor=NoExecutorModel.getNoExecutorModel(bpmTask.getTaskId(), bpmTask.getBpmnInstId(), bpmTask.getSubject(), bpmTask.getNodeId(), bpmTask.getName(), bpmTask.getBpmnDefId());
			BpmUtil.publishNoExecutorEvent(noExcutor);
			return;
		}
		//设置任务执行人列表。
		bpmTask.setIdentityList(identityList);
		
		
		Map<String,Object> vars=delegateTask.getVariables();
		
		ActionCmd cmd=ContextThreadUtil.getActionCmd();
		
		ActionType actionType=ActionType.APPROVE;
		String actionName="start";
		String opinion="";
		if(cmd instanceof DefaultTaskFinishCmd){
			DefaultTaskFinishCmd finishCmd=(DefaultTaskFinishCmd)cmd;
			actionType=finishCmd.getActionType();
			actionName=finishCmd.getActionName();
			opinion=finishCmd.getApprovalOpinion();
		}
		//将用户抽取出来。
		List<IUser> userList= bpmIdentityExtractService.extractUser(identityList);
		
		NotifyTaskModel model=NotifyTaskModel.getNotifyModel(bpmTask.getTaskId(), bpmTask.getBpmnInstId(), bpmTask.getProcInstId(),
				bpmTask.getSubject(), bpmTask.getNodeId(), bpmTask.getName(), bpmTask.getBpmnDefId(),
				vars, userList, actionType, actionName, opinion);
		
		if(identityList.size()==1){
			BpmIdentity identity=identityList.get(0);
			if(BpmIdentity.TYPE_USER.equals( identity.getType())){
				handTask(bpmTask,delegateTask,vars,model);
			}
			else{
				//加到候选人
				bpmTaskCandidateManager.addCandidate(bpmTask, identityList);
			}
		}
		else{		
			bpmTaskCandidateManager.addCandidate(bpmTask, identityList);
		}
		//添加流程任务对象到流程的线程变量。
		ContextThreadUtil.addTask(bpmTask);
		
		//当用户组的人员为空时抛出事件。
		publishIdentityListWhenEmpty(bpmTask,identityList,userList);
		//撤销时不通知执行人
		//跳过任务时不做通知
		BpmUtil.setTaskSkip(bpmTask);
		if(!ActionType.RECOVER.equals(actionType) && !bpmTask.getSkipResult().isSkipTask()){
			//发布通知事件。
			publishNotifyEvent(model);
		}
		
	}
	

	/**
	 * 发布任务通知事件。
	 * @param model 
	 * void
	 */
	private   void publishNotifyEvent(NotifyTaskModel model){
		if(model.getActionType()==ActionType.RECOVER) return;
		TaskNotifyEvent ev=new TaskNotifyEvent(model);
		AppUtil.publishEvent(ev);
	}
	
	/**
	 * 处理任务。
	 * 同时处理代理产生。
	 * @param bpmTask
	 * @param delegateTask
	 * @param identity
	 * @param vars
	 * @param model 
	 * void
	 */
	private void handTask(DefaultBpmTask bpmTask,BpmDelegateTask delegateTask,
			Map<String,Object> vars,NotifyTaskModel model){
		BpmIdentity identity= bpmTask.getIdentityList().get(0);
		//获取任务代理人。
		IUser agent=getAgent(identity,delegateTask,vars);
		
		//未获取代理人
		if(agent==null){
			bpmTask.setAssigneeId(identity.getId());
			delegateTask.setAssignee(identity.getId());
		}
		else{
			IUser delegator=userServiceImpl.getUserById(identity.getId());
			
			bpmTask.setAssigneeId(agent.getUserId());
			bpmTask.setStatus(TaskType.AGENT.name());
			delegateTask.setAssignee(agent.getUserId());
			//添加代理 
			bpmTaskTurnManager.add(bpmTask, delegator,agent,"代理任务默认转办",BpmTaskTurn.TYPE_AGENT);
			model.setAgent(true);
			model.setAgent(agent);
			model.setDelegator(delegator);
		}
		
		delegateTask.setOwner(identity.getId());
		bpmTask.setOwnerId(identity.getId());
		
		// 会签串行任务状态处理
		MultiInstanceType instanceType = delegateTask.multiInstanceType();
		if (BeanUtils.isNotEmpty(instanceType) && MultiInstanceType.SEQUENTIAL.equals(instanceType)) {// 会签串行，判断是否为加签任务
			BpmSignData signData = bpmSignDataManager.getByInstanIdAndUserId(bpmTask.getProcInstId(),
					bpmTask.getOwnerId());
			if (BeanUtils.isNotEmpty(signData) && BpmSignData.TYPE_ADDSIGN.equals(signData.getType())) {
				bpmTask.setStatus(TaskType.ADDSIGN.getKey());
			}
		}
		
		update(bpmTask);
	}
	
	/**
	 * 获取代理人。
	 * @param cmd
	 * @param identity
	 * @param delegateTask
	 * @param vars
	 * @return  User
	 */
	private IUser getAgent(BpmIdentity identity,BpmDelegateTask delegateTask,Map<String,Object> vars){
		ActionCmd cmd= ContextThreadUtil.getActionCmd();
		IUser agent=null;
		//只有审批产生的任务才计算代理，驳回和撤销的任务不再代理。
		if(cmd instanceof DefaultTaskFinishCmd){
			DefaultTaskFinishCmd finishCmd=(DefaultTaskFinishCmd)cmd;
			if(ActionType.APPROVE.equals(  finishCmd.getActionType())){
				//获取任务代理人。
				agent= bpmAgentService.getAgent(identity.getId(), delegateTask, vars);
			}
		}
		else{
			agent= bpmAgentService.getAgent(identity.getId(), delegateTask, vars);
		}
		return agent;
	}
	
	/**
	 * 当用户组的人员为空时抛出事件。
	 * @param bpmTask
	 * @param list 
	 * void
	 */
	private void publishIdentityListWhenEmpty(BpmTask bpmTask, List<BpmIdentity> list,List<IUser> users){
		 if(BeanUtils.isNotEmpty(users)) return;
		 NoExecutorModel noExcutor=NoExecutorModel.getNoExecutorModel(bpmTask.getTaskId(), bpmTask.getBpmnInstId(), bpmTask.getSubject(), bpmTask.getNodeId(), bpmTask.getName(), bpmTask.getBpmnDefId());
		 noExcutor.setIdentifyList(list);
		 BpmUtil.publishNoExecutorEvent(noExcutor);
	}


	@Override
	public List<DefaultBpmTask> getByInstId(String instId) {
		return bpmTaskDao.getByInstId(instId);
	}

	@Override
	public List<DefaultBpmTask> getByExeIdAndNodeId(String instId,
			String nodeId) {
		return bpmTaskDao.getByExeIdAndNodeId(instId, nodeId);
	}

	@Override
	public List<DefaultBpmTask> getByInstUser(String instId, String userId) {
		return bpmTaskDao.getByInstUser(instId,userId);
	}

	/**
	 * 根据任务获取所有人。
	 */
	@Override
	public List<BpmIdentity> getIdentitysByTaskId(String taskId) {
		return Collections.emptyList();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultMessage addSignTask(String taskId,String[] aryUsers) {
		if(aryUsers==null || aryUsers.length==0) return  ResultMessage.getFail("没有指定执行人!" );
		
		BpmTask bpmTask=bpmTaskDao.get(taskId);
		String bpmnTaskId=bpmTask.getTaskId();
		
		ActTask actTask=actTaskManager.get(bpmnTaskId);
		
		BpmNodeDef nodeDef= bpmDefinitionAccessor.getBpmNodeDef(bpmTask.getProcDefId(), actTask.getTaskDefKey());
		
		if(!(nodeDef instanceof SignNodeDef)) return ResultMessage.getFail("当前节点不是会签节点!" );
		
		SignNodeDef signNodeDef=(SignNodeDef)nodeDef;
		
		String actInstId=actTask.getProcInstId();
		String executionId=actTask.getExecutionId();
		String nodeId=nodeDef.getNodeId();
		String instId = bpmTask.getProcInstId();
		
		
		List<BpmSignData> signDataList= bpmSignDataManager.getVoteByExecuteNode(bpmTask.getExecId(), nodeId,1);
		if(BeanUtils.isEmpty(signDataList)) return ResultMessage.getFail("没有会签数据!" );
		
		
		List<BpmIdentity> users=getCanAddUsers(signDataList,aryUsers);
		if(BeanUtils.isEmpty(users)) return ResultMessage.getFail("指定的人员已存在!" );
		
		int userAmount=users.size();
		
		Integer nrOfInstances=(Integer)natProInstanceService.getVariable(executionId, BpmConstants.NUMBER_OF_INSTANCES);
		
		if(nrOfInstances!=null){
			natProInstanceService.setVariable(executionId, BpmConstants.NUMBER_OF_INSTANCES, nrOfInstances + userAmount);
		}
		
		List<Map<String, String>> taskIds = new ArrayList<Map<String,String>>();
		//并行
		if(signNodeDef.isParallel()){
			Integer loopCounter=nrOfInstances-1;
			//添加活动的线程个数
			Integer nrOfActiveInstances=(Integer)natProInstanceService.getVariable(executionId, BpmConstants.NUMBER_OF_ACTIVE_INSTANCES);
			natProInstanceService.setVariable(executionId, BpmConstants.NUMBER_OF_ACTIVE_INSTANCES, nrOfActiveInstances + userAmount);
			for(int i=0;i<userAmount;i++){
				BpmIdentity bpmIdentity=users.get(i);
				//创建流程引擎任务。
				ActTask newActTask= actTaskManager.createTask(taskId, bpmIdentity.getId());
				//添加审核意见
				DefaultBpmTask signBpmTask=bpmTaskDao.get(newActTask.getId());
				signBpmTask.setStatus(TaskType.ADDSIGN.getKey());
				signBpmTask.setExecId(bpmTask.getExecId());
				bpmTaskDao.update(signBpmTask);
				this.addSignCheckOpinion(signBpmTask,OpinionStatus.AWAITING_CHECK,bpmIdentity.getId());
				Map<String,String> map = new HashMap<String, String>();
				map.put("userId", bpmIdentity.getId());
				map.put("taskId", signBpmTask.getTaskId());
				taskIds.add(map);
				
				String newExecutionId= newActTask.getExecutionId();
				Integer index= (Integer)(loopCounter + i +1);
				
				natProInstanceService.setVariableLocal(newExecutionId,BpmConstants.NUMBER_OF_LOOPCOUNTER,index);
				natProInstanceService.setVariableLocal(newExecutionId,BpmConstants.ASIGNEE,bpmIdentity );

				bpmSignDataManager.addSignData(bpmTask.getProcDefId(), instId, actInstId,bpmTask.getExecId(), nodeId, signBpmTask.getTaskId(),
						bpmIdentity.getId(), bpmIdentity.getName(),index.shortValue(),BpmSignData.TYPE_ADDSIGN);
			}
		}
		//串行。
		else{
			String varName=BpmConstants.SIGN_USERIDS + nodeId;
			List<BpmIdentity> addList=new ArrayList<BpmIdentity>();
			
			for(int i=0;i<userAmount;i++){
				Integer index=nrOfInstances+i;
				BpmIdentity bpmIdentity=users.get(i);
				bpmSignDataManager.addSignData(bpmTask.getProcDefId(), instId, actInstId,bpmTask.getExecId(), nodeId, bpmTask.getTaskId(),
						bpmIdentity.getId(), bpmIdentity.getName(),index.shortValue(),BpmSignData.TYPE_ADDSIGN);
				addList.add(bpmIdentity);
				Map<String,String> map = new HashMap<String, String>();
				map.put("userId", bpmIdentity.getId());
				map.put("taskId", bpmTask.getTaskId());
				taskIds.add(map);
			}
			//修改串行的流程变量。
			List<BpmIdentity> list=(List<BpmIdentity>)natProInstanceService.getVariable(executionId, varName);
			list.addAll(addList);
			natProInstanceService.setVariable(executionId, varName, list);
		}
		
		ResultMessage rtnMessage= ResultMessage.getSuccess("加签成功!");
		rtnMessage.addVariable("actTask", actTask);
		rtnMessage.addVariable("users", users);
		rtnMessage.addVariable("taskIds", taskIds);
		
		
		return rtnMessage;
	}
	
 
	/**
	 * 添加加签任务意见。
	 * 
	 * @param bpmTask
	 * @param opinionStatus
	 *            void
	 * @param toUser
	 *   增加任务的那个人
	 */
	private void addSignCheckOpinion(DefaultBpmTask bpmTask, OpinionStatus opinionStatus, String toUser)
	{

		// 如果是流转中的人添加意见，则办理人为那个人
		IUser user = BpmUtil.getUser(toUser);
		List<BpmIdentity> identityList =new ArrayList<BpmIdentity>();
		BpmIdentity bpmIdentity=new DefaultBpmIdentity();
		bpmIdentity.setType(IdentityType.USER);
		bpmIdentity.setId(user.getUserId());
		bpmIdentity.setName(user.getFullname());
		identityList.add(bpmIdentity);
		
		String bpmnInstId = bpmTask.getBpmnInstId();
		DefaultBpmCheckOpinion checkOpinion = new DefaultBpmCheckOpinion();
		checkOpinion.setId(UniqueIdUtil.getSuid());
		checkOpinion.setProcDefId(bpmTask.getBpmnDefId());
		checkOpinion.setProcInstId(bpmTask.getProcInstId());
		checkOpinion.setTaskId(bpmTask.getTaskId());
		checkOpinion.setTaskKey(bpmTask.getNodeId());
		checkOpinion.setTaskName(bpmTask.getName());
		checkOpinion.setStatus(opinionStatus.getKey());
		checkOpinion.setCreateTime(new Date());
		checkOpinion.setQualfieds(BpmCheckOpinionUtil.getIdentityIds(identityList));
		checkOpinion.setQualfiedNames(user.getFullname());
		checkOpinion.setAuditor(user.getUserId());
		checkOpinion.setAuditorName(user.getFullname());

		bpmCheckOpinionManager.create(checkOpinion);
	}
	

	/**
	 * 获取可以进行补签的人员。
	 * @param list
	 * @param aryUsers
	 * @return 
	 * List&lt;BpmIdentity>
	 */
	private List<BpmIdentity> getCanAddUsers(List<BpmSignData> list,String[] aryUsers){
		List<BpmIdentity> rtnList=new ArrayList<BpmIdentity>();
		List<String> userList=new ArrayList<String>();
		
		for(BpmSignData signData:list){
			userList.add(signData.getQualifiedId());
		}
		
		for(String userId:aryUsers){
			if(userList.contains(userId)) continue;
			IUser user= userServiceImpl.getUserById(userId);
			BpmIdentity identity=DefaultBpmIdentity.getIdentityByUserId(userId, user.getFullname());
			rtnList.add(identity);
		}
		return rtnList;
	}
	
	 /**
	  * 按用户ID，实例Id 用户组列表查找任务
	  * @param bpmnInstId
	  * @param userId
	  * @param groupList
	  * @return
	  */
	 public List<DefaultBpmTask> getByBpmInstIdUserIdGroupList(String bpmnInstId,String userId,List<IGroup> groupList){
		 return bpmTaskDao.getByBpmInstIdUserIdGroupList(bpmnInstId, userId, groupList);
	 }

	@Override
	public void lockTask(String taskId, String userId) {
		bpmTaskDao.updateAssigneeOwnerId(taskId, userId, userId);
	}

	@Override
	public void unLockTask(String taskId) {
		//设置其值为0，即表示为空，用0值方便数据库索引建立及提交查询速度
		bpmTaskDao.updateAssigneeOwnerId(taskId, BpmConstants.EmptyUser,  BpmConstants.EmptyUser);
	}
	
	@Override
	public void assignTask(String taskId, String assigneeId) {
		//修改执行人发布事件。
		DefaultBpmTask bpmTask=bpmTaskDao.get(taskId);
		bpmTaskDao.updateAssigneeById(taskId, assigneeId);
		natTaskService.setAssignee(bpmTask.getTaskId(), assigneeId);
		
	}

	@Override
	public void delByInstList(List<String> instList) {
		bpmTaskDao.delByInstList(instList);
	}

	@Override
	public void delByParentId(String parentId) {
		bpmTaskDao.delByParentId(parentId);
	}

	@Override
	public List<DefaultBpmTask> getChildsByTaskId(String taskId) {
		List<DefaultBpmTask> list=bpmTaskDao.getByParentId(taskId);
		List<DefaultBpmTask> rtnList=new ArrayList<DefaultBpmTask>();
		for(DefaultBpmTask task:list){
			getByParentId(task,rtnList);
		}
		return rtnList;
	}
	
	private void getByParentId(DefaultBpmTask task,List<DefaultBpmTask> rtnList){
		rtnList.add(task);
		List<DefaultBpmTask> list=bpmTaskDao.getByParentId(task.getId());
		if(BeanUtils.isEmpty(list)) return;
		
		for(DefaultBpmTask tmp:list){
			getByParentId(tmp,rtnList);
		}
	}

	

	
	@Override
	public void createTask(BpmProcessInstance instance) {
		ActTask actTask=new ActTask();
		actTask.setId(UniqueIdUtil.getSuid());
		actTask.setRev(1);
		actTask.setExecutionId(instance.getBpmnInstId());
		actTask.setProcInstId(instance.getBpmnInstId());
		
		actTask.setAssignee(instance.getCreateBy());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmTask> queryList(QueryFilter queryFilter) {
		//增加分管授权查询判断
		IUser user = ContextUtil.getCurrentUser();
		String userId = user.getUserId();	
		
		boolean isAdmin=user.isAdmin();
		
		queryFilter.addParamsFilter("isAdmin", isAdmin?1:0);
		
		if(!isAdmin){
			actRightMap = bpmDefAuthorizeManager.getActRightByUserId(userId,BPMDEFAUTHORIZE_RIGHT_TYPE.TASK,true,true);
			//获得分管授权与用户相关的信息集合的流程KEY
			String defKeys = (String) actRightMap.get("defKeys");
			if(StringUtil.isNotEmpty(defKeys)){
				queryFilter.addParamsFilter("defKeys", defKeys);
			}
		}
		
		//查询列表
		List<DefaultBpmTask> list=  this.query(queryFilter);
	
	
		return convertInfo(list);
	}
	
	

	
	public List<DefaultBpmTask>  convertInfo(List<DefaultBpmTask> list){
		if(list == null) return list;
		
		for (DefaultBpmTask bpmTask  : list){
			//处理用户的数据
			if(StringUtil.isNotZeroEmpty(bpmTask.getOwnerId()))
				bpmTask.setOwnerName(getUserFullName(bpmTask.getOwnerId()));
			//处理执行人用户的数据
			if(StringUtil.isNotZeroEmpty(bpmTask.getAssigneeId()))
				bpmTask.setAssigneeName(getUserFullName(bpmTask.getAssigneeId()));
			//处理用户的数据
			if(StringUtil.isNotZeroEmpty(bpmTask.getCreatorId()))
				bpmTask.setCreator(getUserFullName(bpmTask.getCreatorId()));
			
			List<BpmIdentity> identityList = bpmIdentityService.queryListByBpmTask(bpmTask);
			bpmTask.setIdentityList(identityList);
			
			
			// 计算已用时间
			if(bpmTask.getDueTaskTime() > 0){
				int dueUseTaskTime = -1;
				if("caltime".equals(bpmTask.getDueDateType())){
					// getSecondDiff 秒
					dueUseTaskTime = TimeUtil.getSecondDiff(new Date(), bpmTask.getCreateTime())/60;
				}else{
					List<IUser> extractUser = bpmIdentityExtractService.extractUser(identityList);
					if(BeanUtils.isNotEmpty(extractUser) && extractUser.size()==1){
						IUser iUser = extractUser.get(0);
						// getWorkTimeByUser 毫秒
						dueUseTaskTime =(int) (iCalendarService.getWorkTimeByUser(iUser.getUserId(), bpmTask.getCreateTime(), new Date())/60000);
					}
				}
				bpmTask.setDueUseTaskTime(dueUseTaskTime);
			}
			
		}
		 return list;
	}
	
	
	private String getUserFullName(String userId) {
		IUser user =  userServiceImpl.getUserById(userId);
		if(BeanUtils.isNotEmpty(user))
			return user.getFullname();
		return null;
	}

	@Override
	public List<DefaultBpmTask> getReminderTask() {
		return bpmTaskDao.getReminderTask();
	}

	/**
	  * 根据任务ID终止流程。
	  * <pre>
	  * 1.根据任务ID查询到BPM_TASK记录。
	  * 2.发送通知消息，通知相关人员。	
	  * 	如果传入了通知类型，那么就是用传入的通知类型，如果没有则获取节点的通知类型。
	  * 3.删除bpm_task_candidate对应记录。
	  * 4.删除BPM_TASK记录。
	  * 5.删除act_ru_identitylink记录。
	  * 6.删除act_ru_task记录。
	  * 7.删除act_ru_execution的记录.
	  * 	这里需要查询是否为外部子流程。如果为外部子流程，则需要删除主流程的Execution信息。
	  * 8.标记bpm_pro_inst，bpm_pro_inst_hi为人工终止。
	  * 9.在bpm_check_opinion 将对应的状态更新为人工终止。
	  * </pre>
	  * @param taskId 
	  * void
	 * @throws Exception 
	  */
	@Override
	public void endProcessByTaskId(String taskId,String informType,String cause) throws Exception {
		BpmTask bpmTask = bpmTaskDao.get(taskId);
		
		String InstId = bpmTask.getProcInstId();
		
		String topInstId = bpmCheckOpinionManager.getTopInstId(InstId);
		
		List<String> instList= bpmCheckOpinionManager.getListByInstId(topInstId);
	
		//删除流程数据。
		actExecutionManager.delByInstList(instList);
		//删除关联的实例。
		actExecutionManager.remove(InstId);
		//删除候选人和任务
		//this.delByRelateTaskId(taskId);
		//删除候选人数据
		bpmTaskCandidateManager.delByInstList(instList);
		//任务转办代理
		bpmTaskTurnManager.delByInstList(instList);
		//会签数据
		bpmSignDataManager.delByInstList(instList);
		//再删除任务
		bpmTaskDao.delByInstList(instList);
		//TODO 删除会签任务
		
		//更新实例的状态
		DefaultBpmProcessInstance  instance= bpmProcessInstanceManager.get(InstId);
		instance.setStatus(ProcessInstanceStatus.STATUS_MANUAL_END.getKey());
		instance.setEndTime(new Date());
		instance.setDuration(new Date().getTime()-instance.getCreateTime().getTime());
		bpmProcessInstanceManager.update(instance);
		//bpmProcessInstanceManager.updateStatusByInstanceId(InstId,ProcessInstanceStatus.STATUS_MANUAL_END.getKey());
		//更新正在审批的审批意见的状态
		List<DefaultBpmCheckOpinion> litCheckOpinions= bpmCheckOpinionManager.getByInstId(InstId);
		if(litCheckOpinions!=null)
		{
			for (DefaultBpmCheckOpinion defaultBpmCheckOpinion : litCheckOpinions)
			{
				if(!defaultBpmCheckOpinion.getStatus().equals("awaiting_check"))continue;
				defaultBpmCheckOpinion.setCompleteTime(new Date());
				defaultBpmCheckOpinion.setAuditor(BeanUtils.isNotEmpty(ContextUtil.getCurrentUser())?ContextUtil.getCurrentUser().getUserId():"-1");
				defaultBpmCheckOpinion.setAuditorName(BeanUtils.isNotEmpty(ContextUtil.getCurrentUser())?ContextUtil.getCurrentUser().getFullname():"系统执行人");
				defaultBpmCheckOpinion.setStatus(OpinionStatus.MANUAL_END.getKey());
				defaultBpmCheckOpinion.setDurMs(new Date().getTime()-defaultBpmCheckOpinion.getCreateTime().getTime());
				String opinion=StringUtil.isNotEmpty(defaultBpmCheckOpinion.getOpinion())?defaultBpmCheckOpinion.getOpinion()+"|"+cause:cause;
				defaultBpmCheckOpinion.setOpinion(opinion);
				bpmCheckOpinionManager.update(defaultBpmCheckOpinion);
				//更新节点状态
				NodeStatus nanualEndStatus=NodeStatus.MANUAL_END;
				bpmProStatusManager.createOrUpd(instance.getId(), instance.getBpmnDefId(), defaultBpmCheckOpinion.getTaskKey(), defaultBpmCheckOpinion.getTaskName(),nanualEndStatus);
				
			}
		}
		//bpmCheckOpinionManager.updStatusByWait(InstId,null, OpinionStatus.MANUAL_END.getKey());


		//4.通知相关人员。
		//TODO 执行过该任务的相关人员  暂时只通知发起人
		BpmProcessInstance processInstance = bpmProcessInstanceManager.get(topInstId);
		List<IUser> userList=new ArrayList<IUser>();
		if(topInstId!=null){
			IUser user = BpmUtil.getUser(processInstance.getCreateBy(), processInstance.getCreator());
			userList.add(user);
		}
		
		Map<String,Object> vars= getVars(bpmTask,cause);
		// 发送通知
		MessageUtil.sendMsg(TemplateConstants.TYPE_KEY.BPM_END_PROCESS, informType, userList, vars);
	
	}
	
	private Map<String,Object> getVars(BpmTask task,String cause){
		String baseUrl=SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
		IUser user = ContextUtil.getCurrentUser();
		Map<String,Object> map=new HashMap<String, Object>();
		//处理人
		map.put(TemplateConstants.TEMP_VAR.DELEGATE, BeanUtils.isNotEmpty(user)?user.getFullname():"系统执行人");
		map.put(TemplateConstants.TEMP_VAR.NODE_NAME, task.getName());
		//任务标题
		map.put(TemplateConstants.TEMP_VAR.TASK_SUBJECT, task.getSubject());
		map.put(TemplateConstants.TEMP_VAR.INST_SUBJECT, task.getSubject());
			
		map.put(TemplateConstants.TEMP_VAR.BASE_URL, baseUrl);
		//意见
		map.put(TemplateConstants.TEMP_VAR.CAUSE, cause);
		//流程实例Id
		map.put(TemplateConstants.TEMP_VAR.INST_ID, task.getProcInstId());
		
		
		return map;
	}

	@Override
	public List<DefaultBpmTask> getByInstList(List<String> instIds) {
		return bpmTaskDao.getByInstList(instIds);
	}

	

	@Override
	public PageList<DefaultBpmTask> getMyTransTask(String userId,
			QueryFilter queryFilter) {
		
		return bpmTaskDao.getMyTransTask(userId, queryFilter);
	}

	@Override
	public void delegate(String taskId, String toUser) {
		DefaultBpmTask bpmTask=bpmTaskDao.get(taskId);
		bpmTask.setAssigneeId(toUser);
		bpmTask.setStatus(TaskType.DELIVERTO.name());
		bpmTaskDao.update(bpmTask);
		natTaskService.setAssignee(bpmTask.getTaskId(), toUser);
		
	}
	//0:任务已经处理,1:可以锁定,2:不需要解锁 ,3:可以解锁，4,被其他人锁定,5:这种情况一般是管理员操作，所以不用出锁定按钮。
	@Override
	public int canLockTask(String taskId) {
		BpmTask task = bpmTaskDao.get(taskId);
		if(task==null){
			return 0;
		}
		String assigneeId=task.getAssigneeId();
		String curUserId=ContextUtil.getCurrentUserId();
		
		List<DefaultBpmTaskCandidate> list= bpmTaskCandidateManager.queryByTaskId(taskId);
		
		//任务执行人为空则可以进行锁定。
		if(BpmConstants.EmptyUser.equals(assigneeId)){
			if(BeanUtils.isEmpty(list) || ContextUtil.getCurrentUser().isAdmin() ){
				return 1;
			}
			//判断是否有候选人
			boolean isExist=isInCandidate(list,curUserId);
			return (isExist)?1 :5;
		}
		//执行人和当前人一致。
		else if(curUserId.equals(assigneeId)){
			//判断是否有候选人
			//不存在候选人，则不需要解锁。
			if(BeanUtils.isEmpty(list)){
				return 2;
			}
			// 存在候选人， 并且是超级管理员
			else if(ContextUtil.getCurrentUser().isAdmin()){
				return 3;
			}
			//存在候选人，则可以解锁。
			else{
				boolean isExist=isInCandidate(list,curUserId);
				return (isExist) ?3:5;
			}
		}
		//被其他人锁定。
		return 4;
	}
	
	

	@Override
	public boolean canAccessTask(String taskId, String userId) {
		BpmTask task= this.get(taskId);
		if(task.getAssigneeId().equals(userId)) return true;
		//判断候选人
		List<DefaultBpmTaskCandidate> candidateList=bpmTaskCandidateManager.queryByTaskId(taskId);
		
		return isInCandidate(candidateList, userId);
	}
	
	/**
	 * 人是否在候选人中。
	 * @param candidateList
	 * @param userId
	 * @return
	 */
	private boolean isInCandidate(List<DefaultBpmTaskCandidate> candidateList,String userId){
		List<IGroup> groups= defaultUserGroupService.getGroupsByUserId(userId);
		Map<String,IGroup> groupMap=groupListToMap(groups);
		
		for(DefaultBpmTaskCandidate candidate:candidateList){
			String executor=candidate.getExecutor();
			if(BpmIdentity.TYPE_USER.equals(candidate.getType())){
				if(userId.equals(executor)){
					return true;
				}
			}
			else{
				if(groupMap.containsKey(executor)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 将组织转换成为map。
	 * @param groups
	 * @return
	 */
	private Map<String,IGroup> groupListToMap(List<IGroup> groups){
		Map<String,IGroup> map=new HashMap<String, IGroup>();
		for(IGroup group:groups){
			map.put(group.getGroupId(), group);
		}
		return map;
		
	}

	@Override
	public DefaultBpmTask getByTaskId(String taskId) {
		
		DefaultBpmTask task = bpmTaskDao.get(taskId);
		List<DefaultBpmTask> tasks = new ArrayList<DefaultBpmTask>();
		tasks.add(task);
		convertInfo(tasks);
		
		return tasks.get(0);
	}

	@Override
	public void setTaskExecutors(String taskId, String[] userIds,String notifyType,String opinion) {
		DefaultBpmTask task=bpmTaskDao.get(taskId);
		bpmTaskCandidateManager.removeByTaskId(taskId);
		//修改意见中的审核人
		DefaultBpmCheckOpinion bpmCheckOpinion= bpmCheckOpinionManager.getByTaskId(taskId);
		List<BpmIdentity> identityList =new ArrayList<BpmIdentity>();
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		String userRealName="";
		int index=0;
		for (String userId  : userIds) {
			index++;
			IUser user= userServiceImpl.getUserById(userId);
			BpmIdentity bpmIdentity= bpmIdentityConverter.convertUser(user);
			bpmIdentity.setType(IdentityType.USER);
			identityList.add(bpmIdentity);
			userRealName+=user.getFullname();
			if(index!=userIds.length)
			userRealName+=",";
			
		}
		bpmCheckOpinion.setQualfiedNames(userRealName);
		bpmCheckOpinion.setQualfieds(BpmCheckOpinionUtil.getIdentityIds(identityList));
		bpmCheckOpinionManager.update(bpmCheckOpinion);
		
		if(BeanUtils.isEmpty(userIds)){
			task.setAssigneeId(BpmConstants.EmptyUser);
		}
		else if(userIds.length==1){
			task.setAssigneeId(userIds[0]);
		}
		else{
			task.setAssigneeId(BpmConstants.EmptyUser);
			List<BpmIdentity> list=new ArrayList<BpmIdentity>();
			for(String userId:userIds){
				DefaultBpmIdentity identity=new DefaultBpmIdentity();
				identity.setType(BpmIdentity.TYPE_USER);
				identity.setId(userId);
				list.add(identity);
			}
			//添加候选人。
			bpmTaskCandidateManager.addCandidate(taskId, list);
		}
		bpmTaskDao.update(task);
		
		//发送消息。
		sendMsg(task, userIds,notifyType,opinion);
	}
	
	/**
	 * 发送消息。
	 * @param task
	 * @param userIds
	 * @param notifyType
	 * @param opinion
	 */
	private void sendMsg(BpmTask task, String[] userIds,String notifyType,String opinion){
		if(BeanUtils.isEmpty(userIds)) return;
		String baseUrl=SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
		
		//发送通知。
		NotifyTaskModel model=new NotifyTaskModel();
		List<IUser> userList=new ArrayList<IUser>();
		for(String userId:userIds){
			IUser user= userServiceImpl.getUserById(userId);
			userList.add(user);
		}
		model.setIdentitys(userList);
		model.setOpinion(opinion);
		model.addVars(TemplateConstants.TEMP_VAR.BASE_URL, baseUrl)
		.addVars(TemplateConstants.TEMP_VAR.TASK_SUBJECT, task.getSubject())
		.addVars(TemplateConstants.TEMP_VAR.TASK_ID, task.getId())
		.addVars(TemplateConstants.TEMP_VAR.CAUSE, opinion);
		
		
		MessageUtil.send(model, notifyType, "bpmTaskSetExecutors");
	}

	@Override
	public void updateTaskPriority(String taskId, Long priority) {
		bpmTaskDao.updateTaskPriority(taskId,priority);
		
	}

	@Override
	public List<DefaultBpmTask> getByExecuteAndNodeId(String executeId, String nodeId) {
		return bpmTaskDao.getByExecuteAndNodeId(executeId, nodeId);
	}

	@Override
	public void unLockTask(String taskId, String userId) {
		bpmTaskDao.updateAssigneeOwnerId(taskId, userId, userId);
	}

	@Override
	public List<DefaultBpmTask> getByNeedPendMeetingUserId(String userId, QueryFilter queryFilter) {
		List<IGroup> list= defaultUserGroupService.getGroupsByUserId(userId);
		Map<String,String> groupMap=convertGroupList(list);
		return bpmTaskDao.getByNeedPendMeetingUserId(userId, groupMap,queryFilter);
	}
	
}
