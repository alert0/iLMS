package com.hanthink.pup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.pup.manager.PupManualOrderManager;
import com.hanthink.pup.model.PupManualOrderModel;
import com.hanthink.pup.model.PupManualOrderPageModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
 * <pre>
 * 描述:手工调整订单控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月24日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/pup/manualOrder")
@Controller
public class PupManualOrderController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupPickTimeController.class);
	
	@Resource
	PupManualOrderManager orderManager;
	/**
	 * 数据查询控制器
	 *@param request
	 *@param model
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月24日
	 */
	@RequestMapping("/listManualOrder")
	public @ResponseBody PageJson queryManualOrderForPage(HttpServletRequest request,
									PupManualOrderPageModel model)throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute(SessionKey.PUP_MANUALORDER_QYERFILTER, model);
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupManualOrderModel> pageList = orderManager.queryManualOrderForPage(model,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 批量/单个删除控制器类
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	@RequestMapping("/deleteManualOrder")
	public void removeManualOrder(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String resultMsg = null;
		String purchaseNo[] = RequestUtil.getStringAryByStr(request, "purchaseNo");
		try {
			orderManager.removeManualOders(purchaseNo);
			resultMsg = "删除成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "删除失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	/**
	 * 页面数据导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	@RequestMapping("/exportDataForExcel")
	public void exportForExcel(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			HttpSession session = request.getSession();
			PupManualOrderPageModel model = (PupManualOrderPageModel) session.getAttribute(SessionKey.PUP_MANUALORDER_QYERFILTER);
			List<PupManualOrderModel> list = orderManager.queryManualOederForExport(model);
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
			String[] headers = {"订单号","物流单号","工厂代码","取货标识","合并车次","取货区域","订单描述","供应商出货地",
								"订单物流模式","车型","工作日","取货日期","取货时间","到货日期","到货时间","订单交货时间",
								"集货线路","累计车次","当日车次","创建人","创建时间","最后修改用户","最后修改时间"};
			String[] columns = {"purchaseNo","orderNo","factoryCode","pickupFlag","mergeNo","area","orderDesc","supFactory",
								"pickupType","carType","workday","pickDate","pickTime","arriveDate","arriveTime","orderDate",
								"routeCode","totalNo","todayNo","creationUser","creationTime","lastModifiedUser","lastModifiedTime"};
			int[] widths = {80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "MM_PUP_MANUALORDER_LOAD", list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 数据导入控制器类
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	@RequestMapping("/importManualOder")
	public void importManualOrder(HttpServletRequest request,HttpServletResponse response,@RequestParam("file")MultipartFile file) throws Exception {
		String uuid = null;
		try {
			HttpSession session = request.getSession();
			uuid = (String) session.getAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID);
			if(StringUtil.isNotEmpty(uuid)) {
				orderManager.deleteTempManualOrderByUUID(uuid);
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID, uuid);
			
			Map<String, Object> resultMap = orderManager.importManualOrderToTempTable(file, uuid, RequestUtil.getIpAddr(request));
			if ((boolean) resultMap.get("result")) {
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.SUCCESS);
			}else {
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
		}
	}
	
	@RequestMapping("/curdlistImportTempJson")
	public @ResponseBody PageJson queryImportDataForPage(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		paramMap.put("uuid", (String) session.getAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID));
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupManualOrderModel> list = orderManager.queryImportManualOrderForPage(paramMap, page);
		return new PageJson(list);
	}
	/**
	 * 确定导入数到正式表控制器类
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		ResultMessage resultMsg = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			paramMap.put("uuid", session.getAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID));
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			
			orderManager.insertImportDataToFormalTable(paramMap,RequestUtil.getIpAddr(request));
			resultMsg = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), resultMsg);
	}
	
	@RequestMapping("/exportTempData")
	public void exportImportMunualOrder(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			paramMap.put("uuid", (String) session.getAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID));
			List<PupManualOrderModel> list = orderManager.queryManualOrderTempDataForExport(paramMap);
			for (PupManualOrderModel orderModel : list) {
				if (orderModel.getCheckResult().equals("1")) {
					orderModel.setCheckResult("通过");
				}else {
					orderModel.setCheckResult("需修改");
				}
			}
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
			String[] headers = {"取货标识","取货区域","订单号","订单描述","出货地区","物流模式","车型区分",
								"物流单号","工作日","计划取货日期","计划取货时间","计划到达日期","计划到达时间","交货时间",
								"线路代码","累计车次","当日车次","累计车次","校验结果","校验信息"};
			String[] columns = {"pickupFlag","area","purchaseNo","orderDesc","supFactory","pickupType","carType",
								"orderNo","workday","pickDate","pickTime","arriveDate","arriveTime","orderDate",
								"routeCode","totalNo","todayNo","mergeNo","checkResult","checkInfo"};
			int[] widths = {80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "MM_PUP_MANUAL_ORDER_UPDATE", list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
}
