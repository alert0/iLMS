package com.hanthink.mon.manager;

import com.hanthink.mon.model.MonJitOrderModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: MonJitOrderManager
 * @Description: 拉动单查询
 * @author Midnight
 * @date 2018年11月03日
 */
public interface MonJitOrderManager extends Manager<String, MonJitOrderModel> {

	/**
	 * @Description: 拉动单查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return
	 * @return: PageList<JitOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonJitOrderModel> queryJitOrderPage(MonJitOrderModel model, DefaultPage page);

	/**
	 * @Description: 拉动单明细查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return
	 * @return: PageList<JitOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonJitOrderModel> queryJitOrderDetailPage(MonJitOrderModel model, DefaultPage page);
}
