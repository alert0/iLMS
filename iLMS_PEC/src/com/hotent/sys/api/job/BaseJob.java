package com.hotent.sys.api.job;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.sys.api.model.SysJobLog;

@DisallowConcurrentExecution
public abstract class BaseJob implements Job {

	//抽象方法。
	public abstract void executeJob(JobExecutionContext context) throws Exception;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//日志记录
		String jobName=context.getJobDetail().getKey().getName();
		Boolean trans = false;
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		//是否加上事务控制
		Object transObj = jobDataMap.get("transaction");
		if(BeanUtils.isNotEmpty(transObj)){
			trans = Boolean.parseBoolean(transObj.toString());
		}
		String trigName="directExec";
		Trigger trig=context.getTrigger();
		if(trig!=null)
			trigName=trig.getKey().getName();
		Date strStartTime=new Date();
		long startTime=System.currentTimeMillis();
		ResourceTransactionManager transactionManager = null;
		TransactionStatus status = null;
		if(trans){
			transactionManager = (ResourceTransactionManager)AppUtil.getBean("transactionManager");
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);  
			status = transactionManager.getTransaction(def);  
		}
		try{
			executeJob(context);
			long endTime=System.currentTimeMillis();
			Date strEndTime=new Date();
			//记录日志
			long runTime=(endTime-startTime) /1000;
			addLog(jobName, trigName, strStartTime, strEndTime, runTime, "任务执行成功!", "1");
			if(trans){
				transactionManager.commit(status);
			}
		}
		catch(Exception ex)
		{
			if(trans){
				transactionManager.rollback(status);
			}
			long endTime=System.currentTimeMillis();
			Date strEndTime=new Date();
			long runTime=(endTime-startTime) /1000;
			try {
				String rootCause = ExceptionUtils.getRootCauseMessage(ex);
				addLog(jobName, trigName, strStartTime, strEndTime, runTime, rootCause, "0");
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	private void addLog(String jobName, String trigName, Date startTime,
			Date endTime, long runTime,String content,String state) throws Exception {
		IJobLogService logService=AppUtil.getBean(IJobLogService.class);

		SysJobLog jobLog=new SysJobLog(jobName, trigName, startTime, endTime, content, state,runTime);

		String id=UniqueIdUtil.getSuid();
		jobLog.setId(id);
		logService.create(jobLog);
	}

}
