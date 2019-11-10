package com.hanthink.mes.common.utilities;

import java.util.Hashtable;

import com.hanthink.mes.print.model.MESPRJobQueue;

/**
 * job queue manager
 * 
 * @author Lei Pan
 * @version 2016-01-05
 */
public abstract class QueueManager {

	protected Hashtable<String, JobQueue> jobQueueTable = null;

	public QueueManager() {
		jobQueueTable = new Hashtable<String, JobQueue>();
	}

	/**
	* get job by queue key from job queue table
	* 
	* @param queueKey
	* @return MESPRJobQueue
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public MESPRJobQueue getNext(String queueKey) {
		JobQueue jobQueue = jobQueueTable.get(queueKey);
		if (jobQueue != null) {
			return jobQueue.getNextJob();
		}
		return null;
	}

	/**
	* remove first job by queue key from job queue table
	* 
	* @param queueKey void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void removeFirst(String queueKey) {
		JobQueue jobQueue = jobQueueTable.get(queueKey);
		if (jobQueue != null && jobQueue.getQueueSize() > 0) {
			jobQueue.removeNextJob();
		}
	}

	/**
	* add job to job queue table
	* 
	* @param job
	* @param queueKey void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void addJob(MESPRJobQueue job, String queueKey) {
		if (job != null) {
			JobQueue jobQueue = jobQueueTable.get(queueKey);
			if (jobQueue != null) {
				jobQueue.addJob(job);
			}
		}
	}

	/**
	* get job queue key by business and job name
	* 
	* @param business
	* @param jobName
	* @return String
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public String getQueueKey(String business, String jobName) {
		return business + "_" + jobName;
	}

	/**
	* get job queue by queue key from job queue table
	* 
	* @param queueKey
	* @return JobQueue
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public JobQueue getJobQueue(String queueKey) {
		return jobQueueTable.get(queueKey);
	}

	/**
	* get job queue table
	* 
	* @return Hashtable<String,JobQueue>
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public Hashtable<String, JobQueue> getJobQueue() {
		return jobQueueTable;
	}

	public abstract void updateJobQueue(MESPRJobQueue job, String status) throws Exception;

}
