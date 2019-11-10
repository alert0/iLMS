package com.hanthink.pub.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：打印机配置 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubPrPrinterModel extends AbstractModel<String>{
	
	

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年11月12日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 3656567942520219366L;

	/**
     * 主表 打印机配置
     */
	private String id;	
	
	/**
	* 打印机
	*/
	private String printerName; 
	
	/**
	* 工厂
	*/
	private String factory; 
	
	/**
	* 描述
	*/
	private String description; 
	
	/**
	* 位置
	*/
	private String location; 
	
	/**
	* IP地址
	*/
	private String ip; 
	
	/**
	* 端口
	*/
	private String port; 
	
	/**
	* 供应商
	*/
	private String vendor; 
	
	/**
	* 驱动
	*/
	private String driver; 
	
	/**
	* 打印机类型
	*/
	private String type; 
	
	/**
	* 责任部门
	*/
	private String owner; 
	
	/**
	* 打印分组
	*/
	private String printerGroup; 
	
	/**
	* 是否启用
	*/
	private String active; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 创建时间
	*/
	private String creationTimeStr; 
	
	/**
	* 创建人
	*/
	private String creationUser; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime; 
	
	/**
	* 最后修改时间
	*/
	private String lastModifiedTimeStr; 
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser; 
	
	/**
	* 最后修改IP
	*/
	private String lastModifiedIp;
	
	/**
	 * 数据字典表
	 */
	/**
	* 数据值A
	*/
	private String codeValueNameA;
	
	/**
	* 数据值B
	*/
	private String codeValueNameB;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPrinterGroup() {
		return printerGroup;
	}

	public void setPrinterGroup(String printerGroup) {
		this.printerGroup = printerGroup;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreationTimeStr() {
		return creationTimeStr;
	}

	public void setCreationTimeStr(String creationTimeStr) {
		this.creationTimeStr = creationTimeStr;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getLastModifiedTimeStr() {
		return lastModifiedTimeStr;
	}

	public void setLastModifiedTimeStr(String lastModifiedTimeStr) {
		this.lastModifiedTimeStr = lastModifiedTimeStr;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getLastModifiedIp() {
		return lastModifiedIp;
	}

	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}

	public String getCodeValueNameA() {
		return codeValueNameA;
	}

	public void setCodeValueNameA(String codeValueNameA) {
		this.codeValueNameA = codeValueNameA;
	}

	public String getCodeValueNameB() {
		return codeValueNameB;
	}

	public void setCodeValueNameB(String codeValueNameB) {
		this.codeValueNameB = codeValueNameB;
	}

}