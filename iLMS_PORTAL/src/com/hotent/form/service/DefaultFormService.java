package com.hotent.form.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.api.service.FormService;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.model.BpmForm;

public class DefaultFormService implements FormService {
	
	@Resource
	private BpmFormManager bpmFormManager;

	public FormModel getByFormKey(String formKey) {
		BpmForm form = bpmFormManager.getMainByFormKey(formKey);
		if(BeanUtils.isNotEmpty(form)) return form;
		
		return this.getByFormId(formKey);
	}

	
	@Override
	public FormModel getByFormId(String formId) {
		return bpmFormManager.get(formId);
	}


	@Override
	public String getFormExportXml(Set<String> formKeys) {
		List<String> id = new ArrayList<String>();
		for (String formKey : formKeys) {
			BpmForm form = bpmFormManager.getMainByFormKey(formKey);
			id.add(form.getId());
		}
		Map<String,String> map = bpmFormManager.exportForms(id, false); 
		
		return map.get("form.xml");
	}


	@Override
	public void importForm(String formXmlStr) {
		try {
			bpmFormManager.importByFormXml(formXmlStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("导入表单失败"+e.getMessage(),e);
		}
	}

}
