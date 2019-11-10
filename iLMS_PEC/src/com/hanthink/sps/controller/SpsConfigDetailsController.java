package com.hanthink.sps.controller;

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

import com.hanthink.pup.controller.PupProPlanController;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.sps.manager.SpsConfigDetailManager;
import com.hanthink.sps.model.SpsConfigDetailModel;
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
 * 功能描述:SPS配置明细维护控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年11月8日 下午4:28:13
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/sps/config")
@Controller
public class SpsConfigDetailsController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupProPlanController.class);
	
	@Resource
	private SpsConfigDetailManager configManager;
	/**
	 * 分页数据查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryConfigDetailsForPage(HttpServletRequest request,HttpServletResponse response,
													SpsConfigDetailModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		
		PageList<SpsConfigDetailModel> pageList = configManager.queryConfigDetailsForPage(model,page);
		
		return new PageJson(pageList);
	}
	
	/**
	 * 新增/修改配置项信息详情
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/savaModOrAdd")
	public void modifyAndAddConfig(HttpServletRequest request,HttpServletResponse response,
								@RequestBody SpsConfigDetailModel model)throws Exception {
		ResultMessage resultMessage = null;
		//根据id判断是新增还是修改
		String id = model.getId();
		model.setLastModifiedIp(RequestUtil.getIpAddr(request));
		//如果id为空判定为新增
		if (StringUtil.isEmpty(id)) {
			try {
				configManager.createConfigDetails(model);
				resultMessage = new ResultMessage(ResultMessage.SUCCESS,"新增成功");
			} catch (Exception e) {
				e.printStackTrace();
				resultMessage = new ResultMessage(ResultMessage.FAIL,e.getMessage());
			}
		}else {
			try {
				configManager.updateConfigDetails(model,RequestUtil.getIpAddr(request));
				resultMessage = new ResultMessage(ResultMessage.SUCCESS,"修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				resultMessage = new ResultMessage(ResultMessage.FAIL,e.getMessage());
			}
		}
		writeResultMessage(response.getWriter(),resultMessage);
	}
	/**
	 * 单条/批量数据删除
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/removeConfigDetails")
	public void removeConfigDetails(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String[] ids = RequestUtil.getStringAryByStr(request, "id");
		String ipAddr = RequestUtil.getIpAddr(request);
		ResultMessage resultMessage = null;
		try {
			configManager.removeConfigDetailsByIds(ids,ipAddr);
			resultMessage = new ResultMessage(ResultMessage.SUCCESS,"删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage = new ResultMessage(ResultMessage.FAIL,e.getMessage());
		}
		writeResultMessage(response.getWriter(),resultMessage);
	}
	/**
	 * 导出页面的查询数据
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/exportQueryForExcel")
	public void exportQueryForExcel(HttpServletRequest request,HttpServletResponse response,
											SpsConfigDetailModel model)throws Exception {
		try {
			List<SpsConfigDetailModel> list = configManager.exportQueryForExcel(model);
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
			String[] headers = {"配置项代码","配置项描述","车型", "工位", "零件号","简号","零件名称","记号","拣货号"};
			String[] columns = {"configCode","configDesc","modelCode", "stationCode", "partNo","partShortNo","partName","partMark","shelfNo"};
			int[] widths = {100,100, 60, 60,100, 80, 100,60, 60};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "配置明细导出"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * SPS配置项明细Excel导入
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/importForExcel")
	public void importConfigForExcel(HttpServletRequest request,HttpServletResponse response,
											@RequestParam("file") MultipartFile file)throws Exception {
		String uuid = RequestUtil.getString(request, "uuid");
		if (StringUtil.isNotEmpty(uuid)) {
			configManager.deleteTempCongfigByUUID(uuid);
		}else {
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
		}
		try {
			Map<String, Object> resultMap = configManager.importConfigDetailModel(file,uuid,RequestUtil.getIpAddr(request));
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
	 * @author zmj
	 */
	@RequestMapping("/queryForImport")
	public @ResponseBody PageJson queryImportForPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String uuid = RequestUtil.getString(request, "uuid");
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<SpsConfigDetailModel> pageList = configManager.queryImportForPage(uuid,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 确认导入,将临时表数据写入正式表
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		ResultMessage resultMessage = null;
		String uuid = RequestUtil.getString(request, "uuid");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//查询导入校验结果是否包含不通过
			int count = configManager.queryIsExistsCheckResultFalse(uuid);
			if(count > 0) {
				writeResultMessage(response.getWriter(), "存在校验结果不通过数据", ResultMessage.FAIL);
				return;
			}
			paramMap.put("ipAddr", RequestUtil.getIpAddr(request));
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			configManager.insertTempToFormal(paramMap);
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
			List<SpsConfigDetailModel> list = configManager.exportForImport(uuid);
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
			String[] headers = {"配置项代码","车型", "工位", "零件号","记号","拣货号","导入状态","校验结果","校验信息"};
			String[] columns = {"configCode","modelCode", "stationCode", "partNo","partMark","shelfNo","excelImportStatus","excelCheckResult","checkInfo"};
			int[] widths = {100,60, 60, 80, 60, 60, 60, 70, 70, 120};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "配置明细导入"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
}
