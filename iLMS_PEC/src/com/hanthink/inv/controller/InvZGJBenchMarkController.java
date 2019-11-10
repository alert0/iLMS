package com.hanthink.inv.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.hanthink.business.util.SessionKey;
import com.hanthink.inv.manager.InvZGJBenchMarkManager;
import com.hanthink.inv.model.InvZGJBenchMarkModel;
import com.hanthink.pup.util.PupUtil;
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

import net.sf.json.JSONObject;

/**
 * @ClassName: InvZGJBenchMarkController
 * @Description: 支给件W-1周库存
 * @author wangyu
 * @date 2019年5月10日
 */
@RequestMapping("/inv/zgjBenchMark")
@Controller
public class InvZGJBenchMarkController extends GenericController {

	@Resource
	private InvZGJBenchMarkManager invZGJBenchMarkManager;

	private static Logger log = LoggerFactory.getLogger(InvStockTakController.class);
	/**
	 * 查询W-1周支给件库存
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryBenchMarkForPage")
	public @ResponseBody
	PageJson queryBenchMarkForPage(HttpServletRequest request,
			HttpServletResponse response, InvZGJBenchMarkModel model)
			throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		Page page = getQueryFilter(request).getPage();

		PageList<InvZGJBenchMarkModel> pageList = invZGJBenchMarkManager
				.queryBenchMarkForPage(model, page);

		return new PageJson(pageList);
	}

	/**
	 * 导出EXCEL
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("exportForExcel")
	public void queryExportDataForExcel(HttpServletRequest request,
			HttpServletResponse response, InvZGJBenchMarkModel model)
			throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<InvZGJBenchMarkModel> list = invZGJBenchMarkManager
				.queryExportDataForExcel(model);

		try {
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
			String[] headers = { "仓库代码", "仓库名称", "零件号", "简号", "零件名称", "在库数量",
					"标识" };
			String[] columns = { "wareCode", "wareName", "partNo",
					"partShortNo", "partName", "stock", "flagStr" };
			int[] widths = { 80, 80, 80, 80, 80, 80, 80 };
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					"支给件W-1周库存" + PupUtil.getCurrentTime("yyyyMMddHHmmss"),
					list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			ExcelUtil.exportException(e, request, response);
		}
	}

	/**
	 * 修改库存基准值
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("updateObj")
	public void updateObj(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody InvZGJBenchMarkModel model) throws Exception {
		ResultMessage message = null;
		String id = model.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				message = new ResultMessage(ResultMessage.FAIL, "数据参数有问题");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			model.setLastModifyUser(ContextUtil.getCurrentUser().getAccount());
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			invZGJBenchMarkManager.updateObj(model,
					RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "库存基准数据更新成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "库存基准数据更新失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 新增库存基准值
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("addObj")
	public void addObj(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody InvZGJBenchMarkModel model) throws Exception {
		ResultMessage message = null;
		if (model.getWareCode() == null || "".equals(model.getWareCode())
				|| model.getPartNo() == null || "".equals(model.getPartNo())) {
			message = new ResultMessage(ResultMessage.FAIL, "参数不正确");
			writeResultMessage(response.getWriter(), message);
			return;
		}
		try {
			model.setCreationUser(ContextUtil.getCurrentUser().getAccount());
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			Integer i = invZGJBenchMarkManager.addObj(model);
			if (i == 0) {
				message = new ResultMessage(ResultMessage.FAIL, "该零件和仓库对应关系不存在");
			} else if (i == 2) {
				message = new ResultMessage(ResultMessage.FAIL, "数据已存在");
			} else {
				message = new ResultMessage(ResultMessage.SUCCESS, "库存基准数据新增成功");
			}
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "库存基准数据新增失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 批量删除W-1周库存基准数据
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("delBatchObj")
	public void delBatchObj(@RequestBody List<InvZGJBenchMarkModel> list,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		try {
			if (list != null && list.size() > 0) {
				invZGJBenchMarkManager.delBatchObj(list,
						RequestUtil.getIpAddr(request),
						ContextUtil.getCurrentUser());
			}
			message = new ResultMessage(ResultMessage.SUCCESS, "批量删除W-1周库存基准成功");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "批量删除W-1周库存基准失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 生成新的W-1周库存基准数据
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("generateBenchMark")
	public void generateBenchMark(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			InvZGJBenchMarkModel model = new InvZGJBenchMarkModel();
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			model.setCalUser(ContextUtil.getCurrentUser().getAccount());
			Integer i = invZGJBenchMarkManager.generateBenchMark(model);
			if (i == 0) {
				message = new ResultMessage(ResultMessage.SUCCESS, "生成W-1周库存基准成功");
			}else {
				message = new ResultMessage(ResultMessage.FAIL,  "生成W-1周库存基准失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "生成W-1周库存基准失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**导入数据开始*******************************************************************************************/
	/**
	 * 导入EXCEL数据
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importModel")
	public void importMonthModel(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session
					.getAttribute(SessionKey.MM_INV_ZGJBENCHMARK_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				invZGJBenchMarkManager.deleteImportTempDataByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MM_INV_ZGJBENCHMARK_UUID,
					uuid);

			Map<String, Object> resultMap = invZGJBenchMarkManager.imporModel(file,
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
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody
	PageJson curdlistImportTempJson(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<InvZGJBenchMarkModel> pageList = (PageList<InvZGJBenchMarkModel>) invZGJBenchMarkManager
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
	public void exportTempData(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String) session
					.getAttribute(SessionKey.MM_INV_ZGJBENCHMARK_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			List<InvZGJBenchMarkModel> list = invZGJBenchMarkManager
					.queryTempDataForExport(paramMap);

			String[] headers = { "仓库代码", "零件编号", "在库数量", 
					"校验结果","导入状态", "校验信息" };
			String[] columns = { "wareCode", "partNo", "stock",
					"checkResultStr", "importStatusStr", "checkInfo" };
			int[] widths = { 100, 200, 100, 100, 100, 200};
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					"支给件W-1周库存导入校验结果数据" + df.format(new Date()), list, headers,
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
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String) session
					.getAttribute(SessionKey.MM_INV_ZGJBENCHMARK_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			invZGJBenchMarkManager.insertImportData(paramMap,
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
	 * @Description: 确认处理查询出来的未处理数据
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws IOException 
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月21日 下午4:54:27
	 */
	@RequestMapping("dealStock")
	public void dealStock(HttpServletRequest request, HttpServletResponse response, 
			InvZGJBenchMarkModel model) throws IOException {
		ResultMessage message = null;
		try {
			IUser user = ContextUtil.getCurrentUser();
			model.setDealUser(user.getAccount());
			model.setIpAddr(RequestUtil.getIpAddr(request));
			model.setFactoryCode(user.getCurFactoryCode());
			invZGJBenchMarkManager.dealStock(model); //确认处理，记录处理人
			message = new ResultMessage(ResultMessage.SUCCESS, "处理数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "处理数据失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	/**支给件推算周维护界面开始****************************************************************/
	/**
	 * 
	 * @Description: 分页查询支给件推算周
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午3:22:27
	 */
	@RequestMapping("queryWeekCalForPage")
	public @ResponseBody
	PageJson queryWeekCalForPage(HttpServletRequest request,
			HttpServletResponse response, InvZGJBenchMarkModel model)
			throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		Page page = getQueryFilter(request).getPage();

		PageList<InvZGJBenchMarkModel> pageList = invZGJBenchMarkManager
				.queryWeekCalForPage(model, page);

		return new PageJson(pageList);
	}

	/**
	 * 导出EXCEL
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("queryExportWeekCalForExcel")
	public void queryExportWeekCalForExcel(HttpServletRequest request,
			HttpServletResponse response, InvZGJBenchMarkModel model)
			throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<InvZGJBenchMarkModel> list = invZGJBenchMarkManager
				.queryExportWeekCalForExcel(model);

		try {
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
			String[] headers = { "仓库代码", "仓库名称", "零件号", "简号", "零件名称", "推算周"};
			String[] columns = { "wareCode", "wareName", "partNo",
					"partShortNo", "partName", "calWeek" };
			int[] widths = { 80, 80, 80, 80, 80, 80, 80 };
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					"支给件推算周维护" + PupUtil.getCurrentTime("yyyyMMddHHmmss"),
					list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			ExcelUtil.exportException(e, request, response);
		}
	}

	/**
	 * 修改推算周
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("updateWeekCalObj")
	public void updateWeekCalObj(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody InvZGJBenchMarkModel model) throws Exception {
		ResultMessage message = null;
		String id = model.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				message = new ResultMessage(ResultMessage.FAIL, "数据参数有问题");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			model.setLastModifyUser(ContextUtil.getCurrentUser().getAccount());
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			invZGJBenchMarkManager.updateWeekCalObj(model,
					RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "推算周更新成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "推算周更新失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Description: 新增界面查询零件号弹窗
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月24日 下午5:54:27
	 */
	@RequestMapping("handleListPartNo")
	public @ResponseBody PageJson handleListPartNo(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") InvZGJBenchMarkModel model) throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(user.getCurFactoryCode());
			PageList<InvZGJBenchMarkModel> list = (PageList<InvZGJBenchMarkModel>) invZGJBenchMarkManager.handleListPartNo(model,p);
			return new PageJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}

	}

	
	/**
	 * 
	 * @Description: 获取新增弹窗界面下拉框
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Map<String,String>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月24日 下午5:21:14
	 */
	@RequestMapping("selectUnloadWare")
	public @ResponseBody List<InvZGJBenchMarkModel> selectUnloadWare(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			Map<String, String> map = new HashMap<String, String>();
			map.put("factoryCode", user.getCurFactoryCode());
			List<InvZGJBenchMarkModel> list =  (List<InvZGJBenchMarkModel>) invZGJBenchMarkManager.selectUnloadWare(map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 新增零件推算周
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("addWeekCalObj")
	public void addWeekCalObj(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody InvZGJBenchMarkModel model) throws Exception {
		ResultMessage message = null;
		if (model.getWareCode() == null || "".equals(model.getWareCode())
				|| model.getPartNo() == null || "".equals(model.getPartNo())) {
			message = new ResultMessage(ResultMessage.FAIL, "参数不正确");
			writeResultMessage(response.getWriter(), message);
			return;
		}
		try {
			model.setCreationUser(ContextUtil.getCurrentUser().getAccount());
			model.setFactoryCode(ContextUtil.getCurrentUser()
					.getCurFactoryCode());
			Integer i = invZGJBenchMarkManager.addWeekCalObj(model);
			if (i == 0) {
				message = new ResultMessage(ResultMessage.FAIL, "该零件和仓库对应关系不存在");
			} else if (i == 2) {
				message = new ResultMessage(ResultMessage.FAIL, "数据已存在");
			} else {
				message = new ResultMessage(ResultMessage.SUCCESS, "推算周数据新增成功");
			}
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "推算周数据新增失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 批量删除支给件推算周
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("delBatchWeekCalObj")
	public void delBatchWeekCalObj(@RequestBody List<InvZGJBenchMarkModel> list,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		try {
			if (list != null && list.size() > 0) {
				invZGJBenchMarkManager.delBatchWeekCalObj(list,
						RequestUtil.getIpAddr(request),
						ContextUtil.getCurrentUser());
			}
			message = new ResultMessage(ResultMessage.SUCCESS, "批量删除支给件推算周成功");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "批量删除支给件推算周失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/***支给件推算周导入************************************************************/
	
	/**
	 * 导入EXCEL数据
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importModelWeekCal")
	public void importMonthModelWeekCal(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session
					.getAttribute(SessionKey.MM_INV_ZGJWEEKCAL_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				invZGJBenchMarkManager.deleteImportTempDataWeekCalByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MM_INV_ZGJWEEKCAL_UUID,
					uuid);

			Map<String, Object> resultMap = invZGJBenchMarkManager.imporModelWeekCal(file,
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
	@RequestMapping("curdlistImportTempJsonWeekCal")
	public @ResponseBody
	PageJson curdlistImportTempJsonWeekCal(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<InvZGJBenchMarkModel> pageList = (PageList<InvZGJBenchMarkModel>) invZGJBenchMarkManager
				.queryImportTempDataWeekCal(paramMap, page);
		return new PageJson(pageList);
	}
	
	
	/**
	 * 导出导入校验结果数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportTempDataWeekCal")
	public void exportTempDataWeekCal(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String) session
					.getAttribute(SessionKey.MM_INV_ZGJBENCHMARK_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			List<InvZGJBenchMarkModel> list = invZGJBenchMarkManager
					.queryTempDataForExportWeekCal(paramMap);

			String[] headers = { "仓库代码", "零件编号", "推算天", 
					"校验结果","导入状态", "校验信息" };
			String[] columns = { "wareCode", "partNo", "calWeek",
					"checkResultStr", "importStatusStr", "checkInfo" };
			int[] widths = { 100, 200, 100, 100, 100, 200};
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					"支给件推算周导入校验结果数据" + df.format(new Date()), list, headers,
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
	@RequestMapping("isImportWeekCal")
	public void isImportWeekCal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String) session
					.getAttribute(SessionKey.MM_INV_ZGJBENCHMARK_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			invZGJBenchMarkManager.insertImportDataWeekCal(paramMap,
					RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());

			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	/**支给件缺件查询*************************************************************************/
	/**
	 * 
	 * @Description: 生成支给件差异
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月30日 下午5:32:27
	 */
	@RequestMapping("getZGJDifference")
	public void getZGJDifference(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			InvZGJBenchMarkModel model = new InvZGJBenchMarkModel();
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			model.setCreationUser(ContextUtil.getCurrentUser().getAccount());
			Integer i = invZGJBenchMarkManager.getZGJDifference(model);
			if (i == 0) {
				message = new ResultMessage(ResultMessage.SUCCESS, "生成支给件差异成功");
			}else {
				message = new ResultMessage(ResultMessage.FAIL,  "生成支给件差异失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "生成支给件差异失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Description: 分页查询支给件差异查询
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月30日 下午3:16:49
	 */
	@RequestMapping("queryWeekCalForPageDifference")
	public @ResponseBody PageJson queryWeekCalForPageDifference(HttpServletRequest request,
			HttpServletResponse response, InvZGJBenchMarkModel model)
			throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		PageList<InvZGJBenchMarkModel> pageList = invZGJBenchMarkManager
				.queryWeekCalForPageDifference(model, page);

		return new PageJson(pageList);
	}
	
	/**
	 * 
	 * @Description: 导出支给件差异数据
	 * @param    
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月30日 下午3:18:05
	 */
	@RequestMapping("exportForExcelDifference")
	public void exportForExcelDifference(HttpServletRequest request, HttpServletResponse response, InvZGJBenchMarkModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<InvZGJBenchMarkModel> list = invZGJBenchMarkManager
				.exportForExcelDifference(model);

		try {
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
			String[] headers = { "仓库代码", "仓库名称", "零件号", "简号", "零件名称", "供应商代码",
					"供应商名称","出货地代码","年度周次","差异数量" };
			String[] columns = { "wareCode", "wareName", "partNo","partShortNo", "partName", "supplierNo", 
					"supplierName","supFactory","yearWeek","differenceNum" };
			int[] widths = { 80, 150, 150, 80, 200, 100, 
					150, 80, 80, 80 };
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					"支给件差异查询" + PupUtil.getCurrentTime("yyyyMMddHHmmss"),
					list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			ExcelUtil.exportException(e, request, response);
		}
	}
}
