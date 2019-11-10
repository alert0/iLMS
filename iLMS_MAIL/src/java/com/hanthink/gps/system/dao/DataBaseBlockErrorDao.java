/**
 * 
 */
package com.hanthink.gps.system.dao;

import java.util.List;

import com.hanthink.gps.system.vo.DataBaseBlockErrorVO;

/**
 * 描述：数据库表阻塞异常邮件提醒DAO
 * @author chenyong
 * @date   2016-10-10
 */
public interface DataBaseBlockErrorDao {
     
	public List<DataBaseBlockErrorVO> queryDataBaseBlockErrorInfo();
}
