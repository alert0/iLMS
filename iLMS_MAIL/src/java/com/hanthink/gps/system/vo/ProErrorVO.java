package com.hanthink.gps.system.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 存储过程执行异常VO
 * @author Administrator
 *
 */
public class ProErrorVO implements Serializable {

	private static final long serialVersionUID = 48518043841431002L;
	
	private String jobCode;
	private String dbName;
	private String job;
	private String what;
	private String lastDate;
	private String nextDate;
	private String interval;
	private String triggerName;
	private String tableName;
	private String status;
	private String triggeringEvent;
	private String jobDesc;
	private String lastRunTime;
	private String nextRunTime;
	private String triggerValue;
	
	
	/** 是否发送邮件-发送 */
	public static final int SEND_MAIL_FLAG_YES = 1;
	/** 是否发送邮件-不发送 */
	public static final int SEND_MAIL_FLAG_NO = 0;
	
	private String pkId;
	
	/** 警讯类别 */
	private String alertType;
	/** 错误代码 */
	private String errorName;
	/** 错误描述 */
	private String errorDesc;
	/** 创建时间 */
	private Date creationDate;
	/** 创建时间 */
	private String creationDateStr;
	/** KEY_NAME */
	private String keyName;
	/** 是否发送邮件(0:否;1:是) */
	private Integer sendMailFlag;
	/** 警讯级别 */
	private String errorLevel;
	/**
	 * 获取pkId
	 * @return the pkId pkId
	 */
	public String getPkId() {
		return pkId;
	}
	/**
	 * 设置pkId
	 * @param pkId the pkId pkId
	 */
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	/**
	 * 获取警讯类别
	 * @return the alertType 警讯类别
	 */
	public String getAlertType() {
		return alertType;
	}
	/**
	 * 设置警讯类别
	 * @param alertType the alertType 警讯类别
	 */
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	/**
	 * 获取错误代码
	 * @return the errorName 错误代码
	 */
	public String getErrorName() {
		return errorName;
	}
	/**
	 * 设置错误代码
	 * @param errorName the errorName 错误代码
	 */
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	/**
	 * 获取错误描述
	 * @return the errorDesc 错误描述
	 */
	public String getErrorDesc() {
		return errorDesc;
	}
	/**
	 * 设置错误描述
	 * @param errorDesc the errorDesc 错误描述
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	/**
	 * 获取创建时间
	 * @return the creationDate 创建时间
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * 设置创建时间
	 * @param creationDate the creationDate 创建时间
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * 获取创建时间
	 * @return the creationDateStr 创建时间
	 */
	public String getCreationDateStr() {
		return creationDateStr;
	}
	/**
	 * 设置创建时间
	 * @param creationDateStr the creationDateStr 创建时间
	 */
	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}
	/**
	 * 获取KEY_NAME
	 * @return the keyName KEY_NAME
	 */
	public String getKeyName() {
		return keyName;
	}
	/**
	 * 设置KEY_NAME
	 * @param keyName the keyName KEY_NAME
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	/**
	 * 获取是否发送邮件(0:否;1:是)
	 * @return the sendMailFlag 是否发送邮件(0:否;1:是)
	 */
	public Integer getSendMailFlag() {
		return sendMailFlag;
	}
	/**
	 * 设置是否发送邮件(0:否;1:是)
	 * @param sendMailFlag the sendMailFlag 是否发送邮件(0:否;1:是)
	 */
	public void setSendMailFlag(Integer sendMailFlag) {
		this.sendMailFlag = sendMailFlag;
	}
	/**
	 * 获取警讯级别
	 * @return the errorLevel 警讯级别
	 */
	public String getErrorLevel() {
		return errorLevel;
	}
	/**
	 * 设置警讯级别
	 * @param errorLevel the errorLevel 警讯级别
	 */
	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	public String getNextDate() {
		return nextDate;
	}
	public void setNextDate(String nextDate) {
		this.nextDate = nextDate;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTriggeringEvent() {
		return triggeringEvent;
	}
	public void setTriggeringEvent(String triggeringEvent) {
		this.triggeringEvent = triggeringEvent;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getLastRunTime() {
		return lastRunTime;
	}
	public void setLastRunTime(String lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
	public String getNextRunTime() {
		return nextRunTime;
	}
	public void setNextRunTime(String nextRunTime) {
		this.nextRunTime = nextRunTime;
	}
	public String getTriggerValue() {
		return triggerValue;
	}
	public void setTriggerValue(String triggerValue) {
		this.triggerValue = triggerValue;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	
}
