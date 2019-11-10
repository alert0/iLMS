package com.hanthink.mes.common.utilities;

import com.hanthink.mes.print.ifc.IPrintService;
import com.hanthink.mes.print.impl.PrintService;
import com.hanthink.mes.print.model.MESPRJobQueue;

/**
 * storage job worker object
 * 
 * @author Lei Pan
 * @version 2016-01-05
 */

public abstract class JobWorker {

	protected IPrintService printService = null;
	protected QueueManager queueManager = null;
	protected int maxCachedSize = 1000;
	protected String printType = null;
	protected String jobName = null;
	protected boolean jobWorkderReady = false;
	protected boolean toStop = false;
	protected boolean isStop = false;

	/**
	 * constructor job worker by job name
	 * 
	 * @param jobName
	 */
	public JobWorker(String jobName) {
		printService = new PrintService();
		this.jobName = jobName;
	}

	/**
	 * constructor job worker object by print type and job name
	 * 
	 * @param printType
	 * @param jobName
	 */
	public JobWorker(String printType, String jobName) {
		this(jobName);
		this.printType = printType;
	}

	/**
	* stop job worker thread
	* 
	*  void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void stopWorkerThread() {
		toStop = true;
	}

	/**
	* check job worker is ready
	* 
	* @return boolean
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public boolean isJobWorkderReady() {
		return jobWorkderReady;
	}

	/**
	* set job worker is ready
	* 
	* @param jobWorkderReady void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void setJobWorkderReady(boolean jobWorkderReady) {
		this.jobWorkderReady = jobWorkderReady;
	}

	/**
	* get job name
	* 
	* @return String
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public String getJobName() {
		return jobName;
	}

	/**
	* get queue manager
	* 
	* @return QueueManager
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public QueueManager getQueueManager() {
		return queueManager;
	}

	/**
	* add job to job worker the queue
	* 
	* @param job
	* @param queueKey
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-5-3
	*/
	public void addJob(MESPRJobQueue job, String queueKey) throws Exception {
		if (job == null) {
			LogUtility.error("[JobWorker.addJob] job is null .");
			return;
		}
		JobQueue jobQueue = queueManager.getJobQueue(queueKey);
		if (jobQueue == null) {
			jobQueue = new JobQueue();
			queueManager.getJobQueue().put(queueKey, jobQueue);
		} else {
			int currentQueueSize = jobQueue.getQueueSize();
			if (currentQueueSize > maxCachedSize) {
				LogUtility.warn("[JobWorker.addJob] The Job Queue is larger than the maximal, the current size is:["
								+ currentQueueSize + "]");
			}
		}
		queueManager.addJob(job, queueKey);
	}

	/**
	* check job worker is stop
	* 
	* @return boolean
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public boolean isStop() {
		return isStop;
	}

}
