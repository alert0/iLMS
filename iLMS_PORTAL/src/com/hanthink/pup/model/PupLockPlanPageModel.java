package com.hanthink.pup.model;
/**
 * <pre> 
 * 功能描述:锁定取货计划维护页面查询数据封装类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月25日上午9:50:27
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class PupLockPlanPageModel {
	private String routeCode;
	private String area;
	private String carType;
	private String pickDateStart;
	private String pickDateEnd;
	
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
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
	@Override
	public String toString() {
		return "PupLockPlanPageModel [routeCode=" + routeCode + ", carType=" + carType + ", pickDateStart="
				+ pickDateStart + ", pickDateEnd=" + pickDateEnd + "]";
	}
}
