package com.hanthink.gps.email.dao;

import java.util.List;

import com.hanthink.gps.email.vo.EmailManageVO;
import com.hanthink.gps.pub.vo.PageObject;

/**
 * 定时器管理Dao
 * @author smy
 * @date 2016-7-27
 */
public interface EmailManageDao {

	/**
	 * 定时管理器信息查询
	 *@author smy
	 *@date 2016-7-27
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param emailVO
	 *@param start
	 *@param limit
	 *@return
	 */
	PageObject queryEmailTimerForPage(EmailManageVO emailVO, int start,
			int limit);

	/**
	 * 定时器信息修改
	 *@author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param emailVO
	 */
	int updateEmail(EmailManageVO emailVO);

	/**
	 * 删除分组信息
	 *@author smy
	 *@date 2016-8-3
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param emailVO
	 *@return
	 */
	int delete(EmailManageVO emailVO);

	/**
	 * 插入分组信息
	 *@author smy
	 *@date 2016-8-3
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param userRoles
	 */
	void batchInsert(List<EmailManageVO> userRoles);

	/**
	 * 停止运行状态
	 *@author smy
	 *@date 2016-8-24
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param emailVO
	 */
	int updateRunstate(EmailManageVO emailVO);

}
