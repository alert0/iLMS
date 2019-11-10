package com.hotent.mini.controller.calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.MapUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.system.worktime.manager.CalendarAssignManager;
import com.hotent.system.worktime.manager.CalendarDateTypeManager;
import com.hotent.system.worktime.manager.CalendarManager;
import com.hotent.system.worktime.manager.CalendarSettingManager;
import com.hotent.system.worktime.manager.CalendarShiftManager;
import com.hotent.system.worktime.model.Calendar;
import com.hotent.system.worktime.model.CalendarSettingEvent;
import com.hotent.system.worktime.model.CalendarShift;

/**
 * 系统日历
 * <pre>
 * 构建组：x5-bpmx-platform
 * 作者:heyf
 * 日期:2016-10-27
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/calendar/work/")
public class WorkController extends GenericController {
	@Resource
	CalendarManager calendarManager;
	@Resource
	UserManager userManager;
	@Resource
	CalendarAssignManager calendarAssignManager;
	@Resource
	CalendarSettingManager calendarSettingManager;
	@Resource
	CalendarShiftManager calendarShiftManager;
	@Resource
	CalendarDateTypeManager calendarDateTypeManager;

	/**
	 * 列表(分页条件查询)数据
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
		PageList<Calendar> overTimeList = (PageList<Calendar>) calendarManager
				.query(queryFilter);
		return new PageJson(overTimeList);
	}
	
	/**
	 * 查询工作日历详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@ResponseBody
	public Object detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		Map<String,Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(id)) {
			Calendar calendar = calendarManager.get(id);
			map.put("calendar", calendar);
		}
		List<CalendarShift> shifts = calendarShiftManager.getAll();
		map.put("shifts", shifts);
		return map;
	}
	
	/**
	 * 查询工作日历设置
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("calendarSetting")
	@ResponseBody
	public Object calendarSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		Integer year = RequestUtil.getInt(request, "year");
		if(StringUtil.isEmpty(id)||BeanUtils.isEmpty(year)){
			return null;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		List<CalendarSettingEvent> calendarSettingEvent = calendarManager.getCalendarSettingEvent(id, year);
		map.put("events", calendarSettingEvent);
		map.put("year", year);
		map.put("result", true);
		return map;
	}
	
	/**
	 * 系统用户信息明细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		Calendar calendar = null;
		if (StringUtil.isNotEmpty(id)) {
			calendar = calendarManager.get(id);
		}
		return getAutoView().addObject("calendar", calendar).addObject(
				"returnUrl", preUrl);
	}

	/**
	 * 保存系统日历信息
	 * 
	 * json格式： { "id": 10000000590155, "name": "日历都是一整年", "memo": "日历都是一整年",
	 * "year": 2013, "month": 3, "days": [{ "day": "12", "type": "1",
	 * "calendarShiftId": "10000001267001" }, { "day": "13", "type": "1",
	 * "calendarShiftId": "10000001267001" }] }
	 * 
	 * @param request
	 * @param response
	 * @param calendar
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String,Object> map) throws Exception {
		String json = MapUtil.getString(map, "calendar");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("result", true);
		try {
			calendarManager.saveCalendar(json);
			returnMap.put("message", "日历设置成功");
		} catch (Exception ex) {
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
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			calendarAssignManager.delByCalId(aryIds); // 日历分配
			calendarSettingManager.delByCalId(aryIds); // 具体的日历设置
			calendarManager.removeByIds(aryIds); // 日历
			message = new ResultMessage(ResultMessage.SUCCESS,
					"删除成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL,
					"删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("setDefault")
	public void setDefault(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		calendarManager.setDefaultCal(id);
		message = new ResultMessage(ResultMessage.SUCCESS, "设置默认成功");
		writeResultMessage(response.getWriter(), message);
		response.sendRedirect(preUrl);
	}

}
