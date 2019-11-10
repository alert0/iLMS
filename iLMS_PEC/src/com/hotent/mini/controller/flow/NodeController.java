package com.hotent.mini.controller.flow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.DecideType;
import com.hotent.bpmx.api.constant.FollowMode;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.PrivilegeMode;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.constant.VoteType;
import com.hotent.bpmx.api.def.BpmDefXmlHandler;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.helper.identity.UserQueryPluginHelper;
import com.hotent.bpmx.api.model.process.def.BpmDefLayout;
import com.hotent.bpmx.api.model.process.def.BpmDefSetting;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.api.model.process.def.EventScript;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.def.Restful;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.JumpRule;
import com.hotent.bpmx.api.model.process.nodedef.ext.AutoTaskDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SubProcessNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.Button;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.DefaultJumpRule;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.PrivilegeItem;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.SignRule;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.UserAssignRule;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.DiagramService;
import com.hotent.bpmx.core.engine.def.impl.DefaultBpmDefConditionService;
import com.hotent.bpmx.core.engine.def.impl.handler.BpmDefSettingBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.EventScriptBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.PluginsBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.PrivilegeBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.ServiceNodeBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.SignRulesBpmDefXmlHandler;
import com.hotent.bpmx.core.util.HandlerUtil;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.plugin.core.context.AbstractBpmPluginContext;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleParser;
import com.hotent.bpmx.plugin.execution.globalRestful.context.GlobalRestFulsPluginContext;
import com.hotent.bpmx.plugin.task.restful.context.RestFulsPluginContext;
import com.hotent.bpmx.plugin.task.userassign.UserDefBpmDefXmlHandler;
import com.hotent.bpmx.plugin.task.userassign.context.UserAssignPluginContext;
import com.hotent.form.api.model.Form;
import com.hotent.form.api.model.FormCategory;
import com.hotent.form.api.model.FormType;
import com.hotent.form.persistence.model.DefaultForm;
import com.hotent.org.api.model.GroupType;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.sys.api.jms.MessageUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

/**
 * 
 * <pre>
 * 描述：流程节点配置管理
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2014-6-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/flow/node/")
public class NodeController extends GenericController {
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	private UserQueryPluginHelper userQueryPluginHelper;
	@Resource
	private IUserGroupService userGroupService;
	@Resource
	private BODefManager bODefManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	DiagramService diagramService;
	

	/**
	 * 编辑节点规则
	 */
	@RequestMapping("ruleEdit")
	public ModelAndView ruleEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String processDifinitionId = RequestUtil.getString(request, "definitionId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String parentFlowKey = RequestUtil.getString(request, "parentFlowKey");
		List<BpmNodeDef> nodeDefList = bpmDefinitionAccessor.getAllNodeDef(processDifinitionId);
		UserTaskNodeDef nodeDef = (UserTaskNodeDef) bpmDefinitionService.getBpmNodeDefByDefIdNodeId(processDifinitionId, nodeId);
		return getAutoView().addObject("nodeDef", nodeDef).addObject("parentFlowKey", parentFlowKey).addObject("nodeDefList", nodeDefList).addObject("processDifinitionId", processDifinitionId).addObject("ruleList", nodeDef.getJumpRuleList());
	}

	/**
	 * 节点规则列表
	 */
	@RequestMapping("ruleListJson")
	public @ResponseBody PageJson ruleListJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String processDifinitionId = RequestUtil.getString(request, "definitionId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		UserTaskNodeDef nodeDef = (UserTaskNodeDef) bpmDefinitionService.getBpmNodeDefByDefIdNodeId(processDifinitionId, nodeId);
		List<DefaultJumpRule> rules = nodeDef.getJumpRuleList();
		if(rules == null)rules = Collections.EMPTY_LIST;
		return new PageJson(rules);
	}

	/**
	 * 保存节点的跳转规则
	 */
	@RequestMapping("ruleSave")
	public void ruleSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String nodeId = RequestUtil.getString(request, "nodeId");
		String defId = RequestUtil.getString(request, "processDifinitionId");
		String rulesJson = RequestUtil.getString(request, "rules");

		JSONArray rules1 = JSONArray.fromObject(rulesJson);
		List<JumpRule> jumpRuleList = JSONArray.toList(rules1, DefaultJumpRule.class);
		try {
			if (StringUtil.isNotEmpty(nodeId)) {
				BpmDefXmlHandler<List<JumpRule>> bpmDefXmlHandler = (BpmDefXmlHandler<List<JumpRule>>) AppUtil.getBean("transRulesBpmDefXmlHandler");
				bpmDefXmlHandler.saveNodeXml(defId, nodeId, jumpRuleList);
				resultMsg = "更新节点跳转规则成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}



	/**
	 * 保存节点json 配置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping("nodeUserConditionSave")
	public void nodeUserConditionSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String nodeId = RequestUtil.getString(request, "nodeId");
		String defId = RequestUtil.getString(request, "defId");
		String nodeJson = RequestUtil.getString(request, "nodeJson");
		String parentFlowKey = RequestUtil.getString(request, "parentFlowKey", BpmConstants.LOCAL);
		try {
			if (StringUtil.isNotEmpty(nodeId)) {
				UserDefBpmDefXmlHandler userDefBpmDefXmlHandler = (UserDefBpmDefXmlHandler) AppUtil.getBean(UserDefBpmDefXmlHandler.class);
				userDefBpmDefXmlHandler.saveNodeXml(defId, nodeId, nodeJson, parentFlowKey);
				resultMsg = "更新节点人员配置成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "更新节点人员配置失败";
			e.printStackTrace();
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	

	/**
	 * 节点规则脚本设置
	 */
	@RequestMapping("eventScriptEdit")
	public ModelAndView eventScriptEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String parentFlowKey = RequestUtil.getString(request, "parentFlowKey");
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		Map<ScriptType, String> scriptMap = bpmNodeDef.getScripts();
		return getAutoView().addObject("bpmNodeDef", bpmNodeDef).addObject("defId", defId).addObject("nodeId", nodeId).addObject("parentFlowKey", parentFlowKey).addObject("eventScriptMap", scriptMap);
	}

	/**
	 * 节点事件脚本保存
	 */
	@RequestMapping("eventScriptSave")
	public void eventScriptSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String eventScriptArray = RequestUtil.getString(request, "eventScriptArray");
		try {
			EventScriptBpmDefXmlHandler eventScriptHandler = AppUtil.getBean(EventScriptBpmDefXmlHandler.class);
			JSONArray eventScript = JSONArray.fromObject(eventScriptArray);

			for (int i = 0; i < eventScript.size(); i++) {
				JSONObject objct = eventScript.getJSONObject(i);
				ScriptType scriptType = ScriptType.fromKey(objct.getString("scriptType"));
				EventScript es = new EventScript(scriptType, objct.getString("content"));

				eventScriptHandler.saveNodeXml(defId, nodeId, es);
			}
			writeResultMessage(response.getWriter(), "脚本处理成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "脚本保存失败："+e.getMessage(), ResultMessage.FAIL);
		}

	}

	/**
	 * 分支节点规则脚本设置
	 */
	@RequestMapping("branchConditionEdit")
	public ModelAndView branchConditionEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String parentFlowKey = RequestUtil.getString(request, "parentFlowKey");

		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		return getAutoView().addObject("bpmNodeDef", bpmNodeDef).addObject("defId", defId).addObject("parentFlowKey", parentFlowKey);
	}

	/**
	 * 分支节点规则脚本保存
	 */
	@RequestMapping("branchConditionSave")
	public void branchConditionSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String condition = RequestUtil.getString(request, "condition");
		try {
			DefaultBpmDefConditionService bpmDefHandler = AppUtil.getBean(DefaultBpmDefConditionService.class);
			JSONObject jsonObj = JSONObject.fromObject(condition);
			Map map = (Map) JSONObject.toBean(jsonObj, HashMap.class);
			bpmDefHandler.saveCondition(defId, nodeId, map);
			writeResultMessage(response.getWriter(), "分支网关设置成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "分支网关设置失败", ResultMessage.FAIL);
		}

	}

	/**
	 * 自动任务管理页面
	 */
	@RequestMapping("autoTaskManager")
	public ModelAndView autoTaskManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");

		List<BpmPluginContext> autoTaskPluginList = (List<BpmPluginContext>) AppUtil.getBean("autoTaskPluginList");
		AutoTaskDef autoTaskDef = (AutoTaskDef) bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		BpmPluginContext bpmPluginContext = autoTaskDef.getAutoTaskBpmPluginContext();
		return getAutoView().addObject("autoTaskPluginList", autoTaskPluginList).addObject("defId", defId).addObject("nodeId", nodeId).addObject("bpmPluginContext", bpmPluginContext);
	}

	/**
	 * 自动节点，获取插件数据
	 */
	@RequestMapping("autoTaskPluginGet")
	public ModelAndView autoTaskPluginGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String pluginType = RequestUtil.getString(request, "pluginType");
		List<BpmVariableDef> bpmVariableList = getAllBpmVariableDef(defId, nodeId);

		ModelAndView mv = new ModelAndView("/flow/node/autoTask" + StringUtil.toFirst(pluginType, true) + "Edit");

		AutoTaskDef autoTaskDef = (AutoTaskDef) bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);

		AbstractBpmPluginContext bpmPluginContext = (AbstractBpmPluginContext) autoTaskDef.getAutoTaskBpmPluginContext();

		// 已经选择并保存该插件。
		if (bpmPluginContext != null && bpmPluginContext.getType().equals(pluginType)) {
			BpmPluginDef bpmPluginDef = bpmPluginContext.getBpmPluginDef();
			mv.addObject("bpmPluginDef", bpmPluginDef);
			String json = bpmPluginContext.getJson();
			json = JsonUtil.escapeSpecialChar(json);
			mv.addObject("bpmPluginDefJson", json);
		}

		return mv.addObject("defId", defId).addObject("nodeId", nodeId).addObject("bpmPluginContext", bpmPluginContext).addObject("pluginType", pluginType).addObject("bpmVariableList", bpmVariableList);
	}

	/**
	 * 自动节点保存json 配置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("autoTaskPluginSave")
	public void autoTaskPluginSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String nodeId = RequestUtil.getString(request, "nodeId");
		String defId = RequestUtil.getString(request, "defId");
		String jsonStr = RequestUtil.getString(request, "jsonStr");
		try {
			if (StringUtil.isNotEmpty(nodeId)) {
				ServiceNodeBpmDefXmlHandler serviceNodeDefXmlHandler = AppUtil.getBean(ServiceNodeBpmDefXmlHandler.class);
				serviceNodeDefXmlHandler.saveNodeXml(defId, nodeId, jsonStr);
				resultMsg = "更新节点配置成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "更新节点配置失败";
			e.printStackTrace();
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}


	
	/**
	 * 会签规则特权配置
	 */
	@RequestMapping("getSignConfig")
	@ResponseBody
	public Map<String,Object> bpmNodeSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");

		SignNodeDef signNodeDef = (SignNodeDef) bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<PrivilegeItem> privilegeList = signNodeDef.getPrivilegeList();
		SignRule signRule = signNodeDef.getSignRule();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("privilegeList", getPrivilegeListJson(privilegeList));
		map.put("signRule", SignRule.toJson(signRule));
		return map;
	}
	

	/** 将PrivilegeList 转化成json */
	private JSONObject getPrivilegeListJson(List<PrivilegeItem> privilegeList) {
		JSONObject jsonObject =  new JSONObject();
		if (BeanUtils.isEmpty(privilegeList)) return jsonObject;

		for (PrivilegeItem privilege : privilegeList) {
			JsonConfig config=new JsonConfig();
			UserAssignRuleParser.handJsonConfig(config, privilege.getUserRuleList());
			JSON json= JSONSerializer.toJSON(privilege.getUserRuleList(),config);
			jsonObject.put(privilege.getPrivilegeMode().getKey(), json);
		}
		String json =  jsonObject.toString().replaceAll("null,", "\"\",");
		return JSONObject.fromObject(json);
	}

	/**
	 * 会签规则特权配置
	 */
	@RequestMapping("signConfigSave")
	public void signConfigSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String nodeId = RequestUtil.getString(request, "nodeId");
		String defId = RequestUtil.getString(request, "defId");
		String signRuleJson = RequestUtil.getString(request, "signRule");
		String privilegeListJson = RequestUtil.getString(request, "privilegeList");
		try {
			if (StringUtil.isNotEmpty(nodeId)) {
				List<PrivilegeItem> privilegeList = getPrivilegeList(privilegeListJson);
				SignRule signRule = getSignRule(signRuleJson);

				SignRulesBpmDefXmlHandler signRulesBpmDefXmlHandler = AppUtil.getBean(SignRulesBpmDefXmlHandler.class);
				signRulesBpmDefXmlHandler.saveNodeXml(defId, nodeId, signRule);
				resultMsg = "会签规则更新成功，会签权限人员配置更新失败!";

				PrivilegeBpmDefXmlHandler privilegeBpmDefXmlHandler = AppUtil.getBean(PrivilegeBpmDefXmlHandler.class);
				privilegeBpmDefXmlHandler.saveNodeXml(defId, nodeId, privilegeList);
				resultMsg = "会签节点配置成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = resultMsg == null ? "会签规则配置失败" : resultMsg;
			e.printStackTrace();
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	// /json to SingRule
	private SignRule getSignRule(String json) {
		JSONObject obj = JSONObject.fromObject(json);
		DecideType decideType = DecideType.fromKey(obj.get("decideType").toString());
		VoteType voteType = VoteType.fromKey(obj.get("voteType").toString());
		FollowMode followMode = FollowMode.fromKey(obj.get("followMode").toString());
		int voteAmount = obj.getInt("voteAmount");
		return new SignRule(decideType, voteType, followMode, voteAmount);
	}

	/** PrivilegeList转化成 对象 */
	private List<PrivilegeItem> getPrivilegeList(String json) {
		JSONObject privilegeListJson = JSONObject.fromObject(json);
		List<PrivilegeItem> privilegeList = new ArrayList<PrivilegeItem>();
		for (Object pKey : privilegeListJson.keySet()) {
			PrivilegeItem privilege = new PrivilegeItem();
			PrivilegeMode privilegeMode = PrivilegeMode.fromKey(pKey.toString());
			privilege.setPrivilegeMode(privilegeMode);

			JSONArray ruleArray = (JSONArray) privilegeListJson.get(pKey);
			if (ruleArray.size() == 0)
				continue;

			// /用户规则
			List<UserAssignRule> rules = new ArrayList<UserAssignRule>();
			for (int i = 0; i < ruleArray.size(); i++) {
				JSONObject ruleJsonObject = (JSONObject) ruleArray.get(i);
				UserAssignRule rule = UserAssignRuleParser.getUserAssignRule(ruleJsonObject);
				rules.add(rule);
			}
			privilege.setUserRuleList(rules);
			privilegeList.add(privilege);
		}

		return privilegeList;
	}

	/**
	 * 预览人员条件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("previewCondition")
	@ResponseBody
	public PageJson previewCondition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String conditionArray = RequestUtil.getString(request, "conditionArray");
		String variables = RequestUtil.getString(request, "variables");
		JSONObject obj = JSONObject.fromObject(variables);
		Map<String, String> map = (Map<String, String>) JSONObject.toBean(obj, Map.class);

		List<IUser> users = userQueryPluginHelper.queryUsersByConditions(conditionArray, map);
		return new PageJson(users);
	}

	/**
	 * 获取流程节点的流程变量 bo变量，流程变量
	 */
	@RequestMapping("flowVarDialog")
	public ModelAndView flowVarDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		List<GroupType> dimensionList = userGroupService.getGroupTypes();
		return getAutoView().addObject("defId", defId).addObject("nodeId", nodeId).addObject("dimensionList", dimensionList);
	}

	/**
	 * 该节点能用的所有变量
	 */
	private List<BpmVariableDef> getAllBpmVariableDef(String defId, String nodeId) {
		List<BpmVariableDef> bpmVariableList = new ArrayList<BpmVariableDef>();
		// 全局变量
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		if (defExt.getVariableList() != null) {
			bpmVariableList.addAll(defExt.getVariableList());
		}

		List<BpmNodeDef> list = bpmProcessDefExt.getBpmnNodeDefs();
		for (BpmNodeDef node : list) {
			if (!node.getNodeId().equals(nodeId))
				continue;

			if (!node.getType().toString().equalsIgnoreCase("usertask"))
				continue;

			UserTaskNodeDef targetNodeDef = (UserTaskNodeDef) node;
			if (targetNodeDef.getVariableList() != null) {
				bpmVariableList.addAll(targetNodeDef.getVariableList());
			}

		}
		return bpmVariableList;
	}

	/**
	 * 规则选择框
	 */
	@RequestMapping("userAssignConditionDialog")
	public ModelAndView userAssignConditionDialog(HttpServletRequest request, HttpServletResponse response, String defId, String nodeId) throws Exception {
		return new ModelAndView("/flow/def/userAssignConditionDialog.jsp").addObject("defId", defId).addObject("nodeId", nodeId);
	}


	
	/**
	 * 判断手机表单是否存在。
	 * @param defId
	 * @param bpmDefSetting
	 * @return
	 */
	private boolean isMobileSet(String defId, BpmDefSetting bpmDefSetting){
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
		
		BpmNodeDef nodeDef= bpmDefinitionAccessor.getStartEvent(defId);
		
		List<BpmNodeDef> startNodes= bpmDefinitionAccessor.getStartNodes(defId);
		Map<String,Form> nodeMap= bpmDefSetting.getFormMap(false);
		
		Form startForm=nodeMap.get(nodeDef.getNodeId());
		boolean isSet=isFormEmpty(startForm);
		if(isSet)  return true;
		
		for(BpmNodeDef def:startNodes){
			Form frm=nodeMap.get(def.getNodeId());
			isSet=isFormEmpty(frm);
			if(isSet) return true;
		}
		Form globalForm=defExt.getGlobalMobileForm();
		isSet=isFormEmpty(globalForm);
		return isSet;
	}
	
	private boolean isFormEmpty(Form startForm){
		
		if(startForm!=null && StringUtil.isNotEmpty(startForm.getFormValue())){
			return true;
		}
		return false;
	}

	

	/**
	 * 验证handler。 输入格式为 serviceId +"." + 方法名。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("validHandler")
	public void validHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String handler = RequestUtil.getString(request, "handler");
		int rtn = HandlerUtil.isHandlerValid(handler);
		String template = "{\"result\":\"%s\",\"msg\":\"%s\"}";
		String msg = "";
		switch (rtn) {
		case 0:
			msg = "输入有效";
			break;
		case -1:
			msg = "输入格式无效";
			break;
		case -2:
			msg = "没有service类";
			break;
		case -3:
			msg = "没有对应的方法";
			break;
		default:
			msg = "其他错误";
			break;
		}
		String str = String.format(template, rtn, msg);
		response.getWriter().print(str);
	}

	/**
	 * 流程变量对话框的树 其中包含：bodef的字段，流程变量，流程常量（发起人,当前用户,...）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             Object
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("varTree")
	@ResponseBody
	public Object varTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		boolean removeSub = RequestUtil.getBoolean(request, "removeSub", false);
		boolean removeMain = RequestUtil.getBoolean(request, "removeMain", false);
		String flowKey = RequestUtil.getString(request, "flowKey");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String parentFlowKey = RequestUtil.getString(request, "parentFlowKey");
		
		if(StringUtil.isNotEmpty(parentFlowKey)){
			BpmDefinition parentDefinition = bpmDefinitionService.getBpmDefinitionByDefKey(parentFlowKey, true);
			if(BeanUtils.isNotEmpty(parentDefinition)){
				defId = parentDefinition.getDefId();
			}
		}

		if (StringUtil.isEmpty(defId) && StringUtil.isNotEmpty(flowKey)) {
			BpmDefinition definition = bpmDefinitionService.getBpmDefinitionByDefKey(flowKey, false);
			defId = definition.getDefId();
		}
		
		com.alibaba.fastjson.JSONArray treeJA = new com.alibaba.fastjson.JSONArray();
		
		if(StringUtil.isEmpty(defId)) return treeJA;

		// 获取流程定义
		BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionAccessor.getBpmProcessDef(defId);


		// 获取表单BO树
		List<ProcBoDef> boDefs = procDef.getProcessDefExt().getBoDefList();
		com.alibaba.fastjson.JSONArray boList = new com.alibaba.fastjson.JSONArray();

		if (BeanUtils.isNotEmpty(boDefs)) {
			for (ProcBoDef boDef : boDefs) {
				BODef def = bODefManager.getByAlias(boDef.getKey());
				com.alibaba.fastjson.JSONObject objec = bODefManager.getBOJson(def.getId());

				if (removeSub || removeMain) {// 去掉子表数据
					com.alibaba.fastjson.JSONArray children = objec.getJSONArray("children");
					com.alibaba.fastjson.JSONArray temp = new com.alibaba.fastjson.JSONArray();
					for (Object obj : children) {
						com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) obj;
						if (json.get("children") == null && removeSub) {
							temp.add(json);
						}
						if (json.get("children") != null && removeMain) {
							temp.add(json);
						}
					}
					objec.put("children", temp);
				}
				boList.add(objec);
			}

			com.alibaba.fastjson.JSONObject bos = com.alibaba.fastjson.JSONObject.parseObject("{id:\"0\",parentId:\"-1\",desc:\"表单变量\",name:\"表单变量\",icon:\"fa fa-bold dark\"}");
			bos.put("children", boList);
			treeJA.add(bos);
		}

		// 获取流程变量
		boolean includeBpmConstants=RequestUtil.getBoolean(request, "includeBpmConstants", true);
		JSONObject flowVarJson= getFlowVarJson(procDef, includeBpmConstants,nodeId);
		if(flowVarJson!=null){
			treeJA.add(flowVarJson);
		}
		
		return treeJA;
	}

	private JSONObject getFlowVarJson(BpmProcessDef<BpmProcessDefExt> procDef, boolean includeBpmConstants, String nodeId) {
		List<BpmVariableDef> variables = new ArrayList<BpmVariableDef>();
		boolean isBmpnInstId = true;//流程变量中是否加入流程实例ID（开始节点不加入）
		// 全局变量
		if(procDef.getProcessDefExt().getVariableList()!=null){
			variables.addAll(procDef.getProcessDefExt().getVariableList());
		}
		JSONObject flowVariable = JSONObject.fromObject("{desc:\"流程变量\",name:\"流程变量\",icon:\"fa fa-bold dark\",\"nodeType\":\"root\"}");
		
		// 节点变量
		if(StringUtil.isNotEmpty(nodeId)){
			BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(procDef.getProcessDefinitionId(), nodeId);
			if(bpmNodeDef !=null && bpmNodeDef instanceof UserTaskNodeDef){
				UserTaskNodeDef taskNodeDef = (UserTaskNodeDef) bpmNodeDef;
				if(taskNodeDef.getVariableList()!=null){
					variables.addAll(taskNodeDef.getVariableList());
				}
			}else{
				//开始节点流程变量中不加入流程实例ID
				if(bpmNodeDef.getType().equals(NodeType.START)){
					isBmpnInstId = false;
				}
			}
			
		}
		
		JSONArray varList = new JSONArray();
		if (BeanUtils.isNotEmpty(variables)){
			for (BpmVariableDef variable : variables) {
				String name = variable.getName();
				variable.setName(variable.getVarKey()); // @ 前端流程变量都是 取name，
														// 而名字为desc
				JSONObject obj = JSONObject.fromObject(variable);
				obj.accumulate("nodeType", "var");
				obj.accumulate("desc", name);
				varList.add(obj);
			}
		}
		// 如果表单变量需要包含流程常量
		if (includeBpmConstants) {
			JSONObject bussinessKey = JSONObject.fromObject("{\"name\":\"" + BpmConstants.BPM_FLOW_KEY + "\",\"desc\":\"流程定义Key\",\"nodeType\":\"var\"}");
			JSONObject startUser = JSONObject.fromObject("{\"name\":\"" + BpmConstants.START_USER + "\",\"desc\":\"发起人\",\"nodeType\":\"var\"}");

			if(isBmpnInstId){
				JSONObject bmpnInstId = JSONObject.fromObject("{\"name\":\"" + BpmConstants.PROCESS_INST_ID + "\",\"desc\":\"流程实例ID\",\"nodeType\":\"var\"}");
				varList.add(bmpnInstId);
			}
			varList.add(bussinessKey);
			varList.add(startUser);
		}
		if(varList.isEmpty()){
			return null;
		}
		flowVariable.put("children", varList);
		return flowVariable;
	}

	/**
	 * 获取流程节点的列表 一些基本信息而已
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             Object
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("getNodes")
	@ResponseBody
	public Object getNodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId", "");
		List<BpmNodeDef> nodeDefs = bpmDefinitionAccessor.getAllNodeDef(defId);

		JSONArray list = new JSONArray();
		for (BpmNodeDef node : nodeDefs) {
			JSONObject jo = new JSONObject();
			jo.put("name", node.getName());
			jo.put("nodeId", node.getNodeId());
			jo.put("type", node.getType().toString());
			list.add(jo);
		}
		return list;
	}
	
	/**
	 * 流程定义页面
	 * @return json 
	 * @throws Exception
	 */
	@RequestMapping("nodeDefSetting")
	public ModelAndView nodeDefSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String topDefKey = RequestUtil.getString(request, "topDefKey","");
		
		DefaultBpmDefinition bpmDefinition = null;
		BpmDefLayout bpmDefLayout = null;
		if (StringUtil.isNotEmpty(defId)) {
			bpmDefinition = bpmDefinitionManager.getById(defId);
			// 流程图layout
			bpmDefLayout = diagramService.getLayoutByDefId(defId);
		}
		//if(StringUtil.isEmpty(topDefKey)) topDefKey=bpmDefinition.getDefKey();
		List<JmsHandler<JmsVo>> messageTypelist = MessageUtil.getHanlerList();
		return getAutoView().addObject("bpmDefinition", bpmDefinition)
				.addObject("bpmDefLayout", bpmDefLayout)
				.addObject("messageTypelist", messageTypelist)
				.addObject("topDefKey", topDefKey);
	}
	
	/**流程定义设置json数据
	 * @return json 
	 * @throws Exception
	 */
	@RequestMapping("getDefSetting")
	@ResponseBody
	public JSONObject getDefSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String topDefKey = RequestUtil.getString(request, "topDefKey", "");
		JSONObject returnData = new JSONObject();
		
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		List<BpmNodeDef> nodeDefList = bpmProcessDefExt.getBpmnNodeDefs();
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		
		BpmDefSetting bpmDefSetting = new BpmDefSetting();
		bpmDefSetting.setParentDefKey(topDefKey);
		//全局表单
		bpmDefSetting.setGlobalForm(StringUtil.isEmpty(topDefKey)?defExt.getGlobalForm():defExt.getGlobalFormByDefKey(topDefKey, false));
		bpmDefSetting.setGlobalMobileForm(StringUtil.isEmpty(topDefKey)? defExt.getGlobalMobileForm():defExt.getGlobalFormByDefKey(topDefKey, true));
		bpmDefSetting.setInstForm(StringUtil.isEmpty(topDefKey)?defExt.getInstForm():defExt.getInstFormByDefKey(topDefKey, false));
		bpmDefSetting.setInstMobileForm(StringUtil.isEmpty(topDefKey)? defExt.getInstMobileForm():defExt.getInstFormByDefKey(topDefKey, true));
		//全局restful事件
		bpmDefSetting.setGlobalRestfuls(getGlobalRestFulList(request, response));
		//节点设置，节点表单，节点信息，节点脚本
		JSONArray nodes = new JSONArray();
		List<Form> formList = new ArrayList<Form>();
		List<NodeProperties> properties = new ArrayList<NodeProperties>();
		Map<String,List<Button>> btnMap = new HashMap<String, List<Button>>();
		Map<String, JSONObject> nodeScriptMap = new HashMap<String, JSONObject>();
		
		handNodeDefSetting(topDefKey,nodeDefList,properties,formList,nodes,btnMap,nodeScriptMap);
		bpmDefSetting.setNodeProperties(properties);
		bpmDefSetting.setNodeForms(formList);
		
		String jsonObject = com.alibaba.fastjson.JSON.toJSON(bpmDefSetting).toString();
		String userJson = getNodesUserJson(request, response).toString();
		String restfulJson = getNodesRestFulJson(request, response).toString();
		
		returnData.put("bpmDefSetting",JSONObject.fromObject(jsonObject));
		returnData.put("nodes", nodes);
		returnData.put("nodeUserMap",JSONObject.fromObject(userJson));
		returnData.put("nodeBtnMap", btnMap);
		returnData.put("nodeScriptMap", nodeScriptMap);
		returnData.put("nodeRestfulMap", restfulJson);

		return returnData;
	}
	
	//整理用户节点  节点信息，节点属性，节点表单，手机表单,节点按钮
	private void handNodeDefSetting(String parentDefKey,List<BpmNodeDef> nodeDefList,
			List<NodeProperties> properties,List<Form> formList, JSONArray nodes, 
			Map<String, List<Button>> btnMap,Map<String, JSONObject> nodeScriptMap){
		JSONObject node = new JSONObject();
		for (BpmNodeDef nodeDef : nodeDefList) {
			String nodeId = nodeDef.getNodeId();
			NodeType type = nodeDef.getType();
			if (NodeType.START.equals(type) || NodeType.USERTASK.equals(type) || NodeType.SIGNTASK.equals(type)) {
				//节点信息
				node.put("name", nodeDef.getName());
				node.put("nodeId", nodeId);
				node.put("type", nodeDef.getType().toString());
				nodes.add(node);
				
				//节点表单，节点按钮，节点手机表单
				Form form = null;
				Form mobileForm = null;
				NodeProperties propertie = null;
				//本地节点。
				if (StringUtil.isEmpty(parentDefKey) || BpmConstants.LOCAL.equals(parentDefKey)){
					propertie =nodeDef.getLocalProperties();
					form = nodeDef.getForm();
					mobileForm = nodeDef.getMobileForm();
				}else{
					propertie = nodeDef.getPropertiesByParentDefKey(parentDefKey);
					form = nodeDef.getSubForm(parentDefKey, FormType.PC);
					mobileForm = nodeDef.getSubForm(parentDefKey, FormType.MOBILE);
				}
				//开始节点 添加属性配置
				if(propertie != null){
					propertie.setNodeId(nodeId);
					properties.add(propertie);
				}
				
				//设置form默认值
				if(form== null) {
					form = new DefaultForm();
					form.setType(FormCategory.INNER);
				}
				if(mobileForm== null){
					mobileForm = new DefaultForm();
					mobileForm.setFormType(FormType.MOBILE.value());
				}
				
				form.setNodeId(nodeId);
				mobileForm.setNodeId(nodeId);
				formList.add(mobileForm);
				formList.add(form);

				List<Button> buttons = nodeDef.getButtons();
				btnMap.put(nodeDef.getNodeId(), buttons);
			} else if (NodeType.SUBPROCESS.equals(type)) {
				SubProcessNodeDef subProcessNodeDef = (SubProcessNodeDef) nodeDef;
				BpmProcessDef<? extends BpmProcessDefExt> processDef = subProcessNodeDef.getChildBpmProcessDef();
				List<BpmNodeDef> bpmNodeDefs = processDef.getBpmnNodeDefs();
				handNodeDefSetting(parentDefKey, bpmNodeDefs, properties, formList,nodes,btnMap, nodeScriptMap);
			}
			//节点脚本
			Map<ScriptType, String> scriptMap = nodeDef.getScripts();
			if(!scriptMap.isEmpty()){
				nodeScriptMap.put(nodeId, JSONObject.fromObject(com.alibaba.fastjson.JSON.toJSON(scriptMap).toString()));
			}
			
		}
	}
	//获取所有节点人员的JSON
	private Object getNodesUserJson(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String defId = RequestUtil.getString(request, "defId", "");
		String parentFlowKey = RequestUtil.getString(request, "topDefKey", BpmConstants.LOCAL);

		List<BpmNodeDef> nodeDefList = bpmDefinitionAccessor.getAllNodeDef(defId);
		com.alibaba.fastjson.JSONObject jobject = new com.alibaba.fastjson.JSONObject();
		List<BpmNodeDef> userNodes = new ArrayList<BpmNodeDef>();
		for (BpmNodeDef nodeDef : nodeDefList) {
			if (nodeDef.getType() == NodeType.USERTASK || nodeDef.getType() == NodeType.SIGNTASK)
				userNodes.add(nodeDef);
		}

		for (int i = 0; i < userNodes.size(); i++) {
			BpmNodeDef node = userNodes.get(i);
			UserAssignPluginContext userPluginContext = (UserAssignPluginContext) node.getPluginContext(UserAssignPluginContext.class);
			if (userPluginContext == null) {
				jobject.put(node.getNodeId(), new JSONArray());
			} 
			else {
				//String nodeConditionJson = userPluginContext.getJsonByParentFlowKey(parentFlowKey);
				String nodeConditionJson = userPluginContext.getJsonByParentFlowKey(parentFlowKey);
				Object nodeConditionJsonAry = com.alibaba.fastjson.JSONArray.parse(nodeConditionJson);
				jobject.put(node.getNodeId(), nodeConditionJsonAry);
			}
		}
		return jobject;
	}
	
	//获取全局restful事件
	private List<Restful> getGlobalRestFulList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Restful> globalRests = new ArrayList<Restful>();
		String defId = RequestUtil.getString(request, "defId", "");
		String parentFlowKey = RequestUtil.getString(request, "topDefKey", BpmConstants.LOCAL);
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		List<BpmPluginContext> plugins = defExt.getPluginContextList();
		for (BpmPluginContext bpmPluginContext : plugins) {
			if(bpmPluginContext instanceof GlobalRestFulsPluginContext){
				GlobalRestFulsPluginContext context = (GlobalRestFulsPluginContext) bpmPluginContext;
				List<Restful> restFuls = context.getByParentFlowKey(parentFlowKey);
				if(BeanUtils.isNotEmpty(restFuls)){
					globalRests.addAll(restFuls);
				}
			}
		}
		return globalRests;
	}
	
	//获取所有节点事件的JSON
	private Object getNodesRestFulJson(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String defId = RequestUtil.getString(request, "defId", "");
		String parentFlowKey = RequestUtil.getString(request, "topDefKey", BpmConstants.LOCAL);

		List<BpmNodeDef> nodeDefList = bpmDefinitionAccessor.getAllNodeDef(defId);
		com.alibaba.fastjson.JSONObject jobject = new com.alibaba.fastjson.JSONObject();
		List<BpmNodeDef> restfulNodes = new ArrayList<BpmNodeDef>();
		for (BpmNodeDef nodeDef : nodeDefList) {
			if (NodeType.USERTASK.equals(nodeDef.getType())  || NodeType.SIGNTASK.equals(nodeDef.getType())
					||NodeType.START.equals(nodeDef.getType())||NodeType.END.equals(nodeDef.getType()))
				restfulNodes.add(nodeDef);
		}

		for (int i = 0; i < restfulNodes.size(); i++) {
			BpmNodeDef node = restfulNodes.get(i);
			RestFulsPluginContext context = (RestFulsPluginContext)node.getPluginContext(RestFulsPluginContext.class);
			if (context == null) {
				jobject.put(node.getNodeId(), new JSONArray());
			} 
			else {
				String nodeRestfulJson = context.getJsonByParentFlowKey(parentFlowKey);
				Object nodeRestfulJsonAry = com.alibaba.fastjson.JSONArray.parse(nodeRestfulJson);
				jobject.put(node.getNodeId(), nodeRestfulJsonAry);
			}
		}
		return jobject;
	}
	
	/**
	 * 保存流程配置
	 * 
	 * @param request
	 * @param reponse
	 * @throws Exception
	 */
	@RequestMapping("saveDefConf")
	public void saveDefConf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String topDefKey = RequestUtil.getString(request, "topDefKey");
		String defSettingJson = RequestUtil.getString(request, "bpmDefSetting");
		String userJson = RequestUtil.getString(request,"nodeUserMap" );
		String restfulJson = RequestUtil.getString(request,"nodeRestfulMap" );
		
		//保存表单
		BpmDefSettingBpmDefXmlHandler bpmDefSettingBpmDefXmlHandler = AppUtil.getBean(BpmDefSettingBpmDefXmlHandler.class);
		try {
			BpmDefSetting bpmDefSetting = com.alibaba.fastjson.JSON.parseObject(defSettingJson, BpmDefSetting.class);
			bpmDefSettingBpmDefXmlHandler.saveNodeXml(defId, null, bpmDefSetting);
			
			//设置是否支持手机表单。
			int supportMobile=isMobileSet(defId, bpmDefSetting)?1:0;
			DefaultBpmDefinition def= (DefaultBpmDefinition) bpmDefinitionService.getBpmDefinitionByDefId(defId);
			def.setSupportMobile(supportMobile);
			bpmDefinitionManager.update(def);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "保存表单参数失败："+e.getMessage(), ResultMessage.FAIL);
			return;
		}
		
		//保存全局restful插件
		try {
			JSONObject settingJson = JSONObject.fromObject(defSettingJson);
			String globalRestfulStr = settingJson.getString("globalRestfuls");
			if(StringUtil.isNotEmpty(globalRestfulStr)||settingJson.getJSONArray("globalRestfuls").size()>0){
				GlobalRestFulsPluginContext globalContext = new GlobalRestFulsPluginContext();
				globalContext.parse(globalRestfulStr);
				BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
				DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
				List<BpmPluginContext> plugins = changeOnePluginContextForSave(defExt.getPluginContextList(),globalContext);
				PluginsBpmDefXmlHandler bpmDefXmlHandler = (PluginsBpmDefXmlHandler) AppUtil.getBean(PluginsBpmDefXmlHandler.class);
				bpmDefXmlHandler.saveNodeXml(defId, null, plugins);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String theNode = "";
		try {
			//保存人员
			JSONObject jsonObject = JSONObject.fromObject(userJson);
			Set<String> nodeIds = jsonObject.keySet();
			for (String nodeId : nodeIds) {
				theNode = nodeId;
				UserDefBpmDefXmlHandler userDefBpmDefXmlHandler = (UserDefBpmDefXmlHandler) AppUtil.getBean(UserDefBpmDefXmlHandler.class);
				userDefBpmDefXmlHandler.saveNodeXml(defId, nodeId, jsonObject.get(nodeId).toString(), topDefKey);
			}
		}catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), theNode+"节点人员失败："+e.getMessage(), e.getMessage(), ResultMessage.FAIL);
		}
		
		try {
			//保存节点restful事件
			JSONObject restJsonObject = JSONObject.fromObject(restfulJson);
			Set<String> restNodeIds = restJsonObject.keySet();
			for (String nodeId : restNodeIds) {
				BpmNodeDef nodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
				RestFulsPluginContext context = new RestFulsPluginContext();
				context.parse(restJsonObject.getString(nodeId));
				List<BpmPluginContext> plugins = changeOnePluginContextForSave(nodeDef.getBpmPluginContexts(),context);
				PluginsBpmDefXmlHandler bpmDefXmlHandler = (PluginsBpmDefXmlHandler) AppUtil.getBean(PluginsBpmDefXmlHandler.class);
				bpmDefXmlHandler.saveNodeXml(defId, nodeId, plugins);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		writeResultMessage(response.getWriter(), "流程配置保存成功", ResultMessage.SUCCESS);
	}
	
	
	/**
	 * 替换要保存的插件，
	 * @return List<BpmPluginContext>
	 */
	private List<BpmPluginContext> changeOnePluginContextForSave(List<BpmPluginContext> contexts,BpmPluginContext pluginContext){
		List<BpmPluginContext> bpmPluginContexts = new ArrayList<BpmPluginContext>();
		bpmPluginContexts.add(pluginContext);
		
		if(BeanUtils.isEmpty(contexts)) return bpmPluginContexts;
		
		for(BpmPluginContext context : contexts){
			if(!context.getClass().isAssignableFrom(pluginContext.getClass())){
				bpmPluginContexts.add(context);
			}
		}
		return bpmPluginContexts;
	}
	
	
	
	@RequestMapping("getNodeOutcomes")
	@ResponseBody
	public Object getNodeOutcomes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		BpmNodeDef nodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		if(nodeDef == null) return null;
		JSONObject data = new JSONObject();
		JSONArray outComes = new JSONArray();
		for(BpmNodeDef n :nodeDef.getOutcomeNodes()){
			JSONObject nodeJson = new JSONObject();
			nodeJson.put("nodeName", n.getName());
			nodeJson.put("nodeId", n.getNodeId());
			outComes.add(nodeJson);
		}
		data.put("scriptMap", nodeDef.getConditions());
		data.put("outComes", outComes);
		return data;
	}
	
	/**
	 * 自动任务信息明细
	 */
	@RequestMapping("getNodeAutoTask")
	@ResponseBody
	public Object getNodeAutoTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		
		AutoTaskDef autoTaskDef = (AutoTaskDef) bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		AbstractBpmPluginContext bpmPluginContext = (AbstractBpmPluginContext)autoTaskDef.getAutoTaskBpmPluginContext();
		if(bpmPluginContext == null)return null;
		
		JSONObject object = JSONObject.fromObject( bpmPluginContext.getJson());
		object.put("title", bpmPluginContext.getTitle());
		return object;
	}
}
