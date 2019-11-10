package com.hanthink.gps.email.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hanthink.gps.email.dao.EmailManageDao;
import com.hanthink.gps.email.service.EmailManageService;
import com.hanthink.gps.email.vo.EmailManageVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.UserVO;

/**
 * 定时器管理ServiceImpl
 * @author smy
 * @date 2016-7-27
 */
public class EmailManageServiceImpl extends BaseServiceImpl implements
		EmailManageService {
	private EmailManageDao emailDao;

	/**
	 * 定时器管理查询
	 */
	public PageObject queryEmailTimerForPage(EmailManageVO emailVO, int start,
			int limit) {
		return emailDao.queryEmailTimerForPage(emailVO,start,limit);
	}

	/**
	 * 定时器信息修改
	 */
	public void updateEmail(EmailManageVO emailVO) {
		emailDao.updateEmail(emailVO);	
		
	}
	public EmailManageDao getEmailDao() {
		return emailDao;
	}

	public void setEmailDao(EmailManageDao emailDao) {
		this.emailDao = emailDao;
	}

	/**
	 * 配置分组
	 */
	public void configGroup(EmailManageVO emailVO, List<String> groups) {
		//更新
		if(null != groups && 0 < groups.size()){
			// 删除分组
			emailDao.delete(emailVO);
			// 插入分组
			List<EmailManageVO> userRoles = new ArrayList<EmailManageVO>();
			for(String group : groups){
				EmailManageVO userRole = new EmailManageVO();
				userRole.setEntryId(emailVO.getEntryId());
				userRole.setPkId(emailVO.getPkId());
				userRole.setGroupCode(group);
				userRoles.add(userRole);
			}
			emailDao.batchInsert(userRoles);
		}
		
	}

	/**
	 * 运行状态停止
	 */
	public void updateRunstate(EmailManageVO emailVO) {
		emailDao.updateRunstate(emailVO);		
	}

	

	

}
