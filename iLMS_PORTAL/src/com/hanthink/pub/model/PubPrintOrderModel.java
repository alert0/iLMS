package com.hanthink.pub.model;

/**
 * @ClassName: PubPrintOrderModel
 * @Description: 订单打印实体类
 * @author dtp
 * @date 2018年12月1日
 */
public class PubPrintOrderModel {
	
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
	
}
