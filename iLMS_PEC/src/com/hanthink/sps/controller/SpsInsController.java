package com.hanthink.sps.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.sps.manager.SpsInsManager;
import com.hanthink.sps.manager.SpsVehQueueManager;
import com.hanthink.sps.model.SpsInsDetailModel;
import com.hanthink.sps.model.SpsInsModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @ClassName: SpsInsController
 * @Description: SPS分拣指示票
 * @author dtp
 * @date 2018年11月25日
 */
@Controller
@RequestMapping("/sps/spsIns")
public class SpsInsController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(SpsInsController.class);
	
	/**
	 * 箱子默认269
	 */
	public static final Integer maxMouldPlaceSPS_XH =  266;
	
	/**
	 * 箱子默认206
	 */
	public static final Integer maxMouldPlaceSPS_TC =  206;
	
	@Resource
	private SpsInsManager spsInsManager; 
	@Resource
	private SpsVehQueueManager spsVehQueueManager;
	
	/**
	 * @Description: SPS分拣指示票查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("querySpsInsPage")
	public @ResponseBody PageJson querySpsInsPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsInsModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsInsModel> pageList = spsInsManager.querySpsInsPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: SPS指示票导出excel
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("downloadSpsIns")
	public void downloadSpsIns(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") SpsInsModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "SPS指示票" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsInsModel> pageList = spsInsManager.querySpsInsPage(model, page);
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
		List<SpsInsModel> list = spsInsManager.querySpsInsList(model);
		if(null != list) {
			String[] headers = {"指示票号", "信息点", "VIN", "车型",
					"车身序号", "过点时间", "票据模板", "打印机", 
					"打印状态", "打印时间"};
			String[] columns = {"insNo", "planCodeDesc", "vin", "modelCode",
					"wcSeqno", "passTime", "mouldName", "printerName",
					"printStatus" ,"printTime"};
			int[] widths = {100, 60, 120, 60,
					80, 100, 100, 80,
					60, 100};
			if(curNum <= sysMaxNum) {
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}else {
				//ExcelExportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}
			
		}
	}
	
	/**
	 * @Description: SPS指示票明细查询 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("querySpsInsDetailPage")
	public @ResponseBody PageJson querySpsInsDetailPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsInsDetailModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsInsDetailModel> pageList = spsInsManager.querySpsInsDetailPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: SPS指示票明细导出
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@RequestMapping("downloadSpsInsDetail")
	public void downloadSpsInsDetail(HttpServletRequest request,HttpServletResponse response) {
		
	}
	
	/**
	 * @Description: SPS指示票打印 
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws JRException 
	 * @throws IOException 
	 * @date: 2018年12月8日
	 */
	@RequestMapping("printSpsIns")
	public void printIns(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		String insNoStr = RequestUtil.getString(request, "insNoStr");
		String mouldCodeStr = RequestUtil.getString(request, "mouldCodeStr");
		String[] insNoArr = insNoStr.split(",");
		String[] mouldCodeArr = mouldCodeStr.split(",");
		try {
			if(null != insNoArr && null != mouldCodeArr && (insNoArr.length == mouldCodeArr.length)) {
				//打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
				//更新打印状态list
				List<SpsInsModel> list_printInfo = new ArrayList<SpsInsModel>();
				for (int i = 0; i < insNoArr.length; i++) {
					SpsInsModel model_q = new SpsInsModel(); 
					model_q.setInsNo(insNoArr[i]);
					model_q.setPrintIp(RequestUtil.getIpAddr(request));
					model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
					model_q.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					list_printInfo.add(model_q);
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "sps" + File.separator 
							+ mouldCodeArr[i] + ".jasper";
					//分页
					String filenurl_2 = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "sps" + File.separator 
							+ mouldCodeArr[i] + "2" + ".jasper";
					//查询分拣单最大位置号,判断是否分页打印
					SpsMouldModel modelCodeModel = new SpsMouldModel();
					modelCodeModel.setMouldCode(mouldCodeArr[i]);
					modelCodeModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					SpsMouldModel spsModel = spsVehQueueManager.queryMaxPlaceByModelCode(modelCodeModel);
					Integer maxMouldPlace =  206;
					if("SPS_XH".equals(mouldCodeArr[i])) {
						maxMouldPlace = SpsInsController.maxMouldPlaceSPS_XH;//箱子模板给个固定值266
					}
					if("SPS_TC".equals(mouldCodeArr[i])) {
						maxMouldPlace = SpsInsController.maxMouldPlaceSPS_TC;//台车模板给固定值206
					}
					if(null != spsModel && StringUtils.isNotBlank(spsModel.getMouldHeadPlace())) {
						maxMouldPlace = Integer.valueOf(spsModel.getMouldHeadPlace());//根据模板获取最大位置号
					}
					InputStream file = new FileInputStream(filenurl);
					InputStream file_2 = new FileInputStream(filenurl_2);
					//参数
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					HashMap<String, Object> parameters_2 = new HashMap<String, Object>();
					//查询打印信息
					List<SpsInsDetailModel> detailList = spsInsManager.querySpsInsDetailList(model_q);
					//设置打印值
					List<Integer> mouldPlaceList = new ArrayList<Integer>();
					for (int j = 0; j < detailList.size(); j++) {
						SpsInsDetailModel sps = detailList.get(j);
						mouldPlaceList.add(Integer.valueOf(sps.getMouldPlace()));
						if(21 == sps.getMouldPlace()) {
							//打印二维码
							parameters.put("s21", MakeQrcodeImages.getQrCodeImage(sps.getShowValue(), "80", "80"));
							parameters_2.put("s21", MakeQrcodeImages.getQrCodeImage(sps.getShowValue(), "80", "80"));
						}else {
							parameters.put("s" + sps.getMouldPlace(), sps.getShowValue());
							parameters_2.put("s" + sps.getMouldPlace(), sps.getShowValue());
						}
					}
					JRDataSource jRDataSource = new JREmptyDataSource();
					JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
					JasperPrint jasperPrint_2 = JasperFillManager.fillReport(file_2, parameters_2, jRDataSource);
					JasperPrintList.add(jasperPrint);
					//判断是否需要分页
					Integer mouldPlace = Collections.max(mouldPlaceList);
					if( maxMouldPlace < mouldPlace) {
						JasperPrintList.add(jasperPrint_2);
					}
				}
				//防止size = 0 抛异常
				if(JasperPrintList.size() > 0) {
					response.setContentType("application/pdf");
					response.setHeader("Content-disposition", "inline;");
					JRPdfExporter exporter = new JRPdfExporter();
					OutputStream out = response.getOutputStream();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
					exporter.exportReport();
					spsInsManager.updatePrintInfo(list_printInfo);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"系统错误,请联系管理员",e.getMessage(),ResultMessage.FAIL);
		}
		
		
	}
	
	/**
	 * @Description: 加载打印机下拉框
	 * @param: @param request
	 * @param: @param response
	 * @param: @return    
	 * @return: List<T>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("loadPrinterComboData")
	public @ResponseBody List<T> loadPrinterComboData(HttpServletRequest request,HttpServletResponse response){
		//打印机类型
		PubDataDictModel model = new PubDataDictModel();
		String printGroup = RequestUtil.getString(request, "printGroup");
		model.setCodeType(printGroup);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<PubDataDictModel> list = spsInsManager.loadPrinterComboDataByType(model);
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
	 * @Description: 修改sps指示票打印状态
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年12月28日
	 */
	@RequestMapping("updateSpsInsPrintStatus")
	public void updateSpsInsPrintStatus(HttpServletRequest request,HttpServletResponse response,
			@RequestBody SpsInsModel[] models) throws IOException {
		IUser user = ContextUtil.getCurrentUser();
		//model.setLastModifiedIp(RequestUtil.getIpAddr(request));
		//model.setLastModifiedUser(user.getAccount());
		try {
			spsInsManager.updateSpsInsPrintStatus(models, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(), "操作成功", ResultMessage.SUCCESS);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"系统错误,请联系管理员",ResultMessage.FAIL);
		}
		
	}
	
}
