package com.hanthink.gps.mail.job;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hanthink.gps.mail.service.MainPoolService;
import com.hanthink.gps.mail.vo.MailMsgTimerVo;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.logger.LogUtil;

/**
 * 
 * @author anMin
 * @createDate 2016-07-03
 * @createFor 主定时器，查询没有关闭的定时器，创建新job
 * 
 */
public class MainPoolJob extends QuartzJobBean {
	
	
	@Override
	protected void executeInternal(JobExecutionContext jobContext)
			throws JobExecutionException {
		
		try{
	        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
	
			List<MailMsgTimerVo> mailTimerVoList = null;
			//上下文
			ApplicationContext applicationContext = (ApplicationContext) jobContext.getJobDetail().getJobDataMap().get("applicationContext");
			MainPoolService service = (MainPoolService) applicationContext.getBean("mainPoolService");

        	//查询定时器
			MailMsgTimerVo qtimer = new MailMsgTimerVo();
			qtimer.setAppType(Constants.CUR_APP_TYPE);
        	mailTimerVoList = service.queryMailTimerList(qtimer);
        	
           //循环创建定时器执行
        	for(int i=0;i<mailTimerVoList.size();i++){
        		Date currentDate = new Date();

        		MailMsgTimerVo	mailTimerVo = new MailMsgTimerVo();
        		MailMsgTimerVo	updatemMailTimerVo = new MailMsgTimerVo();

        		mailTimerVo=mailTimerVoList.get(i);
        		
                //所有正在运行的定时器
                JobDetail jobDetail = scheduler.getJobDetail(mailTimerVo.getTimerName(),mailTimerVo.getTimerGroup());
                
                //如果 定时器不存在且状态为启用（RunState）则创建定时器
                if (jobDetail == null && "1".equals(mailTimerVo.getRunState())) {

                	CronTrigger trigger = new CronTrigger(mailTimerVo.getTimerName(), null, mailTimerVo.getTimerParam());
                    
                    if (mailTimerVo.getStartTime() != null) {
                        trigger.setStartTime(mailTimerVo.getStartTime());
                    }
                    
                    jobDetail = new JobDetail(mailTimerVo.getTimerName(), mailTimerVo.getTimerGroup(), Class.forName(mailTimerVo.getRunCode()));
                    jobDetail.getJobDataMap().put("ifCode", mailTimerVo.getTimerCode());
                    jobDetail.getJobDataMap().put("jobName", mailTimerVo.getTimerGroup());
                    jobDetail.getJobDataMap().put("jobClass", mailTimerVo.getRunCode());
                    jobDetail.getJobDataMap().put(Constants.TIMER_JOB_ID, mailTimerVo.getId());
                    jobDetail.getJobDataMap().put("scheduler", scheduler);
                    scheduler.scheduleJob(jobDetail, trigger);
                 
                    
                    //更新定时器
                    updatemMailTimerVo.setUpdateState(Constants.NO);
                    updatemMailTimerVo.setNextRunTime(new Timestamp(trigger.getNextFireTime().getTime()));
                    updatemMailTimerVo.setId(mailTimerVo.getId());
                    service.updateMailTimer(updatemMailTimerVo);
                    
                } 
                //如果 定时器存在且状态为关闭（RunState）则删除定时器
                else if (jobDetail != null && "0".equals(mailTimerVo.getRunState())) {
                    //关闭
                    scheduler.deleteJob(jobDetail.getName(),jobDetail.getGroup());
//                    System.out.println("删除"+mailTimerVo.getTimerCode());
                    LogUtil.info("删除定时任务：" + mailTimerVo.getTimerCode() + "," + mailTimerVo.getTimerName());
                    //更新定时器
                    updatemMailTimerVo.setUpdateState(Constants.NO);
                    updatemMailTimerVo.setNextRunTime(null);
                    updatemMailTimerVo.setId(mailTimerVo.getId());
                    service.updateMailTimer(updatemMailTimerVo);

                }
                //如果 定时器存在且状态为启用（RunState）且更新标识（UpdateState）为1则更新定时器
                else if (jobDetail != null&& (("1".equals(mailTimerVo.getRunState()) && "1".equals(mailTimerVo.getUpdateState()))
                                || (mailTimerVo.getNextRunTime() != null && mailTimerVo.getNextRunTime().getTime() < new Date(currentDate.getTime() - Constants.TIMER_DATE_OVER).getTime()))) {
                    //关闭
                    scheduler.deleteJob(jobDetail.getName(),jobDetail.getGroup());
//                    System.out.println("删除"+mailTimerVo.getTimerCode());
                    LogUtil.info("删除定时任务：" + mailTimerVo.getTimerCode() + "," + mailTimerVo.getTimerName());

                    CronTrigger trigger = new CronTrigger(mailTimerVo.getTimerName(), null, mailTimerVo.getTimerParam());

                    if (mailTimerVo.getStartTime() != null) {
                        trigger.setStartTime(mailTimerVo.getStartTime());
                    }

                    JobDetail jobDetailUp = new JobDetail(mailTimerVo.getTimerName(), mailTimerVo.getTimerGroup(), Class.forName(mailTimerVo.getRunCode()));
                    jobDetailUp.getJobDataMap().put("ifCode", mailTimerVo.getTimerCode());
                    jobDetailUp.getJobDataMap().put("jobName", mailTimerVo.getTimerGroup());
                    jobDetailUp.getJobDataMap().put("jobClass", mailTimerVo.getRunCode());
                    jobDetailUp.getJobDataMap().put(Constants.TIMER_JOB_ID, mailTimerVo.getId());
                    jobDetailUp.getJobDataMap().put("scheduler", scheduler);
                    scheduler.scheduleJob(jobDetailUp, trigger);
                    
                    //更新定时器
                    updatemMailTimerVo.setUpdateState(Constants.NO);
                    updatemMailTimerVo.setNextRunTime(new Timestamp(trigger.getNextFireTime().getTime()));
                    updatemMailTimerVo.setId(mailTimerVo.getId());

                    service.updateMailTimer(updatemMailTimerVo);
                    
                }
//                    else if ("0".equals(mailTimerVo.getRunState())){
//                	
//                	//更新定时器
//                	updatemMailTimerVo.setUpdateState(Constants.NO);
//                    updatemMailTimerVo.setNextRunTime(null);
//                    updatemMailTimerVo.setId(mailTimerVo.getId());
//
//                    service.updateMailTimer(updatemMailTimerVo);
//                    
//                }
                
            }

        	//开启调度
            scheduler.start();

		}catch (Exception e) {
//			System.err.println(e);
			LogUtil.error("主定时器异常。。。");
			LogUtil.error(e);
		}
   
	}

}
