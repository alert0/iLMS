package com.hanthink.gps.gacne.sw.vo;

import java.io.Serializable;

/**
 * @Desc    : 公告未反馈VO
 * @FileName: NoticeOverTimeData.java 
 * @CreateOn: 2018-11-14 16:19
 * @author  : zhengwuchao  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2018-11-14	V1.0		zhengwuchao		新建
 * 
 *
 */
public class PickupPlanVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 工厂
	 */
	private String factory;
	
	/**
	 * 当前时间
	 */
	private String time;
	
	/**
	 * 物流管理员
	 */
	private String planUser;
	
	/**
	 * 物流管理员
	 */
	private String account;
	
	/**
	 * 供应商给数量
	 */
	private String planNum;
	
	/**
	 * 物流管理员邮箱
	 */
	private String mail;
	
	
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPlanUser() {
		return planUser;
	}

	public void setPlanUser(String planUser) {
		this.planUser = planUser;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	

	
}
