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
import com.hanthink.mon.manager.MonJitOrderManager;
import com.hanthink.mon.model.MonJitOrderModel;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: MonJitOrderController
 * @Description: 拉动单查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Controller
@RequestMapping("/mon/MonJitOrder")
public class MonJitOrderController extends GenericController {

	@Resource
	private MonJitOrderManager monJitOrderManager;

	/**
	 * @Description: 拉动单查询
	 * @param: request
	 * @param: response
	 * @param: model
	 * @return: PageJson
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@RequestMapping("queryJitOrderPage")
	public @ResponseBody PageJson queryJitOrderPage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") MonJitOrderModel model) {
		HttpSession session = request.getSession();
		//将数据保存到session
		session.setAttribute(SessionKey.JIT_ORDER_QUERYFILTER, model);
		
		DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		PageList<MonJitOrderModel> pageList = monJitOrderManager.queryJitOrderPage(model, page);
		return new PageJson(pageList);

	}

	/**
	 * @Description: 拉动单明细查询
	 * @param: request
	 * @param: response
	 * @param: model
	 * @return: PageJson
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@RequestMapping("queryJitOrderDetailPage")
	public @ResponseBody PageJson queryJitOrderDetailPage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") MonJitOrderModel model) {
		DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		//设置查询条件工厂值
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		PageList<MonJitOrderModel> pageList = monJitOrderManager.queryJitOrderDetailPage(model, page);
		return new PageJson(pageList);

	}

}
