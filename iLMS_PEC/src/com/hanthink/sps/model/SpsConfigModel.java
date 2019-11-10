package com.hanthink.sps.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：MM_SPS_CONFIG 实体对象
  * 作者:dtp
  * 日期:2018-10-16 10:43:21
  * </pre>
  */
public class SpsConfigModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 6968867091733794709L;

	protected String id; 
	
	/**
	 * 工厂     
	 */
	protected String factory; 
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	} 

	
}