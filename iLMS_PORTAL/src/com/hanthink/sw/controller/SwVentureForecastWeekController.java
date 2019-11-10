package com.hanthink.sw.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.cmd.NeedsActiveExecutionCmd;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sw.manager.SwVentureForecastWeekManager;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.sw.model.SwLongOrderModel;
import com.hanthink.sw.model.SwVentureForecastModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;
import springfox.documentation.spring.web.json.Json;
import sun.tools.asm.ArrayData;


@Controller
@RequestMapping("/sw/swVentureWeekForecast")
public class SwVentureForecastWeekController extends GenericController {

	private static Logger log = LoggerFactory.getLogger(SwVentureForecastWeekController.class);

	@Resource
	private SwVentureForecastWeekManager manager;

	/**
	 * 
	 * @Description: 合资车周预测导入临时表
	 * @param @param file
	 * @param @param request
	 * @param @param response
	 * @return void
	 * @throws Exception
	 * @throws           @author luoxq
	 * @date 2019年8月2日 下午3:11:48
	 */
	@RequestMapping("importModel")
	public void importModel(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		String uuid = null;
		try {
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				manager.deleteImportTempDataByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replace("-", ""); // 生成随机UUID
			}
			Map<String, Object> rtn = manager.importModel(file, uuid, RequestUtil.getIpAddr(request), user);
			rtn.put("uuid", uuid);
			if ((Boolean) rtn.get("result")) {
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "失败", (String) rtn.get("console"), JSONObject.fromObject(rtn),
						ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception(e.toString());
		}
	}

	/**
	 * 
	 * @Description: 分页查询导入临时表数据
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception
	 * @return PageJson
	 * @throws @author luoxq
	 * @date 2019年8月14日 上午10:29:30
	 */
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));

		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<SwVentureForecastModel> pageList = (PageList<SwVentureForecastModel>) manager
				.queryImportTempData(paramMap, page);
		return new PageJson(pageList);
	}

	/**
	 * 导出导入校验结果数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			String uuid = RequestUtil.getString(request, "uuid");
			paramMap.put("uuid", uuid);
			List<SwVentureForecastModel> list = manager.queryTempDataForExport(paramMap);

			String[] headers = { "工厂", "版本号", "零件号", "需求量", "下线日期", "提前取货时间(天)", "生产阶段", "供应商代码", "车型",
					 "校验结果", "导入状态", "校验信息" };
			String[] columns = { "jvPlace", "jvVersion", "partNo", "orderQty", "planDelivery", "advanceTime",
					"phaseStr", "supplierNo", "modelCode",  "checkResultStr", "importStatusStr", "checkInfo" };
			int[] widths = { 80, 100, 150, 80, 180, 150, 100, 100,  100, 100, 100, 100 };
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "合资车预测导入校验结果数据" + df.format(new Date()),
					list, headers, widths, columns);
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
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String uuid = RequestUtil.getString(request, "uuid");
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
			/**
			 * 临时表数据写入正式表
			 */
			manager.insertImportData(paramMap, RequestUtil.getIpAddr(request));
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
	 * @Description: 分页查询
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception
	 * @return PageJson
	 * @throws @author luoxq
	 * @date 2019年8月14日 下午3:30:54
	 */
	@RequestMapping("queryVentureForePage")
	public @ResponseBody PageJson queryVentureForePage(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") SwVentureForecastModel model) throws Exception{
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		model.setUserType(user.getUserType());
		model.setUserId(user.getUserId());
		PageList<SwVentureForecastModel> pageList = (PageList<SwVentureForecastModel>) manager
				.queryVentureForePage(model, page);
		return new PageJson(pageList);
	}

	/**
	 * 
	 * @Description: 根据版本删除数据
	 * @param @param request
	 * @param @param response
	 * @return void
	 * @throws IOException 
	 * @throws @author luoxq
	 * @date 2019年8月14日 下午4:01:11
	 */
	@RequestMapping("deleteVentureVersion")
	public void deleteVentureVersion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SwVentureForecastModel model = new SwVentureForecastModel();
		ResultMessage message = null;
		try {
			IUser user = ContextUtil.getCurrentUser();
			String jvVersion = RequestUtil.getString(request, "jvVersion");
			model.setJvVersion(jvVersion);
			model.setFactoryCode(user.getCurFactoryCode());
			model.setReleaseStatus("1"); //判断要删除的版本是否已发布
			int msgCount = manager.deleteVentureVersion(model); //根据版本删除数据
			if (msgCount == 0) {
				message = new ResultMessage(ResultMessage.SUCCESS,"删除成功");
			}else if (msgCount == 1) {
				message = new ResultMessage(ResultMessage.FAIL, "已发布不能进行删除");
			} else {
				message = new ResultMessage(ResultMessage.FAIL,"删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.ERROR, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportForExcelModel")
	public void exportForExcelModel(HttpServletRequest request, HttpServletResponse response,
			 SwVentureForecastModel model) throws Exception {

		try {
			
			IUser user = ContextUtil.getCurrentUser();
			model.setFactoryCode(user.getCurFactoryCode());
			model.setUserType(user.getUserType());
			model.setUserId(user.getUserId());
			List<SwVentureForecastModel> list = (List<SwVentureForecastModel>) manager
					.exportForExcelModel(model);
			int curNum = list.size();
			if (0 == curNum) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
			if (curNum > sysMaxNum) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			if (null != list) {	
				
				String exportFileName = "合资车预测数据导出(周度)" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String[] headers = {"工厂", "版本号", "发布状态","零件号",
						"简号", "零件名称", "下线日期", "生产阶段", "创建时间"};
				String[] columns = { "jvPlace","jvVersion","releaseStatusStr", "partNo",
						"partShortNo", "partNameCn", "planDelivery", "phaseStr",
						"creationTime"};
				int[] widths = { 80,100, 80, 130, 
						80, 150, 150, 100, 130};
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers,
							widths, columns);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	} 
	
	/**
	 * 
	 * @Description: 检索
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 上午11:40:20
	 */
	@RequestMapping("checkDataRsult")
	public void checkDataRsult(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String[] jvVersionList) throws Exception {
		ResultMessage message = null;
		try {
			IUser user = ContextUtil.getCurrentUser();
			QueryFilter queryFilter = getQueryFilter(request);
			Page page = queryFilter.getPage();
			
			SwVentureForecastModel model = new SwVentureForecastModel();
			model.setFactoryCode(user.getCurFactoryCode());
			model.setCreationUser(user.getAccount());
			String erpVersion = RequestUtil.getString(request, "erpVersion");
			if (StringUtil.isEmpty(erpVersion) || null == jvVersionList || jvVersionList.length == 0) {
				message = new ResultMessage(ResultMessage.FAIL, "请选择要合并的版本号");
			}else {
				model.setErpVersion(erpVersion);
				String[] jvVStrings = removeNull(jvVersionList);
				model.setJvVersionList(jvVStrings);
				manager.checkDataRsult(model,page); //检索数据写入临时表
				message = new ResultMessage(ResultMessage.SUCCESS, "检索数据写入合并临时表成功");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, e.toString());
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Description: 检索数据写入临时表后，分页查询统计后数量
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午6:39:52
	 */
	@RequestMapping("queryTotalQty")
	public @ResponseBody PageJson queryTotalQty(HttpServletRequest request, HttpServletResponse response,
			SwVentureForecastModel model) throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			QueryFilter queryFilter = getQueryFilter(request);
			Page page = queryFilter.getPage();
			model.setFactoryCode(user.getCurFactoryCode());
			model.setCreationUser(user.getAccount());
			PageList<SwVentureForecastModel> pageList = manager.queryTotalQty(model,page);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
		
	}
	
	/**
	 * 
	 * @Description: 导出合资车合并后的周预测数据
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午10:31:22
	 */
	@RequestMapping("exportDemandForecastWeekModel")
	public void exportDemandForecastWeekModel(HttpServletRequest request,HttpServletResponse response, SwVentureForecastModel model) throws Exception{
		ResultMessage message=null;
		IUser user = ContextUtil.getCurrentUser();
		try {
			model.setUserType(user.getUserType());
			model.setUserId(user.getUserId());
			model.setCreationUser(user.getAccount());
			model.setFactoryCode(user.getCurFactoryCode());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("erpVersion", model.getErpVersion());
 			SwVentureForecastModel swVentureForecastModel = manager.getExportModeMsg(map);//获取到erp下发的每个版本零件的最大日期和最小日期、对象周
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdfym = new SimpleDateFormat("yyyy-MM-dd");

			Date startMonthStr1 = sdfym.parse(swVentureForecastModel.getStartDate());//导出中的每天最小日期
			cal.setTime(startMonthStr1);
			
			String startMonthStr = sdfym.format(cal.getTime()).toString();

			Date endMonthStr1 = sdfym.parse(swVentureForecastModel.getEndDate());
			cal.setTime(endMonthStr1);
			String endMonthStr = sdfym.format(endMonthStr1).toString();//导出中的每天最大日期
//			String releaseStatus = model.getReleaseStatus();
			String exportFileName = "合资车周预测数据信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			VentureForecastWeekExportUtil exportUtil = new VentureForecastWeekExportUtil(manager, startMonthStr, endMonthStr, model);
			exportUtil.exportDemandForecastExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 
	 * @Description: 发布
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws IOException   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午10:28:20
	 */
	@RequestMapping("releaseVersion")
	public void releaseVersion(HttpServletRequest request, HttpServletResponse response, 
			SwVentureForecastModel model) throws IOException {
		ResultMessage message = null;
		try {
			IUser user = ContextUtil.getCurrentUser();
			
			model.setFactoryCode(user.getCurFactoryCode());
			model.setCreationUser(user.getAccount());
			int msgCount = manager.insertReleaseVersion(model);
			if (msgCount == 0) {
				message = new ResultMessage(ResultMessage.SUCCESS, "发布成功");
			}else if (msgCount == 1) {
				message = new ResultMessage(ResultMessage.FAIL, "版本已存在,请确认");
			}else {
				message = new ResultMessage(ResultMessage.FAIL, "发布失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "合并发布失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Description: 获取订购方版本号下拉框
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午10:42:41
	 */
	@RequestMapping("getJvVersion")
	public @ResponseBody List<SwVentureForecastModel> getJvVersion(HttpServletRequest request, HttpServletResponse response) throws Exception 
			 {
		IUser user = ContextUtil.getCurrentUser();
		try {
			Map<String, String> map = new HashMap<String, String>();
			String releaseStatus = RequestUtil.getString(request, "releaseStatus");
			if (StringUtil.isNotEmpty(releaseStatus)) {
				map.put("releaseStatus", releaseStatus);
			}
			map.put("factoryCode", user.getCurFactoryCode());
			map.put("userType", user.getUserType());
			map.put("userId",user.getUserId());
			List<SwVentureForecastModel> list = manager.getJvVersion(map);
			if (null == list || list.size() == 0) {
				list = new ArrayList<SwVentureForecastModel>();
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 获取ERP版本号下拉框
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月16日 下午3:59:07
	 */
	@RequestMapping("getErpVersion")
	public @ResponseBody List<SwVentureForecastModel> getErpVersion(HttpServletRequest request, HttpServletResponse response) throws Exception 
			 {
		IUser user = ContextUtil.getCurrentUser();
		try {
			Map<String, String> map = new HashMap<String, String>();
			String releaseStatus = RequestUtil.getString(request, "releaseStatus");
			if (StringUtil.isNotEmpty(releaseStatus)) {
				map.put("releaseStatus", releaseStatus);
			}
			map.put("factoryCode", user.getCurFactoryCode());
			List<SwVentureForecastModel> list = manager.getErpVersion(map);
			if (null == list || list.size() == 0) {
				list = new ArrayList<SwVentureForecastModel>();
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	
	public String[] removeNull(String[] arr) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			if (null != arr[i] && (!"".equals(arr[i]))) {
				list.add(arr[i]);
			}
		}
		String[] newArr = new String[list.size()];
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = list.get(i);
		}
		return newArr;
	}
}
