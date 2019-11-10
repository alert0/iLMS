package com.hanthink.inv.controller;

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
import com.hanthink.inv.manager.InvStockTakManager;
import com.hanthink.inv.model.InvStockTakModel;
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
 * 盘点信息业务控制器类
 * <pre> 
 * 功能描述:
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月12日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/inv/stockTak")
@Controller
public class InvStockTakController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(InvStockTakController.class);
	
	@Resource
	private InvStockTakManager stockTakManager;
	/**
	 * 分页数据查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryStockTakForPage(HttpServletRequest request,HttpServletResponse response,
									InvStockTakModel model)throws Exception {
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvStockTakModel> pageList = stockTakManager.queryStockTakForPage(model,page);
		
		return new PageJson(pageList);
	}
	/**
	 * Excel数据导出查询控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@RequestMapping("/queryForExport")
	public void queryDataForExcelExport(HttpServletRequest request,HttpServletResponse response,
										InvStockTakModel model)throws Exception {
		
		List<InvStockTakModel> list = stockTakManager.queryDataForExcelExport(model);
		
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
			
			String[] headers = {"零件 编号","简号","零件地址","收容数","理论在库(箱)","实际在库(箱)","盘点时间","数据来源","备注"};
			String[] columns = {"partNo","partShortNo","location","standerdPackage","takeStock","calStock","creationTime","dataSource","note"};
			int[] widths = {100,100,100, 100,100,100, 100,100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "盘点数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * Excel数据导入控制器
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@RequestMapping("/importForExcel")
	public void importForExcel(HttpServletRequest request,HttpServletResponse response,
								@RequestParam("file")MultipartFile file)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String importUuid = null;
			HttpSession session = request.getSession();
			importUuid = (String) session.getAttribute(SessionKey.INV_STOCKTAK_IMPORT_UUID);
			if (StringUtil.isEmpty(importUuid)) {
				importUuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(importUuid)) {
				stockTakManager.deleteTempStockTakByUUID(importUuid);
			}else {
				importUuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.INV_STOCKTAK_IMPORT_UUID, importUuid);
			
			resultMap = stockTakManager.importStockTakToTemp(file,importUuid,RequestUtil.getIpAddr(request));
			resultMap.put("uuid", importUuid);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), "文件导入成功", "文件导入成功", JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), "系统错误,请联系管理员", ResultMessage.FAIL);
		}
	}
	/**
	 * 查询Excel导入数据
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@RequestMapping("/queryImportTemp")
	public @ResponseBody PageJson queryImportTempForPage(HttpServletRequest request)throws Exception {
		
		Page page = getQueryFilter(request).getPage();
		String uuid = RequestUtil.getString(request, "uuid");
		
		PageList<InvStockTakModel> pageList = stockTakManager.queryImportTempForPage(uuid,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 确认导入，将临时表数据写入正式表
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		ResultMessage message = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.INV_STOCKTAK_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			paramMap.put("ipAddr", RequestUtil.getIpAddr(request));
			stockTakManager.isImport(paramMap);
			message = new ResultMessage(ResultMessage.SUCCESS, "数据写入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "系统错误,请联系管理员");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 导入数据详情Excel导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@RequestMapping("/exportTempForExcel")
	public void exportTempForExcel(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Page page = getQueryFilter(request).getPage();
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.INV_STOCKTAK_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		PageList<InvStockTakModel> pageList = stockTakManager.queryImportTempForExport(uuid,page);
		
		String[] headers = {"NO.", "零件编号", "简号", "仓库代码","实际在库(箱)","备注","导入状态","校验结果", "校验信息"};
		String[] columns = {"NO", "partNo", "partShortNo", "wareCode", "takeStock", "note", "excelImportStatus","excelCheckResult", "checkInfo"};
		int[] widths = {40, 80, 80, 80, 80, 280, 80, 80, 360};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "盘点导入数据详情"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), pageList, headers, widths, columns);
	}
}
