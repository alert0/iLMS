package com.hotent.system.worktime.manager;

import java.io.Serializable;
import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.system.worktime.model.Calendar;
import com.hotent.system.worktime.model.CalendarSettingEvent;


public interface CalendarManager  extends Manager<String, Calendar>{
	/**
	 * 取得默认的日历。
	 * @return
	 */
	public Calendar getDefaultCalendar();
	
	/**
	 * 设置默认日历
	 * @param id
	 */
	public void setNotDefaultCal(Long id);
	
	/**
	 * 保存日历设置情况。
	 * @param json 格式
	 *  var data={
	 *				id:calId,
	 *				name:name,
	 *				memo:memo,
	 *				year:year,
	 *				month:month,
	 *				days:[{day:day,type:type,worktimeid:worktimeid}]
	 *		};
	 * return id 返回日历的id，以供页面进行跳转
	 * @throws Exception
	 */
	public String saveCalendar(String json) throws Exception ;

	public void setDefaultCal(String id);
	
	/**
	 * 通过日历ID和年份获取日历设置事件
	 * @param calendarId 日历ID
	 * @param year 年份
	 * @return
	 */
	public List<CalendarSettingEvent> getCalendarSettingEvent(String calendarId, int year);
}

