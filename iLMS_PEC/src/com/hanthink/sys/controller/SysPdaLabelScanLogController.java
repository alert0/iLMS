package com.hanthink.sys.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sys.manager.SysPdaLabelScanLogManager;
import com.hanthink.sys.model.SysPdaLabelScanLogModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * 
 * <pre> 
 * 描述：PDA标签扫描日志表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sys/sysPdaLabelScanLog")
public class SysPdaLabelScanLogController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(SysPdaLabelScanLogController.class);
	@Resource
	SysPdaLabelScanLogManager sysPdaLabelScanLogManager;
	
	/**
     * 分页查询PDA标签扫描日志表
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            SysPdaLabelScanLogModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<SysPdaLabelScanLogModel> pageList = (PageList<SysPdaLabelScanLogModel>) sysPdaLabelScanLogManager.querySysPdaLabelScanLogForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
    /**
   	 * 下载导出PDA标签扫描日志表信息
   	 * @param request
   	 * @param response
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:25:20
   	 */
   	@RequestMapping("downloadSysPdaLabelScanLogModel")
   	public void downloadSysPdaLabelScanLogModel(HttpServletRequest request,HttpServletResponse response,
   			SysPdaLabelScanLogModel model){
   		try {
   		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
   		List<SysPdaLabelScanLogModel> list =  sysPdaLabelScanLogManager.querySysPdaLabelScanLogByKey(model);
   		/**
   		 * 如果查询记录超过10000条则报错
   		 */
   		if(0 == list.size()){
   			ExcelUtil.exportNoDataError(request, response);
   			return;
   		}
   		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
   		if(list.size() > sysMaxNum){
   			ExcelUtil.exportNumError(sysMaxNum, request, response);
   			return;
   		}
   		
   		String[] headers = {"二维码", "配送单号", "物流单号", "订单行号", "零件编号","简号","零件名称", "数量",
   				 "唯一标识", "类型",
   				 "上架ID", "下架ID", "托标识", "创建人", "创建时间",
   				 "IP地址"
   				};
   		String[] columns = {"barCode", "insNo", "orderNo", "rowNo", "partNo","partShortNo","partNameCn", "qty",
   				"barUuid","codeValueNameA",
   				"shelveId", "deliverNo", "toraId", "createUser","createTimeStr",
   		        "ip"};
   		int[] widths = {80, 80, 80, 80, 80, 80, 80, 80,
   				80, 80,
   				80, 80, 80, 80, 80,
   				80};
   		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
   		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "PDA标签扫描日志"+df.format(new Date()), list, headers, widths, columns);
   		} catch (Exception e) {
   			e.printStackTrace();
   			log.error(e.toString());
   			ExcelUtil.exportException(e, request, response);
   		}
   		
   	}
    
}
