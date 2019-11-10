package com.hotent.service.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.service.api.handler.ServiceClient;
import com.hotent.service.api.model.InvokeCmd;
import com.hotent.service.api.model.InvokeResult;
import com.hotent.service.ws.WebServiceClient;

@Service
public class ServiceClientImpl implements ServiceClient{
	@Resource
	private WebServiceClient webServiceClient;
	
	public InvokeResult invoke(InvokeCmd invokeCmd) {
		if("webservice".equals(invokeCmd.getType())){
			return webServiceClient.invoke(invokeCmd);
		}
		return null;
	}
}