package com.hotent.system.worktime.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.system.worktime.dao.CalendarAssignDao;
import com.hotent.system.worktime.dao.CalendarDao;
import com.hotent.system.worktime.dao.CalendarSettingDao;
import com.hotent.system.worktime.manager.CalendarAssignManager;
import com.hotent.system.worktime.manager.CalendarSettingManager;
import com.hotent.system.worktime.manager.OverTimeManager;
import com.hotent.system.worktime.model.Calendar;
import com.hotent.system.worktime.model.CalendarAssign;
import com.hotent.system.worktime.model.CalendarSetting;
import com.hotent.system.worktime.model.CalendarShiftPeroid;

@Service("calendarAssignManager")
public class CalendarAssignManagerImpl extends AbstractManagerImpl<String, CalendarAssign> implements CalendarAssignManager{
	@Resource
	CalendarAssignDao calendarAssignDao;
	@Resource
	CalendarDao calendarDao;
	@Resource
	OverTimeManager overTimeManager;
	@Resource
	CalendarSettingManager calendarSettingManager;
	@Resource
	CalendarSettingDao calendarSettingDao;
	 
	@Override
	protected Dao<String, CalendarAssign> getDao() {
		return calendarAssignDao;
	}
	
	/**
	 * 取日历设置。
	 * <pre>
	 * 	1.根据个人获取日历。
	 *  2.没有获取到则获取部门的日历。
	 *  3.部门也没有设置的情况，获取系统默认的日历。
	 *  4.没有则返回0。
	 * </pre>
	 * @param userId
	 * @return
	 */
	public String getCalendarIdByUserId(String userId){ 
		//根据用户ID取得日历ID，一个用户应该只有一个日历
		CalendarAssign calendarAssign =calendarAssignDao.getByAssignId(CalendarAssign.TYPE_USER, userId);
		if(calendarAssign==null){
			//SysOrg sysOrg = sysOrgDao.getPrimaryOrgByUserId(userId);
			//XogGroup xogGroup=XogGroupGao;
//			if(sysOrg!=null){
//				long orgId=sysOrg.getOrgId();
//				calendarAssign =calendarAssignDao.getByAssignId(CalendarAssign.TYPE_ORGANIZATION, orgId);
//			}
		}
		if(calendarAssign!=null){
			return calendarAssign.getCanlendarId();
		}
		//获取默认的日历。
		Calendar calendar=calendarDao.getDefaultCalendar();
		if(calendar!=null){
			return calendar.getId();
		}
		return "0";
	}
	/**
	 * 根据日历列表获取相应的工作时间分段列表
	 * @param list
	 * @return
	 */
	@Deprecated
	public List<CalendarShiftPeroid> getBycalList(List<CalendarSetting> list){
		List<CalendarShiftPeroid>tmplist=new ArrayList<CalendarShiftPeroid>();
		List<CalendarShiftPeroid>worklist=new ArrayList<CalendarShiftPeroid>();
		for(CalendarSetting setting:list){
			String calDay=setting.getCalDay();   
			
            List<CalendarShiftPeroid>workTimeList=setting.getCalendarShiftPeroidList();
			for(CalendarShiftPeroid work:workTimeList){
				work.setCalDay(calDay);
				tmplist .add((CalendarShiftPeroid)work.clone());
			}
		}
		int len=tmplist.size();
		for(int i=0;i<len;i++){
			CalendarShiftPeroid workTime=tmplist.get(i);  
			
			if(i<len-1){
				int j=i+1;
				CalendarShiftPeroid nextTime=tmplist.get(j);
				if(workTime.getEndDateTime().compareTo(nextTime.getStartDateTime())>0){
					workTime.setEndDateTime(nextTime.getEndDateTime());
					worklist.add(workTime);
					i++;
				}
				else{
					worklist.add(workTime);
				}
			}else{
				worklist.add(workTime);
			}
		}
		return worklist;
	}
	
	
	/**
	 * 返回系统默认工作日历的工作时段
	 * @param startDate 开始时间
	 * @return
	 */
	public List<CalendarShiftPeroid> getTaskTimeByDefault(Date startDate){
		Calendar CalCalendar=calendarDao.getDefaultCalendar();
		if(CalCalendar!=null){
			String calendarId = CalCalendar.getId();
			List<CalendarShiftPeroid> worklist= calendarSettingManager.getByCalendarId(calendarId,startDate);
			return worklist;
		}	
		return null;	
	}
	
	
	/**
	 * 取被分配的类型,用户或组织
	 * @return
	 */
	public List<Map<String,String>> getAssignUserType(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", "1");
		map.put("name", "用户");
		list.add(map);
		
		map = new HashMap<String,String>();
		map.put("id", "2");
		map.put("name", "组织");
		list.add(map);
		
		return list;
	}
	
	/**
	 * 根据日历id删除记录
	 * @param calId
	 */
	public void delByCalId(String[] calIds){
		calendarAssignDao.delByCalId(calIds);
	}
	
	/**
	 * 根据用户ID得到唯一条分配信息
	 * @param assignId
	 * @return
	 */
	public CalendarAssign getbyAssignId(String assignId){
		return calendarAssignDao.getbyAssignId(assignId);
	}

	@Override
	public List<String> saveAssign(String assign) throws Exception{
		List<String> duplicateNames = new ArrayList<String>();
		JSONObject jobject = JSONObject.fromObject(assign);
		String assignType = jobject.getString("assignType");
		String calendarId = jobject.getString("calendarId");
		JSONArray jsonArray = jobject.getJSONArray("assign");
		String id = null;
		try {
			id = jobject.getString("id");
		} catch (Exception e) {}
		for (Object object : jsonArray) {
			JSONObject jObj = (JSONObject)object;
			String assignId = jObj.getString("id");
			CalendarAssign calendarAssign = new CalendarAssign();
			calendarAssign.setAssignId(assignId);
			calendarAssign.setAssignType(assignType);
			calendarAssign.setCanlendarId(calendarId);
			if(StringUtil.isNotEmpty(id)){
				CalendarAssign oldAssign = getbyAssignId(assignId);
				if(oldAssign!=null && !oldAssign.getId().equals(id)){
					duplicateNames.add(jObj.getString("name"));
					continue;
				}else{
					calendarAssign.setId(id);
					this.update(calendarAssign);
				}
			}else{
				CalendarAssign oldAssign = getbyAssignId(assignId);
				if(oldAssign!=null){
					duplicateNames.add(jObj.getString("name"));
					continue;
				}
				calendarAssign.setId(UniqueIdUtil.getSuid());
				this.create(calendarAssign);
			}
		}
		return duplicateNames;
	}
}
