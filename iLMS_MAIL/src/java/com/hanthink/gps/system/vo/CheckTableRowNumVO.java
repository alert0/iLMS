/**
 * 
 */
package com.hanthink.gps.system.vo;

import java.io.Serializable;

/**
 * 描述：统计数据表记录行数 VO
 * @author chenyong
 * @date   2016-10-11
 */
public class CheckTableRowNumVO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String factory;    //工厂
	private String tableName;  //表名
	private String rowNums;     //行数
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getRowNums() {
		return rowNums;
	}
	public void setRowNums(String rowNums) {
		this.rowNums = rowNums;
	}
	
	
}
