package com.hanthink.gps.pub.vo;

public class FactoryVO extends BaseVO{

	private static final long serialVersionUID = 102513541638416L;
	
	/** 工厂代码 */
	private String factoryCode;
	
	/** 工厂名称 */
	private String factoryName;
	
	/** 工厂地址 */
	private String factoryAddr;
	
	//用户名
	private String userName;
	//用户姓名
	private String userCName;
	
	private String type;
	
	private String userGroup;
	
	private String opeName;
	//部门
	private String departmentName;
	
	

	

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * 获取工厂代码
	 * @return the factoryCode 工厂代码
	 */
	public String getFactoryCode() {
		return factoryCode;
	}

	/**
	 * 设置工厂代码
	 * @param factoryCode the factoryCode 工厂代码
	 */
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	/**
	 * 获取工厂名称
	 * @return the factoryName 工厂名称
	 */
	public String getFactoryName() {
		return factoryName;
	}

	/**
	 * 设置工厂名称
	 * @param factoryName the factoryName 工厂名称
	 */
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCName() {
		return userCName;
	}

	public void setUserCName(String userCName) {
		this.userCName = userCName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getOpeName() {
		return opeName;
	}

	public void setOpeName(String opeName) {
		this.opeName = opeName;
	}

	/**
	 * 获取工厂地址
	 * @return the factoryAddr 工厂地址
	 */
	public String getFactoryAddr() {
		return factoryAddr;
	}

	/**
	 * 设置工厂地址
	 * @param factoryAddr the factoryAddr 工厂地址
	 */
	public void setFactoryAddr(String factoryAddr) {
		this.factoryAddr = factoryAddr;
	}
	
	
	
	

}
