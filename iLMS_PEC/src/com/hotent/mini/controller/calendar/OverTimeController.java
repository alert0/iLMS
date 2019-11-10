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
import com.hotent.base.db.api.IdGenerator;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.system.worktime.manager.OverTimeManager;
import com.hotent.system.worktime.model.OverTime;

/**
 * 加班管理
 * <pre>
 * 构建组：x5-bpmx-platform
 * 作者:hyf
 * 日期:2016-10-27
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/calendar/overTime/")
public class OverTimeController extends GenericController {
	@Resource
	OverTimeManager overTimeManager;
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
		PageList<OverTime> overTimeList = (PageList<OverTime>) overTimeManager
				.query(queryFilter);
		return new PageJson(overTimeList);
	}

	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		OverTime overTime = null;
		if (StringUtil.isNotEmpty(id)) {
			overTime = overTimeManager.get(id);
		} else {
			overTime = new OverTime();
			Date curdate = new Date();
			overTime.setStartTime(curdate);
			overTime.setEndTime(curdate);
		}
		return getAutoView().addObject("overTime", overTime).addObject(
				"returnUrl", preUrl);
	}

	/**
	 * 明细页面
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
		OverTime overTime = null;
		if (StringUtil.isNotEmpty(id)) {
			overTime = overTimeManager.get(id);
		}
		return getAutoView().addObject("overTime", overTime).addObject(
				"returnUrl", preUrl);
	}

	/**
	 * 保存信息
	 * 
	 * @param request
	 * @param response
	 * @param overTime
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			OverTime overTime) throws Exception {
		String resultMsg = null;
		String id = overTime.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				overTime.setId(idGenerator.getSuid());
				overTimeManager.create(overTime);
				resultMsg = "添加加班成功";
			} else {
				overTimeManager.update(overTime);
				resultMsg = "更新加班成功";
			}
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
		}
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
			overTimeManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
