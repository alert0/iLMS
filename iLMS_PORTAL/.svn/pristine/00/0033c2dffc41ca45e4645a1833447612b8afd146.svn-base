package com.hanthink.jiso.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.jiso.dao.JisoNetDemandDao;
import com.hanthink.jiso.manager.JisoNetDemandManager;
import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoNetDemandModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Service("JisoNetDemandManager")
public class JisoNetDemandServiceImpl extends AbstractManagerImpl<String, JisoNetDemandModel> implements JisoNetDemandManager{

	@Resource 
	private JisoNetDemandDao dao;
	
	@Override
	protected Dao<String, JisoNetDemandModel> getDao() {
		
		return dao;
	}

	@Override
	public PageList<JisoNetDemandModel> queryJisoNetDemandPage(JisoNetDemandModel model, DefaultPage page) {
		
		return dao.queryJisoNetDemandPage(model,page);
	}

	@Override
	public List<JisoNetDemandModel> queryJisoNetDemandByKey(JisoNetDemandModel model) {
		
		return dao.queryJisoNetDemandByKey(model);
	}

}
