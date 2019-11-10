package com.hanthink.mon.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonDeliveryOrderDao;
import com.hanthink.mon.manager.MonDeliveryOrderManager;
import com.hanthink.mon.model.MonDeliveryOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: DeliveryOrderManagerImpl
 * @Description: 取货单查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Service("deliveryOrderManager")
public class MonDeliveryOrderManagerImpl extends AbstractManagerImpl<String, MonDeliveryOrderModel>
		implements MonDeliveryOrderManager {

	@Resource
	private MonDeliveryOrderDao deliveryOrderDao;

	@Override
	protected Dao<String, MonDeliveryOrderModel> getDao() {
		return deliveryOrderDao;
	}

	/**
	 * @Description: 取货单查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<DeliveryOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@Override
	public PageList<MonDeliveryOrderModel> queryDeliveryOrderPage(MonDeliveryOrderModel model, DefaultPage page) {
		return deliveryOrderDao.queryDeliveryOrderPage(model, page);
	}

	/**
	 * @Description: 取货单明细查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<DeliveryOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@Override
	public PageList<MonDeliveryOrderModel> queryDeliveryOrderDetailPage(MonDeliveryOrderModel model, DefaultPage page) {
		return deliveryOrderDao.queryDeliveryOrderDetailPage(model, page);
	}

}
