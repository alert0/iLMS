package com.hotent.system.worktime.dao;
import java.util.Date;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.system.worktime.model.OverTime;


public interface OverTimeDao extends Dao<String, OverTime> {
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
	public List<OverTime> getListByUserId(String userId,int type,Date startTime,Date endTime);
	public List<OverTime> getListByStart(Date startTime, String userId, int type) ;
}
