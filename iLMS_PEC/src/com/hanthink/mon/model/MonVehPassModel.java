package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：MM_PUB_VEH_PASS 实体对象
 * </pre>
 */
public class MonVehPassModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7270974271720993670L;

	protected String id;
	
	protected String factoryCode;
	/**
	 * 车间
	 */
	protected String workCenter;
	/**
	 * 生产订单号
	 */
	protected String orderNo;
	/**
	 * 车型
	 */
	protected String modelCode;
	/**
	 * VIN
	 */
	protected String vin;
	/**
	 * 最近通过站点
	 */
	protected String stationCode;
	/**
	 * 站点通过时间
	 */
	protected String passTime;
	/**
	 * 生产阶段
	 */
	protected String phase;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getPassTime() {
		return passTime;
	}
	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	
	
}
