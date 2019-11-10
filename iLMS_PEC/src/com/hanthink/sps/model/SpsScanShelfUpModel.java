package com.hanthink.sps.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：SPS上架实体
  * 作者:dtp
  * 日期:2018-10-16 10:56:13
  * </pre>
  */
public class SpsScanShelfUpModel extends AbstractModel<String>{
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2019年4月4日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 5950001211096937346L;
	
	/**
	 * 工厂
	 */
	private String factoryCode;
	
	/**
	 * 车间
	 */
	private String workCenter;
	
	/**
	 * 批次基准
	 */
	private String distriBcycleSeqNo;
	
	/**
	 * 批次基准开始
	 */
	private String distriBcycleSeqNoStart;
	
	/**
	 * 批次基准结束
	 */
	private String distriBcycleSeqNoEnd;
	
	/**
	 * 配送批次
	 */
	private String distriBatchNo; 
	
	/**
	 * 配送批次开始
	 */
	private String distriBatchNoStart; 
	
	/**
	 * 配送批次结束
	 */
	private String distriBatchNoEnd; 
	
	/**
	 * 循环批次流水
	 */
	private String batchDiff; 
	
	/**
	 * 循环批次流水
	 */
	private String batchDiffStart; 
	
	/**
	 * 循环批次流水
	 */
	private String batchDiffEnd; 
	
	/**
	 * 零件号
	 */
	private String partNo; 
	
	/**
	 * 简号
	 */
	private String partShortNo;
	
	/**
	 * 零件名称
	 */
	private String partName;
	
	/**
	 * 备件工程
	 */
	private String preparePerson;
	
	/**
	 * 配送工程
	 */
	private String distriPerson;
	
	/**
	 * 配送地址
	 */
	private String location;
	
	/**
	 * 需求数量
	 */
	private String requireNum;
	
	/**
	 * 需求箱数
	 */
	private String boxNum; 
	
	/**
	 * 已配送数量
	 */
	private String distriNum;
	
	/**
	 * 已配送箱数
	 */
	private String distriBoxNum; 
	
	/**
	 * 供应商代码
	 */
	private String supplierNo; 
	
	/**
	 * 供应商名称
	 */
	private String supplierName ;

	/**
	 * 物流模式
	 */
	private String planCodeType;
	
	/**
	 * 卸货代码
	 */
	private String unloadPort;
	
	
	
	public String getDistriNum() {
		return distriNum;
	}

	public void setDistriNum(String distriNum) {
		this.distriNum = distriNum;
	}

	public String getDistriBoxNum() {
		return distriBoxNum;
	}

	public void setDistriBoxNum(String distriBoxNum) {
		this.distriBoxNum = distriBoxNum;
	}

	public String getBatchDiffStart() {
		return batchDiffStart;
	}

	public void setBatchDiffStart(String batchDiffStart) {
		this.batchDiffStart = batchDiffStart;
	}

	public String getBatchDiffEnd() {
		return batchDiffEnd;
	}

	public void setBatchDiffEnd(String batchDiffEnd) {
		this.batchDiffEnd = batchDiffEnd;
	}

	public String getDistriBcycleSeqNoStart() {
		return distriBcycleSeqNoStart;
	}

	public void setDistriBcycleSeqNoStart(String distriBcycleSeqNoStart) {
		this.distriBcycleSeqNoStart = distriBcycleSeqNoStart;
	}

	public String getDistriBcycleSeqNoEnd() {
		return distriBcycleSeqNoEnd;
	}

	public void setDistriBcycleSeqNoEnd(String distriBcycleSeqNoEnd) {
		this.distriBcycleSeqNoEnd = distriBcycleSeqNoEnd;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getDistriBcycleSeqNo() {
		return distriBcycleSeqNo;
	}

	public void setDistriBcycleSeqNo(String distriBcycleSeqNo) {
		this.distriBcycleSeqNo = distriBcycleSeqNo;
	}

	public String getDistriBatchNo() {
		return distriBatchNo;
	}

	public void setDistriBatchNo(String distriBatchNo) {
		this.distriBatchNo = distriBatchNo;
	}

	public String getDistriBatchNoStart() {
		return distriBatchNoStart;
	}

	public void setDistriBatchNoStart(String distriBatchNoStart) {
		this.distriBatchNoStart = distriBatchNoStart;
	}

	public String getDistriBatchNoEnd() {
		return distriBatchNoEnd;
	}

	public void setDistriBatchNoEnd(String distriBatchNoEnd) {
		this.distriBatchNoEnd = distriBatchNoEnd;
	}

	public String getBatchDiff() {
		return batchDiff;
	}

	public void setBatchDiff(String batchDiff) {
		this.batchDiff = batchDiff;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
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

	public String getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
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

	public String getPlanCodeType() {
		return planCodeType;
	}

	public void setPlanCodeType(String planCodeType) {
		this.planCodeType = planCodeType;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}