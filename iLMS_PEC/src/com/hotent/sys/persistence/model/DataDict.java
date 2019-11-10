package com.hotent.sys.persistence.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.api.model.Tree;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.persistence.model.SysResource;

/**
 * 对象功能:数据字典 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:miao
 * 创建时间:2014-06-20 13:55:53
 */
public class DataDict extends AbstractModel<String> implements Tree{
	protected String id; /*主键*/
	protected String typeId; /*类型ID*/
	protected String key; /*字典值代码,在同一个字典中值不能重复*/
	protected String name; /*字典值名称*/
	protected String parentId; /*父ID*/
	protected Integer sn; /*序号*/
	
	protected List<SysResource> children = new ArrayList<SysResource>();
	
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
	public void setSn(Integer sn) 
	{
		this.sn = sn;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public Integer getSn() 
	{
		return this.sn;
	}
	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}
	/**
	 * 返回 类型ID
	 * @return
	 */
	public String getTypeId() 
	{
		return this.typeId;
	}
	public void setKey(String key) 
	{
		this.key = key;
	}
	/**
	 * 返回 字典值代码,在同一个字典中值不能重复
	 * @return
	 */
	public String getKey() 
	{
		return this.key;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 字典值名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}
	/**
	 * 返回 父ID
	 * @return
	 */
	public String getParentId() 
	{
		return this.parentId;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("typeId", this.typeId) 
		.append("key", this.key) 
		.append("name", this.name) 
		.append("parentId", this.parentId)
		.append("sn", this.sn)
		.toString();
	}
	
	public List getChildren() {
		return children;
	}

	public void setChildren(List children){
		this.children = children;
	}
	
	public String getText() {
		return this.name;
	}
	
}