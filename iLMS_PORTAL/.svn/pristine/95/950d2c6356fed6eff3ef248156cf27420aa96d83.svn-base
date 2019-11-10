package com.hanthink.sw.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwMaterialOrderDao;
import com.hanthink.sw.dao.SwNonStandardDao;
import com.hanthink.sw.model.SwNonStandardModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

import bsh.This;

@Repository
public class SwNonStandardDaoImpl extends MyBatisDaoImpl<String, SwNonStandardModel> 
implements SwNonStandardDao{

	@Override
	public String getNamespace() {
		
		return SwNonStandardModel.class.getName();
	}

	@Override
	public void uploadSignProFile(SwNonStandardModel model) {
		this.updateByKey("uploadSignProFile", model);
	}

	@Override
	public PageList<SwNonStandardModel> queryNonStandardPage(SwNonStandardModel model, DefaultPage p) {
		
		return (PageList<SwNonStandardModel>) this.getByKey("queryNonStandardPage", model, p);
	}

	@Override
	public List<SwNonStandardModel> getUploadPicIdBy(Map<String, String> map) {
		
		return this.getByKey("getUploadPicIdBy", map);
	}

	@Override
	public void submitFeedback(SwNonStandardModel model) {
		
		this.updateByKey("submitFeedback", model) ;
	}

	@Override
	public void submitFeedbackPic(SwNonStandardModel model) {
		this.updateByKey("submitFeedbackPic", model);
	}

	@Override
	public List<SwNonStandardModel> queryNonStandDetail(SwNonStandardModel model) {
		
		return this.getByKey("queryNonStandDetail", model);
	}

	@Override
	public void insertNonStandModel(SwNonStandardModel model) {
		this.insertByKey("insertNonStandModel", model);
	}

	@Override
	public String getSeqCheck() {
		
		return this.sqlSessionTemplate.selectOne("getSeqCheck");
	}

	@Override
	public void updateNonStandard(SwNonStandardModel model) {
		this.updateByKey("updateNonStandard", model);
	}

	@Override
	public List<SwNonStandardModel> getUploadPicIdByModel(SwNonStandardModel model) {
		
		return this.getByKey("getUploadPicIdByModel", model);
	}

	@Override
	public PageList<SwNonStandardModel> selectDetail(SwNonStandardModel model, DefaultPage p) {
		
		return (PageList<SwNonStandardModel>) this.getByKey("selectDetail", model, p);
	}

	@Override
	public void deleteNonStandPic(SwNonStandardModel model) {
		this.deleteByKey("deleteNonStandPic", model);
	}

	@Override
	public void deleteNonStandCheck(SwNonStandardModel model) {
		this.deleteByKey("deleteNonStandCheck", model);
	}

	@Override
	public List<SwNonStandardModel> getUploadPicIdByCheckId(String checkId) {
		
		return this.getByKey("getUploadPicIdByCheckId", checkId);
	}

	@Override
	public String getParamsByString(Map<String, String> map) {
		
		return this.sqlSessionTemplate.selectOne("getParamsByString", map);
	}

}
