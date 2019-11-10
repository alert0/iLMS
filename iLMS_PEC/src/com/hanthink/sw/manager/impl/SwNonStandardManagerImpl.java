package com.hanthink.sw.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sw.dao.SwNonStandardDao;
import com.hanthink.sw.manager.SwNonStandardManager;
import com.hanthink.sw.model.SwNonStandardModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("SwNonStandardManager")
public  class SwNonStandardManagerImpl extends AbstractManagerImpl<String, SwNonStandardModel> 
implements SwNonStandardManager{
	
	@Resource
	private SwNonStandardDao dao;
	
	@Override
	protected Dao<String, SwNonStandardModel> getDao() {
		
		return dao;
	}
	
	@Override
	public void uploadSignProFile(SwNonStandardModel model) {
//		dao.insertNonStandModel(model);
		dao.uploadSignProFile(model);
	}

	@Override
	public PageList<SwNonStandardModel> queryNonStandardPage(SwNonStandardModel model, DefaultPage p) {
		
		return dao.queryNonStandardPage(model,p);
	}

	@Override
	public List<SwNonStandardModel> getUploadPicIdBy(Map<String, String> map) {
		
		return dao.getUploadPicIdBy(map);
	}

	@Override
	public void submitFeedback(SwNonStandardModel model) {
		 dao.submitFeedback(model); //检查提交后更新主表中数据
		 if (model.getFeedbackPicArr().length > 0) {
			for (int i = 0; i < model.getFeedbackPicArr().length; i++) {
				model.setFeedbackPic(model.getFeedbackPicArr()[i]);
				dao.submitFeedbackPic(model); //检查提交后更新图片与主表关系表
			}
		}
	}

	@Override
	public List<SwNonStandardModel> queryNonStandDetail(SwNonStandardModel model) {
		
		return dao.queryNonStandDetail(model);
	}

	@Override
	public String getSeqCheck() {
		
		return dao.getSeqCheck();
	}

	@Override
	public void updateNonStandard(SwNonStandardModel model) {
		dao.updateNonStandard(model);
	}

	@Override
	public List<SwNonStandardModel> getUploadPicIdByModel(SwNonStandardModel model) {
		
		return dao.getUploadPicIdByModel(model);
	}

	@Override
	public void insertIntoNonStandCheck(SwNonStandardModel model, String ipAddr) {
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SW_NON_STANDAR_CHECK");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		model.setCreationUserIp(ipAddr);
		dao.insertNonStandModel(model);
	}

	@Override
	public PageList<SwNonStandardModel> selectDetail(SwNonStandardModel model, DefaultPage p) {
		
		return dao.selectDetail(model,p);
	}

	@Override
	public void deleteNonStandPic(SwNonStandardModel model) {
		dao.deleteNonStandPic(model);
		dao.deleteNonStandCheck(model);
	}

	@Override
	public List<SwNonStandardModel> getUploadPicIdByCheckId(String checkId) {
		
		return dao.getUploadPicIdByCheckId(checkId);
	}
	
	@Override
	public String getParamsByString(Map<String, String> map) {
		
		return dao.getParamsByString(map);
	}

	@Override
	public List<SwNonStandardModel> getCheckHistory(SwNonStandardModel model) {
		
		return dao.getCheckHistory(model);
	}


}
