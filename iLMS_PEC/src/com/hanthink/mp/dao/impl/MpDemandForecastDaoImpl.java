package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpDemandForecastDao;
import com.hanthink.mp.model.MpDemandForecastModel;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 月度预测
 * 
 * @author WY
 * 
 */
@Repository
public class MpDemandForecastDaoImpl extends
		MyBatisDaoImpl<String, MpDemandForecastModel> implements
		MpDemandForecastDao {

	@Override
	public String getNamespace() {
		return MpDemandForecastModel.class.getName();
	}

	@Override
	public Integer genMonthDemandForcast(MpDemandForecastModel model) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("version", model.getVersion());
		paramMap.put("account", model.getCreationUser());
		paramMap.put("factoryCode", model.getFactoryCode());
		paramMap.put("foreType", model.getForeType());
		paramMap.put("outCode", "0");
		// 获取订购的单车BOM
		this.sqlSessionTemplate.selectOne("genMonthDemandForcast", paramMap);
		return Integer.parseInt(paramMap.get("outCode"));
	}

	@Override
	public List<MpDemandForecastModel> getVersion(Map<String, String> map) {
		return this.getByKey("getVersion", map);
	}

	@Override
	public List<MpDemandForecastModel> queryDemandForeCastForPage(
			MpDemandForecastModel model, DefaultPage p) {
		return this.getByKey("queryDemandForeCastForPage", model, p);
	}

	@Override
	public List<MpDemandForecastModel> queryDemandForeCastByKey(
			MpDemandForecastModel model) {
		return this.getByKey("queryDemandForeCastForPage", model);
	}

	@Override
	public void batchRemoveDemandForcast(String aryIds) {
		this.deleteByKey("batchRemoveDemandForcast", aryIds);
	}

	@Override
	public void updateDemandForcast(MpDemandForecastModel mpDemandForecastModel) {
		this.updateByKey("updateDemandForcast", mpDemandForecastModel);
	}

	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	@Override
	public List<MpDemandForecastModel> getDefaultVersion(Map<String, String> map) {
		return this.getByKey("getDefaultVersion", map);
	}

	@Override
	public void clearDemandForecast(MpDemandForecastModel model) {
		this.deleteByKey("clearDemandForecast", model);
	}

	@Override
	public void insertImportMonthTempData(List<MpDemandForecastModel> importList) {
		this.insertByKey("insertImportMonthTempData", importList);
	}

	@Override
	public void checkImportMonthData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkImportMonthData", ckParamMap);
	}

	@Override
	public String queryIsImportFlag(String uuid) {
		return this.sqlSessionTemplate.selectOne(getNamespace()
				+ ".queryIsImportFlag", uuid);
	}

	@Override
	public PageList<MpDemandForecastModel> queryImportTempData(
			Map<String, String> paramMap, Page page) {
		return (PageList) this.getByKey("queryImportTempData", paramMap, page);
	}

	@Override
	public List<MpDemandForecastModel> queryMonthTempDataForExport(
			Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList(
				"queryMonthTempDataForExport", paramMap);
	}

	@Override
	public List<MpDemandForecastModel> queryForInsertMonthList(
			Map<String, Object> paramMap) {
		return (List<MpDemandForecastModel>) this.getList(
				"queryForInsertMonthList", paramMap);
	}

	@Override
	public List<String> queryUpdateDataFromMonthImp(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.selectList(getNamespace()
				+ ".queryUpdateDataFromMonthImp", paramMap);
	}

	@Override
	public void updateMonthImportData(Map<String, Object> paramMap) {
		this.updateByKey("updateMonthImportData", paramMap);
	}

	@Override
	public void insertMonthImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertMonthImportData", paramMap);
	}

	@Override
	public void updateDemandForecastImportDataImpStatus(
			Map<String, Object> paramMap) {
		this.updateByKey("updateDemandForecastImportDataImpStatus", paramMap);
	}

	@Override
	public Integer releaseDemandForcast(MpDemandForecastModel model) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("version", model.getVersion());
		paramMap.put("account", model.getCreationUser());
		paramMap.put("factoryCode", model.getFactoryCode());
		paramMap.put("foreType", model.getForeType());
		paramMap.put("outCode", "0");
		// 获取订购的单车BOM
		this.sqlSessionTemplate.selectOne("releaseDemandForcast", paramMap);
		return Integer.parseInt(paramMap.get("outCode"));
	}

	@Override
	public Integer queryIsRelease(MpDemandForecastModel model) {
		return this.sqlSessionTemplate.selectOne("queryIsRelease", model);
	}

	@Override
	public Integer queryIsReleaseById(List<String> aryIds) {
		return this.sqlSessionTemplate.selectOne("queryIsReleaseById", aryIds);
	}

	@Override
	public Integer genDemandPartIf(MpDemandForecastModel model) {
		
	    HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("account", model.getCreationUser());
		paramMap.put("factoryCode", model.getFactoryCode());
		paramMap.put("foreType", model.getForeType());
		paramMap.put("startDate", model.getStartDate());
		paramMap.put("endDate", model.getEndDate());
		paramMap.put("outCode", "0");

		// 获取订购的单车BOM
		this.sqlSessionTemplate.selectOne("genDemandPartIf", paramMap);
		return Integer.parseInt(paramMap.get("outCode"));
	}

	@Override
	public void updateWeekForecastImportData(Map<String, Object> paramMap) {
		this.updateByKey("updateWeekForecastImportData", paramMap);
	}

	@Override
	public void insertWeekForecastImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertWeekForecastImportData", paramMap);
	}

	@Override
	public PageList<MpDemandForecastModel> queryDemandWeekForePage(MpDemandForecastModel model, DefaultPage p) {
		
		return (PageList<MpDemandForecastModel>) this.getByKey("queryDemandWeekForePage", model, p);
	}

	@Override
	public List<MpDemandForecastModel> downloadMpDemandWeekForeCast(MpDemandForecastModel model) {
		
		return this.getByKey("queryDemandWeekForePage", model);
	}

	@Override
	public void checkImportMonthDataFore(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkImportMonthDataFore", ckParamMap);
	}

	@Override
	public void updateWeekForecastVersion(Map<String, Object> paramMap) {
		this.updateByKey("updateWeekForecastVersion", paramMap);
	}

	@Override
	public Integer effectDemand(MpDemandForecastModel model) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("account", model.getCreationUser());
		paramMap.put("factoryCode", model.getFactoryCode());
		paramMap.put("foreType", model.getForeType());
		paramMap.put("outCode", "0");
		// 获取订购的单车BOM
		this.sqlSessionTemplate.selectOne("effectDemand", paramMap);
		return Integer.parseInt(paramMap.get("outCode"));
	}

	@Override
	public Integer isEffectVersion(MpDemandForecastModel model) {
		
		return this.sqlSessionTemplate.selectOne("isEffectVersion", model);
	}

	@Override
	public List<MpDemandForecastModel> getForeVersion(Map<String, String> map) {
		
		return this.getByKey("getForeVersion", map);
	}

	@Override
	public Integer validateVersionExists(SwDemandForecastModel model) {
		
		return this.sqlSessionTemplate.selectOne("validateVersionExists", model);
	}

	@Override
	public void submitVersion(SwDemandForecastModel model) {
		this.updateByKey("submitVersion", model);
	}

}
