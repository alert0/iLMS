package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.PartMaintenanceManagerDao;
import com.hanthink.inv.model.PartMainTenanance;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class PartMaintenanceManagerDaoImpl extends MyBatisDaoImpl<String, PartMainTenanance> implements PartMaintenanceManagerDao{

	@Override
	public String getNamespace() {
		return PartMainTenanance.class.getName();
	}
	
	/**
	 * 分页查询
	 *
	 * @param model
	 * @param p
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PartMainTenanance> querypartMaintenanceForPage(
			PartMainTenanance model, DefaultPage p) {
		return (PageList<PartMainTenanance>) this.getList("queryPartMainTenananceForPage", model, p);
	}


	

	/**
	 * 查询属地零件作为导出数据
	 *
	 * @param model
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartMainTenanance> queryexportList(PartMainTenanance model) {
		return (List<PartMainTenanance>) this.getList("queryPartMainTenananceExport", model);
	}

	/**
	 * 查询导入的临时数据
	 * @param model
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartMainTenanance> queryPartMaintenanceImport(PartMainTenanance model) {
		
		return (List<PartMainTenanance>) this.getList("queryPartMaintenanceImport", model);
	}

	/**
	 * excel数据导入到临时表
	 *
	 * @param file
	 * @return
	 * 李兴辉
	 */
	@Override
	public void importPartMaintenanceTemp(List<PartMainTenanance> importList) {
		this.insertBatchByKey("importPartMaintenanceTemp", importList);
	}

	/**
	 * excel数据导入到临时表
	 *
	 * @param file
	 * @return
	 * 李兴辉
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".checkImportData", ckParamMap);
		
	}

	/**
	 *导入确认
	 *
	 * @return
	 * 李兴辉
	 */
	@Override
	public void PartMaintenanceImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".PartMaintenanceImportData", ckParamMap);
		
	}
	/**
	 * 导入的临时数据查询
	 *
	 * @param model
	 * @param page
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PartMainTenanance> queryPartMaintenanceTemp(PartMainTenanance model, DefaultPage page) {
		return (PageList<PartMainTenanance>) this.getList("queryPartMaintenanceTemp", model,page);
	}

	/**
	 * 导入的临时数据查询
	 *
	 * @param model
	 * @param page
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartMainTenanance> queryPartMaintenanceTemp(PartMainTenanance model) {
		return (List<PartMainTenanance>) this.getList("queryPartMaintenanceTempForExport", model);
	}

	/**
	 * 获取仓库代码
	 * @param m
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartMainTenanance> getWarCode(PartMainTenanance m) {
		return (List<PartMainTenanance>) this.getList("getWarCode", m);
	}

	/**
	 * 临时数据删除
	 */
	@Override
	public int removePartMaintenanceTemp() {
		int deleteByKey = this.deleteByKey("removePartMaintenanceTemp");
		return deleteByKey;
	}

	/**
	 * 查询验证不通过的数据是否存在
	 * @return
	 * Lxh
	 */
	@Override
	public int queryImportFailCount(PartMainTenanance m) {
		@SuppressWarnings("unchecked")
		List<PartMainTenanance> list = (List<PartMainTenanance>) this.getList("queryImportFailCount",  m);
		return list.size();
	}

	/**
	 * 零件数据维护查询货架标签打印信息
	 * @Description:   
	 * @param: @param model
	 * @param: @return    
	 * @return: PartMainTenanance   
	 * @author: dtp
	 * @date: 2019年1月19日
	 */
	@Override
	public PartMainTenanance queryInvShelfPrintInfo(PartMainTenanance model) {
		return this.getUnique("queryInvShelfPrintInfo", model);
	}

}
