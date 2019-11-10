package com.hanthink.sw.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwLogictiscOrderDao;
import com.hanthink.sw.model.SwLogictiscOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class SwLogictiscOrderDaoImpl extends MyBatisDaoImpl<SwLogictiscOrderModel, String>
												implements SwLogictiscOrderDao{

	@Override
	public String getNamespace() {
		return SwLogictiscOrderModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SwLogictiscOrderModel> queryLogistiscsOrderForPage(SwLogictiscOrderModel model, Page page)
			throws Exception {
		return (List<SwLogictiscOrderModel>) this.getList("queryLogistiscsOrderForPage", model, page);
	}

}
