package com.hanthink.inv.model;


import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
import com.mysql.jdbc.StringUtils;

public class ShelfManager  extends AbstractModel<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String factoryCode;
	private String partNo; //零件号
	private String partName; //零件名称
	private String partShortNo; //简号
	private String supplierNo; //供应商代码
	private String supplierName; //供应商名称
	private String shelvesAddr; //货架地址
	private String standardPack; //规格包装
	private String safeStock; //安全库存
	private String carType; //车型
	private String stackLayers; //叠放次数
	private String modifiedTime; //更新日期
	private String creatBy;
	private String dealFlag;
	private String checkFlag;
	private String checkResult;
	
	
	
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getDealFlag() {
		return dealFlag;
	}
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String getId() {
		return id;
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
	public String getPartShortNo() {
		return partShortNo;
	}
	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
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
	public String getShelvesAddr() {
		return shelvesAddr;
	}
	public void setShelvesAddr(String shelvesAddr) {
		this.shelvesAddr = shelvesAddr;
	}
	public String getStandardPack() {
		return standardPack;
	}
	public void setStandardPack(String standardPack) {
		this.standardPack = standardPack;
	}
	public String getSafeStock() {
		return safeStock;
	}
	public void setSafeStock(String safeStock) {
		this.safeStock = safeStock;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getStackLayers() {
		return stackLayers;
	}
	public void setStackLayers(String stackLayers) {
		this.stackLayers = stackLayers;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	
	public String getCreatBy() {
		return creatBy;
	}
	public void setCreatBy(String creatBy) {
		this.creatBy = creatBy;
	}
	@Override
	public String toString() {
		return "ShelfManager [id=" + id + ", partNo=" + partNo + ", partName=" + partName + ", partShortNo="
				+ partShortNo + ", supplierNo=" + supplierNo + ", supplierName=" + supplierName + ", shelvesAddr="
				+ shelvesAddr + ", standardPack=" + standardPack + ", safeStock=" + safeStock + ", carType=" + carType
				+ ", stackLayers=" + stackLayers + ", modifiedTime=" + modifiedTime + "]";
	}
	
	public static void checkImportData(ShelfManager model) {
		StringBuffer checkInfo = new StringBuffer();
		if (StringUtils.isNullOrEmpty(model.getPartNo())) {
			checkInfo.append("零件号不能为空");
		} 
		if (StringUtils.isNullOrEmpty(model.getSupplierNo())) {
			checkInfo.append("供应商代码不能为空");
		} 
		if (StringUtils.isNullOrEmpty(model.getShelvesAddr())) {
			checkInfo.append("货架地址不能为空");
		} 
		
		String regex = "^([0-9])*";
		String safeStock = model.getSafeStock();//安全库存
		if (!StringUtil.isEmpty(safeStock)) {
			boolean  isCorrect = safeStock.trim().matches(regex);
			if (!isCorrect) {
				checkInfo.append( "若安全库存不为空，则必须填写大于0的整数");
			}
		}
		String stackLayers = model.getStackLayers();//叠放层数
		if (!StringUtil.isEmpty(stackLayers)) {
			boolean  isCorrect = stackLayers.trim().matches(regex);
			if (!isCorrect) {
				checkInfo.append( "若叠放层数不为空，则必须填写大于0的整数");
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
	
	
	
	
	
}
