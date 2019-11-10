package com.hotent.mini.controller.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.persistence.manager.LogErrManager;
import com.hotent.sys.persistence.model.LogErr;

/**
 * 
 * <pre>
 * 描述：错误日志 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-28 17:40:56
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/system/sysObjLog/")
public class LogErrController extends GenericController {
	@Resource
	LogErrManager logErrManager;

	/**
	 * 错误日志列表(分页条件查询)数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("logErrListJson")
	public @ResponseBody
	PageJson listJson(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<LogErr> sysLogErrList = (PageList<LogErr>) logErrManager
				.query(queryFilter);
		return new PageJson(sysLogErrList);
	}

	/**
	 * 错误日志明细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("logErrGetJson")
	@ResponseBody
	public LogErr get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		LogErr sysLogErr = null;
		if (StringUtil.isNotEmpty(id)) {
			sysLogErr = logErrManager.get(id);
		}
		return sysLogErr;
		
	}

}