package com.hanthink.sps.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：MM_SPS_VEH_QUEUE 实体对象
  * 作者:dtp
  * 日期:2018-10-16 10:56:13
  * </pre>
  */
public class SpsVehQueueModel extends AbstractModel<String>{
	
	/**
	 * uuid
	 */
	private String uuid;
	
	/**
	 * 模板ID
	 */
	private String mouldId;
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	
	private static final long serialVersionUID = -2780626733773599427L;

	protected String id;
	
	/**
	 * 信息点     
	 */
	protected String planCode; 
	
	/**
	 * 信息点描述
	 */
	protected String planCodeDesc;
	
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
	 * 过点时间     
	 */
	protected String passTime; 
	
	/**
	 * 过点时间至
	 */
	protected String passTimeTo;
	
	/**
	 * 车间投入号     
	 */
	protected String wcSeqno; 
	
	/**
	 * 生产投入号     
	 */
	protected String plSeqno; 
	
	/**
	 * 推算状态,数据字典类型" TRUE_FLASE"     
	 */
	protected String execStatus; 
	
	/**
	 * 推算时间     
	 */
	protected String execTime; 
	
	/**
	 * 创建时间     
	 */
	protected String creationTime; 
	
	/**
	 * 生产阶段     
	 */
	protected String phase;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public String getPassTimeTo() {
		return passTimeTo;
	}

	public void setPassTimeTo(String passTimeTo) {
		this.passTimeTo = passTimeTo;
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

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
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


	public String getMouldId() {
		return mouldId;
	}

	public void setMouldId(String mouldId) {
		this.mouldId = mouldId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
}