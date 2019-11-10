package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * InvZGJBenchMarkModel支给件W-1周库存
 * @author WY
 *
 */
@SuppressWarnings("serial")
public class InvZGJBenchMarkModel extends AbstractModel<String>{

	private String id;
	
	//工厂
	private String factoryCode;
	//仓库
	private String wareCode;
	//零件号
	private String partNo;
	//在库数量
	private String stock;
	//标识
	private String flag;
	//flagStr
	private String flagStr;
	//零件简号
	private String partShortNo;
	//零件名称
	private String partName;
	//仓库名称
	private String wareName;
	//最后修改人
	private String lastModifyUser;
	//最后修改时间
	private String lastModifyTime;
	//创建人
	private String creationUser;
	//创建时间
	private String creationTime;
	//计算时间
	private String calTimeStr;
	//计算人
	private String calUser;
	/**确认处理人**/
	private String dealUser;
	/**处理人ip**/
	private String ipAddr;
	/**推算周**/
	private String calWeek;
	
	/**导入字段******************************/
	/** 导入UUID */
	private String uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String checkResult;
	/** 检查结果信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
	private String importStatusStr;
	private String checkResultStr;
	
	private String value;
	private String label;
	
	/***支给件缺件查询*************************/
	/**缺件标识**/
	private String diffeFlag;
	/**预计消耗量**/
	private String useNum;
	/**本周第一天**/
	private String weekDay;
	/**周次**/
	private String week;
	/**出货地**/
	private String supFactory;
	/**供应商代码**/
	private String supplierNo;
	/**供应商名称**/
	private String supplierName;
	
	public String getCalUser() {
		return calUser;
	}

	public void setCalUser(String calUser) {
		this.calUser = calUser;
	}

	public String getCalTimeStr() {
		return calTimeStr;
	}

	public void setCalTimeStr(String calTimeStr) {
		this.calTimeStr = calTimeStr;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifyUser() {
		return lastModifyUser;
	}

	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

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

	public String getWareCode() {
		return wareCode;
	}

	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlagStr() {
		return flagStr;
	}

	public void setFlagStr(String flagStr) {
		this.flagStr = flagStr;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public String getImportStatusStr() {
		return importStatusStr;
	}

	public void setImportStatusStr(String importStatusStr) {
		this.importStatusStr = importStatusStr;
	}

	public String getCheckResultStr() {
		return checkResultStr;
	}

	public void setCheckResultStr(String checkResultStr) {
		this.checkResultStr = checkResultStr;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getCalWeek() {
		return calWeek;
	}

	public void setCalWeek(String calWeek) {
		this.calWeek = calWeek;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDiffeFlag() {
		return diffeFlag;
	}

	public void setDiffeFlag(String diffeFlag) {
		this.diffeFlag = diffeFlag;
	}

	public String getUseNum() {
		return useNum;
	}

	public void setUseNum(String useNum) {
		this.useNum = useNum;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
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
	
		
}
