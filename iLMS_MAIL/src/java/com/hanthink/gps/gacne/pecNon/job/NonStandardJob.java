package com.hanthink.gps.gacne.pecNon.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.pecNon.service.NonStandardService;
import com.hanthink.gps.gacne.pecNon.vo.NonStandardVo;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class NonStandardJob extends BaseJob{
	
	private static final String NON_STANDARD_BEAN_PEC = "NonStandardServicePEC";
	
	private static final String DATE_COUNT = "DATE_COUNNT";  //定制化订单提醒时间间隔
	private NonStandardService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if (null == service) {
			service = (NonStandardService) SpringContextUtil.getBean(NON_STANDARD_BEAN_PEC);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer dateCount = service.getDateCount(DATE_COUNT);    //从系统参数中查询出定制化订单提醒时间间隔
		if (null == dateCount ) {
			dateCount = 0;
		}
		map.put("dateCount", dateCount);
		List<NonStandardVo> list = service.getNonStandardCount(map);

		// 查询发送邮件人信息
		String[] toArr = this.queryTimerEmailAddress();
		if (null == toArr || 0 >= toArr.length) {
			LogUtil.info("定制化订单提醒的人员");
			return;
		}
		if (null != list && list.size() > 0) {

			// 发送邮件
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("count", list.size());
			model.put("date", (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())));
			boolean sendFlg = MailUtil.sendMail("nonStandarCheck.ftl", "定制化订单检查提醒", toArr, null, null, model, null, null);
			LogUtil.info("定制化订单检查提醒,发送状态：" + sendFlg);
			if (sendFlg) {
				service.updateEmailFlag(map);
			}
		}
	}

	public NonStandardService getService() {
		return service;
	}

	public void setService(NonStandardService service) {
		this.service = service;
	}

}
