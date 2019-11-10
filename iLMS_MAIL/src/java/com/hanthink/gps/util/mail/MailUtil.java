package com.hanthink.gps.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.springframework.mail.SimpleMailMessage;

import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;

/**
 * 邮件工具类
 * @author Administrator
 *
 */
public class MailUtil {
	
	private static String MAIL_SERVICE_BEAN_NAME = "mailService";
	private static String MAIL_MESSAGE_BEAN_NAME = "mailMessage";

	/**
	 * 获取邮件服务类
	 * @return
	 * @author zuosl 2016-6-29
	 */
	public synchronized static MailService getMailService() {
		return (MailService) SpringContextUtil.getBean(MAIL_SERVICE_BEAN_NAME);
	}

	/**
	 * 获取邮件消息定义
	 * @return
	 * @author zuosl 2016-6-29
	 */
	public synchronized static SimpleMailMessage getSimpleMailMessage() {
		return (SimpleMailMessage) SpringContextUtil.getBean(MAIL_MESSAGE_BEAN_NAME);
	}
	
	

	
	/**
	 * 根据模板发送邮件
	 * @param modelString 模板名称
	 * @param subject 主题
	 * @param toArr 发送组
	 * @param ccArr 抄送组
	 * @param bccArr 密送组
	 * @param modelParam 模板参数
	 * @param attachmentName 附件名称
	 * @param resource 附件资源路径
	 * @return
	 * @author zuosl 2016-6-29
	 */
	public synchronized static boolean sendMail(String modelString,
			String subject, String[] toArr, String[] ccArr, String[] bccArr,
			Map<String, Object> modelParam,
			String[] attachmentName, String[] resource){
		
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info(subject + "收件人为空;");
			return false;
		}
		
		MailService mailService = getMailService();
		SimpleMailMessage message = getSimpleMailMessage();
		
		message.setTo(toArr);
		message.setCc(ccArr);
		message.setBcc(bccArr);
//		if(null != ccArr && 0 < ccArr.length){
//			message.setCc(ccArr);
//		}
//		if(null != bccArr && 0 < bccArr.length){
//			message.setBcc(bccArr);
//		}
		message.setSubject(subject);
		return mailService.sendAsyncMail(message, modelString, modelParam, attachmentName, resource);
	}
	
	
	/**
	 * 根据收件人姓名和邮箱地址拼接收件地址信息
	 * @param name
	 * @param email
	 * @return
	 * @author zuosl 2016-7-28
	 */
	public synchronized static String getEmailAddress(String name, String email){
		if(StringUtil.isNullOrEmpty(email)){
			return null;
		}
		StringBuffer buf = new StringBuffer();
		if(!StringUtil.isNullOrEmpty(name)){
			try {
				buf.append(MimeUtility.encodeText(name));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return email;
			}
			buf.append("<");
			buf.append(email);
			buf.append(">");
			
			return buf.toString();
		}else{
			return email;
		}
		
	}

}
