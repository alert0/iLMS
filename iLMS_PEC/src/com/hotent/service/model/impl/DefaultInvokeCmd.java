package com.hotent.service.model.impl;

import com.hotent.service.api.model.InvokeCmd;

/**
 * 服务调用的参数对象
 * @author heyifan
 * @version 创建时间: 2014-8-18
 */
public class DefaultInvokeCmd implements InvokeCmd{
	private String address;					/*调用地址*/
	private String username;				/*服务的用户名*/
	private String password;				/*服务的用户密码*/
	private String operatorName;			/*操作方法名*/
	private String operatorNamespace;		/*操作的名称空间*/
	private String jsonParam;				/*操作的调用参数json*/
	private String xmlParam;				/*操作的调用参数xml*/
	private String type = "webservice"; 	/*调用的服务类型*/
	private Boolean needPrefix = false;		/*是否添加xmlns*/
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorNamespace() {
		return operatorNamespace;
	}
	public void setOperatorNamespace(String operatorNamespace) {
		this.operatorNamespace = operatorNamespace;
	}
	public String getJsonParam() {
		return jsonParam;
	}
	public void setJsonParam(String jsonParam) {
		this.jsonParam = jsonParam;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getNeedPrefix() {
		return needPrefix;
	}
	public void setNeedPrefix(Boolean needPrefix) {
		this.needPrefix = needPrefix;
	}
	public String getXmlParam() {
		return xmlParam;
	}
	public void setXmlParam(String xmlParam) {
		this.xmlParam = xmlParam;
	}
}