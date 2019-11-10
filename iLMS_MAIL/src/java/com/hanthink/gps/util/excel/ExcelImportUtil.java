package com.hanthink.gps.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hanthink.gps.util.logger.LogUtil;

/**
 * Excel数据导入
 * @author Administrator
 *
 */
public abstract class ExcelImportUtil {
	private static final String EXCEL_XLS = ".xls";  	//Excel03文件后缀
    private static final String EXCEL_XLSX = ".xlsx"; 	//Excel07文件后缀
    public int pageSize = 15000;//50000;//80000  						//每次写入数据库的数据量
    
    
    /**
     * excel数据导入 07数据格式
     * @param vo VO属性全为String
     * @param is
     * @param columns
     * @param startRow
     * @param startColumn
     * @return 1：成功  0：失败
     * @throws IOException
     * @author zuosl 2016-4-3
     */
    @SuppressWarnings("unchecked")
	public int importExcel2(Object vo, InputStream is, String[] columns, 
			int startRowNum, int startColumn) throws IOException{
    	
    	// 获取工作表Sheet
		Workbook workbook = new XSSFWorkbook(is);
        
		//获取sheet
    	Sheet sheet = workbook.getSheetAt(0);
        if (null == sheet) {
           return 0;
        }
        
        int lastRowNum = sheet.getLastRowNum();
        int totalTotalNum = lastRowNum - startRowNum + 1;
        
        int totalPage = 0 == totalTotalNum%pageSize ? totalTotalNum/pageSize : (totalTotalNum/pageSize + 1);
    	
        List<Object> voList = new ArrayList<Object>();
        
        //分页循环
        for(int page = 0; page < totalPage; page ++){
        	if(null != voList && 0 < voList.size()){
        		voList.clear();
        	}
        	
        	int curStartRow = page*pageSize + startRowNum;
        	int pageLastRow = ((page+1)*pageSize + startRowNum - 1);
        	int curLastRow = pageLastRow > lastRowNum ? lastRowNum : pageLastRow;
        	
        	//页循环
        	for(int rowNum = curStartRow; rowNum <= curLastRow; rowNum ++){
        		Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                
              //循环列Cell
                Class voClass = vo.getClass();
    			try {
    				Object tempVO = voClass.newInstance();
    				for (int cellNum = startColumn; cellNum <= row.getLastCellNum(); cellNum++) {
    					if (cellNum >= (columns.length + startColumn)) {
    						break;
    					}
    					
    					Cell cell = row.getCell(cellNum);
    	                if (null == cell) {
    	                    continue;
    	                }
    	                
    	                int k = cellNum - startColumn;
    					Method setMethod = voClass.getMethod("set"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1), String.class);
    					setMethod.invoke(tempVO, getValue(cell));
    				}
    				voList.add(tempVO);
    			}catch(Exception e) {
    				LogUtil.error(e);
    				e.printStackTrace();
    			}
        	}
        	
        	//将数据写入到数据库
        	this.insertDataToDB(voList);
        	
        }
    	
    	return 1;
    }


    /**
     * 将数据写入到数据库
     * @param voList
     * @author zuosl 2016-4-3
     */
    public abstract void insertDataToDB(List<Object> voList);


	/**
     * 读取Excel单元格值
     * @param cell
     * @return
     * @author zuosl 2016-4-3
     */
	private String getValue(Cell cell) {
		switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:{
        	if(HSSFDateUtil.isCellDateFormatted(cell)){				
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
        		return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
        	}
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
    
}
