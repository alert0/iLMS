package com.hotent.form.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.BpmForm;

public interface BpmFormManager extends Manager<String, BpmForm>{
	/**
	 * 通过模板生成表单Html
	 * @param formId 表单主键
	 * @param main 主模板
	 * @param sub 复合字段模板
	 * @return
	 */
	
	String getHtml(String formId, String mainFieldTemplate, String subFieldListTemplate) throws Exception;
	/**
	 * 根据formKey 取得表单定义。
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
	 * @param filter 
	 * @return
	 */
	List<BpmForm> getByBoCodes(List<String> codes,String formType, QueryFilter filter);
	
	Integer getBpmFormCountsByFormKey(String formKey);
	
	void newVersion(String formId) throws Exception ;

	void setDefaultVersion(String formId, String formKey) ;
	
	void publish(String formId);
	
	
	
	List<BpmForm> getByDefId(String defId);
	
	void importForms(String unZipFilePath);
	
	void importByFormXml(String formXml) throws Exception;
	
	Map<String,String> exportForms(List<String> idList, boolean containBo);
	//生成字段的html
	String genByField(String defId, String attrId, String formType);
	
	void remove(String[] aryIds);
}