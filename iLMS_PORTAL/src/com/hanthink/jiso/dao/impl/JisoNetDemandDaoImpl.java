package com.hanthink.jiso.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jiso.dao.JisoNetDemandDao;
import com.hanthink.jiso.model.JisoNetDemandModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class JisoNetDemandDaoImpl extends MyBatisDaoImpl<String, JisoNetDemandModel> implements JisoNetDemandDao{

	@Override
	public String getNamespace() {
		
		return JisoNetDemandModel.class.getName();
	}

	@Override
	public PageList<JisoNetDemandModel> queryJisoNetDemandPage(JisoNetDemandModel model, DefaultPage page) {
		
		return (PageList<JisoNetDemandModel>) this.getByKey("queryJisoNetDemandPage", model, page);
	}

	@Override
	public List<JisoNetDemandModel> queryJisoNetDemandByKey(JisoNetDemandModel model) {
		
		return this.getByKey("queryJisoNetDemandPage", model);
	}

}
