package com.hanthink.mon.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mon.manager.MonPCInStockManager;
import com.hanthink.mon.model.MonPCInStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;

@Controller
@RequestMapping("/mon/pcInStock")
public class MonPCInStockController extends GenericController{
	
	@Resource
	private MonPCInStockManager pcInStockManager;
	
	@RequestMapping("/queryPCInStock")
	public @ResponseBody PageJson queryPCInStock(HttpServletRequest request,HttpServletResponse response,
													MonPCInStockModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<MonPCInStockModel> pageList = pcInStockManager.queryPCInStock(model,page);
		
		return new PageJson(pageList);
	}
	
	@RequestMapping("/queryForLineCharts")
	public @ResponseBody PageJson queryForLineCharts(HttpServletRequest request,HttpServletResponse response,
														MonPCInStockModel model)throws Exception{
		PageList<MonPCInStockModel> pageList = pcInStockManager.queryForLineCharts(model);
		return new PageJson(pageList);
	}
}
