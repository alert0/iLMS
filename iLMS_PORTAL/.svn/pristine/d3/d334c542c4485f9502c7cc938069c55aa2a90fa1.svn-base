package com.hanthink.sw.manager;

import java.util.List;

import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hanthink.sw.model.SwZGJOrderModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: SwZGJOrderManager
 * @Description: 
 * @author dtp
 * @date 2019年3月22日
 */
public interface SwZGJOrderManager extends Manager<String, SwZGJOrderModel>{

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
	 * @Description: 更新打印信息  
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月24日
	 */
	void updatePrintInfo(List<SwZGJOrderModel> list_printInfo);

	/**
	 * @Description: 更新下载状态 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月26日
	 */
	void updateDownloadStatus(SwZGJOrderModel model);

	/**
	 * 
	 * @Description: 全部发货
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年6月13日 上午11:58:21
	 */
	void updateAllOrderObj(SwDemondOrderDetailModel model, String ipAddr);

	

}
