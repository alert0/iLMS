package com.hotent.system.worktime.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.system.worktime.model.CalendarAssign;


public interface CalendarAssignDao extends Dao<String, CalendarAssign> {

	/**
	 * 根据分配类型和分配ID取得分配对象。
	 * @param assignType 1,用户,2,组织
	 * @param assignId	分配ID
	 * @return
	 */
	public CalendarAssign getByAssignId(int assignType,String assignId);
	
	/**
	 * 根据日历id删除记录
	 * @param calId
	 */
	public void delByCalId(String[] calIds);
	
	/**
	 * 根据用户ID得到唯一条分配信息
	 * @param assignId
	 * @return
	 */
	public CalendarAssign getbyAssignId(String assignId);
}
