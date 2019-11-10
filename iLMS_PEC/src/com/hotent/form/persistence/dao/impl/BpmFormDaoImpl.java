package com.hotent.form.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.BpmFormDao;
import com.hotent.form.persistence.model.BpmForm;

@Repository
public class BpmFormDaoImpl extends MyBatisDaoImpl<String, BpmForm> implements BpmFormDao{
    @Override
    public String getNamespace() {
        return BpmForm.class.getName();
    }


	/**
	 * 根据表单key获取表单记录。
	 */
	public BpmForm getMainByFormKey(String formKey) {
		 return this.getUnique("getMainByFormKey", formKey);
	}
	
	/**
	 * 根据表单key获取表单记录。
	 */
	public List<BpmForm> getByFormKey(String formKey) {
		return this.getList("getByFormKey", buildMap("formKey", formKey));
	}

	public List<BpmForm> getByBoCodes(List<String> codes,String formType,QueryFilter filter) {
		MapBuilder mb= buildMapBuilder()
		.addParam("codes", codes)
		.addParam("formType", formType)
		.addParam("formKey", filter.getParams().get("formKey"))
		.addParam("name",filter.getParams().get("name"));
		
		return this.getByKey("getByBoCodes", mb.getParams(), filter.getPage());
	}
	
	public Integer getBpmFormCountsByFormKey(String formKey){
		return (Integer)this.getOne("getBpmFormCountsByFormKey", formKey);
	}
	
	/**
	 * 获取当前表单的最大版本。
	 * @param formKey
	 * @return
	 */
	public Integer getMaxVersionByFormKey(String formKey){
		Integer rtn=(Integer)this.getOne("getMaxVersionByFormKey", formKey);
		return rtn;
	}
	/**
	 * 设置默认版本
	 * @param formKey
	 * @param formId
	 */
	public void setDefaultVersion(String formKey,String formId){ 
		this.updateByKey("updNotDefaultByFormKey", formKey);
		this.updateByKey("updDefaultByFormId", formId);
	}

	@Override
	public List<String> getFormIdByBODefId(String BODefId) {
		return this.getList("getFormIdByBODefId", buildMap("BODefId", BODefId));
	}


	@Override
	public List<BpmForm> getByDefId(String defId) {
		return this.getByKey("getByDefId", defId);
	}


	
}