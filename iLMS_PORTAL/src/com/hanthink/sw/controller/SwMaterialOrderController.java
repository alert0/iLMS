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

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.sw.manager.SwMaterialOrderManager;
import com.hanthink.sw.model.SwMaterialOrderModel;
import com.hanthink.sw.model.SwMaterialOrderModel;
import com.hanthink.util.BufferImage.BarCodeImage;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.entity.CurrentUser;
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
import net.sf.json.JSONObject;

/**
 * 资材订单
 * 
 * @author 李兴辉
 *
 */
@Controller
@RequestMapping("/sw/swMaterialOrder")
public class SwMaterialOrderController extends GenericController {

	@Resource
	private SwMaterialOrderManager swMaterialOrderManager;

	private static Logger log = LoggerFactory.getLogger(SwMaterialOrderController.class);

	/**
	 * 订单查询
	 * 
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception Lxh
	 */
	@RequestMapping("/queryMaterialOrderPage")
	public @ResponseBody PageJson queryMaterialOrderPage(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") SwMaterialOrderModel model) throws Exception {
		DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SwMaterialOrderModel> pageList = (PageList<SwMaterialOrderModel>) swMaterialOrderManager
				.queryMaterialOrderPage(model, p);
		return new PageJson(pageList);
	}



	/**
	 * 批量导出
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("downloadMaterialOrderByBatch")
	public void downloadMaterialOrderByBatch(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") SwMaterialOrderModel model) throws Exception {

		try {
			String exportFileName = "资材订单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			System.out.println(model);
			PageList<SwMaterialOrderModel> pageList = (PageList<SwMaterialOrderModel>) swMaterialOrderManager
					.queryMaterialOrderPage(model, page);
			int curNum = pageList.getPageResult().getTotalCount();
			if (0 == curNum) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
			int sysMaxNum_T = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000);
			if (curNum > sysMaxNum_T) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			List<SwMaterialOrderModel> list = swMaterialOrderManager.downloadMaterialOrder(model);
			if (null != list) {
				//订单号	采购单行号	物料编号	资材名称	规格	订购数量	单位	订购日期	订购人	
				//联系方式	供应商代码	供应商名称	仓库地址	库存区分	费用中心代码	成本中心代码	
				//订单打印时间	反馈状态	反馈时间	计划交货日期	交货数量	反馈备注
				String[] headers = { "订单号", "采购单行号", "物料编号", "资材名称", "规格", 
									"订购数量", "单位", "订购日期","订购人", "联系方式", "供应商代码",
									"供应商名称", "仓库地址", "库存区分", "费用中心代码", "成本中心代码", "订单打印时间", "反馈状态",
									 "反馈时间", "计划交货日期" , "交货数量",  "反馈备注" };
				String[] columns = { "purchaseNo", "purchaseRowNo", "partNo", "partName", "standPackage",
						"orderQty", "orderUnit", "orderDate", 
						"recUser", "recTel", "supplierNo","supplierName", "recAdress",
						"invType", "costCode", "costCenter", "printTime", "returnStatus",
						"returnTime", "planTime", "planNum", "returnMsg" };
				
				/*String[] headers = { "订单号", "库存区分", "物料编号", "费用中心代码", "资材名称", "成本中心代码", "规格", "要求交货日期", "订购数量", "订单状态",
						"订购单位", "打印状态", "订购日期", "打印时间", "订购人", "反馈状态", "收货联系方式", "反馈时间", "收货地址" , "计划交货数", "供应商代码", "反馈备注" };
				String[] columns = { "purchaseNo", "invType", "partNo", "costCode", "partName",
						"costCenter", "standPackage", "arriveDate", "orderQty", "orderStatus", "orderUnit",
						"printStatus", "orderDate", "printTime", "recUser", "returnStatus", "recTel", "returnTime",
						"recAdress", "planNum", "supplierNo", "returnMsg" };
				int[] widths = { 100, 100, 100, 100, 150, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
						100, 100, 100, 100, 100 };*/
				int[] widths = { 100, 100, 100, 100, 150, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
						100, 100, 100, 100, 100 };
				if (curNum <= sysMaxNum_T) {
					ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers,
							widths, columns);
				} else {
					// ExcelExportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					// exportFileName, list, headers, widths, columns);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}


	/**
	 * 交货反馈修改
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/updateMaterialOrderPage")
	public void updateMaterialOrderPage(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SwMaterialOrderModel model) throws Exception {

		IUser user = ContextUtil.getCurrentUser();
		model.setLasetModifiedUser(user.getAccount());
		String message = "";
		try {
			Boolean flag = swMaterialOrderManager.updateMaterialOrderPage(model);
			if (flag) {
				message = "更新成功";
				writeResultMessage(response.getWriter(), message, ResultMessage.SUCCESS);
			} else {
				message = "更新失败";
				writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
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
	@RequestMapping("printLabel")
	public void printLabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String replySeqNos = request.getParameter("replySeqNos");
		String purchaseNos = request.getParameter("purchaseNos");
		String purchaseRowNos = request.getParameter("purchaseRowNos");
		String[] replySeqNo = replySeqNos.split(",");
		String[] purchaseNo = purchaseNos.split(",");
		String[] purchaseRowNo = purchaseRowNos.split(",");
		List<SwMaterialOrderModel> list = null;

		try {

			if (null != replySeqNo && replySeqNo.length > 0) {
				// 打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();

				for (int i = 0; i < replySeqNo.length; i++) {
					SwMaterialOrderModel orderModel = new SwMaterialOrderModel();
					orderModel.setReplySeqNo(replySeqNo[i]);
					orderModel.setPurchaseNo(purchaseNo[i]);
					orderModel.setPurchaseRowNo(purchaseRowNo[i]);

					list = swMaterialOrderManager.queryDemondOrderPrintLabelList(orderModel);
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
							+ "ireport" + File.separator + "demo" + File.separator + "MM_MATERIAL_LABEL.jasper";
					Map<String, Object> map = new HashMap<String, Object>();
					//					<parameter name="purchaseNo" class="java.lang.String"/>
					//					<parameter name="partNo" class="java.lang.String"/>
					//					<parameter name="partName" class="java.lang.String"/>
					//					<parameter name="standPackage" class="java.lang.String"/>
					//					<parameter name="invType" class="java.lang.String"/>
					//					<parameter name="recUser" class="java.lang.String"/>
					//					<parameter name="recTel" class="java.lang.String"/>
					//					<parameter name="costCode" class="java.lang.String"/>
					//					<parameter name="planNum" class="java.lang.String"/>
					//					<parameter name="orderUnit" class="java.lang.String"/>
					//					<parameter name="planTime" class="java.lang.String"/>
					//					<parameter name="returnTime" class="java.lang.String"/>
					//					<parameter name="supplierNo" class="java.lang.String"/>
					//					<parameter name="supplierName" class="java.lang.String"/>
					SwMaterialOrderModel m = list.get(0);
					map.put("purchaseNo",m.getPurchaseNo() );
					map.put("partNo", m.getPartNo());
					map.put("partName", m.getPartName());
					map.put("standPackage", m.getStandPackage());
					map.put("invType", m.getInvType());
					map.put("recUser", m.getRecUser());
					map.put("recTel", m.getRecTel());
					map.put("costCode", m.getCostCode());
					map.put("planNum", m.getPlanNum());
					map.put("orderUnit", m.getOrderUnit());
					map.put("returnTime", m.getReturnTime());
					map.put("supplierNo", m.getSupplierNo());
					map.put("supplierName", m.getSupplierName());
					map.put("planTime", m.getPlanTime());
					InputStream file = new FileInputStream(filenurl);

					JRDataSource jRDataSource = new JRBeanCollectionDataSource(list);
					JasperPrint jasperPrint = JasperFillManager.fillReport(file, map, jRDataSource);
					JasperPrintList.add(jasperPrint);
				}
				if (JasperPrintList.size() > 0) {
					response.setContentType("application/pdf");
					response.setHeader("Content-disposition", "inline;");
					JRPdfExporter exporter = new JRPdfExporter();
					OutputStream out = response.getOutputStream();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
					exporter.exportReport();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}


	/**
	 * 订单打印
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("orderPrint")
	public void orderPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String replySeqNos = request.getParameter("replySeqNos");
		String purchaseNos = request.getParameter("purchaseNos");
		String purchaserRowNos = request.getParameter("purchaserRowNos");
		String[] replySeqNo = replySeqNos.split(",");
		String[] purchaseNo = purchaseNos.split(",");
		String[] purchaserRowNo = purchaserRowNos.split(",");
		List<SwMaterialOrderModel> list = null;
		try {

			if (null != replySeqNo && replySeqNo.length > 0) {
				// 打印N张
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();

				for (int i = 0; i < replySeqNo.length; i++) {
					SwMaterialOrderModel orderModel = new SwMaterialOrderModel();
					orderModel.setReplySeqNo(replySeqNo[i]);
					orderModel.setPurchaseNo(purchaseNo[i]);
					orderModel.setPurchaseRowNo(purchaserRowNo[i]);

					list = swMaterialOrderManager.queryDemondOrderPrintLabelList(orderModel);
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
							+ "ireport" + File.separator + "demo" + File.separator + "MM_MATERIAL_ORDER.jasper";
					Map<String, Object> map = new HashMap<String, Object>();
					SwMaterialOrderModel model = list.get(0);
					InputStream file = new FileInputStream(filenurl);
					// 零件号#包装数#erp单号#物流单行号#物流单号#零件简号#订单类型#追朔等级#供应商代码#零件批次号#同批次数量#uuid
										
					String imgUrl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
							+ "img" + File.separator + "gacneLogo.jpg";
					BufferedImage img = ImageIO.read(new FileInputStream(imgUrl));
					//String code = model.getPurchaseNo() ;
					String code = model.getPartNo() + "#" + model.getStandPackage() + "#" + model.getPurchaseNo()
					+ "#" + model.getPurchaseRowNo() + "#" + model.getPurchaseNo() + "#" + model.getPartShortNo()
					+ "#" + "14" + "#" + "" + "#" + model.getSupplierNo() + "#" + "" + "#"
					+ model.getOrderQty() + "#" + UUID.randomUUID().toString().replaceAll("-", "");
					BufferedImage qrImg = MakeQrcodeImages.getQrCodeImage(code, "210", "210");
					BufferedImage brImg = BarCodeImage.creatBarCode(model.getPurchaseNo());
					map.put("QR_INFOS", qrImg);
					map.put("BR_INFOS", brImg);
					map.put("GQ_INFOS", img);
					JRDataSource jRDataSource = new JRBeanCollectionDataSource(list);
					JasperPrint jasperPrint = JasperFillManager.fillReport(file, map, jRDataSource);
					JasperPrintList.add(jasperPrint);
					swMaterialOrderManager.updatePrintStatus(RequestUtil.getIpAddr(request), model);
				}
				if (JasperPrintList.size() > 0) {
					response.setContentType("application/pdf");
					response.setHeader("Content-disposition", "inline;");
					JRPdfExporter exporter = new JRPdfExporter();
					OutputStream out = response.getOutputStream();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
					exporter.exportReport();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}



	/**
	 * 交货反馈临时数据查询
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * Lxh
	 */
	@RequestMapping("/queryMaterialOrderReturnTmp")
	public @ResponseBody PageJson queryMaterialOrderReturnTmp(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") SwMaterialOrderModel model) throws Exception {
		DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SwMaterialOrderModel> pageList = (PageList<SwMaterialOrderModel>) swMaterialOrderManager
				.queryMaterialOrderReturnTmp(model, p);
		return new PageJson(pageList);
	}

	/**
	 * 交货反馈临时数据新增
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/insertMaterialOrderReturnTmp")
	public void insertMaterialOrderReturnTmp(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SwMaterialOrderModel model) throws Exception {


		String message = "";
		try {
			Boolean flag = swMaterialOrderManager.insertMaterialOrderReturnTmp(model);
			if (flag) {
				message = "新增成功";
				writeResultMessage(response.getWriter(), message, ResultMessage.SUCCESS);
			} else {
				message = "新增失败";
				writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}


	/**
	 * 交货反馈临时数据修改
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/updateMaterialOrderReturnTmp")
	public void updateMaterialOrderReturnTmp(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SwMaterialOrderModel model) throws Exception {

		String message = "";
		try {
			Boolean flag = swMaterialOrderManager.updateMaterialOrderReturnTmp(model);
			if (flag) {
				message = "修改成功";
				writeResultMessage(response.getWriter(), message, ResultMessage.SUCCESS);
			} else {
				message = "修改失败";
				writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}


	/**
	 * 交货反馈临时数据删除
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/deleteMaterialOrderReturnTmp")
	public void deleteMaterialOrderReturnTmp(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SwMaterialOrderModel model) throws Exception {
		try {
			String tmpIds = model.getTmpId();
			String[] tmpId = tmpIds.split(",");
			for (int i = 0; i < tmpId.length; i++) {
				SwMaterialOrderModel m = new SwMaterialOrderModel();
				m.setTmpId(tmpId[i]);
				swMaterialOrderManager.deleteMaterialOrderReturnTmp(m);
			}
			writeResultMessage(response.getWriter(), "删除成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 反馈临时表数据提交到业务表
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * Lxh
	 */
	@RequestMapping("/MaterialOrderReturnTmpToOrder")
	public void MaterialOrderReturnTmpToOrder(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SwMaterialOrderModel model) throws Exception {
		
		String message = "";
		int count = 0;
		try {
			
			String tmpId = model.getTmpId();//序列号
			String planTime = model.getPlanTime();//计划日期
			String planNum = model.getPlanNum();//反馈数
			String returnMsg = model.getReturnMsg();//备注
			String purchaseNo = model.getPurchaseNo();//订单号
			String purchaseRowNo = model.getPurchaseRowNo();//订单行号
			String[] spliTmpId = tmpId.split(",");
			String[] splitPlanTime = planTime.split(",");
			String[] spliPlanNum = planNum.split(",");
			String[] splitReturnMsg = returnMsg.split(",");
			for (int i = 0; i < spliTmpId.length; i++) {
				model.setPurchaseNo(purchaseNo);
				model.setPurchaseRowNo(purchaseRowNo);
				model.setTmpId(spliTmpId[i]);
				model.setPlanTime(splitPlanTime[i]);
				model.setPlanNum(spliPlanNum[i]);
				model.setReturnMsg(splitReturnMsg != null ? splitReturnMsg[i] : "");
				Boolean materialOrderReturnTmpToOrder = swMaterialOrderManager.MaterialOrderReturnTmpToOrder(model);
				if (materialOrderReturnTmpToOrder) {
					count ++;
				}
			}
			if (count == spliTmpId.length) {
				message = "反馈成功";
				writeResultMessage(response.getWriter(), message, ResultMessage.SUCCESS);
			} else {
				message = "反馈失败";
				writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
			
//		IUser user = ContextUtil.getCurrentUser();
//		Boolean returnFull = swMaterialOrderManager.checkMeetDelivery(model);//判断反馈数是否满足订单总数
//
//		if (returnFull) {
//
//		} else {
//			writeResultMessage(response.getWriter(), "已反馈数量汇总不等于订单总数", ResultMessage.FAIL);
//			return;
//		}
//		int count = swMaterialOrderManager.getMaterialOrderReturnTmpCount(model);//汇总反馈条数，用于更新反馈状态
//		if (count == 1) {//当只反馈一次时反馈状态为满足
//			model.setReturnStatus("1");
//		} else if (count > 1) {//当多次反馈时状态为多次发货
//			model.setReturnStatus("2");
//		}
//
//		String message = "";
//
//		Boolean returnFlag = swMaterialOrderManager.updateReturnStatus(model);//更新反馈状态
//
//		if (!returnFlag) {
//			message = "反馈状态更新失败";
//			writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
//		}
//		try {
//			Boolean flag = swMaterialOrderManager.MaterialOrderReturnTmpToOrder(model);//反馈临时表数据提交到业务表
//			if (flag) {
//				message = "反馈成功";
//				writeResultMessage(response.getWriter(), message, ResultMessage.SUCCESS);
//			} else {
//				message = "反馈失败";
//				writeResultMessage(response.getWriter(), message, ResultMessage.FAIL);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error(e.toString());
//			throw new Exception("系统错误,请联系管理员");
//		}
	}

	/**
	 * 资材反馈Excel导入
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * 
	 */
	@RequestMapping("/importForExcel")
	public void importMaterialOrderForExcel(HttpServletRequest request,HttpServletResponse response,
											@RequestParam("file") MultipartFile file)throws Exception {
		String uuid = RequestUtil.getString(request, "uuid");
		if (StringUtil.isNotEmpty(uuid)) {
			swMaterialOrderManager.deleteTempMaterialOrderByUUID(uuid);
		}else {
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
		}
		try {
			Map<String, Object> resultMap = swMaterialOrderManager.importMaterialOrderModel(file,uuid,RequestUtil.getIpAddr(request));
			resultMap.put("uuid", uuid);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), "数据导入成功", "", JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", e.getMessage());
			writeResultMessage(response.getWriter(), (String) rtn.get("console"), ResultMessage.FAIL);
		}
	}
	/**
	 * 查询导入数据的详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping("/queryImportForPage")
	public @ResponseBody PageJson queryImportForPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String uuid = RequestUtil.getString(request, "uuid");
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<SwMaterialOrderModel> pageList = swMaterialOrderManager.queryImportForPage(uuid,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 确认导入,将临时表数据写入正式表
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		ResultMessage resultMessage = null;
		String uuid = RequestUtil.getString(request, "uuid");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//查询导入校验结果是否包含不通过
			int count = swMaterialOrderManager.queryIsExistsCheckResultFalse(uuid);
			if(count > 0) {
				writeResultMessage(response.getWriter(), "存在校验结果不通过数据", ResultMessage.FAIL);
				return;
			}
			paramMap.put("ipAddr", RequestUtil.getIpAddr(request));
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			swMaterialOrderManager.insertTempToFormal(paramMap);
			resultMessage = new ResultMessage(ResultMessage.SUCCESS, "数据导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		    resultMessage = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), resultMessage);
	}
	
	@RequestMapping("/exportForImport")
	public void exportForImport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String uuid = RequestUtil.getString(request, "uuid");
		try {
			List<SwMaterialOrderModel> list = swMaterialOrderManager.exportForImport(uuid);
			/**
			 * 如果查询记录超过100000条则报错
			 */
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000);
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"订单号","订单行号", "物料编号", "资材名称","规格",
					"订购数量","单位","订购日期","订购人", "联系方式",
					"供应商代码", "供应商名称", "仓库地址", "库存区分", "费用中心代码", 
					"成本中心代码", "订单打印时间", "反馈状态", "反馈时间 ", "计划交货日期", 
					"交货数量", "反馈备注","导入状态","校验结果","校验信息"};
			//订单号	采购单行号	物料编号	资材名称	规格	订购数量	单位	订购日期	订购人	联系方式	供应商代码	
//			供应商名称	仓库地址	库存区分	费用中心代码	成本中心代码	订单打印时间	反馈状态	反馈时间	计划交货日期	交货数量	反馈备注
			String[] columns = {"purchaseNo","purchaseRowNo","partNo","partName","standPackage",
					"orderQty","orderUnit","orderDate","recUser","recTel",
					"supplierNo","supplierName","recAdress","invType","costCode",
					"costCenter","printTime","returnStatus","returnTime","planTime",
					"planNum","returnMsg","excelImportStatus","excelCheckResult","checkInfo"};
			int[] widths = {100,60, 60, 80, 60, 
					60, 60, 70, 70, 120,
					60, 60, 70, 70, 120,
					60, 60, 70, 70, 120,
					60, 60, 70, 70, 120};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "资材反馈导入"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}

}
