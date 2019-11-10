package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SwDemondOrderManager  extends Manager<String, SwDemondOrderDetailModel>{

	/**
	 * 订单分页查询
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	PageList<SwDemondOrderDetailModel> queryDemandOrderPage(SwDemondOrderDetailModel model, DefaultPage p);

	/**
	 * 订单明细查询
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	PageList<SwDemondOrderDetailModel> queryDemandDetailOrderPage(SwDemondOrderDetailModel model, DefaultPage p);

	/**
	 * 订单批量下载
	 * @param model
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> downloadDemondOrder(SwDemondOrderDetailModel model);

	/**
	 * 订单选择下载
	 * @param swDemondOrderDetailModel3
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> downloadDemondOrderByChoose(SwDemondOrderDetailModel swDemondOrderDetailModel3);

	/**
	 * 明细更新
	 * @param map
	 * @param p
	 * @return
	 * Lxh
	 */
	PageList<SwDemondOrderDetailModel> demondOrderDetailUpdateQuery(Map<String, Object> map, DefaultPage p);

	/**
	 * 打印查询
	 * @param orderModel
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> queryDemondOrderPrintLabelList(SwDemondOrderDetailModel orderModel);

	/**
	 * 打印状态更新
	 * @param swDemondOrderDetailModel
	 * Lxh
	 */
	void updatePrintLabelStatus(SwDemondOrderDetailModel swDemondOrderDetailModel);

	/**
	 * 订单明细查询
	 * @param model_q
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> queryDemondOrderDetailList(SwDemondOrderDetailModel model_q);

	/**
	 * 打印更新
	 * @param list_printInfo
	 * Lxh
	 */
	void updatePrintInfo(List<SwDemondOrderDetailModel> list_printInfo);

	/**
	 * 下载更新
	 * @param map
	 * Lxh
	 */
	void updateDowloadInfo(Map<String, Object> map);

	/**
	 * 批量下载更新
	 * @param model
	 * Lxh
	 */
	void updateDowloadInfoByBatch(SwDemondOrderDetailModel model);

	/**
	 * 托盘打印
	 * @param model_q
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> demondOrderPrintTpLabel(SwDemondOrderDetailModel model_q);

	/**
	 *  发货数更新
	 * @param model
	 * Lxh
	 */
	void updateDemandDetailForPrint(SwDemondOrderDetailModel model);

	/**
	 * 获取发货单号
	 * @param m
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> getDeliveryNo(SwDemondOrderDetailModel m);

	/**
	 * 
	 * @Description: 查询导出的数据
	 * @param @param model
	 * @param @return   
	 * @return PageList<SwDemondOrderDetailModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月15日 下午7:06:27
	 */
	List<SwDemondOrderDetailModel> queryDemandOrderDownload(SwDemondOrderDetailModel model);

	/**
	 * 
	 * @Description: 根据选择数据的订单号查询出数据
	 * @param @param splitPurchaseNo
	 * @param @return   
	 * @return List<SwDemondOrderDetailModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月15日 下午7:47:19
	 */
	List<SwDemondOrderDetailModel> downloadDemondOrderByChoose(String[] splitPurchaseNo);

	/**
	 * 
	 * @Description: 判断是否该订单是否已经写入过发货数据表中
	 * @param @param m
	 * @param @return   
	 * @return Boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月6日 上午12:35:33
	 */
	Boolean isExistsByDetail(SwDemondOrderDetailModel m);

	/**
	 * 套打功能
	 * <p>return: List<PubPrintOrderModel></p>  
	 * <p>Description: SwDemondOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月22日
	 * @version 1.0
	 */
	List<PubPrintOrderModel> queryOrderDetailList(SwDemondOrderDetailModel model_q);

	/**
	 * 更新发货状态
	 * <p>return: void</p>  
	 * <p>Description: SwDemondOrderManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月27日
	 * @version 1.0
	 * @return 
	 * @throws Exception 
	 */
	void updateDeliveryStatus(String[] splitOrderNo) throws Exception;

	/**
	 * 查询要打印的标签数据
	 * @param orderModel
	 * @return
	 */
	List<SwDemondOrderDetailModel> queryLabelPrintList(
			SwDemondOrderDetailModel orderModel);

	/**
	 * 
	 * @Description: 全部发货
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月27日 下午5:15:10
	 */
	void updateAllOrderObj(SwDemondOrderDetailModel model, String ipAddr);

	/**
	 * insert by luoxianqin
	 * @Description: 记录二维码内容
	 * @param @param swDemondOrderDetailModel_q
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月11日 下午3:53:47
	 */
	void logBarCode(SwDemondOrderDetailModel swDemondOrderDetailModel_q, String ipAddr);
	
}
