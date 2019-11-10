package com.hanthink.gps.pub.vo;

/**
 * 供应商信息VO
 * @author zuosl
 *
 */
public class SupplierVO extends BaseVO{

	private static final long serialVersionUID = 10201240120136L;
	
	/** 供应商代码 */
	private String supplierNo;
	/** 供应商名称 */
	private String supplierName;
	/** 供应商状态 */
	private String status;
	/** 父供应商代码 */
	private String parentNo;
	/** 邮政编码 */
	private String youZhengBianMa;
	/** 供应商地址 */
	private String addr;
	/** 供应商详细地址 */
	private String detailAddr;
	/** 供应商激活状态(0:未激活;1:已激活;2:已注册;3:已禁用) */
	private String activeStatus;
	
	/** 需求联系人 */
	private String xuqiuLianxiren;
	/** 需求联系手机 */
	private String xuqiuMobile;
	/** 需求联系电话 */
	private String xuqiuLianxiTel;
	/** 需求联系邮箱 */
	private String xuqiuEmail;
	/** 需求联系传真 */
	private String xuqiuFax;
	
	/** 发货联系人 */
	private String fahuoLianxiren;
	/** 发货联系电话 */
	private String fahuoLianxiTel;
	/** 发货联系手机 */
	private String fahuoMobile;
	/** 发货联系邮箱 */
	private String fahuoEmail;
	/** 发货联系传真 */
	private String fahuoFax;
	
	/** 物流异常联系人 */
	private String wuliuLianxiren;
	/** 物流异常联系电话 */
	private String wuliuLianxiTel;
	/** 物流异常联系手机 */
	private String wuliuMobile;
	/** 物流异常联系邮箱 */
	private String wuliuEmail;
	/** 物流异常联系传真 */
	private String wuliuFax;
	
	/** ERP联系人 */
	private String erpLianxiren;
	/** ERP电话 */
	private String erpTel;
	/** ERP手机 */
	private String erpMobile;
	/** ERP邮箱 */
	private String erpEMail;
	/** ERP传真 */
	private String erpFax;
	/** 发货方代码 */
	private String fahuofangId;
	/** 发货方地址 */
	private String fahuofangAddr;
	/** 发货方备料时间 */
	private String beiliaoTime;
	/** 物流天数 */
	private String wuliuTime;
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
	/**
	 * 获取供应商状态
	 * @return the status 供应商状态
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置供应商状态
	 * @param status the status 供应商状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取父供应商代码
	 * @return the parentNo 父供应商代码
	 */
	public String getParentNo() {
		return parentNo;
	}
	/**
	 * 设置父供应商代码
	 * @param parentNo the parentNo 父供应商代码
	 */
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	/**
	 * 获取邮政编码
	 * @return the youZhengBianMa 邮政编码
	 */
	public String getYouZhengBianMa() {
		return youZhengBianMa;
	}
	/**
	 * 设置邮政编码
	 * @param youZhengBianMa the youZhengBianMa 邮政编码
	 */
	public void setYouZhengBianMa(String youZhengBianMa) {
		this.youZhengBianMa = youZhengBianMa;
	}
	/**
	 * 获取供应商地址
	 * @return the addr 供应商地址
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * 设置供应商地址
	 * @param addr the addr 供应商地址
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * 获取供应商详细地址
	 * @return the detailAddr 供应商详细地址
	 */
	public String getDetailAddr() {
		return detailAddr;
	}
	/**
	 * 设置供应商详细地址
	 * @param detailAddr the detailAddr 供应商详细地址
	 */
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	/**
	 * 获取需求联系人
	 * @return the xuqiuLianxiren 需求联系人
	 */
	public String getXuqiuLianxiren() {
		return xuqiuLianxiren;
	}
	/**
	 * 设置需求联系人
	 * @param xuqiuLianxiren the xuqiuLianxiren 需求联系人
	 */
	public void setXuqiuLianxiren(String xuqiuLianxiren) {
		this.xuqiuLianxiren = xuqiuLianxiren;
	}
	/**
	 * 获取需求联系手机
	 * @return the xuqiuMobile 需求联系手机
	 */
	public String getXuqiuMobile() {
		return xuqiuMobile;
	}
	/**
	 * 设置需求联系手机
	 * @param xuqiuMobile the xuqiuMobile 需求联系手机
	 */
	public void setXuqiuMobile(String xuqiuMobile) {
		this.xuqiuMobile = xuqiuMobile;
	}
	/**
	 * 获取需求联系电话
	 * @return the xuqiuLianxiTel 需求联系电话
	 */
	public String getXuqiuLianxiTel() {
		return xuqiuLianxiTel;
	}
	/**
	 * 设置需求联系电话
	 * @param xuqiuLianxiTel the xuqiuLianxiTel 需求联系电话
	 */
	public void setXuqiuLianxiTel(String xuqiuLianxiTel) {
		this.xuqiuLianxiTel = xuqiuLianxiTel;
	}
	/**
	 * 获取需求联系邮箱
	 * @return the xuqiuEmail 需求联系邮箱
	 */
	public String getXuqiuEmail() {
		return xuqiuEmail;
	}
	/**
	 * 设置需求联系邮箱
	 * @param xuqiuEmail the xuqiuEmail 需求联系邮箱
	 */
	public void setXuqiuEmail(String xuqiuEmail) {
		this.xuqiuEmail = xuqiuEmail;
	}
	/**
	 * 获取需求联系传真
	 * @return the xuqiuFax 需求联系传真
	 */
	public String getXuqiuFax() {
		return xuqiuFax;
	}
	/**
	 * 设置需求联系传真
	 * @param xuqiuFax the xuqiuFax 需求联系传真
	 */
	public void setXuqiuFax(String xuqiuFax) {
		this.xuqiuFax = xuqiuFax;
	}
	/**
	 * 获取发货联系人
	 * @return the fahuoLianxiren 发货联系人
	 */
	public String getFahuoLianxiren() {
		return fahuoLianxiren;
	}
	/**
	 * 设置发货联系人
	 * @param fahuoLianxiren the fahuoLianxiren 发货联系人
	 */
	public void setFahuoLianxiren(String fahuoLianxiren) {
		this.fahuoLianxiren = fahuoLianxiren;
	}
	/**
	 * 获取发货联系电话
	 * @return the fahuoLianxiTel 发货联系电话
	 */
	public String getFahuoLianxiTel() {
		return fahuoLianxiTel;
	}
	/**
	 * 设置发货联系电话
	 * @param fahuoLianxiTel the fahuoLianxiTel 发货联系电话
	 */
	public void setFahuoLianxiTel(String fahuoLianxiTel) {
		this.fahuoLianxiTel = fahuoLianxiTel;
	}
	/**
	 * 获取发货联系手机
	 * @return the fahuoMobile 发货联系手机
	 */
	public String getFahuoMobile() {
		return fahuoMobile;
	}
	/**
	 * 设置发货联系手机
	 * @param fahuoMobile the fahuoMobile 发货联系手机
	 */
	public void setFahuoMobile(String fahuoMobile) {
		this.fahuoMobile = fahuoMobile;
	}
	/**
	 * 获取发货联系邮箱
	 * @return the fahuoEmail 发货联系邮箱
	 */
	public String getFahuoEmail() {
		return fahuoEmail;
	}
	/**
	 * 设置发货联系邮箱
	 * @param fahuoEmail the fahuoEmail 发货联系邮箱
	 */
	public void setFahuoEmail(String fahuoEmail) {
		this.fahuoEmail = fahuoEmail;
	}
	/**
	 * 获取发货联系传真
	 * @return the fahuoFax 发货联系传真
	 */
	public String getFahuoFax() {
		return fahuoFax;
	}
	/**
	 * 设置发货联系传真
	 * @param fahuoFax the fahuoFax 发货联系传真
	 */
	public void setFahuoFax(String fahuoFax) {
		this.fahuoFax = fahuoFax;
	}
	/**
	 * 获取物流异常联系人
	 * @return the wuliuLianxiren 物流异常联系人
	 */
	public String getWuliuLianxiren() {
		return wuliuLianxiren;
	}
	/**
	 * 设置物流异常联系人
	 * @param wuliuLianxiren the wuliuLianxiren 物流异常联系人
	 */
	public void setWuliuLianxiren(String wuliuLianxiren) {
		this.wuliuLianxiren = wuliuLianxiren;
	}
	/**
	 * 获取物流异常联系电话
	 * @return the wuliuLianxiTel 物流异常联系电话
	 */
	public String getWuliuLianxiTel() {
		return wuliuLianxiTel;
	}
	/**
	 * 设置物流异常联系电话
	 * @param wuliuLianxiTel the wuliuLianxiTel 物流异常联系电话
	 */
	public void setWuliuLianxiTel(String wuliuLianxiTel) {
		this.wuliuLianxiTel = wuliuLianxiTel;
	}
	/**
	 * 获取物流异常联系手机
	 * @return the wuliuMobile 物流异常联系手机
	 */
	public String getWuliuMobile() {
		return wuliuMobile;
	}
	/**
	 * 设置物流异常联系手机
	 * @param wuliuMobile the wuliuMobile 物流异常联系手机
	 */
	public void setWuliuMobile(String wuliuMobile) {
		this.wuliuMobile = wuliuMobile;
	}
	/**
	 * 获取物流异常联系邮箱
	 * @return the wuliuEmail 物流异常联系邮箱
	 */
	public String getWuliuEmail() {
		return wuliuEmail;
	}
	/**
	 * 设置物流异常联系邮箱
	 * @param wuliuEmail the wuliuEmail 物流异常联系邮箱
	 */
	public void setWuliuEmail(String wuliuEmail) {
		this.wuliuEmail = wuliuEmail;
	}
	/**
	 * 获取物流异常联系传真
	 * @return the wuliuFax 物流异常联系传真
	 */
	public String getWuliuFax() {
		return wuliuFax;
	}
	/**
	 * 设置物流异常联系传真
	 * @param wuliuFax the wuliuFax 物流异常联系传真
	 */
	public void setWuliuFax(String wuliuFax) {
		this.wuliuFax = wuliuFax;
	}
	/**
	 * 获取ERP联系人
	 * @return the erpLianxiren ERP联系人
	 */
	public String getErpLianxiren() {
		return erpLianxiren;
	}
	/**
	 * 设置ERP联系人
	 * @param erpLianxiren the erpLianxiren ERP联系人
	 */
	public void setErpLianxiren(String erpLianxiren) {
		this.erpLianxiren = erpLianxiren;
	}
	/**
	 * 获取ERP电话
	 * @return the erpTel ERP电话
	 */
	public String getErpTel() {
		return erpTel;
	}
	/**
	 * 设置ERP电话
	 * @param erpTel the erpTel ERP电话
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
	 * @return the erpEMail ERP邮箱
	 */
	public String getErpEMail() {
		return erpEMail;
	}
	/**
	 * 设置ERP邮箱
	 * @param erpEMail the erpEMail ERP邮箱
	 */
	public void setErpEMail(String erpEMail) {
		this.erpEMail = erpEMail;
	}
	/**
	 * 获取发货方代码
	 * @return the fahuofangId 发货方代码
	 */
	public String getFahuofangId() {
		return fahuofangId;
	}
	/**
	 * 设置发货方代码
	 * @param fahuofangId the fahuofangId 发货方代码
	 */
	public void setFahuofangId(String fahuofangId) {
		this.fahuofangId = fahuofangId;
	}
	/**
	 * 获取发货方地址
	 * @return the fahuofangAddr 发货方地址
	 */
	public String getFahuofangAddr() {
		return fahuofangAddr;
	}
	/**
	 * 设置发货方地址
	 * @param fahuofangAddr the fahuofangAddr 发货方地址
	 */
	public void setFahuofangAddr(String fahuofangAddr) {
		this.fahuofangAddr = fahuofangAddr;
	}
	/**
	 * 获取发货方备料时间
	 * @return the beiliaoTime 发货方备料时间
	 */
	public String getBeiliaoTime() {
		return beiliaoTime;
	}
	/**
	 * 设置发货方备料时间
	 * @param beiliaoTime the beiliaoTime 发货方备料时间
	 */
	public void setBeiliaoTime(String beiliaoTime) {
		this.beiliaoTime = beiliaoTime;
	}
	/**
	 * 获取物流天数
	 * @return the wuliuTime 物流天数
	 */
	public String getWuliuTime() {
		return wuliuTime;
	}
	/**
	 * 设置物流天数
	 * @param wuliuTime the wuliuTime 物流天数
	 */
	public void setWuliuTime(String wuliuTime) {
		this.wuliuTime = wuliuTime;
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
	 * 获取供应商激活状态(0:未激活;1:已激活;2:已注册;3:已禁用)
	 * @return the activeStatus 供应商激活状态(0:未激活;1:已激活;2:已注册;3:已禁用)
	 */
	public String getActiveStatus() {
		return activeStatus;
	}
	/**
	 * 设置供应商激活状态(0:未激活;1:已激活;2:已注册;3:已禁用)
	 * @param activeStatus the activeStatus 供应商激活状态(0:未激活;1:已激活;2:已注册;3:已禁用)
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	
	
	
}
