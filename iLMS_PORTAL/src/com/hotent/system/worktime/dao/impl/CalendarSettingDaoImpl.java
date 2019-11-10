package com.hotent.system.worktime.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.system.worktime.dao.CalendarSettingDao;
import com.hotent.system.worktime.model.CalendarSetting;

@Repository
public class CalendarSettingDaoImpl extends MyBatisDaoImpl<String, CalendarSetting> implements CalendarSettingDao{

	@Override
	public String getNamespace() {
		return CalendarSetting.class.getName();
	}

	/**
	 * 根据日历ID取得日历列表。
	 * @param calendarId
	 * @return
	 */
	public List<CalendarSetting> getByCalendarId(String calendarId,Date startTime){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("calendarId", calendarId);
		List<CalendarSetting> list=this.getByKey("getByCalendarId", map);
		return list;
	}

	/**
	 * 根据日历和开始结束时间取得时间段。
	 * @param calendarId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<CalendarSetting> getSegmentByCalId(String calendarId,Date startDate ,Date endDate){
		Map<String,Object> map=new HashMap<String,Object>();
		if(startDate!=null)map.put("startDate", TimeUtil.getDateString(startDate));//只要年月日，有可能上班时间段落在开始日期上，
		if(endDate!=null)map.put("endDate", TimeUtil.getDateString(endDate));
		map.put("calendarId", calendarId);
		List<CalendarSetting> list=this.getByKey("getSegmentByCalId", map);
		return list;
	}

	/**
	 * 根据日历id，year，month 得到日历
	 * @param id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<CalendarSetting> getCalByIdYearMon(String id, int year, int month){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("year", year);
		map.put("month", month);
		return this.getByKey("getCalByIdYearMon", map);
	}

	@Override
	public List<CalendarSetting> getCalByIdYear(String id, int year) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("year", year);
		return this.getByKey("getCalByIdYear", map);
	}

	/**
	 * 根据 日历id，year，month 删除日历
	 * @param calid
	 * @param year
	 * @param month
	 */
	public void delByCalidYearMon(String calid, short year, short month){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", calid);
		map.put("year", year);
		map.put("month", month);
		this.deleteByKey("delByCalidYearMon", map);
	}

	/**
	 * 根据日历id删除记录
	 * @param calId
	 */
	public void delByCalId(String[] calIds){
		for(String calId:calIds){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("calendarId", calId);
			this.deleteByKey("delByCalId", map);
		}
	}

	@Override
	public void delByCalendarId(String calendarId) {
		this.deleteByKey("delByCalendarId", calendarId);
	}
}
