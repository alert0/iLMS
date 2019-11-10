package com.hotent.system.worktime.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hotent.base.manager.api.Manager;
import com.hotent.system.worktime.model.OverTime;

public interface OverTimeManager extends Manager<String, OverTime>{
	
	/**
	 *  取得工作日类型
	 * @return
	 */
	public List<Map<String,String>> getWorkType();
	
	/**
	 * 取得一段时间内的加班/请假情况列表
	 * @param userId 用户ID
	 * @param type   工作变更类型 1，加班 2，请假
	 * @param startTime  任务开始时间
	 * @param endTime    结束时间
	 * @return
	 */
	public List<OverTime> getListByUserId(String userId,int type,Date startTime,Date endTime);
	
	/**
	 * 取得一开始时间（有关的\之后）用户的加班/请假情况列表
	 * @param startTime  开始时间
	 * @param userId  用户ID
	 * @param type	工作变更类型 1，加班 2，请假
	 * @return
	 */
	public List<OverTime> getListByStart(Date startTime, String userId, int type);
	
	/**
	 * 
	 * @author hjx
	 * @version 创建时间：2014-2-24  上午12:12:49
	 * @param userId
	 * @param start_time
	 * @param end_time
	 * @return
	 */
	public List<OverTime> getRealOverTimeList(String userId, Date start_time,
			Date end_time) ;
}
