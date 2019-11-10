package com.hotent.org.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：分级组织管理 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-20 14:30:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class OrgAuth extends AbstractModel<String>{
	
	/**
	* ID_
	*/
	protected String id; 
	
	/**
	* 分级组织管理员id
	*/
	protected String userId; 
	
	/**
	* 对应管理组织id
	*/
	protected String orgId; 
	
	/**
	* 对应维度id
	*/
	protected String demId; 
	
	/**
	* 组织管理权限
	*/
	protected String orgPerms; 
	
	/**
	* 用户管理权限
	*/
	protected String userPerms; 
	
	/**
	* 岗位管理权限
	*/
	protected String posPerms; 
	
	/**
	* 分级管理员权限
	*/
	protected String orgauthPerms; 
	/**
	 * 布局管理权限
	 */
	protected String layoutPerms; 
	
	
	/**
	 * 分级管理员名称
	 */
	protected String userName;
	
	/**
	 * 对应组织
	 */
	protected String orgName;
	
	/**
	 * 对应维度名称
	 */
	protected String demName;
	
	/**
	 * 组织路径
	 */
	protected String orgPath;
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 ID_
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDemName() {
		return demName;
	}

	public void setDemName(String demName) {
		this.demName = demName;
	}

	/**
	 * 返回 分级组织管理员id
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/**
	 * 返回 对应管理组织id
	 * @return
	 */
	public String getOrgId() {
		return this.orgId;
	}
	
	public void setDemId(String demId) {
		this.demId = demId;
	}
	
	/**
	 * 返回 对应维度id
	 * @return
	 */
	public String getDemId() {
		return this.demId;
	}
	
	public void setOrgPerms(String orgPerms) {
		this.orgPerms = orgPerms;
	}
	
	/**
	 * 返回 组织管理权限
	 * @return
	 */
	public String getOrgPerms() {
		return this.orgPerms;
	}
	
	public void setUserPerms(String userPerms) {
		this.userPerms = userPerms;
	}
	
	/**
	 * 返回 用户管理权限
	 * @return
	 */
	public String getUserPerms() {
		return this.userPerms;
	}
	
	public void setPosPerms(String posPerms) {
		this.posPerms = posPerms;
	}
	
	/**
	 * 返回 岗位管理权限
	 * @return
	 */
	public String getPosPerms() {
		return this.posPerms;
	}
	
	public void setOrgauthPerms(String orgauthPerms) {
		this.orgauthPerms = orgauthPerms;
	}
	
	public String getOrgPath() {
		return orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	/**
	 * 返回 分级管理员权限
	 * @return
	 */
	public String getOrgauthPerms() {
		return this.orgauthPerms;
	}
	
	public String getLayoutPerms() {
		return layoutPerms;
	}

	public void setLayoutPerms(String layoutPerms) {
		this.layoutPerms = layoutPerms;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("userId", this.userId) 
		.append("orgId", this.orgId) 
		.append("demId", this.demId) 
		.append("orgPerms", this.orgPerms) 
		.append("userPerms", this.userPerms) 
		.append("posPerms", this.posPerms) 
		.append("orgauthPerms", this.orgauthPerms) 
		.append("userName",this.userName)
		.append("orgName",this.orgName)
		.append("demName",this.demName)
		.append("orgPath",this.orgPath)
		.toString();
	}
}