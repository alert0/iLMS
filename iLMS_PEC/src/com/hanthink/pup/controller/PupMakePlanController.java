package com.hanthink.pup.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pup.manager.PupMakePlanManager;
import com.hanthink.pup.model.PupMakePlanModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:取货计划生成业务控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月27日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/pup/makePlan")
@Controller
public class PupMakePlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupMakePlanController.class);
	
	@Resource
	PupMakePlanManager makePlanManager;
	
	/**
	 * 取货计划生成查询业务控制器
	 * @param request 请求
	 * @param response 响应
	 * @param model 请求参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryPlanForPage(HttpServletRequest request,HttpServletResponse response,
													PupMakePlanModel model)throws Exception{
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupMakePlanModel> pageList = makePlanManager.queryForPage(model,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 生成取货计划控制器
	 * @param request 请求
	 * @param response 响应
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@RequestMapping("/makeLogisticsPlan")
	public void makeLogisticsPlan(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String resultMsg = null;
		try {
			String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
			int resultCode = makePlanManager.makeLogisticsPlan(factoryCode);
			if(resultCode == 1) {
				resultMsg = "生成物流计划成功";
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
			}else {
				resultMsg = "生成物流计划失败";
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
			}
		}catch (Exception e) {
			e.printStackTrace();
			resultMsg = "系统错误,请联系管理员";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
		}
	}
	/**
	 * 发布取货计划到信息共享平台
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/publish")
	public void publishToPlatform(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String resultMsg = null;
		try {
			makePlanManager.publishToPlatform(RequestUtil.getIpAddr(request));
			resultMsg = "物流计划发布成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
		}
	}
	/**
	 * 范围趟次数据导出控制器
	 * @param request
	 * @param response
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@RequestMapping("/exportTripTime")
	public void exportTripTime(HttpServletRequest request,HttpServletResponse response) {
		try {
			String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
			List<PupMakePlanModel> list = makePlanManager.queryTripTimesForExport(factoryCode);
			
			String[] headers = {"区域", "供应商代码", "出货地代码","计算队列","订单物流模式","车型区分","集货线路","趟次","StartNo","EndNo","本次台套"};
			String[] columns = {"area", "supplierNo", "supFactory","unloadPort","pickupType","carType","routeCode","totalNo","startSortId","endSortId","tokenNum"};
			int[] widths = {100, 100, 100,100,100,100, 100,100,100,100,100};
			
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "范围趟次数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 导出取货时间数据控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@RequestMapping("/exportPickupTimes")
	public void exportPickupTimes(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try {
			String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
			List<PupMakePlanModel> list = makePlanManager.queryPickupTimesForExport(factoryCode);
			
			String[] headers = {"区域","卸货地点", "计算队列","订单物流模式","车型区分","集货线路","累计车次","合并车次","工作日","当日车次",
								"计划取货日期","计划取货时间","计划到货日期","计划到货时间","计划装配日期","计划装配时间","是否拼车"};
			String[] columns = {"area","unloadPlace", "unloadPort","pickupType","carType","routeCode","totalNo","mergeNo","workDay","todayNo",
								"pickDate","pickTime","arriveDate","arriveTime","assembleDate","assembleTime","isMerge"};
			int[] widths = {100,100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100, 100};
			
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "取货时间数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 导出取货计划DCS控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@RequestMapping("/exportPlanDCS")
	public void exportPlanDCS(HttpServletRequest request,HttpServletResponse response,
									PupMakePlanModel model)throws Exception{
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<PupMakePlanModel> list = makePlanManager.queryPlanDCSForExport(model);
			
			String[] headers = {"订单物流模式","区域", "计算队列", "车型区分","集货线路","累计车次","合并车次","供应商代码","出货地代码","供应商名称","物流单号",
								"订单号","工作日","当日车次","计划取货日期","计划取货时间","计划到货日期","计划到货时间","计划装配日期",
								"计划装配时间","订购用途","外物流管理员","内物流管理员"};
			String[] columns = {"pickupType","area", "unloadPort", "carType","routeCode","totalNo","mergeNo","supplierNo","supFactory","supplierName","orderNo",
								"purchaseNo","workDay","todayNo","pickDate","pickTime","arriveDate","arriveTime","assembleDate",
								"assembleTime","purposes","wwlManager","nwlManager"};
			int[] widths = {100, 100, 100, 100, 100, 100, 100,100,100,100,100, 100,100,100,100,100, 100, 100, 100, 100,100, 100, 100};
			
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "取货计划DCS"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 供应商备货数据导出控制器
	 * @param request 请求
	 * @param response 响应
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@RequestMapping("/exportSupplierStockNum")
	public void exportSupplierStockNum(HttpServletRequest request,HttpServletResponse response,
										PupMakePlanModel model)throws Exception {
		try {
			
			List<PupMakePlanModel> list = makePlanManager.querySupplierStockNumForExport(model);
			
			String[] headers = {"区域","车型区分","路线代码","累计车次","合并车次","出货地代码","供应商代码","物流单号","订单号","工作日","当日车次",
								"计划取货日期","计划取货时间","计划到货日期","计划到货时间","计划装配日期","计划装配时间","订购用途",
								"外物流管理员","内物流管理员","确认天数","物流模式", "计算队列"};
			String[] columns = {"area","carType","routeCode","totalNo","mergeNo","supFactory","supplierNo","orderNo","purchaseNo",
								"workDay","todayNo","pickDate","pickTime","arriveDate","arriveTime","assembleDate","assembleTime",
								"purposes","wwlManager","nwlManager","confirmDay","pickupType", "unloadPort"};
			int[] widths = {100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100, 100,100, 100};
			
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "物流计划上传数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 收货数据导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@RequestMapping("/exportPickData")
	public void exportPickData(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
			List<PupMakePlanModel> list = makePlanManager.queryPickDataForExport(factoryCode);
			String[] headers = {"订单物流模式","区域","车型区分","集货线路","累计车次","合并车次","出货地代码","供应商名称","物流单号",
								"订单号","工作日","当日车次","计划取货日期","计划取货时间","计划到货日期","计划到货时间","计划装配日期",
								"计划装配时间","收货日期","仓库","A班收货","B班收货","订购用途","外物流管理员","内物流管理员"};
			String[] columns = {"pickupType","area","carType","routeCode","totalNo","mergeNo","supFactory","supplierName","orderNo",
								"purchaseNo","workDay","todayNo","pickDate","pickTime","arriveDate","arriveTime","assembleDate",
								"assembleTime","recDate","wareCode","recShiftA","recShiftB","purposes","wwlManager","nwlManager"};
			int[] widths = {100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100};
			
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "到货计划"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 按模版导出箱种数据
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@RequestMapping("/exportBoxData")
	public void exportBoxForExcel(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			List<PupMakePlanModel> list = makePlanManager.exportBoxForExcel(ContextUtil.getCurrentUser().getCurFactoryCode());
			String[] headers = {"订单物流模式","区域","车型","集货线路","累计车次","合并车次","出货地代码","供应商名称","物流单号",
					"订单号","箱种","数量"};
			String[] columns = {"pickupType","area","carType","routeCode","totalNo","mergeNo","supFactory","supplierName","orderNo",
								"purchaseNo","boxType","boxRequireQTY"};
			int[] widths = {100,100,100,100,100, 100,100,100,100,100, 100,100};

			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "箱种数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 获取当前订单的发布状态
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月27日
	 */
	@RequestMapping("/queryPublishFlag")
	public void queryPublishFlag(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Integer resultMsg = null;
		try {
			resultMsg = makePlanManager.queryPublishFlag();
			writeResultMessage(response.getWriter(),resultMsg.toString(),ResultMessage.SUCCESS);
		} catch (Exception e) {
			String errMsg = "系统错误,请联系管理员";
			writeResultMessage(response.getWriter(),errMsg.toString(),ResultMessage.FAIL);
		}
	}
	/**
	 * 导出理论取货时间
	 * @param request
	 * @param response
	 * @author zmj
	 * @date 2019年2月15日
	 */
	@RequestMapping("exportDelayTime")
	public void exportDelayTime(HttpServletRequest request,HttpServletResponse response) {
		try {
			List<PupMakePlanModel> list = makePlanManager.queryForDelayTime();
			
			String[] headers = {"区域","车型区分","路线代码","累计车次","合并车次","出货地代码","供应商代码","物流单号","订单号","工作日","当日车次",
								"计划取货日期","计划取货时间","计划到货日期","计划到货时间","计划装配日期","计划装配时间","理论取货日期","理论取货时间","订购用途",
								"外物流管理员","内物流管理员","确认天数"};
			String[] columns = {"area","carType","routeCode","totalNo","mergeNo","supFactory","supplierNo","orderNo","purchaseNo",
								"workDay","todayNo","pickDate","pickTime","arriveDate","arriveTime","assembleDate","assembleTime","firstPickDate","firstPickTime",
								"purposes","wwlManager","nwlManager","confirmDay"};
			int[] widths = {100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100, 100,100,100,100,100, 100,100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "理论取货时间数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
}
