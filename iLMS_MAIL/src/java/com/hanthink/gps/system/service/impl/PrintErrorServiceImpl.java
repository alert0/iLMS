/**
 * 
 */
package com.hanthink.gps.system.service.impl;

import java.util.List;

import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.system.dao.PrintErrorDao;
import com.hanthink.gps.system.service.PrintErrorService;
import com.hanthink.gps.system.vo.PrintErrorVO;

/**
 * 自动打印异常邮件信息提醒
 * @author chenyong
 * @date   2016-09-22
 */
public class PrintErrorServiceImpl extends BaseServiceImpl implements PrintErrorService{
	
	private PrintErrorDao dao;
  
	@Override
	public List<PrintErrorVO> queryG1PrintErrorInfo(PrintErrorVO pVo) {
		
		return dao.queryG1PrintErrorInfo(pVo);
	}
	  
	
	public PrintErrorDao getDao() {
		return dao;
	}
	
	public void setDao(PrintErrorDao dao) {
		this.dao = dao;
	}
  
  
}
