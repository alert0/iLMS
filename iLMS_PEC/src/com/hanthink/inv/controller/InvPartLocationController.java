package com.hanthink.inv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.business.util.SessionKey;
import com.hanthink.inv.manager.InvPartLocationManager;
import com.hanthink.inv.model.InvPartLocationModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
import com.mysql.jdbc.StringUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONObject;

/**
 * @ClassName: InvPartLocationController
 * @Description: 零件属地维护
 * @author dtp
 * @date 2019年2月16日
 */
@RequestMapping("/inv/partLocation")
@Controller
public class InvPartLocationController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(InvPartLocationController.class);
	
	@Resource
	private InvPartLocationManager invPartLocationManager;
	
	/**
	 * @Description: 分页查询零件属地
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@RequestMapping("queryInvPartLocationPage")
	public @ResponseBody PageJson queryInvPartLocationPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") InvPartLocationModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<InvPartLocationModel> pageList = invPartLocationManager.queryInvPartLocationPage(model, page);
		
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: 导出零件属地数据
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@RequestMapping("downloadInvPartLocation")
	public void downloadInvPartLocation(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") InvPartLocationModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "零件属地导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<InvPartLocationModel> pageList = invPartLocationManager.queryInvPartLocationPage(model, page);
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
		List<InvPartLocationModel> list = invPartLocationManager.queryInvPartLocationList(model);
		if(null != list) {
			String[] headers = {"卸货代码", "供应商代码", "零件号", "备货工程",
					"台车号", "物流库地址", "配送工程", "配送地址", "工位号", 
					"货架号", "工程深度", "责任班组", "车型","生效日期", "失效日期", "简号",
					"零件名称", "供应商名称"};
			String[] columns = {"unload_port", "supplier_no",  "part_no", "prepare_person",
					"carpool", "storage", "distri_person", "location", "station_code", 
					"shelf_no", "location_num", "dept_No", "modelCode","eff_start", "eff_end", "part_short_no",
					"part_name_cn", "supplier_name"};
			int[] widths = {60, 60, 120, 60, 
					60, 60, 60, 60, 60, 
					60, 60, 60, 40, 60, 60, 40,
					120, 120};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
	}
	
	/**
	 * @Description: 零件属地维护页面打印货架标签(线边库)
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年1月19日
	 */
	@RequestMapping("printInvShelfLabel")
	public void printInvShelfLabel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String idStr = RequestUtil.getString(request, "idStr");
		String modelCodeStr = RequestUtil.getString(request, "modelCodeStr");
		String[] idArr = idStr.split(",");
		String[] modelCodeArr = modelCodeStr.split(";");
		if(null != idArr && idArr.length > 0 && null != modelCodeArr && modelCodeArr.length == idArr.length ) {
			//打印N张
			List<InvPartLocationModel> list = new ArrayList<InvPartLocationModel>();
			InvPartLocationModel model = new InvPartLocationModel();
			//红点图片
			//String pagePath = FileUtil.getClassesPath() + File.separator + "template" 
			//		+ File.separator + "page" + File.separator + "inv_shelf.jpg";
			//BufferedImage shelfImg = ImageIO.read(new FileInputStream(pagePath));
			List<String> cfList =  new ArrayList<String>();
			for (int i = 0; i < idArr.length; i++) {
				model.setId(idArr[i]);
				InvPartLocationModel bean = invPartLocationManager.queryInvShelfPrintInfo(model);
				if (cfList.contains(bean.getPartNo()+bean.getLocation())) {
					continue;
				}
				cfList.add(bean.getPartNo()+bean.getLocation());
				//二维码
				StringBuffer qrCode = new StringBuffer();
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getPartNo());//零件号
				qrCode.append("#");
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getPartShortNo());//简号
				qrCode.append("#");
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getSupplierNo());//供应商代码
				qrCode.append("#");
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getShelfNo());//货架号
				qrCode.append("#");
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getStandardPackage());//收容数
				qrCode.append("#");
				qrCode.append("100");//最小在库量
				qrCode.append("#");
				qrCode.append("1000");//最大在库量
				qrCode.append("#");
				qrCode.append("4");//叠放层次
     			bean.setQrCode(MakeQrcodeImages.getQrCodeImage(qrCode.toString(), "120", "120"));
				//bean.setShelfImg(shelfImg);
     			//车型
     			bean.setModelCode(modelCodeArr[i]);
				list.add(bean);
			}
			JRDataSource jRDataSource;
			if (list.size() > 0) {
				jRDataSource = new JRBeanCollectionDataSource(list);
			} else {
				jRDataSource = new JREmptyDataSource();
			}
			String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator +"ireport" + File.separator + "inv" + File.separator + "INV_SHELF_LABEL.jasper";
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;"); 
			try {
				InputStream file = new FileInputStream(filenurl);
				Map<String, Object> parameters = new HashMap<String, Object>();
				JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
				JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				writeResultMessage(response.getWriter(), "系统错误，请联系管理员", e.getMessage(),ResultMessage.FAIL);
			}
		}
		
	}
	
	/**
	 * @Description: PC库货架标签打印  (PC库)
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年1月24日
	 */
	@RequestMapping("printInvShelfLabelPc")
	public void printInvShelfLabelPc(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String idStr = RequestUtil.getString(request, "idStr");
		String modelCodeStr = RequestUtil.getString(request, "modelCodeStr");
		String[] idArr = idStr.split(",");
		String[] modelCodeArr = modelCodeStr.split(";");
		if(null != idArr && idArr.length > 0 && null != modelCodeArr && modelCodeArr.length == idArr.length) {
			//打印N张
			List<InvPartLocationModel> list = new ArrayList<InvPartLocationModel>();
			List<String> cfList =  new ArrayList<String>();
			InvPartLocationModel model = new InvPartLocationModel();
			for (int i = 0; i < idArr.length; i++) {
				model.setId(idArr[i]);
				InvPartLocationModel bean = invPartLocationManager.queryInvShelfPrintInfo(model);
				if (cfList.contains(bean.getPartNo()+bean.getStorage())) {
					continue;
				}
				cfList.add(bean.getPartNo()+bean.getStorage());
				//二维码
				StringBuffer qrCode = new StringBuffer();
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getPartNo());//零件号
				qrCode.append("#");
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getPartShortNo());//简号
				qrCode.append("#");
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getSupplierNo());//供应商代码
				qrCode.append("#");
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getShelfNo());//货架号
				qrCode.append("#");
				qrCode = InvPartLocationController.addEmptyStr(qrCode, bean.getStandardPackage());//收容数
				qrCode.append("#");
				qrCode.append("100");//最小在库量
				qrCode.append("#");
				qrCode.append("1000");//最大在库量
				qrCode.append("#");
				qrCode.append("4");//叠放层次
     			bean.setQrCode(MakeQrcodeImages.getQrCodeImage(qrCode.toString(), "120", "120"));
				//bean.setShelfImg(shelfImg);
     			//车型
     			bean.setModelCode(modelCodeArr[i]);
				list.add(bean);
			}
			JRDataSource jRDataSource;
			if (list.size() > 0) {
				jRDataSource = new JRBeanCollectionDataSource(list);
			} else {
				jRDataSource = new JREmptyDataSource();
			}
			String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator +"ireport" + File.separator + "inv" + File.separator 
					+ "INV_SHELF_LABEL_DOUBLE.jasper";
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;"); 
			try {
				InputStream file = new FileInputStream(filenurl);
				Map<String, Object> parameters = new HashMap<String, Object>();
				JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
				JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				writeResultMessage(response.getWriter(), "系统错误，请联系管理员", e.getMessage(),ResultMessage.FAIL);
			}
		}
	}
	
	
	/**
	 * 标签打印null换成""
	 * @param qrCode
	 * @param str
	 * @author dtp
	 * @return
	 */
	private static StringBuffer addEmptyStr(StringBuffer qrCode, String str) {
		if(StringUtils.isNullOrEmpty(str)) {
			qrCode.append("");
		}else {
			qrCode.append(str);
		}
		return qrCode;
	}
	
	/**
	 * @Description: 零件属地导入
	 * @param: @param file
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年2月16日
	 */
	@RequestMapping("importInvPartLocation")
	public void importInvPartLocation(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.INV_PARTLOCATION_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)) {
				invPartLocationManager.deleteImportTempDataByUUID(uuid);
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.INV_PARTLOCATION_IMPORT_UUID, uuid);
			Map<String,Object> rtn = invPartLocationManager.importInvPartLocation(file, uuid, RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), "失败", "", JSONObject.fromObject(rtn), ResultMessage.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), "系统错误,请联系管理员",e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * @Description: 查询临时表数据 
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@RequestMapping("queryImportTempPage")
	public @ResponseBody PageJson queryImportTempPage (HttpServletRequest request,HttpServletResponse reponse,
			@ModelAttribute("model") InvPartLocationModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<InvPartLocationModel> pageList = invPartLocationManager.queryImportTempPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: 导出临时表数据
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") InvPartLocationModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName =  "零件属地批量导入数据导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<InvPartLocationModel> pageList = invPartLocationManager.queryImportTempPage(model, page);
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
		List<InvPartLocationModel> list = invPartLocationManager.queryImportTempList(model);
		if(null != list) {
			String[] headers = {"变更前卸货代码", "变更前厂家编号", "变更前零件号", "变更前备货工程 ", "变更前台车号", "变更前物流库地址", 
					"变更前配送工程","变更前配送地址", "变更前工位号", "变更前货架号", "变更前工程深度", "变更前责任班组", 
					"变更后卸货代码", "变更后厂家编号", "变更后零件号", "变更后备货工程 ", "变更后台车号", "变更后物流库地址", 
					"变更后配送工程","变更后配送地址", "变更后工位号", "变更后货架号", "变更后工程深度", "变更后责任班组",
					"操作类型","车型", "生效日期", "失效日期",
					"导入状态", "检查结果", "校验信息"};
			String[] columns = {"oldUnloadPort","oldSupplierNo", "oldPartNo", "oldReparePerson", "oldCarpool","oldStorage", 
					"oldDistriPerson", "oldLocation", "oldStationCode", "oldShelfNo", "oldLocationNum",	"oldDeptNo",
					"newUnloadPort", "newSupplierNo", "newPartNo", "newReparePerson","newCarpool","newStorage", 
					"newDistriPerson", "newLocation","newStationCode", "newShelfNo","newLocationNum","newDeptNo",
					"operationType","modelCode","effStart","effEnd",
					"importStatusDesc", "checkResultDesc", "checkInfo"};	
			int[] widths = {60, 60, 100, 60, 60,60, 
					60, 60, 60, 60,60, 60,
					60, 60, 100,60, 60, 60, 
					60, 60, 60,60, 60, 60,
					60, 40,65, 65, 
					60, 60, 150};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
	}
	
	/**
	 * @Description: 将临时表数据导入正式表
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年2月17日
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") InvPartLocationModel model) throws IOException {
		ResultMessage message = null;
		try {
			//查询导入校验结果是否包含不通过
			int count = invPartLocationManager.queryIsExistsCheckResultFalse(model.getUuid());
			if(count > 0) {
				writeResultMessage(response.getWriter(), "存在校验结果不通过数据", ResultMessage.FAIL);
				return;
			}
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("uuid", model.getUuid());
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			paramMap.put("ipAddr", RequestUtil.getIpAddr(request));
			paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
			paramMap.put("lastModifiedUser", ContextUtil.getCurrentUser().getAccount());
			paramMap.put("lastModifiedIp", RequestUtil.getIpAddr(request));
			invPartLocationManager.insertImportData(paramMap);
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "执行失败", e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * @Description: 零件属地维护日志查询 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2019年2月25日
	 */
	@RequestMapping("queryInvPartLocationLogPage")
	public @ResponseBody PageJson queryInvPartLocationLogPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") InvPartLocationModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<InvPartLocationModel> pageList = invPartLocationManager.queryInvPartLocationLogPage(model, page);
		
		return new PageJson(pageList);
	}
	
	/**
	 * @Description:  属地维护日志导出
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月25日
	 */
	@RequestMapping("downloadInvPartLocationLog")
	public void downloadInvPartLocationLog(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") InvPartLocationModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "零件属地维护日志导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<InvPartLocationModel> pageList = invPartLocationManager.queryInvPartLocationLogPage(model, page);
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
		List<InvPartLocationModel> list = invPartLocationManager.queryInvPartLocationLogList(model);
		if(null != list) {
			String[] headers = {"卸货代码(前)", "厂家代码(前)", "零件号(前)", "备货工程(前)",
					"台车号(前)", "物流库地址(前)", "配送工程(前)", "配送地址(前)", "工位号(前)", 
					"货架号(前)", "工程深度(前)", "责任班组(前)",
					"卸货代码(后)", "厂家代码(后)", "零件号(后)", "备货工程(后)",
					"台车号(后)", "物流库地址(后)", "配送工程(后)", "配送地址(后)", "工位号(后)", 
					"货架号(后)", "工程深度(后)", "责任班组(后)",
					 "车型", "生效日期", "失效日期", "操作类型", "操作人","操作时间"};
			String[] columns = {"oldUnloadPort", "oldSupplierNo",  "oldPartNo", "oldReparePerson",
					"oldCarpool", "oldStorage", "oldDistriPerson", "oldLocation", "oldStationCode", 
					"oldShelfNo", "oldLocationNum", "oldDeptNo", 
					"newUnloadPort", "newSupplierNo",  "newPartNo", "newReparePerson",
					"newCarpool", "newStorage", "newDistriPerson", "newLocation", "newStationCode", 
					"newShelfNo", "newLocationNum", "newDeptNo",
					"modelCode","effStart", "effEnd","operationType","creationUser", "creationTime"};
			int[] widths = {80, 100, 100, 100, 
					80, 100, 100, 100, 80, 
					80, 100, 100, 
					80, 100, 100, 100, 
					80, 100, 100, 100, 80, 
					80, 100, 100,
					60, 100, 100, 60,60, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
	}
	
	
	
}
