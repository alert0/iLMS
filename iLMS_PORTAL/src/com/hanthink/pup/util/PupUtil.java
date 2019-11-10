package com.hanthink.pup.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;


/**
* <pre> 
* 描述：取货计划生产工具类
* 构建组：x5-bpmx-platform
* 作者:zmj
* 日期:2018-09-13 10:39:09
* 版权：汉思信息技术有限公司
* </pre>
*/
public class PupUtil {
	
	/**
	 *功能描述:根据年获取当年的日历
	 * @param year
	 * @return Calendar
	 * @author zmj
	 */
	private static Calendar getCalendarFormYear(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
        cal.set(Calendar.YEAR, year);
        return cal;
    }
	
	/**
	 *功能描述:获取年度周次开始时间
	 * @param year 年份
	 * @param weekNo 周次
	 * @return String : "2018-09-13"
	 * @author zmj
	 */
	public static String getStartDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
               cal.get(Calendar.DAY_OF_MONTH);    
    }
	
	/**
	 *功能描述:获取年度周次结束时间
	 * @param year 年份
	 * @param weekNo 周次
	 * @return String : "2018-09-13"
	 * @author zmj
	 */
	public static String getEndDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
               cal.get(Calendar.DAY_OF_MONTH);    
    }
	
	/**
	 *功能描述:将时间格式转为字符格式
	 * @param date 时间
	 * @param style 输出的时间格式
	 * @return String : "2018-09-13"/"2018-09-13 00:00:00"
	 * @author zmj
	 */
	public static String date2String(Date date,String style) {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		return sdf.format(date);
	}
	
	public static Date String2Date(String str,String style) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		return sdf.parse(str);
	}
	/**
	 *功能描述:返回当前时间的前一周的日期
	 * @return String yyyy-MM-dd
	 * @author zmj
	 * @Date 2018-09-13 14:26:33
	 */
	public static String getTimeWeekAgo() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar can = Calendar.getInstance();
        can.setTime(new Date());
        can.add(Calendar.DATE, - 7);
        Date date = can.getTime();
        String day = format.format(date);
        return day;
	}
	/**
	 *功能描述:获取当前时间的年月日
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 下午12:11:40
	 */
	public static String getCurrentDate()throws Exception{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(date);
		return currentDate;
	}
	
	/**
	 *功能描述:获取当前日期的具体时间
	 *@return style中的格式时间
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 下午12:32:12
	 */
	public static String getCurrentTime(String style)throws Exception{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		String currenttime = sdf.format(date);
		return currenttime;
	}
	
	/**
	 *功能描述:比较两个时间的先后
	 *@param minTime
	 *@param maxTime
	 *@return 返回true时maxTime比minTime晚,
	 *		      返回false时maxTime比minTime早
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月19日 下午10:17:49
	 */
	public static boolean compareTwoTimes(String minTime,String maxTime) throws Exception {
		Boolean result = false;
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date minDateTime = sdf.parse(minTime);
		Date maxDateTime = sdf.parse(maxTime);
		int compareResult = minDateTime.compareTo(maxDateTime);
		if(compareResult < 0) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}
	
	/**
	 *功能描述:获取当前日期的后一天日期
	 *@return
	 *@author zmj
	 *@date 2018年9月19日 下午10:48:52
	 */
	@SuppressWarnings("static-access")
	public static String getNextDay() {
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}
	/**
	 * 功能描述:计算两个日期之间的天数差
	 * 作者:zmj
	 * 日期:2018年9月19日下午9:38:39
	 * 版权:汉思信息技术有限公司
	 */
	public static int daysBetween(Date early, Date late) {
		int difference =  (int) ((late.getTime()-early.getTime())/86400000);
		return Math.abs(difference);
	}
	/**
	 * 功能描述:判断是否是数字
	 * 作者:zmj
	 * 日期:2018年10月4日下午9:40:08
	 * 版权:汉思信息技术有限公司
	 */
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
  }
	
	/**
	 * 判断实体对象属性是否全为空
	 * @param obj
	 * @return true:空  false:非空
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月19日
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isAllFieldNull(Object obj) throws Exception{
        Class cla = (Class) obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        boolean flag = true;
        for (Field field : fields) {
        	field.setAccessible(true);
            Object val = field.get(obj);
            if(val!=null) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
