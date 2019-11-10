package com.hotent.mini.controller.form;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.api.instance.BoUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.form.api.service.FormService;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.manager.BpmFormDefManager;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.manager.BpmFormRightManager;
import com.hotent.form.persistence.manager.FormBusManager;
import com.hotent.form.persistence.manager.FormBusSetManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.FormBusSet;


/**
 * 
 * <pre> 
 * 描述：表单业务保存 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-08-23 13:55:59
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/form/formBus")
public class FormBusController extends GenericController{
	@Resource
	FormBusSetManager formBusSetManager;
	@Resource
	BpmFormManager bpmFormManager;
	@Resource
	BODefManager bODefManager;
	@Resource
	BpmFormDefManager bpmFormdefManager;
	@Resource
	BpmFormRightManager bpmFormRightManager;
	@Resource
	FormBusManager formBusManager;
	
	/**
	 * 表单业务设置明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("{formKey}/getList")
	@ResponseBody
	public JSONArray getList(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "formKey") String formKey) throws Exception{
		Map<String,Object> param = RequestUtil.getParameterValueMap(request, false, true);
		
		return formBusManager.getList(formKey,param);
	}
	
	/**
	 * 表单业务设置明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("{formKey}/getData")
	@ResponseBody
	public JSONObject getJson(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "formKey") String formKey) throws Exception{
		String id = RequestUtil.getString(request, "id");
		Boolean readonly = RequestUtil.getBoolean(request, "readonly",false);
		
		FormBusSet formBusSet = formBusSetManager.getByFormKey(formKey);
		
		BpmForm form = bpmFormManager.getMainByFormKey(formKey);
		BpmFormDef formDef= bpmFormdefManager.get(form.getDefId());
		List<String> boCode = bpmFormdefManager.getBOCodeByFormId(formDef.getId());
		
		JSONObject permissionConf = bpmFormRightManager.getByFormKey(formKey, readonly);
		JSONObject permission = bpmFormRightManager.calcFormPermission(permissionConf);

		BoData boData =  formBusManager.getBoData(boCode.get(0),id);
		
		JSONObject json = new JSONObject();
		json.put("data", BoUtil.toJSONObject(boData,true));
		json.put("boCode", boCode.get(0));
		json.put("permission", permission);
		json.put("formHtml", form.getFormHtml());
		json.put("formBusSet", formBusSet);//  提交前后置脚本
		return json;
	}
	/**
	 * 编辑页面
	 * @param request
	 * @param response
	 * @param formKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("{formKey}/edit")
	@ResponseBody
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response, @PathVariable(value = "formKey") String formKey) throws Exception{
		String id = RequestUtil.getString(request, "id");
		String parentData = RequestUtil.getString(request, "parentId_");
		
		ModelAndView mv = new ModelAndView("/form/form/formBusEdit");
		mv.addObject("id", id);
		mv.addObject("formKey", formKey);
		
		// 树形编辑情况下。添加父ID的值
		if(StringUtil.isNotEmpty(parentData)){
			String [] parentSet = parentData.split("\\$");
			if(parentSet.length!=2) return mv;
			mv.addObject("parentKey",parentSet[0]);
			mv.addObject("parentVal",parentSet[1]);
		}
		
		return mv;
	}
	/**
	 * 详细页面
	 * @param request
	 * @param response
	 * @param formKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("{formKey}/get")
	@ResponseBody
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "formKey") String formKey) throws Exception{
		String id = RequestUtil.getString(request, "id");
		
		ModelAndView mv = new ModelAndView("/form/form/formBusEdit");
		mv.addObject("id", id);
		mv.addObject("formKey", formKey);
		mv.addObject("readonly", true);
		return mv;
	}
	
	@RequestMapping("{formKey}/treeList")
	@ResponseBody
	public ModelAndView treeList(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "formKey") String formKey) throws Exception{
		FormBusSet formBusSet = formBusSetManager.getByFormKey(formKey);
		BpmForm form = bpmFormManager.getMainByFormKey(formKey);
		
		if(formBusSet.getIsTreeList()!=1) throw new RuntimeException("改表单不支持树形列表！请修改表单的业务数据保存的设置！");
		
		ModelAndView mv = new ModelAndView("/form/form/formBusTreeList");
		mv.addObject("formName", form.getName());
		mv.addObject("formKey", formKey);
		mv.addObject("treeConf", formBusSet.getTreeConf());
		return mv;
	}
	
	/**
	 * 保存表单业务设置信息
	 * @param request
	 * @param response
	 * @param formBusSet
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("{formKey}/save")
	@ResponseBody
	public void save(HttpServletRequest request,HttpServletResponse response, @PathVariable(value = "formKey") String formKey) throws Exception{
		String json =RequestUtil.getString(request, "json");
		
		try {
			formBusManager.saveData(formKey,json);
			
			writeResultMessage(response.getWriter(),ThreadMsgUtil.getMessage(),ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(),"保存失败",e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 批量删除表单业务设置记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("{formKey}/remove")
	public void remove(HttpServletRequest request,HttpServletResponse response, @PathVariable(value = "formKey") String formKey) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			formBusManager.removeByIds(aryIds,formKey);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除表单业务设置成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除表单业务设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

}
