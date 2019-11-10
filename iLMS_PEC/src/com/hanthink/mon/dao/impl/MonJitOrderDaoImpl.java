package com.hanthink.mon.dao.impl;

import org.springframework.stereotype.Repository;
import com.hanthink.mon.dao.MonJitOrderDao;
import com.hanthink.mon.model.MonJitOrderModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitOrderDaoImpl
 * @Description: 取货单查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Repository
public class MonJitOrderDaoImpl extends MyBatisDaoImpl<String, MonJitOrderModel> implements MonJitOrderDao {

	@Override
	public String getNamespace() {
		return MonJitOrderModel.class.getName();
	}

	/**
	 * @Description: 拉动单查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<JitOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonJitOrderModel> queryJitOrderPage(MonJitOrderModel model, DefaultPage page) {
		return (PageList<MonJitOrderModel>) this.getList("queryJitOrderPage", model, page);
	}

	/**
	 * @Description: 拉动单明细查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<JitOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonJitOrderModel> queryJitOrderDetailPage(MonJitOrderModel model, DefaultPage page) {
		return (PageList<MonJitOrderModel>) this.getList("queryJitOrderDetailPage", model, page);
	}

}
