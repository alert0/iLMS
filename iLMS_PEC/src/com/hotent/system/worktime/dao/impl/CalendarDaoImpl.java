package com.hotent.system.worktime.dao.impl;

import java.io.Serializable;
import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.system.worktime.dao.CalendarDao;
import com.hotent.system.worktime.model.Calendar;


@Repository
public class CalendarDaoImpl extends MyBatisDaoImpl<String,Calendar> implements CalendarDao{

    @Override
    public String getNamespace() {
        return Calendar.class.getName();
    }
	
    /**
	 * 取得默认的日历。
	 * @return
	 */
	public Calendar getDefaultCalendar(){
		return this.getUnique("getDefaultCalendar", null);
	}
	
	/**
	 * 设置默认日历
	 * @param id
	 */
	public void setNotDefaultCal(){
		this.updateByKey("setNotDefaultCal");
	}

}

