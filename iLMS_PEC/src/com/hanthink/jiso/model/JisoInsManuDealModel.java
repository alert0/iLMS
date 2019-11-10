package com.hanthink.jiso.model;

/**
 * <pre> 
 * 描述：MM_JISO_PARTGROUP 实体对象
 * 作者:dtp
 * 日期:2018-09-13 15:44:45
 * </pre>
 */
public class JisoInsManuDealModel {
	
	/**
	 * 信息点
	 */
	private String planCode;
	
	/**
	 * 零件组代码
	 */
	private String partgroupNo;
	
	/**
	 * 手工组票请求人
	 */
	private String manuReqUser;
	
	/**
	 * 手工组票请求时间
	 */
	private String manuReqTime;
	
	/**
	 * 手工组票请求IP
	 */
	private String manuReqIp;
	
	/**
	 * 处理状态
	 */
	private String dealFlag;
	
	/**
	 * 处理时间
	 */
	private String dealTime;

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPartgroupNo() {
		return partgroupNo;
	}

	public void setPartgroupNo(String partgroupNo) {
		this.partgroupNo = partgroupNo;
	}

	public String getManuReqUser() {
		return manuReqUser;
	}

	public void setManuReqUser(String manuReqUser) {
		this.manuReqUser = manuReqUser;
	}

	public String getManuReqTime() {
		return manuReqTime;
	}

	public void setManuReqTime(String manuReqTime) {
		this.manuReqTime = manuReqTime;
	}

	public String getManuReqIp() {
		return manuReqIp;
	}

	public void setManuReqIp(String manuReqIp) {
		this.manuReqIp = manuReqIp;
	}

	public String getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	
}
