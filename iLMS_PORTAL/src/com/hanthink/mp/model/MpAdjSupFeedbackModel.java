package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：供应商能力反馈  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpAdjSupFeedbackModel extends AbstractModel<String>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -308016095727715017L;
	/**
	 * 主表  供应商能力反馈表
	 * @return
	 */
	/**
	* 逻辑主键id
	*/
	private String id; 
	/**
	* 工厂信息
	*/
	private String factoryCode; 
	
	/**
	* 零件号
	*/
	private String partNo; 
	
	/**
	* 供应商代码
	*/
	private String supplierNo; 
	
	/**
	* 出货地
	*/
	private String supFactory; 
	
	/**
	* 差异数量
	*/
	private String diffNum; 
	
	/**
	* 供应日期
	*/
	private java.util.Date supplyDate; 
	
	/**
	* 供应日期
	*/
	private String supplyDateStr; 
	
	/**
	* 供应日期（开始）
	*/
	private String supplyDateStrStart; 
	
	/**
	* 供应日期（结束）
	*/
	private String supplyDateStrEnd; 
	
	/**
	* 是否可供应
	*/
	private String  isSupply;
	
	/**
	* 创建人
	*/
	private String  creationUser;
	
	/**
	* 创建时间
	*/
	private java.util.Date  creationTime;
	
	/**
	* 供应原因
	*/
	private String  supplyReason;
	
	/**
	* 反馈时间
	*/
	private java.util.Date  feedbackTime;
	
	/**
	* 反馈时间
	*/
	private String feedbackTimeStr;
	
	/**
	* id数组
	*/
	private String[]  aryIds;
	
	/**
	 * 副表 物料主数据
	 */
	/**
	 * 简号
	 */
	private String  partShortNo;
	
	/**
	 * 零件名称
	 */
	private String  partNameCn;
	
	/**
	 * 副表 供应商表
	 */
	/**
	 * 供应商名称
	 */
	private String  supplierName;
	
	/**
	* 供应商状态
	*/
	private String status;
	
	/**
	* 供应商状态
	*/
	private String codeValueNameF; 
	
	/**
	 * 主表 供应商能力反馈
	 */
	
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

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}

	public String getDiffNum() {
		return diffNum;
	}

	public void setDiffNum(String diffNum) {
		this.diffNum = diffNum;
	}

	public java.util.Date getSupplyDate() {
		return supplyDate;
	}

	public void setSupplyDate(java.util.Date supplyDate) {
		this.supplyDate = supplyDate;
	}

	public String getSupplyDateStr() {
		return supplyDateStr;
	}

	public void setSupplyDateStr(String supplyDateStr) {
		this.supplyDateStr = supplyDateStr;
	}

	public String getSupplyDateStrStart() {
		return supplyDateStrStart;
	}

	public void setSupplyDateStrStart(String supplyDateStrStart) {
		this.supplyDateStrStart = supplyDateStrStart;
	}

	public String getSupplyDateStrEnd() {
		return supplyDateStrEnd;
	}

	public void setSupplyDateStrEnd(String supplyDateStrEnd) {
		this.supplyDateStrEnd = supplyDateStrEnd;
	}

	public String getIsSupply() {
		return isSupply;
	}

	public void setIsSupply(String isSupply) {
		this.isSupply = isSupply;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getSupplyReason() {
		return supplyReason;
	}

	public void setSupplyReason(String supplyReason) {
		this.supplyReason = supplyReason;
	}

	public java.util.Date getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(java.util.Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getFeedbackTimeStr() {
		return feedbackTimeStr;
	}

	public void setFeedbackTimeStr(String feedbackTimeStr) {
		this.feedbackTimeStr = feedbackTimeStr;
	}

	public String[] getAryIds() {
		return aryIds;
	}

	public void setAryIds(String[] aryIds) {
		this.aryIds = aryIds;
	}

	/**
	 * 副表 物料主数据
	 */
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
	
	/**
	 * 副表 供应商表
	 */
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCodeValueNameF() {
		return codeValueNameF;
	}

	public void setCodeValueNameF(String codeValueNameF) {
		this.codeValueNameF = codeValueNameF;
	}
	
	
}