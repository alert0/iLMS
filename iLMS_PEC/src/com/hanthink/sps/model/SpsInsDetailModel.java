package com.hanthink.sps.model;

/**
  * <pre> 
  * 描述：MM_SPS_INS_DETAIL 实体对象
  * 作者:dtp
  * 日期:2018-10-16 17:14:23
  * </pre>
  */
public class SpsInsDetailModel{
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	/**
	 * 指示票号
	 */
	protected String insNo; 
	
	/**
	 * 位置号     
	 */
	protected Integer mouldPlace; 
	
	/**
	 * 配置项代码     
	 */
	protected String configCode; 
	
	/**
	 * 配置项描述     
	 */
	protected String configDesc; 
	
	/**
	 * PART_NO数据字典类型“SPS_CONFIG_TYPE"     
	 */
	protected String configType; 
	
	/**
	 * 零件号     
	 */
	protected String partNo; 
	
	/**
	 * 零件名称     
	 */
	protected String partName; 
	
	/**
	 * 用量     
	 */
	protected String quantity;
	
	/**
	 * 显示值     
	 */
	protected String showValue;

	
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getInsNo() {
		return insNo;
	}

	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}

	public Integer getMouldPlace() {
		return mouldPlace;
	}

	public void setMouldPlace(Integer mouldPlace) {
		this.mouldPlace = mouldPlace;
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

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getShowValue() {
		return showValue;
	}

	public void setShowValue(String showValue) {
		this.showValue = showValue;
	} 
	
	
}