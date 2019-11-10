package com.hanthink.pub.model;

/**
 * @ClassName: PubFactoryInfoModel
 * @Description: 工厂信息实体类
 * @author dtp
 * @date 2018年12月3日
 */
public class PubFactoryInfoModel {
	
	/**
	 * 工厂代码
	 */
	private String factoryCode;
	
	/**
	 * 工厂名称
	 */
	private String factoryName;
	
	/**
	 * GACNE
	 */
	private String logo;
	
	/**
	 * 订单类型
	 */
	private String orderType;
	
	/**
	 * 页数
	 */
	private String ys;
	
	/**
	 * 标签张数
	 */
	private String labelPageNum;

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getYs() {
		return ys;
	}

	public void setYs(String ys) {
		this.ys = ys;
	}

	public String getLabelPageNum() {
		return labelPageNum;
	}

	public void setLabelPageNum(String labelPageNum) {
		this.labelPageNum = labelPageNum;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	
}
