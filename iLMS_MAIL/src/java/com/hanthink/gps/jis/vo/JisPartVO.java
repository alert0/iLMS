package com.hanthink.gps.jis.vo;

import java.io.Serializable;

/**
 * @Desc    : 同步零件提醒VO
 * @FileName: JisPartVO.java 
 * @CreateOn: 2016-7-27 下午02:58:44
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JisPartVO implements Serializable {

	private static final long serialVersionUID = 4744984000621018422L;
	
	/** 工厂代码 */
	private String factoryCode;
	
	/** MTOC */
	private String mtoc;
	/** 零件组代码 */
	private String groupCode;
	/** 零件组名称 */
	private String groupName;
	
	
	
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
	 * 获取MTOC
	 * @return the mtoc MTOC
	 */
	public String getMtoc() {
		return mtoc;
	}
	/**
	 * 设置MTOC
	 * @param mtoc the mtoc MTOC
	 */
	public void setMtoc(String mtoc) {
		this.mtoc = mtoc;
	}
	/**
	 * 获取零件组代码
	 * @return the groupCode 零件组代码
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * 设置零件组代码
	 * @param groupCode the groupCode 零件组代码
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	/**
	 * 获取零件组名称
	 * @return the groupName 零件组名称
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * 设置零件组名称
	 * @param groupName the groupName 零件组名称
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	
}
