package com.hanthink.inv.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.manager.InvDevelopManager;
import com.hanthink.inv.model.InvDevelopModel;
import com.hanthink.inv.model.InvLockModel;
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
 * 功能描述:库存推移查询业务控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月16日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/inv/develop")
@Controller
public class InvDevelopController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(InvDevelopController.class);
	
	@Resource
	private InvDevelopManager developManager;
	/**
	 * 库存推移分页查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryDevelopForPage(HttpServletRequest request,HttpServletResponse response,
											InvDevelopModel model)throws Exception{
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvDevelopModel> pageList = developManager.queryDevelopForPage(model,page);
				
		return new PageJson(pageList);
	}
	/**
	 * 启动/停止推算服务控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/elapseUpdate")
	public void updateElapseStates(HttpServletRequest request,HttpServletResponse response) throws Exception {
		/** 1-启动  0-停止 */
		Integer state = Integer.parseInt(RequestUtil.getString(request, "state"));
		try {
			developManager.elapseUpdate(state);
			if (state == 1) {
				writeResultMessage(response.getWriter(), "启动成功", "1", ResultMessage.SUCCESS);
			}
			if(state == 0) {
				writeResultMessage(response.getWriter(), "停止成功", "0", ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "异常了", e.getMessage(), ResultMessage.SUCCESS);
		}
	}
	/**
	 * 库存推移查询数据导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/exportForExcel")
	public void queryForExcelExport(HttpServletRequest request,HttpServletResponse response,
							InvDevelopModel model) throws Exception{
		
		List<InvDevelopModel> list = developManager.queryForExcelExport(model);
		
		try {
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			
			String[] headers = {"车间","零件号","简号","零件名称","时间点","实时库存","预计消耗量","计划到货量","库存汇总"};
			String[] columns = {"workCenter","partNo","partShortNo","partName","calPoint","stock","proQTY","planQTY","invSummary"};
			int[] widths = {100,100,100,100, 100,100,100,100, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "库存推移查询数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 零件消耗量查询业务控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/queryConsumption")
	public @ResponseBody PageJson queryConsumptionForPage(HttpServletRequest request,HttpServletResponse response,
												InvDevelopModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvDevelopModel> list = developManager.queryConsumptionForPage(model,page);
		
		return new PageJson(list);
	}
	/**
	 * 零件消耗量Excel导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/exportConsumptionForExport")
	public void queryConsumptionForExcelExport(HttpServletRequest request,HttpServletResponse response,
										InvDevelopModel model) throws Exception{
		
		List<InvDevelopModel> list = developManager.queryConsumptionForExcelExport(model);
		
		try {
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			
			String[] headers = {"车间","零件号","简号","零件名称","时间点","车型","预计消耗量"};
			String[] columns = {"workCenter","partNo","partShortNo","partName","calPoint","carType","proQTY"};
			int[] widths = {100,100,100,100, 100,100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "零件消耗量查询数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 库存推移管理分页查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/queryForDevelopManager")
	public @ResponseBody PageJson queryDevelopManagerForPage(HttpServletRequest request,HttpServletResponse response,
								InvDevelopModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		PageList<InvDevelopModel> pageList = developManager.queryDevelopManagerForPage(model,page);
		return new PageJson(pageList);
	}
	
	@RequestMapping("/exportExcelForDevelopManager")
	public void exportExcelForDevelopManager(HttpServletRequest request,HttpServletResponse response,
											InvDevelopModel model)throws Exception {
		List<InvDevelopModel> list = developManager.exportExcelForDevelopManager(model);
		try {
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			//获取系统所允许的最大导出数量
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); 
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			
			String[] headers = {"车间","零件号","简号","零件名称","库存数量","调整库存量","收容数","箱数"};
			String[] columns = {"workCenter","partNo","partShortNo","partName","stock","adjStock","standardPac","codeNum"};
			int[] widths = {100,100,100,100, 100,100,100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "库存推移管理查询数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 修改库存数据信息控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/updateStock")
	public void updateStock(HttpServletRequest request,HttpServletResponse response,
								@RequestBody InvDevelopModel model)throws Exception{
		String resultMsg = null;
		try {
			developManager.updateStock(model,RequestUtil.getIpAddr(request));
			resultMsg = "数据修改成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
		}
	}
	/**
	 * 获取库存数据控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/getStock")
	public void getStock(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String resultMsg = null;
		try {
			developManager.getStock();
			resultMsg = "获取库存数据成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "获取库存数据失败";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
		}
	}
	/**
	 * Excel数据导入控制器
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/importExcel")
	public void importExcelStock(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file,InvDevelopModel model)throws Exception {
		try {
			
			String uuid = model.getUuid();
			if(StringUtil.isNotEmpty(uuid)) {
				developManager.deleteImportByUUID(uuid);
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			Map<String, Object> resultMap = developManager.importStockToTempTable(file, uuid,
					RequestUtil.getIpAddr(request));
			resultMap.put("uuid", uuid);
			if ((boolean) resultMap.get("result")) {
				writeResultMessage(response.getWriter(), "文件导入成功", "文件导入成功", JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", e.getMessage());
			writeResultMessage(response.getWriter(), (String) rtn.get("console"), ResultMessage.FAIL);
		}
	}
	/**
	 * 导入数据查询控制器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/queryImportForPage")
	public @ResponseBody PageJson queryImportForPage(HttpServletRequest request,HttpServletResponse response,
											InvDevelopModel model)throws Exception {
		String uuid = model.getUuid();
		uuid = RequestUtil.getString(request, "uuid");
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvDevelopModel> pageList = developManager.queryImportForPage(page,uuid);
		
		return new PageJson(pageList);
	}
	
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String uuid = RequestUtil.getString(request, "uuid");
		ResultMessage resultMessage = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			developManager.isImport(paramMap);
			resultMessage = new ResultMessage(ResultMessage.SUCCESS,"数据写入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultMessage = new ResultMessage(ResultMessage.FAIL,e.getMessage());
		}
		writeResultMessage(response.getWriter(), resultMessage);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/exportExcelForImport")
	public void exportExcelForImport(HttpServletRequest request,HttpServletResponse response,
							InvDevelopModel model) throws Exception{
		
		String uuid = RequestUtil.getString(request, "uuid");
		List<InvDevelopModel> list = developManager.exportExcelForImport(uuid);
		
		try {
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			
			String[] headers = {"车间","仓库代码","零件号","实时库存","库存调整量","导入状态","校验结果","校验信息"};
			String[] columns = {"workCenter","wareCode","partNo","stock","adjStock","excelImportStatus","excelCheckResult","checkInfo"};
			int[] widths = {100,100,100,100, 100,100,100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "库存推移导入数据信息"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 查询推算服务的运行状态
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/queryRunStatus")
	public void queryRunStatus(HttpServletRequest request,HttpServletResponse response)throws Exception {
		InvLockModel model = developManager.queryRunStatus();
		String status = model.getIsLock();
		if ("1" == status) {
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS,status));
		}else {
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS,status));
		}
	}
	/**
	 * 查询零件日消耗量
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月11日
	 */
	@RequestMapping("/queryDailyConsumption")
	public @ResponseBody PageJson queryDailyConsumption(HttpServletRequest request,HttpServletResponse response,
									InvDevelopModel model)throws Exception{
		Page page = getQueryFilter(request).getPage();
		PageList<InvDevelopModel> pageList = developManager.queryDailyConsumption(model,page);
		return new PageJson(pageList);
	}
	/**
	 * 查询零件消耗详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月11日
	 */
	@RequestMapping("/queryDailyConsumptionDetail")
	public @ResponseBody PageJson queryDailyConsumptionDetail(HttpServletRequest request,HttpServletResponse response,
																InvDevelopModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		PageList<InvDevelopModel> pageList = developManager.queryDailyConsumptionDetail(model,page);
		return new PageJson(pageList);
	}
}
