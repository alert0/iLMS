package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
/**
 * <pre> 
 * 功能描述:盘点信息管理数据实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月12日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class InvStockTakModel extends AbstractModel<String>{

	private static final long serialVersionUID = -7415514371388891096L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 实物通过状态 */
	private final Integer PROPOSAL_STATUS = 5;
	
	@SuppressWarnings("unused")
	private Integer proposalStatus;
	public Integer getProposalStatus() {
		return this.PROPOSAL_STATUS;
	}
	/** 盘点单号 */
	private String insNo;
	/** 工厂 */
	private String factoryCode;
	/** 处理标识 */
	private String dealFlag;
	/** 处理时间 */
	private String dealTime;
	/** 仓库 */
	private String wareCode;
	/** 零件号 */
	private String partNo;
	/** 简号 */
	private String partShortNo;
	/** 物流库地址 */
	private String storage;
	/** 盘点前系统库存 */
	private String sysStock;
	/** 实际盘点库存 */
	private String takeStock;
	/** 理论在库 */
	private String calStock;
	/** 库存差异 */
	private String diffStock;
	/** 仓库地址 */
	private String location;
	/** 数据来源 */
	private String dataSource;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/**  零件收容数 */
	private String standerdPackage;
	/** 采购类型 */
	private String purchaseType;
	private String workCenter;
	private String wareType;
	/** 备注 */
	private String note;
	/** Excel导入、导出序号 */
	private String NO;
	/** 导入UUID */
	private String importUuid;
	/** 导入状态 */
	private String importStatus;
	private String excelImportStatus;
	/** 数据校验结果 */
	private String checkResult;
	private String excelCheckResult;
	/** 数据校验信息 */
	private String checkInfo;
	/** 操作类型 */
	private String opeType;
	
	
	public String getInsNo() {
		return insNo;
	}
	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getDealFlag() {
		return dealFlag;
	}
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
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
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getSysStock() {
		return sysStock;
	}
	public void setSysStock(String sysStock) {
		this.sysStock = sysStock;
	}
	public String getTakeStock() {
		return takeStock;
	}
	public void setTakeStock(String takeStock) {
		this.takeStock = takeStock;
	}
	public String getCalStock() {
		return calStock;
	}
	public void setCalStock(String calStock) {
		this.calStock = calStock;
	}
	public String getDiffStock() {
		return diffStock;
	}
	public void setDiffStock(String diffStock) {
		this.diffStock = diffStock;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
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
	public String getStanderdPackage() {
		return standerdPackage;
	}
	public void setStanderdPackage(String standerdPackage) {
		this.standerdPackage = standerdPackage;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getWareType() {
		return wareType;
	}
	public void setWareType(String wareType) {
		this.wareType = wareType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNO() {
		return NO;
	}
	public void setNO(String nO) {
		NO = nO;
	}
	public String getImportUuid() {
		return importUuid;
	}
	public void setImportUuid(String importUuid) {
		this.importUuid = importUuid;
	}
	public String getImportStatus() {
		return importStatus;
	}
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}
	public String getExcelImportStatus() {
		return excelImportStatus;
	}
	public void setExcelImportStatus(String excelImportStatus) {
		this.excelImportStatus = excelImportStatus;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getExcelCheckResult() {
		return excelCheckResult;
	}
	public void setExcelCheckResult(String excelCheckResult) {
		this.excelCheckResult = excelCheckResult;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public void checkImportModel(InvStockTakModel invStockTakModel) {
		StringBuffer checkInfo = new StringBuffer();
		if(StringUtil.isEmpty(invStockTakModel.getPartNo())) {
			checkInfo.append("零件号为空;");
		}
		if (StringUtil.isEmpty(invStockTakModel.getWareCode())) {
			checkInfo.append("仓库代码为空;");
		}
		if (StringUtil.isEmpty(invStockTakModel.getTakeStock())) {
			checkInfo.append("实际在库数目为空;");
		}else {
			try {
				if (Integer.parseInt(invStockTakModel.getTakeStock()) < 0) {
					invStockTakModel.setTakeStock("0");
				}
			} catch (Exception e) {
				invStockTakModel.setTakeStock(null);
				checkInfo.append("实际在库数目数字无效");
			}
		}
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
            invStockTakModel.setCheckResult("1");
            invStockTakModel.setOpeType("I");
            invStockTakModel.setCheckInfo("");
        }else{
        	invStockTakModel.setCheckResult("0");
        	invStockTakModel.setOpeType("U");
        	invStockTakModel.setCheckInfo(checkInfo.toString());
        }
	}
}
