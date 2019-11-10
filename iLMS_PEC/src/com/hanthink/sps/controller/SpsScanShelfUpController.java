package com.hanthink.sps.controller;

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

import com.hanthink.sps.manager.SpsScanShelfUpManager;
import com.hanthink.sps.model.SpsScanShelfUpModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * @ClassName: SpsScanShelfUpController
 * @Description: SPS扫描上架查询
 * @author dtp
 * @date 2018年10月16日
 */
@Controller
@RequestMapping("/sps/spsScanShelfUp")
public class SpsScanShelfUpController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(SpsScanShelfUpController.class);
	@Resource
	private SpsScanShelfUpManager spsScanShelfUpManager;
	
	/**
	 * @Description: SPS扫描上架查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("querySpsScanShelfUpPage")
	public @ResponseBody PageJson querySpsScanShelfUpPage(HttpServletRequest request,HttpServletResponse response, 
		  SpsScanShelfUpModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		model.setFactoryCode(ContextUtil.getCurrentUser().getFactoryCode());
		PageList<SpsScanShelfUpModel> pageList = spsScanShelfUpManager.querySpsScanShelfUpPage(model, page);
		return new PageJson(pageList);
	}

	/**
	 * @Description: 查询默认值
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("selectDefaultValue")
	public @ResponseBody SpsScanShelfUpModel selectDefaultValue(HttpServletRequest request,HttpServletResponse response, 
		  SpsScanShelfUpModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getFactoryCode());
		SpsScanShelfUpModel spsScanShelfUpModel = spsScanShelfUpManager.selectDefaultValue(model);
		return spsScanShelfUpModel;
	}
	
	/**
	 * 导出
	 * <p>return: void</p>  
	 * <p>Description: SpsScanShelfUpController.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月4日
	 * @version 1.0
	 */
	@RequestMapping("downloadSpsScanShelfUpModel")
	public void downloadSpsScanShelfUpModel(HttpServletRequest request,HttpServletResponse response,
			SpsScanShelfUpModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<SpsScanShelfUpModel> list =  spsScanShelfUpManager.querySpsScanShelfUpByKey(model);
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
		
		String[] headers = {"车间","循环批次流水号","配送批次","批次基准","零件号",
				"简号","需求箱数","需求量","已配送箱数","已配送量",
				"配送地址","零件名称",
				"备件工程","配送工程","供应商代码","供应商名称","物流模式"
				};
		String[] columns = {"workCenter", "distriBcycleSeqNo", "distriBatchNo", "batchDiff", "partNo",
				"partShortNo","boxNum","requireNum","distriBoxNum","distriNum",
				"location","partName",
				"preparePerson","distriPerson","supplierNo","supplierName","planCodeType"
				};
		int[] widths = {100, 100, 100, 100, 100,
				 100, 100, 100, 100, 100,
				 100, 100,
				 100, 100, 100, 100, 100
				 };
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "配送"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
}
