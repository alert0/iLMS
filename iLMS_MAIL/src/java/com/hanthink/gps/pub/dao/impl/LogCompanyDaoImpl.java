package com.hanthink.gps.pub.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.LogCompanyDao;
import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.PageObject;

public class LogCompanyDaoImpl extends BaseDaoSupport implements LogCompanyDao {

	/**
	 * 根据公司代码查询公司信息
	 * @param companyNo
	 * @return
	 * @author zuosl 2016-3-23
	 */
	public LogCompanyVO queryByCompanyNo(String companyNo) {
		return (LogCompanyVO) this.executeQueryForObject("pub.select_queryLogCompanyByCompanyNo", companyNo);
	}

	/**
	 * 新增物流公司信息 
	 * @param logCompanyVO
	 * @author zuosl 2016-3-23
	 */
	public void insert(LogCompanyVO logCompanyVO) {
		this.executeInsert("pub.insert_insertLogCompany", logCompanyVO);
	}

	/**
	 * 分页查询物流公司信息
	 * @param logCompanyVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-3-23
	 */
	public PageObject queryLogCompanyForPage(LogCompanyVO logCompanyVO, int start, int limit) {
		return this.executeQueryForPage("pub.select_queryLogCompanyForList", logCompanyVO, start, limit);
	}

	/**
	 * 修改物流公司
	 * @param logCompanyVO
	 * @return
	 * @author zuosl 2016-3-23
	 */
	public int updateLogCompany(LogCompanyVO logCompanyVO) {
		return this.executeUpdate("pub.update_updateLogCompany", logCompanyVO);
	}

	/**
	 * 删除物流公司信息
	 * @param logCompanyVO
	 * @return
	 * @author zuosl 2016-3-23
	 */
	public int deleteLogCompany(LogCompanyVO logCompanyVO) {
		return this.executeDelete("pub.delete_deleteLogCompany", logCompanyVO);
	}

	/**
	 * 分页查询物流供应商已配置的供应商信息
	 * @param companyNo
	 * @return
	 * @author zuosl 2016-3-24
	 */
	public PageObject queryLogConfigSupplierForPage(String companyNo,
			int start, int limit) {
		return this.executeQueryForPage("pub.select_queryLogConfigSupplierForPage", companyNo, start, limit);
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
		return this.executeQueryForPage("pub.select_queryLogNotConfigSupplierForPage", logCompanyVO, start, limit);
	}

	/**
	 * 增加物流公司供应商配置
	 * @param companyVO
	 * @param supplierNoArr
	 * @author zuosl 2016-3-24
	 */
	public void addConfigSupplier(LogCompanyVO companyVO, String[] supplierNoArr) {
		List<LogCompanyVO> logCompanyVOs = new ArrayList<LogCompanyVO>();
		for(int i = 0; i < supplierNoArr.length; i ++){
			LogCompanyVO vo = new LogCompanyVO();
			vo.setCompanyNo(companyVO.getCompanyNo());
			vo.setEntryId(companyVO.getEntryId());
			vo.setSupplierNo(supplierNoArr[i]);
			logCompanyVOs.add(vo);
		}
		
		this.executeBatchInsert("pub.insert_addLogCompanyConfigSupplier", logCompanyVOs);
	}

	/**
	 * 删除物流公司供应商配置
	 * @param companyVO
	 * @param supplierNoArr
	 * @author zuosl 2016-3-24
	 */
	public void deleteConfigSupplier(LogCompanyVO companyVO,
			String[] supplierNoArr) {
		
		List<LogCompanyVO> logCompanyVOs = new ArrayList<LogCompanyVO>();
		for(int i = 0; i < supplierNoArr.length; i ++){
			LogCompanyVO vo = new LogCompanyVO();
			vo.setCompanyNo(companyVO.getCompanyNo());
			vo.setSupplierNo(supplierNoArr[i]);
			logCompanyVOs.add(vo);
		}
		
		this.executeBatchInsert("pub.delete_deleteLogConfigSupplier", logCompanyVOs);
		
	}

	
	
}
