package com.hanthink.pup.model;

import com.hotent.base.core.model.AbstractModel;
/**
 * <pre> 
 * 功能描述:版本比对数据实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月28日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class PupVersionModel extends AbstractModel<String>{

	private static final long serialVersionUID = -2376019278643734145L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String factoryCode;
	/** 区域 */
	private String area;
	/** 卸货地点 */
	private String unloadPlace;
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
	/** 新工作日 */
	private String newWorkday;
	/** 旧工作日 */
	private String oldWorkday;
	/** 工作日差异 */
	private String workdayGap;
	/** 当日车次 */
	private String todayNo;
	/** 计划取货日期 */
	private String pickDate;
	/** 计划取货时间 */
	private String pickTime;
	/** 计划到货日期 */
	private String arriveDate;
	/** 计划到货时间 */
	private String arriveTime;
	/** 原计划装配日期 */
	private String newAssembleDate;
	/** 新计划装配日期 */
	private String oldAssembleDate;
	/** 原计划装配时间 */
	private String newAssembleTime;
	/** 新计划装配时间 */
	private String oldAssembleTime;
	
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
	public String getNewWorkday() {
		return newWorkday;
	}
	public void setNewWorkday(String newWorkday) {
		this.newWorkday = newWorkday;
	}
	public String getOldWorkday() {
		return oldWorkday;
	}
	public void setOldWorkday(String oldWorkday) {
		this.oldWorkday = oldWorkday;
	}
	public String getWorkdayGap() {
		return workdayGap;
	}
	public void setWorkdayGap(String workdayGap) {
		this.workdayGap = workdayGap;
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
	public String getNewAssembleDate() {
		return newAssembleDate;
	}
	public void setNewAssembleDate(String newAssembleDate) {
		this.newAssembleDate = newAssembleDate;
	}
	public String getOldAssembleDate() {
		return oldAssembleDate;
	}
	public void setOldAssembleDate(String oldAssembleDate) {
		this.oldAssembleDate = oldAssembleDate;
	}
	public String getNewAssembleTime() {
		return newAssembleTime;
	}
	public void setNewAssembleTime(String newAssembleTime) {
		this.newAssembleTime = newAssembleTime;
	}
	public String getOldAssembleTime() {
		return oldAssembleTime;
	}
	public void setOldAssembleTime(String oldAssembleTime) {
		this.oldAssembleTime = oldAssembleTime;
	}
}
