package com.hanthink.gps.pub.vo;

import java.io.Serializable;
/**
 * 
 * 广汽乘用车系统模块-操作表VO
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 04/09 0.01  anMin    新建
 * 
 */
public class OpeTableVO implements Serializable {
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 9041326990203612092L;
	
	
	//表名
	private String Opetable;
	//字段名
	private String ColumnName;
	//字段类型
	private String DataType;
	
	public String getColumnName() {
		return ColumnName;
	}
	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}
	
	public String getDataType() {
		return DataType;
	}
	public void setDataType(String dataType) {
		DataType = dataType;
	}
	public String getOpetable() {
		return Opetable;
	}
	public void setOpetable(String opetable) {
		Opetable = opetable;
	}


}