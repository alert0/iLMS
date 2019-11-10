/**
 * 
 */
package com.hanthink.gps.interf.vo;

import java.io.Serializable;

/**
 * 描述：PECGPS和MES接口异常信息邮件提醒VO
 * @author chenyong
 * @date   2016-10-18
 */
public class PECGpsMesIfErrorVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String factory;    //工厂
	private String alertModule;     //
	private String alertCode;   //代码
	private String alertLevel;  //警讯级别
	private String alertInfo;   //警讯信息
	private String creationTime; //创建时间
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getAlertModule() {
		return alertModule;
	}
	public void setAlertModule(String alertModule) {
		this.alertModule = alertModule;
	}
	public String getAlertCode() {
		return alertCode;
	}
	public void setAlertCode(String alertCode) {
		this.alertCode = alertCode;
	}
	public String getAlertLevel() {
		return alertLevel;
	}
	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}
	public String getAlertInfo() {
		return alertInfo;
	}
	public void setAlertInfo(String alertInfo) {
		this.alertInfo = alertInfo;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	
	

}
