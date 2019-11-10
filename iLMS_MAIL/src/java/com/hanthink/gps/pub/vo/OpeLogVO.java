package com.hanthink.gps.pub.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * 广汽乘用车系统模块-操作日志表VO
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 04/09 0.01  anMin    新建
 * 
 */
public class OpeLogVO implements Serializable {
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 9041326990203612092L;
	
	//操作日志id
	private int id;
	//操作时间
	private Date trxTime;
	//操作时间Str
	private String trxTimeStr;
	//操作时间开始
	private String trxTimeStartStr;
	//操作时间结束
	private String trxTimeEndStr;
	//操作用户名称
	private String userName;
	//被操作的表
	private String tableName;
	//触发方式
	private String fromName;
	//操作类型
	private String trxType;
	//列名 
	private String columns;
	//值
	private String oldValue;
	//登录 Ip
	private String loclIp;
	//主键名
	private String idColumn;
	public String getIdColumn() {
		return idColumn;
	}
	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}
	//主键值
	private String recordKey;
	//表类型(待定)
	private String tableType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTrxTime() {
		return trxTime;
	}
	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTrxType() {
		return trxType;
	}
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getLoclIp() {
		return loclIp;
	}
	public void setLoclIp(String loclIp) {
		this.loclIp = loclIp;
	}
	public String getRecordKey() {
		return recordKey;
	}
	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}
	public String getTableType() {
		return tableType;
	}
	public String getTrxTimeStartStr() {
		return trxTimeStartStr;
	}
	public void setTrxTimeStartStr(String trxTimeStartStr) {
		this.trxTimeStartStr = trxTimeStartStr;
	}
	public String getTrxTimeEndStr() {
		return trxTimeEndStr;
	}
	public void setTrxTimeEndStr(String trxTimeEndStr) {
		this.trxTimeEndStr = trxTimeEndStr;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getTrxTimeStr() {
		return trxTimeStr;
	}
	public void setTrxTimeStr(String trxTimeStr) {
		this.trxTimeStr = trxTimeStr;
	}

}