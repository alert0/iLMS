package com.hanthink.sps.model;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

/**
 * <pre>
 *  
 * 描述：MM_SPS_CONFIG_DETAIL 实体对象
 * 作者:dtp
 * 日期:2018-10-16 10:51:55
 * </pre>
 */
public class SpsConfigDetailModel extends AbstractModel<String> {

	private static final long serialVersionUID = -408760271210696906L;

	protected String id;
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	/**
	 * 生产线
	 */
	private String productLine;

	/**
	 * 配置项ID
	 */
	protected String configId;

	/**
	 * 车型
	 */
	protected String modelCode;

	/**
	 * 工位
	 */
	protected String stationCode;

	/**
	 * 零件号
	 */
	protected String partNo;

	/**
	 * 零件记号
	 */
	protected String partMark;

	/**
	 * 货架号
	 */
	protected String shelfNo;

	/**
	 * 创建时间
	 */
	protected String creationTime;

	/**
	 * 创建人
	 */
	protected String creationUser;

	/**
	 * 最后修改用户
	 */
	protected String lastModifiedUser;

	/**
	 * 最后修改IP
	 */
	protected String lastModifiedIp;

	/**
	 * 最后修改时间
	 */
	protected String lastModifiedTime;
	/**
	 * 配置项代码
	 */
	private String configCode;
	/**
	 * 配置项描述
	 */
	private String configDesc;
	/**
	 * 简号
	 */
	private String partShortNo;
	/**
	 * 零件名称
	 */
	private String partName;

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
	
	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartMark() {
		return partMark;
	}

	public void setPartMark(String partMark) {
		this.partMark = partMark;
	}

	public String getShelfNo() {
		return shelfNo;
	}

	public void setShelfNo(String shelfNo) {
		this.shelfNo = shelfNo;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
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

	public String getLastModifiedIp() {
		return lastModifiedIp;
	}

	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
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
	
	/** 导入UUID */
	private String  uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String  checkResult;
	private String  excelCheckResult;
	/** 检查结果信息 */
	private String  checkInfo;
	/** 导入状态 */
	private String  importStatus;
	private String excelImportStatus;
	/** 操作类型状态 */
	private String  opeType;

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

	public static void checkImport(SpsConfigDetailModel configModel) {
		StringBuffer checkInfo = new StringBuffer();
		
		if (StringUtil.isEmpty(configModel.getConfigCode())) {
			checkInfo.append("配置项代码不能为空;");
		}
		if (StringUtil.isEmpty(configModel.getModelCode())) {
			checkInfo.append("车型不能为空;");
		}
		/*if (StringUtil.isEmpty(configModel.getStationCode())) {
			checkInfo.append("工位不能为空;");
		}*/
		if (StringUtil.isEmpty(configModel.getPartNo())) {
			checkInfo.append("零件号不能为空;");
		}
		/*if(StringUtil.isEmpty(configModel.getPartMark())) {
			checkInfo.append("零件记号不能为空;");
		}*/
		if (StringUtil.isEmpty(configModel.getShelfNo())) {
			checkInfo.append("拣货号不能为空;");
		}
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
            configModel.setCheckResult("1");
            configModel.setCheckInfo("");
        }else{
        	configModel.setCheckResult("0");
        	configModel.setCheckInfo(checkInfo.toString());
        }
	}
}