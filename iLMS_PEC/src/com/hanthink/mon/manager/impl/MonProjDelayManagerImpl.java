package com.hanthink.mon.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonProjDelayDao;
import com.hanthink.mon.manager.MonProjDelayManager;
import com.hanthink.mon.model.MonProjDelayModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("monProjDelay")
public class MonProjDelayManagerImpl extends AbstractManagerImpl<String, MonProjDelayModel>
						implements MonProjDelayManager{
	
	@Resource
	private MonProjDelayDao projDao;
	
	@Override
	protected Dao<String, MonProjDelayModel> getDao() {
		return projDao;
	}

	@Override
	public PageList<MonProjDelayModel> queryProjDealyForPage(MonProjDelayModel model, Page page)throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MonProjDelayModel> list = projDao.queryProjDealyForPage(model, page);
		Integer checkCount = 0;
		Integer prePareCount = 0;
		Integer distCount = 0;
		Integer emptCount = 0;
		if(list.size() > 0) {
			for (MonProjDelayModel monProjDelayModel : list) {
				if ("验收".equals(monProjDelayModel.getOpeType())) {
					checkCount += 1;
				}else if ("PC备货".equals(monProjDelayModel.getOpeType())) {
					prePareCount += 1;
				}else if ("配送".equals(monProjDelayModel.getOpeType())) {
					distCount += 1;
				}else if ("空箱返还".equals(monProjDelayModel.getOpeType())) {
					emptCount += 1;
				}
			}
			MonProjDelayModel countModel = new MonProjDelayModel();
			countModel.setCheckCount(checkCount.toString());
			countModel.setEmptCount(emptCount.toString());
			countModel.setPrePareCount(prePareCount.toString());
			countModel.setDistCount(distCount.toString());
			list.add(countModel);
		}
		return new PageList<MonProjDelayModel>(list);
	}
}
