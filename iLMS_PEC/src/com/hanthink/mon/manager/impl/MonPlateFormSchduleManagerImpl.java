package com.hanthink.mon.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonPlateFormSchduleDao;
import com.hanthink.mon.manager.MonPlateFormSchduleManager;
import com.hanthink.mon.model.MonPlateFormModel;
import com.hanthink.mon.model.MonPlateFormSchduleModel;
import com.hanthink.mon.model.MonRouteModel;
import com.hotent.base.db.api.Dao;

@Service("MonPlateFormSchduleManager")
public class MonPlateFormSchduleManagerImpl extends
		AbstractManagerImpl<String, MonPlateFormSchduleModel> implements
		MonPlateFormSchduleManager {

	@Resource
	private MonPlateFormSchduleDao dao;
	
	@Override
	protected Dao<String, MonPlateFormSchduleModel> getDao() {
		return dao;
	}

	@Override
	public MonPlateFormSchduleModel queryCurrentTime() {
		return dao.queryCurrentTime();
	}

	@Override
	public List<MonPlateFormModel> queryDownPlateFormData(MonPlateFormSchduleModel model) {
		return dao.queryDownPlateFormData(model);
	}

	@Override
	public List<MonPlateFormModel> queryUpPlateFormData(
			MonPlateFormSchduleModel returnModel) {
		return dao.queryUpPlateFormData(returnModel);
	}

	@Override
	public List<MonRouteModel> querySWMonRouteData(
			MonPlateFormSchduleModel returnModel) {
		return dao.querySWMonRouteData(returnModel);
	}

	@Override
	public MonPlateFormSchduleModel queryWorkTime(MonPlateFormSchduleModel returnModel) {
		//查询作业时间
		return dao.queryWorkTime(returnModel);
	}

	@Override
	public List<MonRouteModel> queryJITMonRouteData(
			MonPlateFormSchduleModel returnModel) {
		return dao.queryJITMonRouteData(returnModel);
	}

}
