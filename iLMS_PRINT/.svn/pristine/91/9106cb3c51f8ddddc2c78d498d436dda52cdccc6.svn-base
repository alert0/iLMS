package com.hanthink.mes.print.ifc;

import java.sql.Connection;

import com.hanthink.mes.print.bean.PrintMsg;
import com.hanthink.mes.print.model.MESPRJobQueue;

public interface IPrintService 
{
	/**
	 * get printer name by business and job name
	 * 
	 * @param business
	 * @param jobName
	 * @return printer name
	 * 
	 * @author Lei Pan
	 * @version 2016-01-05
	 */
	public String getPrinterName(String business, String jobName);

	/**
	 * get printer copies by business and job name
	 * 
	 * @param business
	 * @param jobName
	 * @return copies
	 * 
	 * @author Lei Pan
	 * @version 2016-01-05
	 */
	public int getPrintCopies(String business, String jobName);
	
	/**
	 * get sequence by sequence name
	 * 
	 * @param sequence name
	 * @return sequence
	 * 
	 * @author Lei Pan
	 * @version 2016-01-05
	 * @throws Exception 
	 */
	public long getNextSequence(String sequenceName, Connection connection) throws Exception;
	
	/**
	 * generate print job queue
	 * 
	 * @param MESPRJobQueue
	 * @return MESPRJobQueue
	 * 
	 * @author Lei Pan
	 * @version 2016-01-05
	 * @throws Exception 
	 */
	public MESPRJobQueue generatePrintJobQueue(MESPRJobQueue jobQueue) throws Exception;

	/**
	 * logistics print
	 * 
	 * @param MESPRJobQueue
	 * @return print classes
	 * 
	 * @author Lei Pan
	 * @version 2016-01-05
	 */
	public PrintMsg printLogistics(MESPRJobQueue printJob, String printClasses);

	/**
	* update print status and time
	* 
	* @param job void
	*  
	* @author Lei Pan
	* @version 2016-6-23
	*/
	public void updatePrintStatus(MESPRJobQueue job);

	/**
	 * 广汽新能源拉动配送单
	 * @param printJob
	 * @param printType
	 * @author dtp
	 * @return
	 */
	public PrintMsg printGacneJitIns(MESPRJobQueue printJob, String printType);
	
	/**
	 * 广汽新能源SPS指示票
	 * @param printJob
	 * @param printType
	 * @return
	 */
	public PrintMsg printGacneSpsIns(MESPRJobQueue printJob, String printType);

}
