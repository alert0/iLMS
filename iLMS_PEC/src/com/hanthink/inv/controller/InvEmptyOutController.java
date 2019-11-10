package com.hanthink.inv.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.inv.manager.InvEmptyOutManager;
import com.hanthink.inv.model.InvEmptyOutModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
/**
 * <pre> 
 * 功能描述:空容器出库指示业务控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月18日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;
@RequestMapping("/inv/emptyOut")
@Controller
public class InvEmptyOutController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(InvEmptyOutController.class);
	
	@Resource
	private InvEmptyOutManager emptyOutManager;
	/**
	 * 空容器出库指示分页查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryEmptyOutForPage(HttpServletRequest request,HttpServletResponse response,
											InvEmptyOutModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvEmptyOutModel> pageList = emptyOutManager.queryEmptyOutForPage(model,page);
 		
		return new PageJson(pageList);
	}
	/**
	 * 出库指示数据Excel导出查询控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	@RequestMapping("/exportForExcel")
	public void queryForExcelExport(HttpServletRequest request,HttpServletResponse response,
										InvEmptyOutModel model)throws Exception {
		
		List<InvEmptyOutModel> list = emptyOutManager.queryForExcelExport(model);
		
		try {
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			
			String[] headers = {"供应商代码","供应商名称","路线代码","车型","累计车次","取货时间","出库数量","箱种"};
			String[] columns = {"supplierNo","supplierName","routeCode","carType","totalCarNo","pickupTime","outQTY","boxType"};
			int[] widths = {100,100,100,100, 100,100,100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "空容器出库指示数据列表"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	@RequestMapping("/makeEmptyContainer")
	public void makeEmptyContainer(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			Integer resultCode = emptyOutManager.makeEmptyContainer();
			if (resultCode == 0) {
				writeResultMessage(response.getWriter(),"空容器生成成功",ResultMessage.SUCCESS);
			}else {
				writeResultMessage(response.getWriter(),"空容器生成失败",ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(),"空容器生成失败",ResultMessage.FAIL);
		}
	}
}
