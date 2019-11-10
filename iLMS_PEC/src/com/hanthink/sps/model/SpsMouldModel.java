package com.hanthink.sps.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：MM_SPS_MOULD 实体对象
 * 作者:dtp
 * 日期:2018-11-21 11:59:55
 * </pre>
 */
@SuppressWarnings("serial")
public class SpsMouldModel extends AbstractModel<String>{

	protected String id; 
	
	/**
	 * 信息点
	 */
	protected String planCode; 
	
	/**
	 * 工厂编码
	 */
	protected String factoryCode;
	
	/**
	 * 信息点描述
	 */
	protected String planCodeDesc;
	
	/**
	 * 模板编号
	 */
	protected String mouldCode; 
	
	/**
	 * 版本号
	 */
	protected String version; 
	
	/**
	 * 车型
	 */
	protected String modelCode;
	
	/**
	 * 模板名称
	 */
	protected String mouldName; 
	
	/**
	 * 是否启用数据字典类型“IS_ENABLE”     
	 */
	protected String isEnable; 
	
	/**
	 * 模板状态描述
	 */
	protected String isEnableDesc;
	
	/**
	 * 是否自动打印数据字典类型“TRUE_FALSE”     
	 */
	protected String isAutoPrint; 
	
	/**
	 * 是否自动打印描述
	 */
	protected String isAutoPrintDesc;
	
	/**
	 * 打印机
	 */
	protected String printerId;
	
	/**
	 * 打印机名称
	 */
	protected String printerIdDesc;
	
	/**
	 * 对应装车单名称
	 */
	protected String assemblyIns;
	
	/**
	 * 分拣单首页最大位置号
	 */
	protected String mouldHeadPlace;
	
	/**
	 * 创建时间
	 */
	protected String creationTime; 
	
	/**
	 * 创建人
	 */
	protected String creationUser; 
	
	/**
	 * 最后修改用户
	 */
	protected String lastModifiedUser; 
	
	/**
	 * 最后修改IP
	 */
	protected String lastModifiedIp; 
	
	/**
	 * 最后修改时间
	 */
	protected String lastModifiedTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getMouldCode() {
		return mouldCode;
	}

	public void setMouldCode(String mouldCode) {
		this.mouldCode = mouldCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMouldName() {
		return mouldName;
	}

	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getIsAutoPrint() {
		return isAutoPrint;
	}

	public void setIsAutoPrint(String isAutoPrint) {
		this.isAutoPrint = isAutoPrint;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getLastModifiedIp() {
		return lastModifiedIp;
	}

	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getPlanCodeDesc() {
		return planCodeDesc;
	}

	public void setPlanCodeDesc(String planCodeDesc) {
		this.planCodeDesc = planCodeDesc;
	}

	public String getPrinterId() {
		return printerId;
	}

	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}

	public String getAssemblyIns() {
		return assemblyIns;
	}

	public void setAssemblyIns(String assemblyIns) {
		this.assemblyIns = assemblyIns;
	}

	public String getPrinterIdDesc() {
		return printerIdDesc;
	}

	public void setPrinterIdDesc(String printerIdDesc) {
		this.printerIdDesc = printerIdDesc;
	}

	public String getIsEnableDesc() {
		return isEnableDesc;
	}

	public void setIsEnableDesc(String isEnableDesc) {
		this.isEnableDesc = isEnableDesc;
	}

	public String getIsAutoPrintDesc() {
		return isAutoPrintDesc;
	}

	public void setIsAutoPrintDesc(String isAutoPrintDesc) {
		this.isAutoPrintDesc = isAutoPrintDesc;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getMouldHeadPlace() {
		return mouldHeadPlace;
	}

	public void setMouldHeadPlace(String mouldHeadPlace) {
		this.mouldHeadPlace = mouldHeadPlace;
	} 
	
	
}
