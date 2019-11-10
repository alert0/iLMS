package com.hanthink.mon.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonJitOrderDao;
import com.hanthink.mon.manager.MonJitOrderManager;
import com.hanthink.mon.model.MonJitOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: DeliveryOrderManagerImpl
 * @Description: 取货单查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Service("monJitOrderManager")
public class MonJitOrderManagerImpl extends AbstractManagerImpl<String, MonJitOrderModel>
		implements MonJitOrderManager {

	@Resource
	private MonJitOrderDao monJitOrderDao;

	@Override
	protected Dao<String, MonJitOrderModel> getDao() {
		return monJitOrderDao;
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
	@Override
	public PageList<MonJitOrderModel> queryJitOrderPage(MonJitOrderModel model, DefaultPage page) {
		return monJitOrderDao.queryJitOrderPage(model, page);
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
	@Override
	public PageList<MonJitOrderModel> queryJitOrderDetailPage(MonJitOrderModel model, DefaultPage page) {
		return monJitOrderDao.queryJitOrderDetailPage(model, page);
	}

}
