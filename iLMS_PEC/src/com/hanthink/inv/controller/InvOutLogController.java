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

import com.hanthink.inv.manager.InvOutLogManager;
import com.hanthink.inv.model.InvOutLogModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * <pre> 
 * 功能描述:出库管理控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/inv/outLog")
@Controller
public class InvOutLogController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(InvOutLogController.class);
	
	@Resource
	private InvOutLogManager outLogManager;
	
	/**
	 * 分页查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月11日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryOutLogForPage(HttpServletRequest request,HttpServletResponse response,
													InvOutLogModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvOutLogModel> pageList = outLogManager.queryOutLogForPage(model,page);
		
		return new PageJson(pageList);
	}
	/**
	 * Excel数据导出控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月11日
	 */
	@RequestMapping("/exportForExcel")
	public void queryOutLogForExport(HttpServletRequest request, HttpServletResponse response,
								InvOutLogModel model)throws Exception{
		//获取页面查询条件并执行导出业务逻辑
		List<InvOutLogModel> list = outLogManager.queryOutLogForExport(model);
		
		try {
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			//获取系统所允许的最大导出数量
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); 
			
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			//设置导出Excel的列标题
			String[] headers = {"出库单号","出货仓库","出库类型","零件号","简号","零件名称","收容数","出库数量","出库数量","出库时间"};
			//绑定数据
			String[] columns = {"outNo","toDepotNo","outType","partNo","partShortNo","partName","standardPac","recNum",
									"recQty","outTime"};
			//设置列宽
			int[] widths = {100,100,100,100, 100,100,100,100, 100,100};
			//调用公共方法写出Excel
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, 
					"出库数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 加载出库类型下拉框
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月7日
	 */
	@RequestMapping("/queryOutType")
	public @ResponseBody List<InvOutLogModel> queryOutType(HttpServletRequest request,HttpServletResponse response)throws Exception{
		return outLogManager.queryOutType();
	}
}
