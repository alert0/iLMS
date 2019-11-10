package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

/**
 * <pre> 
 * 功能描述:库存推移业务实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月16日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class InvDevelopModel extends AbstractModel<String>{

	private static final long serialVersionUID = 3736868834029316133L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/** 工厂 */
	private String factoryCode;
	/** 仓库代码 */
	private String wareCode;
	/** 工作中心 */
	private String workCenter;
	/** 零件号 */
	private String partNo;
	/** 简号 */
	private String partShortNo;
	/** 零件名称 */
	private String partName;
	/** 时间点 */
	private String calPoint;
	/** 最小库存 */
	private String minStock;
	/** 最大库存 */
	private String maxStock;
	/** 实际库存 */
	private String stock;
	/** 计划消耗库存 */
	private String proQTY;
	/** 计划到货 */
	private String planQTY;
	/** 库存汇总 */
	private String invSummary;
	/**	收容数 */
	private String standardPac;
	/** 车型 */
	private String carType;
	/** 调整库存量 */
	private String adjStock;
	private String codeNum;
	/** 日消耗量 */
	private String dailyConsumption;
	/** 创建人 */
	private String creationUser;
	private String resultMsg;
	
	/** 导入uuid */
	private String uuid;
	/** 校验结果 */
	private String checkResult;
	private String excelCheckResult;
	/** 校验信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
	private String excelImportStatus;
	/** 操作类型 */
	private String opeType;
	
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
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
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
	public String getCalPoint() {
		return calPoint;
	}
	public void setCalPoint(String calPoint) {
		this.calPoint = calPoint;
	}
	public String getMinStock() {
		return minStock;
	}
	public void setMinStock(String minStock) {
		this.minStock = minStock;
	}
	public String getMaxStock() {
		return maxStock;
	}
	public void setMaxStock(String maxStock) {
		this.maxStock = maxStock;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getProQTY() {
		return proQTY;
	}
	public void setProQTY(String proQTY) {
		this.proQTY = proQTY;
	}
	public String getPlanQTY() {
		return planQTY;
	}
	public void setPlanQTY(String planQTY) {
		this.planQTY = planQTY;
	}
	public String getInvSummary() {
		return invSummary;
	}
	public void setInvSummary(String invSummary) {
		this.invSummary = invSummary;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getAdjStock() {
		return adjStock;
	}
	public void setAdjStock(String adjStock) {
		this.adjStock = adjStock;
	}
	public String getCodeNum() {
		return codeNum;
	}
	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}
	public String getDailyConsumption() {
		return dailyConsumption;
	}
	public void setDailyConsumption(String dailyConsumption) {
		this.dailyConsumption = dailyConsumption;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
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
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public String getExcelCheckResult() {
		return excelCheckResult;
	}
	public void setExcelCheckResult(String excelCheckResult) {
		this.excelCheckResult = excelCheckResult;
	}
	public String getExcelImportStatus() {
		return excelImportStatus;
	}
	public void setExcelImportStatus(String excelImportStatus) {
		this.excelImportStatus = excelImportStatus;
	}
	public String getStandardPac() {
		return standardPac;
	}
	public void setStandardPac(String standardPac) {
		this.standardPac = standardPac;
	}
	public static void checkImportModel(InvDevelopModel invDevelopModel) {
		StringBuilder checkInfo = new StringBuilder();
		if(StringUtil.isEmpty(invDevelopModel.getWorkCenter())) {
			invDevelopModel.setWorkCenter("");
			checkInfo.append("工作中心未选择;");
		}
		if (StringUtil.isEmpty(invDevelopModel.getWareCode())) {
			invDevelopModel.setWareCode("");
			checkInfo.append("仓库代码为空;");
		}
		if (StringUtil.isEmpty(invDevelopModel.getPartNo())) {
			invDevelopModel.setPartNo("");
			checkInfo.append("零件号为空;");
		}
		if (StringUtil.isEmpty(invDevelopModel.getStock())) {
			invDevelopModel.setStock(null);
			checkInfo.append("库存数量为空;");
		}else {
			try {
				Integer.parseInt(invDevelopModel.getStock());
			} catch (Exception e) {
				invDevelopModel.setStock(null);
				checkInfo.append("库存数量格式录入有误;");
			}
		}
		if (StringUtil.isEmpty(invDevelopModel.getAdjStock())) {
			invDevelopModel.setAdjStock(null);
		}
		if (StringUtil.isNotEmpty(invDevelopModel.getAdjStock())) {
			try {
				Integer.parseInt(invDevelopModel.getAdjStock());
			} catch (Exception e) {
				invDevelopModel.setAdjStock(null);
				checkInfo.append("库存调整量格式录入有误");
			}
		}
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
            invDevelopModel.setCheckResult("1");
            invDevelopModel.setOpeType("I");
            invDevelopModel.setCheckInfo("");
        }else{
        	invDevelopModel.setCheckResult("0");
        	invDevelopModel.setOpeType("U");
        	invDevelopModel.setCheckInfo(checkInfo.toString());
        }
	}
}
