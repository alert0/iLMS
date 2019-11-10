package com.hanthink.gps.gacne.pub.vo;

/**
 * @Title: PubOrderVO.java
 * @Package: com.hanthink.gps.gacne.pub.vo
 * @Description: 订购零件基本信息维护异常提醒VO
 * @author dtp
 * @date 2019-2-14
 */
public class PubOrderVO {
	
	private String orderNo;
	private String erpOrderNo;
	private String isSendEmail;
	private String paoffAft1onNum;
	private String paoffAfonNum;
	private String afonAft1onNum;
	private String passTime;
	private String modelNum;
	private String sysdateTime;
	private String vin;
	private String sort;
	private String partShortNo;
	private String preparePerson;
	private String carpool;
	private String storage;
	private String distriPerson;
	private String location;
	private String processType;
	private String shelfNo;
	private String locationNum;
	private String deptNo;
	private String supplierName;
	private String shipDepot;
	
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
	 * 产线
     */
	private String productionLine; 
	
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
	 * 生效时间
	 */
	private String effStart;
	
	/**
	 * 失效时间
	 */
	private String effEnd;
	
	/**
	 * 工位
	 */
	private String stationCode;
	
	/**
	 * 车型
	 */
	private String modelCode;
	
	/**
	 * 工作中心
	 */
	private String workcenter;
	
	/**
	 * 工作中心描述
	 */
	private String workcenterDesc;
	
	/**
	 * 零件号
	 */
	private String partNo;
	
	/**
	 * 零件名称
	 */
	private String partName;
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	
	/**
	 * 工厂名称
	 */
	private String factory;
	
	/**
	 * 异常信息
	 */
	private String errorMessage;
	
	/**
	 * 供应商代码 
	 */
	private String supplierNo;
	
	/**
	 * 供应商出货地
	 */
	private String supFactory;
	
	/**
	 * 卸货口
	 */
	private String unloadPort;
	
	/**
	 * 到货仓库
	 * @return
	 */
	private String arrDepot;
	
	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}

	public String getWorkcenterDesc() {
		return workcenterDesc;
	}

	public void setWorkcenterDesc(String workcenterDesc) {
		this.workcenterDesc = workcenterDesc;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getArrDepot() {
		return arrDepot;
	}

	public void setArrDepot(String arrDepot) {
		this.arrDepot = arrDepot;
	}

	public String getEffStart() {
		return effStart;
	}

	public void setEffStart(String effStart) {
		this.effStart = effStart;
	}

	public String getEffEnd() {
		return effEnd;
	}

	public void setEffEnd(String effEnd) {
		this.effEnd = effEnd;
	}

	public Long getStopTime() {
		return stopTime;
	}

	public void setStopTime(Long stopTime) {
		this.stopTime = stopTime;
	}

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

	public String getProductionLine() {
		return productionLine;
	}

	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
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

	public String getPreparePerson() {
		return preparePerson;
	}

	public void setPreparePerson(String preparePerson) {
		this.preparePerson = preparePerson;
	}

	public String getCarpool() {
		return carpool;
	}

	public void setCarpool(String carpool) {
		this.carpool = carpool;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getDistriPerson() {
		return distriPerson;
	}

	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getShelfNo() {
		return shelfNo;
	}

	public void setShelfNo(String shelfNo) {
		this.shelfNo = shelfNo;
	}

	public String getLocationNum() {
		return locationNum;
	}

	public void setLocationNum(String locationNum) {
		this.locationNum = locationNum;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getShipDepot() {
		return shipDepot;
	}

	public void setShipDepot(String shipDepot) {
		this.shipDepot = shipDepot;
	}

	

	public String getSysdateTime() {
		return sysdateTime;
	}

	public void setSysdateTime(String sysdateTime) {
		this.sysdateTime = sysdateTime;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getModelNum() {
		return modelNum;
	}

	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public String getPaoffAfonNum() {
		return paoffAfonNum;
	}

	public void setPaoffAfonNum(String paoffAfonNum) {
		this.paoffAfonNum = paoffAfonNum;
	}

	public String getAfonAft1onNum() {
		return afonAft1onNum;
	}

	public void setAfonAft1onNum(String afonAft1onNum) {
		this.afonAft1onNum = afonAft1onNum;
	}

	public String getPaoffAft1onNum() {
		return paoffAft1onNum;
	}

	public void setPaoffAft1onNum(String paoffAft1onNum) {
		this.paoffAft1onNum = paoffAft1onNum;
	}

	public String getIsSendEmail() {
		return isSendEmail;
	}

	public void setIsSendEmail(String isSendEmail) {
		this.isSendEmail = isSendEmail;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getErpOrderNo() {
		return erpOrderNo;
	}

	public void setErpOrderNo(String erpOrderNo) {
		this.erpOrderNo = erpOrderNo;
	}
	
	
}
