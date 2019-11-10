package com.hotent.form.api.service;

import java.util.Set;

import com.hotent.form.api.model.FormModel;

public interface FormService {

	/**
	 * 根据formkey获取表单。
	 * @param formKey
	 * @return FormModel
	 */
	FormModel getByFormKey(String formKey);
	
	/**
	 * 根据表单ID取得表单对象。
	 * @param formId
	 * @return FormModel
	 */
	FormModel getByFormId(String formId);
	
	/**
	 * 根据formKey 导出表单
	 * @param formKeys
	 * @return
	 */
	String getFormExportXml(Set<String> formKeys);

	void importForm(String formXmlStr);
}
