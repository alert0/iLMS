package com.hotent.sys.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:角色和资源关联表 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:miao
 * 创建时间:2014-05-05 14:35:57
 */
public class RoleResource extends AbstractModel<String>{
	protected String id; /*主键*/
	protected String roleId; /*角色ID*/
	protected String resId; /*资源ID*/
	protected String systemId; /*系统ID*/
	
	
	public RoleResource() {
	}
	
	public RoleResource(String roleId, String resId, String systemId) {
		this.roleId = roleId;
		this.resId = resId;
		this.systemId = systemId;
	}
	
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
	public void setRoleId(String roleId) 
	{
		this.roleId = roleId;
	}
	/**
	 * 返回 角色ID
	 * @return
	 */
	public String getRoleId() 
	{
		return this.roleId;
	}
	public void setResId(String resId) 
	{
		this.resId = resId;
	}
	/**
	 * 返回 资源ID
	 * @return
	 */
	public String getResId() 
	{
		return this.resId;
	}
	public void setSystemId(String systemId) 
	{
		this.systemId = systemId;
	}
	/**
	 * 返回 系统ID
	 * @return
	 */
	public String getSystemId() 
	{
		return this.systemId;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("roleId", this.roleId) 
		.append("resId", this.resId) 
		.append("systemId", this.systemId) 
		.toString();
	}
}