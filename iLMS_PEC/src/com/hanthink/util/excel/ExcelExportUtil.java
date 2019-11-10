package com.hanthink.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc		: 分页查询导出
 * @FileName	: ExcelExportUtil.java
 * @CreateOn	: 2018年8月31日 上午10:00:30
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2018年8月31日		V1.0		ZUOSL		新建
 */
public abstract class ExcelExportUtil {
    protected int pageSize = 80000;  						//每次查询数据库的数据量
    protected static Logger log = LoggerFactory.getLogger(ExcelExportUtil.class);
    
    /**
     * 分页查询数据
     * @return
     * @author zuosl  2015-11-17
     */
    public abstract PageList<Object> queryDataFromDB(int page);
    
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
     * @throws IOException 
     */
	public void exportExcel(String excelExtName, HttpServletRequest request, HttpServletResponse response,
			String exportFileName, List<?> VOList, 
			String[] headers, int[] widths, String[] columns) throws IOException{
    	
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
    	
		if(null != excelExtName){
			excelExtName = excelExtName.toLowerCase();
		}
		
		Workbook workbook = null;
		if(ExcelUtil.EXCEL_XLS.equals(excelExtName)){
			workbook = new HSSFWorkbook();
			if(!exportFileName.endsWith(ExcelUtil.EXCEL_XLS)){
				exportFileName = exportFileName + ExcelUtil.EXCEL_XLS;
			}
		}else{
			workbook = new SXSSFWorkbook(2000);
			if(!exportFileName.endsWith(ExcelUtil.EXCEL_XLSX)){
				exportFileName = exportFileName + ExcelUtil.EXCEL_XLSX;
			}
		}
		
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
		}
        
		response.setHeader("Content-disposition", "attachment; filename=" + downName);
    	
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
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
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
        columnFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
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
    	
    	PageList<Object> qPageList = null;
    	int queryCount = 1;
    	
    	do {
    		qPageList = this.queryDataFromDB(queryCount);
    		queryCount ++;
    		
    		if(null != qPageList && 0 < qPageList.size()){
    			List<Object> list = qPageList;
    			
    			for (int i = 0; i < list.size(); i++) {
    				Object vo = list.get(i);
    				this.showChange(vo); // 数据显示转换
    				
    				row = sheet.createRow((queryCount-2)*pageSize+i+1);
    	    		row.setHeight((short) 360);
    	    		
    	    		for (int k = 0; k < columns.length; k++) {
    	    			Cell cell = row.createCell(k);
    	    			cell.setCellStyle(columnStyle);
    	    			
    	    			Class<? extends Object> voClass = vo.getClass();
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

						} catch (Exception e) {
							log.error(e.toString());;
							e.printStackTrace();
						}
    	    		}
    			}
    		}
    		
		} while (null != qPageList && 0 < qPageList.size());
    	
    	try {
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
    	
    }

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
    
    

}
