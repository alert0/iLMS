package com.hotent.form.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.dao.BpmFormFieldDao;
import com.hotent.form.persistence.manager.BpmFormDefManager;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.BpmFormField;

/**
 * 
 * <pre> 
 * 描述：bpm_form_def 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:苗继方
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-03-17 10:10:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmFormDefManager")
public class BpmFormDefManagerImpl extends AbstractManagerImpl<String, BpmFormDef> implements BpmFormDefManager{
	@Resource
	BpmFormDefDao bpmFormDefDao;
	@Resource
	BpmFormFieldDao bpmFormFieldDao;
	@Resource
	BpmFormManager bpmFormManager;
	
	
	
	
	@Override
	protected Dao<String, BpmFormDef> getDao() {
		return bpmFormDefDao;
	}
	
	
	
	
	
	/**
	 * 创建实体包含子表实体
	 */
	public void create(BpmFormDef bpmFormDef)
	{
		super.create(bpmFormDef);
		
		createFields(bpmFormDef);
	
		updateBpmFormBo(bpmFormDef);
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

	/**
	 * 更新表单和BO的关联记录
	 * @param formDef
	 */
	private void updateBpmFormBo(BpmFormDef formDef)
	{
		String formId = formDef.getId();
		
		JSONObject expand =JSONObject.parseObject(formDef.getExpand());
		JSONArray boDefs = expand.getJSONArray("boDefList");
		for (int i = 0; i < boDefs.size(); i++)
		{
			String boDefId = boDefs.getJSONObject(i).getString("id");
			bpmFormDefDao.createBpmFormBo(boDefId, formId);
		}
	}


	/**
	 * 获取实体,包含 字段信息，分组信息，主表表单
	 */
	public BpmFormDef get(String entityId)
	{
		BpmFormDef bpmFormDef = super.get(entityId);
		return bpmFormDef;
	}
	

	@Override
	public BpmFormDef getByKey(String formKey) {
		BpmFormDef  def =bpmFormDefDao.getByKey(formKey);
		return def;
	}
	
	/**
	 *  删除记录包含子表记录
	 */
	public void remove(String entityId)
	{
		List<BpmForm> forms = bpmFormManager.getByDefId(entityId);
		for (BpmForm form : forms) {
			bpmFormManager.remove(form.getId());
		}
		
		bpmFormDefDao.deleteBpmFormBo(entityId);
		bpmFormFieldDao.delByMainId(entityId);
		
		super.remove(entityId);
	}
	
	/**
	 * 更新实体同时更新子表记录
	 */
	public void update(BpmFormDef bpmFormDef)
	{
		super.update(bpmFormDef);
		String formDefId =bpmFormDef.getId();
		
		bpmFormFieldDao.delByMainId(formDefId);
		createFields(bpmFormDef);
	//	bpmFormGroupDao.delByFormId(defId);
		bpmFormDefDao.deleteBpmFormBo(formDefId);
		updateBpmFormBo(bpmFormDef);
	}
	
	

	@Override
	public List<BpmFormDef> getByBODefId(String BODefId) {
		return bpmFormDefDao.getByBODefId(BODefId);
	}


	@Override
	public void updateOpinionConf(String id, String opinionJson) {
		bpmFormDefDao.updateOpinionConf(id,opinionJson);
		
	}





	@Override
	public String getMetaKeyByFormKey(String formKey) {
		return bpmFormDefDao.getMetaKeyByFormKey(formKey);
	}





	@Override
	public List<String> getBOCodeByFormId(String formDefId) {
		return	bpmFormDefDao.getBOCodeByFormId(formDefId);
	}

}
