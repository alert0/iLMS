package com.hotent.mini.controller.calendar;

import java.util.ArrayList;
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

import com.google.gson.reflect.TypeToken;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.MapUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.api.IdGenerator;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.system.worktime.manager.CalendarShiftManager;
import com.hotent.system.worktime.manager.CalendarShiftPeroidManager;
import com.hotent.system.worktime.model.CalendarShift;
import com.hotent.system.worktime.model.CalendarShiftPeroid;

/**
 * 班次管理
 * <pre>
 * 构建组：x5-bpmx-platform
 * 作者:hyf
 * 日期:2016-10-27
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/calendar/shift/")
public class ShiftController extends GenericController {
	@Resource
	private CalendarShiftManager calendarShiftManager;
	@Resource
	private  CalendarShiftPeroidManager calendarShiftPeroidManager;
	@Resource
	IdGenerator idGenerator;
	@Resource
	UserManager userManager;

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
		PageList<CalendarShift> shifList = (PageList<CalendarShift>) calendarShiftManager
				.query(queryFilter);
		return new PageJson(shifList);
	}

	@RequestMapping("detail")
	@ResponseBody
	public Object detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		Map<String,Object> map = new HashMap<String, Object>();
		List<CalendarShiftPeroid> shiftPeroidlist = new ArrayList<CalendarShiftPeroid>();
		CalendarShift shift = null;
		if (StringUtil.isNotEmpty(id)) {
			shift = calendarShiftManager.get(id);
			shiftPeroidlist = calendarShiftPeroidManager.getByShiftId(id);
			map.put("shift",shift);
			map.put("shiftPeroids",shiftPeroidlist);
		}
		return map;
	}

	/**
	 * 保存信息
	 * 
	 * @param request
	 * @param response
	 * @param calendarShift
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String,Object> map) throws Exception {
		String shiftJson = MapUtil.getString(map, "shift");
		String shiftPeroidsJson = MapUtil.getString(map, "shiftPeroids");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		try {
			CalendarShift calendarshift = GsonUtil.toBean(shiftJson, CalendarShift.class);
			List<CalendarShiftPeroid> peroids = GsonUtil.toBean(shiftPeroidsJson, new TypeToken<List<CalendarShiftPeroid>>(){}.getType());
			calendarshift.setCalendarShiftPeroidList(peroids);
			String shiftId = calendarshift.getId();
			if (StringUtil.isEmpty(shiftId)) {
				shiftId = idGenerator.getSuid();
				calendarshift.setId(shiftId);
				calendarShiftManager.create(calendarshift);
				resultMap.put("message", "添加加班成功");
			} else {
				shiftId = calendarshift.getId();
				calendarShiftManager.update(calendarshift);
				resultMap.put("message", "更新加班成功");
			}
		} catch (Exception e) {
			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
			resultMap.put("result", false);
			resultMap.put("message", rootCauseMessage);
		}
		return resultMap;
	}

	/**
	 * 批量删除记录(逻辑删除)
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
			calendarShiftManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS,
					"删除成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL,
					"删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 设置默认工时，只能有一个是默认的
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("setDefault")
	public void setDefault(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		calendarShiftManager.setDefaultShift(id);
		message = new ResultMessage(ResultMessage.SUCCESS, "设置默认成功");
		writeResultMessage(response.getWriter(), message);
		response.sendRedirect(preUrl);
	}
}
