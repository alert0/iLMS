package com.hanthink.mes.print.impl;

import com.hanthink.mes.common.utilities.JobWorker;
import com.hanthink.mes.common.utilities.LogUtility;
import com.hanthink.mes.print.bean.PrintMsg;
import com.hanthink.mes.print.constants.PrintStatus;
import com.hanthink.mes.print.constants.PrintType;
import com.hanthink.mes.print.model.MESPRJobQueue;

/**
 * print job worker
 * 
 * @author Lei Pan
 * @version 2016-01-06
 */
public class PrintJobWorker extends JobWorker {
	
	private String printClasses = null;
	private String business = null;
	private long printInterval = 0;
	private long printFailInterval = 120 * 1000;

	/**
	 * constructor print job worker
	 * 
	 * @param printClasses
	 * @param printType
	 * @param jobName
	 * @param business
	 * @param printInterval
	 */
	public PrintJobWorker(String printClasses, String printType,
			String jobName, String business, Long printInterval) {
		super(printType, jobName);
		queueManager = new PrintQueueManager();
		this.printClasses = printClasses;
		this.business = business;
		if (printInterval == null || printInterval <= 0) {
			this.printInterval = 3000;
		} else {
			this.printInterval = printInterval;
		}

	}

	/**
	* start print job worker thread
	* 
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void startPrintThread() throws Exception {
		JobRunner jobRunner = new JobRunner();
		jobRunner.start();
	}

	/**
	* stop print job worker thread
	* 
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void stopWorkerThread() {
		super.stopWorkerThread();
	}

	/**
	* check print job worker is ready
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public boolean isJobWorkderReady() {
		return super.isJobWorkderReady();
	}

	/**
	* set print job worker is ready
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void setJobWorkderReady(boolean jobWorkderReady) {
		super.setJobWorkderReady(jobWorkderReady);
	}

	/**
	* get print job worker business name
	* 
	* @return String
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public String getBusiness() {
		return business;
	}

	/**
	* get print job worker job name
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public String getJobName() {
		return super.getJobName();
	}

	/**
	* add job to job worker the queue
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public void addJob(MESPRJobQueue job, String queueKey) throws Exception {
		super.addJob(job, queueKey);
		queueManager.updateJobQueue(job, PrintStatus.IN_CACHE);
	}

	/**
	* check job worker is stop
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public boolean isStop() {
		return super.isStop();
	}

	/**
	* get job worker the queue manager
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public PrintQueueManager getQueueManager() {
		return (PrintQueueManager) super.getQueueManager();
	}

	private class JobRunner extends Thread {
		
		private String queueKey = null;
		
		public JobRunner() {
			queueKey = queueManager.getQueueKey(business, jobName);
		}

		@Override
		public void run() {
			LogUtility.info("[JobRunner.run] The print thread[" + queueKey
					+ "]has been started .");
			MESPRJobQueue job = null;
			PrintMsg isOk = new PrintMsg();
			isOk.setPrintResult(false);
			isOk.setPrintErrMsg("");
			isOk.setPrinterName("");
			while (!toStop) {
				job = queueManager.getNext(queueKey);
				if (job != null) {
					try {
						isOk = doPrint(job);
						if (isOk.getPrintResult()) {
							queueManager.updateJobQueue(job, PrintStatus.PRINTED);
							printService.updatePrintStatus(job);
							queueManager.removeFirst(queueKey);
							LogUtility.info("[JobRunner.run] The print Type["
									+ job.getPrintType()
									+ "] job type [" + job.getJobType()
									+ "] job no[" + job.getJobNo()
									+ "] Print Job run successfully.");
						} else {
							LogUtility.error("[JobRunner.run] The print Type["
									+ job.getPrintType() + "] job type ["
									+ job.getJobType() + "] job no["
									+ job.getJobNo()
									+ "] Print Job run failed .");
						}
					} catch (Exception e) {
						LogUtility.error("[JobRunner.run] The print Type["
								+ job.getPrintType() + "] job type ["
								+ job.getJobType() + "] " + "job no["
								+ job.getJobNo() + "] Print Job run failed .");
					}
					try {
						if (isOk.getPrintResult()) {
							Thread.sleep(printInterval);
						} else if (isOk.getPrintErrMsg().length() > 30
								&& "Invalid name of PrintService."
										.equals(isOk.getPrintErrMsg()
												.substring(
														isOk.getPrintErrMsg()
																.length() - 29))) {
							Thread.sleep(240 * 1000);
						} else {
							job = null;
							System.gc();
							Thread.sleep(printFailInterval);
//							toStop = true;
						}
					} catch (InterruptedException ex) {
						LogUtility.error("[JobRunner.run] " + ex.getLocalizedMessage(), ex);
					}

				} else {
					try {
						Thread.sleep(printInterval);
					} catch (InterruptedException e) {
						LogUtility.error("[JobRunner.run] " + e.getLocalizedMessage(), e);
					}
				}
			}
			isStop = true;
			LogUtility.info("[JobRunner.run] The print thread[" + queueKey
					+ "]has been stopped");
		}

		/**
		* do print the job
		* 
		* @param printJob
		* @return boolean
		*  
		* @author Lei Pan
		* @version 2016-6-29
		*/
		private PrintMsg doPrint(MESPRJobQueue printJob) {
			
			PrintMsg isOk = new PrintMsg();
			isOk.setPrintResult(false);
			isOk.setPrintErrMsg("");
			isOk.setPrinterName("");
			
			/**
			 * add by dtp 广汽新能源拉动指示票
			 */
			if(PrintType.PRINT_TYPE_LOGISTICS_JIT_PRINT.equalsIgnoreCase(printType)){
				isOk = printService.printGacneJitIns(printJob, printType);
			}else if(PrintType.PRINT_TYPE_LOGISTICS_SPS_PRINT.equals(printType)){
				isOk = printService.printGacneSpsIns(printJob, printType);
			}else {
				LogUtility.info("Print Type " + printType + " is not exist !");
				isOk.setPrintErrMsg("Print Type " + printType + " is not exist !");
			}
			
			return isOk;
		}
	}
}
