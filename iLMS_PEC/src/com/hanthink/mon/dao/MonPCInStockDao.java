package com.hanthink.mon.dao;

import java.util.List;

import com.hanthink.mon.model.MonPCInStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

public interface MonPCInStockDao extends Dao<String, MonPCInStockModel>{

	List<MonPCInStockModel> queryPCInStock(MonPCInStockModel model, Page page)throws Exception;

	List<MonPCInStockModel> queryForLineCharts(MonPCInStockModel model)throws Exception;

}
