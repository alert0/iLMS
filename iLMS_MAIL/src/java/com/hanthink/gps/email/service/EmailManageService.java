package com.hanthink.gps.email.service;
import java.util.List;

import com.hanthink.gps.email.vo.EmailManageVO;
import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.pub.vo.PageObject;

/**
 * 定时器管理Service
 * @author smy
 * @date 2016-7-27
 */
public interface EmailManageService extends BaseService {

	/**
	 * 定时器管理信息查询
	 *@author smy
	 *@date 2016-7-27
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
	 *@param start
	 *@param limit
	 *@return
	 */
	PageObject queryEmailTimerForPage(EmailManageVO emailVO, int start, int limit);

	/**
	 * 定时器信息修改
	 *@author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
	 */
	void updateEmail(EmailManageVO emailVO);
	
	/**
	 * 配置分组
	 *@author smy
	 *@date 2016-8-8
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param emailVO
	 *@param groups
	 */
	void configGroup(EmailManageVO emailVO, List<String> groups);

	/**
	 * 停止运行状态
	 *@author smy
	 *@date 2016-8-24
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
	 */
	void updateRunstate(EmailManageVO emailVO);

	

}
