package com.hotent.form.persistence.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.engine.freemark.FreemarkEngine;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.api.IDynamicDatasource;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.table.util.SQLConst;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.constant.BodefConstants;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.instance.BoInstanceFactory;
import com.hotent.bo.api.instance.BoUtil;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.persistence.dao.BpmDataTemplateDao;
import com.hotent.form.persistence.dao.BpmFormDao;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.dao.BpmFormFieldDao;
import com.hotent.form.persistence.dao.BpmFormTemplateDao;
import com.hotent.form.persistence.manager.BpmDataTemplateManager;
import com.hotent.form.persistence.model.BpmDataTemplate;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormField;
import com.hotent.form.persistence.model.BpmFormTemplate;
import com.hotent.form.util.FreeMakerUtil;
import com.hotent.sys.api.permission.PermissionCalc;
import com.hotent.sys.api.permission.PermissionUtil;
import com.hotent.sys.api.util.ISysCoreUtilService;
import com.hotent.sys.api.var.IContextVar;


/**
 * 
 * <pre> 
 * 描述：业务数据模板 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-03-15 14:52:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmDataTemplateManager")
public class BpmDataTemplateManagerImpl extends AbstractManagerImpl<String, BpmDataTemplate> implements BpmDataTemplateManager{
	@Resource
	BpmDataTemplateDao bpmDataTemplateDao;
	@Resource
	BpmFormTemplateDao bpmFormTemplateDao;
	@Resource
	BpmFormDao bpmFromDao;
	@Resource 
	BpmFormDefDao bpmFormDefDao;
	@Resource
	BpmFormFieldDao bpmFormFieldDao;
	@Resource
	BpmFormDao bpmFormDao;
	@Resource
	BoDataHandler boDataHandler;
	@Resource
	BoInstanceFactory boInstanceFactory;
	@Resource
	BoDefService boDefService;
	@Resource(name="formPermissionCalc")
	PermissionCalc permssionCalc;
	@Resource
	FreemarkEngine freemarkEngine;
	@Resource
	IDynamicDatasource dynamicDatasource;
	@Resource
	ISysCoreUtilService sysCoreUtilService;
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	
	@Override
	protected Dao<String, BpmDataTemplate> getDao() {
		return bpmDataTemplateDao;
	}
	
	@Override
	public JSONObject getByFormKey(String formKey) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", ResultMessage.SUCCESS);
		jsonObject.put("msg", "支持生成业务数据模板");
		List<BpmFormTemplate> templates = new ArrayList<BpmFormTemplate>();
		BpmDataTemplate bpmDataTemplate = new BpmDataTemplate();
		List<BpmFormField> fields = new ArrayList<BpmFormField>();
		BaseBoDef baseBoDef = null;
		String boDefId = "";
		String colPrefix = "";
		String displaySettingFields ="";
		Boolean fag = true;
		
		// formKey ==>获取表单元数据定义id  def_id 根据def_id 获取bo对象的个数， 只有一个才支持业务对象模板的绑定
		BpmForm bpmForm = bpmFromDao.getMainByFormKey(formKey);
		String formId = bpmForm.getDefId();
		List<String> boDefIds = bpmFormDefDao.getBODefIdByFormId(formId);
		if(boDefIds.isEmpty() || boDefIds.size()!=1 ){
			ThreadMsgUtil.addMsg("该业务表单不支持生成业务数据模板(只支持一个BO生成的表单)");
			jsonObject.put("status", ResultMessage.FAIL);
			jsonObject.put("msg", ThreadMsgUtil.getMessage().trim());
			fag = false;
		}
		
		if(fag){
			boDefId = boDefIds.get(0);
			baseBoDef =  boDefService.getByDefId(boDefId);
			colPrefix = baseBoDef.getBoEnt().isExternal()?"":SQLConst.CUSTOMER_COLUMN_PREFIX;
			if(1!=baseBoDef.getSupportDb()){
				jsonObject.put("status", ResultMessage.FAIL);
				jsonObject.put("msg", "该表单不支持数据库，不能生成业务数据模板");
				fag = false;
			}
		}
		if(fag){
			// 获取字段信息  根据form_id_(表单元数据定义id) , 业务对象定义id 获取主表字段 
			fields  = bpmFormFieldDao.getByFormIdAndBoDefId(formId, boDefId);
			displaySettingFields = this.getDisplayField(fields, "");
			bpmDataTemplate = bpmDataTemplateDao.getByFormKey(formKey);
			if(BeanUtils.isEmpty(bpmDataTemplate)){
				bpmDataTemplate = new BpmDataTemplate();
				bpmDataTemplate.setFormKey(formKey);
				bpmDataTemplate.setDisplayField(displaySettingFields);
				bpmDataTemplate.setBoDefId(boDefId); // 设置业务对象定义id
				bpmDataTemplate.setBoDefAlias(baseBoDef.getAlias());
				bpmDataTemplate.setAlias(formKey);
				bpmDataTemplate.setName(bpmForm.getName());
			}else{
				bpmDataTemplate.setDisplayField(bpmDataTemplate.getDisplayField());
			}
		}
		
		// 获取表单模板
		templates = bpmFormTemplateDao.getTemplateType(BpmFormTemplate.DATA_TEMPLATE);
		
		jsonObject.put("templates", templates);
		jsonObject.put("bpmDataTemplate", bpmDataTemplate);
		jsonObject.put("fields", fields);
		jsonObject.put("displaySettingFields", displaySettingFields);
		jsonObject.put("DataRightsJson",tidyJson(bpmDataTemplate));
		jsonObject.put("colPrefix", colPrefix);
		
		jsonObject.put("permissionList", PermissionUtil.getPermissionList("formPermissionCalcList"));
		return jsonObject;
	}

	private JSONObject tidyJson(BpmDataTemplate template){
		template.setTemplateHtml("");
		JSONObject jsonObject= JSONObject.fromObject(template);
		jsonObject.remove("pageBean");
		jsonObject.remove("createBy");
		jsonObject.remove("createtime");
		jsonObject.remove("updateBy");
		jsonObject.remove("updatetime");
		
		return jsonObject;
	}
	
	private String getDisplayField(List<BpmFormField> fields, String displayField) {
		Map<String, String> map = getDisplayFieldRight(displayField);
		Map<String, String> descMap = getDisplayFieldDesc(displayField);
		if (BeanUtils.isNotEmpty(fields)) {
			JSONArray jsonAry = new JSONArray();
			for (BpmFormField bpmFormField : fields) {
				JSONObject json = new JSONObject();
				json.put("name", bpmFormField.getName());
				String desc = bpmFormField.getDesc();
				if (BeanUtils.isNotEmpty(map) && map.containsKey(bpmFormField.getName())) {
					desc = descMap.get(bpmFormField.getName());
				}
				json.put("desc", desc);
				json.put("type", bpmFormField.getType());
				String right = "";
				if (BeanUtils.isNotEmpty(map))
					right = map.get(bpmFormField.getName());
				if (StringUtil.isEmpty(right))
					right = getDefaultDisplayFieldRight();

				json.put("right", right);
				jsonAry.add(json);
			}
			displayField = jsonAry.toString();
		}
		return displayField;
	
	}
	
	private Map<String, String> getDisplayFieldDesc(String displayField) {
		if (StringUtil.isEmpty(displayField))
			return null;
		Map<String, String> map = new HashMap<String, String>();
		JSONArray jsonAry = JSONArray.fromObject(displayField);
		for (Object obj : jsonAry) {
			JSONObject json = (JSONObject) obj;
			String name = (String) json.get("name");
			String desc = (String) json.get("desc");
			map.put(name, desc);
		}
		return map;
	}
	
	private Map<String, String> getDisplayFieldRight(String displayField) {
		if (StringUtil.isEmpty(displayField))
			return null;
		Map<String, String> map = new HashMap<String, String>();
		JSONArray jsonAry = JSONArray.fromObject(displayField);

		for (Object obj : jsonAry) {
			JSONObject json = (JSONObject)obj;
			String name = (String) json.get("name");
			JSONArray right = (JSONArray) json.get("right");
			map.put(name, right.toString());
		}
		return map;
	}

	private String getDefaultDisplayFieldRight() {
		JSONArray array=new JSONArray();
		JSONObject json = new JSONObject();
		json.put("type", "everyone");
		json.put("id", "");
		json.put("name", "");
		json.put("script", "");
		array.add(json);
		return array.toString();
	}

	@Override
	public void save(BpmDataTemplate bpmDataTemplate, boolean resetTemp) throws Exception {


		Integer bpmDataTemplateNum = getCountByFormKey(bpmDataTemplate.getFormKey());
		boolean flag1 = bpmDataTemplateNum > 0;//判断是否已存在数据
		boolean flag2 = StringUtil.isEmpty(bpmDataTemplate.getId());

		String templateHtml = generateTemplate(bpmDataTemplate);
		if (!flag1 && flag2) {//新
			bpmDataTemplate.setId(UniqueIdUtil.getSuid());
			bpmDataTemplate.setTemplateHtml(templateHtml);//每次保存都需要重新生成模板
			bpmDataTemplateDao.create(bpmDataTemplate);
		} else {
			if(resetTemp){//需要更新模板
				bpmDataTemplate.setTemplateHtml(templateHtml);
			}else{
				BpmDataTemplate old = bpmDataTemplateDao.getByFormKey(bpmDataTemplate.getFormKey());
				bpmDataTemplate.setTemplateHtml(old.getTemplateHtml());
			}
			this.update(bpmDataTemplate);
		}
	
		
	}
	
	/**
	 * 解析生成第一次的模板
	 * 
	 * @param bpmDataTemplate
	 * @return
	 * @throws Exception
	 */
	private String generateTemplate(BpmDataTemplate bpmDataTemplate) throws Exception {
		BpmFormTemplate bpmFormTemplate = bpmFormTemplateDao.getByTemplateAlias(bpmDataTemplate.getTemplateAlias());//获取需要第一次解释的模板
		List<BpmFormField> fileds = bpmFormFieldDao.getByboDefId(bpmDataTemplate.getBoDefId()); // 获取主表字段
		// 是否有条件查询
		boolean hasCondition = hasCondition(bpmDataTemplate.getConditionField());
		// 是否有功能按钮
		boolean hasManage = hasManage(bpmDataTemplate.getManageField());
		// 获取主键
		BaseBoDef boDef = boDefService.getByName(bpmDataTemplate.getBoDefAlias());
		BaseBoEnt boEnt = (BaseBoEnt) boDef.getBoEnt();
		// 第一次解析模板
		FreeMakerUtil freeMakerUtil = new FreeMakerUtil();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bpmDataTemplate", bpmDataTemplate);
		map.put("hasCondition", hasCondition);
		map.put("hasManage", hasManage);
		map.put("pkField", boEnt.getPkKey().toLowerCase());
		map.put("colPrefix", boEnt.isExternal()?"":SQLConst.CUSTOMER_COLUMN_PREFIX);
		map.put("formatData", fileds);
		map.put("util", freeMakerUtil);
		String templateHtml = freemarkEngine.parseByStringTemplate(bpmFormTemplate.getHtml(),map);
		return templateHtml;

	}

	/**
	 * 是否有管理
	 * 
	 * @param manageField
	 * @return
	 */
	private boolean hasManage(String manageField) {
		if (StringUtil.isEmpty(manageField))
			return false;
		JSONArray jsonAry = JSONArray.fromObject(manageField);
		return jsonAry.size() > 0 ? true : false;
	}

	/**
	 * 是否有条件
	 * 
	 * @param conditionField
	 * @return
	 */
	private boolean hasCondition(String conditionField) {
		if (StringUtil.isEmpty(conditionField))
			return false;
		JSONArray jsonAry = JSONArray.fromObject(conditionField);
		return jsonAry.size() > 0 ? true : false;
	}
	
	/**
	 * 根据formKey获取业务表单数量。
	 * 
	 * @param formKey
	 * @return
	 */
	public Integer getCountByFormKey(BpmDataTemplate bpmDataTemplate) {
		return bpmDataTemplateDao.getCountByFormKey(bpmDataTemplate.getFormKey(), bpmDataTemplate.getId());
	}

	/**
	 * 根据表单key获取是否定义了数据模版。
	 * 
	 * @param formKey
	 * @return
	 */
	public Integer getCountByFormKey(String formKey) {
		return bpmDataTemplateDao.getCountByFormKey(formKey, "");
	}

	@Override
	public String getDisplay(String alias, Map<String, Object> params,
			Map<String, Object> queryParams) throws Exception {
		Map<String, Set<String>> curProfiles =  permssionCalc.getCurrentProfiles();
		Map<String, Object> model = new HashMap<String, Object>();
		
		//获取业务模板数据
		BpmDataTemplate bpmDataTemplate = bpmDataTemplateDao.getByFormKey(alias);
		
		params.put(BpmDataTemplate.PARAMS_KEY_BOALIAS, bpmDataTemplate.getBoDefAlias());
		params.put(BpmDataTemplate.PARAMS_KEY_FORM_KEY, bpmDataTemplate.getFormKey());
		params.put(BpmDataTemplate.PARAMS_KEY_DEF_ID, BeanUtils.isEmpty(bpmDataTemplate.getDefId())?"":bpmDataTemplate.getDefId());
		
		Map<String,Boolean> managePermission = getManagePermission(bpmDataTemplate.getManageField(), curProfiles);
		
		if(StringUtil.isEmpty(bpmDataTemplate.getDefId())){
			managePermission.put(BpmDataTemplate.MANAGE_TYPE_START_FLOW, false);
		}
		
		List<Map<String,String>> filters = getFilterPermission(bpmDataTemplate.getFilterField(),curProfiles); 
		// actionUrl
		model.put("actionUrl", getActionUrl(params));
		model.put("ctx",params.get(BpmDataTemplate.PARAMS_KEY_CTX));
		model.put("bpmDataTemplate", bpmDataTemplate);
		// 当前字段的权限
		model.put("permission", getPermission( bpmDataTemplate.getDisplayField(), curProfiles));
		// 功能按钮的权限
		model.put("managePermission", managePermission);
		
		// 获取主键
		BaseBoDef boDef = boDefService.getByName(bpmDataTemplate.getBoDefAlias());
		BaseBoEnt boEnt = (BaseBoEnt) boDef.getBoEnt();
		model.put("pkField", boEnt.getPkKey().toLowerCase());
		model.put("colPrefix", boEnt.isExternal()? "":SQLConst.CUSTOMER_COLUMN_PREFIX);
		model.put("filters", filters);
		
		String templateHtml = bpmDataTemplate.getTemplateHtml();
		
		String html  = freemarkEngine.parseByStringTemplate(templateHtml, model);
		
		return html;
	}
	
	/**
	 * 获取当前用户有权限的过滤条件
	 * @param filterField
	 * @param curProfiles
	 * @return
	 */
	private List<Map<String, String>> getFilterPermission(String filterField,
			Map<String, Set<String>> curProfiles) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		if(StringUtil.isEmpty(filterField)) 
			return list;
		JSONArray jsonAry = JSONArray.fromObject(filterField);
		
		for (Object obj : jsonAry) {
			JSONObject json = JSONObject.fromObject(obj);
			JSONArray rights = (JSONArray) json.get("right");
			Iterator<JSONObject> iterator = rights.iterator();
			boolean hasRight = false;
			while(iterator.hasNext()){
				JSONObject permission = iterator.next();
				hasRight = permssionCalc.hasRight(permission.toString(), curProfiles);
				if(hasRight){
					break;
				}
			}
			if(hasRight){
				Map<String,String> map = new HashMap<String, String>();
				map.put("name", JsonUtil.getString(json, "name", ""));
				map.put("filterKey", JsonUtil.getString(json, "key", ""));
				list.add(map);
			}
		}
		
		return list;
	}

	/**
	 * 获取管理的权限
	 * 
	 * @param manageField
	 * @param rightMap
	 * @return
	 */
	private Map<String, Boolean> getManagePermission(String manageField, Map<String, Set<String>> currentMap) {
		if (StringUtil.isEmpty(manageField))
			return null;
		JSONArray jsonAry = JSONArray.fromObject(manageField);
		return getPermissionMap(jsonAry, currentMap);
	}
	
	/**
	 * [{"desc":"新增","name":"add","right":[{"type":"everyone"}]},
	 * {"desc":"编辑","name":"edit","right":[{"type":"everyone"}]},
	 * {"desc":"删除","name":"del","right":[{"type":"everyone"}]},
	 * {"desc":"明细","name":"detail","right":[{"type":"everyone"}]}]
	 * @param jsonAry
	 * @param currentMap
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Boolean> getPermissionMap(JSONArray jsonAry,
			Map<String, Set<String>> currentMap) {
		Map<String,Boolean> map = new HashMap<String, Boolean>();
		if(JsonUtil.isEmpty(jsonAry)){
			return map;
		}
		for (Object obj : jsonAry) {

			JSONObject json = JSONObject.fromObject(obj);
			String name = json.getString("name");;
			JSONArray rights = (JSONArray) json.get("right");
			Iterator<JSONObject> iterator = rights.iterator();
			boolean hasRight = false;
			while(iterator.hasNext()){
				JSONObject permission = iterator.next();
				hasRight = permssionCalc.hasRight(permission.toString(), currentMap);
				if(hasRight){
					map.put(name, hasRight);
					break;
				}
			}
			map.put(name, hasRight);
		}
		return map;
	}
	

	/**
	 * 获取字段的权限
	 * 
	 * @param userId
	 * @param type
	 * @param bpmDataTemplate
	 * @return
	 */
	private Map<String, Boolean> getPermission( String displayField, Map<String, Set<String>> rightMap) {
		JSONArray jsonAry = JSONArray.fromObject(displayField);
		return getPermissionMap(jsonAry, rightMap);
	}
	
	/**
	 * 
	 * 获取Action的URl
	 * 
	 * @param params
	 * @return
	 */
	private Map<String, String> getActionUrl(Map<String, Object> params) {
		Map<String, String> map = new HashMap<String, String>();
		String __baseURL = (String) params.get("__baseURL");
		String __ctx = (String) params.get(BpmDataTemplate.PARAMS_KEY_CTX);
		String alias = params.get(BpmDataTemplate.PARAMS_KEY_ALIAS).toString();
		String formKey = params.get(BpmDataTemplate.PARAMS_KEY_FORM_KEY).toString();
		String boAlias = params.get(BpmDataTemplate.PARAMS_KEY_BOALIAS).toString();
		String defId = params.get(BpmDataTemplate.PARAMS_KEY_DEF_ID).toString();
		String toReplace = "/getDisplay_" + alias + ".ht";
		String addBaseURL = __baseURL.replace(toReplace, "/editDataPreview?formKey="+formKey+"&boAlias="+boAlias+"&defId="+defId);
		String editBaseURL = __baseURL.replace(toReplace, "/editDataPreview?formKey="+formKey+"&boAlias="+boAlias+"&defId="+defId);
		String deleteBaseURL = __baseURL.replace(toReplace,  "/boDel/"+boAlias).substring(__ctx.length());
		String detailBaseURL = __baseURL.replace(toReplace, "/editDataPreview?formKey="+formKey+"&boAlias="+boAlias);
		String exportBaseURL = __baseURL.replace(toReplace, "/bpmDataTemplateExport?formKey="+formKey+"&boAlias="+boAlias);
		String startFlowURL = __ctx + "/flow/instance/instanceToStart?defId="+defId;

		map.put(BpmDataTemplate.MANAGE_TYPE_ADD, addBaseURL);
		map.put(BpmDataTemplate.MANAGE_TYPE_EDIT, editBaseURL);
		map.put(BpmDataTemplate.MANAGE_TYPE_DEL, deleteBaseURL);
		map.put(BpmDataTemplate.MANAGE_TYPE_DETAIL, detailBaseURL);
		map.put(BpmDataTemplate.MANAGE_TYPE_EXPORT, exportBaseURL);
		map.put(BpmDataTemplate.MANAGE_TYPE_START_FLOW, startFlowURL);
		map.put(BpmDataTemplate.PARAMS_KEY_CTX, __ctx);
		return map;
	}

	@Override
	public Map<String,Object> getFormByFormKey(String formKey) {
		Map<String,Object> map = new HashMap<String, Object>();
		FormModel formModel = bpmFormDao.getMainByFormKey(formKey);
		if(formModel==null || StringUtil.isEmpty(formModel.getFormHtml())){
			map.put("result", "formEmpty");
			return map;
		}
		map.put("form", formModel);
		map.put("result", true);
		return map;
	}

	@Override
	public void boSave(com.alibaba.fastjson.JSONObject jsonObject,String boAlias) {
		BoData boData = BoUtil.transJSON(jsonObject.getJSONObject(boAlias));
		BoDataHandler handler= boInstanceFactory.getBySaveType(BodefConstants.SAVE_MODE_DB);
		BaseBoDef boDef = boDefService.getByName(boAlias);
		BaseBoEnt boEnt = (BaseBoEnt)boDef.getBoEnt();
		boData.setBoEnt(boEnt);
		boData.setBoDef(boDef);
		handler.save("", "", boData);
	}

	@Override
	public void boDel(String[] ids, String boAlias) {
		BoDataHandler handler= boInstanceFactory.getBySaveType(BodefConstants.SAVE_MODE_DB);
		handler.removeBoData(boAlias, ids);
	}

	@Override
	public BpmDataTemplate getTemplateByFormKey(String formKey) {
		return bpmDataTemplateDao.getByFormKey(formKey);
	}
	
	@Override
	public BpmDataTemplate getExportDisplay(String formKey) {
		BpmDataTemplate bpmDataTemplate = bpmDataTemplateDao.getByFormKey(formKey);
		if(BeanUtils.isNotEmpty(bpmDataTemplate)){
			JSONArray jsonAry = JSONArray.fromObject(bpmDataTemplate.getDisplayField());
			Map<String, Set<String>> curProfiles =  permssionCalc.getCurrentProfiles();
			if(!JsonUtil.isEmpty(jsonAry)){
				JSONArray newjsonAry = new JSONArray();
				for (Object obj : jsonAry) {
					JSONObject json = JSONObject.fromObject(obj);
					JSONArray rights = (JSONArray) json.get("right");
					Iterator<JSONObject> iterator = rights.iterator();
					boolean hasRight = false;
					while(iterator.hasNext()){
						JSONObject permission = iterator.next();
						hasRight = permssionCalc.hasRight(permission.toString(), curProfiles);
						break;
					}
					json.put("permission", hasRight);
					newjsonAry.add(json);
				}
				bpmDataTemplate.setDisplayField(newjsonAry.toString());
			}
		}
		return bpmDataTemplate;
	}

	@Override
	public String getShowSql(String filterField, String filterKey,
			String dsName, String tableName, Map<String, Object> param) {
		
		StringBuffer sb = new StringBuffer(" select *  from " + tableName);
		String sql = "";
		/*if(StringUtil.isEmpty(filterKey)){
			Map<String, Set<String>> curProfiles =  permssionCalc.getCurrentProfiles();
			List<Map<String,String>> filters = getFilterPermission(filterField,curProfiles); 
			if(BeanUtils.isNotEmpty(filters)){
				filterKey = filters.get(0).get("filterKey");
			}
		}*/
		JSONArray jsonArray = JSONArray.fromObject(filterField);
		JSONObject jsonObject = JsonUtil.arrayToObject(jsonArray, "key");
		jsonObject = jsonObject.getJSONObject(filterKey);
		if(BeanUtils.isEmpty(jsonObject)) return sb.toString();
		int type = JsonUtil.getInt(jsonObject, "type", 0);
		if (2==type) {// 过滤条件是SQL替代，直接返回
			return executeScript(jsonObject.getString("condition"), param);
		}else if(1==type){// 条件脚本
			String dbType = dynamicDatasource.getDbTypeByAlias(dsName);
			sql = sysCoreUtilService.getSql(JsonUtil.getString(jsonObject, "condition"), dbType);
		}else if(3==type){//追加SQL
			sql = executeScript(jsonObject.getString("condition"), param);
		}
		if(StringUtil.isNotEmpty(sql)){
			sb.append(" where 1=1 and " + sql);
		}
		return sb.toString();
	}
	
	/**
	 * 字符串的常量
	 * 
	 * @param script
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	private String executeScript(String script, Map<String, Object> param) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("param", param);
		vars.putAll(param);
		String str = groovyScriptEngine.executeString(replaceVar(script), vars);
		return str;
	}
	
	private String replaceVar(String str) {
		List<IContextVar> comVarList = (List<IContextVar>) AppUtil.getBean("queryViewComVarList");
		for (IContextVar c : comVarList) {
			str = str.replace("[" + c.getAlias() + "]", c.getValue());
		}
		return "return \"" + str + "\" ;";
	}
}
