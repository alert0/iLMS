package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 合资车收货数据导入实体类
 * <pre> 
 * 功能描述:
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2019年8月20日下午2:40
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class SwVentureReceiveModel extends AbstractModel<String>{

	private static final long serialVersionUID = -8978806762985282540L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 用户类型,数据权限
	 */
	private String userType;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 入库单号
	 */
	private String recNo;
	/**
	 * 工厂
	 */
	private String factoryCode;
	/**
	 * 订购方订单号
	 */
	private String ordOrderNo;
	/**
	 * 订购方订单行号
	 */
	private String ordOrderRowNo;
	/**
	 * 零件号(品番)
	 */
	private String partNo;
	/**
	 * 零件名称
	 */
	private String partName;
	/**
	 * 简号(背番)
	 */
	private String partShortNo;
	/**
	 * 供应商代码
	 */
	private String supplierNo;
	/**
	 * 订购数量
	 */
	private String orderQty;
	/**
	 * 已收货数量
	 */
	private String totalRecQty;
	/**
	 * 到货仓库
	 */
	private String depotNo;
	/**
	 * 收货时间
	 */
	private String recTime;
	/**
	 * 收货状态
	 */
	private String receiveStatus;
	/**
	 * 订购方代码
	 */
	private String ordPlace;
	/**
	 * 本次收货数量
	 */
	private String recQty;
	/**
	 * 收货次数
	 */
	private String recCount;
	/**
	 * uuid
	 */
	private String impUUID;
	/**
	 * 审核状态
	 */
	private String checkStatus;
	/**
	 * 审核状态
	 */
	private String excelCheckStatus;
	/**
	 * 校验结果
	 */
	private String checkResult;
	/**
	 * 校验信息
	 */
	private String checkInfo;
	/**
	 * 导入状态
	 */
	private String importStatus;
	/**
	 * 操作类型
	 */
	private String opeType;
	/**
	 * 创建人
	 */
	private String creationUser;
	/**
	 * 创建时间
	 */
	private String creationTime;
	/**
	 * 创建时间开始时间
	 */
	private String creationTimeStr;
	/**
	 * 创建时间结束时间
	 */
	private String creationTimeEnd;
	/**
	 * Excel导出导入状态
	 */
	private String excelImportStatus;
	/**
	 * Excel导出校验状态
	 */
	private String excelCheckResult;
	/**
	 * 操作IP地址
	 */
	private String opeIp;
	
	
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
	public String getRecNo() {
		return recNo;
	}
	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getOrdOrderNo() {
		return ordOrderNo;
	}
	public void setOrdOrderNo(String ordOrderNo) {
		this.ordOrderNo = ordOrderNo;
	}
	public String getOrdOrderRowNo() {
		return ordOrderRowNo;
	}
	public void setOrdOrderRowNo(String ordOrderRowNo) {
		this.ordOrderRowNo = ordOrderRowNo;
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
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getTotalRecQty() {
		return totalRecQty;
	}
	public void setTotalRecQty(String totalRecQty) {
		this.totalRecQty = totalRecQty;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}
	public String getRecTime() {
		return recTime;
	}
	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public String getOrdPlace() {
		return ordPlace;
	}
	public void setOrdPlace(String ordPlace) {
		this.ordPlace = ordPlace;
	}
	public String getRecQty() {
		return recQty;
	}
	public void setRecQty(String recQty) {
		this.recQty = recQty;
	}
	public String getRecCount() {
		return recCount;
	}
	public void setRecCount(String recCount) {
		this.recCount = recCount;
	}
	public String getImpUUID() {
		return impUUID;
	}
	public void setImpUUID(String impUUID) {
		this.impUUID = impUUID;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getExcelCheckStatus() {
		return excelCheckStatus;
	}
	public void setExcelCheckStatus(String excelCheckStatus) {
		this.excelCheckStatus = excelCheckStatus;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
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
	public String getCreationTimeStr() {
		return creationTimeStr;
	}
	public void setCreationTimeStr(String creationTimeStr) {
		this.creationTimeStr = creationTimeStr;
	}
	public String getCreationTimeEnd() {
		return creationTimeEnd;
	}
	public void setCreationTimeEnd(String creationTimeEnd) {
		this.creationTimeEnd = creationTimeEnd;
	}
	public String getExcelImportStatus() {
		return excelImportStatus;
	}
	public void setExcelImportStatus(String excelImportStatus) {
		this.excelImportStatus = excelImportStatus;
	}
	public String getExcelCheckResult() {
		return excelCheckResult;
	}
	public void setExcelCheckResult(String excelCheckResult) {
		this.excelCheckResult = excelCheckResult;
	}
	public String getOpeIp() {
		return opeIp;
	}
	public void setOpeIp(String opeIp) {
		this.opeIp = opeIp;
	}
}
