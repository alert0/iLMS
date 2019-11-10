package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

public class SwVentureForecastModel extends AbstractModel<String>{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 456282139604439053L;
	
	/**主键**/
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**用户id**/
	private String userId;
	/**用户类型**/
	private String userType;
	
	/**工厂**/
	private String factoryCode;
	/**预测类型**/
	private String foreType;
	/**预测类型**/
	private String foreTypeStr;
	/**合并后版本**/
	private String version;
	/**目标版本号**/
	private String erpVersion;
	/**订购方版本号**/
	private String jvVersion;
	/**发布状态**/
	private String releaseStatus;
	/**发布状态**/
	private String releaseStatusStr;
	/**车型**/
	private String modelCode;
	/**需求到货日期**/
	private String planDelivery;
	/**需求到货日期开始**/
	private String planDeliveryStar;
	/**需求到货日期结束**/
	private String planDeliveryEnd;
	/**到货日期**/
	private String arriveTime;
	/**需求到货日期(查询条件开始)**/
	private String arriveStarTime;
	/**需求到货日期(查询条件结束)**/
	private String arriveEndTime;

	/**生产阶段**/
	private String phase;
	/**生产阶段**/
	private String phaseStr;
	/**卸货口**/
	private String unloadPort;
	/**零件号**/
	private String partNo;
	/**简号**/
	private String partShortNo;
	/**需求数量**/
	private String orderQty;
	/**供应商代码**/
	private String supplierNo;
	/**供应商名称**/
	private String supplierName;
	/**总成件供应商**/
	private String parentSupplier;
	/**出货地址**/
	private String supFactoryAddr;
	/**出货地代码**/
	private String supFactory;
	/**创建人**/
	private String creationUser;
	/**最后修改人**/
	private String lastModifiedUser;
	/**创建时间**/
	private String creationTime;
	/**创建时间开始**/
	private String creationTimeStar;
	/**创建时间结束**/
	private String creationTimeEnd;
	/**零件名称**/
	private String partNameCn;
	
	/**开始日期**/
	private String  startDate;
	/**结束日期**/
	private String endDate;
	
	/**发布周**/
	private String publishWeek;
	/**对象周**/
	private String objWeek;
	
	/**发布月份**/
	private String publishMonth;
	
	/**对象月份**/
	private String objMonth;
	
	/**W1周**/
	private String weekOne;
	/**W2周**/
	private String weekTwo;
	/**W+3周**/
	private String weekThree;
	/**W+4周**/
	private String weekFour;
	
	/**N月数量**/
	private String monthOne;
	/**N+1月数量**/
	private String monthTwo;
	/**N+2月数量**/
	private String monthThree;
	
	private String value;
	private String label;

	/**提前取货时间**/
	private String advanceTime;
	/**订购方代码**/
	private String jvPlace;
	/**erp版本号**/
	private String erpVersionList;
	/**订购方版本号**/
	private String[] jvVersionList;
	
	/** 导入UUID */
	private String uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String checkResult;
	/** 检查结果信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
	
	private String importStatusStr;
	private String checkResultStr;

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getForeType() {
		return foreType;
	}

	public void setForeType(String foreType) {
		this.foreType = foreType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getPlanDelivery() {
		return planDelivery;
	}

	public void setPlanDelivery(String planDelivery) {
		this.planDelivery = planDelivery;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getArriveStarTime() {
		return arriveStarTime;
	}

	public void setArriveStarTime(String arriveStarTime) {
		this.arriveStarTime = arriveStarTime;
	}

	public String getArriveEndTime() {
		return arriveEndTime;
	}

	public void setArriveEndTime(String arriveEndTime) {
		this.arriveEndTime = arriveEndTime;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
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

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
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

	public String getSupFactoryAddr() {
		return supFactoryAddr;
	}

	public void setSupFactoryAddr(String supFactoryAddr) {
		this.supFactoryAddr = supFactoryAddr;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
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

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
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

	public String getAdvanceTime() {
		return advanceTime;
	}

	public void setAdvanceTime(String advanceTime) {
		this.advanceTime = advanceTime;
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

	public String getReleaseStatusStr() {
		return releaseStatusStr;
	}

	public void setReleaseStatusStr(String releaseStatusStr) {
		this.releaseStatusStr = releaseStatusStr;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}


	public String getParentSupplier() {
		return parentSupplier;
	}

	public void setParentSupplier(String parentSupplier) {
		this.parentSupplier = parentSupplier;
	}

	public String getPhaseStr() {
		return phaseStr;
	}

	public void setPhaseStr(String phaseStr) {
		this.phaseStr = phaseStr;
	}

	public String getForeTypeStr() {
		return foreTypeStr;
	}

	public void setForeTypeStr(String foreTypeStr) {
		this.foreTypeStr = foreTypeStr;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPublishWeek() {
		return publishWeek;
	}

	public void setPublishWeek(String publishWeek) {
		this.publishWeek = publishWeek;
	}

	public String getObjWeek() {
		return objWeek;
	}

	public void setObjWeek(String objWeek) {
		this.objWeek = objWeek;
	}

	public String getPublishMonth() {
		return publishMonth;
	}

	public void setPublishMonth(String publishMonth) {
		this.publishMonth = publishMonth;
	}

	public String getObjMonth() {
		return objMonth;
	}

	public void setObjMonth(String objMonth) {
		this.objMonth = objMonth;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getErpVersion() {
		return erpVersion;
	}

	public void setErpVersion(String erpVersion) {
		this.erpVersion = erpVersion;
	}

	public String getJvVersion() {
		return jvVersion;
	}

	public void setJvVersion(String jvVersion) {
		this.jvVersion = jvVersion;
	}

	public String getJvPlace() {
		return jvPlace;
	}

	public void setJvPlace(String jvPlace) {
		this.jvPlace = jvPlace;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPlanDeliveryStar() {
		return planDeliveryStar;
	}

	public void setPlanDeliveryStar(String planDeliveryStar) {
		this.planDeliveryStar = planDeliveryStar;
	}

	public String getPlanDeliveryEnd() {
		return planDeliveryEnd;
	}

	public void setPlanDeliveryEnd(String planDeliveryEnd) {
		this.planDeliveryEnd = planDeliveryEnd;
	}

	public String getCreationTimeStar() {
		return creationTimeStar;
	}

	public void setCreationTimeStar(String creationTimeStar) {
		this.creationTimeStar = creationTimeStar;
	}

	public String getCreationTimeEnd() {
		return creationTimeEnd;
	}

	public void setCreationTimeEnd(String creationTimeEnd) {
		this.creationTimeEnd = creationTimeEnd;
	}

	public String getErpVersionList() {
		return erpVersionList;
	}

	public void setErpVersionList(String erpVersionList) {
		this.erpVersionList = erpVersionList;
	}

	public String[] getJvVersionList() {
		return jvVersionList;
	}

	public void setJvVersionList(String[] jvVersionList) {
		this.jvVersionList = jvVersionList;
	}

	public String getWeekOne() {
		return weekOne;
	}

	public void setWeekOne(String weekOne) {
		this.weekOne = weekOne;
	}

	public String getWeekTwo() {
		return weekTwo;
	}

	public void setWeekTwo(String weekTwo) {
		this.weekTwo = weekTwo;
	}

	public String getWeekThree() {
		return weekThree;
	}

	public void setWeekThree(String weekThree) {
		this.weekThree = weekThree;
	}

	public String getWeekFour() {
		return weekFour;
	}

	public void setWeekFour(String weekFour) {
		this.weekFour = weekFour;
	}

	public String getMonthOne() {
		return monthOne;
	}

	public void setMonthOne(String monthOne) {
		this.monthOne = monthOne;
	}

	public String getMonthTwo() {
		return monthTwo;
	}

	public void setMonthTwo(String monthTwo) {
		this.monthTwo = monthTwo;
	}

	public String getMonthThree() {
		return monthThree;
	}

	public void setMonthThree(String monthThree) {
		this.monthThree = monthThree;
	}
	
}
