package com.hanthink.mes.common.utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.impl.JobDetailImpl;

import com.hanthink.mes.print.ifc.IPrintService;
import com.hanthink.mes.print.impl.PrintService;

public class LogDBUtil {
	
	private static IPrintService printService = new PrintService();
	
	private static final int MAX_SIZE = 2048;

	/**
	* record the timer run log
	* 
	* @param jobexecutioncontext void
	*  
	* @author Lei Pan
	* @version 2016-6-30
	* mod by dtp 2018-12-29
	*/
	public static void logTimer(JobExecutionContext jobexecutioncontext) {
		JobDetailImpl jobDetail = (JobDetailImpl)jobexecutioncontext.getJobDetail();
		
		StringBuffer querySql = new StringBuffer();
		querySql.append("select id from mm_pr_timer_log where 1=1 ");
		querySql.append(" and job_name = '" + jobDetail.getName() +"'");
		querySql.append(" and job_group = '" + jobDetail.getGroup() + "'");
		querySql.append(" order by id desc");
		Connection connection = null;
		try {
			List<String[]> list = JdbcUtil.executeQuery(querySql.toString());
			if(list != null && list.size() >0) {
				StringBuffer updateSql = new StringBuffer();
				updateSql.append("update mm_pr_timer_log set process_time = sysdate where 1=1 ");
				updateSql.append(" and id = " + list.get(0)[0]);
				JdbcUtil.executeUpdate(updateSql.toString());
			} else {
				connection = JdbcUtil.getConnection();
				long sequence = printService.getNextSequence("TIMER_LOG_SEQ", connection);
				StringBuffer insertSql = new StringBuffer();
				insertSql.append("insert into mm_pr_timer_log(id, source, job_name, job_group, process_time) ");
				insertSql.append(" values(");
				insertSql.append(sequence + ", ");
				insertSql.append("'" + jobDetail.getJobClass() + "', ");
				insertSql.append("'" + jobDetail.getName() + "', ");
				insertSql.append("'" + jobDetail.getGroup() + "', ");
				insertSql.append("sysdate");
				insertSql.append(")");
				JdbcUtil.executeUpdate(insertSql.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	/**
	* record error log to db
	*  void
	*  
	* @author Lei Pan
	* @version 2016-6-30
	*/
	public static void logTransaction(String category, String content, String source, String trxName, String level) {
		if(content != null) {
			if(content.length() > MAX_SIZE) {
				content = content.substring(0, MAX_SIZE);
			}
		}
		Connection connection = null;
		long sequence = 0;
		try {
			connection = JdbcUtil.getConnection();
			sequence = printService.getNextSequence("TRANSACTION_LOG_SEQ", connection);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into mm_pr_transaction_log(id, category, content, entry_time, source, trx_name, trx_level) ");
		insertSql.append(" values(");
		insertSql.append(sequence + ", ");
		insertSql.append("'" + category + "', ");
		insertSql.append("'" + content + "', ");
		insertSql.append("sysdate, ");
		insertSql.append("'" + source + "', ");
		insertSql.append("'" + trxName + "', ");
		insertSql.append("'" + level + "'");
		insertSql.append(")");
		try {
			JdbcUtil.executeUpdate(insertSql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>重载添加日志表方法，新增插入打印机名称到日志表里面</p>
	 * <p>Description</p>
	 * @auther add by chenyong 2016-09-20
	 * @date
	 */
	public static void logTransaction(String category, String content, String source, String trxName, String level,String printName) {
		if(content != null) {
			if(content.length() > MAX_SIZE) {
				content = content.substring(0, MAX_SIZE);
			}
		}
		Connection connection = null;
		long sequence = 0;
		try {
			connection = JdbcUtil.getConnection();
			sequence = printService.getNextSequence("TRANSACTION_LOG_SEQ", connection);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into mm_pr_transaction_log(id, category, content, entry_time, source, trx_name, trx_level, printer_name) ");
		insertSql.append(" values(");
		insertSql.append(sequence + ", ");
		insertSql.append("'" + category + "', ");
		insertSql.append("'" + content + "', ");
		insertSql.append("sysdate, ");
		insertSql.append("'" + source + "', ");
		insertSql.append("'" + trxName + "', ");
		insertSql.append("'" + level + "',");
		insertSql.append("'"+ printName +"'");
		insertSql.append(")");
		try {
			JdbcUtil.executeUpdate(insertSql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
