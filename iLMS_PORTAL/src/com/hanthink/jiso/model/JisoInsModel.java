package com.hanthink.jiso.model;

import com.hotent.base.core.model.AbstractModel;

/**
  * <pre> 
  * 描述：MM_JISO_INS 实体对象
  * 作者:dtp
  * 日期:2018-09-15 16:57:05
  * </pre>
  */
public class JisoInsModel extends AbstractModel<String> {
	
	private static final long serialVersionUID = -4971590018224466449L;
	
	/**
	 * 销售单号
	 */
	private String saleNo;
	
	/**
	 * 底色标识
	 */
	protected String remarkFlag;

	/**
	 * 底色标识2
	 */
	protected String remarkFlag2;

	protected String id;
	
	/**
	 * 供应商代码(权限)
	 */
	protected String supplierNoAuth;
	
	/**
	 * 打印人
	 */
	protected String printUser;
	
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
	 * 工厂
	 */
	protected String factoryCode;
	
	/**
	 * 指示票号
     */
	protected String insNo; 
	
	/**
	 * 待组单指示票数
	 */
	protected String notDelIns;
	
	/**
	 * 二维码
	 */
	protected String QRCode;
	
	/**
	 * 信息点
     */
	protected String planCode; 
	
	/**
	 * 零件组代码
     */
	protected String partgroupNo; 
	
	/**
	 * 零件组代码
     */
	protected String partgroupNoDisplay; 
	
	/**
	 * 到货仓库
     */
	protected String arrDepot; 
	
	/**
	 * 供应商出货地
     */
	protected String supFactory; 
	
	/**
	 * 分零件组指示票代码
     */
	protected String insPartgroupSeqno; 
	
	/**
	 * 分供应商指示票代码
     */
	protected String insSupfactorySeqno; 
	
	/**
	 * 指示票唯一流水号
     */
	protected String insSeq; 
	
	/**
	 * 零件组名称
     */
	protected String partgroupName; 
	
	/**
	 * 是否生成订单数据字典类型“TRUE_FALSE"
     */
	protected String orderFlg; 
	
	/**
	 * 是否考虑出货地切换数据字典类型“TRUE_FALSE"
     */
	protected String genInsWay; 
	
	/**
	 * 供应商代码
     */
	protected String supplierNo; 
	
	/**
	 * 供应商名称专属供应商订单    
	 */
	protected String supplierName; 
	
	/**
	 * 路线代码
     */
	protected String routeCode; 
	
	/**
	 * 路线描述
     */
	protected String routeDesc; 
	
	/**
	 * 车次流水号车次一直递增    
	 */
	protected String carBatch; 
	
	/**
	 * 配送地址
     */
	protected String location; 
	
	/**
	 * 备货批次产品流水号
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
	 * 是否手工组票数据字典类型“TRUE_FALSE"     
	 */
	protected String isManuDeal; 
	
	/**
	 * 手工组票请求时间
     */
	protected String manuReqTime; 
	
	/**
	 * 手工组票请求人
     */
	protected String manuReqUser; 
	
	/**
	 * 手工组票请求IP
     */
	protected String manuReqIp; 
	
	/**
	 * 组票台套数
	 */
	protected String insProductNum;
	
	/**
	 * 配送工程
	 */
	protected String distriPerson;
	
	/**
	 * 打印状态数据字典类型“PRINT_STATUS”
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
	 * 物流单号
     */
	protected String orderNo; 
	
	/**
	 * 物流单组单标志数据字典类型: “JIT_GEN_ORDER_STATUS”     
	 */
	protected String orderDealFlag; 
	
	/**
	 * 物流单组单时间
     */
	protected String orderDealTime;
	
	/**
	 * 车次流水号
	 */
	protected String carBatchSeqno;
	
	/**
	 * 创建日期从
	 */
	protected String creationTimeFrom;
	
	/**
	 * 创建日期至
	 */
	protected String creationTimeTo;

	/**
	 * 到货批次
	 */
	protected String arriveBatchFrom;
	
	/**
	 * 到货批次至
	 */
	protected String arriveBatchTo;
	
	/**
	 * 零件号
	 */
	protected String partNo;
	
	/**
	 * 需求数量
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
	 * 零件记号
	 */
	protected String partMark;
	
	/**
	 * VIN
	 */
	protected String vin;
	
	/**
	 * 车型
	 */
	protected String modelCode;
	
	/**
	 * 生产阶段	
	 */
	protected String phase;
	
	/**
	 * 过点时间
	 */
	protected String passTime;
	
	/**
	 * 车间投入号
	 */
	protected String wcSeqno;
	
	/**
	 * 生产投入号
	 */
	protected String plSeqno;
	
	/**
	 * 看板批次产品流水号
	 */
	protected String kbProductSeqno;
	
	/**
	 * 产品流水号
	 */
	protected String kbTime;
	
	/**
	 * 车身序号
	 */
	protected String csxh;
	
	/**
	 * 收容数
	 */
	protected String srs;
	
	/**
	 * 下一个组票方式
	 */
	protected String nextGroupInsWay;
	
	/**
	 * 下一个组单方式
	 */
	protected String nextGroupOrderWay;
	
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInsNo() {
		return insNo;
	}

	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPartgroupNo() {
		return partgroupNo;
	}

	public void setPartgroupNo(String partgroupNo) {
		this.partgroupNo = partgroupNo;
	}

	public String getArrDepot() {
		return arrDepot;
	}

	public void setArrDepot(String arrDepot) {
		this.arrDepot = arrDepot;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}

	public String getInsPartgroupSeqno() {
		return insPartgroupSeqno;
	}

	public void setInsPartgroupSeqno(String insPartgroupSeqno) {
		this.insPartgroupSeqno = insPartgroupSeqno;
	}

	public String getInsSupfactorySeqno() {
		return insSupfactorySeqno;
	}

	public void setInsSupfactorySeqno(String insSupfactorySeqno) {
		this.insSupfactorySeqno = insSupfactorySeqno;
	}

	public String getInsSeq() {
		return insSeq;
	}

	public void setInsSeq(String insSeq) {
		this.insSeq = insSeq;
	}

	public String getPartgroupName() {
		return partgroupName;
	}

	public void setPartgroupName(String partgroupName) {
		this.partgroupName = partgroupName;
	}

	public String getOrderFlg() {
		return orderFlg;
	}

	public void setOrderFlg(String orderFlg) {
		this.orderFlg = orderFlg;
	}

	public String getGenInsWay() {
		return genInsWay;
	}

	public void setGenInsWay(String genInsWay) {
		this.genInsWay = genInsWay;
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

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getRouteDesc() {
		return routeDesc;
	}

	public void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}

	public String getCarBatch() {
		return carBatch;
	}

	public void setCarBatch(String carBatch) {
		this.carBatch = carBatch;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getIsManuDeal() {
		return isManuDeal;
	}

	public void setIsManuDeal(String isManuDeal) {
		this.isManuDeal = isManuDeal;
	}

	public String getManuReqTime() {
		return manuReqTime;
	}

	public void setManuReqTime(String manuReqTime) {
		this.manuReqTime = manuReqTime;
	}

	public String getManuReqUser() {
		return manuReqUser;
	}

	public void setManuReqUser(String manuReqUser) {
		this.manuReqUser = manuReqUser;
	}

	public String getManuReqIp() {
		return manuReqIp;
	}

	public void setManuReqIp(String manuReqIp) {
		this.manuReqIp = manuReqIp;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDealFlag() {
		return orderDealFlag;
	}

	public void setOrderDealFlag(String orderDealFlag) {
		this.orderDealFlag = orderDealFlag;
	}

	public String getOrderDealTime() {
		return orderDealTime;
	}

	public void setOrderDealTime(String orderDealTime) {
		this.orderDealTime = orderDealTime;
	}

	public String getCarBatchSeqno() {
		return carBatchSeqno;
	}

	public void setCarBatchSeqno(String carBatchSeqno) {
		this.carBatchSeqno = carBatchSeqno;
	}

	public String getCreationTimeFrom() {
		return creationTimeFrom;
	}

	public void setCreationTimeFrom(String creationTimeFrom) {
		this.creationTimeFrom = creationTimeFrom;
	}

	public String getCreationTimeTo() {
		return creationTimeTo;
	}

	public void setCreationTimeTo(String creationTimeTo) {
		this.creationTimeTo = creationTimeTo;
	}

	public String getArriveBatchFrom() {
		return arriveBatchFrom;
	}

	public void setArriveBatchFrom(String arriveBatchFrom) {
		this.arriveBatchFrom = arriveBatchFrom;
	}

	public String getArriveBatchTo() {
		return arriveBatchTo;
	}

	public void setArriveBatchTo(String arriveBatchTo) {
		this.arriveBatchTo = arriveBatchTo;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
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

	public String getPartMark() {
		return partMark;
	}

	public void setPartMark(String partMark) {
		this.partMark = partMark;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public String getWcSeqno() {
		return wcSeqno;
	}

	public void setWcSeqno(String wcSeqno) {
		this.wcSeqno = wcSeqno;
	}

	public String getPlSeqno() {
		return plSeqno;
	}

	public void setPlSeqno(String plSeqno) {
		this.plSeqno = plSeqno;
	}

	public String getKbProductSeqno() {
		return kbProductSeqno;
	}

	public void setKbProductSeqno(String kbProductSeqno) {
		this.kbProductSeqno = kbProductSeqno;
	}

	public String getKbTime() {
		return kbTime;
	}

	public void setKbTime(String kbTime) {
		this.kbTime = kbTime;
	}

	public String getCsxh() {
		return csxh;
	}

	public void setCsxh(String csxh) {
		this.csxh = csxh;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public String getQRCode() {
		return QRCode;
	}

	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}

	public String getPartgroupNoDisplay() {
		return partgroupNoDisplay;
	}

	public void setPartgroupNoDisplay(String partgroupNoDisplay) {
		this.partgroupNoDisplay = partgroupNoDisplay;
	}

	public String getInsProductNum() {
		return insProductNum;
	}

	public void setInsProductNum(String insProductNum) {
		this.insProductNum = insProductNum;
	}

	public String getNextGroupInsWay() {
		return nextGroupInsWay;
	}

	public void setNextGroupInsWay(String nextGroupInsWay) {
		this.nextGroupInsWay = nextGroupInsWay;
	}

	public String getNotDelIns() {
		return notDelIns;
	}

	public void setNotDelIns(String notDelIns) {
		this.notDelIns = notDelIns;
	}

	public String getNextGroupOrderWay() {
		return nextGroupOrderWay;
	}

	public void setNextGroupOrderWay(String nextGroupOrderWay) {
		this.nextGroupOrderWay = nextGroupOrderWay;
	}

	public String getDistriPerson() {
		return distriPerson;
	}

	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
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

	public String getSupplierNoAuth() {
		return supplierNoAuth;
	}

	public void setSupplierNoAuth(String supplierNoAuth) {
		this.supplierNoAuth = supplierNoAuth;
	}

	public String getRemarkFlag() {
		return remarkFlag;
	}

	public void setRemarkFlag(String remarkFlag) {
		this.remarkFlag = remarkFlag;
	}

	public String getRemarkFlag2() {
		return remarkFlag2;
	}

	public void setRemarkFlag2(String remarkFlag2) {
		this.remarkFlag2 = remarkFlag2;
	}

	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	
	
}