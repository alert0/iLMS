package com.hanthink.jit.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.jit.manager.JitInsManager;
import com.hanthink.jit.manager.JitOrderManager;
import com.hanthink.jit.model.JitInsModel;
import com.hanthink.jit.model.JitLabelModel;
import com.hanthink.jit.model.JitOrderModel;
import com.hanthink.pub.manager.PubDataDictManager;
import com.hanthink.pub.model.PubFactoryInfoModel;
import com.hanthink.pub.model.PubPrintInsModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.pup.manager.PupDcsManager;
import com.hanthink.pup.model.PupDcsModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hanthink.util.print.PrintOrderUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
import com.mysql.jdbc.StringUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @ClassName: JitOrderController
 * @Description: 拉动订单查询
 * @author dtp
 * @date 2018年9月28日
 */
@Controller
@RequestMapping("/jit/jitOrder")
public class JitOrderController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(JitOrderController.class);
	
	//封条号数量
	private static final int JIT_SEAL_NUM = 4;
	
	@Resource 
	private JitOrderManager jitOrderManager;
	@Resource 
	private JitInsManager jitInsManager; 
	@Resource
	private PubDataDictManager pubDataDictManager;
	@Resource
	PupDcsManager pupDcsManager;
	
	/**
	 * @Description: 拉动订单查询  
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@RequestMapping("queryJitOrderPage")
	public @ResponseBody PageJson queryJitOrderPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JitOrderModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitOrderModel> pageList = jitOrderManager.queryJitOrderPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 拉动订单明细查询  
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@RequestMapping("queryJitOrderDetailPage")
	public @ResponseBody PageJson queryJitOrderDetailPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JitOrderModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		PageList<JitOrderModel> pageList = jitOrderManager.queryJitOrderDetailPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 拉动订单导出excel
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@RequestMapping("downloadJitOrder")
	public void downloadJitOrder(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitOrderModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "拉动订单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitOrderModel> pageList = jitOrderManager.queryJitOrderPage(model, page);
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
		//int sysMaxNum_T = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000);
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		List<JitOrderModel> list = jitOrderManager.queryJitOrderList(model);
		if(null != list) {
			String[] headers = {"车间", "信息点", "出货仓库", "物流单号", "是否急件",
					"供应商代码", "供应商名称", 
					"计划取货日期",
					"发车批次进度","开始备件批次进度", "结束备件批次进度","发货批次进度",
					"到货批次进度", "看板名称","打印状态", "打印时间", 
					"收货状态", "创建时间"};
			String[] columns = {"workcenter", "planCodeDesc", "shipDepot", "orderNo", "orderNoDiffseq",
					"supplierNo", "supplierName",
					"prepareTime",
					"dispatchProductSeqno", "sprepareProductSeqno", "eprepareProductSeqno","deliveryProductSeqno", 
					"arriveProductSeqno" ,"kbName", "printStatus", "printTime",
					"arriveStatus", "creationTime"};
			int[] widths = {60, 80, 100, 100, 80,
					90, 100, 
					100,
					100, 120, 120, 100,
					100, 80, 80, 80,
					90, 100};
			if(curNum <= sysMaxNum) {
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}else {
				//ExcelExportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}
		}
		
	}
	
	/**
	 * @Description: 拉动订单明细导出excel
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@RequestMapping("downloadJitOrderDetail")
	public void downloadJitOrderDetail(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitOrderModel model) {
		String exportFileName = "拉动订单明细" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		//拉动订单明细
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<JitOrderModel> list = jitOrderManager.queryJitOrderDetailList(model);
		if(null != list) {
			int curNum = list.size();
			if(0 == curNum){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
			//int sysMaxNum_T = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000);
			if(curNum > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			if(null != list) {
				String[] headers = {"订单号", "出货仓库", "最后备件批次",  
						"发货批次", "行号", "供应商代码",
						"零件号", "简号","零件名称","配送地址",
						"收容数","箱数", "订购量", "收货状态"};
				String[] columns = {"orderNo", "shipDepot", "eprepareBatchNo", 
						"deliveryBatchNo","orderRowno", "supplierNo",
						"partNo", "partShortNo","partName", "location",
						"standardPackage" ,"boxNum", "requireNum", "arriveStatus"};
				int[] widths = {100, 80, 100,
						80, 60, 80,
						100, 80, 100, 80,
						80, 60, 60, 80};
				if(curNum <= sysMaxNum) {
					ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
				}else {
					//ExcelExportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
				}
			}
			
		}
		
	}
	
	/**
	 * @Description: 拉动订单打印(A4)
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @throws Exception 
	 * @date: 2018年9月28日
	 */
	@RequestMapping("jitOrderPrint")
	public void jitOrderPrint(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//订单每页打印明细行数
		int pageSize = 18;
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String shipDepotTypeStr = RequestUtil.getString(request, "shipDepotTypeStr");
		String[] orderNoArr = orderNoStr.split(",");
		String[] shipDepotTypeArr = shipDepotTypeStr.split(",");
		if(null != orderNoArr && orderNoArr.length > 0 && null != shipDepotTypeArr 
				&& shipDepotTypeArr.length == orderNoArr.length) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			//更新打印状态list
			List<JitOrderModel> list_printInfo = new ArrayList<JitOrderModel>();
			try {
				for (int i = 0; i < orderNoArr.length; i++) {
					JitOrderModel model_q = new JitOrderModel(); 
					//生成多个InputStream file,防止抛异常
					String filenurl = "";
					if("WX".equals(shipDepotTypeArr[i])) {
						filenurl = FileUtil.getClassesPath() + File.separator + "template" 
								+ File.separator +"ireport" + File.separator + "jit" + File.separator + "WX_ORDER.jasper";
					}else {
						filenurl = FileUtil.getClassesPath() + File.separator + "template" 
								+ File.separator +"ireport" + File.separator + "pub" + File.separator + "PR_ORDER.jasper";
					}
					InputStream file = new FileInputStream(filenurl);
					model_q.setOrderNo(orderNoArr[i]);
					model_q.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					//查询订单打印明细
					List<PubPrintOrderModel> detailList = jitOrderManager.queryJitOrderPrintDetailList(model_q);
					model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
					//更新打印状态list
					list_printInfo.add(model_q); 
					//工厂名称
					String factoryName = pubDataDictManager.queryDataDictCodeValueName("PUB_FACTORY_NAME", "2000");
					//订单类型 
					String orderType = pubDataDictManager.queryDataDictCodeValueName("PUB_ORDER_TYPE", "LC");
					//GACNE logo
					String logo = pubDataDictManager.queryDataDictCodeValueName("PUB_FACTORY_LOGO", "LOGO");
					PubFactoryInfoModel paramModel = new PubFactoryInfoModel();
					paramModel.setFactoryName(factoryName);
					paramModel.setOrderType(orderType);
					paramModel.setLogo(logo);
					//页数
					paramModel.setYs(((detailList.size()%pageSize== 0) ? detailList.size()/pageSize : detailList.size()/pageSize + 1) + "");
					JasperPrint jasperPrint = PrintOrderUtil.getJasPerPrintUtil(detailList, file, paramModel);
					if(null != jasperPrint) {
						JasperPrintList.add(jasperPrint); 
					}   
				}
				PrintOrderUtil.exportReportOrderUtil(response, JasperPrintList);
				jitOrderManager.updatePrintInfo(list_printInfo);
			} catch (Exception e) { 
				e.printStackTrace();
				log.error(e.toString()); 
				String resultMsg="打印失败";
				writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
			}
			
		}
	}
	
	/**
	 * @Description: 拉动订单打印(三联纸) 
	 * @param: @param request
	 * @param: @param response
	 * @param: @throws IOException    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月25日
	 */
	@RequestMapping("jitTriOrderPrint")
	public void jitTriOrderPrint(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//订单每页打印明细行数
		int pageSize = 18;
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String[] orderNoArr = orderNoStr.split(",");
		if(null != orderNoArr && orderNoArr.length > 0) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			//更新打印状态list
			List<JitOrderModel> list_printInfo = new ArrayList<JitOrderModel>();
			try {
				for (int i = 0; i < orderNoArr.length; i++) {
					JitOrderModel model_q = new JitOrderModel(); 
					//生成多个InputStream file,防止抛异常
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "pub" + File.separator + "ORDER.jasper";
					InputStream file = new FileInputStream(filenurl);
					model_q.setOrderNo(orderNoArr[i]);
					model_q.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
					model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
					//查询订单打印明细
					List<PubPrintOrderModel> detailList = jitOrderManager.queryJitOrderPrintDetailList(model_q);
					//更新打印状态list
					list_printInfo.add(model_q); 
					//工厂名称
					String factoryName = pubDataDictManager.queryDataDictCodeValueName("PUB_FACTORY_NAME", "2000");
					//订单类型 
					String orderType = pubDataDictManager.queryDataDictCodeValueName("PUB_ORDER_TYPE", "LC");
					//GACNE logo
					String logo = pubDataDictManager.queryDataDictCodeValueName("PUB_FACTORY_LOGO", "LOGO");
					PubFactoryInfoModel paramModel = new PubFactoryInfoModel();
					paramModel.setFactoryName(factoryName);
					paramModel.setOrderType(orderType);
					paramModel.setLogo(logo);
					//页数
					paramModel.setYs(((detailList.size()%pageSize== 0) ? detailList.size()/pageSize : detailList.size()/pageSize + 1) + "");
					JasperPrint jasperPrint = PrintOrderUtil.getJasPerPrintUtil(detailList, file, paramModel);
					if(null != jasperPrint) {
						JasperPrintList.add(jasperPrint); 
					}   
				}
				PrintOrderUtil.exportReportOrderUtil(response, JasperPrintList);
				jitOrderManager.updatePrintInfo(list_printInfo);
			} catch (Exception e) { 
				e.printStackTrace();
				log.error(e.toString()); 
				String resultMsg="打印失败";
				writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
			}
			
		}
	}
	
	/**
	 * @Description: 标签打印 
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws Exception 
	 * @date: 2018年10月4日
	 */
	@RequestMapping("jitOrderPrintLabel")
	public void jitOrderPrintLabel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String[] orderNoArr = orderNoStr.split(",");
		//更新打印状态list
		List<JitLabelModel> printInfo_List = new ArrayList<JitLabelModel>();
		List<JitLabelModel> list = new ArrayList<JitLabelModel>();
		List<JitLabelModel> list_label = new ArrayList<JitLabelModel>();
		if(null != orderNoArr && orderNoArr.length > 0) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			for (int i = 0; i < orderNoArr.length; i++) {
				JitLabelModel model = new JitLabelModel();
				model.setOrderNo(orderNoArr[i]);
				model.setPrintUser(ContextUtil.getCurrentUser().getAccount());
				model.setPrintUserIp(RequestUtil.getIpAddr(request));
				list = jitOrderManager.queryJitOrderPrintLabelList(model);
				//初始化看板张数
				int kbzs = 1;
				//更新打印状态model
				printInfo_List.add(model);
				//生成多个InputStream file,防止抛异常
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
						+ File.separator +"ireport" + File.separator + "jit" + File.separator + "JIT_LABEL.jasper";
				InputStream file = new FileInputStream(filenurl);
				Map<String, Object> parameters = new HashMap<String, Object>();
				for (int j = 0; j < list.size(); j++) {
					String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					JitLabelModel bean = list.get(j);
					//二维码
					StringBuffer qrCode = new StringBuffer();
					qrCode = JitOrderController.addEmptyStr(qrCode, bean.getPartNo());//零件号
					qrCode.append("#");
					qrCode = JitOrderController.addEmptyStr(qrCode, bean.getStandardPackage());//包装数
					qrCode.append("#");
					qrCode = JitOrderController.addEmptyStr(qrCode, bean.getPurchaseOrderno());//订单号
					qrCode.append("#");
					qrCode = JitOrderController.addEmptyStr(qrCode, bean.getOrderRowNo());//采购订单行号
					qrCode.append("#");
					qrCode = JitOrderController.addEmptyStr(qrCode, bean.getOrderNo());//物流单号
					qrCode.append("#");
					qrCode = JitOrderController.addEmptyStr(qrCode, bean.getPartShortNo());//简号
					qrCode.append("#");
					qrCode = JitOrderController.addEmptyStr(qrCode, "03");//订单类型
					qrCode.append("#");
					qrCode.append("");//追溯重要度等级
					qrCode.append("#");  
					qrCode = JitOrderController.addEmptyStr(qrCode, bean.getSupplierNo());//供应商代码
					qrCode.append("#");
					qrCode.append("");//供应商生产批次
					qrCode.append("#");
					qrCode = JitOrderController.addEmptyStr(qrCode, bean.getRequireNum());//同批次数量
					qrCode.append("#"); 
					if(StringUtils.isNullOrEmpty(bean.getUuid())) {
						qrCode.append(uuid);//UUID
						bean.setUuid(uuid);
					}else {
						qrCode.append(bean.getUuid());
					}
					bean.setQRCode(MakeQrcodeImages.getQrCodeImage(qrCode.toString(), "150", "150"));
					bean.setKbzs((kbzs++) + "/" + bean.getLabelPageNum());
					//看板张数超过零件标签数量,初始化为1
					if(kbzs > Integer.valueOf(bean.getLabelPageNum())) {
						kbzs = 1;
					}
				}
				JRDataSource jRDataSource;
				if (list.size() > 0) {   
					jRDataSource = new JRBeanCollectionDataSource(list);
				} else {
					jRDataSource = new JREmptyDataSource();
				}
				for (JitLabelModel mod : list) {
					list_label.add(mod);
				}
				JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
				JasperPrintList.add(jasperPrint);
			}
			//防止list=0抛异常
			if(JasperPrintList.size() > 0) {
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline;");
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				exporter.exportReport();	
				jitOrderManager.updatePrintStatus(printInfo_List, list_label);
			}
			
		}
		
	}
	
	/**
	 * @Description: 配送单打印 
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws Exception 
	 * @date: 2018年10月9日 
	 */
	@RequestMapping("jitOrderPrintIns") 
	public void jitOrderPrintIns(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String orderNoStr = RequestUtil.getString(request, "orderNoStr"); 
		String[] orderNoArr = orderNoStr.split(",");
		if(null != orderNoArr && orderNoArr.length > 0) {
			//通过物流单号查询配送单号
			List<JitInsModel> insNoList = jitOrderManager.queryInsNoByOrderNoArr(orderNoArr);
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			//更新打印状态list
			List<JitInsModel> list_printInfo = new ArrayList<JitInsModel>();
			if(null != insNoList && insNoList.size() > 0) {
				for (int i = 0; i < insNoList.size(); i++) {
					JitInsModel model_q = new JitInsModel();
					//生成多个InputStream file,防止抛异常
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "jit" + File.separator + "JIT_INS.jasper";
					InputStream file = new FileInputStream(filenurl);
					model_q.setInsNo(insNoList.get(i).getInsNo());
					//查询配送单明细
					List<JitInsModel> detailList = jitInsManager.queryJitInsDetailList(model_q);
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					List<PubPrintInsModel> list = new ArrayList<PubPrintInsModel>();
					model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
					list_printInfo.add(model_q);
					//行数统计
					int rowCount = 0;
					//每列最多显示行数
					int maxRowSize = 30;
					if(null != detailList && detailList.size() > 0) {
						for (int j = 0; j < detailList.size(); j++) {
							//行数自增
							rowCount++;
							JitInsModel model = detailList.get(j);
							if(rowCount == 1) {
								//配送单头信息
								parameters.put("preparePerson", model.getPreparePerson());
								parameters.put("distriPerson", model.getDistriPerson());
								parameters.put("carpool", model.getCarpool());
								parameters.put("printTime", model.getPrintTime());
								parameters.put("prepareBatchNo", model.getPrepareBatchNo());
							}
							PubPrintInsModel bean = new PubPrintInsModel();
							if(rowCount <= maxRowSize) {
								bean.setNo("" + rowCount);
								bean.setPartShortNo(model.getPartShortNo());
								bean.setStorage(model.getStorage());
								bean.setLocation(model.getLocation());
								bean.setRequireNum(model.getRequireNum());
							}else if(rowCount > maxRowSize && rowCount <= maxRowSize * 2) {
								bean = list.get(rowCount - maxRowSize -1 );
								bean.setNo2("" + rowCount);
								bean.setStorage2(model.getStorage());
								bean.setPartShortNo2(model.getPartShortNo());
								bean.setLocation2(model.getLocation());
								bean.setRequireNum2(model.getRequireNum());
							}
							if(null != bean && rowCount <= maxRowSize) {
								list.add(bean);
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
				//防止size = 0 抛异常
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
	}
	
	/**
	 * @Description: 托盘标签打印
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws JRException 
	 * @throws IOException 
	 * @date: 2018年10月23日
	 */
	@RequestMapping("jitOrderPrintTpLabel")
	public void jitOrderPrintTpLabel(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String[] orderNoArr = orderNoStr.split(",");
		if(null != orderNoArr && orderNoArr.length > 0) { 
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			for (int i = 0; i < orderNoArr.length; i++) {
				JitOrderModel model_q = new JitOrderModel();
				model_q.setOrderNo(orderNoArr[i]);
				//根据订单号查询托盘标签打印信息
				List<JitOrderModel> list = jitOrderManager.jitOrderPrintTpLabel(model_q);
				for (int j = 0; j < list.size(); j++) {
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "jit" + File.separator + "TP_LABEL.jasper";
					InputStream file = new FileInputStream(filenurl);
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					JitOrderModel model = list.get(j);
					parameters.put("supplierNo", model.getSupplierNo());
					parameters.put("supplierName", model.getSupplierName());
					parameters.put("unloadPort", model.getUnloadPort());
					parameters.put("distriBatchNo", model.getDistriProductSeqno());
					parameters.put("orderNo", model.getOrderNo());
					parameters.put("purchaseOrderno", model.getPurchaseOrderno());
					//托盘总数
					parameters.put("total", "" + list.size());
					//第 ?拖
					parameters.put("no", "" + (j + 1));
					JRDataSource jRDataSource;
					if (list.size() > 0) {
						jRDataSource = new JRBeanCollectionDataSource(list);
					} else {
						jRDataSource = new JREmptyDataSource();
					}
					JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters,
							jRDataSource);
					JasperPrintList.add(jasperPrint);
					
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
			}
			
		}else {
			writeResultMessage(response.getWriter(), "没有订单可打印", ResultMessage.FAIL);
		}
		
		
	}
	
	/**
	 * @Description: DCS单据打印
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年4月2日
	 */
	@RequestMapping("jitOrderPrintDCS")
	public void jitOrderPrintDCS (HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String orderNoStr = RequestUtil.getString(request, "orderNoStr"); 
			String[] orderNoArr = orderNoStr.split(",");
			if(null != orderNoArr && orderNoArr.length > 0) {
				//打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
				for (int i = 0; i < orderNoArr.length; i++) {
					JitOrderModel model_q = new JitOrderModel();
					//生成多个InputStream file,防止抛异常
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "jit" + File.separator + "JIT_DCS.jasper";
					InputStream file = new FileInputStream(filenurl);
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					
					model_q.setOrderNo(orderNoArr[i]);
					model_q.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
					model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
					
					// 拣取图片
	                String pagePath = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "page" + File.separator
	                        + "gacneLogo.jpg";
	             // 拣取图片
	                String pagePath1 = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "page" + File.separator
	                        + "gacne_logo.jpg";
	                BufferedImage logoImg = ImageIO.read(new FileInputStream(pagePath));
	                BufferedImage gacneLogoImg = ImageIO.read(new FileInputStream(pagePath1));
					
	                //获取DCS封条号
	                PupDcsModel pupDcsModel = new PupDcsModel();
	                
					//查询DCS订单打印明细
					List<PubPrintOrderModel> detailList = jitOrderManager.queryJitOrderDCSDetailList(model_q);
					if(null != detailList && detailList.size() > 0 ) {
						PubPrintOrderModel model_pr = detailList.get(0);
						
						//查询DCS封条号
						pupDcsModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
						//拉动订单获取1个封条号
						pupDcsModel.setSealNum(JIT_SEAL_NUM);
						pupDcsModel.setSessionNo("");
						List<PupDcsModel> sealList = pupDcsManager.querySealForList(pupDcsModel);
						//封条号
						if (sealList.size() < JIT_SEAL_NUM) {
			                throw new Exception("封条号不够");
			            }
						List<String> sealNoList_parameters = new ArrayList<String>();
						List<String> sealNoList_field = new ArrayList<String>();
						for (int j = 0; j < sealList.size(); j++) {
							if(j < JIT_SEAL_NUM/2) {
								sealNoList_parameters.add(sealList.get(j).getSealNo());
							}else {
								sealNoList_field.add(sealList.get(j).getSealNo());
							}
							pupDcsModel.setSealNo(sealList.get(j).getSealNo());
							//更新DCS任务的状态
							pupDcsModel.setCreateUser(ContextUtil.getCurrentUser().getAccount());
							jitOrderManager.updateDcsPrintStatus(pupDcsModel);
						}
						parameters.put("sealNo", org.apache.commons.lang3.StringUtils.join(sealNoList_parameters, ","));
						pupDcsModel.setSealNo(org.apache.commons.lang3.StringUtils.join(sealNoList_parameters, ","));
						//供应商封条号
						model_pr.setSealNo(org.apache.commons.lang3.StringUtils.join(sealNoList_field, ","));
						
						StringBuffer planSheetNo = new StringBuffer();
						planSheetNo.append("LD");
						planSheetNo.append("-");
						planSheetNo.append("2000");
						planSheetNo.append("-");
						planSheetNo.append(model_pr.getWorkDay());
						planSheetNo.append("-");
						planSheetNo.append((i+1)+"");
						//图片
						parameters.put("gacneLogoImg", gacneLogoImg);
						parameters.put("logoImg", logoImg);
						//路线名称
						String routeCode = model_pr.getSupplierNo() + "（拉动线）";
						parameters.put("planSheetNo", planSheetNo.toString());
						//路线名称
						parameters.put("routeCode", routeCode);
						//路线代码
						parameters.put("route", model_pr.getRoute());
						//发车时间1
						parameters.put("planStartDate", model_pr.getDispatchTime());
						//回厂时间1
						parameters.put("planEndDate", model_pr.getArriveTime());
						//发车批次
						parameters.put("planStartTime", model_pr.getDispatchProductSeqno());
						//回厂批次
						parameters.put("planEndTime", model_pr.getArriveBatch());
						//工作日
						//parameters.put("workDay", model_pr.getWorkDay());
						//取货家数
						parameters.put("supPickNum", detailList.size()+"");
						//工厂名
						parameters.put("factoryName", "广汽新能源");
						//二维码
						parameters.put("barCode", MakeQrcodeImages.getQrCodeImage(model_pr.getSupplierNo() + "#" + planSheetNo.toString()
							+ "#" + UUID.randomUUID().toString().replaceAll("-", ""), "100", "100"));
						JRDataSource jRDataSource;
						if (detailList.size() > 0) {
							jRDataSource = new JRBeanCollectionDataSource(detailList);
						} else {
							jRDataSource = new JREmptyDataSource();
						}
						JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
						if(null != jasperPrint) {
							JasperPrintList.add(jasperPrint); 
						}   
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
				}else {
					throw new Exception("系统错误,请联系管理员");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString()); 
			String resultMsg="打印失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 标签打印null换成""
	 * @param qrCode
	 * @param str
	 * @author dtp
	 * @return
	 */
	private static StringBuffer addEmptyStr(StringBuffer qrCode, String str) {
		if(StringUtils.isNullOrEmpty(str)) {
			qrCode.append("");
		}else {
			qrCode.append(str);
		}
		return qrCode;
	}
	
}