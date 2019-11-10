package com.hanthink.pup.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.dao.PupProPlanDao;
import com.hanthink.pup.model.PupProPlanPageModel;
import com.hanthink.pup.model.PupProPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class PupProPlanDaoImpl extends MyBatisDaoImpl<String, PupProPlanModel>
			implements PupProPlanDao{

	@Override
	public String getNamespace() {
		return PupProPlanModel.class.getName();
	}

	@Override
	public List<PupProPlanModel> queryProPlanForPage(PupProPlanPageModel model,Page page) throws Exception {
		return this.getByKey("queryProPlanForPage",model,page);
	}

	@Override
	public DictVO querySendFlag()throws Exception{
		Map<String, Object> map = new HashMap<>();
		return (DictVO) this.getOne("querySendFlag", map);
	}
	
	@Override
	public List<PupProPlanModel> queryProPlanByKey(PupProPlanPageModel plan) throws Exception {
		return this.getByKey("queryProPlanForPage",plan);
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
	public void getProPlan(PupProPlanPageModel model) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("afftimeStart", model.getAfoffTimeStart());
		paramMap.put("afoffTimeEnd", model.getAfoffTimeEnd());
		paramMap.put("week", model.getWeek());
		paramMap.put("factoryCode", model.getFactoryCode());
		paramMap.put("opeId", model.getOpeId());
		this.getByKey("getProPlan", paramMap);
	}
}
