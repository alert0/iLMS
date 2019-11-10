package com.hanthink.sps.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.hanthink.sps.manager.SpsConfigItemManager;
import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hanthink.sps.model.SpsConfigItemModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
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


@Controller
@RequestMapping("/sps/SpsConfigItem")
public class SpsConfigItemController extends GenericController{

	@Resource
	private SpsConfigItemManager SpsConfigItemManager;
	private static Logger log = LoggerFactory.getLogger(SpsConfigItemController.class);
	
	/**
	 * 分页查询配置项代码基础数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("querySpsConfigItemPage")
	public @ResponseBody PageJson querySpsConfigItemPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsConfigItemModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsConfigItemModel> pageList = SpsConfigItemManager.querySpsConfigItemPage(model, page);
		return new PageJson(pageList);
	}


	/**
	 * 更新配置项代码(方法已作废，合并到新增)
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("updateConfigItem")
	public void updateConfigItem(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsConfigItemModel model) {
		//SpsConfigItemManager.updateConfigItem(model);
		//writeResultMessage(response.getWriter(), message);
	}


	/**
	 * 批量删除配置项代码
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping("deleteConfigItemByBatch")
	public void deleteConfigItemByBatch(HttpServletRequest request,HttpServletResponse response, 
			@RequestBody SpsConfigItemModel model) throws Exception {
		ResultMessage resultMessage = null;
		String parameter = model.getId();
		String[] split = parameter.split(",");
		ArrayList<SpsConfigItemModel> arrayList = new ArrayList<SpsConfigItemModel>();
		for (int i = 0; i < split.length; i++) {
			String string = split[i];
			SpsConfigItemModel models = new SpsConfigItemModel();
			models.setId(string);
			arrayList.add(models);
		}
		try {
			List<SpsConfigDetailModel> detailList = SpsConfigItemManager.querySpsConfigDetailList(arrayList);
			if(null != detailList && detailList.size() > 0) {
				writeResultMessage(response.getWriter(), "配置项代码[" + detailList.get(0).getConfigCode() + "]不可删除",ResultMessage.FAIL);
				return;
			}
			SpsConfigItemManager.deleteConfigItemByBatch(arrayList, RequestUtil.getIpAddr(request), split);
			resultMessage =  new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMessage = new ResultMessage(ResultMessage.FAIL,e.getMessage());
		}
		writeResultMessage(response.getWriter(),resultMessage);
	}

	/**
	 * 新增or保存配置项代码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/insertConfigItem")
	public void insertConfigItem(HttpServletRequest request,HttpServletResponse response, 
			@RequestBody SpsConfigItemModel model) throws Exception{
		try {
		String message = "";
		IUser currentUser = ContextUtil.getCurrentUser();
		String configCode2 = model.getConfigCode();
		String configDesc = model.getConfigDesc();
		String configType = model.getConfigType();
		if (configCode2 != null && !"".equals(configCode2) &&
				configDesc != null && !"".equals(configDesc) &&
						configType != null && !"".equals(configType)) {
			String id = model.getId();
			if (StringUtil.isEmpty(id)) {//判断id是否存在，存在即修改，不存在即为新增
				model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				List<SpsConfigItemModel> queryConfigCode = SpsConfigItemManager.queryConfigCode(model);
				if (queryConfigCode != null && queryConfigCode.size() > 0) {
					String configCode = queryConfigCode.get(0).getConfigCode();
					message = "配置项代码" + configCode + "已存在";
					writeResultMessage(response.getWriter(),message,ResultMessage.FAIL);
					return;
				}  else {
					model.setCreationUser(currentUser.getAccount());
					model.setFactory(currentUser.getCurFactoryCode());
					model.setProductionLine(currentUser.getCurProductLine());
					int insertConfigItem = SpsConfigItemManager.insertConfigItem(model);
					if (insertConfigItem == 1) {
						message += "新增成功";
						writeResultMessage(response.getWriter(),message,ResultMessage.SUCCESS);
					}
				}
			} else {
				if ("1".equals(configType)) {
					message = "所属类型不允许修改";
					writeResultMessage(response.getWriter(),message,ResultMessage.FAIL);
					return;
				}
				model.setLastModifiedUser(currentUser.getAccount());
				model.setFactory(currentUser.getCurFactoryCode());
				model.setProductionLine(currentUser.getCurProductLine());
				int updateConfigItem = SpsConfigItemManager.updateConfigItem(model, RequestUtil.getIpAddr(request));
				if (updateConfigItem == 1) {
					message += "修改成功";
					writeResultMessage(response.getWriter(),message,ResultMessage.SUCCESS);
				}
			}
		} else {
			message = "必填信息不完整，无法完成操作";
			writeResultMessage(response.getWriter(),message,ResultMessage.FAIL);
		}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}

	/**
	 * 下载导出配置项代码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("downloadSpsConfigItem")
	public void downloadSpsConfigItem(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") SpsConfigItemModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "SPS配置项" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsConfigItemModel> pageList = SpsConfigItemManager.querySpsConfigItemPage(model, page);
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
		int sysMaxNum_T = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000);
		if(curNum > sysMaxNum_T){
			ExcelUtil.exportNumError(sysMaxNum_T, request, response);
			return;
		}
		List<SpsConfigItemModel> list = SpsConfigItemManager.querySpsConfigItemList(model);
		if(null != list) {
			String[] headers = {"配置项代码", "配置项描述", "所属类型", "固定值"};
			String[] columns = {"configCode", "configDesc", "codeValueName", "configValue"};
			int[] widths = {100, 100, 100, 100};
			if(curNum <= sysMaxNum) {
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}else {
			}
		}

	}
	
	/**
	 * 导入临时表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("importSpsConfigItemTemp")
	public void importSpsConfigItemTemp(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.SPS_CONFIG_ITEM_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)) {
				SpsConfigItemManager.deleteImportTempDataByUUID(uuid);
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.SPS_CONFIG_ITEM_UUID, uuid);
			Map<String,Object> rtn = SpsConfigItemManager.importSpsConfigItemTemp(file, uuid,RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), "失败", "", JSONObject.fromObject(rtn), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
		}
		
	}
	
	
	/**
	 * 确认保存配置项代码
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") SpsConfigItemModel model) throws IOException {
		ResultMessage message = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.SPS_CONFIG_ITEM_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			//查询导入校验结果是否包含不通过
			int count = SpsConfigItemManager.queryIsExistsCheckResultFalse(uuid);
			if(count > 0) {
				writeResultMessage(response.getWriter(), "存在校验结果不通过数据", ResultMessage.FAIL);
				return;
			}
			paramMap.put("uuid", model.getUuid());
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
			paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
			paramMap.put("lastModifiedUser", ContextUtil.getCurrentUser().getAccount());
			paramMap.put("lastModifiedIp", RequestUtil.getIpAddr(request));
			SpsConfigItemManager.insertImportData(paramMap);
			message = new ResultMessage(ResultMessage.SUCCESS, "数据导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "数据导入失败");
		}
		writeResultMessage(response.getWriter(), message);
		
		/*ResultMessage message = null;
		try {
			Map<String, Object> spsConfigItemImportData = SpsConfigItemManager.spsConfigItemImportData();
			Boolean flag = (Boolean) spsConfigItemImportData.get("result");
			String mess = (String) spsConfigItemImportData.get("message");
			if (flag) {
				message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "执行失败"+mess);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);*/
	}
	
	
	/**
	 * 查询配置项代码导入临时数据
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 */
	@RequestMapping("queryImportTempPage")
	public @ResponseBody PageJson queryImportTempPage(HttpServletRequest request,HttpServletResponse reponse,
			@ModelAttribute("model") SpsConfigItemModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsConfigItemModel> pageList = SpsConfigItemManager.querySpsConfigTemp(model, page);
		return new PageJson(pageList);
		
	}
	

	/**
	 * 配置项代码导入临时数据导出
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") SpsConfigItemModel model) {
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.SPS_CONFIG_ITEM_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		model.setUuid(uuid);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName =  "配置项维护导入" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsConfigItemModel> pageList = SpsConfigItemManager.querySpsConfigTemp(model, page);
		//判断记录是否超过系统允许数量
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); //获取系统所允许的最大导出数量
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		List<SpsConfigItemModel> list = SpsConfigItemManager.querySpsConfigTemp(model);
		if(null != list) {
			String[] headers = {"配置项代码", "配置项描述", "所属类型", "固定值", 
					"导入状态", "检查结果", "校验信息"};
			String[] columns = {"configCode", "configDesc", "codeValueName", "configValue", 
					"importStatus", "checkResult", "checkInfo"};
			int[] widths = {100, 100, 80, 80, 
					80, 100, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
		
	}
	
	/**
	 * 临时数据删除
	 * @param request
	 * @param reponse
	 * @param model
	 * @throws IOException
	 * Lxh
	 */
	@RequestMapping("removeTemp")
	public void removeModelTemp(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		int count = 0;
		count = SpsConfigItemManager.removeSpsConfigItemTemp();
		ResultMessage message;
		message = new ResultMessage(ResultMessage.SUCCESS, "成功删除临时数据" + count + "条");
		writeResultMessage(reponse.getWriter(), message);
	}


}
