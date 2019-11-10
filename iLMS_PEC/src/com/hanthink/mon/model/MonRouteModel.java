package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 站台调度Model
 * @author WY
 *
 */
public class MonRouteModel extends AbstractModel<String>{

	private static final long serialVersionUID = 4843548741968260031L;
	
	private String id;
	
	/**
	 * 站台
	 */
	private String plateForm;
	
	/**
	 * 路线
	 */
	private String routeCode;
	
	/**
	 * 卸货开始时间
	 */
	private String downStartTime;
	
	/**
	 * 卸货完成时间
	 */
	private String downEndTime;
	
	/**
	 * 备空箱开始时间
	 */
	private String upStartTime;
	
	/**
	 * 备空箱完成时间
	 */	
	private String upEndTime;
	
	/**
	 * 转移时间
	 */
	private String inTransTime;
	
	/**
	 * 卸货时间
	 */
	private String inUnloadTime;
	
	/**
	 * 返空装箱时间
	 */
	private String inEmptyTime;
	
	/**
	 * 是否往后推迟的标识 0标识为未推迟,1标识为推迟
	 */
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDownStartTime() {
		return downStartTime;
	}
	public void setDownStartTime(String downStartTime) {
		this.downStartTime = downStartTime;
	}
	public String getDownEndTime() {
		return downEndTime;
	}
	public void setDownEndTime(String downEndTime) {
		this.downEndTime = downEndTime;
	}
	public String getUpStartTime() {
		return upStartTime;
	}
	public void setUpStartTime(String upStartTime) {
		this.upStartTime = upStartTime;
	}
	public String getUpEndTime() {
		return upEndTime;
	}
	public void setUpEndTime(String upEndTime) {
		this.upEndTime = upEndTime;
	}
	public String getPlateForm() {
		return plateForm;
	}
	public void setPlateForm(String plateForm) {
		this.plateForm = plateForm;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getInTransTime() {
		return inTransTime;
	}
	public void setInTransTime(String inTransTime) {
		this.inTransTime = inTransTime;
	}
	public String getInUnloadTime() {
		return inUnloadTime;
	}
	public void setInUnloadTime(String inUnloadTime) {
		this.inUnloadTime = inUnloadTime;
	}
	public String getInEmptyTime() {
		return inEmptyTime;
	}
	public void setInEmptyTime(String inEmptyTime) {
		this.inEmptyTime = inEmptyTime;
	}
	@Override
	public String toString() {
		return "MonRouteModel [id=" + id + ", plateForm=" + plateForm
				+ ", routeCode=" + routeCode + ", downStartTime="
				+ downStartTime + ", downEndTime=" + downEndTime
				+ ", upStartTime=" + upStartTime + ", upEndTime=" + upEndTime
				+ ", inTransTime=" + inTransTime + ", inUnloadTime="
				+ inUnloadTime + ", inEmptyTime=" + inEmptyTime + ", flag="
				+ flag + "]";
	}
	
}
