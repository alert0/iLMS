package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.PupPickTimeDao;
import com.hanthink.pup.model.PupPickTimeModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

/**
* <pre> 
* 描述：固定取货时间维护dao层接实现类
* 构建组：x5-bpmx-platform
* 作者:zmj
* 日期:2018-09-17
* 版权：汉思信息技术有限公司
* </pre>
*/
@Repository
public class PupPickTimeDaoImpl extends MyBatisDaoImpl<String, PupPickTimeModel>
				implements PupPickTimeDao{
	
	@Override
	public String getNamespace() {
		return PupPickTimeModel.class.getName();
	}

	@Override
	public List<PupPickTimeModel> queryPickupTimeForPage(PupPickTimeModel model,Page page) throws Exception {
		return this.getByKey("queryPickupTimeForPage", model,page);
	}
	
	@Override
	public PupPickTimeModel getPickTimeByRouteCode(String routeCode) throws Exception {
		return (PupPickTimeModel) this.getOne("getPickTimeByRouteCode", routeCode);
	}
	
	@Override
	public PupPickTimeModel getPickTimeByTodayNo(String todayNo) throws Exception {
		return (PupPickTimeModel) this.getOne("getPickTimeByTodayNo", todayNo);
	}
	
	@Override
	public void createPickTime(PupPickTimeModel model) throws Exception {
		this.insertByKey("createPickTime", model);
	}
	@Override
	public void removeByRouteCodes(String[] routeCodes) throws Exception {
		this.deleteByKey("removeByRouteCodes", routeCodes);
	}
	@Override
	public void updatePickTime(PupPickTimeModel model) throws Exception {
		this.updateByKey("updatePickTime", model);
	}

	@Override
	public List<PupPickTimeModel> queryPickTimeForExport(PupPickTimeModel model) throws Exception {
		return this.getByKey("queryPickupTimeForPage", model);
	}
	
	@Override
	public List<PupPickTimeModel> queryPickTimeForImport(Map<String, String> param,Page page) throws Exception {
		return this.getByKey("queryPickTimeForImport",param,page);
	}

	@Override
	public void deletePickTimeTempDataByUUID(String uuid) throws Exception {
		this.deleteByKey("deleteTempDataByUUID", uuid);
	}

	@Override
	public void inserDataToTempDataTable(List<PupPickTimeModel> list) throws Exception {
		this.insertBatchByKey("insertDataIntoTempTable", list);
	}

	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkImportPickTimeData", ckParamMap);
	}

	@Override
	public String queryImportFlag(String uuid) throws Exception {
		return this.sqlSessionTemplate.selectOne(getNamespace()+".queryImportFlag",uuid);
	}

	@Override
	public List<PupPickTimeModel> queryPickupTimeTempDataForExport(Map<String, String> param) throws Exception {
		return this.sqlSessionTemplate.selectList("queryPickTimeForImport", param);
	}

	@Override
	public List<String> queryUpdateDataFromTemp(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectList("queryUpdateDataFromTemp", paramMap);
	}

	@Override
	public void updatePickTimeImportData(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updatePickTimeImportData", paramMap);
	}

	@Override
	public void insertTempDataToRegula(Map<String, Object> paramMap) throws Exception {
		this.insertByKey("insertTempDataToRegula", paramMap);
	}

	@Override
	public void updatePickTimeImportDataImpStatus(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updatePickTimeImportDataImpStatus",paramMap);
	}
}
