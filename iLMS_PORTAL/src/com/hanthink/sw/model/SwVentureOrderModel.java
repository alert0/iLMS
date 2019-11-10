package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

public class SwVentureOrderModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = -3539392121564274968L;
	
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 广新工厂
	 */
	private String factoryCode;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 订购方代码(工区)
	 */
	private String jvPlace;
	/**
	 * 订单类型
	 */
	private String orderType;
	private String excelOrderType;
	/**
	 * 卸货口
	 */
	private String unloadPort;
	/**
	 * 供应商代码
	 */
	private String supplierNo;
	/**
	 * 出货地
	 */
	private String supFactory;
	/**
	 * 供应商代码
	 */
	private String supplierName;
	/**
	 * 订购方订单号
	 */
	private String orderNo;
	/**
	 * 行号
	 */
	private String rowNo;
	/**
	 * 采购订单号
	 */
	private String purchseNo;
	/**
	 * 托数
	 */
	private String tpCount;
	/**
	 * 零件号(番号)
	 */
	private String partNo;
	/**
	 * 零件名称
	 */
	private String partName;
	/**
	 * 简号(背番号)
	 */
	private String partShortNo;
	/**
	 * 收容数
	 */
	private String standardPackage;
	/**
	 * 订单箱数
	 */
	private String orderBox;
	/**
	 * 订单个数
	 */
	private String orderQty;
	/**
	 * 拣货地址(所番地)
	 */
	private String storage;
	/**
	 * 配送地址(搬入路线)
	 */
	private String location;
	/*
	 * 到货批次(P_LANE)
	 */
	private String arriveBatch;
	/*
	 * 订单日期(发注日)
	 */
	private String orderDate;
	/*
	 * 供应商出货时间
	 */
	private String supShipTime;
	/*
	 * 供应商线路名
	 */
	private String supRouteName;
	/*
	 * 供应商到达时间
	 */
	private String supArriveTime;
	/*
	 * 便次(趟次)
	 */
	private String supCarBatch;
	/*
	 * GBL仓库出货时间
	 */
	private String gblShipTime;
	/*
	 * GBL仓库到达时间
	 */
	private String gblArriveTime;
	/*
	 * GBL路线名
	 */
	private String gblRouteName;
	/*
	 * GBL便次(趟次)
	 */
	private String gblCarBatch;
	/*
	 * 到货日期(纳入日)
	 */
	private String arriveDate;
	/*
	 * 收货日期
	 */
	private String receiveDate;
	/*
	 * 收货次数
	 */
	private String receiveCount;
	/*
	 * 到货仓库
	 */
	private String depotNo;
	/*
	 * 需求人
	 */
	private String demander;
	/*
	 * 联系电话
	 */
	private String conNumber;
	/*
	 * 配送工程
	 */
	private String distriPerson;
	/*
	 * 导入UUID
	 */
	private String uuid;
	/*
	 * 导入校验结果/审核结果
	 */
	private String checkResult;
	/*
	 * 审核人
	 */
	private String checkUser;
	/*
	 * 审核时间
	 */
	private String checkTime;
	/*
	 * 审核备注
	 */
	private String checkRemark;
	/*
	 * 校验信息
	 */
	private String checkInfo;
	/*
	 * 导入状态
	 */
	private String importStatus;
	/*
	 * 操作类型
	 */
	private String opeType;
	/*
	 * 创建人
	 */
	private String creationUser;
	/*
	 * 操作类型
	 */
	private String creationTime;
	/*
	 * 最后修改人
	 */
	private String lastModifiedUser;
	/*
	 * 最后修改时间
	 */
	private String lastModifiedTime;
	/**
	 * 导入人IP
	 */
	private String impIp;
	
	
	private String importStatusStr;
	private String checkResultStr;
	private String creationTimeStart;
	private String creationTimeEnd;

	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getJvPlace() {
		return jvPlace;
	}
	public void setJvPlace(String jvPlace) {
		this.jvPlace = jvPlace;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getExcelOrderType() {
		return excelOrderType;
	}
	public void setExcelOrderType(String excelOrderType) {
		this.excelOrderType = excelOrderType;
	}
	public String getUnloadPort() {
		return unloadPort;
	}
	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getSupFactory() {
		return supFactory;
	}
	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getPurchseNo() {
		return purchseNo;
	}
	public void setPurchseNo(String purchseNo) {
		this.purchseNo = purchseNo;
	}
	public String getTpCount() {
		return tpCount;
	}
	public void setTpCount(String tpCount) {
		this.tpCount = tpCount;
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
	public String getStandardPackage() {
		return standardPackage;
	}
	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}
	public String getOrderBox() {
		return orderBox;
	}
	public void setOrderBox(String orderBox) {
		this.orderBox = orderBox;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getArriveBatch() {
		return arriveBatch;
	}
	public void setArriveBatch(String arriveBatch) {
		this.arriveBatch = arriveBatch;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getSupShipTime() {
		return supShipTime;
	}
	public void setSupShipTime(String supShipTime) {
		this.supShipTime = supShipTime;
	}
	public String getSupRouteName() {
		return supRouteName;
	}
	public void setSupRouteName(String supRouteName) {
		this.supRouteName = supRouteName;
	}
	public String getSupArriveTime() {
		return supArriveTime;
	}
	public void setSupArriveTime(String supArriveTime) {
		this.supArriveTime = supArriveTime;
	}
	public String getSupCarBatch() {
		return supCarBatch;
	}
	public void setSupCarBatch(String supCarBatch) {
		this.supCarBatch = supCarBatch;
	}
	public String getGblShipTime() {
		return gblShipTime;
	}
	public void setGblShipTime(String gblShipTime) {
		this.gblShipTime = gblShipTime;
	}
	public String getGblArriveTime() {
		return gblArriveTime;
	}
	public void setGblArriveTime(String gblArriveTime) {
		this.gblArriveTime = gblArriveTime;
	}
	public String getGblRouteName() {
		return gblRouteName;
	}
	public void setGblRouteName(String gblRouteName) {
		this.gblRouteName = gblRouteName;
	}
	public String getGblCarBatch() {
		return gblCarBatch;
	}
	public void setGblCarBatch(String gblCarBatch) {
		this.gblCarBatch = gblCarBatch;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(String receiveCount) {
		this.receiveCount = receiveCount;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}
	public String getDemander() {
		return demander;
	}
	public void setDemander(String demander) {
		this.demander = demander;
	}
	public String getConNumber() {
		return conNumber;
	}
	public void setConNumber(String conNumber) {
		this.conNumber = conNumber;
	}
	public String getDistriPerson() {
		return distriPerson;
	}
	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	public String getImportStatus() {
		return importStatus;
	}
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getImpIp() {
		return impIp;
	}
	public void setImpIp(String impIp) {
		this.impIp = impIp;
	}
	public String getImportStatusStr() {
		return importStatusStr;
	}
	public void setImportStatusStr(String importStatusStr) {
		this.importStatusStr = importStatusStr;
	}
	public String getCheckResultStr() {
		return checkResultStr;
	}
	public void setCheckResultStr(String checkResultStr) {
		this.checkResultStr = checkResultStr;
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
}
