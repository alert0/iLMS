package com.hanthink.base.model;

import java.io.Serializable;
/**
 * @Desc    : 表数据操作日志VO
 * @FileName: TableOpeLogVO.java 
 * @CreateOn: 2017-3-17 下午12:15:07
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2017-3-17	V1.0		zuosl		新建
 * 
 *
 */
public class TableOpeLogVO implements Serializable{

	private static final long serialVersionUID = -69839818706733787L;
	
	/** 操作类型-新增插入 */
	public static final String OPE_TYPE_INSERT = "I";
	/** 操作类型-修改 */
	public static final String OPE_TYPE_MODIFY = "M";
	/** 操作类型-删除 */
	public static final String OPE_TYPE_DELETE = "D";
	
	/** 表名 */
	private String tableName;
	/** 操作类型 */
	private String opeType;
	/** 触发源 */
	private String fromName;
	/** 操作IP */
	private String opeIp;
	/** 操作用户名 */
	private String opeUserName;
	
	
	/** 记录值查询sql */
	private String logValSql;
	/** 主键条件sql */
	private String pkConSql;
	/** 主键字段信息 */
	private String pkColumn;
	/** 主键值信息 */
	private String pkRecord;
	
	
	/**
	 * 获取表名
	 * @return the tableName 表名
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * 设置表名
	 * @param tableName the tableName 表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 获取操作类型
	 * @return the opeType 操作类型
	 */
	public String getOpeType() {
		return opeType;
	}
	/**
	 * 设置操作类型
	 * @param opeType the opeType 操作类型
	 */
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	/**
	 * 获取触发源
	 * @return the fromName 触发源
	 */
	public String getFromName() {
		return fromName;
	}
	/**
	 * 设置触发源
	 * @param fromName the fromName 触发源
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	/**
	 * 获取操作IP
	 * @return the opeIp 操作IP
	 */
	public String getOpeIp() {
		return opeIp;
	}
	/**
	 * 设置操作IP
	 * @param opeIp the opeIp 操作IP
	 */
	public void setOpeIp(String opeIp) {
		this.opeIp = opeIp;
	}
	/**
	 * 获取记录值查询sql
	 * @return the logValSql 记录值查询sql
	 */
	public String getLogValSql() {
		return logValSql;
	}
	/**
	 * 设置记录值查询sql
	 * @param logValSql the logValSql 记录值查询sql
	 */
	public void setLogValSql(String logValSql) {
		this.logValSql = logValSql;
	}
	/**
	 * 获取主键条件sql
	 * @return the pkConSql 主键条件sql
	 */
	public String getPkConSql() {
		return pkConSql;
	}
	/**
	 * 设置主键条件sql
	 * @param pkConSql the pkConSql 主键条件sql
	 */
	public void setPkConSql(String pkConSql) {
		this.pkConSql = pkConSql;
	}
	/**
	 * 获取操作用户名
	 * @return the opeUserName 操作用户名
	 */
	public String getOpeUserName() {
		return opeUserName;
	}
	/**
	 * 设置操作用户名
	 * @param opeUserName the opeUserName 操作用户名
	 */
	public void setOpeUserName(String opeUserName) {
		this.opeUserName = opeUserName;
	}
	/**
	 * 获取主键字段信息
	 * @return the pkColumn 主键字段信息
	 */
	public String getPkColumn() {
		return pkColumn;
	}
	/**
	 * 设置主键字段信息
	 * @param pkColumn the pkColumn 主键字段信息
	 */
	public void setPkColumn(String pkColumn) {
		this.pkColumn = pkColumn;
	}
	/**
	 * 获取主键值信息
	 * @return the pkRecord 主键值信息
	 */
	public String getPkRecord() {
		return pkRecord;
	}
	/**
	 * 设置主键值信息
	 * @param pkRecord the pkRecord 主键值信息
	 */
	public void setPkRecord(String pkRecord) {
		this.pkRecord = pkRecord;
	}
	
	

}
