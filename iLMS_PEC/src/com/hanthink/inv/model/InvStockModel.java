package com.hanthink.inv.model;

import com.hanthink.pup.util.PupUtil;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
/**
 * <pre> 
 * 功能描述:库存管理数据实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月12日下午4:10:36
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class InvStockModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = -4424918349399131953L;
	private String id;
	
	/** 工厂代码 */
	private String factoryCode;
	/** 仓库代码 */
	private String wareCode;
	/** 仓库类型 */
	private String wareType;
	/** 仓库名称 */
	private String wareName;
	/** 零件号 */
	private String partNo;
	/** 简号 */
	private String partShortNo;
	/** 零件名称 */
	private String partName;
	/** 库位 */
	private String baseLocation;
	/**最小库存(数量)*/
	private String minStock;
	/** 最大库存(数量) */
	private String maxStockNom;
	/** 包装数 */
	private String standardPac;
	/** 安全库存(箱) */
	private String safeStock;
	/** 最大库存 (箱)*/
	private String maxStock;
	/** 库存数量(数量) */
	private String realStock;
	/** 库存数量(箱) */
	private String stock;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/** 最后修改用户 */
	private String lastModifiedUser;
	/** 最后修改时间 */
	private String lastModifiedTime;
	/** 盘点单号 */
	private String insNo;
	/** 库存差异 */
	private String diffStock;
	/** 盘赢盘亏标识 */
	private String diffFlag;
	/** 导入UUID */
	private String uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String checkResult;
	private String excelCheckResult;
	/** 检查结果信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
	private String excelImportStatus;
	/** 操作类型状态 */
	private String  opeType;
	/** 库存调整原因 */
	private String adjRemark;
	/** 出库代码 */
	private String fromDepotNo;
	/** 入库代码 */
	private String toDepotNo;
	/**  */
	private String invOutNo;
	
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
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public String getWareType() {
		return wareType;
	}
	public void setWareType(String wareType) {
		this.wareType = wareType;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getSafeStock() {
		return safeStock;
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
	public String getBaseLocation() {
		return baseLocation;
	}
	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}
	public void setSafeStock(String safeStock) {
		this.safeStock = safeStock;
	}
	public String getMinStock() {
		return minStock;
	}
	public void setMinStock(String minStock) {
		this.minStock = minStock;
	}
	public String getMaxStockNom() {
		return maxStockNom;
	}
	public void setMaxStockNom(String maxStockNom) {
		this.maxStockNom = maxStockNom;
	}
	public String getStandardPac() {
		return standardPac;
	}
	public void setStandardPac(String standardPac) {
		this.standardPac = standardPac;
	}
	public String getMaxStock() {
		return maxStock;
	}
	public void setMaxStock(String maxStock) {
		this.maxStock = maxStock;
	}
	public String getRealStock() {
		return realStock;
	}
	public void setRealStock(String realStock) {
		this.realStock = realStock;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
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
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getInsNo() {
		return insNo;
	}
	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}
	public String getDiffStock() {
		return diffStock;
	}
	public void setDiffStock(String diffStock) {
		this.diffStock = diffStock;
	}
	public String getDiffFlag() {
		return diffFlag;
	}
	public void setDiffFlag(String diffFlag) {
		this.diffFlag = diffFlag;
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
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public String getAdjRemark() {
		return adjRemark;
	}
	public void setAdjRemark(String adjRemark) {
		this.adjRemark = adjRemark;
	}
	public String getFromDepotNo() {
		return fromDepotNo;
	}
	public void setFromDepotNo(String fromDepotNo) {
		this.fromDepotNo = fromDepotNo;
	}
	public String getToDepotNo() {
		return toDepotNo;
	}
	public void setToDepotNo(String toDepotNo) {
		this.toDepotNo = toDepotNo;
	}
	public String getInvOutNo() {
		return invOutNo;
	}
	public void setInvOutNo(String invOutNo) {
		this.invOutNo = invOutNo;
	}
	
	public static void checkImportModle(InvStockModel invStockModel) {
		StringBuffer checkInfo = new StringBuffer();
//		if (StringUtil.isEmpty(invStockModel.getWareCode())) {
//			checkInfo.append("仓库代码为空;");
//		}
		if(StringUtil.isEmpty(invStockModel.getFromDepotNo())) {
			checkInfo.append("出库代码为空;");
		}
		if(StringUtil.isEmpty(invStockModel.getToDepotNo())) {
			checkInfo.append("入库代码为空;");
		}
		if (StringUtil.isNotEmpty(invStockModel.getFromDepotNo()) && StringUtil.isNotEmpty(invStockModel.getToDepotNo())) {
			if (invStockModel.getFromDepotNo().equals(invStockModel.getToDepotNo())) {
				checkInfo.append("出库、入库仓库代码相同;");
			}
		}
		if (StringUtil.isEmpty(invStockModel.getMinStock())) {
			checkInfo.append("最小库存为空;");
		}else {
			if (!PupUtil.validateZeroAndInt(invStockModel.getMinStock())) {
				checkInfo.append("最小库存数量填写有误;");
			}
		}
		if(StringUtil.isEmpty(invStockModel.getMaxStock())) {
			checkInfo.append("最大库存为空;");
		}else {
			if (!PupUtil.validateZeroAndInt(invStockModel.getMaxStock())) {
				checkInfo.append("最大库存数量填写有误;");
			}
		}
		
		if (StringUtil.isEmpty(invStockModel.getStock())) {
			checkInfo.append("调整数量为空;");
		}else {
			if (!PupUtil.isInteger(invStockModel.getStock())) {
				checkInfo.append("调整数量填写有误;");
			}
		}
		if (StringUtil.isEmpty(invStockModel.getAdjRemark())) {
			checkInfo.append("备注信息为空;");
		}
		if (StringUtil.isNotEmpty(invStockModel.getMaxStock()) && StringUtil.isNotEmpty(invStockModel.getMinStock())) {
			if (Integer.valueOf(invStockModel.getMaxStock()) < Integer.valueOf(invStockModel.getMinStock())) {
				checkInfo.append("最大库存小于最小库存;");
			}
		}
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
            invStockModel.setCheckResult("1");
            invStockModel.setOpeType("I");
            invStockModel.setCheckInfo("");
        }else{
        	invStockModel.setCheckResult("0");
        	invStockModel.setOpeType("I");
        	invStockModel.setCheckInfo(checkInfo.toString());
        }
	}
}
