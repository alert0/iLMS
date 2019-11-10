package com.hanthink.mon.controller;


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
import com.hanthink.mon.manager.MonAllowDeviationManager;
import com.hanthink.mon.model.MonAllowDeviationModel;
import com.hanthink.mon.model.MonAllowDeviationModelImport;
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
 * @ClassName: MonAllowDeviationController
 * @Description: 允许误差维护
 * @author Midnight
 * @date 2018年11月03日
 */
@Controller
@RequestMapping("/mon/AllowDeviation")
public class MonAllowDeviationController extends GenericController {

	@Resource
	private MonAllowDeviationManager allowDeviationManager;
	

	private static Logger log = LoggerFactory.getLogger(MonAllowDeviationController.class);

	/**
	 * @Description: 允许误差查询
	 * @param: request
	 * @param: response
	 * @param: model
	 * @return: PageJson
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@RequestMapping("queryAllowDeviationPage")
	public @ResponseBody PageJson queryAllowDeviationPage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") MonAllowDeviationModel model) {
		HttpSession session = request.getSession();
		//将数据保存到session
		session.setAttribute(SessionKey.ALLOW_DEVIATION_QUERYFILTER, model);
		
		DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		PageList<MonAllowDeviationModel> pageList = allowDeviationManager.queryAllowDeviationPage(model, page);
		return new PageJson(pageList);

	}

	/**
	 * 保存允许误差数据信息
	 * @param request
	 * @param response
	 * @param mpResidual
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MonAllowDeviationModel model) throws Exception{
		String resultMsg = null;
		String id = model.getId();
		try {
			if(StringUtil.isEmpty(id)){
				//主表暂时未添加操作相关字段
//				model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
//				model.setCreationUser(ContextUtil.getCurrentUser().getAccount());
//				model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				//判断集货线路是否存在
				List<MonAllowDeviationModel> list = allowDeviationManager.queryIsExist(model);
				if(null != list && list.size() > 0) {
					writeResultMessage(response.getWriter(),"集货线路已存在",ResultMessage.FAIL);
					return;
				}
				allowDeviationManager.create(model);
				resultMsg="新增成功";
			}else{
//				model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				allowDeviationManager.updateAndLog(model, RequestUtil.getIpAddr(request));
				resultMsg="修改成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			if(StringUtil.isEmpty(id)) {
				resultMsg = "新增失败";
			}else {
				resultMsg = "修改失败";
			}
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除数据记录
	 * @param request
	 * @param response
	 * @throws Exception
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			allowDeviationManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message=new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	
	/**
	 * 导出允许误差数据信息
	 * @param request
	 * @param response
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@RequestMapping("downloadAllowDeviation")
	public void downloadAllowDeviation(HttpServletRequest request,HttpServletResponse response,
			MonAllowDeviationModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MonAllowDeviationModel> list = allowDeviationManager.queryAllowDeviationForExport(model);

		//如果条目大于1000则报错
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"集货线路","误差时间(分)"};
		String[] columns = {"routeCode","errorDate"};
		int[] widths = {100,100};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "允许误差数据"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
	/**
	 * 导入Excel数据信息
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@RequestMapping("importAllowDeviation")
	public void importAllowDeviation(@RequestParam("file") MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
//			uuid = (String)session.getAttribute(SessionKey.MON_ALLOWDEVIATION_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			//如果临时表中存在该uuid的数据，则先进行删除操作
			if(StringUtil.isNotEmpty(uuid)){
				allowDeviationManager.deleteAllowDeviationImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
//			session.setAttribute(SessionKey.MON_ALLOWDEVIATION_IMPORT_UUID, uuid);
			
			Map<String,Object> resultMap = allowDeviationManager.importAllowDeviationTemp(file, uuid, RequestUtil.getIpAddr(request), user);

			//这里会传递uuid
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
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
//		HttpSession session = request.getSession();
		
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<MonAllowDeviationModelImport> pageList = (PageList<MonAllowDeviationModelImport>) allowDeviationManager.queryAllowDeviationImportTempData(paramMap, page);
		return new PageJson(pageList);
	}
	
	/**
	 * 确定导入，将临时表数据写入到正式业务表
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = null;//(String)session.getAttribute(SessionKey.MON_ALLOWDEVIATION_IMPORT_UUID);
			//若uuid为空，则从前端请求中获取
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			
			/**
			 * 临时表数据写入正式表
			 */
			allowDeviationManager.insertAllowDeviationImportData(paramMap,RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
			
		} catch (Exception e) {
//			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 导出临时数据信息
	 * @param request
	 * @param response
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response){
		try {
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = null; //(String)session.getAttribute(SessionKey.MON_ALLOWDEVIATION_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		List<MonAllowDeviationModelImport> list = allowDeviationManager.queryAllowDeviationImportTempDataForExport(paramMap);
		
		String[] headers = {"集货线路", "误差时间(分)",
				"导入状态", "校验结果","校验信息"};
		String[] columns = {"routeCode", "errorDate", 
				"codeValueNameB","codeValueNameC", "checkInfo"};
		int[] widths = {80,80,
				80, 80, 150};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "允许误差数据"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		}
}
