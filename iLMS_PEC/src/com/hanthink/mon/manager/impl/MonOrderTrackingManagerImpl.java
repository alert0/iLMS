package com.hanthink.mon.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.mon.dao.MonOrderTrackingDao;
import com.hanthink.mon.manager.MonOrderTrackingManager;
import com.hanthink.mon.model.MonOrderTrackingModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("monOrderTracking")
public class MonOrderTrackingManagerImpl extends AbstractManagerImpl<String, MonOrderTrackingModel>
					implements MonOrderTrackingManager{
	
	@Resource
	private MonOrderTrackingDao orderTrackingDao;
	
	@Override
	protected Dao<String, MonOrderTrackingModel> getDao() {
		return orderTrackingDao;
	}

	@Override
	public PageList<MonOrderTrackingModel> queryOrderTrackingForPage(MonOrderTrackingModel model, Page page)
			throws Exception {
		List<MonOrderTrackingModel> list = orderTrackingDao.queryOrderTrackingForPage(model, page);
		return new PageList<MonOrderTrackingModel>(list);
	}

	@Override
	public List<DictVO> queryForOrderType() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return orderTrackingDao.queryForOrderType(factoryCode);
	}

	@Override
	public List<MonOrderTrackingModel> exportForExcel(MonOrderTrackingModel model) throws Exception {
		return orderTrackingDao.exportForExcel(model);
	}

}
