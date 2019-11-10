package com.hanthink.mes.print.impl;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

import com.hanthink.mes.common.utilities.JdbcUtil;
import com.hanthink.mes.common.utilities.JobQueue;
import com.hanthink.mes.common.utilities.LogUtility;
import com.hanthink.mes.common.utilities.QueueManager;
import com.hanthink.mes.print.model.MESPRJobQueue;

/**
 * print queue manager
 * 
 * @author Lei Pan
 * @version 2016-01-06
 */
public class PrintQueueManager extends QueueManager
{
	
	public PrintQueueManager()
	{
	}

	/**
	* get next job by job queue key
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public synchronized MESPRJobQueue getNext(String queueKey)
	{
		return super.getNext(queueKey);
	}

	/**
	* remove first job from job queue 
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public synchronized void removeFirst(String queueKey)
	{
		super.removeFirst(queueKey);
	}

	/**
	* add job to job queue
	* 
	* @param job void
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public synchronized void addJob(MESPRJobQueue job)
	{
		String queueKey = getQueueKey(job.getBusiness(),job.getJobName());
		super.addJob(job,queueKey);
	}

	/**
	* get job queue by queue key from job queue table
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public JobQueue getJobQueue(String queueKey)
	{
		return super.getJobQueue(queueKey);
	}

	/**
	* get job queue table
	*  
	* @author Lei Pan
	* @version 2016-6-29
	*/
	public Hashtable<String, JobQueue> getJobQueue()
	{
		return super.getJobQueue();
	}

	/**
	 * mod by dtp 2018-12-29
	 */
	@Override
	public void updateJobQueue(MESPRJobQueue job, String status) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(" update MM_PR_JOB_QUEUE");
		sb.append(" set status = ?, last_modified_time=sysdate,");
		sb.append(" last_modified_user = ?, last_modified_ip = ? where job_no = ?");
		Connection connection = JdbcUtil.getConnection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sb.toString());

			preparedStatement.setString(1, status);
			//get the host ip address and name
			InetAddress inetAddress = InetAddress.getLocalHost();
			preparedStatement.setString(2, inetAddress.getHostName());
			preparedStatement.setString(3, inetAddress.getHostAddress());
			
			preparedStatement.setLong(4, job.getJobNo());
			int updateCount = preparedStatement.executeUpdate();
			preparedStatement.close();
			if(updateCount != 1) {
				LogUtility.error("[PrintQueueManager.updateJobQueue] update job no ["+ job.getJobNo() +
						"] status to ["+ status + "] is failure .");
			}
			connection.close();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
		}
	}
}
