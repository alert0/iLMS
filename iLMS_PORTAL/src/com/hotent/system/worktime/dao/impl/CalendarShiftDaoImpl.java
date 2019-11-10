package com.hotent.system.worktime.dao.impl;

import java.io.Serializable;
import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.system.worktime.dao.CalendarShiftDao;
import com.hotent.system.worktime.model.CalendarShift;


@Repository
public class CalendarShiftDaoImpl extends MyBatisDaoImpl<String,CalendarShift> implements CalendarShiftDao{

    @Override
    public String getNamespace() {
        return CalendarShift.class.getName();
    }
    
    /**
     * 获取cal_shit表的默认班次的id，
     * 初始化数据的时候，默认班次数据sql脚本insert到表里,
     * 亦可以由用户见面设定
     * @author hjx
     * @version 创建时间：2014-2-26  下午5:20:04
     * @return
     */
    @Override
    public CalendarShift getDefaultCalendarShift(){
    	return this.getUnique("getUniqueDefaultShift", null);
    }

	@Override
	public void setNotDefaultShift() {
		this.updateByKey("setNotDefaultShift");
		
	}
}

