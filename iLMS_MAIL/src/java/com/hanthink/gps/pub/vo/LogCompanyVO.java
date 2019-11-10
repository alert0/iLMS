package com.hanthink.gps.pub.vo;

/**
 * 物流公司VO 
 * @author zuosl
 *
 */
public class LogCompanyVO extends BaseVO{

	private static final long serialVersionUID = 102012416538416L;
	
	/** PKID */
	private String pkId;
	
	/** 状态(0:未激活 1:已激活) */
	private String status;
	
	/** 公司代码 */
	private String companyNo;
	
	/** 公司名称 */
	private String companyName;
	
	/** 公司类别 */
	private String type;
	
	/** 公司地址 */
	private String companyAddr;
	
	/** ERP联系人 */
	private String erpContactUser;
	
	/** ERP联系电话 */
	private String erpTel;
	
	/** ERP手机 */
	private String erpMobile;
	
	/** ERP邮箱 */
	private String erpEmail;
	
	/** ERP传真 */
	private String erpFax;
	
	/** 公司联系人 */
	private String contactUser;
	
	/** 公司联系电话 */
	private String tel;
	
	/** 公司手机 */
	private String mobile;
	
	/** 公司邮箱 */
	private String email;
	
	/** 公司传真 */
	private String fax;
	
	/** 供应商代码 */
	private String supplierNo;
	
	/** 供应商名称 */
	private String supplierName;
	
	
	/**
	 * 获取PKID
	 * @return the pkId PKID
	 */
	public String getPkId() {
		return pkId;
	}

	/**
	 * 设置PKID
	 * @param pkId the pkId PKID
	 */
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	/**
	 * 获取状态(0:未激活1:已激活)
	 * @return the status 状态(0:未激活1:已激活)
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态(0:未激活1:已激活)
	 * @param status the status 状态(0:未激活1:已激活)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取公司代码
	 * @return the companyNo 公司代码
	 */
	public String getCompanyNo() {
		return companyNo;
	}

	/**
	 * 设置公司代码
	 * @param companyNo the companyNo 公司代码
	 */
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	/**
	 * 获取公司类别
	 * @return the type 公司类别
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置公司类别
	 * @param type the type 公司类别
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取公司地址
	 * @return the companyAddr 公司地址
	 */
	public String getCompanyAddr() {
		return companyAddr;
	}

	/**
	 * 设置公司地址
	 * @param companyAddr the companyAddr 公司地址
	 */
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	/**
	 * 获取ERP联系人
	 * @return the erpContactUser ERP联系人
	 */
	public String getErpContactUser() {
		return erpContactUser;
	}

	/**
	 * 设置ERP联系人
	 * @param erpContactUser the erpContactUser ERP联系人
	 */
	public void setErpContactUser(String erpContactUser) {
		this.erpContactUser = erpContactUser;
	}

	/**
	 * 获取ERP联系电话
	 * @return the erpTel ERP联系电话
	 */
	public String getErpTel() {
		return erpTel;
	}

	/**
	 * 设置ERP联系电话
	 * @param erpTel the erpTel ERP联系电话
	 */
	public void setErpTel(String erpTel) {
		this.erpTel = erpTel;
	}

	/**
	 * 获取ERP手机
	 * @return the erpMobile ERP手机
	 */
	public String getErpMobile() {
		return erpMobile;
	}

	/**
	 * 设置ERP手机
	 * @param erpMobile the erpMobile ERP手机
	 */
	public void setErpMobile(String erpMobile) {
		this.erpMobile = erpMobile;
	}

	/**
	 * 获取ERP邮箱
	 * @return the erpEmail ERP邮箱
	 */
	public String getErpEmail() {
		return erpEmail;
	}

	/**
	 * 设置ERP邮箱
	 * @param erpEmail the erpEmail ERP邮箱
	 */
	public void setErpEmail(String erpEmail) {
		this.erpEmail = erpEmail;
	}

	/**
	 * 获取ERP传真
	 * @return the erpFax ERP传真
	 */
	public String getErpFax() {
		return erpFax;
	}

	/**
	 * 设置ERP传真
	 * @param erpFax the erpFax ERP传真
	 */
	public void setErpFax(String erpFax) {
		this.erpFax = erpFax;
	}

	/**
	 * 获取公司联系人
	 * @return the contactUser 公司联系人
	 */
	public String getContactUser() {
		return contactUser;
	}

	/**
	 * 设置公司联系人
	 * @param contactUser the contactUser 公司联系人
	 */
	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	/**
	 * 获取公司联系电话
	 * @return the tel 公司联系电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置公司联系电话
	 * @param tel the tel 公司联系电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 获取公司手机
	 * @return the mobile 公司手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置公司手机
	 * @param mobile the mobile 公司手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取公司邮箱
	 * @return the email 公司邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置公司邮箱
	 * @param email the email 公司邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取公司传真
	 * @return the fax 公司传真
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * 设置公司传真
	 * @param fax the fax 公司传真
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * 获取公司名称
	 * @return the companyName 公司名称
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 设置公司名称
	 * @param companyName the companyName 公司名称
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 获取供应商代码
	 * @return the supplierNo 供应商代码
	 */
	public String getSupplierNo() {
		return supplierNo;
	}

	/**
	 * 设置供应商代码
	 * @param supplierNo the supplierNo 供应商代码
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	/**
	 * 获取供应商名称
	 * @return the supplierName 供应商名称
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * 设置供应商名称
	 * @param supplierName the supplierName 供应商名称
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	
	
	
	
}
