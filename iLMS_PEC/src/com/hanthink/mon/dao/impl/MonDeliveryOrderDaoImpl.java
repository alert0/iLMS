package com.hanthink.mon.dao.impl;

import org.springframework.stereotype.Repository;
import com.hanthink.mon.dao.MonDeliveryOrderDao;
import com.hanthink.mon.model.MonDeliveryOrderModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: DeliveryOrderDaoImpl
 * @Description: 取货单查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Repository
public class MonDeliveryOrderDaoImpl extends MyBatisDaoImpl<String, MonDeliveryOrderModel> implements MonDeliveryOrderDao {

	@Override
	public String getNamespace() {
		return MonDeliveryOrderModel.class.getName();
	}

	/**
	 * @Description: 拉动订单查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<DeliveryOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonDeliveryOrderModel> queryDeliveryOrderPage(MonDeliveryOrderModel model, DefaultPage page) {
		return (PageList<MonDeliveryOrderModel>) this.getList("queryDeliveryOrderPage", model, page);
	}

	/**
	 * @Description: 拉动订单明细查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<DeliveryOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonDeliveryOrderModel> queryDeliveryOrderDetailPage(MonDeliveryOrderModel model, DefaultPage page) {
		return (PageList<MonDeliveryOrderModel>) this.getList("queryDeliveryOrderDetailPage", model, page);
	}

}
