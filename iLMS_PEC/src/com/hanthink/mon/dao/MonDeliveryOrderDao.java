package com.hanthink.mon.dao;

import com.hanthink.mon.model.MonDeliveryOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface MonDeliveryOrderDao extends Dao<String, MonDeliveryOrderModel>{
	/**
	 * @Description: 取货单查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<DeliveryOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonDeliveryOrderModel> queryDeliveryOrderPage(MonDeliveryOrderModel model, DefaultPage page);
	
	/**
	 * @Description: 取货单明细查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<DeliveryOrderModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonDeliveryOrderModel> queryDeliveryOrderDetailPage(MonDeliveryOrderModel model, DefaultPage page);
}
