package com.hotent.mobile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmDefExtProperties;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeForm;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.Button;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.api.service.BpmOpinionService;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.core.engine.form.BpmFormFactory;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.manager.CopyToManager;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.api.model.FormType;
import com.hotent.form.api.service.BpmFormRightsService;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.portal.persistence.manager.SysIndexLayoutManageManager;
import com.hotent.portal.persistence.manager.SysLayoutToolsManager;
import com.hotent.portal.persistence.model.SysIndexTools;
import com.hotent.portal.persistence.model.SysLayoutTools;
import com.hotent.portal.util.PortalUtil;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.util.ContextUtil;

@Controller
@RequestMapping("/mobile/bpm/")
public class BpmController extends GenericController {
	@Resource
	BpmInstService bpmInstService;
	@Resource
	BpmTaskService bpmTaskService;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	
	@Resource
	BpmFormRightsService bpmFormRightsService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor; 
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BoDataService boDataService;
	@Resource
	BpmOpinionService bpmOpinionService;
	@Resource
	CopyToManager copyToManager;
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager;
	@Resource
	SysIndexLayoutManageManager sysIndexlayoutManageService;
	@Resource
	SysLayoutToolsManager sysLayoutToolsManager;
	@Resource
	UserManager userManager;
	@Resource
	SysTypeManager sysTypeManager;
	/**
	 * 首页页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myMobileHome")
	@ResponseBody
	public JSONObject myHome(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try{
			String html = sysIndexlayoutManageService.obtainIndexManageMobileData(PortalUtil.getCurrentUserMobileLayout());
			html = Base64.getFromBase64(html);
			json.put("html", html);
			json.put("result", true);
		}catch(Exception e){
			json.put("result", false);
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * 控制台页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("workbBench")
	@ResponseBody
	public PageJson workbBench(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String layoutId = PortalUtil.getCurrentUserMobileLayout();
		PageList<SysIndexTools> sysIndexToolsList=new PageList<SysIndexTools>(sysLayoutToolsManager.queryTools(layoutId,SysLayoutTools.TOOLS));
		return new PageJson(sysIndexToolsList);
	}
	/**
	 * 返回我的流程定义列表。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMyDef")
	@ResponseBody
	public PageJson getMyDef(HttpServletRequest request, HttpServletResponse response){
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.addFilter("is_main_", "Y", QueryOP.EQUAL);
		queryFilter.addFilter("status_", "deploy", QueryOP.EQUAL);
		queryFilter.addParamsFilter("bpmDefAuthorizeRightType", BPMDEFAUTHORIZE_RIGHT_TYPE.START);
		queryFilter.addFilter("support_mobile_", 1,QueryOP.EQUAL);
		
		// 查询列表
		PageList<BpmDefinition> bpmDefinitionlist = (PageList<BpmDefinition>) bpmDefinitionService.queryList(queryFilter);
		String curUserId = ContextUtil.getCurrentUser().getUserId();
		List<SysType> list = sysTypeManager.getByGroupKey("FLOW_TYPE", curUserId);
		PageList<Map> typedefList = new PageList<Map>();
		Map<String,List> map = new HashMap<String,List>();
		Iterator iterator = list.iterator(); 
		while(iterator.hasNext()){
			SysType next = (SysType) iterator.next();
			queryFilter.addFilter("type_id_", next.getId(),QueryOP.EQUAL);
			List<BpmDefinition> queryList = bpmDefinitionService.queryList(queryFilter);
			if(queryList.size() <= 0){
				iterator.remove();
			}
		}
		map.put("flowType", list);
		map.put("bpmDefinitionlist", bpmDefinitionlist);
		typedefList.add(map);
		return new PageJson(typedefList);
	}
	
	/**
	 * 获取发起流程表单模版。
	 * <pre>
	 * 返回数据如下：
	 * {result:true,get:true,formKey:"",version:1,template:"",data:""};
	 * result:获取表单数据结果
	 * get：表示是否获取表单。
	 * 	true：表示获取表单。
	 *  false：表示客户端的表单和服务端一致。
	 * 		那么不需要之后的json数据。
	 * 		formKey:表单key
	 * 		version:表单版本
	 * 		template:angularjs表单模版。
	 * data:表单数据
	 * permission:表单权限
	 * 
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getStartForm")
	@ResponseBody
	public JSONObject getStartForm(HttpServletRequest request, HttpServletResponse response) {
		String defId = request.getParameter("defId");
		String proInstId = request.getParameter("runId");
		JSONObject json = new JSONObject();
		
		try {
			String curUserId=ContextUtil.getCurrentUserId();
			
			
			
			BpmFormService bpmFormService=BpmFormFactory.getFormService(FormType.MOBILE);
			
			BpmProcessInstance proInstance=null;
		
			BpmNodeForm nodeForm=null;
			if(StringUtil.isEmpty(proInstId)){
				nodeForm = bpmFormService.getByDefId(defId);
			}else{
				proInstance = bpmInstService.getProcessInstance(proInstId);
				nodeForm = bpmFormService.getByDraft(proInstance);
			}
			
			if(nodeForm==null ){
				json.put("result", "formEmpty");
				return json;
			}
			
			FormModel formModel=(FormModel) nodeForm.getForm();
			
			BpmNodeDef bpmNodeDef =nodeForm.getBpmNodeDef();
			
			List<Button> buttons = BpmUtil.getButtons(bpmNodeDef);
			//按钮
			json.put("buttons",JSONArray.toJSON(buttons));
			
			JSONObject jsondata=null;
			//从草稿发起流程。
			if(StringUtil.isNotEmpty(proInstId)){
				List<BoData> boDatas =boDataService.getDataByInst(proInstance);
				jsondata=BoDataUtil.hanlerData(defId,boDatas);
			}else{
				List<BoData> boDatas = boDataService.getDataByDefId(defId);
				jsondata=BoDataUtil.hanlerData(defId,boDatas);
			}
			String permission = bpmFormRightsService.getPermission(formModel.getFormKey(), 
					curUserId,bpmNodeDef.getBpmProcessDef().getDefKey(),"", bpmNodeDef.getNodeId());
			
			BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
			BpmDefExtProperties prop = procDef.getProcessDefExt().getExtProperties();
			
			json.put("data", jsondata);
			json.put("permission", permission);
			json.put("result", true);
			json.put("prop", prop);
			
			
			json.put("form", formModel);
		}catch(Exception e){
			json.put("result", false);
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return json;
	}


	
	/**
	 * 获取我的待办任务。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMyTask")
	@ResponseBody
	public PageJson getMyTask(HttpServletRequest request, HttpServletResponse response) {
		QueryFilter queryFilter= getQueryFilter(request);
		queryFilter.addFilter("support_mobile_", 1, QueryOP.EQUAL); 
		PageList<BpmTask> list= (PageList<BpmTask>)bpmTaskService.getTasksByUserId(ContextUtil.getCurrentUserId(), queryFilter);
		return new PageJson(list);
	}
	
	/**
	 * 获取我的待办任务。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMyDraft")
	@ResponseBody
	public PageJson getMyDraft(HttpServletRequest request, HttpServletResponse response) {
		QueryFilter queryFilter= getQueryFilter(request);
		queryFilter.addFilter("support_mobile_", 1, QueryOP.EQUAL);
		List<DefaultBpmProcessInstance> list = bpmProcessInstanceManager.getDraftsByUserId(ContextUtil.getCurrentUserId(), queryFilter);
		return new PageJson(list);
	}
	
	/**
	 * 获取手机表单。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getTaskForm")
	@ResponseBody
	public JSONObject getTaskForm(HttpServletRequest request, HttpServletResponse response) {
 		String taskId = RequestUtil.getString(request, "taskId");
		BpmTask task = bpmTaskService.getByTaskId(taskId);
		
		JSONObject json = new JSONObject();
		if (BeanUtils.isEmpty(task)) {
			json.put("result", false);
			json.put("message", "任务不存在，可能已经被处理了.");
			return json;
		}
		
		try {
			String topDefKey="";
			BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
			if(StringUtil.isNotZeroEmpty(bpmProcessInstance.getParentInstId())){
				BpmProcessInstance topInstance =bpmProcessInstanceManager.getTopBpmProcessInstance(bpmProcessInstance);
				topDefKey=topInstance.getProcDefKey();
			}
			String defId = task.getProcDefId();
			String nodeId = task.getNodeId();
			ContextThreadUtil.putCommonVars("defId", defId);//流程定义ID
			ContextThreadUtil.putCommonVars("nodeId", nodeId);//流程定义ID
			
			BpmFormService bpmFormService=BpmFormFactory.getFormService(FormType.MOBILE);
			// 表单
			FormModel formModel = bpmFormService.getByDefId(defId, nodeId, bpmProcessInstance);
			/**
			 * 表单为空的情况。
			 */
			if(formModel==null ){
				json.put("result", "formEmpty");
				return json;
			}
			
			//按钮
			BpmNodeDef taskNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
			List<Button> buttons = BpmUtil.getButtons(taskNodeDef, (DefaultBpmTask) task);
			
			
			List<BoData> boDatas = boDataService.getDataByInst(bpmProcessInstance);
			// BO数据前置处理
			JSONObject data =BoDataUtil.hanlerData(bpmProcessInstance,nodeId, boDatas);
			// BO数据
			json.put("data", data);
			
			//获取意见
			JSONObject opinionJson = boDataService.getFormOpinionJson(task.getProcInstId());
			json.put("opinionList", opinionJson);
			
			//流程定义的权限
			String permission = bpmFormRightsService.getPermission(formModel.getFormKey(), ContextUtil.getCurrentUserId(), 
					task.getProcDefKey(),topDefKey, nodeId);
			json.put("permission",permission); 
			json.put("nodeType", taskNodeDef.getType());
			json.put("taskStatus", task.getStatus());
			
			json.put("buttons", JSONArray.toJSON(buttons));
			json.put("form", formModel);
			json.put("result", true);
		}catch(Exception ex){
			json.put("result", false);
			json.put("msg", ex.getMessage());
			ex.printStackTrace();
		}
		return json;
	}
	
	
	@RequestMapping("getInstForm")
	@ResponseBody
	public JSONObject getInstForm(HttpServletRequest request, HttpServletResponse response) {
		String runId = RequestUtil.getString(request,  "runId");
		JSONObject json = new JSONObject();
		try{
			BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(runId);
			List<BoData> boDatas =boDataService.getDataByInst(bpmProcessInstance);
			JSONObject jsondata=BoDataUtil.hanlerData(  boDatas);
			json.put("data", jsondata);
			
			BpmFormService bpmFormService=BpmFormFactory.getFormService(FormType.MOBILE);
			FormModel formModel=bpmFormService.getInstFormByDefId(bpmProcessInstance);
			json.put("form", formModel);
			
			
			String permission = bpmFormRightsService.getInstPermission(formModel.getFormKey(),
					ContextUtil.getCurrentUserId(),bpmProcessInstance.getProcDefKey());
			json.put("permission", permission);
			json.put("result",true);
		}catch(Exception e){
			e.printStackTrace();
			json.put("result",false);
			json.put("msg", "获取流程数据失败");
			if(json.get("form")==null)json.put("msg", "表单不存在！");
		}
	
		return json;
	}
	//我的请求
	@RequestMapping("myRequestListJson")
	@ResponseBody
	public PageJson myRequestListJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter= getQueryFilter(request);
		queryFilter.addFilter("support_mobile_", 1,QueryOP.EQUAL);
		List<BpmProcessInstance> list =(List)bpmProcessInstanceManager.getMyRequestByUserId(ContextUtil.getCurrentUserId(), queryFilter);
		return new PageJson(list);
	}
	
	//我的办结
	@RequestMapping("getMyCompletedListJson")
	@ResponseBody
	public PageJson getMyCompletedListJson(HttpServletRequest request, HttpServletResponse response){
		QueryFilter queryFilter= getQueryFilter(request);
		queryFilter.addFilter("support_mobile_", 1,QueryOP.EQUAL);
		List<BpmProcessInstance> list =(List)bpmProcessInstanceManager.getMyCompletedByUserId(ContextUtil.getCurrentUserId(), queryFilter);
		return new PageJson(list);
	}
	
	//已办事宜
	@RequestMapping("getAlreadyMattersList")
	@ResponseBody
	public PageJson getAlreadyMattersList(HttpServletRequest request, HttpServletResponse response){
		QueryFilter queryFilter= getQueryFilter(request);
		queryFilter.addFilter("support_mobile_", 1,QueryOP.EQUAL);
		List<Map<String,Object>> list =(List<Map<String,Object>>)bpmProcessInstanceManager.getHandledByUserId(ContextUtil.getCurrentUserId(), queryFilter);
		return new PageJson(list);
	}
	// 办结事宜
	@RequestMapping("getCompletedMattersList")
	@ResponseBody
	public PageJson getCompletedMattersList(HttpServletRequest request, HttpServletResponse response){
		QueryFilter queryFilter= getQueryFilter(request);
		queryFilter.addFilter("support_mobile_", 1,QueryOP.EQUAL);
		List<BpmProcessInstance> list =(List)bpmProcessInstanceManager.getCompletedByUserId(ContextUtil.getCurrentUserId(), queryFilter);
		return new PageJson(list);
	}
	
	//转办代理
	@RequestMapping("getMyTurnOutJson")
	@ResponseBody
	public PageJson getMyTurnOutJson(HttpServletRequest request, HttpServletResponse response){
		QueryFilter queryFilter= getQueryFilter(request); 
		List<DefaultBpmTaskTurn> list = bpmTaskTurnManager.getMyDelegate(ContextUtil.getCurrentUserId(), queryFilter);
		return new PageJson(list);
	}
	
	//抄送转发
	@RequestMapping("getCopyToJson")
	@ResponseBody
	public PageJson getCopyToJson(HttpServletRequest request, HttpServletResponse response){
		QueryFilter queryFilter= getQueryFilter(request);
		queryFilter.addFilter("support_mobile_", 1,QueryOP.EQUAL);
		List<CopyTo> list = copyToManager.getReceiverCopyTo(ContextUtil.getCurrentUserId(), queryFilter);
		return new PageJson(list);
	}
	
	
	
	
	
	
	/**
	 * 流程审批历史
	 */
	@RequestMapping("flowOpinions")
	@ResponseBody
	public List<BpmTaskOpinion> opinionHistory(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String runId= RequestUtil.getString(request, "runId");
		String taskId = RequestUtil.getString(request, "taskId");
		if((StringUtil.isEmpty(runId))&&StringUtil.isNotEmpty(taskId))
		{
			runId=bpmTaskService.getByTaskId(taskId).getProcInstId();
		}
		return bpmOpinionService.getTaskOpinions(runId);
	}
	
	@RequestMapping("getUserMsg")
	@ResponseBody
	public JSONObject getUserMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject object = new JSONObject();
		object.put("user", ContextUtil.getCurrentUser());
		object.put("org", ContextUtil.getCurrentGroup());
		
		QueryFilter queryFilter= getQueryFilter(request);
		queryFilter.addFilter("orguser.user_id_", ContextUtil.getCurrentUserId(), QueryOP.EQUAL);
		queryFilter.addFilter("orguser.org_id_", ContextUtil.getCurrentGroupId(), QueryOP.EQUAL);
		List orgUserList = userManager.queryOrgUserRel(queryFilter);
		if(orgUserList != null && orgUserList.size() > 0){
			object.put("orgUserRel", orgUserList.get(0));
		}
		/*queryFilter.addFilter("support_mobile_", 1, QueryOP.EQUAL); 
		String userId = ContextUtil.getCurrentUserId();
		//TODO 先简单处理
		
		//待办数量
		PageList<BpmTask> list= (PageList<BpmTask>)bpmTaskService.getTasksByUserId(userId, queryFilter);
		object.put("taskSize",list.getPageResult().getTotalCount());
		
		//草稿数量
		PageList<DefaultBpmProcessInstance> list2 = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getDraftsByUserId(userId, queryFilter);
		object.put("draftSize",list2.getPageResult().getTotalCount());
		
		//我的请求
		PageList<BpmProcessInstance> list5 =(PageList)bpmProcessInstanceManager.getMyRequestByUserId(userId, queryFilter);
		object.put("requestSize",list5.getPageResult().getTotalCount());
		//转办代理
		
		
		//抄送未读
		queryFilter.addFilter("isRead", 0, QueryOP.EQUAL);
		PageList<CopyTo> list1= (PageList<CopyTo>)copyToManager.getReceiverCopyTo(userId, queryFilter);
		object.put("copeTo",list1.getPageResult().getTotalCount());*/
		
		return object;
	}
	

	/**
	 * 发送到节点指定人
	 * @Title: nodeUsers 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @return: List
	 */
	@RequestMapping("nodeUsers")
	@ResponseBody
	public Object nodeUsers(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
		
		JSONArray ja = new JSONArray();
		for (BpmNodeDef node :listNodeDefs ) {
			JSONObject jo = new JSONObject();
			jo.put("nodeId",node.getNodeId());
			jo.put("nodeName",node.getName());
			ja.add(jo);
		}
		
		return ja;
	}
}
