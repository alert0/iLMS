package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：计划对比  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpAdjOrderDiffModel extends AbstractModel<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1225969614953431008L;
	/**
	 * 主表  计划对比差异表
	 * @return
	 */
	/**
	* 逻辑主键id
	*/
	private String id; 
	
	/**
	* 逻辑主键idStr
	*/
	private String idStr; 
	
	/**
	* 逻辑主键idStr
	*/
	private String[] idArr; 
	
	/**
	* 供应商代码
	*/
	private String supplierNo; 
	
	/**
	* 零件号
	*/
	private String partNo; 
	
	/**
	* 调整日期
	*/
	private java.util.Date adjDate; 
	
	/**
	* 调整日期
	*/
	private String adjDateStr; 
	
	/**
	* 调整日期(开始）
	*/
	private String adjDateStrStart; 
	
	/**
	* 调整日期（结束）
	*/
	private String adjDateStrEnd; 
	
	/**
	* 差异数量
	*/
	private String calDiffNum; 
	
	/**
	* 调整差异数量
	*/
	private String adjDiffNum; 
	
	/**
	* 工厂
	*/
	private String factoryCode; 
	
	/**
	* 发送标识
	*/
	private String sendFlag; 
	
	/**
	* 发送时间
	*/
	private java.util.Date sendTime; 
	
	/**
	* 发送时间
	*/
	private String sendTimeStr; 
	
	/**
	* 创建人
	*/
	private String creationUser; 
	
	/**
	* 创建时间
	*/
	private String creationTime; 
	
	/**
	 * 副表 物料主数据
	 */
	/**
	 * 简号
	 */
	private String partShortNo; 
	
	/**
	 * 零件名称
	 */
	private String partNameCn; 
	
	/**
	 * 副表 数据字典表
	 */
	/**
	 * 数据值
	 */
	private String codeValueName; 
	
	/**
	 * 副表 供应商表
	 */
	/**
	 * 数据值
	 */
	private String supplierName; 
	
	/**
	 * 数据值
	 */
	private String email; 
	
	/**
	 * 副表  零件供应商关系表
	 */
	/**
	 * 出货地
	 */
	private String supFactory;

	/**
	 * 副表  供应商明细邮箱
	 */
	private String importMail;
	private String ptMail;
	private String massMail;
	private String excepMailA;
	private String excepMailB;
	private String deviceMail;
	private String importMailA;
	private String ptMailA;
	private String massMailA;
	private String deviceMailA;
	private String packMailA;
	private String packMailB;
	private String ptLogisticsMail;
	private String ptLogisticsMailA;
	private String massLogisticsMail;
	private String massLogisticsMailA;
	
	/**
	 * 
	 */
	
	/**
	 * 主表 计划对比差异表
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public java.util.Date getAdjDate() {
		return adjDate;
	}

	public void setAdjDate(java.util.Date adjDate) {
		this.adjDate = adjDate;
	}

	public String getAdjDateStr() {
		return adjDateStr;
	}

	public void setAdjDateStr(String adjDateStr) {
		this.adjDateStr = adjDateStr;
	}

	public String getAdjDateStrStart() {
		return adjDateStrStart;
	}

	public void setAdjDateStrStart(String adjDateStrStart) {
		this.adjDateStrStart = adjDateStrStart;
	}

	public String getAdjDateStrEnd() {
		return adjDateStrEnd;
	}

	public void setAdjDateStrEnd(String adjDateStrEnd) {
		this.adjDateStrEnd = adjDateStrEnd;
	}

	public String getCalDiffNum() {
		return calDiffNum;
	}

	public void setCalDiffNum(String calDiffNum) {
		this.calDiffNum = calDiffNum;
	}

	public String getAdjDiffNum() {
		return adjDiffNum;
	}

	public void setAdjDiffNum(String adjDiffNum) {
		this.adjDiffNum = adjDiffNum;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public java.util.Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
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
	 * 副表 数据字典表
	 */
	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getIdArr() {
		return idArr;
	}

	public void setIdArr(String[] idArr) {
		this.idArr = idArr;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}

	public String getImportMail() {
		return importMail;
	}

	public void setImportMail(String importMail) {
		this.importMail = importMail;
	}

	public String getPtMail() {
		return ptMail;
	}

	public void setPtMail(String ptMail) {
		this.ptMail = ptMail;
	}

	public String getMassMail() {
		return massMail;
	}

	public void setMassMail(String massMail) {
		this.massMail = massMail;
	}

	public String getExcepMailA() {
		return excepMailA;
	}

	public void setExcepMailA(String excepMailA) {
		this.excepMailA = excepMailA;
	}

	public String getExcepMailB() {
		return excepMailB;
	}

	public void setExcepMailB(String excepMailB) {
		this.excepMailB = excepMailB;
	}

	public String getDeviceMail() {
		return deviceMail;
	}

	public void setDeviceMail(String deviceMail) {
		this.deviceMail = deviceMail;
	}

	public String getImportMailA() {
		return importMailA;
	}

	public void setImportMailA(String importMailA) {
		this.importMailA = importMailA;
	}

	public String getPtMailA() {
		return ptMailA;
	}

	public void setPtMailA(String ptMailA) {
		this.ptMailA = ptMailA;
	}

	public String getMassMailA() {
		return massMailA;
	}

	public void setMassMailA(String massMailA) {
		this.massMailA = massMailA;
	}

	public String getDeviceMailA() {
		return deviceMailA;
	}

	public void setDeviceMailA(String deviceMailA) {
		this.deviceMailA = deviceMailA;
	}

	public String getPackMailA() {
		return packMailA;
	}

	public void setPackMailA(String packMailA) {
		this.packMailA = packMailA;
	}

	public String getPackMailB() {
		return packMailB;
	}

	public void setPackMailB(String packMailB) {
		this.packMailB = packMailB;
	}

	public String getPtLogisticsMail() {
		return ptLogisticsMail;
	}

	public void setPtLogisticsMail(String ptLogisticsMail) {
		this.ptLogisticsMail = ptLogisticsMail;
	}

	public String getPtLogisticsMailA() {
		return ptLogisticsMailA;
	}

	public void setPtLogisticsMailA(String ptLogisticsMailA) {
		this.ptLogisticsMailA = ptLogisticsMailA;
	}

	public String getMassLogisticsMail() {
		return massLogisticsMail;
	}

	public void setMassLogisticsMail(String massLogisticsMail) {
		this.massLogisticsMail = massLogisticsMail;
	}

	public String getMassLogisticsMailA() {
		return massLogisticsMailA;
	}

	public void setMassLogisticsMailA(String massLogisticsMailA) {
		this.massLogisticsMailA = massLogisticsMailA;
	}
	
}