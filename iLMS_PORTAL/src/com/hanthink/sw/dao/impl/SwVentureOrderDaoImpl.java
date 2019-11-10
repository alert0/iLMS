package com.hanthink.sw.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwVentureOrderDao;
import com.hanthink.sw.model.SwVentureOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class SwVentureOrderDaoImpl extends MyBatisDaoImpl<String, SwVentureOrderModel>
			implements SwVentureOrderDao{

	@Override
	public String getNamespace() {
		return SwVentureOrderModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SwVentureOrderModel> queryVentureOrderForPage(SwVentureOrderModel model, Page page) {
		return this.getListByKey("queryVentureOrderForPage", model, page);
	}

	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	@Override
	public void insertImportTempData(List<SwVentureOrderModel> importList) {
		this.insertBatchByKey("insertImportTempData", importList);
	}

	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(getNamespace() +".checkImportData", ckParamMap);
	}

	@Override
	public String queryIsImportFlag(String uuid) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace() +".queryIsImportFlag", uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwVentureOrderModel> queryImportTempData(Map<String, String> paramMap, Page page) {
		
		return this.getListByKey("queryImportTempData", paramMap, page);
	}

	@Override
	public List<SwVentureOrderModel> queryTempDataForExport(Map<String, String> paramMap) {
		
		return this.getByKey("queryImportTempData", paramMap);
	}

	@Override
	public List<SwVentureOrderModel> queryForInsertList(Map<String, Object> paramMap) {
		
		return this.getByKey("queryForInsertList", paramMap);
	}

	@Override
	public void insertImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertImportData", paramMap);
		this.insertByKey("insertVersion", paramMap);
	}

	@Override
	public void updateVentureOrderImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateVentureOrderImportDataImpStatus", paramMap);
	}

	@Override
	public void deleteAllPuchaeIsNull(Map<String, Object> paramMap) {
		this.deleteByKey("deleteAllPuchaeIsNull", paramMap);
	}

	@Override
	public List<SwVentureOrderModel> queryForPuchaeIsNull(Map<String, Object> paramMap) {
		return this.getByKey("queryForPuchaeIsNull", paramMap);
	}

	@Override
	public void deleteAllHeader(List<SwVentureOrderModel> list) {
		this.deleteByKey("deleteAllHeader", list);
	}

	@Override
	public void deleteAllBody(List<SwVentureOrderModel> list) {
		this.deleteByKey("deleteAllBody", list);
	}
	
	/**
	 * 订单界面数据导出
	 * @param model
	 * @return
	 * @author zmj
	 * @date 2019年8月24日
	 */
	@Override
	public List<SwVentureOrderModel> queryVentureOrderForExport(SwVentureOrderModel model) {
		return this.getByKey("queryVentureOrderForPage", model);
	}

	@Override
	public void orederRelease(Map<String, Object> paramMap) {
		this.sqlSessionTemplate.selectOne("orederRelease", paramMap);
	}

}
