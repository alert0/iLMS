package com.hanthink.mon.manager;

import com.hanthink.mon.model.MonPCInStockModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface MonPCInStockManager extends Manager<String, MonPCInStockModel>{

	PageList<MonPCInStockModel> queryPCInStock(MonPCInStockModel model, Page page)throws Exception;

	PageList<MonPCInStockModel> queryForLineCharts(MonPCInStockModel model)throws Exception;

}
