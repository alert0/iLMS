package com.hotent.mini.controller.flow;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ExceptionUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmDefExtProperties;
import com.hotent.bpmx.api.model.process.def.BpmDefLayout;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.inst.BpmInstanceTrack;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeForm;
import com.hotent.bpmx.api.model.process.nodedef.ext.CallActivityNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.Button;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.BpmIdentityService;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.api.service.BpmOpinionService;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.api.service.DiagramService;
import com.hotent.bpmx.core.engine.form.BpmFormFactory;
import com.hotent.bpmx.core.engine.inst.DefaultProcessInstCmd;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.model.var.DefaultBpmVariableDef;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackRelationManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.CopyToManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.form.api.model.FormCategory;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.api.model.FormType;
import com.hotent.form.api.service.BpmFormRightsService;
import com.hotent.mini.controller.util.SysErrorUtil;
import com.hotent.mini.web.util.BpmIdentityUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.jms.MessageUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：流程实例管理
 * 构建组：x5-bpmx-platform
 * 作者:csx
 * 邮箱:csx@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/flow/instance/")
public class InstanceController extends GenericController{
	private static Logger log = LoggerFactory.getLogger(InstanceController.class);
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmInstService processInstanceService;
	@Resource
	DiagramService diagramService;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmIdentityService bpmIdentityService;
	@Resource
	BpmOpinionService bpmOpinionService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmFormRightsService bpmFormRightsService;
	@Resource
	BpmTaskService bpmTaskService;
	@Resource
	CopyToManager copyToManager;
	@Resource
	BoDataService boDataService;
	@Resource
	IUserService userServiceImpl;
	@Resource(name="boDbHandlerImpl")
	BoDataHandler boDataHandler;
	@Resource
	BpmExeStackRelationManager bpmExeStackRelationManager;
	@Resource
	BpmIdentityExtractService extractService;
	
	/**
	 * 获取流程实例的表单和数据。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInstFormAndBO")
	@ResponseBody
	public Object getInstFormAndBO(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String proInstId = request.getParameter("proInstId");
		String nodeId = request.getParameter("taskKey");
		
		BpmFormService  bpmFormService =BpmFormFactory.getFormService(FormType.PC);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(proInstId);
		
		BpmProcessInstance topInstance=bpmProcessInstanceManager.getTopBpmProcessInstance(instance);
		
		FormModel formModel = bpmFormService. getInstFormByDefId( instance);
		if(BeanUtils.isNotEmpty(formModel)&&(StringUtil.isNotEmpty(formModel.getFormId())||StringUtil.isNotEmpty(formModel.getFormValue()))){
			if(StringUtil.isNotEmpty(nodeId)){
				FormModel formModelTmp = bpmFormService.getByDefId(instance.getProcDefId(), nodeId, instance);
				if(formModelTmp!=null && !formModelTmp.isFormEmpty()) formModel = formModelTmp;
			}
			
			if(formModel==null || formModel.isFormEmpty()){
				map.put("result", "formEmpty");
				return map;
			}
			
			map.put("form", formModel);
			
			if(FormCategory.INNER.equals(formModel.getType())){
				//获取bo数据
				List<BoData> boDatas =boDataService.getDataByInst(instance);
				
				JSONObject jsondata=BoDataUtil.hanlerData(boDatas);
				map.put("data", jsondata);
				
				JSONObject opinionJson = boDataService.getFormOpinionJson(proInstId);
				map.put("opinionList", opinionJson);
				
				String curUserId=ContextUtil.getCurrentUserId();
				
				//获取最外层的权限。
				String permission = bpmFormRightsService.getInstPermission(formModel.getFormKey(), curUserId, 
						topInstance.getProcDefKey());
				map.put("permission", permission);
				
			}
		}
		return map;
	}
	
	
	/**
	 * 流程启动时获取bo和表单。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getFormAndBO")
	@ResponseBody
	public Object getFormAndBO(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   
		String defId = request.getParameter("defId");
		String proInstId = request.getParameter("proInstId");
		
		BpmFormService  bpmFormService =BpmFormFactory.getFormService(FormType.PC);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		DefaultBpmProcessInstance proInstance = null;
		
		BpmNodeForm nodeForm=null;
		if(StringUtil.isEmpty(proInstId)){
			nodeForm = bpmFormService.getByDefId(defId);
		}
		else{
			proInstance = bpmProcessInstanceManager.get(proInstId);
			nodeForm = bpmFormService.getByDraft(proInstance);
		}
		
		if(nodeForm==null ){
			map.put("result", "formEmpty");
			return map;
		}
		
		FormModel formModel=(FormModel) nodeForm.getForm();
		//
		map.put("form", formModel);
		
		BpmNodeDef bpmNodeDef =nodeForm.getBpmNodeDef();
		
		List<Button> buttons = BpmUtil.getButtons(bpmNodeDef);
		//按钮
		map.put("buttons", JSONArray.fromObject(buttons));
		
		if(FormCategory.INNER.equals(formModel.getType())){
			JSONObject jsondata=null;
			
			DefaultTaskFinishCmd cmd = new DefaultTaskFinishCmd();
			cmd.setVariables(this.getStartCmd(request).getVariables());
			ContextThreadUtil.setActionCmd(cmd);
			//从草稿发起流程。
			if(StringUtil.isNotEmpty(proInstId)){
				List<BoData> boDatas =boDataService.getDataByInst(proInstance);
				jsondata=BoDataUtil.hanlerData(defId,boDatas);
			}
			else{
				List<BoData> boDatas = boDataService.getDataByDefId(defId);
				jsondata=BoDataUtil.hanlerData(defId,boDatas);
			}
			map.put("data", jsondata);
			
			// 获取流程启动时的表单权限
			String permission = bpmFormRightsService.getStartPermission(formModel.getFormKey(),bpmNodeDef.getBpmProcessDef().getDefKey(),bpmNodeDef.getNodeId(),bpmNodeDef.getOutcomeNodes().get(0).getNodeId());
			
			map.put("permission", permission);
			
		}
		
		// 获取开始节点的配置信息  用与配置路径跳转
		BpmNodeDef startEvent = bpmDefinitionAccessor.getStartEvent(defId);
		NodeProperties startNodeProp = startEvent.getLocalProperties();
		
		map.put("jumpType", startNodeProp.getJumpType());
		
		return map;
	}
	
	/**
	 * 获取发起的cmd格式数据。
	 * @param request
	 * @return
	 */
	private DefaultProcessInstCmd getStartCmd(HttpServletRequest request){
		String proInstId = request.getParameter("proInstId");
		String defId=RequestUtil.getString(request,"defId");
		//是否由选择人员做为下一节点处理人
		int isSendNodeUsers=RequestUtil.getInt(request,"isSendNodeUsers",0);
		
		BpmDefinition bpmDefinition=bpmDefinitionService.getBpmDefinitionByDefId(defId);

		//TODO 判断流程状态，权限
		String defKey = bpmDefinition.getDefKey();
		//目标节点
		String destination=RequestUtil.getString(request, "destination","");
		//节点执行人 [{nodeId:"userTask1",executors:[{id:"",type:"org,user,pos", name:""},{id:"",type:"org,user,pos",name:""}]}]
		String nodeUsers=RequestUtil.getString(request, "nodeUsers");
		//流程表单数据。
		String busData = RequestUtil.getString(request, "data");
		String formType = RequestUtil.getString(request, "formType",FormCategory.INNER.value());
		
		
		Map<String, List<BpmIdentity>> specUserMap= BpmIdentityUtil.getBpmIdentity(nodeUsers);
		
		DefaultProcessInstCmd cmd=new DefaultProcessInstCmd();
		
		
		cmd.setFlowKey(defKey);
		if(StringUtil.isNotEmpty(proInstId)){
			cmd.setInstId(proInstId);
		}
		 
//		cmd.setBusinessKey(UniqueIdUtil.getSuid());
		cmd.setDestination(destination);
		cmd.setBusData(busData);
		if(FormCategory.INNER.value().equals(formType)){
			cmd.setDataMode(ActionCmd.DATA_MODE_BO);
		}
		else{
			cmd.setDataMode(ActionCmd.DATA_MODE_PK);
		}
		//指定执行人
		if(BeanUtils.isNotEmpty(specUserMap)){
			cmd.addTransitVars(BpmConstants.BPM_NODE_USERS, specUserMap);
			if(isSendNodeUsers==1){
			   cmd.setBpmIdentities(specUserMap);
			}
		}
		
		Map<String,Object> params=getProcessStartVars(request,defId);
		
		cmd.setVariables(params);
		//添加基础变量，防止开始节点设置设置脚本事件用到
		cmd.addVariable(BpmConstants.PROCESS_DEF_ID, defId);
		cmd.addVariable(BpmConstants.BPM_FLOW_KEY, defKey);
		cmd.addVariable(BpmConstants.START_USER, ContextUtil.getCurrentUserId());
		
		return cmd;
	}
	
	/**
	 * 启动流程实例
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 */
	@RequestMapping("start")
	@ResponseBody
	public void start(HttpServletRequest request,HttpServletResponse response) throws Exception{
		DefaultProcessInstCmd cmd=getStartCmd(request);
		cmd.setActionName("startFlow");
		try{
			processInstanceService.startProcessInst(cmd);
			writeResultMessage(response.getWriter(), "流程启动成功",ResultMessage.SUCCESS);
		}
		catch (Exception e) { 
			SysErrorUtil.saveErrorLog(e);
			log.error(ExceptionUtil.getExceptionMessage(e));
			String msg  = ExceptionUtil.getExceptionMessage(e);
			try {
				String specificMsg = ExceptionUtils.getRootCause(e).getMessage();
				if(!StringUtils.isNumericSpace(msg)){
					msg = specificMsg;
				}
			} catch (Exception e2) {}
			writeResultMessage(response.getWriter(),"流程启动失败!", msg,ResultMessage.FAIL);
		}
	}
	
	/**
	 * 业务数据模板中 启动流程实例
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 */
	@RequestMapping("startForm")
	@ResponseBody
	public void startForm(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			DefaultProcessInstCmd cmd= new DefaultProcessInstCmd();
			String defId = RequestUtil.getString(request, "defId");
			String businessKey = RequestUtil.getString(request, "businessKey");
			String boAlias = RequestUtil.getString(request, "boAlias");
			cmd.setProcDefId(defId);
			cmd.setDataMode(ActionCmd.DATA_MODE_BO);
			cmd.setActionName("startFlow");
			// 获取编辑数据
			BoData boData = boDataHandler.getById(businessKey, boAlias);
			cmd.setBusData(BoDataUtil.hanlerData(Arrays.asList(boData)).toJSONString());
			processInstanceService.startProcessInst(cmd);
			writeResultMessage(response.getWriter(), "流程启动成功",ResultMessage.SUCCESS);
		}
		catch (Exception e) { 
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(),"流程启动失败!", e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 发送到节点指定人
	 * @Title: nodeUsers 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 */
	@RequestMapping("sendNodeUsers")
	public ModelAndView nodeUsers(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = getAutoView();
		String defId = RequestUtil.getString(request, "defId");
		//如果为空，则认为是发起时的配置
		String nodeId = RequestUtil.getString(request, "nodeId");
		 List<BpmNodeDef> listNodeDefs=new ArrayList<BpmNodeDef>();
		if(StringUtil.isEmpty(nodeId)){
			BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
			BpmDefExtProperties prop = procDef.getProcessDefExt().getExtProperties();
			listNodeDefs=bpmDefinitionAccessor.getStartNodes(defId);
			boolean isSkip= prop.isSkipFirstNode();
			if(isSkip){
				listNodeDefs=BpmStackRelationUtil.getAfterListNode(defId,listNodeDefs.get(0).getNodeId());
			}	 
		}
		else {
			listNodeDefs=BpmStackRelationUtil.getAfterListNode(defId,nodeId);
		}
		 
		return mv.addObject("listNode", listNodeDefs);
	}
	
	/**
	 * 选择路径
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("selectDestination")
	public ModelAndView selectDestination(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		ModelAndView autoView = getAutoView();
		
		BpmNodeDef startEvent = bpmDefinitionAccessor.getStartEvent(defId);
		
		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, startEvent.getNodeId());
		
		NodeProperties nodeProperties = taskNodeDef.getLocalProperties();
		
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
		boolean isSkipFirstNode = bpmProcessDef.getProcessDefExt().getExtProperties().isSkipFirstNode();

		String jumpType = "common";
		if (nodeProperties != null) {
			jumpType = nodeProperties.getJumpType();
			
			if (jumpType.indexOf("select") > -1) {
				Map outcomeUserMap = new HashMap();
				if(isSkipFirstNode){
					taskNodeDef = taskNodeDef.getOutcomeNodes().get(0);
				}
				List<BpmNodeDef> outcomeNodes = taskNodeDef.getOutcomeNodes();
				List<BpmNodeDef> handlerSelectOutcomeNodes = handlerSelectOutcomeNodes(outcomeNodes);
				for (BpmNodeDef bpmNodeDef : handlerSelectOutcomeNodes) {
					List<BpmIdentity> bpmIdentitys = bpmIdentityService.searchByNodeIdOnStartEvent(defId, bpmNodeDef.getNodeId());
					outcomeUserMap.put(bpmNodeDef.getNodeId(), extractService.extractBpmIdentity(bpmIdentitys));
				}
				autoView.addObject("outcomeUserMap", outcomeUserMap);
				autoView.addObject("outcomeNodes", handlerSelectOutcomeNodes);
			}
			if (jumpType.indexOf("free") > -1) {
				List<BpmNodeDef> allNodeDef = bpmDefinitionAccessor.getAllNodeDef(defId);
				allNodeDef = handlerSelectOutcomeNodes(allNodeDef);
				// 移除开始节点
				List<BpmNodeDef> removeList = new ArrayList<BpmNodeDef>();
				Map allNodeUserMap = new HashMap();
				for (BpmNodeDef bpmNodeDef : allNodeDef) {
					NodeType type = bpmNodeDef.getType();
					if (NodeType.START.equals(type) || NodeType.EXCLUSIVEGATEWAY.equals(type) ||NodeType.PARALLELGATEWAY.equals(type) || NodeType.END.equals(type) ) {
						removeList.add(bpmNodeDef);
					} else if (NodeType.USERTASK.equals(bpmNodeDef.getType()) || NodeType.SIGNTASK.equals(bpmNodeDef.getType())) {
						List<BpmIdentity> bpmIdentitys = new ArrayList<BpmIdentity>();
						allNodeUserMap.put(bpmNodeDef.getNodeId(), bpmIdentitys);
					}
				}
				allNodeDef.removeAll(removeList);
				autoView.addObject("allNodeDef", allNodeDef);
				autoView.addObject("allNodeUserMap", allNodeUserMap);
			}
		}
		return autoView.addObject("jumpType", jumpType);
	}
	
	// 处理选择路径跳转的分支出口
	private List<BpmNodeDef> handlerSelectOutcomeNodes(List<BpmNodeDef> outcomeNodes) {
		int size = outcomeNodes.size();
		List<BpmNodeDef> returnList = new ArrayList<BpmNodeDef>();
		if (size == 1) {
			BpmNodeDef bpmNodeDef = outcomeNodes.get(0);
			NodeType nodeType = bpmNodeDef.getType();
			// 网关节点
			if (NodeType.EXCLUSIVEGATEWAY.equals(nodeType) || NodeType.INCLUSIVEGATEWAY.equals(nodeType) || NodeType.PARALLELGATEWAY.equals(nodeType)) {
				returnList = bpmNodeDef.getOutcomeNodes();
			}
		}
		if (BeanUtils.isEmpty(returnList)) {
			return outcomeNodes;
		} else {
			return returnList;
		}
	}
	
	
	
	
	@RequestMapping("instanceToStart")
	public ModelAndView instanceToStart(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String defId=RequestUtil.getString(request, "defId");
		BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
		BpmDefExtProperties prop = procDef.getProcessDefExt().getExtProperties();
		return getAutoView().addObject("prop",prop);
	}
	/**
	 * 保存草稿
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveDraft")
	@ResponseBody
	public void saveDraft(HttpServletRequest request,HttpServletResponse response) throws Exception{
		DefaultProcessInstCmd cmd=getStartCmd(request);
		cmd.setActionName("saveDraft");
		try{
			processInstanceService.saveDraft(cmd);
			writeResultMessage(response.getWriter(), "草稿保存成功",ResultMessage.SUCCESS);
		}
		catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(), "草稿保存失败", e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	@RequestMapping("instanceToCopyTo")
	public ModelAndView toCopyTo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = getAutoView();
		String instanceId = RequestUtil.getString(request, "instanceId");
		String taskId = RequestUtil.getString(request,"taskId","");
		String nodeId = "";
		if(StringUtil.isNotEmpty(taskId)){
			BpmTask task = bpmTaskService.getByTaskId(taskId); 
			instanceId = task.getProcInstId();
			nodeId = task.getNodeId();
		}
		String copyToType = RequestUtil.getString(request, "copyToType", "1");  // 0 抄送  1转发
		List<JmsHandler<JmsVo>> handlerList = MessageUtil.getHanlerList();
		mv.addObject("handlerList", handlerList);
		mv.addObject("instanceId", instanceId);
		mv.addObject("nodeId", nodeId);
		mv.addObject("copyToType", copyToType);
		return mv;
	}
	
	

	
	/**
	 * 
	 * 流程实例抄送转发
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("instanceTrans")
	public void trans(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String instanceId = RequestUtil.getString(request, "instanceId");
		String nodeId = RequestUtil.getString(request, "nodeId","");
		String messageType = RequestUtil.getStringAry(request, "messageType");
		String userId = RequestUtil.getString(request, "userId");
		String opinion = RequestUtil.getString(request, "opinion");
		String copyToType = RequestUtil.getString(request,"copyToType");
		String curUserId=ContextUtil.getCurrentUserId();
		try{
			if (StringUtil.isEmpty(userId)){
				throw new RuntimeException("请选择要抄送转发的人员！");
			}
			String[] userIds = userId.split("\\,");
			List<String> userList = new ArrayList<String>();
			for (String id : userIds){
				if (curUserId.equals(id)){
					throw new RuntimeException("抄送转发人不能为自己！");
				}
				userList.add(id);
			}
			ContextThreadUtil.putCommonVars("nodeId", nodeId);
			// 抄送转发给多个人
			copyToManager.transToMore(instanceId, userList, messageType, opinion,copyToType);
			
			writeResultMessage(response.getWriter(), "操作成功", ResultMessage.SUCCESS);
		} 
		catch (Exception e){
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(), "操作失败！", e.getMessage(), ResultMessage.ERROR);
		}
	}
	
	private Map<String,Object> getProcessStartVars(HttpServletRequest request, String defId){
		Map<String,Object> params=new HashMap<String, Object>();
		List<BpmVariableDef> list=bpmDefinitionService.getVariableDefs(defId);
		if(BeanUtils.isEmpty(list)) return params;
		for(BpmVariableDef varDef:list){
			String reqValue = request.getParameter(varDef.getVarKey());
			if(StringUtils.isEmpty(reqValue) && varDef.getDefaultVal()!=null && StringUtil.isNotEmpty(varDef.getDefaultVal().toString()) ){
				reqValue = varDef.getDefaultVal().toString();
			}
			if(StringUtils.isNotEmpty(reqValue)){
				Object convertVal=DefaultBpmVariableDef.getValue(varDef.getDataType(), reqValue);
				params.put(varDef.getVarKey(),convertVal);
			}
		}
		return params;
	}
	
	/**
	 * 流程实例列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		List<DefaultBpmProcessInstance> bpmProcessInstanceList= bpmProcessInstanceManager.queryList(queryFilter);
		return new PageJson(bpmProcessInstanceList);
	}
	
	/**
	 * 系统用户信息明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("instanceInfo")
	public ModelAndView info(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		DefaultBpmProcessInstance bpmProcessInstance=null;
		if(StringUtil.isNotEmpty(id)){
			bpmProcessInstance=bpmProcessInstanceManager.get(id);
			//流程图layout
			if(bpmProcessInstance==null)
				bpmProcessInstance=bpmProcessInstanceManager.getBpmProcessInstanceHistory(id);
		}
		return getAutoView().addObject("bpmProcessInstance", bpmProcessInstance)
							.addObject("returnUrl", preUrl);
	}
	/**
	 * 节点图片
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("instanceFlowImage")
	public ModelAndView flowImage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String instanceId=RequestUtil.getString(request, "id");
		String type=RequestUtil.getString(request, "type");
		String from=RequestUtil.getString(request, "from");
		BpmProcessInstance bpmProcessInstance=null;
		List<BpmProcessInstance> bpmProcessInstanceList=new ArrayList<BpmProcessInstance>();
		BpmDefLayout bpmDefLayout=null; 
		
		ModelAndView mv=getAutoView();
		
		if(StringUtil.isNotEmpty(instanceId)){
			if(type!=null&&type.equals("subFlow")){
			     //查子流程
				 String nodeId=RequestUtil.getString(request, "nodeId");
				 bpmProcessInstanceList=bpmProcessInstanceManager.getBpmProcessByParentIdAndSuperNodeId(instanceId,nodeId);
				if(BeanUtils.isEmpty(bpmProcessInstanceList)){
					bpmProcessInstanceList=bpmProcessInstanceManager.getHiBpmProcessByParentIdAndSuperNodeId(instanceId,nodeId);
				}
				
				
				if(BeanUtils.isNotEmpty(bpmProcessInstanceList)){
					 bpmProcessInstance=bpmProcessInstanceList.get(0);
					 /*********如果当前流程为子流程时，将instanceId设置为当前流程的Id************/
					 if(bpmProcessInstance!=null){
						 instanceId = bpmProcessInstance.getId();
					 }
					 bpmDefLayout = diagramService.getLayoutByDefId(bpmProcessInstanceList.get(0).getProcDefId());
				}
				//流程还没有执行到子流程位置的情况。
				else {
					bpmProcessInstance = processInstanceService.getProcessInstance(instanceId);
					
					CallActivityNodeDef bpmNodeDef= (CallActivityNodeDef) bpmDefinitionAccessor.getBpmNodeDef(bpmProcessInstance.getProcDefId(), nodeId);
					
					String folowKey=bpmNodeDef.getFlowKey();
					
					BpmDefinition def= bpmDefinitionManager.getMainByDefKey(folowKey, false);
					
					String defId=def.getDefId();
					
					bpmDefLayout = diagramService.getLayoutByDefId(defId);
					
					mv.addObject("defId",defId);
				}

			}
			else {
				    bpmProcessInstance = processInstanceService.getProcessInstance(instanceId);
					if(bpmProcessInstance==null){
						bpmProcessInstance=bpmProcessInstanceManager.getBpmProcessInstanceHistory(instanceId);
					}
					bpmProcessInstanceList.add(bpmProcessInstance);
					//流程图layout
					bpmDefLayout = diagramService.getLayoutByDefId(bpmProcessInstance.getProcDefId());
				 
			}
		
		}
		return mv.addObject("bpmProcessInstance", bpmProcessInstance)
				.addObject("instId", instanceId)
				.addObject("bpmProcessInstanceList", bpmProcessInstanceList)
				.addObject("parentInstId", bpmProcessInstance.getParentInstId())
				.addObject("bpmDefLayout", bpmDefLayout)
				.addObject("from", from)
				.addObject("returnUrl", preUrl);
	}
	

	/**
	 * 节点审批状态
	 */
	@RequestMapping("instanceNodeStatus")
	public ModelAndView getNodeStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String instId = RequestUtil.getString(request, "instId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		List<IUser> userList = null;
		
		// 获取审批情况
		List<BpmTaskOpinion> bpmTaskOpinions = bpmOpinionService.getByInstNodeId(instId, nodeId);
		//没有审批、则获取有审批权限的人...
		if(bpmTaskOpinions.size() < 1) {
			userList = bpmIdentityService.queryUsersByNode(instId, nodeId);
		}
		return getAutoView().addObject("userList", userList)
							.addObject("bpmTaskOpinions", bpmTaskOpinions);
	}
	/**
	 * 流程审批历史
	 */
	@RequestMapping("instanceFlowOpinions")
	public ModelAndView opinionHistory(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String procInstanceId= RequestUtil.getString(request, "instId");
		String taskId = RequestUtil.getString(request, "taskId");
		if((procInstanceId==null||procInstanceId.isEmpty())&&taskId!=null)
		{
			procInstanceId=bpmTaskService.getByTaskId(taskId).getProcInstId();
		}
		
		List<BpmTaskOpinion> bpmTaskOpinions =  bpmOpinionService.getTaskOpinions(procInstanceId);
		
		//以下要把整理意见格式，要展示出如果上下节点key一致的话要展现在同一个tr中
		List<List<BpmTaskOpinion>> llist = new ArrayList<List<BpmTaskOpinion>>(); 
		List<BpmTaskOpinion> list = null;
		String preKey ="";
		for(BpmTaskOpinion bto : bpmTaskOpinions){
			if(StringUtil.isNotEmpty(bto.getTaskKey())&&!bto.getTaskKey().equals(preKey)){
				list = new ArrayList<BpmTaskOpinion>();
				llist.add(list);
				preKey = bto.getTaskKey();
			}
			list.add(bto);
		}
		return getAutoView().addObject("llist", llist);
	}
	
	/**
	 * 批量删除系统用户记录(逻辑删除)
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			bpmProcessInstanceManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除流程实例成功");
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			message=new ResultMessage(ResultMessage.FAIL, "删除流程实例失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("checkInvoke")
	@ResponseBody
	public ResultMessage checkInvoke(HttpServletRequest request,HttpServletResponse reponse){
		ResultMessage result=null;
		int invokeToStart=RequestUtil.getInt(request, "invokeToStart",1);

		String instanceId=RequestUtil.getString(request, "instanceId");
		if(invokeToStart==1){
			result= bpmProcessInstanceManager.canRevokeToStart(instanceId);
		}
		return result;
	}
	
	@RequestMapping("forbiddenInstance")
	public void forbiddenInstance(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String instanceId = RequestUtil.getString(request, "id");
		ResultMessage message=null; 
		try {
			processInstanceService.suspendProcessInstance(instanceId);
			message=new ResultMessage(ResultMessage.SUCCESS, "流程实例挂起成功");
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			message=new ResultMessage(ResultMessage.FAIL, "流程实例挂起失败");
		}
		
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("unForbiddenInstance")
	public void unForbiddenInstance(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String instanceId = RequestUtil.getString(request, "id");
		
		ResultMessage message=null;
		try {
			processInstanceService.recoverProcessInstance(instanceId);
			message=new ResultMessage(ResultMessage.SUCCESS, "流程实例取消挂起成功");
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			message=new ResultMessage(ResultMessage.FAIL, "流程实例取消挂起失败");
		}
		
		writeResultMessage(response.getWriter(), message);
	}
	
	 
	private List<List<BpmTaskOpinion>> getOpinions(String procInstanceId,String taskId){
		if((procInstanceId==null||procInstanceId.isEmpty())&&taskId!=null)
		{
			procInstanceId=bpmTaskService.getByTaskId(taskId).getProcInstId();
		}

		List<BpmTaskOpinion> bpmTaskOpinions =  bpmOpinionService.getTaskOpinions(procInstanceId);

		//以下要把整理意见格式，要展示出如果上下节点key一致的话要展现在同一个tr中
		List<List<BpmTaskOpinion>> llist = new ArrayList<List<BpmTaskOpinion>>(); 
		List<BpmTaskOpinion> list = null;
		String preKey ="";
		for(BpmTaskOpinion bto : bpmTaskOpinions){
			if(!bto.getTaskKey().equals(preKey)){
				list = new ArrayList<BpmTaskOpinion>();
				llist.add(list);
				preKey = bto.getTaskKey();
			}
			list.add(bto);
		}
		return llist;
	}
	 
	 @RequestMapping("getPathNodes")
	 @ResponseBody
	 public Object getPathNodes(HttpServletRequest request,HttpServletResponse response){
		 String procInstId=RequestUtil.getString(request, "id");
		 String taskId = RequestUtil.getString(request, "taskId");
		 List<BpmInstanceTrack> tracksByInstId = processInstanceService.getTracksByInstId(procInstId);
		 
//		 List<List<BpmTaskOpinion>> opinionLists = getOpinions(procInstId,taskId);
//		 JSONArray pathArray = new JSONArray();
//		 List<BpmNodeDef> nodeDefs = getAllNodeDefs(procInstId);
//		 if(BeanUtils.isNotEmpty(opinionLists)&&BeanUtils.isNotEmpty(nodeDefs)){
//			 for (List<BpmTaskOpinion> list : opinionLists) {
//				 BpmTaskOpinion opinion = list.get(0);//暂时只处理第一个
//				 if(!opinion.getStatus().equals(OpinionStatus.AWAITING_CHECK.getKey())){
//					 creatPathNode(nodeDefs,opinionLists,pathArray,opinion);
//				 }
//				 
//			}
//		 }
		 return tracksByInstId;
	 }
	 
	 private void creatPathNode(List<BpmNodeDef> nodeDefs,List<List<BpmTaskOpinion>> opinionLists,JSONArray pathArray,BpmTaskOpinion opinion){
		 String formNodeId = opinion.getTaskKey();
		 BpmNodeDef nodeDef = findNodeDef(nodeDefs, formNodeId);
		 if(BeanUtils.isNotEmpty(nodeDef)){
			 //驳回或驳回到发起人
			 if(opinion.getStatus().equals(OpinionStatus.REJECT.getKey())||opinion.getStatus().equals(OpinionStatus.BACK_TO_START.getKey())){
				 BpmTaskOpinion nextOpinion = findNextOpinion(opinionLists,opinion.getId());
				 if(BeanUtils.isNotEmpty(nextOpinion)){
					 JSONObject obj = getPathNode(formNodeId,nextOpinion.getTaskKey());
						if(BeanUtils.isNotEmpty(obj)){
							pathArray.add(obj);
						}
				 }
			 }else{
				 List<BpmNodeDef> toNodes = nodeDef.getOutcomeNodes();
				 addToPathArray(opinionLists, pathArray, toNodes, formNodeId);
			 }
		 }
	 }
	 
	 private void addToPathArray(List<List<BpmTaskOpinion>> opinionLists,JSONArray pathArray,List<BpmNodeDef> toNodes,String formNodeId){
		 for (BpmNodeDef bpmNodeDef : toNodes) {
			 if(bpmNodeDef.getType().equals(NodeType.EXCLUSIVEGATEWAY)||bpmNodeDef.getType().equals(NodeType.PARALLELGATEWAY)||bpmNodeDef.getType().equals(NodeType.INCLUSIVEGATEWAY)){
				 JSONObject obj = getPathNode(formNodeId,bpmNodeDef.getNodeId());
				 if(BeanUtils.isNotEmpty(obj)){
					pathArray.add(obj);
				 }
				 List<BpmNodeDef> nodes = bpmNodeDef.getOutcomeNodes();
				 addToPathArray(opinionLists, pathArray, nodes, bpmNodeDef.getNodeId());
			 }else{
				 if(isInOpinion(opinionLists,bpmNodeDef.getNodeId())){
						JSONObject obj = getPathNode(formNodeId,bpmNodeDef.getNodeId());
						if(BeanUtils.isNotEmpty(obj)){
							pathArray.add(obj);
						}
					}
			 }
		}
	 }
	 
	 private BpmNodeDef findNodeDef(List<BpmNodeDef> list,String nodeId){
		 BpmNodeDef nodeDef = null;
		 for (BpmNodeDef bpmNodeDef : list) {
			if(bpmNodeDef.getNodeId().equals(nodeId)){
				nodeDef = bpmNodeDef;
				break;
			}
		}
		 return nodeDef;
	 }
	 
	 //获取下一条审批意见
	 private BpmTaskOpinion findNextOpinion(List<List<BpmTaskOpinion>> opinionLists,String id){
		 try {
			 for (int i=0;i<opinionLists.size();i++) {
				 for (BpmTaskOpinion bpmTaskOpinion : opinionLists.get(i)) {
					 if(bpmTaskOpinion.getId().equals(id)){
						 BpmTaskOpinion nextOpinion = opinionLists.get(i+1).get(0);
						 if(!nextOpinion.getStatus().equals(OpinionStatus.AWAITING_CHECK.getKey())){
							 return nextOpinion;
						 }
					 }
				}
			}
		} catch (Exception e) {}
		 return null;
	 }
	 
	 private boolean isInOpinion(List<List<BpmTaskOpinion>> opinionLists,String nodeId){
		 for (List<BpmTaskOpinion> opinions : opinionLists) {
			 for (BpmTaskOpinion bpmTaskOpinion : opinions) {
				 if(!bpmTaskOpinion.getStatus().equals(OpinionStatus.AWAITING_CHECK.getKey())&&bpmTaskOpinion.getTaskKey().equals(nodeId)){
					 return true;
				 }
			}
		}
		 return false;
	 }
	 
	 
	 /**
	  * 构建路径node
	  * @param fromNodeId
	  * @param toNodeId
	  * @return
	  */
	 private JSONObject getPathNode(String fromNodeId,String toNodeId){
		 JSONObject obj = new JSONObject();
		 obj.put("fromNodeId", fromNodeId);
		 obj.put("toNodeId", toNodeId);
		 return obj;
	 }
	 
	 /**
	  * 根据流程实例ID获取流程设计的所有节点
	  * @param procInstId
	  * @return
	  */
	 private List<BpmNodeDef> getAllNodeDefs(String procInstId){
		 List<BpmNodeDef> nodeDefs = null;
		 if(StringUtil.isNotEmpty(procInstId)){
			 try {
				 DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(procInstId);
				 nodeDefs = bpmDefinitionService.getAllBpmNodeDefs(instance.getProcDefId());
			} catch (Exception e) {}
		}
		 return nodeDefs;
	 }
	 
	 @RequestMapping("getTestData")
	 @ResponseBody
	 public PageJson getTestData(HttpServletRequest request,HttpServletResponse response){
			String sql = "select * from W_qs"; 
			CommonDao commonDao=AppUtil.getBean(CommonDao.class);
			List list = commonDao.query(sql);	
			return new PageJson(new PageList(list));
	 }
}
