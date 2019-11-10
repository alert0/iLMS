package com.hotent.sys.persistence.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:通用服务调用设置表 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:heyifan
 * 创建时间:2014-08-25 14:40:40
 */
public class SysServiceSet extends AbstractModel<String>{
	protected String id; /*主键*/
	protected String alias; /*别名*/
	protected String name;/*名字*/
	protected String url; /*wsdl地址*/
	protected String address; /*接口调用地址*/
	protected String methodName; /*调用的方法名称*/
	protected String namespace; /*名称空间*/
	protected char soapAction; /*构建soap的模式*/
	protected String inputSet; /*输入参数设定*/
	protected List<SysServiceParam> sysServiceParamList=new ArrayList<SysServiceParam>(); /*通用服务调用参数表列表*/
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setAlias(String alias) 
	{
		this.alias = alias;
	}
	/**
	 * 返回 别名
	 * @return
	 */
	public String getAlias() 
	{
		return this.alias;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}
	/**
	 * 返回 wsdl地址
	 * @return
	 */
	public String getUrl() 
	{
		return this.url;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 接口调用地址
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setMethodName(String methodName) 
	{
		this.methodName = methodName;
	}
	/**
	 * 返回 调用的方法名称
	 * @return
	 */
	public String getMethodName() 
	{
		return this.methodName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNamespace(String namespace) 
	{
		this.namespace = namespace;
	}
	/**
	 * 返回 名称空间
	 * @return
	 */
	public String getNamespace() 
	{
		return this.namespace;
	}
	public void setSoapAction(char soapAction) 
	{
		this.soapAction = soapAction;
	}
	/**
	 * 返回 构建soap的模式
	 * @return
	 */
	public char getSoapAction() 
	{
		return this.soapAction;
	}
	public void setInputSet(String inputSet) 
	{
		this.inputSet = inputSet;
	}
	/**
	 * 返回 输入参数设定
	 * @return
	 */
	public String getInputSet() 
	{
		return this.inputSet;
	}
	public void setSysServiceParamList(List<SysServiceParam> sysServiceParamList) 
	{
		this.sysServiceParamList = sysServiceParamList;
	}
	/**
	 * 返回 通用服务调用参数表列表
	 * @return
	 */
	public List<SysServiceParam> getSysServiceParamList() 
	{
		return this.sysServiceParamList;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("alias", this.alias) 
		.append("url", this.url) 
		.append("address", this.address) 
		.append("methodName", this.methodName) 
		.append("namespace", this.namespace) 
		.append("soapAction", this.soapAction) 
		.append("inputSet", this.inputSet) 
		.toString();
	}
}