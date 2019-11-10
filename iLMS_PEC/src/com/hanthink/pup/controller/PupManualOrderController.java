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
import com.hanthink.pup.util.PupUtil;
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
	private PupManualOrderManager orderManager;
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
									PupManualOrderModel model)throws Exception {

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
			orderManager.removeManualOders(purchaseNo,RequestUtil.getIpAddr(request));
			resultMsg = "删除数据成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
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
	public void exportForExcel(HttpServletRequest request,HttpServletResponse response,
										PupManualOrderModel model)throws Exception {
		try {
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
			String[] headers = {"物流单号","订单号","出货地代码","车型","工作日","计划取货日期","计划取货时间","计划到货日期",
								"计划到货时间","订单到货时间","集货线路","累计车次","合并车次","当日车次","订单物流模式","区域",
								"特殊标识","订单说明"};
			String[] columns = {"orderNo","purchaseNo","supFactory","carType","workday","pickDate","pickTime","arriveDate",
								"arriveTime","orderDate","routeCode","totalNo","mergeNo","todayNo","pickupType","area",
								"pickupFlag","orderDesc"};
			int[] widths = {80, 80, 80, 80, 80, 80,
							80, 80, 80, 80, 80, 80,
							80, 80, 80, 80, 80, 80,};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "例外订单-SP件数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
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
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			uuid = (String) session.getAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID);
			if(StringUtil.isNotEmpty(uuid)) {
				orderManager.deleteTempManualOrderByUUID(uuid);
			}else {
				if (StringUtil.isEmpty(uuid)) {
					uuid = RequestUtil.getString(request, "uuid");
					if (StringUtil.isNotEmpty(uuid)) {
						orderManager.deleteTempManualOrderByUUID(uuid);
					}else {
						uuid = UUID.randomUUID().toString().replaceAll("-", "");
					}
				}
			}
			session.setAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID, uuid);
			
			rtn = orderManager.importManualOrderToTempTable(file, uuid, RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if ((boolean) rtn.get("result")) {
				writeResultMessage(response.getWriter(), "导入成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			rtn.put("result", false);
			rtn.put("console", e.getMessage());
			writeResultMessage(response.getWriter(), (String) rtn.get("console"), ResultMessage.FAIL);
		}
	}
	/**
	 * 导入数据查询控制器接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	@RequestMapping("/curdlistImportTempJson")
	public @ResponseBody PageJson queryImportDataForPage(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		
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
			String uuid = (String) session.getAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			
			orderManager.insertImportDataToFormalTable(paramMap,RequestUtil.getIpAddr(request));
			resultMsg = new ResultMessage(ResultMessage.SUCCESS, "执行数据写入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), resultMsg);
	}
	/**
	 * 临时数据Excel导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/exportTempData")
	public void exportImportMunualOrder(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.PUP_MANUALORDER_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			
			List<PupManualOrderModel> list = orderManager.queryManualOrderTempDataForExport(paramMap);
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
			
			String[] headers = {"物流单号","订单号","出货地代码","供应商代码","车型","集货线路","累计车次",
								"特殊标识","订单说明","导入状态","校验结果","校验信息"};
			String[] columns = {"orderNo","purchaseNo","supFactory","supplierNo","carType","routeCode","totalNo",
								"excelPickupFlag","orderDesc","excelImportStatus","excelCheckResult","checkInfo"};
			int[] widths = {80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "例外订单-SP件导入数据详情"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
}
