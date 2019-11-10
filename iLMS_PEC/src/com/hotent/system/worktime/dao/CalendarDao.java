package com.hotent.system.worktime.dao;
import java.io.Serializable;
import com.hotent.base.db.api.Dao;
import com.hotent.system.worktime.model.Calendar;


public interface CalendarDao extends Dao<String, Calendar> {
	/**
	 * 取得默认的日历。
	 * @return
	 */
	public Calendar getDefaultCalendar();
	
	/**
	 * 设置默认日历
	 * @param id
	 */
	public void setNotDefaultCal();

}


