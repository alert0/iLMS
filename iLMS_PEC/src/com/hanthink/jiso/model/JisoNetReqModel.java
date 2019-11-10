package com.hanthink.jiso.model;

/**
  * <pre> 
  * 描述：MM_JISO_NET_REQ 实体对象
  * 作者:dtp
  * 日期:2018-09-13 14:26:31
  * </pre>
  */
public class JisoNetReqModel {
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	
	/**
	 * 供应商代码
	 */
	protected String supplierNo;
	
	/**
	 * 信息点
     */
	protected String planCode; 
	
	/**
	 * 零件组代码
     */
	protected String partgroupNo; 
	
	/**
	 * 产品订单号
     */
	protected String orderNo; 
	
	/**
	 * 供应商出货地
     */
	protected String supFactory; 
	
	/**
	 * 零件号
     */
	protected String partNo; 
	
	/**
	 * 简号
	 */
	protected String partShortNo;
	
	/**
	 * 零件名称
	 */
	protected String partNameCn;
	
	/**
	 * 需求数量
     */
	protected String requireNum; 
	
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
	 * 看板产品流水号
     */
	protected String kbProductSeqno; 
	
	/**
	 * 创建时间
     */
	protected String creationTime;

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
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

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
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

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	} 
	
	
}