package com.hanthink.sps.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：MM_SPS_INS 实体对象
  * 作者:dtp
  * 日期:2018-10-16 10:56:13
  * </pre>
  */
public class SpsPadCheckModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = -2780626733773599427L;
	
	/**
	 * 车型
	 */
	private String modelCode; 
	
	/**
	 * 信息点
	 */
	private String planCode;
	
	/**
	 * VIN码
	 */
	private String vin; 
	
	/**
	 * 顺序号
	 */
	private String wcSeqNo; 
	
	/**
	 * 流水号
	 */
	private String plSeqNo; 
	
	/**
	 * 显示值
	 */
	private String showValue;
	
	/**
	 * 模板名称
	 */
	private String mouldName; 
	
	/**
	 * 模板位置号
	 */
	private String mouldPlace; 
	
	/**
	 * 图片ID
	 */
	private String imageId; 
	
	/**
	 * 过点时间
	 */
	private java.util.Date passTime;
	
	/**
	 * 过点时间（字符）
	 */
	private String passTimeStr; 
	
	/**
	 * 货架号
	 */
	private String shelfNo; 
	
	/**
	 * 零件记号
	 */
	private String partMark; 
	
	/**
	 * 零件号
	 */
	private String partNo; 
	
	/**
	 * 零件名称
	 */
	private String partNameCn;
	
	/**
	 * 工厂
	 */
	private String factoryCode;

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
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

	public String getMouldName() {
		return mouldName;
	}

	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}

	public String getMouldPlace() {
		return mouldPlace;
	}

	public void setMouldPlace(String mouldPlace) {
		this.mouldPlace = mouldPlace;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public java.util.Date getPassTime() {
		return passTime;
	}

	public void setPassTime(java.util.Date passTime) {
		this.passTime = passTime;
	}

	public String getPassTimeStr() {
		return passTimeStr;
	}

	public void setPassTimeStr(String passTimeStr) {
		this.passTimeStr = passTimeStr;
	}

	public String getShelfNo() {
		return shelfNo;
	}

	public void setShelfNo(String shelfNo) {
		this.shelfNo = shelfNo;
	}

	public String getPartMark() {
		return partMark;
	}

	public void setPartMark(String partMark) {
		this.partMark = partMark;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getWcSeqNo() {
		return wcSeqNo;
	}

	public void setWcSeqNo(String wcSeqNo) {
		this.wcSeqNo = wcSeqNo;
	}

	public String getPlSeqNo() {
		return plSeqNo;
	}

	public void setPlSeqNo(String plSeqNo) {
		this.plSeqNo = plSeqNo;
	}

	public String getShowValue() {
		return showValue;
	}

	public void setShowValue(String showValue) {
		this.showValue = showValue;
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