package com.hanthink.mp.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.MpDemandForecastManager;
import com.hanthink.mp.model.MpDemandForecastModel;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
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
 * 支给件预测数据处理
 * 
 * @author WY
 * 
 */
@Controller
@RequestMapping("/mp/mpDemandForecast")
public class MpDemandForecastController extends GenericController {

	private static Logger log = LoggerFactory
			.getLogger(MpDemandForecastController.class);

	@Resource
	MpDemandForecastManager manager;

	/**
	 * 预测数据查询
	 * 
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody
	PageJson curdlistJson(HttpServletRequest request,
			HttpServletResponse reponse, MpDemandForecastModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(
					request).getPage().getStartIndex(), getQueryFilter(request)
					.getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			List<MpDemandForecastModel> pageList = (PageList<MpDemandForecastModel>) manager
					.queryDemandForeCastForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成预测数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("genDemandForcast")
	public void genDemandForcast(HttpServletRequest request,
			HttpServletResponse response, MpDemandForecastModel model)
			throws Exception {
		ResultMessage message = null;
		log.debug("生成月度预测数据开始");
		try {
			if (model.getVersion() == null || "".equals(model.getVersion())
					|| model.getForeType() == null
					|| "".equals(model.getForeType())) {
				message = new ResultMessage(ResultMessage.SUCCESS, "数据参数有问题");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = manager.genMonthDemandForcast(model,
					ContextUtil.getCurrentUser());
			if (2 == count) {
				message = new ResultMessage(ResultMessage.FAIL,
						"该版本预测数据已发布,不可生成覆盖");
			} else if (0 == count) {
				message = new ResultMessage(ResultMessage.SUCCESS, "生成月度预测数据成功");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "生成月度预测数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "生成月度预测数据失败");
		}
		writeResultMessage(response.getWriter(), message);
		log.debug("生成月度预测数据结束");
	}

	/**
	 * 获取版本号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getVersion")
	public @ResponseBody
	List<MpDemandForecastModel> getVersion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			IUser user = ContextUtil.getCurrentUser();
			String foreType = RequestUtil.getString(request, "foreType");
			map.put("foreType", foreType);
			map.put("factoryCode", user.getCurFactoryCode());
			List<MpDemandForecastModel> list = manager.getVersion(map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 导出预测数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("downloadMpDemandForeCast")
	public void downloadMpDemandForeCast(HttpServletRequest request,
			HttpServletResponse response, MpDemandForecastModel model) {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			List<MpDemandForecastModel> list = manager
					.queryDemandForeCastByKey(model);
			/**
			 * 如果查询记录超过1000000条则报错
			 */
			if (0 == list.size()) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias(
					"EXCEL_EXPORT_MAX_SIZE", 100000); // 获取系统所允许的最大导出数量
			if (list.size() > sysMaxNum) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			
			String exportName = "";
			if (model.getForeType() != null && "1".equals(model.getForeType())) {
				exportName = "月度预测";
				String[] headers = { "ID", "零件编号", "零件简号", "需求量", "一级件", "一级件用量",
						"到货日期", "物流模式", "发布月份", "对象月份", "开始时间", "结束时间", "车型",
						"版本号", "生产阶段", "卸货口代码", "供应商代码", "出货地代码", "供应商名称", "发布状态" };
				String[] columns = { "id", "partNo", "partShortNo", "orderQty",
						"partfId", "partfOrderQty", "planDelivery",
						"logisticsModeStr", "publishMonth", "objMonth",
						"startDate", "endDate", "modelCode", "version", "phaseStr",
						"unloadPort", "supplierNo", "supFactory", "supplierName","releaseStatusStr" };
				int[] widths = { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
						100, 100, 100, 100, 100, 100, 100, 100, 100, 100 };
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
						exportName + df.format(new Date()), list, headers, widths,
						columns);
			} else {
				exportName = "周度预测";
				String[] headers = { "ID", "零件编号", "零件简号", "需求量", "一级件", "一级件用量",
						"到货日期", "物流模式", "发布周", "对象周", "提前取货时间", "开始时间", "结束时间", "车型",
						"版本号", "生产阶段", "卸货口代码", "供应商代码", "出货地代码", "供应商名称", "发布状态" };
				String[] columns = { "id", "partNo", "partShortNo", "orderQty",
						"partfId", "partfOrderQty", "planDelivery",
						"logisticsModeStr", "publishWeek", "objWeek","advanceTime",
						"startDate", "endDate", "modelCode", "version", "phaseStr",
						"unloadPort", "supplierNo", "supFactory", "supplierName","releaseStatusStr" };
				int[] widths = { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
						100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 };
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
						exportName + df.format(new Date()), list, headers, widths,
						columns);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}

	/**
	 * 批量删除需求预测数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("batchRemoveDemandForecast")
	public void batchRemoveDemandForecast(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			if (aryIds != null && aryIds.length > 0) {
				// 判断该版本是否已发布
				Integer count = manager.queryIsReleaseById(Arrays.asList(aryIds));
				if (count > 0) {
					message = new ResultMessage(ResultMessage.FAIL, "存在已发布数据不可删除");
					writeResultMessage(response.getWriter(), message);
					return;
				}
				manager.batchRemoveDemandForcast(aryIds,
						RequestUtil.getIpAddr(request));
				message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "数据参数有问题");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 修改预测数据
	 * 
	 * @param request
	 * @param response
	 * @param mpResidualModel
	 * @throws Exception
	 */
	@RequestMapping("updateDemandForecast")
	public void updateDemandForecast(HttpServletRequest request,
			HttpServletResponse response,
			MpDemandForecastModel mpDemandForecastModel) throws Exception {
		ResultMessage message = null;
		String id = mpDemandForecastModel.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				message = new ResultMessage(ResultMessage.FAIL, "数据参数有问题");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			String[] aryIds = {id};	
			List<String> arrList = Arrays.asList(aryIds);
			if (aryIds != null && aryIds.length > 0) {
				// 判断该版本是否已发布
				Integer count = manager.queryIsReleaseById(arrList);
				if (count > 0) {
					message = new ResultMessage(ResultMessage.FAIL, "存在已发布数据不可修改");
					writeResultMessage(response.getWriter(), message);
					return;
				}
			}
			mpDemandForecastModel.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			manager.updateDemandForcast(mpDemandForecastModel,
					RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "修改成功");
		} catch (Exception e) {
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "修改失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 获取默认版本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDefaultVersion")
	public @ResponseBody
	List<MpDemandForecastModel> getDefaultVersion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			IUser user = ContextUtil.getCurrentUser();
			String foreType = RequestUtil.getString(request, "foreType");
			map.put("foreType", foreType);
			map.put("factoryCode", user.getCurFactoryCode());
			List<MpDemandForecastModel> list = manager.getDefaultVersion(map);

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 清除数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("clearDemandForecast")
	public void clearDemandForecast(HttpServletRequest request,
			HttpServletResponse response, MpDemandForecastModel model)
			throws Exception {
		ResultMessage message = null;
		log.debug("清除预测数据开始");
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			if (model.getVersion() == null || "".equals(model.getVersion())
					|| model.getForeType() == null
					|| "".equals(model.getForeType())) {
				message = new ResultMessage(ResultMessage.FAIL, "数据参数有问题");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			// 判断该版本是否已经发布
			Integer count = manager.queryIsRelease(model);
			if (count > 0) {
				message = new ResultMessage(ResultMessage.FAIL, "该版本已发布,不可清除");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			manager.clearDemandForecast(model, ContextUtil.getCurrentUser());
			message = new ResultMessage(ResultMessage.SUCCESS, "清除预测数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "清除预测数据失败");
		}
		writeResultMessage(response.getWriter(), message);
		log.debug("清除预测数据结束");
	}

	/**
	 * 导入EXCEL数据
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importMonthModel")
	public void importMonthModel(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session
					.getAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				manager.deleteImportTempDataByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID,
					uuid);

			Map<String, Object> resultMap = manager.importMonthModel(file,
					uuid, RequestUtil.getIpAddr(request), user);
			/**
			 * 这里要传递uuid***
			 */
			resultMap.put("uuid", uuid);
			if ((Boolean) resultMap.get("result")) {
				writeResultMessage(response.getWriter(), "成功", "",
						JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "失败",
						(String) resultMap.get("console"),
						JSONObject.fromObject(resultMap), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn)
					.toString(), ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 导入EXCEL数据  周预测
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importWeekModel")
	public void importWeekModel(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session
					.getAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				manager.deleteImportTempDataByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID,
					uuid);

			Map<String, Object> resultMap = manager.importWeekModel(file,
					uuid, RequestUtil.getIpAddr(request), user);
			/**
			 * 这里要传递uuid***
			 */
			resultMap.put("uuid", uuid);
			if ((Boolean) resultMap.get("result")) {
				writeResultMessage(response.getWriter(), "成功", "",
						JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "失败",
						(String) resultMap.get("console"),
						JSONObject.fromObject(resultMap), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn)
					.toString(), ResultMessage.FAIL);
		}
	}

	/**
	 * 查询导入的临时例外订单用量数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("curdlistImportMonthTempJson")
	public @ResponseBody
	PageJson curdlistImportTempJson(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		paramMap.put("foreType", "1");
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<MpDemandForecastModel> pageList = (PageList<MpDemandForecastModel>) manager
				.queryImportTempData(paramMap, page);
		return new PageJson(pageList);
	}
	
	/**
	 * 查询导入的临时例外订单用量数据  周预测
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("curdlistImportWeekTempJson")
	public @ResponseBody
	PageJson curdlistImportWeekTempJson(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		paramMap.put("foreType", "2");
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<MpDemandForecastModel> pageList = (PageList<MpDemandForecastModel>) manager
				.queryImportTempData(paramMap, page);
		return new PageJson(pageList);
	}

	/**
	 * 导出导入校验结果数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportMonthTempData")
	public void exportMonthTempData(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String) session
					.getAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			List<MpDemandForecastModel> list = manager
					.queryMonthTempDataForExport(paramMap);

			String[] headers = { "ID", "零件编号", "零件简号", "需求量", "一级件", "一级件用量",
					"到货日期", "物流模式", "发布月份", "对象月份", "开始时间", "结束时间", "车型",
					"版本号", "生产阶段", "卸货口代码", "供应商代码", "出货地代码", "供应商名称", "校验结果",
					"导入状态", "校验信息" };
			String[] columns = { "id", "partNo", "partShortNo", "orderQty",
					"partfId", "partfOrderQty", "planDelivery",
					"logisticsModeStr", "publishMonth", "objMonth",
					"startDate", "endDate", "modelCode", "version", "phaseStr",
					"unloadPort", "supplierNo", "supFactory", "supplierName",
					"checkResultStr", "importStatusStr", "checkInfo" };
			int[] widths = { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
					100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 };
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					"月度预测导入校验结果数据" + df.format(new Date()), list, headers,
					widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}

	/**
	 * 确认导入
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("isImportMonth")
	public void isImportMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String) session
					.getAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("foreType", "1");
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			manager.insertMonthImportData(paramMap,
					RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());

			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}

	
	/**
	 * 确认导入
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("isImportWeek")
	public void isImportWeek(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String) session
					.getAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("foreType", "2");
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			manager.insertMonthImportData(paramMap,
					RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());

			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}

	
	/**
	 * 生成预测数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("releaseDemandForcast")
	public void releaseDemandForcast(HttpServletRequest request,
			HttpServletResponse response, MpDemandForecastModel model)
			throws Exception {
		ResultMessage message = null;
		log.debug("发布月度预测数据开始");
		try {
			if (model.getVersion() == null || "".equals(model.getVersion())
					|| model.getForeType() == null
					|| "".equals(model.getForeType())) {
				message = new ResultMessage(ResultMessage.SUCCESS, "数据参数有问题");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = manager.releaseDemandForcast(model,
					ContextUtil.getCurrentUser());
			if (2 == count) {
				message = new ResultMessage(ResultMessage.FAIL,
						"该版本预测数据已发布,请不要重复发布");
			} else if (0 == count) {
				message = new ResultMessage(ResultMessage.SUCCESS, "发布月度预测数据成功");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "发布月度预测数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "发布月度预测数据失败");
		}
		writeResultMessage(response.getWriter(), message);
		log.debug("发布月度预测数据结束");
	}
	
	
    /**生成一级件周度预测***********************************************************************************/
	/**
	 * 生成一级件预测数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("genDemandPartIf")
	public void genDemandPartIf(HttpServletRequest request,
			HttpServletResponse response, MpDemandForecastModel model)
			throws Exception {
		ResultMessage message = null;
		log.debug("生成周度一级件预测数据开始");
		try {
			if (model.getForeType() == null
					|| "".equals(model.getForeType())) {
				message = new ResultMessage(ResultMessage.SUCCESS, "数据参数有问题");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			model.setCreationUser(ContextUtil.getCurrentUser().getAccount());
			Integer count = manager.genDemandPartIf(model);
			if  (0 == count) {
				message = new ResultMessage(ResultMessage.SUCCESS, "生成周度预测数据成功");
			}else if (2 == count) {
				message = new ResultMessage(ResultMessage.FAIL, "选择的日期范围没有数据");
			} else if (3 == count) {
				message = new ResultMessage(ResultMessage.FAIL, "BOM数据未获取完全");
			} else{
				message = new ResultMessage(ResultMessage.FAIL, "生成周度预测数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "生成周度预测数据失败");
		}
		writeResultMessage(response.getWriter(), message);
		log.debug("生成周度预测数据结束");
	}
	
	/**
	 * 
	 * @Description: 导出生成的一级件周度预测数据
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月5日 上午10:52:46
	 */
	@RequestMapping("downloadMpDemandWeekForeCast")
	public void downloadMpDemandWeekForeCast(HttpServletRequest request,
			HttpServletResponse response, MpDemandForecastModel model) {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			List<MpDemandForecastModel> list = manager
					.downloadMpDemandWeekForeCast(model);
			/**
			 * 如果查询记录超过1000000条则报错
			 */
			if (0 == list.size()) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias(
					"EXCEL_EXPORT_MAX_SIZE", 100000); // 获取系统所允许的最大导出数量
			if (list.size() > sysMaxNum) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			
			String exportName = "一级件周度预测";
			String[] headers = { "ID", "零件编号", "零件简号", "需求量",
					"到货日期", "物流模式", "发布周", "对象周", "提前取货时间", "开始时间", "结束时间", "车型",
					"版本号", "生产阶段", "卸货口代码", "供应商代码", "出货地代码", "供应商名称" };
			String[] columns = { "id", "partNo", "partShortNo", "orderQty",
					 "planDelivery","logisticsModeStr", "publishWeek", "objWeek","advanceTime",
					"startDate", "endDate", "modelCode", "version", "phaseStr",
					"unloadPort", "supplierNo", "supFactory", "supplierName" };
			int[] widths = { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
					100, 100, 100, 100, 100, 100, 100, 100};
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					exportName + df.format(new Date()), list, headers, widths,
					columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}

	
	/**
	 * 导入EXCEL数据  一级件周预测
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importWeekForecastModel")
	public void importWeekForecastModel(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session
					.getAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				manager.deleteImportTempDataByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID,
					uuid);

			Map<String, Object> resultMap = manager.importWeekForecastModel(file,
					uuid, RequestUtil.getIpAddr(request), user);
			/**
			 * 这里要传递uuid***
			 */
			resultMap.put("uuid", uuid);
			if ((Boolean) resultMap.get("result")) {
				writeResultMessage(response.getWriter(), "成功", "",
						JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "失败",
						(String) resultMap.get("console"),
						JSONObject.fromObject(resultMap), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn)
					.toString(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 确认导入
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("isImportWeekForecast")
	public void isImportWeekForecast(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String) session
					.getAttribute(SessionKey.MP_DEMAND_FORECAST_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("foreType", "2");
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			manager.insertWeekForecastImportData(paramMap,
					RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());

			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Description: 分页查询一级件周度预测数据
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月5日 上午10:50:17
	 */
	@RequestMapping("queryDemandWeekForePage")
	public @ResponseBody
	PageJson queryDemandWeekForePage(HttpServletRequest request,
			HttpServletResponse reponse, MpDemandForecastModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(
					request).getPage().getStartIndex(), getQueryFilter(request)
					.getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			List<MpDemandForecastModel> pageList = (PageList<MpDemandForecastModel>) manager
					.queryDemandWeekForePage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 生效使用生成的周预测数据
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 上午11:46:32
	 */
	@RequestMapping("effectDemand")
	public void effectDemand(HttpServletRequest request,
			HttpServletResponse response, MpDemandForecastModel model)
			throws Exception {
		ResultMessage message = null;
		log.debug("生效使用预测数据开始");
		try {
			if (model.getForeType() == null
					|| "".equals(model.getForeType())) {
				message = new ResultMessage(ResultMessage.SUCCESS, "数据参数有问题");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			model.setCreationUser(ContextUtil.getCurrentUser().getAccount());
			//生效使用之前判断当前版本是否已经存在业务表中
			Boolean b = manager.isEffectVersion(model);
			if (b) {
				message = new ResultMessage(ResultMessage.FAIL, "该版本已经在系统中存在");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			Integer count = manager.effectDemand(model);
			if  (0 == count) {
				message = new ResultMessage(ResultMessage.SUCCESS, "发布周度预测数据成功");
			} else if (2 == count) {
				message = new ResultMessage(ResultMessage.FAIL, "要发布的数据没有版本号,请确认是否已经导入第四周预测");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "发布周度预测数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "发布周度预测数据失败");
		}
		writeResultMessage(response.getWriter(), message);
		log.debug("发布周度预测数据结束");
	}
	
	/**
	 * 
	 * @Description: 周预测计算界面获取版本号
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return List<MpDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 下午2:46:41
	 */
	@RequestMapping("getForeVersion")
	public @ResponseBody
	List<MpDemandForecastModel> getForeVersion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			IUser user = ContextUtil.getCurrentUser();
			String foreType = RequestUtil.getString(request, "foreType");
			map.put("foreType", foreType);
			map.put("factoryCode", user.getCurFactoryCode());
			List<MpDemandForecastModel> list = manager.getForeVersion(map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 用户自定义
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws IOException 
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月8日 下午4:35:25
	 */
	@RequestMapping("submitVersion")
	public void submitVersion(HttpServletRequest request, HttpServletResponse response, SwDemandForecastModel model) throws IOException {
		ResultMessage message = null;
		try {
			boolean b = manager.validateVersionExists(model); //判断版本号是否已存在
			if (b) {
				message = new ResultMessage(ResultMessage.FAIL,"该版本号已存在,请重新输入");
			} else {
				manager.submitVersion(model);
				message = new ResultMessage(ResultMessage.SUCCESS, "版本号修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "系统错误请联系管理员");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
}
