package com.hanthink.gps.pub.vo;

import com.hanthink.gps.pub.vo.BaseVO;

/**
 * 系统信息表VO
 * @author qzhang
 */
public class SysMessageVO extends BaseVO {
	/***/
	private static final long serialVersionUID = -3153275022189755581L;
	// 供应商代码 
	private String supplierNo;
	// 责任人
	private String dutyPerson;
	// 信息内容
	private String message;
	
	public String getDutyPerson() {
		return dutyPerson;
	}
	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
}
