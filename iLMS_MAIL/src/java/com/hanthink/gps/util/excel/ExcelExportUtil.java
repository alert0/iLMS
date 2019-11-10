package com.hanthink.gps.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.util.logger.LogUtil;

/**
 * 分页查询导出
 * @author zuosl 2015-11-17
 * @modified by anMin 2015-11-18 数据不转String导出；测试一次性导出xlsx17W耗时3~4min
 * 
 */

public abstract class ExcelExportUtil {
	private static final String EXCEL_XLS = ".xls";  	//Excel03文件后缀
    private static final String EXCEL_XLSX = ".xlsx"; 	//Excel07文件后缀
    public int pageSize = 10000;  						//每次查询数据库的数据量
    
    /**
     * 分页查询数据
     * @return
     * @author zuosl  2015-11-17
     */
    public abstract PageObject queryDataFromDB(int page);
    
    /**
     * 数据显示转换
     * @param vo
     * @author zuosl  2015-11-17
     */
    public abstract void showChange(Object vo);
    
    
    /**
     * Excel导出
     * @param headers
     * @param columns
     * @param widths
     * @param out
     * @author zuosl  2015-11-17
     */
    @SuppressWarnings("unchecked")
	public void export(String[] headers, String[] columns,
			int[] widths, String exportFileName, HttpServletResponse response){
    	
    	exportFileName = exportFileName + EXCEL_XLSX;
		
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-disposition", "attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
    	
    	Workbook workbook = new SXSSFWorkbook(2000);
    	Sheet sheet = workbook.createSheet();

        //表头样式
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor((short) 70); 
        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setBorderTop(CellStyle.BORDER_THIN);
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);

        //表头字体
        Font headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);

        //内容样式
        CellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFillPattern(CellStyle.NO_FILL);//(HSSFCellStyle.SOLID_FOREGROUND);
        columnStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnStyle.setBorderLeft(CellStyle.BORDER_THIN);
        columnStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnStyle.setAlignment(CellStyle.ALIGN_LEFT);
        columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //内容字体
        Font columnFont = workbook.createFont();
        columnFont.setFontHeightInPoints((short) 11);
        columnFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        columnStyle.setFont(columnFont);

        //表头
    	Row row = sheet.createRow(0);
    	row.setHeight((short) 360);
    	for (int i = 0; i < headers.length; i++) {
    		Cell cell = row.createCell(i);
    		cell.setCellStyle(headStyle);
    		XSSFRichTextString text = new XSSFRichTextString(headers[i]);
    		cell.setCellValue(text);
    		sheet.setColumnWidth(i, widths[i] * 45);
    	}
    	
    	PageObject qPageO = null;
    	int queryCount = 1;
    	
    	do {
    		qPageO = this.queryDataFromDB(queryCount);
    		queryCount ++;
    		
    		if(null != qPageO && null != qPageO.getItems() && 0 < qPageO.getItems().size()){
    			List<Object> list = qPageO.getItems();
    			
    			for (int i = 0; i < list.size(); i++) {
    				Object vo = list.get(i);
    				this.showChange(vo); // 数据显示转换
    				
    				row = sheet.createRow((queryCount-2)*pageSize+i+1);
    	    		row.setHeight((short) 360);
    	    		
    	    		for (int k = 0; k < columns.length; k++) {
    	    			Cell cell = row.createCell(k);
    	    			cell.setCellStyle(columnStyle);
    	    			
    	    			Class voClass = vo.getClass();
//	        			String textValue = null;
						try {
							Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
							
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
							LogUtil.error(e);
							e.printStackTrace();
						}
    	    			
//    	    			cell.setCellValue(new XSSFRichTextString(null == textValue ? "" : textValue));
    	    		}
    			}
    		}
    		
		} while (null != qPageO && null != qPageO.getItems() && 0 < qPageO.getItems().size());
    	
    	try {
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }


    /**
     * 导出Excel数据
     * @param out
     * @param VOList
     * @param headers
     * @param widths
     * @param columns
     * @author zuosl 2016-7-8
     */
    @SuppressWarnings("unchecked")
	public boolean exportExcel(OutputStream out,  
			String[] headers, int[] widths, String[] columns) {
		
		Workbook workbook = new SXSSFWorkbook(2000);
    	Sheet sheet = workbook.createSheet();

        //表头样式
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor((short) 70); 
        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setBorderTop(CellStyle.BORDER_THIN);
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);

        //表头字体
        Font headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);

        //内容样式
        CellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFillPattern(CellStyle.NO_FILL);//(HSSFCellStyle.SOLID_FOREGROUND);
        columnStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnStyle.setBorderLeft(CellStyle.BORDER_THIN);
        columnStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnStyle.setAlignment(CellStyle.ALIGN_LEFT);
        columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //内容字体
        Font columnFont = workbook.createFont();
        columnFont.setFontHeightInPoints((short) 11);
        columnFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        columnStyle.setFont(columnFont);

        //表头
    	Row row = sheet.createRow(0);
    	row.setHeight((short) 360);
    	for (int i = 0; i < headers.length; i++) {
    		Cell cell = row.createCell(i);
    		cell.setCellStyle(headStyle);
    		XSSFRichTextString text = new XSSFRichTextString(headers[i]);
    		cell.setCellValue(text);
    		sheet.setColumnWidth(i, widths[i] * 45);
    	}
    	
    	PageObject qPageO = null;
    	int queryCount = 1;
    	
    	do {
    		qPageO = this.queryDataFromDB(queryCount);
    		
    		if(1 == queryCount 
    				&& (null == qPageO || null == qPageO.getItems() || 0 >= qPageO.getItems().size()) ){
    			return false;
    		}
    		
    		queryCount ++;
    		
    		if(null != qPageO && null != qPageO.getItems() && 0 < qPageO.getItems().size()){
    			List<Object> list = qPageO.getItems();
    			
    			for (int i = 0; i < list.size(); i++) {
    				Object vo = list.get(i);
    				this.showChange(vo); // 数据显示转换
    				
    				row = sheet.createRow((queryCount-2)*pageSize+i+1);
    	    		row.setHeight((short) 360);
    	    		
    	    		for (int k = 0; k < columns.length; k++) {
    	    			Cell cell = row.createCell(k);
    	    			cell.setCellStyle(columnStyle);
    	    			
    	    			Class voClass = vo.getClass();
						try {
							Method getMethod = voClass.getMethod("get"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1));
							
							//可以根据需要增加导出的数据格式，只是要确保VO的类型与导出的类型一致
							Object cellValue = getMethod.invoke(vo);
							if(cellValue == null ){
								cell.setCellValue("");
							}else if(cellValue instanceof Integer){
								cell.setCellValue(((Integer)cellValue).intValue());
							}else if(cellValue instanceof Long ){
								cell.setCellValue(((Long)cellValue).intValue());
							}else if(cellValue instanceof Date ){
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								cell.setCellValue(((Date)cellValue));
							}else{
								cell.setCellValue(String.valueOf(cellValue));
							}

						} catch (Exception e) {
							LogUtil.error(e);
							e.printStackTrace();
						}
    	    			
    	    		}
    			}
    		}
    		
		} while (null != qPageO && null != qPageO.getItems() && 0 < qPageO.getItems().size());
    	
    	try {
    		workbook.write(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			LogUtil.error(e);
			return false;
		}
		return true;
    }
}
