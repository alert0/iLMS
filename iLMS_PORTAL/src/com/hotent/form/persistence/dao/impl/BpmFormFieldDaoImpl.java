package com.hotent.form.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.BpmFormFieldDao;
import com.hotent.form.persistence.model.BpmFormField;


@Repository
public class BpmFormFieldDaoImpl extends MyBatisDaoImpl<String, BpmFormField> implements BpmFormFieldDao{
    @Override
    public String getNamespace() {
        return BpmFormField.class.getName();
    }
	/**
	 * 根据外键获取子表明细列表
	 * @param formId
	 * @return
	 */
	public List<BpmFormField> getByFormId(String formId) {
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("formId", formId);
		return this.getByKey("getByFormId", params);
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param formId
	 * @return
	 */
	public void delByMainId(String formId) {
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("formId", formId);
		this.deleteByKey("delByMainId", params);
	}
	
	public List<BpmFormField> getOnlyByFormId(String formId) {
		return this.getByKey("getOnlyByFormId", buildMap("formId", formId));
	}
	
	
	public List<BpmFormField> getByGroupId(String groupId) {
		return this.getByKey("getByGroupId", buildMap("groupId", groupId));
	}
	@Override
	public List<BpmFormField> getExtByFormId(String formId) {
		List<BpmFormField> fields= this.getByKey("getExtByFormId", buildMap("formId", formId));
		return fields;
	}
	
	public void removeByAttrId(String attrId) {
		this.deleteByKey("removeByAttrId", attrId);
	}
	
	@Override
	public List<BpmFormField> getByboDefId(String boDefId) {
		List<BpmFormField> fields= this.getByKey("getByboDefId", buildMap("boDefId", boDefId));
		return fields;
	}
	@Override
	public List<BpmFormField> getByFormIdAndBoDefId(String formId,
			String boDefId) {
		Map<String,Object> params = buildMap("boDefId", boDefId);
		params.put("formId", formId);
		List<BpmFormField> fields= this.getByKey("getByFormIdAndBoDefId", params);
		return fields;
	}
	
}

