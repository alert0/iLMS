package com.hotent.base.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：角色资源分配 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class ResRole extends AbstractModel<String>{
	
	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* 系统ID
	*/
	protected String systemId; 
	
	/**
	* 资源ID
	*/
	protected String resId; 
	
	/**
	* 角色ID
	*/
	protected String roleId; 
	
	/**
	 * 角色别名。
	 */
	protected String roleAlias;
	/**
	 * 资源url连接。
	 */
	protected String url;
	
	/**
	 * 资源别名。
	 */
	protected String resAlias;
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	/**
	 * 返回 系统ID
	 * @return
	 */
	public String getSystemId() {
		return this.systemId;
	}
	
	public void setResId(String resId) {
		this.resId = resId;
	}
	
	/**
	 * 返回 资源ID
	 * @return
	 */
	public String getResId() {
		return this.resId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * 返回 角色ID
	 * @return
	 */
	public String getRoleId() {
		return this.roleId;
	}
	public String getRoleAlias() {
		return roleAlias;
	}

	public void setRoleAlias(String roleAlias) {
		this.roleAlias = roleAlias;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResAlias() {
		return resAlias;
	}

	public void setResAlias(String resAlias) {
		this.resAlias = resAlias;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("systemId", this.systemId) 
		.append("resId", this.resId) 
		.append("roleId", this.roleId) 
		.toString();
	}
}