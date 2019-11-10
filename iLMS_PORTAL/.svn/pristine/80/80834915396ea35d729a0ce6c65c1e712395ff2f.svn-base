package com.hotent.mini.controller.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.HttpUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.manager.BOEntManager;
import com.hotent.bo.persistence.manager.BOEntRelManager;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.bo.persistence.model.BOEntRel;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProBoManager;
import com.hotent.bpmx.persistence.model.BpmProBo;
import com.hotent.form.persistence.manager.BpmFormDefManager;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormDef;

/**
 * 
 * <pre>
 * 描述：xbo_def 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-11 17:40:47
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/bo/def/bODef")
public class BODefController extends GenericController {
	@Resource
	BODefManager bODefManager;
	@Resource
	BOEntRelManager boEntRelManager;
	@Resource
	BOEntManager boEntManager;
	@Resource
	BpmFormDefManager bpmFormDefManager;
	@Resource
	BoDataHandler boDataHandler;
	@Resource
	BpmProBoManager bpmProBoManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmFormManager bpmFormManager;
	/**
	 * xbo_def列表(分页条件查询)数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<BODef> bODefList = (PageList<BODef>) bODefManager.query(queryFilter);
		
		PageList<JSONObject> pageList = new PageList<JSONObject>(bODefList.getPageResult());
		JSONArray list =  (JSONArray) JSON.toJSON(bODefList);
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = list.getJSONObject(i);
			obj.remove("boEnt");
			boolean b =bODefManager.isEditable(obj.getString("id"));
			obj.put("editable", b);
			
			pageList.add(obj);
		}
		
		return new PageJson(pageList);
	}

	/**
	 * 保存xbo_def信息
	 * 
	 * @param request
	 * @param response
	 * @param bODef
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = FileUtil.inputStream2String(request.getInputStream());
		String resultMsg = null;
		try {
			resultMsg = "保存成功";
			bODefManager.save(json);
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "保存失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除xbo_def记录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			// 删除前判断是否绑定过表单
			for (String id : aryIds) {
				List<BpmFormDef> list = bpmFormDefManager.getByBODefId(id);
				if (list.isEmpty()) continue;
				BODef boDef = bODefManager.get(id);
				String str = "";
				for (BpmFormDef def : list) {
					if (StringUtil.isNotEmpty(str)) {
						str += ",";
					}
					str += def.getName();
				}
				throw new Exception("业务对象定义“" + boDef.getDescription() + "”已绑定表单元数据“" + str + "”，不能被删除！");
			}
			bODefManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除业务对象定义成功");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("getObject")
	public @ResponseBody BODef getObject(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isNotEmpty(id)) {
			BODef boDef = bODefManager.get(id);
			return boDef;
		}
		String key = RequestUtil.getString(request, "key");
		if (StringUtil.isNotEmpty(key)) {
			BODef boDef = bODefManager.getByAlias(key);
			return boDef;
		}
		return null;
	}

	@RequestMapping("isExist")
	public @ResponseBody boolean isExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String alias = RequestUtil.getString(request, "key");
		if (StringUtil.isNotEmpty(alias)) {
			BODef temp = bODefManager.getByAlias(alias);// 数据库中用这个别名的对象
			if (temp == null) {
				return false;
			}
			return !temp.getId().equals(id);// 如果id跟数据库中用这个别名的对象一样就返回false，反之true
		}
		return false;
	}

	@RequestMapping("getRelList")
	public @ResponseBody List<BOEntRel> getRelList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String defId = RequestUtil.getString(request, "defId");
		if (StringUtil.isNotEmpty(defId)) {
			List<BOEntRel> list = boEntRelManager.getByDefId(defId);
			return list;
		}
		return null;
	}

	@RequestMapping("deploy")
	public void deploy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String id = RequestUtil.getString(request, "id");
			BODef boDef = bODefManager.getByDefId(id);
			boDef.setDeployed((short) 1);
			bODefManager.update(boDef);
			message = new ResultMessage(ResultMessage.SUCCESS, boDef.getDescription() + "发布成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "发布失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("setStatus")
	public void setStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String id = RequestUtil.getString(request, "id");
			String status = RequestUtil.getString(request, "status","normal");
			BODef boDef = bODefManager.getByDefId(id);
			boDef.setStatus(status);
			bODefManager.update(boDef);
			message = new ResultMessage(ResultMessage.SUCCESS, boDef.getDescription() + "更改状态成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "更改状态失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	
	// 用于导入导出时用的唯一文件名
	public static final String fileName = "boDefs.xml";

	@RequestMapping("exportXml")
	public void exportXml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] ids = RequestUtil.getStringAryByStr(request, "ids");
		if (BeanUtils.isEmpty(ids))
			return;
		List boDefs = new ArrayList();
		for (String id : ids) {
			boDefs.add(bODefManager.getByDefId(id));
		}
		// 要写的内容
		String strXml = bODefManager.serialToXml(boDefs);
		String zipName = "boDef_" + DateFormatUtil.format(new Date(), "yyyyMMddHHmmss");
		HttpUtil.downLoadFile(request, response, strXml, fileName, zipName);
	}

	@RequestMapping("importXml")
	public void importXml(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		ResultMessage message = null;
		try {
			String xmlStr = FileUtil.readZip(fileLoad, fileName);
			List list = bODefManager.parseXml(xmlStr);
			bODefManager.importBoDef(list);
			String msg = ThreadMsgUtil.getMessage();
			message = new ResultMessage(ResultMessage.SUCCESS, msg);
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "导入失败:" + e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("getBOTree")
	public @ResponseBody JSONObject getBOTree(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String defIds = RequestUtil.getString(request, "ids");
		String[] ids = defIds.split(",");
		JSONArray boList = new JSONArray();
		for (String id : ids) {
			JSONObject objec = bODefManager.getBOJson(id);
			boList.add(objec);
		}
		JSONObject bos = JSONObject.parseObject("{id:\"0\",parentId:\"-1\",desc:\"BO对象属性\"}");
		bos.put("children", boList);
		return bos;
	}

	/**
	 * 能否编辑
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             boolean
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("isEditable")
	public @ResponseBody boolean isEditable(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id", "");
		//boolean b =bODefManager.isEditable(id);
		boolean b = false;
		if(StringUtil.isNotEmpty(id)){
			BODef def = bODefManager.get(id);
			if(def!=null&&"0".equals(def.getDeployed().toString())){
				b = true;
			}
		}else{
			b = true;
		}
		
		return b;
	}

	/**
	 * 根据bo定义返回bo对应的JSON数据结构，可以给第三方接口进行传递数据使用。
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getBoJson")
	public @ResponseBody JSONObject getBoJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String boCode = RequestUtil.getString(request, "boCode");
		BoData boData = boDataHandler.getByBoDefCode(boCode);
		return JSONObject.parseObject(boData.toString());
	}

	/**
	 * 关系页的初始方法
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             Object
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("initRelation")
	public @ResponseBody Object initRelation(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String type = RequestUtil.getString(request, "type");
		//绑定的实体列表
		JSONArray boList = new JSONArray();
		//绑定的业务对象定义列表
		JSONArray defList = new JSONArray();
		//绑定的流程列表
		JSONArray flowList = new JSONArray();
		//绑定的表单元数据列表
		List<BpmFormDef> formDefList = new ArrayList<BpmFormDef>();
		//绑定的表单列表
		List<BpmForm> formList = new ArrayList<BpmForm>();
		if("boEnt".equals(type)){
			List<BOEntRel> boEntRels = boEntRelManager.getByEntId(id);
			defList = getBoDeflistByBoEnt(boEntRels);
			for(int i=0;i<defList.size();i++){
				JSONObject obj = (JSONObject) defList.get(i);
				List<BpmFormDef> formDefs = bpmFormDefManager.getByBODefId(obj.getString("id"));
				if(BeanUtils.isNotEmpty(formDefs)){
					formDefList.addAll(formDefs);
					for(BpmFormDef formDef:formDefs){
						List<BpmForm> forms = bpmFormManager.getByDefId(formDef.getId());
						formList.addAll(forms);
					}
				}
				flowList.addAll(getFlowListByDefId(obj.getString("id")));
			}
		}else if("boDef".equals(type)){
			List<BOEntRel> boEntRels = boEntRelManager.getByDefId(id);
			boList = getBoEntlistByBoDef(boEntRels);
			List<BpmFormDef> formDefs = bpmFormDefManager.getByBODefId(id);
			if(BeanUtils.isNotEmpty(formDefs)){
				formDefList.addAll(formDefs);
				for(BpmFormDef formDef:formDefs){
					List<BpmForm> forms = bpmFormManager.getByDefId(formDef.getId());
					formList.addAll(forms);
				}
			}
			flowList = getFlowListByDefId(id);
		}else if("formDef".equals(type)){
			List<BpmForm> forms = bpmFormManager.getByDefId(id);
			if(BeanUtils.isNotEmpty(forms)){
				formList.addAll(forms);
				for(BpmForm form:forms){
					findRelByForm(form,defList,boList,flowList,formDefList,type);
				}
			}else{
				List<String> defAlias = bpmFormDefManager.getBOCodeByFormId(id);
				if(BeanUtils.isNotEmpty(defAlias)){
					for(String alias:defAlias){
						BODef boDef = bODefManager.getByAlias(alias);
						if(BeanUtils.isNotEmpty(boDef)){
							JSONObject jobj = new JSONObject();
							jobj.put("id", boDef.getId());
							jobj.put("name", boDef.getDescription());
							defList.add(jobj);
							List<BOEntRel> boEntRels = boEntRelManager.getByDefId(boDef.getId());
							boList.addAll(getBoEntlistByBoDef(boEntRels));
						}
					}
				}
			}
			
		}else if("form".equals(type)){
			BpmForm form = bpmFormManager.get(id);
			findRelByForm(form,defList,boList,flowList,formDefList,type);
		}
		JSONObject json = new JSONObject();
		this.removeDuplicate(boList);
		json.put("boList", boList);
		this.removeDuplicate(defList);
		json.put("defList", defList);
		this.removeDuplicate(formDefList);
		json.put("formDefList", formDefList);
		this.removeDuplicate(formList);
		json.put("formList", formList);
		this.removeDuplicate(flowList);
		json.put("flowList", flowList);
		return json;
	}
	
	/**
	 * 根据BO定义的和实体的关系获取bo列表
	 * @param boEntRels
	 * @return
	 */
	private JSONArray getBoEntlistByBoDef(List<BOEntRel> boEntRels){
		JSONArray boList = new JSONArray();
		if(BeanUtils.isNotEmpty(boEntRels)){
			for(BOEntRel boEntRel:boEntRels){
				BOEnt boEnt = boEntManager.get(boEntRel.getRefEntId());
				if(BeanUtils.isNotEmpty(boEnt)){
					JSONObject jobj = new JSONObject();
					jobj.put("id", boEnt.getId());
					jobj.put("name", boEnt.getName());
					jobj.put("isExternal", boEnt.getIsExternal());
					boList.add(jobj);
				}
			}
		}
		return boList;
	}
	
	/**
	 * 根据BO定义的和实体的关系获取BO定义列表
	 * @param boEntRels
	 * @return
	 */
	private JSONArray getBoDeflistByBoEnt(List<BOEntRel> boEntRels){
		JSONArray boDefList = new JSONArray();
		if(BeanUtils.isNotEmpty(boEntRels)){
			for(BOEntRel boEntRel:boEntRels){
				BODef boDef = bODefManager.get(boEntRel.getBoDefid());
				if(BeanUtils.isNotEmpty(boDef)){
					JSONObject jobj = new JSONObject();
					jobj.put("id", boDef.getId());
					jobj.put("name", boDef.getDescription());
					boDefList.add(jobj);
				}
			}
		}
		return boDefList;
	}
	
	/**
	 * 根据bo定义ID获取流程列表
	 * @param defId
	 * @return
	 */
	private JSONArray getFlowListByDefId(String defId){
		JSONArray flowList = new JSONArray();
		List<BpmProBo> defs = bpmProBoManager.getByBoCode(bODefManager.get(defId).getAlias());
		for(BpmProBo bpb : defs){
			BpmDefinition bpmDefinition = bpmDefinitionManager.get(bpb.getProcessId());
			if(BeanUtils.isNotEmpty(bpmDefinition)){
				JSONObject jobj = new JSONObject();
				jobj.put("id", bpb.getProcessId());
				jobj.put("name", bpmDefinition.getName());
				flowList.add(jobj);
			}
		}
		return flowList;
	}
	
	
	/**
	 * 根据表单获取表单相关实体、实体定义、流程、表单元数据
	 * @param form
	 * @param defList
	 * @param boList
	 * @param flowList
	 * @param formDefList
	 * @param type
	 */
	private void findRelByForm(BpmForm form,JSONArray defList,JSONArray boList,JSONArray flowList,List<BpmFormDef> formDefList,String type){
		List<BODef> boDefs = bODefManager.getByFormKey(form.getFormKey());
		if(BeanUtils.isNotEmpty(boDefs)){
			for(BODef boDef:boDefs){
				JSONObject jobj = new JSONObject();
				jobj.put("id", boDef.getId());
				jobj.put("name", boDef.getDescription());
				defList.add(jobj);
				List<BOEntRel> boEntRels = boEntRelManager.getByDefId(boDef.getId());
				boList.addAll(getBoEntlistByBoDef(boEntRels));
				if("form".equals(type)){
					BpmFormDef formDef = bpmFormDefManager.get(form.getDefId());
					formDefList.add(formDef);
				}
				flowList.addAll(getFlowListByDefId(boDef.getId()));
			}
		}
	}
	
	//去重复项
	public void removeDuplicate(List<?> list)  
	 {  
	  if(BeanUtils.isNotEmpty(list)){
		  HashSet h = new HashSet(list);  
		  list.clear();  
		  list.addAll(h); 
	  }
	} 
}
