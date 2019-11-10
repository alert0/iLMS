package com.hotent.mini.controller.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmBoDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.core.engine.def.BpmDefUtil;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProBoManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.form.persistence.dao.BpmFormDao;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.manager.BpmFormDefManager;
import com.hotent.form.persistence.manager.BpmFormHistoryManager;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.util.FormUtil;

import net.sf.json.JSONObject;

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
@RequestMapping("/form/formDef/")
public class FormDefController extends GenericController{
	@Resource
	BpmFormManager bpmFormManager;
	@Resource
	BpmFormDefManager bpmFormDefManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmFormHistoryManager bpmFormHistoryManager;
	@Resource
	BpmFormDao bpmFormDao;
	@Resource
	BpmProBoManager bpmProBoManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmFormDefDao bpmFormDefDao;
	@Resource
	BpmFormService bpmFormService;
	/**
	 * 流程任务表单列表(分页条件查询)数据
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
		List<JSONObject> bpmFormDefList=new ArrayList<JSONObject>();
		PageList<BpmFormDef> list = (PageList<BpmFormDef>) bpmFormDefManager.query(queryFilter);
		for(BpmFormDef bpmFormdef : list){
			JSONObject formJson = new JSONObject();//JSONObject.fromObject(bpmForm);
			formJson.accumulate("desc", bpmFormdef.getName());
			formJson.accumulate("key", bpmFormdef.getKey());
			formJson.accumulate("id", bpmFormdef.getId());
			formJson.accumulate("name", bpmFormdef.getName());
			formJson.accumulate("type", bpmFormdef.getType());
			
			//计算bo对象
			String expand = bpmFormdef.getExpand();
			if(StringUtil.isNotEmpty(expand)){
				JSONObject expandJson = JSONObject.fromObject(expand);
				if(expandJson.containsKey("boDefList")){
					formJson.accumulate("boDefList", expandJson.get("boDefList").toString());
				}
			}
			
			bpmFormDefList.add(formJson);
		}
		PageJson pageJson = new PageJson(bpmFormDefList);
		pageJson.setTotal(list.getPageResult().getTotalCount());
		return pageJson;
	}

	
	/**
	 * 获取与BO关联的所有表单
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listJsonByBODef")
	public @ResponseBody PageJson listJsonByBODef(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String defId = RequestUtil.getString(request, "defId");
		String formType = RequestUtil.getString(request, "formType","pc");
		String topDefKey = RequestUtil.getString(request, "topDefKey","");
		
		List<ProcBoDef> boList=null;
		//如果是子流程，继承父类的bo数据。
		if(StringUtil.isNotEmpty(topDefKey)){
			BpmBoDef  bpmBodef= BpmDefUtil.getBpmBoDef(topDefKey);
			boList=bpmBodef.getBoDefs();
		}
		else{
			BpmProcessDef<BpmProcessDefExt> bpmProcessDef= bpmDefinitionAccessor.getBpmProcessDef(defId);
			DefaultBpmProcessDefExt  defExt= (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
			boList= defExt.getBoDefList();
		}
		QueryFilter filter = this.getQueryFilter(request);
		
		
		if(BeanUtils.isEmpty(boList)){
			return new PageJson();
		}
		List<String> codes = new ArrayList<String>();
		for(ProcBoDef procBoDef : boList){
			codes.add(procBoDef.getKey());
		}
		List<BpmForm> list = bpmFormManager.getByBoCodes(codes,formType,filter);
		return new PageJson(list);
	}
	
	/**
	 * 编辑表单页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("formDefEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		String typeId=RequestUtil.getString(request, "typeId");
		ModelAndView andView = getAutoView();
		if(StringUtil.isNotEmpty(typeId)){
			andView.addObject("typeId", typeId); 
			String typeName = RequestUtil.getString(request, "typeName","");
			andView.addObject("key", RequestUtil.getString(request, "key"));
			String desc = RequestUtil.getString(request, "desc","");
			String name = RequestUtil.getString(request, "name","");
			String bos = RequestUtil.getString(request, "bos","");
			andView.addObject("desc", desc);
			andView.addObject("name",name);
			andView.addObject("bos", bos);
			andView.addObject("type", typeName);
			preUrl=preUrl.replace("addFormStepOne", "list"); 
		} 
		return andView.addObject("formId", id).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 获取表单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@ResponseBody
	public Object get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String formId = RequestUtil.getString(request, "id");
		Map<String,Object> map=new HashMap<String, Object>(); 
		try{
			BpmFormDef bpmForm = null;
			if(StringUtil.isNotEmpty(formId)){
				bpmForm = bpmFormDefManager.get(formId);
			}
			if(BeanUtils.isEmpty(bpmForm)){
				map.put("result", false);
				map.put("message", "未获取到表单定义");
				return map;
			}
			else{
				map.put("result", true);
 				map.put("bpmForm", bpmForm);
				return map;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			map.put("result", false);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	
	@RequestMapping("getFormFieldTree")
	@ResponseBody
	public Object getBoTreeByFormId(HttpServletRequest request,HttpServletResponse response,String defId) throws Exception{
		BpmFormDef bpmForm = bpmFormDefManager.get(defId);
		JSONArray fields = bpmForm.getFieldList();
		return fields;
	}
	
	@RequestMapping("getObject")
	public @ResponseBody BpmFormDef getObject(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isNotEmpty(id)) {
			return bpmFormDefManager.get(id);
		}
		String key = RequestUtil.getString(request, "key");
		if (StringUtil.isNotEmpty(key)) {
			return bpmFormDefManager.getByKey(key);
		}
		return null;
	}
	
	
	/**
	 * 保存表单信息
	 * @param request
	 * @param response
	 * @param bpmForm
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String form = FileUtil.inputStream2String(request.getInputStream());
		JSONObject jobject = new JSONObject();
		try{
			BpmFormDef bpmFormdef = GsonUtil.toBean(form, BpmFormDef.class);
			if(BeanUtils.isNotEmpty(bpmFormdef.getId())){
				bpmFormdef.setUpdateTime(new Date());
				bpmFormDefManager.update(bpmFormdef);
			}
			else{
				String formKey=bpmFormdef.getKey();
				if(bpmFormDefManager.getByKey(formKey)!=null){
					throw new RuntimeException("表单已经存在！key:"+formKey);
				}
					
				
				bpmFormdef.setId(UniqueIdUtil.getSuid());
				bpmFormdef.setCreateTime(new Date());
				bpmFormDefManager.create(bpmFormdef);
			}
			return jobject.accumulate("result", true);
		}
		catch(Exception e){
			e.printStackTrace();
			return jobject.accumulate("result", false).accumulate("message", e.getMessage());
		}
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
			String bpmNames =checkBpmForm(aryIds);//检查是否绑定了表单
			if(StringUtil.isEmpty(bpmNames)){
				bpmFormDefManager.removeByIds(aryIds);
				message=new ResultMessage(ResultMessage.SUCCESS, "删除流程任务表单成功");
			}else{
				String msg = "已被用于生成业务表单："+bpmNames+"不能被删除";
				message=new ResultMessage(ResultMessage.FAIL, msg);
			}
			
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除流程任务表单失败："+e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 删除表单时检查是否绑定了流程
	 * 目前是先找表单和业务对象的关系，然后再找业务对象和流程的关系
	 * @param aryIds
	 * @return
	 */
	public String checkBpmForm(String[] aryIds) {
		String formKey = "";
		for(String defId : aryIds){//多个表单同时删除
			List<BpmForm> form = bpmFormDao.getByDefId(defId);
			if(BeanUtils.isNotEmpty(form)){
				for (BpmForm f : form) formKey += f.getName()+"（"+f.getFormKey()+"）,";
			}
			
		}
		
		return formKey ;
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
			reStr += "{name:'" + name + "',alias:'" + alias
					+ "',templateDesc:'" + templateDesc + "'}";
		}
		reStr += "]";
		mv.addObject("subject", subject).addObject("categoryId", categoryId)
				.addObject("formDesc", formDesc).addObject("temps", reStr)
				.addObject("isSimple", isSimple);
		return mv;
	}
	
	
	/**
	 * 表单定义对话框。 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("boFormDefDialog")
	public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Boolean isSingle= RequestUtil.getBoolean(request, "isSingle",true);
		String defId = RequestUtil.getString(request, "defId");
		String formType = RequestUtil.getString(request, "formType");
		String topDefKey = RequestUtil.getString(request, "topDefKey","");
		//src+="&parentDefKey=" + parentDefKey +"&nodeId="+nodeId;
		return getAutoView()
				.addObject("isSingle", isSingle)
				.addObject("formType", formType)
				.addObject("defId", defId)
				.addObject("topDefKey", topDefKey);
	}
	
	@RequestMapping("checkkeyIsExist")
	public @ResponseBody boolean checkAliasIsExist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String key=RequestUtil.getString(request, "key");
		if(StringUtil.isEmpty(key)) return false;
		BpmFormDef bpmForm = bpmFormDefManager.getByKey(key);
		if(BeanUtils.isEmpty(bpmForm)) return false;
		return true;
	}
	
	
	@RequestMapping("getOpinionConf")
	@ResponseBody
	public String opinionConf(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String id = RequestUtil.getString(request, "id");
		BpmFormDef def = bpmFormDefDao.get(id);
		
		String opinionConf = def.getOpinionConf();
		if(StringUtil.isEmpty(opinionConf))opinionConf = "[]";
		return opinionConf;
	}
	
	@RequestMapping("opinionConfSave")
	public void opinionConfSave(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String id = RequestUtil.getString(request, "id");
		String config = RequestUtil.getString(request, "opinionConf");
		try {
			bpmFormDefManager.updateOpinionConf(id,config);
			writeResultMessage(reponse.getWriter(), "保存成功！", ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(reponse.getWriter(), "保存失败！"+e.getMessage(), ResultMessage.FAIL);
		}
	}
	
}
