package com.hotent.system.worktime.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.system.worktime.dao.CalendarShiftPeroidDao;
import com.hotent.system.worktime.model.CalendarShiftPeroid;


@Repository
public class CalendarShiftPeroidDaoImpl extends MyBatisDaoImpl<String,CalendarShiftPeroid> implements CalendarShiftPeroidDao{

    @Override
    public String getNamespace() {
        return CalendarShiftPeroid.class.getName();
    }
	
    /**
	 * 根据外键获取子表明细列表
	 * @param settingid
	 * @return
	 */
	public List<CalendarShiftPeroid> getByMainId(String shiftId) {
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("shiftId", shiftId);
		return this.getByKey("getCalendarShiftPeroidList", params);
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param settingid
	 * @return
	 */
	public void delByMainId(String shiftId) {
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("shiftId", shiftId);
		this.deleteByKey("delByMainId", params);
	}
	
	public List<CalendarShiftPeroid> getByShiftId(String shiftId){
		return this.getByMainId(shiftId);
	}
}

