package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：周计划维护 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpWeekPlanModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1042793690561587942L;
	/**
     * 主表 周计划维护
     */
	private String id;	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	* 工厂
	*/
	private String factoryCode; 
	/**
	* 年
	*/
	private String year; 
	
	/**
	* 当年的第几周
	*/
	private Integer week; 
	
	/**
	* 起始日期
	*/
	private java.util.Date startDay; 
	
	/**
	* 起始日期
	*/
	private String startDayStr; 
	
	/**
	* 结束日期
	*/
	private java.util.Date endDay; 
	
	/**
	* 起始日期
	*/
	private String endDayStr; 
	
	/**
	* 周次获取
	*/
	private Integer day; 
	
	/**
	* 获取几周的数据
	*/
	private Integer weekNum; 
	
	/**
	* 创建人
	*/
	private String creationUser; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime;

	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public java.util.Date getStartDay() {
		return startDay;
	}
	public void setStartDay(java.util.Date startDay) {
		this.startDay = startDay;
	}
	public java.util.Date getEndDay() {
		return endDay;
	}
	public void setEndDay(java.util.Date endDay) {
		this.endDay = endDay;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getWeekNum() {
		return weekNum;
	}
	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
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
	public String getStartDayStr() {
		return startDayStr;
	}
	public void setStartDayStr(String startDayStr) {
		this.startDayStr = startDayStr;
	}
	public String getEndDayStr() {
		return endDayStr;
	}
	public void setEndDayStr(String endDayStr) {
		this.endDayStr = endDayStr;
	}
	
	
}