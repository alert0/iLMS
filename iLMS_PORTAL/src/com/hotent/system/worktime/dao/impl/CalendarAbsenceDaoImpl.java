package com.hotent.system.worktime.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.system.worktime.dao.CalendarAbsenceDao;
import com.hotent.system.worktime.model.CalendarAbsence;
import com.hotent.system.worktime.model.OverTime;


@Repository
public class CalendarAbsenceDaoImpl extends MyBatisDaoImpl<String,CalendarAbsence> implements CalendarAbsenceDao{

    @Override
    public String getNamespace() {
        return CalendarAbsence.class.getName();
    }
    /**
	 * 根据用户Id，
	 * 开始时间，结束时间
	 * 获取用户在某一段时间内的请假管理列表
	 * 任务开始时间、结束时间肯定落在上班时段上，
	 * 即使开始、结束时间落在休假时段上，那这一段休假也不用计算到列表中
	 * @param userId
	 * @param workType
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @Override
	public List<CalendarAbsence> getListByUserId(String userId,String type,Date startTime,Date endTime) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("catKey", type);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<CalendarAbsence> list  =this.getByKey("getListByUserId", params);
		return list;
	}
}

