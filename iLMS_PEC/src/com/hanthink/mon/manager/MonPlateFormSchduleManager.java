package com.hanthink.mon.manager;


import java.util.List;

import com.hanthink.mon.model.MonPlateFormModel;
import com.hanthink.mon.model.MonPlateFormSchduleModel;
import com.hanthink.mon.model.MonRouteModel;
import com.hotent.base.manager.api.Manager;

public interface MonPlateFormSchduleManager extends Manager<String, MonPlateFormSchduleModel>{

	/**
	 * 查询当前时间列表
	 * @param time
	 * @return
	 */
	MonPlateFormSchduleModel queryCurrentTime();

	/**
	 * 查询所有卸货站台信息站台信息
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
	 * 查询所有取货路线信息
	 * @param returnModel
	 * @return
	 */
	List<MonRouteModel> querySWMonRouteData(MonPlateFormSchduleModel returnModel);

	/**
	 * 查询作业时间
	 * @return
	 */
	MonPlateFormSchduleModel queryWorkTime(MonPlateFormSchduleModel returnModel);

	/**
	 * 查询所有拉动路线信息
	 * @param returnModel
	 * @return
	 */
	List<MonRouteModel> queryJITMonRouteData(
			MonPlateFormSchduleModel returnModel);

	
}
