package com.hanthink.mes.common.utilities;

import java.util.ArrayList;
import com.hanthink.mes.print.model.MESPRJobQueue;

/**
 * job queue
 * 
 * @author Lei Pan
 * @version 2016-01-05
 * 
 */

public class JobQueue {

	protected ArrayList<MESPRJobQueue> jobList = new ArrayList<MESPRJobQueue>();

	public JobQueue() {

	}

	/**
	* get next job from job queue
	* 
	* @return MESPRJobQueue
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public MESPRJobQueue getNextJob() {
		if (jobList.size() > 0) {
			return jobList.get(0);
		}
		return null;
	}

	/**
	* add job to job queue
	* 
	* @param job void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void addJob(MESPRJobQueue job) {
		if (job != null) {
			jobList.add(job);
		} else {
			LogUtility.info("[JobQueue.addJob] job is null!");
		}
	}

	/**
	* remove next job from job queue
	*  void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void removeNextJob() {
		if (jobList.size() > 0) {
			jobList.remove(0);
		}
	}

	/**
	* get job queue size
	* 
	* @return int
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public int getQueueSize() {
		if (jobList != null) {
			return jobList.size();
		}
		return 0;
	}
}
