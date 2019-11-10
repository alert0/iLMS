package com.hotent.org.persistence.model;

public class OrgAuthTree extends OrgTree {
	
	/**
	 * 岗位权限
	 */
	protected String posPerms ="";
	
	/**
	 * 组织管理权限
	 */
	protected String orgPerms ="";
	
	/**
	 * 分级组织管理权限
	 */
	protected String orgauthPerms ="";
	
	/**
	 * 用户管理权限
	 */
	protected String userPerms ="";
	
	/**
	 * 布局管理权限
	 */
	protected String layoutPerms = "";
	protected String userId;
	
	public OrgAuthTree(Org group) {
		this.id =group.id;
		this.name = group.name;
		this.code = group.code;
		this.sn = group.orderNo;
		this.parentId = group.parentId;
		this.demId = group.demId;
		this.isIsParent = group.isIsParent;
		if(!this.name.contains("style=")){
			this.title = name;
		}
	}

	public String getPosPerms() {
		return posPerms;
	}

	public void setPosPerms(String posPerms) {
		this.posPerms = posPerms;
	}

	public String getOrgPerms() {
		return orgPerms;
	}

	public void setOrgPerms(String orgPerms) {
		this.orgPerms = orgPerms;
	}

	public String getOrgauthPerms() {
		return orgauthPerms;
	}

	public void setOrgauthPerms(String orgauthPerms) {
		this.orgauthPerms = orgauthPerms;
	}

	public String getUserPerms() {
		return userPerms;
	}

	public void setUserPerms(String userPerms) {
		this.userPerms = userPerms;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLayoutPerms() {
		return layoutPerms;
	}

	public void setLayoutPerms(String layoutPerms) {
		this.layoutPerms = layoutPerms;
	}
	

}
