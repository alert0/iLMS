package com.hanthink.mon.dao;

import com.hanthink.mon.model.MonVehPassModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: VehPassDao
 * @Description: 实时工程查询
 * @author Midnight
 * @date 2018年11月03日
 */
public interface MonVehPassDao extends Dao<String, MonVehPassModel> {

	/**
	 * @Description: 实时工程查询
	 * @param: model
	 * @param: page
	 * @return: PageList<VehPassModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonVehPassModel> queryVehPassPage(MonVehPassModel model, DefaultPage page);



}
