/**
 * 
 */
package com.hanthink.gps.system.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.system.dao.DataBaseBlockErrorDao;
import com.hanthink.gps.system.vo.DataBaseBlockErrorVO;

/**
 * 描述：数据库阻塞异常邮件提醒DaoImpl
 * @author chenyong
 * @date   2016-10-09
 */
public class DataBaseBlockErrorDaoImpl extends BaseDaoSupport implements DataBaseBlockErrorDao{
	
	/**
	 * 获取数据库表阻塞异常的信息列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataBaseBlockErrorVO> queryDataBaseBlockErrorInfo() {
		
		 DataBaseBlockErrorVO  vo=new DataBaseBlockErrorVO();
		 vo.setVsession("v$session");
		 vo.setVlock("v$locked_object");
		 vo.setSqltext("v$sqltext");
		return (List<DataBaseBlockErrorVO>) this.executeQueryForList("system.queryDataBaseBlockErrorInfo",vo);
	}

}
