package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


/**
 * 订购单车BOM使用Model
 * @author WY
 *
 */
public class MpOrderBomModel extends AbstractModel<String>{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5953495523804925961L;
	
	/**
	* 工厂
	*/
	protected String factoryCode ; 
	
	/**
	* 生产订单号
	*/
	protected String orderNo; 
	
	/**
	 * 订单号数组
	 */
	private String[] orderNos;
	
	/**
	* 整车物料
	*/
	protected String vehiclePartNo; 
	
	/**
	 * 车型
	 */
	private String modelCode;
	
	/**
	* 子件号
	*/
	protected String partNo; 
	
	/**
	* 车间
	*/
	private String workcenter;
	
	/**
	* 工位号
	*/
	protected String stationCode; 
	
	/**
	* 用量
	*/
	protected String num; 
	
	/**
	* 子件单位
	*/
	protected String usageAmountUnit; 
	
	/**
	* 采购类型
	*/
	protected String purchaseType; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date creationTime; 
	
	/**
	* 最后修改时间
	*/
	protected java.util.Date lastModifiedTime;
	
	/**
	 * 零件名称
	 */
	protected String partNameCn;
    
	/**
	 * 数据值
	 */
	protected String codeValueName;
	
	/**
	 * 行号
	 */
	private String partRowNo;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String[] getOrderNos() {
		return orderNos;
	}
	public void setOrderNos(String[] orderNos) {
		this.orderNos = orderNos;
	}
	public String getVehiclePartNo() {
		return vehiclePartNo;
	}
	public void setVehiclePartNo(String vehiclePartNo) {
		this.vehiclePartNo = vehiclePartNo;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getUsageAmountUnit() {
		return usageAmountUnit;
	}
	public void setUsageAmountUnit(String usageAmountUnit) {
		this.usageAmountUnit = usageAmountUnit;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	public java.util.Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}
	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getPartNameCn() {
		return partNameCn;
	}
	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}
	public String getCodeValueName() {
		return codeValueName;
	}
	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}
	public String getPartRowNo() {
		return partRowNo;
	}
	public void setPartRowNo(String partRowNo) {
		this.partRowNo = partRowNo;
	}
	/**
	 * id
	 */
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	
}