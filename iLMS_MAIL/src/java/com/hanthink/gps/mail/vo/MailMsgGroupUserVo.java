package com.hanthink.gps.mail.vo;

import java.util.Date;

/**
 * 
 * 广汽乘用车邮件模块-定时器
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 03/31 0.01  anMin    新建
 * 
 */
public class MailMsgGroupUserVo {
	
	private String sortId;//排序码
	
	private int id;
	
	private String groupCode;//分组代码
	
	private String userId;//用户id

	private String timerCode;//定时器代码
	
	
	private String delFlag;//删除标识
	
	private Date entryDate;
	
	private String entryId;
	
	private Date modifyDate;
	
	private String modifyId;
	
	
	/** 姓名 */
	private String userCName;
	/** 手机 */
	private String mobile;
	/** 电子邮箱 */
	private String email;
	/** 工厂 */
	private String factory;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTimerCode() {
		return timerCode;
	}

	public void setTimerCode(String timerCode) {
		this.timerCode = timerCode;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	/**
	 * 获取姓名
	 * @return the userCName 姓名
	 */
	public String getUserCName() {
		return userCName;
	}

	/**
	 * 设置姓名
	 * @param userCName the userCName 姓名
	 */
	public void setUserCName(String userCName) {
		this.userCName = userCName;
	}

	/**
	 * 获取手机
	 * @return the mobile 手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * @param mobile the mobile 手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取电子邮箱
	 * @return the email 电子邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置电子邮箱
	 * @param email the email 电子邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取工厂
	 * @return the factory 工厂
	 */
	public String getFactory() {
		return factory;
	}

	/**
	 * 设置工厂
	 * @param factory the factory 工厂
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	
	
	
}
