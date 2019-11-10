package com.hotent.restful.params;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分级组织参数对象
 * @author Administrator
 *
 */
public class OrgAuthRestfulObject {
	
	@ApiModelProperty(name="id",notes="id")
	private String id;
	
	@ApiModelProperty(name="demId",notes="对应维度id",required=true)
	private String demId;
	
	@ApiModelProperty(name="orgPerms",notes="组织管理权限，add（增加）delete（删除）edit（修改）多个用英文逗号隔开",allowableValues="add,delete,edit")
	protected String orgPerms; 
	
	@ApiModelProperty(name="userPerms",notes="用户管理权限，add（增加）delete（删除）edit（修改）多个用英文逗号隔开",allowableValues="add,delete,edit")
	protected String userPerms; 
	
	@ApiModelProperty(name="posPerms",notes="岗位管理权限，add（增加）delete（删除）edit（修改）多个用英文逗号隔开",allowableValues="add,delete,edit")
	protected String posPerms; 
	
	@ApiModelProperty(name="orgauthPerms",notes="分级管理员权限，add（增加）delete（删除）edit（修改）多个用英文逗号隔开",allowableValues="add,delete,edit")
	protected String orgauthPerms;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDemId() {
		return demId;
	}

	public void setDemId(String demId) {
		this.demId = demId;
	}

	public String getOrgPerms() {
		return orgPerms;
	}

	public void setOrgPerms(String orgPerms) {
		this.orgPerms = orgPerms;
	}

	public String getUserPerms() {
		return userPerms;
	}

	public void setUserPerms(String userPerms) {
		this.userPerms = userPerms;
	}

	public String getPosPerms() {
		return posPerms;
	}

	public void setPosPerms(String posPerms) {
		this.posPerms = posPerms;
	}

	public String getOrgauthPerms() {
		return orgauthPerms;
	}

	public void setOrgauthPerms(String orgauthPerms) {
		this.orgauthPerms = orgauthPerms;
	}
	
	public String toString(){
		return "{"
				+ "\""+"id"+"\""+":"+"\""+this.id+"\","
				+"\""+"demId"+"\""+":"+"\""+this.demId+"\","
				+"\""+"orgauthPerms"+"\""+":"+"\""+this.orgauthPerms+"\","
				+"\""+"userPerms"+"\""+":"+"\""+this.userPerms+"\","
				+"\""+"posPerms"+"\""+":"+"\""+this.posPerms+"\","
				+"\""+"orgPerms"+"\""+":"+"\""+this.orgPerms+"\""
				+ "}";
	}

}
