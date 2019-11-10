package com.hanthink.base.dao;

import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;

/**
 * @Desc    : 扩展Dao接口(含记录数据日志方法)
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
public interface TableDataLogDao{
	
	/**
	 * 操作表数据日志记录(单主键字段)
	 * @param opeLogVO  操作日志VO  包括表名、操作人、操作IP等信息
	 * @param pkColumnVO 主键字段VO 定义相关表的主键字段与主键字段值
	 * @author zuosl 
	 * @date 2017-3-17 下午12:35:37
	 */
	public void logOpeTableData(TableOpeLogVO opeLogVO, TableColumnVO pkColumnVO);
	
	/**
	 * 操作表数据日志批量记录(单主键字段)
	 * @param opeLogVO  操作日志VO  包括表名、操作人、操作IP等信息
	 * @param pkColumnVO 主键字段VO 定义相关表的主键字段与主键字段值
	 * @author zuosl 
	 * @date 2017-3-17 下午12:38:37
	 */
	public void logOpeTableDataBatch(TableOpeLogVO opeLogVO, TableColumnVO pkColumnVO);
	
}
