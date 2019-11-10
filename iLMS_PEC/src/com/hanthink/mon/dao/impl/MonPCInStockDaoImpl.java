package com.hanthink.mon.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonPCInStockDao;
import com.hanthink.mon.model.MonPCInStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class MonPCInStockDaoImpl extends MyBatisDaoImpl<String, MonPCInStockModel>
					implements MonPCInStockDao{

	@Override
	public String getNamespace() {
		return MonPCInStockModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonPCInStockModel> queryPCInStock(MonPCInStockModel model, Page page) throws Exception {
		return (List<MonPCInStockModel>) this.getList("queryPCInStock", model, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonPCInStockModel> queryForLineCharts(MonPCInStockModel model) throws Exception {
		return this.getListByKey("queryForLineCharts", model);
	}

}
