package com.hanthink.gps.mail.service;

import java.util.List;

import com.hanthink.gps.mail.vo.MailMsgGroupUserVo;
import com.hanthink.gps.mail.vo.MailMsgGroupVo;
import com.hanthink.gps.mail.vo.MailMsgTimerVo;
import com.hanthink.gps.pub.service.BaseService;
/**
 * 
 * @author anMin
 * @createDate 2016-07-03
 * @createFor 主定时器Service
 * 
 * @update  zuosl  2016-08-01  增加查询邮件接收人员方法
 * 
 */
public interface MainPoolService extends BaseService{

	/**
	 * @userFor  定时器查询
	 * @return
	 * @author anMin
	 * @since 2016-06-27
	 */
	public List<MailMsgTimerVo> queryMailTimerList(MailMsgTimerVo qtimer);
	
	/**
	 * @userFor  定时器更新
	 * @return
	 * @author anMin
	 * @since 2016-06-27
	 */
	public int updateMailTimer(MailMsgTimerVo mailTimerVo);
	
	
	/**
	 * 根据分组代码查询分组成员
	 * @param groupVO
	 * @return
	 * @author zuosl 2016-7-4
	 */
	public List<MailMsgGroupUserVo> queryGroupUserByGroupCode(MailMsgGroupVo groupVO);

	/**
	 * 根据定时器ID查询其邮件接收人信息
	 * @param timerId
	 * @return
	 * @author zuosl 2016-7-28
	 */
	public List<MailMsgGroupUserVo> queryTimerSendUserByTimerId(Integer timerId);

	/**
	 * 查询抄送邮箱
	 * @param model
	 * @return
	 */
	public List<MailMsgGroupUserVo> queryCSEmailUserInfo(
			MailMsgGroupUserVo model);

	/**
	 * 查询资材抄送邮箱
	 * @param model
	 * @return
	 */
	public List<MailMsgGroupUserVo> queryZCCSEmailAddress(
			MailMsgGroupUserVo model);
	
}
