package com.hanthink.gps.mail.job;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.vo.QueueVO;

/**
 * @Desc    : 使用类型为"JOB_CLASS"的消息队列时实现本接口
 * @FileName: QueueJobClassBase.java 
 * @CreateOn: 2016-7-1 下午04:11:55
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-1		V1.0		zuosl		新建
 * 
 *
 */
public interface QueueJobClassBase {

	/**
	 * 任务执行方法
	 * @param jobContext 
	 * @param queueVO 队列信息
	 * @author zuosl 2016-7-1
	 */
	public void jobRun(JobExecutionContext jobContext, QueueVO queueVO);
	
}
