package com.hanthink.sw.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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




/**
 * 
 * @Desc		: Excel操作工具类
 * @FileName	: ExcelUtil.java
 * @CreateOn	: 2018年8月31日 上午10:35:40
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2018年8月31日		V1.0		ZUOSL		新建
 * 2018-10-22		V1.1		ZUOSL		修改导出错误提示方法
 */
public class ExcelBillUtil {
	public static final String EXCEL_XLS = ".xls";
    public static final String EXCEL_XLSX = ".xlsx";
    private static final int SHEET_SIZE_XLS = 65535; //65536;
    private static final int SHEET_SIZE_XLSX = 1000001;
    
    protected static Logger log = LoggerFactory.getLogger(ExcelBillUtil.class);
    
    /** Excel导出数量超过最大值错误代码 */
    public static final String DOWNLOAD_ERROR_CODE = "1";
    
    /** Excel导入检查结果-成功 */
    public static final String EXCEL_IMPCKRESULT_SUCCESS = "1";
    /** Excel导入检查结果-错误 */
    public static final String EXCEL_IMPCKRESULT_ERROR = "0";
    /** Excel导入检查结果-数据重复存在 */
    public static final String EXCEL_IMPCKRESULT_EXIST = "2";
    
    /** Excel导入状态-已导入 */
    public static final String EXCEL_IMPSTATUS_YES = "1";
    /** Excel导入状态-未导入 */
    public static final String EXCEL_IMPSTATUS_NO = "0";
    
    
    /**
     * Excel导出操作  
     * @param excelExtName excel扩展名 (.xls/.xlsx)
     * @param response
     * @param exportFileName 导出文件名
     * @param VOList 需导出的数据
     * @param headers 表头字段数据 
     * @param widths 宽度
     * @param columns VO属性字段
     * @author zuosl  2015-9-15
     */
	public static void exportExcel(String excelExtName, HttpServletRequest request, HttpServletResponse response,
			String exportFileName, List<?> VOList, 
			String[] headers, int[] widths, String[] columns) {
		if(null == VOList || 0 >= VOList.size()){
			return;
		}
		
		if(null != excelExtName){
			excelExtName = excelExtName.toLowerCase();
		}
		
		if(ExcelBillUtil.EXCEL_XLS.equals(excelExtName)){
			exportFileName = exportFileName + EXCEL_XLS;
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
			
			HSSFWorkbook workbook = new HSSFWorkbook();
	        
	        HSSFPalette palette = workbook.getCustomPalette();  
//	        palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));
	        palette.setColorAtIndex((short) 50, (byte)(0xc0), (byte)(0xc0), (byte)(0xc0));

	        //表头样式
	        HSSFCellStyle headStyle = workbook.createCellStyle();
	        headStyle.setFillForegroundColor((short) 50);
	        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        headStyle.setBorderRight(CellStyle.BORDER_THIN);
	        headStyle.setBorderTop(CellStyle.BORDER_THIN);
	        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        /**
	         * update by luoxianqin
	         * 设置单元格内容自动换行
	         * 2019-07-20
	         */
	        headStyle.setWrapText(true);

	        //表头字体
	        HSSFFont headFont = workbook.createFont();
	        headFont.setFontHeightInPoints((short) 11);
	        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        headStyle.setFont(headFont);

	        //内容样式
	        HSSFCellStyle columnStyle = workbook.createCellStyle();
//	        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
//	        columnStyle.setFillPattern(CellStyle.NO_FILL);//(HSSFCellStyle.SOLID_FOREGROUND);
	        columnStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        columnStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        columnStyle.setBorderRight(CellStyle.BORDER_THIN);
	        columnStyle.setBorderTop(CellStyle.BORDER_THIN);
	        columnStyle.setAlignment(CellStyle.ALIGN_LEFT);
	        columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

	        //内容字体
	        HSSFFont columnFont = workbook.createFont();
	        columnFont.setFontHeightInPoints((short) 11);
	        columnFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
	        columnStyle.setFont(columnFont);

	        //导出修改，数据量超过SHEET_SIZE_XLS行的建立新的sheet 
	        int dataSize = VOList.size();
	        int sheetCount = dataSize % (SHEET_SIZE_XLS-1) == 0 ? dataSize / (SHEET_SIZE_XLS-1) : dataSize / (SHEET_SIZE_XLS-1) + 1;
	        for(int sheetNum = 0; sheetNum < sheetCount; sheetNum ++){
	        	HSSFSheet sheet = workbook.createSheet();
	        	
	        	//表头
	        	HSSFRow row = sheet.createRow(0);
	        	
	        	row.setHeight((short) 600);
	        	/**
	        	 * update by luoxianqin 
	        	 * 设置行高
	        	 * 2019-07-20
	        	 */
	        	row.setHeightInPoints(45F);
	        	for (int i = 0; i < headers.length; i++) {
	        		HSSFCell cell = row.createCell(i);
	        		cell.setCellStyle(headStyle);
	        		HSSFRichTextString text = new HSSFRichTextString(headers[i]);
	        		cell.setCellValue(text);
	        		sheet.setColumnWidth(i, widths[i] * 45);
	        	}
	        	
	        	//内容
	        	int rowNum = 0;
	        	for (int i = sheetNum*(SHEET_SIZE_XLS-1); i < (sheetNum+1)*(SHEET_SIZE_XLS-1) && i < dataSize; i++) {
	        		Object vo = VOList.get(i);
	        		row = sheet.createRow( ++ rowNum);
	        		row.setHeight((short) 360);
	        		
	        		for (int k = 0; k < columns.length; k++) {
	        			HSSFCell cell = row.createCell(k);
	        			cell.setCellStyle(columnStyle);
	        			
	        			Class<? extends Object> voClass = vo.getClass();
						
						try {
							Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
//							
							//可以根据需要增加导出的数据格式，只是要确保VO的类型与导出的类型一致
							Object cellValue = getMethod.invoke(vo);
							if(cellValue instanceof Integer){
								cell.setCellValue(((Integer)cellValue).intValue());
							}else if(cellValue instanceof Long ){
								cell.setCellValue(((Long)cellValue).intValue());
							}else if(cellValue == null ){
								cell.setCellValue("");
							}else{
								cell.setCellValue(String.valueOf(cellValue));
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.toString());
							exportException(e, request, response);
							return;
						}
	        			
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
		}else{
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
	        headStyle.setWrapText(true);
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
	        	/**
	        	 * update by luoxianqin
	        	 * 设置表头行高为600
	        	 */
//	        	row.setHeight((short) 360);
	        	row.setHeight((short)800);
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
	        		Object vo = VOList.get(i);
	        		row = sheet.createRow( ++ rowNum);
	        		row.setHeight((short) 360);
	        		
	        		for (int k = 0; k < columns.length; k++) {
	        			XSSFCell cell = row.createCell(k);
	        			cell.setCellStyle(columnStyle);
	        			
	        			Class<? extends Object> voClass = vo.getClass();
	        			String textValue = null;
						try {
							Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
							textValue = null == getMethod.invoke(vo) ? "" : String.valueOf(getMethod.invoke(vo));
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
	 * Excel导入数据（XLS）
	 * 支持的VO属性全为String
	 * @param is 文件输入流
	 * @param columns
	 * @param startRow
	 * @param startColumn
	 * @return
	 * @throws IOException
	 * @author zuosl  2015-9-16
	 */
	public static List<?> importExcelXLS(Object vo, InputStream is, String[] columns, 
			int startRow, int startColumn) throws Exception {

		List<Object> voList = new ArrayList<Object>();
		
        // 循环工作表Sheet
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet == null) {
           return voList;
        }

        // 循环行Row
        for (int rowNum = startRow; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }

            // 循环列Cell
            Class<? extends Object> voClass = vo.getClass();
            Object tempVO = voClass.newInstance();
            for (int cellNum = startColumn; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
            	//超过字段数
            	if (cellNum >= (columns.length + startColumn)) {
            		break;
            	}
            	
            	HSSFCell hssfCell = hssfRow.getCell(cellNum);
            	if (hssfCell == null) {
            		continue;
            	}
            	
            	int k = cellNum - startColumn;
            	String cellVal = getValue(hssfCell);
                if(null != cellVal){
                	cellVal = cellVal.trim();
                }
            	Method setMethod = voClass.getMethod("set"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1), String.class);
            	setMethod.invoke(tempVO, cellVal);
            }
            voList.add(tempVO);
			
        }
		return voList;
        
    }

	/**
	 * 获取单元格值
	 * @param hssfCell
	 * @return
	 * @author zuosl  2015-9-16
	 */
    private static String getValue(HSSFCell hssfCell) {
        switch (hssfCell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:{
        	if(DateUtil.isCellDateFormatted(hssfCell)){				
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
        		return sdf.format(DateUtil.getJavaDate(hssfCell.getNumericCellValue())).toString();			
        	}
//        	return String.valueOf(hssfCell.getNumericCellValue());
        	return new DecimalFormat("#.######").format(hssfCell.getNumericCellValue());
        }
        
        case Cell.CELL_TYPE_STRING:
            return String.valueOf(hssfCell.getStringCellValue());
        
        case Cell.CELL_TYPE_FORMULA:
            return String.valueOf(hssfCell.getNumericCellValue());
            
        case Cell.CELL_TYPE_BOOLEAN:
            return String.valueOf(hssfCell.getBooleanCellValue());
        
        default:
            return "";
        } 
    }
    


    /**
     * 读取Excel数据  （XLSX）
     * 支持的VO属性全为String
     * @param vo
     * @param is
     * @param columns
     * @param startRow
     * @param startColumn
     * @return
     * @author zuosl  2015-11-13
     * @throws IOException 
     */
	public static List<?> importExcelXLSX(Object vo, InputStream is, String[] columns, 
			int startRow, int startColumn) throws IOException {
		List<Object> voList = new ArrayList<Object>();
		
        // 获取工作表Sheet
		Workbook workbook = new XSSFWorkbook(is);
        
		//获取sheet
    	Sheet sheet = workbook.getSheetAt(0);
        if (null == sheet) {
           return voList;
        }
        
     // 循环行Row
        for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
        	Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            
            //循环列Cell
            Class<? extends Object> voClass = vo.getClass();
			try {
				Object tempVO = voClass.newInstance();
				for (int cellNum = startColumn; cellNum <= row.getLastCellNum(); cellNum++) {
					//超过字段数
					if (cellNum >= (columns.length + startColumn)) {
						break;
					}
					
					Cell cell = row.getCell(cellNum);
	                if (null == cell) {
	                    continue;
	                }
	                
	                int k = cellNum - startColumn;
					Method setMethod = voClass.getMethod("set"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1), String.class);
					setMethod.invoke(tempVO, getValue(cell, null));
				}
				voList.add(tempVO);
			}catch(Exception e) {
				e.printStackTrace();
			}
        	
        }
		
		
		return voList;
	}


	/**
	 * 读取Excel单元格值
	 * @param cell
	 * @return
	 * @author zuosl  2015-11-13
	 */
	public static String getValue(Cell cell, String dateFormat) {
		switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:{
        	if(DateUtil.isCellDateFormatted(cell)){				
        		SimpleDateFormat sdf = null;
        		if(null != dateFormat && !"".equals(dateFormat)){
        			sdf = new SimpleDateFormat(dateFormat);
        		}else{
        			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		}
        		return sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();			
        	}
//        	return String.valueOf(hssfCell.getNumericCellValue());
        	return new DecimalFormat("#.######").format(cell.getNumericCellValue());
        }
        
        case Cell.CELL_TYPE_STRING:
            return String.valueOf(cell.getStringCellValue());
        
        case Cell.CELL_TYPE_FORMULA:
            return String.valueOf(cell.getNumericCellValue());
            
        case Cell.CELL_TYPE_BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        
        default:
            return "";
        } 
	}


	/**
	 * 导出excel数量超过系统所允许最大值
	 * @param sysMaxNum
	 * @author ZUOSL	
	 * @param request 
	 * @param response 
	 * @DATE	2018年9月3日 下午6:10:30
	 */
	public static void exportNumError(int sysMaxNum, HttpServletRequest request, HttpServletResponse response) {
		try {
//			response.sendRedirect(request.getContextPath() + "/pub/downloadError?code=0&param=" + sysMaxNum);
			
			downloadFileError(request, response, "超过系统最大允许导出条数【" + sysMaxNum + "】");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}


	/**
	 * 没有可导出数据提示
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年9月4日 下午12:14:24
	 */
	public static void exportNoDataError(HttpServletRequest request, HttpServletResponse response) {
		try {
//			response.sendRedirect(request.getContextPath() + "/pub/downloadError?code=1");
			
			downloadFileError(request, response, "没有可导出的数据！");
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
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
//			response.sendRedirect(request.getContextPath() + "/pub/downloadError?code=9&param=" + e.toString());
			
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
    

	/**
	 * 根据单元格列的字母序号获取数字索引序号（只支持最多两位字母，如AB）
	 * @param column 如 K，AK 
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午10:25:49
	 */
	public static int getColumnIndex(String column){
		if(null != column && column.length() <= 2){
			column = column.toUpperCase();
			if(1 == column.length()){
				return Integer.valueOf(column.charAt(0)) - Integer.valueOf('A');
			}else if(2 == column.length()){
				return (Integer.valueOf(column.charAt(0)) - Integer.valueOf('A') + 1)*26 
						+ (Integer.valueOf(column.charAt(1)) - Integer.valueOf('A'));
			}
		}else{
			new RuntimeException("getColumnIndex Error！");
		}
		return 0;
	}
	
	/**
	 * 获取Excel单元格行序号的索引序号
	 * @param rownum
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午10:40:18
	 */
	public static int getRowIndex(int rownum){
		return rownum - 1;
	}
}
