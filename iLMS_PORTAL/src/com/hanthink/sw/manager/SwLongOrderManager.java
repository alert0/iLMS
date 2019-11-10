package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hanthink.sw.model.SwLongOrderModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SwLongOrderManager extends Manager<String, SwLongOrderModel>{

	/**
	 * 
	 * @Description: 长周期订单分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<SwDemondOrderDetailModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月22日 下午12:14:47
	 */
	PageList<SwLongOrderModel> queryLongOrderPage(SwLongOrderModel model, DefaultPage p);
	
	/**
	 * 
	 * @Description: 批量导出
	 * @param @param model
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月25日 上午9:44:53
	 */
	List<SwLongOrderModel> queryLongOrderDownload(SwLongOrderModel model);

	/**
	 * 
	 * @Description: 选择导出
	 * @param @param paramList
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月25日 下午2:00:24
	 */
	List<SwLongOrderModel> downloadLongOrderByChoose(List<Map<String, String>> paramList);

	/**
	 * 
	 * @Description: 查询订单打印数据
	 * @param @param model_q
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月25日 下午7:30:50
	 */
	List<SwLongOrderModel> queryLongOrderDetailList(SwLongOrderModel model_q);

	/**
	 * 
	 * @Description: 更新发货数
	 * @param @param m
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月27日 下午5:25:23
	 */
	void updateDelivery(SwLongOrderModel m, String ipAddr);

	/**
	 * 
	 * @Description: 查询标签打印数据
	 * @param @param model
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月4日 下午3:13:21
	 */
	List<SwLongOrderModel> queryLongOrderPrintLabelList(SwLongOrderModel model);

	/**
	 * 
	 * @Description: 查询托盘打印
	 * @param @param model_q
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月4日 下午5:16:34
	 */
	List<SwLongOrderModel> longOrderPrintTpLabel(SwLongOrderModel model_q);
	
	/**
	 * 
	 * @Description: 获取长周期订单号
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月12日 下午10:53:08
	 */
	List<SwLongOrderModel> getVersion();

	/**
	 * 
	 * @Description: 反馈
	 * @param @param ids
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月23日 上午11:50:09
	 */
	void updateFeedbackLongOrder(List<SwLongOrderModel> list, SwLongOrderModel model);

	/**
	 * 
	 * @param ipAddr 
	 * @Description: 删除长周期订单
	 * @param @param list   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月10日 上午11:44:09
	 */
	void deleteLongOrder(SwLongOrderModel model, String ipAddr);

	/**
	 * 
	 * @Description: 修改收货数量
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月21日 下午6:13:39
	 */
	void updateLongReceiveQty(SwLongOrderModel model, String ipAddr);

	/**
	 * 
	 * @param map 
	 * @Description: 长周期预测需求界面获取默认最新版本
	 * @param @return   
	 * @return SwLongOrderModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月23日 下午4:23:40
	 */
	SwLongOrderModel getDefaultVersion(Map<String, String> map);

}
