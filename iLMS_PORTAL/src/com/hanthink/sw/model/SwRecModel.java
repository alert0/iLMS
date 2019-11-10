package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

/**  
* <p>Title: SwRecModel.java</p>  
* <p>Description: 收货查询model</p>  
* <p>Company: hanthink</p>  
* @author luoxq  
* @date 2018年10月19日 下午6:04:16 
*/ 
public class SwRecModel extends AbstractModel<String>{

	
	private static final long serialVersionUID = -6343795718566917903L;

	/**id**/
	private String id;

	/**
	 * 采购订单收货表
	 */
	/**收获单号**/
	private String recNo;
	/**订单号**/
	private String purchaseNo;
	/**工厂**/
	private String factoryCode;
	/**收货人**/
	private String recUser;
	/**收货时间**/
	private String recTime;
	/**收货时间开始（查询条件）**/
	private String recTimeStart;
	/**收货时间结束（查询条件）**/
	private String recTimeEnd;
	/**接口处理标志**/
	private String dealFlag;
	/**接口处理时间**/
	private String dealTime;
	
	/**
	 * 采购订单收货明细表
	 */
	/**收货单行号**/
	private String recRowno;
	/**零件号**/
	private String partNo;
	/**收货数量**/
	private String recQty;
	/**订单收货次数**/
	private String recNums;
	/**创建时间**/
	private String creationTime;
	
	/**
	 * 采购订单表
	 */
	/**到货仓库**/
	private String depotNo;
	/**累计收货数量**/
	private String totalRecQty;
	/**订购日期**/
	private String orderDate;
	/**收货日期**/
	private String receiveDate;
	/**收货日期开始（查询条件）**/
	private String receiveDateStart;
	/**收货日期结束（查询条件）**/
	private String receiveDateEnd;
	/**收货状态**/
	private String receiveStatus;
	/**订购数量**/
	private String orderQty;
	
	/**物流单号**/
	private String orderNo;
	/**供应商代码**/
	private String supplierNo;
	/**出货地代码**/
	private String supFactory;
	/**供应商名称**/
	private String supplierName;
	/**简号**/
	private String partShortNo;
	/**零件名称**/
	private String partNameCn;
	/**采购单行号**/
	private String purchaseRowno;
	
	/**数据值**/
	private String codeValueName;
	/**到货时间**/
	private String arriveTime;
	/**到货时间开始**/
	private String arriveTimeStart;
	/**到货时间结束**/
	private String arriveTimeEnd;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRecNo() {
		return recNo;
	}
	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getRecUser() {
		return recUser;
	}
	public void setRecUser(String recUser) {
		this.recUser = recUser;
	}
	public String getRecTime() {
		return recTime;
	}
	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}
	public String getDealFlag() {
		return dealFlag;
	}
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getRecRowno() {
		return recRowno;
	}
	public void setRecRowno(String recRowno) {
		this.recRowno = recRowno;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getRecQty() {
		return recQty;
	}
	public void setRecQty(String recQty) {
		this.recQty = recQty;
	}
	public String getRecNums() {
		return recNums;
	}
	public void setRecNums(String recNums) {
		this.recNums = recNums;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}
	public String getTotalRecQty() {
		return totalRecQty;
	}
	public void setTotalRecQty(String totalRecQty) {
		this.totalRecQty = totalRecQty;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getRecTimeStart() {
		return recTimeStart;
	}
	public void setRecTimeStart(String recTimeStart) {
		this.recTimeStart = recTimeStart;
	}
	public String getRecTimeEnd() {
		return recTimeEnd;
	}
	public void setRecTimeEnd(String recTimeEnd) {
		this.recTimeEnd = recTimeEnd;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getPartShortNo() {
		return partShortNo;
	}
	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}
	public String getPartNameCn() {
		return partNameCn;
	}
	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public String getPurchaseRowno() {
		return purchaseRowno;
	}
	public void setPurchaseRowno(String purchaseRowno) {
		this.purchaseRowno = purchaseRowno;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getReceiveDateStart() {
		return receiveDateStart;
	}
	public void setReceiveDateStart(String receiveDateStart) {
		this.receiveDateStart = receiveDateStart;
	}
	public String getReceiveDateEnd() {
		return receiveDateEnd;
	}
	public void setReceiveDateEnd(String receiveDateEnd) {
		this.receiveDateEnd = receiveDateEnd;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getSupFactory() {
		return supFactory;
	}
	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}
	public String getCodeValueName() {
		return codeValueName;
	}
	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getArriveTimeStart() {
		return arriveTimeStart;
	}
	public void setArriveTimeStart(String arriveTimeStart) {
		this.arriveTimeStart = arriveTimeStart;
	}
	public String getArriveTimeEnd() {
		return arriveTimeEnd;
	}
	public void setArriveTimeEnd(String arriveTimeEnd) {
		this.arriveTimeEnd = arriveTimeEnd;
	}
	
	
}
