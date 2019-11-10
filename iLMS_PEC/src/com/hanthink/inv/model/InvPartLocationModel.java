package com.hanthink.inv.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.hanthink.util.excel.ExcelCheckUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.model.AbstractModel;
import com.mysql.jdbc.StringUtils;

/**
 * @ClassName: InvPartLocationModel
 * @Description: 零件属地维护MODEL
 * @author dtp
 * @date 2019年2月16日
 */
public class InvPartLocationModel extends AbstractModel<String> implements Cloneable{
	
	/**
	 * 工作中心
	 */
	private String workCenter;
	
	/** 导入UUID */
	private String uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String checkResult;
	/** 检查结果描述 */
	private String checkResultDesc;
	/** 检查结果信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
	/** 导入状态描述 */
	private String importStatusDesc;
	/** 是否继续检查 */
	private String continueCheck;
	/** 导入数据操作类型 */
	private String opeType;
	/** 业务表主键字段值 */
	private String busiId;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/** 最后修改IP */
	private String lastModifiedIp;
	/** 新车间 */
	private String newWorkCenter;
	/** 旧车间 */
	private String oldWorkCenter;
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	
	/**
	 * 红点图片
	 */
	private Object shelfImg;
	
	/**
	 * 二维码
	 */
	private Object qrCode;
	
	/**
	 * 简号
	 */
	private String partShortNo;
	
	/**
	 * 零件名称
	 */
	private String partName;
	
	/**
	 * 零件号
	 */
	private String partNo;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 收容数
	 */
	private String standardPackage;
	
	/**
	 * 货架号
	 */
	private String shelfNo;
	
	/**
	 * 供应商代码
	 */
	private String supplierNo;
	
	/**
	 * 更新日期
	 */
	private String lastModifiedTime;
	
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String part_no;
	private String part_name_cn;
	private String part_short_no;
	private String supplier_no;
	private String supplier_name;
	private String unload_port;
	private String prepare_person;
	private String carpool;
	private String storage;
	private String distri_person;
	private String location;
	private String station_code;
	private String shelf_no;
	private String location_num;
	private String eff_start;
	private String eff_end;
	private String dept_No;//责任班组
	
	 private String newPartNo;
	 private String newSupplierNo;
	 private String newUnloadPort;
	 private String newReparePerson;
	 private String newCarpool;
	 private String newDistriPerson;
	 private String newLocation;
	 private String newStationCode;;
	 private String newShelfNo;
	 private String newLocationNum;
	 private String newStorage;
	 private String newDeptNo;
	 private String oldPartNo;
	 private String oldSupplierNo;
	 private String oldUnloadPort;
	 private String oldReparePerson;
	 private String oldCarpool;
	 private String oldDistriPerson;
	 private String oldLocation;
	 private String oldStationCode;
	 private String oldShelfNo;
	 private String oldLocationNum;
	 private String oldStorage;
	 private String modelCode;
	 private String  effStart;
	 private String effEnd;
	 private String dealFlag;
	 private String checkFlag;
	 private String operationType;
	 private String wareCode;
	 private String factory;
	 private String oldDeptNo;
	public Object getShelfImg() {
		return shelfImg;
	}
	public void setShelfImg(Object shelfImg) {
		this.shelfImg = shelfImg;
	}
	public Object getQrCode() {
		return qrCode;
	}
	public void setQrCode(Object qrCode) {
		this.qrCode = qrCode;
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
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getStandardPackage() {
		return standardPackage;
	}
	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}
	public String getShelfNo() {
		return shelfNo;
	}
	public void setShelfNo(String shelfNo) {
		this.shelfNo = shelfNo;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPart_no() {
		return part_no;
	}
	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}
	public String getPart_name_cn() {
		return part_name_cn;
	}
	public void setPart_name_cn(String part_name_cn) {
		this.part_name_cn = part_name_cn;
	}
	public String getPart_short_no() {
		return part_short_no;
	}
	public void setPart_short_no(String part_short_no) {
		this.part_short_no = part_short_no;
	}
	public String getSupplier_no() {
		return supplier_no;
	}
	public void setSupplier_no(String supplier_no) {
		this.supplier_no = supplier_no;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getUnload_port() {
		return unload_port;
	}
	public void setUnload_port(String unload_port) {
		this.unload_port = unload_port;
	}
	public String getPrepare_person() {
		return prepare_person;
	}
	public void setPrepare_person(String prepare_person) {
		this.prepare_person = prepare_person;
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
	public String getDistri_person() {
		return distri_person;
	}
	public void setDistri_person(String distri_person) {
		this.distri_person = distri_person;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStation_code() {
		return station_code;
	}
	public void setStation_code(String station_code) {
		this.station_code = station_code;
	}
	public String getShelf_no() {
		return shelf_no;
	}
	public void setShelf_no(String shelf_no) {
		this.shelf_no = shelf_no;
	}
	public String getLocation_num() {
		return location_num;
	}
	public void setLocation_num(String location_num) {
		this.location_num = location_num;
	}
	public String getEff_start() {
		return eff_start;
	}
	public void setEff_start(String eff_start) {
		this.eff_start = eff_start;
	}
	public String getEff_end() {
		return eff_end;
	}
	public void setEff_end(String eff_end) {
		this.eff_end = eff_end;
	}
	public String getDept_No() {
		return dept_No;
	}
	public void setDept_No(String dept_No) {
		this.dept_No = dept_No;
	}
	public String getNewPartNo() {
		return newPartNo;
	}
	public void setNewPartNo(String newPartNo) {
		this.newPartNo = newPartNo;
	}
	public String getNewSupplierNo() {
		return newSupplierNo;
	}
	public void setNewSupplierNo(String newSupplierNo) {
		this.newSupplierNo = newSupplierNo;
	}
	public String getNewUnloadPort() {
		return newUnloadPort;
	}
	public void setNewUnloadPort(String newUnloadPort) {
		this.newUnloadPort = newUnloadPort;
	}
	public String getNewReparePerson() {
		return newReparePerson;
	}
	public void setNewReparePerson(String newReparePerson) {
		this.newReparePerson = newReparePerson;
	}
	public String getNewCarpool() {
		return newCarpool;
	}
	public void setNewCarpool(String newCarpool) {
		this.newCarpool = newCarpool;
	}
	public String getNewDistriPerson() {
		return newDistriPerson;
	}
	public void setNewDistriPerson(String newDistriPerson) {
		this.newDistriPerson = newDistriPerson;
	}
	public String getNewLocation() {
		return newLocation;
	}
	public void setNewLocation(String newLocation) {
		this.newLocation = newLocation;
	}
	public String getNewStationCode() {
		return newStationCode;
	}
	public void setNewStationCode(String newStationCode) {
		this.newStationCode = newStationCode;
	}
	public String getNewShelfNo() {
		return newShelfNo;
	}
	public void setNewShelfNo(String newShelfNo) {
		this.newShelfNo = newShelfNo;
	}
	public String getNewLocationNum() {
		return newLocationNum;
	}
	public void setNewLocationNum(String newLocationNum) {
		this.newLocationNum = newLocationNum;
	}
	public String getNewStorage() {
		return newStorage;
	}
	public void setNewStorage(String newStorage) {
		this.newStorage = newStorage;
	}
	public String getNewDeptNo() {
		return newDeptNo;
	}
	public void setNewDeptNo(String newDeptNo) {
		this.newDeptNo = newDeptNo;
	}
	public String getOldPartNo() {
		return oldPartNo;
	}
	public void setOldPartNo(String oldPartNo) {
		this.oldPartNo = oldPartNo;
	}
	public String getOldSupplierNo() {
		return oldSupplierNo;
	}
	public void setOldSupplierNo(String oldSupplierNo) {
		this.oldSupplierNo = oldSupplierNo;
	}
	public String getOldUnloadPort() {
		return oldUnloadPort;
	}
	public void setOldUnloadPort(String oldUnloadPort) {
		this.oldUnloadPort = oldUnloadPort;
	}
	public String getOldReparePerson() {
		return oldReparePerson;
	}
	public void setOldReparePerson(String oldReparePerson) {
		this.oldReparePerson = oldReparePerson;
	}
	public String getOldCarpool() {
		return oldCarpool;
	}
	public void setOldCarpool(String oldCarpool) {
		this.oldCarpool = oldCarpool;
	}
	public String getOldDistriPerson() {
		return oldDistriPerson;
	}
	public void setOldDistriPerson(String oldDistriPerson) {
		this.oldDistriPerson = oldDistriPerson;
	}
	public String getOldLocation() {
		return oldLocation;
	}
	public void setOldLocation(String oldLocation) {
		this.oldLocation = oldLocation;
	}
	public String getOldStationCode() {
		return oldStationCode;
	}
	public void setOldStationCode(String oldStationCode) {
		this.oldStationCode = oldStationCode;
	}
	public String getOldShelfNo() {
		return oldShelfNo;
	}
	public void setOldShelfNo(String oldShelfNo) {
		this.oldShelfNo = oldShelfNo;
	}
	public String getOldLocationNum() {
		return oldLocationNum;
	}
	public void setOldLocationNum(String oldLocationNum) {
		this.oldLocationNum = oldLocationNum;
	}
	public String getOldStorage() {
		return oldStorage;
	}
	public void setOldStorage(String oldStorage) {
		this.oldStorage = oldStorage;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
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
	public String getDealFlag() {
		return dealFlag;
	}
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}
	public String getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getOldDeptNo() {
		return oldDeptNo;
	}
	public void setOldDeptNo(String oldDeptNo) {
		this.oldDeptNo = oldDeptNo;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getContinueCheck() {
		return continueCheck;
	}
	public void setContinueCheck(String continueCheck) {
		this.continueCheck = continueCheck;
	}
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public String getBusiId() {
		return busiId;
	}
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}
	public String getCheckResultDesc() {
		return checkResultDesc;
	}
	public void setCheckResultDesc(String checkResultDesc) {
		this.checkResultDesc = checkResultDesc;
	}
	public String getImportStatusDesc() {
		return importStatusDesc;
	}
	public void setImportStatusDesc(String importStatusDesc) {
		this.importStatusDesc = importStatusDesc;
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
	public String getLastModifiedIp() {
		return lastModifiedIp;
	}
	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}
	public String getNewWorkCenter() {
		return newWorkCenter;
	}
	public void setNewWorkCenter(String newWorkCenter) {
		this.newWorkCenter = newWorkCenter;
	}
	public String getOldWorkCenter() {
		return oldWorkCenter;
	}
	public void setOldWorkCenter(String oldWorkCenter) {
		this.oldWorkCenter = oldWorkCenter;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	/**
	 * @Description: 校验必填项 ,数据类型  
	 * @param: @param m    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	public static void checkImportData(InvPartLocationModel model) {
		StringBuffer checkInfo = new StringBuffer();
		if(StringUtils.isNullOrEmpty(model.getOperationType())) {
			checkInfo.append("状态不能为空;");
		}else {
			String operationType2 = model.getOperationType();
			if("新增".equals(operationType2)) {
				List<String> newCheckInfoList = new ArrayList<String>();
				//移动时变更后
				if (StringUtils.isNullOrEmpty(model.getNewPartNo())) {
					newCheckInfoList.add("零件号");
				};
				if (StringUtils.isNullOrEmpty(model.getNewSupplierNo())) {
					newCheckInfoList.add("厂家代码");
				};
				if (StringUtils.isNullOrEmpty(model.getNewUnloadPort())) {
					newCheckInfoList.add("卸货口");
				};
				if (StringUtils.isNullOrEmpty(model.getNewReparePerson())) {
					newCheckInfoList.add("备货工程");
				};
				if (StringUtils.isNullOrEmpty(model.getNewCarpool())) {
					newCheckInfoList.add("台车号");
				};
				if (StringUtils.isNullOrEmpty(model.getNewStorage())) {
					newCheckInfoList.add("物流库地址");
				};
				if (StringUtils.isNullOrEmpty(model.getNewDistriPerson())) {
					newCheckInfoList.add("配送工程");
				};
				if (StringUtils.isNullOrEmpty(model.getNewLocation())) {
					newCheckInfoList.add("配送地址");
				};
				if (StringUtils.isNullOrEmpty(model.getNewLocationNum())) {
					newCheckInfoList.add("工程深度");
				}
				if(ExcelCheckUtil.isNotNumeric(model.getNewLocationNum())) {
					checkInfo.append("新增时变更后工程深度应为正整数;");
					model.setNewLocation(null);
				}
				if (StringUtils.isNullOrEmpty(model.getNewStationCode())) {
					newCheckInfoList.add("工位号");
				};
				if (StringUtils.isNullOrEmpty(model.getNewShelfNo())) {
					newCheckInfoList.add("货架号");
				};
				if (StringUtils.isNullOrEmpty(model.getNewDeptNo())) {
					newCheckInfoList.add("责任班组");
				};
				if(null != newCheckInfoList && newCheckInfoList.size() > 0) {
					String checkInfoStr = org.apache.commons.lang3.StringUtils.join(newCheckInfoList, "、");
					checkInfo.append("新增时变更后：" + checkInfoStr + "不能为空;");
				}
				//if新增
			}else if("移动".equals(operationType2)) {
				List<String> newCheckInfoList = new ArrayList<String>();
				//移动时变更后
				if (StringUtils.isNullOrEmpty(model.getNewPartNo())) {
					newCheckInfoList.add("零件号");
				};
				if (StringUtils.isNullOrEmpty(model.getNewSupplierNo())) {
					newCheckInfoList.add("厂家代码");
				};
				if (StringUtils.isNullOrEmpty(model.getNewUnloadPort())) {
					newCheckInfoList.add("卸货口");
				};
				if (StringUtils.isNullOrEmpty(model.getNewReparePerson())) {
					newCheckInfoList.add("备货工程");
				};
				if (StringUtils.isNullOrEmpty(model.getNewCarpool())) {
					newCheckInfoList.add("台车号");
				};
				/*if (StringUtils.isNullOrEmpty(model.getNewStorage())) {
					newCheckInfoList.add("物流库地址");
				};*/
				if (StringUtils.isNullOrEmpty(model.getNewDistriPerson())) {
					newCheckInfoList.add("配送工程");
				};
				if (StringUtils.isNullOrEmpty(model.getNewLocation())) {
					newCheckInfoList.add("配送地址");
				};
				if (StringUtils.isNullOrEmpty(model.getNewLocationNum())) {
					newCheckInfoList.add("工程深度");
				}
				if(ExcelCheckUtil.isNotNumeric(model.getNewLocationNum())) {
					checkInfo.append("移动时变更后工程深度应为正整数;");
					model.setNewLocation(null);
				}
				if (StringUtils.isNullOrEmpty(model.getNewStationCode())) {
					newCheckInfoList.add("工位号");
				};
				if (StringUtils.isNullOrEmpty(model.getNewShelfNo())) {
					newCheckInfoList.add("货架号");
				};
				if (StringUtils.isNullOrEmpty(model.getNewDeptNo())) {
					newCheckInfoList.add("责任班组");
				};
				//移动时变更前
				List<String> oldCheckInfoList = new ArrayList<String>();
				if (StringUtils.isNullOrEmpty(model.getOldPartNo())) {
					oldCheckInfoList.add("零件号");
				};
				if (StringUtils.isNullOrEmpty(model.getOldSupplierNo())) {
					oldCheckInfoList.add("厂家代码");
				};
				if (StringUtils.isNullOrEmpty(model.getOldUnloadPort())) {
					oldCheckInfoList.add("卸货口");
				};
				if (StringUtils.isNullOrEmpty(model.getOldReparePerson())) {
					oldCheckInfoList.add("备货工程");
				};
				if (StringUtils.isNullOrEmpty(model.getOldCarpool())) {
					oldCheckInfoList.add("台车号");
				};
				/*if (StringUtils.isNullOrEmpty(model.getOldStorage())) {
					oldCheckInfoList.add("物流库地址");
				};*/
				if (StringUtils.isNullOrEmpty(model.getOldDistriPerson())) {
					oldCheckInfoList.add("配送工程");
				};
				if (StringUtils.isNullOrEmpty(model.getOldLocation())) {
					oldCheckInfoList.add("配送地址");
				};
				if (StringUtils.isNullOrEmpty(model.getOldLocationNum())) {
					oldCheckInfoList.add("工程深度");
				}
				if(ExcelCheckUtil.isNotNumeric(model.getOldLocationNum())) {
					checkInfo.append("移动时变更前工程深度应为正整数;");
					model.setOldLocationNum(null);
				}
				if (StringUtils.isNullOrEmpty(model.getOldStationCode())) {
					oldCheckInfoList.add("工位号");
				};
				if (StringUtils.isNullOrEmpty(model.getOldShelfNo())) {
					oldCheckInfoList.add("货架号");
				};
				if (StringUtils.isNullOrEmpty(model.getOldDeptNo())) {
					oldCheckInfoList.add("责任班组");
				};
				if(null != oldCheckInfoList && oldCheckInfoList.size() > 0) {
					String checkInfoStr = org.apache.commons.lang3.StringUtils.join(oldCheckInfoList, "、");
					checkInfo.append("移动时变更前"  + checkInfoStr + "不能为空;");
				}
				if(null != newCheckInfoList && newCheckInfoList.size() > 0) {
					String checkInfoStr = org.apache.commons.lang3.StringUtils.join(newCheckInfoList, "、");
					checkInfo.append("移动时变更后"  + checkInfoStr + "不能为空;");
				}
			}else if("废止".equals(operationType2)) {
				//废止时变更前
				List<String> oldCheckInfoList = new ArrayList<String>();
				if (StringUtils.isNullOrEmpty(model.getOldPartNo())) {
					oldCheckInfoList.add("零件号");
				};
				if (StringUtils.isNullOrEmpty(model.getOldSupplierNo())) {
					oldCheckInfoList.add("厂家代码");
				};
				if (StringUtils.isNullOrEmpty(model.getOldUnloadPort())) {
					oldCheckInfoList.add("卸货口");
				};
				if (StringUtils.isNullOrEmpty(model.getOldReparePerson())) {
					oldCheckInfoList.add("备货工程");
				};
				if (StringUtils.isNullOrEmpty(model.getOldCarpool())) {
					oldCheckInfoList.add("台车号");
				};
				/*if (StringUtils.isNullOrEmpty(model.getOldStorage())) {
					oldCheckInfoList.add("物流库地址");
				};*/
				if (StringUtils.isNullOrEmpty(model.getOldDistriPerson())) {
					oldCheckInfoList.add("配送工程");
				};
				if (StringUtils.isNullOrEmpty(model.getOldLocation())) {
					oldCheckInfoList.add("配送地址");
				};
				if (StringUtils.isNullOrEmpty(model.getOldLocationNum())) {
					oldCheckInfoList.add("工程深度");
				}
				if(ExcelCheckUtil.isNotNumeric(model.getOldLocationNum())) {
					checkInfo.append("废止时变更前工程深度应为正整数;");
					model.setOldLocationNum(null);
				}
				if (StringUtils.isNullOrEmpty(model.getOldStationCode())) {
					oldCheckInfoList.add("工位号");
				};
				if (StringUtils.isNullOrEmpty(model.getOldShelfNo())) {
					oldCheckInfoList.add("货架号");
				};
				if (StringUtils.isNullOrEmpty(model.getOldDeptNo())) {
					oldCheckInfoList.add("责任班组");
				};
				if(null != oldCheckInfoList && oldCheckInfoList.size() > 0) {
					String checkInfoStr = org.apache.commons.lang3.StringUtils.join(oldCheckInfoList, "、");
					checkInfo.append("废止时变更前"  + checkInfoStr + "不能为空;");
				}
			}else {
				checkInfo.append("状态不存在;");
			}
			
			//车型,生效,废止日期
			String effStart2 = model.getEffStart();
			String effEnd2 = model.getEffEnd();
			String modelCode2 = model.getModelCode();
			if(StringUtils.isNullOrEmpty(effStart2)) {
				checkInfo.append("生效日期不能为空;");
			}
			if(StringUtils.isNullOrEmpty(effEnd2)) {
				checkInfo.append("废止日期不能为空;");
			}
			if(!StringUtils.isNullOrEmpty(effEnd2) && !StringUtils.isNullOrEmpty(effEnd2)) {
				SimpleDateFormat format = new SimpleDateFormat( "yyyyMMdd" );
				try {
					format.parse(effStart2);
				} catch (Exception e) {
					checkInfo.append("生效日期格式不正确;");
					model.setEffStart("");
				}
				try {
					format.parse(effEnd2);
				} catch (Exception e) {
					checkInfo.append("废止日期格式不正确;");
					model.setEffEnd("");
				}
				try {
					format.parse(effStart2);
					format.parse(effEnd2);
					long number = parseTime(effEnd2) - parseTime(effStart2);
					if (number < 0L) {
						checkInfo.append("废止日期不能小于生效日期;");
					}
				} catch (Exception e) {
					checkInfo.append("生效日期格式不正确;废止日期格式不正确;");
				}
			}
			if (StringUtils.isNullOrEmpty(modelCode2)) {
				checkInfo.append("车型不能为空;");
			}
		}
		if(StringUtils.isNullOrEmpty(checkInfo.toString())){
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
		}else{
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
			model.setCheckInfo(checkInfo.toString());
		}
		
	}
	
	/**
	 * @Description: 判断生失效日期大小
	 * @param: @param strTime
	 * @param: @return    
	 * @return: long   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	private static long parseTime(String strTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		long time = 0L;
		try {
			time = format.parse(strTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;   
		}
		
	}
	 
}
