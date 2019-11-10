package com.hotent.sys.jms.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import oracle.net.aso.f;

import com.hotent.base.core.mail.model.MailAttachment;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.EmailUtil;
import com.hotent.base.core.util.PropertyUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.msg.constants.MsgType;
import com.hotent.sys.api.msg.model.DefaultMsgVo;

/**
 * 邮件消息处理器。
 * 
 * <pre>
 * 构建组：x5-sys-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-12-16-上午11:51:06
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class MailHandler implements JmsHandler<DefaultMsgVo> {
	@Override
	public String getType() {
		return MsgType.MAIL.key();
	}
	@Override
	public boolean send(DefaultMsgVo vo) {
		String fromEmail = PropertyUtil.getProperty("mail.nickName");
		List<IUser> recievers = vo.getReceivers();
		List<IUser> ccUsers = vo.getCcUsers();
		List<IUser> bccUsers = vo.getBccUsers();
		
		String strReceiver=getMailAddress(recievers);
		String strCc=getMailAddress(ccUsers);
		String strBcc=getMailAddress(bccUsers);
		
		
		
		if(StringUtil.isEmpty(strReceiver) && StringUtil.isEmpty(strCc) && StringUtil.isEmpty(strBcc)) return false;
		
		try {
			// 添加附件
			List<MailAttachment> attachmentList = null;
			Map<String, String> map = vo.getAttachments();
			if (BeanUtils.isNotEmpty(map) ) {
				attachmentList = new ArrayList<MailAttachment>();
				for (String fileName : map.keySet()) {
					MailAttachment attachment = new MailAttachment();
					attachment.setFileName(fileName);
					attachment.setFilePath(map.get(fileName));
					attachmentList.add(attachment);
				}
			}
			EmailUtil.sendEmail(strReceiver, strCc, strBcc, fromEmail, vo.getSubject(), vo.getContent(), attachmentList);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private String getMailAddress(List<IUser> recievers){
		if(BeanUtils.isEmpty(recievers)) return "";
		String toUserEmail = "";
		for (IUser iUser : recievers) {
			String email = iUser.getEmail();
			if (StringUtil.isEmpty(email))continue;
			toUserEmail+=email +",";
		}
		toUserEmail=StringUtil.trimSufffix(toUserEmail, ",");
		return toUserEmail;
	}
	
	@Override
	public String getTitle() {
		return "邮件";
	}
	@Override
	public boolean getIsDefault() {
		return false;
	}
	@Override
	public boolean getSupportHtml() {
		return true;
	}
}
