package com.hanthink.pub.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：打印任务与业务关系配置 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubPrJobBusinessModel extends AbstractModel<String>{
	
	

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年11月12日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 3656567942520219366L;

	/**
     * 主表 打印任务与业务关系配置
     */
	private String id;	
	
	/**
	* 业务名称
	*/
	private String business; 
	
	/**
	* 打印任务名
	*/
	private String jobName; 
	
	/**
	* 描述
	*/
	private String description; 
	
	/**
	* 打印间隔
	*/
	private Integer printInterval; 
	
	/**
	* 是否启用
	*/
	private String active; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 创建人
	*/
	private String creationUser; 
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime; 
	
	/**
	* 最后修改IP
	*/
	private String lastModifiedIp; 
	
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

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
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

	public Integer getPrintInterval() {
		return printInterval;
	}

	public void setPrintInterval(Integer printInterval) {
		this.printInterval = printInterval;
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

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
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