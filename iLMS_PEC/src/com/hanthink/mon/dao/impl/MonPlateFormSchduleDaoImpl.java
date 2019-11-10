package com.hanthink.mon.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonPlateFormSchduleDao;
import com.hanthink.mon.model.MonPlateFormModel;
import com.hanthink.mon.model.MonPlateFormSchduleModel;
import com.hanthink.mon.model.MonRouteModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class MonPlateFormSchduleDaoImpl extends
		MyBatisDaoImpl<String, MonPlateFormSchduleModel> implements
		MonPlateFormSchduleDao {

	@Override
	public String getNamespace() {

		return MonPlateFormSchduleModel.class.getName();
	}

	@Override
	public MonPlateFormSchduleModel queryCurrentTime() {
		return (MonPlateFormSchduleModel) this.getOne("queryCurrentTime", null);
	}

	@Override
	public List<MonPlateFormModel> queryDownPlateFormData(
			MonPlateFormSchduleModel model) {
		return this.sqlSessionTemplate.selectList(getNamespace()
				+ ".queryDownPlateFormData", model);
	}

	@Override
	public List<MonPlateFormModel> queryUpPlateFormData(
			MonPlateFormSchduleModel returnModel) {
		return this.sqlSessionTemplate.selectList(getNamespace()
				+ ".queryUpPlateFormData", returnModel);
	}

	@Override
	public List<MonRouteModel> querySWMonRouteData(
			MonPlateFormSchduleModel returnModel) {
		return this.sqlSessionTemplate.selectList(getNamespace()
				+ ".querySWMonRouteData", returnModel);
	}

	@Override
	public MonPlateFormSchduleModel queryWorkTime(
			MonPlateFormSchduleModel returnModel) {
		return this.getUnique("queryWorkTime", returnModel);
	}

	@Override
	public List<MonRouteModel> queryJITMonRouteData(
			MonPlateFormSchduleModel returnModel) {
		return this.sqlSessionTemplate.selectList(getNamespace()
				+ ".queryJITMonRouteData", returnModel);
	}

}
