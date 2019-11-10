package com.hanthink.jit.model;
import com.hotent.base.core.model.AbstractModel;

 /**
  * <pre> 
  * 描述：MM_JIT_INS 实体对象
  * 作者:dtp
  * 日期:2018-10-08 10:25:40
  * </pre>
  */
public class JitInsModel extends AbstractModel<String>{
	
	/**
	 * 创建人
	 */
	private String creationUser;
	
	/**
	 * 出库单号
	 */
	private String invOutNo;
	
	/**
	 * 出库次数
	 */
	private String outTimes;
	
	/**
	 * 指示票类型
	 */
	private String insType;
	
	/**
	 * 入库仓库
	 */
	private String fromDepotNo;
	
	/**
	 * 出库仓库
	 */
	private String toDepotNo;
	
	/**
	 * 出库类型(1-从PC仓库到虚拟仓库2-从虚拟仓库到线边仓库)
	 */
	private String outType;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 订单数量
	 */
	private String orderQty;
	
	/**
	 * 出库数量
	 */
	private String recQty;
	
	/**
	 * 计划备件箱数
	 */
	private String planPrepareXs;
	
	/**
	 * 实际备件箱数
	 */
	private String actualPrepareXs;
	
	/**
	 * 计划配送箱数
	 */
	private String planDistriXs;
	
	/**
	 * 实际配送箱数
	 */
	private String actualDistriXs;
	
	
	/**
	 * 
	 */
	
	
	/**
	 * 组单方式
	 * 1 按配送工程
	 * 2 按拣货工程
	 */
	private String genInsWay;
	
	/**
	 * 打印人IP
	 */
	protected String printUserIp;
	
	/**
	 * 组单方式
	 */
	protected String genOrderWay;
	
	/**
	 * 最大台车号
	 */
	protected String maxCarPool;
	
	/**
	 * 箱数
	 */
	protected String xs;
	
	/**
	 * 工厂编码
	 */
	protected String factoryCode;
	
	/**
	 * 打印机
	 */
	protected String printerName;
	
	/**
	 * 配送工程有多少个台车
	 */
	protected String totalCarPool;
	
	/**
	 * 序号
	 */
	protected String serial;
	
	/**
	 * 车身序号
	 */
	protected String wcSeqno;
	
	/**
	 * 简号
	 */
	protected String partShortNo;
	
	/**
	 * 记号
	 */
	protected String partMark;
	
	/**
	 * 序号
	 */
	protected String serial2;
	
	/**
	 * 车身序号
	 */
	protected String wcSeqno2;
	
	/**
	 * 简号
	 */
	protected String partShortNo2;
	
	/**
	 * 记号
	 */
	protected String partMark2;
	
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
	
	private static final long serialVersionUID = -7062931285460229243L;

	protected String id;
	
	protected String no;
	
	/**
	 * 配送单号
     */
	protected String insNo; 
	
	/**
	 * 工作中心
	 */
	protected String workcenter;
	
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
	 * 备货工程
     */
	protected String preparePerson; 
	
	/**
	 * 配送工程
     */
	protected String distriPerson; 
	
	/**
	 * 台车
     */
	protected String carpool; 
	
	/**
	 * 备件批次产品流水号
     */
	protected String prepareProductSeqno; 
	
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
	 * 配送单号(不加差异序号)
     */
	protected String insNoBatch; 
	
	/**
	 * 配送单号差异序号
     */
	protected String insNoDiffseq; 
	
	/**
	 * 物流单号
     */
	protected String orderNo; 
	
	/**
	 * 打印状态
		0为未打印
		1为已打印     
	 */
	protected String printStatus; 
	
	/**
	 * 打印时间
     */
	protected String printTime; 
	
	/**
	 * 打印人
     */
	protected String printUser; 
	
	/**
	 * 备件批次
     */
	protected String prepareBatchNo; 
	
	/**
	 * 备件批次至
	 */
	protected String prepareBatchNoTo;
	
	/**
	 * 创建时间
     */
	protected String creationTime; 
	
	/**
	 * 创建时间至
	 */
	protected String creationTimeTo;
	
	/**
	 * 备件状态
		0-未备件
		1-部分备件
		2-全部备件    
	 */
	protected String prepareStatus; 
	
	/**
	 * 备件状态DESC
	 */
	protected String prepareStatusDesc;
	
	/**
	 * 备件次数
		1-部分备货
		2-全部备货    
	 */
	protected String prepareCount; 
	
	/**
	 * 配送出发状态
		0：未配送
		1：部分配送
		2：全部配送    
	 */
	protected String fdistriStatus; 
	
	/**
	 * 配送出发次数
		1-部分备货
		2-全部备货     
	*/
	protected String fdistriCount; 
	
	/**
	 * 配送到达状态
		0-未配送
		1-部分配送
		2-全部配送    
	 */
	protected String tdistriStatus; 
	
	/**
	 * 配送到达次数
		0：未到达
		1：部分到达
		2：全部到达     
	 */
	protected String tdistriCount; 
	
	/**
	 * 是否发送打印平台
     */
	protected String isLoad; 
	
	/**
	 * 发送打印平台时间
     */
	protected String loadTime;
	
	/**
	 * 打印机
	 */
	protected String prPrinter;
	
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
	 * 收容数
     */
	protected String standardPackage; 
	
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getInsNo() {
		return insNo;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getWcSeqno() {
		return wcSeqno;
	}

	public void setWcSeqno(String wcSeqno) {
		this.wcSeqno = wcSeqno;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getPartMark() {
		return partMark;
	}

	public void setPartMark(String partMark) {
		this.partMark = partMark;
	}

	public String getSerial2() {
		return serial2;
	}

	public void setSerial2(String serial2) {
		this.serial2 = serial2;
	}

	public String getWcSeqno2() {
		return wcSeqno2;
	}

	public void setWcSeqno2(String wcSeqno2) {
		this.wcSeqno2 = wcSeqno2;
	}

	public String getPartShortNo2() {
		return partShortNo2;
	}

	public void setPartShortNo2(String partShortNo2) {
		this.partShortNo2 = partShortNo2;
	}

	public String getPartMark2() {
		return partMark2;
	}

	public void setPartMark2(String partMark2) {
		this.partMark2 = partMark2;
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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
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

	public String getPreparePerson() {
		return preparePerson;
	}

	public void setPreparePerson(String preparePerson) {
		this.preparePerson = preparePerson;
	}

	public String getDistriPerson() {
		return distriPerson;
	}

	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}

	public String getCarpool() {
		return carpool;
	}

	public void setCarpool(String carpool) {
		this.carpool = carpool;
	}

	public String getPrepareProductSeqno() {
		return prepareProductSeqno;
	}

	public void setPrepareProductSeqno(String prepareProductSeqno) {
		this.prepareProductSeqno = prepareProductSeqno;
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

	public String getInsNoBatch() {
		return insNoBatch;
	}

	public void setInsNoBatch(String insNoBatch) {
		this.insNoBatch = insNoBatch;
	}

	public String getInsNoDiffseq() {
		return insNoDiffseq;
	}

	public void setInsNoDiffseq(String insNoDiffseq) {
		this.insNoDiffseq = insNoDiffseq;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getPrintUser() {
		return printUser;
	}

	public void setPrintUser(String printUser) {
		this.printUser = printUser;
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

	public String getPrepareCount() {
		return prepareCount;
	}

	public void setPrepareCount(String prepareCount) {
		this.prepareCount = prepareCount;
	}

	public String getFdistriStatus() {
		return fdistriStatus;
	}

	public void setFdistriStatus(String fdistriStatus) {
		this.fdistriStatus = fdistriStatus;
	}

	public String getFdistriCount() {
		return fdistriCount;
	}

	public void setFdistriCount(String fdistriCount) {
		this.fdistriCount = fdistriCount;
	}

	public String getTdistriStatus() {
		return tdistriStatus;
	}

	public void setTdistriStatus(String tdistriStatus) {
		this.tdistriStatus = tdistriStatus;
	}

	public String getTdistriCount() {
		return tdistriCount;
	}

	public void setTdistriCount(String tdistriCount) {
		this.tdistriCount = tdistriCount;
	}

	public String getIsLoad() {
		return isLoad;
	}

	public void setIsLoad(String isLoad) {
		this.isLoad = isLoad;
	}

	public String getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(String loadTime) {
		this.loadTime = loadTime;
	}

	public String getPrPrinter() {
		return prPrinter;
	}

	public void setPrPrinter(String prPrinter) {
		this.prPrinter = prPrinter;
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

	public String getStandardPackage() {
		return standardPackage;
	}

	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}

	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getTotalCarPool() {
		return totalCarPool;
	}

	public void setTotalCarPool(String totalCarPool) {
		this.totalCarPool = totalCarPool;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getXs() {
		return xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

	public String getMaxCarPool() {
		return maxCarPool;
	}

	public void setMaxCarPool(String maxCarPool) {
		this.maxCarPool = maxCarPool;
	}

	public String getGenOrderWay() {
		return genOrderWay;
	}

	public void setGenOrderWay(String genOrderWay) {
		this.genOrderWay = genOrderWay;
	}

	public String getPrintUserIp() {
		return printUserIp;
	}

	public void setPrintUserIp(String printUserIp) {
		this.printUserIp = printUserIp;
	}

	public String getPrepareStatusDesc() {
		return prepareStatusDesc;
	}

	public void setPrepareStatusDesc(String prepareStatusDesc) {
		this.prepareStatusDesc = prepareStatusDesc;
	}

	public String getGenInsWay() {
		return genInsWay;
	}

	public void setGenInsWay(String genInsWay) {
		this.genInsWay = genInsWay;
	}

	public String getPlanPrepareXs() {
		return planPrepareXs;
	}

	public void setPlanPrepareXs(String planPrepareXs) {
		this.planPrepareXs = planPrepareXs;
	}

	public String getActualPrepareXs() {
		return actualPrepareXs;
	}

	public void setActualPrepareXs(String actualPrepareXs) {
		this.actualPrepareXs = actualPrepareXs;
	}

	public String getPlanDistriXs() {
		return planDistriXs;
	}

	public void setPlanDistriXs(String planDistriXs) {
		this.planDistriXs = planDistriXs;
	}

	public String getActualDistriXs() {
		return actualDistriXs;
	}

	public void setActualDistriXs(String actualDistriXs) {
		this.actualDistriXs = actualDistriXs;
	}

	public String getInvOutNo() {
		return invOutNo;
	}

	public void setInvOutNo(String invOutNo) {
		this.invOutNo = invOutNo;
	}

	public String getOutTimes() {
		return outTimes;
	}

	public void setOutTimes(String outTimes) {
		this.outTimes = outTimes;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public String getFromDepotNo() {
		return fromDepotNo;
	}

	public void setFromDepotNo(String fromDepotNo) {
		this.fromDepotNo = fromDepotNo;
	}

	public String getToDepotNo() {
		return toDepotNo;
	}

	public void setToDepotNo(String toDepotNo) {
		this.toDepotNo = toDepotNo;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
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

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	
	
}