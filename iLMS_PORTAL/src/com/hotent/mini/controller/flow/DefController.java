package com.hotent.mini.controller.flow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.HttpUtil;
import com.hotent.base.core.util.PinyinUtil;
import com.hotent.base.core.util.ZipUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.constant.DesignerType;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.inst.ISkipCondition;
import com.hotent.bpmx.api.model.process.def.BpmBoDef;
import com.hotent.bpmx.api.model.process.def.BpmDefExtProperties;
import com.hotent.bpmx.api.model.process.def.BpmDefLayout;
import com.hotent.bpmx.api.model.process.def.BpmDefSetting;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmFormInit;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDefComparator;
import com.hotent.bpmx.api.model.process.nodedef.ext.CallActivityNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.Button;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.plugin.core.context.PluginParse;
import com.hotent.bpmx.api.service.BpmDefTransform;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.api.service.DiagramService;
import com.hotent.bpmx.api.service.FlowStatusService;
import com.hotent.bpmx.core.engine.def.BpmDefUtil;
import com.hotent.bpmx.core.engine.def.impl.handler.BoBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.BpmDefSettingBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.BpmFormInitBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.ButtonsBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.impl.handler.PropertiesBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.task.skip.SkipConditionUtil;
import com.hotent.bpmx.persistence.manager.BpmDefAuthorizeManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProBoManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.BpmProBo;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.plugin.task.userassign.context.UserAssignPluginContext;
import com.hotent.form.api.model.Form;
import com.hotent.form.persistence.manager.BpmFormRightManager;
import com.hotent.sys.api.jms.MessageUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.constants.CategoryConstants;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.util.ContextUtil;

/**
 * 流程定义管理
 * 
 * @author heyifan
 *
 */
@Controller
@RequestMapping("/flow/def/")
public class DefController extends GenericController {
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	DiagramService diagramService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	PropertiesBpmDefXmlHandler propertiesBpmDefXmlHandler;
	@Resource
	SysTypeManager sysTypeManager;
	@Resource
	BpmDefAuthorizeManager bpmDefAuthorizeManager;
	@Resource
	BpmDefTransform bpmDefTransform;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BoBpmDefXmlHandler boBpmDefXmlHandler;
	@Resource
	BpmFormInitBpmDefXmlHandler bpmFormInitBpmDefXmlHandler;
	@Resource
	BpmProBoManager bpmProBoManager;
	@Resource
	ButtonsBpmDefXmlHandler buttonsBpmDefXmlHandler;
	@Resource
	FlowStatusService flowStatusService;
	@Resource
	BpmTaskService bpmTaskService;
	@Resource
	BpmFormRightManager bpmFormRightManager;
	
	private final static String ROOT_PATH = "attachFiles" + File.separator + "tempZip"; // 导入和导出的文件操作根目录
	
	/**
	 * 返回流程设计生成的BPMNxml
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("bpmnXml")
	public void bpmnXml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/xml;charset=utf-8");
		String defId = request.getParameter("defId");
		PrintWriter writer = response.getWriter();
		if (StringUtils.isEmpty(defId)) {
			writer.print("no def input");
			return;
		}

		DefaultBpmDefinition po = bpmDefinitionManager.getById(defId);
		String bpmnXml = po.getBpmnXml();
		writer.print(bpmnXml);

	}

	/**
	 * 返回流程设计的xml
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("designXml")
	public void designXml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/xml;charset=utf-8");
		String defId = request.getParameter("defId");
		PrintWriter writer = response.getWriter();
		if (StringUtils.isEmpty(defId)) {
			writer.print("no def input");
			return;
		}
		DefaultBpmDefinition po = bpmDefinitionManager.getById(defId);
		String defXml = po.getDefXml();
		response.getWriter().print(defXml);
	}

	
	
	/**
	 * 流程通过flex 在线设置流程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("defDesign")
	public ModelAndView design(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		if (StringUtil.isNotEmpty(defId)) {
			DefaultBpmDefinition po = bpmDefinitionManager.getById(defId);
			request.setAttribute("bpmDefinition", po);
			request.setAttribute("name", po.getName());
		} else {
			request.setAttribute("name", "未命名");
		}
		String uId = ContextUtil.getCurrentUserId(); // 当前用户id
		// xml流程分类
		String xmlRecord = sysTypeManager.getXmlByKey(CategoryConstants.CAT_FLOW.key(), uId);

		request.setAttribute("xmlRecord", xmlRecord);
		request.setAttribute("defId", defId); // defId流程定义id
		return getAutoView();
	}

	/**
	 * 通过h5来设计一个流程
	 * @param request
	 * @param response
	 */
	@RequestMapping("webDefDesign")
	@ResponseBody
	public Map<String,Object> webDefDesign(HttpServletRequest request, HttpServletResponse response){
		String defId = RequestUtil.getString(request, "defId");
		Map<String,Object> map = new HashMap<String,Object>();
		DefaultBpmDefinition def = bpmDefinitionManager.getById(defId);
		if (BeanUtils.isNotEmpty(def)) {
			map.put("defId", def.getDefId());
			map.put("name", def.getName());
			map.put("desc", def.getDesc());
			map.put("defKey", def.getDefKey());
			map.put("modelId", def.getDefId());
			map.put("model", def.getDefJson());
			map.put("reason", def.getReason());
			map.put("version", def.getVersion());
			SysType type = sysTypeManager.get(def.getTypeId());
			if(BeanUtils.isNotEmpty(type)){
				map.put("typeId", def.getTypeId());
				map.put("typeName", type.getName());
			}	
		} 
		return map;
	}
	
	/**
	 * web流程设计器保存
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("webDefSave")
	public void webDefSave(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DefaultBpmDefinition bpmDefinition = this.getWebDesignFromRequest(request);
		Boolean isDeploy = Boolean.parseBoolean(request.getParameter("deploy"));
		String resultMsg = "";
		try {
			if (StringUtils.isEmpty(bpmDefinition.getDefId())) {
				List<DefaultBpmDefinition> oldDefs = bpmDefinitionManager.queryByDefKey(bpmDefinition.getDefKey());
				if (BeanUtils.isNotEmpty(oldDefs)) {
					resultMsg = "流程定义KEY“" + bpmDefinition.getDefKey() + "” 已经存在于：" + oldDefs.get(0).getName();
					response.getWriter().print(resultMsg);
					return;
				}
			}
			if (isDeploy) {
				bpmDefinition.setUpdateTime(new Date());
				if(!bpmDefinitionService.deploy(bpmDefinition)){
					throw new Exception("流程发布失败");
				}
			} else {
				if (StringUtils.isNotEmpty(bpmDefinition.getDefId())) {
					bpmDefinitionService.updateBpmDefinition(bpmDefinition);
				} else {
					bpmDefinitionService.saveDraft(bpmDefinition);
				}
			}
			response.getWriter().print("success");
		} catch (Exception ex) {
			ex.printStackTrace();
			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(ex);
			response.getWriter().print(rootCauseMessage);
		}
	}
	
	
	/**
	 * flex，保存发布流程信息。
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("flexDefSave")
	public void flexDefSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isDeploy = Boolean.parseBoolean(request.getParameter("deploy"));
		DefaultBpmDefinition bpmDefinition = this.getFromRequest(request);
		String resultMsg = "";
		try {
			if (StringUtils.isEmpty(bpmDefinition.getDefId())) {
				List<DefaultBpmDefinition> oldDefs = bpmDefinitionManager.queryByDefKey(bpmDefinition.getDefKey());
				if (BeanUtils.isNotEmpty(oldDefs)) {
					resultMsg = "流程定义KEY“" + bpmDefinition.getDefKey() + "” 已经存在于：" + oldDefs.get(0).getName();
					response.getWriter().print(resultMsg);
					return;
				}
			}
			if (isDeploy) {
				bpmDefinition.setUpdateTime(new Date());
				bpmDefinitionService.deploy(bpmDefinition);
			} else {
				if (StringUtils.isNotEmpty(bpmDefinition.getDefId())) {
					bpmDefinitionService.updateBpmDefinition(bpmDefinition);
				} else {
					bpmDefinitionService.saveDraft(bpmDefinition);
				}
			}
			response.getWriter().print("success");
		} catch (Exception ex) {
			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(ex);
			response.getWriter().print(rootCauseMessage);
		}
	}

	/**
	 * 流程定义列表(分页条件查询)数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("listJson")
	@ResponseBody
	public PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.addFilter("is_main_", "Y", QueryOP.EQUAL);
		// 查询列表
		PageList<DefaultBpmDefinition> bpmDefinitionList = (PageList<DefaultBpmDefinition>) bpmDefinitionManager.queryList(queryFilter);

		return new PageJson(bpmDefinitionList);
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
	@RequestMapping("defGet")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String defId = RequestUtil.getString(request, "defId");
		DefaultBpmDefinition bpmDefinition = null;

		if (StringUtil.isNotEmpty(defId)) {
			bpmDefinition = bpmDefinitionManager.getById(defId);
		}
		return getAutoView().addObject("bpmDefinition", bpmDefinition).addObject("returnUrl", preUrl);
	}

	@RequestMapping("defSetting")
	public ModelAndView setting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String defId = RequestUtil.getString(request, "defId");
		DefaultBpmDefinition bpmDefinition = null;
		BpmDefLayout bpmDefLayout = null;
		if (StringUtil.isNotEmpty(defId)) {
			bpmDefinition = bpmDefinitionManager.getById(defId);
			// 流程图layout
			bpmDefLayout = diagramService.getLayoutByDefId(defId);
		}
		List<JmsHandler<JmsVo>> messageTypelist = MessageUtil.getHanlerList();
		return getAutoView().addObject("bpmDefinition", bpmDefinition).addObject("bpmDefLayout", bpmDefLayout).addObject("messageTypelist", messageTypelist).addObject("returnUrl", preUrl);
	}

	private InputStream getDiagramByInstance(DiagramService diagramService, FlowStatusService flowStatusService, String bpmnInstId) {
		BpmInstService bpmInstService = AppUtil.getBean(BpmInstService.class);
		BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstanceByBpmnInstId(bpmnInstId);
		Map<String, String> colorMap = flowStatusService.getProcessInstanceStatus(bpmProcessInstance.getId());
		return diagramService.getDiagramByDefId(bpmProcessInstance.getProcDefId(), colorMap);
	}

	/**
	 * 流程图
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("image")
	public void image(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = request.getParameter("defId");
		String bpmnInstId = request.getParameter("bpmnInstId");
		String taskId = request.getParameter("taskId");
		InputStream is = null;
		if (StringUtils.isNotEmpty(defId)) {
			is = diagramService.getDiagramByBpmnDefId(defId);
		} else if (StringUtils.isNotEmpty(bpmnInstId)) {
			is = getDiagramByInstance(diagramService, flowStatusService, bpmnInstId);
		} else if (StringUtils.isNotEmpty(taskId)) {
			BpmTask bpmTask = bpmTaskService.getByTaskId(taskId);
			is = getDiagramByInstance(diagramService, flowStatusService, bpmTask.getBpmnInstId());
		}
		if (is == null) return;
		
		response.setContentType("image/png");
		OutputStream out = response.getOutputStream();
		FileUtil.writeInput(is, out);
		
	}

	/**
	 * 保存流程定义。
	 * 
	 * @param request
	 * @param response
	 * @param bpmDefinition
	 * @throws Exception
	 *             void
	 */
	@RequestMapping("save")
	public void save(MultipartHttpServletRequest request, HttpServletResponse response, DefaultBpmDefinition bpmDefinition) throws Exception {
		String resultMsg = null;
		String isDeploy = request.getParameter("isDeploy");
		String isSave = request.getParameter("isSave");
		String defXml = request.getParameter("defXml");

		MultipartFile fileLoad = request.getFile("file");
		if (fileLoad == null) {// 有文件就优先取文件内容
			bpmDefinition.setDefXml(defXml);
		} else {
			bpmDefinition.setDefXml(FileUtil.inputStream2String(fileLoad.getInputStream()));
		}

		bpmDefinition.setDesigner(DesignerType.ECLIPSE.name());
		try {
			if ("true".equals(isDeploy)) {// deploy
				if (StringUtils.isNotEmpty(bpmDefinition.getDefId())) {
					DefaultBpmDefinition oldBpmDefinition = bpmDefinitionManager.getById(bpmDefinition.getDefId());
					BeanUtils.copyNotNullProperties(oldBpmDefinition, bpmDefinition);
					oldBpmDefinition.setUpdateTime(new Date());
					bpmDefinitionService.deploy(oldBpmDefinition);
					resultMsg = "成功发布新版本流程定义！";
				} else {
					bpmDefinitionService.deploy(bpmDefinition);
					resultMsg = "成功发布流程定义！";
				}
			} else if ("true".equals(isSave) && StringUtils.isNotEmpty(bpmDefinition.getDefId())) {
				DefaultBpmDefinition oldBpmDefinition = bpmDefinitionManager.getById(bpmDefinition.getDefId());
				BeanUtils.copyNotNullProperties(oldBpmDefinition, bpmDefinition);
				bpmDefinitionService.updateBpmDefinition(oldBpmDefinition);
				resultMsg = "成功更新流程定义！";
			} else {// 保存草稿
				bpmDefinitionService.saveDraft(bpmDefinition);
				resultMsg = "成功保存流程定义草稿！";
			}

			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();

			writeResultMessage(response.getWriter(), " 操作出错：" + e.getMessage(), ResultMessage.FAIL);
		}

	}

	/**
	 * 批量删除系统用户记录(逻辑删除)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "defId");
			bpmDefinitionManager.removeByIds(aryIds);

			message = new ResultMessage(ResultMessage.SUCCESS, "删除流程定义成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除流程定义失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("removeByDefIds")
	public void removeByDefIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "defId");
			bpmDefinitionManager.removeDefIds(aryIds);

			message = new ResultMessage(ResultMessage.SUCCESS, "删除流程定义成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除流程定义失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 流程定义对话框。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("defDialog")
	public ModelAndView dialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = RequestUtil.getString(request, "type", "single");

		ModelAndView mv = getAutoView();
		mv.addObject("type", type);
		return mv;
	}

	/**
	 * BO定义对话框。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("boDialog")
	public ModelAndView boDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		getDialog(request, response, "boDialog", mv);
		return mv;
	}

	/**
	 * BO定义对话框。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("boSetDialog")
	public ModelAndView boSetDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		mv.addObject("flowKey", "");
		mv.addObject("nodeId", "");
		getDialog(request, response, "boDialog", mv);
		return mv;
	}

	public void getDialog(HttpServletRequest request, HttpServletResponse response, String maintType, ModelAndView mv) throws Exception {
		String type = RequestUtil.getString(request, "type", "single");
		mv.addObject("type", type);
	}

	/**
	 * 保存流程设置的BO设置
	 * 
	 * <pre>
	 * 1.保存BO设定。
	 * 2.保存表单初始化数据。
	 * 
	 * {
	 * 	    "formInitItems": [
	 * 	        {
	 * 	            "nodeId": "userTask1",
	 * 	            "parentDefKey": "qingjia",
	 * 	            "saveFieldsSetting": [
	 * 	                {
	 * 	                    "boDefCode": "code1",
	 * 	                    "fieldDesc": "描述",
	 * 	                    "setting": "return \"1\";"
	 * 	                }
	 * 	            ],
	 * 	            "showFieldsSetting": [
	 * 	                {
	 * 	                    "boDefCode": "code1",
	 * 	                    "fieldDesc": "描述",
	 * 	                    "setting": "return \"1\";"
	 * 	                }
	 * 	            ]
	 * 	        },
	 * 	        {
	 * 	            "nodeId": "userTask1",
	 * 	            "parentDefKey": "local",
	 * 	            "saveFieldsSetting": [
	 * 	                {
	 * 	                    "boDefCode": "code1",
	 * 	                    "fieldDesc": "描述",
	 * 	                    "setting": "return \"1\";"
	 * 	                }
	 * 	            ],
	 * 	            "showFieldsSetting": [
	 * 	                {
	 * 	                    "boDefCode": "code1",
	 * 	                    "fieldDesc": "描述",
	 * 	                    "setting": "return \"1\";"
	 * 	                }
	 * 	            ]
	 * 	        }
	 * 	    ],
	 * 	    bodef:{"boSaveMode":"db","boDefs":[{"required":false,"key":"bbb","name":"a"}]}
	 * 	}
	 * 
	 * </pre>
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             ResultMessage
	 */
	@RequestMapping("saveSetBos")
	public @ResponseBody ResultMessage saveSetBos(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		ResultMessage msg = new ResultMessage(ResultMessage.FAIL, "BO设置内容为空!", "内容为空!");
		String userId = ContextUtil.getCurrentUser().getUserId();

		String json = RequestUtil.getString(request, "json");

		String topDefKey = RequestUtil.getString(request, "topDefKey");

		String flowId = RequestUtil.getString(request, "flowId");
		
		Boolean isClearForm = RequestUtil.getBoolean(request, "isClearForm");
		
		BpmDefinition def= bpmDefinitionManager.get(flowId);

		JSONObject jsonObj = (JSONObject)JSONObject.parse(json);

		BpmFormInit formInit = new BpmFormInit();

		if (jsonObj.containsKey("formInitItems")) {
			formInit = JSONObject.parseObject(json, BpmFormInit.class);
			formInit.setParentDefKey(topDefKey);
		}
		// 保存表单初始化数据。
		bpmFormInitBpmDefXmlHandler.saveNodeXml(flowId, "", formInit);

		JSONObject bodefJson = jsonObj.getJSONObject("bodef");

		BpmBoDef bpmBoDef = new BpmBoDef();

		if (bodefJson != null) {
			bpmBoDef = JSONObject.toJavaObject(bodefJson, BpmBoDef.class);
			saveBpmProBoList(userId, flowId, def.getDefKey(),topDefKey, bpmBoDef,isClearForm);
		}

		boBpmDefXmlHandler.saveNodeXml(flowId, "", bpmBoDef);

		msg.setResult(ResultMessage.SUCCESS);
		msg.setMessage("保存成功！");
		msg.setCause("内容完整！");

		return msg;
	}

	private void saveBpmProBoList(String userId, String flowId, String flowKey,String parentFlowKey, BpmBoDef bpmBoDef, Boolean isClearForm) {

		// 清除之前的流程和BO的绑定数据
		boolean mark = true;
		List<BpmProBo> dbProBos = bpmProBoManager.getByProcessId(flowId);//数据库中的bodef和表单数据
		if (StringUtil.isNotEmpty(flowId)) {
			bpmProBoManager.removeByProcessId(flowId);
			mark = false;
		}
		if (mark && StringUtil.isNotEmpty(flowKey)) {
			bpmProBoManager.removeByProcessKey(flowKey);
		}
		
		// 封装数据到绑定表
		List<BpmProBo> bpmProBoList = new ArrayList<BpmProBo>();
		List<ProcBoDef> boDefs = bpmBoDef.getBoDefs();

		for (ProcBoDef procBoDef : boDefs) {
			BpmProBo bpmProBo = new BpmProBo();
			bpmProBo.setProcessId(flowId);
			bpmProBo.setProcessKey(flowKey);
			bpmProBo.setBoCode(procBoDef.getKey());
			bpmProBo.setBoName(procBoDef.getName());
			bpmProBo.setCreatorId(userId);
			bpmProBo.setCreateTime(new Date());
			bpmProBoList.add(bpmProBo);
		}
		bpmProBoManager.createByBpmProBoList(bpmProBoList);
		
		//数据库的关系表跟新的关系表比较，用list.equals只需要重载equals就行- -
		if(BeanUtils.isNotEmpty(boDefs)&&dbProBos.containsAll(bpmProBoList)){
			return;
		}
		//bo不相等了 需要做下面操作
		if(isClearForm){
			//1 删除表单的所有相关权限
			bpmFormRightManager.removeInst(flowKey);
			bpmFormRightManager.remove(flowKey, parentFlowKey);
			//2 删除所有表单配置
			BpmDefSetting bpmDefSetting = new BpmDefSetting();
			BpmDefSettingBpmDefXmlHandler bpmDefSettingBpmDefXmlHandler = AppUtil.getBean(BpmDefSettingBpmDefXmlHandler.class);
			bpmDefSettingBpmDefXmlHandler.saveNodeXml(flowId, null, bpmDefSetting);
		}
	}
	
	/**
	 * 根据从web设计器提交的数据构建流程定义对象。
	 * 
	 * @param request
	 * @return
	 */
	private DefaultBpmDefinition getWebDesignFromRequest(HttpServletRequest request)throws Exception{
		String typeId = RequestUtil.getString(request, "typeId"); // 流程分类
		String name = RequestUtil.getString(request, "name"); // 流程标题
		String defKey = RequestUtil.getString(request, "defKey"); // 流程key
		String descp = RequestUtil.getString(request, "desc"); // description
		String defJson = RequestUtil.getString(request, "defJson"); // defXml
		Boolean isDeploy = Boolean.parseBoolean(request.getParameter("deploy"));

		String reason = RequestUtil.getString(request, "reason");// reason
		String defId = RequestUtil.getString(request, "defId");

		DefaultBpmDefinition bpmDefinition = null;
		if (StringUtils.isNotEmpty(defId)) {
			if (isDeploy) {
				bpmDefinition = bpmDefinitionManager.getById(defId);
			} else {
				bpmDefinition = bpmDefinitionManager.getById(defId);
			}
		}
		if (bpmDefinition == null) {
			bpmDefinition = new DefaultBpmDefinition();
			if (StringUtils.isNotEmpty(defKey)) {
				bpmDefinition.setDefKey(defKey);
			}
		}
		// 设置属性值
		if (StringUtils.isNotEmpty(typeId)) {
			bpmDefinition.setTypeId(typeId);
		}
		if (StringUtils.isNotEmpty(name)) {
			bpmDefinition.setName(name);
		}
		if (StringUtils.isNotEmpty(descp)) {
			bpmDefinition.setDesc(descp);
		} else {
			bpmDefinition.setDesc(name);
		}
		if(StringUtils.isNotEmpty(defJson)){
			bpmDefinition.setDefJson(defJson);
		}
		bpmDefinition.setDesigner(DesignerType.WEB.name());
		bpmDefinition.setReason(reason);

		return bpmDefinition;
	}
	
	/**
	 * 根据从flex提交的数据构建流程定义对象。
	 * 
	 * @param request
	 * @return
	 */
	private DefaultBpmDefinition getFromRequest(HttpServletRequest request) throws Exception {
		String typeId = RequestUtil.getString(request, "bpmDefinition.typeId"); // 流程分类
		String subject = RequestUtil.getString(request, "bpmDefinition.subject"); // 流程标题
		String defKey = RequestUtil.getString(request, "bpmDefinition.defKey"); // 流程key
		String descp = RequestUtil.getString(request, "bpmDefinition.descp"); // description
		String defXml = RequestUtil.getString(request, "bpmDefinition.defXml"); // defXml
		String defJson = RequestUtil.getString(request, "bpmDefinition.defJson"); // defXml
		Boolean isDeploy = Boolean.parseBoolean(request.getParameter("deploy"));
		defXml = defXml.replace("''", "'");

		String reason = RequestUtil.getString(request, "bpmDefinition.reason");// reason
		String defId = RequestUtil.getString(request, "bpmDefinition.defId");

		DefaultBpmDefinition bpmDefinition = null;
		if (StringUtils.isNotEmpty(defId)) {
			if (isDeploy) {
				bpmDefinition = bpmDefinitionManager.getById(defId);
			} else {
				bpmDefinition = bpmDefinitionManager.getById(defId);
			}
		}
		if (bpmDefinition == null) {
			bpmDefinition = new DefaultBpmDefinition();
			if (StringUtils.isNotEmpty(defKey)) {
				bpmDefinition.setDefKey(defKey);
			}
		}
		// 设置属性值
		if (StringUtils.isNotEmpty(typeId)) {
			bpmDefinition.setTypeId(typeId);
		}
		if (StringUtils.isNotEmpty(subject)) {
			bpmDefinition.setName(subject);
		}
		if (StringUtils.isNotEmpty(descp)) {
			bpmDefinition.setDesc(descp);
		} else {
			bpmDefinition.setDesc(subject);
		}
		if (StringUtils.isNotEmpty(defXml)) {
			bpmDefinition.setDefXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + defXml);
			bpmDefinition.setDesigner(DesignerType.FLASH.name());
		}
		if(StringUtils.isNotEmpty(defJson)){
			bpmDefinition.setDefJson(defJson);
			bpmDefinition.setDesigner(DesignerType.WEB.name());
		}

		bpmDefinition.setReason(reason);

		return bpmDefinition;
	}

	/**
	 * 在流程在线设计中获取分类的所有流程列表。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getFlowListByTypeId")
	public void getFlowListByTypeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String typeId = RequestUtil.getString(request, "typeId");
		String word = RequestUtil.getString(request, "word");

		if (StringUtil.isNotEmpty(typeId)) {
			params.put("typeId", typeId);
		}

		if (StringUtil.isNotEmpty(word)) {
			word = "%" + word + "%";
			params.put(typeId, typeId);
		}

		List<DefaultBpmDefinition> list = bpmDefinitionManager.queryListByMap(params);

		StringBuffer msg = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Result>");
		for (DefaultBpmDefinition bpmDefinition : list) {
			msg.append("<item name=\"" + bpmDefinition.getName() + "\" key=\"" + bpmDefinition.getDefKey() + "\" type=\"" + bpmDefinition.getTypeId() + "\"></item>");
		}
		msg.append("</Result>");
		PrintWriter out = response.getWriter();
		out.println(msg.toString());
	}

	/**
	 * 流程在线设计，根据defId获取流程对应的详细信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("flexGet")
	public void flexGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		DefaultBpmDefinition bpmDefinition = null;
		if (StringUtil.isNotEmpty(defId)) {
			bpmDefinition = bpmDefinitionManager.getById(defId);
		} else {
			bpmDefinition = new DefaultBpmDefinition();
		}
		StringBuffer msg = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Result>");
		msg.append("<defId>" + bpmDefinition.getDefId() + "</defId>");
		msg.append("<defXml>" + bpmDefinition.getDefXml() + "</defXml>");

		if (bpmDefinition.getTypeId() != null) {
			SysType proType = sysTypeManager.get(bpmDefinition.getTypeId());
			msg.append("<typeName>" + proType.getName() + "</typeName>");
			msg.append("<typeId>" + proType.getId() + "</typeId>");
		}

		msg.append("<subject>" + bpmDefinition.getName() + "</subject>");
		msg.append("<defKey>" + bpmDefinition.getDefKey() + "</defKey>");
		msg.append("<descp>" + bpmDefinition.getDesc() + "</descp>");
		msg.append("<versionNo>" + bpmDefinition.getVersion() + "</versionNo>");
		msg.append("</Result>");

		PrintWriter out = response.getWriter();
		out.println(msg.toString());
	}

	/**
	 * 其他参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("defOtherParam")
	public ModelAndView otherParam(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");

		BpmDefinition definition = bpmDefinitionManager.get(defId);

		ModelAndView mv = this.getAutoView();

		mv.addObject("defId", defId);

		List<JmsHandler<JmsVo>> handlerList = MessageUtil.getHanlerList();
		
		List<ISkipCondition> skipConditionList=SkipConditionUtil.getSkipConditions();

		mv.addObject("handlerList", handlerList);
		mv.addObject("skipConditionList", skipConditionList);

		mv.addObject("def", definition);

		return mv;

	}

	/**
	 * 获取流程其他属性的参数。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             BpmDefExtProperties
	 */
	@RequestMapping("getOtherParam")
	@ResponseBody
	public BpmDefExtProperties getOtherParam(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");

		BpmDefinition bpmDefinition = bpmDefinitionManager.get(defId);

		BpmProcessDef<BpmProcessDefExt> procDef = bpmDefinitionAccessor.getBpmProcessDef(defId);

		BpmDefExtProperties prop = procDef.getProcessDefExt().getExtProperties();

		// 这样做不是最好的，应该在bpmnxml生成的时候给ext:description初始化。。但这太难跟了- -
		if (StringUtil.isEmpty(prop.getDescription())) {
			prop.setDescription(bpmDefinition.getDesc());
		}

		prop.setTestStatus(bpmDefinition.getTestStatus());
		prop.setStatus(bpmDefinition.getStatus());

		return prop;

	}

	/**
	 * 保存流程的其他属性。 注意:这里有几个操作没有使用事务。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 */
	@RequestMapping("saveProp")
	@ResponseBody
	public void saveProp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String json = FileUtil.inputStream2String(request.getInputStream());
			JSONObject jsonObject = JSONObject.parseObject(json);
			String defId = jsonObject.getString("defId");
			String description = jsonObject.getString("description");
			BpmDefExtProperties prop = (BpmDefExtProperties)JSONObject.toJavaObject(jsonObject, BpmDefExtProperties.class);

			String status = prop.getStatus();

			propertiesBpmDefXmlHandler.saveNodeXml(defId, "", prop);
			// 更新测试状态
			DefaultBpmDefinition bpmDefinition = bpmDefinitionManager.get(defId);

			// 更新状态
			String oldStatus = bpmDefinition.getStatus();

			bpmDefinition.setTestStatus(prop.getTestStatus());
			bpmDefinition.setStatus(status);
			bpmDefinition.setDesc(description);
			bpmDefinitionManager.update(bpmDefinition);

			bpmDefinitionManager.updBpmDefinitionStatus(bpmDefinition, oldStatus);
			
			bpmDefinitionAccessor.clean(defId);

			message = new ResultMessage(ResultMessage.SUCCESS, "保存流程参数成功!");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "保存流程参数失败!");
		}

		writeResultMessage(response.getWriter(), message);
	}


	/**
	 * 
	 * 展示流程节点人员设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("defNodeUsers")
	public ModelAndView nodeUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = request.getParameter("defId");
		String parentFlowKey = request.getParameter("parentFlowKey");
		DefaultBpmDefinition bpmDefinition = bpmDefinitionManager.get(defId);
		List<BpmNodeDef> nodeDefs = bpmDefinitionAccessor.getAllNodeDef(defId);
		return getAutoView().addObject("nodeList", nodeDefs).addObject("bpmDefinition", bpmDefinition).addObject("parentFlowKey", parentFlowKey);
	}

	/**
	 * 
	 * 展示流程节点BO设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("nodeBos")
	@ResponseBody
	public Object nodeBos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = request.getParameter("defId");
		String topDefKey = request.getParameter("topDefKey");
		
		
		DefaultBpmDefinition bpmDefinition = bpmDefinitionManager.get(defId);
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		
		BpmBoDef boDef =null;
		//在子流程中做配置，获取顶级流程的bo设定。
		if(StringUtil.isNotEmpty(topDefKey)){
			DefaultBpmDefinition bpmDef= bpmDefinitionManager.getMainByDefKey(topDefKey, false);
			BpmProcessDef<BpmProcessDefExt> topProcessExt = bpmDefinitionAccessor.getBpmProcessDef(bpmDef.getDefId());
			boDef = BpmDefUtil.getBpmBoDef(topProcessExt);
		}
		else{
			boDef = BpmDefUtil.getBpmBoDef(bpmProcessDefExt);
		}
		
		List<BpmNodeDef> nodeDefList = BpmDefUtil.getNodeDefs(bpmProcessDefExt);

		BpmFormInit formInit = BpmDefUtil.getBpmFormInit(bpmProcessDefExt,topDefKey);

		JSONObject jsonObj = (JSONObject) JSONObject.toJSON(formInit);

		JSONObject boDefJson = (JSONObject) JSONObject.toJSON(boDef);

		jsonObj.put("bodef", boDefJson);

		JSONArray jry = new JSONArray();
		for (BpmNodeDef def : nodeDefList) {
			JSONObject jo = new JSONObject();
			jo.put("nodeId", def.getNodeId());
			jo.put("name", def.getName());
			jry.add(jo);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bpmDefinition", bpmDefinition);
		map.put("json", jsonObj);
		map.put("nodeDefList", jry);
		return map;

	}

	/**
	 * flash设计器根据中文名获取拼音。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 */
	@RequestMapping("getFlowKey")
	public void getFlowKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String subject = request.getParameter("subject");
		String pinyin = PinyinUtil.getPinYinHeadCharFilter(subject);
		response.getWriter().print(pinyin);
	}

	@RequestMapping("exportXml")
	public void exportXml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] bpmDefIds = RequestUtil.getStringAryByStr(request, "bpmDefIds");
		if (BeanUtils.isEmpty(bpmDefIds)) return;

		List<String> defList = Arrays.asList(bpmDefIds);
		String zipName = "ht_flow_"+DateFormatUtil.format(new Date(), "yyyy_MMdd_HHmm");
		// 写XML
		Map<String, String> strXml = bpmDefTransform.exportDef(defList);
		
		HttpUtil.downLoadFile(request, response, strXml,zipName);
	}

	@SuppressWarnings("unchecked")
	public static void checkXmlFormat(String xml) throws Exception {
		String firstName = "bpmlist";
		String nextName = "bpmDef";
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();
		String msg = "导入文件格式不对";
		if (!root.getName().equals(firstName))
			throw new Exception(msg);
		List<Element> itemLists = root.elements();
		for (Element elm : itemLists) {
			if (!elm.getName().equals(nextName))
				throw new Exception(msg);
		}

	}

	/**
	 * 根据xmlStr获取BpmDefinition对象，是根据xml中的defKey
	 * 
	 * @param xmlStr
	 * @return BpmDefinition
	 * @exception
	 * @since 1.0.0
	 */
	public BpmDefinition getBpmDefByXml(String xmlStr) {
		String key = "";
		Pattern pattern = Pattern.compile("defKey=\"(.*?)\"");
		Matcher matcher = pattern.matcher(xmlStr);
		while (matcher.find()) {
			key = matcher.group(1);
		}
		BpmDefinition bpmDefinition = bpmDefinitionManager.getMainByDefKey(key);
		return bpmDefinition;
	}

	@RequestMapping("importSave")
	public void importSave(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		String unZipFilePath = "";
		ResultMessage message = null;
		try {
			//String rootRealPath = request.getSession().getServletContext().getRealPath(ROOT_PATH); // 操作的根目录
			String rootRealPath = this.getClass().getClassLoader().getResource(File.separator).getPath()+ROOT_PATH;// 操作的根目录
			String name = fileLoad.getOriginalFilename();
			String fileDir = StringUtil.substringBeforeLast(name, ".");
			
			ZipUtil.unZipFile(fileLoad, rootRealPath); // 解压文件
			unZipFilePath = rootRealPath + File.separator + fileDir; // 解压后文件的真正路径

			String flowXml = FileUtil.readFile( unZipFilePath + "/bpmdefs.flow.xml");
			if (StringUtils.isEmpty(flowXml)) throw new Exception("导入的未按指定的格式");

			checkXmlFormat(flowXml);

			boolean pass = true;// 是否通过了同名验证
			String newVer = RequestUtil.getString(request, "newVer");
			BpmDefinition oldDefinition = getBpmDefByXml(flowXml);
			if (StringUtil.isEmpty(newVer)) {// 是否需要同名验证
				if (oldDefinition != null) {
					pass = false;
				}
			}

			if (pass) {
				bpmDefTransform.importDef(unZipFilePath);
				message = new ResultMessage(ResultMessage.SUCCESS, "导入成功!");
			} else {
				message = new ResultMessage(3, "系统已存在流程：" + oldDefinition.getName() + ",是否继续为其新增版本");// 随便给个标记3
			}

		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "导入失败:" + e.getMessage());
		} finally {
			File boDir = new File(unZipFilePath);
			if (boDir.exists()) {
				FileUtil.deleteDir(boDir); // 删除解压后的目录
			}
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 流程定义历史版本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("versions")
	public @ResponseBody PageJson versions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String defKey = RequestUtil.getString(request, "defKey");
		QueryFilter queryFilter = getQueryFilter(request);
		DefaultBpmDefinition defaultBpmDefinition = null;
		if (StringUtil.isEmpty(defKey) && StringUtil.isNotEmpty(defId)) {
			defaultBpmDefinition = bpmDefinitionManager.get(defId);
			if (defaultBpmDefinition != null) {
				queryFilter.addFilter("def_key_", defaultBpmDefinition.getDefKey(), QueryOP.EQUAL);
			} else {
				queryFilter.addFilter("def_key_", "", QueryOP.EQUAL);
			}
		} else {
			queryFilter.addFilter("def_key_", defKey, QueryOP.EQUAL);
		}
		PageList<DefaultBpmDefinition> defList = (PageList<DefaultBpmDefinition>) bpmDefinitionManager.query(queryFilter);
		return new PageJson(defList);
	}

	/**
	 * 设置历史版本的流程定义为主版本
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("switchMainVersion")
	public void switchMainVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		ResultMessage message = null;
		try {
			bpmDefinitionService.switchMainVersion(defId);
			message = new ResultMessage(ResultMessage.SUCCESS, "设置成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("defDetail")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pre = RequestUtil.getPrePage(request);
		String defId = request.getParameter("defId");
		String status = request.getParameter("status");
		boolean isDialog = RequestUtil.getBoolean(request, "isDialog", false);

		return this.getAutoView().addObject("defId", defId).addObject("status", status).addObject("returnUrl", pre).addObject("isDialog", isDialog);
	}

	/**
	 * 设置外部子流程
	 * **/
	@RequestMapping("subFlowDetail")
	public ModelAndView subFlowDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String topDefKey=RequestUtil.getString(request, "topDefKey");
		
		String defId=RequestUtil.getString(request, "defId");
		String nodeId=RequestUtil.getString(request, "nodeId");
		
		BpmNodeDef node = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		if (node.getType() != NodeType.CALLACTIVITY)
			throw new RuntimeException("这不是一个外部子流程节点！");


		CallActivityNodeDef callActivityNode = (CallActivityNodeDef) node;
		String childFlowKey = callActivityNode.getFlowKey();
		BpmDefinition subProcessDef = bpmDefinitionManager.getMainByDefKey(childFlowKey);

		return new ModelAndView("redirect:/flow/def/defDetail?topDefKey=" + topDefKey + "&defId=" + subProcessDef.getDefId() + "&isDialog=1");
	}
	/**
	 * 得到全部节点自定义按钮
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("getNodeBtns")
	public @ResponseBody PageJson getNodeBtns(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		List<BpmNodeDef> nodeDefs = bpmDefinitionAccessor.getSignUserNode(defId);
		List list = new ArrayList();
		if (nodeDefs != null && !nodeDefs.isEmpty()) {
			for (BpmNodeDef bnd : nodeDefs) {
				Map map = new HashMap();
				map.put("nodeId", bnd.getNodeId());
				map.put("name", bnd.getName());
				map.put("type", bnd.getType());
				map.put("btns",JSON.toJSON(bnd.getButtons())); 
				list.add(map);
			}
		}
		return new PageJson(list);
	}

	/**
	 * 得到某个节点自定义按钮
	 * 
	 * action : 0:获取默认初始化的按钮,1:获取配置的按钮,2:获取默认不初始化的按钮
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getNodeSet")
	public @ResponseBody List<Button> getNodeSet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		//0:获取默认初始化的按钮,1:获取配置的按钮,2:获取默认不初始化的按钮
		int action = RequestUtil.getInt(request, "action",0);
		
		BpmNodeDef nodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<Button> nodeBtns = nodeDef.getButtons();
		
		List<Button> defaultBtns = nodeDef.getButtonsByType(true);
		
		if(action==0) {
			return defaultBtns;
		}
		else if(action==2){
			List<Button> notInitBtns = nodeDef.getButtonsByType(false);
			return notInitBtns;
		}
		
		// supportScript 要修改buttonXml 定义 而且仅仅在编辑页面游泳 so 如此处理吧。快点
		for (Button btn : nodeBtns) {
			btn.setSupportScript(true);
			for (Button b : defaultBtns) {
				if(btn.getAlias().equals(b.getAlias())){
					if(b.getSupportScript()==false) {
						btn.setSupportScript(false);
					} 
				}
			}
		}
		
		return nodeBtns;
	}

	/**
	 * 保存节点的按钮
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveNodeBtns")
	public void saveNodeBtn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = "";
		try {
			String defId = RequestUtil.getString(request, "defId");
			String nodeId = RequestUtil.getString(request, "nodeId");
			String btnListJson = RequestUtil.getString(request, "buttonList");
			List<Button> btns =JSONArray.parseArray(btnListJson, Button.class);
			
			buttonsBpmDefXmlHandler.saveNodeXml(defId, nodeId, btns);
			resultMsg = "按钮设置成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "按钮设置失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	
	
	/**
	 * 清除测试状态流程的测试数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("cleanData")
	public void cleanData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		try {
			bpmDefinitionService.cleanData(defId);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, "清除数据成功"));
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.FAIL, e.getMessage()));
		}
	}

	/**
	 * 发布
	 * 
	 */
	@RequestMapping("deploy")
	public void deploy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		try {
			BpmDefinition def = bpmDefinitionService.getBpmDefinitionByDefId(defId);
			bpmDefinitionService.deploy(def);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, "发布流程定义成功！"));
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.FAIL, e.getMessage()));
		}
	}

	/**
	 * 节点概要
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("nodeSummary")
	public ModelAndView nodeSummary(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		// String parentActDefId = RequestUtil.getString(request,
		// "parentActDefId", "");
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		List<BpmNodeDef> nodeDefList = bpmProcessDefExt.getBpmnNodeDefs();

		Map<String, Boolean> startScriptMap = new HashMap<String, Boolean>();
		Map<String, Boolean> endScriptMap = new HashMap<String, Boolean>();
		Map<String, Boolean> preScriptMap = new HashMap<String, Boolean>();
		Map<String, Boolean> afterScriptMap = new HashMap<String, Boolean>();
		Map<String, Boolean> assignScriptMap = new HashMap<String, Boolean>();

		Map<String, Boolean> nodeRulesMap = new HashMap<String, Boolean>();
		Map<String, Boolean> nodeButtonMap = new HashMap<String, Boolean>();
		Map<String, Boolean> taskReminderMap = new HashMap<String, Boolean>();

		Map<String, Boolean> nodeFormMap = new HashMap<String, Boolean>();
		boolean globalFormFlag = false;
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		Form globalForm = defExt.getGlobalForm();
		if (globalForm != null)
			globalFormFlag = true;

		Map<String, Boolean> nodeUserMap = new HashMap<String, Boolean>();

		for (BpmNodeDef bpmNodeDef : nodeDefList) {
			String nodeId = bpmNodeDef.getNodeId();
			// 用户设置
			this.getNodeUserMap(bpmNodeDef, nodeId, nodeUserMap);

			// 流程事件(脚本)
			this.getNodeScriptMap(bpmNodeDef, nodeId, startScriptMap, endScriptMap, preScriptMap, afterScriptMap, assignScriptMap);
			// 流程节点规则
			this.getNodeRulesMap(bpmNodeDef, nodeId, nodeRulesMap);

			// 操作按钮
			this.getNodeButtonMap(bpmNodeDef, nodeId, nodeButtonMap);

			// 催办信息
		//	this.getTaskReminderMap(bpmNodeDef, nodeId, defId, taskReminderMap);
		}

		this.orderNodeDefList(nodeDefList);
		return this.getAutoView().addObject("bpmDefinition", bpmProcessDefExt).addObject("defId", defId).addObject("nodeDefList", nodeDefList).addObject("startScriptMap", startScriptMap).addObject("endScriptMap", endScriptMap).addObject("preScriptMap", preScriptMap).addObject("afterScriptMap", afterScriptMap).addObject("assignScriptMap", assignScriptMap).addObject("nodeRulesMap", nodeRulesMap).addObject("nodeUserMap", nodeUserMap).addObject("nodeFormMap", nodeFormMap).addObject("nodeButtonMap", nodeButtonMap).addObject("taskReminderMap", taskReminderMap).addObject("globalFormFlag", globalFormFlag);

	}


	/**
	 * 操作按钮
	 * 
	 * @param bpmNodeDef
	 * @param nodeId
	 * @param nodeButtonMap
	 */
	private void getNodeButtonMap(BpmNodeDef bpmNodeDef, String nodeId, Map<String, Boolean> nodeButtonMap) {
		if (BeanUtils.isNotEmpty(bpmNodeDef.getButtons()))
			nodeButtonMap.put(nodeId, true);
		else
			nodeButtonMap.put(nodeId, false);
	}

	/**
	 * 流程节点规则
	 * 
	 * @param bpmNodeDef
	 * @param nodeId
	 * @param nodeRulesMap
	 */
	private void getNodeRulesMap(BpmNodeDef bpmNodeDef, String nodeId, Map<String, Boolean> nodeRulesMap) {
		if (! (bpmNodeDef instanceof UserTaskNodeDef) ) return ;
		
		UserTaskNodeDef userTaskNodeDef = (UserTaskNodeDef) bpmNodeDef;
		if (BeanUtils.isNotEmpty(userTaskNodeDef.getJumpRuleList()))
			nodeRulesMap.put(nodeId, true);
		else
			nodeRulesMap.put(nodeId, false);

	}

	/**
	 * 流程事件(脚本)
	 * 
	 * @param bpmNodeDef
	 * @param nodeId
	 * @param startScriptMap
	 * @param endScriptMap
	 * @param preScriptMap
	 * @param afterScriptMap
	 * @param assignScriptMap
	 */
	private void getNodeScriptMap(BpmNodeDef bpmNodeDef, String nodeId, Map<String, Boolean> startScriptMap, Map<String, Boolean> endScriptMap, Map<String, Boolean> preScriptMap, Map<String, Boolean> afterScriptMap, Map<String, Boolean> assignScriptMap) {
		Map<ScriptType, String> scriptMap = bpmNodeDef.getScripts();
		for (Map.Entry<ScriptType, String> entry : scriptMap.entrySet()) {
			if (StringUtil.isEmpty(entry.getValue())) continue;
			
			switch (entry.getKey()) {
				case START:
					startScriptMap.put(nodeId, true);
					break;
				case END:
					endScriptMap.put(nodeId, true);
					break;
				case CREATE:
					preScriptMap.put(nodeId, true);
					break;
				case COMPLETE:
					afterScriptMap.put(nodeId, true);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 节点排序
	 * 
	 * @param nodeDefList
	 */
	private void orderNodeDefList(List<BpmNodeDef> nodeDefList) {
		for (BpmNodeDef bpmNodeDef : nodeDefList) {
			if (bpmNodeDef.getType() == NodeType.START)
				bpmNodeDef.setOrder(bpmNodeDef.getOrder());
			else if (bpmNodeDef.getType() == NodeType.END)
				bpmNodeDef.setOrder(bpmNodeDef.getOrder() + 100);
			else
				bpmNodeDef.setOrder(bpmNodeDef.getOrder() + 1000);
		}
		// 节点排序
		Collections.sort(nodeDefList, new BpmNodeDefComparator());
	}

	/**
	 * 设置节点人员
	 * 
	 * @param bpmNodeDef
	 * @param nodeId
	 * @param nodeUserMap
	 */
	private void getNodeUserMap(BpmNodeDef bpmNodeDef, String nodeId, Map<String, Boolean> nodeUserMap) {
		// 节点类型是用户节点和会签才有人员设置
		if (bpmNodeDef.getType() == NodeType.USERTASK || bpmNodeDef.getType() == NodeType.SIGNTASK) {
			PluginParse userPluginContext = (PluginParse) bpmNodeDef.getPluginContext(UserAssignPluginContext.class);
			if (userPluginContext != null && userPluginContext.getJson() != null) {
				nodeUserMap.put(nodeId, true);
			}

		}
	}

	
	/**
	 * 初始化所有按钮
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("initNodeBtn")
	public void initNodeBtn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = "";
		try {
			String defId = RequestUtil.getString(request, "defId");
			//如果nodeId为空则表示初始化全部按钮。
			String nodeId = RequestUtil.getString(request, "nodeId");
		
			List<BpmNodeDef> nodeDefs = bpmDefinitionAccessor.getSignUserNode(defId);
		
			if (BeanUtils.isNotEmpty(nodeDefs) ) {
				//初始化全部按钮
				if (StringUtil.isEmpty(nodeId)) {
					for (BpmNodeDef bnd : nodeDefs) {
						String key=bnd.getType().getKey();
						if (key.equals("signTask") || key.equals("userTask") || key.equals("start")) {
							buttonsBpmDefXmlHandler.saveNodeXml(defId, bnd.getNodeId(), bnd.getButtonsByType(true));
						}
					}
				}
				// 初始化某个节点按钮。
				else {
					for (BpmNodeDef bnd : nodeDefs) {
						if (nodeId.equals(bnd.getNodeId())) {
							buttonsBpmDefXmlHandler.saveNodeXml(defId, nodeId, bnd.getButtonsByType(true));
						}
					}
				}
			}
			resultMsg =  "初始化成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg =  "初始化失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	@RequestMapping("defSetCategory")
	public void setCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		String typeId = RequestUtil.getString(request, "typeId");
		String defIds = RequestUtil.getString(request, "defIds");
		String[] aryIdKey = defIds.split(",");

		if (StringUtil.isEmpty(typeId)) {
			writeResultMessage(writer, new ResultMessage(ResultMessage.FAIL, "请选择需要重置的分类!"));
			return;
		}

		if (StringUtil.isEmpty(defIds)) {
			writeResultMessage(writer, new ResultMessage(ResultMessage.FAIL, "请选择需要重置的流程"));
			return;
		}

		List<String> list = Arrays.asList(aryIdKey);

		try {
			bpmDefinitionManager.updDefType(typeId, list);
			writeResultMessage(writer, new ResultMessage(ResultMessage.SUCCESS, "重置分类成功!"));
		} catch (Exception ex) {
			writeResultMessage(writer, new ResultMessage(ResultMessage.FAIL, ex.getMessage()));
		}
	}
	
	@RequestMapping("copyDef")
	public @ResponseBody ResultMessage copyDef(@RequestBody Map<String,String> map) throws IOException{
		ResultMessage msg = new ResultMessage();
		String defId = map.get("defId");
		String name =  map.get("name");
		String defKey = map.get("defKey");
		
		BpmDefinition bpmDefinition = bpmDefinitionManager.getMainByDefKey(defKey, false);
		if(bpmDefinition!=null){
			msg.setMessage("流程key已经存在");
			return msg;
		}
		try{
			bpmDefinitionManager.copyDef(defId,name,defKey);
		}catch(Exception ex){
			msg.setMessage(ex.getMessage());
			return msg;
		}
		msg.setResult(ResultMessage.SUCCESS);
		msg.setMessage("复制流程成功");
		return msg;
			
	}

}
