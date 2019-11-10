package com.hanthink.gps.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hanthink.gps.jit.vo.JitPartVO;
import com.hanthink.gps.pub.service.PubSysParamServer;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;




/**
 * Excel操作工具类
 * @author zuosl 2015-9-15
 * 
 * @modified by anMin 2015-11-18 数据不转String导出；测试一次性导出xlsx18W耗时3~4min
 * 
 */

public class ExcelUtil {
	private static final String EXCEL_XLS = ".xls";
    private static final String EXCEL_XLSX = ".xlsx";
    private static final int SHEET_SIZE_XLS = 65535; //65536;
    private static final int SHEET_SIZE_XLSX = 100001;
    
    /** Excel导出数量超过最大值错误代码 */
    public static final String DOWNLOAD_ERROR_CODE = "1";
    
    
    /**
     * Excel导出操作  
     * @param is03Excel 是否导出03版本的Excel，否则导出07版本
     * @param response
     * @param exportFileName 导出文件名
     * @param VOList 需导出的数据
     * @param headers 表头字段数据 
     * @param widths 宽度
     * @param columns VO属性字段
     * @author zuosl  2015-9-15
     */
	@SuppressWarnings("unchecked")
	public static void exportExcel(boolean is03Excel, HttpServletResponse response,
			String exportFileName, List<?> VOList, 
			String[] headers, int[] widths, String[] columns) {
		if(null == VOList || 0 >= VOList.size()){
			return;
		}
		
		if(is03Excel){
			exportFileName = exportFileName + EXCEL_XLS;
//			response.setContentType("application/octet-stream");
//			response.setHeader("Content-Disposition", "attachment; filename=" + exportFileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			try {
				response.setHeader("Content-disposition", "attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
			HSSFWorkbook workbook = new HSSFWorkbook();
	        
	        HSSFPalette palette = workbook.getCustomPalette();  
//	        palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));
	        palette.setColorAtIndex((short) 50, (byte)(0xc0), (byte)(0xc0), (byte)(0xc0));

	        //表头样式
	        HSSFCellStyle headStyle = workbook.createCellStyle();
	        headStyle.setFillForegroundColor((short) 50);
	        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	        //表头字体
	        HSSFFont headFont = workbook.createFont();
	        headFont.setFontHeightInPoints((short) 11);
	        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headStyle.setFont(headFont);

	        //内容样式
	        HSSFCellStyle columnStyle = workbook.createCellStyle();
//	        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
//	        columnStyle.setFillPattern(CellStyle.NO_FILL);//(HSSFCellStyle.SOLID_FOREGROUND);
	        columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

	        //内容字体
	        HSSFFont columnFont = workbook.createFont();
	        columnFont.setFontHeightInPoints((short) 11);
	        columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        columnStyle.setFont(columnFont);

	        //导出修改，数据量超过SHEET_SIZE_XLS行的建立新的sheet 
	        int dataSize = VOList.size();
	        int sheetCount = dataSize % (SHEET_SIZE_XLS-1) == 0 ? dataSize / (SHEET_SIZE_XLS-1) : dataSize / (SHEET_SIZE_XLS-1) + 1;
	        for(int sheetNum = 0; sheetNum < sheetCount; sheetNum ++){
	        	HSSFSheet sheet = workbook.createSheet();
	        	
	        	//表头
	        	HSSFRow row = sheet.createRow(0);
	        	row.setHeight((short) 360);
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
	        			
	        			Class voClass = vo.getClass();
	        			
	        			
						
//	        			String textValue = null;
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
							
//							textValue = null == getMethod.invoke(vo) ? "" : String.valueOf(getMethod.invoke(vo));
						} catch (Exception e) {
						e.printStackTrace();
						}
	        			
//	        			HSSFRichTextString text = new HSSFRichTextString(null == textValue ? "" : textValue);
//	        			cell.setCellValue(text);
	        		}
	        	}
	        }
			
			try {
				OutputStream out = response.getOutputStream();
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			//导出07 后版本
			exportFileName = exportFileName + EXCEL_XLSX;
			
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			try {
				response.setHeader("Content-disposition", "attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
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
	        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
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
	        columnFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
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
	        		Object vo = VOList.get(i);
	        		row = sheet.createRow( ++ rowNum);
	        		row.setHeight((short) 360);
	        		
	        		for (int k = 0; k < columns.length; k++) {
	        			XSSFCell cell = row.createCell(k);
	        			cell.setCellStyle(columnStyle);
	        			
	        			Class voClass = vo.getClass();
	        			String textValue = null;
						try {
							Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
							textValue = null == getMethod.invoke(vo) ? "" : String.valueOf(getMethod.invoke(vo));
						} catch (Exception e) {
							e.printStackTrace();
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
			}
			
		}
		
	}
	
	
	/**
	 * Excel导入数据
	 * 支持的VO属性全为String
	 * @param is 文件输入流
	 * @param columns
	 * @param startRow
	 * @param startColumn
	 * @return
	 * @throws IOException
	 * @author zuosl  2015-9-16
	 */
	@SuppressWarnings("unchecked")
	public static List<?> importExcel(Object vo, InputStream is, String[] columns, 
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
            Class voClass = vo.getClass();
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
            	Method setMethod = voClass.getMethod("set"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1), String.class);
            	setMethod.invoke(tempVO, getValue(hssfCell));
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
    public static String getValue(HSSFCell hssfCell) {
        switch (hssfCell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:{
        	if(HSSFDateUtil.isCellDateFormatted(hssfCell)){				
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
        		return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue())).toString();			
        	}
//        	return String.valueOf(hssfCell.getNumericCellValue());
        	return new DecimalFormat("#").format(hssfCell.getNumericCellValue());
        }
        
        case HSSFCell.CELL_TYPE_STRING:
            return String.valueOf(hssfCell.getStringCellValue());
        
        case HSSFCell.CELL_TYPE_FORMULA:
            return String.valueOf(hssfCell.getNumericCellValue());
            
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return String.valueOf(hssfCell.getBooleanCellValue());
        
        default:
            return "";
        } 
    }
    
    /**
	 * 获取单元格值
	 * @param hssfCell
	 * @return
	 * @author zuosl  2015-9-16
	 */
    public static String getValue(HSSFCell hssfCell, String dateFormat) {
        switch (hssfCell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:{
        	if(HSSFDateUtil.isCellDateFormatted(hssfCell)){				
        		SimpleDateFormat sdf = null;
        		if(null != dateFormat && !"".equals(dateFormat)){
        			sdf = new SimpleDateFormat(dateFormat);
        		}else{
        			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		}
        		return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue())).toString();			
        	}
//        	return String.valueOf(hssfCell.getNumericCellValue());
        	return new DecimalFormat("#").format(hssfCell.getNumericCellValue());
        }
        
        case HSSFCell.CELL_TYPE_STRING:
            return String.valueOf(hssfCell.getStringCellValue());
        
        case HSSFCell.CELL_TYPE_FORMULA:
            return String.valueOf(hssfCell.getNumericCellValue());
            
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return String.valueOf(hssfCell.getBooleanCellValue());
        
        default:
            return "";
        } 
    }


    /**
     * 读取Excel数据  07后版本
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
	@SuppressWarnings("unchecked")
	public static List<?> importExcel2(Object vo, InputStream is, String[] columns, 
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
            Class voClass = vo.getClass();
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
        	if(HSSFDateUtil.isCellDateFormatted(cell)){				
        		SimpleDateFormat sdf = null;
        		if(null != dateFormat && !"".equals(dateFormat)){
        			sdf = new SimpleDateFormat(dateFormat);
        		}else{
        			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		}
        		return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();			
        	}
//        	return String.valueOf(hssfCell.getNumericCellValue());
        	return new DecimalFormat("#").format(cell.getNumericCellValue());
        }
        
        case HSSFCell.CELL_TYPE_STRING:
            return String.valueOf(cell.getStringCellValue());
        
        case HSSFCell.CELL_TYPE_FORMULA:
            return String.valueOf(cell.getNumericCellValue());
            
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        
        default:
            return "";
        } 
	}


	/**
	 * 获取Excel导出的最大数量
	 * @return
	 * @author zuosl 2016-4-13
	 */
	public static int getExcelExportMaxSize() {
		String max = PubSysParamServer.querySysParam("EXCEL_EXPORT_MAX_SIZE");
		if(StringUtil.isNullOrEmpty(max)){
			return 10000;
		}
		return Integer.valueOf(max);
	}
    
    
	/**
     * Excel导出操作  
     * @param is03Excel 是否导出03版本的Excel，否则导出07版本
     * @param response
     * @param exportFileName 导出文件名
     * @param VOList 需导出的数据
     * @param headers 表头字段数据 
     * @param widths 宽度
     * @param columns VO属性字段
     * @author zuosl  2016-7-5
     */
	@SuppressWarnings("unchecked")
	public static void exportExcel(boolean is03Excel, OutputStream out, List<?> VOList, 
			String[] headers, int[] widths, String[] columns) {
		if(null == VOList || 0 >= VOList.size()){
			return;
		}
		
		if(is03Excel){
			
			HSSFWorkbook workbook = new HSSFWorkbook();
	        
	        HSSFPalette palette = workbook.getCustomPalette();  
//	        palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));
	        palette.setColorAtIndex((short) 50, (byte)(0xc0), (byte)(0xc0), (byte)(0xc0));

	        //表头样式
	        HSSFCellStyle headStyle = workbook.createCellStyle();
	        headStyle.setFillForegroundColor((short) 50);
	        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	        //表头字体
	        HSSFFont headFont = workbook.createFont();
	        headFont.setFontHeightInPoints((short) 11);
	        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headStyle.setFont(headFont);

	        //内容样式
	        HSSFCellStyle columnStyle = workbook.createCellStyle();
//	        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
//	        columnStyle.setFillPattern(CellStyle.NO_FILL);//(HSSFCellStyle.SOLID_FOREGROUND);
	        columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

	        //内容字体
	        HSSFFont columnFont = workbook.createFont();
	        columnFont.setFontHeightInPoints((short) 11);
	        columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        columnStyle.setFont(columnFont);

	        //导出修改，数据量超过SHEET_SIZE_XLS行的建立新的sheet 
	        int dataSize = VOList.size();
	        int sheetCount = dataSize % (SHEET_SIZE_XLS-1) == 0 ? dataSize / (SHEET_SIZE_XLS-1) : dataSize / (SHEET_SIZE_XLS-1) + 1;
	        for(int sheetNum = 0; sheetNum < sheetCount; sheetNum ++){
	        	HSSFSheet sheet = workbook.createSheet();
	        	
	        	//表头
	        	HSSFRow row = sheet.createRow(0);
	        	row.setHeight((short) 360);
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
	        			
	        			Class voClass = vo.getClass();
	        			
	        			
						
//	        			String textValue = null;
						try {
							Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
//							
							//可以根据需要增加导出的数据格式，只是要确保VO的类型与导出的类型一致
							Object cellValue = getMethod.invoke(vo);
							if(cellValue == null ){
								cell.setCellValue("");
							}else if(cellValue instanceof Integer){
								cell.setCellValue(((Integer)cellValue).intValue());
							}else if(cellValue instanceof Long ){
								cell.setCellValue(((Long)cellValue));
							}else if(cellValue instanceof Date ){
								cell.setCellValue(((Date)cellValue));
							}else{
								cell.setCellValue(String.valueOf(cellValue));
							}
							
//							textValue = null == getMethod.invoke(vo) ? "" : String.valueOf(getMethod.invoke(vo));
						} catch (Exception e) {
						e.printStackTrace();
						}
	        			
//	        			HSSFRichTextString text = new HSSFRichTextString(null == textValue ? "" : textValue);
//	        			cell.setCellValue(text);
	        		}
	        	}
	        }
			
			try {
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.error(e);
			}
		}else{
			//导出07 后版本
			
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
	        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
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
	        columnFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
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
	        		Object vo = VOList.get(i);
	        		row = sheet.createRow( ++ rowNum);
	        		row.setHeight((short) 360);
	        		
	        		for (int k = 0; k < columns.length; k++) {
	        			XSSFCell cell = row.createCell(k);
	        			cell.setCellStyle(columnStyle);
	        			
	        			Class voClass = vo.getClass();
//	        			String textValue = null;
						try {
							Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
//							textValue = null == getMethod.invoke(vo) ? "" : String.valueOf(getMethod.invoke(vo));
							
							Object cellValue = getMethod.invoke(vo);
							if(cellValue == null ){
								cell.setCellValue("");
							}else if(cellValue instanceof Integer){
								cell.setCellValue(((Integer)cellValue).intValue());
							}else if(cellValue instanceof Long ){
								cell.setCellValue(((Long)cellValue));
							}else if(cellValue instanceof Date ){
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								cell.setCellValue(((Date)cellValue));
							}else{
								cell.setCellValue(String.valueOf(cellValue));
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							LogUtil.error(e);
						}
	        			
//	        			XSSFRichTextString text = new XSSFRichTextString(null == textValue ? "" : textValue);
//	        			cell.setCellValue(text);
	        		}
	        	}
	        	
	        }
	        
	        try {
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.error(e);
			}
			
		}
		
	}


	/**
     * Excel导出操作  
     * @param is03Excel 是否导出03版本的Excel，否则导出07版本
     * @param response
     * @param exportFileName 导出文件名
     * @param VOList 需导出的数据
     * @param headers 表头字段数据 
     * @param widths 宽度
     * @param columns VO属性字段
     * @param title 报表名
     * @param creationTime 报表日期
     * @author dtp  2018-08-07
     */
	@SuppressWarnings("unchecked")
	public static void createNotRecGoodsExcel(boolean is03Excel, OutputStream out, List<JitPartVO> list, 
			String[] headers, int[] widths, String[] columns, String title, String repDate, 
			String arriveBatch) {
		if(null == list || 0 >= list.size()){
			return;
		}
		if(is03Excel){
			
		}else{
			//导出07 后版本
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			//title样式
			XSSFCellStyle titleStyle = workbook.createCellStyle();
			XSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			titleFont.setFontName(HSSFFont.FONT_ARIAL);
			titleFont.setFontHeightInPoints((short) 20);
			titleStyle.setFont(titleFont);
			titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
			titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			//批次样式
	        XSSFCellStyle batchStyle = workbook.createCellStyle();
	        batchStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        batchStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        batchStyle.setBorderRight(CellStyle.BORDER_THIN);
	        batchStyle.setBorderTop(CellStyle.BORDER_THIN);
	        batchStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        batchStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
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
	        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        
	        //表头字体
	        XSSFFont headFont = workbook.createFont();
	        headFont.setFontName(HSSFFont.FONT_ARIAL);
	        headFont.setFontHeightInPoints((short) 10);
	        headStyle.setFont(headFont);
	        
	        //内容样式
	        XSSFCellStyle columnStyle = workbook.createCellStyle();
	        columnStyle.setFillPattern(CellStyle.NO_FILL);
	        columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        
	        //内容字体
	        XSSFFont columnFont = workbook.createFont();
	        columnFont.setFontHeightInPoints((short) 10);
	        columnFont.setFontName(HSSFFont.FONT_ARIAL);
	        columnFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
	        columnStyle.setFont(columnFont);
	        
	        //导出修改，数据量超过SHEET_SIZE_XLSX行的建立新的sheet 
	        int dataSize = list.size();
	        int sheetCount = dataSize % (SHEET_SIZE_XLSX-1) == 0 ? dataSize / (SHEET_SIZE_XLSX-1) : dataSize / (SHEET_SIZE_XLSX-1) + 1;
	        for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {
	        	XSSFSheet sheet = workbook.createSheet();
	        	
	        	sheet.addMergedRegion(new CellRangeAddress( 0, 0, 0, 13));
	        	//title 第一行
	        	XSSFRow row0 = sheet.createRow(0);
	        	row0.setHeight((short) 800);
	        	XSSFCell titleCell = row0.createCell(0);
	        	titleCell.setCellStyle(titleStyle);
	        	titleCell.setCellValue(title);

	        	//第二行
	        	XSSFRow row1 = sheet.createRow(1);
	        	
	        	//第三行
	        	sheet.addMergedRegion(new CellRangeAddress( 2, 2, 0, 1));
	            XSSFRow row2 = sheet.createRow(2);
	            row2.setHeight((short) 360);
	            XSSFCell cell0 = row2.createCell(0);
	            XSSFCell cell01 = row2.createCell(1);
	        	cell0.setCellStyle(headStyle);
	        	cell01.setCellStyle(headStyle);
	        	cell0.setCellValue("报表日期");
	        	
	        	//报表日期
	        	XSSFCell cell1 = row2.createCell(2);
	        	cell1.setCellStyle(batchStyle);
	        	if(!StringUtil.isNullOrEmpty(repDate)){
	        		cell1.setCellValue(repDate);
	        	}
	        	
	        	sheet.addMergedRegion(new CellRangeAddress( 2, 2, 3, 5));
	        	XSSFCell cell2 = row2.createCell(3);
	        	XSSFCell cell21 = row2.createCell(4);
	        	XSSFCell cell22 = row2.createCell(5);
	        	cell2.setCellStyle(headStyle);
	        	cell21.setCellStyle(headStyle);
	        	cell22.setCellStyle(headStyle);
	        	cell2.setCellValue("报表计算截止物流看板批次");
	        	
	        	//看板批次
	        	XSSFCell cell3 = row2.createCell(6);
	        	cell3.setCellStyle(batchStyle);
	        	cell3.setCellValue(arriveBatch);
	        	
	        	//第四行
	        	XSSFRow row3 = sheet.createRow(3);
	        	
	        	//第五行 表头
	        	XSSFRow row = sheet.createRow(4);
	        	row.setHeight((short) 500);
	        	for (int i = 0; i < headers.length; i++) {
	        		XSSFCell cell = row.createCell(i);
	        		cell.setCellStyle(headStyle);
	        		XSSFRichTextString text = new XSSFRichTextString(headers[i]);
	        		cell.setCellValue(text);
	        		sheet.setColumnWidth(i, widths[i] * 45);
	        	}
	        	
	        	//内容 
	        	int rowNum = 4;
	        	for (int i = sheetNum*(SHEET_SIZE_XLSX-1); i < (sheetNum+1)*(SHEET_SIZE_XLSX-1) && i < dataSize; i++) {
	        		Object vo = list.get(i);
	        		row = sheet.createRow( ++ rowNum);
	        		row.setHeight((short) 320);
	        		
	        		for (int k = 0; k < columns.length; k++) {
	        			XSSFCell cell = row.createCell(k);
	        			cell.setCellStyle(columnStyle);
	        			
	        			Class voClass = vo.getClass();
						try {
							Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
							
							Object cellValue = getMethod.invoke(vo);
							if(cellValue == null ){
								cell.setCellValue("");
							}else if(cellValue instanceof Integer){
								cell.setCellValue(((Integer)cellValue).intValue());
							}else if(cellValue instanceof Long ){
								cell.setCellValue(((Long)cellValue));
							}else if(cellValue instanceof Date ){
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								cell.setCellValue(((Date)cellValue));
							}else{
								cell.setCellValue(String.valueOf(cellValue));
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							LogUtil.error(e);
						}
	        			
	        		}
	        	}
	        	
			}
			
	        try {
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.error(e);
			}
	        
		}
		
	}

}
