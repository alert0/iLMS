package com.hotent.system.worktime.model;

import java.util.Date;

import com.hotent.base.core.util.time.TimeUtil;

/**
 * 时间段 
 * @author lenovo
 *
 */
public class TimePeroid implements Comparable<TimePeroid>{
	
	//如何表示startDate 均不能为空
	protected Date startDate;   //开始时间
	protected Date endDate;     //结束时间
	protected long time;        //时长（ 毫秒）
    protected String PeroidType;//时间段类型，上班、请假、加班
    /**
     * 上班
     */
    public static final String PEROID_TYPE_SHIFT="shift";
    /**
     * 请假
     */
    public static final String PEROID_TYPE_ABSENCE="absence";
    /**
     * 加班
     */
    public static final String PEROID_TYPE_OVER="over";
	/**
	 * 构造函数
	 */
	public TimePeroid(){
		
	}
	/**
	 * 构造函数
	 * overTime类型转TimePart
	 * @param overTime
	 */
	public TimePeroid(Date strat_date,Date end_date,String peroid_type) {
	this.startDate=strat_date;
	this.endDate=end_date;
	this.time=end_date.getTime()-strat_date.getTime();
	this.PeroidType=peroid_type;
	}
	
	/**
	 * 构造函数
	 * overTime类型转TimePart
	 * @param overTime
	 */
	public  TimePeroid(OverTime overTime) {
		this.startDate=overTime.getStartTime();
		this.endDate=overTime.getEndTime();
		this.time=endDate.getTime()-startDate.getTime();
		this.PeroidType=PEROID_TYPE_OVER;
	}
	
	/**
	 * 构造函数
	 * CalendarAbsence类型转TimePart
	 * @param overTime
	 */
	public TimePeroid(CalendarAbsence absence) {
		this.startDate=absence.getStartTime();
		this.endDate=absence.getEndTime();
		this.time=endDate.getTime()-startDate.getTime();
		this.PeroidType=PEROID_TYPE_ABSENCE;
	}
	
	
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * 每次设置开始时间，都要计算时长
	 * @author hjx
	 * @version 创建时间：2014-2-24  下午2:26:57
	 * @param beginDate
	 */
	public void setStartDate(Date beginDate) {
		this.startDate = beginDate;
		this.time=this.endDate.getTime()-beginDate.getTime();
	}
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * 次设置结束时间，都要计算时长
	 * @author hjx
	 * @version 创建时间：2014-2-24  下午2:27:03
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		this.time=endDate.getTime()-this.getStartDate().getTime();
	}
	/**
	 * 计算时长 毫秒
	 * @author hjx
	 * @version 创建时间：2014-2-20  下午4:47:12
	 * @return
	 */
	public long getTime() {
		return endDate.getTime()-startDate.getTime();
	}
	public void setTime(Long time) {
		this.time = time;
	}

	
	/**
	 * 比较大小
	 * 这样就能使用Collections.sort(lists)排序
	 */
	@Override
	public int compareTo(TimePeroid timePeroid) {
		  if (null == timePeroid) return 1;  
	        else {  
	            return this.startDate.compareTo(timePeroid.startDate);  
	        }  
	}
	
	@Override
	public String toString(){
		return TimeUtil.getDateTimeString(this.getStartDate())
				+"~"
				+TimeUtil.getDateTimeString(this.getEndDate())
				+"["+this.PeroidType+"]"
				+"["+(long)(time/60000)+"]"
				;
		
	}
	

}
