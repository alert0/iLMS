package com.hotent.system.worktime.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.time.DateUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.system.worktime.dao.CalendarDateTypeDao;
import com.hotent.system.worktime.model.CalendarDateType;
import com.hotent.system.worktime.manager.CalendarDateTypeManager;

@Service("calDateTypeManager")
public class CalendarDateTypeManagerImpl extends AbstractManagerImpl<String, CalendarDateType> implements CalendarDateTypeManager{
	@Resource
	CalendarDateTypeDao calendarDateTypeDao;
	@Override
	protected Dao<String, CalendarDateType> getDao() {
		return calendarDateTypeDao;
	}
	
	
	/**
	 * 根据年月取法定假日。
	 * map 对象存放天数 {1,"五一"}
	 * @return
	 */
	@Override
	public Map<Integer,String> getLhByYearMon(String statTime, String endTime){
		
		Map<Integer,String> map=new HashMap<Integer, String>();
		
		Date startDate=TimeUtil.convertString(statTime, "yyyy-MM-dd");
		Date endDate=TimeUtil.convertString(endTime, "yyyy-MM-dd");
		List<CalendarDateType> valist = calendarDateTypeDao.getLhByYearList();
		int curMonth = Integer.parseInt(statTime.split("-")[1]);
		for(CalendarDateType va:valist){
			if(va.getYearBegin().compareTo(startDate)>=0||va.getYearEnd().compareTo(endDate)<=0){
			Date[] days = DateUtil.getDaysBetween(va.getYearBegin(), va.getYearEnd());
			for(Date day:days){
				int tmpMonth = day.getMonth()+1;
				if(curMonth==tmpMonth){
					map.put(days.length,va.getName());
				}
			}
		}
		}
		return map;
	}


	@Override
	public Map<Integer, String> getPhByWeekMap() {
		Map<Integer, String> map =new HashMap<Integer, String>();
		List<CalendarDateType> phList=calendarDateTypeDao.getPhByWeekList();
		for(CalendarDateType calendarDateType :phList){
			map.put(calendarDateType.getWeekBegin().intValue(), calendarDateType.getName());
		}
		return map;
	}

}
