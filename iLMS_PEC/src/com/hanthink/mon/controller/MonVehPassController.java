package com.hanthink.mon.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.business.util.SessionKey;
import com.hanthink.mon.manager.MonVehPassManager;
import com.hanthink.mon.model.MonVehPassModel;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: VehPassController
 * @Description: 实时工程查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Controller
@RequestMapping("/mon/VehPass")
public class MonVehPassController extends GenericController {

	@Resource
	private MonVehPassManager vehPassManager;

	/**
	 * @Description: 实时工程查询
	 * @param: request
	 * @param: response
	 * @param: model
	 * @return: PageJson
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@RequestMapping("queryVehPassPage")
	public @ResponseBody PageJson queryVehPassPage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") MonVehPassModel model) {
		HttpSession session = request.getSession();
		//将数据保存到session
		session.setAttribute(SessionKey.VEH_PASS_QUERYFILTER, model);
		
		DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		PageList<MonVehPassModel> pageList = vehPassManager.queryVehPassPage(model, page);
		return new PageJson(pageList);

	}

	

}
