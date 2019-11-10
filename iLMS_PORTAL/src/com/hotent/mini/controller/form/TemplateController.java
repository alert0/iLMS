package com.hotent.mini.controller.form;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.form.api.model.FormType;
import com.hotent.form.persistence.manager.BpmFormDefManager;
import com.hotent.form.persistence.manager.BpmFormTemplateManager;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.BpmFormTemplate;

/**
 * 
 * <pre> 
 * 描述：表单模版管理
 * 构建组：x5-bpmx-platform
 * 作者:xianggang
 * 邮箱:xianggang@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/form/template/")
public class TemplateController extends GenericController{
	@Resource
	BpmFormTemplateManager bpmFormTemplateManager;
	@Resource
	BpmFormDefManager bpmFormDefManager;
	
	/**
	 * 表单模版列表(分页条件查询)数据
	 * TODO方法名称描述
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<BpmFormTemplate> bpmFormTemplateList=(PageList<BpmFormTemplate>)bpmFormTemplateManager.query(queryFilter);
		return new PageJson(bpmFormTemplateList);
	}
	
	/**
	 * 编辑表单模版信息页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("templateEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String templateId=RequestUtil.getString(request, "templateId");
		BpmFormTemplate bpmFormTemplate=null;
		if(StringUtil.isNotEmpty(templateId)){
			bpmFormTemplate=bpmFormTemplateManager.get(templateId);
		}
		List<BpmFormTemplate> macroTemplates = bpmFormTemplateManager.getAllMacroTemplate();
		return getAutoView().addObject("bpmFormTemplate",bpmFormTemplate).addObject("returnUrl", preUrl)
				.addObject("macroTemplates", macroTemplates);
	}
	
	/**
	 * 表单模版明细页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("templateGet")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String templateId=RequestUtil.getString(request, "templateId");
		BpmFormTemplate bpmFormTemplate=null;
		if(StringUtil.isNotEmpty(templateId)){
			bpmFormTemplate=bpmFormTemplateManager.get(templateId);
		}
		return getAutoView().addObject("bpmFormTemplate", bpmFormTemplate).addObject("returnUrl", preUrl);
	}
	
	@RequestMapping("checkAliasIsExist")
	public @ResponseBody boolean checkAliasIsExist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String alias=RequestUtil.getString(request, "alias");
		if(StringUtil.isEmpty(alias)) return false;
		BpmFormTemplate bpmFormTemplate=bpmFormTemplateManager.getByTemplateAlias(alias);
		if(BeanUtils.isEmpty(bpmFormTemplate)) return false;
		return true;
	}
	
	/**
	 * 保存表单模版信息
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @param bpmFormTemplate
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,BpmFormTemplate bpmFormTemplate) throws Exception{
		String resultMsg=null;
		String templateId=bpmFormTemplate.getTemplateId();
		try {
			if(StringUtil.isEmpty(templateId)){
				bpmFormTemplate.setId(UniqueIdUtil.getSuid());
				bpmFormTemplate.setCanedit(1);
				bpmFormTemplateManager.create(bpmFormTemplate);
				resultMsg="添加表单模版成功";
			}else{
				bpmFormTemplate.setCanedit(1);
				bpmFormTemplateManager.update(bpmFormTemplate);
				resultMsg="更新表单模版成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对表单模版操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除表单模版记录
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "templateId");
			bpmFormTemplateManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除表单模版成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除表单模版失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	//此方法找不到对应的jsp路径啊
	@RequestMapping("templateSelector")
	public ModelAndView selector(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=getQueryFilter(request);
		List<BpmFormTemplate> list=bpmFormTemplateManager.query(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("bpmFormTemplateList",list);
		return mv;
	}
	/**
	 * 取得初始化模板信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("init")
	public void init(HttpServletRequest request,HttpServletResponse response)throws Exception
	{	
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			bpmFormTemplateManager.initAllTemplate();
			message=new ResultMessage(ResultMessage.SUCCESS, "初始化表单模板成功!");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "初始化表单模板失败:");
		}
		writeResultMessage(response.getWriter(), message);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 将用户自定义模板备份
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("backUp")
	public void backUp(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		String id=RequestUtil.getString(request, "templateId");
		String preUrl=RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			bpmFormTemplateManager.backUpTemplate(id);
			message=new ResultMessage(ResultMessage.SUCCESS, "模板备份成功!");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "模板备份失败:");
		}
		writeResultMessage(response.getWriter(), message);
		response.sendRedirect(preUrl);
	}
	
	/**
	 *  复制模板信息
	 * @param request
	 * @param response
	 * @param template
	 * @throws Exception
	 */
	@RequestMapping("copyTemplate")
	public void copyTemplate(HttpServletRequest request,HttpServletResponse response,BpmFormTemplate template) throws Exception
	{   
		String id=RequestUtil.getString(request, "templateId");
		BpmFormTemplate bpmFormTemplate=bpmFormTemplateManager.get(id);
		String name=RequestUtil.getString(request, "newTemplateName");
		String newAlias=RequestUtil.getString(request, "newAlias");
		boolean isExist=bpmFormTemplateManager.isExistAlias(newAlias);
//		SysAuditThreadLocalHolder.putParamerter("isExist", isExist);
		if(isExist){
		   writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.FAIL,"该别名已被使用"));
		}else{
			String newId=UniqueIdUtil.getSuid();
			template.setTemplateId(newId);
			template.setTemplateName(name);
			template.setAlias(newAlias);
			template.setCanedit(1);
			template.setHtml(bpmFormTemplate.getHtml());
			template.setMacrotemplateAlias(bpmFormTemplate.getMacrotemplateAlias());
			template.setTemplateDesc(bpmFormTemplate.getTemplateDesc());
			template.setTemplateType(bpmFormTemplate.getTemplateType());
			bpmFormTemplateManager.create(template);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, "复制模板成功"));
		}
	}
	
	/**
	 * 选择模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectTemplate")
	public ModelAndView selectTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		int isSimple = RequestUtil.getInt(request, "isSimple", 0);
		String templatesId = RequestUtil.getString(request, "templatesId");
		String formType = RequestUtil.getString(request, "formType",FormType.PC.value());

		ModelAndView mv = this.getAutoView();
		BpmFormDef bpmFormDef=bpmFormDefManager.get(defId);
		
		JSONArray fieldList =bpmFormDef.getFieldList();
		
		List<BpmFormTemplate> mainTableTemplates = bpmFormTemplateManager.getAllMainTableTemplate(formType.equals(FormType.PC.value()));
		List<BpmFormTemplate> subTableTemplates = bpmFormTemplateManager.getAllSubTableTemplate(formType.equals(FormType.PC.value()));
		
		
		return mv.addObject("mainTableTemplates", mainTableTemplates)
			.addObject("subTableTemplates", subTableTemplates)
			.addObject("tableList", fieldList) 
			.addObject("bpmForm", bpmFormDef)
			.addObject("isSimple", isSimple)
			.addObject("templatesId", templatesId).addObject("formType", formType);
	}
}
