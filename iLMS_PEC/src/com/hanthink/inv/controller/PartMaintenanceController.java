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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.hanthink.demo.controller.DemoController;
import com.hanthink.inv.manager.PartMaintenanceManager;
import com.hanthink.inv.model.PartMainTenanance;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;
import com.mysql.jdbc.StringUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/ter/parts")
public class PartMaintenanceController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(DemoController.class);
	@Resource
	private PartMaintenanceManager partMaintenanceManager;

	/**
	 * 属地零件数据分页查询 
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * 李兴辉
	 */
	@RequestMapping("getJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
			HttpServletResponse reponse,
			@ModelAttribute("model") PartMainTenanance model) throws Exception{
		DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
		System.out.println(model);
		PageList<PartMainTenanance> pageList = (PageList<PartMainTenanance>) partMaintenanceManager.queryPartMaintenanceForPage(model, p);

		return new PageJson(pageList);
	}

	/**
	 * 属地数据导出
	 * @param request
	 * @param response
	 * @param model
	 * 李兴辉
	 */
	@RequestMapping("downloadModel")
	public void downloadModel(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") PartMainTenanance model){
		String exportFileName = "零件属地维护信息" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		List<PartMainTenanance> list = partMaintenanceManager.queryexportList(model);
		if(null != list) {
			int curNum = list.size();
			if(0 == curNum){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
			int sysMaxNum_T = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000);
			if(curNum > sysMaxNum_T){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"卸货口", "厂家代码", "零件号", "简号", "备货工程",
					"台车号", "物流库地址", "配送工程", "落地地址", "工位号", 
					"货架号", "工程深度", "车型", "责任班组","生效日期", "失效日期"};
			String[] columns = {"unload_port", "supplier_no",  "part_no", "part_short_no", "prepare_person",
					"carpool", "storage", "distri_person", "location", "station_code", 
					"shelf_no", "location_num", "model_code", "dept_No", "eff_start", "eff_end"};
			int[] widths = {100, 100, 100, 100, 100,
					100, 100, 100, 100, 100, 
					100, 100, 100, 100, 100,100};
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
	@SuppressWarnings("unused")
	@RequestMapping("importPartMaintenanceTemp")
	public void importPartMaintenanceTemp(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
//			List<PartMainTenanance> queryPartMaintenanceImport = partMaintenanceManager.queryPartMaintenanceImport(null);
//			if (queryPartMaintenanceImport!= null && queryPartMaintenanceImport.size() > 0) {
//				writeResultMessage(response.getWriter(), "失败,存在已满足校验但未提交的数据，请先提交", ResultMessage.FAIL);
//				return;
//			}
			int count = 0;
			count = partMaintenanceManager.removePartMaintenanceTemp();
			Map<String,Object> rtn = partMaintenanceManager.importPartMaintenanceTemp(file);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "成功", ResultMessage.SUCCESS);
			}else{
				String object = (String) rtn.get("console");
				writeResultMessage(response.getWriter(), "失败," + object, ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), "导入失败，请联系系统管理员", ResultMessage.FAIL);
		}

	}

	/**
	 * 导入临时表数据提交到业务表
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 * 李兴辉
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") PartMainTenanance model) throws IOException {
		ResultMessage message = null;
		try {
			int queryImportFailCount = queryImportFailCount(model);
			if (queryImportFailCount >=1) {
				message = new ResultMessage(ResultMessage.FAIL, "执行失败","存在校验结果不通过的数据");
			} else {
				Map<String, Object> partMaintenanceImportData = partMaintenanceManager.PartMaintenanceImportData();
				Boolean object = (Boolean) partMaintenanceImportData.get("result");
				String mes = (String) partMaintenanceImportData.get("message");
				if (object) {
					message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
				} else {
					message = new ResultMessage(ResultMessage.FAIL, "执行失败",mes);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 	查询导入的临时数据
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * 李兴辉
	 */
	@RequestMapping("queryImportTempPage")
	public @ResponseBody PageJson queryImportTempPage(HttpServletRequest request,HttpServletResponse reponse,
			@ModelAttribute("model") PartMainTenanance model) {
		DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
		PageList<PartMainTenanance> pageList = partMaintenanceManager.queryPartMaintenanceTemp(model, p);
		return new PageJson(pageList);

	}


	/**
	 * 导入的临时数据导出
	 *
	 * @param request
	 * @param response
	 * @param model
	 * 李兴辉
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") PartMainTenanance model) {
		String exportFileName =  "属地零件导入" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<PartMainTenanance> pageList = partMaintenanceManager.queryPartMaintenanceTemp(model, page);
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
		List<PartMainTenanance> list = partMaintenanceManager.queryPartMaintenanceTemp(model);
		if(null != list) {
			String[] headers = {"变更前卸货口", "变更前厂家编号", "变更前零件号", "变更前备货工程 ", "变更前台车号", "变更前物流库地址", 
					"变更前配送工程","变更前配送地址", "变更前工位号", "变更前货架号", "变更前工程深度", "变更前责任班组", 
					"变更后卸货口", "变更后厂家编号", "变更后零件号", "变更后备货工程 ", "变更后台车号", "变更后物流库地址", 
					"变更后配送工程","变更后配送地址", "变更后工位号", "变更后货架号", "变更后工程深度", "变更后责任班组","操作类型","车型", "生效日期", "失效日期","校验信息","校验结果"};
			String[] columns = {"oldUnloadPort","oldSupplierNo", "oldPartNo", "oldReparePerson",
					"oldCarpool","oldStorage", "oldDistriPerson", "oldLocation", "oldStationCode",
					"oldShelfNo", "oldLocationNum",	"oldDeptNo","newUnloadPort", "newSupplierNo", "newPartNo", "newReparePerson","newCarpool",
					"newStorage", "newDistriPerson", "newLocation","newStationCode", "newShelfNo",
					"newLocationNum","newDeptNo","operationType","modelCode","effStart","effEnd",
					"importStatusDesc", "checkResultDesc", "checkInfo"};	
			int[] widths = {100, 100, 100, 100, 150,100, 100, 100, 100, 150,100, 100, 100, 100, 150,100, 100, 100, 100, 150,100, 100, 100, 100,
					100, 100,100, 100,100, 100, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}

	}
	
	/**
	 * 零件临时数据删除
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
		count = partMaintenanceManager.removePartMaintenanceTemp();
		ResultMessage message;
		message = new ResultMessage(ResultMessage.SUCCESS, "成功删除临时数据" + count + "条");
		writeResultMessage(reponse.getWriter(), message);
	}
	
	
	/**
	 * 查询验证不通过的数据是否存在
	 * @return
	 * Lxh
	 */
	public int queryImportFailCount(PartMainTenanance m) {
		int count =0;
		count = partMaintenanceManager.queryImportFailCount(m);
		return count;
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
		String[] idArr = idStr.split(",");
		if(null != idArr && idArr.length > 0) {
			//打印N张
			List<PartMainTenanance> list = new ArrayList<PartMainTenanance>();
			PartMainTenanance model = new PartMainTenanance();
			//红点图片
			//String pagePath = FileUtil.getClassesPath() + File.separator + "template" 
			//		+ File.separator + "page" + File.separator + "inv_shelf.jpg";
			//BufferedImage shelfImg = ImageIO.read(new FileInputStream(pagePath));
			for (int i = 0; i < idArr.length; i++) {
				model.setId(idArr[i]);
				PartMainTenanance bean = partMaintenanceManager.queryInvShelfPrintInfo(model);
				//二维码
				StringBuffer qrCode = new StringBuffer();
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getPartNo());//零件号
				qrCode.append("#");
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getPartShortNo());//简号
				qrCode.append("#");
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getSupplierNo());//供应商代码
				qrCode.append("#");
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getShelfNo());//货架号
				qrCode.append("#");
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getStandardPackage());//收容数
				qrCode.append("#");
				qrCode.append("100");//最小在库量
				qrCode.append("#");
				qrCode.append("1000");//最大在库量
				qrCode.append("#");
				qrCode.append("4");//叠放层次
     			bean.setQrCode(MakeQrcodeImages.getQrCodeImage(qrCode.toString(), "120", "120"));
				//bean.setShelfImg(shelfImg);
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
				writeResultMessage(response.getWriter(), "系统错误，请联系管理员", ResultMessage.FAIL);
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
		String[] idArr = idStr.split(",");
		if(null != idArr && idArr.length > 0) {
			//打印N张
			List<PartMainTenanance> list = new ArrayList<PartMainTenanance>();
			PartMainTenanance model = new PartMainTenanance();
			//红点图片
			//String pagePath = FileUtil.getClassesPath() + File.separator + "template" 
			//		+ File.separator + "page" + File.separator + "inv_shelf.jpg";
			//BufferedImage shelfImg = ImageIO.read(new FileInputStream(pagePath));
			for (int i = 0; i < idArr.length; i++) {
				model.setId(idArr[i]);
				PartMainTenanance bean = partMaintenanceManager.queryInvShelfPrintInfo(model);
				//二维码
				StringBuffer qrCode = new StringBuffer();
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getPartNo());//零件号
				qrCode.append("#");
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getPartShortNo());//简号
				qrCode.append("#");
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getSupplierNo());//供应商代码
				qrCode.append("#");
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getShelfNo());//货架号
				qrCode.append("#");
				qrCode = PartMaintenanceController.addEmptyStr(qrCode, bean.getStandardPackage());//收容数
				qrCode.append("#");
				qrCode.append("100");//最小在库量
				qrCode.append("#");
				qrCode.append("1000");//最大在库量
				qrCode.append("#");
				qrCode.append("4");//叠放层次
     			bean.setQrCode(MakeQrcodeImages.getQrCodeImage(qrCode.toString(), "120", "120"));
				//bean.setShelfImg(shelfImg);
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
				writeResultMessage(response.getWriter(), "系统错误，请联系管理员", ResultMessage.FAIL);
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
}
