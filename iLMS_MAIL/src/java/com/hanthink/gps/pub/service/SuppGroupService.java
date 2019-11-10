package com.hanthink.gps.pub.service;

import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SuppGroupVO;

public interface SuppGroupService extends BaseService {
	
	
	
	
	/**
	 * 查询供应商分组信息
	 * @param dDictVO
	 * @param start
	 * @param limit
	 * @return PageObject
	 */

	PageObject querySuppGroupForPage(SuppGroupVO suppGroupVO, int start,int limit);

	
	
	
	/**
	 * 新增供应商分组信息 
	 * @param           
	 * @return 
	 */
	void insertSuppGroup(SuppGroupVO suppGroupVO);



	/**
	 * 修改供应商分组信息 
	 * @param           
	 * @return 
	 */
	void updateSuppGroup(SuppGroupVO suppGroupVO);


	/**
	 * 删除供应商分组信息 
	 * @param           
	 * @return 
	 */

	void deleteSuppGroup(SuppGroupVO suppGroupVO);


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
	 * 增加物流公司供应商配置
	 * @param suppGroupVO
	 * @param supplierNoArr
	 * @author smy 2016-3-24
	 */

	void addConfigSupplier(SuppGroupVO suppGroupVO, String[] supplierNoArr);



	/**
	 * 删除物流公司供应商配置
	 * @param suppGroupVO
	 * @param supplierNoArr
	 * @author smy 2016-3-24
	 */
	void deleteConfigSupplier(SuppGroupVO suppGroupVO, String[] supplierNoArr);


	

}
