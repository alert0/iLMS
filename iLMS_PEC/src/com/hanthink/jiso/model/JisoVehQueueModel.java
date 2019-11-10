package com.hanthink.jiso.model;

import com.hotent.base.core.model.AbstractModel;

public class JisoVehQueueModel  extends AbstractModel<String>{
	
	private String factoryCode;

	private static final long serialVersionUID = -9121896979353414792L;

	/**
	 * 信息点
     */
	protected String planCode; 
	
	/**
	 * 生产单号
	 */
	protected String erpOrderNo;
	
	/**
	 * 产品订单号
     */
	protected String orderNo; 
	
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
	 * 推算状态数据字典类型“EXEC_STATE”     
	 */
	protected String execStatus; 
	
	/**
	 * 推算时间
     */
	protected String execTime; 
	
	/**
	 * 同步零件组总数
     */
	protected String partgroupNum; 
	
	/**
	 * 已组票的零件组总数
     */
	protected String partgroupInsNum; 
	
	/**
	 * 已组单的零件组总数
     */
	protected String partgroupOrderNum; 
	
	/**
	 * 创建时间
     */
	protected String creationTime; 
	
	protected String id;
	
	/**
	 * 过点接收时间
	 */
	protected String passTimeTo;
	
	/**
	 * 看板产品流水号
	 */
	protected String kbProductSeqno;
	
	/**
	 * 组票状态
	 */
	protected String groupInsState;
	
	/**
	 * 组单状态
	 */
	protected String groupOrderState;

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}

	public String getPartgroupNum() {
		return partgroupNum;
	}

	public void setPartgroupNum(String partgroupNum) {
		this.partgroupNum = partgroupNum;
	}

	public String getPartgroupInsNum() {
		return partgroupInsNum;
	}

	public void setPartgroupInsNum(String partgroupInsNum) {
		this.partgroupInsNum = partgroupInsNum;
	}

	public String getPartgroupOrderNum() {
		return partgroupOrderNum;
	}

	public void setPartgroupOrderNum(String partgroupOrderNum) {
		this.partgroupOrderNum = partgroupOrderNum;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassTimeTo() {
		return passTimeTo;
	}

	public void setPassTimeTo(String passTimeTo) {
		this.passTimeTo = passTimeTo;
	}

	public String getKbProductSeqno() {
		return kbProductSeqno;
	}

	public void setKbProductSeqno(String kbProductSeqno) {
		this.kbProductSeqno = kbProductSeqno;
	}

	public String getGroupInsState() {
		return groupInsState;
	}

	public void setGroupInsState(String groupInsState) {
		this.groupInsState = groupInsState;
	}

	public String getGroupOrderState() {
		return groupOrderState;
	}

	public void setGroupOrderState(String groupOrderState) {
		this.groupOrderState = groupOrderState;
	}

	public String getErpOrderNo() {
		return erpOrderNo;
	}

	public void setErpOrderNo(String erpOrderNo) {
		this.erpOrderNo = erpOrderNo;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	

}
