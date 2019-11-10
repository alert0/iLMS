package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

public class MonOrderTrackingModel extends AbstractModel<String>{

	private static final long serialVersionUID = -7437510378484327774L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂 */
	private String factoryCode;
	/** 物流单号 */
	private String orderNo;
	/** 订单号 */
	private String purchaseNo;
	/** 零件简号 */
	private String partShortNo;
	/** 零件号 */
	private String partNo;
	/** 操作(备货、出货、收货) */
	private String opeType;
	/** 完成状态 */
	private String finishStatus;
	/** 准时状态 */
	private String onTimeStatus;
	/** 供应商代码 */
	private String supplierNo;
	/** 供应商名称 */
	private String supplierName;
	/** 出货地 */
	private String supFactory;
	/** 出货仓库 */
	private String supWareCode;
	/** 到货仓库 */
	private String intoWareCode;
	/** 订单类型(取货订单、拉动订单、同步订单) */
	private String orderType;
	/** 车辆VIN号 */
	private String vinNo;
	/** 创建开始时间 */
	private String creationTimeStart;
	/** 创建结束时间 */
	private String creationTimeEnd;
	/** 零件名称 */
	private String partName;
	/** 订购量 */
	private String orderQTY;
	/** 计划备货时间 */
	private String planPreTime;
	/** 实际备货时间 */
	private String realyPreTime;
	/** 备货数量 */
	private String preNum;
	/** 备货状态 */
	private String preStatus;
	/** 备货准时性 */
	private String preOnTimeStatus;
	/** 计划出货时间 */
	private String planOutTime;
	/** 实际出货时间 */
	private String realyOutTime;
	/** 出货数量 */
	private String outNum;
	/** 出货状态 */
	private String outStatus;
	/** 出货准时性 */
	private String outOnTimeStatus;
	/** 计划到货时间 */
	private String planArrTime;
	/** 实际到货时间 */
	private String realyArrTime;
	/** 到货数量 */
	private String arrNum;
	/** 到货状态 */
	private String arrStatus;
	/** 到货准时性 */
	private String arrOntimeStatus;
	/** 拉动单计划到货时间 */
	private String jitPlanArrTime;
	private String jitActualArrTime;
	
	private String preExcelStatus;
	private String outExcelStatus;
	private String arrExcelStatus;
	private String preExcelOnTimeStatus;
	private String outExcelOnTimeStatus;
	private String arrExcelOnTimeStatus;
	
	/**
	 * add 2019-05029 ZengFanZhuo
	 * @return
	 */
	/** 计划到货开始时间  */
	private String planArrTimeStart;
	/** 计划到货结束时间  */
	private String planArrTimeEnd;
	
	/** 实际到货开始时间  */
	private String realyArrTimeStart;
	/** 实际到货结束时间  */
	private String realyArrTimeEnd;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
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
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public String getFinishStatus() {
		return finishStatus;
	}
	public void setFinishStatus(String finishStatus) {
		this.finishStatus = finishStatus;
	}
	public String getOnTimeStatus() {
		return onTimeStatus;
	}
	public void setOnTimeStatus(String onTimeStatus) {
		this.onTimeStatus = onTimeStatus;
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
	public String getSupFactory() {
		return supFactory;
	}
	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}
	public String getSupWareCode() {
		return supWareCode;
	}
	public void setSupWareCode(String supWareCode) {
		this.supWareCode = supWareCode;
	}
	public String getIntoWareCode() {
		return intoWareCode;
	}
	public void setIntoWareCode(String intoWareCode) {
		this.intoWareCode = intoWareCode;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getVinNo() {
		return vinNo;
	}
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
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
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getOrderQTY() {
		return orderQTY;
	}
	public void setOrderQTY(String orderQTY) {
		this.orderQTY = orderQTY;
	}
	public String getPlanPreTime() {
		return planPreTime;
	}
	public void setPlanPreTime(String planPreTime) {
		this.planPreTime = planPreTime;
	}
	public String getRealyPreTime() {
		return realyPreTime;
	}
	public void setRealyPreTime(String realyPreTime) {
		this.realyPreTime = realyPreTime;
	}
	public String getPreNum() {
		return preNum;
	}
	public void setPreNum(String preNum) {
		this.preNum = preNum;
	}
	public String getPreStatus() {
		return preStatus;
	}
	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	public String getPreOnTimeStatus() {
		return preOnTimeStatus;
	}
	public void setPreOnTimeStatus(String preOnTimeStatus) {
		this.preOnTimeStatus = preOnTimeStatus;
	}
	public String getPlanOutTime() {
		return planOutTime;
	}
	public void setPlanOutTime(String planOutTime) {
		this.planOutTime = planOutTime;
	}
	public String getRealyOutTime() {
		return realyOutTime;
	}
	public void setRealyOutTime(String realyOutTime) {
		this.realyOutTime = realyOutTime;
	}
	public String getOutNum() {
		return outNum;
	}
	public void setOutNum(String outNum) {
		this.outNum = outNum;
	}
	public String getOutStatus() {
		return outStatus;
	}
	public void setOutStatus(String outStatus) {
		this.outStatus = outStatus;
	}
	public String getOutOnTimeStatus() {
		return outOnTimeStatus;
	}
	public void setOutOnTimeStatus(String outOnTimeStatus) {
		this.outOnTimeStatus = outOnTimeStatus;
	}
	public String getPlanArrTime() {
		return planArrTime;
	}
	public void setPlanArrTime(String planArrTime) {
		this.planArrTime = planArrTime;
	}
	public String getRealyArrTime() {
		return realyArrTime;
	}
	public void setRealyArrTime(String realyArrTime) {
		this.realyArrTime = realyArrTime;
	}
	public String getArrNum() {
		return arrNum;
	}
	public void setArrNum(String arrNum) {
		this.arrNum = arrNum;
	}
	public String getArrStatus() {
		return arrStatus;
	}
	public void setArrStatus(String arrStatus) {
		this.arrStatus = arrStatus;
	}
	public String getArrOntimeStatus() {
		return arrOntimeStatus;
	}
	public void setArrOntimeStatus(String arrOntimeStatus) {
		this.arrOntimeStatus = arrOntimeStatus;
	}
	public String getJitPlanArrTime() {
		return jitPlanArrTime;
	}
	public void setJitPlanArrTime(String jitPlanArrTime) {
		this.jitPlanArrTime = jitPlanArrTime;
	}
	public String getJitActualArrTime() {
		return jitActualArrTime;
	}
	public void setJitActualArrTime(String jitActualArrTime) {
		this.jitActualArrTime = jitActualArrTime;
	}
	public String getPreExcelStatus() {
		return preExcelStatus;
	}
	public void setPreExcelStatus(String preExcelStatus) {
		this.preExcelStatus = preExcelStatus;
	}
	public String getOutExcelStatus() {
		return outExcelStatus;
	}
	public void setOutExcelStatus(String outExcelStatus) {
		this.outExcelStatus = outExcelStatus;
	}
	public String getArrExcelStatus() {
		return arrExcelStatus;
	}
	public void setArrExcelStatus(String arrExcelStatus) {
		this.arrExcelStatus = arrExcelStatus;
	}
	public String getPreExcelOnTimeStatus() {
		return preExcelOnTimeStatus;
	}
	public void setPreExcelOnTimeStatus(String preExcelOnTimeStatus) {
		this.preExcelOnTimeStatus = preExcelOnTimeStatus;
	}
	public String getOutExcelOnTimeStatus() {
		return outExcelOnTimeStatus;
	}
	public void setOutExcelOnTimeStatus(String outExcelOnTimeStatus) {
		this.outExcelOnTimeStatus = outExcelOnTimeStatus;
	}
	public String getArrExcelOnTimeStatus() {
		return arrExcelOnTimeStatus;
	}
	public void setArrExcelOnTimeStatus(String arrExcelOnTimeStatus) {
		this.arrExcelOnTimeStatus = arrExcelOnTimeStatus;
	}
	public String getPlanArrTimeEnd() {
		return planArrTimeEnd;
	}
	public void setPlanArrTimeEnd(String planArrTimeEnd) {
		this.planArrTimeEnd = planArrTimeEnd;
	}
	public String getRealyArrTimeEnd() {
		return realyArrTimeEnd;
	}
	public void setRealyArrTimeEnd(String realyArrTimeEnd) {
		this.realyArrTimeEnd = realyArrTimeEnd;
	}
	public String getPlanArrTimeStart() {
		return planArrTimeStart;
	}
	public void setPlanArrTimeStart(String planArrTimeStart) {
		this.planArrTimeStart = planArrTimeStart;
	}
	public String getRealyArrTimeStart() {
		return realyArrTimeStart;
	}
	public void setRealyArrTimeStart(String realyArrTimeStart) {
		this.realyArrTimeStart = realyArrTimeStart;
	}	
}