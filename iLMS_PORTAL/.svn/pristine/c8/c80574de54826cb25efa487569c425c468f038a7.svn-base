package com.hotent.system.worktime.dao;
import java.io.Serializable;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.system.worktime.model.CalendarShiftPeroid;


public interface CalendarShiftPeroidDao  extends Dao<String, CalendarShiftPeroid> {

	
	/**
	 * 根据外键获取子表明细列表
	 * @param settingid
	 * @return
	 */
	public List<CalendarShiftPeroid> getByMainId(String shiftId);
	
	/**
	 * 根据外键删除子表记录
	 * @param settingid
	 * @return
	 */
	public void delByMainId(String settingid);

	public List<CalendarShiftPeroid> getByShiftId(String shiftId);

}


