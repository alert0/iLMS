package com.hanthink.jit.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：拉动包装明细查询 实体对象
  * 作者:lz
  * 日期:2018-10-09 10:43:53
  * </pre>
  */
public class JitPkgReqModel extends AbstractModel<Integer>{
	
	private static final long serialVersionUID = 2427384271999580381L;
	
	/**
	 * 备件批次
	 */
	private String prepareSeqno;
	
	/**
	 * 备件批次至
	 */
	private String prepareSeqnoTo;
	
	/**
	 * 箱数
	 */
	private String xs;
	
	/**
	 * 主表  拉动包装明细查询
	 */
	/**
	 * ID
     */
	private Integer id; 
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	
	/**
	 * 信息点
     */
	private String planCode; 
	
	/**
	 * 信息点描述
	 */
	private String planCodeDesc;
	
	/**
	 * 零件号
     */
	private String partNo; 
	
	/**
	 * 配送地址
     */
	private String location; 
	
	/**
	 * 开始产品编号流水号
     */
	private String sproductSeqno; 
	
	/**
	 * 结束产品编号流水号
     */
	private String eproductSeqno; 
	
	/**
	 * 配送量
     */
	private String requireNum; 
	
	/**
	 * 简号
     */
	private String partShortNo; 
	
	/**
	 * 零件名称
     */
	private String partName; 
	
	/**
	 * 供应商出货地
     */
	private String supFactory; 
	
	/**
	 * 供应商代码
     */
	private String supplierNo; 
	
	/**
	 * 供应商名称
     */
	private String supplierName; 
	
	/**
	 * 出货仓库
     */
	private String shipDepot; 
	
	/**
	 * 到货仓库
     */
	private String arrDepot; 
	
	/**
	 * 配送包装
     */
	private String distriPackage; 
	
	/**
	 * 规格包装
     */
	private String standardPackage; 
	
	/**
	 * 卸货口
     */
	private String unloadPort; 
	
	/**
	 * 库位
     */
	private String storage; 
	
	/**
	 * 备货工程
     */
	private String preparePerson; 
	
	/**
	 * 台车
     */
	private String carpool; 
	
	/**
	 * 配送工程
     */
	private String distriPerson; 
	
	/**
	 * 看板批次产品流水号
     */
	private String kbProductSeqno; 
	
	/**
	 * 备件批次产品流水号
     */
	private String prepareProductSeqno; 
	
	/**
	 * 发车批次产品流水号
     */
	private String dispatchProductSeqno; 
	
	/**
	 * 发货批次产品流水号
     */
	private String deliveryProductSeqno; 
	
	/**
	 * 到货批次产品流水号
     */
	private String arriveProductSeqno; 
	
	/**
	 * 配送批次产品流水号
     */
	private String distriProductSeqno; 
	
	/**
	 * 装配批次产品流水号
     */
	private String assembleProductSeqno; 
	
	/**
	 * 看板时间
     */
	private String kbTime; 
	
	/**
	 * 备件时间
     */
	private String prepareTime; 
	
	/**
	 * 发车时间
     */
	private String dispatchTime; 
	
	/**
	 * 发货时间
     */
	private String deliveryTime; 
	
	/**
	 * 到货时间
     */
	private String arriveTime; 
	
	/**
	 * 配送时间
     */
	private String distriTime; 
	
	/**
	 * 装配时间
     */
	private String assembleTime; 
	
	/**
	 * 创建时间
     */
	private String creationTime; 
	
	/**
	 * 创建时间
     */
	private String creationTimeTo; 
	
	/**
	 * 物流单号
     */
	private String orderNo; 
	
	/**
	 * 物流单组单标志数据字典类型: “JIT_GEN_ORDER_STATUS”     
	 */
	private String orderDealFlag; 
	
	/**
	 * PREPARE_BATCH_SEQNO     */
	private String prepareBatchSeqno; 

	/**
	 * 副表  信息点信息表
	 */
	/**
	 * 车间
	 */
	private  String workcenter;
	
	/**
	 * 主表  拉动包装明细查询表
	 */
	/**
	 * 返回 ID
     * @return
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
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

	public String getSproductSeqno() {
		return sproductSeqno;
	}

	public void setSproductSeqno(String sproductSeqno) {
		this.sproductSeqno = sproductSeqno;
	}

	public String getEproductSeqno() {
		return eproductSeqno;
	}

	public void setEproductSeqno(String eproductSeqno) {
		this.eproductSeqno = eproductSeqno;
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

	public String getDistriPackage() {
		return distriPackage;
	}

	public void setDistriPackage(String distriPackage) {
		this.distriPackage = distriPackage;
	}

	public String getStandardPackage() {
		return standardPackage;
	}

	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
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

	public String getCarpool() {
		return carpool;
	}

	public void setCarpool(String carpool) {
		this.carpool = carpool;
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

	public String getAssembleTime() {
		return assembleTime;
	}

	public void setAssembleTime(String assembleTime) {
		this.assembleTime = assembleTime;
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

	public String getPrepareBatchSeqno() {
		return prepareBatchSeqno;
	}

	public void setPrepareBatchSeqno(String prepareBatchSeqno) {
		this.prepareBatchSeqno = prepareBatchSeqno;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getXs() {
		return xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

	public String getPrepareSeqno() {
		return prepareSeqno;
	}

	public void setPrepareSeqno(String prepareSeqno) {
		this.prepareSeqno = prepareSeqno;
	}

	public String getPrepareSeqnoTo() {
		return prepareSeqnoTo;
	}

	public void setPrepareSeqnoTo(String prepareSeqnoTo) {
		this.prepareSeqnoTo = prepareSeqnoTo;
	}

	
}