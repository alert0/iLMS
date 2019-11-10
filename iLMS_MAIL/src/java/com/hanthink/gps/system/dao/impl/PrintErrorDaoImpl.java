/**
 * 
 */
package com.hanthink.gps.system.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.system.dao.PrintErrorDao;
import com.hanthink.gps.system.vo.PrintErrorVO;

/**
 * 自动打印异常DaoImpl
 * @author chenyong
 *date 2016-09-21
 *
 */
public class PrintErrorDaoImpl extends BaseDaoSupport implements PrintErrorDao{

	
	
	//查询自动打印异常信息
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintErrorVO> queryG1PrintErrorInfo(PrintErrorVO pVo) {

		//执行查询异常之前的存储过程
		this.executeUpdate("system.checkPrintError");
        
		return (List<PrintErrorVO>) this.executeQueryForList("system.select_queryPrintErrorInfo",pVo);
	}

}
