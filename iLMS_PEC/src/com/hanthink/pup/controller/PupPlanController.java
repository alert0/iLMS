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

import com.hanthink.base.model.DictVO;
import com.hanthink.business.util.SessionKey;
import com.hanthink.pup.manager.PupPlanManager;
import com.hanthink.pup.model.PupPlanModel;
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
 * 功能描述:取货计划查询控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/pup/getPlan")
@Controller
public class PupPlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupPlanController.class);
	
	@Resource
	private PupPlanManager planManager;
	/**
	 * 取货计划查询业务控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryPlanForPage(HttpServletRequest request,HttpServletResponse response,
													PupPlanModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupPlanModel> list = planManager.queryPlanForPage(model,page);
		
		return new PageJson(list);
	}
	/**
	 * 数据字典项下载状态加载控制器
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/getDownloadStatus")
	public @ResponseBody Object getDiffFlag()throws Exception {
		try {
			List<DictVO> models = planManager.getDownloadStatus();
			return new PageJson(models);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			return null;
		}
	}
	/**
	 * 单条/批量删除数据控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/remove")
	public void deleteById(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String[] orderNos = RequestUtil.getStringAryByStr(request, "orderNo");
		String[] purchaseNos = RequestUtil.getStringAryByStr(request, "purchaseNo");
		String resultMsg = null;
		try {
			planManager.deletePlans(orderNos,purchaseNos,RequestUtil.getIpAddr(request));
			resultMsg = "删除数据成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	/**
	 *  取货计划查询导入控制器
	 * @param request
	 * @param response
	 * @param file
	 * @author zmj
	 * @throws Exception 
	 * @date 2018年9月30日
	 */
	@RequestMapping("/planExcelImport")
	public void planExcelImport(HttpServletRequest request,HttpServletResponse response,
									@RequestParam("file")MultipartFile file) throws Exception {
		String uuid = null;
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			uuid = (String) session.getAttribute(SessionKey.PUP_PLAN_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				planManager.deletePlanByUUID(uuid);
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.PUP_PLAN_IMPORT_UUID, uuid);
			
			rtn = planManager.importPlanToTempTable(file,uuid,RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if ((boolean) rtn.get("result")) {
				writeResultMessage(response.getWriter(), "文件导入成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), (String) rtn.get("console"), (String) rtn.get("console"), JSONObject.fromObject(rtn), ResultMessage.FAIL);
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
	 * 查询导入数据详情控制器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/queryImportForPage")
	public @ResponseBody PageJson queryImportForPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.PUP_PLAN_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupPlanModel> pageList = planManager.queryImportForPage(paramMap,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 确认导入控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ResultMessage resultMsg = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.PUP_PLAN_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid",uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			paramMap.put("opeIp", RequestUtil.getIpAddr(request));
			
			planManager.insertTempToFormal(paramMap,RequestUtil.getIpAddr(request));
			resultMsg = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), resultMsg);
	}
	/**
	 * 临时数据导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@RequestMapping("/exportTempData")
	public void exportTempDataForExcel(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try {
			Map<String,String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.PUP_PLAN_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			List<PupPlanModel> list = planManager.queryImportForExport(paramMap);
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
			String[] headers = {"区域","车型区分","路线代码","累计车次","合并车次","出货地代码","供应商代码","物流单号","订单号","工作日","当日车次",
								"计划取货日期","计划取货时间","计划到货日期","计划到货时间","计划装配日期",
								"计划装配时间","订购用途","内物流管理员","确认天数"};
			String[] columns = {"area","carType","routeCode","totalBatchs","mergeBatchs","supFactory","supplierNo","orderNo",
					"purchaseNo","workDate","todayCarBatch","planPickupDate","planPickupTime","planArrDate","planArrTime","planAssembleDate",
					"planAssembleTime","orderUse","outerLogisManager","interLogisManager","confirmDays"};
			int[] widths = {100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100, 100,100,100,100, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "取货计划查询导入数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	@RequestMapping("/exportPlan")
	public void exportPlanForquery(HttpServletRequest request,HttpServletResponse response,
										PupPlanModel model)throws Exception {
		try {
			List<PupPlanModel> list = planManager.queryPlanForExport(model);
			
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000);
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"物流单号","订单号","出货地","区域", "供应商代码","供应商名称", "集货线路","车型","当日车次","前版对比","累计车次",
								"合并车次","订单物流模式","计划取货时间","计划到货时间","计划装配时间","订购用途","用户ID","内物流管理员","确认天数",
								"反馈状态","下载状态"};
			String[] columns = {"orderNo","purchaseNo","supFactory", "area", "supplierNo", "supplierName","routeCode","carType","todayCarBatch","diffFlag","totalBatchs",
								"mergeBatchs","pickupType","planPickupTime","planArrTime","planAssembleTime","orderUse","creationUser","interLogisManager","confirmDays",
								"excelFeedBackStatus","excelDownloadStatus"};
			int[] widths = {80, 80, 80, 80, 80,  100, 80, 80, 80, 80,  80, 80, 100, 100, 100,  100, 120, 80, 100, 80,  80, 80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "取货计划调整数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				ExcelUtil.exportException(e, request, response);
			}
	}
	/**
	 * 已发车查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月22日
	 */
	@RequestMapping("/depEnqQuery")
	public @ResponseBody PageJson queryForDepEnq(HttpServletRequest request,HttpServletResponse response,
																	PupPlanModel model)throws Exception{
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupPlanModel> pageList = planManager.queryForDepEnq(model,page);
 		
		return new PageJson(pageList);
	}
	
	@RequestMapping("/exportDepEnq")
	public void exportDepEnq(HttpServletRequest request,HttpServletResponse response,
									PupPlanModel model)throws Exception {
		try {
			List<PupPlanModel> list = planManager.queryForExportDepEnq(model);
			
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000);
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"物流单号","订单号","出货地","区域", "供应商代码","集货线路","车牌号","车型","当日车次","累计车次",
								"合并车次","订单物流模式","计划取货时间","计划到货时间","计划装配时间","订购用途","内物流管理员","确认天数","是否发车"};
			String[] columns = {"orderNo","purchaseNo","supFactory", "area", "supplierNo","routeCode","plateNum","carType","todayCarBatch","totalBatchs",
								"mergeBatchs","pickupType","planPickupTime","planArrTime","planAssembleTime","orderUse","interLogisManager","confirmDays","excelDeqStatus"};
			int[] widths = {80, 80, 80, 80, 80,  100, 80, 80, 80, 80,  80, 80, 80, 100, 100,  100, 100, 120, 80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "已发车查询数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				ExcelUtil.exportException(e, request, response);
			}
	}
}
