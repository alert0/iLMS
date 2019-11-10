package com.hotent.system.worktime.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.system.worktime.dao.OverTimeDao;
import com.hotent.system.worktime.model.OverTime;

@Repository
public class OverTimeDaoImpl extends MyBatisDaoImpl<String, OverTime> implements OverTimeDao{

    @Override
    public String getNamespace() {
        return OverTime.class.getName();
    }
	
    
    /**
	 * 根据用户Id，工作类型(1加班,2请假)，
	 * 开始时间，结束时间
	 * 获取用户在某一段时间内的加班/请假管理列表
	 * @param userId
	 * @param workType
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @Override
	public List<OverTime> getListByUserId(String userId,int type,Date startTime,Date endTime) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("workType", type);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return this.getByKey("getListByUserId", params);
	}
    
    @Override
	public List<OverTime> getListByStart(Date startTime, String userId, int type) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("userId", userId);
		params.put("workType", type);
		
		return this.getByKey("getListByStart", params);
	}
}
