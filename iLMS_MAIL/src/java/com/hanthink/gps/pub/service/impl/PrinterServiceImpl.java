package com.hanthink.gps.pub.service.impl;

import com.hanthink.gps.pub.dao.PrinterDao;
import com.hanthink.gps.pub.service.PrinterService;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrinterVO;

public class PrinterServiceImpl extends BaseServiceImpl implements PrinterService {
	
	private PrinterDao printerDao;
	public PrinterDao getPrinterDao() {
		return printerDao;
	}

	public void setPrinterDao(PrinterDao printerDao) {
		this.printerDao = printerDao;
	}

	/**
	 * 查询打印机配置信息
	 * @param queryInfo
	 * @param start
	 * @param limit
	 * @return PageObject
	 * @author dbb  2016-4-19
	 */
	public PageObject queryPrinterForPage(PrinterVO queryInfo, int start,int limit) {
		return printerDao.queryPrinterForPage(queryInfo, start, limit);
	}
	
	/**
	 * 新增打印机配置信息
	 * 
	 * @param printerVO
	 *            打印机配置信息
	 */
	public PrinterVO insertPrinter(PrinterVO printerVO) {
		return printerDao.insertPrinter(printerVO);
	}
	
	/**
	 * 修改打印机配置信息
	 * @param printerVO
	 *            打印机配置信息
	 */
	public int updatePrinter(PrinterVO printerVO) {
		return printerDao.updatePrinter(printerVO);
	}
	
	/**
	 * 删除打印机配置信息
	 * @param printerVO
	 *            打印机配置信息
	 */
	public int deletePrinter(PrinterVO printerVO) {
		return printerDao.deletePrinter(printerVO);
	}

}
