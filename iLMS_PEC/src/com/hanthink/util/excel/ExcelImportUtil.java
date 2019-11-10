package com.hanthink.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc    : Excel数据导入工具类
 * @FileName: ExcelImportUtil.java 
 * @CreateOn: 2016-4-3
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 20170426		1.1			zuosl		过滤获取的单元格数据的前后空格
 *
 */
public abstract class ExcelImportUtil {
    protected int pageSize = 20000;//50000;//80000  						//每次写入数据库的数据量
    protected static Logger log = LoggerFactory.getLogger(ExcelImportUtil.class);
    
    
    /**
     * excel数据导入
     * @param vo VO使用属性全为String
     * @param is
     * @param columns
     * @param startRow
     * @param startColumn
     * @return 1：成功  0：失败
     * @throws IOException
     * @author zuosl 2016-4-3
     */
	public int importExcel(String excelExtName, Object vo, InputStream is, String[] columns, 
			int startRowNum, int startColumn) throws IOException{
		
		if(null != excelExtName){
			excelExtName = excelExtName.toLowerCase();
		}
		
		Workbook workbook = null;
		if(ExcelUtil.EXCEL_XLS.equals(excelExtName)){
			workbook = new HSSFWorkbook(is);
		}else{
			workbook = new XSSFWorkbook(is);
		}
        
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
                Class<? extends Object> voClass = vo.getClass();
    			try {
    				Object tempVO = voClass.newInstance();
    				boolean rowAllEmpty = true;
    				for (int cellNum = startColumn; cellNum <= row.getLastCellNum(); cellNum++) {
    					if (cellNum >= (columns.length + startColumn)) {
    						break;
    					}
    					
    					Cell cell = row.getCell(cellNum);
    	                if (null == cell) {
    	                    continue;
    	                }
    	                
    	                int k = cellNum - startColumn;
    	                String cellVal = getValue(cell);
    	                if(null != cellVal){
    	                	cellVal = cellVal.trim();
    	                }
    	                if(rowAllEmpty && null != cellVal && !"".equals(cellVal)){
    	                	rowAllEmpty = false;
    	                }
    					Method setMethod = voClass.getMethod("set"+columns[k].substring(0,1).toUpperCase()+columns[k].substring(1), String.class);
    					setMethod.invoke(tempVO, cellVal);
    				}
    				if(!rowAllEmpty){
    	            	voList.add(tempVO);
    	            }
    				voList.add(tempVO);
    			}catch(Exception e) {
    				log.error(e.toString());
    				e.printStackTrace();
    				return 0;
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
        	if(DateUtil.isCellDateFormatted(cell)){				
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
        		return sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
        	}
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
    
}
