package com.hanthink.gps.pub.dao;

import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SuppGroupVO;

public interface SuppGroupDao {

	/**
	 * 查询
	 * @param 
	 * @return 
	 */
	
	PageObject querySuppGroupForPage(SuppGroupVO suppGroupVO, int start,
			int limit);

	
	
	/**
	 * 新增
	 * @param 
	 * @return 
	 */
	Object insertSuppGroup(SuppGroupVO suppGroupVO);

	/**
	 * 修改
	 * @param 
	 * @return 
	 */

	int updateSuppGroup(SuppGroupVO suppGroupVO);

	/**
	 * 删除
	 * @param 
	 * @return 
	 */

	int deleteSuppGroup(SuppGroupVO suppGroupVO);


	/**
	 * 查询供应商
	 * @param 
	 * @return 
	 */

	PageObject querySupplierForPage(String groupId, int start, int limit);

	/**
	 * 查询未配置供应商
	 * @param 
	 * @return 
	 */

	PageObject queryNotSupplierForPage(SuppGroupVO suppGroupVO, int start,
			int limit);

	/**
	 * 新增未配置供应商
	 * @param 
	 * @return 
	 */

	void addConfigSupplier(SuppGroupVO suppGroupVO, String[] supplierNoArr);



	/**
	 * 删除配置供应商
	 * @param 
	 * @return 
	 */
	void deleteConfiigSupplier(SuppGroupVO suppGroupVO, String[] supplierNoArr);


}
