package com.hotent.system.worktime.model;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:班次 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-02-18 10:45:58
 */
public class CalendarShift extends AbstractModel<String> implements Cloneable{
	private static final long serialVersionUID = 1L;
	protected String  id;            /*主键*/
	protected String  name;          /*班次名*/
	protected String  memo;          /*描述*/
	protected Long    minutes;       /*工时,精确到分钟*/
	protected String  isDefault="0"; /*是否默认班次,1是,0否*/

	protected List<CalendarShiftPeroid> calendarShiftPeroidList=new ArrayList<CalendarShiftPeroid>(); /*班次时间列表*/
	
	public List<CalendarShiftPeroid> getCalendarShiftPeroidList() {
		return calendarShiftPeroidList;
	}
	public void setCalendarShiftPeroidList(
			List<CalendarShiftPeroid> calendarShiftPeroidList) {
		this.calendarShiftPeroidList = calendarShiftPeroidList;
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
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * 返回 班次名
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setMemo(String memo) 
	{
		this.memo = memo;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getMemo() 
	{
		return this.memo;
	}
	public void setMinutes(Long minutes) 
	{
		this.minutes = minutes;
	}
	/**
	 * 返回 工时,精确到分钟
	 * @return
	 */
	public Long getMinutes() 
	{
		return this.minutes;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("memo", this.memo) 
		.append("minutes", this.minutes) 
		.toString();
	}
}