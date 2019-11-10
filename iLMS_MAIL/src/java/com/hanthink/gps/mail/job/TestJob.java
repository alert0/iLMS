package com.hanthink.gps.mail.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.util.logger.LogUtil;

public class TestJob  extends BaseJob{
	
	private static int count = 0;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
//		MailMulServeice send = MailMulServeice.getInstance();
//		String to[] = { "jinquan.chen@hanthink.com" };
//		String cs[] = { "shilai.zuo@hanthink.com","haowen.tan@hanthink.com","beibei.ding@hanthink.com" };
//		String ms[] = null;
//		String subject = "测试一下";
//		String content = "这是邮件内容，仅仅是测试，不需要回复(逗)";
//		String formEmail = "anminht@126.com";
//		String[] arrArchievList = new String[1];
//		arrArchievList[0] = "c:\\li.png";
//		LogUtil.info("发邮件:");
		// 2.保存多个附件
//		send.send(to, cs, ms, subject, content, formEmail, arrArchievList);
		
		count ++;
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LogUtil.info("任务测试:" + sdf.format(new Date()));		
		LogUtil.info("这是第【" + count + "】次执行");
		if(count % 5 == 0){
			LogUtil.info("假设出现一个异常");
			double d = Double.valueOf("asdf");
			System.out.println(d);
		}
		LogUtil.info("-----------------------------");
	}
}
