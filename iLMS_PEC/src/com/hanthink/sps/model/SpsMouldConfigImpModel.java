package com.hanthink.sps.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：MM_SPS_MOULD_CONFIG_IMP 实体对象
 * 作者:dtp
 * 日期:2018-11-22 09:45:26
 * </pre>
 */
@SuppressWarnings("serial")
public class SpsMouldConfigImpModel extends AbstractModel<String>{
	
	protected String id; 
	
	/**
	 * 模板ID
	 */
	protected String mouldId; 
	
	/**
	 * 对应模板位置号
	 */
	protected String mouldPlace; 
	
	/**
	 * 配置项ID
	 */
	protected String configId; 
	
	/**
	 * 配置项显示属性数据字典类型“SPS_CONFIG_SHOW"     
	 */
	protected String configShow; 
	
	/**
	 * 导入UUID
	 */
	protected String impUuid; 
	
	/**
	 * 检查结果数据字典类型"PUB_IMP_CK_RESULT"     
	 */
	protected String checkResult; 
	
	/**
	 * 检查结果错误信息
	 */
	protected String checkInfo; 
	
	/**
	 * 导入状态数据字典类型“PUB_IMP_STATUS”     
	 */
	protected String importStatus; 
	
	/**
	 * 是否继续检查0:否1:是     
	 */
	protected String continueCheck; 
	
	/**
	 * 导入数据操作类型I:新增U:更新     
	 */
	protected String opeType; 
	
	/**
	 * 业务表主键字段值
	 */
	protected String busiId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMouldId() {
		return mouldId;
	}

	public void setMouldId(String mouldId) {
		this.mouldId = mouldId;
	}

	public String getMouldPlace() {
		return mouldPlace;
	}

	public void setMouldPlace(String mouldPlace) {
		this.mouldPlace = mouldPlace;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigShow() {
		return configShow;
	}

	public void setConfigShow(String configShow) {
		this.configShow = configShow;
	}

	public String getImpUuid() {
		return impUuid;
	}

	public void setImpUuid(String impUuid) {
		this.impUuid = impUuid;
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

	public String getContinueCheck() {
		return continueCheck;
	}

	public void setContinueCheck(String continueCheck) {
		this.continueCheck = continueCheck;
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
	
	
}
