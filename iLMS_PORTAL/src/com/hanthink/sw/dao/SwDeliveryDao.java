package com.hanthink.sw.dao;

import java.util.List;

import com.hanthink.sw.model.SwDeliveryModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SwDeliveryDao extends Dao<String, SwDeliveryModel>{

	/**
	 * 
	 * <p>Title: queryJisoDeliveryPage</p>  
	 * <p>Description: 发布数据管理界面，分页查询功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:09:49
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
	 * @time 2018年10月18日 上午10:14:02
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
	 * @date 2018年10月26日 下午3:08:45
	 */
	List<SwDeliveryModel> queryDeliveryOrderDetailList(SwDeliveryModel model_q);

	/**
	 * 
	 * @Description: 查询托盘打印需要的信息
	 * @param @param model_q
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月26日 下午5:06:50
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
	 * @date 2018年10月27日 上午9:58:26
	 */
	List<SwDeliveryModel> querydeliveryOrderPrintLabelList(List<SwDeliveryModel> list_q);

	/**
	 * 
	 * @Description: 查询标签打印需要的信息
	 * @param @param model
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月20日 下午11:43:53
	 */
	List<SwDeliveryModel> querydeliveryOrderPrintLabelList(SwDeliveryModel model);

	/**
	 * 
	 * @Description: 查询标签打印需要的信息
	 * @param @param orderModel
	 * @param @return   
	 * @return List<SwDemondOrderDetailModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月29日 下午5:40:34
	 */
	List<SwDeliveryModel> queryDeliveryOrderPrintLabelList(SwDeliveryModel orderModel);

}
