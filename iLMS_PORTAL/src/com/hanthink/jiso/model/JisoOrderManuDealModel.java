package com.hanthink.jiso.model;

/**
 * <pre> 
 * 描述：MM_JISO_ORDER_MANU_DEAL 实体对象
 * 作者:dtp
 * 日期:2018-09-14 17:53:45
 * </pre>
 */
public class JisoOrderManuDealModel {
	
	/**
	 * 信息点
	 */
	protected String planCode;
	
	/**
	 * 供应商出货地
	 */
	protected String supFactory;
	
	/**
	 * 路线代码
	 */
	protected String routeCode;
	
	/**
	 * 车次流水号
	 */
	protected String carBatchSeqno;
	
	/**
	 * 手工组单请求人
	 */
	protected String manuReqUser;
	
	/**
	 * 手工组单请求时间
	 */
	protected String manuReqTime;
	
	/**
	 * 手工组单请求IP
	 */
	protected String manuReqIp;
	
	/**
	 * 处理状态
	 */
	protected String dealFlag;
	
	/**
	 * 处理时间
	 */
	protected String dealTime;
	
	/**
	 * 装车代码
	 */
	protected String loadCarCode;

	/**
	 * 下一个组单方式
	 */
	protected String nextGroupOrderWay;

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getCarBatchSeqno() {
		return carBatchSeqno;
	}

	public void setCarBatchSeqno(String carBatchSeqno) {
		this.carBatchSeqno = carBatchSeqno;
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

	public String getLoadCarCode() {
		return loadCarCode;
	}

	public void setLoadCarCode(String loadCarCode) {
		this.loadCarCode = loadCarCode;
	}

	public String getNextGroupOrderWay() {
		return nextGroupOrderWay;
	}

	public void setNextGroupOrderWay(String nextGroupOrderWay) {
		this.nextGroupOrderWay = nextGroupOrderWay;
	}

	
}
