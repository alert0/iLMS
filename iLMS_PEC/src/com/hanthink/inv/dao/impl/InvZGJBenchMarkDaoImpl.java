package com.hanthink.inv.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvZGJBenchMarkDao;
import com.hanthink.inv.model.InvZGJBenchMarkModel;
import com.hanthink.mp.model.MpDemandForecastModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 支给件W-1周库存
 * @author WY
 *
 */
@Repository
public class InvZGJBenchMarkDaoImpl extends MyBatisDaoImpl<String, InvZGJBenchMarkModel> implements
		InvZGJBenchMarkDao{

	@Override
	public String getNamespace() {
		return InvZGJBenchMarkModel.class.getName();
	}

	@Override
	public PageList<InvZGJBenchMarkModel> queryBenchMarkForPage(
			InvZGJBenchMarkModel model, Page page) {
		return (PageList<InvZGJBenchMarkModel>) this.getByKey("queryBenchMarkForPage", model, page);
	}

	@Override
	public List<InvZGJBenchMarkModel> queryExportDataForExcel(
			InvZGJBenchMarkModel model) {
		return this.getByKey("queryBenchMarkForPage", model);
	}

	@Override
	public void updateObj(InvZGJBenchMarkModel model) {
		this.updateByKey("updateObj", model);
	}

	@Override
	public void addObj(InvZGJBenchMarkModel model) {
		this.insertByKey("addObj", model);
	}

	@Override
	public Integer queryIsSupportPart(InvZGJBenchMarkModel model) {
		return this.sqlSessionTemplate.selectOne("queryIsSupportPart", model);
	}

	@Override
	public Integer queryIsExistsBenchMark(InvZGJBenchMarkModel model) {
		return this.sqlSessionTemplate.selectOne("queryIsExistsBenchMark", model);
	}

	@Override
	public void delBatchObj(String[] array) {
		this.deleteByKey("delBatchObj", array);
	}

	@Override
	public Integer generateBenchMark(InvZGJBenchMarkModel model) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("account", model.getCalUser());
		paramMap.put("factoryCode", model.getFactoryCode());
		paramMap.put("outCode", "0");
		// 获取订购的单车BOM
		this.sqlSessionTemplate.selectOne("generateBenchMark", paramMap);
		return Integer.parseInt(paramMap.get("outCode"));
	}

	/***导入数据开始*****************************************/
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	@Override
	public void insertImportTempData(List<InvZGJBenchMarkModel> importList) {
		this.insertBatchByKey("insertImportTempData", importList);
	}

	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkImportMonthData", ckParamMap);
	}

	@Override
	public String queryIsImportFlag(String uuid) {
		return this.sqlSessionTemplate.selectOne(getNamespace()
				+ ".queryIsImportFlag", uuid);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<InvZGJBenchMarkModel> queryImportTempData(Map<String, String> paramMap, Page page) {
		
	      return (PageList) this.getByKey("queryImportTempData", paramMap, page);
	}

	@Override
	public List<InvZGJBenchMarkModel> queryTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList(
				"queryTempDataForExport", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvZGJBenchMarkModel> queryForInsertList(Map<String, Object> paramMap) {
		return (List<InvZGJBenchMarkModel>) this.getList(
				"queryForInsertList", paramMap);
	}

	@Override
	public List<String> queryUpdateDataFromImp(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.selectList(getNamespace()
				+ ".queryUpdateDataFromImp", paramMap);
	}

	@Override
	public void updateImportData(Map<String, Object> paramMap) {
		this.updateByKey("updateImportData", paramMap);
	}

	@Override
	public void insertImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertImportData", paramMap);
	}

	@Override
	public void updateImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateImportDataImpStatus", paramMap);
	}

	@Override
	public void dealStockUpdateExists(InvZGJBenchMarkModel model) {
		this.updateByKey("dealStockUpdateExists", model);
	}

	@Override
	public void dealStockNotUpdateExists(InvZGJBenchMarkModel model) {
		this.updateByKey("dealStockNotUpdateExists", model);
	}

	@Override
	public void dealStockDeleteExists(InvZGJBenchMarkModel model) {
		this.deleteByKey("dealStockDeleteExists", model);
	}

	
	/***支给件推算周维护开始**************************************************************************/
	@SuppressWarnings("unchecked")
	@Override
	public PageList<InvZGJBenchMarkModel> queryWeekCalForPage(InvZGJBenchMarkModel model, Page page) {
		
		return this.getListByKey("queryWeekCalForPage", model, page);
	}

	@Override
	public List<InvZGJBenchMarkModel> queryExportWeekCalForExcel(InvZGJBenchMarkModel model) {
		
		return this.getByKey("queryWeekCalForPage", model);
	}

	@Override
	public void updateWeekCalObj(InvZGJBenchMarkModel model, String ipAddr) {
		this.updateByKey("updateWeekCalObj", ipAddr);
	}

	@Override
	public void addWeekCalObj(InvZGJBenchMarkModel model) {
		this.insertByKey("addWeekCalObj", model);
	}

	@Override
	public void delBatchWeekCalObj(String[] array) {
		this.deleteByKey("delBatchWeekCalObj", array);
	}

	@Override
	public List<InvZGJBenchMarkModel> selectUnloadWare(Map<String, String> map) {
		
		return this.getByKey("selectUnloadWare", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<InvZGJBenchMarkModel> handleListPartNo(InvZGJBenchMarkModel model, DefaultPage p) {
		
		return (PageList<InvZGJBenchMarkModel>) this.getList("handleListPartNo", model, p);
	}

	
	/**支给件推算周导入************************************************/
	@Override
	public void deleteImportTempDataWeekCalByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataWeekCalByUUID", uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<InvZGJBenchMarkModel> queryImportTempDataWeekCal(Map<String, String> paramMap, Page page) {
		
		return this.getListByKey("queryImportTempDataWeekCal", paramMap, page);
	}

	@Override
	public List<InvZGJBenchMarkModel> queryTempDataForExportWeekCal(Map<String, String> paramMap) {
		
		return this.getByKey("queryTempDataForExportWeekCal", paramMap);
	}

	@Override
	public void insertImportTempDataWeekCal(List<InvZGJBenchMarkModel> importList) {
		this.insertByKey("insertImportTempDataWeekCal", importList);
	}

	@Override
	public String queryIsImportFlagWeekCal(String uuid) {
		return this.sqlSessionTemplate.selectOne(getNamespace()
				+ ".queryIsImportFlagWeekCal", uuid);
	}

	@Override
	public List<String> queryUpdateDataFromImpWeekCal(Map<String, Object> paramMap) {
		
		return this.sqlSessionTemplate.selectList(getNamespace()
				+ ".queryUpdateDataFromImpWeekCal", paramMap);
	}

	@Override
	public void updateImportDataWeekCal(Map<String, Object> paramMap) {
		this.updateByKey("updateImportDataWeekCal", paramMap);
	}

	@Override
	public void insertImportDataWeekCal(Map<String, Object> paramMap) {
		this.insertByKey("insertImportDataWeekCal", paramMap);
	}

	@Override
	public void updateImportDataImpStatusWeekCal(Map<String, Object> paramMap) {
		this.updateByKey("updateImportDataImpStatusWeekCal", paramMap);
	}

	@Override
	public List<InvZGJBenchMarkModel> queryForInsertListWeekCal(Map<String, Object> paramMap) {
		
		return this.getByKey("queryForInsertListWeekCal", paramMap);
	}

	@Override
	public void checkImportDataWeekCal(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkImportDataWeekCal", ckParamMap);
	}

	
	/**支给件缺件查询******************************************************/
	@SuppressWarnings("unchecked")
	@Override
	public PageList<InvZGJBenchMarkModel> queryWeekCalForPageDifference(InvZGJBenchMarkModel model, Page page) {
		
		return (PageList<InvZGJBenchMarkModel>) this.getList("queryWeekCalForPageDifference", model, page);
	}

	@Override
	public List<InvZGJBenchMarkModel> exportForExcelDifference(InvZGJBenchMarkModel model) {
		
		return this.getByKey("queryWeekCalForPageDifference", model);
	}

	@Override
	public Integer getZGJDifference(InvZGJBenchMarkModel model) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("account", model.getCreationUser());
		paramMap.put("factoryCode", model.getFactoryCode());
		paramMap.put("outCode", "0");
		// 获取订购的单车BOM
		this.sqlSessionTemplate.selectOne("getZGJDifference", paramMap);
		return Integer.parseInt(paramMap.get("outCode"));
	}

}
