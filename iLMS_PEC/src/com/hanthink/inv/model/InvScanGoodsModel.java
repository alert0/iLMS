package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;

public class InvScanGoodsModel extends AbstractModel<String>{

	private static final long serialVersionUID = 7100789248505117184L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂 */
	private String factoryCode;
	/** 订单类型 */
	private String orderType;
	/** 到货仓库 */
	private String depotNo;
	/** 收货单号 */
	private String orderNoSeq;
	/** 物流单号 */
	private String orderNo;
	/** 物流单行号 */
	private String orderRowNo;
	/** 供应商 */
	private String supplier;
	/** 供应商名称 */
	private String supplierName;
	/** 出货地 */
	private String supFactory;
	/** 零件号 */
	private String partNo;
	/** 简号 */
	private String partShortNo;
	/** 零件名称 */
	private String partName;
	/** 配送量 */
	private String shipQuantity;
	/** 已收数量 */
	private String receiveNum;
	/** 本次收货数量 */
	private String thisTimeReceiveNum;
	/** 收货次数 */
	private String recTimes;
	//收货状态
    private String shStatus;
	/** 查询标识 */
	private String flag;
	/** 操作人信息 */
	private String ipAddr;
	private String opeUser;
	/** 看板批次号 */
	private String curSeq;
	
	/** 错误信息返回数据 */
	private String resultMsg;	
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}
	public String getOrderNoSeq() {
		return orderNoSeq;
	}
	public void setOrderNoSeq(String orderNoSeq) {
		this.orderNoSeq = orderNoSeq;
	}
	public String getOrderRowNo() {
		return orderRowNo;
	}
	public void setOrderRowNo(String orderRowNo) {
		this.orderRowNo = orderRowNo;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
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
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getShipQuantity() {
		return shipQuantity;
	}
	public void setShipQuantity(String shipQuantity) {
		this.shipQuantity = shipQuantity;
	}
	public String getReceiveNum() {
		return receiveNum;
	}
	public void setReceiveNum(String receiveNum) {
		this.receiveNum = receiveNum;
	}
	public String getThisTimeReceiveNum() {
		return thisTimeReceiveNum;
	}
	public void setThisTimeReceiveNum(String thisTimeReceiveNum) {
		this.thisTimeReceiveNum = thisTimeReceiveNum;
	}
	public String getRecTimes() {
		return recTimes;
	}
	public void setRecTimes(String recTimes) {
		this.recTimes = recTimes;
	}
	public String getShStatus() {
		return shStatus;
	}
	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getOpeUser() {
		return opeUser;
	}
	public void setOpeUser(String opeUser) {
		this.opeUser = opeUser;
	}
	public String getCurSeq() {
		return curSeq;
	}
	public void setCurSeq(String curSeq) {
		this.curSeq = curSeq;
	}
}
