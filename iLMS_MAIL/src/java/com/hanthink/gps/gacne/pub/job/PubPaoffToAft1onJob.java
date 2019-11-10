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
 * @Title: PubPaoffToAft1onJob.java
 * @Package: com.hanthink.gps.gacne.pub.job
 * @Description: PA-OFF_AF-T1-ON区间流动数邮件提醒
 * @author dtp
 * @date 2019-7-17
 */
public class PubPaoffToAft1onJob extends BaseJob{

	//通廊流动数比较基数
	
	private final static Integer FLOW_NUM = 60;
	
	private final static String FACTORY_CODE = "2000";
	private PubOrderAlertService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (PubOrderAlertService)SpringContextUtil.getBean("pubOrderAlertService");
		}
		//查询条件
		PubOrderVO model = new PubOrderVO();
		model.setFactoryCode(FACTORY_CODE);
		
		//根据MM_PUB_VEH_PASS判断是否发送邮件
		List<PubOrderVO> list_isSend = service.queryIsSendEmail(model);
		if(null == list_isSend || list_isSend.size() <= 0){
			LogUtil.info("低流动数提醒:最近没有过点的车辆");
			return;
		}
		
		List<PubOrderVO> list = service.queryPubPaoffToAft1onJob(model);
		if(null == list || 0 >= list.size()){
			LogUtil.info("没有通廊流动数<60提示信息");
			return;
		}
		
		//查询发送邮件人信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收通廊流动数<60提示的人员");
			return;
		}
		
		//通过前两行进行比较
		if(list.size() == 2){
			PubOrderVO m1 = list.get(0);
			PubOrderVO m2 = list.get(1);
			Integer m1_num = Integer.valueOf(m1.getPaoffAft1onNum());
			//Integer m2_num = Integer.valueOf(m2.getPaoffAft1onNum());
			//"是否已发送邮件"标识为0
			if("0".equals(m1.getIsSendEmail())){
				//m1<60   邮件通知提醒流动数低
				if(m1_num < PubPaoffToAft1onJob.FLOW_NUM){
					//拼接邮件发送信息
					StringBuffer emailInfo = new StringBuffer();
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("截止");
					emailInfo.append(m1.getSysdateTime());
					emailInfo.append("</span><br/><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("PA OFF~AF ON流动数为：");
					emailInfo.append(m1.getPaoffAfonNum());
					emailInfo.append("台");
					emailInfo.append("</span><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("AF ON~AF T1 ON流动数为：");
					emailInfo.append(m1.getAfonAft1onNum());
					emailInfo.append("台");
					emailInfo.append("</span><br/><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("合计流动数：");
					emailInfo.append(m1.getPaoffAft1onNum());
					emailInfo.append("台，低于标准值");
					emailInfo.append("</span>");
					
					//发送邮件
					Map<String, Object> model_s = new HashMap<String, Object>();
					model_s.put("info", emailInfo.toString());
					boolean sendFlg = MailUtil.sendMail("common.ftl", 
							"通廊低于60台异常提醒", 
							toArr, null, null, model_s, null, null);
					//更新是否已发送邮件标识，发送通知后，将标识设为1
					PubOrderVO vo = new PubOrderVO();
					vo.setIsSendEmail("1");
					Integer res = service.updateEmailSendFlag(vo);
					LogUtil.info("PA-OFF至AF-T1-ON提醒,发送状态："+sendFlg);
				}
			}else{
				//m1>60   邮件通知流动数恢复
				if(m1_num > PubPaoffToAft1onJob.FLOW_NUM){
					StringBuffer emailInfo = new StringBuffer();
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("截止");
					emailInfo.append(m1.getSysdateTime());
					emailInfo.append("</span><br/><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("PA OFF~AF ON流动数为：");
					emailInfo.append(m1.getPaoffAfonNum());
					emailInfo.append("台");
					emailInfo.append("</span><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("AF ON~AF T1 ON流动数为：");
					emailInfo.append(m1.getAfonAft1onNum());
					emailInfo.append("台");
					emailInfo.append("</span><br/><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("合计流动数：");
					emailInfo.append(m1.getPaoffAft1onNum());
					emailInfo.append("台，恢复标准值以上");
					emailInfo.append("</span>");
					
					//发送邮件
					Map<String, Object> model_s = new HashMap<String, Object>();
					model_s.put("info", emailInfo.toString());
					boolean sendFlg = MailUtil.sendMail("common.ftl", 
							"通廊恢复60台以上提醒", 
							toArr, null, null, model_s, null, null);
					//更新是否已发送邮件标识，发送恢复通知后，将标识设为0
					PubOrderVO vo = new PubOrderVO();
					vo.setIsSendEmail("0");
					Integer res = service.updateEmailSendFlag(vo);
					LogUtil.info("PA-OFF至AF-T1-ON提醒,发送状态："+sendFlg);
				}else if(m1_num < PubPaoffToAft1onJob.FLOW_NUM){
					//拼接邮件发送信息
					StringBuffer emailInfo = new StringBuffer();
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("截止");
					emailInfo.append(m1.getSysdateTime());
					emailInfo.append("</span><br/><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("PA OFF~AF ON流动数为：");
					emailInfo.append(m1.getPaoffAfonNum());
					emailInfo.append("台");
					emailInfo.append("</span><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("AF ON~AF T1 ON流动数为：");
					emailInfo.append(m1.getAfonAft1onNum());
					emailInfo.append("台");
					emailInfo.append("</span><br/><br/>");
					emailInfo.append("<span style=\"color:red;\">");
					emailInfo.append("合计流动数：");
					emailInfo.append(m1.getPaoffAft1onNum());
					emailInfo.append("台，低于标准值");
					emailInfo.append("</span>");
					
					//发送邮件
					Map<String, Object> model_s = new HashMap<String, Object>();
					model_s.put("info", emailInfo.toString());
					boolean sendFlg = MailUtil.sendMail("common.ftl", 
							"通廊低于60台异常提醒", 
							toArr, null, null, model_s, null, null);
					//更新是否已发送邮件标识，发送通知后，将标识设为1
					PubOrderVO vo = new PubOrderVO();
					vo.setIsSendEmail("1");
					Integer res = service.updateEmailSendFlag(vo);
					LogUtil.info("PA-OFF至AF-T1-ON提醒,发送状态："+sendFlg);
				}
			}
		}
		
	}

}
