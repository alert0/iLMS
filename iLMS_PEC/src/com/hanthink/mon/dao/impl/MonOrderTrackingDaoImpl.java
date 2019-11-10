package com.hanthink.mon.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.dao.MonOrderTrackingDao;
import com.hanthink.mon.model.MonOrderTrackingModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class MonOrderTrackingDaoImpl extends MyBatisDaoImpl<String, MonOrderTrackingModel>
				implements MonOrderTrackingDao{

	@Override
	public String getNamespace() {
		return MonOrderTrackingModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonOrderTrackingModel> queryOrderTrackingForPage(MonOrderTrackingModel model, Page page)
			throws Exception {
		return this.getListByKey("queryOrderTrackingForPage", model, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> queryForOrderType(String factoryCode) throws Exception {
		return (List<DictVO>) this.getList("queryForOrderType", factoryCode);
	}

	@Override
	public List<MonOrderTrackingModel> exportForExcel(MonOrderTrackingModel model) throws Exception {
		return this.getByKey("queryOrderTrackingForPage", model);
	}

}
