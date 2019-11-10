package com.hotent.mini.controller.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.MapUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.sys.api.calendar.ICalendarService;
import com.hotent.system.worktime.manager.CalendarAssignManager;
import com.hotent.system.worktime.manager.CalendarManager;
import com.hotent.system.worktime.model.Calendar;
import com.hotent.system.worktime.model.CalendarAssign;

/**
 * 日历分配
 * <pre>
 * 作者:hyf
 * 日期:2016-10-27
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/calendar/assign/")
public class CalendarAssignController extends GenericController {
	@Resource
	CalendarAssignManager calendarAssignManager;
	@Resource
	UserManager userManager;
	@Resource
	CalendarManager calendarManager;
	@Resource
	ICalendarService iCalendarService;

	/**
	 * @名称：cal_calendar_assign【日历分配】 (分页条件查询)数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("listJson")
	public @ResponseBody
	PageJson listJson(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<CalendarAssign> calendarAssignList = (PageList<CalendarAssign>) calendarAssignManager
				.query(queryFilter);
		return new PageJson(calendarAssignList);
	}
	
	@RequestMapping("detail")
	@ResponseBody
	public Object detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		List<Calendar> calendarList = calendarManager.getAll();
		CalendarAssign calendarAssign = null;
		if (StringUtil.isNotEmpty(id)) {
			calendarAssign = calendarAssignManager.get(id);
		} 
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("calendarAssign", calendarAssign);
		map.put("calendarList", calendarList);
		return map;
	}

	/**
	 * 保存系统用户信息
	 * 
	 * @param request
	 * @param response
	 * @param calendarAssign
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,
					   @RequestBody Map<String,Object> map) throws Exception {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("result", true);
		String assignStr = MapUtil.getString(map, "assign");
		try{
			List<String> duplicateNames = calendarAssignManager.saveAssign(assignStr);
			returnMap.put("duplicateNames", duplicateNames);
		}
		catch(Exception ex){
			returnMap.put("result", false);
			returnMap.put("message", ExceptionUtils.getRootCauseMessage(ex));
		}
		return returnMap;
	}

	/**
	 * 批量删除系统用户记录(逻辑删除)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @since 1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			calendarAssignManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * ajax返回任务所花费时间
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getWorkTimeByUser")
	@ResponseBody
	public Object getWorkTimeByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = "10000009130014";
		Date startTime = new Date();
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(java.util.Calendar.YEAR, 2017);
		cal.set(java.util.Calendar.MONTH, 1);
		cal.set(java.util.Calendar.DAY_OF_MONTH, 5);
		Date endTime = cal.getTime();
		Long time = iCalendarService.getWorkTimeByUser(userId, startTime, endTime) / 60000;
		String result = time.toString();
		return result;
	}

	/**
	 * 根据时长 和开始时间，计算任务完成时间
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getEndTimeByUser")
	@ResponseBody
	public Object getEndTimeByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId =  "10000009130014";
//		java.util.Calendar cal = java.util.Calendar.getInstance();
//		cal.set(java.util.Calendar.YEAR, 2017);
//		cal.set(java.util.Calendar.MONTH, 1);
//		cal.set(java.util.Calendar.DAY_OF_MONTH, 3);
//		Date startTime = cal.getTime();
		Date startTime = DateUtil.getCurrentDate();
		long time = 1440;
		Date dateTime = iCalendarService.getEndTimeByUser(userId, startTime, time);
		String result = TimeUtil.getDateTimeString(dateTime);
		return result;
	}
}
