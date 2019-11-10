package com.hanthink.jisi.model;

import com.hotent.base.core.model.AbstractModel;

public class JisiVehQueueModel extends AbstractModel<String>{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -194359554303530632L;
	
	/**
	 * 生产单号
	 */
	private String erpOrderNo;
	/**工厂**/
	private String factoryCode;
	/**id**/
	private String id;
	/**信息点**/
	private String planCode;
	/**产品订单号**/
	private String orderNo;
	/**VIN**/
	private String vin;
	/**车型**/
	private String modelCode;
	/**生产阶段**/
	private String phase;
	/**过点时间**/
	private String passTime;
	/**过点时间开始（查询）**/
	private String passTimeStart;
	/**过点时间结束（查询）**/
	private String passTimeEnd;
	/**车间投入号**/
	private String wcSeqno;
	/**生产投入号**/
	private String plSeqno;
	/**看板批次产品流**/
	private String kbProductSeqno;
	/**看板时间**/
	private String kbTime;
	/**推算状态**/
	private String execStatus;
	/**推算时间**/
	private String execTime;
	/**同步零件组总数**/
	private String partgroupNum;
	/**已组票的零件组**/
	private String partgroupInsNum;
	/**创建时间**/
	private String creationTime;
	/**下线批次进度**/
	private String offineSeqno;
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
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPassTimeStart() {
		return passTimeStart;
	}
	public void setPassTimeStart(String passTimeStart) {
		this.passTimeStart = passTimeStart;
	}
	public String getPassTimeEnd() {
		return passTimeEnd;
	}
	public void setPassTimeEnd(String passTimeEnd) {
		this.passTimeEnd = passTimeEnd;
	}
	public String getOffineSeqno() {
		return offineSeqno;
	}
	public void setOffineSeqno(String offineSeqno) {
		this.offineSeqno = offineSeqno;
	}
	public String getErpOrderNo() {
		return erpOrderNo;
	}
	public void setErpOrderNo(String erpOrderNo) {
		this.erpOrderNo = erpOrderNo;
	}
	
}
