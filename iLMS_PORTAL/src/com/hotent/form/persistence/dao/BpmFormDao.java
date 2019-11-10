package com.hotent.form.persistence.dao;

import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.form.persistence.model.BpmForm;

public interface BpmFormDao extends Dao<String, BpmForm> {
	
	/**
	 * 根据表单key获取表单对象数据。
	 * @param formKey
	 * @return BpmForm
	 */
	BpmForm getMainByFormKey(String formKey);

	/**
	 * 根据formKey 取得表单定义。
	 * @param formKey
	 * @return List<BpmForm>
	 */
	List<BpmForm> getByFormKey(String formKey);
	/**
	 * 通过bocode获取与bo绑定的表单
	 * @param codes
	 * @return
	 */
	List<BpmForm> getByBoCodes(List<String> codes,String formType, QueryFilter filter);
	
	Integer getBpmFormCountsByFormKey(String formKey);
	Integer getMaxVersionByFormKey(String formKey);
	void setDefaultVersion(String formId, String formKey) ;

	List<String> getFormIdByBODefId(String bOdefId);

	List<BpmForm> getByDefId(String defId);
	
	
}
