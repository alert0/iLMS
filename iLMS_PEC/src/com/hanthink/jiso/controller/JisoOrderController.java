package com.hanthink.jiso.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.hanthink.business.util.SessionKey;
import com.hanthink.jiso.manager.JisoInsManager;
import com.hanthink.jiso.manager.JisoOrderManager;
import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoOrderDetailModel;
import com.hanthink.jiso.model.JisoOrderModel;
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
import com.hotent.base.core.util.string.StringUtil;
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
 * @ClassName: JisoOrderController
 * @Description: 厂外同步订单
 * @author dtp
 * @date 2018年9月17日
 */
@Controller
@RequestMapping("/jiso/order")
public class JisoOrderController extends GenericController{
	
	//封条号数量
	private static final int JISO_SEAL_NUM = 4;
	
	private static Logger log = LoggerFactory.getLogger(JisoOrderController.class);

	@Resource 
	private JisoOrderManager jisoOrderManager;
	@Resource
	private PubDataDictManager pubDataDictManager;
	@Resource 
	private JisoInsManager jisoInsManager;
	@Resource
	PupDcsManager pupDcsManager;
	
	/**
	 * @Description: 查询厂外同步订单 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@RequestMapping("queryJisoOrderPage")
	public @ResponseBody PageJson queryJisoOrderPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JisoOrderModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JisoOrderModel> pageList = jisoOrderManager.queryJisoOrderPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: 根据订单号查询零件明细  
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@RequestMapping("queryJisoOrderDetailPageByOrderNo")
	public @ResponseBody PageJson queryJisoOrderDetailPageByOrderNo(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JisoOrderModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		model.setCurFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		PageList<JisoOrderDetailModel> pageList = jisoOrderManager.queryJisoOrderDetailPageByOrderNo(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 厂外同步订单导出  
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@RequestMapping("downloadJisoOrder")
	public void downloadJisoOrder(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JisoOrderModel model) {
		String exportFileName = "厂外同步订单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JisoOrderModel> pageList = jisoOrderManager.queryJisoOrderPage(model, page);
		//判断记录是否超过系统允许数量
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); //获取系统所允许的最大导出数量
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		List<JisoOrderModel> list = jisoOrderManager.queryJisoOrderList(model);
		if(null != list) {
			String[] headers = {"供应商代码", "出货地代码", "到货仓库", "装车代码", "车次", "物流单号", "订单号",
					"发车取货批次进度",
					"备件批次进度", "发货批次进度", "到货批次进度","配送批次进度",
					"打印状态",
					"打印时间","是否手工组单","手工组单人","手工组单申请时间","创建时间"};
			String[] columns = {"supplierNo", "supFactory", "arrDepot", "routeCode", "carBatch", "orderNo", "purchaseOrderno",
					"dispatchProductSeqno",
					"prepareProductSeqno", "deliveryProductSeqno","arriveProductSeqno", "distriProductSeqno",
					"printStatus",
					"printTime","isManuDeal","manuReqUser","manuReqTime","creationTime"};
			int[] widths = {80, 80, 80, 80, 60, 100, 100,
					100,
					80, 80, 80, 80,
					80,
					100, 80, 80, 110, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
		
	}
	
	/**
	 * @Description: 厂外同步订单明细导出   
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@RequestMapping("downloadJisoOrderDetail")
	public void downloadJisoOrderDetail(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JisoOrderModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "厂外同步订单明细" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		List<JisoOrderDetailModel> list = jisoOrderManager.downloadJisoOrderDetail(model);
		//判断记录是否超过系统允许数量
		if(null != list) {
			int curNum = list.size();
			if(0 == curNum){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); //获取系统所允许的最大导出数量
			if(curNum > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"订单号",  "指示票号", "供应商代码","出货地代码", "到货仓库", "简号", "零件号", "零件名称",
					"数量"};
			String[] columns = {"orderNo", "insNo", "supplierNo", "supFactory", "arrDepot", "partShortNo", "partNo", "partName",
					"requireNum"};
			int[] widths = {100, 100, 80, 80, 80, 60, 150, 150,
					60};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
		
	}
	
	/**
	 * @Description: 厂外同步订单打印(三联纸)
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws Exception 
	 * @date: 2018年9月17日
	 */
	@RequestMapping("jisoOrderPrint")
	public void jisoOrderPrint(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//订单每页打印明细行数
		int pageSize = 18;
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String[] orderNoArr = orderNoStr.split(",");
		//更新打印状态list
		List<JisoOrderDetailModel> printInfo_List = new ArrayList<JisoOrderDetailModel>();
		if(null != orderNoArr && orderNoArr.length > 0) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			for (int i = 0; i < orderNoArr.length; i++) {
				JisoOrderDetailModel model_q = new JisoOrderDetailModel();
				//生成多个InputStream file,防止抛异常
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
						+ File.separator +"ireport" + File.separator + "pub" + File.separator + "ORDER.jasper";
				InputStream file = new FileInputStream(filenurl);
				model_q.setOrderNo(orderNoArr[i]);
				model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
				model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
				printInfo_List.add(model_q);
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
				//订单明细
				List<PubPrintOrderModel> detailList = jisoOrderManager.queryJisoOrderDetailList(model_q);
				//页数
				paramModel.setYs(((detailList.size()%pageSize== 0) ? detailList.size()/pageSize : detailList.size()/pageSize + 1) + "");
				JasperPrint jasperPrint = PrintOrderUtil.getJasPerPrintUtil(detailList, file, paramModel);
				if(null != jasperPrint) {
					JasperPrintList.add(jasperPrint);
				}
			}
			//防止size=0抛异常
			if(JasperPrintList.size() > 0) {
				PrintOrderUtil.exportReportOrderUtil(response, JasperPrintList);
				//jisoOrderManager.updatePrintInfo(orderNoArr);
				jisoOrderManager.updatePrintInfoList(printInfo_List);
			}
		}
		
	}
	
	/**
	 * @Description: 厂外同步订单打印(A4纸)  
	 * @param:     
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	@RequestMapping("jisoOrderA4Print")
	public void jisoOrderA4Print(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//订单每页打印明细行数
		int pageSize = 18;
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String[] orderNoArr = orderNoStr.split(",");
		//更新打印状态list
		List<JisoOrderDetailModel> printInfo_List = new ArrayList<JisoOrderDetailModel>();
		if(null != orderNoArr && orderNoArr.length > 0) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			for (int i = 0; i < orderNoArr.length; i++) {
				JisoOrderDetailModel model_q = new JisoOrderDetailModel();
				//生成多个InputStream file,防止抛异常
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
						+ File.separator +"ireport" + File.separator + "pub" + File.separator + "PR_ORDER.jasper";
				InputStream file = new FileInputStream(filenurl);
				model_q.setOrderNo(orderNoArr[i]);
				model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
				model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
				printInfo_List.add(model_q);
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
				//订单明细
				List<PubPrintOrderModel> detailList = jisoOrderManager.queryJisoOrderDetailList(model_q);
				//页数
				paramModel.setYs(((detailList.size()%pageSize== 0) ? detailList.size()/pageSize : detailList.size()/pageSize + 1) + "");
				JasperPrint jasperPrint = PrintOrderUtil.getJasPerPrintUtil(detailList, file, paramModel);
				if(null != jasperPrint) {
					JasperPrintList.add(jasperPrint);
				}
			}
			//防止size=0抛异常
			if(JasperPrintList.size() > 0) {
				PrintOrderUtil.exportReportOrderUtil(response, JasperPrintList);
				//jisoOrderManager.updatePrintInfo(orderNoArr);
				jisoOrderManager.updatePrintInfoList(printInfo_List);
			}
		}
	}
	
	/**
	 * @Description: 厂外同步指示票打印   
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws JRException 
	 * @throws IOException 
	 * @throws Exception 
	 * @date: 2018年12月25日
	 */
	@RequestMapping("jisoOrderPrintIns")
	public void jisoOrderPrintIns(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String[] orderNoArr = orderNoStr.split(",");
		//根据orderNo查询insNo
		List<JisoInsModel> list_ins = jisoOrderManager.queryJisoInsList(orderNoArr);
		String[] insNoArr = new String[list_ins.size()];
		if(null != list_ins && list_ins.size() > 0) {
			for (int i = 0; i < list_ins.size(); i++) {
				insNoArr[i] = list_ins.get(i).getInsNo();
			}
		}
		if(null != insNoArr && insNoArr.length > 0) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			//更新打印状态list
			List<JisoInsModel> list_printInfo = new ArrayList<JisoInsModel>();
			for (int j = 0; j < insNoArr.length; j++) {
				JisoInsModel model_q = new JisoInsModel();
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
						+ File.separator +"ireport" + File.separator + "jis" + File.separator + "JISO_INS.jasper";
				InputStream file = new FileInputStream(filenurl);
				model_q.setInsNo(insNoArr[j]); 
				model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
				model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
				//指示票明细
				List<JisoInsModel> detailList = jisoInsManager.queryJisoInsDetailList(model_q);
				list_printInfo.add(model_q);
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				List<PubPrintInsModel> list = new ArrayList<PubPrintInsModel>();
				//行数统计
				int rowCount = 0;
				//每列最多显示行数
				int maxRowSize = 21;
				//页码
				int page = 1;
				//打印页码
				int size = detailList.size();
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
					for (int i = 0; i < detailList.size(); i++) {
						//行数自增
						rowCount++;
						JisoInsModel model = detailList.get(i);
						//指示票头信息
						if(rowCount == 1) {
							parameters.put("insNo", model.getInsNo());
							parameters.put("arriveTime", model.getArriveTime());
							parameters.put("supplierName", model.getSupplierName());
							parameters.put("supplierNo", model.getSupplierNo());
							//零件名称取主表零件组名称
							parameters.put("partName", model.getPartgroupName());
							//车身序号
							String csxh = "";
							if(StringUtil.isNotEmpty(model.getWcSeqno()) && 
									StringUtil.isNotEmpty(detailList.get(detailList.size()-1).getWcSeqno())) {
								csxh = model.getWcSeqno() + "~" + detailList.get(detailList.size()-1).getWcSeqno();
								//收容数
								parameters.put("srs", String.valueOf(detailList.size()));
							}
							parameters.put("csxh", csxh);
							parameters.put("distriPerson", model.getDistriPerson());
							parameters.put("location", model.getLocation());
							//二维码
							String qrCode = model.getInsNo();
							parameters.put("QRCode", MakeQrcodeImages.getQrCodeImage(qrCode, "80", "80"));
						}
						PubPrintInsModel insBean = new PubPrintInsModel(); 
						if(rowCount <= maxRowSize * ( 2 * page - 1 )) {
							insBean.setWcSeqno(model.getWcSeqno());
							insBean.setPartShortNo(model.getPartShortNo());
							insBean.setPartMark(model.getPartMark());
							if(SessionKey.MM_JISO_INS_REMARK_FLAG.equals(model.getRemarkFlag())) {
								StringBuffer sb = new StringBuffer();
								sb.append(model.getPartNo());
								sb.append("\r\n");
								sb.append("销售单号:");
								sb.append(model.getSaleNo());
								insBean.setPartNo(sb.toString());
							}else {
								insBean.setPartNo(model.getPartNo());
							}
							insBean.setRemarkFlag(model.getRemarkFlag());
						}else if(rowCount > maxRowSize * ( 2 * page - 1 ) && rowCount <= maxRowSize * 2 * page ) {
							insBean = list.get(rowCount - 1 - maxRowSize * page );
							insBean.setWcSeqno2(model.getWcSeqno());
							insBean.setPartShortNo2(model.getPartShortNo());
							insBean.setPartMark2(model.getPartMark());
							if(SessionKey.MM_JISO_INS_REMARK_FLAG.equals(model.getRemarkFlag())) {
								StringBuffer sb = new StringBuffer();
								sb.append(model.getPartNo());
								sb.append("\r\n");
								sb.append("销售单号:");
								sb.append(model.getSaleNo());
								insBean.setPartNo2(sb.toString());
							}else {
								insBean.setPartNo2(model.getPartNo());
							}
							insBean.setRemarkFlag2(model.getRemarkFlag());
						}
						if(null != insBean && rowCount <= maxRowSize * ( 2 * page - 1 )) {
							list.add(insBean);
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
			//JRE异常
			if(JasperPrintList.size() > 0) {
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline;");
				JRPdfExporter exporter = new JRPdfExporter();
				OutputStream out = response.getOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
				exporter.exportReport();
				//更新打印状态
				jisoInsManager.updatePrintStatus(list_printInfo, RequestUtil.getIpAddr(request), insNoArr);
			}else {
				writeResultMessage(response.getWriter(), "没有数据可打印", ResultMessage.FAIL);
			}
		}
	}
	
	/**
	 * @Description: 厂外同步订单dcs打印  
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @throws JRException 
	 * @date: 2019年4月25日
	 */
	@RequestMapping("jisoOrderPrintDCS")
	public void jisoOrderPrintDCS (HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String orderNoStr = RequestUtil.getString(request, "orderNoStr");
			String[] orderNoArr = orderNoStr.split(",");
			if(null != orderNoArr && orderNoArr.length > 0) {
				//打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
				for (int i = 0; i < orderNoArr.length; i++) {
					JisoOrderModel model_q = new JisoOrderModel();
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
				    List<PubPrintOrderModel> detailList = jisoOrderManager.queryJisoOrderDCSDetailList(model_q);
	                if(null != detailList && detailList.size() > 0) {
	                	PubPrintOrderModel model_pr = detailList.get(0);
	                	
	                	/*//查询DCS封条号
						pupDcsModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
						//拉动订单获取1个封条号
						pupDcsModel.setSealNum(JISO_SEAL_NUM);
						pupDcsModel.setSessionNo("");
						List<PupDcsModel> sealList = pupDcsManager.querySealForList(pupDcsModel);
						//封条号
						if (sealList.size() < JISO_SEAL_NUM) {
			                throw new Exception("封条号不够");
			            }
						List<String> sealNoList_parameters = new ArrayList<String>();
						List<String> sealNoList_field = new ArrayList<String>();
						for (int j = 0; j < sealList.size(); j++) {
							if(j < JISO_SEAL_NUM/2) {
								sealNoList_parameters.add(sealList.get(j).getSealNo());
							}else {
								sealNoList_field.add(sealList.get(j).getSealNo());
							}
							pupDcsModel.setSealNo(sealList.get(j).getSealNo());
							//更新DCS任务的状态
							pupDcsModel.setCreateUser(ContextUtil.getCurrentUser().getAccount());
							jisoInsManager.updateDcsPrintStatus(pupDcsModel);
						}
						parameters.put("sealNo", org.apache.commons.lang3.StringUtils.join(sealNoList_parameters, ","));
						pupDcsModel.setSealNo(org.apache.commons.lang3.StringUtils.join(sealNoList_parameters, ","));
						//供应商封条号
						model_pr.setSealNo(org.apache.commons.lang3.StringUtils.join(sealNoList_field, ","));*/
						
						StringBuffer planSheetNo = new StringBuffer();
						planSheetNo.append("TB");
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
						String routeCode = model_pr.getSupplierNo() + "（同步线）";
						parameters.put("planSheetNo", planSheetNo.toString());
						//路线名称
						parameters.put("routeCode", routeCode);
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
			} 
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString()); 
			String resultMsg="打印失败";
			writeResultMessage(response.getWriter(), resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
}
	
