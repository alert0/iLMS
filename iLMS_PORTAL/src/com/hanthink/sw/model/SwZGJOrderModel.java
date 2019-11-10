package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

@SuppressWarnings("serial")
public class SwZGJOrderModel extends AbstractModel<String>{
	
	/**
	 * 供应商代码查询
	 */
	private String supplierNoAuth;
	
	/**
	 * 到达时间
	 */
	private String arriveTime;
	
	/**
	 * 订单号Str
	 */
	private String orderNoStr;
	/**采购单号**/
	private String purchaseNoArr;
	
	/**
	 * 物流订单号Str
	 */
	private String purchaseNoStr;
	
	private String boxesNum;
	private String cancelNum;
	private String totalRecQty;
	private String currentQty;
	/**
	 * 卸货口
	 */
	private String xhk;
	/**
	 * 
	 */
	private String bh;
	private String dh;
	private String dfwz;
	private String ljpc;
	private String page;
	
	/**
	 * 箱数
	 */
	private String xs;
	
	/**
	 * 采购单号
	 */
	private String purchaseNo;
	
	/**
	 * 采购单行号
	 */
	private String purchaseRowNo;
	
	/**
	 * 采购单行号
	 */
	private String orderNo;
	
	/**
	 * 订单类型
	 */
	private String orderType;
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	
	/**
	 * 供应商代码
	 */
	private String supplierNo;
	
	/**
	 * 订购日期
	 */
	private String orderDate;
	
	/**
	 * 到货日期至
	 */
	private String arriveDateTo;
	
	/**
	 * 仓库编码
	 */
	private String depotNo;
	
	/**
	 * 出货地代码
	 */
	private String supFactory;
	
	/**
	 * 打印状态
	 */
	private String printStatus;
	
	/**
	 * 打印状态
	 */
	private String printStatusDesc;
	
	/**
	 * 打印时间
	 */
	private String printTime;
	
	/**
	 * 标签打印状态
	 */
	private String labelPrintStatus;
	
	/**
	 * 标签打印时间
	 */
	private String labelPrintTime;
	
	/**
	 * 发货状态
	 */
	private String deliveryStatus;
	
	/**
	 * 收货状态
	 */
	private String receiveStatus;
	
	/**
	 * 收货状态
	 */
	private String receiveStatusDesc;
	
	/**
	 * 下载状态
	 */
	private String downloadStatus;
	
	/**
	 * 供应商反馈交付日期
	 */
	private String replyDeliveryDate;
	
	/**
	 * 是否紧急订单
	 */
	private String isUrgent;
	
	/**
	 * 物流订单行号
	 */
	private String orderRowno;
	
	/**
	 * 订购数量
	 */
	private String orderQty;
	
	/**
	 * 订购单位
	 */
	private String orderUnit;
	
	/**
	 * 零件编号
	 */
	private String partNo;
	
	/**
	 * 包装规格
	 */
	private String standardPackage;
	
	/**
	 * 累计发货数量
	 */
	private String totalDelivQty;
	
	/**
	 * 收货日期
	 */
	private String receiveDate;
	
	/**
	 * 收货次数
	 */
	private String receiveCount;
	
	/**
	 * 取消数量
	 */
	private String cancleNum;
	
	/**
	 * 已备数量
	 */
	private String prepareNum;
	
	/**
	 * 到货数量
	 */
	private String arriveNum;
	
	/**
	 * 最后修改用户
	 */
	private String lasetModifiedUser;
	/**本次发货数**/
	private String tempDelivQty;
	
	//****************标签属性*******************************
	/**
	 * uuid
	 */
	protected String uuid;
	
	/**
	 * 打印人
	 */
	protected String printUser;
	
	/**
	 * 打印人IP
	 */
	protected String printUserIp;
	
	/**
	 * 拣货地址
	 */
	protected String storage;
	
	/**
	 * 拣货工程
	 */
	protected String preparePerson;

	/**
	 * ID
	 */
	protected String id;
	
	/**
	 * 用户ID
	 */
	protected String userId;
	
	/**
	 * 用户类型
	 */
	protected String userType;
	
	/**
	 * 车间
	 */
	protected String workcenter;
	
	/**
	 * 收容数
	 */
	protected String srs;
	
	/**
	 * 订单行号
	 */
	protected String orderRowNo;
	
	/**
	 * 标签行号
     */
	protected String labelRowno; 
	
	
	/**
	 * 信息点
     */
	protected String planCode; 
	
	/**
	 * 信息点描述
	 */
	protected String planCodeDesc;
	
	/**
	 * 配送地址
     */
	protected String location; 
	
	/**
	 * 配送量
     */
	protected String requireNum; 
	
	/**
	 * 简号
     */
	protected String partShortNo; 
	
	/**
	 * 零件名称
     */
	protected String partName; 
	
	/**
	 * 供应商名称
     */
	protected String supplierName; 
	
	/**
	 * 出货仓库
     */
	protected String shipDepot; 
	
	/**
	 * 到货仓库
     */
	protected String arrDepot; 
	
	/**
	 * 配送包装数
     */
	protected String distriPackage; 
	
	/**
	 * 卸货口
	 */
	protected String unloadPort;
	
	/**
	 * 配送工程
     */
	protected String distriPerson; 
	
	/**
	 * 下线批次产品流水号
     */
	protected String kbProductSeqno; 
	
	/**
	 * 备件批次产品流水号
     */
	protected String prepareProductSeqno; 
	
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
	 * 配送批次产品流水号
     */
	protected String distriProductSeqno; 
	
	/**
	 * 装配批次产品流水号
	 */
	protected String assembleProductSeqno;
	
	/**
	 * 下线时间
     */
	protected String kbTime; 
	
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
	protected String arriveDate; 
	
	/**
	 * 配送时间
     */
	protected String distriTime; 
	
	/**
	 * 备件批次
     */
	protected String prepareBatchNo; 
	
	/**
	 * 备件批次至
	 */
	protected String prepareBatchNoTo;
	
	/**
	 * 下线批次
     */
	protected String kbBatchNo; 
	
	/**
	 * 创建时间
     */
	protected String creationTime; 
	
	/**
	 * 接口处理标志位0：未处理1：已处理     
	 */
	protected String dealFlag; 
	
	/**
	 * 接口处理时间
     */
	protected String dealTime; 
	
	/**
	 * 装配时间     
	 */
	protected String assembleTime; 
	
	/**
	 * 创建日期至
	 */
	protected String creationTimeTo;
	
	/**
	 * 装车代码
	 */
	protected String unloadCode;
	
	/**
	 * 订单号
	 */
	protected String purchaseOrderno;
	
	/**
	 * 订单日期 时分
	 */
	protected String orderTimeHhmi;
	
	/**
	 * 订单日期 年月日
	 */
	protected String orderTimeYmd;
	
	/**
	 * 看板张数
	 */
	protected String kbzs;
	
	/**
	 * 看板名称
	 */
	protected String kbName;
	
	/**
	 * 二维码
	 */
	protected Object QRCode;
	
	/**
	 * 标签张数
	 */
	protected String labelPageNum;

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseRowNo() {
		return purchaseRowNo;
	}

	public void setPurchaseRowNo(String purchaseRowNo) {
		this.purchaseRowNo = purchaseRowNo;
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

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
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

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
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

	public String getLabelPrintStatus() {
		return labelPrintStatus;
	}

	public void setLabelPrintStatus(String labelPrintStatus) {
		this.labelPrintStatus = labelPrintStatus;
	}

	public String getLabelPrintTime() {
		return labelPrintTime;
	}

	public void setLabelPrintTime(String labelPrintTime) {
		this.labelPrintTime = labelPrintTime;
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

	public String getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public String getReplyDeliveryDate() {
		return replyDeliveryDate;
	}

	public void setReplyDeliveryDate(String replyDeliveryDate) {
		this.replyDeliveryDate = replyDeliveryDate;
	}

	public String getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}

	public String getOrderRowno() {
		return orderRowno;
	}

	public void setOrderRowno(String orderRowno) {
		this.orderRowno = orderRowno;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}

	public String getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	

	public String getTotalDelivQty() {
		return totalDelivQty;
	}

	public void setTotalDelivQty(String totalDelivQty) {
		this.totalDelivQty = totalDelivQty;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(String receiveCount) {
		this.receiveCount = receiveCount;
	}

	public String getCancleNum() {
		return cancleNum;
	}

	public void setCancleNum(String cancleNum) {
		this.cancleNum = cancleNum;
	}

	public String getPrepareNum() {
		return prepareNum;
	}

	public void setPrepareNum(String prepareNum) {
		this.prepareNum = prepareNum;
	}

	public String getArriveNum() {
		return arriveNum;
	}

	public void setArriveNum(String arriveNum) {
		this.arriveNum = arriveNum;
	}

	public String getLasetModifiedUser() {
		return lasetModifiedUser;
	}

	public void setLasetModifiedUser(String lasetModifiedUser) {
		this.lasetModifiedUser = lasetModifiedUser;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getPreparePerson() {
		return preparePerson;
	}

	public void setPreparePerson(String preparePerson) {
		this.preparePerson = preparePerson;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public String getOrderRowNo() {
		return orderRowNo;
	}

	public void setOrderRowNo(String orderRowNo) {
		this.orderRowNo = orderRowNo;
	}

	public String getLabelRowno() {
		return labelRowno;
	}

	public void setLabelRowno(String labelRowno) {
		this.labelRowno = labelRowno;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRequireNum() {
		return requireNum;
	}

	public void setRequireNum(String requireNum) {
		this.requireNum = requireNum;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

	public String getStandardPackage() {
		return standardPackage;
	}

	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}

	public String getDistriPackage() {
		return distriPackage;
	}

	public void setDistriPackage(String distriPackage) {
		this.distriPackage = distriPackage;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}

	public String getDistriPerson() {
		return distriPerson;
	}

	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}

	public String getKbProductSeqno() {
		return kbProductSeqno;
	}

	public void setKbProductSeqno(String kbProductSeqno) {
		this.kbProductSeqno = kbProductSeqno;
	}

	public String getPrepareProductSeqno() {
		return prepareProductSeqno;
	}

	public void setPrepareProductSeqno(String prepareProductSeqno) {
		this.prepareProductSeqno = prepareProductSeqno;
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

	public String getDistriProductSeqno() {
		return distriProductSeqno;
	}

	public void setDistriProductSeqno(String distriProductSeqno) {
		this.distriProductSeqno = distriProductSeqno;
	}

	public String getAssembleProductSeqno() {
		return assembleProductSeqno;
	}

	public void setAssembleProductSeqno(String assembleProductSeqno) {
		this.assembleProductSeqno = assembleProductSeqno;
	}

	public String getKbTime() {
		return kbTime;
	}

	public void setKbTime(String kbTime) {
		this.kbTime = kbTime;
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

	public String getArriveDateTo() {
		return arriveDateTo;
	}

	public void setArriveDateTo(String arriveDateTo) {
		this.arriveDateTo = arriveDateTo;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getDistriTime() {
		return distriTime;
	}

	public void setDistriTime(String distriTime) {
		this.distriTime = distriTime;
	}

	public String getPrepareBatchNo() {
		return prepareBatchNo;
	}

	public void setPrepareBatchNo(String prepareBatchNo) {
		this.prepareBatchNo = prepareBatchNo;
	}

	public String getPrepareBatchNoTo() {
		return prepareBatchNoTo;
	}

	public void setPrepareBatchNoTo(String prepareBatchNoTo) {
		this.prepareBatchNoTo = prepareBatchNoTo;
	}

	public String getKbBatchNo() {
		return kbBatchNo;
	}

	public void setKbBatchNo(String kbBatchNo) {
		this.kbBatchNo = kbBatchNo;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
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

	public String getAssembleTime() {
		return assembleTime;
	}

	public void setAssembleTime(String assembleTime) {
		this.assembleTime = assembleTime;
	}

	public String getCreationTimeTo() {
		return creationTimeTo;
	}

	public void setCreationTimeTo(String creationTimeTo) {
		this.creationTimeTo = creationTimeTo;
	}

	public String getUnloadCode() {
		return unloadCode;
	}

	public void setUnloadCode(String unloadCode) {
		this.unloadCode = unloadCode;
	}

	public String getPurchaseOrderno() {
		return purchaseOrderno;
	}

	public void setPurchaseOrderno(String purchaseOrderno) {
		this.purchaseOrderno = purchaseOrderno;
	}

	public String getOrderTimeHhmi() {
		return orderTimeHhmi;
	}

	public void setOrderTimeHhmi(String orderTimeHhmi) {
		this.orderTimeHhmi = orderTimeHhmi;
	}

	public String getOrderTimeYmd() {
		return orderTimeYmd;
	}

	public void setOrderTimeYmd(String orderTimeYmd) {
		this.orderTimeYmd = orderTimeYmd;
	}

	public String getKbzs() {
		return kbzs;
	}

	public void setKbzs(String kbzs) {
		this.kbzs = kbzs;
	}

	public String getKbName() {
		return kbName;
	}

	public void setKbName(String kbName) {
		this.kbName = kbName;
	}

	public Object getQRCode() {
		return QRCode;
	}

	public void setQRCode(Object qRCode) {
		QRCode = qRCode;
	}

	public String getLabelPageNum() {
		return labelPageNum;
	}

	public void setLabelPageNum(String labelPageNum) {
		this.labelPageNum = labelPageNum;
	}

	public String getXs() {
		return xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

	public String getReceiveStatusDesc() {
		return receiveStatusDesc;
	}

	public void setReceiveStatusDesc(String receiveStatusDesc) {
		this.receiveStatusDesc = receiveStatusDesc;
	}

	public String getBoxesNum() {
		return boxesNum;
	}

	public void setBoxesNum(String boxesNum) {
		this.boxesNum = boxesNum;
	}

	public String getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(String cancelNum) {
		this.cancelNum = cancelNum;
	}

	public String getTotalRecQty() {
		return totalRecQty;
	}

	public void setTotalRecQty(String totalRecQty) {
		this.totalRecQty = totalRecQty;
	}

	public String getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(String currentQty) {
		this.currentQty = currentQty;
	}

	public String getOrderNoStr() {
		return orderNoStr;
	}

	public void setOrderNoStr(String orderNoStr) {
		this.orderNoStr = orderNoStr;
	}

	public String getXhk() {
		return xhk;
	}

	public void setXhk(String xhk) {
		this.xhk = xhk;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getDh() {
		return dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	public String getDfwz() {
		return dfwz;
	}

	public void setDfwz(String dfwz) {
		this.dfwz = dfwz;
	}

	public String getLjpc() {
		return ljpc;
	}

	public void setLjpc(String ljpc) {
		this.ljpc = ljpc;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPurchaseNoStr() {
		return purchaseNoStr;
	}

	public void setPurchaseNoStr(String purchaseNoStr) {
		this.purchaseNoStr = purchaseNoStr;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getSupplierNoAuth() {
		return supplierNoAuth;
	}

	public void setSupplierNoAuth(String supplierNoAuth) {
		this.supplierNoAuth = supplierNoAuth;
	}

	public String getTempDelivQty() {
		return tempDelivQty;
	}

	public void setTempDelivQty(String tempDelivQty) {
		this.tempDelivQty = tempDelivQty;
	}

	public String getPurchaseNoArr() {
		return purchaseNoArr;
	}

	public void setPurchaseNoArr(String purchaseNoArr) {
		this.purchaseNoArr = purchaseNoArr;
	}
	
}
