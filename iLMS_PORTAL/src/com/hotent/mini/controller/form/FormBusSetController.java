package com.hotent.mini.controller.form;


import java.util.List;

import javax.annotation.Resource;
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
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.form.api.service.FormService;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.manager.BpmFormDefManager;
import com.hotent.form.persistence.manager.FormBusSetManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.FormBusSet;


/**
 * 
 * <pre> 
 * 描述：表单业务设置 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-08-23 13:55:59
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/form/formBusSet")
public class FormBusSetController extends GenericController{
	@Resource
	FormBusSetManager formBusSetManager;
	@Resource
	FormService formService;
	@Resource
	BpmFormDefDao bpmFormDefDao;
	@Resource
	BODefManager bODefManager;
	@Resource
	BpmFormDefManager bpmFormdefManager;
	@Resource
	BODefManager boDefManager;

	
	
	/**
	 * 表单业务设置明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	@ResponseBody
	public  JSONObject getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String formKey=RequestUtil.getString(request, "formKey");
		if(StringUtil.isEmpty(formKey)){
			return null;
		}
		FormBusSet formBusSet=formBusSetManager.getByFormKey(formKey);
		
		JSONObject object = new JSONObject();
		object.put("formBusSet", formBusSet);
		
		//获取BO
		BpmForm form = (BpmForm) formService.getByFormKey(formKey);
		List<String> boIds = bpmFormDefDao.getBODefIdByFormId(form.getDefId());
		if(boIds.size()>= 1){
			JSONObject boJson =	bODefManager.getBOJson(boIds.get(0));
			object.put("boJson", boJson);
		}
		return object;
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
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody FormBusSet formBusSet) throws Exception{
		String resultMsg=null;
		String id=formBusSet.getId();
		boolean rtn=formBusSetManager.isExist(formBusSet);
		if(rtn){
			writeResultMessage(response.getWriter(),"业务设置已存在!",ResultMessage.FAIL);
			return;
		}
		
		try {
			if(StringUtil.isEmpty(id)){
				formBusSet.setId(UniqueIdUtil.getSuid());
				formBusSetManager.create(formBusSet);
				resultMsg="添加表单业务设置成功";
			}else{
				formBusSetManager.update(formBusSet);
				resultMsg="更新表单业务设置成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对表单业务设置操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
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
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			formBusSetManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除表单业务设置成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除表单业务设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	
	
	@RequestMapping("createSqlList")
	@ResponseBody
	public ModelAndView treeList(HttpServletRequest request,HttpServletResponse response,String formKey) throws Exception{
		BpmForm form = (BpmForm) formService.getByFormKey(formKey);
		
		
		BpmFormDef formDef= bpmFormdefManager.get(form.getDefId());
		List<String> boCode = bpmFormdefManager.getBOCodeByFormId(formDef.getId());
		BODef boDef = boDefManager.getByDefName(boCode.get(0));
		BOEnt boEnt = (BOEnt)boDef.getBoEnt();
		
		JSONObject json = new JSONObject();
		json.put("name", form.getName()+"列表");
		json.put("alias",form.getFormKey()+"List");
		json.put("sql" , " select * from "+ boEnt.getTableName());
		json.put("dsName", boEnt.getDsName().equals("LOCAL")?"dataSource_Default":boEnt.getDsName());
		
		JSONArray array = new JSONArray();
		JSONObject addBtn = new JSONObject();
		addBtn.put("name", "新增");
		addBtn.put("inRow", "0");
		addBtn.put("triggerType", "href");
		addBtn.put("urlPath", "/form/formBus/"+formKey+"/edit");
		array.add(addBtn);
		
		JSONObject editBtn = new JSONObject();
		editBtn.put("name", "编辑");
		editBtn.put("inRow", "1");
		editBtn.put("triggerType", "href");
		editBtn.put("urlPath", "/form/formBus/"+formKey+"/edit?id={id_}");
		array.add(editBtn);
		
		JSONObject getBtn = new JSONObject();
		getBtn.put("name", "明细");
		getBtn.put("inRow", "1");
		getBtn.put("triggerType", "href");
		getBtn.put("urlPath", "/form/formBus/"+formKey+"/get?id={id_}");
		array.add(getBtn);
		
		JSONObject export = new JSONObject();
		export.put("name", "导出");
		export.put("inRow", "0");
		export.put("triggerType", "onclick");
		export.put("urlPath", "exports()");
		array.add(export);
		
		json.put("buttonDef", array);
		ModelAndView mv = new ModelAndView("/system/query/querySqldefEdit");
		mv.addObject("initModel", json);
		return mv;
	}

}
