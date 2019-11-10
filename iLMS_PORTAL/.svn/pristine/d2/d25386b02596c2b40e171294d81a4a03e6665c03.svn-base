package com.hotent.mini.job;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.base.core.mail.model.MailSetting;
import com.hotent.base.core.util.AppUtil;
import com.hotent.mail.persistence.manager.MailManager;
import com.hotent.mail.persistence.manager.MailSettingManager;
import com.hotent.sys.api.job.BaseJob;


public class SyncMail extends BaseJob {
	protected Logger logger = LoggerFactory.getLogger(SyncMail.class);
	@Override
	public void executeJob(JobExecutionContext context)  {
		MailManager mailManager = AppUtil.getBean(MailManager.class);
		MailSettingManager settingManager = AppUtil.getBean(MailSettingManager.class);
		List<MailSetting> mailSettings = settingManager.getAll();
		for (MailSetting mailSetting : mailSettings) {
			try {
				//获取UID列表
				List<String> uidList= mailManager.getUIDBySetId(mailSetting.getId());
				//读取邮件
				List<com.hotent.core.mail.model.Mail> mailList= mailManager.getMailListBySetting(mailSetting, uidList);
				//保存邮件
				mailManager.saveMail(mailList, mailSetting.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
