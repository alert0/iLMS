package com.hanthink.jit.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.jit.manager.JitInvenCompManager;
import com.hanthink.jit.model.JitInvenCompModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONObject;

/**
 * @ClassName: JitInvenCompController
 * @Description: 拉动库存对比
 * @author dtp
 * @date 2018年11月3日
 */
@Controller
@RequestMapping("/jit/jitInvenComp")
public class JitInvenCompController extends GenericController {
	
	public static final String EXCEL_XLS = ".xls";
    public static final String EXCEL_XLSX = ".xlsx";
    private static final int SHEET_SIZE_XLS = 65535; //65536;
    private static final int SHEET_SIZE_XLSX = 1000001;
	
	private static Logger log = LoggerFactory.getLogger(JitInvenCompController.class);
	
	@Resource
	private JitInvenCompManager jitInvenCompManager;
	
	
	/**
	 * @Description: 拉动库存对比excel批量导入
	 * @param: @param file
	 * @param: @param request
	 * @param: @param response
	 * @param: @throws IOException    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@RequestMapping("importJitInvenComp")
	public void importJitInvenComp(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.JIT_INVENCOMP_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)) {
				//根据uuid删除导入临时数据 
				jitInvenCompManager.deleteImportTempDataByUUID(uuid);
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.JIT_INVENCOMP_IMPORT_UUID, uuid);
			Map<String,Object> rtn = jitInvenCompManager.importJitInvenComp(file, uuid, RequestUtil.getIpAddr(request));
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
	 * @Description: 查询导入excel数据
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@RequestMapping("queryImportTempPage")
	public @ResponseBody PageJson queryImportTempPage(HttpServletRequest request,HttpServletResponse reponse,
			@ModelAttribute("model") JitInvenCompModel model) {
		if(!StringUtils.isNullOrEmpty(model.getUuid())) {
			model.setImpUuid(model.getUuid());
		}
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInvenCompModel> pageList = jitInvenCompManager.queryImportTempPage(model, page);
		return new PageJson(pageList);	
	}
	
	/**
	 * @Description: 执行拉动库存对比计算
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月5日
	 */
	@RequestMapping("isReckon")
	public void isReckon(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitInvenCompModel model) throws IOException {
		ResultMessage message = null;
		//查询校验结果是否包含不通过
		int count = jitInvenCompManager.queryIsExistsCheckResultFalse(model.getUuid());
		if(count > 0) {
			writeResultMessage(response.getWriter(), "存在校验结果不通过数据", ResultMessage.FAIL);
			return;
		}
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			//前端传参uuid
			paramMap.put("impUuid", model.getUuid());
			paramMap.put("uuid", model.getUuid());
			paramMap.put("result", "");
			paramMap.put("errMsg", "");
			//2018-11-06存储未完成
			jitInvenCompManager.isReckon(paramMap);
			message = new ResultMessage(ResultMessage.SUCCESS, "执行推算成功");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.SUCCESS, "系统错误,请联系管理员");
			log.error(e.toString());
		}
		writeResultMessage(response.getWriter(), message);
		
	}
	
	/**
	 * @Description: 导出拉动库存对比计算excel   
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@RequestMapping("downloadJitInvenComp")
	public void downloadJitInvenComp(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitInvenCompModel model) {
		if(!StringUtils.isNullOrEmpty(model.getUuid())) {
			model.setImpUuid(model.getUuid());
		}
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "拉动库存对比" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInvenCompModel> pageList = jitInvenCompManager.queryImportTempPage(model, page);
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
		List<JitInvenCompModel> list = jitInvenCompManager.queryImportTempList(model);
		if(null != list) {
			String[] headers = {"推算状态", "校验信息", "车间", "信息点", "零件号",
					"简号", "零件名称", "盘点截止VIN", "下一个到货批次进度", 
					"当前已推算截止VIN", "当前已推算最新过点批次进度", "盘点数量(a)",
					"安全库存(b)", "计划到货数量(c)", "装配需求(d)", "库存差异(e=a+c-b-d)"};
			String[] columns = {"checkResultDesc", "checkInfo", "workcenterDesc", "planCodeDesc", "partNo",
					"partShortNo", "partName", "vin", "checkArrProSeqno", 
					"checkCalVin", "checkCalKbProSeqno", "currInventory", 
					"safetyInventory", "checkPlanQty", "checkAssemblyQty", "diff"};
			int[] widths = {80, 80, 60, 70, 100,
					80, 100, 120, 120,
					120, 150, 100,
					100, 120, 100, 130};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			
		}
		
	}
	
	/**
	 * @Description: 查询计划差异
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年4月18日
	 */
	@RequestMapping("queryJitPlanDiff")
	public @ResponseBody PageJson queryJitPlanDiff(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") JitInvenCompModel model_q) throws IOException {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		String workcenter = model_q.getWorkcenter();
		JitInvenCompModel model = new JitInvenCompModel();
		model.setWorkcenter(workcenter);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<JitInvenCompModel> list_batch = jitInvenCompManager.queryJitCurBatchByWorkcenter(model);
		List<Integer> list_curbatch = new ArrayList<Integer>();
		if(null != list_batch && list_batch.size() > 0) {
			for (JitInvenCompModel mod : list_batch) {
				list_curbatch.add(Integer.valueOf(mod.getCurBatch()));
			}
			Collections.sort(list_curbatch);
			//拼接动态列
			StringBuffer sbf = new StringBuffer();
			for (int i = 0; i < list_curbatch.size(); i++) {
				if(i < list_curbatch.size() - 1) {
					sbf.append(list_curbatch.get(i));
					sbf.append(" PO");
					sbf.append(list_curbatch.get(i));
					sbf.append(", ");
				}else {
					sbf.append(list_curbatch.get(i));
					sbf.append(" PO");
					sbf.append(list_curbatch.get(i));
					sbf.append(" ");
				}
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			param.put("workcenter", workcenter);
			param.put("planCode", model_q.getPlanCode());
			param.put("partNo", model_q.getPartNo());
			param.put("partShortNo", model_q.getPartShortNo());
			param.put("columnSql", sbf.toString());
	        PageList<Map<String, Object>> pageList = jitInvenCompManager.queryJitPlanDiffPage(param, page);
	        return new PageJson(pageList);
		}else {
			writeResultMessage(response.getWriter(), "系统错误,请联系管理员", "根据车间最小,最大批次信息获取失败",ResultMessage.FAIL);
		}
		return null;
	}
	
	
	/**
	 * @Description: 查询计划差异
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年4月18日
	 */
	@RequestMapping("queryJitCurBatch")
	public @ResponseBody List<Integer> queryJitCurBatch(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") JitInvenCompModel model_q) throws IOException {
		String workcenter = model_q.getWorkcenter();
		JitInvenCompModel model = new JitInvenCompModel();
		model.setWorkcenter(workcenter);
		List<JitInvenCompModel> list_batch = jitInvenCompManager.queryJitCurBatchByWorkcenter(model);
		List<Integer> list_curbatch = new ArrayList<Integer>();
		if(null != list_batch && list_batch.size() > 0) {
			for (JitInvenCompModel mod : list_batch) {
				list_curbatch.add(Integer.valueOf(mod.getCurBatch()));
			}
			Collections.sort(list_curbatch);
		}else {
			writeResultMessage(response.getWriter(), "系统错误,请联系管理员", "根据车间获取批次信息失败",ResultMessage.FAIL);
		}
		return list_curbatch;
	}
	
	/**
	 * @Description:   
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月19日
	 */
	@RequestMapping("downloadJitPlanDiff")
	public void downloadJitPlanDiff(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") JitInvenCompModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<JitInvenCompModel> list_batch = jitInvenCompManager.queryJitCurBatchByWorkcenter(model);
		List<Integer> list_curbatch = new ArrayList<Integer>();
		for (JitInvenCompModel mod : list_batch) {
			list_curbatch.add(Integer.valueOf(mod.getCurBatch()));
		}
		Collections.sort(list_curbatch);
		//拼接动态列
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < list_curbatch.size(); i++) {
			if(i < list_curbatch.size() - 1) {
				sbf.append(list_curbatch.get(i));
				sbf.append(" PO");
				sbf.append(list_curbatch.get(i));
				sbf.append(", ");
			}else {
				sbf.append(list_curbatch.get(i));
				sbf.append(" PO");
				sbf.append(list_curbatch.get(i));
				sbf.append(" ");
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("workcenter", model.getWorkcenter());
		param.put("planCode", model.getPlanCode());
		param.put("partNo", model.getPartNo());
		param.put("partShortNo", model.getPartShortNo());
		param.put("columnSql", sbf.toString());
		String exportFileName = "拉动计划差异" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        List<Map<String, Object>> list = jitInvenCompManager.queryJitPlanDiffList(param);
        if(null != list) {
        	//String[] headers = new String[];
        	String[] headers = new String[4+list_curbatch.size()];
        	headers[0] = "车间";
        	headers[1] = "信息点";
        	headers[2] = "零件号";
        	headers[3] = "简号";
        	for (int i = 4; i < list_curbatch.size() + 4; i++) {
        		headers[i] = list_curbatch.get(i-4)+"";
			} 
        	String[] columns = new String[4+list_curbatch.size()];
        	columns[0] = "WORKCENTERDESC";
        	columns[1] = "PLANCODEDESC";
        	columns[2] = "PART_NO";
        	columns[3] = "PARTSHORTNO";
        	for (int i = 4; i < list_curbatch.size() + 4; i++) {
        		columns[i] = "PO" + list_curbatch.get(i-4);
			}
        	int[] widths = new int[4+list_curbatch.size()];
        	for (int i = 0; i < widths.length; i++) {
        		widths[i] = 65;
			}
        	widths[2] = 120;
        	JitInvenCompController.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
		
	}

	/**
	 * @Description: 导出拉动计划差异excel  
	 * @param: @param excelXlsx
	 * @param: @param request
	 * @param: @param response
	 * @param: @param exportFileName
	 * @param: @param list
	 * @param: @param headers
	 * @param: @param widths
	 * @param: @param columns    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月21日
	 */
	private static void exportExcel(String excelExtName, HttpServletRequest request, HttpServletResponse response,
			String exportFileName, List<?> VOList, 
			String[] headers, int[] widths, String[] columns) {
		if(null == VOList || 0 >= VOList.size()){
			return;
		}
		
		if(null != excelExtName){
			excelExtName = excelExtName.toLowerCase();
		}
		if(true) {
			//导出07 后版本
			exportFileName = exportFileName + EXCEL_XLSX;
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			String downName = null;
        	try {
        		if (request.getHeader("user-agent").toLowerCase().contains("msie")
        				|| request.getHeader("user-agent").toLowerCase().contains("like gecko") ) {
				downName = URLEncoder.encode(exportFileName, "UTF-8");
        	}else{
        		downName = new String(exportFileName.getBytes("UTF-8"), "ISO_8859_1");
        	}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				log.error(e.toString());
				exportException(e, request, response);
				return;
			}
			response.setHeader("Content-disposition", "attachment; filename=" + downName);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			//表头样式
	        XSSFCellStyle headStyle = workbook.createCellStyle();
	        XSSFColor color = new XSSFColor(new byte[] {(byte)(0xc0), (byte)(0xc0), (byte)(0xc0)});
	        headStyle.setFillForegroundColor(color);
	        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        headStyle.setBorderRight(CellStyle.BORDER_THIN);
	        headStyle.setBorderTop(CellStyle.BORDER_THIN);
	        headStyle.setAlignment(CellStyle.ALIGN_CENTER);

	        //表头字体
	        XSSFFont headFont = workbook.createFont();
	        headFont.setFontHeightInPoints((short) 11);
	        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        headStyle.setFont(headFont);

	        //内容样式
	        XSSFCellStyle columnStyle = workbook.createCellStyle();
	        columnStyle.setFillPattern(CellStyle.NO_FILL);//(HSSFCellStyle.SOLID_FOREGROUND);
	        columnStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        columnStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        columnStyle.setBorderRight(CellStyle.BORDER_THIN);
	        columnStyle.setBorderTop(CellStyle.BORDER_THIN);
	        columnStyle.setAlignment(CellStyle.ALIGN_LEFT);
	        columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

	        //内容字体
	        XSSFFont columnFont = workbook.createFont();
	        columnFont.setFontHeightInPoints((short) 11);
	        columnFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
	        columnStyle.setFont(columnFont);

	        //导出修改，数据量超过SHEET_SIZE_XLSX行的建立新的sheet 
	        int dataSize = VOList.size();
	        int sheetCount = dataSize % (SHEET_SIZE_XLSX-1) == 0 ? dataSize / (SHEET_SIZE_XLSX-1) : dataSize / (SHEET_SIZE_XLSX-1) + 1;
	        for(int sheetNum = 0; sheetNum < sheetCount; sheetNum ++){
	        	XSSFSheet sheet = workbook.createSheet();
	        	
	        	//表头
	        	XSSFRow row = sheet.createRow(0);
	        	row.setHeight((short) 360);
	        	for (int i = 0; i < headers.length; i++) {
	        		XSSFCell cell = row.createCell(i);
	        		cell.setCellStyle(headStyle);
	        		XSSFRichTextString text = new XSSFRichTextString(headers[i]);
	        		cell.setCellValue(text);
	        		sheet.setColumnWidth(i, widths[i] * 45);
	        	}
	        	
	        	//内容 
	        	int rowNum = 0;
	        	for (int i = sheetNum*(SHEET_SIZE_XLSX-1); i < (sheetNum+1)*(SHEET_SIZE_XLSX-1) && i < dataSize; i++) {
	        		Map<String, Object> vo = (Map<String, Object>) VOList.get(i);
	        		row = sheet.createRow( ++ rowNum);
	        		row.setHeight((short) 360);
	        		
	        		for (int k = 0; k < columns.length; k++) {
	        			XSSFCell cell = row.createCell(k);
	        			cell.setCellStyle(columnStyle);
	        			
	        			Class<? extends Object> voClass = vo.getClass();
	        			String textValue = null;
						try {
							//Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
							//textValue = null == getMethod.invoke(vo) ? "" : String.valueOf(getMethod.invoke(vo));
							textValue = null == String.valueOf(vo.get(columns[k])) ? "" : String.valueOf(vo.get(columns[k])); 
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.toString());
							exportException(e, request, response);
							return;
						}
	        			
	        			XSSFRichTextString text = new XSSFRichTextString(null == textValue ? "" : textValue);
	        			cell.setCellValue(text);
	        		}
	        	}
	        	
	        }
	        
	        try {
				OutputStream out = response.getOutputStream();
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.toString());
			}
			
		}
		
	}
	
	/**
	 * 文件下载异常跳转提示
	 * @param e
	 * @author ZUOSL	
	 * @DATE	2018年9月7日 上午9:14:39
	 */
	public static void exportException(Exception e, HttpServletRequest request, HttpServletResponse response) {
		try {
			downloadFileError(request, response, "文件下载异常。[" + e.getMessage() + "]");
		} catch (IOException e1) {
			e1.printStackTrace();
			log.error(e1.toString());
		}
	}
	
	/**
	 * 文件下载 错误信息提示
	 * @param request
	 * @param response
	 * @param errMsg 错误提示信息
	 * @throws IOException
	 * @author ZUOSL	
	 * @DATE	2018年10月22日 下午4:09:44
	 */
	public static void downloadFileError(HttpServletRequest request, HttpServletResponse response, String errMsg) throws IOException {
		if(null != errMsg){
			if(-1 < errMsg.indexOf("\n")){
				errMsg = errMsg.replaceAll("\n", " ");
			}
			if(-1 < errMsg.indexOf("\t")){
				errMsg = errMsg.replaceAll("\t", " ");
			}
			if(-1 < errMsg.indexOf("\r")){
				errMsg = errMsg.replaceAll("\r", " ");
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		StringBuffer sbf = new StringBuffer();
		sbf.append("<!DOCTYPE html>");
		sbf.append("<head><meta charset=\"UTF-8\"><title>下载失败</title></head>");
		sbf.append("<body><script>");
		sbf.append("var tmpvue = new window.parent.Vue(); tmpvue.$message({message:\"");
		sbf.append(null == errMsg ? "文件下载失败啦!" : errMsg);
		sbf.append("\",type:\"error\"});");
		sbf.append("</script></body>");
		sbf.append("</html>");
		writer.print(sbf.toString());
	}
	
}
