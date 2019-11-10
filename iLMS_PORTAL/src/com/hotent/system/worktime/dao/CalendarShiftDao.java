package com.hotent.system.worktime.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.system.worktime.model.CalendarShift;


public interface CalendarShiftDao  extends Dao<String, CalendarShift> {

	CalendarShift getDefaultCalendarShift();
	
	
	/**
	 * 设置默认
	 * @param id
	 */
	public void setNotDefaultShift();
}


