package com.hanthink.sw.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.dao.SwZGJOrderDao;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hanthink.sw.model.SwZGJOrderModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SwZGJOrderDaoimpl
 * @Description: 支给件订单查询
 * @author dtp
 * @date 2019年3月22日
 */
@Repository
public class SwZGJOrderDaoImpl extends MyBatisDaoImpl<String, SwZGJOrderModel> implements SwZGJOrderDao{

	@Override
	public String getNamespace() {
		return SwZGJOrderModel.class.getName();
	}

	/**
	 * @Description: 查询支给件订单   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwZGJOrderModel> queryZGJOrderPage(SwZGJOrderModel model, DefaultPage page) {
		return (PageList<SwZGJOrderModel>) this.getList("queryZGJOrderPage", model, page);
	}

	/**
	 * @Description: 支给件订单导出    
	 * @param: @param model
	 * @param: @return    
	 * @return: PageList<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwZGJOrderModel> queryZGJOrderList(SwZGJOrderModel model) {
		return (List<SwZGJOrderModel>) this.getList("queryZGJOrderPage", model);
	}

	/**
	 * @Description: 查询打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubPrintOrderModel> queryZGJOrderPrintDetailList(SwZGJOrderModel model_q) {
		return (List<PubPrintOrderModel>) this.getList("queryZGJOrderPrintDetailList", model_q);
	}

	/**
	 * @Description: 查询标签打印信息  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwZGJOrderModel> querySwZGJOrderPrintLabelList(SwZGJOrderModel model) {
		return this.getListByKey("querySwZGJOrderPrintLabelList", model);
	}

	/**
	 * @Description:  选择导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwZGJOrderModel> querySelectZGJOrderList(SwZGJOrderModel model) {
		return (List<SwZGJOrderModel>) this.getList("querySelectZGJOrderList", model);
	}

	/**
	 * @Description: 更新本次发货数量  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	@Override
	public void updateZGJDetailForPrint(SwZGJOrderModel model) {
		this.updateByKey("updateZGJDetailForPrint", model);
	}

	/**
	 * @Description: 查询订单明细  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwZGJOrderModel> queryZGJOrderDetailPage(SwZGJOrderModel model, DefaultPage page) {
		return (PageList<SwZGJOrderModel>) this.getList("queryZGJOrderDetailPage", model, page);
	}

	/**
	 * @Description: 更新订单打印信息    
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月24日
	 */
	@Override
	public void updatePrintInfo(SwZGJOrderModel model) {
		this.updateByKey("updatePrintInfo", model);
	}

	/**
	 * @Description: 更新下载状态  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月26日
	 */
	@Override
	public void updateDownloadStatus(SwZGJOrderModel model) {
		this.updateByKey("updateDownloadStatus", model);
	}

	/**
	 * @Description: 查询从表发货状态  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月27日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwZGJOrderModel> queryZGJDetailList(SwZGJOrderModel model) {
		return (List<SwZGJOrderModel>) this.getList("queryZGJOrderDetailPage", model);
	}

	/**
	 * @Description: 更新主表发货状态
	 * @param: @param model_q    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月27日
	 */
	@Override
	public void updateOrderDeliveryStatus(SwZGJOrderModel model_q) {
		this.updateByKey("updateOrderDeliveryStatus", model_q);
	}

	@Override
	public void updateAllOrderObj(SwDemondOrderDetailModel model) {
		this.updateByKey("updateAllOrderObj", model);
	}

	@Override
	public void updateOrderDeliveryStatus(SwDemondOrderDetailModel model) {
		this.updateByKey("updateOrderDeliveryStatus", model);
	}

	@Override
	public void updateZGJOrderForPrint(SwZGJOrderModel model) {
		this.updateByKey("updateZGJOrderForPrint", model);
	}

	@Override
	public void updateZGJOrderDeliveryStatus(SwZGJOrderModel model) {
		this.updateByKey("updateZGJOrderDeliveryStatus", model);
	}
	
	

}
