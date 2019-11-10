package com.hotent.service.ws;

import java.io.File;

import com.hotent.service.api.model.InvokeCmd;
import com.hotent.service.api.model.InvokeResult;
import com.hotent.service.ws.model.SoapService;

public interface WebServiceClient {
	public SoapService parse(String wsdlPath);
	
	public SoapService parse(String wsdlPath,String username,String password);
	
	public SoapService parse(File wsdlFile);
	
	public SoapService parse(File wsdlFile,String username,String password);
	
	public InvokeResult invoke(InvokeCmd invokeCmd);
}
