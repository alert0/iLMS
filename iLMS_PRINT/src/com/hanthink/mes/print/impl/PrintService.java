package com.hanthink.mes.print.impl;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.engine.util.JRLoader;

import org.hsqldb.lib.StringUtil;

import com.hanthink.mes.common.constants.LogLevel;
import com.hanthink.mes.common.utilities.JdbcUtil;
import com.hanthink.mes.common.utilities.LogDBUtil;
import com.hanthink.mes.common.utilities.LogUtility;
import com.hanthink.mes.common.utilities.MakeQrcodeImages;
import com.hanthink.mes.print.bean.JitInsModel;
import com.hanthink.mes.print.bean.PrintMsg;
import com.hanthink.mes.print.bean.SpsInsModel;
import com.hanthink.mes.print.constants.ManifestType;
import com.hanthink.mes.print.constants.PrintClass;
import com.hanthink.mes.print.constants.PrintFile;
import com.hanthink.mes.print.constants.PrintStatus;
import com.hanthink.mes.print.ifc.IPrintService;
import com.hanthink.mes.print.model.MESPRJobQueue;

/**
 * all print method
 * 
 * @author PanLei
 *
 */
public class PrintService implements IPrintService {

	public PrintService() {
	}

	/**
	 * mod by dtp 2018-12-29
	 */
	@Override
	public String getPrinterName(String business, String jobName) {
		String printerName = null ;
		StringBuffer sb = new StringBuffer();
		sb.append(" select b.printer_name printer_name from mm_pr_job_business a");
		sb.append(" left join mm_pr_job b on a.job_name=b.job_name");
		sb.append(" left join mm_pr_printer c on b.printer_name = c.printer_name");
		sb.append(" where c.active = 1 and a.business=? and a.job_name= ?");
		Connection connection = null;
		try {
			connection = JdbcUtil.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(sb.toString());
			preparedStatement.setString(1, business);
			preparedStatement.setString(2, jobName);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				printerName = resultSet.getString("printer_name");
			}
			preparedStatement.close();
		} catch (SQLException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:getPrinterName", LogLevel.ERROR);
		} catch (ClassNotFoundException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:getPrinterName", LogLevel.ERROR);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LogUtility.error(e.getLocalizedMessage(), e);
					LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:getPrinterName", LogLevel.ERROR);
				}
			}
		}
		return printerName;
	}

	/**
	 * 根据打印机名称，获取打印机服务
	 * 
	 * @param printerName
	 * @return javax.print.PrintService
	 * 
	 * @author Lei Pan
	 * @version 2016-4-9
	 */
	private javax.print.PrintService getPrinterByName(String printerName) {
		javax.print.PrintService printer = null;
		javax.print.PrintService[] printers = (javax.print.PrintService[]) PrinterJob
				.lookupPrintServices();
		for (javax.print.PrintService ps : printers) {
			if (printerName.equals(ps.getName())) {
				printer = ps;
				break;
			}
		}
		return printer;
	}

	@Override
	public int getPrintCopies(String business, String jobName) {
		int copies = 1;
		String sql = "select b.copies copies from mm_pr_job_business a"
				+ "	left join mm_pr_job b on a.job_name=b.job_name"
				+ "	where a.business=? and a.job_name= ?";
		Connection connection = null;
		try {
			connection = JdbcUtil.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setString(1, business);
			preparedStatement.setString(2, jobName);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				copies = resultSet.getInt("copies");
			}
			preparedStatement.close();
		} catch (Exception e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:getPrintCopies", LogLevel.ERROR);
		}  finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LogUtility.error(e.getLocalizedMessage(), e);
					LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:getPrintCopies", LogLevel.ERROR);
				}
			}
		}
		return copies;
	}

	@Override
	public synchronized long getNextSequence(String sequenceName, Connection connection) throws Exception {
		long nextSequence = -1;
		String sql = "select  sequence_name  from  user_sequences  where  sequence_name= '"
				+ sequenceName.toUpperCase() + "'";
		List<String[]> sequenceList = JdbcUtil.executeQuery(connection, sql);
		if (sequenceList != null && sequenceList.size() > 0) {
			String nextSequenceSql = "select " + sequenceName + ".nextval "
					+ sequenceName + " from dual";
			List<String[]> sequence = JdbcUtil.executeQuery(connection, nextSequenceSql);
			if (sequence != null && sequence.size() > 0) {
				nextSequence = Long.parseLong(sequence.get(0)[0]);
			}
		} else {
			String createSequence = "create sequence " + sequenceName
					+ " minvalue 0 increment by 1 start with 0";
			JdbcUtil.executeUpdate(connection, createSequence);
			nextSequence = getNextSequence(sequenceName, connection);
		}
		return nextSequence;
	}

	/**
	 * mod by dtp 2018-12-28
	 */
	@Override
	public synchronized MESPRJobQueue generatePrintJobQueue(MESPRJobQueue jobQueue) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into MM_PR_JOB_QUEUE(job_no, business, job_name, print_type, job_type, classes, status, serial_number,");
		sb.append(" atrow_key, parameters, creation_time, last_modified_time, last_modified_user, last_modified_ip)");
		sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, sysdate, ?, ?)");
		
		System.out.println(sb.toString());
		
		Connection connection = JdbcUtil.getConnection();
		Long jobNo = getNextSequence("JOB_NO", connection);
		jobQueue.setJobNo(jobNo);
		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement(sb.toString());
			preparedStatement.setLong(1, jobNo);
			preparedStatement.setString(2, jobQueue.getBusiness());
			preparedStatement.setString(3, jobQueue.getJobName());
			preparedStatement.setString(4, jobQueue.getPrintType());
			preparedStatement.setString(5, jobQueue.getJobType());
			preparedStatement.setString(6, PrintClass.PRINT_CLASS_NETWORK);
			preparedStatement.setString(7, PrintStatus.JOB_INIT);
			if (jobQueue.getSerialNumber() != ""
					&& jobQueue.getSerialNumber() != null) {
				preparedStatement.setString(8, jobQueue.getSerialNumber());
			} else {
				preparedStatement.setString(8, "");
			}
			if (jobQueue.getAtrowKey() != null) {
				preparedStatement.setLong(9, jobQueue.getAtrowKey());
			} else {
				preparedStatement.setLong(9, -1L);
			}
			
			if(jobQueue.getParameters() != null) {
				preparedStatement.setString(10, jobQueue.getParameters());
			} else {
				preparedStatement.setString(10, "");
			}
			
			InetAddress inetAddress;
			try {
				inetAddress = InetAddress.getLocalHost();
				preparedStatement.setString(11, inetAddress.getHostName());
				preparedStatement.setString(12, inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				LogUtility.error(
						"[PrintService.generatePrintJobQueue] "
								+ e.getLocalizedMessage(), e);
				LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:generatePrintJobQueue", LogLevel.ERROR);
			}
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LogUtility.error(e.getLocalizedMessage(), e);
					LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:generatePrintJobQueue", LogLevel.ERROR);
				}
			}
		}
		return jobQueue;
	}
	
	/**
	* update print status and time
	* 
	* @param jobQueue
	* @return boolean
	*  
	* @author Lei Pan
	* @version 2016-6-23
	* mod by dtp 2018-12-29
	*/
	public void updatePrintStatus(MESPRJobQueue jobQueue) {
		String updateSql = null;
		String querySql = "select update_sql from mm_pr_job where job_name = ?";
		Connection connection = null;
		try {
			connection = JdbcUtil.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(querySql.toString());

			preparedStatement.setString(1, jobQueue.getJobName());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				updateSql = resultSet.getString("update_sql");
			}
			preparedStatement.close();
		} catch (SQLException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:updatePrintStatus", LogLevel.ERROR);
		} catch (ClassNotFoundException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:updatePrintStatus", LogLevel.ERROR);
		}
		
		if(updateSql != null && updateSql != "") {
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
				preparedStatement.setString(1, jobQueue.getSerialNumber());
				preparedStatement.execute();
			} catch (SQLException e) {
				LogUtility.error(e.getLocalizedMessage(), e);
				LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:updatePrintStatus", LogLevel.ERROR);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						LogUtility.error(e.getLocalizedMessage(), e);
						LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:updatePrintStatus", LogLevel.ERROR);
					}
				}
			 }
		} else {
			LogUtility.info("[PrintService.updatePrintStatus] job name[" + jobQueue.getJobName() + "] the update sql is not configration.");
		}
		
	}

	@Override
	public PrintMsg printLogistics(MESPRJobQueue printJob, String printClasses) {
		
		if(ManifestType.GACNE_JIT_INS.equals(printJob.getJobType())){
			//广汽新能源拉动配送单
			return printGacneJitIns(printJob, printClasses);
		}else if(ManifestType.GACNE_SPS_INS.equals(printJob.getJobType())){
			//广汽新能源SPS指示票
			return printGacneSpsIns(printJob, printClasses);
		}else {
			LogUtility.info("jobType  " + printJob.getJobType() + "  is not exists!");
		}
		/**
		 * 打印输出结果
		 */
		PrintMsg printMsg = new PrintMsg();
		printMsg.setPrintResult(false);
		printMsg.setPrintErrMsg("jobType  " + printJob.getJobType() + "  is not exists!");
		printMsg.setPrinterName("");
		return printMsg;
	}
	
	/**
	 * 获取JasperReport
	 * @author Lei Pan
	 * @version 2016-4-9
	 * @ModifyUser dingbb 
	 * @ModifyDate 2016-7-21
	 * @ModifyReason 取模板路径为绝对路径。
	 */
	private JasperReport getReport(String fileName) {
		String filePath = PrintFile.FILE_PATH + fileName;
		URL url;
		JasperReport jasperReport = null;
		try {
			url = getClass().getResource(filePath);
			jasperReport = (JasperReport) JRLoader.loadObject(url);
		} catch (JRException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:getReport", LogLevel.ERROR);
		}
		return jasperReport;
	}
	
	/**
	 * 获取拉动配送单图片路径
	 * @param fileName
	 * @return
	 */
	private String getPagePath(String fileName){
		String pagePath = PrintFile.FILE_PATH + fileName;
		URL url;
		try {
			url = getClass().getResource(fileName);
		} catch (Exception e) {
			
		}
		return "";
	}

	/**
	 * 打印报表
	 * @author Lei Pan
	 * @version 2016-4-9
	 * @update lixl 2017-6-15
	 * 	返回结果增加错误信息和打印机名称
	 */
	@SuppressWarnings({ "unchecked", "deprecation"})
	private PrintMsg printReport(JasperPrint jasperPrint,
			javax.print.PrintService printer, int copies) {
		
		/************** 打印服务属性判断 ***************/
		AttributeSet att = printer.getAttributes();  
        for (Attribute a : att.toArray()) {  
        	String attributeName;  
        	String attributeValue;  
        	attributeName = a.getName();  
        	attributeValue = att.get(a.getClass()).toString();  
        }
        ArrayList<JRBasePrintPage> aljrpp = (ArrayList<JRBasePrintPage>) jasperPrint.getPages();
        int printSize = 0;
        for (int i=0; i<aljrpp.size(); i++) {
        	ArrayList<JRTemplatePrintText> aljrpt = (ArrayList<JRTemplatePrintText>) aljrpp.get(i).getElements();
        	/*for (int j=0; j<aljrpt.size(); j++) {
        		printSize += aljrpt.get(j).getSize();
        	}*/
        }
        //System.out.println("JRTemplatePrintText Elements Size: "+printSize);
        
		JRPrintServiceExporter jrPrintServiceExporter = new JRPrintServiceExporter();
		jrPrintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT,
				jasperPrint);
		// 设置打印份数
		HashPrintRequestAttributeSet hashprintrequestattributeset = new HashPrintRequestAttributeSet();
		hashprintrequestattributeset.add(new Copies(copies));
		// 设置打印机
		jrPrintServiceExporter.setParameter(
				JRPrintServiceExporterParameter.PRINT_SERVICE, printer);
		jrPrintServiceExporter.setParameter(
				JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
				hashprintrequestattributeset);
		jrPrintServiceExporter.setParameter(
				JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, false);
		jrPrintServiceExporter.setParameter(
				JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, false);
		/**
		 * 打印输出结果
		 */
		PrintMsg printMsg = new PrintMsg();
		try {
			jrPrintServiceExporter.exportReport();
		} catch (JRException e) {
			LogUtility.error("printer Name [" + printer.getName() + "]", e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), "printer name [" + printer.getName() + "]" + e.getLocalizedMessage(), getClass().getName(), "function:printReport", LogLevel.ERROR,printer.getName());
			//打印异常返回
			printMsg.setPrintResult(false);
			printMsg.setPrintErrMsg(e.getLocalizedMessage());
			printMsg.setPrinterName(printer.getName());
			return printMsg;
		}
		finally {
		}
		//打印正常返回
		printMsg.setPrintResult(true);
		printMsg.setPrintErrMsg("NO ERROR");
		printMsg.setPrinterName(printer.getName());
		return printMsg;
	}

	/**
	 * 广汽新能源拉动配送单
	 * @param printJob
	 * @param printType
	 * @author dtp
	 * @return
	 */
	@Override
	public PrintMsg printGacneJitIns(MESPRJobQueue jobQueue, String printType) {
		String printerName = getPrinterName(jobQueue.getBusiness(),
				jobQueue.getJobName());
		javax.print.PrintService printer = getPrinterByName(printerName);
		
		/**
		 * 输出打印结果
		 */
		PrintMsg printMsg = new PrintMsg();
		if (printer == null) {
			LogUtility.error("printer [" + printerName + "] is not configuration !");
			//打印异常返回
			printMsg.setPrintResult(false);
			printMsg.setPrintErrMsg("printer [" + printerName + "] is not configuration !");
			printMsg.setPrinterName(printerName);
			return printMsg;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT T.INS_NO insNo,");
		sb.append(" T.PREPARE_PERSON preparePerson,");
		sb.append(" T.DISTRI_PERSON distriPerson,");
		sb.append(" (SELECT COUNT(*) FROM MM_JIT_INS II WHERE II.DISTRI_PERSON = T.DISTRI_PERSON) totalCarPool,");
		sb.append(" (SELECT MAX(T.CARPOOL) FROM MM_JIT_INS II WHERE II.DISTRI_PERSON = T.DISTRI_PERSON) maxCarPool,");
		sb.append(" T.CARPOOL carpool,");
		sb.append(" to_char(sysdate, 'yyyy\"年\"mm\"月\"dd\"日\"') printTime,");
		sb.append(" T.PREPARE_BATCH_NO prepareBatchNo,");
		sb.append(" D.PART_SHORT_NO partShortNo,");
		sb.append(" D.STORAGE storage,");
		sb.append(" D.REQUIRE_NUM requireNum,");
		sb.append(" ceil(D.REQUIRE_NUM/D.STANDARD_PACKAGE) xs,");
		sb.append(" T.GEN_INS_WAY genInsWay,");
		sb.append(" D.LOCATION location");
		sb.append(" FROM MM_JIT_INS T");
		sb.append(" LEFT JOIN MM_JIT_INS_DETAIL D ON D.INS_NO = T.INS_NO");
		sb.append(" WHERE T.INS_NO = '" + jobQueue.getSerialNumber() + "'");
		sb.append(" ORDER BY D.STORAGE");
		
		LogUtility.info(sb);
		
		//查询打印信息
		ArrayList<JitInsModel> detailList = new ArrayList<JitInsModel>();
		Connection connection = null;
		try {
			connection = JdbcUtil.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				JitInsModel bean = new JitInsModel();
				bean.setInsNo(resultSet.getString("insNo"));
				bean.setPreparePerson(resultSet.getString("preparePerson"));
				bean.setDistriPerson(resultSet.getString("distriPerson"));
				bean.setTotalCarPool(resultSet.getString("totalCarPool"));
				bean.setMaxCarPool(resultSet.getString("maxCarPool"));
				bean.setCarpool(resultSet.getString("carpool"));
				bean.setPrintTime(resultSet.getString("printTime"));
				bean.setPrepareBatchNo(resultSet.getString("prepareBatchNo"));
				bean.setPartShortNo(resultSet.getString("partShortNo"));
				bean.setStorage(resultSet.getString("storage"));
				bean.setRequireNum(resultSet.getString("requireNum"));
				bean.setXs(resultSet.getString("xs"));
				bean.setGenInsWay(resultSet.getString("genInsWay"));
				bean.setLocation(resultSet.getString("location"));
				if(null != bean){
					detailList.add(bean);
				}
			}
			preparedStatement.close();
		} catch (SQLException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:printJitIns", LogLevel.ERROR);
		} catch (ClassNotFoundException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:printJitIns", LogLevel.ERROR);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LogUtility.error(e.getLocalizedMessage(), e);
					LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:printJitIns", LogLevel.ERROR);
				}
			}
		}
		//表头参数
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		List<JitInsModel> list = new ArrayList<JitInsModel>();
		//箭头图标
		String pagePath = "C:\\Windows\\jit_ins.jpg";
		BufferedImage img = null;
		try {
			img = ImageIO.read(new FileInputStream(pagePath));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//行数统计
		int rowCount = 0;
		//每列最多显示行数
		int maxRowSize = 30;
		//页码
		int page = 1;
		int size = detailList.size();
		//判断打印张数
		int totalPage = 1;
		//整除
		if(size%(maxRowSize*2) == 0) {
			totalPage = size/(maxRowSize*2);
		}else if(size%(maxRowSize*2) != 0) {
			totalPage = size/(maxRowSize*2) + 1;
		}
		//目的填充空白行
		int totalRowCount = totalPage * maxRowSize;
		
		if(null != detailList && detailList.size() > 0){
			for (int j = 0; j < detailList.size(); j++) {
				//行数自增
				rowCount++;
				JitInsModel model = detailList.get(j);
				if(rowCount == 1) {
					//配送单头信息
					parameters.put("preparePerson", model.getPreparePerson());
					parameters.put("distriPerson", model.getDistriPerson());
					parameters.put("totalCarPool", model.getTotalCarPool());
					parameters.put("maxCarPool", model.getMaxCarPool());
					parameters.put("printTime", model.getPrintTime());
					parameters.put("prepareBatchNo", model.getPrepareBatchNo());
					parameters.put("jitInsPage", img);
					//按拣货工程组票,显示 * 
					if("2".equals(model.getGenInsWay())) {
						parameters.put("distriPerson", "*");
						parameters.put("totalCarPool", "*");
						parameters.put("maxCarPool", "*");
					}
					if(!StringUtil.isEmpty(model.getCarpool())) {
						parameters.put("carpool", "(" + model.getCarpool() + ")");
					}else {
						parameters.put("carpool", "(" + "  " + ")");
					}
				}
				JitInsModel bean = new JitInsModel();
				if(rowCount <= maxRowSize * ( 2 * page - 1 )) {
					bean.setNo("" + rowCount);
					bean.setPartShortNo(model.getPartShortNo());
					bean.setStorage(model.getStorage());
					bean.setLocation(model.getLocation());
					bean.setRequireNum(model.getRequireNum());
					bean.setCarpool(model.getCarpool());
					bean.setXs(model.getXs());
				}else if(rowCount > maxRowSize * ( 2 * page - 1 ) && rowCount <= maxRowSize * 2 * page) {
					bean = list.get(rowCount - 1 - maxRowSize * page );
					bean.setNo2("" + rowCount);
					bean.setStorage2(model.getStorage());
					bean.setPartShortNo2(model.getPartShortNo());
					bean.setLocation2(model.getLocation());
					bean.setRequireNum2(model.getRequireNum());
					bean.setCarpool2(model.getCarpool());
					bean.setXs2(model.getXs());
				}
				if(null != bean && rowCount <= maxRowSize * ( 2 * page - 1 )) {
					list.add(bean);
				}
				//换页
				if(rowCount == maxRowSize * 2 * page) {
					page++;
				}
			}
			//填充空白行
			if((totalRowCount - list.size()) > 0) {
				int blackRow = totalRowCount - list.size();
				for (int k = 0; k < blackRow; k++) {
					JitInsModel insBlackBean = new JitInsModel(); 
					list.add(insBlackBean);
				}
			}
		}
		JRDataSource jRDataSource;
		if (list.size() > 0) {
			jRDataSource = new JRBeanCollectionDataSource(list);
		} else {
			jRDataSource = new JREmptyDataSource();
		}
		JasperPrint jasperPrint;
		OutputStream myIO = new FileBufferedOutputStream();
		try {
			jasperPrint = JasperFillManager.fillReport(getReport(PrintFile.GACNE_JIT_INS), parameters,
					jRDataSource);
			jasperPrint.setName(jobQueue.getBusiness() + "_" + jobQueue.getSerialNumber());
		} catch (Exception e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), 
					getClass().getName(), "function:printGacneJitIns", LogLevel.ERROR);
			//打印异常返回
			printMsg.setPrintResult(false);
			printMsg.setPrintErrMsg(e.getLocalizedMessage());
			printMsg.setPrinterName(printer.getName());
			return printMsg;
		}
		try {
			myIO.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 打印份数
		
		int copies = getPrintCopies(jobQueue.getBusiness(), jobQueue.getJobName());
		
		return printReport(jasperPrint, printer, copies);
		
	}

	/**
	 * 广汽新能源SPS指示票
	 * @param printJob
	 * @param printType
	 * @return
	 */
	@Override
	public PrintMsg printGacneSpsIns(MESPRJobQueue jobQueue, String printType) {
		String printerName = getPrinterName(jobQueue.getBusiness(),
				jobQueue.getJobName());
		javax.print.PrintService printer = getPrinterByName(printerName);
		
		/**
		 * 打印输出结果
		 */
		PrintMsg printMsg = new PrintMsg();
		if (printer == null) {
			LogUtility.error("printer [" + printerName + "] is not configuration !");
			//打印异常返回
			printMsg.setPrintResult(false);
			printMsg.setPrintErrMsg("printer [" + printerName + "] is not configuration !");
			printMsg.setPrinterName(printerName);
			return printMsg;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT T.INS_NO insNo,");
		sb.append(" D.MOULD_PLACE mouldPlace,");
		sb.append(" M.MOULD_CODE mouldCode,");
		sb.append(" D.SHOW_VALUE showValue");
		sb.append(" FROM MM_SPS_INS T");
		sb.append(" INNER JOIN MM_SPS_INS_DETAIL D ON D.INS_NO = T.INS_NO");
		sb.append(" LEFT JOIN MM_SPS_MOULD M ON M.ID = T.MOULD_ID");
		sb.append(" WHERE T.INS_NO = '" + jobQueue.getSerialNumber() + "'");
		sb.append(" ORDER BY T.PASS_TIME");
		
		//指示票打印模板
		String spsPrintJasper = "";
		String spsPrintJasper_2 = "";
		
		//查询打印信息
		ArrayList<SpsInsModel> detailList = new ArrayList<SpsInsModel>();
		Connection connection = null;
		try {
			connection = JdbcUtil.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				SpsInsModel bean = new SpsInsModel();
				bean.setInsNo(resultSet.getString("insNo"));
				bean.setMouldPlace(resultSet.getString("mouldPlace"));
				bean.setShowValue(resultSet.getString("showValue"));
				bean.setMouldCode(resultSet.getString("mouldCode"));
				spsPrintJasper = resultSet.getString("mouldCode") + ".jasper";
				spsPrintJasper_2 = resultSet.getString("mouldCode") + "2.jasper";
				if(null != bean){
					detailList.add(bean);
				}
			}
			preparedStatement.close();
		} catch (SQLException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:printSpsIns", LogLevel.ERROR);
		} catch (ClassNotFoundException e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:printSpsIns", LogLevel.ERROR);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LogUtility.error(e.getLocalizedMessage(), e);
					LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), "function:printSpsIns", LogLevel.ERROR);
				}
			}
		}
		//打印分页
		List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
		//填充ireport模板
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		HashMap<String, Object> parameters_2 = new HashMap<String, Object>();
		//设置打印值
		List<Integer> mouldPlaceList = new ArrayList<Integer>();
		Integer maxMouldPlace =  206;
		if(null != detailList && detailList.size() > 0){
			if("SPS_XH".equals(detailList.get(0).getMouldCode())){
				maxMouldPlace = SpsInsModel.maxMouldPlaceSPS_XH;
			}
			if("SPS_TC".equals(detailList.get(0).getMouldCode())) {
				maxMouldPlace = SpsInsModel.maxMouldPlaceSPS_TC;
			}
		}
		for (int j = 0; j < detailList.size(); j++) {
			SpsInsModel sps = detailList.get(j);
			mouldPlaceList.add(Integer.valueOf(sps.getMouldPlace()));
			if("21".equals(sps.getMouldPlace())) {
				//打印二维码
				parameters.put("s21", MakeQrcodeImages.getQrCodeImage(sps.getShowValue(), "80", "80"));
				parameters_2.put("s21", MakeQrcodeImages.getQrCodeImage(sps.getShowValue(), "80", "80"));
			}else {
				parameters.put("s" + sps.getMouldPlace(), sps.getShowValue());
				parameters_2.put("s" + sps.getMouldPlace(), sps.getShowValue());
			}
		}
		JRDataSource jRDataSource = new JREmptyDataSource();
		JasperPrint jasperPrint;
		JasperPrint jasperPrint_2;
		OutputStream myIO = new FileBufferedOutputStream();
		try {
			jasperPrint = JasperFillManager.fillReport(getReport(spsPrintJasper), parameters,
					jRDataSource);
			jasperPrint_2 = JasperFillManager.fillReport(getReport(spsPrintJasper_2), parameters_2,
					jRDataSource);
			jasperPrint.setName(jobQueue.getBusiness() + "_" + jobQueue.getSerialNumber());
			jasperPrint_2.setName(jobQueue.getBusiness() + "_" + jobQueue.getSerialNumber());
			JasperPrintList.add(jasperPrint);
			//判断是否需要分页
			Integer mouldPlace = Collections.max(mouldPlaceList);
			if( maxMouldPlace < mouldPlace) {
				JasperPrintList.add(jasperPrint_2);
			}
		} catch (Exception e) {
			LogUtility.error(e.getLocalizedMessage(), e);
			LogDBUtil.logTransaction(e.getClass().getSimpleName(), e.getLocalizedMessage(), getClass().getName(), 
					"function:printGacneSpsIns", LogLevel.ERROR);
			//打印异常返回
			printMsg.setPrintResult(false);
			printMsg.setPrintErrMsg(e.getLocalizedMessage());
			printMsg.setPrinterName(printer.getName());
			return printMsg;
		}
		try {
			myIO.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 打印份数
		int copies = getPrintCopies(jobQueue.getBusiness(), jobQueue.getJobName());
		return printReport(jasperPrint, printer, copies);
		
	}
	
	//获取相对路径
	public static String getClassesPath()  {
		String path = PrintService.class.getClassLoader().getResource("").getPath();
		if ("\\".equals(File.separator)) {
			path = PrintService.trimPrefix(path, "/");
		}
		path = path.replace("\\", "/");
		path = PrintService.trimSuffix(path, "/");
		return path;
	}
	
	public static String trimPrefix(String content, String prefix) {
		String resultStr = content;
		while (resultStr.startsWith(prefix)) {
			resultStr = resultStr.substring(prefix.length());
		}
		return resultStr;
	}
	public static String trimSuffix(String content, String suffix) {
		String resultStr = content;
		while (resultStr.endsWith(suffix)) {
			resultStr = resultStr.substring(0,
					resultStr.length() - suffix.length());
		}
		return resultStr;
	}
}
