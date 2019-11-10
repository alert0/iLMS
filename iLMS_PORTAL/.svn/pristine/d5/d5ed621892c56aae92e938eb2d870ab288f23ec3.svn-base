package com.hotent.system.worktime.calc.impl;


import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.hotent.system.worktime.calc.ICalendarCalc;
import com.hotent.system.worktime.manager.CalendarAbsenceManager;
import com.hotent.system.worktime.manager.CalendarShiftPeroidManager;
import com.hotent.system.worktime.model.TimePeroid;
import com.hotent.system.worktime.util.CalendarUtil;

public class ShiftCalc implements ICalendarCalc {
	@Resource
	CalendarShiftPeroidManager calendarShiftPeroidManager;

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "shift";
	}

	@Override
	public List<TimePeroid> getRealTimePeroidList(String userId, Date startTime,
			Date endTime) {
		List<TimePeroid> list=calendarShiftPeroidManager.getRealShiftPeroidList(userId,startTime,endTime);
		return list;
	}

	@Override
	public SortedMap<Date, TimePeroid> overrideCalendarShiftPeroidMap(
			SortedMap<Date, TimePeroid> calendarShiftPeroidMap,
			List<TimePeroid> shiftTimePeroidlist) {
		// TODO Auto-generated method stub
		return CalendarUtil.getTimePeroidMap(shiftTimePeroidlist);
	}

}
