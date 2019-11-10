package com.hanthink.gps.pub.dao.impl;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.SupplierDataManageDao;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SupplierVO;

public class SupplierDataManageDaoImpl extends BaseDaoSupport implements SupplierDataManageDao {

	/**
	 * 分页查询供应商数据
	 * @param supVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-5-9
	 */
	public PageObject querySupplierDataForPage(SupplierVO supVO, int start,
			int limit) {
		return this.executeQueryForPage("pub.select_querySupplierDataForList", supVO, start, limit);
	}

	/**
	 * 根据供应商代码查询供应商信息
	 * @param supplierNo
	 * @return
	 * @author zuosl 2016-5-10
	 */
	public SupplierVO querySupplierInfoBySupplierNo(String supplierNo) {
		return (SupplierVO) this.executeQueryForObject("pub.select_querySupplierInfoBySupplierNo", supplierNo);
	}

	/**
	 * 更新供应商激活状态
	 * @param supVO
	 * @return
	 * @author zuosl 2016-5-10
	 */
	public int updateSupplierActiveStatus(SupplierVO supVO) {
		return this.executeUpdate("pub.update_updateSupplierActiveStatus", supVO);
	}

	/**
	 * 更新供应商信息
	 * @param supVO
	 * @return
	 * @author zuosl 2016-5-10
	 */
	public int updateSupplierInfoBySupplierNo(SupplierVO supVO) {
		return this.executeUpdate("pub.update_updateSupplierInfoBySupplierNo", supVO);
	}

	
	
	
}
