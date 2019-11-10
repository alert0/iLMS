package com.hanthink.jit.model;

import com.hotent.base.core.model.AbstractModel;

public class JitPartLackModel extends AbstractModel<String>{
	
	/**
	 * @Fields serialVersionUID : 序列
	 */
	private static final long serialVersionUID = -2046835478049804872L;

	private String id;
	private String factoryCode;
	/**信息点**/
	private String planCode;
	/**零件号**/
	private String partNo;
	/**简号**/
	private String partShortNo;
	/**零件名称**/
	private String partNameCn;
	/**开始备件批次产品流水号**/
	private String sPrepareProductSeqno;
	/**结束备件批次产品流水号**/
	private String ePrepareProductSeqno;
	/**备件批次**/
	private String prepareProduct;
	/**开始配送批次流水号**/
	private String sDistriProductSeqno;
	/**结束配送批次流水号**/
	private String eDistriProductSeqno;
	/**配送批次**/
	private String distriProduct;
	/**落点**/
	private String location;
	/**欠品数**/
	private String lackNum;
	/**已调配数**/
	private String distriNum;
	/**待处理欠品数**/
	private String waitDistriNum;
	/**处理状态**/
	private String dealStatus;
	/**处理状态名称**/
	private String dealStatusName;
	/**创建时间**/
	private String creationTime;
	/**创建时间开始**/
	private String creationTimeStart;
	/**创建时间结束**/
	private String creationTimeEnd;
	
	/**********明细************************/
	/**处理人**/
	private String dealUser;
	/**处理Ip**/
	private String dealIp;
	/**处理方式**/
	private String dealMethod;
	/**处理方式**/
	private String dealMethodName;
	/**处理时间**/
	private String dealTime;
	/**处理人名字**/
	private String dealUserName;
	/**订单号**/
	private String orderNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getsPrepareProductSeqno() {
		return sPrepareProductSeqno;
	}

	public void setsPrepareProductSeqno(String sPrepareProductSeqno) {
		this.sPrepareProductSeqno = sPrepareProductSeqno;
	}

	public String getePrepareProductSeqno() {
		return ePrepareProductSeqno;
	}

	public void setePrepareProductSeqno(String ePrepareProductSeqno) {
		this.ePrepareProductSeqno = ePrepareProductSeqno;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLackNum() {
		return lackNum;
	}

	public void setLackNum(String lackNum) {
		this.lackNum = lackNum;
	}

	public String getDistriNum() {
		return distriNum;
	}

	public void setDistriNum(String distriNum) {
		this.distriNum = distriNum;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreationTimeStart() {
		return creationTimeStart;
	}

	public void setCreationTimeStart(String creationTimeStart) {
		this.creationTimeStart = creationTimeStart;
	}

	public String getCreationTimeEnd() {
		return creationTimeEnd;
	}

	public void setCreationTimeEnd(String creationTimeEnd) {
		this.creationTimeEnd = creationTimeEnd;
	}

	public String getDealStatusName() {
		return dealStatusName;
	}

	public void setDealStatusName(String dealStatusName) {
		this.dealStatusName = dealStatusName;
	}

	public String getWaitDistriNum() {
		return waitDistriNum;
	}

	public void setWaitDistriNum(String waitDistriNum) {
		this.waitDistriNum = waitDistriNum;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getPrepareProduct() {
		return prepareProduct;
	}

	public void setPrepareProduct(String prepareProduct) {
		this.prepareProduct = prepareProduct;
	}

	public String getsDistriProductSeqno() {
		return sDistriProductSeqno;
	}

	public void setsDistriProductSeqno(String sDistriProductSeqno) {
		this.sDistriProductSeqno = sDistriProductSeqno;
	}

	public String geteDistriProductSeqno() {
		return eDistriProductSeqno;
	}

	public void seteDistriProductSeqno(String eDistriProductSeqno) {
		this.eDistriProductSeqno = eDistriProductSeqno;
	}

	public String getDistriProduct() {
		return distriProduct;
	}

	public void setDistriProduct(String distriProduct) {
		this.distriProduct = distriProduct;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	public String getDealIp() {
		return dealIp;
	}

	public void setDealIp(String dealIp) {
		this.dealIp = dealIp;
	}

	public String getDealMethod() {
		return dealMethod;
	}

	public void setDealMethod(String dealMethod) {
		this.dealMethod = dealMethod;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getDealMethodName() {
		return dealMethodName;
	}

	public void setDealMethodName(String dealMethodName) {
		this.dealMethodName = dealMethodName;
	}

	public String getDealUserName() {
		return dealUserName;
	}

	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
