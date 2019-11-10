package com.hotent.system.worktime.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.system.worktime.dao.CalendarAssignDao;
import com.hotent.system.worktime.model.CalendarAssign;

@Repository
public class CalendarAssignDaoImpl extends MyBatisDaoImpl<String, CalendarAssign> implements CalendarAssignDao{

    @Override
    public String getNamespace() {
        return CalendarAssign.class.getName();
    }
	

	/**
	 * 根据分配类型和分配ID取得分配对象。
	 * @param assignType 1,用户,2,组织
	 * @param assignId	分配ID
	 * @return
	 */
	public CalendarAssign getByAssignId(int assignType,String assignId){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("assignType", assignType);
		map.put("assignId", assignId);
		CalendarAssign obj=this.getUnique("getByAssignId", map);
		return obj;
	}
	
	/**
	 * 根据日历id删除记录
	 * @param calId
	 */
	public void delByCalId(String[] calIds){
		for(String calId:calIds){
			Map<String,Object> calIdMap=new HashMap<String,Object>();
			calIdMap.put("canlendarId", calId);
			this.getByKey("delByCalId", calIdMap);
		}
	}
	
	/**
	 * 根据用户ID得到唯一条分配信息
	 * @param assignId
	 * @return
	 */
	public CalendarAssign getbyAssignId(String assignId){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("assignId", assignId);
		return this.getUnique("getbyAssign", map);
	}
}
