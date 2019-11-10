package com.hanthink.gps.mail.dao.impl;

import java.util.List;

import com.hanthink.gps.mail.dao.MainPoolDao;
import com.hanthink.gps.mail.vo.MailMsgGroupUserVo;
import com.hanthink.gps.mail.vo.MailMsgGroupVo;
import com.hanthink.gps.mail.vo.MailMsgTimerVo;
import com.hanthink.gps.pub.dao.BaseDaoSupport;
/**
 * 
 * @author anMin
 * @createDate 2016-07-03
 * @createFor 主定时器DaoImpl
 * 
 * @update  zuosl  2016-08-01  增加查询邮件接收人员方法
 */
public class MainPoolDaoImpl extends BaseDaoSupport implements MainPoolDao {

	/**
	 * @userFor  定时器查询
	 * @return
	 * @author anMin
	 * @since 2016-06-27
	 */
	@SuppressWarnings("unchecked")
	public List<MailMsgTimerVo> queryMailTimerListDao(MailMsgTimerVo qtimer) {
		return (List<MailMsgTimerVo>) this.executeQueryForList("mail.queryMailTimerListDaoSql", qtimer);
	}


	/**
	 * @userFor  定时器更新
	 * @return
	 * @author anMin
	 * @since 2016-06-27
	 */
	public int updateMailTimerDao(MailMsgTimerVo mailTimerVo) {
		return executeUpdate("mail.updateMailTimerSql",mailTimerVo);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MailMsgGroupUserVo> queryGroupUserByGroupCode(
			MailMsgGroupVo groupVO) {
		return (List<MailMsgGroupUserVo>) this.executeQueryForList("mail.select_queryGroupUserByGroupCode", groupVO);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MailMsgGroupUserVo> queryTimerSendUserByTimerId(Integer timerId) {
		return (List<MailMsgGroupUserVo>) this.executeQueryForList("mail.select_queryTimerSendUserByTimerId", timerId);
	}

	/**
	 * 查询抄送邮箱
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MailMsgGroupUserVo> queryCSEmailUserInfo(
			MailMsgGroupUserVo model) {
		return (List<MailMsgGroupUserVo>) this.executeQueryForList("mail.select_queryCSEmailUserInfo", model);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MailMsgGroupUserVo> queryZCCSEmailAddress(
			MailMsgGroupUserVo model) {
		return (List<MailMsgGroupUserVo>) this.executeQueryForList("mail.select_queryZCCSEmailAddress", model);
	}

	

}
