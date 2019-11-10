package com.hotent.system.worktime.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.manager.api.Manager;
import com.hotent.system.worktime.model.CalendarDateType;

public interface CalendarDateTypeManager extends Manager<String, CalendarDateType>{

	Map<Integer, String> getLhByYearMon(String statTime, String endTime);
	
	//得到周日期范围的公休日
	Map<Integer, String> getPhByWeekMap();
	
	
}
