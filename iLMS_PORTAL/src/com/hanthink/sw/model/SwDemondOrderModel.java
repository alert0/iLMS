package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;
/**
 * 零件订单
 * @author 林卓
 *
 */
public class SwDemondOrderModel extends AbstractModel<String>{

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2019年3月13日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -3707195587105699370L;
	private String supplierNo;//供应商编号
	private String supplierName; //供应商名
	private String orderNo;//订单号
	private String orderDate;//订购日期
	private String arriveDate;//到货日期
	private String depotNo;//仓库编码
	private String purchaseNo;//订单号
	private String printStatus;//打印状态
	private String downloadStatus;//下载状态
	private String deliveryStatus;//发货状态
	private String receiveStatus;//收获状态
	private String orderType;//订单类型
	private String planPickUpTimeStr;//取货时间
	private String planPickUpTimeStrStart;//取货时间
	private String planPickUpTimeStrEnd;//取货时间
	private String printUser;//打印人
	private String printUserIp;//打印IP
	
	@Override
	public void setId(String id) {
		
	}

	@Override
	public String getId() {
		return null;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getDepotNo() {
		return depotNo;
	}

	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPlanPickUpTimeStr() {
		return planPickUpTimeStr;
	}

	public void setPlanPickUpTimeStr(String planPickUpTimeStr) {
		this.planPickUpTimeStr = planPickUpTimeStr;
	}

	public String getPlanPickUpTimeStrStart() {
		return planPickUpTimeStrStart;
	}

	public void setPlanPickUpTimeStrStart(String planPickUpTimeStrStart) {
		this.planPickUpTimeStrStart = planPickUpTimeStrStart;
	}

	public String getPlanPickUpTimeStrEnd() {
		return planPickUpTimeStrEnd;
	}

	public void setPlanPickUpTimeStrEnd(String planPickUpTimeStrEnd) {
		this.planPickUpTimeStrEnd = planPickUpTimeStrEnd;
	}

	public String getPrintUser() {
		return printUser;
	}

	public void setPrintUser(String printUser) {
		this.printUser = printUser;
	}

	public String getPrintUserIp() {
		return printUserIp;
	}

	public void setPrintUserIp(String printUserIp) {
		this.printUserIp = printUserIp;
	}

	@Override
	public String toString() {
		return "DemondOrderModel [supplierNo=" + supplierNo + ", supplierName=" + supplierName + ", orderNo=" + orderNo
				+ ", orderDate=" + orderDate + ", arriveDate=" + arriveDate + ", depotNo=" + depotNo + ", purchaseNo="
				+ purchaseNo + ", printStatus=" + printStatus + ", downloadStatus=" + downloadStatus
				+ ", deliveryStatus=" + deliveryStatus + ", receiveStatus=" + receiveStatus + ", orderType=" + orderType
				+ "]";
	}

	
}
