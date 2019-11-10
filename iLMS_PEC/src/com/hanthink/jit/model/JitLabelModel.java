package com.hanthink.jit.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：MM_JIT_LABEL 实体对象
  * 作者:dtp
  * 日期:2018-09-29 13:54:46
  * </pre>
  */
public class JitLabelModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 7536580899316378405L;
	
	/**
	 * uuid
	 */
	protected String uuid;

	protected String id;
	
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
	 * 工厂编码
	 */
	protected String factoryCode;
	
	/**
	 * 车间
	 */
	protected String workcenter;
	
	/**
	 * 收容数
	 */
	protected String srs;
	
	/**
	 * 物流单号
     */
	protected String orderNo; 
	
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
	 * 零件号
     */
	protected String partNo; 
	
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
	 * 供应商代码
     */
	protected String supplierNo; 
	
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
	 * 收容数
     */
	protected String standardPackage; 
	
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
	protected String arriveTime; 
	
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
	 * 打印状态数据字典类型“PRINT_STATUS"     
	 */
	protected String printStatus; 
	
	/**
	 * 打印时间
     */
	protected String printTime; 
	
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
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
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
		this.QRCode = qRCode;
	}

	public String getLabelPageNum() {
		return labelPageNum;
	}

	public void setLabelPageNum(String labelPageNum) {
		this.labelPageNum = labelPageNum;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getOrderRowNo() {
		return orderRowNo;
	}

	public void setOrderRowNo(String orderRowNo) {
		this.orderRowNo = orderRowNo;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}