/**
 * 
 */
package com.hanthink.gps.system.vo;

import java.io.Serializable;

/**
 * 描述：统计数据库对象占用空间信息VO
 * @author chenyong
 * @date  2016-10-10
 *
 */
public class CheckDBObjectSpaceVO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String factory;     //工厂
	private String segnName;    //数据库对象名
	private String segnType;    //数据库对象类型
	private String useSpace;       //占用空间大小
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getSegnName() {
		return segnName;
	}
	public void setSegnName(String segnName) {
		this.segnName = segnName;
	}
	public String getSegnType() {
		return segnType;
	}
	public void setSegnType(String segnType) {
		this.segnType = segnType;
	}
	public String getUseSpace() {
		return useSpace;
	}
	public void setUseSpace(String useSpace) {
		this.useSpace = useSpace;
	}
   
}
