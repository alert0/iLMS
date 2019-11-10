package com.hotent.system.worktime.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.system.worktime.dao.OverTimeDao;
import com.hotent.system.worktime.model.OverTime;
import com.hotent.system.worktime.manager.OverTimeManager;

@Service("overTimeManager")
public class OverTimeManagerImpl extends AbstractManagerImpl<String, OverTime> implements OverTimeManager{
	@Resource
	OverTimeDao overTimeDao;
	@Override
	protected Dao<String, OverTime> getDao() {
		return overTimeDao;
	}
	
	/**
	 *  取得工作日类型
	 * @return
	 */
	public List<Map<String,String>> getWorkType()
	{
		List<Map<String,String>> typelist = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("typeId", "1");
		map.put("typeName", "加班");
		typelist.add(map);
		map = new HashMap<String,String>();
		map.put("typeId", "2");
		map.put("typeName", "请假");
		typelist.add(map);
		return typelist;
	}
	
	/**
	 * 取得一段时间内的加班/请假情况列表
	 * 此列表范围可能超出startTime~endTime
	 * @param userId 用户ID
	 * @param type   工作变更类型 1，加班 2，请假
	 * @param startTime  任务开始时间
	 * @param endTime    结束时间
	 * @return
	 */
	public List<OverTime> getListByUserId(String userId,int type,Date startTime,Date endTime){
		return overTimeDao.getListByUserId(userId, type, startTime, endTime);
	}
	
	/**
	 * 根据开始时间 和结束时间 得到实际加班时间段列表
	 * 此列表范围正好在 startTime~endTime 之间
	 * 分4种情况
	 * 1加班时间段落在startTime 上,修改overTime,加班时间段分成2段
	 * 2加班时间段落在endTime 上,修改overTime,加班时间段分成2段
	 * 3加班时间 包含了 startTime到endTime这一段,修改overTime
	 * 4加班时间段正好落在startTime 和 endTime之间,  不用处理
	 * 
	 * 请假不一样，请假不可能落在任务开始或结束时间点上
	 */
	
	public List<OverTime> getRealOverTimeList(String userId, Date start_time,
			Date end_time) {
		List<OverTime> overTimeList = this.getListByUserId(userId,
				1, start_time, end_time);
		for (OverTime over : overTimeList) {
			if (over.getStartTime().compareTo(start_time) < 0
					&& over.getEndTime().compareTo(start_time) > 0) {
				over.setStartTime(start_time);
			}
			if (over.getStartTime().compareTo(end_time) < 0
					&& over.getEndTime().compareTo(end_time) > 0) {
				over.setEndTime(end_time);
			}
			if (over.getStartTime().compareTo(start_time) < 0
					&& over.getEndTime().compareTo(end_time) > 0) {
				over.setStartTime(start_time);
				over.setEndTime(end_time);

			}
		}
		return overTimeList;
	}
	
	/**
	 * 取得一开始时间（有关的\之后）用户的加班/请假情况列表
	 * @param startTime  开始时间
	 * @param userId  用户ID
	 * @param type	工作变更类型 1，加班 2，请假
	 * @return
	 */
	public List<OverTime> getListByStart(Date startTime, String userId, int type) {
		return overTimeDao.getListByStart(startTime,userId,type);
	}
}
