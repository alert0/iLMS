package com.hanthink.pup.model;
/**
 * <pre> 
 * 功能描述:版本比对页面数据封装实体对象
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月28日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class PupVersionPageModel {
	/** 线路代码 */
	private String routeCode;
	/** 车型代码 */
	private String carType;
	/** 差异标识 */
	private String diffFlag;
	
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getDiffFlag() {
		return diffFlag;
	}
	public void setDiffFlag(String diffFlag) {
		this.diffFlag = diffFlag;
	}
}
