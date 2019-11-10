/**
 * 
 */
package com.hanthink.gps.print.dao.impl;

import java.util.List;

import com.hanthink.gps.print.dao.G1PrintErrorDao;
import com.hanthink.gps.print.vo.G1PrintErrorVO;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

/**
 * 一线自动打印异常DaoImpl
 * @author chenyong
 *date 2016-09-21
 *
 */
public class G1PrintErrorDaoImpl extends BaseDaoSupport implements G1PrintErrorDao{

	
	
	//查询自动打印异常信息
	@SuppressWarnings("unchecked")
	@Override
	public List<G1PrintErrorVO> queryG1PrintErrorInfo(G1PrintErrorVO pVo) {

		//执行查询异常之前的存储过程
		this.executeUpdate("print.checkPrintError");
        
		return (List<G1PrintErrorVO>) this.executeQueryForList("print.select_queryPrintErrorInfo",pVo);
	}

}
