package com.hanthink.gps.gacne.pub.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.pub.service.PubOrderAlertService;
import com.hanthink.gps.gacne.pub.vo.PubOrderVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Title: JitNotGroupOrderJob.java
 * @Package: com.hanthink.gps.gacne.pub.job
 * @Description: 拉动未组单需求邮件提醒
 * @author dtp
 * @date 2019-7-16
 */
public class JitNotGroupOrderJob extends BaseJob{
	
	private final static String FACTORY_CODE = "2000";
	private PubOrderAlertService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		
		if(null == service){
			service = (PubOrderAlertService)SpringContextUtil.getBean("pubOrderAlertService");
		}
		
		//设置查询参数
		PubOrderVO vo = new PubOrderVO();
		vo.setFactoryCode(FACTORY_CODE);
		
		List<PubOrderVO> list = service.queryNotGroupOrder(vo);
		if(null == list || 0 >= list.size()){
			LogUtil.info("没有未组单需求提示信息");
			return;
		}
		//查询发送邮件人信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收未组算提示的人员");
			return;
		}
		
		//拼接邮件发送信息
		StringBuffer emailInfo = new StringBuffer();
		emailInfo.append("<span style=\"color:red;\">");
		emailInfo.append("系统存在拉动未组单包装需求,请及时处理");
		emailInfo.append("</span>");
		
		//发送邮件
		Map<String, Object> model_s = new HashMap<String, Object>();
		model_s.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"拉动需求未组单提醒", 
				toArr, null, null, model_s, null, null);
		LogUtil.info("包装需求未组单提醒,发送状态："+sendFlg);
		
	}
	
	public PubOrderAlertService getService() {
		return service;
	}

	public void setService(PubOrderAlertService service) {
		this.service = service;
	}

}
