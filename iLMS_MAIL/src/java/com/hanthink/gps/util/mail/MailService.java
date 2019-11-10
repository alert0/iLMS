package com.hanthink.gps.util.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hanthink.gps.util.exception.IOException;
import com.hanthink.gps.util.logger.LogUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 发送邮件的服务类
 * 
 * @author zhangyingchun
 * 
 */
public class MailService {
	/** FreeMarker模板 */
	private FreeMarkerConfigurer freeMarkerConfigurer;
	/** Velocity模板 */
	private VelocityEngine velocityEngine;
	/** mail发送类(使用spring的javamail注入类) */
	private MailSender mailSender;
	/** 具有异步执行功能的方法 */
	private TaskExecutor taskExecutor;

	/**
	 * 通过模板产生邮件正文(不推荐使用)
	 * 
	 * @param templateName
	 *            邮件模板名称
	 * @param map
	 *            模板中要填充的对象
	 * @return 邮件正文（HTML）
	 */
	@Deprecated
	public String generateEmailContentByVilocity(String templateName,
			Map<String, Object> map) {
		// 使用Vilocity模板
		try {
			return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
					templateName, map);
		} catch (VelocityException e) {
		}

		return null;
	}

	/**
	 * 通过模板产生邮件正文(不带附件发送功能)
	 * 
	 * @param templateName
	 *            邮件模板名称
	 * @param map
	 *            模板中要填充的对象
	 * @return 邮件正文（HTML）
	 * @throws java.io.IOException
	 */
	public String generateEmailContentByFreeMarker(String templateName,
			Map<String, Object> map) {
		// 使用FreeMaker模板
		try {
			Configuration configuration = freeMarkerConfigurer
					.getConfiguration();
			Template t;
			try {
				t = configuration.getTemplate(templateName);
				return FreeMarkerTemplateUtils
						.processTemplateIntoString(t, map);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送邮件
	 * 
	 * @param emailAddress
	 *            收件人Email地址的数组
	 * @param fromEmail
	 *            寄件人Email地址, null为默认寄件人web@vnvtrip.com
	 * @param bodyText
	 *            邮件正文
	 * @param subject
	 *            邮件主题
	 * @param attachmentName
	 *            附件名
	 * @param resource
	 *            附件
	 * @throws Exception 
	 */
	private void sendMessage(SimpleMailMessage msg, String bodyText,
			String[] attachmentName, String[] resource)
			throws Exception {
		MimeMessage mimeMsg = ((JavaMailSenderImpl) mailSender)
				.createMimeMessage();
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "GBK");
		helper.setTo(msg.getTo());
		
		String[] bccArr = msg.getBcc();
		if(null != bccArr){
			helper.setBcc(bccArr);
		}
		String [] ccArr = msg.getCc();
		if(null != ccArr){
			helper.setCc(ccArr);
		}
		
		helper.setFrom(msg.getFrom());
		helper.setText(bodyText, true);
		helper.setSubject(msg.getSubject());

		if (attachmentName != null && resource != null
				&& attachmentName.length == resource.length)
			for (int i = 0; i < resource.length; i++) {
				FileSystemResource file = new FileSystemResource(new File(
						resource[i]));
				// helper.addInline("identifier1234", res);
				helper.addAttachment(MimeUtility.encodeWord(attachmentName[i]),
						file);
			}
		try {
			((JavaMailSenderImpl) mailSender).send(mimeMsg);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}

	/**
	 * 发送简单邮件
	 * 
	 * @param msg
	 */
	public void send(SimpleMailMessage msg) {
		try {
			((JavaMailSenderImpl) mailSender).send(msg);
		} catch (MailException ex) {
		}
	}

	/**
	 * 异步发送带有附件的邮件
	 * 
	 * @param msg 多媒体消息
	 * 
	 * @param model 模板中对应的key和value键值对
	 * 
	 * @param templateName 模板名称
	 * 
	 * @param attachmentName 附件名(数组)
	 * 
	 * @param resource 资源地址(数组)
	 */
	public boolean sendAsyncMail(final SimpleMailMessage msg,
			final String templateName, final Map<String, Object> model,
			final String[] attachmentName, final String[] resource) {

		
		// 为了防止同时发送时候导致的错误,复制一份SimpleMailMessage
		final SimpleMailMessage copyMsg = new SimpleMailMessage();
		
		copyMsg.setFrom(msg.getFrom());
		copyMsg.setTo(msg.getTo()); 
		
		String[] bccArr = msg.getBcc();
		if(null != bccArr){
			copyMsg.setBcc(bccArr);
		}
		String [] ccArr = msg.getCc();
		if(null != ccArr){
			copyMsg.setCc(ccArr);
		}
		
		copyMsg.setSubject(msg.getSubject());
		
		
		// 收件人地址不能为空
		if (copyMsg.getTo() == null) {
			LogUtil.warn("收件人地址为空");
			return false;
		}
		// 发件人地址不能为空
		if (copyMsg.getFrom() == null) {
			LogUtil.warn("发件人地址为空");
			return false;
		}
		// 邮件内容不能为空
		final String contextText = generateEmailContentByFreeMarker(
				templateName, model);
		if (contextText == null) {
			LogUtil.warn("模板文件："+templateName);
			LogUtil.warn("model数据："+model);
			LogUtil.warn("邮件内容为空");
			return false;
		}
		// 主题为空的情况下,设置默认标题为无题
		if (copyMsg.getSubject() == null) {
			copyMsg.setSubject("无题");
		}
//		taskExecutor.execute(new Runnable() {
//			public void run() {
//				try {
//					try {
//						sendMessage(copyMsg, contextText, attachmentName, resource);
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//						LogUtil.error(e);
//					}
//				} catch (MessagingException e) {
//					LogUtil.error(e);
//				}
//			}
//		});
		try {
			sendMessage(copyMsg, contextText, attachmentName, resource);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e);
			return false;
		}
		return true;
	}

	/**
	 * 使用模版发送HTML格式的邮件
	 * 
	 * @param msg
	 *            装有to,from,subject信息的SimpleMailMessage
	 * @param templateName
	 *            模版名,模版根路径已在配置文件定义于freemakarengine中
	 * @param model
	 *            渲染模版所需的数据
	 */
	@SuppressWarnings("unused")
	private void send(SimpleMailMessage msg, String templateName,
			Map<String, Object> model) {
		// 生成html邮件内容
		String content = generateEmailContentByFreeMarker(templateName, model);
		MimeMessage mimeMsg = null;
		try {
			mimeMsg = ((JavaMailSenderImpl) mailSender).createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true,
					"UTF-8");
			helper.setTo(msg.getTo());
			if (msg.getSubject() != null) {
				helper.setSubject(msg.getSubject());
			} else {
				helper.setSubject("无题");
			}
			if (msg.getFrom() != null)
				helper.setFrom(msg.getFrom());
			helper.setText(content, true);
			((JavaMailSenderImpl) mailSender).send(mimeMsg);
		} catch (MessagingException ex) {
		}
	}

	/**
	 * 设定freeMarkerConfigurer
	 * 
	 * @param freeMarkerConfigurer
	 *            freeMarkerConfigurer
	 */
	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	/**
	 * 设定velocityEngine
	 * 
	 * @param velocityEngine
	 *            velocityEngine
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * 设定mailSender
	 * 
	 * @param mailSender
	 *            mailSender
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 设定taskExecutor
	 * 
	 * @param taskExecutor
	 *            taskExecutor
	 */
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
