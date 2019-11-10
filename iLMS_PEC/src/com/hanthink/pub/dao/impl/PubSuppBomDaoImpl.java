package com.hanthink.pub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubSuppBomDao;
import com.hanthink.pub.model.PubSuppBomModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class PubSuppBomDaoImpl extends MyBatisDaoImpl<String, PubSuppBomModel> implements PubSuppBomDao{

	@Override
	public String getNamespace() {
		
		return PubSuppBomModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<PubSuppBomModel> queryPubSuppBomForPage(PubSuppBomModel model, DefaultPage p) {
		
		return (PageList<PubSuppBomModel>) this.getList("queryPubSuppBomForPage", model, p);
	}

	@Override
	public List<PubSuppBomModel> downloadPubSuppBomModel(PubSuppBomModel model) {
		
		return this.getByKey("queryPubSuppBomForPage", model);
	}

}
