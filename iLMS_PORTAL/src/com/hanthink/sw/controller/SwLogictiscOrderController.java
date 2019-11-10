package com.hanthink.sw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sw.manager.SwLogictiscOrderManager;
import com.hanthink.sw.model.SwLogictiscOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;

@RequestMapping("/sw/logisticsOrder")
@Controller
public class SwLogictiscOrderController extends GenericController{
	
	@Resource
	private SwLogictiscOrderManager logictiscManager;
	
	@RequestMapping("/queryLogistiscsOrder")
	public @ResponseBody PageJson queryLogistiscsOrderForPage(HttpServletRequest request,HttpServletResponse response,
																SwLogictiscOrderModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<SwLogictiscOrderModel> pageList = logictiscManager.queryLogistiscsOrderForPage(model,page);
		
		return new PageJson(pageList);
	}
}
