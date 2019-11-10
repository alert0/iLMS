package com.hotent.mini.controller.calendar;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.system.worktime.manager.CalendarAbsenceManager;
import com.hotent.system.worktime.model.CalendarAbsence;

/**
 * 请假管理
 * 
 * <pre>
 * 描述：请假管理
 * 构建组：x5-bpmx-platform
 * 作者:hyf
 * 日期:2016-10-27
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/calendar/absence/")
public class AbsenceController extends GenericController {
	@Resource
	CalendarAbsenceManager calendarAbsenceManager;

	/**
	 * @名称：cal_absence【请假】列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<CalendarAbsence> CalendarAbsenceList = (PageList<CalendarAbsence>) calendarAbsenceManager
				.query(queryFilter);
		return new PageJson(CalendarAbsenceList);
	}

	/**
	 * 编辑@名称：cal_absence【请假】信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		CalendarAbsence calendarAbsence = null;
		if (StringUtil.isNotEmpty(id)) {
			calendarAbsence = calendarAbsenceManager.get(id);
		} else {
			calendarAbsence = new CalendarAbsence();
			Date curdate = new Date();
			calendarAbsence.setStartTime(curdate);
			calendarAbsence.setEndTime(curdate);
		}
		return getAutoView().addObject("calendarAbsence", calendarAbsence)
				.addObject("returnUrl", preUrl);
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
		CalendarAbsence calendarAbsence = null;
		if (StringUtil.isNotEmpty(id)) {
			calendarAbsence = calendarAbsenceManager.get(id);
		}
		return getAutoView().addObject("calendarAbsence", calendarAbsence)
				.addObject("returnUrl", preUrl);
	}

	/**
	 * 保存系统用户信息
	 * 
	 * @param request
	 * @param response
	 * @param CalendarAbsence
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			CalendarAbsence calendarAbsence) throws Exception {
		String resultMsg = null;
		String id = calendarAbsence.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				calendarAbsence.setId(UniqueIdUtil.getSuid());
				calendarAbsenceManager.create(calendarAbsence);
				resultMsg = "添加成功";
			} else {
				calendarAbsenceManager.update(calendarAbsence);
				resultMsg = "更新成功";
			}
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
		}
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
			calendarAbsenceManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
