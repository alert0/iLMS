package com.hotent.system.worktime.manager.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.system.worktime.dao.CalendarShiftDao;
import com.hotent.system.worktime.dao.CalendarShiftPeroidDao;
import com.hotent.system.worktime.manager.CalendarSettingManager;
import com.hotent.system.worktime.manager.CalendarShiftManager;
import com.hotent.system.worktime.model.Calendar;
import com.hotent.system.worktime.model.CalendarSetting;
import com.hotent.system.worktime.model.CalendarShift;
import com.hotent.system.worktime.model.CalendarShiftPeroid;


@Service("calendarShiftManager")
public class CalendarShiftManagerImpl extends AbstractManagerImpl<String, CalendarShift> implements CalendarShiftManager{
	@Resource
	CalendarShiftDao calendarShiftDao;
	@Resource
	CalendarShiftPeroidDao calendarShiftPeroidDao;
	@Resource
	CalendarSettingManager  calendarSettingManager;
	@Override
	protected Dao<String, CalendarShift> getDao() {
		return calendarShiftDao;
	}
	
	/**
	 * 创建实体包含子表实体
	 */
	public void create(CalendarShift calendarShift){
    	super.create(calendarShift);
    	String shiftId = calendarShift.getId();
    	calendarShiftPeroidDao.delByMainId(shiftId);
    	List<CalendarShiftPeroid> workTimeList=calendarShift.getCalendarShiftPeroidList();
    	for(CalendarShiftPeroid CalendarShiftPeroid:workTimeList){
    		CalendarShiftPeroid.setShiftId(shiftId);
    		calendarShiftPeroidDao.create(CalendarShiftPeroid);
    	}
    }
	
	/**
	 * 删除记录包含子表记录
	 */
	public void remove(String entityId){
		DefaultQueryFilter queryFilter=new  DefaultQueryFilter();
		Page page = new DefaultPage(10000);
		queryFilter.setPage(page);
		queryFilter.addFilter("SHIFT_ID_", entityId, QueryOP.EQUAL);
		List<CalendarSetting> list =calendarSettingManager.query(queryFilter);
		for (CalendarSetting calendarSetting : list) {
			calendarSettingManager.remove(calendarSetting.getId());
		}
		super.remove(entityId);
    	calendarShiftPeroidDao.delByMainId(entityId);
		
	}
	
	/**
	 * 批量删除包含子表记录
	 */
	public void removeByIds(String[] entityIds){
		for(String id:entityIds){
			this.remove(id);
		}
	}
    
	/**
	 * 获取实体
	 */
    public CalendarShift get(String entityId){
    	CalendarShift CalendarShift=super.get(entityId);
    	List<CalendarShiftPeroid> calendarShiftPeroidList=calendarShiftPeroidDao.getByMainId(entityId);
    	CalendarShift.setCalendarShiftPeroidList(calendarShiftPeroidList);
    	return CalendarShift;
    }
    
    /**
     * 更新实体同时更新子表记录
     */
    public void update(CalendarShift  calendarShift){
    	super.update(calendarShift);
    	String shiftId = calendarShift.getId();
    	calendarShiftPeroidDao.delByMainId(shiftId);
    	List<CalendarShiftPeroid> calendarShiftPeroidList=calendarShift.getCalendarShiftPeroidList();
    	for(CalendarShiftPeroid calendarShiftPeroid:calendarShiftPeroidList){
    		calendarShiftPeroid.setShiftId(shiftId);
    		calendarShiftPeroidDao.create(calendarShiftPeroid);
    	}
    }
    
	
	public void workTimeAdd(Long shiftId, String[] startTime, String[] endTime, String[] memo){
		
		if(startTime!=null && endTime!=null){
			
			calendarShiftPeroidDao.delByMainId(shiftId.toString());
			
			for(int idx=0;idx<startTime.length;idx++){
				CalendarShiftPeroid CalendarShiftPeroid = new CalendarShiftPeroid();
				try {
					//CalendarShiftPeroid.setId(UniqueIdUtil.genId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CalendarShiftPeroid.setShiftId(shiftId.toString());
				CalendarShiftPeroid.setStartTime(startTime[idx]);
				CalendarShiftPeroid.setEndTime(endTime[idx]);
				CalendarShiftPeroid.setMemo(memo[idx]);
				calendarShiftPeroidDao.create(CalendarShiftPeroid);
			}
		}
	}

	@Override
	public void setDefaultShift(String id) {
		//设置非默认
		calendarShiftDao.setNotDefaultShift();
		CalendarShift calendarShift =this.get(id);
		calendarShift.setIsDefault("1");
		update(calendarShift);
		
	}
}
