package com.hotent.system.worktime.model;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.time.TimeUtil;

/**
 * 对象功能:班次时间 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-02-18 10:45:58
 */
public class CalendarShiftPeroid extends AbstractModel<String>implements Cloneable{
	private static final long serialVersionUID = 1L;
	protected String  id;        /*主键*/
	protected String  shiftId;   /*班次ID*/
	protected String  startTime; /*开始时间*/
	protected String  endTime;   /*结束时间*/
	protected String  memo;      /*备注*/
	
	//日期
	protected String calDay  ="";
	//开始时间
	@Deprecated
	protected Date startDateTime;
	//结束时间
	@Deprecated
	protected Date endDateTime;
	
	public String getCalDay() {
		return calDay;
	}
	public void setCalDay(String calDay) {
		this.calDay = calDay;
		String start=calDay +" " + this.startTime +":00";
		String end=calDay +" " + this.endTime+":00";
		
		this.startDateTime=TimeUtil.getDateTimeByTimeString(start);
		this.endDateTime=TimeUtil.getDateTimeByTimeString(end);
		if(this.startDateTime.compareTo(this.endDateTime)>0){
			this.endDateTime=TimeUtil.getNextDays(this.endDateTime,1);
		}
	}
	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Date getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
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
	public void setStartTime(String startTime) 
	{
		this.startTime = startTime;
	}
	/**
	 * 返回 开始时间
	 * @return
	 */
	public String getStartTime() 
	{
		return this.startTime;
	}
	public void setEndTime(String endTime) 
	{
		this.endTime = endTime;
	}
	/**
	 * 返回 结束时间
	 * @return
	 */
	public String getEndTime() 
	{
		return this.endTime;
	}
	public void setMemo(String memo) 
	{
		this.memo = memo;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getMemo() 
	{
		return this.memo;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("shiftId", this.shiftId) 
		.append("startTime", this.startTime) 
		.append("endTime", this.endTime) 
		.append("memo", this.memo) 
		.toString();
	}
	
	public Object clone()
	{
		CalendarShiftPeroid obj=null;
		try{
			obj=(CalendarShiftPeroid)super.clone();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}