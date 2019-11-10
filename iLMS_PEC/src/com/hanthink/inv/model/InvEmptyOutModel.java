package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;
/**
 * <pre> 
 * 功能描述:空容器出库指示数据实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月18日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class InvEmptyOutModel extends AbstractModel<String>{

	private static final long serialVersionUID = -3130781123149086567L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂 */
	private String factoryCode;
	/** 供应商代码 */
	private String supplierNo;
	/** 供应商名称 */
	private String supplierName;
	/** 路线代码 */
	private String carType;
	/** 路线代码 */
	private String routeCode;
	/** 累计车次 */
	private String totalCarNo;
	/** 箱种 */
	private String boxType;
	/** 出库数量 */
	private String outQTY;
	/** 取货时间 */
	private String pickupTime;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
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
	public String getTotalCarNo() {
		return totalCarNo;
	}
	public void setTotalCarNo(String totalCarNo) {
		this.totalCarNo = totalCarNo;
	}
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public String getOutQTY() {
		return outQTY;
	}
	public void setOutQTY(String outQTY) {
		this.outQTY = outQTY;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}	
}
