package com.hanthink.sps.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.business.util.SessionKey;
import com.hanthink.sps.manager.SpsMouldManager;
import com.hanthink.sps.model.SpsConfigModel;
import com.hanthink.sps.model.SpsMouldConfigModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
import com.mysql.jdbc.StringUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.json.JSONObject;

/**
 * @ClassName: SpsMouldController
 * @Description: SPS票据模板管理
 * @author dtp
 * @date 2018年11月21日
 */
@Controller
@RequestMapping("/sps/spsMould")
public class SpsMouldController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(SpsMouldController.class);
	
	@Resource
	private SpsMouldManager spsMouldManager;
	
	/**
	 * @Description: 模板列表查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	@RequestMapping("querySpsMouldPage")
	public @ResponseBody PageJson querySpsMouldPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsMouldModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsMouldModel> pageList = spsMouldManager.querySpsMouldPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 修改SPS模板列表,2019-1-12修改未可新增
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月21日
	 */
	@RequestMapping("updateSpsMould")
	public void updateSpsMould(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsMouldModel model) throws IOException {
		IUser user = ContextUtil.getCurrentUser();
		model.setLastModifiedIp(RequestUtil.getIpAddr(request));
		model.setLastModifiedUser(user.getAccount());
		model.setCreationUser(user.getAccount());
		//根据id是否为空判断新增还是修改
		if(StringUtils.isNullOrEmpty(model.getId())) {
			try {
				spsMouldManager.insertSpsMould(model, RequestUtil.getIpAddr(request));
				writeResultMessage(response.getWriter(), "保存成功", ResultMessage.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				writeResultMessage(response.getWriter(),"系统错误,请联系管理员",e.getMessage(),ResultMessage.FAIL);
			}
		}else {
			try {
				spsMouldManager.updateSpsMould(model, RequestUtil.getIpAddr(request));
				writeResultMessage(response.getWriter(), "修改成功", ResultMessage.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				writeResultMessage(response.getWriter(),"系统错误,请联系管理员",e.getMessage(),ResultMessage.FAIL);
			}
		}
		
	}
	
	/**
	 * @Description: 配置列表查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	@RequestMapping("querySpsMouldConfigPage")
	public @ResponseBody PageJson querySpsMouldConfigPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsMouldConfigModel model) {
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsMouldConfigModel> pageList = spsMouldManager.querySpsMouldConfigPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description:   
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	@RequestMapping("downloadSpsMouldConfig")
	public void downloadSpsMouldConfig(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsMouldConfigModel model) {
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		String exportFileName = "SPS指示票模板配置列表" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsMouldConfigModel> pageList = spsMouldManager.querySpsMouldConfigPage(model, page);
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		List<SpsMouldConfigModel> list = spsMouldManager.querySpsMouldConfigList(model);
		if(null != list) {
			String[] headers = {"位置号", "配置项代码", "显示属性", "是否上传图片"};
			String[] columns = {"mouldPlace", "configCode",  "configShowDesc", "isUploadImage"};
			int[] widths = {100, 100, 100, 100};
			if(curNum <= sysMaxNum) {
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}else {
				//ExcelExportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}
		}
	}
	
	/**
	 * @Description: 修改配置列表数据
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月22日
	 */
	@RequestMapping("updateSpsMouldConfig")
	public void updateSpsMouldConfig(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsMouldConfigModel model) throws IOException {
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		model.setLastModifiedIp(RequestUtil.getIpAddr(request));
		model.setLastModifiedUser(user.getAccount());
		try {
			if(StringUtils.isNullOrEmpty(model.getId())) {
				//判断业务主键是否冲突
				List<SpsMouldConfigModel> list = spsMouldManager.queryIsExists(model);
				if(null != list && list.size() > 0) {
					resultMsg = "位置号已维护配置项代码";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}else {
					spsMouldManager.insertSpsMouldConfig(model, RequestUtil.getIpAddr(request));
					resultMsg = "新增成功";
				}
			}else {
				spsMouldManager.updateSpsMouldConfig(model, RequestUtil.getIpAddr(request));
				resultMsg = "修改成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"系统错误,请联系管理员",e.getMessage(),ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * @Description: 上传图片后保存fileId
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月22日
	 */
	@RequestMapping("updateSpsMouldConfigFileId")
	public void updateSpsMouldConfigFileId(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsMouldConfigModel model) throws IOException {
		IUser user = ContextUtil.getCurrentUser();
		model.setLastModifiedIp(RequestUtil.getIpAddr(request));
		model.setLastModifiedUser(user.getAccount());
		try {
			spsMouldManager.updateSpsMouldConfigFileId(model, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(), "保存成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"保存失败",e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * @Description: SPS票据模板配置列表导入
	 * @param: @param file
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月23日
	 */
	@RequestMapping("importSpsMould")
	public void importSpsMould (@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			//SPS票据模板管理模板id
			String mouldId = RequestUtil.getString(request, "mouldId");
			if(StringUtil.isEmpty(mouldId)) {
				writeResultMessage(response.getWriter(), "SPS票据模板为空", ResultMessage.FAIL);
				return;
			}
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.SPS_MOULD_CONFIG_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)) {
				spsMouldManager.deleteImportTempDataByUUID(uuid); 
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.SPS_MOULD_CONFIG_IMPORT_UUID, uuid);
			Map<String,Object> rtn = spsMouldManager.importSpsMould(file, uuid, RequestUtil.getIpAddr(request), mouldId);
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
	 * @Description: SPS配置列表临时表导入到正式表
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月23日
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") SpsMouldConfigModel model) throws IOException {
		ResultMessage message = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.SPS_MOULD_CONFIG_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			//查询校验结果是否包含不通过
			int count = spsMouldManager.queryIsExistsCheckResultFalse(uuid);
			if(count > 0) {
				writeResultMessage(response.getWriter(), "存在校验结果不通过数据", ResultMessage.FAIL);
				return;
			}
			paramMap.put("uuid", model.getUuid());
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			paramMap.put("lastModifiedUser", ContextUtil.getCurrentUser().getAccount());
			paramMap.put("lastModifiedIp", RequestUtil.getIpAddr(request));
			spsMouldManager.insertImportData(paramMap);
			message = new ResultMessage(ResultMessage.SUCCESS, "数据导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "数据导入失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * @Description: 查询临时表数据
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@RequestMapping("queryImportTempPage")
	public @ResponseBody PageJson queryImportTempPage(HttpServletRequest request,HttpServletResponse response) {
		//@ModelAttribute("model") SpsMouldConfigModel model
		SpsMouldConfigModel model = new SpsMouldConfigModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.SPS_MOULD_CONFIG_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		model.setUuid(uuid);
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsMouldConfigModel> pageList = spsMouldManager.queryImportTempPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 导出临时表数据 
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response) {
		SpsMouldConfigModel model = new SpsMouldConfigModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.SPS_MOULD_CONFIG_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		model.setUuid(uuid);
		String exportFileName =  "SPS模板配置列表" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsMouldConfigModel> pageList = spsMouldManager.queryImportTempPage(model, page);
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
		List<SpsMouldConfigModel> list = spsMouldManager.queryImportTempList(model);
		if(null != list) {
			String[] headers = {"位置号", "配置项代码", "显示属性", "导入状态",
					"校验结果","校验信息"};
			String[] columns = {"mouldPlace", "configCode", "configShowDesc", "importStatus",
					"checkResult" , "checkInfo"};
			int[] widths = {80, 100, 80, 80,
					80, 150};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
		
	}
	
	/**
	 * @Description: 模板预览
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws Exception 
	 * @date: 2018年12月8日
	 */
	@RequestMapping("showSpsMould")
	public void showSpsMould(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsMouldModel model) throws Exception {
		List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
		try {
			String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator +"ireport" + File.separator + "sps" + File.separator 
					+ model.getMouldCode() + ".jasper";
			InputStream file = new FileInputStream(filenurl);
			//分页
			String filenurl_2 = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator +"ireport" + File.separator + "sps" + File.separator 
					+ model.getMouldCode() + "2" + ".jasper";
			InputStream file_2 = new FileInputStream(filenurl_2);
			//分页
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			HashMap<String, Object> parameters_2 = new HashMap<String, Object>();
			for (int i = 0; i <= 600; i++) {
				if(21 == i) {
					//打印二维码
					parameters.put("s21", MakeQrcodeImages.getQrCodeImage("21", "80", "80"));
					parameters_2.put("s21", MakeQrcodeImages.getQrCodeImage("21", "80", "80"));
				}else {
					parameters.put("s" + i, "" + i);
					parameters_2.put("s" + i, "" + i);
				}
			}
			JRDataSource jRDataSource = new JREmptyDataSource();
			JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
			JasperPrint jasperPrint_2 = JasperFillManager.fillReport(file_2, parameters_2, jRDataSource);
			if(null != jasperPrint && null != jasperPrint_2) {
				JasperPrintList.add(jasperPrint);
				JasperPrintList.add(jasperPrint_2);
			}
			//防止size = 0 抛异常
			if(JasperPrintList.size() > 0) {
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline;");
				JRPdfExporter exporter = new JRPdfExporter();
				OutputStream out = response.getOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
				exporter.exportReport();	
			}
//			response.setContentType("application/pdf");
//			response.setHeader("Content-disposition", "inline;"); 
//			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"系统错误,请联系管理员",e.getMessage(),ResultMessage.FAIL);
		}
		
		
	}
	
	/**
	 * @Description: 配置列表删除  
	 * @param: @param request
	 * @param: @param response
	 * @param: @param models    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年1月21日
	 */
	@RequestMapping("deleteSpsMouldConfig")
	public void deleteSpsMouldConfig(HttpServletRequest request,HttpServletResponse response,
			@RequestBody SpsMouldConfigModel[] models) throws IOException {
		try {
			spsMouldManager.deleteSpsMouldConfig(models, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(), "删除成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"删除失败",ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * @Description:  加载SPS配置项列表下拉框
	 * @param: @param request
	 * @param: @param response
	 * @param: @return    
	 * @return: List<T>   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@RequestMapping("querySpsConfigData")
	public @ResponseBody List<T> querySpsConfigData(HttpServletRequest request,HttpServletResponse response) {
		SpsConfigModel model = new SpsConfigModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setProductionLine(ContextUtil.getCurrentUser().getCurProductLine());
		List<SpsConfigModel> list = spsMouldManager.querySpsConfigData(model);
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		if(null != list && list.size() > 0) {
			for (SpsConfigModel SpsConfigModel : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("label", SpsConfigModel.getConfigCode());
				map.put("value", SpsConfigModel.getConfigCode());
				dataDictList.add(map);
			}
		}
		return new PageJson(dataDictList).getRows();
	}
	
	
}
