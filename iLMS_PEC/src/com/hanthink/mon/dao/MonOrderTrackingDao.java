package com.hanthink.mon.dao;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.model.MonOrderTrackingModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

public interface MonOrderTrackingDao extends Dao<String, MonOrderTrackingModel>{

	List<MonOrderTrackingModel> queryOrderTrackingForPage(MonOrderTrackingModel model, Page page)throws Exception;

	List<DictVO> queryForOrderType(String factoryCode)throws Exception;

	List<MonOrderTrackingModel> exportForExcel(MonOrderTrackingModel model)throws Exception;

}
