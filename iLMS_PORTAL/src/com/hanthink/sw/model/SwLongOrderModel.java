package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

public class SwLongOrderModel extends AbstractModel<String>{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -3758655297038134264L;
	/**id**/
	private String id;
	private String []idArr;
	/**版本号**/
	private String version;
	/**订单号**/
	private String orderNo;
	/**当前月**/
	private String currentMonth;
	/**对象月**/
	private String objMonth;
	/**订单周期**/
	private String orderPeriod;
	/**预测周期**/
	private String forecastPeriod;
	/**预测对象月1**/
	private String forecastFirst;
	/**预测对象月1数量**/
	private String forecastFirstNum;
	/**预测对象月2**/
	private String forecastSecond;
	/**预测对象月2数量**/
	private String forecastSecondNum;
	/**预测对象月3**/
	private String forecastThird;
	/**预测对象月3数量**/
	private String forecastThirdNum;
	/**创建时间**/
	private String creationTime;
	/**创建人**/
	private String creationUser;
	/**最后修改人**/
	private String lastModifiedUser;
	/**最后修改时间**/
	private String lastModifiedTime;
	/**反馈状态**/
	private String feedbackStatus;
	/**反馈备注**/
	private String feedbackRemark;
	
	/***以下为明细字段*****************************/
	/**零件号**/
	private String partNo;
	/**车型**/
	private String carType;
	/**供应商代码**/
	private String supplierNo;
	/**ERP下发的出货地**/
	private String supFactory;
	/**收容数**/
	private String requireNum;
	/**订单收容数**/
	private String orderRequireNum;
	/**单位**/
	private String partUnit;
	/**订单个数**/
	private String orderNum;
	/**订单箱数**/
	private String orderBox;
	/**本次发货数量**/
	private String currentDelivQty;
	/**已发货数量**/
	private String totalDelivQty;
	/**已到货数量**/
	private String arriveNum;
	
	/*********************************/
	/**零件简号**/
	private String partShortNo;
	/**零件名称**/
	private String partNameCn;
	
	/**供应商名称**/
	private String supplierName;
	
	private String factoryCode;
	
	/**收货数量**/
	private String receiveQty;
	private String receiveStatus;
	/**打印用到字段****************************/
	private String rowId;
	private String pageCount;
	private String labelCount;
	private String hmsOrder;
	private String partName;
	private String unloadPort;
	private String standartPackage;
	private String boxesNum;
	private String purchaseNo;
	private String arriveDate;
	private String orderTypeName;
	private String purchaseRowNo;
	private String depotNo;
	private String orderQty;
	private String totalRecQty;
	private String barCode;
	
	private String distriPerson;
	private String prepareProductSeqno;
	private String location;
	private String orderTimeHhmi;
	private String orderTimeYmd;
	private String kbzs;
	private String distriProductSeqno;
	private String arriveTime;
	private String purchaseOrderno;
	private String arriveProductSeqno;
	private String QRCode;
	private String prepareAddress;
	private String preparePerson;
	
	private String userType;
	private String userId;
	
	private String value;
	private String label;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}
	public String getObjMonth() {
		return objMonth;
	}
	public void setObjMonth(String objMonth) {
		this.objMonth = objMonth;
	}
	public String getOrderPeriod() {
		return orderPeriod;
	}
	public void setOrderPeriod(String orderPeriod) {
		this.orderPeriod = orderPeriod;
	}
	public String getForecastPeriod() {
		return forecastPeriod;
	}
	public void setForecastPeriod(String forecastPeriod) {
		this.forecastPeriod = forecastPeriod;
	}

	public String getForecastFirst() {
		return forecastFirst;
	}
	public void setForecastFirst(String forecastFirst) {
		this.forecastFirst = forecastFirst;
	}
	public String getForecastFirstNum() {
		return forecastFirstNum;
	}
	public void setForecastFirstNum(String forecastFirstNum) {
		this.forecastFirstNum = forecastFirstNum;
	}
	public String getForecastSecond() {
		return forecastSecond;
	}
	public void setForecastSecond(String forecastSecond) {
		this.forecastSecond = forecastSecond;
	}
	public String getForecastSecondNum() {
		return forecastSecondNum;
	}
	public void setForecastSecondNum(String forecastSecondNum) {
		this.forecastSecondNum = forecastSecondNum;
	}
	public String getForecastThird() {
		return forecastThird;
	}
	public void setForecastThird(String forecastThird) {
		this.forecastThird = forecastThird;
	}
	public String getForecastThirdNum() {
		return forecastThirdNum;
	}
	public void setForecastThirdNum(String forecastThirdNum) {
		this.forecastThirdNum = forecastThirdNum;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
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
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
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
	public String getRequireNum() {
		return requireNum;
	}
	public void setRequireNum(String requireNum) {
		this.requireNum = requireNum;
	}
	public String getOrderRequireNum() {
		return orderRequireNum;
	}
	public void setOrderRequireNum(String orderRequireNum) {
		this.orderRequireNum = orderRequireNum;
	}
	public String getPartUnit() {
		return partUnit;
	}
	public void setPartUnit(String partUnit) {
		this.partUnit = partUnit;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderBox() {
		return orderBox;
	}
	public void setOrderBox(String orderBox) {
		this.orderBox = orderBox;
	}
	public String getCurrentDelivQty() {
		return currentDelivQty;
	}
	public void setCurrentDelivQty(String currentDelivQty) {
		this.currentDelivQty = currentDelivQty;
	}
	public String getTotalDelivQty() {
		return totalDelivQty;
	}
	public void setTotalDelivQty(String totalDelivQty) {
		this.totalDelivQty = totalDelivQty;
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
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getLabelCount() {
		return labelCount;
	}
	public void setLabelCount(String labelCount) {
		this.labelCount = labelCount;
	}
	public String[] getIdArr() {
		return idArr;
	}
	public void setIdArr(String[] idArr) {
		this.idArr = idArr;
	}
	public String getHmsOrder() {
		return hmsOrder;
	}
	public void setHmsOrder(String hmsOrder) {
		this.hmsOrder = hmsOrder;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getUnloadPort() {
		return unloadPort;
	}
	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}
	public String getStandartPackage() {
		return standartPackage;
	}
	public void setStandartPackage(String standartPackage) {
		this.standartPackage = standartPackage;
	}
	public String getBoxesNum() {
		return boxesNum;
	}
	public void setBoxesNum(String boxesNum) {
		this.boxesNum = boxesNum;
	}
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getOrderTypeName() {
		return orderTypeName;
	}
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}
	public String getPurchaseRowNo() {
		return purchaseRowNo;
	}
	public void setPurchaseRowNo(String purchaseRowNo) {
		this.purchaseRowNo = purchaseRowNo;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
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
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getDistriPerson() {
		return distriPerson;
	}
	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}
	public String getPrepareProductSeqno() {
		return prepareProductSeqno;
	}
	public void setPrepareProductSeqno(String prepareProductSeqno) {
		this.prepareProductSeqno = prepareProductSeqno;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOrderTimeHhmi() {
		return orderTimeHhmi;
	}
	public void setOrderTimeHhmi(String orderTimeHhmi) {
		this.orderTimeHhmi = orderTimeHhmi;
	}
	public String getOrderTimeYmd() {
		return orderTimeYmd;
	}
	public void setOrderTimeYmd(String orderTimeYmd) {
		this.orderTimeYmd = orderTimeYmd;
	}
	public String getKbzs() {
		return kbzs;
	}
	public void setKbzs(String kbzs) {
		this.kbzs = kbzs;
	}
	public String getDistriProductSeqno() {
		return distriProductSeqno;
	}
	public void setDistriProductSeqno(String distriProductSeqno) {
		this.distriProductSeqno = distriProductSeqno;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getPurchaseOrderno() {
		return purchaseOrderno;
	}
	public void setPurchaseOrderno(String purchaseOrderno) {
		this.purchaseOrderno = purchaseOrderno;
	}
	public String getArriveProductSeqno() {
		return arriveProductSeqno;
	}
	public void setArriveProductSeqno(String arriveProductSeqno) {
		this.arriveProductSeqno = arriveProductSeqno;
	}
	public String getQRCode() {
		return QRCode;
	}
	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}
	public String getPrepareAddress() {
		return prepareAddress;
	}
	public void setPrepareAddress(String prepareAddress) {
		this.prepareAddress = prepareAddress;
	}
	public String getPreparePerson() {
		return preparePerson;
	}
	public void setPreparePerson(String preparePerson) {
		this.preparePerson = preparePerson;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getFeedbackStatus() {
		return feedbackStatus;
	}
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
	public String getFeedbackRemark() {
		return feedbackRemark;
	}
	public void setFeedbackRemark(String feedbackRemark) {
		this.feedbackRemark = feedbackRemark;
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
	public String getArriveNum() {
		return arriveNum;
	}
	public void setArriveNum(String arriveNum) {
		this.arriveNum = arriveNum;
	}
	public String getReceiveQty() {
		return receiveQty;
	}
	public void setReceiveQty(String receiveQty) {
		this.receiveQty = receiveQty;
	}
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	
}
