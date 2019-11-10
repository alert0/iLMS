package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * @ClassName: InvZGJStockModel
 * @Description: 支给件库存管理实体类
 * @author dtp
 * @date 2019年4月9日
 */
@SuppressWarnings("serial")
public class InvZGJStockModel extends AbstractModel<String>{
	
	
	private String standardPackage;
	
	/**
	 * 供应商代码
	 */
	private String supplierNo;

	private String id;
	
	/** 工厂代码 */
	private String factoryCode;
	/** 仓库代码 */
	private String wareCode;
	/** 仓库类型 */
	private String wareType;
	/** 仓库名称 */
	private String wareName;
	/** 零件号 */
	private String partNo;
	/** 简号 */
	private String partShortNo;
	/** 零件名称 */
	private String partName;
	/** 库位 */
	private String baseLocation;
	/** 安全库存 */
	private String safeStock;
	/** 最大库存 */
	private String maxStock;
	/** 库存数量 */
	private String stock;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/** 最后修改用户 */
	private String lastModifiedUser;
	/** 最后修改时间 */
	private String lastModifiedTime;
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
	public String getWareType() {
		return wareType;
	}
	public void setWareType(String wareType) {
		this.wareType = wareType;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
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
	public String getBaseLocation() {
		return baseLocation;
	}
	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}
	public String getSafeStock() {
		return safeStock;
	}
	public void setSafeStock(String safeStock) {
		this.safeStock = safeStock;
	}
	public String getMaxStock() {
		return maxStock;
	}
	public void setMaxStock(String maxStock) {
		this.maxStock = maxStock;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
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
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getStandardPackage() {
		return standardPackage;
	}
	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}
	
}
