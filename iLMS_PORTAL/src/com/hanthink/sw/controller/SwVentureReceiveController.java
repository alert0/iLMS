package com.hanthink.sw.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.pup.util.PupUtil;
import com.hanthink.sw.manager.SwVentureReceiveManager;
import com.hanthink.sw.model.SwVentureReceiveModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
 * 合资车收货数据数据导入控制器类
 * <pre> 
 * 功能描述:
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2019年8月20日下午2:40:44
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sw/swVentureReceive")
public class SwVentureReceiveController extends GenericController{
	
	@Resource
	private SwVentureReceiveManager receiveManager;
	
	/**
	 * 合资车收货数据界面查询
	 * @return
	 * @author zmj
	 * @date 2019年8月20日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson querySwVentureReceiveForPage(HttpServletRequest request, HttpServletResponse response,
							SwVentureReceiveModel model) {
		Page page = getQueryFilter(request).getPage();
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		model.setUserType(user.getUserType());
		model.setUserId(user.getUserId());
		PageList<SwVentureReceiveModel> pageList = receiveManager.querySwVentureReceiveForPage(model, page);
		return new PageJson(pageList);
	}
	/**
	 * 合资车收货数据界面导出
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@RequestMapping("/exportForQueryPage")
	public void exportForQueryPage(HttpServletRequest request, HttpServletResponse response,
			SwVentureReceiveModel model) throws Exception{
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<SwVentureReceiveModel> list = receiveManager.exportForQueryPage(model);
		if (0 == list.size()) {
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000);
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"工厂","订单号","订单行号","收货时间","零件号","简号","零件名称","订单数量","累计收货数量","是否审核","是否收齐","导入日期"};
		String[] columns = {"ordPlace","ordOrderNo","ordOrderRowNo","recTime","partNo","partShortNo","partName","orderQty","totalRecQty","excelCheckStatus","receiveStatus","creationTime"};
		int[] widths = {80, 100, 80, 120, 120, 80, 120, 80, 80, 80, 80, 120};
		
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "收货数据导出"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
	}
	
	/**
	 * 合资车收货数据导入
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@RequestMapping("/importExcel")
	public void importExcelReceiveData(HttpServletRequest request,HttpServletResponse response,@RequestParam("file")MultipartFile file)throws Exception {
		Map<String, Object> rtn = new HashMap<String, Object>();
		//获取当前用户信息以及页面参数
		IUser user = ContextUtil.getCurrentUser();
		String uuid = RequestUtil.getString(request, "uuid");
		if (StringUtil.isEmpty(uuid)) {
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
		}else {
			receiveManager.deleteLastTimeImportExcel(uuid);
		}
		rtn = receiveManager.importReceiveExcel(file, uuid, RequestUtil.getIpAddr(request), user);
		rtn.put("uuid", uuid);
		//将导入结果返回页面
		if((Boolean)rtn.get("result")){
			writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
		}else {
			writeResultMessage(response.getWriter(), (String) rtn.get("console"), ResultMessage.FAIL);
		}
	}
	/**
	 * 导入数据查询
	 * @param request
	 * @param response
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	@RequestMapping("/queryImportForPage")
	public @ResponseBody PageJson queryImportForPage(HttpServletRequest request,HttpServletResponse response) {
		Page page = getQueryFilter(request).getPage();
		String uuid = RequestUtil.getString(request, "uuid");
		PageList<SwVentureReceiveModel> pageList = receiveManager.queryImportForPage(uuid, page);
		return new PageJson(pageList);
	}
	/**
	 * 确认导入数据
	 * @param request
	 * @param response
	 * @author zmj
	 * @throws IOException 
	 * @date 2019年8月22日
	 */
	@RequestMapping("/isImportForRecNum")
	public void isImportForRecNum(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ResultMessage resultMessage = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String uuid = RequestUtil.getString(request, "uuid");
		if (StringUtil.isEmpty(uuid)) {
			resultMessage = new ResultMessage(ResultMessage.FAIL,"系统错误,请联系管理员");
			writeResultMessage(response.getWriter(), resultMessage);
		}
		paramMap.put("uuid", uuid);
		paramMap.put("opeIp", RequestUtil.getIpAddr(request));
		Boolean isAllPass = receiveManager.checkImportCount(uuid);
		if (isAllPass) {
			try {
				//判断本次导入数据是否全部正确
				receiveManager.isImportForRecNum(paramMap);
				resultMessage = new ResultMessage(ResultMessage.SUCCESS, "执行数据写入成功");
				writeResultMessage(response.getWriter(), resultMessage);
			} catch (Exception e) {
				e.printStackTrace();
				resultMessage = new ResultMessage(ResultMessage.FAIL,"系统错误,请联系管理员");
				writeResultMessage(response.getWriter(), resultMessage);
			}
		}else {
			resultMessage = new ResultMessage(ResultMessage.FAIL,"请确保所有数据的正确性");
			writeResultMessage(response.getWriter(), resultMessage);
		}
	}
	/**
	 * 导出导入数据
	 * @param request
	 * @param response
	 * @author zmj
	 * @throws Exception 
	 * @date 2019年8月22日
	 */
	@RequestMapping("exportForRecImport")
	public void exportForRecImport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String uuid = RequestUtil.getString(request, "uuid");
		List<SwVentureReceiveModel> list = receiveManager.queryRecImportForExport(uuid);
		String[] headers = {"工厂","订单号","订单行号","品番","本次收货数量","收货时间","背番号", "零件名称","校验结果","导入状态","校验信息"};
		String[] columns = {"ordPlace","ordOrderNo","ordOrderRowNo", "partNo", "recQty", "recTime", "partShortNo","partName","excelCheckResult","excelImportStatus","checkInfo"};
		int[] widths = {80, 120, 80, 120, 80, 100, 80, 120, 100, 80, 300};
		
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "合资车收货数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
	}
	
	@RequestMapping("/checkRecForUpload")
	public void checkRecForUpload(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		String opeIp = RequestUtil.getIpAddr(request);
		String checkResult = RequestUtil.getString(request, "checkResult");
		String checkInfo = RequestUtil.getSecureString(request, "checkInfo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user", user.getAccount());
		paramMap.put("opeIp", opeIp);
		paramMap.put("checkResult", checkResult);
		paramMap.put("checkInfo", checkInfo);
		paramMap.put("factoryCode",user.getCurFactoryCode());
		paramMap.put("checkStatus", "1");
		paramMap.put("errFlag", 0);
		paramMap.put("errMsg", "");
		try {
			receiveManager.checkRecForUploadAll(paramMap);
			if (String.valueOf(paramMap.get("errFlag")).equals("1")) {
				resultMsg = "审核收货数据成功";
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
			}else {
				resultMsg = String.valueOf(paramMap.get("errMsg"));
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "系统错误,请联系管理员";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}
	}
}
