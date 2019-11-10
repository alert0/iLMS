package com.hotent.system.worktime.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.sys.api.calendar.ICalendarService;
import com.hotent.system.worktime.calc.ICalendarCalc;
import com.hotent.system.worktime.manager.CalendarAssignManager;
import com.hotent.system.worktime.model.TimePeroid;
import com.hotent.system.worktime.util.CalendarUtil;

@Service
public class CalendarServiceImpl implements ICalendarService {
	@Resource
	CalendarAssignManager calendarAssignManager;
	
    @Resource
    SortedMap<String, ICalendarCalc> calendarCalcMap;
	
	@Override
	public Date getEndTimeByUser(String userId, long time) {
		Date startTime=Calendar.getInstance().getTime();
		return this.getEndTimeByUser(userId,startTime, time);
	}
	/**
	 * 根据时长 和开始时间，计算任务完成时间
	 * time  分钟
	 */
	@Override
	public Date getEndTimeByUser(String userId, Date startTime, long time) {
		if(BeanUtils.isEmpty(startTime)) return null;
		//暂定一个结束时间为开始后的120天，不够再加120天
		Date date=null;
		Date endTime=startTime;
		Date finalTime=TimeUtil.getNextDays(startTime, 365*10);
		//一直循环，最多循环到任务开始时间往后推10年
		while (date == null && endTime.compareTo(finalTime)<0) {
		    endTime =TimeUtil.getNextDays(endTime, 120);
			SortedMap<Date, TimePeroid> timePeroidMap = new TreeMap<Date, TimePeroid>();
			// map遍历
			Iterator<Entry<String, ICalendarCalc>> iter = calendarCalcMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, ICalendarCalc> entry = (Map.Entry<String, ICalendarCalc>) iter.next();
				ICalendarCalc calc = (ICalendarCalc) entry.getValue();
				List<TimePeroid> timePeroid = calc.getRealTimePeroidList(userId, startTime, endTime);
				timePeroidMap = calc.overrideCalendarShiftPeroidMap(timePeroidMap, timePeroid);
			}
			// 根据时长找到结束时间
			date = CalendarUtil.getEndTime(timePeroidMap, time);
		}
		 return date;

	}
	
  /**
   * 根据用户开始时间结束时间获取有效的工时
   */
	@Override
	public Long getWorkTimeByUser(String userId, Date startTime, Date endTime) {
		if(endTime.compareTo(startTime) <= 0)return 0L;
		SortedMap<Date,TimePeroid> timePeroidMap=new TreeMap<Date,TimePeroid>();
		// map遍历
		Iterator<Entry<String, ICalendarCalc>> iter = calendarCalcMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, ICalendarCalc> entry = (Map.Entry<String, ICalendarCalc>) iter.next();
			ICalendarCalc calc =(ICalendarCalc) entry.getValue();
			List<TimePeroid> timePeroid=calc.getRealTimePeroidList(userId, startTime, endTime);
			timePeroidMap=calc.overrideCalendarShiftPeroidMap(timePeroidMap,timePeroid);
		}
		return CalendarUtil.getCountTimePeroid(timePeroidMap);
	}
}
