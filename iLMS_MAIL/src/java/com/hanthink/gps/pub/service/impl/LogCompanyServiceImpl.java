package com.hanthink.gps.pub.service.impl;

import com.hanthink.gps.pub.dao.LogCompanyDao;
import com.hanthink.gps.pub.service.LogCompanyService;
import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.exception.BizException;

public class LogCompanyServiceImpl  extends BaseServiceImpl implements LogCompanyService {
	
	private LogCompanyDao logCompanyDao;
	

	public LogCompanyDao getLogCompanyDao() {
		return logCompanyDao;
	}
	public void setLogCompanyDao(LogCompanyDao logCompanyDao) {
		this.logCompanyDao = logCompanyDao;
	}
	
	/**
	 * 新增物流公司
	 * @param pageLogCompanyVO
	 * @author zuosl 2016-3-23
	 */
	public void insert(LogCompanyVO logCompanyVO) {
		
		//公司代码重复检查
		if(logCompanyDao.queryByCompanyNo(logCompanyVO.getCompanyNo()) != null){
			throw new BizException(Constants.MSG_ID_E_COMPANYNO_REPEAT); 
		}
		
		//新增物流公司信息
		logCompanyDao.insert(logCompanyVO);
		
	}
	
	/**
	 * 分页查询物流公司信息
	 * @param logCompanyVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-3-23
	 */
	public PageObject queryLogCompanyForPage(LogCompanyVO logCompanyVO,
			int start, int limit) {
		return logCompanyDao.queryLogCompanyForPage(logCompanyVO, start, limit);
	}
	
	/**
	 * 修改物流公司
	 * @param logCompanyVO
	 * @return
	 * @author zuosl 2016-3-23
	 */
	public int updateLogCompany(LogCompanyVO logCompanyVO) {
		return logCompanyDao.updateLogCompany(logCompanyVO);
	}
	
	/**
	 * 删除物流公司信息
	 * @param logCompanyVO
	 * @return
	 * @author zuosl 2016-3-23
	 */
	public int deleteLogCompany(LogCompanyVO logCompanyVO) {
		return logCompanyDao.deleteLogCompany(logCompanyVO);
	}
	
	/**
	 * 分页查询物流供应商已配置的供应商信息
	 * @param companyNo
	 * @return
	 * @author zuosl 2016-3-24
	 */
	public PageObject queryLogConfigSupplierForPage(String companyNo, int start, int limit) {
		return logCompanyDao.queryLogConfigSupplierForPage(companyNo, start, limit);
	}
	
	/**
	 * 分页查询物流供应商未配置的供应商信息
	 * @param logCompanyVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-3-24
	 */
	public PageObject queryLogNotConfigSupplierForPage(LogCompanyVO logCompanyVO,
			int start, int limit) {
		return logCompanyDao.queryLogNotConfigSupplierForPage(logCompanyVO, start, limit);
	}
	
	/**
	 * 增加物流公司供应商配置
	 * @param companyVO
	 * @param supplierNoArr
	 * @author zuosl 2016-3-24
	 */
	public void addConfigSupplier(LogCompanyVO companyVO, String[] supplierNoArr) {
		logCompanyDao.addConfigSupplier(companyVO, supplierNoArr);
	}
	
	/**
	 * 删除物流公司供应商配置
	 * @param companyVO
	 * @param supplierNoArr
	 * @author zuosl 2016-3-24
	 */
	public void deleteConfigSupplier(LogCompanyVO companyVO,
			String[] supplierNoArr) {
		logCompanyDao.deleteConfigSupplier(companyVO, supplierNoArr);
	}
	
	


}
