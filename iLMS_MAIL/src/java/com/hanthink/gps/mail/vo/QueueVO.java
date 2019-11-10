package com.hanthink.gps.mail.vo;

import java.io.Serializable;
import java.util.Date;

public class QueueVO implements Serializable {
	
	private static final long serialVersionUID = -1865318932932116178L;
	
	
	/** 发送标识-未发送 */
	public final static Integer SEND_FLG_NO = 0;
	/** 发送标识-已发送 */
	public final static Integer SEND_FLG_YES = 1;
	/** 发送标识-错误 */
	public final static Integer SEND_FLG_ERR = 2;
	
	
	/** 邮件类型-默认类型 */
	public final static String EMAIL_TYPE_DEFAULT = "DEFAULT";
	/** 邮件类型-JOB Class */
	public final static String EMAIL_TYPE_JOB_CLASS = "JOB_CLASS";
	
	
	
	private Long pkId;
	
	/** 类型 */
	private String emailType;
	/** 发送标识位(0:未发送;1:已发送;2:错误;) */
	private Integer sendFlg;
	/** 发送处理时间 */
	private String sendDealTimeStr;
	/** 发送处理时间 */
	private Date sendDealTime;
	/** 发送参数 */
	private String sendParam;
	/** Job类名 */
	private String className;
	/** 方法名 */
	private String methodName;
	/** 收件人地址 */
	private String sendTo;
	/** 收件组代码 */
	private String sendGroup;
	/** 主题 */
	private String subject;
	/** 发送内容 */
	private String sendMsg;
	/** 模板名称 */
	private String modelName;
	/** 创建时间 */
	private Date createTime;
	/** 创建时间 */
	private String createTimeStr;
	/** 创建用户 */
	private String createUser;
	
	
	/**
	 * 获取pkId
	 * @return the pkId pkId
	 */
	public Long getPkId() {
		return pkId;
	}
	/**
	 * 设置pkId
	 * @param pkId the pkId pkId
	 */
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	/**
	 * 获取类型
	 * @return the emailType 类型
	 */
	public String getEmailType() {
		return emailType;
	}
	/**
	 * 设置类型
	 * @param emailType the emailType 类型
	 */
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	/**
	 * 获取发送标识位(0:未发送;1:已发送;2:错误;)
	 * @return the sendFlg 发送标识位(0:未发送;1:已发送;2:错误;)
	 */
	public Integer getSendFlg() {
		return sendFlg;
	}
	/**
	 * 设置发送标识位(0:未发送;1:已发送;2:错误;)
	 * @param sendFlg the sendFlg 发送标识位(0:未发送;1:已发送;2:错误;)
	 */
	public void setSendFlg(Integer sendFlg) {
		this.sendFlg = sendFlg;
	}
	/**
	 * 获取发送处理时间
	 * @return the sendDealTimeStr 发送处理时间
	 */
	public String getSendDealTimeStr() {
		return sendDealTimeStr;
	}
	/**
	 * 设置发送处理时间
	 * @param sendDealTimeStr the sendDealTimeStr 发送处理时间
	 */
	public void setSendDealTimeStr(String sendDealTimeStr) {
		this.sendDealTimeStr = sendDealTimeStr;
	}
	/**
	 * 获取发送处理时间
	 * @return the sendDealTime 发送处理时间
	 */
	public Date getSendDealTime() {
		return sendDealTime;
	}
	/**
	 * 设置发送处理时间
	 * @param sendDealTime the sendDealTime 发送处理时间
	 */
	public void setSendDealTime(Date sendDealTime) {
		this.sendDealTime = sendDealTime;
	}
	/**
	 * 获取发送参数
	 * @return the sendParam 发送参数
	 */
	public String getSendParam() {
		return sendParam;
	}
	/**
	 * 设置发送参数
	 * @param sendParam the sendParam 发送参数
	 */
	public void setSendParam(String sendParam) {
		this.sendParam = sendParam;
	}
	/**
	 * 获取Job类名
	 * @return the className Job类名
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置Job类名
	 * @param className the className Job类名
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取方法名
	 * @return the methodName 方法名
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置方法名
	 * @param methodName the methodName 方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取收件人地址
	 * @return the sendTo 收件人地址
	 */
	public String getSendTo() {
		return sendTo;
	}
	/**
	 * 设置收件人地址
	 * @param sendTo the sendTo 收件人地址
	 */
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	/**
	 * 获取收件组代码
	 * @return the sendGroup 收件组代码
	 */
	public String getSendGroup() {
		return sendGroup;
	}
	/**
	 * 设置收件组代码
	 * @param sendGroup the sendGroup 收件组代码
	 */
	public void setSendGroup(String sendGroup) {
		this.sendGroup = sendGroup;
	}
	/**
	 * 获取主题
	 * @return the subject 主题
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 设置主题
	 * @param subject the subject 主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 获取发送内容
	 * @return the sendMsg 发送内容
	 */
	public String getSendMsg() {
		return sendMsg;
	}
	/**
	 * 设置发送内容
	 * @param sendMsg the sendMsg 发送内容
	 */
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	/**
	 * 获取模板名称
	 * @return the modelName 模板名称
	 */
	public String getModelName() {
		return modelName;
	}
	/**
	 * 设置模板名称
	 * @param modelName the modelName 模板名称
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	/**
	 * 获取创建时间
	 * @return the createTime 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间
	 * @param createTime the createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取创建时间
	 * @return the createTimeStr 创建时间
	 */
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	/**
	 * 设置创建时间
	 * @param createTimeStr the createTimeStr 创建时间
	 */
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	/**
	 * 获取创建用户
	 * @return the createUser 创建用户
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置创建用户
	 * @param createUser the createUser 创建用户
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	
}
