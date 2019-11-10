package com.hanthink.gps.pub.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.SuppGroupDao;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SuppGroupVO;

public class SuppGroupDaoImpl extends BaseDaoSupport implements SuppGroupDao {
	
	/**
	 * 查询
	 * @param 
	 * @return 
	 */

	public PageObject querySuppGroupForPage(SuppGroupVO suppGroupVO, int start,
			int limit) {
		return this.executeQueryForPage("pub.select_SupplierGroupForPage", suppGroupVO, start, limit);
	}

	
	/**
	 * 新增
	 * @param 
	 * @return 
	 */
	

	public Object insertSuppGroup(SuppGroupVO suppGroupVO) {
		return this.executeInsert("pub.insert_insertSuppGroup",suppGroupVO);
		
	}

	/**
	 * 新增
	 * @param 
	 * @return 
	 * @return 
	 */
	public int updateSuppGroup(SuppGroupVO suppGroupVO) {
		return this.executeUpdate("pub.update_updateSuppGroup", suppGroupVO);
		
	}


	/**
	 * 删除
	 * @param 
	 * @return 
	 */

	public int deleteSuppGroup(SuppGroupVO suppGroupVO) {
		return this.executeDelete("pub.delete_deleteSuppGroup", suppGroupVO);
		
	}




	/**
	 *查询
	 * @param 
	 * @return 
	 */


	public PageObject querySupplierForPage(String groupId, int start, int limit) {
		return this.executeQueryForPage("pub.select_SupplierForPage", groupId, start, limit);
	}

	/**
	 *查询未配置供应商
	 * @param 
	 * @return 
	 */
	public PageObject queryNotSupplierForPage(SuppGroupVO suppGroupVO,
			int start, int limit) {
		return this.executeQueryForPage("pub.select_queryNotSupplierForPage", suppGroupVO, start, limit);
	}
	/**
	 *新增供应商
	 * @param 
	 * @return 
	 */

	public void addConfigSupplier(SuppGroupVO suppGroupVO,
			String[] supplierNoArr) {
		List<SuppGroupVO> suppGroupVOs = new ArrayList<SuppGroupVO>();
		for(int i = 0; i < supplierNoArr.length; i ++){
			SuppGroupVO suppGroupVO1 = new SuppGroupVO(); 
			suppGroupVO1.setGroupId(suppGroupVO.getGroupId());
			suppGroupVO1.setCreateUser(suppGroupVO.getCreateUser());
			suppGroupVO1.setSupplierNo(supplierNoArr[i]);
			suppGroupVOs.add(suppGroupVO1);
		}
		
		this.executeBatchInsert("pub.insert_addConfigSupplier", suppGroupVOs);
	}


	public void deleteConfiigSupplier(SuppGroupVO suppGroupVO,
			String[] supplierNoArr) {
		List<SuppGroupVO> suppGroupVOs = new ArrayList<SuppGroupVO>();
		for(int i = 0; i < supplierNoArr.length; i ++){
			SuppGroupVO vo = new SuppGroupVO();
			vo.setGroupId(suppGroupVO.getGroupId());
			vo.setSupplierNo(supplierNoArr[i]);
			suppGroupVOs.add(vo);
		}
		
		this.executeBatchInsert("pub.delete_deleteConfigSupplier", suppGroupVOs);
		
		
	}
		
	}


