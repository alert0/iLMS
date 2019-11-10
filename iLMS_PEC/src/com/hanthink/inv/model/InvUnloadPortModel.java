package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;
/**
 * <pre> 
 * 功能描述:卸货口管理数据实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日9
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class InvUnloadPortModel extends AbstractModel<String>{

	private static final long serialVersionUID = -2653895274550432704L;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂代码 */
	private String factoryCode;
	/** 仓库代码 */
	private String wareCode;
	private String wareName;
	/** 车间 */
	private String workCenter;
	private String excelWorkCenter;
	/** 卸货口 */
	private String unloadPort;
	/** 有无P批链 */
	private String plFlag;
	private String excelPlFlag;
	/** 物流模式 */
	private String logisticsMode;
	private String excelLogisticsModel;
	/** 物理卸货口 */
	private String logicUnload;
	/** 厂内物流模式 */
	private String innerLogisticsMode;
	/** 卸货口类别 */
	private String unloadType;
	/** 备注信息 */
	private String note;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/** 最后修改人 */
	private String lastModifiedUser;
	/** 最后修改时间 */
	private String lastModifiedTime;
	
	private String wareCodeLabel;
	private String wareCodeValue;
	public String getWareCodeLabel() {
		return wareCodeLabel;
	}
	public void setWareCodeLabel(String wareCodeLabel) {
		this.wareCodeLabel = wareCodeLabel;
	}
	public String getWareCodeValue() {
		return wareCodeValue;
	}
	public void setWareCodeValue(String wareCodeValue) {
		this.wareCodeValue = wareCodeValue;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getExcelWorkCenter() {
		return excelWorkCenter;
	}
	public void setExcelWorkCenter(String excelWorkCenter) {
		this.excelWorkCenter = excelWorkCenter;
	}
	public String getUnloadPort() {
		return unloadPort;
	}
	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}
	public String getPlFlag() {
		return plFlag;
	}
	public void setPlFlag(String plFlag) {
		this.plFlag = plFlag;
	}
	public String getExcelPlFlag() {
		return excelPlFlag;
	}
	public void setExcelPlFlag(String excelPlFlag) {
		this.excelPlFlag = excelPlFlag;
	}
	public String getLogisticsMode() {
		return logisticsMode;
	}
	public void setLogisticsMode(String logisticsMode) {
		this.logisticsMode = logisticsMode;
	}
	public String getExcelLogisticsModel() {
		return excelLogisticsModel;
	}
	public void setExcelLogisticsModel(String excelLogisticsModel) {
		this.excelLogisticsModel = excelLogisticsModel;
	}
	public String getLogicUnload() {
		return logicUnload;
	}
	public void setLogicUnload(String logicUnload) {
		this.logicUnload = logicUnload;
	}
	public String getInnerLogisticsMode() {
		return innerLogisticsMode;
	}
	public void setInnerLogisticsMode(String innerLogisticsMode) {
		this.innerLogisticsMode = innerLogisticsMode;
	}
	public String getUnloadType() {
		return unloadType;
	}
	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	@Override
	public String toString() {
		return "InvUnloadPortModel [wareCode=" + wareCode + ", wareName=" + wareName + ", workCenter=" + workCenter
				+ ", unloadPort=" + unloadPort + ", plFlag=" + plFlag + ", logisticsMode=" + logisticsMode
				+ ", logicUnload=" + logicUnload + ", innerLogisticsMode=" + innerLogisticsMode + ", unloadType="
				+ unloadType + ", note=" + note + "]";
	}
}
