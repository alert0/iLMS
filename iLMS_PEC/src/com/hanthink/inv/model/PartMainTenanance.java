package com.hanthink.inv.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.model.AbstractModel;
import com.mysql.jdbc.StringUtils;

public class PartMainTenanance extends AbstractModel<String>{
	
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
	private String model_code;
	private String eff_start;
	private String eff_end;
	private String part_shot_no;
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
	  private String checkResult;
	  private String wareCode;
	  private String factory;
	  private String oldDeptNo;
	  
	  
	  
	  
	
	public String getDept_No() {
		return dept_No;
	}
	public void setDept_No(String dept_No) {
		this.dept_No = dept_No;
	}
	
	public String getNewDeptNo() {
		return newDeptNo;
	}
	public void setNewDeptNo(String newDeptNo) {
		this.newDeptNo = newDeptNo;
	}
	public String getOldDeptNo() {
		return oldDeptNo;
	}
	public void setOldDeptNo(String oldDeptNo) {
		this.oldDeptNo = oldDeptNo;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
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
	public String getModel_code() {
		return model_code;
	}
	public void setModel_code(String model_code) {
		this.model_code = model_code;
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
	public String getPart_shot_no() {
		return part_shot_no;
	}
	public void setPart_shot_no(String part_shot_no) {
		this.part_shot_no = part_shot_no;
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
	

	
	@Override
	public String toString() {
		return "";
	}
	
	@SuppressWarnings("deprecation")
	public static void checkImportData(PartMainTenanance model) {
		StringBuffer checkInfo = new StringBuffer();
		if (StringUtils.isNullOrEmpty(model.getOperationType())) {
			checkInfo.append("操作类型不能为空；");
		} else {
			String operationType2 = model.getOperationType();
			if ("新增".equals(operationType2)) {
				/*"oldUnloadPort","oldSupplierNo", "oldPartNo", "oldReparePerson",
				"oldCarpool","oldStorage", "oldDistriPerson", "oldLocation", "oldStationCode",
				"oldShelfNo", "oldLocationNum",	"newUnloadPort", "newSupplierNo", "newPartNo", "newReparePerson","newCarpool",
				"newStorage", "newDistriPerson", "n
				ewLocation","newStationCode", "newShelfNo",
				"newLocationNum","operationType","modelCode","effStart","effEnd"*/
				StringBuffer  message = new StringBuffer();
				if (StringUtils.isNullOrEmpty(model.getNewPartNo())) {
					message.append("零件号、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewSupplierNo())) {
					message.append("供应商代码、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewUnloadPort())) {
					message.append("卸货口、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewReparePerson())) {
					message.append("备货工程、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewCarpool())) {
					message.append("台车号、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewStorage())) {
					message.append("物流库地址、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewDistriPerson())) {
					message.append("配送工程、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewLocation())) {
					message.append("配送地址、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewLocationNum())) {
					message.append("工程深度、");
				} else {
					String regex = "^([0-9])*";//非负整数正则
					boolean matches = model.getNewLocationNum().trim().matches(regex);
					if (!matches) {
						checkInfo.append("变更后工程深度为正整数；");
						model.setNewLocationNum("");
					}
				};
				if (StringUtils.isNullOrEmpty(model.getNewStationCode())) {
					message.append("工位号、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewShelfNo())) {
					message.append("货架号、");
				};
				
				if (StringUtils.isNullOrEmpty(model.getNewDeptNo())) {
					message.append("责任班组");
				};
				
				
				if (!StringUtils.isNullOrEmpty(message.toString())) {
					checkInfo.append("新增时变更后" + message.toString() + "不能为空；");
				}
			} else if ("移动".equals(operationType2)) {
				StringBuffer newMessage = new StringBuffer();
				StringBuffer oldMessage = new StringBuffer();
				if (StringUtils.isNullOrEmpty(model.getNewPartNo())) {
					newMessage.append("零件号、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewSupplierNo())) {
					newMessage.append("供应商代码、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewUnloadPort())) {
					newMessage.append("卸货口、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewReparePerson())) {
					newMessage.append("备货工程、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewCarpool())) {
					newMessage.append("台车号、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewStorage())) {
					newMessage.append("物流库地址、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewDistriPerson())) {
					newMessage.append("配送工程、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewLocation())) {
					newMessage.append("配送地址、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewLocationNum())) {
					newMessage.append("工程深度、");
				}else {
					String regex = "^([0-9])*";//非负整数正则
					boolean matches = model.getNewLocationNum().trim().matches(regex);
					if (!matches) {
						checkInfo.append("移动时变更后工程深度为正整数；");
						model.setNewLocationNum("-1");
					}
				};
				if (StringUtils.isNullOrEmpty(model.getNewStationCode())) {
					newMessage.append("工位号、");
				};
				if (StringUtils.isNullOrEmpty(model.getNewShelfNo())) {
					newMessage.append("货架号、");
				};
				
				if (StringUtils.isNullOrEmpty(model.getNewDeptNo())) {
					newMessage.append("责任班组");
				};
				
				//
				if (StringUtils.isNullOrEmpty(model.getOldPartNo())) {
					oldMessage.append("零件号、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldSupplierNo())) {
					oldMessage.append("供应商代码、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldUnloadPort())) {
					oldMessage.append("卸货口、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldReparePerson())) {
					oldMessage.append("备货工程、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldCarpool())) {
					oldMessage.append("台车号、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldStorage())) {
					oldMessage.append("物流库地址、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldDistriPerson())) {
					oldMessage.append("配送工程、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldLocation())) {
					oldMessage.append("配送地址、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldLocationNum())) {
					oldMessage.append("工程深度、");
				} else {
					String regex = "^([0-9])*";//非负整数正则
					boolean matches = model.getOldLocationNum().trim().matches(regex);
					if (!matches) {
						checkInfo.append("移动时变更前工程深度为正整数；");
						model.setOldLocationNum("");
					}
				};
				if (StringUtils.isNullOrEmpty(model.getOldStationCode())) {
					oldMessage.append("工位号、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldShelfNo())) {
					oldMessage.append("货架号、");
				};
				
				if (StringUtils.isNullOrEmpty(model.getOldDeptNo())) {
					oldMessage.append("责任班组");
				};
				
				if (!StringUtils.isNullOrEmpty(newMessage.toString())) {
					checkInfo.append("移动时变更后" + newMessage.toString() + "不能为空；");
				};
				if (!StringUtils.isNullOrEmpty(oldMessage.toString())) {
					checkInfo.append("移动时变更前" + oldMessage.toString() + "不能为空；");
				};
				
				
			}  else if ("废止".equals(operationType2)) {
				StringBuffer oldMessage = new StringBuffer();
				if (StringUtils.isNullOrEmpty(model.getOldPartNo())) {
					oldMessage.append("零件号、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldSupplierNo())) {
					oldMessage.append("供应商代码、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldUnloadPort())) {
					oldMessage.append("卸货口、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldReparePerson())) {
					oldMessage.append("备货工程、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldCarpool())) {
					oldMessage.append("台车号、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldStorage())) {
					oldMessage.append("物流库地址、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldDistriPerson())) {
					oldMessage.append("配送工程、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldLocation())) {
					oldMessage.append("配送地址、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldLocationNum())) {
					oldMessage.append("工程深度、");
				} else {
					String regex = "^([0-9])*";//非负整数正则
					boolean matches = model.getOldLocationNum().trim().matches(regex);
					if (!matches) {
						checkInfo.append("废止时变更前工程深度必须为正负整数；");
						model.setOldLocationNum("");
					}
				};
				if (StringUtils.isNullOrEmpty(model.getOldStationCode())) {
					oldMessage.append("工位号、");
				};
				if (StringUtils.isNullOrEmpty(model.getOldShelfNo())) {
					oldMessage.append("货架号");
				};
				
				if (StringUtils.isNullOrEmpty(model.getOldDeptNo())) {
					oldMessage.append("责任班组");
				};
				
				if (!StringUtils.isNullOrEmpty(oldMessage.toString())) {
					oldMessage.append("废止时变更前" + oldMessage.toString() + "不能为空；");
				};
			} else {
				checkInfo.append("操作类型不正确；");
			}
			
			String effStart2 = model.getEffStart();
			String effEnd2 = model.getEffEnd();
			String modelCode2 = model.getModelCode();
			if (StringUtils.isNullOrEmpty(effStart2) || StringUtils.isNullOrEmpty(effEnd2)) {
				
				checkInfo.append("生效日期或失效日期不能为空；");
			} else {
//				String startFormat = effStart2.substring(0, 10);
//				String endFormat = effEnd2.substring(0, 10);
				SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );

				try {
					format.parse(effStart2);
				} catch (Exception e) {
					checkInfo.append("生效日期格式不正确；");
					model.setEffStart("");
				}
				
				try {
					format.parse(effEnd2);
				} catch (Exception e) {
					checkInfo.append("失效日期格式不正确；");
					model.setEffEnd("");
				}
				
				try {
					format.parse(effStart2);
					format.parse(effEnd2);
					long number = parseTime(effEnd2) - parseTime(effStart2);
					if (number < 0L) {
						checkInfo.append("生效日期不得大于失效日期；");
					}
				} catch (Exception e) {
					
				}
				
			}
			if (StringUtils.isNullOrEmpty(modelCode2)) {
				checkInfo.append("车型不能为空；");
			}
				
		}
		if(StringUtils.isNullOrEmpty(checkInfo.toString())){
			model.setCheckFlag(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
		}else{
			model.setCheckFlag(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
			model.setCheckResult(checkInfo.toString());
		}
		
		model.setDealFlag("0");
	}
	
	/**
	 * 获得日期的long用来判断生效日期是否小于失效日期
	 * @param strTime
	 * @return
	 * Lxh
	 */
	private static long parseTime(String strTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long time = 0L;
		try {
			time = format.parse(strTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
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
	public Object getQrCode() {
		return qrCode;
	}
	public void setQrCode(Object qrCode) {
		this.qrCode = qrCode;
	}
	public Object getShelfImg() {
		return shelfImg;
	}
	public void setShelfImg(Object shelfImg) {
		this.shelfImg = shelfImg;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	   
 
}
