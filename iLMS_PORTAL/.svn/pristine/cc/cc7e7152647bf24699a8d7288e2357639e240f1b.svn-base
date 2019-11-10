package com.hanthink.base.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.hanthink.base.dao.TableDataLogDao;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;

/**
 * @Desc    : Dao实现(含记录数据日志方法)
 * @FileName: DaoImpl.java 
 * @CreateOn: 2017-3-17 上午11:38:40
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2017-3-17	V1.0		zuosl		新建
 * 
 *
 */
@Repository
public class TableDataLogDaoImpl implements TableDataLogDao{
	
	private static String TABLE_DATA_LOG_NAMESPACE = "com.hanthink.base.TableDataLog";
	
	@Resource
    protected SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 根据表名获取表字段信息
	 * @param tableName
	 * @return
	 * @author zuosl 
	 * @date 2017-3-17 下午12:30:31
	 */
	private List<TableColumnVO> queryTableColumnInfoByTableName(String tableName){
		return this.sqlSessionTemplate.selectList(TABLE_DATA_LOG_NAMESPACE + ".select_selectTableColumnInfoByTableName", tableName);
	}
	
	/**
	 * 根据表信息获取查询记录值sql
	 * @param tableColumnList
	 * @return
	 * @author zuosl 
	 * @date 2017-3-17 下午03:19:17
	 */
	private static String createLogValSql(List<TableColumnVO> tableColumnList){
		StringBuffer sbf = new StringBuffer();
		sbf.append("'{");
		for(int i = 0; i < tableColumnList.size(); i ++){
			TableColumnVO colVO = tableColumnList.get(i);
			if(i > 0){
				sbf.append(" || ',");
				sbf.append(colVO.getColumnName());
			}else{
				sbf.append(colVO.getColumnName());
			}
			sbf.append("=' || ");
			if(TableColumnVO.DF_DATA_TYPE_DATE == colVO.getDefineDataTypeByDbType()){
				sbf.append(" TO_CHAR( ");
				sbf.append(colVO.getColumnName());
				sbf.append(" ,'YYYY-MM-DD HH24:MI:SS') ");
			}else{
				sbf.append(colVO.getColumnName());
			}
		}
		sbf.append(" || '}' ");
		
		return sbf.toString();
	}
	
	
	
	/**
	 * 操作表数据日志记录(单主键字段)
	 * @param opeLogVO  操作日志VO  包括表名、操作人、操作IP等信息
	 * @param pkColumnVO 主键字段VO 定义相关表的主键字段与主键字段值
	 * @author zuosl 
	 * @date 2017-3-17 下午12:35:37
	 */
	public void logOpeTableData(TableOpeLogVO opeLogVO, TableColumnVO pkColumnVO){
		
		List<TableColumnVO> tableColumnList = this.queryTableColumnInfoByTableName(opeLogVO.getTableName());
		
		opeLogVO.setLogValSql(createLogValSql(tableColumnList));
		
		//若未自定义字段类型，根据数据库字段类型判断
		if(TableColumnVO.DF_DATA_TYPE_NULL == pkColumnVO.getDefineDataType()){
			for(TableColumnVO cvo : tableColumnList){
				if(cvo.getColumnName().equals(pkColumnVO.getColumnName())){
					pkColumnVO.setDefineDataType(cvo.getDefineDataTypeByDbType());
					break;
				}
			}
		}
		
		//拼接条件SQL
		StringBuffer sbf = new StringBuffer();
		if(null != pkColumnVO.getColumnVal() && !"".equals(pkColumnVO.getColumnVal().trim())){
			sbf.append(pkColumnVO.getColumnName());
			sbf.append(" = ");
			switch (pkColumnVO.getDefineDataType()) {
				case TableColumnVO.DF_DATA_TYPE_DATE : {
					sbf.append(" TO_DATE('");
					sbf.append(pkColumnVO.getColumnVal());
					sbf.append("','YYYY-MM-DD HH24:MI:SS') ");
					break;
				}
				case TableColumnVO.DF_DATA_TYPE_STRING : {
					sbf.append(" '");
					sbf.append(pkColumnVO.getColumnVal());
					sbf.append("' ");
					break;
				}
				case TableColumnVO.DF_DATA_TYPE_NUMBER : {
					sbf.append(pkColumnVO.getColumnVal());
					break;
				}
			}
		}else{
			throw new RuntimeException("记录日志条件为空");
		}
		
		opeLogVO.setPkConSql(sbf.toString());
		
		opeLogVO.setPkColumn(pkColumnVO.getColumnName());
		opeLogVO.setPkRecord(pkColumnVO.getColumnVal());
		
		this.sqlSessionTemplate.insert(TABLE_DATA_LOG_NAMESPACE + ".insert_insertTableDataLog", opeLogVO);
	}
	
	/**
	 * 操作表数据日志批量记录(单主键字段)
	 * @param opeLogVO  操作日志VO  包括表名、操作人、操作IP等信息
	 * @param pkColumnVO 主键字段VO 定义相关表的主键字段与主键字段值
	 * @author zuosl 
	 * @date 2017-3-17 下午12:35:37
	 */
	public void logOpeTableDataBatch(TableOpeLogVO opeLogVO, TableColumnVO pkColumnVO){
		
		List<TableColumnVO> tableColumnList = this.queryTableColumnInfoByTableName(opeLogVO.getTableName());
		
		opeLogVO.setLogValSql(createLogValSql(tableColumnList));
		
		//若未自定义字段类型，根据数据库字段类型判断
		if(TableColumnVO.DF_DATA_TYPE_NULL == pkColumnVO.getDefineDataType()){
			for(TableColumnVO cvo : tableColumnList){
				if(cvo.getColumnName().equals(pkColumnVO.getColumnName())){
					pkColumnVO.setDefineDataType(cvo.getDefineDataTypeByDbType());
					break;
				}
			}
		}
		
		String[] valArr = pkColumnVO.getColumnValArr();
		
		int total = valArr.length;
		int size = 995;
		int count = total % size == 0 ? total/size : total/size + 1;
		int index = 0; 
		for(int i = 0; i < count; i ++){
			List<String> list = new ArrayList<String>();
			for(int k = 0; k < size; k ++){
				if(index < total){
					list.add(valArr[index]);
				}
				index ++;
				if(index >= total){
					break;
				}
			}
			String[] newValArr = list.toArray(new String[list.size()]);
			insertLogOpeTableDataBatch(opeLogVO, pkColumnVO, newValArr);
			
			if(index >= total){
				break;
			}
		}
		
	}
	
	
	/**
	 * 操作表数据日志批量记录(单主键字段)记录数据
	 * @param columnName
	 * @param valArr
	 * @author zuosl 
	 * @date 2017-11-8 下午09:57:51
	 */
	private void insertLogOpeTableDataBatch(TableOpeLogVO opeLogVO, TableColumnVO pkColumnVO, String[] valArr) {
		//拼接条件SQL
		StringBuffer sbf = new StringBuffer();
		StringBuffer valSbf = new StringBuffer();
		if(null != valArr && 0 < valArr.length){
			sbf.append(pkColumnVO.getColumnName());
			sbf.append(" IN ( ");
			switch (pkColumnVO.getDefineDataType()) {
				case TableColumnVO.DF_DATA_TYPE_DATE : {
					for(int i = 0; i < valArr.length; i ++){
						if(i > 0){
							sbf.append(", ");
							valSbf.append(",");
						}
						sbf.append(" TO_DATE('");
						sbf.append(valArr[i]);
						sbf.append("','YYYY-MM-DD HH24:MI:SS') ");
						
						valSbf.append(valArr[i]);
					}
					sbf.append(")");
					break;
				}
				case TableColumnVO.DF_DATA_TYPE_STRING : {
					for(int i = 0; i < valArr.length; i ++){
						if(i > 0){
							sbf.append(", ");
							valSbf.append(",");
						}
						sbf.append(" '");
						sbf.append(valArr[i]);
						sbf.append("' ");
						
						valSbf.append(valArr[i]);
					}
					sbf.append(")");
					break;
				}
				case TableColumnVO.DF_DATA_TYPE_NUMBER : {
					for(int i = 0; i < valArr.length; i ++){
						if(i > 0){
							sbf.append(", ");
							valSbf.append(",");
						}
						sbf.append(valArr[i]);
						
						valSbf.append(valArr[i]);
					}
					sbf.append(")");
					break;
				}
			}
		}else{
			throw new RuntimeException("记录日志条件为空");
		}
		
		opeLogVO.setPkConSql(sbf.toString());
		
		opeLogVO.setPkColumn(pkColumnVO.getColumnName());
//		opeLogVO.setPkRecord(valSbf.toString());
		
		this.sqlSessionTemplate.insert(TABLE_DATA_LOG_NAMESPACE + ".insert_insertTableDataLog", opeLogVO);
	}
	
}
