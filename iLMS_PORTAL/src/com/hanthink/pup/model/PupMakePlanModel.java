package com.hanthink.pup.model;

import com.hotent.base.core.model.AbstractModel;
/**
 * <pre> 
 * 功能描述:取货计划生成业务实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月27日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class PupMakePlanModel extends AbstractModel<String> {

	private static final long serialVersionUID = 7613813450979889083L;
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂代码 */
	private String factoryCode;
	/** 取货区域 */
	private String area;
	/** 订单物流模式 */
	private String pickupType;
	/** 车型 */
	private String carType;
	/** 集货线路 */
	private String routeCode;
	/** 累计车次 */
	private String totalNo;
	/** 合并车次 */
	private String mergeNo;
	/** 当日车次 */
	private String todayNo;
	/** 取货日期 */
	private String pickDate;
	/** 取货时间 */
	private String pickTime;
	/** 到达日期 */
	private String arriveDate;
	/** 到达时间 */
	private String arriveTime;
	/** 装配日期 */
	private String assembleDate;
	/** 装配时间 */
	private String assembleTime;
	/** 调整取货日期 */
	private String adjPickupDate;
	/** 调整取货时间 */
	private String adjPickupTime;
	/** 卸货地点 */
	private String unloadPlace;
	/** 起始sortid */
	private String startSortId;
	/** 结束sortid */
	private String ensSortId;
	/** 提前台套数 */
	private String advanceArrNum;
	/** 运输时间 */
	private String trainsTime;
	/** 取货周期 */
	private String pickCycle;
	/** 最早到货时间 */
	private String firstArriveTime;
	/** 特殊到货时间 */
	private String speArrTime;
	private String flag;
	private String assemleId;
	/** 本次台套数 */
	private String tokenNum;
	/** 是否拼车 */
	private String isMerge;
	/** 合并基准 */
	private String mergeNum;
	/** 工作日 */
	private String workday;
	/** 创建时间 */
	private String creationTime;
	/** dockrange范围 */
	private String drSortIdStart;
	/** dockrange范围 */
	private String drSortIdEnd;
	/** 物流单号 */
	private String orderNo;
	/** 订单号 */
	private String purchaseNo;
	/** 出货地代码 */
	private String supFactory;
	/** 计算队列 */
	private String unloadPort;
	/** 工作日 */
	private String workDay;
	/** 外物流管理员/用户ID */
	private String wwlManager;
	/** 内物流管理员 */
	private String nwlManager;
	/** 收货日期 */
	private String recDate;
	/** 仓库代码 */
	private String wareCode;
	/** A班收货 */
	private String recShiftA;
	/** A班收货 */
	private String recShiftB;
	/** 物流标识 */
	private String logisticFlag;
	/** 供应商代码 */
	private String supplierNo;
	/** 供应商名称 */
	private String supplierName;
	/** 订单类型 */
	private String orderType;
	/** 确认天数 */
	private String confirmDay;
	/** 订购用途 */
	private String purposes;
	
	/** 订单履历单号 */
	private String planOrderNo;
	/** 零件号 */
	private String partNo;
	/** 供应商分组号 */
	private String groupId;
	/** linesiderange范围 */
	private String lrSortIdStart;
	/** linesiderange范围 */
	private String lrSortIdEnd;
	/** 订单数量 */
	private String orderNum;
	/** 订单总数 */
	private String totalOrderNum;
	/** 订单发布日期 */
	private String issueDate;
	/** 订单状态 */
	private String orderStatus;
	/** 车型区分 */
	private String carTypeDist;

	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPickupType() {
		return pickupType;
	}
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getTotalNo() {
		return totalNo;
	}
	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}
	public String getMergeNo() {
		return mergeNo;
	}
	public void setMergeNo(String mergeNo) {
		this.mergeNo = mergeNo;
	}
	public String getTodayNo() {
		return todayNo;
	}
	public void setTodayNo(String todayNo) {
		this.todayNo = todayNo;
	}
	public String getPickDate() {
		return pickDate;
	}
	public void setPickDate(String pickDate) {
		this.pickDate = pickDate;
	}
	public String getPickTime() {
		return pickTime;
	}
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getAssembleDate() {
		return assembleDate;
	}
	public void setAssembleDate(String assembleDate) {
		this.assembleDate = assembleDate;
	}
	public String getAssembleTime() {
		return assembleTime;
	}
	public void setAssembleTime(String assembleTime) {
		this.assembleTime = assembleTime;
	}
	public String getAdjPickupDate() {
		return adjPickupDate;
	}
	public void setAdjPickupDate(String adjPickupDate) {
		this.adjPickupDate = adjPickupDate;
	}
	public String getAdjPickupTime() {
		return adjPickupTime;
	}
	public void setAdjPickupTime(String adjPickupTime) {
		this.adjPickupTime = adjPickupTime;
	}
	public String getUnloadPlace() {
		return unloadPlace;
	}
	public void setUnloadPlace(String unloadPlace) {
		this.unloadPlace = unloadPlace;
	}
	public String getStartSortId() {
		return startSortId;
	}
	public void setStartSortId(String startSortId) {
		this.startSortId = startSortId;
	}
	public String getEnsSortId() {
		return ensSortId;
	}
	public void setEnsSortId(String ensSortId) {
		this.ensSortId = ensSortId;
	}
	public String getAdvanceArrNum() {
		return advanceArrNum;
	}
	public void setAdvanceArrNum(String advanceArrNum) {
		this.advanceArrNum = advanceArrNum;
	}
	public String getTrainsTime() {
		return trainsTime;
	}
	public void setTrainsTime(String trainsTime) {
		this.trainsTime = trainsTime;
	}
	public String getPickCycle() {
		return pickCycle;
	}
	public void setPickCycle(String pickCycle) {
		this.pickCycle = pickCycle;
	}
	public String getFirstArriveTime() {
		return firstArriveTime;
	}
	public void setFirstArriveTime(String firstArriveTime) {
		this.firstArriveTime = firstArriveTime;
	}
	public String getSpeArrTime() {
		return speArrTime;
	}
	public void setSpeArrTime(String speArrTime) {
		this.speArrTime = speArrTime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAssemleId() {
		return assemleId;
	}
	public void setAssemleId(String assemleId) {
		this.assemleId = assemleId;
	}
	public String getTokenNum() {
		return tokenNum;
	}
	public void setTokenNum(String tokenNum) {
		this.tokenNum = tokenNum;
	}
	public String getMergeNum() {
		return mergeNum;
	}
	public void setMergeNum(String mergeNum) {
		this.mergeNum = mergeNum;
	}
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getDrSortIdStart() {
		return drSortIdStart;
	}
	public void setDrSortIdStart(String drSortIdStart) {
		this.drSortIdStart = drSortIdStart;
	}
	public String getDrSortIdEnd() {
		return drSortIdEnd;
	}
	public void setDrSortIdEnd(String drSortIdEnd) {
		this.drSortIdEnd = drSortIdEnd;
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
	public String getSupFactory() {
		return supFactory;
	}
	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}
	public String getUnloadPort() {
		return unloadPort;
	}
	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}
	public String getWorkDay() {
		return workDay;
	}
	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}
	public String getWwlManager() {
		return wwlManager;
	}
	public void setWwlManager(String wwlManager) {
		this.wwlManager = wwlManager;
	}
	public String getNwlManager() {
		return nwlManager;
	}
	public void setNwlManager(String nwlManager) {
		this.nwlManager = nwlManager;
	}
	public String getRecDate() {
		return recDate;
	}
	public void setRecDate(String recDate) {
		this.recDate = recDate;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public String getRecShiftA() {
		return recShiftA;
	}
	public void setRecShiftA(String recShiftA) {
		this.recShiftA = recShiftA;
	}
	public String getRecShiftB() {
		return recShiftB;
	}
	public void setRecShiftB(String recShiftB) {
		this.recShiftB = recShiftB;
	}
	public String getLogisticFlag() {
		return logisticFlag;
	}
	public void setLogisticFlag(String logisticFlag) {
		this.logisticFlag = logisticFlag;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getConfirmDay() {
		return confirmDay;
	}
	public void setConfirmDay(String confirmDay) {
		this.confirmDay = confirmDay;
	}
	public String getPurposes() {
		return purposes;
	}
	public void setPurposes(String purposes) {
		this.purposes = purposes;
	}
	public String getPlanOrderNo() {
		return planOrderNo;
	}
	public void setPlanOrderNo(String planOrderNo) {
		this.planOrderNo = planOrderNo;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getLrSortIdStart() {
		return lrSortIdStart;
	}
	public void setLrSortIdStart(String lrSortIdStart) {
		this.lrSortIdStart = lrSortIdStart;
	}
	public String getLrSortIdEnd() {
		return lrSortIdEnd;
	}
	public void setLrSortIdEnd(String lrSortIdEnd) {
		this.lrSortIdEnd = lrSortIdEnd;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getTotalOrderNum() {
		return totalOrderNum;
	}
	public void setTotalOrderNum(String totalOrderNum) {
		this.totalOrderNum = totalOrderNum;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCarTypeDist() {
		return carTypeDist;
	}
	public void setCarTypeDist(String carTypeDist) {
		this.carTypeDist = carTypeDist;
	}
	public String getIsMerge() {
		return isMerge;
	}
	public void setIsMerge(String isMerge) {
		this.isMerge = isMerge;
	}
}
