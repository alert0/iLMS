package com.hotent.sys.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:通用服务调用参数表 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:heyifan
 * 创建时间:2014-08-25 14:40:40
 */
public class SysServiceParam extends AbstractModel<String>{
	protected String id; /*主键*/
	protected String setId = "0"; /*服务设置ID*/
	protected String name; /*参数名称*/
	protected String type; /*参数类型*/
	protected String desc; /*参数说明*/
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
	public void setSetId(String setId) 
	{
		this.setId = setId;
	}
	/**
	 * 返回 服务设置ID
	 * @return
	 */
	public String getSetId() 
	{
		return this.setId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 参数名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 参数类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
	/**
	 * 返回 参数说明
	 * @return
	 */
	public String getDesc() 
	{
		return this.desc;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("setId", this.setId) 
		.append("name", this.name) 
		.append("type", this.type) 
		.append("desc", this.desc) 
		.toString();
	}
}