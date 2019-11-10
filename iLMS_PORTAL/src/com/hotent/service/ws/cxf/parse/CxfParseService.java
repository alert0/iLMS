package com.hotent.service.ws.cxf.parse;

import java.io.File;

import org.apache.cxf.endpoint.Client;

import com.hotent.service.ws.model.SoapService;

public interface CxfParseService {
	public SoapService parse(String wsdlPath);

	public SoapService parse(Client client);

	public SoapService parse(File wsdlFile);

	public Client createClient(String wsdlPath);

	public Client createClient(File wsdlFile);
}
