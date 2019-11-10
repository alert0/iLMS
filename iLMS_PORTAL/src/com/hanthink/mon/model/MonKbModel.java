package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：MM_MON_KB 实体对象
 * 作者:luocc
 * 日期:2018-11-3 11:00:00
 * </pre>
 */
public class MonKbModel extends AbstractModel<String>{

	private static final long serialVersionUID = 4843548741968260031L;

	/**传入参数*/
	private	String reqParameter;
	/**看板ID*/
	private	String id;
	/**工厂*/
	private	String factoryCode;
	/**看板代码*/
	private	String kbCode;
	/**产线*/
	private	String productionLine;
	/**车间*/
	private	String workcenter;
	/**工位   数据字典类型“mon_kb_station*/
	private	String	stationCode;
	/**看板名称*/
	private	String kbName;
	/**所属业务 数据字典类型“mon_kb_type*/
	private	String	kbType;
	/**批次循环基数*/
	private	String	batchCycleNum;
	/**进度循环基数*/
	private	String	processCycleNum;
	/**当前批次*/
	private	String	currbatchNo;
	/**当前进度*/
	private	String	currprocessNo;
	/**当前批次流水号*/
	private	Long currbatchseqNo;
	/**产品流水号*/
	private	Long productSeqno;
	/**是否可编辑    数据字典类型 true_false*/
	private	String isEdit;
	/**"备注*/
	private	String remark;
	/**修改IP地址*/
	private String modifyIp;
	/**偏移进度*/
	private String skweProcessNo;
	
	//-------------------------------业务字段    明细MM_MON_KB_DETAIL----------------------------------------
	/**明细表ID*/
	private String detailId;
	/**工程*/
	private String distriPerson;
	/**出发进度数*/
	private String runProcessNo;
	/**看板状态*/
	private String kbStatus;
	/**安灯灯号*/
	private String lampId;
	/**允许延迟时间*/
	private String limitDelay;
	/**出发时间*/
	private String planRunTime;
	/**出发延迟标识*/
	private String runDelayFlag;
	/** 最后修改人*/
	private String lastModifiedUser;
	/** 最后修改IP*/
	private String lastModifiedIp;

	public String getReqParameter() {
		return reqParameter;
	}
	public void setReqParameter(String reqParameter) {
		this.reqParameter = reqParameter;
	}
	public String getSkweProcessNo() {
		return skweProcessNo;
	}
	public void setSkweProcessNo(String skweProcessNo) {
		this.skweProcessNo = skweProcessNo;
	}
	public String getLimitDelay() {
		return limitDelay;
	}
	public void setLimitDelay(String limitDelay) {
		this.limitDelay = limitDelay;
	}
	public String getPlanRunTime() {
		return planRunTime;
	}
	public void setPlanRunTime(String planRunTime) {
		this.planRunTime = planRunTime;
	}
	public String getRunDelayFlag() {
		return runDelayFlag;
	}
	public void setRunDelayFlag(String runDelayFlag) {
		this.runDelayFlag = runDelayFlag;
	}
	public String getRunProcessNo() {
		return runProcessNo;
	}
	public void setRunProcessNo(String runProcessNo) {
		this.runProcessNo = runProcessNo;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getDistriPerson() {
		return distriPerson;
	}
	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}
	public String getKbStatus() {
		return kbStatus;
	}
	public void setKbStatus(String kbStatus) {
		this.kbStatus = kbStatus;
	}
	public String getLampId() {
		return lampId;
	}
	public void setLampId(String lampId) {
		this.lampId = lampId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getKbCode() {
		return kbCode;
	}
	public void setKbCode(String kbCode) {
		this.kbCode = kbCode;
	}
	public String getProductionLine() {
		return productionLine;
	}
	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}
	public String getWorkcenter() {
		return workcenter;
	}
	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getKbName() {
		return kbName;
	}
	public void setKbName(String kbName) {
		this.kbName = kbName;
	}
	public String getKbType() {
		return kbType;
	}
	public void setKbType(String kbType) {
		this.kbType = kbType;
	}
	
	public String getBatchCycleNum() {
		return batchCycleNum;
	}
	public void setBatchCycleNum(String batchCycleNum) {
		this.batchCycleNum = batchCycleNum;
	}
	public String getProcessCycleNum() {
		return processCycleNum;
	}
	public void setProcessCycleNum(String processCycleNum) {
		this.processCycleNum = processCycleNum;
	}
	public String getCurrbatchNo() {
		return currbatchNo;
	}
	public void setCurrbatchNo(String currbatchNo) {
		this.currbatchNo = currbatchNo;
	}
	public String getCurrprocessNo() {
		return currprocessNo;
	}
	public void setCurrprocessNo(String currprocessNo) {
		this.currprocessNo = currprocessNo;
	}
	public Long getCurrbatchseqNo() {
		return currbatchseqNo;
	}
	public void setCurrbatchseqNo(Long currbatchseqNo) {
		this.currbatchseqNo = currbatchseqNo;
	}
	public Long getProductSeqno() {
		return productSeqno;
	}
	public void setProductSeqno(Long productSeqno) {
		this.productSeqno = productSeqno;
	}
	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getModifyIp() {
		return modifyIp;
	}
	public void setModifyIp(String modifyIp) {
		this.modifyIp = modifyIp;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getLastModifiedIp() {
		return lastModifiedIp;
	}
	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}
	
}
