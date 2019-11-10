package com.hotent.bpmx.webservice.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.Direction;
import com.hotent.base.api.query.FieldLogic;
import com.hotent.base.api.query.FieldSort;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultFieldSort;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.exception.HotentHttpStatus;
import com.hotent.base.exception.RequiredException;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.PrivilegeMode;
import com.hotent.bpmx.api.constant.ProcessInstanceStatus;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.BpmIdentityService;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.api.service.SignService;
import com.hotent.bpmx.api.service.TaskCommuService;
import com.hotent.bpmx.api.service.TaskTransService;
import com.hotent.bpmx.core.engine.form.BpmFormFactory;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.manager.BpmAgentSettingManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTransRecordManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.manager.CopyToManager;
import com.hotent.bpmx.persistence.model.BpmAgentCondition;
import com.hotent.bpmx.persistence.model.BpmAgentDef;
import com.hotent.bpmx.persistence.model.BpmAgentSetting;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.bpmx.persistence.model.BpmTaskTrans;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.bpmx.webservice.api.IFlowService;
import com.hotent.bpmx.webservice.model.BasicResult;
import com.hotent.form.api.model.FormCategory;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.api.model.FormType;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.restful.params.AssignParamObject;
import com.hotent.restful.params.BaseQueryFilter;
import com.hotent.restful.params.BpmAgentConditionParam;
import com.hotent.restful.params.BpmAgentDefParam;
import com.hotent.restful.params.BpmAgentsettingParam;
import com.hotent.restful.params.CommonResult;
import com.hotent.restful.params.CommunicateParamObject;
import com.hotent.restful.params.ModifyExecutorsParamObject;
import com.hotent.restful.params.RestParamUtil;
import com.hotent.restful.params.RevokeParamObject;
import com.hotent.restful.params.TaskOrInstanQueryFilter;
import com.hotent.restful.params.TaskTransParamObject;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

@Service("IFlowService")
public class FlowServiceImpl implements IFlowService
{
	
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor ;
	@Resource
	CopyToManager copyToManager;
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager;
	@Resource
	BpmTaskTransRecordManager taskTransRecordManager;
	@Resource
	BpmTaskActionService bpmTaskActionService;
	@Resource
	NatTaskService natTaskService;
	@Resource
	SignService signService;
	@Resource
	BpmIdentityExtractService bpmIdentityExtractService;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmAgentSettingManager bpmAgentSettingManager;
	@Resource
	IUserService userServiceImpl;
	@Resource
	TaskTransService taskTransService;
	@Resource
	BpmInstService bpmInstService;
	@Resource
	BpmIdentityService bpmIdentityService;
	@Resource
	TaskCommuService taskCommuService;
	
	@Override
	public PageList<DefaultBpmTask> getTodoList(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception
	{
		String account = taskOrInstanQueryFilter.getAccount();
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();
		String processName = taskOrInstanQueryFilter.getProcessName();

		ServiceUtil.setCurrentUser(account);
		IUser user = ServiceUtil.getUserByAccount(account);

		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);

		// 设置查询条件
		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject_", subject, QueryOP.LIKE);
		}
		if (StringUtil.isNotEmpty(processName)) {
			queryFilter.addFilter("proc_def_name_", processName, QueryOP.LIKE);
		}
		// 查询列表
		try {
			PageList<DefaultBpmTask> list = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(user.getUserId(), queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
	}

	@Override
	public PageList<Map<String,Object>> getDoneList(TaskOrInstanQueryFilter taskOrInstanQueryFilter,String status) throws Exception
	{

		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();
		String processName = taskOrInstanQueryFilter.getProcessName();

		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);
		// 去除buildQueryFilter方法中设的按创建日期排序的条件，因为查询语句中创建日期取了别名，直接用会不通过，按前台传入排序字段排序
		queryFilter.getFieldSortList().clear();
		String orderField = taskOrInstanQueryFilter.getOrderField();
		String orderSeq = taskOrInstanQueryFilter.getOrderSeq();
		// 重新设置排序
		if (StringUtil.isNotEmpty(orderField)) {
			if(StringUtil.isEmpty(orderSeq)){
				orderSeq = "desc";
			}
			List<FieldSort> fieldSorts = new ArrayList<FieldSort>();
			fieldSorts.add(new DefaultFieldSort(orderField, Direction.fromString(orderSeq)));
			queryFilter.setFieldSortList(fieldSorts);
		}

		IUser user = ServiceUtil.getUserByAccount(account);

		// 设置查询条件
		if (StringUtil.isNotEmpty(status)) {
			queryFilter.addFilter("wfInst.status_", status, QueryOP.EQUAL);
		}
		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject_", subject, QueryOP.LIKE);
		}
		if (StringUtil.isNotEmpty(processName)) {
			queryFilter.addFilter("proc_def_name_", processName, QueryOP.LIKE);
		}

		// 查询列表
		try {
			PageList<Map<String,Object>> list = (PageList<Map<String,Object>>)bpmProcessInstanceManager.getHandledByUserId(user.getUserId(), queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
		
	}

	@Override
	public PageList<DefaultBpmProcessInstance> getMyCompletedList(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception
	{
		// 传入的账户
		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();
		String processName = taskOrInstanQueryFilter.getProcessName();
		// 排序分页
		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);
		IUser user = ServiceUtil.getUserByAccount(account);

		// 设置查询条件
		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject_", subject, QueryOP.LIKE);
		}
		if (StringUtil.isNotEmpty(processName)) {
			queryFilter.addFilter("proc_def_name_", processName, QueryOP.LIKE);
		}
		// 查询列表
		try {
			PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getCompletedByUserId(user.getUserId(),
					queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
		
	}

	@Override
	public PageList<DefaultBpmProcessInstance> getMyRequestList(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception
	{
		// 传入的账户
		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();
		String processName = taskOrInstanQueryFilter.getProcessName();
		// 排序分页
		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);

		IUser user = ServiceUtil.getUserByAccount(account);

		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject_", subject, QueryOP.LIKE);
		}
		if (StringUtil.isNotEmpty(processName)) {
			queryFilter.addFilter("proc_def_name_", processName, QueryOP.LIKE);
		}

		// 查询列表
		try {
			PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getMyRequestByUserId(user.getUserId(),
					queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
	}

	@Override
	public PageList<DefaultBpmDefinition> getMyFlowList(TaskOrInstanQueryFilter taskOrInstanQueryFilter,String typeId) throws Exception
	{

		// 传入的账户
		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String processName = taskOrInstanQueryFilter.getProcessName();
		// 设置这个让调用 ContextUtil.getCurrentUser()方法能够获取到值。
		ServiceUtil.setCurrentUser(account);
		IUser user = ServiceUtil.getUserByAccount(account);

		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);
		// 设置查询条件
		if (StringUtil.isNotEmpty(processName)) {
			queryFilter.addFilter("name_", processName, QueryOP.LIKE);
		}
		// 分类ID
		if (StringUtil.isNotEmpty(typeId)) {
			queryFilter.addFilter("type_id_", typeId, QueryOP.EQUAL);
		}
		queryFilter.addParamsFilter("bpmDefAuthorizeRightType", BPMDEFAUTHORIZE_RIGHT_TYPE.START);
		queryFilter.addFilter("is_main_", "Y", QueryOP.EQUAL);
		// 查询列表
		try {
			PageList<DefaultBpmDefinition> list = (PageList<DefaultBpmDefinition>) bpmDefinitionManager.queryList(queryFilter, user);

			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
	}
	
	/**
	 * 根据jsonobject 构建  QueryFilter 。
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("unused")
	private DefaultQueryFilter buildQueryFilter(JSONObject jsonObject ){
		String orderField =JsonUtil.getString(jsonObject, "orderField","create_time_"); 
		String orderSeq =JsonUtil.getString(jsonObject, "orderSeq","desc"); 
		Integer currentPage = JsonUtil.getInt(jsonObject,"currentPage",1);
		Integer pageSize = JsonUtil.getInt(jsonObject,"pageSize",20);  
		
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		// 设置分页
		DefaultPage page = new DefaultPage(currentPage,pageSize);
		queryFilter.setPage(page);
		// 设置排序
		if (StringUtil.isNotEmpty(orderField) && StringUtil.isNotEmpty(orderSeq))
		{
			List<FieldSort> fieldSorts = new ArrayList<FieldSort>();
			fieldSorts.add(new DefaultFieldSort(orderField, Direction.fromString(orderSeq)));
			queryFilter.setFieldSortList(fieldSorts);
		}
		
		return queryFilter;
	}
	
	/**
	 * 根据TaskQueryFilterObject 构建  QueryFilter
	 * @param TaskQueryFilterObject
	 * @return
	 */
	private DefaultQueryFilter buildDefaultFilter(TaskOrInstanQueryFilter taskOrInstanQueryFilter ){
		String orderField = StringUtil.isEmpty(taskOrInstanQueryFilter.getOrderField())? null:taskOrInstanQueryFilter.getOrderField();
		String orderSeq = StringUtil.isEmpty(taskOrInstanQueryFilter.getOrderSeq())?"desc":taskOrInstanQueryFilter.getOrderSeq();
		Integer currentPage = BeanUtils.isEmpty(taskOrInstanQueryFilter.getCurrentPage())?1:taskOrInstanQueryFilter.getCurrentPage();
		Integer pageSize = BeanUtils.isEmpty(taskOrInstanQueryFilter.getPageSize())?20:taskOrInstanQueryFilter.getPageSize();
		Map<String,Object> paramMap = BeanUtils.isEmpty(taskOrInstanQueryFilter.getParamMap())?new HashMap<String,Object>():taskOrInstanQueryFilter.getParamMap();
		
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		// 设置分页
		DefaultPage page = new DefaultPage(currentPage,pageSize);
		queryFilter.setPage(page);
		// 设置排序
		if (StringUtil.isNotEmpty(orderField) && StringUtil.isNotEmpty(orderSeq))
		{
			List<FieldSort> fieldSorts = new ArrayList<FieldSort>();
			fieldSorts.add(new DefaultFieldSort(orderField, Direction.fromString(orderSeq)));
			queryFilter.setFieldSortList(fieldSorts);
		}
		
		try {
			if(BeanUtils.isNotEmpty(paramMap)){
				FieldLogic andFieldLogic = RestParamUtil.getFieldLogic(paramMap, null);
				if(BeanUtils.isNotEmpty(andFieldLogic)){
					queryFilter.setFieldLogic(andFieldLogic);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return queryFilter;
	}
	

	@Override
	public PageList<DefaultBpmProcessInstance> getMyDraftList(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception
	{
		// 传入的账户
		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();
		String processName = taskOrInstanQueryFilter.getProcessName();

		IUser user = ServiceUtil.getUserByAccount(account);

		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);

		// 设置查询条件
		if (StringUtil.isNotEmpty(processName)) {
			queryFilter.addFilter("proc_def_name_", processName, QueryOP.LIKE);
		}
		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject_", subject, QueryOP.LIKE);
		}

		// 查询列表
		try {
			PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getDraftsByUserId(user.getUserId(),
					queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
	}
	
	@Override
	public PageList<CopyTo> getReceiverCopyTo(TaskOrInstanQueryFilter taskOrInstanQueryFilter,String type) throws Exception {
		// 传入的账户
		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();

		IUser user = ServiceUtil.getUserByAccount(account);

		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);

		// 设置查询条件
		if (StringUtil.isNotEmpty(type)) {
			queryFilter.addFilter("type", type, QueryOP.EQUAL);
		}
		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject", subject, QueryOP.LIKE);
		}

		// 查询列表
		try {
			PageList<CopyTo> list = copyToManager.getReceiverCopyTo(user.getUserId(), queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
	}

	

	/**
	 * 根据流程定义ID或流程定义KEY获取流程变量
	 * 
	 * @param json
	 * @return
	 */
	@Override
	public List<BpmVariableDef> getWorkflowVar(String json) throws Exception
	{
		JSONObject result = new JSONObject();
		JSONObject jsonObject = JSONObject.fromObject(json);
		String defId =JsonUtil.getString(jsonObject,"defId");
		String defKey = JsonUtil.getString(jsonObject,"defKey");
		
		BpmDefinition bpmDefinition = null;
		bpmDefinition = bpmDefinitionManager.get(defId);
		if (BeanUtils.isEmpty(bpmDefinition) && StringUtil.isNotEmpty(defKey))
		{
			bpmDefinition = bpmDefinitionManager.getMainByDefKey(defKey, false);
			if(BeanUtils.isEmpty(bpmDefinition)){
				throw new NullPointerException("找不到对应的流程定义，请检查输入的defId或defKey！");
			}
			defId=bpmDefinition.getDefId();
		}
		if(BeanUtils.isEmpty(bpmDefinition)){
			throw new NullPointerException("找不到对应的流程定义，请检查输入的defId或defKey！");
		}
		List<BpmVariableDef> bpmVariableList = new ArrayList<BpmVariableDef>();
		// 全局变量
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt= (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		if(defExt.getVariableList() != null)
			bpmVariableList.addAll(defExt.getVariableList());
		
		//节点变量
		List<BpmNodeDef> bpmNodeDefList = bpmDefinitionAccessor.getNodesByType(defId, NodeType.USERTASK);
		bpmNodeDefList.addAll(bpmDefinitionAccessor.getNodesByType(defId, NodeType.SIGNTASK));
		
		for(BpmNodeDef bpmNodeDef : bpmNodeDefList){
			UserTaskNodeDef taskNodeDef = (UserTaskNodeDef) bpmNodeDef;
			List<BpmVariableDef> nodeVarList = taskNodeDef.getVariableList();
			if(nodeVarList != null)
				bpmVariableList.addAll(nodeVarList); 
		}
		
		ServiceUtil.bulidListResult(result, bpmVariableList);
		return bpmVariableList;
		
	}

	@Override
	public PageList<DefaultBpmTaskTurn> getDelegate(TaskOrInstanQueryFilter taskOrInstanQueryFilter,String type) throws Exception {
		// 传入的账户
		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();
		String taskName = taskOrInstanQueryFilter.getTaskName();// 任务名称 

		IUser user = ServiceUtil.getUserByAccount(account);

		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);

		// 设置查询条件
		if (StringUtil.isNotEmpty(type)) {
			queryFilter.addFilter("turn_type_", type, QueryOP.EQUAL);
		}
		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject_", subject, QueryOP.LIKE);
		}
		if (StringUtil.isNotEmpty(taskName)) {
			queryFilter.addFilter("task_name_", taskName, QueryOP.LIKE);
		}

		// 查询列表
		try {
			PageList<DefaultBpmTaskTurn> list = (PageList<DefaultBpmTaskTurn>) bpmTaskTurnManager.getMyDelegate(user.getUserId(), queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
	}

	@Override
	public PageList<BpmTaskTransRecord> getMyTrans(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception {
		// 传入的账户
		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();// 任务主题
		String processName = taskOrInstanQueryFilter.getProcessName();// 流程名称

		// 设置这个让调用 ContextUtil.getCurrentUser()方法能够获取到值。
		ServiceUtil.setCurrentUser(account);

		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);

		// 设置查询条件
		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject", subject, QueryOP.LIKE);
		}
		if (StringUtil.isNotEmpty(processName)) {
			queryFilter.addFilter("defName", processName, QueryOP.LIKE);
		}

		// 查询列表
		try {
			PageList<BpmTaskTransRecord> list = (PageList<BpmTaskTransRecord>) taskTransRecordManager.getMyTransRecord(queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
	}

	@Override
	public CommonResult<String> removeDraftById(String id) throws Exception {
		DefaultBpmProcessInstance processeInstance = bpmProcessInstanceManager.get(id);
		if(BeanUtils.isEmpty(processeInstance)){
			throw new NullPointerException("id为"+id+"的草稿不存在！");
		}
		if(!ProcessInstanceStatus.STATUS_DRAFT.getKey().equals(processeInstance.getStatus())){
			throw new RuntimeException("该实例不是草稿状态，请不要通过此接口删除！");
		}
		bpmProcessInstanceManager.remove(id);
		return new CommonResult<String>(true, "流程草稿删除成功", "");
	}
	
	@Override
	public DefaultBpmDefinition getBpmDefinitionByDefId(String defId) throws Exception {
		if(StringUtil.isEmpty(defId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":流程定义id必填！");
		}
		DefaultBpmDefinition definition = bpmDefinitionManager.get(defId);
		if(BeanUtils.isEmpty(definition)){
			throw new RuntimeException("根据输入的defId没有查询到对应数据！");
		}
		return definition;
	}

	@Override
	public BasicResult taskAssign(AssignParamObject assignParamObject) throws Exception {
		String account = assignParamObject.getAccount();
		ServiceUtil.setCurrentUser(account);
		String taskId = assignParamObject.getTaskId();
		String messageType = assignParamObject.getMessageType();
		String userAccount = assignParamObject.getUserAccount();
		String opinion = assignParamObject.getOpinion();
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if (BeanUtils.isEmpty(task)) {
			throw new RuntimeException("任务不存在或已经被处理！");
		}
		if(StringUtil.isEmpty(messageType)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":messageType通知类型必填！");
		}
		String userId = "";
		if (StringUtil.isEmpty(userAccount)) {
			throw new RuntimeException("必须传入转办/加签用户账号(userAccount)");
		}
		else{
			if(account.equals(userAccount)){
				throw new RuntimeException("任务转办/加签人不能为自己(account不能与userAccount相同)");
			}
			if(userAccount.contains(",")){
				throw new RuntimeException(String.format("转办/加签给多个用户:%s时请调用taskSignUsers接口", userAccount));
			}
			IUser userByAccount = ServiceUtil.getUserByAccount(userAccount);
			if(BeanUtils.isEmpty(userByAccount)){
				throw new RuntimeException("转办/加签用户账号(userAccount)不存在");
			}
			userId = userByAccount.getUserId();
		}
		//转办
		bpmTaskActionService.delegate(taskId, userId, messageType, opinion);
		return new BasicResult("任务转办/加签成功");
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public BasicResult taskSignUsers(AssignParamObject assignParamObject) throws Exception {
		String account = assignParamObject.getAccount();
		ServiceUtil.setCurrentUser(account);
		IUser user = ServiceUtil.getUserByAccount(account);
		String taskId = assignParamObject.getTaskId();
		String userAccounts = assignParamObject.getUserAccount();
		String messageType = assignParamObject.getMessageType();
		String addReason = assignParamObject.getOpinion();
		if(StringUtil.isEmpty(userAccounts)){
			throw new RuntimeException("必须传入转办/加签用户账号");
		}
		String[] userAccountAry = userAccounts.split(",");
		List<String> userIdList = new ArrayList<String>();
		for (String userAccount : userAccountAry) {
			if(account.equals(userAccount)){
				throw new RuntimeException(String.format("任务转办/加签人不能为自己(userAccount中不能包含account):%s", account));
			}
			IUser userByAccount = ServiceUtil.getUserByAccount(userAccount);
			if(BeanUtils.isEmpty(userByAccount)){
				throw new RuntimeException(String.format("转办/加签用户账号(userAccount):%s不存在", userAccount));
			}
			userIdList.add(userByAccount.getUserId());
		}
		String[] userIdAry = new String[userIdList.size()];
		userIdList.toArray(userIdAry);
		ResultMessage addSignTask = signService.addSignTask(taskId, userIdAry);
		// 加签成功 发送消息
		if (addSignTask.getResult() == ResultMessage.SUCCESS) {
			if (StringUtil.isNotEmpty(messageType)) {
				List<BpmIdentity> bpmIdentities = (List<BpmIdentity>) addSignTask.getVars().get("users");
				List<IUser> users = bpmIdentityExtractService.extractUser(bpmIdentities);
				Map<String, Object> variables = natTaskService.getVariables(taskId);
				variables.put("cause", addReason);
				variables.put("sender", ContextUtil.getCurrentUser().getFullname());
				variables.put("taskSubject", variables.get("subject_"));
				String baseUrl = SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
				variables.put("baseUrl", baseUrl);
				
				List<Map<String, String>> taskIds = (List<Map<String, String>>) addSignTask.getVars().get("taskIds");
				for (IUser iuser : users) {
					String taskid = findTaskId(taskIds,user.getUserId());
					if(StringUtils.isNotEmpty(taskid)){
						variables.put("taskId", taskid);
						users = new ArrayList<IUser>();
						users.add(user);
						//发送消息
						signService.sendNotify(users, Arrays.asList(messageType.split(",")), TemplateConstants.TYPE_KEY.BPM_ADD_SIGN_TASK, variables);
					}
				}
			}
			return new BasicResult("任务转办/加签成功");
		} else {
			throw new RuntimeException(addSignTask.getCause());
		}
	}
	
	private String findTaskId(List<Map<String, String>> taskIds,String userId){
		String taskId = "";
		for(Map<String, String> map:taskIds){
			String uid = map.get("userId");
			if(userId.equals(uid)){
				taskId = map.get("taskId");
				break;
			}
		}
		return taskId;
	}

	@Override
	public CommonResult<String> revokeInstance(RevokeParamObject revokeParamObject) throws Exception {
		String account = revokeParamObject.getAccount();
		ServiceUtil.setCurrentUser(account);
		String instanceId = revokeParamObject.getInstanceId();
		String messageType = revokeParamObject.getMessageType();
		String cause = revokeParamObject.getCause();
		Boolean isHandRevoke = revokeParamObject.getIsHandRevoke();//是否从已办中撤回
		ResultMessage result = null;
		if (isHandRevoke) {
			result = bpmProcessInstanceManager.revokeTask(instanceId, messageType, cause);
		} else {
			result = bpmProcessInstanceManager.revokeInstance(instanceId, messageType, cause);
		}
		if(result.getResult() == 1){
			return new CommonResult<String>(true, "撤回成功", "");
		}else{
			return new CommonResult<String>(false, "撤回失败", "");
		}
	}

	@Override
	public JSONArray getNodesByDefKey(String defKey) throws Exception {
		if(StringUtil.isEmpty(defKey)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description() + ":" + "流程定义key必填!");
		}
		String defId = bpmDefinitionService.getBpmDefinitionByDefKey(defKey, false).getDefId();
		List<BpmNodeDef> nodeDefs = bpmDefinitionAccessor.getAllNodeDef(defId);
		JSONArray array = new JSONArray();
		for(BpmNodeDef n : nodeDefs){
			JSONObject object = new JSONObject();
			object.put("nodeId", n.getNodeId());
			object.put("nodeName", n.getName());
			object.put("type", n.getType());
			object.put("typeDescription",n.getType().getValue());
			array.add(object);
		}
		return array;
	}

	@Override
	public Map<String, List<BpmIdentity>> getNextTaskUsers(String taskId) throws Exception {
		DefaultBpmTask bpmTask= bpmTaskManager.get(taskId);
		if(BeanUtils.isEmpty(bpmTask)){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(bpmTask.getProcInstId());
		String defId = bpmProcessInstance.getProcDefId();//流程定义id
		String nodeId = bpmTask.getNodeId();//任务节点id
		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<BpmNodeDef> nodes = taskNodeDef.getOutcomeNodes();
		Map<String,List<BpmIdentity>> outcomeUserMap = new HashMap<String,List<BpmIdentity>>();
		List<BpmNodeDef> handlerSelectOutcomeNodes = handlerSelectOutcomeNodes(nodes);
		for (BpmNodeDef bpmNodeDef : handlerSelectOutcomeNodes) {
			List<BpmIdentity> bpmIdentitys = bpmIdentityService.searchByNode(bpmTask.getProcInstId(), bpmNodeDef.getNodeId());
			outcomeUserMap.put(bpmNodeDef.getNodeId(), bpmIdentitys);
		}
		return outcomeUserMap;
	}
	
	// 处理选择路径跳转的分支出口
	private List<BpmNodeDef> handlerSelectOutcomeNodes(List<BpmNodeDef> outcomeNodes) {
		int size = outcomeNodes.size();
		List<BpmNodeDef> returnList = new ArrayList<BpmNodeDef>();
		if (size == 1) {
			BpmNodeDef bpmNodeDef = outcomeNodes.get(0);
			NodeType nodeType = bpmNodeDef.getType();
			// 网关节点
			if (NodeType.EXCLUSIVEGATEWAY.equals(nodeType) || NodeType.INCLUSIVEGATEWAY.equals(nodeType)
					|| NodeType.PARALLELGATEWAY.equals(nodeType)) {
				returnList = bpmNodeDef.getOutcomeNodes();
			}
		}
		if (BeanUtils.isEmpty(returnList)) {
			return outcomeNodes;
		} else {
			return returnList;
		}
	}

	@Override
	public CommonResult<String> setTaskExecutors(ModifyExecutorsParamObject modifyExecutorsParamObject) throws Exception {
		String taskId = modifyExecutorsParamObject.getTaskId();
		String[] userIds = modifyExecutorsParamObject.getUserIds();
		String messageType = modifyExecutorsParamObject.getMessageType();
		String opinion = modifyExecutorsParamObject.getCause();
		if(StringUtil.isEmpty(taskId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":任务id必填！");
		}
		if(BeanUtils.isEmpty(userIds)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":userIds任务执行人id必填！");
		}
		if(StringUtil.isEmpty(messageType)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":messageType通知方式必填！");
		}
		if(StringUtil.isEmpty(opinion)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":cause通知内容比必填！");
		}
		bpmTaskManager.setTaskExecutors(taskId, userIds,messageType,opinion);
		return new CommonResult<String>(true, "修改任务执行人成功", "");
	}

	@Override
	public CommonResult<String> setAgent(BpmAgentsettingParam agentSetting) throws Exception {
		List<BpmAgentConditionParam> conditions = agentSetting.getConditions();
		List<BpmAgentDefParam> agentDefList = agentSetting.getBpmDefs();
		
		List<BpmAgentDef> bpmAgentDefList = new ArrayList<BpmAgentDef>();
		List<BpmAgentCondition> bpmAgentConditionList = new ArrayList<BpmAgentCondition>();
		
		if(StringUtil.isEmpty(agentSetting.getSubject())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：subject流程代理设置标题必填！");
		}
		if(StringUtil.isEmpty(agentSetting.getAuthName())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：authName流程代理设置“授权人姓名”必填！");
		}
		if(StringUtil.isEmpty(agentSetting.getAuthId())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：authId流程代理设置“授权人ID”必填！");
		}
		if(BeanUtils.isEmpty(agentSetting.getStartDate())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：startDate流程代理设置“代理开始生效时间”必填！");
		}
		if (BeanUtils.isEmpty(agentSetting.getEndDate())) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：endDate流程代理设置“代理结束时间”必填！");
		}
		//日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(agentSetting.getStartDate());
		Date endDate = sdf.parse(agentSetting.getEndDate());
		
		Short type = agentSetting.getType();
		//全部代理
		if(type == 1){
			if(StringUtil.isEmpty(agentSetting.getAgent())){
				throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：agent流程代理设置“代理人”必填！");
			}
			if(StringUtil.isEmpty(agentSetting.getAgentId())){
				throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：agentId流程代理设置“代理人ID”必填！");
			}
		}
		
		//部分代理
		if(type == 2 && BeanUtils.isEmpty(agentDefList)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：type=2,部分代理必须要有具体代理的流程，agentDefList数据！");
		}else if(type == 2 && BeanUtils.isNotEmpty(agentDefList)){
			if(StringUtil.isEmpty(agentSetting.getAgent())){
				throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：agent流程代理设置“代理人”必填！");
			}
			if(StringUtil.isEmpty(agentSetting.getAgentId())){
				throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：agentId流程代理设置“代理人ID”必填！");
			}
			for(BpmAgentDefParam a : agentDefList){//对部分代理数据验证
				if(StringUtil.isEmpty(a.getFlowKey())){
					throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：flowKey代理指定流程对象中的“流程定义key”必填！");
				}
				if(StringUtil.isEmpty(a.getFlowName())){
					throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：flowName代理指定流程对象中的“流程定义名称”必填！");
				}
			}
			for(BpmAgentDefParam a : agentDefList){
				com.alibaba.fastjson.JSONObject agentDefObject = com.alibaba.fastjson.JSONObject.parseObject(a.toString());
				BpmAgentDef b = com.alibaba.fastjson.JSONObject.toJavaObject(agentDefObject,BpmAgentDef.class);
				bpmAgentDefList.add(b);
			}
		}
		
		//条件代理
		if(type == 3 && BeanUtils.isEmpty(conditions)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：type=3,条件代理必须要有具体条件设置，conditions数据！");
		}else if(type == 3 && BeanUtils.isNotEmpty(conditions)){
			if(StringUtil.isEmpty(agentSetting.getFlowKey())){
				throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：当type=3为条件代理时，flowKey必填！");
			}
			DefaultBpmDefinition definition = bpmDefinitionManager.getMainByDefKey(agentSetting.getFlowKey());
			if(BeanUtils.isEmpty(definition)){
				throw new RuntimeException("根据代理中输入的流程key找不到对应的流程定义，请检查！");
			}
			for(BpmAgentConditionParam c :conditions){//对条件代理数据验证
				if(StringUtil.isEmpty(c.getAgentId())){
					throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：agentId流程代理条件对象中的“代理人ID”必填！");
				}
				if(StringUtil.isEmpty(c.getAgentName())){
					throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：agentName流程代理条件对象中的“代理人”必填！");
				}
				if(StringUtil.isEmpty(c.getCondition())){
					throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：condition流程代理条件对象中的“条件规则”必填！");
				}
			}
			for(BpmAgentConditionParam c :conditions){
				com.alibaba.fastjson.JSONObject conditionsObject = com.alibaba.fastjson.JSONObject.parseObject(c.toString());
				BpmAgentCondition bc = com.alibaba.fastjson.JSONObject.toJavaObject(conditionsObject,BpmAgentCondition.class);
				String conditionStr =  Base64.getFromBase64(bc.getCondition());
				bc.setCondition(conditionStr);
				bpmAgentConditionList.add(bc);
			}
		}
		
		com.alibaba.fastjson.JSONObject agentSettingObject = com.alibaba.fastjson.JSONObject.parseObject(agentSetting.toString());
		BpmAgentSetting bpmAgentSetting = com.alibaba.fastjson.JSONObject.toJavaObject(agentSettingObject,BpmAgentSetting.class);
		bpmAgentSetting.setStartDate(startDate);
		bpmAgentSetting.setEndDate(endDate);
		
		if(type == 2){
			bpmAgentSetting.setDefList(bpmAgentDefList);
		}
		if(type == 3){
			bpmAgentSetting.setConditionList(bpmAgentConditionList);
		}
		
		ResultMessage result = bpmAgentSettingManager.checkConflict(bpmAgentSetting);
		if (ResultMessage.FAIL == result.getResult()) {
			throw new RuntimeException("设置代理失败:"+result.getMessage());
		}
		String id = bpmAgentSetting.getId();
		if (StringUtil.isEmpty(id)) {
			bpmAgentSettingManager.create(bpmAgentSetting);
			return new CommonResult<String>(true, "添加流程代理设置成功", "");
		} else {
			bpmAgentSettingManager.update(bpmAgentSetting);
			return new CommonResult<String>(true, "更新流程代理设置成功", "");
		}
	}

	@Override
	public Boolean isAllowAddSign(Map<String, Object> json) throws Exception {
		if(!json.containsKey("taskId")){
			throw new RuntimeException("taskId没有设置值");
		}
		
		if(!json.containsKey("userId")&&!json.containsKey("account")  ){
			throw new RuntimeException("用户id或者用户账号两者不能同时为空");
		}
		
		String userId = "";
		if(json.containsKey("userId")){
			userId = json.get("userId").toString();
		}else{
			IUser user = ServiceUtil.getUserByAccount(json.get("account").toString());
			if(BeanUtils.isEmpty(user)){
				throw new RuntimeException("用户不存在");
			}
			userId = user.getUserId();
		}
		
		String taskId = json.get("taskId").toString();
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		
		String defId = task.getProcDefId();
		String nodeId = task.getNodeId();
		
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		
		if(!(bpmNodeDef instanceof SignNodeDef)){
			throw new RuntimeException("该任务不是会签任务");
		}
		
		NatTaskService natTaskService = (NatTaskService) AppUtil.getBean(NatTaskService.class);
		SignService signService = (SignService) AppUtil.getBean(SignService.class);
		Map<String, Object> variables = natTaskService.getVariables(json.get("taskId").toString());
		List<PrivilegeMode> privilege = signService.getPrivilege(userId, (SignNodeDef) bpmNodeDef, variables);
		if (privilege.contains(PrivilegeMode.ALL) || privilege.contains(PrivilegeMode.ALLOW_ADD_SIGN)) {
			return true;
		}
		return false;
	}

	@Override
	public CommonResult<String> taskToTrans(TaskTransParamObject taskTransParamObject) throws Exception {
		String notifyType = taskTransParamObject.getNotifyType();
		String opinion = taskTransParamObject.getOpinion();
		String userIds = taskTransParamObject.getUserIds();
		String currentUserId=ContextUtil.getCurrentUserId();
		if(StringUtil.isEmpty(userIds)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":userIds流转人员id必填");
		}
		if(StringUtil.isEmpty(opinion)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":opinion流转意见必填");
		}
		String[] userIdArray = userIds.split(",");

		List<IUser> userList = new ArrayList<IUser>();
		for (String id : userIdArray) {
			IUser u = userServiceImpl.getUserById(id);
			if (u != null)
				userList.add(u);
			if(currentUserId.equals(u.getUserId())){
				throw new RuntimeException("流转人员不能包含本人！");
			}
		}
		com.alibaba.fastjson.JSONObject transObject = com.alibaba.fastjson.JSONObject.parseObject(taskTransParamObject.toString());
		BpmTaskTrans taskTrans = com.alibaba.fastjson.JSONObject.toJavaObject(transObject, BpmTaskTrans.class);
		taskTransService.addTransTask(taskTrans, userList, notifyType, opinion);
		return new CommonResult<String>(true, "流程流转成功", "");
	}
	
	@Override
	public String getUrlFormByTaskId(String taskId, String formType) {
		DefaultBpmTask task = bpmTaskManager.getByTaskId(taskId);
		if(BeanUtils.isEmpty(task)){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		String defId = task.getProcDefId();
		String nodeId = task.getNodeId();
		
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		BpmFormService  bpmFormService =BpmFormFactory.getFormService(FormType.fromValue(formType));
		FormModel formModel = bpmFormService.getByDefId(defId, nodeId, bpmProcessInstance);
		if(formModel==null || formModel.isFormEmpty() || FormCategory.INNER.equals(formModel.getType())){
			return "";
		}
		return formModel.getFormValue();
	}
	
	@Override
	public String getInstUrlForm(String proInstId, String nodeId,
			String formType) {
		BpmFormService  bpmFormService =BpmFormFactory.getFormService(FormType.fromValue(formType));
		DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(proInstId);
		FormModel formModel = bpmFormService. getInstFormByDefId( instance);
		if(BeanUtils.isNotEmpty(formModel)&&StringUtil.isNotEmpty(formModel.getFormId())){
			if(StringUtil.isNotEmpty(nodeId)){
				FormModel formModelTmp = bpmFormService.getByDefId(instance.getProcDefId(), nodeId, instance);
				if(formModelTmp!=null && !formModelTmp.isFormEmpty()) formModel = formModelTmp;
			}
			
			if(formModel==null || formModel.isFormEmpty() || FormCategory.INNER.equals(formModel.getType()) ){
				return "";
			}
		}
		return formModel.getFormValue();
	}

	@Override
	public BasicResult communicate(CommunicateParamObject communicateParamObject)
			throws Exception {
		String account = communicateParamObject.getAccount();
		ServiceUtil.setCurrentUser(account);
		String taskId = communicateParamObject.getTaskId();
		if(StringUtil.isEmpty(taskId)){
			throw new RuntimeException("必须传入任务ID(taskId)");
		}
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if (BeanUtils.isEmpty(task)) {
			throw new RuntimeException("任务不存在或已经被处理！");
		}
		String userAccounts = communicateParamObject.getUserAccount();
		String communicateReason = communicateParamObject.getOpinion();
		String messageType = communicateParamObject.getMessageType();
		if(StringUtil.isEmpty(userAccounts)){
			throw new RuntimeException("必须传入沟通用户账号(userAccount)");
		}
		String[] userAccountAry = userAccounts.split(",");
		List<IUser> userIdList = new ArrayList<IUser>();
		for (String userAccount : userAccountAry) {
			if(account.equals(userAccount)){
				throw new RuntimeException(String.format("不能对自己发起沟通(userAccount中不能包含account):%s", account));
			}
			IUser userByAccount = ServiceUtil.getUserByAccount(userAccount);
			if(BeanUtils.isEmpty(userByAccount)){
				throw new RuntimeException(String.format("沟通用户账号(userAccount):%s不存在", userAccount));
			}
			userIdList.add(userByAccount);
		}
		taskCommuService.addCommuTask(taskId, messageType, communicateReason, userIdList);
		return new BasicResult("沟通成功");
	}

	@Override
	public PageList<DefaultBpmProcessInstance> getMyRequestListAll(
			TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception {
		// 传入的账户
		String account = taskOrInstanQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		// 查询条件
		String subject = taskOrInstanQueryFilter.getSubject();
		String processName = taskOrInstanQueryFilter.getProcessName();
		// 排序分页
		DefaultQueryFilter queryFilter = buildDefaultFilter(taskOrInstanQueryFilter);

		IUser user = ServiceUtil.getUserByAccount(account);
		queryFilter.addFilter("create_by_", user.getUserId(), QueryOP.EQUAL);
		queryFilter.addFilter("status_", "draft", QueryOP.NOT_EQUAL);
		
		if (StringUtil.isNotEmpty(subject)) {
			queryFilter.addFilter("subject_", subject, QueryOP.LIKE);
		}
		if (StringUtil.isNotEmpty(processName)) {
			queryFilter.addFilter("proc_def_name_", processName, QueryOP.LIKE);
		}

		// 查询列表
		try {
			PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.query(queryFilter);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库查询出错了！");
		}
	}

	@Override
	public PageList<DefaultBpmDefinition> getBpmDefList(
			BaseQueryFilter baseQueryFilter) throws Exception {
		String account = baseQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		DefaultQueryFilter queryFilter = buildDefaultFilter(baseQueryFilter);
		return (PageList<DefaultBpmDefinition>) bpmDefinitionManager.query(queryFilter);
	}
	
	/**
	 * 根据baseQueryFilter 构建  QueryFilter
	 * @param baseQueryFilter
	 * @return
	 */
	private DefaultQueryFilter buildDefaultFilter(BaseQueryFilter baseQueryFilter ){
		String orderField = StringUtil.isEmpty(baseQueryFilter.getOrderField())? null:baseQueryFilter.getOrderField();
		String orderSeq = StringUtil.isEmpty(baseQueryFilter.getOrderSeq())?"desc":baseQueryFilter.getOrderSeq();
		Integer currentPage = BeanUtils.isEmpty(baseQueryFilter.getCurrentPage())?1:baseQueryFilter.getCurrentPage();
		Integer pageSize = BeanUtils.isEmpty(baseQueryFilter.getPageSize())?20:baseQueryFilter.getPageSize();
		Map<String,Object> paramMap = BeanUtils.isEmpty(baseQueryFilter.getParamMap())?new HashMap<String,Object>():baseQueryFilter.getParamMap();
		
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		// 设置分页
		DefaultPage page = new DefaultPage(currentPage,pageSize);
		queryFilter.setPage(page);
		// 设置排序
		if (StringUtil.isNotEmpty(orderField) && StringUtil.isNotEmpty(orderSeq))
		{
			List<FieldSort> fieldSorts = new ArrayList<FieldSort>();
			fieldSorts.add(new DefaultFieldSort(orderField, Direction.fromString(orderSeq)));
			queryFilter.setFieldSortList(fieldSorts);
		}
		
		try {
			if(BeanUtils.isNotEmpty(paramMap)){
				FieldLogic andFieldLogic = RestParamUtil.getFieldLogic(paramMap, null);
				if(BeanUtils.isNotEmpty(andFieldLogic)){
					queryFilter.setFieldLogic(andFieldLogic);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return queryFilter;
	}

	@Override
	public PageList<DefaultBpmProcessInstance> getInstanceList(
			BaseQueryFilter baseQueryFilter) throws Exception {
		String account = baseQueryFilter.getAccount();
		ServiceUtil.setCurrentUser(account);
		DefaultQueryFilter queryFilter = buildDefaultFilter(baseQueryFilter);
		return (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.query(queryFilter);
	}
	
}
