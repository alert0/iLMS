package com.hanthink.inv.controller;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.inv.manager.InvEmptyManager;
import com.hanthink.inv.model.InvEmptyModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
/**
 * <pre> 
 * 功能描述:空容器库存查询业务控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

@RequestMapping("/inv/empty")
@Controller
public class InvEmptyController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(InvEmptyModel.class);
	
	@Resource
	private InvEmptyManager emptyManager;
	/**
	 * 空容器分页查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryEmptyForPage(HttpServletRequest request,HttpServletResponse response,
											InvEmptyModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		PageList<InvEmptyModel> pageList = emptyManager.queryEmptyForPage(model,page);
		
		return new PageJson(pageList);
	}
	@RequestMapping("/exportForExcel")
	public void exportForExcel(HttpServletRequest request,HttpServletResponse response,
									InvEmptyModel model)throws Exception{
		List<InvEmptyModel> list = emptyManager.exportForExcel(model);
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
			String[] headers = {"车间","供应商代码","箱种","空容器数量"};
			String[] columns = {"codeValueNameE","supplierNo","boxTypeExcel","boxQty"};
			int[] widths = {100,100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "空容器库存信息"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 修改空容器数据控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@RequestMapping("/updateForEmpty")
	public void updateForEmpty(HttpServletRequest request,HttpServletResponse response,
						@RequestBody InvEmptyModel model)throws Exception {
		try {
			emptyManager.updateForEmpty(model,RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(), "更新空容器数量成功", model.getId(), ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "更新空容器数量失败", e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 导入Excel数据信息
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月1日 下午11:03:18
	 */
	@RequestMapping("importInvEmptyModel")
	public void importInvEmptyModel(@RequestParam("file") MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.INV_EMPTYMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				emptyManager.deleteInvEmptyImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.INV_EMPTYMODEL_IMPORT_UUID, uuid);
			
			Map<String,Object> resultMap = emptyManager.importInvEmptyModel(file, uuid, RequestUtil.getIpAddr(request),user);
			/**
			 * 这里要传递uuid***
			 */
			resultMap.put("uuid", uuid);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", 
				JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(),"失败", (String) resultMap.get("console"),
				JSONObject.fromObject(resultMap), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 查询导入的临时数据信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月1日 下午6:59:07
	 */
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<InvEmptyModel> pageList = (PageList<InvEmptyModel>) emptyManager.queryInvEmptyImportTempData(paramMap, page);
		return new PageJson(pageList);
	}
	
	/**
	 * 确定导入，将临时表数据写入到正式业务表
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:08:51
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.INV_EMPTYMODEL_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			emptyManager.insertInvEmptyImportData(paramMap,RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
			
		} catch (Exception e) {
    		e.printStackTrace();
			log.error(e.toString());
			
			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 导出临时数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:28
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response){
		try {
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.INV_EMPTYMODEL_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		List<InvEmptyModel> list = emptyManager.queryInvEmptyImportTempDataForExport(paramMap);
		
		String[] headers = {"车间","供应商代码", "箱种", "空容器数量",
				"校验结果","导入状态", "校验信息"};
		String[] columns = {"codeValueNameE","supplierNo", "codeValueNameD", "boxQty",
				"codeValueNameB","codeValueNameC", "checkInfo"};
		int[] widths = {80,80,80,80,
				50,50, 360};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "空容器库存差异量导入"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		}
	
}
