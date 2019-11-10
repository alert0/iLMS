package com.hotent.bpmx.core.engine.form;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.form.api.model.FormType;

/**
 * 返回表单服务。
 * @author ray
 *
 */
public class BpmFormFactory {

	/**
	 * 根据formtype 获取对应的service。
	 * @param formType
	 * @return
	 */
	public static BpmFormService getFormService(FormType formType){
		BpmFormService service=null;
		if(FormType.PC.equals(formType)){
			service=  (BpmFormService) AppUtil.getBean("defaultBpmFormService");
		}
		else if(FormType.MOBILE.equals(formType)){
			service=  (BpmFormService) AppUtil.getBean("mobileFormService");
		}
		
		return service;
		
	}
}
