package com.hanthink.mes.common.utilities;

import java.util.Hashtable;

import com.hanthink.mes.print.ifc.IPrintService;
import com.hanthink.mes.print.impl.PrintService;
import com.hanthink.mes.print.model.MESPRJobQueue;

/**
 * all job worker manager
 * 
 * @author Lei Pan
 * @version 2016-01-05
 */

public abstract class JobWorkerManager {

	protected IPrintService printService = null;
	protected String printType = null;
	protected Hashtable<String, JobWorker> jobWorkerTable = new Hashtable<String, JobWorker>();

	public JobWorkerManager() {
		printService = new PrintService();
	}

	public JobWorkerManager(String printType) {
		this();
		this.printType = printType;
	}

	/**
	* add job queue to job worker 
	* 
	* @param job
	* @param workerKey
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void addJob(MESPRJobQueue job, String workerKey) throws Exception {
		if (job != null) {
			JobWorker jobWorker = jobWorkerTable.get(workerKey);
			if (jobWorker != null) {
				jobWorker.addJob(job, workerKey);
			}
		}
	}

	/**
	* get job worker key by job name and job type
	* 
	* @param jobName
	* @param jobType
	* @return String
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public String getWorkerKey(String jobName, String jobType) {
		return jobName + "_" + jobType;
	}

	/**
	* get job Worker by job worker key  from job worker table
	* 
	* @param workerKey
	* @return JobWorker
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public JobWorker getWorkerByKey(String workerKey) {
		return jobWorkerTable.get(workerKey);
	}

	/**
	* start job worker
	* 
	* @param workerKey
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public abstract void startJobWorker(String workerKey) throws Exception;

	/**
	* stop the job worker
	* 
	* @param printerKey
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public abstract void stopJobWorker(String printerKey) throws Exception;

	/**
	* start all job worker
	* 
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public abstract void startAllJobWorker() throws Exception;

	/**
	* stop all job worker
	* 
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public abstract void stopAllJobWorker() throws Exception;

	/**
	* wait job worker started
	* 
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public abstract void waitJobWorkerStarted() throws Exception;

	/**
	* reload init job to job queue
	* 
	* @param jobType
	* @param businessName
	* @param jobWorker
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public abstract void reloadJobQueue(String jobType, String businessName,
			JobWorker jobWorker) throws Exception;

}
