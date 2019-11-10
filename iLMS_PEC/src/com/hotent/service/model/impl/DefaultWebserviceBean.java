package com.hotent.service.model.impl;

import com.hotent.service.model.WebserviceBean;
import com.hotent.service.ws.model.SoapService;

/**
 * webservice服务类
 * @author heyifan
 * @version 创建时间: 2014-8-18
 */
public class DefaultWebserviceBean extends AbstractServiceBean implements WebserviceBean{
	private String namespace; 			/*webservice服务的名称空间*/
	private SoapService soapService;	/*webservice服务参数*/

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getType() {
		return "webservice";
	}

	public SoapService getSoapService() {
		return soapService;
	}

	public void setSoapService(SoapService soapService) {
		this.soapService = soapService;
	}
}