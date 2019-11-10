package com.hanthink.sps.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hanthink.sps.manager.SpsVehQueueManager;
import com.hanthink.sps.model.SpsVehQueueModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.jasperreports.engine.JRException;

/**
 * @ClassName: SpsVehQueueController
 * @Description: SPS过点车序查询
 * @author dtp
 * @date 2018年10月16日
 */
@Controller
@RequestMapping("/sps/spsVehQueue")
public class SpsVehQueueController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(SpsVehQueueController.class);

	@Resource
	private SpsVehQueueManager spsVehQueueManager;
	
	/**
	 * @Description: SPS过点车序查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("querySpsVehQueuePage")
	public @ResponseBody PageJson querySpsVehQueuePage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsVehQueueModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsVehQueueModel> pageList = spsVehQueueManager.querySpsVehQueuePage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: SPS过点车序导出excel
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("downloadSpsVehQueue")
	public void downloadSpsVehQueue(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") SpsVehQueueModel model) {
		String exportFileName = "SPS过点车序" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsVehQueueModel> pageList = spsVehQueueManager.querySpsVehQueuePage(model, page);
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
		int sysMaxNum_T = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000);
		if(curNum > sysMaxNum_T){
			ExcelUtil.exportNumError(sysMaxNum_T, request, response);
			return;
		}
		List<SpsVehQueueModel> list = spsVehQueueManager.querySpsVehQueueList(model);
		if(null != list) {
			String[] headers = {"信息点", "生产单号","过点时间", "VIN", "车型", 
					"车身序号", "推算状态", "推算时间"};
			String[] columns = {"planCodeDesc", "erpOrderNo","passTime", "vin", "modelCode", 
					"wcSeqno", "execStatus", "execTime"};
			int[] widths = {80, 100, 100, 100, 60, 
					80, 80, 100};
			if(curNum <= sysMaxNum) {
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}else {
				//ExcelExportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}
		}
		
	}
	
	/**
	 * @Description: 加载SPS信息点下拉框   
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("loadPlanCodeComboData")
	public @ResponseBody List<T> loadPlanCodeComboData(HttpServletRequest request,HttpServletResponse response) {
		PubPlanCodeModel model = new PubPlanCodeModel();
		String planCodeType = RequestUtil.getString(request, "planCodeType");
		model.setPlanCodeType(planCodeType);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		List<PubPlanCodeModel> list = spsVehQueueManager.loadPlanCodeComboData(model);
		if(null != list) {
			for (PubPlanCodeModel pubPlanCodeModel : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("label", pubPlanCodeModel.getPlanCodeDesc());
				map.put("value", pubPlanCodeModel.getPlanCode());
				dataDictList.add(map);
			}
		}
		return new PageJson(dataDictList).getRows();
	}
	
	/**
	 * 加载票据模板下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("loadSpsMouldComboData")
	public @ResponseBody List<T> loadSpsMouldComboData(HttpServletRequest request,HttpServletResponse response){
		//打印机类型
		SpsVehQueueModel model = new SpsVehQueueModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<PubDataDictModel> list = spsVehQueueManager.loadSpsMouldComboData(model);
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		if(null != list && list.size() > 0) {
			for (PubDataDictModel pubDataDictModel : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("label", pubDataDictModel.getCodeValueName());
				map.put("value", pubDataDictModel.getCodeValue());
				dataDictList.add(map);
			}
		}
		return new PageJson(dataDictList).getRows();
	}
	
	
	/**
	 * @param request SPS试打印
	 * @param response
	 * @throws JRException
	 * @throws IOException
	 */
	@RequestMapping("printTestIns")
	public void printTestIns(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		String mouldId = RequestUtil.getString(request, "mouldId");
		String planCodeStr = RequestUtil.getString(request, "planCodeStr");
		String erpOrderNoStr = RequestUtil.getString(request, "erpOrderNoStr");
		String[] planCodeArr = planCodeStr.split(",");
		String[] erpOrderNoArr = erpOrderNoStr.split(",");
		SpsVehQueueModel testPrintModel = new SpsVehQueueModel();
		try {
			List<SpsVehQueueModel> list = new ArrayList<SpsVehQueueModel>();
			if(null != mouldId && null != planCodeArr && null != erpOrderNoArr 
					&& planCodeArr.length == erpOrderNoArr.length) {
				Map<String, String> map = new HashMap<String, String>();
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				testPrintModel.setUuid(uuid);
				testPrintModel.setMouldId(mouldId);
				map.put("uuid", uuid);
				for (int j = 0; j < erpOrderNoArr.length; j++) {
					SpsVehQueueModel model = new SpsVehQueueModel();
					model.setId(UniqueIdUtil.getSuid());
					model.setMouldId(mouldId);
					model.setPlanCode(planCodeArr[j]);
					model.setErpOrderNo(erpOrderNoArr[j]);
					model.setUuid(uuid);
					list.add(model);
				}
				spsVehQueueManager.printTestIns(list, request, response, map, testPrintModel);
			}else {
				writeResultMessage(response.getWriter(),"系统错误,请联系管理员","",ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			writeResultMessage(response.getWriter(),"系统错误,请联系管理员",e.getMessage(),ResultMessage.FAIL);
		}
		
	}
	
	
}
