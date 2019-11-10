package com.hanthink.gps.pub.service;

import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.PageObject;

/**
 * 物流公司管理service
 * @author zuosl
 *
 */
public interface LogCompanyService extends BaseService {

	/**
	 * 新增物流公司
	 * @param pageLogCompanyVO
	 * @author zuosl 2016-3-23
	 */
	void insert(LogCompanyVO logCompanyVO);

	/**
	 * 分页查询物流公司信息
	 * @param logCompanyVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-3-23
	 */
	PageObject queryLogCompanyForPage(LogCompanyVO logCompanyVO, int start,
			int limit);

	/**
	 * 修改物流公司
	 * @param logCompanyVO
	 * @return
	 * @author zuosl 2016-3-23
	 */
	int updateLogCompany(LogCompanyVO logCompanyVO);

	/**
	 * 删除物流公司信息
	 * @param pageLogCompanyVO
	 * @return
	 * @author zuosl 2016-3-23
	 */
	int deleteLogCompany(LogCompanyVO pageLogCompanyVO);

	/**
	 * 分页查询物流供应商已配置的供应商信息
	 * @param companyNo
	 * @return
	 * @author zuosl 2016-3-24
	 */
	PageObject queryLogConfigSupplierForPage(String companyNo, int start, int limit);

	/**
	 * 分页查询物流供应商未配置的供应商信息
	 * @param logCompanyVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-3-24
	 */
	PageObject queryLogNotConfigSupplierForPage(LogCompanyVO logCompanyVO, int start,
			int limit);

	/**
	 * 增加物流公司供应商配置
	 * @param companyVO
	 * @param supplierNoArr
	 * @author zuosl 2016-3-24
	 */
	void addConfigSupplier(LogCompanyVO companyVO, String[] supplierNoArr);

	/**
	 * 删除物流公司供应商配置
	 * @param companyVO
	 * @param supplierNoArr
	 * @author zuosl 2016-3-24
	 */
	void deleteConfigSupplier(LogCompanyVO companyVO, String[] supplierNoArr);

}
