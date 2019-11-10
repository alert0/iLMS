package com.hanthink.pkg.model;

import com.hotent.base.core.model.AbstractModel;

/**
* <p>Title: PkgProposalModel.java</p>
* <p>Description: </p>
* <p>Company: hanthink</p>
* @author luoxq
* @date 2018年9月30日
*/

public class PkgProposalModel extends AbstractModel<String>{
	
	/** 提案状态-未发起 */
	public static final String PROPOSAL_STATUS_NO = "0";
	/** 提案状态-发起 */
	public static final String PROPOSAL_STATUS_START = "1";
	/** 提案状态-待审核*/
	public static final String PROPOSAL_STATUS_WCHECK = "2";
	/** 提案状态-提案通过*/
	public static final String PROPOSAL_STATUS_CHECK_YES = "3";
	/** 提案状态-提案不通过*/
	public static final String PROPOSAL_STATUS_CHECK_NO = "4";
	/** 提案状态-实物审核通过*/
	public static final String PROPOSAL_STATUS_MCHECK_YES = "5";
	/** 提案状态-实物审核不通过*/
	public static final String PROPOSAL_STATUS_MCHECK_NO = "6";
	/** 提案状态-终止*/
	public static final String PROPOSAL_STATUS_STOP = "7";
	
	/** 留用新设状态-新设 */
	public static final String STAY_NEW_STATUS_NEW = "1";
	/** 留用新设状态-留用 */
	public static final String STAY_NEW_STATUS_STAY = "2";
	
	/** 包装类型-台车 */
	public static final String PACK_TYPE_TROLLEY = "1";
	/** 包装类型-非台车 */
	public static final String PACK_TYPE_NOT_TRO = "0";
	
	/** 是否组合包装-是 */
	public static final String IS_COM_PACK_YES = "1";
	/** 是否组合包装-否 */
	public static final String IS_COM_PACK_NO = "0";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8161657536929912794L;
    /**逻辑主键**/
	private String id;
	private String factoryCode;
	/**车型**/
	private String carType;
	/**车型（用来接收前端查询时候传递的字符串数组**/
	private String carTypes;
	/**供应商代码**/
	private String supplierNo;
	/**供应商名称**/
	private String supplierName;
	/**区域**/
	private String area;
	/**零件号**/
	private String partNo;
	/**简号**/
	private String partShortNo;
	/**工程**/
	private String project;
	/**零件名称**/
	private String partNameCn;
	/**箱种**/
	private String boxType;
	/**箱种名称**/
	private String boxTypeName;
	/**箱长**/
	private String packLength;
	/**箱宽**/
	private String packWidth;
	/**箱高**/
	private String packHeight;
	/**零件重量**/
	private String partWeight;
	/**包装重量**/
	private String packWeight;
	/**总重量**/
	private String totalWeight;
	/** 零件总重量 */
	private String partTotalWeight;
	/**看板位置**/
	private String boardLocation;
	/**提案状态**/
	private String proposalStatus;
	/**提案状态名称 **/
	private String proposalStatusName;
	/**回复期限**/
	private  String replyLimitDate;
	/**是否组合包装**/
	private String isComPack;
	/**留用新设状态**/
	private String stayNewStatus;
	/**留用新设状态名称**/
	private String stayNewStatusName;
	/**签字提案文件（FTP文件ID）**/
	private String signProFile;
	/**生效时间**/
	private String effStart;
	/**失效时间**/
	private String effEnd;
	/**提案时间**/
	private String createTimesStr;
	/**提案开始时间**/
	private String createTimeStart;
	/**提案时间至**/
	private String createTimeEnd;
	/**提交状态**/
	private String submitStatus;
    /**零件担当**/
    private String partRespUser;
    /**留用新设**/
    private String status;
    /**联系电话**/
    private String telNo;
    /**组合包装备注**/
    private String comPackRemark;
    
    /** 零件长 */
    private String partLength;
    /** 零件宽 */
    private String partWidth;
    /** 零件高 */
    private String partHeight;
    /** 空台车长 */
    private String emptyTrolleyLength;
    /** 空台车宽 */
    private String emptyTrolleyWidth;
    /** 空台车高 */
    private String emptyTrolleyHeight;
    /** 实台车长 */
    private String realTrolleyLength;
    /** 实台车宽 */
    private String realTrolleyWidth;
    /** 实台车高 */
    private String realTrolleyHeight;
    /** 台车重量 */
    private String trolleyWeight;
    /** 有无台车编码 */
    private String isTrolleyCode;
    /** 有无定位器 */
    private String isPositioner;
    /** 最大包装数 */
    private String maxPackageNum;
    /** 空台车正面图 */
    private String emptyTroFrontPic;
    /** 空台车侧面图 */
    private String emptyTroSidePic;
    /** 实台车图 */
    private String realTroPic;
    /** 台车重要部位图 */
    private String importantPostionPic;
    /** 牵引棒距地高 */
    private String tractionRodHeight;
    /** 车轮直径 */
    private String wheelDiameter;
    
    
    /**
     * 
     * 以下是包装提案非台车信息字段
     * 
     * **/
	
	/**包装提案id**/
	private String proposalId;
	/**箱code**/
	private String boxCode;
	/**托盘尺寸长**/
	private String trayLength;
	/**托盘尺寸宽**/
	private String trayWidth;
	/**托盘尺寸高**/
	private String trayHeight;
	/**逐个包装**/
	private String onePackage;
	/**防尘罩**/
	private String dustCover;
	/**内材**/
	private String intMate;
	/**作业要求**/
	private String workRequire;
	/**文字说明***/
	private String wordDesc;
	/**单一零件图**/
	private String singlePartPic;
	/**单一零件摆放图**/
	private String singlePartPutPic;
	/**包装俯视图**/
	private String packOverLookPic;
	/**包装侧视图**/
	private String packSideLookPic;
    /**创建人**/
	private String creationUser;
	/**最后修改用户**/
	private String lastModifiedUser;
	/**零件规格包装**/
    private String standardPackage;
    
    /**查询类型**/
    private String queryType;
    /**生失效状态**/
    private String effStatus;
    /**包装类型(台车，非台车）**/
    private String packType;
    /**分组编号**/
    private String groupNo;
    
    /**
     * 托盘数量管理表字段
     */
    /**已纳入量**/
    private String provideQty;
    /**托盘需求量**/
    private String trayRequireQty;
    /**计划完成日期**/
    private String planDate;
    /**延迟原因**/
    private String delayReason;
    /**箱子需求量**/
    private String boxRequireQty;
    /**物流模式**/
    private String hairNoteModel;
    
    /**弹窗**/
    private String value;
    private String label;
    
    private String isFile;
    
    private String importName;
    private String importMobile;
    private String importMail;
    
    /**审核备注**/
    private String checkRemark;
    
    /***由原来从供应商表中查询出来改为由供应商自己填写*************************************************/
    /**供应商联系人**/
    private String packDeptContact;
    /**供应商联系电话**/
    private String packDeptTel;
    /**供应商联系邮箱**/
    private String packDeptMail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
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

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	public String getReplyLimitDate() {
		return replyLimitDate;
	}

	public void setReplyLimitDate(String replyLimitDate) {
		this.replyLimitDate = replyLimitDate;
	}

	public String getIsComPack() {
		return isComPack;
	}

	public void setIsComPack(String isComPack) {
		this.isComPack = isComPack;
	}

	public String getStayNewStatus() {
		return stayNewStatus;
	}

	public void setStayNewStatus(String stayNewStatus) {
		this.stayNewStatus = stayNewStatus;
	}

	public String getSignProFile() {
		return signProFile;
	}

	public void setSignProFile(String signProFile) {
		this.signProFile = signProFile;
	}

	public String getEffStart() {
		return effStart;
	}

	public void setEffStart(String effStart) {
		this.effStart = effStart;
	}

	public String getEffEnd() {
		return effEnd;
	}

	public void setEffEnd(String effEnd) {
		this.effEnd = effEnd;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public String getPackLength() {
		return packLength;
	}

	public void setPackLength(String packLength) {
		this.packLength = packLength;
	}

	public String getPackWidth() {
		return packWidth;
	}

	public void setPackWidth(String packWidth) {
		this.packWidth = packWidth;
	}

	public String getPackHeight() {
		return packHeight;
	}

	public void setPackHeight(String packHeight) {
		this.packHeight = packHeight;
	}


	public String getPartWeight() {
		return partWeight;
	}

	public void setPartWeight(String partWeight) {
		this.partWeight = partWeight;
	}

	public String getPackWeight() {
		return packWeight;
	}

	public void setPackWeight(String packWeight) {
		this.packWeight = packWeight;
	}

	public String getBoardLocation() {
		return boardLocation;
	}

	public void setBoardLocation(String boardLocation) {
		this.boardLocation = boardLocation;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	
	/**
	 * 
	 * 以下是包装提案非台车信息
	 * 
	 * **/
    
	
	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public String getTrayLength() {
		return trayLength;
	}

	public void setTrayLength(String trayLength) {
		this.trayLength = trayLength;
	}

	public String getTrayWidth() {
		return trayWidth;
	}

	public void setTrayWidth(String trayWidth) {
		this.trayWidth = trayWidth;
	}

	public String getTrayHeight() {
		return trayHeight;
	}

	public void setTrayHeight(String trayHeight) {
		this.trayHeight = trayHeight;
	}

	public String getOnePackage() {
		return onePackage;
	}

	public void setOnePackage(String onePackage) {
		this.onePackage = onePackage;
	}

	public String getDustCover() {
		return dustCover;
	}

	public void setDustCover(String dustCover) {
		this.dustCover = dustCover;
	}

	public String getIntMate() {
		return intMate;
	}

	public void setIntMate(String intMate) {
		this.intMate = intMate;
	}

	public String getWorkRequire() {
		return workRequire;
	}

	public void setWorkRequire(String workRequire) {
		this.workRequire = workRequire;
	}

	public String getWordDesc() {
		return wordDesc;
	}

	public void setWordDesc(String wordDesc) {
		this.wordDesc = wordDesc;
	}

	public String getSinglePartPic() {
		return singlePartPic;
	}

	public void setSinglePartPic(String singlePartPic) {
		this.singlePartPic = singlePartPic;
	}

	public String getSinglePartPutPic() {
		return singlePartPutPic;
	}

	public void setSinglePartPutPic(String singlePartPutPic) {
		this.singlePartPutPic = singlePartPutPic;
	}

	public String getPackOverLookPic() {
		return packOverLookPic;
	}

	public void setPackOverLookPic(String packOverLookPic) {
		this.packOverLookPic = packOverLookPic;
	}

	public String getPackSideLookPic() {
		return packSideLookPic;
	}

	public void setPackSideLookPic(String packSideLookPic) {
		this.packSideLookPic = packSideLookPic;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getPartRespUser() {
		return partRespUser;
	}

	public void setPartRespUser(String partRespUser) {
		this.partRespUser = partRespUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getStandardPackage() {
		return standardPackage;
	}

	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getComPackRemark() {
		return comPackRemark;
	}

	public void setComPackRemark(String comPackRemark) {
		this.comPackRemark = comPackRemark;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getEffStatus() {
		return effStatus;
	}

	public void setEffStatus(String effStatus) {
		this.effStatus = effStatus;
	}

	public String getPackType() {
		return packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getProvideQty() {
		return provideQty;
	}

	public void setProvideQty(String provideQty) {
		this.provideQty = provideQty;
	}

	public String getTrayRequireQty() {
		return trayRequireQty;
	}

	public void setTrayRequireQty(String trayRequireQty) {
		this.trayRequireQty = trayRequireQty;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	public String getBoxRequireQty() {
		return boxRequireQty;
	}

	public void setBoxRequireQty(String boxRequireQty) {
		this.boxRequireQty = boxRequireQty;
	}

	public String getHairNoteModel() {
		return hairNoteModel;
	}

	public void setHairNoteModel(String hairNoteModel) {
		this.hairNoteModel = hairNoteModel;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return the createTimesStr
	 */
	public String getCreateTimesStr() {
		return createTimesStr;
	}

	/**
	 * @param createTimesStr the createTimesStr to set
	 */
	public void setCreateTimesStr(String createTimesStr) {
		this.createTimesStr = createTimesStr;
	}

	/**
	 * @return the partTotalWeight
	 */
	public String getPartTotalWeight() {
		return partTotalWeight;
	}

	/**
	 * @param partTotalWeight the partTotalWeight to set
	 */
	public void setPartTotalWeight(String partTotalWeight) {
		this.partTotalWeight = partTotalWeight;
	}

	/**
	 * @return the partLength
	 */
	public String getPartLength() {
		return partLength;
	}

	/**
	 * @param partLength the partLength to set
	 */
	public void setPartLength(String partLength) {
		this.partLength = partLength;
	}

	/**
	 * @return the partWidth
	 */
	public String getPartWidth() {
		return partWidth;
	}

	/**
	 * @param partWidth the partWidth to set
	 */
	public void setPartWidth(String partWidth) {
		this.partWidth = partWidth;
	}

	/**
	 * @return the partHeight
	 */
	public String getPartHeight() {
		return partHeight;
	}

	/**
	 * @param partHeight the partHeight to set
	 */
	public void setPartHeight(String partHeight) {
		this.partHeight = partHeight;
	}

	/**
	 * @return the emptyTrolleyLength
	 */
	public String getEmptyTrolleyLength() {
		return emptyTrolleyLength;
	}

	/**
	 * @param emptyTrolleyLength the emptyTrolleyLength to set
	 */
	public void setEmptyTrolleyLength(String emptyTrolleyLength) {
		this.emptyTrolleyLength = emptyTrolleyLength;
	}

	/**
	 * @return the emptyTrolleyWidth
	 */
	public String getEmptyTrolleyWidth() {
		return emptyTrolleyWidth;
	}

	/**
	 * @param emptyTrolleyWidth the emptyTrolleyWidth to set
	 */
	public void setEmptyTrolleyWidth(String emptyTrolleyWidth) {
		this.emptyTrolleyWidth = emptyTrolleyWidth;
	}

	/**
	 * @return the emptyTrolleyHeight
	 */
	public String getEmptyTrolleyHeight() {
		return emptyTrolleyHeight;
	}

	/**
	 * @param emptyTrolleyHeight the emptyTrolleyHeight to set
	 */
	public void setEmptyTrolleyHeight(String emptyTrolleyHeight) {
		this.emptyTrolleyHeight = emptyTrolleyHeight;
	}

	/**
	 * @return the realTrolleyLength
	 */
	public String getRealTrolleyLength() {
		return realTrolleyLength;
	}

	/**
	 * @param realTrolleyLength the realTrolleyLength to set
	 */
	public void setRealTrolleyLength(String realTrolleyLength) {
		this.realTrolleyLength = realTrolleyLength;
	}

	/**
	 * @return the realTrolleyWidth
	 */
	public String getRealTrolleyWidth() {
		return realTrolleyWidth;
	}

	/**
	 * @param realTrolleyWidth the realTrolleyWidth to set
	 */
	public void setRealTrolleyWidth(String realTrolleyWidth) {
		this.realTrolleyWidth = realTrolleyWidth;
	}

	/**
	 * @return the realTrolleyHeight
	 */
	public String getRealTrolleyHeight() {
		return realTrolleyHeight;
	}

	/**
	 * @param realTrolleyHeight the realTrolleyHeight to set
	 */
	public void setRealTrolleyHeight(String realTrolleyHeight) {
		this.realTrolleyHeight = realTrolleyHeight;
	}

	/**
	 * @return the trolleyWeight
	 */
	public String getTrolleyWeight() {
		return trolleyWeight;
	}

	/**
	 * @param trolleyWeight the trolleyWeight to set
	 */
	public void setTrolleyWeight(String trolleyWeight) {
		this.trolleyWeight = trolleyWeight;
	}

	/**
	 * @return the isTrolleyCode
	 */
	public String getIsTrolleyCode() {
		return isTrolleyCode;
	}

	/**
	 * @param isTrolleyCode the isTrolleyCode to set
	 */
	public void setIsTrolleyCode(String isTrolleyCode) {
		this.isTrolleyCode = isTrolleyCode;
	}

	/**
	 * @return the isPositioner
	 */
	public String getIsPositioner() {
		return isPositioner;
	}

	/**
	 * @param isPositioner the isPositioner to set
	 */
	public void setIsPositioner(String isPositioner) {
		this.isPositioner = isPositioner;
	}

	/**
	 * @return the maxPackageNum
	 */
	public String getMaxPackageNum() {
		return maxPackageNum;
	}

	/**
	 * @param maxPackageNum the maxPackageNum to set
	 */
	public void setMaxPackageNum(String maxPackageNum) {
		this.maxPackageNum = maxPackageNum;
	}

	/**
	 * @return the emptyTroFrontPic
	 */
	public String getEmptyTroFrontPic() {
		return emptyTroFrontPic;
	}

	/**
	 * @param emptyTroFrontPic the emptyTroFrontPic to set
	 */
	public void setEmptyTroFrontPic(String emptyTroFrontPic) {
		this.emptyTroFrontPic = emptyTroFrontPic;
	}

	/**
	 * @return the emptyTroSidePic
	 */
	public String getEmptyTroSidePic() {
		return emptyTroSidePic;
	}

	/**
	 * @param emptyTroSidePic the emptyTroSidePic to set
	 */
	public void setEmptyTroSidePic(String emptyTroSidePic) {
		this.emptyTroSidePic = emptyTroSidePic;
	}

	/**
	 * @return the realTroPic
	 */
	public String getRealTroPic() {
		return realTroPic;
	}

	/**
	 * @param realTroPic the realTroPic to set
	 */
	public void setRealTroPic(String realTroPic) {
		this.realTroPic = realTroPic;
	}

	/**
	 * @return the importantPostionPic
	 */
	public String getImportantPostionPic() {
		return importantPostionPic;
	}

	/**
	 * @param importantPostionPic the importantPostionPic to set
	 */
	public void setImportantPostionPic(String importantPostionPic) {
		this.importantPostionPic = importantPostionPic;
	}

	/**
	 * @return the tractionRodHeight
	 */
	public String getTractionRodHeight() {
		return tractionRodHeight;
	}

	/**
	 * @param tractionRodHeight the tractionRodHeight to set
	 */
	public void setTractionRodHeight(String tractionRodHeight) {
		this.tractionRodHeight = tractionRodHeight;
	}

	/**
	 * @return the wheelDiameter
	 */
	public String getWheelDiameter() {
		return wheelDiameter;
	}

	/**
	 * @param wheelDiameter the wheelDiameter to set
	 */
	public void setWheelDiameter(String wheelDiameter) {
		this.wheelDiameter = wheelDiameter;
	}

	/**
	 * @return the boxTypeNamePkgProposalModel.java
	 */
	public String getBoxTypeName() {
		return boxTypeName;
	}

	/**
	 * @param boxTypeName the boxTypeName to set
	 */
	public void setBoxTypeName(String boxTypeName) {
		this.boxTypeName = boxTypeName;
	}

	/**
	 * @return the proposalStatusNamePkgProposalModel.java
	 */
	public String getProposalStatusName() {
		return proposalStatusName;
	}

	/**
	 * @param proposalStatusName the proposalStatusName to set
	 */
	public void setProposalStatusName(String proposalStatusName) {
		this.proposalStatusName = proposalStatusName;
	}

	/**
	 * @return the stayNewStatusNamePkgProposalModel.java
	 */
	public String getStayNewStatusName() {
		return stayNewStatusName;
	}

	/**
	 * @param stayNewStatusName the stayNewStatusName to set
	 */
	public void setStayNewStatusName(String stayNewStatusName) {
		this.stayNewStatusName = stayNewStatusName;
	}

	public String getCarTypes() {
		return carTypes;
	}

	public void setCarTypes(String carTypes) {
		this.carTypes = carTypes;
	}

	public String getIsFile() {
		return isFile;
	}

	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}

	public String getImportName() {
		return importName;
	}

	public void setImportName(String importName) {
		this.importName = importName;
	}

	public String getImportMobile() {
		return importMobile;
	}

	public void setImportMobile(String importMobile) {
		this.importMobile = importMobile;
	}

	public String getImportMail() {
		return importMail;
	}

	public void setImportMail(String importMail) {
		this.importMail = importMail;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getPackDeptContact() {
		return packDeptContact;
	}

	public void setPackDeptContact(String packDeptContact) {
		this.packDeptContact = packDeptContact;
	}

	public String getPackDeptTel() {
		return packDeptTel;
	}

	public void setPackDeptTel(String packDeptTel) {
		this.packDeptTel = packDeptTel;
	}

	public String getPackDeptMail() {
		return packDeptMail;
	}

	public void setPackDeptMail(String packDeptMail) {
		this.packDeptMail = packDeptMail;
	}
	
	
}
