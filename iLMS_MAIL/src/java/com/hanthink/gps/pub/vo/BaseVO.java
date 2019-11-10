package com.hanthink.gps.pub.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 基本VO
 */
public class BaseVO implements Serializable {
	private static final long serialVersionUID = 2051472917667444164L;
	
	private String delFlg;
	private String entryId;
	private Date entryDate;
	private String modifyId;
	private Date modifyDate;
	@SuppressWarnings("unused")
	private String entryDateStr;
	private String jsonStr;
	
	/** 用户登录工厂 */
	private String loginFactory;
	/** 登录用户名 */
	private String loginUserName;
	/** 登录用户类型 */
	private String loginUserType;
	/** 当前登录的供应商代码 */
	private String loginSupplierNo;
	
	public  String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public String getModifyId() {
		return modifyId;
	}
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getEntryDateStr() {
		if (this.getEntryDate() != null) {
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			return dateformat.format(this.getEntryDate());
		}
		return "";
	}
	public void setEntryDateStr(String entryDateStr) {
		this.entryDateStr = entryDateStr;
	}
	
	
	/**
	 * 获取用户登录工厂
	 * @return the loginFactory 用户登录工厂
	 */
	public String getLoginFactory() {
		return loginFactory;
	}
	/**
	 * 设置用户登录工厂
	 * @param loginFactory the loginFactory 用户登录工厂
	 */
	public void setLoginFactory(String loginFactory) {
		this.loginFactory = loginFactory;
	}
	/**
	 * 获取登录用户名
	 * @return the loginUserName 登录用户名
	 */
	public String getLoginUserName() {
		return loginUserName;
	}
	/**
	 * 设置登录用户名
	 * @param loginUserName the loginUserName 登录用户名
	 */
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	/**
	 * 获取登录用户类型
	 * @return the loginUserType 登录用户类型
	 */
	public String getLoginUserType() {
		return loginUserType;
	}
	/**
	 * 设置登录用户类型
	 * @param loginUserType the loginUserType 登录用户类型
	 */
	public void setLoginUserType(String loginUserType) {
		this.loginUserType = loginUserType;
	}
	/**
	 * 获取当前登录的供应商代码
	 * @return the loginSupplierNo 当前登录的供应商代码
	 */
	public String getLoginSupplierNo() {
		return loginSupplierNo;
	}
	/**
	 * 设置当前登录的供应商代码
	 * @param loginSupplierNo the loginSupplierNo 当前登录的供应商代码
	 */
	public void setLoginSupplierNo(String loginSupplierNo) {
		this.loginSupplierNo = loginSupplierNo;
	}
	
	
}