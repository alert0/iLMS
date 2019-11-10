package com.hanthink.pup.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.PupProPlanDao;
import com.hanthink.pup.model.PupProPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.util.ContextUtil;

@Repository
public class PupProPlanDaoImpl extends MyBatisDaoImpl<String, PupProPlanModel>
			implements PupProPlanDao{

	@Override
	public String getNamespace() {
		return PupProPlanModel.class.getName();
	}

	@Override
	public List<PupProPlanModel> queryProPlanForPage(PupProPlanModel model,Page page) throws Exception {
		return this.getByKey("queryProPlanForPage",model,page);
	}
	
	@Override
	public List<PupProPlanModel> queryProPlanByKey(PupProPlanModel model) throws Exception {
		return this.getByKey("queryProPlanForPage",model);
	}
	
	@Override
	public Integer getCountForImport(Map<String, String> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("getProPlanCountForImport", paramMap);
	}

	@Override
	public void deleteProPlanTempDataByUUID(String uuid) throws Exception {
		this.deleteByKey("deleteProPlanTempDataByUUID", uuid);
	}

	@Override
	public void insertProPlanIntoTempData(List<PupProPlanModel> plan) throws Exception {
		this.insertBatchByKey("insertProPlanIntoTempData", plan);
	}

	@Override
	public void checkProplanImportDataInformation(Map<String, String> map) throws Exception {
			this.sqlSessionTemplate.selectOne("checkProplanImportDataInformation",map);
	}
	
	@Override
	public List<PupProPlanModel> queryImportInformationForPage(Map<String, String> paramMap, Page page)
			throws Exception {
		return this.getByKey("queryImportInformationForPage", paramMap, page);
	}
	
	@Override
	public String queryProPlanImportFlag(String uuid) throws Exception {	
		return this.sqlSessionTemplate.selectOne(getNamespace()+".queryProPlanImportFlag", uuid);
	}

	@Override
	public List<String> queryUpdateDataFromImportPlan(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectList(getNamespace()+".queryUpdateDataFromImportPlan",paramMap);
	}
	
	@Override
	public void updateProPlanImportData(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updateProPlanImportData",paramMap);
	}
	
	@Override
	public void deleteRegulaData(Map<String, String> paramMap) throws Exception {
		this.deleteByKey("deleteRegulaData", paramMap);
	}
	
	@Override
	public void insertTempDataToRegula(Map<String, String> paramMap) throws Exception {
		this.insertByKey("insertTempDataToRegula", paramMap);
	}
	
	@Override
	public void updateImportStatus(Map<String, String> paramMap) throws Exception {
		this.updateByKey("updateImportStatus", paramMap);
	}
	
	@Override
	public void deleteImportedDataFromTemp(Map<String, String> paramMap) throws Exception {
		this.deleteByKey("deleteImportedTempData", paramMap);
	}
	
	@Override
	public List<PupProPlanModel> queryImportInformationForExport(String uuid) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", uuid);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		return this.getByKey("queryImportInformationForPage", paramMap);
	}
	
	@Override
	public void getProPlan(Map<String, String> paramMap) throws Exception {
		this.sqlSessionTemplate.selectOne("getProPlan", paramMap);
	}

	@Override
	public PupProPlanModel querySearchTimes(String fatoryCode) throws Exception {
		return this.sqlSessionTemplate.selectOne("querySearchTimes", fatoryCode);
	}
}
