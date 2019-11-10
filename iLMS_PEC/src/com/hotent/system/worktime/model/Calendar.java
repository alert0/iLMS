package com.hotent.system.worktime.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:系统日历 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-02-18 13:59:33
 */
public class Calendar extends AbstractModel<String> implements Cloneable{
	private static final long serialVersionUID = 1L;
	protected String  id; /*主键*/
	protected String  name; /*日历名称*/
	protected String  memo; /*描述*/
	protected char  isDefault; /*1=默认日历*/
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
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 日历名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setMemo(String memo) 
	{
		this.memo = memo;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getMemo() 
	{
		return this.memo;
	}
	public void setIsDefault(char isDefault) 
	{
		this.isDefault = isDefault;
	}
	/**
	 * 返回 1=默认日历
	 * @return
	 */
	public char getIsDefault() 
	{
		return this.isDefault;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("memo", this.memo) 
		.append("isDefault", this.isDefault) 
		.toString();
	}
}