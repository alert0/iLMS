/**
 * 
 */
package com.hanthink.gps.system.vo;

import java.io.Serializable;

/**
 * 描述：统计存储过程执行时间VO
 * @author chenyong
 * @date  2016-10-10
 */
public class CheckProcedureTimeVO implements Serializable{

	
	private static final long serialVersionUID = 1L;
  
	private String factory;   //工厂
	private String execCode;  //推算代码
	private String range;     //指摘范围
	private String maxExecTime; //最大执行时间
	private String minExecTime; //最小执行时间
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getExecCode() {
		return execCode;
	}
	public void setExecCode(String execCode) {
		this.execCode = execCode;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getMaxExecTime() {
		return maxExecTime;
	}
	public void setMaxExecTime(String maxExecTime) {
		this.maxExecTime = maxExecTime;
	}
	public String getMinExecTime() {
		return minExecTime;
	}
	public void setMinExecTime(String minExecTime) {
		this.minExecTime = minExecTime;
	}
	
}
