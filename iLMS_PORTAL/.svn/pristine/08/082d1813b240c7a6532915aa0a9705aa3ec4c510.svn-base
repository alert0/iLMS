package com.hotent.system.worktime.dao;
import java.util.Date;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.system.worktime.model.CalendarSetting;


public interface CalendarSettingDao extends Dao<String, CalendarSetting> {
	/**
	 * 根据日历ID取得日历列表。
	 * @param calendarId
	 * @return
	 */
	public List<CalendarSetting> getByCalendarId(String calendarId,Date startTime);

	/**
	 * 根据日历和开始结束时间取得时间段。
	 * @param calendarId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<CalendarSetting> getSegmentByCalId(String calendarId,Date startDate ,Date endDate);

	/**
	 * 根据日历id，year，month 得到日历
	 * @param id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<CalendarSetting> getCalByIdYearMon(String id, int year, int month);
	
	/**
	 * 根据日历id，year 得到日历
	 * @param id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<CalendarSetting> getCalByIdYear(String id, int year);

	/**
	 * 根据 日历id，year，month 删除日历
	 * @param calid
	 * @param year
	 * @param month
	 */
	public void delByCalidYearMon(String calid, short year, short month);
	
	/**
	 * 根据日历ID删除所有日历设置
	 * @param calendarId
	 */
	public void delByCalendarId(String calendarId);

	/**
	 * 根据日历id删除记录
	 * @param calId
	 */
	public void delByCalId(String[] calIds);
}
