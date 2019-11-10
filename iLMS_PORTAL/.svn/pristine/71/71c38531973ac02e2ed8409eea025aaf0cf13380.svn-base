package com.hotent.mini.ext.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
@WebService(serviceName="serviceTest", endpointInterface = "com.hotent.mini.ext.service.ISerivceTest", 
targetNamespace = "http://impl.webservice.platform.hotent.com/")
public interface ISerivceTest {
	
	String sayHello(String name);
}
