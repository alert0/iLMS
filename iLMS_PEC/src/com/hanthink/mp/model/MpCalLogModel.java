package com.hanthink.mp.model;

import java.util.Date;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：零件分组  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpCalLogModel extends AbstractModel<String>{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6235290441807196670L;
	/**
	 * 主表  订单计算表
	 * @return
	 */
	/**
	* 工厂信息
	*/
	protected String factoryCode; 
	
	/**
	* 程序名
	*/
	protected Integer calType; 
	
	/**
	* 开始运行时间
	*/
	protected java.util.Date calStartTime; 
	
	/**
	* 开始运行时间
	*/
	protected String calStartTimeStr; 
	
	/**
	* 结束运行时间
	*/
	protected java.util.Date calEndTime; 
	
	/**
	* 结束运行时间
	*/
	protected String calEndTimeStr;
	
	/**
	* 创建人
	*/
	protected String creationUser;
	
	/**
	* 创建时间
	*/
	protected java.util.Date creationTime; 
	
	/**
	* 最后修改人
	*/
	protected String lastModifiedUser;
	
	/**
	* 最后修改时间
	*/
	protected java.util.Date lastModifiedTime; 
	
	/**
	* 运行状态
	*/
	protected Integer isLock;
	
	/**
	* 操作ID
	*/
	protected String uuid;

	/**
	 * 主表 订单计算表
	 */
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public Integer getCalType() {
		return calType;
	}

	public void setCalType(Integer calType) {
		this.calType = calType;
	}

	public java.util.Date getCalStartTime() {
		return calStartTime;
	}

	public void setCalStartTime(java.util.Date calStartTime) {
		this.calStartTime = calStartTime;
	}

	public String getCalStartTimeStr() {
		return calStartTimeStr;
	}

	public void setCalStartTimeStr(String calStartTimeStr) {
		this.calStartTimeStr = calStartTimeStr;
	}

	public java.util.Date getCalEndTime() {
		return calEndTime;
	}

	public void setCalEndTime(java.util.Date calEndTime) {
		this.calEndTime = calEndTime;
	}

	public String getCalEndTimeStr() {
		return calEndTimeStr;
	}

	public void setCalEndTimeStr(String calEndTimeStr) {
		this.calEndTimeStr = calEndTimeStr;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
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

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
}