/**
 * 
 */
package com.hanthink.gps.system.vo;

import java.io.Serializable;

/**
 * 描述：数据库表阻塞异常邮件提醒信息VO
 * @author chenyong
 * @date   2016-10-10
 */
public class DataBaseBlockErrorVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String factory;       //工厂
    private String username;      //用户名
    private String lockwait;      //锁等待
    private String status;        //状态
    private String machine;       //机器
    private String program;       //项目
    private String vsession;      //传入参数一
    private String vlock;         //传入参数二
    private String sqltext;         //传入参数二
    
    private String tableName;     //表名
    private String sql;           //sql语句
    
	public String getVsession() {
		return vsession;
	}
	public void setVsession(String vsession) {
		this.vsession = vsession;
	}
	public String getVlock() {
		return vlock;
	}
	public void setVlock(String vlock) {
		this.vlock = vlock;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLockwait() {
		return lockwait;
	}
	public void setLockwait(String lockwait) {
		this.lockwait = lockwait;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
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
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getSqltext() {
		return sqltext;
	}
	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}
    
	
}
