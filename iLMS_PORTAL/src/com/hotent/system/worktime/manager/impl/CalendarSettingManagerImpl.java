package com.hotent.system.worktime.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.api.IdGenerator;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.system.worktime.dao.CalendarDateTypeDao;
import com.hotent.system.worktime.dao.CalendarSettingDao;
import com.hotent.system.worktime.dao.CalendarShiftDao;
import com.hotent.system.worktime.dao.CalendarShiftPeroidDao;
import com.hotent.system.worktime.manager.CalendarSettingManager;
import com.hotent.system.worktime.model.CalendarDateType;
import com.hotent.system.worktime.model.CalendarSetting;
import com.hotent.system.worktime.model.CalendarShift;
import com.hotent.system.worktime.model.CalendarShiftPeroid;


@Service("calendarSettingManager")
public class CalendarSettingManagerImpl extends AbstractManagerImpl<String, CalendarSetting> implements CalendarSettingManager{
	@Resource
	CalendarSettingDao calendarSettingDao;
	@Resource
	CalendarShiftPeroidDao calendarShiftPeroidDao;
	@Resource
	CalendarShiftDao calendarShiftDao;
	@Resource
	CalendarDateTypeDao calendarDateTypeDao;
	@Resource
	protected IdGenerator idGenerator;
	 
	@Override
	protected Dao<String, CalendarSetting> getDao() {
		return calendarSettingDao;
	}
	
	/**
	 * 根据日历查询时间。
	 * 将时间进行分段。
	 * 开始时间1 结束时间1
	 * 开始时间2 结束时间2
	 * @param calendarId
	 * @return
	 */
	@Deprecated
	public List<CalendarShiftPeroid> getByCalendarId(String calendarId,Date startTime){
		List<CalendarShiftPeroid>  rtnList=new ArrayList<CalendarShiftPeroid>();
		List<CalendarShiftPeroid>  tmpList=new ArrayList<CalendarShiftPeroid>();
		List<CalendarSetting> list=calendarSettingDao.getByCalendarId(calendarId,startTime);
		for(CalendarSetting calendarSetting:list){
			String calDay=calendarSetting.getCalDay();
			List<CalendarShiftPeroid> workTimeList=calendarSetting.getCalendarShiftPeroidList();
			for(CalendarShiftPeroid calendarShiftPeroid:workTimeList){
				calendarShiftPeroid.setCalDay(calDay);
				tmpList.add((CalendarShiftPeroid)calendarShiftPeroid.clone());
			}
		}
		int len=tmpList.size();
		for(int i=0;i<len;i++){
			CalendarShiftPeroid curTime=tmpList.get(i);
			if(i<len-1){
				int j=i+1;
				CalendarShiftPeroid nextTime=tmpList.get(j);
				if(curTime.getEndDateTime().compareTo(nextTime.getStartDateTime())>0){
					curTime.setEndDateTime(nextTime.getEndDateTime());
					rtnList.add(curTime);
					i++;
				}
				else{
					rtnList.add(curTime);
				}
			}
			else{
				rtnList.add(curTime);
			}
		}
		return rtnList;
	}
	
	/**
	 * 获取工作时间。
	 * set CalendarShiftPeroidList
	 * 以日历ID，开始时间和结束时间为备件获取所有符合备件的工作时间的列表。
	 * 如果用户没有找到日历，则生成一个临时的日历设置
	 * @author hjx
	 * @param calendarId 日历ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 工作时间的列表。
	 */
	public List<CalendarSetting> getByCalendarId(String calendarId,Date startTime,Date endTime){
		List<CalendarShiftPeroid>  tmpList=new ArrayList<CalendarShiftPeroid>();
		List<CalendarSetting> list =new ArrayList<CalendarSetting>();
		
		//如果用户没有找到日历，则生成一个临时的日历设置
		if("0".equals(calendarId)){
			list=this.generateCalendarByDateType( startTime, endTime);
		}else{
			list= calendarSettingDao.getSegmentByCalId(calendarId, startTime, endTime);
			//如果用户某段时间没有设置过日历，还是生成临时日历
			if(list==null||list.size()<=0)list=this.generateCalendarByDateType( startTime, endTime);
		}
		
		for(CalendarSetting calendarSetting:list){
			String shiftId=calendarSetting.getShiftId();
			tmpList=calendarShiftPeroidDao.getByMainId(shiftId);
			calendarSetting.setCalendarShiftPeroidList(tmpList);
		}
		return list;
	}
	
	/**
	 * 根据日历id，year，month 得到日历
	 * @param id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<CalendarSetting> getCalByIdYearMon(String id, int year, int month){
		return calendarSettingDao.getCalByIdYearMon(id, year, month);
	}
	
	@Override
	public List<CalendarSetting> getCalByIdYear(String id, int year) {
		return calendarSettingDao.getCalByIdYear(id, year);
	}
	
	/**
	 * 根据 日历id，year，month 删除日历
	 * @param calid
	 * @param year
	 * @param month
	 */
	public void delByCalidYearMon(String calid, Short year, Short month){
		calendarSettingDao.delByCalidYearMon(calid, year, month);
	}
	
	/**
	 * 根据日历id删除记录
	 * @param calId
	 */
	public void delByCalId(String[] calIds){
		calendarSettingDao.delByCalId(calIds);
	}
	
	/**
	 * 生成一个临时日历
	 * @author hjx
	 * @version 创建时间：2014-2-26  下午1:57:31
	 * @return
	 */
	private List<CalendarSetting> generateTempCalendar(Date startTime,Date endTime) {
		List<CalendarSetting> list = new ArrayList<CalendarSetting>();

		// 获取cal_shit表的默认班次的id，默认班次数据sql脚本insert到表里
		CalendarShift calendarShift = calendarShiftDao.getDefaultCalendarShift();
		if(calendarShift==null){
			throw new RuntimeException("请设置默认班次");
		}
		String shiftId = calendarShift.getId();
        
		//循环次数，即天数
		double times=(endTime.getTime()-startTime.getTime()) / (double)(1000 * 60 * 60 * 24);
		int n = (int) Math.ceil (times);
		for (int i = 0; i <= n; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(startTime);          // 当天
			c.add(Calendar.DAY_OF_YEAR, i); // 下一天
			// 得到格式化的日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String calDay = sdf.format(c.getTime());

			int years = c.get(Calendar.YEAR);         // 年
			int months = c.get(Calendar.MONTH) + 1;   // 月
			int days = c.get(Calendar.DATE);          // 日
			int weeks = c.get(Calendar.DAY_OF_WEEK) - 1;// 星期
			weeks=(weeks==0?7:weeks);//1代表星期一 2代表星期二 7代表星期日
			CalendarSetting calendarSetting = new CalendarSetting();
			calendarSetting.setId(idGenerator.getSuid());
			calendarSetting.setCalendarId(idGenerator.getSuid());
			calendarSetting.setYears(Long.valueOf(years));
			calendarSetting.setMonths(Long.valueOf(months));
			calendarSetting.setDays(Long.valueOf(days));
			calendarSetting.setShiftId(shiftId);
			calendarSetting.setCalDay(calDay);
			calendarSetting.setDateType(CalendarSetting.SETTING_DATETYPE_WORK);
			list.add(calendarSetting);
		}
		return list;
	}
   
	/**
	 * 根据cal_date_type设置日历的公休日、节假日
	 */
	@Override
	public  List<CalendarSetting>  generateCalendarByDateType(Date startTime,Date endTime){
		List<CalendarSetting> list=this.generateTempCalendar( startTime, endTime);
		//设置双休日，双休日不一定在周六、周日，而要根据cal_date_type表的公休日来设置
		//得到周日期范围的公休日
		List<CalendarDateType> phList=calendarDateTypeDao.getPhByWeekList();
		//得到年日期范围的法定假日（如春节 中秋节 寒暑假）
		List<CalendarDateType> lhList=calendarDateTypeDao.getLhByYearList();
		//得到年日期范围的公司假日
		List<CalendarDateType> chList=calendarDateTypeDao.getChByYearList();

		for(CalendarSetting calendarSetting:list){
			Date date=TimeUtil.getDateByDateString(calendarSetting.getCalDay());

			//如果是年日期范围的法定假日，setting改成休假
			for(CalendarDateType calDateType:lhList){
				if(date.compareTo(calDateType.getYearBegin())<=0&&date.compareTo(calDateType.getYearEnd())>=0)
					calendarSetting.setDateType(CalendarSetting.SETTING_DATETYPE_HOLIDAY);
			}

			//如果是年日期范围的法定假日，setting改成休假
			for(CalendarDateType calDateType:chList){
				if(date.compareTo(calDateType.getYearBegin())<=0&&date.compareTo(calDateType.getYearEnd())>=0)
					calendarSetting.setDateType(CalendarSetting.SETTING_DATETYPE_HOLIDAY);
			}
		}
		return list;
	}
}