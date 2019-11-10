package com.hanthink.mes.print.bean;

/**
 * 广汽新能源SPS指示票实体类
 * @Title: SpsInsModel.java
 * @Package: com.hanthink.mes.print.bean
 * @Description: 
 * @author dtp
 * @date 2018-12-29
 */
public class SpsInsModel {
	
	/**
	 * 箱子默认269
	 */
	public static final Integer maxMouldPlaceSPS_XH =  266;
	
	/**
	 * 箱子默认206
	 */
	public static final Integer maxMouldPlaceSPS_TC =  206;
	
	/**
	 * 模板代码 
	 */
	private String mouldCode;
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	/**
	 * 指示票号
	 */
	private String insNo; 
	
	/**
	 * 位置号     
	 */
	private String mouldPlace; 
	
	/**
	 * 配置项代码     
	 */
	private String configCode; 
	
	/**
	 * 配置项描述     
	 */
	private String configDesc; 
	
	/**
	 * PART_NO数据字典类型“SPS_CONFIG_TYPE"     
	 */
	private String configType; 
	
	/**
	 * 零件编号     
	 */
	private String partNo; 
	
	/**
	 * 零件名称     
	 */
	private String partName; 
	
	/**
	 * 用量     
	 */
	private String quantity;
	
	/**
	 * 显示值     
	 */
	private String showValue;

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

	public String getMouldPlace() {
		return mouldPlace;
	}

	public void setMouldPlace(String mouldPlace) {
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

	public String getMouldCode() {
		return mouldCode;
	}

	public void setMouldCode(String mouldCode) {
		this.mouldCode = mouldCode;
	}
	
	
}
