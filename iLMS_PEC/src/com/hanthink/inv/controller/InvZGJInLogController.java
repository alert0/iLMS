package com.hanthink.inv.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.inv.manager.InvZGJInLogManager;
import com.hanthink.inv.model.InvInLogModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * @ClassName: InvZGJInLogController
 * @Description: 支给件入库查询
 * @author dtp
 * @date 2019年4月10日
 */
@RequestMapping("/inv/zgjInLog")
@Controller
public class InvZGJInLogController extends GenericController{

	@Resource
	private InvZGJInLogManager invZGJInLogManager;
	
	/**
	 * 入库数据分页查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryInLogForPage(HttpServletRequest request,HttpServletResponse response,
													InvInLogModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvInLogModel> pageList = invZGJInLogManager.queryInLogForPage(model,page);
		
		return new PageJson(pageList);
	}
	
	/**
	 * 入库数据导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月13日
	 */
	@RequestMapping("/exportForExcel")
	public void queryInLogForExport(HttpServletRequest request,HttpServletResponse response,
										InvInLogModel model)throws Exception {
		
		List<InvInLogModel> list = invZGJInLogManager.queryInLogForExport(model);
		
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
			String[] headers = {"仓库代码","入库单号","物流单号","零件号","简号","零件名称","入库数量","入库时间"};
			String[] columns = {"depotNo","recNo","orderNo","partNo","partShortNo","partName","recQty","inTime"};
			int[] widths = {60,100,100,100, 50,120,60,90};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "支给件入库管理导出"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	
}
