package com.hotent.system.worktime.manager.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.system.worktime.dao.CalendarAbsenceDao;
import com.hotent.system.worktime.manager.CalendarAbsenceManager;
import com.hotent.system.worktime.model.CalendarAbsence;

@Service("calendarAbsenceManager")
public class CalendarAbsenceManagerImpl extends AbstractManagerImpl<String, CalendarAbsence> implements CalendarAbsenceManager{
	@Resource
	CalendarAbsenceDao calendarAbsenceDao;
	@Override
	protected Dao<String, CalendarAbsence> getDao() {
		return calendarAbsenceDao;
	}
	/**
	 *根据用户Id，
	 *开始时间，结束时间
	 *获取用户在某一段时间内的请假管理列表
	 */
	@Override
	public List<CalendarAbsence> getListByUserId(String userId, String type,
			Date startTime, Date endTime) {
		return calendarAbsenceDao.getListByUserId(userId, type, startTime, endTime);
	}
}
