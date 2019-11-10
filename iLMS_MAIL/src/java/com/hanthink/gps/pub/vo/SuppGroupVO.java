package com.hanthink.gps.pub.vo;

import java.io.Serializable;
import java.util.Date;

public class SuppGroupVO implements Serializable {
	private static final long serialVersionUID = 102513541638416L;
	/** 供应商分组ID */
	private String groupId;
	/** 供应商分组名称 */
	private String groupName;
	private String supplierNo;
	private String supplierName;
	
	/** 公告信息ID */
	private String infoId;
	
	
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/** 创建用户 */
	private String createUser;
	
	/** 创建时间 */
	private String createTime;
	
	/** 最后修改用户 */
	private String lastModifyUser;
	
	/** 最后修改时间*/
	private Date lastModifyTime;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyUser() {
		return lastModifyUser;
	}

	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	
}
