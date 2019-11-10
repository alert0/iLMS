package com.hanthink.jit.controller;


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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.jit.manager.JitRealKbBatchManager;
import com.hanthink.jit.model.JitRealKbBatchModel;
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
 * 描述：实际过点批次表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/jit/jitRealKbBatch")
public class JitRealKbBatchController extends GenericController{
	private static Logger log = LoggerFactory.getLogger(JitRealKbBatchController.class);
	
	@Resource
	JitRealKbBatchManager jitRealKbBatchManager;
	
	/**
     * 实际过点批次表
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("queryJitRealKbBatchPage")
    public @ResponseBody PageJson queryJitRealKbBatchPage(HttpServletRequest request,
            HttpServletResponse reponse, @ModelAttribute("model") JitRealKbBatchModel model) {
    	model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
    	DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
    	PageList<JitRealKbBatchModel> pageList = (PageList<JitRealKbBatchModel>) jitRealKbBatchManager.queryJitRealKbBatchForPage(model, p);
        return new PageJson(pageList);
    	
    }
	
    /**
	 * 下载导出实际过点批次信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadJitRealKbBatchModel")
	public void downloadJitRealKbBatchModel(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitRealKbBatchModel model){
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
		List<JitRealKbBatchModel> list =  jitRealKbBatchManager.queryJitRealKbBatchByKey(model);
		/**
		 * 如果查询记录超过10000条则报错
		 */
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"信息点", "VIN", "过点工位","下线批次进度",
				"实际过点批次进度","工位过点时间", "信息点过点时间"};
		String[] columns = {"planCodeDesc", "vin", "stationCode","kbProductSeqno",
				"realKbProductSeqno","realKbTimeStr", "passTime"};
		int[] widths = {80, 80, 80, 80,
				80, 80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "实际过点批次"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
}
