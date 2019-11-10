package com.hotent.mini.controller.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.def.BpmDefXmlHandler;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmSubTableRight;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.core.engine.def.impl.handler.SubRightBpmDefXmlHandler;
import com.hotent.form.persistence.manager.BpmFormDefManager;
import com.hotent.form.persistence.manager.BpmFormRightManager;
import com.hotent.sys.api.permission.PermissionUtil;

/**
 * 
 * <pre>
 * 描述：表单权限管理
 * 构建组：x5-bpmx-platform
 * 作者:xianggang
 * 邮箱:xianggang@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/form/rights/")
public class FormRightsController extends GenericController {
	@Resource
	private BpmFormRightManager bpmFormRightManager;
	@Resource
	private BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	private BpmFormDefManager bpmFormDefManager;

	/**
	 * 获取表单权限。
	 * 
	 * @return
	 */
	@RequestMapping("getPermission")
	@ResponseBody
	public JSONObject getPermission(HttpServletRequest request, HttpServletResponse response) {
		String flowKey = RequestUtil.getString(request, "flowKey");
		String formKey = RequestUtil.getString(request, "formKey");
		String nodeId = RequestUtil.getString(request, "nodeId");
		int type = RequestUtil.getInt(request, "type", 1);
		String parentflowKey = RequestUtil.getString(request, "parentflowKey", "");
		
		if(type==1){
			bpmFormRightManager.remove(formKey, flowKey, nodeId, parentflowKey);
		}
		
		JSONObject json = bpmFormRightManager.getPermissionSetting(formKey, flowKey, parentflowKey, nodeId, type);
		List<Map<String,String>> tableSn =  bpmFormRightManager.getTableOrderBySn(formKey);
		
		JSONObject jo = new JSONObject();
		jo.put("json", json);
		jo.put("permissionList", PermissionUtil.getPermissionList("formPermissionCalcList"));
		jo.put("tableSn", tableSn);
		return jo;
	}
	
	
	/**
	 * 保存表单权限配置。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public ResultMessage save(HttpServletRequest request, HttpServletResponse response) {

		String flowKey = RequestUtil.getString(request, "flowKey");
		String formKey = RequestUtil.getString(request, "formKey");
		String nodeId = RequestUtil.getString(request, "nodeId");
		int type = RequestUtil.getInt(request, "type", 1);
		String parentflowKey = RequestUtil.getString(request, "parentflowKey", "");
		String permission = RequestUtil.getString(request, "permission", "");
		ResultMessage message = new ResultMessage(ResultMessage.SUCCESS, "保存成功");
		try {
			//权限表中存入表单元数据key。
			bpmFormRightManager.save(formKey, flowKey, parentflowKey, nodeId, permission, type);
		} catch (Exception ex) {
			message.setResult(ResultMessage.ERROR);
			message.setMessage(ex.getMessage());
		}
		return message;
	}

	/**
	 * 获取默认表单权限。
	 * 
	 * @return
	 */
	@RequestMapping("getDefaultByFormKey")
	@ResponseBody
	public JSONObject getDefaultByFormKey(HttpServletRequest request, HttpServletResponse response) {
		String formKey = RequestUtil.getString(request, "formKey");
		// 1.为流程权限，2，为实例权限。
		String type = RequestUtil.getString(request, "type", "1");
		boolean isInstance = !"1".equals(type);
		
		String formMetaKey=bpmFormDefManager.getMetaKeyByFormKey(formKey);
		
		JSONObject json = bpmFormRightManager.getDefaultByFormDefKey(formMetaKey, isInstance);
		
		JSONObject jo = new JSONObject();
		jo.put("json", json);
		jo.put("tableSn", bpmFormRightManager.getTableOrderBySn(formKey));
		return jo;
	}

	@RequestMapping("saveSub")
	@ResponseBody
	public void saveSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String nodeId = RequestUtil.getString(request, "nodeId");
			String defId = RequestUtil.getString(request, "defId");
			String parentDefKey = RequestUtil.getString(request, "parentDefKey", BpmConstants.LOCAL);

			Map<String, Object> param = new HashMap<String, Object>();
			List<BpmSubTableRight> rights = new ArrayList<BpmSubTableRight>();
			String json = FileUtil.inputStream2String(request.getInputStream());
			JSONObject jobj = JSONObject.parseObject(json);
			for (String key : jobj.keySet()) {
				BpmSubTableRight right = JSONObject.toJavaObject(jobj.getJSONObject(key), BpmSubTableRight.class);
				rights.add(right);
			}
			param.put("list", rights);
			param.put("parentDefKey", parentDefKey);
			BpmDefXmlHandler handler = AppUtil.getBean(SubRightBpmDefXmlHandler.class);
			handler.saveNodeXml(defId, nodeId, param);
			writeResultMessage(response.getWriter(), "保存子表权限成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "保存子表权限失败", e.getMessage(), ResultMessage.FAIL);
		}
	}

	@RequestMapping("initSub")
	@ResponseBody
	public Object initSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String parentDefKey = RequestUtil.getString(request, "parentDefKey",BpmConstants.LOCAL);
		
		JSONObject json = new JSONObject();
		BpmNodeDef nodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		UserTaskNodeDef utnd = (UserTaskNodeDef) nodeDef;
		for (BpmSubTableRight bsr : utnd.getBpmSubTableRightByParentDefKey(parentDefKey)) {
			json.put(bsr.getTableName(),JSONObject.toJSON(bsr));
		}
		return json;
	}
	
	@RequestMapping("remove")
	@ResponseBody
	public Object remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String json = FileUtil.inputStream2String(request.getInputStream());
			JSONObject jsonObject = JSONObject.parseObject(json);
			String flowKey = jsonObject.getString("flowKey");
			String nodeId = jsonObject.getString("nodeId");
			String parentFlowKey =  jsonObject.getString("parentFlowKey");
			bpmFormRightManager.remove(flowKey, nodeId, parentFlowKey);
			message = new ResultMessage(ResultMessage.SUCCESS, "表单设置已清除！");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "表单设置清除失败！");
		}
		return message;
	}

}
