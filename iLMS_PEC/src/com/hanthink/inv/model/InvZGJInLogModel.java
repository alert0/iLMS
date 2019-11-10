package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;

public class InvZGJInLogModel extends AbstractModel<String>{

private static final long serialVersionUID = -8239261808191526806L;
	
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂代码 */
	private String factoryCode;
	/** 物流单号 */
	private String orderNo;
	/** 入库单号 */
	private String recNo;
	/** 零件号 */
	private String partNo;
	/** 入库仓库 */
	private String depotNo;
	/** 简号 */
	private String partShortNo;
	/** 零件名称 */
	private String partName;
	/** 入库时间 */
	private String inTime;
	private String inTimeStart;
	private String inTimeEnd;
	/** 收货数量 */
	private String recQty;

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
	public String getRecNo() {
		return recNo;
	}
	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
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
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getInTimeStart() {
		return inTimeStart;
	}
	public void setInTimeStart(String inTimeStart) {
		this.inTimeStart = inTimeStart;
	}
	public String getInTimeEnd() {
		return inTimeEnd;
	}
	public void setInTimeEnd(String inTimeEnd) {
		this.inTimeEnd = inTimeEnd;
	}
	public String getRecQty() {
		return recQty;
	}
	public void setRecQty(String recQty) {
		this.recQty = recQty;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
