package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：调整计划  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpAdjPlanModel extends AbstractModel<String>{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4520894086360574806L;
	/**
	 * 主表  调整计划表
	 * @return
	 */
	/**
	* 逻辑主键id
	*/
	protected String id; 
	
	/**
	* 计划ID
	*/
	protected String pkid; 
	
	/**
	* 工厂信息
	*/
	protected String factoryCode; 
	
	/**
	* 车型
	*/
	protected String carType; 
	
	/**
	* 数量
	*/
	protected String diffNum; 
	
	/**
	* 调整日期
	*/
	protected java.util.Date adjDate; 
	
	/**
	* 调整日期
	*/
	protected String adjDateStr; 
	
	/**
	* 调整日期(开始）
	*/
	protected String adjDateStrStart; 
	
	/**
	* 调整日期（结束）
	*/
	protected String adjDateStrEnd; 
	
	/**
	* 创建人
	*/
	protected String creationUser; 
	
	/**
	* 创建时间
	*/
	protected String creationTime; 
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser;
	
	/**
	* 最后修改时间
	*/
	private java.util.Date  lastModifiedTime;

	/**
	 * 主表 调整计划
	 */
	
	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}
	
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getDiffNum() {
		return diffNum;
	}

	public void setDiffNum(String diffNum) {
		this.diffNum = diffNum;
	}

	public java.util.Date getAdjDate() {
		return adjDate;
	}

	public void setAdjDate(java.util.Date adjDate) {
		this.adjDate = adjDate;
	}

	public String getAdjDateStr() {
		return adjDateStr;
	}

	public void setAdjDateStr(String adjDateStr) {
		this.adjDateStr = adjDateStr;
	}
	
	public String getAdjDateStrStart() {
		return adjDateStrStart;
	}

	public void setAdjDateStrStart(String adjDateStrStart) {
		this.adjDateStrStart = adjDateStrStart;
	}

	public String getAdjDateStrEnd() {
		return adjDateStrEnd;
	}

	public void setAdjDateStrEnd(String adjDateStrEnd) {
		this.adjDateStrEnd = adjDateStrEnd;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
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

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}
	
}