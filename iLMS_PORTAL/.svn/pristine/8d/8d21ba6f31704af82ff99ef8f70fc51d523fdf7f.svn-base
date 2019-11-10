package com.hanthink.base.model;

import java.io.Serializable;

/**
 * @Desc    : 数据表更新日志记录 表信息表字段VO 
 * @FileName: TableColumnVO.java 
 * @CreateOn: 2017-3-17 下午12:17:23
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2017-3-17	V1.0		zuosl		新建
 * 
 *
 */
public class TableColumnVO implements Serializable{

	private static final long serialVersionUID = -1913822024436940178L;
	
	/** 自定义字段类型-空(未定义)*/
	public static final int DF_DATA_TYPE_NULL = -1;
	/** 自定义字段类型-日期 */
	public static final int DF_DATA_TYPE_DATE = 1;
	/** 自定义字段类型-数字 */
	public static final int DF_DATA_TYPE_NUMBER = 2;
	/** 自定义字段类型-字符串 */
	public static final int DF_DATA_TYPE_STRING = 3;
	

	/** 字段名 */
	private String columnName;
	/** 数据库字段类型 */
	private String dbDataType;
	/** 自定义字段类型 */
	private int defineDataType = DF_DATA_TYPE_NULL;
	/** 字段长度 */
	private String dataLength;
	/** 字段值 */
	private String columnVal;
	/** 字段值(批量处理) */
	private String[] columnValArr;
	
	
	
	/**
	 * 根据数据库字段类型获取自定义字段类型
	 * @return
	 * @date 2017-3-17 
	 */
	public int getDefineDataTypeByDbType(){
		
		if(null != dbDataType && !"".equals(dbDataType.trim())){
			if(dbDataType.toUpperCase().startsWith("DATE") 
					|| dbDataType.toUpperCase().startsWith("TIMESTAMP") ){
				return DF_DATA_TYPE_DATE;
			}
			if(dbDataType.startsWith("NUMBER")){
				return DF_DATA_TYPE_NUMBER;
			}
			return DF_DATA_TYPE_STRING;
		}
		
		return DF_DATA_TYPE_NULL;
	}
	
	
	/**
	 * 获取字段名
	 * @return the columnName 字段名
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * 设置字段名
	 * @param columnName the columnName 字段名
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * 获取数据库字段类型
	 * @return the dbDataType 数据库字段类型
	 */
	public String getDbDataType() {
		return dbDataType;
	}
	/**
	 * 设置数据库字段类型
	 * @param dbDataType the dbDataType 数据库字段类型
	 */
	public void setDbDataType(String dbDataType) {
		this.dbDataType = dbDataType;
	}
	/**
	 * 获取自定义字段类型
	 * @return the defineDataType 自定义字段类型
	 */
	public int getDefineDataType() {
		return defineDataType;
	}
	/**
	 * 设置自定义字段类型
	 * @param defineDataType the defineDataType 自定义字段类型
	 */
	public void setDefineDataType(int defineDataType) {
		this.defineDataType = defineDataType;
	}
	/**
	 * 获取字段长度
	 * @return the dataLength 字段长度
	 */
	public String getDataLength() {
		return dataLength;
	}
	/**
	 * 设置字段长度
	 * @param dataLength the dataLength 字段长度
	 */
	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}
	/**
	 * 获取字段值
	 * @return the columnVal 字段值
	 */
	public String getColumnVal() {
		return columnVal;
	}
	/**
	 * 设置字段值
	 * @param columnVal the columnVal 字段值
	 */
	public void setColumnVal(String columnVal) {
		this.columnVal = columnVal;
	}


	/**
	 * 获取字段值(批量处理)
	 * @return the columnValArr 字段值(批量处理)
	 */
	public String[] getColumnValArr() {
		return columnValArr;
	}


	/**
	 * 设置字段值(批量处理)
	 * @param columnValArr the columnValArr 字段值(批量处理)
	 */
	public void setColumnValArr(String[] columnValArr) {
		this.columnValArr = columnValArr;
	}
	
}
