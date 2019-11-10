package com.hanthink.gps.mail.service.impl;

import java.util.List;

import com.hanthink.gps.mail.dao.MainPoolDao;
import com.hanthink.gps.mail.service.MainPoolService;
import com.hanthink.gps.mail.vo.MailMsgGroupUserVo;
import com.hanthink.gps.mail.vo.MailMsgGroupVo;
import com.hanthink.gps.mail.vo.MailMsgTimerVo;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
/**
 * 
 * @author anMin
 * @createDate 2016-07-03
 * @createFor 主定时器ServiceImpl
 * 
 * @update  zuosl  2016-08-01  增加查询邮件接收人员方法
 * 
 */
public class MainPoolServiceImpl extends BaseServiceImpl implements MainPoolService {
	
	private MainPoolDao mainPoolDao;

	public MainPoolDao getMainPoolDao() {
		return mainPoolDao;
	}

	public void setMainPoolDao(MainPoolDao mainPoolDao) {
		this.mainPoolDao = mainPoolDao;
	}

	/**
	 * @userFor  定时器查询
	 * @return
	 * @author anMin
	 * @since 2016-06-27
	 */
	public List<MailMsgTimerVo> queryMailTimerList(MailMsgTimerVo qtimer) {
		
		return mainPoolDao.queryMailTimerListDao(qtimer);
	}

	/**
	 * @userFor  定时器更新
	 * @return
	 * @author anMin
	 * @since 2016-06-27
	 */
	public int updateMailTimer(MailMsgTimerVo mailTimerVo) {
		return mainPoolDao.updateMailTimerDao(mailTimerVo);		
	}

	/**
	 * 根据分组代码查询分组成员
	 * @param groupVO
	 * @return
	 * @author zuosl 2016-7-4
	 */
	@Override
	public List<MailMsgGroupUserVo> queryGroupUserByGroupCode(
			MailMsgGroupVo groupVO) {
		return mainPoolDao.queryGroupUserByGroupCode(groupVO);
	}

	/**
	 * 根据定时器ID查询其邮件接收人信息
	 * @param timerId
	 * @return
	 * @author zuosl 2016-7-28
	 */
	@Override
	public List<MailMsgGroupUserVo> queryTimerSendUserByTimerId(Integer timerId) {
		return mainPoolDao.queryTimerSendUserByTimerId(timerId);
	}

	@Override
	public List<MailMsgGroupUserVo> queryCSEmailUserInfo(
			MailMsgGroupUserVo model) {
		return mainPoolDao.queryCSEmailUserInfo(model);
	}

	@Override
	public List<MailMsgGroupUserVo> queryZCCSEmailAddress(
			MailMsgGroupUserVo model) {
		return mainPoolDao.queryZCCSEmailAddress(model);
	}
	

	
}
