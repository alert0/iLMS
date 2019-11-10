package com.hanthink.gps.pkg.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.pkg.service.PkgPartService;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class PkgProposalJob extends BaseJob{
	private static final String PGK_PART_BEAN_NAME_PEC = "pkgPartServicePEC";
	private static final String EMAIL_FLAG_NO = "0";
	private static final String FACTORY_CODE = "2000";
	private static final String PROPOSAL_STATUS_FQ = "1";
	private static final String EMAIL_FALG_YES = "1";
	private PkgPartService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if (null == service) {
			service = (PkgPartService) SpringContextUtil.getBean(PGK_PART_BEAN_NAME_PEC);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EMAIL_FLAG_NO", EMAIL_FLAG_NO);
		map.put("FACTORY_CODE", FACTORY_CODE);
		map.put("PROPOSAL_STATUS_FQ", PROPOSAL_STATUS_FQ);

		Integer count = service.getPkgProposalCount(map);

		// 查询发送邮件人信息
		String[] toArr = this.queryTimerEmailAddress();
		if (null == toArr || 0 >= toArr.length) {
			LogUtil.info("没有接收供应商超过回复期限提醒包装管理的人员");
			return;
		}
		if (null != count && count > 0) {

			// 发送邮件
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("count", count);
			model.put("date", (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())));
			boolean sendFlg = MailUtil.sendMail("pkgProposal.ftl", "供应商超过回复期限未进行包装信息设定提醒", toArr, null, null, model, null, null);
			LogUtil.info("供应商超过回复期限未进行包装信息设定提醒,发送状态：" + sendFlg);
//			if (sendFlg) {
//				map.put("EMAIL_FALG_YES", EMAIL_FALG_YES);
//				service.updateEmailFlagPro(map);
//			}
		}
	}

}
