package com.hanthink.inv.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.inv.manager.InvZGJStockManager;
import com.hanthink.inv.model.InvUnloadPortModel;
import com.hanthink.inv.model.InvZGJStockModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * @ClassName: InvZGJStockController
 * @Description: 支给件库存管理
 * @author dtp
 * @date 2019年4月9日
 */
@RequestMapping("/inv/zgj")
@Controller
public class InvZGJStockController extends GenericController{

	@Resource
	private InvZGJStockManager invZGJStockManager;
	
	
	/**
	 * 分页数据查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@RequestMapping("queryStockForPage")
	public @ResponseBody PageJson queryStockForPage(HttpServletRequest request,HttpServletResponse response,
									InvZGJStockModel model)throws Exception{
		Page page = getQueryFilter(request).getPage();

		PageList<InvZGJStockModel> pageList = invZGJStockManager.queryStockForPage(model,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 加载单条数据详情控制器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@RequestMapping("getJson")
	public @ResponseBody InvZGJStockModel queryStockById(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String id = RequestUtil.getString(request, "id");
		InvZGJStockModel model = invZGJStockManager.queryStockById(id);
		return model;
	}
	/**
	 * 修改安全库存数控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@RequestMapping("saveStock")
	public void updateForSafeStockNum(HttpServletRequest request,HttpServletResponse response,
									@RequestBody InvZGJStockModel model) throws Exception {
		String resultMsg = null;
		try {
			invZGJStockManager.updateForSafeStockNum(model,RequestUtil.getIpAddr(request));
			resultMsg = "修改库存数量成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	/**
	 * 查询数据导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@RequestMapping("exportForExcel")
	public void queryExportDataForExcel(HttpServletRequest request,HttpServletResponse response,
										InvZGJStockModel model)throws Exception {
		
		List<InvZGJStockModel> list = invZGJStockManager.queryExportDataForExcel(model);
		
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
			String[] headers = {"仓库名称","零件号","简号","零件名称","库位","在库数量"};
			String[] columns = {"wareName","partNo","partShortNo","partName","baseLocation","stock"};
			int[] widths = {60,100,60, 100,50,60};
			/*String[] headers = {"仓库名称","零件号","简号","零件名称","库位","最小库存","最大库存","在库数量"};
			String[] columns = {"wareName","partNo","partShortNo","partName","baseLocation","safeStock","maxStock","stock"};
			int[] widths = {100,100,100, 100,100,100, 100,100};*/
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "支给件库存管理导出"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 批量修改库存数据
	 * @param request
	 * @param response
	 * @param list
	 * @author zmj
	 * @date 2019年2月18日
	 */
	@RequestMapping("batchUpdate")
	public void batchUpdateStock(HttpServletRequest request,HttpServletResponse response,
							@RequestBody List<InvZGJStockModel> list) throws Exception{
		String maxStock = RequestUtil.getString(request, "maxStock");
		String minStock = RequestUtil.getString(request, "minStock");
		String stock = RequestUtil.getString(request, "stock");
		String ipAddr = RequestUtil.getIpAddr(request);
		String resultMsg = null;
		try {
			invZGJStockManager.batchUpdateStock(list,maxStock,minStock, stock, ipAddr);
			resultMsg = "数据修改成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage().toString();
			writeResultMessage(response.getWriter(),resultMsg,resultMsg,ResultMessage.FAIL);
		}
	}
	
	/**
	 * @Description:  加载仓库名称下拉框
	 * @param: @param request
	 * @param: @param response
	 * @param: @return
	 * @param: @throws Exception    
	 * @return: List<InvUnloadPortModel>   
	 * @author: dtp
	 * @date: 2019年4月10日
	 */
	@RequestMapping("/loadWareCode")
	public @ResponseBody List<InvUnloadPortModel> queryWareCodeLsit(HttpServletRequest request,
				HttpServletResponse response)throws Exception{
		InvUnloadPortModel model = new InvUnloadPortModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return invZGJStockManager.queryWareCodeLsit(model);
	}
	
}
