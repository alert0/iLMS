package com.hotent.bpmx.webservice.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.string.StringValidator;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.exception.HotentHttpStatus;
import com.hotent.base.exception.RequiredException;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.BaseActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.ProcessInstanceStatus;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmDefExtProperties;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeForm;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.api.service.BpmOpinionService;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.api.service.DiagramService;
import com.hotent.bpmx.api.service.FlowStatusService;
import com.hotent.bpmx.core.engine.def.impl.handler.PropertiesBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.inst.DefaultProcessInstCmd;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.model.var.DefaultBpmVariableDef;
import com.hotent.bpmx.core.util.BpmIdentityUtil;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.dao.BpmExeStackRelationDao;
import com.hotent.bpmx.persistence.manager.BpmApprovalItemManager;
import com.hotent.bpmx.persistence.manager.BpmBusLinkManager;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.BpmBusLink;
import com.hotent.bpmx.persistence.model.BpmExeStackRelation;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProStatus;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.bpmx.webservice.api.IProcessService;
import com.hotent.bpmx.webservice.model.BasicResult;
import com.hotent.bpmx.webservice.model.BpmIdentityResult;
import com.hotent.bpmx.webservice.model.BpmTaskResult;
import com.hotent.bpmx.webservice.model.StartResult;
import com.hotent.form.api.model.Form;
import com.hotent.form.api.model.FormCategory;
import com.hotent.org.api.model.IUser;
import com.hotent.restful.params.BpmImageParamObject;
import com.hotent.restful.params.DefOtherParam;
import com.hotent.restful.params.DoEndParamObject;
import com.hotent.restful.params.DoNextParamObject;
import com.hotent.restful.params.StartFlowParamObject;
import com.hotent.restful.params.CommonResult;
import com.hotent.restful.vo.BpmCheckOpinionVo;
import com.hotent.sys.util.ContextUtil;

@Service("IProcessService")
public class ProcessServiceImpl implements IProcessService {
	@Resource
	BpmOpinionService bpmOpinionService;
	@Resource
	BpmInstService processInstanceService;
	@Resource
	BpmTaskService bpmTaskService;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmTaskActionService bpmTaskActionService;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmExeStackRelationDao relationDao ;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	PropertiesBpmDefXmlHandler propertiesBpmDefXmlHandler;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmInstService bpmInstService;
	@Resource
	BpmApprovalItemManager bpmApprovalItemManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	BpmProStatusManager bpmProStatusManagerl;
	@Resource
	BpmBusLinkManager bpmBusLinkManager;
	@Resource
	NatTaskService natTaskService;
	@Resource
	BpmFormService bpmFormService;
	
	@Override
	public StartResult start(StartFlowParamObject startFlowParamObject) throws Exception {
		ContextUtil.clearAll();
		// 传入的账户
		String account = startFlowParamObject.getAccount();
		ServiceUtil.setCurrentUser(account);
		String defId = startFlowParamObject.getDefId();
		String flowKey = startFlowParamObject.getFlowKey();
		String subject = startFlowParamObject.getSubject();
		String sysCode = startFlowParamObject.getSysCode();
		// 已有实例，则是从草稿中启动
		String proInstId = startFlowParamObject.getProcInstId();
		
		Map<String,String> vars = startFlowParamObject.getVars();
		// 表单数据，先解密
		String busData = "";
		if(StringUtil.isNotEmpty(startFlowParamObject.getData())){
			busData = Base64.getFromBase64(startFlowParamObject.getData());
		}
		// 表单的业务键
		String businessKey = startFlowParamObject.getBusinessKey();
		// 流程key和定义id二选一。
		if (StringUtil.isEmpty(defId) && StringUtil.isEmpty(flowKey)) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description() + ":" + "流程定义ID和流程key必须填写一个!");
		}
		
		if(StringUtil.isNotEmpty(proInstId)){
			DefaultBpmProcessInstance processInstance = bpmProcessInstanceManager.get(proInstId);
			if(BeanUtils.isNotEmpty(processInstance)){
				if(!ProcessInstanceStatus.STATUS_DRAFT.getKey().equals(processInstance.getStatus())){
					throw new RuntimeException("该实例id对应的流程实例不是草稿状态");
				}
			}
		}
		
		DefaultProcessInstCmd processCmd = new DefaultProcessInstCmd();
		if (StringUtil.isNotEmpty(businessKey)) {
			processCmd.setBusinessKey(businessKey);
		}
		
		
		if (StringUtil.isNotEmpty(defId)) {
			processCmd.setProcDefId(defId);
		}
		if (StringUtil.isEmpty(defId) && StringUtil.isNotEmpty(flowKey)) {
			defId = bpmDefinitionService.getBpmDefinitionByDefKey(flowKey, false).getDefId();
			processCmd.setFlowKey(flowKey);
		}
		
		if (StringUtil.isNotEmpty(subject)) {
			processCmd.setSubject(subject);
		}
		// 表单数据
		processCmd.setBusData(busData);
		
		if(StringUtil.isNotEmpty(sysCode)){
			processCmd.setSysCode(sysCode);
		}
		//设置表单类型
		processCmd.setDataMode(getFormType(defId));
		// 校验流程变量。
		setValriablesToCmd(defId,"",vars,processCmd);

		ContextUtil.setCurrentUserByAccount(account);
		if (StringUtil.isNotEmpty(proInstId)) {
			processCmd.setInstId(proInstId);
		}
		BpmProcessInstance instance = processInstanceService.startProcessInst(processCmd);
		String instId = instance.getId();
		return new StartResult("流程启动成功", instId);
	}
	
	
	/**
	 * 获取表单类型
	 * @param defId
	 * @return
	 */
	private String getFormType(String defId){
		String formType = ActionCmd.DATA_MODE_BO;
		try {
			BpmNodeForm nodeForm = bpmFormService.getByDefId(defId);
			if(BeanUtils.isNotEmpty(nodeForm)){
				Form form = nodeForm.getForm();
				if(BeanUtils.isNotEmpty(form)){
					FormCategory type = form.getType();
					if(FormCategory.FRAME.equals(type)){
						formType = ActionCmd.DATA_MODE_PK;
					}
				}
			}
		} catch (Exception e) {}
		return formType;
	}
	
	/**
	 * 获取合法的流程变量。
	 * @param defId
	 * @param vars
	 * @return
	 * @throws Exception 
	 */
	private Map<String,Object> getActVars(String defId,String nodeId, Map<String,String> varMap) throws Exception{
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		List<BpmVariableDef> bpmVariableList=defExt.getVariableList() ;
		if(bpmVariableList==null) bpmVariableList=new ArrayList<BpmVariableDef>();
		
		Map<String,Object> rtnMap=new HashMap<String, Object>();
		
		if(StringUtil.isNotEmpty(nodeId)){
			bpmVariableList.addAll(defExt.getVariableList(nodeId)) ;
		}
		for (BpmVariableDef var : bpmVariableList) {
			String varkey=var.getVarKey();
			//变量必填
//			if(var.isRequired()) {
//				if(!varMap.containsKey(varkey)){
//					throw new Exception("变量:[" +varkey +"]必填");
//				}
//			}
			String val=varMap.get(varkey);
			if(val!=null){
				Object rtnVal=DefaultBpmVariableDef.getValue(var.getDataType(), val);
				rtnMap.put(varkey, rtnVal);
			}
		}
		return rtnMap;
	}
	
	
	
	@Override
	public BasicResult doNext(DoNextParamObject doNextParamObject) throws Exception{
		// 清理上下文线程变量。
		ContextUtil.clearAll();

		// 传入的账户
		String account = doNextParamObject.getAccount();
		ServiceUtil.setCurrentUser(account);
		String taskId = doNextParamObject.getTaskId();
		String actionName = doNextParamObject.getActionName();
		String opinion = doNextParamObject.getOpinion();
		String data = "";
		String base64data = doNextParamObject.getData();
		if(StringUtil.isNotEmpty(base64data)){
			data = Base64.getFromBase64(base64data);
		}
		// 会签直接处理，当值为true时 ，会签直接完成不用等其他人进行审批。
		Boolean directHandlerSign = doNextParamObject.getDirectHandlerSign();
		// 退回模式
		String backHandMode = doNextParamObject.getBackHandMode();
		String jumpType = doNextParamObject.getJumpType();
		if (bpmTaskService.getByTaskId(taskId) == null) {
			// 返回错误消息。
			throw new RuntimeException("此任务已被处理或不存在");
		}
		DefaultTaskFinishCmd cmd = new DefaultTaskFinishCmd();
		// 自由跳转 或者 驳回到指定节点
		if ("free".equals(jumpType) || "select".equals(jumpType) || "reject".equals(actionName)) {
			//跳转的目标节点，传入节点id
			String destination = doNextParamObject.getDestination();
			if (StringUtil.isNotEmpty(destination)) {
				cmd.setDestination(destination);
			}
		}
		// 会签任务的直接处理
		if (directHandlerSign) {
			cmd.addTransitVars(BpmConstants.SIGN_DIRECT, "1");
		}
		cmd.setTaskId(taskId);
		cmd.setActionName(actionName);
		// 设置表单意见。
		cmd.setApprovalOpinion(opinion);
		// 处理表单意见，如果表单的意见存在则覆盖之前的意见。 覆盖业务表单数据
		if (StringUtil.isNotEmpty(data)) {
			BpmUtil.handOpinion(data, cmd);
			cmd.setBusData(data);
			cmd.setDataMode(ActionCmd.DATA_MODE_BO);
		}

		// 流程变量
		BpmTask bpmTask = (BpmTask) cmd.getTransitVars(BpmConstants.BPM_TASK);
		Map<String,String> vars = doNextParamObject.getVars();
		// 校验流程变量。
		setValriablesToCmd(bpmTask.getProcDefId(),bpmTask.getNodeId(),vars,cmd);

		// 设置流程驳回时跳转模式。direct：回到本节点，按流程图执行
		cmd.addTransitVars(BpmConstants.BACK_HAND_MODE, backHandMode);

		// 设置节点人员
		String nodeUsers = "";
		if(StringUtil.isNotEmpty(doNextParamObject.getNodeUsers())){
			nodeUsers = Base64.getFromBase64(doNextParamObject.getNodeUsers());
		}
		if (StringUtil.isNotEmpty(nodeUsers)) {
			Map<String, List<BpmIdentity>> specUserMap = BpmIdentityUtil.getBpmIdentity(nodeUsers);
			cmd.addTransitVars(BpmConstants.BPM_NODE_USERS, specUserMap);
			cmd.setBpmIdentities(specUserMap);
		}
		// 设置上下文执行人
		ContextUtil.setCurrentUserByAccount(account);
		bpmTaskActionService.finishTask(cmd);

		return new BasicResult("任务处理成功");
		
	}
	
	/**
	 * 设置流程变量。
	 * @param defId		流程定义ID
	 * @param nodeId	节点ID
	 * @param vars		流程变量 格式
	 * @param cmd		
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void setVariables(String defId,String nodeId,String vars,BaseActionCmd cmd) throws Exception{
		Map<String, String> variables = convertVars(vars);
		//检查流程变量合法性。
		Map<String, Object> varMap= getActVars(defId, nodeId, variables);
		// 流程变量
		if (BeanUtils.isNotEmpty(varMap)) {
			cmd.setVariables(varMap);
		}
	}
	
	private void setValriablesToCmd(String defId,String nodeId ,Map<String,String> variables,BaseActionCmd cmd) throws Exception{
		// 检查流程变量合法性。
		if(BeanUtils.isEmpty(variables)) return;
		Map<String, Object> varMap = getActVars(defId, nodeId, variables);
		// 流程变量
		if (BeanUtils.isNotEmpty(varMap)) {
			cmd.setVariables(varMap);
		}
	}
	
	/**
	 * 根据指定taskID获取可驳回的节点。
	 * <pre>
	 * json 格式: {taskId:"",rejectType:"direct,normal"}
	 * rejectType:返回方式 
	 * direct ：直来直往
	 * normal ：按照流程图执行
	 * </pre>
	 */
	@Override
	public List<JSONObject> getEnableRejectNode(String taskId,String rejectType) throws Exception {
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		String procInstId = task.getProcInstId();
		List<BpmNodeDef> listBpmNodeDef = BpmStackRelationUtil.getHistoryListBpmNodeDef(procInstId, task.getNodeId(),
				"pre");
		List<JSONObject> list = new ArrayList<JSONObject>();
		if (rejectType.equals("direct")) {
			// 允许直来直往的节点
			for (BpmNodeDef node : listBpmNodeDef) {
				if (node.getType().equals(NodeType.USERTASK)) {
					JSONObject def = new JSONObject();
					def.put("nodeId", node.getNodeId());
					def.put("nodeName", node.getName());
					list.add(def);
				}
			}
		} else {
			// 允许按流程图执行的节点
			List<BpmExeStackRelation> relationList = relationDao.getListByProcInstId(procInstId);
			for (BpmNodeDef node : listBpmNodeDef) {
				if (node.getType().equals(NodeType.USERTASK)) {
					boolean isHavePre = BpmStackRelationUtil.isHaveAndOrGateway(procInstId, node.getNodeId(), "pre",
							relationList);
					boolean isHaveAfter = BpmStackRelationUtil.isHaveAndOrGateway(procInstId, node.getNodeId(), "after",
							relationList);
					if (!(isHavePre && isHaveAfter)) {
						JSONObject def = new JSONObject();
						def.put("nodeId", node.getNodeId());
						def.put("nodeName", node.getName());
						list.add(def);
					}
				}
			}
		}
		return list;
		
	}
	
	
	/**
	 * 将流程变量从一个json数据格式转换成一个map的结构。
	 * @param varsJson 变量格式如下: [{name:"",val:""},{name:"",val:""}]
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> convertVars(String varsJson) throws Exception {
		Map<String, String> varMap = new HashMap<String, String>();
		
		if(StringUtil.isEmpty(varsJson)) return varMap;
		
		JSONArray array = JSONArray.fromObject(varsJson);
		for (int i = 0; i < array.size(); i++) {
			JSONObject var = array.getJSONObject(i);
			String name = JsonUtil.getString(var, "name");
			String value = JsonUtil.getString(var, "val");
			if (StringUtil.isEmpty(name)) {
				throw new Exception("流程变量的变量名不能为空");
			}
		}
		return varMap;
	}
	

	
	@Override
	public List<BpmTaskOpinion> getHistoryOpinion(String procInstId,String taskId) throws Exception {
		
		// 流程key和定义id二选一。
		if (StringUtil.isEmpty(procInstId) && StringUtil.isEmpty(taskId)) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description() + ":procInstId和taskId必须填写一个!");
		}

		if ((procInstId == null || procInstId.isEmpty()) && taskId != null) {
			BpmTask bpmTask = bpmTaskService.getByTaskId(taskId);
			if(BeanUtils.isEmpty(bpmTask)){
				throw new RuntimeException("任务不存在或已经被处理！");
			}
			procInstId = bpmTaskService.getByTaskId(taskId).getProcInstId();
		}
		List<BpmTaskOpinion> list = bpmOpinionService.getTaskOpinions(procInstId);
		return list;
		
	}
	
	@Override
	public CommonResult<String> getBusinessKey(String procInstanceId,String taskId) throws NullPointerException {
		if ((StringUtil.isEmpty(procInstanceId)) && StringUtil.isNotEmpty(taskId)) {
			procInstanceId = bpmTaskService.getByTaskId(taskId).getProcInstId();
		}
		DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(procInstanceId);
		if(BeanUtils.isEmpty(instance)){
			throw new RuntimeException("根据输入的信息没有找到流程实例数据！");
		}
		return new CommonResult<String>(true, "业务主键--"+instance.getBizKey(), instance.getBizKey());
	}

	/**
	 * 根据BussinessKey获取流程实例ID
	 * @param businessKey 
	 * @return
	 */
	@Override
	public CommonResult<String> getProcInstId(String businessKey) throws NullPointerException {
		BpmProcessInstance instance= bpmProcessInstanceManager.getByBusinessKey(businessKey);
		if(BeanUtils.isEmpty(instance)){
			throw new NullPointerException("根据输入的businessKey找不到对应的实例数据！");
		}
		return new CommonResult<String>(true,  "流程实例id--"+instance.getId(),instance.getId());
	}
	
	@Override
	public CommonResult<String> doEndProcess(DoEndParamObject doEndParamObject) throws Exception {
		// 传入的账户
		String account = doEndParamObject.getAccount();
		if (StringUtil.isEmpty(account)) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description() + ":用户帐号必填");
		}
		ServiceUtil.setCurrentUser(account);
		String taskId = doEndParamObject.getTaskId();
		if (StringUtil.isEmpty(taskId)) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description() + ":任务id必填");
		}
		String endReason = doEndParamObject.getEndReason();
		if (StringUtil.isEmpty(endReason)) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description() + ":终止理由必填");
		}
		// 消息通知类型：inner内部消息，mail邮件，sms短信,多个之单使用英文逗号隔开
		String messageType = StringUtil.isNotEmpty(doEndParamObject.getMessageType()) ? doEndParamObject.getMessageType()
				: "mail";
		if(BeanUtils.isEmpty(bpmTaskManager.get(taskId))){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		ContextUtil.setCurrentUserByAccount(account);
		IUser user = ServiceUtil.getUserByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的用户帐号，找不到用户！");
		}
		bpmTaskManager.endProcessByTaskId(taskId, messageType, endReason);

		return new CommonResult<String>(true, "已终止流程", "");
	}

	@Override
	public CommonResult<String> saveDraft(StartFlowParamObject startFlowParamObject) throws Exception {
		ContextUtil.clearAll();
		// 传入的账户
		String account = startFlowParamObject.getAccount();
		ServiceUtil.setCurrentUser(account);

		String defId = startFlowParamObject.getDefId();
		String flowKey = startFlowParamObject.getFlowKey();
		String subject = startFlowParamObject.getSubject();
		// 已有实例，则修改草稿数据
		String proInstId = startFlowParamObject.getProcInstId();
		// vars:[{"name":"name","val":"ray"},{"name":"sex","val":"male"}]
		Map<String,String> vars = startFlowParamObject.getVars();
		// 表单数据，先解密
		String busData = Base64.getFromBase64(startFlowParamObject.getData());
		// 表单的业务键
		String businessKey = startFlowParamObject.getBusinessKey();
		// 流程key和定义id二选一。
		if (StringUtil.isEmpty(defId) && StringUtil.isEmpty(flowKey)) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description() + ":" + "流程定义ID和流程key必须填写一个!");
		}
		DefaultProcessInstCmd processCmd = new DefaultProcessInstCmd();
		if (StringUtil.isNotEmpty(businessKey)) {
			processCmd.setBusinessKey(businessKey);
			processCmd.setSysCode(startFlowParamObject.getSysCode());
			processCmd.setDataMode(ActionCmd.DATA_MODE_PK);
		}
		if (StringUtil.isNotEmpty(defId)) {
			processCmd.setProcDefId(defId);
		}
		if(StringUtil.isNotEmpty(defId) && BeanUtils.isEmpty(bpmDefinitionService.getBpmDefinitionByDefKey(flowKey, false))){
			throw new RuntimeException("根据defId拿不到对应的流程定义");
		}
		if(StringUtil.isEmpty(defId) && StringUtil.isNotEmpty(flowKey) && BeanUtils.isEmpty(bpmDefinitionService.getBpmDefinitionByDefKey(flowKey, false))){
			throw new RuntimeException("根据flowKey拿不到对应的流程定义");
		}
		
		DefaultBpmDefinition definiton = null;
		if(StringUtil.isNotEmpty(defId)){
			definiton = bpmDefinitionManager.getByBpmnDefId(defId);
		}else if(StringUtil.isEmpty(defId) && StringUtil.isNotEmpty(flowKey)){
			definiton = bpmDefinitionManager.getMainByDefKey(flowKey);
		}
		//判断传入的用户是否该流程的操作权限
		ContextUtil.setCurrentUserByAccount(account);
		IUser user = ServiceUtil.getUserByAccount(account);
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		List<DefaultBpmDefinition> list = bpmDefinitionManager.queryList(queryFilter, user);
		Boolean flag = true;
		for(int i=0;i<list.size();i++){
			DefaultBpmDefinition b = list.get(i);
			if(b.getId().equals(definiton.getId())){
				flag = false;
			}
			if((i+1) == list.size() && flag){
				throw new RuntimeException("用户【"+account+"】不能启动该流程，请先为其分配流程权限");
			}
		}
		
		if (StringUtil.isEmpty(defId) && StringUtil.isNotEmpty(flowKey)) {
			defId = bpmDefinitionService.getBpmDefinitionByDefKey(flowKey, false).getDefId();
			processCmd.setFlowKey(flowKey);
		}
		if (StringUtil.isNotEmpty(subject)) {
			processCmd.setSubject(subject);
		}
		// 表单数据
		processCmd.setBusData(busData);
		if (StringUtil.isNotEmpty(busData)) {
			processCmd.setDataMode(ActionCmd.DATA_MODE_BO);
		}
		// 校验流程变量。
		setValriablesToCmd(defId,"",vars,processCmd);

		ContextUtil.setCurrentUserByAccount(account);
		if (StringUtil.isNotEmpty(proInstId)) {
			processCmd.setInstId(proInstId);
		}
		BpmProcessInstance instance = processInstanceService.saveDraft(processCmd);
		return new CommonResult<String>(true, "保存草稿成功", instance.getId());
	}

	@Override
	public CommonResult<String> setDefOtherParam(DefOtherParam defOtherParam) throws Exception {
		String defId = defOtherParam.getDefId();
		String description = defOtherParam.getDescription();
		com.alibaba.fastjson.JSON json = com.alibaba.fastjson.JSONObject.parseObject(defOtherParam.toString());
		BpmDefExtProperties prop = (BpmDefExtProperties)com.alibaba.fastjson.JSONObject.toJavaObject(json, BpmDefExtProperties.class);

		String status = prop.getStatus();

		propertiesBpmDefXmlHandler.saveNodeXml(defId, "", prop);
		// 更新测试状态
		DefaultBpmDefinition bpmDefinition = bpmDefinitionManager.get(defId);

		// 更新状态
		String oldStatus = bpmDefinition.getStatus();
		if(!BpmDefinition.STATUS.DRAFT.equals(oldStatus) && BpmDefinition.STATUS.DRAFT.equals(status)){
			throw new RuntimeException("该流程状态为非草稿状态，不能设置成草稿状态！");
		}

		bpmDefinition.setTestStatus(prop.getTestStatus());
		bpmDefinition.setStatus(status);
		bpmDefinition.setDesc(description);
		
		bpmDefinitionManager.update(bpmDefinition);

		bpmDefinitionManager.updBpmDefinitionStatus(bpmDefinition, oldStatus);
		
		bpmDefinitionAccessor.clean(defId);
		return new CommonResult<String>(true, "设置流程其他参数成功！", "");
	}

	@Override
	public List<String> getApprovalItems(String taskId) throws Exception {
		if(StringUtil.isEmpty(taskId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":任务id必填");
		}
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(BeanUtils.isEmpty(task)){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		String defKey = bpmProcessInstance.getProcDefKey();
		String typeId = task.getTypeId();
		List<String> approvalItem  = bpmApprovalItemManager.getApprovalByDefKeyAndTypeId(defKey, typeId);
		return approvalItem;
	}

	@Override
	public List<BpmIdentityResult> getNodeUsers(String taskId) throws Exception {
		if(StringUtil.isEmpty(taskId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":任务id必填");
		}
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(BeanUtils.isEmpty(task)){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		String instanceId = bpmProcessInstance.getId();//流程实例id
		String nodeId = task.getNodeId();//任务节点id
		List<BpmTaskOpinion> opinionList = bpmOpinionService.getByInstNodeId(instanceId, nodeId);
		List<BpmIdentityResult> result = new ArrayList<BpmIdentityResult>();
		for(BpmTaskOpinion b : opinionList){
			String qualfieds = b.getQualfieds();
			if(b.getCompleteTime()!=null){
				continue;
			}
			result.addAll(qualfields2BpmIdentityResult(qualfieds));
		}
		BeanUtils.removeDuplicate(result);
		return result;
	}
	
	private List<BpmIdentityResult> qualfields2BpmIdentityResult(String qualfieds) throws Exception{
		List<BpmIdentityResult> result = new ArrayList<BpmIdentityResult>();
		if(StringUtil.isEmpty(qualfieds)){
			return result;
		}
		JsonElement parse = GsonUtil.parse(qualfieds);
		if(BeanUtils.isNotEmpty(parse) && parse.isJsonArray()){
			JsonArray jsonArray = parse.getAsJsonArray();
			for (JsonElement jsonElement : jsonArray) {
				if(BeanUtils.isEmpty(jsonElement) || !jsonElement.isJsonObject()){
					continue;
				}
				JsonObject jobject = jsonElement.getAsJsonObject();
				JsonElement typeObj = jobject.get("type");
				if(BeanUtils.isNotEmpty(typeObj) && typeObj.isJsonPrimitive()){
					if("user".equals(typeObj.getAsString())){
						JsonElement idObj = jobject.get("id");
						if(BeanUtils.isNotEmpty(idObj) && idObj.isJsonPrimitive()){
							IUser user = ServiceUtil.getUserById(idObj.getAsString());
							result.add(new BpmIdentityResult(user));
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<BpmCheckOpinionVo> getProcessOpinionByActInstId(String actTaskId) throws Exception {
		if(StringUtil.isEmpty(actTaskId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":actTaskId必填！");
		}
		BpmProcessInstance process = bpmProcessInstanceManager.getByBpmnInstId(actTaskId);
		if(BeanUtils.isEmpty(process)){
			throw new RuntimeException("根据sctTaskId找不到对应的数据！");
		}
		List<DefaultBpmCheckOpinion> bpmCheckOpinionList = bpmCheckOpinionManager.getByInstId(process.getId());
		List<BpmCheckOpinionVo> checkOpinionVoList = new ArrayList<BpmCheckOpinionVo>();
		for(DefaultBpmCheckOpinion o : bpmCheckOpinionList){
			o.setQualfieds("");
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSON(o).toString());
			BpmCheckOpinionVo b = com.alibaba.fastjson.JSONObject.toJavaObject(object, BpmCheckOpinionVo.class);
			checkOpinionVoList.add(b);
		}
		return checkOpinionVoList;
	}

	@Override
	public BpmProcessInstance getProcessRunByTaskId(String taskId) throws Exception {
		if(StringUtil.isEmpty(taskId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":任务id必填");
		}
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(BeanUtils.isEmpty(task)){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		return bpmProcessInstance;
	}

	@Override
	public String getStatusByRunidNodeId(String instId, String nodeId) throws Exception {
		if(StringUtil.isEmpty(instId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：instId流程实例id必填！");
		}
		if(StringUtil.isEmpty(nodeId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：nodeId对应节点id必填！");
		}
		DefaultBpmProStatus proStatus = bpmProStatusManagerl.getByInstNodeId(instId, nodeId);
		if(BeanUtils.isEmpty(proStatus)){
			throw new RuntimeException("未获取到该实例该节点的状态！");
		}
		String status = "";
		if(NodeStatus.SUBMIT.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.SUBMIT.getValue();
		}
		if(NodeStatus.RE_SUBMIT.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.RE_SUBMIT.getValue();
		}
		if(NodeStatus.AGREE.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.AGREE.getValue();
		}
		if(NodeStatus.PENDING.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.PENDING.getValue();
		}
		if(NodeStatus.OPPOSE.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.OPPOSE.getValue();
		}
		if(NodeStatus.BACK.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.BACK.getValue();
		}
		if(NodeStatus.BACK_TO_START.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.BACK_TO_START.getValue();
		}
		if(NodeStatus.COMPLETE.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.COMPLETE.getValue();
		}
		if(NodeStatus.RECOVER.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.RECOVER.getValue();
		}
		if(NodeStatus.RECOVER_TO_START.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.RECOVER_TO_START.getValue();
		}
		if(NodeStatus.SIGN_PASS.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.SIGN_PASS.getValue();
		}
		if(NodeStatus.SIGN_NO_PASS.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.SIGN_NO_PASS.getValue();
		}
		if(NodeStatus.MANUAL_END.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.MANUAL_END.getValue();
		}
		if(NodeStatus.ABANDON.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.ABANDON.getValue();
		}
		if(NodeStatus.SUSPEND.getKey().equals(proStatus.getStatus())){
			status = proStatus.getStatus() + "——"+NodeStatus.SUSPEND.getValue();
		}
		return status;
	}

	@Override
	public BpmTaskResult getTaskByTaskId(String taskId) throws Exception {
		if(StringUtil.isEmpty(taskId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":任务id必填");
		}
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(BeanUtils.isEmpty(task)){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		return new BpmTaskResult(task);
	}

	@Override
	public String getTaskNameByTaskId(String taskId) throws Exception {
		BpmTaskResult taskByTaskId = getTaskByTaskId(taskId);
		return taskByTaskId.getTaskName();
	}

	@Override
	public DefaultBpmProcessInstance getInstancetByBusinessKey(String businessKey) throws Exception {
		if(StringUtil.isEmpty(businessKey)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：businessKey必填");
		}
		DefaultBpmProcessInstance instance = bpmProcessInstanceManager.getByBusinessKey(businessKey);
		
		if(BeanUtils.isEmpty(instance)){
			instance = this.getInstanceFromBusLink(businessKey,null);
			if(BeanUtils.isEmpty(instance)){
				throw new RuntimeException("根据【"+businessKey+"】没有找到对应的数据");
			}
		}
		return instance;
	}
	
	@Override
	public DefaultBpmProcessInstance getInstancetByBizKeySysCode(String businessKey,String sysCode) throws Exception {
		if(StringUtil.isEmpty(businessKey)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：businessKey必填");
		}
		if(StringUtil.isEmpty(sysCode)){
			return this.getInstancetByBusinessKey(businessKey);
		}
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("biz_key_", businessKey, QueryOP.EQUAL);
		queryFilter.addFilter("sys_code_", sysCode, QueryOP.EQUAL);
		List<DefaultBpmProcessInstance> list = bpmProcessInstanceManager.query(queryFilter);
		if(BeanUtils.isEmpty(list)){
			DefaultBpmProcessInstance processInstance = getInstanceFromBusLink(businessKey, sysCode);
			if(BeanUtils.isEmpty(processInstance)){
				throw new RuntimeException("根据businessKey【"+businessKey+"】、sysCode【"+sysCode+"】没有找到对应的数据");
			}
			return processInstance;
		}else{
			if(list.size()>1){
				throw new RuntimeException("根据businessKey【"+businessKey+"】、sysCode【"+sysCode+"】找到了多（"+list.size()+"）条对应的数据");
			}else{
				return list.get(0);
			}
		}
	}
	
	/**
	 * 通过businessKey在BpmBusLink中查询流程实例
	 * @param businessKey
	 * @return
	 */
	private DefaultBpmProcessInstance getInstanceFromBusLink(String businessKey,String sysCode){
		DefaultBpmProcessInstance processInstance = null;
		if(StringUtil.isNotEmpty(businessKey)){
			DefaultQueryFilter queryFilter = new DefaultQueryFilter();
			queryFilter.addFilter("businesskey_str_", businessKey, QueryOP.EQUAL);
			if(StringUtil.isNotEmpty(sysCode)){
				queryFilter.addFilter("sys_code_", sysCode, QueryOP.EQUAL);
			}
			List<BpmBusLink> bpmBusLinks = bpmBusLinkManager.query(queryFilter);
			if(BeanUtils.isEmpty(bpmBusLinks)&&StringValidator.isNumberic(businessKey)){
				DefaultQueryFilter queryFilter2 = new DefaultQueryFilter();
				queryFilter2.addFilter("businesskey_", businessKey, QueryOP.EQUAL);
				if(StringUtil.isNotEmpty(sysCode)){
					queryFilter2.addFilter("sys_code_", sysCode, QueryOP.EQUAL);
				}
				bpmBusLinks = bpmBusLinkManager.query(queryFilter2);
			}
			if(BeanUtils.isNotEmpty(bpmBusLinks)){
				List<DefaultBpmProcessInstance> proInsts = new ArrayList<DefaultBpmProcessInstance>();
				for (BpmBusLink bpmBusLink : bpmBusLinks) {
					DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(bpmBusLink.getProcInstId());
					if(BeanUtils.isNotEmpty(instance)){
						proInsts.add(instance);
					}
				}
				HashSet h = new HashSet(proInsts);
				proInsts.clear();
				proInsts.addAll(h);
				if(proInsts.size()>1){
					throw new RuntimeException("根据businessKey【"+businessKey+"】、sysCode【"+sysCode+"】找到了多（"+proInsts.size()+"）条对应的数据");
				}else{
					if(BeanUtils.isNotEmpty(proInsts)){
						processInstance = proInsts.get(0);
					}
				}
			}
		}
		return processInstance;
	}
	

	@Override
	public DefaultBpmProcessInstance getInstanceByInstId(String instId) throws Exception {
		if(StringUtil.isEmpty(instId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：instId流程实例id必填");
		}
		DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(instId);
		if(BeanUtils.isEmpty(instance)){
			throw new RuntimeException("根据【"+instId+"】没有找到对应的数据");
		}
		return instance;
	}

	@Override
	public JSONObject getInstanceListByXml(String xml) throws Exception {
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();
		String account = root.attributeValue("account"); // 用户账号
		ServiceUtil.setCurrentUser(account);
		String subject = root.attributeValue("subject");// 任务标题
		String status = root.attributeValue("status");// 流程实例状态
		String pageSize = root.attributeValue("pageSize");// 页数
		String currentPage = root.attributeValue("currentPage");// 当前页
		if(StringUtil.isNotEmpty(account)){
			ContextUtil.setCurrentUserByAccount(account);
			IUser user = ServiceUtil.getUserByAccount(account);
			if(BeanUtils.isEmpty(user)){
				throw new RuntimeException("用户不存在");
			}
		}
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		// 设置分页
		DefaultPage page = new DefaultPage(1,20);
		if (StringUtil.isNotEmpty(pageSize)) {
			page.setLimit(new Integer(pageSize));
		}
		if (StringUtil.isNotEmpty(currentPage)) {
			page.setPage(new Integer(currentPage));
		}
		queryFilter.setPage(page);
		if(StringUtil.isNotEmpty(status)){
			queryFilter.addFilter("status_",status,QueryOP.EQUAL);
		}
		if(StringUtil.isNotEmpty(subject)){
			queryFilter.addFilter("subject_",subject,QueryOP.LIKE);
		}
		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>)bpmProcessInstanceManager.queryList(queryFilter);
		JSONObject result = new JSONObject();
		ServiceUtil.buildListReuslt(result,list, queryFilter);
		return result;
	}

	@Override
	public JSONObject getTasksByInstId(String instId) throws Exception {
		if(StringUtil.isEmpty(instId)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：instId实例id必填！");
		}
		List<DefaultBpmTask> taskList = bpmTaskManager.getByInstId(instId);
		if(BeanUtils.isEmpty(taskList)){
			throw new RuntimeException("根据实例id【"+instId+"】没有找到任务数据！");
		}
		JSONObject result = new JSONObject();
		result.put("size", taskList.size());
		String json=com.alibaba.fastjson.JSONObject.toJSONString(taskList);
		result.put("taskList", json);
		return result;
	}

	@Override
	public JSONArray getTaskOutNodes(String taskId) throws Exception {
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(BeanUtils.isEmpty(task)){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		String defId = bpmProcessInstance.getProcDefId();//流程定义id
		String nodeId = task.getNodeId();//任务节点id
		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<BpmNodeDef> nodes = taskNodeDef.getOutcomeNodes();
		List<BpmNodeDef> nextNodes = handlerSelectOutcomeNodes(nodes);
		JSONArray array = new JSONArray();
		for(BpmNodeDef n : nextNodes){
			JSONObject object = new JSONObject();
			object.put("nodeId", n.getNodeId());
			object.put("nodeName", n.getName());
			object.put("type", n.getType());
			object.put("typeDescription",n.getType().getValue());
			array.add(object);
		}
		return array;
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
	public String getDetailUrl(String taskId) throws Exception {
		ApplicationContext  applicationContext = AppUtil.getApplicaitonContext();
		String applicationName = applicationContext.getApplicationName();
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(BeanUtils.isEmpty(task)){
			throw new RuntimeException("任务不存在或已被处理！");
		}
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		if (!ActionCmd.DATA_MODE_BO.equals(bpmProcessInstance.getDataMode())){
			throw new RuntimeException("该任务对应的节点不是在线表单！");
		}
		String url = applicationName + "/flow/instance/instanceFlowForm?id="+bpmProcessInstance.getId();
		return url;
	}

	@Override
	public CommonResult<String> setTaskVar(String taskId, Map<String, Object> variables) throws Exception {
		natTaskService.setVariables(taskId, variables);
		return new CommonResult<String>(true, "设置成功", "");
	}

	@Override
	public CommonResult<String> setTaskVarLocal(String taskId, Map<String, Object> variables) throws Exception {
		natTaskService.setVariablesLocal(taskId, variables);
		return new CommonResult<String>(true, "设置成功", "");
	}


	@Override
	public CommonResult<String> forbiddenInstance(String instId) throws Exception {
		processInstanceService.suspendProcessInstance(instId);
		return new CommonResult<String>(true, "流程实例挂起成功", "");
	}


	@Override
	public CommonResult<String> unForbiddenInstance(String instId) throws Exception {
		processInstanceService.recoverProcessInstance(instId);
		return new CommonResult<String>(true, "流程实例取消挂起成功", "");
	}

	@Override
	public List<BpmProcessInstance> getBpmProcessByParentIdAndSuperNodeId(
			String parentInstId, String superNodeId) throws Exception {
		return bpmProcessInstanceManager.getBpmProcessByParentIdAndSuperNodeId(parentInstId, superNodeId);
	}
	
	@Override
	public List<DefaultBpmProcessInstance> getInstancesByParentId(
			String parentInstId, String status) throws Exception {
		DefaultQueryFilter queryFilter = new DefaultQueryFilter(); 
		queryFilter.addFilter("parent_inst_id_", parentInstId, QueryOP.EQUAL);
		if(StringUtil.isNotEmpty(status)){
			queryFilter.addFilter("status_", status, QueryOP.EQUAL);
		}
		return bpmProcessInstanceManager.queryList(queryFilter);
	}


	@Override
	public List<DefaultBpmProcessInstance> getInstancesByDefId(String defId,
			String status) throws Exception {
		DefaultQueryFilter queryFilter = new DefaultQueryFilter(); 
		queryFilter.addFilter("proc_def_id_", defId, QueryOP.EQUAL);
		if(StringUtil.isNotEmpty(status)){
			queryFilter.addFilter("status_", status, QueryOP.EQUAL);
		}
		return bpmProcessInstanceManager.queryList(queryFilter);
	}


	@Override
	public BpmProcessInstance getTopBpmProcessInstance(String instId)
			throws Exception {
		return bpmProcessInstanceManager.getTopBpmProcessInstance(instId);
	}


	@Override
	public String getBpmImage(BpmImageParamObject bpmImageParamObject)
			throws Exception {
		DiagramService diagramService = (DiagramService) AppUtil.getBean("diagramService");
		FlowStatusService flowStatusService = (FlowStatusService) AppUtil.getBean("flowStatusService");
		InputStream is = null;
		if (StringUtils.isNotEmpty(bpmImageParamObject.getDefId())) {
			is = diagramService.getDiagramByBpmnDefId(bpmImageParamObject.getDefId());
		} else if (StringUtils.isNotEmpty(bpmImageParamObject.getBpmnInstId())) {
			is = getDiagramByInstance(diagramService, flowStatusService, bpmImageParamObject.getBpmnInstId());
		} else if (StringUtils.isNotEmpty(bpmImageParamObject.getTaskId())) {
			BpmTaskService bpmTaskService = (BpmTaskService) AppUtil .getBean("defaultBpmTaskService");
			BpmTask bpmTask = bpmTaskService.getByTaskId(bpmImageParamObject.getTaskId());
			is = getDiagramByInstance(diagramService, flowStatusService, bpmTask.getBpmnInstId());
		} else if (StringUtils.isNotEmpty(bpmImageParamObject.getProcInstId())){
			BpmInstService instService = AppUtil.getBean(BpmInstService.class);
			BpmProcessInstance instance = instService.getProcessInstance(bpmImageParamObject.getProcInstId());
			is = getDiagramByInstance(diagramService, flowStatusService, instance.getBpmnInstId());
		}

		if (is == null)
			return "";
		byte[] data = null;
		try {
			data = new byte[is.available()];
			is.read(data);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(org.apache.commons.codec.binary.Base64.encodeBase64(data));
	}
	
	private InputStream getDiagramByInstance(DiagramService diagramService,
			FlowStatusService flowStatusService, String bpmnInstId) {
		BpmInstService bpmInstService = AppUtil.getBean(BpmInstService.class);
		BpmProcessInstanceManager bpmProcessInstanceManager = AppUtil.getBean(BpmProcessInstanceManager.class);
		BpmProcessInstance bpmProcessInstance = bpmInstService
				.getProcessInstanceByBpmnInstId(bpmnInstId);
		if(bpmProcessInstance==null)
			bpmProcessInstance=bpmProcessInstanceManager.getBpmProcessInstanceHistoryByBpmnInstId(bpmnInstId);
		Map<String, String> colorMap = flowStatusService
				.getProcessInstanceStatus(bpmProcessInstance.getId());
		return diagramService.getDiagramByDefId(
				bpmProcessInstance.getProcDefId(), colorMap);
	}

}
