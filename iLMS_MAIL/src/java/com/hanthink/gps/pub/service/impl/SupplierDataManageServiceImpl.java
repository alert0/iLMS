package com.hanthink.gps.pub.service.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.SupplierDataManageDao;
import com.hanthink.gps.pub.service.SupplierDataManageService;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SupplierVO;

public class SupplierDataManageServiceImpl extends BaseServiceImpl implements SupplierDataManageService {

	private SupplierDataManageDao supDao;

	public SupplierDataManageDao getSupDao() {
		return supDao;
	}
	public void setSupDao(SupplierDataManageDao supDao) {
		this.supDao = supDao;
	}
	
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
		return supDao.querySupplierDataForPage(supVO, start, limit);
	}
	
	/**
	 * 供应商激活处理
	 * @param supVO
	 * @param genPwd
	 * @param roleIds
	 * @return
	 * @author zuosl 2016-5-10
	 */
	public boolean activeSupplier(SupplierVO supVO, String genPwd, List<String> roleIds) {
		
		//以供应商代码作为用户名新增一条供应商用户信息
		
		
		//更新供应商状态为已激活
		
		
		return false;
	}
	
	/**
	 * 根据供应商代码查询供应商信息
	 * @param supplierNo
	 * @return
	 * @author zuosl 2016-5-10
	 */
	public SupplierVO querySupplierInfoBySupplierNo(String supplierNo) {
		return supDao.querySupplierInfoBySupplierNo(supplierNo);
	}
	
	/**
	 * 更新供应商激活状态
	 * @param supVO
	 * @return
	 * @author zuosl 2016-5-10
	 */
	public int updateSupplierActiveStatus(SupplierVO supVO) {
		return supDao.updateSupplierActiveStatus(supVO);
	}
	
	/**
	 * 更新供应商信息
	 * @param supVO
	 * @return
	 * @author zuosl 2016-5-10
	 */
	public int updateSupplierInfoBySupplierNo(SupplierVO supVO) {
		return supDao.updateSupplierInfoBySupplierNo(supVO);
	}

}
