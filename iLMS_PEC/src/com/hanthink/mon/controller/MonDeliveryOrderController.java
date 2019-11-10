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
import com.hanthink.mon.manager.MonDeliveryOrderManager;
import com.hanthink.mon.model.MonDeliveryOrderModel;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: DeliveryOrderController
 * @Description: 取货单查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Controller
@RequestMapping("/mon/DeliveryOrder")
public class MonDeliveryOrderController extends GenericController {

	@Resource
	private MonDeliveryOrderManager deliveryOrderManager;

	/**
	 * @Description: 取货单查询
	 * @param: request
	 * @param: response
	 * @param: model
	 * @return: PageJson
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@RequestMapping("queryDeliveryOrderPage")
	public @ResponseBody PageJson queryDeliveryOrderPage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") MonDeliveryOrderModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		HttpSession session = request.getSession();
		//将数据保存到session
		session.setAttribute(SessionKey.DELIVERY_ORDER_QUERYFILTER, model);
		
		DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		PageList<MonDeliveryOrderModel> pageList = deliveryOrderManager.queryDeliveryOrderPage(model, page);
		return new PageJson(pageList);

	}

	/**
	 * @Description: 取货单明细查询
	 * @param: request
	 * @param: response
	 * @param: model
	 * @return: PageJson
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@RequestMapping("queryDeliveryOrderDetailPage")
	public @ResponseBody PageJson queryDeliveryOrderDetailPage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") MonDeliveryOrderModel model) {
		DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		//设置查询条件工厂值
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		PageList<MonDeliveryOrderModel> pageList = deliveryOrderManager.queryDeliveryOrderDetailPage(model, page);
		return new PageJson(pageList);

	}

}
