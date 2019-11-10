package com.hanthink.jit.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：MM_JIT_ORDER 实体对象
 * 作者:dtp
 * 日期:2018-09-28 13:42:43
 * </pre>
 */
public class JitOrderModel extends AbstractModel<String>{
	
	/**
	 * 打印人IP
	 */
	protected String printUserIp;
	
	/**
	 * 用户ID
	 */
	protected String userId;
	
	/**
	 * 用户类型
	 */
	protected String userType;
	
	/**
	 * 托盘总数
	 */
	protected String total;
	
	/**
	 * 序号
	 */
	protected String no;
	
	/**
	 * 序号2
	 */
	protected String no2;
	
	/**
	 * 拣货地址
	 */
	protected String storage;
	
	/**
	 * 拣货地址2
	 */
	protected String storage2;
	
	/**
	 * 配送地址2
	 */
	protected String location2;
	
	/**
	 * 数量2
	 */
	protected String requireNum2;
	
	private static final long serialVersionUID = -158834059105725275L;
	
	protected String id;

	/**
	 * 车间
	 */
	protected String workcenter;
	
	/**
	 * 物流单号
     */
	protected String orderNo; 
	
	/**
	 * 信息点
     */
	protected String planCode;
	
	/**
	 * 信息点描述
	 */
	protected String planCodeDesc;
	
	/**
	 * 出货仓库
     */
	protected String shipDepot; 
	
	/**
	 * 到货仓库
     */
	protected String arrDepot; 
	
	/**
	 * 出货仓库类型数据字典类型“PUB_SHIP_DEPOT_TYPE”     
	 */
	protected String shipDepotType; 
	
	/**
	 * 出货仓库描述
	 */
	protected String shipDepotTypeDesc;
	
	/**
	 * 出货地代码专属供应商订单     
	 */
	protected String supFactory; 
	
	/**
	 * 供应商代码专属供应商订单     
	 */
	protected String supplierNo; 
	
	/**
	 * 供应商名称专属供应商订单     
	 */
	protected String supplierName; 
	
	/**
	 * 开始备货批次产品流水号
     */
	protected String sprepareProductSeqno; 
	
	/**
	 * 结束备货批次产品流水号
     */
	protected String eprepareProductSeqno; 
	
	/**
	 * 发车批次产品流水号
     */
	protected String dispatchProductSeqno; 
	
	/**
	 * 发货批次产品流水号
     */
	protected String deliveryProductSeqno; 
	
	/**
	 * 到货批次产品流水号
     */
	protected String arriveProductSeqno; 
	
	/**
	 * 开始备货批次
     */
	protected String sprepareBatchNo; 
	
	/**
	 * 结束备货批次
     */
	protected String eprepareBatchNo; 
	
	/**
	 * 发货批次
     */
	protected String deliveryBatchNo; 
	
	/**
	 * 发货批次至
	 */
	protected String deliveryBatchNoTo; 
	
	/**
	 * 打印状态数据字典类型“PRINT_STATUS”     
	 */
	protected String printStatus; 
	
	/**
	 * 打印状态描述
	 */
	protected String printStatusDesc;
	
	/**
	 * 打印时间
     */
	protected String printTime; 
	
	/**
	 * 打印人
     */
	protected String printUser; 
	
	/**
	 * 物流单号(不包含差异序号)
     */
	protected String orderNoBatch; 
	
	/**
	 * 单号差异序号
     */
	protected String orderNoDiffseq; 
	
	/**
	 * 订单号
     */
	protected String purchaseOrderno; 
	
	/**
	 * 创建时间从
     */
	protected String creationTime; 
	
	/**
	 * 创建日期至
	 */
	protected String creationTimeTo;
	
	/**
	 * 备货状态数据字典类型“PREPARE_STATUS”     
	 */
	protected String prepareStatus;
	
	/**
	 * 备货状态描述
	 */
	protected String prepareStatusDesc;
	
	
	/**
	 * 备货次数
     */
	protected String prepareCount; 
	
	/**
	 * 实际备货产品流水号
     */
	protected String actualPreProductSeqno; 
	
	/**
	 * 发货状态数据字典类型“DELIVERY_STATUS”     
	 */
	protected String deliveryStatus;
	
	/**
	 * 发货状态描述
	 */
	protected String deliveryStatusDesc;
	
	
	/**
	 * 发货次数
     */
	protected String deliveryCount; 
	
	/**
	 * 实际发货产品流水号
     */
	protected String actualDeliProductSeqno; 
	
	/**
	 * 到货状态数据字典类型“ARRIVE_STATUS”     
	 */
	protected String arriveStatus; 
	
	/**
	 * 到货状态描述
	 */
	protected String arriveStatusDesc; 
	
	/**
	 * 到货次数
     */
	protected String arriveCount; 
	
	/**
	 * 实际到货产品流水号
     */
	protected String actualArrProductSeqno; 
	
	/**
	 * 接口处理标志位
	 * 0：未处理
	 * 1：已处理   
	 */
	protected String dealFlag; 
	
	/**
	 * 接口处理时间
     */
	protected String dealTime; 
	
	/**
	 * 备件时间
     */
	protected String prepareTime; 
	
	/**
	 * 发车时间
     */
	protected String dispatchTime; 
	
	/**
	 * 发货时间
     */
	protected String deliveryTime; 
	
	/**
	 * 到货时间
     */
	protected String arriveTime;
	
	/**
	 * 物流单行号
     */
	protected String orderRowno; 
	
	/**
	 * 简号
     */
	protected String partShortNo; 
	
	/**
	 * 零件名称
     */
	protected String partName; 
	
	/**
	 * 收容数
     */
	protected String standardPackage; 
	
	/**
	 * 箱数
     */
	protected String boxNum; 
	
	/**
	 * 配送量
     */
	protected String requireNum; 

	/**
	 * 零件号
     */
	protected String partNo; 
	
	/**
	 * 配送地址
     */
	protected String location; 
	
	/**
	 * 备货数量
     */
	protected String prepareNum; 
	
	/**
	 * 条形码
	 */
	protected String barCode;
	
	/**
	 * 卸货口
	 */
	protected String unloadPort;
	
	/**
	 * 批次 DISTRI_PRODUCT_SEQNO
	 */
	protected String distriProductSeqno;
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	
	/**
	 * 看板名称
	 */
	protected String kbName;
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getStorage2() {
		return storage2;
	}

	public void setStorage2(String storage2) {
		this.storage2 = storage2;
	}

	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	public String getRequireNum2() {
		return requireNum2;
	}

	public void setRequireNum2(String requireNum2) {
		this.requireNum2 = requireNum2;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanCodeDesc() {
		return planCodeDesc;
	}

	public void setPlanCodeDesc(String planCodeDesc) {
		this.planCodeDesc = planCodeDesc;
	}

	public String getShipDepot() {
		return shipDepot;
	}

	public void setShipDepot(String shipDepot) {
		this.shipDepot = shipDepot;
	}

	public String getArrDepot() {
		return arrDepot;
	}

	public void setArrDepot(String arrDepot) {
		this.arrDepot = arrDepot;
	}

	public String getShipDepotType() {
		return shipDepotType;
	}

	public void setShipDepotType(String shipDepotType) {
		this.shipDepotType = shipDepotType;
	}

	public String getShipDepotTypeDesc() {
		return shipDepotTypeDesc;
	}

	public void setShipDepotTypeDesc(String shipDepotTypeDesc) {
		this.shipDepotTypeDesc = shipDepotTypeDesc;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
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

	public String getSprepareProductSeqno() {
		return sprepareProductSeqno;
	}

	public void setSprepareProductSeqno(String sprepareProductSeqno) {
		this.sprepareProductSeqno = sprepareProductSeqno;
	}

	public String getEprepareProductSeqno() {
		return eprepareProductSeqno;
	}

	public void setEprepareProductSeqno(String eprepareProductSeqno) {
		this.eprepareProductSeqno = eprepareProductSeqno;
	}

	public String getDispatchProductSeqno() {
		return dispatchProductSeqno;
	}

	public void setDispatchProductSeqno(String dispatchProductSeqno) {
		this.dispatchProductSeqno = dispatchProductSeqno;
	}

	public String getDeliveryProductSeqno() {
		return deliveryProductSeqno;
	}

	public void setDeliveryProductSeqno(String deliveryProductSeqno) {
		this.deliveryProductSeqno = deliveryProductSeqno;
	}

	public String getArriveProductSeqno() {
		return arriveProductSeqno;
	}

	public void setArriveProductSeqno(String arriveProductSeqno) {
		this.arriveProductSeqno = arriveProductSeqno;
	}

	public String getSprepareBatchNo() {
		return sprepareBatchNo;
	}

	public void setSprepareBatchNo(String sprepareBatchNo) {
		this.sprepareBatchNo = sprepareBatchNo;
	}

	public String getEprepareBatchNo() {
		return eprepareBatchNo;
	}

	public void setEprepareBatchNo(String eprepareBatchNo) {
		this.eprepareBatchNo = eprepareBatchNo;
	}

	public String getDeliveryBatchNo() {
		return deliveryBatchNo;
	}

	public void setDeliveryBatchNo(String deliveryBatchNo) {
		this.deliveryBatchNo = deliveryBatchNo;
	}

	public String getDeliveryBatchNoTo() {
		return deliveryBatchNoTo;
	}

	public void setDeliveryBatchNoTo(String deliveryBatchNoTo) {
		this.deliveryBatchNoTo = deliveryBatchNoTo;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getPrintStatusDesc() {
		return printStatusDesc;
	}

	public void setPrintStatusDesc(String printStatusDesc) {
		this.printStatusDesc = printStatusDesc;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public String getPrintUser() {
		return printUser;
	}

	public void setPrintUser(String printUser) {
		this.printUser = printUser;
	}

	public String getOrderNoBatch() {
		return orderNoBatch;
	}

	public void setOrderNoBatch(String orderNoBatch) {
		this.orderNoBatch = orderNoBatch;
	}

	public String getOrderNoDiffseq() {
		return orderNoDiffseq;
	}

	public void setOrderNoDiffseq(String orderNoDiffseq) {
		this.orderNoDiffseq = orderNoDiffseq;
	}

	public String getPurchaseOrderno() {
		return purchaseOrderno;
	}

	public void setPurchaseOrderno(String purchaseOrderno) {
		this.purchaseOrderno = purchaseOrderno;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreationTimeTo() {
		return creationTimeTo;
	}

	public void setCreationTimeTo(String creationTimeTo) {
		this.creationTimeTo = creationTimeTo;
	}

	public String getPrepareStatus() {
		return prepareStatus;
	}

	public void setPrepareStatus(String prepareStatus) {
		this.prepareStatus = prepareStatus;
	}

	public String getPrepareStatusDesc() {
		return prepareStatusDesc;
	}

	public void setPrepareStatusDesc(String prepareStatusDesc) {
		this.prepareStatusDesc = prepareStatusDesc;
	}

	public String getPrepareCount() {
		return prepareCount;
	}

	public void setPrepareCount(String prepareCount) {
		this.prepareCount = prepareCount;
	}

	public String getActualPreProductSeqno() {
		return actualPreProductSeqno;
	}

	public void setActualPreProductSeqno(String actualPreProductSeqno) {
		this.actualPreProductSeqno = actualPreProductSeqno;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryStatusDesc() {
		return deliveryStatusDesc;
	}

	public void setDeliveryStatusDesc(String deliveryStatusDesc) {
		this.deliveryStatusDesc = deliveryStatusDesc;
	}

	public String getDeliveryCount() {
		return deliveryCount;
	}

	public void setDeliveryCount(String deliveryCount) {
		this.deliveryCount = deliveryCount;
	}

	public String getActualDeliProductSeqno() {
		return actualDeliProductSeqno;
	}

	public void setActualDeliProductSeqno(String actualDeliProductSeqno) {
		this.actualDeliProductSeqno = actualDeliProductSeqno;
	}

	public String getArriveStatus() {
		return arriveStatus;
	}

	public void setArriveStatus(String arriveStatus) {
		this.arriveStatus = arriveStatus;
	}

	public String getArriveStatusDesc() {
		return arriveStatusDesc;
	}

	public void setArriveStatusDesc(String arriveStatusDesc) {
		this.arriveStatusDesc = arriveStatusDesc;
	}

	public String getArriveCount() {
		return arriveCount;
	}

	public void setArriveCount(String arriveCount) {
		this.arriveCount = arriveCount;
	}

	public String getActualArrProductSeqno() {
		return actualArrProductSeqno;
	}

	public void setActualArrProductSeqno(String actualArrProductSeqno) {
		this.actualArrProductSeqno = actualArrProductSeqno;
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

	public String getPrepareTime() {
		return prepareTime;
	}

	public void setPrepareTime(String prepareTime) {
		this.prepareTime = prepareTime;
	}

	public String getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getOrderRowno() {
		return orderRowno;
	}

	public void setOrderRowno(String orderRowno) {
		this.orderRowno = orderRowno;
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

	public String getStandardPackage() {
		return standardPackage;
	}

	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}

	public String getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	public String getRequireNum() {
		return requireNum;
	}

	public void setRequireNum(String requireNum) {
		this.requireNum = requireNum;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPrepareNum() {
		return prepareNum;
	}

	public void setPrepareNum(String prepareNum) {
		this.prepareNum = prepareNum;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}

	public String getDistriProductSeqno() {
		return distriProductSeqno;
	}

	public void setDistriProductSeqno(String distriProductSeqno) {
		this.distriProductSeqno = distriProductSeqno;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getKbName() {
		return kbName;
	}

	public void setKbName(String kbName) {
		this.kbName = kbName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPrintUserIp() {
		return printUserIp;
	}

	public void setPrintUserIp(String printUserIp) {
		this.printUserIp = printUserIp;
	}
	
}
