package com.hanthink.mon.model;

public class BigStockKbModel{
	
	private static final long serialVersionUID = -8772055087196811972L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 车间 */
	private String workCenter;
	/** 看板ID */
	private String kbId;
	/** 看板代码 */
	private String kbCode;
	/** 当前批次 */
	private String currBatchNo;
	/** 当前台数*/
	private String currProcessNo;
	/** 配送工程*/
	private String distriPerson;
	/** 备件工程*/
	private String preparePerson;
	/** 工程*/
	private String person;
	/** 配送单编号*/
	private String insNo;
	/** 零件编号*/
	private String partNo;
	/** 台数*/
	private String productSeqNo;
	/** 备件批次序号*/
	private String prepareBatchSeqNo;
	/** 当前批次号*/
	private String currBatchSeqNo;
	/** 备件产品流水号*/
	private String prepareProductSeqNo;
	/** 配送铲平流水号*/
	private String distriProductSeqNo;
	/** 配送工程字符*/
	private String distriPersonStr;
	
	public String getKbId() {
		return kbId;
	}
	public void setKbId(String kbId) {
		this.kbId = kbId;
	}
	public String getKbCode() {
		return kbCode;
	}
	public void setKbCode(String kbCode) {
		this.kbCode = kbCode;
	}
	public String getCurrBatchNo() {
		return currBatchNo;
	}
	public void setCurrBatchNo(String currBatchNo) {
		this.currBatchNo = currBatchNo;
	}
	public String getCurrProcessNo() {
		return currProcessNo;
	}
	public void setCurrProcessNo(String currProcessNo) {
		this.currProcessNo = currProcessNo;
	}
	public String getDistriPerson() {
		return distriPerson;
	}
	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}
	public String getDistriProductSeqNo() {
		return distriProductSeqNo;
	}
	public void setDistriProductSeqNo(String distriProductSeqNo) {
		this.distriProductSeqNo = distriProductSeqNo;
	}
	public String getPreparePerson() {
		return preparePerson;
	}
	public void setPreparePerson(String preparePerson) {
		this.preparePerson = preparePerson;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getInsNo() {
		return insNo;
	}
	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getProductSeqNo() {
		return productSeqNo;
	}
	public void setProductSeqNo(String productSeqNo) {
		this.productSeqNo = productSeqNo;
	}
	public String getPrepareBatchSeqNo() {
		return prepareBatchSeqNo;
	}
	public void setPrepareBatchSeqNo(String prepareBatchSeqNo) {
		this.prepareBatchSeqNo = prepareBatchSeqNo;
	}
	public String getCurrBatchSeqNo() {
		return currBatchSeqNo;
	}
	public void setCurrBatchSeqNo(String currBatchSeqNo) {
		this.currBatchSeqNo = currBatchSeqNo;
	}
	public String getPrepareProductSeqNo() {
		return prepareProductSeqNo;
	}
	public void setPrepareProductSeqNo(String prepareProductSeqNo) {
		this.prepareProductSeqNo = prepareProductSeqNo;
	}
	public String getDistriPersonStr() {
		return distriPersonStr;
	}
	public void setDistriPersonStr(String distriPersonStr) {
		this.distriPersonStr = distriPersonStr;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	
	
}
