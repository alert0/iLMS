package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：MM_SW_ORDER  取货单实体对象
 * 作者:Midnight
 * 日期:2018-11-03
 * </pre>
 */
public class MonDeliveryOrderModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1901421840612477882L;

	protected String id;

	/**
	 * 物流单号
	 */
	protected String orderNo;
	/**
	 * 订单号
	 */
	protected String purchaseNo;
	/**
	 * 供应商代码
	 */
	protected String supplierNo;
	/**
	 * 仓库代码
	 */
	protected String depotNo;
	/**
	 * 计划备货时间
	 */
	protected String planAssembleTime;
	/**
	 * 实际备货时间
	 */
	protected String prepareTime;
	/**
	 * 备货完成度
	 */
	protected String perpareStatus;
	/**
	 * 备货准时性
	 */
//	protected String xxxx;
	/**
	 * 计划出货时间
	 */
//	protected String xxxx;
	/**
	 * 实际出货时间
	 */
//	protected String xxxx;
	/**
	 * 出货完成度
	 */
	protected String deliveryStatus;
	/**
	 * 出货准时性
	 */
//	protected String xxxx;
	/**
	 * 计划收货时间
	 */
	protected String planArrTime;
	/**
	 * 计划收货时间至
	 */
	protected String planArrTimeTo;
	/**
	 * 实际收货时间
	 */
	protected String receiveDate;
	/**
	 * 收货完成度
	 */
	protected String receiveStatus;
	/**
	 * 收货准时性
	 */
//	protected String xxxx;
	/**
	 * 计划取货日期
	 */
	protected String planPickUpTime;
	/**
	 * 计划取货日期至
	 */
	protected String planPickUpTimeTo;
	/**
	 * 简号
	 */
	protected String partShortNo;
	/**
	 * 零件号
	 */
	protected String partNo;
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	/**
	 * 采购订单行号
	 */
	protected String purchaseRowNo;
	/**
	 * 零件名称
	 */
	protected String partNameCn;
	/**
	 * 订单数量
	 */
	protected String orderQty;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}
	public String getPlanAssembleTime() {
		return planAssembleTime;
	}
	public void setPlanAssembleTime(String planAssembleTime) {
		this.planAssembleTime = planAssembleTime;
	}
	public String getPrepareTime() {
		return prepareTime;
	}
	public void setPrepareTime(String prepareTime) {
		this.prepareTime = prepareTime;
	}
	public String getPerpareStatus() {
		return perpareStatus;
	}
	public void setPerpareStatus(String perpareStatus) {
		this.perpareStatus = perpareStatus;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getPlanArrTime() {
		return planArrTime;
	}
	public void setPlanArrTime(String planArrTime) {
		this.planArrTime = planArrTime;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public String getPlanPickUpTime() {
		return planPickUpTime;
	}
	public void setPlanPickUpTime(String planPickUpTime) {
		this.planPickUpTime = planPickUpTime;
	}
	public String getPartShortNo() {
		return partShortNo;
	}
	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getPurchaseRowNo() {
		return purchaseRowNo;
	}
	public void setPurchaseRowNo(String purchaseRowNo) {
		this.purchaseRowNo = purchaseRowNo;
	}
	public String getPartNameCn() {
		return partNameCn;
	}
	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getPlanArrTimeTo() {
		return planArrTimeTo;
	}
	public void setPlanArrTimeTo(String planArrTimeTo) {
		this.planArrTimeTo = planArrTimeTo;
	}
	public String getPlanPickUpTimeTo() {
		return planPickUpTimeTo;
	}
	public void setPlanPickUpTimeTo(String planPickUpTimeTo) {
		this.planPickUpTimeTo = planPickUpTimeTo;
	}

	
	
}
