package com.hotent.system.worktime.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.system.worktime.dao.CalendarDateTypeDao;
import com.hotent.system.worktime.model.CalendarDateType;

@Repository
public class CalendarDateTypeDaoImpl extends MyBatisDaoImpl<String, CalendarDateType> implements CalendarDateTypeDao{

    @Override
    public String getNamespace() {
        return CalendarDateType.class.getName();
    }

	/**
	 * 得到周日期范围的公休日
	 * @author hjx
	 * @version 创建时间：2014-2-26 下午5:52:53
	 * @return
	 */
	@Override
	public List<CalendarDateType> getPhByWeekList() {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("userId", userId);
		List<CalendarDateType> list = this.getByKey("getPhByWeekList", params);
		return list;
	}
	
	/**
	 * 得到年日期范围的法定假日
	 * @author hjx
	 * @version 创建时间：2014-2-26 下午5:52:53
	 * @return
	 */
	@Override
	public List<CalendarDateType> getLhByYearList() {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("userId", userId);
		List<CalendarDateType> list = this.getByKey("getLhByYearList", params);
		return list;
	}
	
	/**
	 * 得到年日期范围的公司假日
	 * @author hjx
	 * @version 创建时间：2014-2-26 下午5:52:53
	 * @return
	 */
	@Override
	public List<CalendarDateType> getChByYearList() {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("userId", userId);
		List<CalendarDateType> list = this.getByKey("getChByYearList", params);
		return list;
	}
	
}
