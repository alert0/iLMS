/**
 * 
 */
package com.hanthink.gps.jis.vo;

import java.io.Serializable;

/**
 * 厂外同步推算服务停止提醒信息VO
 * @author chenyong
 * @date 2016-09-21
 */
public class JisOutReckonStopVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String stopTime;   //停止时间
	private String paramCode;  //停止参数代码
	private String factory;    //工厂
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	
   
}
