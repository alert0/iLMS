package com.hanthink.pub.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：打印机任务配置 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubPrJobModel extends AbstractModel<String>{
	
	

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年11月12日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 3656567942520219366L;

	/**
     * 主表 打印机任务配置
     */
	private String id;	
	
	/**
	* 任务名称
	*/
	private String jobName; 
	
	/**
	* 描述
	*/
	private String description; 
	
	/**
	* 打印类型
	*/
	private String printType; 
	
	/**
	* 业务类型
	*/
	private String jobType; 
	
	/**
	* 打印份数
	*/
	private Integer copies; 
	
	/**
	* 打印方式
	*/
	private String classes; 
	
	/**
	* 打印机
	*/
	private String printerName; 
	
	/**
	* 更新SQL
	*/
	private String updateSql; 
	
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
	* 最后IP
	*/
	private String lastModifiedIp; 
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser;
	
	/**
	* 工厂
	*/
	private String factoryCode;
	
	/**
	* 数据字典表
	*/
	/**
	* 数据值
	*/
	private String codeValueName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
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

	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	} 
	
	
}