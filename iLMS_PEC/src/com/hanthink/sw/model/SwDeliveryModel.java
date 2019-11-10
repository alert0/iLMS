package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
* <p>Title: SwDeliveryModel</p>  
* <p>Description: 发货数据管理实体类</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月17日 下午5:15:48
 */
public class SwDeliveryModel extends AbstractModel<String>{

	
	private static final long serialVersionUID = -3403567977257552779L;
	/**id**/
	private String id;
	/**发货单号**/
	private String deliveryNo;
	/**订单号**/
	private String purchaseNo;
	/**工厂代码**/
	private String factoryCode;
	/**发货人**/
	private String deliveryUser;
	/**发货时间**/
	private String deliveryTime;
	/**发货时间开始（用于查询条件）**/
	private String deliveryTimeStart;
	/**发货时间结束（用于查询条件）**/
	private String deliveryTimeEnd;
	/**打印状态**/
	private String printStatus;
	/**打印时间**/
	private String printTime;
	/**最后修改用户**/
	private String lastModifiedUser;
	
	/**
	 * 明细表
	 */
	/**发货单行号**/
	private String deliveryRowno;
	/**零件号**/
	private String partNo;
	/**发货数量**/
	private String deliveryNums;
	/**创建时间**/
	private String creationTime;
	
	/**
	 * 界面需要用到字段
	 */
	/**供应商代码**/
	private String supplierNo;
	/**供应商名称**/
	private String supplierName;
	/**物流单号**/
	private String orderNo;
	/**订单类型**/
	private String orderType;
	/**订购日期**/
	private String orderDate;
	/**仓库**/
	private String depotNo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeliveryNo() {
		return deliveryNo;
	}
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
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
	public String getDeliveryUser() {
		return deliveryUser;
	}
	public void setDeliveryUser(String deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getPrintStatus() {
		return printStatus;
	}
	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getDeliveryRowno() {
		return deliveryRowno;
	}
	public void setDeliveryRowno(String deliveryRowno) {
		this.deliveryRowno = deliveryRowno;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getDeliveryNums() {
		return deliveryNums;
	}
	public void setDeliveryNums(String deliveryNums) {
		this.deliveryNums = deliveryNums;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getDepotNo() {
		return depotNo;
	}
	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}
	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}
	public void setDeliveryTimeStart(String deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}
	public String getDeliveryTimeEnd() {
		return deliveryTimeEnd;
	}
	public void setDeliveryTimeEnd(String deliveryTimeEnd) {
		this.deliveryTimeEnd = deliveryTimeEnd;
	}
	
}
