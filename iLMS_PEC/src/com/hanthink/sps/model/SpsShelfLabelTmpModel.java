package com.hanthink.sps.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：MM_SPS_SHELF_LABLE_IMP 实体对象
 * 作者:dtp
 * 日期:2018-10-31 17:36:13
 * </pre>
 */
public class SpsShelfLabelTmpModel extends AbstractModel<String> {

	private static final long serialVersionUID = -2442607318121948124L;

	/**
	 * 货架号(标签打印)
	 */
	private String shelfNoView;
	
	protected String id;
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	
	/**
	 * 拣取图片
	 */
	protected Object spsImg;
	
	/**
	 * 二维码
	 */
	protected Object qrCode;
	
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
	protected String partName; 
	
	/**
	 * 车型代码     
	 */
	protected String modelCode; 
	
	/**
	 * 货架号    
	 */
	protected String shelfNo; 
	
	/**
	 * 记号    
	 */
	protected String mark; 
	
	/**
	 * IMP_UUID     
	 */
	protected String impUuid; 
	
	/**
	 * OPE_TYPE     
	 */
	protected String opeType; 
	
	/**
	 * BUSI_ID     
	 */
	protected String busiId; 
	
	/** 导入UUID */
	private String uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String checkResult;
	/** 检查结果信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
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

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getShelfNo() {
		return shelfNo;
	}

	public void setShelfNo(String shelfNo) {
		this.shelfNo = shelfNo;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getImpUuid() {
		return impUuid;
	}

	public void setImpUuid(String impUuid) {
		this.impUuid = impUuid;
	}

	public String getOpeType() {
		return opeType;
	}

	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public Object getQrCode() {
		return qrCode;
	}

	public void setQrCode(Object qrCode) {
		this.qrCode = qrCode;
	}

	public Object getSpsImg() {
		return spsImg;
	}

	public void setSpsImg(Object spsImg) {
		this.spsImg = spsImg;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getShelfNoView() {
		return shelfNoView;
	}

	public void setShelfNoView(String shelfNoView) {
		this.shelfNoView = shelfNoView;
	}
	
	
}
