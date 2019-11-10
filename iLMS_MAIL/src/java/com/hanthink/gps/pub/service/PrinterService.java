package com.hanthink.gps.pub.service;

import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrinterVO;

public interface PrinterService extends BaseService {
	
	/**
	 * 根据指定的开始条数和查询条数，取得零件单落点信息
	 * @param queryInfo 查询条件
	 * @param start 开始记录
	 * @param limit 查询条数
	 * @return 查询结果
	 */
	public PageObject queryPrinterForPage(PrinterVO printerVO, int start, int limit);
	
	/**
	 * 新增打印机配置信息
	 * 
	 * @param printerVO
	 *            打印机配置信息
	 */
	public PrinterVO insertPrinter(PrinterVO printerVO);

	/**
	 * 更新打印机配置信息
	 * 
	 * @param printerVO
	 *            打印机配置信息
	 */
	public int updatePrinter(PrinterVO printerVO);
	
	/**
	 * 删除打印机配置信息
	 * @param printerVO
	 * @return int
	 */
	public int deletePrinter(PrinterVO printerVO);
	
}
