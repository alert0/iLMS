package com.hanthink.mes.print.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import com.hanthink.mes.common.constants.LogLevel;
import com.hanthink.mes.common.utilities.JdbcUtil;
import com.hanthink.mes.common.utilities.JobWorker;
import com.hanthink.mes.common.utilities.JobWorkerManager;
import com.hanthink.mes.common.utilities.LogDBUtil;
import com.hanthink.mes.common.utilities.LogUtility;
import com.hanthink.mes.print.constants.PrintClass;
import com.hanthink.mes.print.constants.PrintStatus;
import com.hanthink.mes.print.model.MESPRJobQueue;

/**
 * print job worker manager
 * 
 * @author Lei Pan
 * @version 2016-01-06
 */
public class PrintJobWorkerManager extends JobWorkerManager {

	public PrintJobWorkerManager(String printClasses, String printType)
			throws Exception {
		super(printType);

		String sql = "select a.business business, a.job_name job_name, a.print_interval print_interval "
				+ " from mm_pr_job_business a "
				+ " join mm_pr_job b on a.job_name=b.job_name "
				+ " where b.print_type = ? and b.classes = ?";
		Connection connection = JdbcUtil.getConnection();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setString(1, printType);
			preparedStatement.setString(2, printClasses);
			ResultSet resultSet = preparedStatement.executeQuery();
			PrintJobWorker jobWorker = null;
			while (resultSet.next()) {
				String business = resultSet.getString("business");
				String jobName = resultSet.getString("job_name");
				Long printInterval = resultSet.getLong("print_interval");
				jobWorker = new PrintJobWorker(printClasses, printType,
						jobName, business, printInterval);
				jobWorkerTable.put(business + "_" + jobName, jobWorker);
				reloadJobQueue(jobName, business, jobWorker);
			}
			preparedStatement.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void reloadJobQueue(String jobName, String businessName,
			JobWorker jobWorker) throws Exception {
		if (jobWorker != null) {
			String sql = "select job_no, business, job_name, print_type, job_type, classes, "
					+ " status, serial_number, atrow_key"
					+ " from mm_pr_job_queue where business = ? "
					+ " and job_name = ?	and (status = ? or status = ?)"
					+ " and classes = ? order by job_no";
			Connection connection = JdbcUtil.getConnection();
			try {
				PreparedStatement preparedStatement = connection
						.prepareStatement(sql);

				preparedStatement.setString(1, businessName);
				preparedStatement.setString(2, jobName);
				preparedStatement.setString(3, PrintStatus.JOB_INIT);
				preparedStatement.setString(4, PrintStatus.IN_CACHE);
				preparedStatement.setString(5, PrintClass.PRINT_CLASS_NETWORK);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					MESPRJobQueue jobQueue = new MESPRJobQueue();
					jobQueue.setJobNo(resultSet.getLong("job_no"));
					jobQueue.setBusiness(resultSet.getString("business"));
					jobQueue.setJobName(resultSet.getString("job_name"));
					jobQueue.setPrintType(resultSet.getString("print_type"));
					jobQueue.setJobType(resultSet.getString("job_type"));
					jobQueue.setClasses(resultSet.getString("classes"));
					jobQueue.setStatus(resultSet.getString("status"));
					jobQueue.setSerialNumber(resultSet
							.getString("serial_number"));
					jobQueue.setAtrowKey(resultSet.getLong("atrow_key"));
					String queueKey = getWorkerKey(businessName, jobName);
					jobWorker.addJob(jobQueue, queueKey);
				}
				jobWorker.setJobWorkderReady(true);
				preparedStatement.close();
			} catch (SQLException e) {
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
			}
		}
	}

	/**
	* create job queue
	* 
	* @param business
	* @param jobName
	* @param serialNumber
	* @param atrowKey
	* @param parameters
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-7-28
	* mod by dtp 2018
	*/
	public void createJobQueue(String business, String jobName,
			String serialNumber, Long atrowKey, String parameters) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT B.PRINT_TYPE, B.JOB_TYPE, B.CLASSES");
		sb.append(" FROM MM_PR_JOB_BUSINESS A");
		sb.append(" JOIN MM_PR_JOB B ON A.JOB_NAME = B.JOB_NAME");
		sb.append(" JOIN MM_PR_PRINTER C ON C.PRINTER_NAME = B.PRINTER_NAME");
		sb.append(" WHERE A.BUSINESS = ? AND A.JOB_NAME = ?");
		Connection connection = JdbcUtil.getConnection();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			
			preparedStatement.setString(1, business);
			preparedStatement.setString(2, jobName);
			ResultSet resultSet = preparedStatement.executeQuery();
			MESPRJobQueue job = new MESPRJobQueue();
			while (resultSet.next()) {
				job.setClasses(resultSet.getString("classes"));
				job.setPrintType(resultSet.getString("print_type"));
				job.setJobType(resultSet.getString("job_type"));
			}
			job.setBusiness(business);
			job.setJobName(jobName);
			job.setSerialNumber(serialNumber);
			job.setAtrowKey(atrowKey);
			job.setParameters(parameters);
			
			resultSet.close();
			preparedStatement.close();
			connection.close();
			job = printService.generatePrintJobQueue(job);
			if (null != job) {
				String workerKey = getWorkerKey(business, jobName);
				addPrintJob(job, workerKey);
			} else {
				LogUtility
						.error("[PrintJobWorkerManager.createJobQueue] business ["
								+ business
								+ "] job Name ["
								+ jobName
								+ "] serialNumber ["
								+ serialNumber
								+ ": generate Print Job failed .");
			}
		} finally {
			if(connection != null){
				connection.close();
			}
		}
	}

	/**
	* add job to job worker queue
	* 
	* @param job
	* @param workerKey
	* @throws Exception void
	*  
	* @author Lei Pan
	* @version 2016-7-28
	*/
	public void addPrintJob(MESPRJobQueue job, String workerKey)
			throws Exception {
		super.addJob(job, workerKey);
	}

	/**
	* start all job worker thread
	*  
	* @author Lei Pan
	* @version 2016-7-28
	*/
	public void startAllJobWorker() throws Exception {
		Enumeration<String> jobKeys = jobWorkerTable.keys();
		while (jobKeys.hasMoreElements()) {
			String printerKey = jobKeys.nextElement();

			PrintJobWorker jobWorker = (PrintJobWorker) jobWorkerTable
					.get(printerKey);
			if (jobWorker != null) {
				jobWorker.startPrintThread();
			}
		}
	}

	/**
	* start job worker thread by printer key
	*  
	* @author Lei Pan
	* @version 2016-7-28
	*/
	public void startJobWorker(String printerKey) throws Exception {
		PrintJobWorker jobWorker = (PrintJobWorker) jobWorkerTable
				.get(printerKey);
		if (jobWorker != null) {
			jobWorker.startPrintThread();
		}
	}

	/**
	* stop all job worker thread
	*  
	* @author Lei Pan
	* @version 2016-7-28
	*/
	public void stopAllJobWorker() throws Exception {
		Enumeration<String> jobKeys = jobWorkerTable.keys();
		while (jobKeys.hasMoreElements()) {
			String jobType = jobKeys.nextElement();

			PrintJobWorker jobWorker = (PrintJobWorker) jobWorkerTable
					.get(jobType);
			if (jobWorker != null) {
				jobWorker.stopWorkerThread();
			}
		}
	}

	/**
	* stop job worker thread by printer key
	*  
	* @author Lei Pan
	* @version 2016-7-28
	*/
	public void stopJobWorker(String printerKey) throws Exception {
		PrintJobWorker jobWorker = (PrintJobWorker) jobWorkerTable
				.get(printerKey);
		if (jobWorker != null) {
			jobWorker.stopWorkerThread();
		}
	}

	public void waitJobWorkerStarted() throws Exception {
		Enumeration<String> jobKeys = jobWorkerTable.keys();
		while (jobKeys.hasMoreElements()) {
			String jobType = jobKeys.nextElement();
			PrintJobWorker jobWorker = (PrintJobWorker) jobWorkerTable
					.get(jobType);

			while (!jobWorker.isJobWorkderReady()) {
				try {
					LogUtility
							.info("[PrintJobWorkerManager.createJobQueue] Wait for "
									+ jobWorker.getBusiness()
									+ "_"
									+ jobWorker.getJobName() + " to start.");
					Thread.sleep(100);
				} catch (InterruptedException e) {
					LogUtility.error(e.getLocalizedMessage(), e);
					LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:waitJobWorkerStarted", LogLevel.ERROR);
				}
			}
		}
	}
	
	/**
	* check job worker is active
	* 
	* @param conn
	* @param business
	* @param jobName
	* @return boolean
	*  
	* @author Lei Pan
	* @version 2016-6-29
	 * @throws SQLException 
	*/
	public boolean checkIsActive(Connection conn, String business, String jobName) throws SQLException {
		boolean isActive = false;
		StringBuffer querySql = new StringBuffer();
		querySql.append("select 1 from mm_pr_job_business where active=1 ");
		querySql.append(" and business = '" + business +  "' ");
		querySql.append(" and job_name = '" + jobName + "'");
		PreparedStatement preparedStatement = conn.prepareStatement(querySql.toString());
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			isActive = true;
		}
		resultSet.close();
		preparedStatement.close();
		return isActive;
	}

}