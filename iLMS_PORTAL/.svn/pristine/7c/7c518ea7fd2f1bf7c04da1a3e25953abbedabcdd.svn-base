package com.hotent.system.worktime.dao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.system.worktime.model.CalendarAbsence;


public interface CalendarAbsenceDao  extends Dao<String, CalendarAbsence> {

	List<CalendarAbsence> getListByUserId(String userId, String type,
			Date startTime, Date endTime);
}


