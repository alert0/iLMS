package com.hanthink.sw.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.dao.SwDemondOrderDao;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class SwDemondOrderDaoImpl extends MyBatisDaoImpl<String, SwDemondOrderDetailModel> implements SwDemondOrderDao{

	
	@Override
	public String getNamespace() {
		return SwDemondOrderDetailModel.class.getName();
	}
	
	/**
	 * 订单分页查询
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@Override
	public PageList<SwDemondOrderDetailModel> queryDemandOrderPage(SwDemondOrderDetailModel model, DefaultPage p) {
		return (PageList<SwDemondOrderDetailModel>) this.getByKey("queryDemandOrderPage", model, p);
	}
	/**
	 * 订单明细查询
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@Override
	public PageList<SwDemondOrderDetailModel> queryDemandDetailOrderPage(SwDemondOrderDetailModel model, DefaultPage p) {
		return (PageList<SwDemondOrderDetailModel>)this.getByKey("queryDemandDetailOrderPage", model, p);
	}
	
	/**
	 * 订单下载
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> downloadDemondOrder(SwDemondOrderDetailModel model) {
		return (List<SwDemondOrderDetailModel>)this.getByKey("downloadDemondOrder", model);
	}

	/**
	 * 订单选择下载
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> downloadDemondOrderByChoose(SwDemondOrderDetailModel model) {
		return (List<SwDemondOrderDetailModel>)this.getByKey("downloadDemondOrderByChoose", model);
	}

	/**
	 * 更新收货明细
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@Override
	public PageList<SwDemondOrderDetailModel> demondOrderDetailUpdateQuery(Map<String, Object> map,
			DefaultPage p) {
		return (PageList<SwDemondOrderDetailModel>) this.getByKey("demondOrderDetailUpdateQuery", map, p);
	}

	/**
	 * 标签打印查询
	 * @param list_q
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwDemondOrderDetailModel> queryDemondOrderPrintLabelList(SwDemondOrderDetailModel model) {
		
			
		return  this.getByKey("queryDemondOrderPrintLabelList",model);
	}

	/**
	 * 打印状态更新
	 * @param swDemondOrderDetailModel
	 * Lxh
	 */
	@Override
	public void updatePrintLabelStatus(SwDemondOrderDetailModel swDemondOrderDetailModel) {

		this.updateByKey("updatePrintLabelStatus",swDemondOrderDetailModel);
	}

	/**
	 * 明细查询
	 * @param model_q
	 * @return
	 * Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> queryDemondOrderDetailList(SwDemondOrderDetailModel model_q) {
		return this.getByKey("queryDemondOrderDetailList", model_q);
	}
	
	/**
	 * 打印更新
	 * @param list_printInfo
	 * Lxh
	 */
	@Override
	public void updatePrintInfo(List<SwDemondOrderDetailModel> list_printInfo) {
		for (SwDemondOrderDetailModel swDemondOrderDetailModel : list_printInfo) {
			this.updateByKey("updatePrintInfo",swDemondOrderDetailModel);
		}
		
	}
	
	/**
	 * 下载更新
	 * @param swDemondOrderDetailModel
	 * Lxh
	 */
	@Override
	public void updateDowloadInfo(Map<String, Object> map) {
		this.updateByKey("updateDowloadInfo",map);
		
	}

	/**
	 * 批量下载更新
	 * @param model
	 * Lxh
	 */
	@Override
	public void updateDowloadInfoByBatch(SwDemondOrderDetailModel model) {
		this.updateByKey("updateDowloadInfoByBatch",model);
		
	}

	/**
	 * 托盘打印
	 * @param model_q
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwDemondOrderDetailModel> demondOrderPrintTpLabel(SwDemondOrderDetailModel model_q) {
		return (List<SwDemondOrderDetailModel>) this.getList("demondOrderPrintTpLabel", model_q);

	}

	/**
	 * 发货数更新
	 * @param model
	 * Lxh
	 */
	@Override
	public void updateDemandDetailForPrint(SwDemondOrderDetailModel model) {
		this.updateByKey("updateDemandDetailForPrint",model);
	}

	/**
	 * 获取发货单号
	 * @param m
	 * @return
	 * Lxh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwDemondOrderDetailModel> getDeliveryNo(SwDemondOrderDetailModel m) {
		// TODO Auto-generated method stub
		return (List<SwDemondOrderDetailModel>) this.getList("getDeliveryNo", m);
	}

	/**
	 * 插入发货订单
	 * @param m
	 * Lxh
	 */
	@Override
	public void insertDelivery(SwDemondOrderDetailModel m) {
		this.insertByKey("insertDelivery", m);
		
	}

	/**
	 * 插入发货订单明细
	 * @param m
	 * Lxh
	 */
	@Override
	public void insertDeliveryDetail(SwDemondOrderDetailModel m) {
		this.insertByKey("insertDeliveryDetail", m);
		
	}

	/**
	 * 
	 * @Description: 获取发货单号最后五位，且为最大值
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月15日 下午6:08:06
	 */
	@Override
	public Integer getLastFive(String purchaseNo) {
		
		return this.sqlSessionTemplate.selectOne("getLastFive", purchaseNo);
	}

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
	@Override
	public List<SwDemondOrderDetailModel> queryDemandOrderDownload(SwDemondOrderDetailModel model) {
		
		return (List<SwDemondOrderDetailModel>) this.getByKey("downloadDemondOrder", model);
	}

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
	@Override
	public List<SwDemondOrderDetailModel> downloadDemondOrderByChoose(String[] splitPurchaseNo) {
		return this.getByKey("downloadDemondOrderByChoose", splitPurchaseNo);
	}

	@Override
	public Integer isExistsByDetail(SwDemondOrderDetailModel m) {
		
		return this.sqlSessionTemplate.selectOne("isExistsByDetail", m);
	}

	/**
	 * 根据订单号获取采购单号
	 */
	@Override
	public String getPurChaseNoByOrderNo(String orderNo) {
		return this.sqlSessionTemplate.selectOne("getPurChaseNoByOrderNo", orderNo);
	}

	/**
	 * 更新发货单明细为0
	 */
	@Override
	public void updateDeliveryDetail(SwDemondOrderDetailModel swDemondOrderDetailModel) {
		updateByKey("updateDeliveryDetail", swDemondOrderDetailModel);
	}

	/**
	 * 套打功能
	 */
	@Override
	public List<PubPrintOrderModel> queryOrderDetailList(SwDemondOrderDetailModel model_q) {
		return (List<PubPrintOrderModel>) this.getList("queryOrderDetailList", model_q);
	}

	/**
	 * 更新订单头表中的发货状态
	 */
	@Override
	public void updateDelivery(SwDemondOrderDetailModel m) {
		updateByKey("updateDelivery", m);
	}

	@Override
	public List<SwDemondOrderDetailModel> queryLabelPrintList(
			SwDemondOrderDetailModel orderModel) {
		return  this.getByKey("queryLabelPrintList",orderModel);
	}


	
}
