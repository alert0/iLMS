package com.hotent.service.parse;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.service.api.model.ServiceBean;
import com.hotent.service.api.parse.ServiceParse;
import com.hotent.service.model.impl.DefaultWebserviceBean;
import com.hotent.service.ws.WebServiceClient;
import com.hotent.service.ws.model.SoapService;

@Service
public class ServiceParseImpl implements ServiceParse{
	@Resource
	private WebServiceClient webServiceClient;
	
	public ServiceBean parse(String url) {
		if(StringUtil.isEmpty(url))return null;
		if(url.matches(".*\\?wsdl$")){
			DefaultWebserviceBean serviceBean = new DefaultWebserviceBean();
			SoapService soapService = webServiceClient.parse(url);
			serviceBean.setUrl(url);
			serviceBean.setName(soapService.getName());
			serviceBean.setNamespace(soapService.getNamespace());
			serviceBean.setSoapService(soapService);
			return serviceBean;
		}
		return null;
	}
}