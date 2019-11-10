package com.hotent.system.worktime.model;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:日历设置 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-01-07 16:06:46
 */
public class CalendarSetting extends AbstractModel<String>{
	private static final long serialVersionUID = 1L;
	protected String  id;          /*主键*/
	protected String  calendarId;  /*日历ID*/
	protected Long   years;       /*年份*/
	protected Long   months;      /*月份*/
	protected Long   days;        /*天数*/
	protected String  dateType;     /*上班类型*/
	protected String  shiftId;     /*班次ID*/
	protected String  calDay;      /*CALDAY*/
	
	protected String wtName=""; /*班次名称*/

	/**
	 *DateType1上班 
	 */
	public static final String SETTING_DATETYPE_WORK="1";
	/**
	 * DateType 2休假
	 */
	public static final String SETTING_DATETYPE_HOLIDAY="2";
	
	//是否法定假日
	@Deprecated
	protected boolean isLegal=false;

	
	
	/**
	 * 班次时间列表
	 */
	protected List<CalendarShiftPeroid> calendarShiftPeroidList;
	
	public List<CalendarShiftPeroid> getCalendarShiftPeroidList() {
		return calendarShiftPeroidList;
	}
	public void setCalendarShiftPeroidList(
			List<CalendarShiftPeroid> calendarShiftPeroidList) {
		this.calendarShiftPeroidList = calendarShiftPeroidList;
	}
	
	public boolean isLegal() {
		return isLegal;
	}
	public void setLegal(boolean isLegal) {
		this.isLegal = isLegal;
	}
	public String getWtName() {
		return wtName;
	}
	public void setWtName(String wtName) {
		this.wtName = wtName;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setCalendarId(String calendarId) 
	{
		this.calendarId = calendarId;
	}
	/**
	 * 返回 日历ID
	 * @return
	 */
	public String getCalendarId() 
	{
		return this.calendarId;
	}
	public void setYears(Long years) 
	{
		this.years = years;
	}
	/**
	 * 返回 年份
	 * @return
	 */
	public Long getYears() 
	{
		return this.years;
	}
	public void setMonths(Long months) 
	{
		this.months = months;
	}
	/**
	 * 返回 月份
	 * @return
	 */
	public Long getMonths() 
	{
		return this.months;
	}
	public void setDays(Long days) 
	{
		this.days = days;
	}
	/**
	 * 返回 天数
	 * @return
	 */
	public Long getDays() 
	{
		return this.days;
	}
	public void setDateType(String dateType) 
	{
		this.dateType = dateType;
	}
	/**
	 * 返回 上班类型
	 * @return
	 */
	public String getDateType() 
	{
		return this.dateType;
	}
	public void setShiftId(String shiftId) 
	{
		this.shiftId = shiftId;
	}
	/**
	 * 返回 班次ID
	 * @return
	 */
	public String getShiftId() 
	{
		return this.shiftId;
	}
	public void setCalDay(String calDay) 
	{
		this.calDay = calDay;
	}
	/**
	 * 返回 CALDAY
	 * @return
	 */
	public String getCalDay() 
	{
		return this.calDay;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("calendarId", this.calendarId) 
		.append("years", this.years) 
		.append("months", this.months) 
		.append("days", this.days) 
		.append("dateType", this.dateType) 
		.append("shiftId", this.shiftId) 
		.append("calDay", this.calDay) 
		.toString();
	}
}