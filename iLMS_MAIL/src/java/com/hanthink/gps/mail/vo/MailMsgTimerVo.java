package com.hanthink.gps.mail.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * 广汽乘用车邮件模块-邮件类型分组
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 03/31 0.01  anMin    新建
 * 
 */
public class MailMsgTimerVo {
	
	private int id;
	
	private String timerCode;//定时器代码
	
	private String timerGroup;//定时器分组

	
	private String timerParam;//定时器参数
	
	private String timerName;//定时器名称
	
	private String runCode;//业务处理代码
	
	private Timestamp startTime;//开始时间
	
	private Timestamp lastRunTime;//上次结束时间
	
	private Timestamp nextRunTime;//下次开始时间
	
	private String runState;//运行状态
	
	private String updateState;//更新状态
	
	/** 应用类型(用于区分多个应用指向同一个数据库) */
	private String appType;



	
	private String delFlag;//删除标识
	
	private Date entryDate;
	
	private String entryId;
	
	private Date modifyDate;
	
	private String modifyId;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTimerCode() {
		return timerCode;
	}

	public void setTimerCode(String timerCode) {
		this.timerCode = timerCode;
	}

	public String getTimerParam() {
		return timerParam;
	}

	public void setTimerParam(String timerParam) {
		this.timerParam = timerParam;
	}

	public String getTimerName() {
		return timerName;
	}

	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}

	public String getRunCode() {
		return runCode;
	}

	public void setRunCode(String runCode) {
		this.runCode = runCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Date getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Timestamp lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	public Date getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(Timestamp nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

	public String getRunState() {
		return runState;
	}

	public void setRunState(String runState) {
		this.runState = runState;
	}

	public String getUpdateState() {
		return updateState;
	}

	public void setUpdateState(String updateState) {
		this.updateState = updateState;
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

	public String getTimerGroup() {
		return timerGroup;
	}

	public void setTimerGroup(String timerGroup) {
		this.timerGroup = timerGroup;
	}

	/**
	 * 获取应用类型(用于区分多个应用指向同一个数据库)
	 * @return the appType 应用类型(用于区分多个应用指向同一个数据库)
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * 设置应用类型(用于区分多个应用指向同一个数据库)
	 * @param appType the appType 应用类型(用于区分多个应用指向同一个数据库)
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	
	
	

}
