package com.hanthink.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SwDemondOrderDao  extends Dao<String, SwDemondOrderDetailModel>{

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
	 * 订单下载
	 * @param model
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> downloadDemondOrder(SwDemondOrderDetailModel model);

	/**
	 * 订单选择下载
	 * @param model
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> downloadDemondOrderByChoose(SwDemondOrderDetailModel model);

	/**
	 * 更新收货明细
	 * @param map
	 * @param p
	 * @return
	 * Lxh
	 */
	PageList<SwDemondOrderDetailModel> demondOrderDetailUpdateQuery(Map<String, Object> map, DefaultPage p);

	/**
	 * 标签打印查询
	 * @param model
	 * @return
	 * Lxh
	 */
	List<SwDemondOrderDetailModel> queryDemondOrderPrintLabelList(SwDemondOrderDetailModel model);

	/**
	 * 打印状态更新
	 * @param swDemondOrderDetailModel
	 * Lxh
	 */
	void updatePrintLabelStatus(SwDemondOrderDetailModel swDemondOrderDetailModel);

	/**
	 * 明细查询
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
	 * 插入发货订单
	 * @param m
	 * Lxh
	 */
	void insertDelivery(SwDemondOrderDetailModel m);

	/**
	 * 插入发货订单明细
	 * @param m
	 * Lxh
	 */
	void insertDeliveryDetail(SwDemondOrderDetailModel m);

	/**
	 * 
	 * @param purchaseNo 
	 * @Description: 获取发货单号最后五位，且为最大值
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月15日 下午6:08:06
	 */
	Integer getLastFive(String purchaseNo);

	/**
	 * 
	 * @Description: 查询需要导出的数据
	 * @param @param model
	 * @param @return   
	 * @return PageList<SwDemondOrderDetailModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月15日 下午7:07:15
	 */
	List<SwDemondOrderDetailModel> queryDemandOrderDownload(SwDemondOrderDetailModel model);

	/**
	 * 
	 * @Description: 根据选择数据的订单号查询出
	 * @param @param splitPurchaseNo
	 * @param @return   
	 * @return List<SwDemondOrderDetailModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月15日 下午7:48:10
	 */
	List<SwDemondOrderDetailModel> downloadDemondOrderByChoose(String[] splitPurchaseNo);

	/**
	 * 
	 * @Description: 判断发货数据表中是否已存在
	 * @param @param m
	 * @param @return   
	 * @return Boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月6日 上午12:36:40
	 */
	Integer isExistsByDetail(SwDemondOrderDetailModel m);

	/**
	 * 根据订单号获取采购单号
	 * <p>return: String</p>  
	 * <p>Description: SwDemondOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月17日
	 * @version 1.0
	 */
	String getPurChaseNoByOrderNo(String orderNo);

	/**
	 * 更新发货单明细为0
	 * <p>return: void</p>  
	 * <p>Description: SwDemondOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月17日
	 * @version 1.0
	 */
	void updateDeliveryDetail(SwDemondOrderDetailModel swDemondOrderDetailModel);

	/**
	 * 套打功能
	 * <p>return: List<PubPrintOrderModel></p>  
	 * <p>Description: SwDemondOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月22日
	 * @version 1.0
	 */
	List<PubPrintOrderModel> queryOrderDetailList(SwDemondOrderDetailModel model_q);

	/**
	 * 更新订单头表中的发货状态
	 * <p>return: void</p>  
	 * <p>Description: SwDemondOrderDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月27日
	 * @version 1.0
	 */
	void updateDelivery(SwDemondOrderDetailModel m);

	/**
	 * 查询需要打印的标签信息
	 * @param orderModel
	 * @return
	 */
	List<SwDemondOrderDetailModel> queryLabelPrintList(
			SwDemondOrderDetailModel orderModel);

	/**
	 * 
	 * @Description: 全部发货
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月27日 下午5:18:34
	 */
	void updateAllOrderObj(SwDemondOrderDetailModel model);

	/**
	 * 
	 * @Description: 更新发货状态为全部发货
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年6月3日 上午9:48:19
	 */
	void updateOrderDeliveryStatus(SwDemondOrderDetailModel model);

	/**
	 * 
	 * @Description: 修改主表发货状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年6月3日 上午10:05:56
	 */
	void updateDemandOrderDeliveryStatus(SwDemondOrderDetailModel model);

	/**
	 * insert by luoxianqin
	 * @Description: 记录二维码内容
	 * @param @param swDemondOrderDetailModel_q
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月11日 下午3:54:51
	 */
	void logBarCode(SwDemondOrderDetailModel swDemondOrderDetailModel_q, String ipAddr);

}
