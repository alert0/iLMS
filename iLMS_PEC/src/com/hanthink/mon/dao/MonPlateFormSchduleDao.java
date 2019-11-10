package com.hanthink.mon.dao;


import java.util.List;

import com.hanthink.mon.model.MonPlateFormModel;
import com.hanthink.mon.model.MonPlateFormSchduleModel;
import com.hanthink.mon.model.MonRouteModel;
import com.hotent.base.db.api.Dao;

public interface MonPlateFormSchduleDao extends Dao<String, MonPlateFormSchduleModel>{

	/**
	 * 查询当前时间段
	 * @return
	 */
	MonPlateFormSchduleModel queryCurrentTime();

	/**
	 * 根据车间和订单类型查询所有站台信息
	 * @param model
	 * @return
	 */
	List<MonPlateFormModel> queryDownPlateFormData(MonPlateFormSchduleModel model);

	/**
	 * 查询所有装空箱站台信息站台信息
	 * @param returnModel
	 * @return
	 */
	List<MonPlateFormModel> queryUpPlateFormData(
			MonPlateFormSchduleModel returnModel);

	/**
	 * 查询所有SW路线信息
	 * @param returnModel
	 * @return
	 */
	List<MonRouteModel> querySWMonRouteData(MonPlateFormSchduleModel returnModel);

	/**
	 * 查询作业时间
	 * @param returnModel
	 * @return
	 */
	MonPlateFormSchduleModel queryWorkTime(MonPlateFormSchduleModel returnModel);

	/**
	 * 查询所有JIT路线信息
	 * @param returnModel
	 * @return
	 */
	List<MonRouteModel> queryJITMonRouteData(
			MonPlateFormSchduleModel returnModel);
	
}
