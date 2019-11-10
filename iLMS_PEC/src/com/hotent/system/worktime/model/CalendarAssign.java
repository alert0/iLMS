package com.hotent.system.worktime.model;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:日历分配 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-01-07 16:06:46
 */
public class CalendarAssign extends AbstractModel<String>{
	private static final long serialVersionUID = 1L;
	protected String     id;             /*主键*/
	protected String     calendarId;     /*日历ID*/
	protected String     assignType;     /*分配者类型*/
	protected String     assignId;       /*分配者ID*/
	// 日历名称
	protected String calendarName;
	// 分配人名称
	protected String assignUserName;
	/**
	 * 用户
	 */
	public static int TYPE_USER=1;
	
	/**
	 * 组织
	 */
	public static int TYPE_ORGANIZATION=2;
	
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getAssignUserName() {
		return assignUserName;
	}
	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
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
	public void setCanlendarId(String calendarId) 
	{
		this.calendarId = calendarId;
	}
	/**
	 * 返回 日历ID
	 * @return
	 */
	public String getCanlendarId() 
	{
		return this.calendarId;
	}
	public void setAssignType(String assignType) 
	{
		this.assignType = assignType;
	}
	/**
	 * 返回 分配者类型
	 * @return
	 */
	public String getAssignType() 
	{
		return this.assignType;
	}
	
	public void setAssignId(String assignId) 
	{
		this.assignId = assignId;
	}
	/**
	 * 返回 分配者ID
	 * @return
	 */
	public String getAssignId() 
	{
		return this.assignId;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("calendarId", this.calendarId) 
		.append("assignType", this.assignType) 
		.append("assignId", this.assignId) 
		.toString();
	}
}