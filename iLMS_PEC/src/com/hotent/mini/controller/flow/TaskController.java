package com.hotent.mini.controller.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ExceptionUtil;
import com.hotent.base.core.util.string.StringPool;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.PrivilegeMode;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmDefLayout;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.Button;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.BpmIdentityService;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.api.service.BpmOpinionService;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.api.service.DiagramService;
import com.hotent.bpmx.api.service.SignService;
import com.hotent.bpmx.api.service.TaskCommuService;
import com.hotent.bpmx.api.service.TaskTransService;
import com.hotent.bpmx.core.engine.form.BpmFormFactory;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.core.model.var.DefaultBpmVariableDef;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.core.util.BpmIdentityUtil;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.dao.BpmExeStackRelationDao;
import com.hotent.bpmx.persistence.manager.BpmApprovalItemManager;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmCommuReceiverManager;
import com.hotent.bpmx.persistence.manager.BpmDefAuthorizeManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskCommuManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTransRecordManager;
import com.hotent.bpmx.persistence.model.BpmCommuReceiver;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType;
import com.hotent.bpmx.persistence.model.BpmExeStackRelation;
import com.hotent.bpmx.persistence.model.BpmTaskCommu;
import com.hotent.bpmx.persistence.model.BpmTaskTrans;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.bpmx.plugin.task.tasknotify.helper.NotifyHelper;
import com.hotent.form.api.model.FormCategory;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.api.model.FormType;
import com.hotent.form.api.service.BpmFormRightsService;
import com.hotent.mini.controller.util.SysErrorUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.jms.MessageUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;
import com.hotent.sys.api.system.PropertyService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
/**
 * 
 * <pre>
 * 描述：流程任务管理
 * 构建组：x5-bpmx-platform
 * 作者:zyp
 * 邮箱:zyp@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/flow/task/")
public class TaskController extends GenericController {
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmInstService bpmInstService;
	@Resource
	BpmTaskActionService bpmTaskActionService;
	@Resource
	BpmOpinionService bpmOpinionService;
	@Resource
	DiagramService diagramService;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmIdentityService bpmIdentityService;
	@Resource
	BpmTaskService bpmTaskService;

	@Resource
	TaskTransService taskTransService;

	@Resource
	IUserService userServiceImpl;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	TaskCommuService taskCommuService;
	@Resource
	BpmCommuReceiverManager bpmCommuReceiverManager;
	@Resource
	BpmTaskCommuManager bpmTaskCommuManager;


	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	SignService signService;
	@Resource
	NatTaskService natTaskService;
	@Resource
	NotifyHelper notifyHelper;
	@Resource
	BpmIdentityExtractService bpmIdentityExtractService;
	@Resource
	BpmFormRightsService bpmFormRightsService;
	@Resource
	BpmExeStackManager bpmExeStackManager;
	@Resource
	BpmApprovalItemManager bpmApprovalItemManager;
	@Resource
	BoDataService boDataService;
	@Resource
	BpmExeStackRelationDao relationDao;
	@Resource
	BpmDefAuthorizeManager bpmDefAuthorizeManager;
	@Resource
	PropertyService propertyService;
	@Resource
	BpmTaskTransRecordManager taskTransRecordManager;

	

	/**
	 * 管理员处理任务页面。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("taskDoNext")
	public ModelAndView doNext(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("id");
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(task == null) throw new RuntimeException("任务不存在，可能已经被处理！");
		
		IUser user=ContextUtil.getCurrentUser();
		if(!user.isAdmin()){
			JSONObject jsonObj= bpmDefAuthorizeManager.getRight(task.getProcDefKey(), BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE.TASK);
			if(jsonObj == null && !ContextUtil.getCurrentUserId().equals(task.getAssigneeId()) ) throw new RuntimeException("没有处理此任务的权限!");
		}
		
		ModelAndView mv = new ModelAndView("/flow/task/taskToStart");
		
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		NodeProperties nodeProperties =   BpmUtil.getNodeProperties(bpmProcessInstance, task.getNodeId());
		boolean isPopWin= nodeProperties.isPopWin();

		return mv.addObject("taskId", taskId)
				.addObject("nodeId", StringUtil.convertCollectionAsString(Arrays.asList(task.getProcDefKey(), task.getNodeId()), StringPool.UNDERSCORE))
				.addObject("isPopWin",isPopWin);
	}
	
	/**
	 * 用户审批界面。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskApprove")
	public ModelAndView approve(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("id");
		
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(task == null) throw new RuntimeException("任务不存在，可能已经被处理！");
		
		boolean isForbindden = bpmInstService.isSuspendByInstId(task.getProcInstId());
		if(isForbindden){//流程已经被禁止
			 throw new RuntimeException("流程已经被禁止，请联系管理员！");
		}
		
		int rtn = bpmTaskManager.canLockTask(taskId);
		// 判断权限
		if(rtn==4)  throw new RuntimeException("此任务已经被其他人锁定!");
		//审批时验证当前人是否有权限访问。
		validTask(taskId);
		
		ModelAndView mv = new ModelAndView("/flow/task/taskToStart");
		
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		NodeProperties nodeProperties =   BpmUtil.getNodeProperties(bpmProcessInstance, task.getNodeId());
		boolean isPopWin= nodeProperties.isPopWin();
		
		return mv.addObject("taskId", taskId)
				.addObject("nodeId", StringUtil.convertCollectionAsString(Arrays.asList(task.getProcDefKey(), task.getNodeId()), StringPool.UNDERSCORE))
				.addObject("isPopWin",isPopWin);
	}

	/**
	 * 查看任务流程图
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskImage")
	public ModelAndView image(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		// 流程图layout
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		String parentInstId= bpmProcessInstance.getParentInstId();
		BpmDefLayout bpmDefLayout = diagramService.getLayoutByDefId(bpmProcessInstance.getProcDefId());
		return getAutoView().addObject("taskId", taskId)
				.addObject("bpmDefLayout", bpmDefLayout)
				.addObject("parentInstId", parentInstId)
				.addObject("instId", bpmProcessInstance.getId());
	}

	/**
	 * 获取流程实例中指定节点的审批意见
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("nodeOpinion")
	@ResponseBody
	public Object nodeOpinion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String instId = RequestUtil.getString(request, "instId");
		String nodeId = RequestUtil.getString(request, "nodeId");

		Map<String, Object> map = new HashMap<String, Object>();
		// 获取审批任务意见。
		List<BpmTaskOpinion> opinionList = bpmOpinionService.getByInstNodeId(instId, nodeId);

		if (BeanUtils.isNotEmpty(opinionList)) {
			map.put("hasOpinion", true);
			map.put("data", opinionList);
			return map;
		}

		// 没有审批意见则获取有审批权限的人
		List<BpmIdentity> userList = bpmIdentityService.searchByNode(instId, nodeId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		map.put("hasOpinion", false);
		if (userList.size() > 0) {
			// 找到了执行人
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (BpmIdentity identity : userList) {
				Map<String, Object> identityMap = new HashMap<String, Object>();
				identityMap.put("id", identity.getId());
				identityMap.put("name", identity.getName());
				// 这个type可能的值如：
				// user,用户,org,组织，role，角色，pos岗位。这个可以扩充。
				String type = identity.getType().equals(BpmIdentity.TYPE_USER) ? BpmIdentity.TYPE_USER : identity.getGroupType();

				identityMap.put("type", type);
				list.add(identityMap);
			}
			map.put("data", list);
		}
		return map;
	}

	/**
	 * 获取任务的详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskDetail")
	@ResponseBody
	public Object taskDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		Map<String, Object> map = new HashMap<String, Object>();
		if (BeanUtils.isEmpty(task)) {
			map.put("result", false);
			map.put("message", "任务不存在，可能已经被处理了.");
			return map;
		}
		
		String topDefKey="";
		
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		if(StringUtil.isNotZeroEmpty( bpmProcessInstance.getParentInstId())){
			BpmProcessInstance topInstance=bpmProcessInstanceManager.getTopBpmProcessInstance(bpmProcessInstance);
			topDefKey=topInstance.getProcDefKey();
		}
		
		
		String defId = task.getProcDefId();
		String nodeId = task.getNodeId();
		
		
		
		BpmFormService  bpmFormService =BpmFormFactory.getFormService(FormType.PC);
		FormModel formModel = bpmFormService.getByDefId(defId, nodeId, bpmProcessInstance);
		
		if(formModel==null || formModel.isFormEmpty()){
			map.put("result", "formEmpty");
			return map;
		}
		
		ContextThreadUtil.putCommonVars("defId", defId);//流程定义ID
		ContextThreadUtil.putCommonVars("nodeId", nodeId);//流程定义ID
		
		// 表单
		map.put("form", formModel);


		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);

		List<Button> buttons = BpmUtil.getButtons(taskNodeDef, task);
		
		
		if( FormCategory.INNER.equals(formModel.getType()) ){
			String curUserId=ContextUtil.getCurrentUserId();
			List<BoData> boDatas = boDataService.getDataByInst(bpmProcessInstance);
			
			DefaultTaskFinishCmd cmd = new DefaultTaskFinishCmd();
			cmd.setVariables(this.getTaskVars(request));
			ContextThreadUtil.setActionCmd(cmd);
			// BO数据前置处理
			JSONObject data =BoDataUtil.hanlerData(bpmProcessInstance,nodeId, boDatas);
			// BO数据
			map.put("data", data);
			
			//获取意见
			JSONObject opinionJson = boDataService.getFormOpinionJson(task.getProcInstId());
			map.put("opinionList", opinionJson);
			//流程定义的权限
			String permission = bpmFormRightsService.getPermission(formModel.getFormKey(), curUserId,
					task.getProcDefKey(),topDefKey, nodeId);
			map.put("permission",permission); 
		}
		//按钮处理，根据groovy脚本去掉定义的按钮。
		handButtons(buttons, task.getTaskId());
		
		// 处理按钮的urlForm 中的占位符（用系统属性替换）
		buttons = handUrlForm(buttons);
		
		// 按钮 
		map.put("buttons", JSONArray.toJSON(buttons));
		
		
		
		map.put("result", true);
		return map;
	} 
	
	/**
	 * 处理按钮是否可以可以显示。
	 * @param buttons
	 * @param taskId
	 */
	private void handButtons(List<Button> buttons,String taskId){
		NatTaskService natTaskService = (NatTaskService) AppUtil.getBean(NatTaskService.class);
		GroovyScriptEngine scriptEngine=(GroovyScriptEngine)AppUtil.getBean(GroovyScriptEngine.class);
		
		Map<String,BoData> boMap= BpmContextUtil.getBoFromContext();
		
		Map<String,Object> variables=new HashMap<String, Object>();
		
		if(StringUtil.isNotEmpty(taskId)){
			Map<String,Object> vars= natTaskService.getVariables(taskId);
			if(BeanUtils.isNotEmpty(vars)){
				variables.putAll(vars);
			}
		}
		
		if(BeanUtils.isNotEmpty(boMap)){
			variables.putAll(boMap);
		}
		
		List<Button>  removeBtns=new ArrayList<Button>();
		
		for(Button btn:buttons){
			String script= btn.getGroovyScript();
			if(StringUtil.isEmpty(script)) continue;
			
			boolean rtn=scriptEngine.executeBoolean(script, variables);
			if(rtn==false){
				removeBtns.add(btn);
			}
		}
		buttons.removeAll(removeBtns);
	}
	
	/**
	 * 处理按钮中urlForm的占位符
	 * @param buttons
	 */
	private List<Button> handUrlForm(List<Button> buttons) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		List<Button> retButtons = new ArrayList<Button>();
		for (Button button : buttons) {
			
			Button tmp = (Button) BeanUtils.cloneBean(button);
			String urlForm = tmp.getUrlForm();
			if(StringUtil.isEmpty(urlForm)){
				retButtons.add(tmp);
				continue;
			} 
			
			Matcher matcher = regex.matcher(urlForm);
			while (matcher.find()) {
				String tag = matcher.group(0);
				String alias = matcher.group(1);
				urlForm = urlForm.replace(tag, propertyService.getByAlias(alias, ""));
				tmp.setUrlForm(urlForm);
			}
			retButtons.add(tmp);
		}
		return retButtons;
		
	}

	/**
	 * 获取我的待办，并且进行条件过滤
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("getMyTasks")
	public @ResponseBody PageJson getMyTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUser().getUserId();
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<DefaultBpmTask> bpmTaskList = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(userId, queryFilter);
		return new PageJson(bpmTaskList);
	}

	/**
	 * 获取任务上下文流程变量。
	 * 
	 * @param request
	 * @return Map&lt;String,Object>
	 */
	private Map<String, Object> getTaskVars(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		String taskId = request.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) return params;
		DefaultBpmTask bpmTask = bpmTaskManager.get(taskId);
		if(StringUtil.isNotEmpty(bpmTask.getParentId())){
			taskId = bpmTask.getParentId();
		}
		params = natTaskService.getVariables(taskId);
		List<BpmVariableDef> list = bpmDefinitionService.getVariableDefs(bpmTask.getProcDefId(), bpmTask.getNodeId());
		for (BpmVariableDef varDef : list) {
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
	 * 处理任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("complete")
	public void complete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 获取cmd对象。
			DefaultTaskFinishCmd cmd = getCmdFromRequest(request);
			boolean result = bpmTaskActionService.finishTask(cmd);
			writeResultMessage(response.getWriter(), "任务办理成功", ResultMessage.SUCCESS);
			if (!result) {
				writeResultMessage(response.getWriter(), "任务办理失败", ResultMessage.FAIL);
			}
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			String rootCauseMsg = ExceptionUtils.getRootCauseMessage(e);
			writeResultMessage(response.getWriter(), "任务办理失败", rootCauseMsg, ResultMessage.FAIL);
		}
	}
	
	
	@RequestMapping("saveDraft")
	public void saveDraft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 获取cmd对象。
			DefaultTaskFinishCmd cmd = getCmdFromRequest(request);
			bpmTaskService.saveDraft(cmd);
			writeResultMessage(response.getWriter(), "保存成功!", ResultMessage.SUCCESS);
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			String rootCauseMsg = ExceptionUtils.getRootCauseMessage(e);
			writeResultMessage(response.getWriter(), "保存失败!", rootCauseMsg, ResultMessage.FAIL);
		}
	}

	/**
	 * 从上下文请求获取包装后的cmd对象。
	 * 
	 * @param request
	 * @return DefaultTaskFinishCmd
	 */
	private DefaultTaskFinishCmd getCmdFromRequest(HttpServletRequest request) {

		String taskId = RequestUtil.getString(request, "taskId");
		String actionName = RequestUtil.getString(request, "actionName");
		String opinion = RequestUtil.getString(request, "opinion");
		String data = RequestUtil.getString(request, "data");
		String formType = RequestUtil.getString(request, "formType",FormCategory.INNER.value());

		String directHandlerSign = RequestUtil.getString(request, "directHandlerSign");
		String backHandMode = RequestUtil.getString(request, BpmConstants.BACK_HAND_MODE);
		String jumpType = RequestUtil.getString(request, "jumpType");
		DefaultTaskFinishCmd cmd = new DefaultTaskFinishCmd();
		// 自由跳转 或者 驳回到指定节点
		if ("free".equals(jumpType) || "select".equals(jumpType) || "reject".equals(actionName)) {
			String destination = RequestUtil.getString(request, "destination");//rejectMode
			if (StringUtil.isNotEmpty(destination)) {
				cmd.setDestination(destination);
			}
			String nodeUsers = RequestUtil.getString(request, "nodeUsers");
			if (StringUtil.isNotEmpty(nodeUsers)) {
				Map<String, List<BpmIdentity>> specUserMap = BpmIdentityUtil.getBpmIdentity(nodeUsers);
				cmd.addTransitVars(BpmConstants.BPM_NODE_USERS, specUserMap);
			}
		}
		
		// 用户手机端驳回    暂时这样处理
		if(StringUtil.isEmpty(cmd.getDestination()) && "reject".equals(actionName)){
			// 设置驳回上一步
			String destination = getRejectPreDestination(taskId);
			if(StringUtil.isEmpty(destination)){
				throw new RuntimeException("该节点任务不支持驳回上一步");
			}
			cmd.setDestination(destination);
		}
		
		if("common".equals(jumpType)){
			String nodeUsers = RequestUtil.getString(request, "nodeUsers");
			List<BpmIdentity> identity = BpmIdentityUtil.getNextNodeBpmIdentity(nodeUsers);
			cmd.addTransitVars(BpmConstants.BPM_NEXT_NODE_USERS, identity);
		}
		// 会签任务的直接处理
		if ("true".equals(directHandlerSign)) {
			cmd.addTransitVars(BpmConstants.SIGN_DIRECT, "1");
		}
		cmd.setTaskId(taskId);
		cmd.setActionName(actionName);

		// 设置表单意见。
		cmd.setApprovalOpinion(opinion);
		// 处理表单意见，如果表单的意见存在则覆盖之前的意见。
		if(FormCategory.INNER.value().equals(formType)){
			BpmUtil.handOpinion(data, cmd);
			cmd.setDataMode(ActionCmd.DATA_MODE_BO);
		}
		else{
			cmd.setDataMode(ActionCmd.DATA_MODE_PK);
		}
		
		// 添加意见的设置
		Map<String, Object> vars = this.getTaskVars(request);

		cmd.setVariables(vars);
		// 设置流程驳回时跳转模式。
		cmd.addTransitVars(BpmConstants.BACK_HAND_MODE, backHandMode);

		cmd.setBusData(data);
		// 设置目标节点映射----------------------------------------------------------------------------------------------------
		Map<String, List<BpmIdentity>> nodeIdentityMap = getNodeBpmIdentities(request);
		cmd.setBpmIdentities(nodeIdentityMap);
		return cmd;
	}
	
	/**
	 * 获取驳回上一步的目标节点
	 * @param taskId
	 * @return "" 不可驳回   ， 非空返回可驳回到上一步节点的名称
	 */
	public String getRejectPreDestination(String taskId){

		boolean canRejectPreAct=true;//是否可以驳回到上一步
		DefaultBpmTask task = bpmTaskManager.get(taskId);

		String defId = task.getProcDefId();
		String nodeId = task.getNodeId();
		
		boolean canReject = false;
		
		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<Button> buttons = BpmUtil.getButtons(taskNodeDef, task);
		for (Button button : buttons) {
			if ("reject".equals(button.getAlias())) 
				canReject = true;
		}
	    
		if(!canReject) return "";
		
		NodeProperties nodeProperties=taskNodeDef.getLocalProperties();
		String backMode= nodeProperties.getBackMode();
		if(StringUtil.isEmpty(backMode))backMode="normal";
		String procInstId = task.getProcInstId();
		
		List<BpmNodeDef> listBpmNodeDef = BpmStackRelationUtil.getHistoryListBpmNodeDef(procInstId, task.getNodeId(), "pre");
		// 允许直来直往的节点
		List<BpmNodeDef> bpmExeStacksUserNode = new ArrayList<BpmNodeDef>();
		// 允许按流程图执行的节点
		List<BpmNodeDef> bpmExeStacksGoMapUserNode = new ArrayList<BpmNodeDef>();
		List<BpmExeStackRelation> relationList= relationDao.getListByProcInstId(procInstId);
		for (BpmNodeDef node : listBpmNodeDef) {
			if ((node.getType().equals(NodeType.USERTASK)||node.getType().equals(NodeType.SIGNTASK))&&!node.getNodeId().equals(nodeId)) {
				bpmExeStacksUserNode.add(node);

				boolean isHavePre = BpmStackRelationUtil.isHaveAndOrGateway(procInstId, node.getNodeId(), "pre",relationList);
				boolean isHaveAfter = BpmStackRelationUtil.isHaveAndOrGateway(procInstId, node.getNodeId(), "after",relationList);
				if (!(isHavePre && isHaveAfter)) {
					bpmExeStacksGoMapUserNode.add(node);
				}else{
					List<BpmNodeDef> incomeNodes = node.getIncomeNodes();
					if(BeanUtils.isNotEmpty(incomeNodes)){
						BpmNodeDef nodeDef = incomeNodes.get(0);
						//如果是从开始网关进入的用户节点，则允许按流程图驳回
						if(node.getType().equals(NodeType.USERTASK) && (nodeDef.getType().equals(NodeType.START)||nodeDef.getType().equals(NodeType.USERTASK))){
							bpmExeStacksGoMapUserNode.add(node);
						}
					}
				}
			}
		}
		canRejectPreAct=bpmExeStacksGoMapUserNode.size()>0||bpmExeStacksUserNode.size()>0;
		
		if(!canRejectPreAct){
			return "";
		}
		
		if("direct".equals(backMode)){
			return bpmExeStacksUserNode.get(0).getNodeId();
		}else{
			return  bpmExeStacksGoMapUserNode.get(0).getNodeId();
		}
	
	}


	

	/**
	 * 根据任务节点获取节点的执行人。
	 * 
	 * @param request
	 * @return Map<String,List<BpmIdentity>>
	 */
	private Map<String, List<BpmIdentity>> getNodeBpmIdentities(HttpServletRequest request) {
		Map<String, List<BpmIdentity>> nodeIdentityMap = new HashMap<String, List<BpmIdentity>>();
		String taskId = request.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) return nodeIdentityMap;

		BpmTask bpmTask = bpmTaskManager.get(taskId);
		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(bpmTask.getProcDefId(), bpmTask.getNodeId());

		for (BpmNodeDef nodeDef : taskNodeDef.getOutcomeTaskNodes()) {
			String[] userIds = request.getParameterValues("uId_" + nodeDef.getNodeId());
			List<BpmIdentity> bpmIdentities = new ArrayList<BpmIdentity>();
			if (BeanUtils.isEmpty(userIds)) continue;

			for (String uId : userIds) {
				IUser user = userServiceImpl.getUserById(uId);
				DefaultBpmIdentity identity = new DefaultBpmIdentity(user);
				bpmIdentities.add(identity);
			}

			nodeIdentityMap.put(nodeDef.getNodeId(), bpmIdentities);
		}
		return nodeIdentityMap;
	}

	/**
	 * 流程任务列表(分页条件查询)数据
	 * 
	 * @param request
	 * @param reponsec
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		// 查询列表
		PageList<DefaultBpmTask> list = (PageList<DefaultBpmTask>) bpmTaskManager.queryList(queryFilter);

		return new PageJson(list);
	}

	/**
	 * 系统用户信息明细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("taskGet")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		// 获取候选记录
		DefaultBpmTask bpmTask = bpmTaskManager.getByTaskId(id);
		// 流程图layout
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(bpmTask.getProcInstId());
		BpmDefLayout bpmDefLayout = diagramService.getLayoutByDefId(bpmProcessInstance.getProcDefId());

		// 审批历史
		List<BpmTaskOpinion> opinionList   = bpmOpinionService.getTaskOpinions(bpmTask.getProcInstId());

		List<BpmIdentity> bpmIdentities = bpmTaskService.getTaskCandidates(id);
		
		//以下要把整理意见格式，要展示出如果上下节点key一致的话要展现在同一个tr中
		List<List<BpmTaskOpinion>> llist = new ArrayList<List<BpmTaskOpinion>>(); 
		List<BpmTaskOpinion> list = null;
		String preKey ="";
		for(BpmTaskOpinion bto : opinionList){
			if(!preKey.equals(bto.getTaskKey())){
				list = new ArrayList<BpmTaskOpinion>();
				llist.add(list);
				if(StringUtil.isNotEmpty(bto.getTaskKey())){
					preKey = bto.getTaskKey();
				}else{
					preKey ="";
				}
			}
			list.add(bto);
		}
		
		return getAutoView().addObject("bpmTask", bpmTask).addObject("bpmDefLayout", bpmDefLayout)
				.addObject("opinionList", llist).addObject("bpmIdentities", bpmIdentities)
				.addObject("returnUrl", preUrl);
	}

	/**
	 * 判断当前用户是否是当前任务的执行人。
	 * 
	 * @param action
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	private void validTask(String taskId) throws Exception {

		IUser defaultUser = ContextUtil.getCurrentUser();
		if (defaultUser.isAdmin()) return;
		
		// 先查询自己是否有这个任务；
		boolean mark = false;
		// 获取任务 的人
		List<IUser> users = bpmTaskService.getUsersByTaskId(taskId);
		String userId = ContextUtil.getCurrentUserId();
		for (IUser user : users) {
			if (userId.equals(user.getUserId())) {
				mark = true;
				break;
			}
		}
		if(!mark) throw new RuntimeException("您没有执行该操作的权限.");
	}

	/**
	 * 任务办理(同意、反对、弃权)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("taskToAgree")
	public ModelAndView toAgree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		String actionName = RequestUtil.getString(request, "actionName");
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(task == null) throw new RuntimeException("任务不存在，可能已经被处理！");
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
		String defId = task.getProcDefId();
		String nodeId = task.getNodeId();
		String defKey = bpmProcessInstance.getProcDefKey();
		String typeId = task.getTypeId();
		ModelAndView autoView = getAutoView();
		autoView.addObject("taskId", taskId).addObject("actionName", actionName);
		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		
		NodeProperties nodeProperties = BpmUtil.getNodeProperties(bpmProcessInstance, nodeId);

		String jumpType = "common";
		boolean isGoNextJustEndEvent = isGoNextJustEndEvent(taskNodeDef);//任务下一个节点是否为结束节点
		autoView.addObject("isGoNextJustEndEvent", isGoNextJustEndEvent);
		if (nodeProperties != null) {
			jumpType = nodeProperties.getJumpType();
			if (OpinionStatus.AGREE.getKey().equals(actionName)) {
				//主要用于人员的计算。
				List<BoData> boDatas = boDataService.getDataByInst(bpmProcessInstance);
				// BO数据前置处理
				BoDataUtil.hanlerData(bpmProcessInstance,nodeId, boDatas);
				
				if (jumpType.indexOf("select") > -1) {
					Map outcomeUserMap = new HashMap();
					List<BpmNodeDef> outcomeNodes = taskNodeDef.getOutcomeNodes();
					List<BpmNodeDef> handlerSelectOutcomeNodes = handlerSelectOutcomeNodes(outcomeNodes);
					for (BpmNodeDef bpmNodeDef : handlerSelectOutcomeNodes) {
						List<BpmIdentity> bpmIdentitys = bpmIdentityService.searchByNode(task.getProcInstId(), bpmNodeDef.getNodeId());
						outcomeUserMap.put(bpmNodeDef.getNodeId(), bpmIdentitys);
					}
					autoView.addObject("outcomeUserMap", outcomeUserMap);
					autoView.addObject("outcomeNodes", handlerSelectOutcomeNodes);
				}
				if (jumpType.indexOf("free") > -1) {
					List<BpmNodeDef> allNodeDef = bpmDefinitionAccessor.getAllNodeDef(defId);
					// 移除开始节点
					List<BpmNodeDef> removeList = new ArrayList<BpmNodeDef>();
					Map allNodeUserMap = new HashMap();
					for (BpmNodeDef bpmNodeDef : allNodeDef) {

						if (NodeType.START.equals(bpmNodeDef.getType())) {
							removeList.add(bpmNodeDef);
						} else if (NodeType.USERTASK.equals(bpmNodeDef.getType()) || NodeType.SIGNTASK.equals(bpmNodeDef.getType())) {
							List<BpmIdentity> bpmIdentitys = bpmIdentityService.searchByNode(task.getProcInstId(), bpmNodeDef.getNodeId());
							allNodeUserMap.put(bpmNodeDef.getNodeId(), bpmIdentitys);
						}
					}
					allNodeDef.removeAll(removeList);
					autoView.addObject("allNodeDef", allNodeDef);
					autoView.addObject("allNodeUserMap", allNodeUserMap);
				}
			}
		}
		
		// 如果是会签节点， 判断用户是否有会签特权中的 [所有， 直接]
		if(taskNodeDef instanceof SignNodeDef){
			String userId = ContextUtil.getCurrentUserId();
			SignNodeDef signNodeDef = (SignNodeDef) taskNodeDef;
			Map<String, Object> variables = natTaskService.getVariables(taskId);
			List<PrivilegeMode>  pmLists = signService.getPrivilege(userId, signNodeDef, variables);
			if( pmLists.contains(PrivilegeMode.ALL) || pmLists.contains(PrivilegeMode.DIRECT)){
				autoView.addObject("directHandlerSign",true);
			}
		}
		
		List<String> approvalItem  = bpmApprovalItemManager.getApprovalByDefKeyAndTypeId(defKey, typeId);
		return autoView.addObject("jumpType", jumpType).addObject("approvalItem", approvalItem);
	}
	
	//判断当前节点的后续节点是否结束节点
	private boolean isGoNextJustEndEvent(BpmNodeDef taskNodeDef){
		boolean isGoNextJustEndEvent = true;
		try {
			List<BpmNodeDef> outcomeNodes = taskNodeDef.getOutcomeNodes();
			if(BeanUtils.isNotEmpty(outcomeNodes)){
				for (BpmNodeDef bpmNodeDef : outcomeNodes) {
					if(!bpmNodeDef.getType().equals(NodeType.END)){
						return false;
					}
				}
			}
		} catch (Exception e) {}
		return isGoNextJustEndEvent;
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

	/**
	 * 驳回任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskToReject")
	public ModelAndView toReject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String backModel = RequestUtil.getString(request, "backModel","");
		String taskId = RequestUtil.getString(request, "taskId");
		boolean canRejectToStart = false; // 是否可以驳回发起人
		boolean canRejectToAnyNode = false; // 是否可以驳回指定节点
		boolean canReject = false;
		boolean canRejectPreAct=true;//是否可以驳回到上一步
		DefaultBpmTask task = bpmTaskManager.get(taskId);

		String defId = task.getProcDefId();
		String nodeId = task.getNodeId();

		BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<Button> buttons = BpmUtil.getButtons(taskNodeDef, task);
		for (Button button : buttons) {
			if ("rejectToAnyNode".equals(button.getAlias())&& "reject".equals(backModel))
				canRejectToAnyNode = true;
			if ("backToStart".equals(button.getAlias())&& "backToStart".equals(backModel))
				canRejectToStart = true;
			if ("reject".equals(button.getAlias())&& "reject".equals(backModel)) 
				canReject = true;
		}
	    
		NodeProperties nodeProperties=taskNodeDef.getLocalProperties();
		String backMode= nodeProperties.getBackMode();
		if(StringUtil.isEmpty(backMode))backMode="normal";
		String backNode= nodeProperties.getBackNode();
		String procInstId = task.getProcInstId();
		
		List<BpmNodeDef> listBpmNodeDef = BpmStackRelationUtil.getHistoryListBpmNodeDef(procInstId, task.getNodeId(), "pre");

		// 允许直来直往的节点
		List<BpmNodeDef> bpmExeStacksUserNode = new ArrayList<BpmNodeDef>();
		// 允许按流程图执行的节点
		List<BpmNodeDef> bpmExeStacksGoMapUserNode = new ArrayList<BpmNodeDef>();
		List<BpmExeStackRelation> relationList= relationDao.getListByProcInstId(procInstId);
		//可驳回节点去重
		Set<String> bpmExeStackSet = new HashSet<String>();
		for (BpmNodeDef node : listBpmNodeDef) {
			if (!bpmExeStackSet.contains(node.getNodeId())&&(node.getType().equals(NodeType.USERTASK)||node.getType().equals(NodeType.SIGNTASK))&&!node.getNodeId().equals(nodeId)) {
				bpmExeStacksUserNode.add(node);

				boolean isHavePre = BpmStackRelationUtil.isHaveAndOrGateway(procInstId, node.getNodeId(), "pre",relationList);
				boolean isHaveAfter = BpmStackRelationUtil.isHaveAndOrGateway(procInstId, node.getNodeId(), "after",relationList);
				if (!(isHavePre && isHaveAfter)) {
					bpmExeStacksGoMapUserNode.add(node);
					bpmExeStackSet.add(node.getNodeId());
				}else{
					List<BpmNodeDef> incomeNodes = node.getIncomeNodes();
					if(BeanUtils.isNotEmpty(incomeNodes)){
						BpmNodeDef nodeDef = incomeNodes.get(0);
						//如果是从开始网关进入的用户节点，则允许按流程图驳回
						if(node.getType().equals(NodeType.USERTASK) && (nodeDef.getType().equals(NodeType.START)||nodeDef.getType().equals(NodeType.USERTASK))){
							bpmExeStacksGoMapUserNode.add(node);
							bpmExeStackSet.add(node.getNodeId());
						}
					}
				}
			}

		}
		canRejectPreAct=bpmExeStacksGoMapUserNode.size()>0||bpmExeStacksUserNode.size()>0;
		
		return getAutoView().addObject("taskId", taskId)
				.addObject("backNode", backNode)
				.addObject("backMode", backMode)
				.addObject("canRejectToStart", canRejectToStart)
				.addObject("canRejectToAnyNode", canRejectToAnyNode)
				.addObject("canRejectPreAct", canRejectPreAct)
				.addObject("canReject", canReject)
				.addObject("bpmExeStacksUserNode", bpmExeStacksUserNode)
				.addObject("bpmExeStacksGoMapUserNode", bpmExeStacksGoMapUserNode);
	}

	/**
	 * 终止流程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskToEndProcess")
	public ModelAndView toEndProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		//handlerList
		
		List<JmsHandler<JmsVo>> handlerList = MessageUtil.getHanlerList();

		ModelAndView mv = getAutoView();
		mv.addObject("handlerList", handlerList);
		mv.addObject("taskId", taskId);

		
		return mv;
	}

	/**
	 * 添加会签任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskToAddSignTask")
	public ModelAndView toAddSignTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		// 判断权限
		return getAutoView().addObject("taskId", taskId);
	}

	/**
	 * 任务转交
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskToDelegate")
	public ModelAndView toDelegate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");

		List<JmsHandler<JmsVo>> handlerList = MessageUtil.getHanlerList();

		ModelAndView mv = getAutoView();
		mv.addObject("handlerList", handlerList);
		mv.addObject("taskId", taskId);

		// 判断权限
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("doAddSignTask")
	public void doAddSignTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		String userId = RequestUtil.getString(request, "addSignTaskUserId");
		String messageType = RequestUtil.getString(request, "messageType");
		String addReason = RequestUtil.getString(request, "addReason");
		ResultMessage addSignTask = signService.addSignTask(taskId, userId.split(","));

		// 加签成功 发送消息
		if (addSignTask.getResult() == ResultMessage.SUCCESS) {
			if (StringUtil.isNotEmpty(messageType)) {
				List<BpmIdentity> bpmIdentities = (List<BpmIdentity>) addSignTask.getVars().get("users");
				List<IUser> users = bpmIdentityExtractService.extractUser(bpmIdentities);
				Map<String, Object> variables = natTaskService.getVariables(taskId);
				variables.put("cause", addReason);
				variables.put("sender", ContextUtil.getCurrentUser().getFullname());
				variables.put("taskSubject", variables.get("subject_"));
				String baseUrl=SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
				variables.put("baseUrl", baseUrl);
				
				List<Map<String, String>> taskIds = (List<Map<String, String>>) addSignTask.getVars().get("taskIds");
				for (IUser user : users) {
					String taskid = findTaskId(taskIds,user.getUserId());
					if(StringUtils.isNotEmpty(taskid)){
						variables.put("taskId", taskid);
						users = new ArrayList<IUser>();
						users.add(user);
						//发送消息
						notifyHelper.notify(users, Arrays.asList(messageType.split(",")), TemplateConstants.TYPE_KEY.BPM_ADD_SIGN_TASK, variables);
					}
				}
			}
			writeResultMessage(response.getWriter(), "加签成功", ResultMessage.SUCCESS);
		} else {
			writeResultMessage(response.getWriter(), "加签失败," + addSignTask.getMessage(), ResultMessage.FAIL);
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

	/**
	 * 人工终止
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("doEndProcess")
	public void doEndProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		String messageType = RequestUtil.getString(request, "messageType");
		String endReason = RequestUtil.getString(request, "endReason");
		try {
			bpmTaskManager.endProcessByTaskId(taskId, messageType, endReason);
			writeResultMessage(response.getWriter(), "终止流程成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(), "终止流程失败," + e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 任务沟通
	 */
	@RequestMapping("taskToCommu")
	public ModelAndView taskToCommu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		BpmTaskCommu taskCommu = bpmTaskCommuManager.getByTaskId(taskId);
		List<BpmCommuReceiver> commuReceivers = null; // 回复消息的
		if (taskCommu != null) {
			commuReceivers = bpmCommuReceiverManager.getByCommuStatus(taskCommu.getId(), null);
		}
		
		List<JmsHandler<JmsVo>> handlerList = MessageUtil.getHanlerList();

		return getAutoView().addObject("taskCommu", taskCommu)
				.addObject("bpmTaskId", taskId)
				.addObject("commuReceivers", commuReceivers)
				.addObject("handlerList", handlerList);
	}

	/**
	 * 反馈消息
	 */
	@RequestMapping("feedback")
	public void feedback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		String opinion = request.getParameter("opinion");
		String notifyType = RequestUtil.getStringAry(request, "messageType");

		DefaultBpmTask bpmTask = bpmTaskManager.get(taskId);

		DefaultTaskFinishCmd cmd = new DefaultTaskFinishCmd();
		cmd.setTaskId(taskId);
		cmd.setActionName("commu");
		cmd.setApprovalOpinion(opinion);
		cmd.setInstId(bpmTask.getProcInstId());
		cmd.setNotifyType(notifyType);//反馈沟通类型
		boolean result = true;
		try {
			result = bpmTaskActionService.finishTask(cmd);
			if (result) {
				writeResultMessage(response.getWriter(), "反馈成功", ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "反馈失败", ResultMessage.FAIL);
			}
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(), "反馈失败", e.getMessage(), ResultMessage.FAIL);
		}

		
	}

	/**
	 * 保存沟通信息
	 */
	@RequestMapping("saveCommu")
	public void saveCommu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bpmTaskId = RequestUtil.getString(request, "bpmTaskId");
		String notifyType = RequestUtil.getStringAry(request, "notifyType");
		String userIds = RequestUtil.getString(request, "receiverIds");
		String opinion = RequestUtil.getString(request, "opinion");
		try {
			taskCommuService.addCommuTask(bpmTaskId, notifyType, opinion, getUsersByUserId(userIds));
			writeResultMessage(response.getWriter(), "沟通成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(), "沟通失败！", e.getMessage(), ResultMessage.ERROR);
		}
	}

	/**
	 * 将逗号分割的用户转换成列表。
	 * @param userIds "1,2,3"
	 * @return
	 */
	private List<IUser> getUsersByUserId(String userIds) {
		if (StringUtil.isEmpty(userIds)) return Collections.emptyList();
		String[] userIdArray = userIds.split(",");

		List<IUser> user = new ArrayList<IUser>();
		for (String id : userIdArray) {
			IUser u = userServiceImpl.getUserById(id);
			if (u != null)
				user.add(u);
		}
		return user;
	}
	
	/**
	 * 保存流转信息
	 */
	@RequestMapping("taskToTrans")
	public ModelAndView taskToTrans(HttpServletRequest request, HttpServletResponse response, BpmTaskTrans taskTrans) throws Exception {
		String taskId = RequestUtil.getString(request,"taskId");
		ModelAndView mv=getAutoView();
		
		List<JmsHandler<JmsVo>> handlerList = MessageUtil.getHanlerList();
		
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.addParamsFilter("taskId", taskId);
		List<BpmTaskTransRecord> transRecordList = taskTransRecordManager.getTransRecordList(queryFilter);

		mv.addObject("handlerList", handlerList);
		mv.addObject("taskId", taskId);
		mv.addObject("transRecordList", transRecordList);
		
		return mv;
		
	}

	/**
	 * 保存流转信息
	 */
	@RequestMapping("saveTrans")
	public void saveTrans(HttpServletRequest request, HttpServletResponse response, BpmTaskTrans taskTrans) throws Exception {
		String notifyType = RequestUtil.getStringAry(request, "notifyType");
		String opinion = RequestUtil.getString(request, "opinion");
		String userIds = RequestUtil.getString(request, "receiverIds");
		String currentUserId=ContextUtil.getCurrentUserId();

		try {
			List<IUser> userList = this.getUsersByUserId(userIds);
			for (IUser user : userList) {
				if (currentUserId.equals(user.getUserId()))
					throw new RuntimeException("流转人员不能包含本人！");
			}

			taskTransService.addTransTask(taskTrans, userList, notifyType, opinion);
			writeResultMessage(response.getWriter(), "流转成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(), "流转失败！", e.getMessage(), ResultMessage.ERROR);
		}
	}

	/**
	 * 获取我流转出去的任务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getMyTransTask")
	public @ResponseBody PageJson getMyTransTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUser().getUserId();
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<DefaultBpmTask> list = taskTransService.getMyTransTask(userId, queryFilter);
		return new PageJson(list);
	}

	/**
	 * 获取任务的流转记录明细
	 */
	@RequestMapping("taskTransDetail")
	public ModelAndView taskTransDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BpmTaskTransRecord transRecord = taskTransRecordManager.get(RequestUtil.getString(request, "id"));
		ModelAndView mv = new ModelAndView("/office/initiatedProcess/transDetail");
		return mv.addObject("trans", transRecord);

	}

	/**
	 * 获取我流转出去的任务
	 */
	@RequestMapping("withDraw")
	public void withDraw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String opinion = RequestUtil.getString(request, "opinion");
		String notifyType = RequestUtil.getStringAry(request, "notifyType");
		String taskId = RequestUtil.getString(request, "taskId");

		try {
			taskTransService.withDraw(taskId, notifyType, opinion);
			writeResultMessage(response.getWriter(), "追回成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(), "追回失败！", e.getMessage(), ResultMessage.ERROR);
		}
	}

	/**
	 * 转交任务
	 */
	@RequestMapping("delegate")
	public void delegate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bpmTaskId = RequestUtil.getString(request, "bpmTaskId");
		String messageType = RequestUtil.getStringAry(request, "messageType");
		String userId = RequestUtil.getString(request, "userId");
		String opinion = RequestUtil.getString(request, "opinion");
		String currentUserId=ContextUtil.getCurrentUserId();
		try {
			if (StringUtil.isEmpty(userId)) {
				throw new RuntimeException("请选择要转办的人员！");
			}
			if (currentUserId.equals(userId)) {
				throw new RuntimeException("任务转办人不能为自己！");
			}
			//转办
			bpmTaskActionService.delegate(bpmTaskId, userId, messageType, opinion);
			
			writeResultMessage(response.getWriter(), "任务转办成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			writeResultMessage(response.getWriter(), "任务转办失败！", e.getMessage(), ResultMessage.ERROR);
		}
	}

	/**
	 * 批量删除系统用户记录(逻辑删除)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			bpmTaskManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除流程任务成功");
		} catch (Exception e) {
			SysErrorUtil.saveErrorLog(e);
			message = new ResultMessage(ResultMessage.FAIL, "删除流程任务失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 任务反馈。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskToFeedBack")
	public ModelAndView toFeedBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");

		BpmTask bpmTask = bpmTaskManager.get(taskId);

		BpmTaskCommu taskCommu = bpmTaskCommuManager.getByTaskId(bpmTask.getParentId());

		List<JmsHandler<JmsVo>> handlerList = MessageUtil.getHanlerList();

		ModelAndView mv = getAutoView();
		mv.addObject("handlerList", handlerList);
		mv.addObject("taskId", taskId);
		mv.addObject("taskCommu", taskCommu);

		// 判断权限
		return mv;
	}
	
	@RequestMapping("canLock")
	@ResponseBody
	public int canLock(HttpServletRequest request, HttpServletResponse response)  {
		String taskId = RequestUtil.getString(request, "taskId");
		
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		if(task==null){
			return 0;
		}
		boolean isForbindden = bpmInstService.isSuspendByInstId(task.getProcInstId());
		if(isForbindden){//流程已经被禁止
			return 6;
		}
		int rtn = bpmTaskManager.canLockTask(taskId);
		// 判断权限
		return rtn;
	}
	
	@RequestMapping("isForbindden")
	@ResponseBody
	public int isForbindden(HttpServletRequest request, HttpServletResponse response)  {
		String taskId = RequestUtil.getString(request, "taskId");
		
		DefaultBpmTask task = bpmTaskManager.get(taskId);
		
		if(task == null) return 2; //任务不存在，可能已经被处理！
		
		boolean isForbindden = bpmInstService.isSuspendByInstId(task.getProcInstId());
		IUser user=ContextUtil.getCurrentUser();
		if(!user.isAdmin()){
			JSONObject jsonObj= bpmDefAuthorizeManager.getRight(task.getProcDefKey(), BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE.TASK);
			if(jsonObj == null && !ContextUtil.getCurrentUserId().equals(task.getAssigneeId()) ) return 3;//没有处理此任务的权限!
		}
		if(isForbindden){//流程已经被禁止
			return 1;
		}else{
			return 0;
		}
	}
	
	@RequestMapping("lockUnlock")
	@ResponseBody
	public int lockUnlock(HttpServletRequest request, HttpServletResponse response)  {
		String taskId = RequestUtil.getString(request, "taskId");
		//0:任务已经处理,1:可以锁定,2:不需要解锁 ,3:可以解锁，4,被其他人锁定
		int rtn = bpmTaskManager.canLockTask(taskId);
		if(rtn==0 ||  rtn==4 ||  rtn==2 || rtn==5){
			return rtn;
		}
		String curUserId=ContextUtil.getCurrentUserId();
		//锁定
		if(rtn==1){
			bpmTaskManager.lockTask(taskId, curUserId);
		}
		//解锁
		else{
			bpmTaskManager.unLockTask(taskId);
		}
		
		return rtn;
	}
	
	
	
	@RequestMapping("setTaskExecutors")
	@ResponseBody
	public ResultMessage setTaskExecutors(HttpServletRequest request, HttpServletResponse response) {
		String taskId=RequestUtil.getString(request, "taskId") ;
		String[] userIds=RequestUtil.getStringAryByStr(request, "receiverIds");
		String opinion=RequestUtil.getString(request, "opinion") ;
		String notifyType=RequestUtil.getStringAry(request, "notifyType") ;
		
		ResultMessage result=null;
		try{
			bpmTaskManager.setTaskExecutors(taskId, userIds,notifyType,opinion);
			result=new ResultMessage(ResultMessage.SUCCESS, "设置成功!");
		}
		catch(Exception ex){
			SysErrorUtil.saveErrorLog(ex);
			String msg=ExceptionUtil.getExceptionMessage(ex);
			result=new ResultMessage(ResultMessage.SUCCESS, "设置失败!",msg);
		}
		
		return result;
	}
	
	
	/**
	 * 更改任务执行人。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskToSetExecutors")
	public ModelAndView taskToSetExecutors(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");

		List<JmsHandler<JmsVo>> handlerList = MessageUtil.getHanlerList();

		ModelAndView mv = getAutoView();
		mv.addObject("handlerList", handlerList);
		mv.addObject("taskId", taskId);

		return mv;
	}

}
