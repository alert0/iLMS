package com.hanthink.sw.manager;

import java.util.List;

import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwDeliveryModel;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SwDeliveryManager extends Manager<String, SwDeliveryModel>{

	/**
	 * 
	 * <p>Title: queryJisoDeliveryPage</p>  
	 * <p>Description: 发货数据管理界面，分页查询功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:08:46
	 */
	PageList<SwDeliveryModel> queryJisoDeliveryPage(SwDeliveryModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: queryJisoDeliveryDetailPage</p>  
	 * <p>Description: 发货数据管理界面，明细查看功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:13:10
	 */
	PageList<SwDeliveryModel> queryJisoDeliveryDetailPage(SwDeliveryModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 查询需要打印的信息
	 * @param @param model_q
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月26日 下午3:07:48
	 */
	List<SwDeliveryModel> queryDeliveryOrderDetailList(SwDeliveryModel model_q);

	/**
	 * 
	 * @Description: 查询打印托盘需要的信息
	 * @param @param model_q
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月26日 下午5:05:55
	 */
	List<SwDeliveryModel> deliveryOrderPrintTpLabel(SwDeliveryModel model_q);

	/**
	 * 
	 * @Description: 查询标签打印需要的信息
	 * @param @param list_q
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月27日 上午9:57:36
	 */
	List<SwDeliveryModel> querydeliveryOrderPrintLabelList(List<SwDeliveryModel> list_q);



	/**
	 * 
	 * @Description: 查询标签打印需要的信息
	 * @param @param orderModel
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月29日 下午5:47:40
	 */
	List<SwDeliveryModel> queryDeliveryOrderPrintLabelList(SwDeliveryModel orderModel);

}