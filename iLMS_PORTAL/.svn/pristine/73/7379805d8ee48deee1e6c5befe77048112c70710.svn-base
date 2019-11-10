package com.hanthink.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwLongOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SwLongOrderDao extends Dao<String,SwLongOrderModel>{

	/**
	 * 
	 * @Description: 长周期订单分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<Map<String,Object>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月22日 下午12:23:17
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
	 * @date 2019年2月25日 上午9:45:39
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
	 * @date 2019年2月25日 下午2:00:03
	 */
	List<SwLongOrderModel> downloadLongOrderByChoose(List<Map<String, String>> paramList);

	List<SwLongOrderModel> queryLongOrderDetailList(SwLongOrderModel model_q);

	/**
	 * 
	 * @Description: 更新发货数
	 * @param @param m   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月27日 下午5:51:08
	 */
	void updateDelivery(SwLongOrderModel m);

	/**
	 * 
	 * @Description: 查询标签打印数据
	 * @param @param model
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月4日 下午3:14:09
	 */
	List<SwLongOrderModel> queryLongOrderPrintLabelList(SwLongOrderModel model);

	/**
	 * 
	 * @Description: 查询托盘打印数据
	 * @param @param model_q
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月4日 下午5:19:30
	 */
	List<SwLongOrderModel> longOrderPrintTpLabel(SwLongOrderModel model_q);

	/**
	 * 
	 * @Description: 获取长周期版本号
	 * @param @return   
	 * @return List<SwLongOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月12日 下午10:54:39
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
	 * @date 2019年3月23日 上午11:50:54
	 */
	void updateFeedbackLongOrder(SwLongOrderModel model);

	/**
	 * 
	 * @Description: 删除长周期订单明细
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月10日 上午11:50:06
	 */
	void deleteLongOrder(SwLongOrderModel model);

	/**
	 * 
	 * @Description: 删除长周期主表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月11日 下午5:20:00
	 */
	void deleteLong(SwLongOrderModel model);

	/**
	 * 
	 * @Description: 修改收货数量
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月21日 下午6:15:07
	 */
	void updateLongReceiveQty(SwLongOrderModel model);

	/**
	 * 
	 * @param map 
	 * @Description: 获取长周期预测需求中默认最新版本
	 * @param @return   
	 * @return SwLongOrderModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月23日 下午4:24:40
	 */
	SwLongOrderModel getDefaultVersion(Map<String, String> map);

}
