/**
 * 
 */
package com.hanthink.gps.interf.vo;

import java.io.Serializable;

/**
 * 二线接口异常邮件提醒信息VO
 * @author chenyong
 * @date   2016-09-22
 */
public class G1interfErrorVO implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String ifNameZH; //接口中文名
	private String ifCode;   //接口代码
	private String jobName;  //任务名称
	private String lastProccessTime;  //最后运行时间
	private String factory;           //工厂
	public String getIfNameZH() {
		return ifNameZH;
	}
	public void setIfNameZH(String ifNameZH) {
		this.ifNameZH = ifNameZH;
	}
	public String getIfCode() {
		return ifCode;
	}
	public void setIfCode(String ifCode) {
		this.ifCode = ifCode;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getLastProccessTime() {
		return lastProccessTime;
	}
	public void setLastProccessTime(String lastProccessTime) {
		this.lastProccessTime = lastProccessTime;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	
}
