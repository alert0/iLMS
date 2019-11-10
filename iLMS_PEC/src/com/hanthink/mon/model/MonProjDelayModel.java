package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

public class MonProjDelayModel extends AbstractModel<String>{

	private static final long serialVersionUID = -8561878539269541066L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 显示类型 */
	private String opeType;
	/** 工程名 */
	private String projName;
	/** 延迟时间 */
	private String delayTime;
	/** 验收延迟数 */
	private String checkCount;
	/** 备件延迟数 */
	private String prePareCount;
	/** PC备货延迟数 */
	private String distCount;
	/** 空箱返还延迟数 */
	private String emptCount;
	/** 工厂 */
	private String factoryCode;
	/** 车间 */
	private String workCenter;
	
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
	public String getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(String checkCount) {
		this.checkCount = checkCount;
	}
	public String getPrePareCount() {
		return prePareCount;
	}
	public void setPrePareCount(String prePareCount) {
		this.prePareCount = prePareCount;
	}
	public String getDistCount() {
		return distCount;
	}
	public void setDistCount(String distCount) {
		this.distCount = distCount;
	}
	public String getEmptCount() {
		return emptCount;
	}
	public void setEmptCount(String emptCount) {
		this.emptCount = emptCount;
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
}
