package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

public class SwLogictiscOrderModel extends AbstractModel<String>{

	private static final long serialVersionUID = 8005642289530476375L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂 */
	private String factoryCode;
	/** 订单号 */
	private String purchaseNo;
	/** 物料编码 */
	private String orderNo;
	/** 资材名称 */
	private String partName;
	/** 规格 */
	private String standPackage;
	/** 订购数量 */
	private String orderQTY;
	/** 订购单位 */
	private String orderUnit;
	/** 订购日期 */
	private String orderDate;
	/** 订购人 */
	private String orderUser;
	/** 联系方式 */
	private String recTel;
	/** 供应商代码 */
	private String supplierNo;
	/** 供应商名称 */
	private String supplierName;
	/** 到货仓库 */
	private String depotNo;
	/** 仓库区分 */
	private String invType;
	/** 费用中心 */
	private String costCode;
	/** 成本费用中心 */
	private String costCenter;
	/** 到货日期 */
	private String arriveTime;
	private String arriveTimeStart;
	private String arriveTimeEnd;
	/** 计划交货日期 */
	private String replyDeliveryDate;
	/** 订单打印状态 */
	private String printStatus;
	/** 订单打印时间 */
	private String printTimes;
	/** 反馈状态 */
	private String feedBackStatus;
	/** 反馈时间 */
	private String feedBackTimes;
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getStandPackage() {
		return standPackage;
	}
	public void setStandPackage(String standPackage) {
		this.standPackage = standPackage;
	}
	public String getOrderQTY() {
		return orderQTY;
	}
	public void setOrderQTY(String orderQTY) {
		this.orderQTY = orderQTY;
	}
	public String getOrderUnit() {
		return orderUnit;
	}
	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderUser() {
		return orderUser;
	}
	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}
	public String getRecTel() {
		return recTel;
	}
	public void setRecTel(String recTel) {
		this.recTel = recTel;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}
	public String getInvType() {
		return invType;
	}
	public void setInvType(String invType) {
		this.invType = invType;
	}
	public String getCostCode() {
		return costCode;
	}
	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getArriveTimeStart() {
		return arriveTimeStart;
	}
	public void setArriveTimeStart(String arriveTimeStart) {
		this.arriveTimeStart = arriveTimeStart;
	}
	public String getArriveTimeEnd() {
		return arriveTimeEnd;
	}
	public void setArriveTimeEnd(String arriveTimeEnd) {
		this.arriveTimeEnd = arriveTimeEnd;
	}
	public String getReplyDeliveryDate() {
		return replyDeliveryDate;
	}
	public void setReplyDeliveryDate(String replyDeliveryDate) {
		this.replyDeliveryDate = replyDeliveryDate;
	}
	public String getPrintStatus() {
		return printStatus;
	}
	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}
	public String getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(String printTimes) {
		this.printTimes = printTimes;
	}
	public String getFeedBackStatus() {
		return feedBackStatus;
	}
	public void setFeedBackStatus(String feedBackStatus) {
		this.feedBackStatus = feedBackStatus;
	}
	public String getFeedBackTimes() {
		return feedBackTimes;
	}
	public void setFeedBackTimes(String feedBackTimes) {
		this.feedBackTimes = feedBackTimes;
	}
}
