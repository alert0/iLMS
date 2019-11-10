package com.hanthink.mon.dao;

import com.hanthink.mon.model.MonJitOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitOrderDao
 * @Description: 拉动单查询
 * @author Midnight
 * @date 2018年11月03日
 */
public interface MonJitOrderDao extends Dao<String, MonJitOrderModel> {

	/**
	 * @Description: 拉动单查询
	 * @param: model
	 * @param: page
	 * @return: PageList<JitOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonJitOrderModel> queryJitOrderPage(MonJitOrderModel model, DefaultPage page);

	/**
	 * @Description: 拉动单明细查询
	 * @param: model
	 * @param: page
	 * @return: PageList<JitOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonJitOrderModel> queryJitOrderDetailPage(MonJitOrderModel model, DefaultPage page);

}
