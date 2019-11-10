package com.hanthink.gps.pub.dao.impl;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.PrinterDao;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrinterVO;

public class PrinterDaoImpl extends BaseDaoSupport implements PrinterDao {

	/**
	 * 查询打印机配置信息
	 * @param start
	 * @param limit
	 * @return PageObject
	 */
	public PageObject queryPrinterForPage(PrinterVO printerVO, int start, int limit) {
		return this.executeQueryForPage("pub.select_queryPrinterForPage", printerVO, start, limit);
	}

	/**
	 * 新增打印机配置信息
	 * @param synPartVO
	 * @return SynPartVO
	 */
	public PrinterVO insertPrinter(PrinterVO printerVO) {
		return (PrinterVO) this.executeInsert("pub.insert_insertPrinter", printerVO);
	}

	/**
	 * 修改打印机配置信息
	 * @param printerVO
	 * @return int
	 */
	public int updatePrinter(PrinterVO printerVO) {
		return this.executeUpdate("pub.update_updatePrinter", printerVO);
	}

	/**
	 * 删除打印机配置信息
	 * @param printerVO
	 * @return int
	 */
	public int deletePrinter(PrinterVO printerVO) {
		return this.executeDelete("pub.delete_deletePrinter", printerVO);
	}

}
