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

import com.hanthink.inv.manager.InvInLogManager;
import com.hanthink.inv.model.InvInLogModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.web.GenericController;

/**
 * <pre> 
 * 功能描述:入库管理控业务制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;
@RequestMapping("/inv/inLog")
@Controller
public class InvInLogController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(InvInLogController.class);
	
	@Resource
	private InvInLogManager inLogManager;
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
		
		PageList<InvInLogModel> pageList = inLogManager.queryInLogForPage(model,page);
		
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
		
		List<InvInLogModel> list = inLogManager.queryInLogForExport(model);
		
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
			String[] headers = {"仓库代码","入库单号","物流单号","零件号","简号","零件名称","收容数","入库数量","入库数量(箱)","入库时间"};
			String[] columns = {"depotNo","recNo","orderNo","partNo","partShortNo","partName","standardPac","recNum","recQty","inTime"};
			int[] widths = {100,100,100,100, 100,100,100,100, 100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "入库数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
}
