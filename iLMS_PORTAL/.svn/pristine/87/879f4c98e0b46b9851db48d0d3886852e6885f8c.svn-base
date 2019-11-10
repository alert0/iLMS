package com.hanthink.sw.dao;

import java.util.List;

import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hanthink.sw.model.SwZGJOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SwZGJOrderDaoimpl
 * @Description: 支给件订单查询
 * @author dtp
 * @date 2019年3月22日
 */
public interface SwZGJOrderDao extends Dao<String, SwZGJOrderModel>{

	/**
	 * @Description: 查询支给件订单   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	PageList<SwZGJOrderModel> queryZGJOrderPage(SwZGJOrderModel model, DefaultPage page);

	/**
	 * @Description: 支给件订单导出    
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	List<SwZGJOrderModel> queryZGJOrderList(SwZGJOrderModel model);

	/**
	 * @Description: 查询打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	List<PubPrintOrderModel> queryZGJOrderPrintDetailList(SwZGJOrderModel model_q);

	/**
	 * @Description: 查询标签打印信息  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	List<SwZGJOrderModel> querySwZGJOrderPrintLabelList(SwZGJOrderModel model);

	/**
	 * @Description:  选择导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	List<SwZGJOrderModel> querySelectZGJOrderList(SwZGJOrderModel model);

	/**
	 * @Description: 更新本次发货数量  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	void updateZGJDetailForPrint(SwZGJOrderModel model);

	/**
	 * @Description: 查询订单明细  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	PageList<SwZGJOrderModel> queryZGJOrderDetailPage(SwZGJOrderModel model, DefaultPage page);

	/**
	 * @Description: 更新订单打印信息    
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月24日
	 */
	void updatePrintInfo(SwZGJOrderModel model);

	/**
	 * @Description: 更新下载状态  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月26日
	 */
	void updateDownloadStatus(SwZGJOrderModel model);

	/**
	 * @Description: 查询从表发货状态  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月27日
	 */
	List<SwZGJOrderModel> queryZGJDetailList(SwZGJOrderModel model);

	/**
	 * @Description: 更新主表发货状态
	 * @param: @param model_q    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月27日
	 */
	void updateOrderDeliveryStatus(SwZGJOrderModel model_q);

	/**
	 * 
	 * @Description: 更新发货数
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年6月13日 下午12:13:53
	 */
	void updateAllOrderObj(SwDemondOrderDetailModel model);

	/**
	 * 
	 * @Description: 更新发货状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年6月13日 下午12:14:12
	 */
	void updateOrderDeliveryStatus(SwDemondOrderDetailModel model);

	/**
	 * 
	 * @Description: 修改主表发货数量
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年6月13日 下午12:31:32
	 */
	void updateZGJOrderForPrint(SwZGJOrderModel model);

	/**
	 * 
	 * @Description: 修改主表发货状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年6月13日 下午12:31:45
	 */
	void updateZGJOrderDeliveryStatus(SwZGJOrderModel model);
	
}
