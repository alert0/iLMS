package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：拉动单 实体对象
 * @author Midnight
 * @date 2018年11月03日
 * </pre>
 */
public class MonJitOrderModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7329380703598330893L;

	protected String id;
	
	/**
	 * 物流单号
	 */
	protected String orderNo;
	/**
	 * 出货仓库
	 */
	protected String depotNo;
	/**
	 * 订单号
	 */
	protected String purchaseNo;
	/**
	 * 订单创建时间
	 */
	protected String creationTime;
	
	/**
	 * 订单创建时间至
	 */
	protected String creationTimeTo;
	/**
	 * 是否急件
	 */
//	protected String xxxx;
	/**
	 * 供应商代码
	 */
	protected String supplierNo;
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	/**
	 * 供应商名称
	 */
	protected String supplierName;
	/**
	 * 出货仓库 
	 */
	protected String shipDepot;
	/**
	 * 打印状态
	 */
	protected String printStatus;
	/**
	 * 打印时间
	 */
	protected String printTime;
	/**
	 * 计划备货批次
	 */
//	protected String xxxx;
	/**
	 * 实际备货批次
	 */
//	protected String xxxx;
	/**
	 * 最后备货时间
	 */
	protected String prepareTime;
	/**
	 * 备货完成度
	 */
	protected String prepareStatus;
	/**
	 * 备货准时性
	 */
//	protected String xxxx;
	/**
	 * 计划出货批次
	 */
	protected String deliveryProductSeqNo;
	/**
	 * 实际出货批次
	 */
	protected String actualDeliProductSeqNo;
	/**
	 * 实际出货数量
	 */
	protected String deliveryNum;
	/**
	 * 最后出货时间
	 */
	protected String deliveryTime;
	/**
	 * 出货完成度
	 */
	protected String deliveryStatus;
	/**
	 * 出货准时性
	 */
//	protected String xxxx;
	/**
	 * 计划收货批次
	 */
	protected String arriceProductSeqNo;
	/**
	 * 计划收货批次至
	 */
	protected String arriceProductSeqNoTo;
	/**
	 * 实际收货批次
	 */
	protected String actualArrProductSeqNo;
	/**
	 * 最后收货时间
	 */
	protected String arriveTime;
	/**
	 * 收货完成度
	 */
	protected String arriveStatus;
	/**
	 * 收货准时性
	 */
//	protected String xxxx;
	
	
	/**
	 * 订单明细数据
	 */
	
	/**
	 * 订单行号
	 */
	protected String orderRowNo;
	/**
	 * 零件名称
	 */
	protected String partName;
	/**
	 * 零件号
	 */
	protected String partNo;
	/**
	 * 零件短号
	 */
	protected String partShortNo;
	/**
	 * 配送地址
	 */
	protected String location;
	/**
	 * 订单数量
	 */
	protected String requireNum;
	/**
	 * 实际收货数量
	 */
	protected String arriveNum;
	/**
	 * 计划备货时间
	 */
//	protected String xxxx;
	
	
	
	
	public String getId() {
		return id;
	}
	public String getDeliveryNum() {
		return deliveryNum;
	}
	public void setDeliveryNum(String deliveryNum) {
		this.deliveryNum = deliveryNum;
	}
	public String getPartShortNo() {
		return partShortNo;
	}
	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}
	public String getArriveNum() {
		return arriveNum;
	}
	public void setArriveNum(String arriveNum) {
		this.arriveNum = arriveNum;
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
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
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
	public String getShipDepot() {
		return shipDepot;
	}
	public void setShipDepot(String shipDepot) {
		this.shipDepot = shipDepot;
	}
	public String getPrintStatus() {
		return printStatus;
	}
	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	public String getPrepareTime() {
		return prepareTime;
	}
	public void setPrepareTime(String prepareTime) {
		this.prepareTime = prepareTime;
	}
	public String getPrepareStatus() {
		return prepareStatus;
	}
	public void setPrepareStatus(String prepareStatus) {
		this.prepareStatus = prepareStatus;
	}
	public String getDeliveryProductSeqNo() {
		return deliveryProductSeqNo;
	}
	public void setDeliveryProductSeqNo(String deliveryProductSeqNo) {
		this.deliveryProductSeqNo = deliveryProductSeqNo;
	}
	public String getActualDeliProductSeqNo() {
		return actualDeliProductSeqNo;
	}
	public void setActualDeliProductSeqNo(String actualDeliProductSeqNo) {
		this.actualDeliProductSeqNo = actualDeliProductSeqNo;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getArriceProductSeqNo() {
		return arriceProductSeqNo;
	}
	public void setArriceProductSeqNo(String arriceProductSeqNo) {
		this.arriceProductSeqNo = arriceProductSeqNo;
	}
	public String getActualArrProductSeqNo() {
		return actualArrProductSeqNo;
	}
	public void setActualArrProductSeqNo(String actualArrProductSeqNo) {
		this.actualArrProductSeqNo = actualArrProductSeqNo;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getArriveStatus() {
		return arriveStatus;
	}
	public void setArriveStatus(String arriveStatus) {
		this.arriveStatus = arriveStatus;
	}
	public String getOrderRowNo() {
		return orderRowNo;
	}
	public void setOrderRowNo(String orderRowNo) {
		this.orderRowNo = orderRowNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRequireNum() {
		return requireNum;
	}
	public void setRequireNum(String requireNum) {
		this.requireNum = requireNum;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getCreationTimeTo() {
		return creationTimeTo;
	}
	public void setCreationTimeTo(String creationTimeTo) {
		this.creationTimeTo = creationTimeTo;
	}
	public String getArriceProductSeqNoTo() {
		return arriceProductSeqNoTo;
	}
	public void setArriceProductSeqNoTo(String arriceProductSeqNoTo) {
		this.arriceProductSeqNoTo = arriceProductSeqNoTo;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}

	
	
	
}
