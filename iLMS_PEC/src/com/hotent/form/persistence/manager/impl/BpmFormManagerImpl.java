package com.hotent.form.persistence.manager.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.engine.freemark.FreemarkEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoDefXml;
import com.hotent.form.persistence.dao.BpmDataTemplateDao;
import com.hotent.form.persistence.dao.BpmFormDao;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.dao.BpmFormFieldDao;
import com.hotent.form.persistence.dao.BpmFormRightDao;
import com.hotent.form.persistence.dao.FormBusSetDao;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.manager.BpmFormTemplateManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.BpmFormField;
import com.hotent.form.persistence.model.BpmFormImportXml;
import com.hotent.form.persistence.model.BpmFormTemplate;
import com.hotent.form.persistence.model.BpmFormXml;
import com.hotent.form.util.FreeMakerUtil;

@Service("bpmFormManager")
public class BpmFormManagerImpl extends AbstractManagerImpl<String, BpmForm> implements BpmFormManager
{
	@Resource
	BpmFormDao bpmFormDao;
	@Resource
	BpmFormFieldDao bpmFormFieldDao;
	@Resource
	FreemarkEngine freemarkEngine;
	@Resource
	BpmFormTemplateManager bpmFormTemplateManager;
	@Resource
	BpmFormDefDao bpmFormDefDao;
	@Resource
	BoDefService boDefService;
	@Resource
	FormBusSetDao formBusSetDao;
	@Resource
	BpmFormRightDao bpmFormRightDao;
	@Resource
	BpmDataTemplateDao bpmDataTemplateDao;
	
	
	@Override
	protected Dao<String, BpmForm> getDao()
	{
		return bpmFormDao;
	}

	



	@Override
	public List<BpmForm> getByFormKey(String formKey)
	{
		return bpmFormDao.getByFormKey(formKey);
	}

	@Override
	public BpmForm getMainByFormKey(String formKey)
	{
		return bpmFormDao.getMainByFormKey(formKey);
	}

	@Override
	public List<BpmForm> getByBoCodes(List<String> codes,String formType, QueryFilter filter)
	{
		return bpmFormDao.getByBoCodes(codes,formType,filter);
	}

	public String getHtml(String defId, String tableNames, String templateAliasList) throws Exception
	{
		String [] tableNameArray = tableNames.split(",");
		String [] templateAliasArray = templateAliasList.split(",");
		
		BpmFormDef bpmFormdef = bpmFormDefDao.get(defId);
		JSONArray fieldList = bpmFormdef.getFieldList();
		
		String html="";
		
		for (int i = 0; i < tableNameArray.length; i++) {
			String tableName =tableNameArray[i];
		
			JSONObject tableField = null ; //获取表对象
			for (int j = 0; j < fieldList.size(); j++) {
				JSONObject table =  fieldList.getJSONObject(j);
				if(tableName.equals( table.getString("name"))){
					tableField =table;
					break;
				}
			}
			//当前模板
			BpmFormTemplate template = bpmFormTemplateManager.getByTemplateAlias(templateAliasArray[i]);
			String macroTemplate = bpmFormTemplateManager.getByTemplateAlias(template.getMacrotemplateAlias()).getHtml();
			if(tableField==null ||	template==null) continue;
			
			boolean isSub = tableField.getString("type").equals("sub");
			JSONArray fieldLists = new JSONArray();
			if(isSub){
				fieldLists = tableField.getJSONArray("children");
			}else{
				JSONObject expand =JSONObject.parseObject(bpmFormdef.getExpand());
				JSONArray separators = new JSONArray();
				if(BeanUtils.isNotEmpty(expand)){
					separators = expand.getJSONArray("separators");
				}
				getFieldList(fieldLists,tableField.getJSONArray("children"),separators);
			}
			FreeMakerUtil freeMakerUtil = new FreeMakerUtil();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("table", tableField);
			map.put("isSub", isSub);
			map.put("mainGroup", "基本信息");
			map.put("formDesc", tableField.getString("desc"));
			map.put("fieldList",fieldLists);
			map.put("ganged", bpmFormdef.getGanged());
			map.put("util", freeMakerUtil);
			
			html += freemarkEngine.parseByStringTemplate(macroTemplate+template.getHtml(), map);  
		}

		return html;
	}
	
	
	private void getFieldList(JSONArray fieldLists,JSONArray fields,JSONArray separators){
		if(BeanUtils.isNotEmpty(fields)){
			JSONArray basefields = new JSONArray();
			JSONObject mainSep = new JSONObject();
			if(BeanUtils.isNotEmpty(separators)){
				for (int i = 0; i < separators.size(); i++) {
					separators.getJSONObject(i).put("fields", new JSONArray());
					if(StringUtil.isZeroEmpty(separators.getJSONObject(i).getString("key"))){
						mainSep = separators.getJSONObject(i);
					}
				}
			}
			for (int i = 0; i < fields.size(); i++) {
				JSONObject obj = fields.getJSONObject(i);
				String separator = obj.getString("separator");
				if(StringUtil.isZeroEmpty(separator)){
					basefields.add(obj);
				}else{
					for (int m = 0; m< separators.size(); m++) {
						JSONObject sepObj = separators.getJSONObject(m);
						if(separator.equals(sepObj.getString("key"))){
							sepObj.getJSONArray("fields").add(obj);
						}
					}
				}
			}
			mainSep.put("fields", basefields);
			fieldLists.add(mainSep);
			for (int m = 0; m< separators.size(); m++) {
				JSONObject sepObj = separators.getJSONObject(m);
				if(BeanUtils.isNotEmpty(sepObj.getJSONArray("fields"))&&!sepObj.getString("key").equals("0")){
					fieldLists.add(sepObj);
				}
			}
		}
	}

	
	
	public Integer getBpmFormCountsByFormKey(String formKey)
	{
		return bpmFormDao.getBpmFormCountsByFormKey(formKey);
	}

	/**
	 * 根据表单定义id创建新的表单版本。 表单定义ID
	 * 
	 * @param formId
	 *            自定义表单Id
	 * @throws Exception
	 */
	public void newVersion(String formId) throws Exception
	{
		BpmForm bpmform = this.get(formId);
		createNewVersionForm(bpmform);
	}
	
	
	// 拷贝表单权限
	private void createNewVersionForm(BpmForm bpmform){
		Integer rtn = bpmFormDao.getMaxVersionByFormKey(bpmform.getFormKey());
		String newFormId = UniqueIdUtil.getSuid();
		// 创建新的版本
		BpmForm newBpmForm = (BpmForm) bpmform.clone();
		newBpmForm.setFormId(newFormId);
		newBpmForm.setIsMain('N');
		newBpmForm.setStatus(BpmForm.STATUS_DRAFT);
		newBpmForm.setVersion(rtn + 1);
		
		this.create(newBpmForm);
		publish(newFormId);
		setDefaultVersion(newFormId,newBpmForm.getFormKey());
	}
	
	

	

	/**
	 * 设为默认版本。
	 * 
	 * @param formId
	 *            自定义表单Id
	 * @param formKey
	 *            在表单版本使用
	 */
	public void setDefaultVersion(String formId, String formKey)
	{
		bpmFormDao.setDefaultVersion(formKey, formId);
	}

	/**
	 * 发布
	 * 
	 * @param formId
	 *            自定义表单Id
	 * @throws Exception
	 */
	public void publish(String formId)
	{
		// 设为已发布
		BpmForm formDef = bpmFormDao.get(formId);
		formDef.setStatus(BpmForm.STATUS_DEPLOY);
		bpmFormDao.update(formDef);
	}
	

	@Override
	public List<BpmForm> getByDefId(String defId) {
		return bpmFormDao.getByDefId(defId);
	}




	/**
	 * 导出,是否 导出表单
	 */
	@Override
	public Map<String,String> exportForms(List<String> idList,  boolean containBo){
			BoDefXml bodefXml =  new BoDefXml();
			BpmFormImportXml formImport = new BpmFormImportXml();
			Map<String, String> map = new HashMap<String, String>();
			//取出表单
			for(String formId : idList){
				BpmFormXml formXml =  new BpmFormXml();
				
				BpmForm form = this.get(formId);
				BpmFormDef formDef = bpmFormDefDao.get(form.getDefId());
				List<String> boCodes = bpmFormDefDao.getBOCodeByFormId(form.getDefId());
				
				//表单 和表单定义
				formXml.setBpmForm(form);
				formXml.setBpmFormDef(formDef);
				formXml.setBoCodes(boCodes);
				if(containBo){
					List<String> boIds= bpmFormDefDao.getBODefIdByFormId(form.getDefId());
					for (String boId : boIds) {
						BaseBoDef bodef  = boDefService.getByDefId(boId);
						bodefXml.addBodef(bodef);
					}
				}
				
				formImport.addFormXml(formXml);
			}
			
			try {
				map.put("form.xml", JAXBUtil.marshall(formImport, BpmFormImportXml.class));
				if(containBo)map.put("bo.xml", JAXBUtil.marshall(bodefXml, BoDefXml.class)); 
			} catch (JAXBException e) {
				e.printStackTrace();
				throw new RuntimeException("导出表单失败"+e.getMessage(),e);
			}
		return map;
	}

	@Override @SuppressWarnings("unchecked")
	public void importForms(String unZipFilePath){
			String formXmlStr = FileUtil.readFile(unZipFilePath + File.separator + "form.xml");
			try {
				//bo导入
				String boXmlStr = FileUtil.readFile(unZipFilePath + File.separator + "bo.xml");
				if(StringUtil.isNotEmpty(boXmlStr)){
					BoDefXml boXml = (BoDefXml) JAXBUtil.unmarshall(boXmlStr, BoDefXml.class);
					List<BaseBoDef> boDefs =boXml.getDefList();
					boDefService.importBoDef(boDefs);
					
					//表单导入
					importByFormXml(formXmlStr);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("表单导入失败"+e.getMessage(),e);
			}
			
			
	}
	
	public void importByFormXml(String xml) throws Exception{
		BpmFormImportXml formImportXml =(BpmFormImportXml)JAXBUtil.unmarshall(xml, BpmFormImportXml.class);
		List<BpmFormXml>formXmlList = formImportXml.getFormXmlList();
		
		for (BpmFormXml formXml : formXmlList) {
			BpmFormDef formDef = formXml.getBpmFormDef();
			List<String> boCodes = formXml.getBoCodes();
			BpmForm form = formXml.getBpmForm();
			
			//更新bpmFormDef
			{
				BpmFormDef oldFormDef = bpmFormDefDao.getByKey(formDef.getKey());
				
				//重新构建bo关系json
				Map<String,BaseBoDef> boDefMap = new HashMap<String, BaseBoDef>();
				JSONObject expand =JSONObject.parseObject(formDef.getExpand());
				JSONArray boDefList = new JSONArray();
				for (String boCode : boCodes){
					BaseBoDef boDef = boDefService.getByAlias(boCode);
					boDefMap.put(boCode,boDef);
					
					JSONObject boDefJson = new JSONObject();
					boDefJson.put("id", boDef.getId());
					boDefJson.put("desc", boDef.getDescription());
					boDefList.add(boDefJson);
					
				}
				expand.put("boDefList", boDefList);
				formDef.setExpand(expand.toJSONString());
				
				handelFormFields(formDef,boDefMap);
				
				if(oldFormDef != null){
					formDef.setId(oldFormDef.getId());
					bpmFormDefDao.update(formDef);
					ThreadMsgUtil.addMsg("表单元数据定义 ”"+formDef.getName()+"“["+formDef.getKey()+"] 已经存在，更新成功！");
				}else{
					formDef.setId(UniqueIdUtil.getSuid());
					bpmFormDefDao.create(formDef);
					ThreadMsgUtil.addMsg("表单元数据定义 “"+formDef.getName()+"”["+formDef.getKey()+"] 添加成功！");
				}
				
				bpmFormDefDao.deleteBpmFormBo(formDef.getId());
				for(Object obj :boDefList){
					JSONObject boDefJson =(JSONObject  )obj;
					//创建表单和BO的关联关系。
					bpmFormDefDao.createBpmFormBo(boDefJson.getString("id"), formDef.getId());
				}
				
			}
			/**
			 * 处理表单列表
			 * 存在则发布新版本。否则创建。设置默认版本并发布。
			 */
			{
				BpmForm oldForm = getMainByFormKey(form.getFormKey());
				
				form.setDefId(formDef.getId());
				form.setStatus(BpmForm.STATUS_DEPLOY);
				if(oldForm != null){
					form.setId(oldForm.getId());
					createNewVersionForm(form);
					ThreadMsgUtil.addMsg("表单”"+form.getName()+"”["+form.getFormKey()+"] 已经存在，更新并发布新版本成功！");
				}else {
					form.setVersion(1);
					form.setIsMain('Y');
					create(form);
					ThreadMsgUtil.addMsg("表单“"+form.getName()+"“["+form.getFormKey()+"] 保存并发布成功！");
				}
			}
			
			//检测表单源数据定义对应的bpmFormField是否存在，如果不存在则创建
			try {
				List<BpmFormField> fields = bpmFormFieldDao.getByFormId(formDef.getId());
				if(BeanUtils.isEmpty(fields)){
					createFields(formDef);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void createFields(BpmFormDef bpmFormDef) {
		JSONObject expand =JSONObject.parseObject(bpmFormDef.getExpand());
		JSONArray tables = expand.getJSONArray("fields");
		for (Object t  : tables) {
			JSONObject table =(JSONObject)t; 
			JSONArray fields = table.getJSONArray("children");
			for (int i = 0; i < fields.size(); i++) {
				JSONObject field =fields.getJSONObject(i);
				BpmFormField formField = (BpmFormField) JSON.parseObject(field.toString(), BpmFormField.class);
				
				formField.setId(UniqueIdUtil.getSuid());
				formField.setFormId(bpmFormDef.getId());
				bpmFormFieldDao.create(formField);
			}
		}
		
	}
	
	// 重新设置 field 的boDefId entId
	private void handelFormFields(BpmFormDef formDef, Map<String, BaseBoDef> boDefMap) {
		JSONObject expand =JSONObject.parseObject(formDef.getExpand());
		JSONArray tables = expand.getJSONArray("fields");
		for (Object t  : tables) {
			JSONObject table =(JSONObject)t; 
			String tableName = table.getString("name");
			String boCode = table.getString("path").split("\\.")[0];
			
			BaseBoDef boDef =boDefMap.get(boCode);
			BaseBoEnt ent = boDefService.getEntByName(tableName);
			if(boDef== null || ent ==null)throw new RuntimeException("表定义字段数据出现异常！");
			
			table.put("entId", ent.getId());
			table.put("boDefId", boDef.getId());
			
			JSONArray fields = table.getJSONArray("children");
			for (int i = 0; i < fields.size(); i++) {
				JSONObject field =fields.getJSONObject(i);
				field.put("entId", ent.getId());
				field.put("boDefId", boDef.getId());
			}
		}
		
		formDef.setExpand(expand.toJSONString());
	}





	@Override
	public String genByField(String defId, String attrId,String formType) {
		BpmFormDef def = bpmFormDefDao.get(defId);
		JSONObject expand =JSONObject.parseObject(def.getExpand());
		JSONArray tables = expand.getJSONArray("fields");
		JSONObject field = new JSONObject();
		Boolean isSub = false;
		
		for (Object t  : tables) {
			JSONObject table =(JSONObject)t; 
			JSONArray fields = table.getJSONArray("children");
			for (int i = 0; i < fields.size(); i++) {
				JSONObject f =fields.getJSONObject(i);
				if(f.getString("boAttrId").equals(attrId)){
					field = f;
					isSub = !table.getString("type").equals("main");
				}
			}
		}
		
		String macroTemplate = bpmFormTemplateManager.getByTemplateAlias("mobile".equals(formType)? "mobileFieldMacro" : "fieldControl").getHtml();
		
		FreeMakerUtil freeMakerUtil = new FreeMakerUtil();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isSub", isSub);
		map.put("field", field);
		map.put("util", freeMakerUtil);
		String template = "<@input field=field isSub=isSub/>" +macroTemplate;
		String html = "";
		try {
			html = freemarkEngine.parseByStringTemplate(template, map);
		} catch (Exception e) {
			html="出现异常！";
		}  	
		return html;
	}
	
	/**
	 * 删除表单。
	 * <pre>
	 * 1.删除表单定义。
	 * 2.删除业务表单配置。
	 * 3.删除表单权限。
	 * </pre>
	 */
	@Override
	public void remove(String defId){
		BpmForm bpmForm =bpmFormDao.get(defId);
		String formKey=bpmForm.getFormKey();
		
		bpmFormRightDao.removeByFormKey(formKey);
		formBusSetDao.removeByFormKey(formKey);
		
		super.remove(defId);
	}


	@Override
	public void remove(String[] aryIds) {
		for(String id:aryIds){
			BpmForm bpmForm = bpmFormDao.get(id);
			if(BeanUtils.isNotEmpty(bpmForm)){
				String formKey = bpmForm.getFormKey();
				if("Y".equals(String.valueOf(bpmForm.getIsMain()))){
					List<BpmForm> list = bpmFormDao.getByFormKey(formKey);
					for(BpmForm bpmform:list){
						bpmFormDao.remove(bpmform.getId());
					}
					//删除表单后，删除表单的业务数据模板
					bpmDataTemplateDao.removeByFormKey(formKey);
				}else{
					bpmFormDao.remove(id);
				}
			}
		}
	}
	

}
