package com.hanthink.sw.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwLogictiscOrderDao;
import com.hanthink.sw.manager.SwLogictiscOrderManager;
import com.hanthink.sw.model.SwLogictiscOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("logictiscs")
public class SwLogictiscOrderManagerImpl extends AbstractManagerImpl<SwLogictiscOrderModel, String>
								implements SwLogictiscOrderManager{
	@Resource
	private SwLogictiscOrderDao logictiscDao;
	
	@Override
	protected Dao<SwLogictiscOrderModel, String> getDao() {
		return logictiscDao;
	}

	@Override
	public PageList<SwLogictiscOrderModel> queryLogistiscsOrderForPage(SwLogictiscOrderModel model, Page page)
			throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<SwLogictiscOrderModel> list = logictiscDao.queryLogistiscsOrderForPage(model,page);
		return new PageList<SwLogictiscOrderModel>(list);
	}

}
