package com.hanthink.pup.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.dao.PupRouteMessageDao;
import com.hanthink.pup.model.PupRouteMessageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class PupRouteMessageDaoImpl extends MyBatisDaoImpl<String, PupRouteMessageModel>
				implements PupRouteMessageDao{

	@Override
	public String getNamespace() {
		return PupRouteMessageModel.class.getName();
	}

	@Override
	public List<PupRouteMessageModel> queryRouteMessageForPage(PupRouteMessageModel model, Page page)
			throws Exception {
		return this.getByKey("queryRouteMessageForPage", model, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> getBatches() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return this.getList("getBatch", map);
	}

	@Override
	public List<PupRouteMessageModel> queryRouteMessageForExport(PupRouteMessageModel model) throws Exception {
		return this.getByKey("queryRouteMessageForPage", model);
	}

	@Override
	public void removeRouteMessagesByIds(String[] ids) throws Exception {
		this.deleteByKey("removeRouteMessagesByIds",ids);
	}

	@Override
	public void deleteTempRouteMessageByUUID(String uuid) throws Exception {
		this.deleteByKey("deleteTempRouteMessageByUUID",uuid);
	}
	
	@Override
	public Integer getCountForImport(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("getCountForRouteImport", paramMap);
	}
	
	@Override
	public void insertRouteMessageToTempTable(List<PupRouteMessageModel> importList) throws Exception {
		this.insertBatchByKey("insertRouteMessageToTempTable", importList);
	}

	@Override
	public void checkImportRouteMessage(Map<String, String> map) throws Exception {
		this.sqlSessionTemplate.selectOne("checkImportRouteMessage", map);
	}

	@Override
	public String queryImportFlag(String uuid) throws Exception {
		return this.sqlSessionTemplate.selectOne(getNamespace()+".queryImportFlag", uuid);
	}

	@Override
	public List<String> queryUpdateDataFromTemp(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectList("queryUpdateRouteFromTemp",paramMap);
	}

	@Override
	public void updateRouteMessageImportData(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updateRouteMessageImportData", paramMap);
	}

	@Override
	public void insertTempDataToRegula(Map<String, Object> paramMap) throws Exception {
		this.insertByKey("insertTempRouteToRegula", paramMap);
	}

	@Override
	public void updateRouteMessageImpStatus(Map<String, Object> paramMap) throws Exception {
		this.updateByKey("updateRouteMessageImpStatus", paramMap);
	}

	@Override
	public List<PupRouteMessageModel> queryImportModelForPage(Map<String, String> paramMap, Page page)
			throws Exception {
		return this.getByKey("queryImportModelForPage", paramMap, page);
	}
	
	@Override
	public List<PupRouteMessageModel> queryImportModelForExport(Map<String, String> paramMap) throws Exception {
		return this.getByKey("queryImportModelForPage", paramMap);
	}
	@Override
	public void deleteRouteMessageFromRegula(Map<String, Object> paramMap) throws Exception {
		this.deleteByKey("deleteRouteMessageFromRegula",paramMap);
	}
	@Override
	public PupRouteMessageModel queryRouteMessageById(String id) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryRouteMessageById", id);
	}

	@Override
	public void updateRouteMessageById(PupRouteMessageModel model) throws Exception {
		this.updateByKey("updateRouteMessageById", model);
	}

}
