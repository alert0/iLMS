package com.hotent.system.worktime.manager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.system.worktime.model.CalendarShiftPeroid;
import com.hotent.system.worktime.model.TimePeroid;


public interface CalendarShiftPeroidManager  extends Manager<String, CalendarShiftPeroid>{

	List<TimePeroid> getRealShiftPeroidList(String userId, Date start_time,
			Date end_time);

	List<CalendarShiftPeroid> getByShiftId(String shiftId);

	void shiftPeroidAdd(String shiftId, String[] startTime, String[] endTime,
			String[] memo);
	
}

