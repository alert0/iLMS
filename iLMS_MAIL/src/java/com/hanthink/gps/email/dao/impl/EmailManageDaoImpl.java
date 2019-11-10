package com.hanthink.gps.email.dao.impl;

import java.util.List;

import com.hanthink.gps.email.dao.EmailManageDao;
import com.hanthink.gps.email.vo.EmailManageVO;
import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.vo.PageObject;

/**
 * 定时器管理DaoImpl
 * 
 * @author smy
 * @date 2016-7-27
 */
public class EmailManageDaoImpl extends BaseDaoSupport implements
		EmailManageDao {

	/**
	 * 定时器管理信息查询
	 */
	public PageObject queryEmailTimerForPage(EmailManageVO emailVO, int start,
			int limit) {
		return this.executeQueryForPage("email.select_queryEmailTimerForPage",
				emailVO, start, limit);
	}

	/**
	 * 修改定时器信息
	 */
	public int updateEmail(EmailManageVO emailVO) {
		return executeUpdate("email.update_updateEmail", emailVO);

	}

	/**
	 * 插入分组信息
	 */
	public void batchInsert(List<EmailManageVO> userRoles) {
		this.executeBatchInsert("email.insert_batchInsert", userRoles);

	}

	/**
	 * 删除分组信息
	 */
	public int delete(EmailManageVO emailVO) {
		return this.executeDelete("email.delete_group", emailVO);

	}

	/**
	 * 停止运行状态
	 * @return 
	 */
	public int updateRunstate(EmailManageVO emailVO) {
		return executeUpdate("email.update_updateRunstate", emailVO);	
	}

}
