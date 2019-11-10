package com.hanthink.mon.manager;

import com.hanthink.mon.model.MonVehPassModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: VehPassManager
 * @Description: 实时工程查询
 * @author Midnight
 * @date 2018年11月03日
 */
public interface MonVehPassManager extends Manager<String, MonVehPassModel> {

	/**
	 * @Description: 实时工程查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return
	 * @return: PageList<VehPassModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonVehPassModel> queryVehPassPage(MonVehPassModel model, DefaultPage page);


}
