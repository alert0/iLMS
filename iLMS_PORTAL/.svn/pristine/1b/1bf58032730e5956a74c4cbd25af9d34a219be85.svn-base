package com.hotent.system.worktime.manager;

import java.util.Date;
import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.system.worktime.model.CalendarAbsence;


public interface CalendarAbsenceManager  extends Manager<String, CalendarAbsence>{

	/**
	 *根据用户Id，
	 *开始时间，结束时间
	 *获取用户在某一段时间内的请假管理列表
	 */
	public List<CalendarAbsence> getListByUserId(String userId,String type,Date startTime,Date endTime);
	
	
}

