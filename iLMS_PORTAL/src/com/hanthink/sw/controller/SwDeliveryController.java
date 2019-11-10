package com.hanthink.sw.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.hanthink.pub.model.PubFactoryInfoModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.manager.SwDeliveryManager;
import com.hanthink.sw.manager.SwDemondOrderManager;
import com.hanthink.sw.manager.SwUserManager;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwDeliveryModel;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hanthink.sw.model.SwUserModel;
import com.hanthink.util.BufferImage.BarCodeImage;
import com.hanthink.util.constant.Constant;
import com.hanthink.util.print.PrintOrderUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.util.AppFileUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
/**
 * 
* <p>Title: SwDeliveryController</p>  
* <p>Description: 发货管理controller</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月19日 上午9:09:49
 */
@Controller
@RequestMapping("/sw/swDelivery")
public class SwDeliveryController extends GenericController{

	@Resource
	private SwDeliveryManager manager;
	@Resource
	private SwDemondOrderManager swDemondOrderManager;
	
	@Resource
	private SwUserManager swUserManager;
	
	private static Logger log = LoggerFactory.getLogger(SwDeliveryController.class);
	/**
	 * 
	 * <p>Title: queryJisoGroupPage</p>  
	 * <p>Description: 发货数据管理，分页查询</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:11:45
	 */
	@RequestMapping("queryJisoDeliveryPage")
	public @ResponseBody PageJson queryJisoDeliveryPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwDeliveryModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        ResultMessage message = null;
        
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<SwDeliveryModel> pageList = (PageList<SwDeliveryModel>) manager.queryJisoDeliveryPage(model, p);
        return new PageJson(pageList);
	 
     }
	
	/**
	 * 
	 * <p>Title: queryJisoDeliveryDetailPage</p>  
	 * <p>Description: 发货管理界面，查询明细功能</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月19日 上午9:10:16
	 */
	@RequestMapping("queryJisoDeliveryDetailPage")
	public @ResponseBody PageJson queryJisoDeliveryDetailPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwDeliveryModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
        
        PageList<SwDeliveryModel> pageList = (PageList<SwDeliveryModel>) manager.queryJisoDeliveryDetailPage(model, p);
        return new PageJson(pageList);
	}
	

	/**
	 * 订单打印
	 * 
	 * @param request
	 * @param response
	 * @throws Exception luoxq
	 */
	@RequestMapping("DemondOrderPrint")
	public void jitOrderPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNoStr = RequestUtil.getString(request, "orderNo");
		String[] orderNoArr = orderNoStr.split(",");
		List<SwDemondOrderDetailModel> detailList = null;
		List<SwDemondOrderDetailModel> updateList = new ArrayList<SwDemondOrderDetailModel>();

		try {

			if (null != orderNoArr && orderNoArr.length > 0) {
				// 打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();

				for (int i = 0; i < orderNoArr.length; i++) {
					SwDemondOrderDetailModel model_q = new SwDemondOrderDetailModel();
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
							+ "ireport" + File.separator + "demo" + File.separator + "DEMOND_ORDER.jasper";
					model_q.setOrderNo(orderNoArr[i]);
					updateList.add(model_q);
					// 查询订单明细
					detailList = swDemondOrderManager.queryDemondOrderDetailList(model_q);
					for (int j = 0; j < detailList.size(); j++) {
						detailList.get(j).setRowId((j + 1) + "");
						detailList.get(j).setPageCount((i + 1) + "/" + orderNoArr.length);
						detailList.get(j).setLabelCount(detailList.size() + "");

					}
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					if (null != detailList && detailList.size() > 0) {
						SwDemondOrderDetailModel model = detailList.get(0);
						String orderType = model.getOrderType();// 订单类型
						String spType = model.getSpType();// 售后订单类型,2,3采用特殊打印类型
						BufferedImage img = BarCodeImage.creatBarCode(model.getOrderNo());
						if ("12".equals(orderType) && ("2".equals(spType) || "3".equals(spType))) {
							filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
									+ "ireport" + File.separator + "demo" + File.separator + "DEMOND_ORDER_SP.jasper";
							String hmsOrder = model.getHmsOrder() != null ? model.getHmsOrder() : " ";
							img = BarCodeImage.creatBarCode(hmsOrder);
						}
						parameters.put("barCode", img);
						InputStream file = new FileInputStream(filenurl);
						JRDataSource jRDataSource = new JRBeanCollectionDataSource(detailList);
						JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
						JasperPrintList.add(jasperPrint);
					}

				}
				if (JasperPrintList.size() > 0) {
					response.setContentType("application/pdf");
					response.setHeader("Content-disposition", "inline;");
					JRPdfExporter exporter = new JRPdfExporter();
					OutputStream out = response.getOutputStream();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
					exporter.exportReport();

//					swDemondOrderManager.updatePrintInfo(updateList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 订单打印（三联纸）
	 * @param @param request
	 * @param @param response
	 * @param @throws IOException   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月25日 下午7:43:45
	 */
	@RequestMapping("deliveryOrderPrint")
	public void deliveryOrderPrint(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//订单每页打印明细行数
		int pageSize = 18;
		String orderNoStr = RequestUtil.getString(request, "orderNo");
		String[] orderNoArr = orderNoStr.split(",");
		if(null != orderNoArr && orderNoArr.length > 0) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			//更新打印状态list
			List<SwDemondOrderDetailModel> list_printInfo = new ArrayList<SwDemondOrderDetailModel>();
			try {
				for (int i = 0; i < orderNoArr.length; i++) {
					SwDemondOrderDetailModel model_q = new SwDemondOrderDetailModel();
					//生成多个InputStream file,防止抛异常
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "pub" + File.separator + "ORDER.jasper";
					InputStream file = new FileInputStream(filenurl);
					model_q.setOrderNo(orderNoArr[i]);
					model_q.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					//查询订单打印明细
					List<PubPrintOrderModel> detailList = swDemondOrderManager.queryOrderDetailList(model_q);
					//更新打印状态list
					list_printInfo.add(model_q); 
					PubFactoryInfoModel paramModel = new PubFactoryInfoModel();
					paramModel.setFactoryName(ContextUtil.getCurrentUser().getCurFactoryCode());
					String orderType = detailList.get(0).getType();
					String orderTypeStr = null;
					if(("01").equals(orderType)) {
						orderTypeStr = "量产";
					}else if(("02").equals(orderType)){
						orderTypeStr = "例外";
					}else if(("05").equals(orderType)){
						orderTypeStr = "样件";
					}else if(("12").equals(orderType)){
						orderTypeStr = "售后";
					}
					paramModel.setOrderType(orderTypeStr);
					//页数
					paramModel.setYs(((detailList.size()%pageSize== 0) ? detailList.size()/pageSize : detailList.size()/pageSize + 1) + "");
					JasperPrint jasperPrint = PrintOrderUtil.getJasPerPrintUtil(detailList, file, paramModel);
					if(null != jasperPrint) {
						JasperPrintList.add(jasperPrint); 
					}   
				}
				PrintOrderUtil.exportReportOrderUtil(response, JasperPrintList);
				swDemondOrderManager.updatePrintInfo(list_printInfo);
			} catch (Exception e) { 
				e.printStackTrace();
				log.error(e.toString()); 
				String resultMsg="打印失败";
				writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
			}
			
		}
	}
	
	/**
	 * 
	 * @Description: 标签打印
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月25日 下午7:37:40
	 */
	@RequestMapping("deliveryOrderPrintLabel")
	public void deliveryOrderPrintLabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String orderNoArr = request.getParameter("orderNo");
		String deliveryNos = RequestUtil.getString(request, "deliveryNoArr");
//		String[] splitOrderNo = orderNoArr.split(",");
		String[] deliveryNoArr = deliveryNos.split(",");
		
		List<SwDeliveryModel> list = null;

		try {

			if (null != deliveryNoArr && deliveryNoArr.length > 0) {
				// 打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
				for (int i = 0; i < deliveryNoArr.length; i++) {
					SwDeliveryModel orderModel = new SwDeliveryModel();
					orderModel.setOrderNo(deliveryNoArr[i]);
					// 查询标签打印信息明细
					list = manager.queryDeliveryOrderPrintLabelList(orderModel);
					if (list == null || list.size() == 0) {
						return;
					}
					List<SwDeliveryModel> paramList = new ArrayList<SwDeliveryModel>();
//					for (int j = 0; j < list.size(); j++) {
//						list.get(j).setRowId((j + 1) + "");
//						list.get(j).setPageCount((j + 1) + "/" + list.size());
//					}
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
							+ "ireport" + File.separator + "demo" + File.separator + "SW_DEMOND_LABEL.jasper";
					Map<String, Object> map = new HashMap<String, Object>();

					SwDeliveryModel model = new SwDeliveryModel();
					model = list.get(i);
					String orderType = model.getOrderType();// 订单类型
					String spType = model.getSpType();// 售后订单类型,2,3采用特殊打印类型
					if ("12".equals(orderType) && ("2".equals(spType) || "3".equals(spType))) {
						filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "ireport"
								+ File.separator + "demo" + File.separator + "SW_DEMOND_LABEL_SP.jasper";
					}
					
					if ("02".equals(orderType)) { //如果是例外订单，则共用支给件模板打印有水印
						filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "ireport"
								+ File.separator + "demo" + File.separator + "SW_DEMOND_LABEL_LW.jasper";
						//如果是例外订单则记录水印标识 “例外”
//						model.setSyFlag("例外");
					}
					
					if (!"12".equals(orderType) && !"02".equals(orderType)) {
						filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
								+ "ireport" + File.separator + "demo" + File.separator + "SW_DEMOND_LABEL.jasper";
					}
					// 拣取图片
	                String pagePath = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "page" + File.separator
	                        + "LW.jpg";
					InputStream file = new FileInputStream(filenurl);
					int size = list.size();
					for (int k = 0; k < size; k++) {
						model = list.get(k);
						Integer standardPackge = Integer.parseInt(model.getStandardPackage());
						Integer currentQty = Integer.parseInt(model.getTempDelivQty());
						while (0 < currentQty) {
							// SwDemondOrderDetailModel m = new SwDemondOrderDetailModel();

							SwDeliveryModel swDeliveryModel_q = new SwDeliveryModel();
							
							BufferedImage logoImg = ImageIO.read(new FileInputStream(pagePath));
							swDeliveryModel_q.setLogoImg(logoImg);
							
							swDeliveryModel_q.setOrderTypeName(model.getOrderType());
							swDeliveryModel_q.setPageCount(model.getPageCount());
							swDeliveryModel_q.setRowId(model.getRowId());
							swDeliveryModel_q.setPartNo(model.getPartNo());
							swDeliveryModel_q.setPartShortNo(model.getPartShortNo());
							swDeliveryModel_q.setSupplierNo(model.getSupplierNo());
							swDeliveryModel_q.setPartName(model.getPartName());
							swDeliveryModel_q.setDistriPerson(model.getDistriPerson());
							swDeliveryModel_q.setDistriProductSeqno(model.getDistriProductSeqno());
							swDeliveryModel_q.setRequireNum(model.getRequireNum());
							swDeliveryModel_q.setLocation(model.getLocation());
							swDeliveryModel_q.setOrderTimeHhmi(model.getOrderTimeHhmi());
							swDeliveryModel_q.setOrderTimeYmd(model.getOrderTimeYmd());
							swDeliveryModel_q.setUnloadPort(model.getUnloadPort());
							swDeliveryModel_q.setArriveTime(model.getArriveTime());
							swDeliveryModel_q.setPurchaseNo(model.getPurchaseNo());
							swDeliveryModel_q.setOrderNo(model.getOrderNo());
							swDeliveryModel_q.setArriveProductSeqno(model.getArriveProductSeqno());
							swDeliveryModel_q.setPrepareAddress(model.getPrepareAddress());
							swDeliveryModel_q.setStandardPackage(model.getStandardPackage());
							swDeliveryModel_q.setPurchaseRowNo(model.getPurchaseRowNo());
							swDeliveryModel_q.setPreparePerson(model.getPreparePerson());
							currentQty = currentQty - standardPackge;
							if (currentQty >= 0) {
								swDeliveryModel_q.setTempDelivQty(standardPackge.toString());
							} else {
								swDeliveryModel_q.setTempDelivQty((currentQty + standardPackge) + "");
							}

							// 零件号#包装数#erp单号#物流单行号#物流单号#简号#订单类型#追朔等级#供应商代码#零件批次号#同批次数量#uuid
							String code = swDeliveryModel_q.getPartNo() + "#"
									+ swDeliveryModel_q.getStandardPackage() + "#"
									+ swDeliveryModel_q.getPurchaseNo() + "#"
									+ swDeliveryModel_q.getPurchaseRowNo() + "#"
									+ swDeliveryModel_q.getOrderNo() + "#"
									+ swDeliveryModel_q.getPartShortNo() + "#"
									+ swDeliveryModel_q.getOrderTypeName() + "#" + "" + "#"
									+ swDeliveryModel_q.getSupplierNo() + "#" + "" + "#"
									+ swDeliveryModel_q.getTempDelivQty() + "#"
									+ UUID.randomUUID().toString().replaceAll("-", "");
							BufferedImage img = MakeQrcodeImages.getQrCodeImage(code, "150", "150");
							// map.put("QR_INFOS", img);
							swDeliveryModel_q.setQR_INFOS(MakeQrcodeImages.getQrCodeImage(code.toString(), "150", "150"));
							paramList.add(swDeliveryModel_q);
							// strList.add(code);
						}
					}
					
					//循环paramList设置每一行的pageCount
					for(int n = 0;n < paramList.size(); n++){
						SwDeliveryModel tempModel = paramList.get(n);
						tempModel.setPageCount((n + 1) + "/" + paramList.size());
					}
					JRDataSource jRDataSource = new JRBeanCollectionDataSource(paramList);
					JasperPrint jasperPrint = JasperFillManager.fillReport(file, map, jRDataSource);

					JasperPrintList.add(jasperPrint);

					if (JasperPrintList.size() > 0) {
						response.setContentType("application/pdf");
						response.setHeader("Content-disposition", "inline;");
						JRPdfExporter exporter = new JRPdfExporter();
						OutputStream out = response.getOutputStream();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
						exporter.exportReport();
//						for (SwDeliveryModel swDeliveryModel : list) {
//							swDemondOrderManager.updatePrintLabelStatus(swDeliveryModel);
//						}
						
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 托盘标签打印
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月25日 下午7:39:01
	 */
	@RequestMapping("deliveryOrderPrintTpLabel")
	public void deliveryOrderPrintTpLabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String[] orderNoArr = orderNoStr.split(",");

		try {

			if (null != orderNoArr && orderNoArr.length > 0) {
				// 打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
				for (int i = 0; i < orderNoArr.length; i++) {
					SwDemondOrderDetailModel model_q = new SwDemondOrderDetailModel();
					model_q.setOrderNo(orderNoArr[i]);
					// 根据订单号查询托盘标签打印信息
					List<SwDemondOrderDetailModel> list = swDemondOrderManager.demondOrderPrintTpLabel(model_q);
					for (int j = 0; j < list.size(); j++) {
						String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
								+ "ireport" + File.separator + "demo" + File.separator + "TP_LABEL_1.jasper";
						InputStream file = new FileInputStream(filenurl);
						HashMap<String, Object> parameters = new HashMap<String, Object>();
						SwDemondOrderDetailModel model = list.get(j);
						parameters.put("supplierNo", model.getSupplierNo());
						parameters.put("supplierName", model.getSupplierName());
						parameters.put("unloadPort", model.getUnloadPort());
						parameters.put("distriBatchNo", "");
						parameters.put("orderNo", model.getOrderNo());
						parameters.put("purchaseOrderno", model.getPurchaseNo());
						// 托盘总数
						parameters.put("total", "" + list.size());
						// 第 ?拖
						parameters.put("no", "" + (j + 1));
						JRDataSource jRDataSource = new JREmptyDataSource();
						JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
						JasperPrintList.add(jasperPrint);

					}
				}
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline;");
				JRPdfExporter exporter = new JRPdfExporter();
				OutputStream out = response.getOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
				exporter.exportReport();

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
}
