package com.hotent.mini.controller.form;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.HttpUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.ZipUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProBoManager;
import com.hotent.bpmx.persistence.model.BpmProBo;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.form.api.model.Form;
import com.hotent.form.api.model.FormType;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.manager.BpmFormHistoryManager;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.manager.BpmFormRightManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.BpmFormHistory;
import com.hotent.form.util.FormUtil;
import com.hotent.mini.web.util.JsoupUtil;

/**
 * 表单管理
 * <pre> 
 * 描述：流程任务表单管理
 * 构建组：x5-bpmx-platform
 * 作者:何一帆
 * 邮箱:heyf@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/form/form/")
public class FormController extends GenericController{
	@Resource
	BpmFormManager bpmFormManager;

	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmFormHistoryManager bpmFormHistoryManager;
	
	@Resource
	BpmProBoManager bpmProBoManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	
	@Resource
	BpmFormDefDao bpmFormDefDao;
	@Resource
	BODefManager bODefManager;
	@Resource
	BpmFormRightManager bpmFormRightManager;
	@Resource
	BoDataService boDataService;
	/**
	 * 流程任务表单列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String formType = RequestUtil.getString(request, "formType",FormType.PC.value());
		QueryFilter queryFilter=getQueryFilter(request);
		queryFilter.addFilter("form_type_", formType, QueryOP.EQUAL);
		queryFilter.addFilter("is_main_", 'Y', QueryOP.EQUAL);
		List<BpmForm> formList = bpmFormManager.query(queryFilter);
		
		return new PageJson(formList);
	}
	
	@RequestMapping("mobile")
	public ModelAndView mobileList(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		ModelAndView mv = new ModelAndView("/form/form/formList");
		mv.addObject("formType", FormType.MOBILE.value());
		return  mv;
	}
	/**
	 * 生成表单HTML
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("formEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String defId=RequestUtil.getString(request, "defId");
		String formType = RequestUtil.getString(request, "formType",FormType.PC.value());
		
		String formId = RequestUtil.getString(request, "id");
		
		BpmForm form ;
		if(StringUtil.isNotEmpty(formId)){
			form = bpmFormManager.get(formId);
		}else{
			BpmFormDef formDef  = bpmFormDefDao.get(defId);
			form = new BpmForm();
			form.setDefId(defId);
			form.setName(formDef.getName());
			form.setFormType(formType);
			form.setTypeName(formDef.getType());
			form.setTypeId(formDef.getTypeId());
			form.setIsMain('Y');
			form.setVersion(1);
			form.setStatus(BpmForm.STATUS_DRAFT);
			
			String tableNames=  RequestUtil.getString(request, "tableNames");  
			String templateAlias=  RequestUtil.getString(request, "templateAlias");  
			String html = bpmFormManager.getHtml(defId, tableNames, templateAlias);
			html = JsoupUtil.prettyHtml(html);
			form.setFormHtml(html);
		}
		
		if(StringUtil.isNotEmpty(formType) && formType.equals(FormType.MOBILE.value())){
			StringBuffer outHtml = new StringBuffer();
			outHtml.append("<div style=\"height: 100%;overflow: auto;\">");
			outHtml.append(form.getFormHtml());
			outHtml.append("</div>");
			form.setFormHtml(outHtml.toString());
		}
		
		return getAutoView().addObject("returnUrl", preUrl)
							.addObject("form",form);
	}
	
	/**
	 * 保存表单html内容
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,BpmForm form) throws Exception{
		try {
			if(StringUtil.isEmpty(form.getId())){
				List<BpmForm> bpmForm = bpmFormManager.getByFormKey(form.getFormKey());
				if(BeanUtils.isNotEmpty(bpmForm)) throw new RuntimeException("KEY【"+form.getFormKey()+"】对应的表单已存在");
				
				form.setId(UniqueIdUtil.getSuid());
				bpmFormManager.create(form);
			}else{
				bpmFormManager.update(form);
			}
			
			BpmFormHistory bpmFormDefHi = new BpmFormHistory(form); // 保持表单的操作记录
			bpmFormHistoryManager.create(bpmFormDefHi);
			writeResultMessage(response.getWriter(),"保存成功",ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),"保存失败："+e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 查看版本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("versions")
	public ModelAndView versions(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView result = getAutoView();
		String formKey = request.getParameter("formKey");
		String formType = request.getParameter("formType");
		String preUrl= RequestUtil.getPrePage(request);
		result.addObject("formKey", formKey).addObject("returnUrl", preUrl).addObject("formType", formType);
		return result;
	}
	
	@RequestMapping("listVersions")
	public  @ResponseBody List<BpmForm>  listVersions(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String formKey = request.getParameter("formKey");
		String formType = RequestUtil.getString(request, "formType",FormType.PC.value());
		
		QueryFilter filter = getQueryFilter(request);
		filter.addFilter("form_key_", formKey, QueryOP.EQUAL);
		filter.addFilter("form_type_", formType, QueryOP.EQUAL);
		
		List<BpmForm> listForm = bpmFormManager.query(filter);
		return listForm;
	}
	
	/**
	 * 表单信息明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("preview")
	public ModelAndView preview(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		ModelAndView modelAndView = getAutoView();
			String id = RequestUtil.getString(request, "id");
			//表单key 对应bpm_form
			String formKey = RequestUtil.getString(request, "formKey");
			//表单定义ID 为BPM_FORM_DEF的主键ID
			String defId = RequestUtil.getString(request, "defId");
			String formType = RequestUtil.getString(request, "formType",FormType.PC.value());
			
			if(StringUtil.isEmpty(id) && StringUtil.isNotEmpty(formKey)){
				BpmForm mainByFormKey = bpmFormManager.getMainByFormKey(formKey);
				id = mainByFormKey.getFormId();
			}
			
			BpmForm bpmForm=new BpmForm();
			String formHtml = RequestUtil.getString(request, "formHtml");
			if(StringUtil.isNotEmpty(id)){
				bpmForm = bpmFormManager.get(id);
				formType =bpmForm.getFormType(); 
			}
			if(StringUtil.isNotEmpty(bpmForm.getDefId())){
				defId = bpmForm.getDefId();
			}
			BpmFormDef formDef= bpmFormDefDao.get(defId);
			
			JSONObject permissionConf = null;
			if(StringUtil.isNotEmpty(bpmForm.getFormKey())){
				permissionConf = bpmFormRightManager.getByFormKey(bpmForm.getFormKey(), false);
			}else{
				permissionConf = bpmFormRightManager.getDefaultByFormDefKey(formDef.getKey(), false);
			}
			
			JSONObject permission = bpmFormRightManager.calcFormPermission(permissionConf);
			
			if(StringUtil.isNotEmpty(formHtml)){
				bpmForm.setFormHtml(formHtml);
			}
			
			List<String> boCode = bpmFormDefDao.getBOCodeByFormId(defId);
			List<BoData> boJson = boDataService.getDataByBoKeys(boCode);
			JSONObject object = BoDataUtil.hanlerData("",boJson);
			
			if(formType.equals(FormType.MOBILE.value())){
				modelAndView = new ModelAndView("/form/form/mobilePreview");
			}
			
			return modelAndView.addObject("bpmForm", bpmForm)
					.addObject("permission",  permission.toString())
					.addObject("data",  object.toJSONString())
					.addObject("returnUrl", preUrl);
	}
	
	/**
	 * 批量删除表单记录
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
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			String bpmNames =checkBpmForm(aryIds);//检查是否绑定了流程
			if(StringUtil.isEmpty(bpmNames)){
				bpmFormManager.remove(aryIds);
				message=new ResultMessage(ResultMessage.SUCCESS, "删除流程任务表单成功");
			}else{
				String msg = "删除失败,表单已帮定流程："+bpmNames;
				message=new ResultMessage(ResultMessage.FAIL, msg);
			}
			
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除流程任务表单失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 删除表单时检查是否绑定了流程。
	 * <pre>
	 * 目前是先找表单和业务对象的关系，然后再找业务对象和流程的关系
	 * </pre>
	 * @param aryIds
	 * @return
	 */
	public String checkBpmForm(String[] aryIds) {
		String bpmNames = "";
		for(String formId : aryIds){//多个表单同时删除
			BpmForm bpmForm = bpmFormManager.get(formId);
			List<String> boDefs= bpmFormDefDao.getBODefIdByFormId(bpmForm.getDefId());//获取表单对应的业务对象
			for(String boDefId :boDefs){//业务对象有主对象和从对象
				BODef boDef = bODefManager.get(boDefId);
				if(boDef==null) continue;
				
				//获取所有绑定该业务对象的设置
				List<BpmProBo> bpmProBos=bpmProBoManager.getByBoCode(boDef.getAlias());
				for(BpmProBo bpmProBo:bpmProBos){
					DefaultBpmDefinition  bpmDef = bpmDefinitionManager.get(bpmProBo.getProcessId());
					if(BeanUtils.isEmpty(bpmDef)) continue;
					
					String defId = bpmDef.getDefId();
					
					boolean isFormExist=isFormExists(defId,bpmForm.getFormKey());
					if(isFormExist){
						bpmNames +=  "『"+bpmDef.getName()+"』";
					}
				}
			}
		}
		return bpmNames;
	}
	
	/**
	 * 检查表单是否被使用过。
	 * @param defId
	 * @param formKey
	 * @return
	 */
	private boolean isFormExists(String defId,String formKey){
		BpmForm bpmForm = bpmFormManager.getMainByFormKey(formKey);
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		Form frm = defExt.getGlobalForm();//获取全局表单
		Form mobileFrm = defExt.getGlobalMobileForm();//全局手机表单
		Form instFrm = defExt.getInstForm();//获取全局实例表单
		Form instMobileFrm = defExt.getInstMobileForm();//获取全局实例手机表单
		if((frm != null && frm.getFormValue().equals(formKey)) || (mobileFrm != null && mobileFrm.getFormValue().equals(formKey))||
				(instFrm != null && instFrm.getFormValue().equals(formKey)) || (instMobileFrm != null && instMobileFrm.getFormValue().equals(formKey))){//不同版本的表单共用一个formKey ,因此不用考虑不同版本的表单绑定了流程
			return true;
		}else{//如果全局表单里面没有设置则会去找节点表单
			List<BpmNodeDef> nodeList = bpmDefinitionAccessor.getSignUserNode(defId);//全局表单没有，则会找节点表单
			for(BpmNodeDef bpmNodeDef:nodeList){
				if(bpmForm.getFormType().equals("pc")){
					frm = bpmNodeDef.getForm();
				}else{
					frm = bpmNodeDef.getMobileForm();
				}
				if(frm != null && frm.getFormValue().equals(formKey)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 加载编辑器设计模式的模板列表
	 */
	@RequestMapping("chooseDesignTemplate")
	public ModelAndView chooseDesignTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();
		String subject = RequestUtil.getString(request, "subject");
		Long categoryId = RequestUtil.getLong(request, "categoryId");
		String formDesc = RequestUtil.getString(request, "formDesc");
		int isSimple = RequestUtil.getInt(request, "isSimple", 0);

		String templatePath = FormUtil.getDesignTemplatePath();
		String xml = FileUtil.readFile(templatePath + "designtemps.xml");
		Document document = Dom4jUtil.loadXml(xml);
		Element root = document.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		String reStr = "[";
		for (Element element : list) {
			String alias = element.attributeValue("alias");
			String name = element.attributeValue("name");
			String templateDesc = element.attributeValue("templateDesc");
			if (!reStr.equals("["))
				reStr += ",";
			reStr += "{name:'" + name + "',alias:'" + alias+ "',templateDesc:'" + templateDesc + "'}";
		}
		reStr += "]";
		mv.addObject("subject", subject).addObject("categoryId", categoryId)
				.addObject("formDesc", formDesc).addObject("temps", reStr)
				.addObject("isSimple", isSimple);
		return mv;
	}
	
	/**
	 * 根据模板产生html。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("genByTemplate")
	public void genByTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String tableNames=  RequestUtil.getString(request, "tableNames");  
		String templateAlias=  RequestUtil.getString(request, "templateAlias");  
		String formId = RequestUtil.getString(request, "formId");
		// 新建表单的情况没有formId
		String formDefId = RequestUtil.getString(request, "formDefId");
		String formType = RequestUtil.getString(request, "formType");
		
		BpmForm form = bpmFormManager.get(formId);
		if(form != null)formDefId = form.getDefId();
		
		String	html = bpmFormManager.getHtml(formDefId, tableNames, templateAlias);
		
		PrintWriter out = response.getWriter();
		html = JsoupUtil.prettyHtml(html);
		StringBuffer outHtml = new StringBuffer();
		String script="<script type='text/javascript'>"
			+"function validForm(scope){"
			+" return true;"	
			+"}"
			+"</script>";
		
		outHtml.append(script);
		if(StringUtil.isNotEmpty(formType) && formType.equals(FormType.MOBILE.value())){
			outHtml.append("<div style=\"height: 100%;overflow: auto;\">");
			outHtml.append(html);
			outHtml.append("</div>");
		}else{
			outHtml.append(html);
		}
		out.println(outHtml);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getFormBoLists")
	@ResponseBody
	public List<BaseBoEnt> getFormBoLists(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String formId=  RequestUtil.getString(request, "formId");  
		String defId=  RequestUtil.getString(request, "defId");  
		if(StringUtil.isEmpty(defId)){
			BpmForm form = bpmFormManager.get(formId);
			defId=form.getDefId();
		}
		
		List<String> ids = bpmFormDefDao.getBODefIdByFormId(defId);
		List<BaseBoEnt> boEntList = new ArrayList<BaseBoEnt>();
		for (String boId : ids) {
			BODef bODef = bODefManager.getByDefId(boId);
			if(bODef!= null){
				handBoEnt(boEntList, bODef.getBoEnt(),bODef.getAlias());
			}
		}
		return boEntList;
	}
	private void handBoEnt(List<BaseBoEnt> boEntList,BaseBoEnt boEnt,String boDefKey){
		if(boEnt==null)return;
		
		boEnt.setTableNameNoPrefix(boEnt.getName());
		if("main".endsWith(boEnt.getType())){
			boEnt.setName(boDefKey);//目前表单主表标志用的boDefKey 这里搞下，方便自定义对话框的使用
		}
		else boEnt.setType(boDefKey);// 子表type为主表表名
		
		boEntList.add(boEnt);
		List<BaseBoEnt> childEnt =	boEnt.getChildEntList();
		if(BeanUtils.isEmpty(childEnt))return;
		
		for (BaseBoEnt ent : childEnt) {
			handBoEnt(boEntList,ent,boDefKey);
		}
	}
	
	
	/**
	 * 流程定义对话框。 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("dialog")
	public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Boolean isSingle= RequestUtil.getBoolean(request, "isSingle",true);
		String defId = RequestUtil.getString(request, "defId");
		return getAutoView()
				.addObject("isSingle", isSingle)
				.addObject("defId", defId);
	}
	
	
	@RequestMapping("newVersion")
	public void newVersion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String formId = RequestUtil.getString(request, "id");
		ResultMessage msg;
		try {
			bpmFormManager.newVersion(formId);
			msg = new ResultMessage(ResultMessage.SUCCESS, "新建表单版本成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = new ResultMessage(ResultMessage.FAIL, "新建表单版本失败!");
		}
		writeResultMessage(response.getWriter(), msg);
	}
	/**
	 * 设置默认版本
	 * 
	 * @param request
	 * @param response
	 * @param formDefId
	 * @param formDefId
	 * @throws Exception
	 */
	@RequestMapping("setDefaultVersion")
	public void setDefaultVersion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage resultObj = new ResultMessage(ResultMessage.SUCCESS,
				"设置默认版本成功!");
		String preUrl = RequestUtil.getPrePage(request);
		String formId = RequestUtil.getString(request, "id");
		String formKey = RequestUtil.getString(request, "formKey");
		bpmFormManager.setDefaultVersion(formId, formKey);
		writeResultMessage(response.getWriter(), resultObj);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 发布表单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("publish")
	public void publish(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String formId = RequestUtil.getString(request, "id");
		ResultMessage resultObj = null;
		try {
			bpmFormManager.publish(formId);
			resultObj = new ResultMessage(ResultMessage.SUCCESS, "发布版本成功!");
		} catch (Exception e) {
			resultObj = new ResultMessage(ResultMessage.FAIL, e.getCause()
					.toString());
		}
		writeResultMessage(response.getWriter(), resultObj);
	}
	
	@RequestMapping("genByField")
	public void genByField(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String defId = RequestUtil.getString(request, "defId");  
		String attrId = RequestUtil.getString(request, "attrId");  
		String formType = RequestUtil.getString(request, "formType");
		String html = bpmFormManager.genByField(defId,attrId,formType);
		html = JsoupUtil.prettyHtml(html);
		PrintWriter out = response.getWriter();
		out.println(html);
	}
	

	/**
	 * <pre>
	 * 导出格式为*.zip的BO对象，zip文件包含多个xml文件，每一个xml文件都是一个bo业务对象;
	 * <br>
	 * zip文件命名为：boDef_yyyyMMddHHmmss.zip;
	 * <br>
	 * 每个xml文件命名规则为:name_id.xml;
	 * <br>
	 * 完成后，相关生成的文件都会删除.
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private final static String ROOT_PATH = "attachFiles" + File.separator + "tempZip"; // 导入和导出的文件操作根目录
	@RequestMapping("exportForm")
	public void exportForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String[] ids = RequestUtil.getStringAryByStr(request, "ids");
			if (BeanUtils.isEmpty(ids)) 	return;
			List<String> idList = Arrays.asList(ids);
			
			Map map = bpmFormManager.exportForms(idList,true); // 输出xml
			
			String fileName = "ht_form_"+DateFormatUtil.format(new Date(), "yyyy_MMdd_HHmm");
			HttpUtil. downLoadFile(request, response, map, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "导出失败!");
			writeResultMessage(response.getWriter(), message);
		} 
	}

	
	/**
	 * 导入BO对象
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importForm")
	public void importForm(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		ResultMessage message = null;
		String unZipFilePath = null;
		try {
			//String rootRealPath = request.getSession().getServletContext().getRealPath(ROOT_PATH); // 操作的根目录
			String rootRealPath = this.getClass().getClassLoader().getResource(File.separator).getPath()+ROOT_PATH;// 操作的根目录
			String name = fileLoad.getOriginalFilename();
			String fileDir = StringUtil.substringBeforeLast(name, ".");
			
			ZipUtil.unZipFile(fileLoad, rootRealPath); // 解压文件
			unZipFilePath = rootRealPath + File.separator + fileDir; // 解压后文件的真正路径
			
			// 导入xml
			bpmFormManager.importForms(unZipFilePath);
			message = new ResultMessage(ResultMessage.SUCCESS, ThreadMsgUtil.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			message = new ResultMessage(ResultMessage.FAIL, "导入失败! "+e.getMessage());
		} finally {
		   try {
				File formDir = new File(unZipFilePath);
				if (formDir.exists()) {
					FileUtil.deleteDir(formDir); // 删除解压后的目录
				}
		   } catch (Exception e2) {
			   message = new ResultMessage(ResultMessage.FAIL, "请传入zip文件! ");
		  }
		
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	
	/**自定义对话框使用。我加载顺序上我已经绝望*/
	@RequestMapping("custBtn")
	public ModelAndView custBtn(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<BaseBoEnt> entList = getFormBoLists(request, response);
		String defId=  RequestUtil.getString(request, "defId");  
		return new ModelAndView("/form/custBtn/customButtonPage")
				.addObject("boEntList", JSONObject.toJSON(entList))
				.addObject("defId", defId);
	}
	
	
}
