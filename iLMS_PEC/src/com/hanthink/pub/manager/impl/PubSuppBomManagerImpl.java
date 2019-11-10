package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubSuppBomDao;
import com.hanthink.pub.manager.PubSuppBomManager;
import com.hanthink.pub.model.PubSuppBomModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Service("PubSuppBomManager")
public class PubSuppBomManagerImpl extends AbstractManagerImpl<String, PubSuppBomModel> 
implements PubSuppBomManager{

	@Resource
	PubSuppBomDao dao;

	@Override
	protected Dao<String, PubSuppBomModel> getDao() {
		
		return dao;
	}

	@Override
	public PageList<PubSuppBomModel> queryPubSuppBomForPage(PubSuppBomModel model, DefaultPage p) {
		
		return dao.queryPubSuppBomForPage(model,p);
	}

	@Override
	public List<PubSuppBomModel> downloadPubSuppBomModel(PubSuppBomModel model) {
		
		return dao.downloadPubSuppBomModel(model);
	}
}
