package com.hanthink.gps.pub.vo;

import java.util.Date;

/**
 * 系统参数
 * @author Smy
 *
 */
public class SystemParamVO {
	
	
	/** 工厂 */
	private String factory;
	/** 参数编码 */
    private String paramCode;
    /** 参数组 */
	private String paramGroup;
	/** 参数名 */
	private String paramName;
	/** 参数值 */
	private String paramVal;
	/** 参数类型 */
	private String paramType;
	/** 是否可编辑 */
	private String isEdit;
	/** note */
	private String note;
	/** 最后修改人*/
	private String lastUpdateUserName;
	/** 最后修改时间*/
	private Date lastUpdateTime;
	/** 创建时间 */
	private Date createTime;
	/** 正则表达 */
	private String uda1;
	/** 正则说明 */
	private String uda2;
	
	
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParamGroup() {
		return paramGroup;
	}
	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamVal() {
		return paramVal;
	}
	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}
	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUda1() {
		return uda1;
	}
	public void setUda1(String uda1) {
		this.uda1 = uda1;
	}
	public String getUda2() {
		return uda2;
	}
	public void setUda2(String uda2) {
		this.uda2 = uda2;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}

	
}
	
