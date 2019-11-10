package com.hanthink.pub.model;

/**
 * <pre> 
 * 描述：MM_PUB_PLAN_CODE 实体对象
 * 作者:dtp
 * 日期:2018-09-12 18:25:36
 * </pre>
 */
public class PubPlanCodeModel {
	/**
	 * 信息点
     */
	protected String planCode; 
	
	/**
	 * 信息点描述
     */
	protected String planCodeDesc; 
	
	/**
	 * 工厂
     */
	protected String factoryCode; 
	
	/**
	 * 产线
     */
	protected String productionLine; 
	
	/**
	 * 车间
     */
	protected String workcenter; 
	
	/**
	 * 工位
     */
	protected String stationCode; 
	
	/**
	 * 信息点类型
		SPS：SPS
		JISI：厂外同步
		JISO：厂内同步
		JIT：是     
	 */
	protected String planCodeType; 
	
	/**
	 * 是否自动推算
	   0：否
	   1：是     
	*/
	protected String isAutoExec; 
	
	/**
	 * 推算状态
	   0：未推算
	   1：推算中     
	 */
	protected String execState; 
	
	/**
	 * 最近推算时间
     */
	protected String lastExecTime; 
	
	/**
	 * 看板代码
     */
	protected String kbCode; 
	
	/**
	 * 是否启用数据字典类型“TRUE_FALSE”     
	 */
	protected String isEnable; 
	
	/**
	 * 是否显示数据字典类型“TRUE_FALSE”     
	 */
	protected String isShow; 
	
	/**
	 * 创建人
     */
	protected String creationUser; 
	
	/**
	 * 创建时间
     */
	protected String creationTime; 
	
	/**
	 * 最后修改用户
     */
	protected String lastModifiedUser; 
	
	/**
	 * 最后修改时间
     */
	protected String lastModifiedTime; 
	
	/**
	 * 最后修改IP
     */
	protected String lastModifiedIp;

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

	
	
}
