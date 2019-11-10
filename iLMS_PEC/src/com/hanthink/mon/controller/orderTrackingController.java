package com.hanthink.mon.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.manager.MonOrderTrackingManager;
import com.hanthink.mon.model.MonOrderTrackingModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

@Controller
@RequestMapping("/mon/orderTracking")
public class orderTrackingController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(orderTrackingController.class);
	
	@Resource
	private MonOrderTrackingManager orderTrackingManager;
	
	@RequestMapping("/queryOrderTracking")
	public @ResponseBody PageJson queryOrderTrackingForPage(HttpServletRequest request,HttpServletResponse response,
																	MonOrderTrackingModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		PageList<MonOrderTrackingModel> pageList = orderTrackingManager.queryOrderTrackingForPage(model,page);
		
		return new PageJson(pageList);
	}
	
	@RequestMapping("/queryForOrderType")
	public @ResponseBody List<DictVO> queryForOrderType()throws Exception{
		return orderTrackingManager.queryForOrderType();
	}
	
	@RequestMapping("/exportForExcel")
	public void exportForExcel(HttpServletRequest request,HttpServletResponse response,
			MonOrderTrackingModel model) throws Exception{
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MonOrderTrackingModel> list = orderTrackingManager.exportForExcel(model);
		
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000);
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"物流单号","订单号","供应商代码", "出货地代码", "供应商名称","出货仓库","到货仓库","零件号","简号",
							"零件名称","VIN","订购量","计划备货时间/批次","实际备货时间/批次","备货数量","备货状态","备货准时性",
							"计划出货时间/批次","实际出货时间/批次","出货数量","出货状态","出货准时性","计划到货时间","计划到货批次",
							"实际到货时间","实际到货批次","到货数量","到货状态","到货准时性"};
		String[] columns = {"orderNo","purchaseNo","supplierNo", "supFactory", "supplierName", "supWareCode", "intoWareCode","partNo","partShortNo",
							"partName","vinNo","orderQTY","planPreTime","realyPreTime","preNum","preExcelStatus","preExcelOnTimeStatus",
							"planOutTime","realyOutTime","outNum","outExcelStatus","outExcelOnTimeStatus","planArrTime","jitPlanArrTime","realyArrTime","jitActualArrTime","arrNum","arrExcelStatus","arrExcelOnTimeStatus"};
		int[] widths = {100,100,120,100,150, 120,120,120,120,120, 120,120,120,120,120,
						120,120,120,120,120, 120,120,120,120,120,120,120,120,120,120,120,
						120,120,120};
		try {
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "订单跟踪界面导出数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
}
