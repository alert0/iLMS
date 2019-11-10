package com.hanthink.mon.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonPCInStockDao;
import com.hanthink.mon.manager.MonPCInStockManager;
import com.hanthink.mon.model.MonPCInStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("PCInStockManager")
public class MonPCInStockManagerImpl extends AbstractManagerImpl<String,MonPCInStockModel>
			implements MonPCInStockManager{
	
	@Resource 
	private MonPCInStockDao pcInStockDao;
	
	@Override
	protected Dao<String, MonPCInStockModel> getDao() {
		return pcInStockDao;
	}

	@Override
	public PageList<MonPCInStockModel> queryPCInStock(MonPCInStockModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MonPCInStockModel> list = pcInStockDao.queryPCInStock(model, page);
		return new PageList<MonPCInStockModel>(list);
	}

	@Override
	public PageList<MonPCInStockModel> queryForLineCharts(MonPCInStockModel model) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return new PageList<MonPCInStockModel>(pcInStockDao.queryForLineCharts(model));
	}

}
