package com.hanthink.jit.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.jit.manager.JitInsManager;
import com.hanthink.jit.model.JitInsModel;
import com.hanthink.pub.model.PubPrintInsModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @ClassName: JitInsController
 * @Description: 配送单查询
 * @author dtp
 * @date 2018年10月8日
 */
@Controller
@RequestMapping("/jit/jitIns")
public class JitInsController extends GenericController{
	
	@Resource 
	private JitInsManager jitInsManager; 
	
	/**
	 * @Description: 配送单查询  
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@RequestMapping("queryJitInsPage")
	public @ResponseBody PageJson queryJitInsPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JitInsModel model) {
		/*HttpSession session = request.getSession();
		session.setAttribute(SessionKey.JIT_INS_QUERYFILTER, model);*/
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInsModel> pageList = jitInsManager.queryJitInsPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 配送单导出
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@RequestMapping("downloadJitIns")
	public void downloadJitIns(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitInsModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "拉动配送单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInsModel> pageList = jitInsManager.queryJitInsPage(model, page);
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		List<JitInsModel> list = jitInsManager.queryJitInsList(model);
		if(null != list) {
			/*String[] headers = {"车间", "信息点", "出货仓库", "物流单号", "是否急件",
					"备件批次", 
					 "配送批次", "拣货工程", "配送工程", "台车编号",
					"备件状态", "备件时间", "打印机", "组单方式"
					,"创建时间", "配送单号"};
			String[] columns = {"workcenter", "planCodeDesc", "shipDepot", "orderNo", "insNoDiffseq",
					"prepareProductSeqno",
					"distriProductSeqno", "preparePerson", "distriPerson", "carpool",
					"prepareStatusDesc", "prepareTime", "printerName", "genInsWay",
					"creationTime", "insNo"};
			int[] widths = {60, 80, 80, 100, 80,
					80, 
					80, 80, 80, 80,
					80, 100, 100, 80,
					120, 120};*/
			String[] headers = {"车间", "信息点", "出货仓库", "物流单号", "是否急件",
					"备件批次", 
					 "配送批次", "拣货工程", "配送工程", "台车编号",
					"备件状态", "打印机", "组单方式"
					,"创建时间", "配送单号"};
			String[] columns = {"workcenter", "planCodeDesc", "shipDepot", "orderNo", "insNoDiffseq",
					"prepareProductSeqno",
					"distriProductSeqno", "preparePerson", "distriPerson", "carpool",
					"prepareStatusDesc", "printerName", "genInsWay",
					"creationTime", "insNo"};
			int[] widths = {60, 80, 80, 100, 80,
					80, 
					80, 80, 80, 80,
					80, 100, 80,
					120, 120};
			if(curNum <= sysMaxNum) {
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}else {
				//ExcelExportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}
		
		}
	}
	
	/**
	 * @Description: 配送单明细查询 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@RequestMapping("queryJitInsDetailPage")
	public @ResponseBody PageJson queryJitInsDetailPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JitInsModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInsModel> pageList = jitInsManager.queryJitInsDetailPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: 配送单打印
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws JRException 
	 * @throws Exception 
	 * @date: 2018年10月9日
	 */
	@RequestMapping("jitInsPrint")
	public void jitInsPrint(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String insNoStr = RequestUtil.getString(request, "insNoStr");
		String[] insNoArr = insNoStr.split(",");
		if(null != insNoArr && insNoArr.length > 0) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			//更新打印状态list
			List<JitInsModel> list_printInfo = new ArrayList<JitInsModel>();
			for (int i = 0; i < insNoArr.length; i++) {
				JitInsModel model_q = new JitInsModel();
				//生成多个InputStream file,防止抛异常
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
						+ File.separator +"ireport" + File.separator + "jit" + File.separator + "JIT_INS.jasper";
				InputStream file = new FileInputStream(filenurl);
				String pagePath = FileUtil.getClassesPath() + File.separator + "template" 
						+ File.separator + "page" + File.separator + "jit_ins.jpg";
				BufferedImage img = ImageIO.read(new FileInputStream(pagePath));
				model_q.setInsNo(insNoArr[i]);
				//查询配送单明细
				List<JitInsModel> detailList = jitInsManager.queryJitInsDetailList(model_q);
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				List<PubPrintInsModel> list = new ArrayList<PubPrintInsModel>();
				model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
				model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
				list_printInfo.add(model_q);
				//行数统计
				int rowCount = 0;
				//每列最多显示行数
				int maxRowSize = 30;
				//页码
				int page = 1;
				int size = detailList.size();
				//判断打印张数
				int totalPage = 1;
				//整除
				if(size%(maxRowSize*2) == 0) {
					totalPage = size/(maxRowSize*2);
				}else if(size%(maxRowSize*2) != 0) {
					totalPage = size/(maxRowSize*2) + 1;
				}
				//目的填充空白行
				int totalRowCount = totalPage * maxRowSize;
				
				if(null != detailList && detailList.size() > 0) {
					for (int j = 0; j < detailList.size(); j++) {
						//行数自增
						rowCount++;
						JitInsModel model = detailList.get(j);
						if(rowCount == 1) {
							//配送单头信息
							parameters.put("preparePerson", model.getPreparePerson());//拣货工程
							parameters.put("distriPerson", model.getDistriPerson());
							parameters.put("totalCarPool", model.getTotalCarPool());
							parameters.put("maxCarPool", model.getMaxCarPool());
							parameters.put("printTime", model.getPrintTime());
							parameters.put("prepareBatchNo", model.getPrepareBatchNo());
							parameters.put("jitInsPage", img);
							//按拣货工程组票,显示 * 
							if("2".equals(model.getGenInsWay())) {
								parameters.put("distriPerson", "*");
								parameters.put("totalCarPool", "*");
								parameters.put("maxCarPool", "*");
							}
							if(StringUtils.isNotEmpty(model.getCarpool())) {
								parameters.put("carpool", "(" + model.getCarpool() + ")");
							}else {
								parameters.put("carpool", "(" + "  " + ")");
							}
							
						}
						PubPrintInsModel bean = new PubPrintInsModel();
						if(rowCount <= maxRowSize * ( 2 * page - 1 )) {
							bean.setNo("" + rowCount);
							bean.setPartShortNo(model.getPartShortNo());
							bean.setStorage(model.getStorage());
							bean.setLocation(model.getLocation());
							bean.setCarpool(model.getCarpool());
							bean.setXs(model.getXs());
						}else if(rowCount > maxRowSize * ( 2 * page - 1 ) && rowCount <= maxRowSize * 2 * page) {
							bean = list.get(rowCount - 1 - maxRowSize * page );
							bean.setNo2("" + rowCount);
							bean.setStorage2(model.getStorage());
							bean.setPartShortNo2(model.getPartShortNo());
							bean.setLocation2(model.getLocation());
							bean.setCarpool2(model.getCarpool());
							bean.setXs2(model.getXs());
						}
						if(null != bean && rowCount <= maxRowSize * ( 2 * page - 1 )) {
							list.add(bean);
						}
						//换页
						if(rowCount == maxRowSize * 2 * page) {
							page++;
						}
					}
					//填充空白行
					if((totalRowCount - list.size()) > 0) {
						int blackRow = totalRowCount - list.size();
						for (int k = 0; k < blackRow; k++) {
							PubPrintInsModel insBlackBean = new PubPrintInsModel(); 
							list.add(insBlackBean);
						}
					}
					JRDataSource jRDataSource;
					if (list.size() > 0) {
						jRDataSource = new JRBeanCollectionDataSource(list);
					} else {
						jRDataSource = new JREmptyDataSource();
					}
					JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
					JasperPrintList.add(jasperPrint);
				}
				
			}
			if(JasperPrintList.size() > 0) {
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline;");
				JRPdfExporter exporter = new JRPdfExporter();
				OutputStream out = response.getOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
				exporter.exportReport();	
				//更新打印状态
				jitInsManager.updatePrintInfo(list_printInfo);
			}
		}
		
	}
	
	/**
	 * @Description: 配送明细导出  
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月6日
	 */
	@RequestMapping("downloadJitInsDetail")
	public void downloadJitInsDetail(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitInsModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "拉动配送单明细" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		List<JitInsModel> list = jitInsManager.downloadJitInsDetail(model);
		if(null != list) {
			int curNum = list.size();
			if(0 == curNum){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
			if(curNum > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"车间", "信息点", "出货仓库", "物流单号", "零件号",
					"零件简号", "是否急件", "备件批次", "拣货工程",
					 "台车编号", "组单方式", "计划备件箱数", "实际备件箱数",
					"备件状态", "配送批次", "配送工程", "计划配送箱数",
					"实际配送箱数", "配送状态", "打印机", "配送时间", 
					"配送单号"};
			String[] columns = {"workcenter", "planCodeDesc", "shipDepot", "orderNo", "partNo",
					"partShortNo", "insNoDiffseq", "prepareBatchNo", "preparePerson",
					"carpool", "genInsWay", "planPrepareXs", "actualPrepareXs",
					"prepareStatus", "distriProductSeqno", "distriPerson", "planDistriXs",
					"actualDistriXs", "fdistriStatus", "printerName", "distriTime",
					"insNo"};
			int[] widths = {60, 60, 60, 100, 80,
					60, 60, 60, 60,
					60, 60, 60, 60,
					60, 60, 60, 60,
					60, 60, 60, 80,
					80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			
		}
		
	}
	
	
}
