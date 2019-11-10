package com.hanthink.sps.model;

import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.model.AbstractModel;
import com.mysql.jdbc.StringUtils;

public class SpsConfigItemModel extends AbstractModel<String>{
protected String id; 
	

	/**
	 * 工厂     
	 */
	protected String factory; 
	
	/** 导入UUID */
	private String uuid;
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	
	/**
	 * 产线     
	 */
	protected String productionLine; 
	
	/**
	 * 配置项代码     
	 */
	protected String configCode; 
	
	/**
	 * 配置项描述     
	 */
	protected String configDesc; 
	
	/**
	 * 所属类型,数据字典类型“SPS_CONFIG_TYPE"     
	 */
	protected String configType; 
	
	/**
	 * 固定值    
	 */
	protected String configValue; 
	
	/**
	 * 是否可编辑,数据字典类型”TRUE_FLASE"     
	 */
	protected String isEdit; 
	
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
	
	private String codeValueName;
	 
	
	private String codeType;

	private String checkResult;
	private String checkInfo;
	private String importStatus;
	@Override
	public void setId(String id) {
		this.id = id;
		
	}

	@Override
	public String getId() {
		return id;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getProductionLine() {
		return productionLine;
	}

	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
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

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
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

	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
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
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public static void checkImportData(SpsConfigItemModel model) {
		StringBuffer checkInfo = new StringBuffer();
		if(StringUtils.isNullOrEmpty(model.getConfigCode())) {
			checkInfo.append("配置项代码不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getConfigDesc())) {
			checkInfo.append("配置项描述不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getConfigType())) {
			checkInfo.append("所属类型不能为空;");
		}
		if("固定值".equals(model.getConfigType()) && StringUtils.isNullOrEmpty(model.getConfigValue())) {
			checkInfo.append("所属类型为[固定值]固定值不能为空;");
		}
		if(StringUtils.isNullOrEmpty(checkInfo.toString())){
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
		}else{
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
			model.setCheckInfo(checkInfo.toString());
		}
	}


}
