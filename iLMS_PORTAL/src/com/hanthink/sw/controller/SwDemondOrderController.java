package com.hanthink.sw.controller;

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

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.pub.model.PubFactoryInfoModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.manager.SwDemondOrderManager;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hanthink.util.BufferImage.BarCodeImage;
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
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 零件订单
 * 
 * @author 李兴辉
 *
 */
@Controller
@RequestMapping("/sw/swDemandOrder")
public class SwDemondOrderController extends GenericController {

	@Resource
	private SwDemondOrderManager swDemondOrderManager;

	private static Logger log = LoggerFactory.getLogger(SwDemondOrderController.class);

	/**
	 * 订单查询
	 * 
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception Lxh
	 */
	@RequestMapping("/queryDemondOrderPage")
	public @ResponseBody PageJson queryDemandOrderPage(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") SwDemondOrderDetailModel model) throws Exception {
		DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		IUser user = ContextUtil.getCurrentUser();
		if ("2".equals(user.getUserType())) {
			model.setSupplierNo(user.getSupplierNo());
		}
		model.setUserType(user.getUserType());
		model.setUserId(user.getUserId());
		model.setFactoryCode(user.getCurFactoryCode());
		PageList<SwDemondOrderDetailModel> pageList = (PageList<SwDemondOrderDetailModel>) swDemondOrderManager
				.queryDemandOrderPage(model, p);
		return new PageJson(pageList);
	}

	/**
	 * 订单明细查询
	 * 
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDemondOrderDetailPage")
	public @ResponseBody PageJson queryDemandDetailOrderPage(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") SwDemondOrderDetailModel model) throws Exception {
		DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		PageList<SwDemondOrderDetailModel> pageList = (PageList<SwDemondOrderDetailModel>) swDemondOrderManager
				.queryDemandDetailOrderPage(model, p);
		return new PageJson(pageList);
	}

	/**
	 * 订单明细修改
	 * 
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception Lxh
	 */
	@RequestMapping("/DemondOrderDetailUpdateQuery")
	public @ResponseBody PageJson demondOrderDetailUpdateQuery(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") SwDemondOrderDetailModel model) throws Exception {
		DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		String parameter = model.getPurchaseNoArr();
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		String[] purchaseNos = parameter.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaseNos", purchaseNos);
		map.put("model", model);
		// ArrayList<SwDemondOrderDetailModel> arrayList = new
		// ArrayList<SwDemondOrderDetailModel>();
		// for (int i = 0; i < split.length; i++) {
		// String string = split[i];
		// SwDemondOrderDetailModel models = new SwDemondOrderDetailModel();
		// models.setPurchaseNo(string);
		// models.setOrderNo(model.getOrderNo());
		// models.setSupplierNo(model.getSupplierNo());
		// models.setSupplierName(model.getSupplierName());
		// models.setPartName(model.getPartName());
		// models.setPartShortNo(model.getPartShortNo());
		// arrayList.add(models);
		// }

		PageList<SwDemondOrderDetailModel> pageList = (PageList<SwDemondOrderDetailModel>) swDemondOrderManager
				.demondOrderDetailUpdateQuery(map, p);
		return new PageJson(pageList);
	}

	/**
	 * 更新发货数量
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/updateDemandDetailForPrint")
	public void updateDemandDetailForPrint(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SwDemondOrderDetailModel model) throws Exception {

		IUser user = ContextUtil.getCurrentUser();
		try {
			String message = "";
			String tempDelivQty = model.getTempDelivQty();// 本次发货
			String totalDelivQty = model.getTotalDelivQty();// 历史发货总数量
			String orderQity = model.getOrderQty();// 采购数量
			String standardPackage = model.getStandardPackage();// 收容数
			String regex = "^([0-9])*";
			boolean isCorrect = tempDelivQty.trim().matches(regex);
			boolean isCorrect1 = totalDelivQty.trim().matches(regex);
			boolean isCorrect2 = orderQity.trim().matches(regex);
			boolean isCorrect3 = standardPackage.trim().matches(regex);
			if (isCorrect && isCorrect1 && isCorrect2 && isCorrect3) {
				int tempDelivQtyInt = Integer.parseInt(tempDelivQty);
				int totalDelivQtyInt = Integer.parseInt(totalDelivQty);
				int orderQityInt = Integer.parseInt(orderQity);
//				int standardPackageInt = Integer.parseInt(standardPackage);
				int count = tempDelivQtyInt + totalDelivQtyInt;
				if (orderQityInt >= count) {
//					if ("01".equals(model.getOrderType())) {   //量产订单按照收容数发货
//
//						if (tempDelivQtyInt % standardPackageInt == 0) {
//							model.setTotalDelivQty(count + "");
//							model.setUpdateUser(user.getAccount());
//							model.setFactoryCode(user.getCurFactoryCode());
//							swDemondOrderManager.updateDemandDetailForPrint(model);
//						} else {
//							message = "本次发货数量不是收容数的倍数";
//							writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
//							return;
//						}
//					}else {
						model.setTotalDelivQty(count + "");
						model.setUpdateUser(user.getAccount());
						model.setFactoryCode(user.getCurFactoryCode());
						swDemondOrderManager.updateDemandDetailForPrint(model);
//					}
				} else {
					message = "发货总数不能大于采购数量";
					writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
					return;
				}

			} else {
				message = "本次发货数只能为数字格式";
				writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
				return;
			}

			message = "更新成功";
			writeResultMessage(response.getWriter(), message, ResultMessage.SUCCESS);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 批量导出
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downloadDemondOrderByBatch")
	public void downloadDemondOrderByBatch(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") SwDemondOrderDetailModel model) throws Exception {
		try {
			String exportFileName = "需求订单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			IUser user = ContextUtil.getCurrentUser();
			if (IUser.USER_TYPE_SUPP.equals(user.getUserType())) {
				model.setSupplierNo(user.getSupplierNo());
			}
			model.setFactoryCode(user.getCurFactoryCode());
			List<SwDemondOrderDetailModel> list = (List<SwDemondOrderDetailModel>) swDemondOrderManager
					.queryDemandOrderDownload(model);
			int curNum = list.size();
			if (0 == curNum) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
			// int sysMaxNum_T = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T",
			// 100000);
			if (curNum > sysMaxNum) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			// List<SwDemondOrderDetailModel> list =
			// swDemondOrderManager.downloadDemondOrder(model);
			if (null != list) {
				model.setDownloadStatusYes(SwDemondOrderDetailModel.DOWNLOAD_STATUS_YES);
				// 如果为供应商用户修改下载状态
				if ("2".equals(ContextUtil.getCurrentUser().getUserType())) {
					swDemondOrderManager.updateDowloadInfoByBatch(model); // 导出时更新下载状态为已下载
				}
				String[] headers = { "供应商代码", "出货地代码", "供应商名称", "物流单号", "订购日期", "到货日期", 
						"取货时间", "仓库代码", "订单号", "打印状态","下载状态", "发货状态", "收货状态(订单)", "打印时间",

						"订单号", "订单行号", "零件号", "零件名称", "零件简号", "订购数量", "包装数", "箱数", "取消数量" , "收货数量"};
				String[] columns = { "supplierNo", "supFactory", "supplierName", "orderNo", "orderDate", "arriveDate",
						"planPickUpTimeStr", "depotNo", "purchaseNo", "printStatus", "downloadStatus", "deliveryStatus",
						"receiveStatus", "printTime",

						"purchaseNo", "purchaseRowNo", "partNo", "partName", "partShortNo", "orderQty",
						"standardPackage", "boxesNum", "cancelNum" , "totalRecQty"};
				int[] widths = { 100, 100, 100, 100, 150, 100, 100, 100, 100, 100, 100, 100, 100, 100,

						100, 100, 100, 100, 100, 100, 100, 100, 100, 100  };
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths,
						columns);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 选择导出
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downloadDemondOrderByChoose")
	public void downloadDemondOrderByChoose(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// String purchaseNoArr = request.getParameter("purchaseNoArr");
		String purchaseNoArr = request.getParameter("purchaseNoArr");
//		String str = request.getQueryString();
//		String ss = java.net.URLDecoder.decode(str);
//		String[] strs = ss.split("=");
//		String purchaseNoArr = strs[1];
//		//去除所有空格
//		purchaseNoArr.trim();
		String exportFileName = "需求订单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		String[] splitPurchaseNo = purchaseNoArr.split(",");
		String[] splitPurchaseNo = purchaseNoArr.split(",");

		try {

			// List<SwDemondOrderDetailModel> list = new
			// ArrayList<SwDemondOrderDetailModel>();

			// SwDemondOrderDetailModel swDemondOrderDetailModel3 = new
			// SwDemondOrderDetailModel();
			// for (int i = 0; i < splitPurchaseNo.length; i++) {
			// String purcharceNo = splitPurchaseNo[i].trim();
			// swDemondOrderDetailModel3.setPurchaseNo(purcharceNo);
			// List<SwDemondOrderDetailModel> swDemondOrderDetailModel =
			// swDemondOrderManager
			// .downloadDemondOrderByChoose(swDemondOrderDetailModel3);
			// swDemondOrderManager.updateDowloadInfo(swDemondOrderDetailModel3);
			// for (SwDemondOrderDetailModel swDemondOrderDetailModel2 :
			// swDemondOrderDetailModel) {
			// list.add(swDemondOrderDetailModel2);
			// }
			// }
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("purchaseNoArr", splitPurchaseNo);
			map.put("downloadStatus", SwDemondOrderDetailModel.DOWNLOAD_STATUS_YES);
			// 如果为供应商用户修改下载状态
			if ("2".equals(ContextUtil.getCurrentUser().getUserType())) {
				swDemondOrderManager.updateDowloadInfo(map); // 导出修改下载状态为已下载
			}
			List<SwDemondOrderDetailModel> list = swDemondOrderManager.downloadDemondOrderByChoose(splitPurchaseNo);
			if (list.size() > 0) {
				String[] headers = { "供应商代码", "出货地代码", "供应商名称", "物流单号", "订购日期", "到货日期", "取货时间", "仓库代码", "订单号", "打印状态",
						"下载状态", "发货状态", "收货状态(订单)", "打印时间",

						"订单号", "订单行号", "零件号", "零件名称", "零件简号", "订购数量", "包装数", "箱数", "取消数量", "收货数量" };
				String[] columns = { "supplierNo", "supFactory", "supplierName", "orderNo", "orderDate", "arriveDate",
						"planPickUpTimeStr", "depotNo", "purchaseNo", "printStatus", "downloadStatus", "deliveryStatus",
						"receiveStatus", "printTime",

						"purchaseNo", "purchaseRowNo", "partNo", "partName", "partShortNo", "orderQty",
						"standardPackage", "boxesNum", "cancelNum", "totalRecQty" };
				int[] widths = { 100, 100, 100, 100, 150, 100, 100, 100, 100, 100, 100, 100, 100, 100,

						100, 100, 100, 100, 100, 100, 100, 100, 100, 100 };
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths,
						columns);
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			// ExcelUtil.exportException(e, request, response);
			throw new Exception("系统错误,请联系管理员");
		}

	}

	/**
	 * 标签打印
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("demondOrderPrintLabel")
	public void jitOrderPrintLabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNoArr = request.getParameter("orderNo");
		String[] splitOrderNo = orderNoArr.split(",");
		List<SwDemondOrderDetailModel> list = null;
		try {

			if (null != splitOrderNo && splitOrderNo.length > 0) {
				// 打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
				for (int i = 0; i < splitOrderNo.length; i++) {
					SwDemondOrderDetailModel orderModel = new SwDemondOrderDetailModel();
					orderModel.setOrderNo(splitOrderNo[i]);
					// 查询标签打印信息明细
//					list = swDemondOrderManager.queryDemondOrderPrintLabelList(orderModel);
					list = swDemondOrderManager.queryLabelPrintList(orderModel);
					if (list == null || list.size() == 0) {
						return;
					}
					List<SwDemondOrderDetailModel> paramList = new ArrayList<SwDemondOrderDetailModel>();
//					for (int j = 0; j < list.size(); j++) {
//						list.get(j).setRowId((j + 1) + "");
//						list.get(j).setPageCount((j + 1) + "/" + list.size());
//					}

					Map<String, Object> map = new HashMap<String, Object>();
					String filenurl = "";
					SwDemondOrderDetailModel model = new SwDemondOrderDetailModel();
					model = list.get(0);
					String orderType = model.getOrderType();// 订单类型
					String spType = model.getSpType();// 售后订单类型,2,3采用特殊打印类型
					if ("12".equals(orderType) && ("2".equals(spType) || "3".equals(spType))) {
						filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "ireport"
								+ File.separator + "demo" + File.separator + "SW_DEMOND_LABEL_SP.jasper";
					}

					if ("02".equals(orderType)) { // 如果是例外订单，则共用支给件模板打印有水印
						filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "ireport"
								+ File.separator + "demo" + File.separator + "SW_DEMOND_LABEL_LW.jasper";
						// 如果是例外订单则记录水印标识 “例外”
//						model.setSyFlag("例外");
					}

					if (!"12".equals(orderType) && !"02".equals(orderType)) {
						filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "ireport"
								+ File.separator + "demo" + File.separator + "SW_DEMOND_LABEL.jasper";
					}
					// 拣取图片
					String pagePath = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "page"
							+ File.separator + "LW.jpg";
					InputStream file = new FileInputStream(filenurl);
					int rowId = 1;
					for (int k = 0; k < list.size(); k++) {
						model = list.get(k);
						double standardPackge = Double.parseDouble(model.getStandardPackage());
						double currentQty = Double.parseDouble(model.getTempDelivQty());
						/**
						 * 如果不更新发货数直接打印，可以打印，但是只会全部发货
						 */
						if (currentQty == 0) {
							currentQty = Integer.parseInt(model.getOrderQty());
						}

						if (currentQty > 0) {
							// 计算总箱数先算出double类型
							double dboxNums = currentQty / standardPackge;
							// 然后向上取整
							int boxNums = (int) Math.ceil(dboxNums);
							// 多箱处理
							for (int m = 0; m < boxNums; m++) {
								// SwDemondOrderDetailModel m = new SwDemondOrderDetailModel();

								SwDemondOrderDetailModel swDemondOrderDetailModel_q = new SwDemondOrderDetailModel();
								BufferedImage logoImg = ImageIO.read(new FileInputStream(pagePath));
								swDemondOrderDetailModel_q.setLogoImg(logoImg);

								swDemondOrderDetailModel_q.setOrderTypeName(model.getOrderType());
								swDemondOrderDetailModel_q.setPageCount(model.getPageCount());
								swDemondOrderDetailModel_q.setRowId(model.getRowId());
								swDemondOrderDetailModel_q.setPartNo(model.getPartNo());
								swDemondOrderDetailModel_q.setPartShortNo(model.getPartShortNo());
								swDemondOrderDetailModel_q.setSupplierNo(model.getSupplierNo());
								swDemondOrderDetailModel_q.setPartName(model.getPartName());
								swDemondOrderDetailModel_q.setDistriPerson(model.getDistriPerson());
								swDemondOrderDetailModel_q.setDistriProductSeqno(model.getDistriProductSeqno());
								swDemondOrderDetailModel_q.setRequireNum(model.getRequireNum());

								if (StringUtil.isNotEmpty(model.getLocation())) {
									model.setLocation(model.getLocation().replace("\\r\\n", "\r\n"));
								}

								swDemondOrderDetailModel_q.setLocation(model.getLocation());
								swDemondOrderDetailModel_q.setOrderTimeHhmi(model.getOrderTimeHhmi());
								swDemondOrderDetailModel_q.setOrderTimeYmd(model.getOrderTimeYmd());
								swDemondOrderDetailModel_q.setUnloadPort(model.getUnloadPort());
								swDemondOrderDetailModel_q.setArriveTime(model.getArriveTime());
								swDemondOrderDetailModel_q.setPurchaseNo(model.getPurchaseNo());
								swDemondOrderDetailModel_q.setOrderNo(model.getOrderNo());
								swDemondOrderDetailModel_q.setArriveProductSeqno(model.getArriveProductSeqno());
								swDemondOrderDetailModel_q.setPrepareAddress(model.getPrepareAddress());
								swDemondOrderDetailModel_q.setStorage(model.getPrepareAddress());
								swDemondOrderDetailModel_q.setStandardPackage(model.getStandardPackage());
								swDemondOrderDetailModel_q.setPurchaseRowNo(model.getPurchaseRowNo());
								swDemondOrderDetailModel_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
								swDemondOrderDetailModel_q.setPrintUserIp(RequestUtil.getIpAddr(request));
								swDemondOrderDetailModel_q.setPreparePerson(model.getPreparePerson());
//									swDemondOrderDetailModel_q.setSyFlag(model.getSyFlag());
								swDemondOrderDetailModel_q.setDemander(model.getDemander());
								swDemondOrderDetailModel_q.setDemandDepartment(model.getDemandDepartment());
								swDemondOrderDetailModel_q.setUse(model.getUse());
								swDemondOrderDetailModel_q.setConNumber(model.getConNumber());
								currentQty = currentQty - standardPackge;
								if (currentQty >= 0) {
									swDemondOrderDetailModel_q.setTempDelivQty((int) standardPackge + "");
								} else {
									swDemondOrderDetailModel_q
											.setTempDelivQty((int) (currentQty + standardPackge) + "");
								}
//					                String pagePath = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "img" + File.separator
//					                        + "LW.jpg";
//									 String pagePath = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "img" + File.separator
//						                        + "LW.png";
//									 BufferedImage logoImg = ImageIO.read(new FileInputStream(pagePath));
//									 swDemondOrderDetailModel_q.setLogoImg(logoImg);
//									SwDemondOrderDetailModel tempModel = new SwDemondOrderDetailModel();
								swDemondOrderDetailModel_q.setPageCount((m + 1) + "/" + boxNums);
								swDemondOrderDetailModel_q.setKbzs((m + 1) + "/" + boxNums);
								// 物流单号#订单号#供应商代码#供应商出货地代码#订单行号#零件号#简号#卸货口#规格包装#当前包装量#拣货工程#拣货地址#配送工程#配送地址
								// String code = model.getOrderNo() + "#" + model.getPurchaseNo() + "#" +
								// model.getSupplierNo()+ "#" + model.getPurchaseRowNo()+ "#" +
								// model.getPartNo()
								// + "#" + model.getUnloadPort() + "#" + model.getStandartPackage() + "#" +
								// model.getBoxesNum() + "#" + model.getPreparePerson() + "#" +
								// model.getPrepareAddress()
								// + "#" + model.getDistriPerson() + "#" + model.getLocation();
								// BufferedImage img = QrCodeImage.createQrcode(code,'L','B' ,3);

								// 零件号#包装数#erp单号#物流单行号#物流单号#简号#订单类型#追朔等级#供应商代码#零件批次号#同批次数量#uuid
								String code = swDemondOrderDetailModel_q.getPartNo() + "#"
										+ swDemondOrderDetailModel_q.getStandardPackage() + "#"
										+ swDemondOrderDetailModel_q.getPurchaseNo() + "#"
										+ swDemondOrderDetailModel_q.getPurchaseRowNo() + "#"
										+ swDemondOrderDetailModel_q.getOrderNo() + "#"
										+ swDemondOrderDetailModel_q.getPartShortNo() + "#"
										+ swDemondOrderDetailModel_q.getOrderTypeName() + "#" + "" + "#"
										+ swDemondOrderDetailModel_q.getSupplierNo() + "#" + "" + "#"
										+ swDemondOrderDetailModel_q.getTempDelivQty() + "#"
										+ UUID.randomUUID().toString().replaceAll("-", "");
								/**
								 * insert by luoxianqin 
								 * 2019-08-11
								 * 增加记录供应商打印标签时二维码内容方法
								 */
								if ("2".equals(ContextUtil.getCurrentUser().getUserType())) {
									swDemondOrderDetailModel_q.setCode(code);
									swDemondOrderDetailModel_q.setOrderType(orderType);
									swDemondOrderDetailModel_q.setCreationUser(ContextUtil.getCurrentUser().getAccount());
									swDemondOrderDetailModel_q.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
								    swDemondOrderManager.logBarCode(swDemondOrderDetailModel_q,RequestUtil.getIpAddr(request)); //记录二维码内容
								}
								
								BufferedImage img = MakeQrcodeImages.getQrCodeImage(code, "150", "150");
								// map.put("QR_INFOS", img);
								swDemondOrderDetailModel_q
										.setQR_INFOS(MakeQrcodeImages.getQrCodeImage(code.toString(), "150", "150"));
//									swDemondOrderDetailModel_q.setQRCode(MakeQrcodeImages.getQrCodeImage(code.toString(), "150", "150"));
								// 设置行号
								swDemondOrderDetailModel_q.setRowId(rowId + "");
								// 行号自增
								rowId++;
								paramList.add(swDemondOrderDetailModel_q);
							}
						}
					}

					// 循环paramList设置每一行的pageCount
//					for(int n = 0;n < paramList.size(); n++){
//						SwDemondOrderDetailModel tempModel = paramList.get(n);
////						tempModel.setPageCount((n + 1) + "/" + paramList.size());
//						tempModel.setKbzs((n + 1) + "/" + paramList.size());
//					}

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
						for (SwDemondOrderDetailModel swDemondOrderDetailModel : list) {
							swDemondOrderDetailModel.setPrintUser(ContextUtil.getCurrentUser().getAccount());
							swDemondOrderDetailModel.setPrintUserIp(RequestUtil.getIpAddr(request));
							// 如果为供应商用户修改打印状态
							if ("2".equals(ContextUtil.getCurrentUser().getUserType())) {
								swDemondOrderManager.updatePrintLabelStatus(swDemondOrderDetailModel);
							}

						}
						// 如果为供应商用户修改发货数，以及发货状态
//						if("2".equals(ContextUtil.getCurrentUser().getUserType())){
//							swDemondOrderManager.updateDeliveryStatus(splitOrderNo);
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
	 * @Description: 拉动订单打印(三联纸)
	 * @param: @param request
	 * @param: @param response
	 * @param: @throws IOException
	 * @return: void
	 * @author: dtp
	 * @date: 2018年12月25日
	 */
	@RequestMapping("DemondOrderPrint")
	public void jitOrderPrint(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 订单每页打印明细行数
		int pageSize = 18;
		String orderNoStr = RequestUtil.getString(request, "orderNo");
		String[] orderNoArr = orderNoStr.split(",");
		if (null != orderNoArr && orderNoArr.length > 0) {
			// 打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			// 更新打印状态list
			List<SwDemondOrderDetailModel> list_printInfo = new ArrayList<SwDemondOrderDetailModel>();
			try {
				for (int i = 0; i < orderNoArr.length; i++) {
					SwDemondOrderDetailModel model_q = new SwDemondOrderDetailModel();
					// 生成多个InputStream file,防止抛异常
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
							+ "ireport" + File.separator + "pub" + File.separator + "ORDER.jasper";
					InputStream file = new FileInputStream(filenurl);
					model_q.setOrderNo(orderNoArr[i]);
					model_q.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
					model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
					// 查询订单打印明细
					List<PubPrintOrderModel> detailList = swDemondOrderManager.queryOrderDetailList(model_q);
					// 更新打印状态list
					list_printInfo.add(model_q);
					PubFactoryInfoModel paramModel = new PubFactoryInfoModel();
					paramModel.setFactoryName(ContextUtil.getCurrentUser().getCurFactoryCode());
					String orderType = detailList.get(0).getType();
					String orderTypeStr = null;
					if (("01").equals(orderType)) {
						orderTypeStr = "量产";
					} else if (("02").equals(orderType)) {
						orderTypeStr = "例外";
					} else if (("05").equals(orderType)) {
						orderTypeStr = "样件";
					} else if (("12").equals(orderType)) {
						orderTypeStr = "售后";
					}
					paramModel.setOrderType(orderTypeStr);
					// 页数
					paramModel.setYs(((detailList.size() % pageSize == 0) ? detailList.size() / pageSize
							: detailList.size() / pageSize + 1) + "");
					JasperPrint jasperPrint = PrintOrderUtil.getJasPerPrintUtil(detailList, file, paramModel);
					if (null != jasperPrint) {
						JasperPrintList.add(jasperPrint);
					}
				}
				PrintOrderUtil.exportReportOrderUtil(response, JasperPrintList);
				// 如果为供应商用户修改发货数，以及发货状态
				if ("2".equals(ContextUtil.getCurrentUser().getUserType())) {
					swDemondOrderManager.updatePrintInfo(list_printInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				String resultMsg = "打印失败";
				writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
			}

		}
	}

	/**
	 * 订单打印A4
	 * 
	 * @param request
	 * @param response
	 * @throws Exception Lxh
	 */
	@RequestMapping("DemondOrderPrintA4")
	public void jitOrderPrintA4(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNoStr = RequestUtil.getString(request, "orderNo");
		String[] orderNoArr = orderNoStr.split(",");
		List<SwDemondOrderDetailModel> detailList = null;
		List<SwDemondOrderDetailModel> topList = null;
		List<SwDemondOrderDetailModel> updateList = new ArrayList<SwDemondOrderDetailModel>();

		try {

			if (null != orderNoArr && orderNoArr.length > 0) {
				// 打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();

				for (int i = 0; i < orderNoArr.length; i++) {
					SwDemondOrderDetailModel model_q = new SwDemondOrderDetailModel();
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
							+ "ireport" + File.separator + "demo" + File.separator + "DEMOND_ORDER_A4.jasper";
					model_q.setOrderNo(orderNoArr[i]);
					model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
					model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
					updateList.add(model_q);
					// 查询订单头
					detailList = swDemondOrderManager.queryDemondOrderDetailList(model_q);
					// 查询订单明细
					topList = swDemondOrderManager.queryDemondOrderPrintLabelList(model_q);
					int num = 0;
					for (int m = 0; m < topList.size(); m++) {
						num = num + Integer.parseInt(topList.get(m).getBoxesNum());
					}
					for (int j = 0; j < detailList.size(); j++) {
						detailList.get(j).setRowId((j + 1) + "");
						detailList.get(j).setPageCount((j + 1) + "/" + orderNoArr.length);
						detailList.get(j).setLabelCount(num + "");
					}
//					for (int j = 0; j < detailList.size(); j++) {
//						detailList.get(j).setRowId((j + 1) + "");
//						detailList.get(j).setPageCount((j + 1) + "/" + orderNoArr.length);
//						detailList.get(j).setLabelCount(detailList.size() + "");
//
//					}
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					if (null != detailList && detailList.size() > 0) {
						SwDemondOrderDetailModel model = detailList.get(0);
						String orderType = model.getOrderType();// 订单类型
						String spType = model.getSpType();// 售后订单类型,2,3采用特殊打印类型
						BufferedImage img = BarCodeImage.creatBarCode(model.getOrderNo());
						if ("12".equals(orderType) && ("2".equals(spType) || "3".equals(spType))) {
							filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
									+ "ireport" + File.separator + "demo" + File.separator
									+ "DEMOND_ORDER_SP_A4.jasper";
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
					// 如果为供应商用户修改发货数，以及打印状态
					if ("2".equals(ContextUtil.getCurrentUser().getUserType())) {
						swDemondOrderManager.updatePrintInfo(updateList);
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
	 * 托盘标签打印
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("demondOrderPrintTpLabel")
	public void demondOrderPrintTpLabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
						parameters.put("arriveTime", model.getArriveTime());
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

	/**
	 * 
	 * @Description: 全部发货
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @return void
	 * @throws IOException
	 * @throws             @author luoxq
	 * @date 2019年5月27日 下午5:03:28
	 */
	@RequestMapping("updateAllOrderObj")
	public void updateAllOrderObj(HttpServletRequest request, HttpServletResponse response,
			SwDemondOrderDetailModel model) throws IOException {
		ResultMessage message = null;
		try {
			IUser user = ContextUtil.getCurrentUser();
			model.setLastModifiedUser(user.getAccount());
			model.setFactoryCode(user.getCurFactoryCode());
			swDemondOrderManager.updateAllOrderObj(model, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "全部发货数修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "系统异常，请联系管理员");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
