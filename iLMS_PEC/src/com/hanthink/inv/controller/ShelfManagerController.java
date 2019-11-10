package com.hanthink.inv.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import com.hanthink.demo.controller.DemoController;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hanthink.inv.manager.ShelfManagerManager;
import com.hanthink.inv.model.ShelfManager;
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
import com.swetake.util.Qrcode;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/ter/shelves")
public class ShelfManagerController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(DemoController.class);
	@Resource
	private ShelfManagerManager shelfManagerManager;

	/**
	 * 分页查询货架数据
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * Lxh
	 */
	@RequestMapping("getJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
			HttpServletResponse reponse,
			@ModelAttribute("model") ShelfManager model) throws Exception{
		DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
		
		PageList<ShelfManager> pageList = (PageList<ShelfManager>) shelfManagerManager.queryShelfManagerForPage(model, p);

		return new PageJson(pageList);
	}

	/**
	 * 分页查询导入临时数据
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * Lxh
	 */
	@RequestMapping("getJsonTemp")
	public @ResponseBody PageJson curdlistJsonTemp(HttpServletRequest request,
			HttpServletResponse reponse,
			@ModelAttribute("model") ShelfManager model) throws Exception{
		DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));

		PageList<ShelfManager> pageList = (PageList<ShelfManager>) shelfManagerManager.queryShelfManagerForPageTemp(model, p);

		return new PageJson(pageList);
	}


	/**
	 * 下载导出货架数据
	 * @param request
	 * @param response
	 * @param model
	 * Lxh
	 */
	@RequestMapping("downloadModel")
	public void downloadModel(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") ShelfManager model)throws Exception{
		try {
			String exportFileName = "货架信息维护" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			List<ShelfManager> list = shelfManagerManager.queryexportList(model);
			
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

				String[] headers = {"零件号", "零件名称", "简号", "供应商代码", "供应商名称",
						"货架地址", "规格包装", "安全库存", "车型", "叠放层数", 
				"更新日期"};
				String[] columns = {"partNo", "partName",  "partShortNo", "supplierNo", "supplierName",
						"shelvesAddr", "standardPack", "safeStock", "carType", "stackLayers", 
				"modifiedTime"};
				int[] widths = {100, 100, 100, 100, 100,
						100, 100, 100, 100, 100, 
						100};
				if(curNum <= sysMaxNum) {
					ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
				}else {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}

//	@RequestMapping("Print")
//	public void shelvesPrint(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		String id = RequestUtil.getString(request, "id");
//		System.out.println(id + "**********************");
//		String[] idArr = id.split(",");
//		if(null != idArr ) {
//			//打印N张
//			List<ShelfManager> list = new ArrayList<ShelfManager>();
//			ShelfManager model = new ShelfManager();
//			for (int j = 0; j < idArr.length; j++) {
//				model.setId(idArr[j]);
//				ShelfManager printModel = shelfManagerManager.queryPrintModel(model);
//				list.add(printModel);
//				JRDataSource jRDataSource = new JRBeanCollectionDataSource(list);
//				String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
//						+ File.separator +"ireport" + File.separator + "demo" + File.separator + "MM_SHELVE_MANAGER.jasper";
//				InputStream file = new FileInputStream(filenurl);
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				// 获取二维码
//				String code = printModel.getPartNo() + "#" +  printModel.getPartShortNo() + "#" +
//						printModel.getSupplierNo() + "#" + printModel.getShelvesAddr() + "#" + 
//						printModel.getStandardPack() + "#" + printModel.getStackLayers();
//				BufferedImage image = createQrcode(code,'L','B' ,3);
//				parameters.put("QR_INFOS", image);
//				JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
//				response.setContentType("application/pdf");
//				response.setHeader("Content-disposition", "inline;");
//				OutputStream out = response.getOutputStream();
//				JasperExportManager.exportReportToPdfStream(jasperPrint, out);
//			}
//
//		}
//
//	}
	
	/**
	 * 货架数据打印
	 * @param request
	 * @param response
	 * @throws Exception
	 * Lxh
	 */
	@RequestMapping("Print")
	public void shelvesPrint(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String[] idArr = id.split(",");
		List<ShelfManager> detailList = null;
		if(null != idArr && idArr.length > 0) {
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
		
			for (int i = 0; i < idArr.length; i++) {
				ShelfManager model_q = new ShelfManager();
				//生成多个InputStream file,防止抛异常 
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
						+ File.separator +"ireport" + File.separator + "demo" + File.separator + "MM_SHELVE_MANAGER.jasper";
				InputStream file = new FileInputStream(filenurl);
				model_q.setId(idArr[i]);
				//查询订单明细
				detailList = shelfManagerManager.queryPrintModel(model_q);
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				if(null != detailList && detailList.size() > 0) {
					ShelfManager model = detailList.get(0);
					String code = model.getPartNo() + "#" +  model.getPartShortNo() + "#" +
							model.getSupplierNo() + "#" + model.getShelvesAddr() + "#" + 
							model.getStandardPack() + "#" + model.getStackLayers();
					BufferedImage image = createQrcode(code,'L','B' ,3);
					parameters.put("QR_INFOS", image);
					JRDataSource jRDataSource  = new JRBeanCollectionDataSource(detailList);
					JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters,
							jRDataSource);
					JasperPrintList.add(jasperPrint);
				}
				
			}
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;");
			JRPdfExporter exporter = new JRPdfExporter();
			OutputStream out = response.getOutputStream();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			exporter.exportReport();
		}
	}
	

	/**
	 * 二维码生成
	 * @param content
	 * @param errorCorrect
	 * @param mode
	 * @param version
	 * @return
	 * @throws IOException
	 * Lxh
	 */
	public static BufferedImage createQrcode(String content, char errorCorrect,
			char mode, int version) throws IOException {
		BufferedImage image =null;
		if (null == content || "".equals(content)) {

		} else {
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect(errorCorrect);
			qrcode.setQrcodeEncodeMode(mode);

			//qrcode.setQrcodeVersion(version);
			// 获取内容的字节数组，设置编码格式
			byte[] bytes = content.getBytes("UTF-8");
			// 图片尺寸,会根据version的变大，而变大，自己需要计算
			int imgSize =  67 + 12 * (version - 1);
			image = new BufferedImage(imgSize, imgSize,
					BufferedImage.TYPE_BYTE_BINARY);
			// 获取画笔
			Graphics2D gs = image.createGraphics();
			// 设置背景色 白色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);
			// 设定图像颜色 黑色
			gs.setColor(Color.BLACK);
			// 设置偏移量，不设置可能导致二维码生产错误(解析失败出错)
			int pixoff = 2;
			// 二维码输出
			if (bytes.length > 0 && bytes.length < 120) {
				boolean[][] s = qrcode.calQrcode(bytes);
				for (int i = 0; i < s.length; i++) {
					for (int j = 0; j < s.length; j++) {
						if (s[j][i]) {
							//注意j * 3 + pixoff, i * 3 + pixoff, 3, 3中的pixoff和3也会影响二维码像素，但是影响并不会很大，
							//二维码像素主要受version影响
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);//填充矩形区域
						}
					}
				}
			}
			gs.dispose();
			image.flush();
		}
		return image;
	}

	/**
	 * 货架数据新增
	 * @param request
	 * @param reponse
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping("save")
	public void insertModel(HttpServletRequest request,
			HttpServletResponse reponse,
			@RequestBody ShelfManager model) throws Exception {
		ResultMessage message;
		String id = model.getId();
		boolean flag;
		IUser currentUser = ContextUtil.getCurrentUser();
		model.setCreatBy(currentUser.getAccount());
		model.setFactoryCode(currentUser.getCurFactoryCode());
		if (StringUtil.isEmpty(id)) {
			if (StringUtil.isEmpty(model.getPartNo())) {
				ResultMessage messa = new ResultMessage(ResultMessage.FAIL, "零件号不能为空");
				writeResultMessage(reponse.getWriter(), messa);
				return;
			}
			if (StringUtil.isEmpty(model.getSupplierNo())) {
				ResultMessage messa = new ResultMessage(ResultMessage.FAIL, "供应商编号不能为空");
				writeResultMessage(reponse.getWriter(), messa);
				return;
			}
			if (StringUtil.isEmpty(model.getShelvesAddr())) {
				ResultMessage messa = new ResultMessage(ResultMessage.FAIL, "货架地址不能为空");
				writeResultMessage(reponse.getWriter(), messa);
				return;
			}
			
			String regex = "^([0-9])*";
			String safeStock = model.getSafeStock();//安全库存
			if (!StringUtil.isEmpty(safeStock)) {
				boolean  isCorrect = safeStock.trim().matches(regex);
				if (!isCorrect) {
					ResultMessage messa = new ResultMessage(ResultMessage.FAIL, "安全库存请输入正整数");
					writeResultMessage(reponse.getWriter(), messa);
					return;
				}
			}
			String stackLayers = model.getStackLayers();//叠放层数
			if (!StringUtil.isEmpty(stackLayers)) {
				boolean  isCorrect = stackLayers.trim().matches(regex);
				if (!isCorrect) {
					ResultMessage messa = new ResultMessage(ResultMessage.FAIL, "叠放层数请输入正整数");
					writeResultMessage(reponse.getWriter(), messa);
					return;
				}
			}
			
			boolean checkPartNoAndSupplierNoIsMaintain = shelfManagerManager.checkPartNoAndSupplierNoIsMaintain(model);
			if (!checkPartNoAndSupplierNoIsMaintain) {
				ResultMessage messa = new ResultMessage(ResultMessage.FAIL, "零件与供应商关系未维护");
				writeResultMessage(reponse.getWriter(), messa);
				return;
			}
			try {
				flag = shelfManagerManager.insertShelvesManager(model);
				if (flag) {
					message = new ResultMessage(ResultMessage.SUCCESS, "保存成功");
				} else {
					message = new ResultMessage(ResultMessage.FAIL, "保存失败");
				}
				writeResultMessage(reponse.getWriter(), message);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误,请联系管理员");
			}
		} else {
			boolean checkPartNoAndSupplierNoIsMaintain = shelfManagerManager.checkPartNoAndSupplierNoIsMaintain(model);
			if (!checkPartNoAndSupplierNoIsMaintain) {
				ResultMessage messa = new ResultMessage(ResultMessage.FAIL, "零件与供应商关系未维护");
				writeResultMessage(reponse.getWriter(), messa);
				return;
			}
			try {
				flag = shelfManagerManager.updateShelvesManager(model);
				if (flag) {
					message = new ResultMessage(ResultMessage.SUCCESS, "修改成功");
				} else {
					message = new ResultMessage(ResultMessage.FAIL, "修改失败");
				}
				writeResultMessage(reponse.getWriter(), message);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误,请联系管理人员");
			}
		}
	}

	/**
	 * 货架数据删除
	 * @param request
	 * @param reponse
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping("remove")
	public void removeModel(HttpServletRequest request,
			HttpServletResponse reponse,
			@ModelAttribute("data") ShelfManager model) throws Exception {
		String id = model.getId();
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			throw new Exception("系统错误,请联系管理员");
		} else {
			flag = shelfManagerManager.removeShelvesManager(model);
		}
		ResultMessage message;
		if (flag) {
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} else {
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(reponse.getWriter(), message);
	}


	/**
	 * 货架数据批量删除
	 * @param request
	 * @param response
	 * @throws Exception
	 * Lxh
	 */
	@RequestMapping("deleteByBatch")
	public void shelvesRemoveByBatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String[] idArr = id.split(",");
		int deleteCount = 0;
		if(null != idArr ) {
			ShelfManager model = new ShelfManager();
			for (int j = 0; j < idArr.length; j++) {
				model.setId(idArr[j]);
				boolean removeShelvesManager = shelfManagerManager.removeShelvesManager(model);
				if (removeShelvesManager) {
					deleteCount ++;
				}
			}
			ResultMessage message;
			if (deleteCount == idArr.length) {
				message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "删除失败");
			}
			writeResultMessage(response.getWriter(), message);
		}

	}

	/**
	 * 新增货架数据到临时表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 * Lxh
	 */
	@RequestMapping("importShelvesTemp")
	public void importShelvesTemp(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			int count = 0;
			count = shelfManagerManager.removeShelvesManagerTemp();
			Map<String, Object> rtn = shelfManagerManager.importShelfManagerTemp(file);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), "系统错误，请联系管理员", ResultMessage.FAIL);
		}

	}
	
	/**
	 * 查询导入临时数据
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * Lxh
	 */
	@RequestMapping("queryImportTempPage")
	public @ResponseBody PageJson queryImportTempPage(HttpServletRequest request,HttpServletResponse reponse,
			@ModelAttribute("model") ShelfManager model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		
		PageList<ShelfManager> pageList = shelfManagerManager.queryPartMaintenanceTemp(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * 临时数据导出
	 * @param request
	 * @param response
	 * @param model
	 * Lxh
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") ShelfManager model) {
		String exportFileName =  "货架标签导入" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<ShelfManager> pageList = shelfManagerManager.queryPartMaintenanceTemp(model, page);
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
		List<ShelfManager> list = shelfManagerManager.exportTempData(model);
		if(null != list) {
			String[] headers = {"零件号", "零件名", "简号", "供应商编码", "供应商名", "货架地址", 
					"规格包装","安全库存", "车型", "叠放层数","校验信息","校验结果"};
			String[] columns = {"partNo","partName", "partShortNo", "supplierNo",
					"supplierName","shelvesAddr", "standardPack", "safeStock", "carType",
					"stackLayers","checkResult","checkFlag"};	
			int[] widths = {100, 100, 100, 100, 150,100, 100, 100, 100, 150,100,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
		
	}
	
	/**
	 * 导入数据提交
	 * 
	 * Lxh
	 */
	@RequestMapping("isImport")
	public void ImportData(HttpServletRequest request,HttpServletResponse response) {
		try {
		Map<String, Object> importData = shelfManagerManager.ImportData();
		Boolean object = (Boolean) importData.get("result");
		ResultMessage message = null;
		if (object) {
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
		} else {
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
			writeResultMessage(response.getWriter(), message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询零件号
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * Lxh
	 */
	@RequestMapping("queryPartNo")
    public @ResponseBody List<ShelfManager> queryPartNo(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") ShelfManager model) throws Exception{
        
        List<ShelfManager> list = (List<ShelfManager>) shelfManagerManager.queryPartNo(model);
        return list;
    }
	
	/**
	 * 查询供应商和零件号对应关系是否存在
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * Lxh
	 */
	@RequestMapping("querySupplierNoByPartNo")
    public @ResponseBody List<ShelfManager> querySupplierNoByPartNo(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") ShelfManager model) throws Exception{
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        List<ShelfManager> list = (List<ShelfManager>) shelfManagerManager.querySupplierNoByPartNo(model);
        return list;
    }
	
	
	/**
	 * 货架临时数据删除
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
		count = shelfManagerManager.removeShelvesManagerTemp();
		ResultMessage message;
		message = new ResultMessage(ResultMessage.SUCCESS, "成功删除临时数据" + count + "条");
		writeResultMessage(reponse.getWriter(), message);
	}

}
