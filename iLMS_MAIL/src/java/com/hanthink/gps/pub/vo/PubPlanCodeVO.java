package com.hanthink.gps.pub.vo;

/**
 * @Title: PubPlanCodeVO.java
 * @Package: com.hanthink.gps.pub.vo
 * @Description: MM_PUB_PLAN_CODE实体类----广汽新能源
 * @author dtp
 * @date 2018-11-6
 */
public class PubPlanCodeVO {
	
	/**
	 * 停线时长(min)
	 */
	private Long stopTime;
	
	/**
	 * 信息点
     */
	private String planCode; 
	
	/**
	 * 信息点描述
     */
	private String planCodeDesc; 
	
	/**
	 * 工厂
     */
	private String factoryCode; 
	
	/**
	 * 产线
     */
	private String productionLine; 
	
	/**
	 * 车间
     */
	private String workcenter; 
	
	/**
	 * 工位
     */
	private String stationCode; 
	
	/**
	 * 信息点类型
		SPS：SPS
		JISI：厂外同步
		JISO：厂内同步
		JIT：是     
	 */
	private String planCodeType; 
	
	/**
	 * 是否自动推算
	   0：否
	   1：是     
	*/
	private String isAutoExec; 
	
	/**
	 * 推算状态
	   0：未推算
	   1：推算中     
	 */
	private String execState; 
	
	/**
	 * 最近推算时间
     */
	private String lastExecTime; 
	
	/**
	 * 看板代码
     */
	private String kbCode; 
	
	/**
	 * 是否启用数据字典类型“TRUE_FALSE”     
	 */
	private String isEnable; 
	
	/**
	 * 是否显示数据字典类型“TRUE_FALSE”     
	 */
	private String isShow; 
	
	/**
	 * 创建人
     */
	private String creationUser; 
	
	/**
	 * 创建时间
     */
	private String creationTime; 
	
	/**
	 * 最后修改用户
     */
	private String lastModifiedUser; 
	
	/**
	 * 最后修改时间
     */
	private String lastModifiedTime; 
	
	/**
	 * 最后修改IP
     */
	private String lastModifiedIp;

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanCodeDesc() {
		return planCodeDesc;
	}

	public void setPlanCodeDesc(String planCodeDesc) {
		this.planCodeDesc = planCodeDesc;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getProductionLine() {
		return productionLine;
	}

	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getPlanCodeType() {
		return planCodeType;
	}

	public void setPlanCodeType(String planCodeType) {
		this.planCodeType = planCodeType;
	}

	public String getIsAutoExec() {
		return isAutoExec;
	}

	public void setIsAutoExec(String isAutoExec) {
		this.isAutoExec = isAutoExec;
	}

	public String getExecState() {
		return execState;
	}

	public void setExecState(String execState) {
		this.execState = execState;
	}

	public String getLastExecTime() {
		return lastExecTime;
	}

	public void setLastExecTime(String lastExecTime) {
		this.lastExecTime = lastExecTime;
	}

	public String getKbCode() {
		return kbCode;
	}

	public void setKbCode(String kbCode) {
		this.kbCode = kbCode;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
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

	public String getLastModifiedIp() {
		return lastModifiedIp;
	}

	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}

	public Long getStopTime() {
		return stopTime;
	}

	public void setStopTime(Long stopTime) {
		this.stopTime = stopTime;
	}
	
	
}
