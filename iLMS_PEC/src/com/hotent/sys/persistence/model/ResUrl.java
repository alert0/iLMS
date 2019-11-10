package com.hotent.sys.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:@名称：SYS_RES_RESOURCEURL【资源URL】 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:miao
 * 创建时间:2014-05-27 10:36:50
 */
public class ResUrl extends AbstractModel<String>{
	protected String id; /*ID_*/
	protected String resId; /*RES_ID_*/
	protected String name; /*NAME_*/
	protected String url; /*URL_*/
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ID_
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setResId(String resId) 
	{
		this.resId = resId;
	}
	/**
	 * 返回 RES_ID_
	 * @return
	 */
	public String getResId() 
	{
		return this.resId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 NAME_
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}
	/**
	 * 返回 URL_
	 * @return
	 */
	public String getUrl() 
	{
		return this.url;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("resId", this.resId) 
		.append("name", this.name) 
		.append("url", this.url) 
		.toString();
	}
}