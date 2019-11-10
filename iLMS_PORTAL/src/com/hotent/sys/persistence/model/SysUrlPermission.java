package com.hotent.sys.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:URL 拦截器 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-10-11 14:57:01
 */
public class SysUrlPermission extends AbstractModel<String>{
	protected String id; /*主键*/
	protected String descp; /*描述*/
	protected String url; /*拦截地址*/
	protected String params; /*拦截参数*/
	protected Short enable; /*是否启用；0：禁用，1：启用*/
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
	public void setDescp(String descp) 
	{
		this.descp = descp;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescp() 
	{
		return this.descp;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}
	/**
	 * 返回 拦截地址
	 * @return
	 */
	public String getUrl() 
	{
		return this.url;
	}
	public void setParams(String params) 
	{
		this.params = params;
	}
	/**
	 * 返回 拦截参数
	 * @return
	 */
	public String getParams() 
	{
		return this.params;
	}
	public void setEnable(Short enable) 
	{
		this.enable = enable;
	}
	/**
	 * 返回 是否启用；0：禁用，1：启用
	 * @return
	 */
	public Short getEnable() 
	{
		return this.enable;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("descp", this.descp) 
		.append("url", this.url) 
		.append("params", this.params) 
		.append("enable", this.enable) 
		.toString();
	}
}