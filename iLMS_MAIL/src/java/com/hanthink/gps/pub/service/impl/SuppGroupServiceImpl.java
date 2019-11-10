package com.hanthink.gps.pub.service.impl;

import com.hanthink.gps.pub.dao.SuppGroupDao;
import com.hanthink.gps.pub.service.SuppGroupService;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SuppGroupVO;


public class SuppGroupServiceImpl extends BaseServiceImpl implements
		SuppGroupService {
	
	
	private SuppGroupDao suppGroupDao;

	public SuppGroupDao getSuppGroupDao() {
		return suppGroupDao;
	}

	public void setSuppGroupDao(SuppGroupDao suppGroupDao) {
		this.suppGroupDao = suppGroupDao;
	}

	
	/**
	 * 查询供应商分组
	 * @param           
	 * @return 
	 */
	
	public PageObject querySuppGroupForPage(SuppGroupVO suppGroupVO, int start,
			int limit) {
		return suppGroupDao.querySuppGroupForPage(suppGroupVO, start, limit);
	}

	
	
	
	
	/**
	 * 新增供应商分组 
	 * @param           
	 * @return 
	 */

	
public void insertSuppGroup(SuppGroupVO suppGroupVO) {
	suppGroupDao.insertSuppGroup(suppGroupVO);
	
}
/**
 * 修改供应商分组 
 * @param           
 * @return 
 */

	public void updateSuppGroup(SuppGroupVO suppGroupVO) {
		suppGroupDao.updateSuppGroup(suppGroupVO);
		
	}
	/**
	 * 删除供应商分组 
	 * @param           
	 * @return 
	 */
	public void deleteSuppGroup(SuppGroupVO suppGroupVO) {
		suppGroupDao.deleteSuppGroup(suppGroupVO);
		
	}

	

	/**
	 * 查询供应商
	 * @param           
	 * @return 
	 */

	public PageObject querySupplierForPage(String groupId, int start, int limit) {
		return suppGroupDao.querySupplierForPage(groupId, start, limit);
	}
	/**
	 * 查询未配置供应商
	 * @param           
	 * @return 
	 */
	public PageObject queryNotSupplierForPage(SuppGroupVO suppGroupVO,
			int start, int limit) {
		return suppGroupDao.queryNotSupplierForPage(suppGroupVO, start, limit);
		
	}
	/**
	 * 新增未配置供应商
	 * @param           
	 * @return 
	 */
	public void addConfigSupplier(SuppGroupVO suppGroupVO,
			String[] supplierNoArr) {
		
		suppGroupDao.addConfigSupplier(suppGroupVO, supplierNoArr);
		
	}

	public void deleteConfigSupplier(SuppGroupVO suppGroupVO,
			String[] supplierNoArr) {
		suppGroupDao.deleteConfiigSupplier(suppGroupVO,supplierNoArr);
		
	}

	

}