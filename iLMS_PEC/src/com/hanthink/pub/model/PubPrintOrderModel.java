package com.hanthink.pub.model;

import java.util.List;

/**
 * @ClassName: PubPrintOrderModel
 * @Description: 订单打印实体类
 * @author dtp
 * @date 2018年12月1日
 */
public class PubPrintOrderModel {
	
	/**
	 * 路线代码(拉动DCS打印新增)
	 */
	private String route;
	
	/**
	 * DCS属性
	 */
	private String arrDepot;
	private String orderDepot;
	private String dispatchProductSeqno;
	private String dispatchTime;
	private String planStartDate;
	private String planEndDate;
	private String sealNo;
	private String factoryName;
	private String planStartTime;
	private String planEndTime;
	private String workDay;
	private String supPickNum;
	private String barCode;
	private String planSheetNo;
	private String SUBREPORT_DIR;
	private String logoImg;
	private String gacneLogoImg;
	private List orderList;
	
	/**
	 * 出货仓库类型
	 */
	private String shipDepotType;
	
	/**
	 * 订单类型
	 */
	private String type;
	
	/**
	 * 订单号
     */
	private String purchaseOrderno; 
	
	/**
	 * 物流单号
     */
	private String orderNo; 
	
	/**
	 * GACNE到货时间
     */
	private String arriveTime; 
	
	/**
	 * 到货批次
     */
	private String arriveBatch; 
	
	/**
	 * 卸货口
	 */
	private String unloadPort;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 供应商代码
	 */
	private String supplierNo;
	
	/**
	 * 页数
	 */
	private String ys;
	
	/**
	 * 标签张数
	 */
	private String labelPageNum;
	
	/**
	 * 序号
	 */
	private Integer no;
	
	/**
	 * 零件号
	 */
	private String partNo;
	
	/**
	 * 零件名称
	 */
	private String partName;
	
	/**
	 * 简号
	 */
	private String partShortNo;
	
	/**
	 * 箱数
	 */
	private String xs;
	
	/**
	 * 收容数
	 */
	private String standardPackage;
	
	/**
	 * 订购数量
	 */
	private String requireNum;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPurchaseOrderno() {
		return purchaseOrderno;
	}

	public void setPurchaseOrderno(String purchaseOrderno) {
		this.purchaseOrderno = purchaseOrderno;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getArriveBatch() {
		return arriveBatch;
	}

	public void setArriveBatch(String arriveBatch) {
		this.arriveBatch = arriveBatch;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getYs() {
		return ys;
	}

	public void setYs(String ys) {
		this.ys = ys;
	}

	public String getLabelPageNum() {
		return labelPageNum;
	}

	public void setLabelPageNum(String labelPageNum) {
		this.labelPageNum = labelPageNum;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getXs() {
		return xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

	public String getStandardPackage() {
		return standardPackage;
	}

	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}

	public String getRequireNum() {
		return requireNum;
	}

	public void setRequireNum(String requireNum) {
		this.requireNum = requireNum;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}

	public String getShipDepotType() {
		return shipDepotType;
	}

	public void setShipDepotType(String shipDepotType) {
		this.shipDepotType = shipDepotType;
	}

	public String getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}

	public String getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}

	public String getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getWorkDay() {
		return workDay;
	}

	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}

	public String getSupPickNum() {
		return supPickNum;
	}

	public void setSupPickNum(String supPickNum) {
		this.supPickNum = supPickNum;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getPlanSheetNo() {
		return planSheetNo;
	}

	public void setPlanSheetNo(String planSheetNo) {
		this.planSheetNo = planSheetNo;
	}

	public String getSUBREPORT_DIR() {
		return SUBREPORT_DIR;
	}

	public void setSUBREPORT_DIR(String sUBREPORT_DIR) {
		SUBREPORT_DIR = sUBREPORT_DIR;
	}

	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public String getGacneLogoImg() {
		return gacneLogoImg;
	}

	public void setGacneLogoImg(String gacneLogoImg) {
		this.gacneLogoImg = gacneLogoImg;
	}

	public List getOrderList() {
		return orderList;
	}

	public void setOrderList(List orderList) {
		this.orderList = orderList;
	}

	public String getDispatchProductSeqno() {
		return dispatchProductSeqno;
	}

	public void setDispatchProductSeqno(String dispatchProductSeqno) {
		this.dispatchProductSeqno = dispatchProductSeqno;
	}

	public String getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getOrderDepot() {
		return orderDepot;
	}

	public void setOrderDepot(String orderDepot) {
		this.orderDepot = orderDepot;
	}

	public String getArrDepot() {
		return arrDepot;
	}

	public void setArrDepot(String arrDepot) {
		this.arrDepot = arrDepot;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}
	
	
}
