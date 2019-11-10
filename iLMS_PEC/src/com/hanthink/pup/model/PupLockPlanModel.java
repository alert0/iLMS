package com.hanthink.pup.model;

import com.hotent.base.core.model.AbstractModel;
/**
 * <pre> 
 * 功能描述:锁定取货计划维护实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月25日上午9:49:16
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class PupLockPlanModel extends AbstractModel<String>{

	private static final long serialVersionUID = 3517482916027706085L;
	
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
	/** 卸货地点 */
	private String unloadPlace;
	/** 计算队列 */
	private String unloadPort;
	/** 订单物流模式*/
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
	/** 工作日 */
	private String workday;
	/** 取货日期 */
	private String pickDate;
	/** 取货时间 */
	private String pickTime;
	/** 到货日期 */
	private String arriveDate;
	/** 到货时间 */
	private String arriveTime;
	/** 装配日期 */
	private String assembleDate;
	/** 装配时间 */
	private String assembleTime;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/** 最后修改人 */
	private String lastModifiedUser;
	/** 最后修改时间 */
	private String lastModifiedTime;
	
	/** 查询条件开始时间 */
	private String pickDateStart;
	/** 查询条件结束时间 */
	private String pickDateEnd;
	
	
	public String getPickDateStart() {
		return pickDateStart;
	}
	public void setPickDateStart(String pickDateStart) {
		this.pickDateStart = pickDateStart;
	}
	public String getPickDateEnd() {
		return pickDateEnd;
	}
	public void setPickDateEnd(String pickDateEnd) {
		this.pickDateEnd = pickDateEnd;
	}
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
	public String getUnloadPlace() {
		return unloadPlace;
	}
	public void setUnloadPlace(String unloadPlace) {
		this.unloadPlace = unloadPlace;
	}
	public String getUnloadPort() {
		return unloadPort;
	}
	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
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
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
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
}
