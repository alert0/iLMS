package com.hotent.org.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：用户角色管理 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:28:34
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class UserRole extends AbstractModel<String>{
	
	/**
	* id_
	*/
	protected String id; 
	
	/**
	* role_id_
	*/
	protected String roleId; 
	
	/**
	* user_id_
	*/
	protected String userId; 
	/**
	 * 以下是扩展字段，用于关联显示。
	 */
	
	//用户名
	protected String fullname; 
	// 角色名称
	protected String roleName; 
	//角色别名
	protected  String alias;
	//账号
	protected String account=""; 
	
	/** 当前登录用户ID */
	private String curLoginUserId;
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
 
	public String getAlias() {
		return this.alias;
	}
	
	public void setId(String id) {
		this.id = id;
	}
 
	public String getId() {
		return this.id;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
 
	public String getFullname() {
		return this.fullname;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
 
	public String getRoleName() {
		return this.roleName;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * 返回 role_id_
	 * @return
	 */
	public String getRoleId() {
		return this.roleId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 user_id_
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("roleId", this.roleId) 
		.append("userId", this.userId) 
		.toString();
	}

	/**
	 * @return the curLoginUserId 当前登录用户ID
	 */
	public String getCurLoginUserId() {
		return curLoginUserId;
	}

	/**
	 * the curLoginUserId 当前登录用户ID to set
	 * @param curLoginUserId 
	 */
	public void setCurLoginUserId(String curLoginUserId) {
		this.curLoginUserId = curLoginUserId;
	}
	
	
}