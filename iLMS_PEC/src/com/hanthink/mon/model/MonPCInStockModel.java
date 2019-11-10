package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

public class MonPCInStockModel extends AbstractModel<String>{

	private static final long serialVersionUID = 5367636411960409676L;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String factoryCode;
	/** 零件编号 */
	private String partNo;
	/** 零件简号 */
	private String partShortNo;
	/** PC地址 */
	private String pcLocation;
	/** 线边地址 */
	private String lineLocation;
	/** 最大库存 */
	private String maxStock;
	/** 最小库存 */
	private String minStock;
	/** 实际在库 */
	private String stock;
	/** 状态 欠品，溢出，正常 */
	private String status;
	/** 下批到达时间 */
	private String nextArriveTime;
	/** 车间 */
	private String workCenter;
	/** 时间点 */
	private String calPoint;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
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
	public String getPcLocation() {
		return pcLocation;
	}
	public void setPcLocation(String pcLocation) {
		this.pcLocation = pcLocation;
	}
	public String getLineLocation() {
		return lineLocation;
	}
	public void setLineLocation(String lineLocation) {
		this.lineLocation = lineLocation;
	}
	public String getMaxStock() {
		return maxStock;
	}
	public void setMaxStock(String maxStock) {
		this.maxStock = maxStock;
	}
	public String getMinStock() {
		return minStock;
	}
	public void setMinStock(String minStock) {
		this.minStock = minStock;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNextArriveTime() {
		return nextArriveTime;
	}
	public void setNextArriveTime(String nextArriveTime) {
		this.nextArriveTime = nextArriveTime;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getCalPoint() {
		return calPoint;
	}
	public void setCalPoint(String calPoint) {
		this.calPoint = calPoint;
	}
}
