package com.hotent.sys.api.calendar;
import java.util.Date;

/**
 * 日历服务接口。
 * <pre> 
 * 构建组：x5-sys-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-10-30-下午2:51:25
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface ICalendarService{ 
	/**
	 * 根据用户和指定工时从现在开始获取任务的完成时间,。
	 * @author hjx
	 * @param userId
	 * @param time
	 * @return
	 */
	 Date getEndTimeByUser(String userId,long time);
		
	/**
	 * 
	 * 根据用户，指定工时，指定开始时间,计算任务实际完成时间。
	 * @param userId
	 * @param startTime
	 * @param time
	 * @return
	 */
	 Date getEndTimeByUser(String userId,Date startTime,long time);

	 /**
	 * 根据用户开始时间结束时间，获取这段时间的有效工时。
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Long getWorkTimeByUser(String userId,Date startTime,Date endTime); 
}