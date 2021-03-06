package com.hanthink.sw.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.dao.SwDemondOrderDao;
import com.hanthink.sw.manager.SwDemondOrderManager;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("SwDemondOrderManager")
public class SwDemondOrderManagerImpl extends AbstractManagerImpl<String, SwDemondOrderDetailModel>
		implements SwDemondOrderManager {

	@Resource
	private SwDemondOrderDao swDemondOrderDao;

	@Override
	protected Dao<String, SwDemondOrderDetailModel> getDao() {
		return swDemondOrderDao;
	}

	/**
	 * 订单分页查询
	 * 
	 * @param model
	 * @param p
	 * @return Lxh
	 */
	@Override
	public PageList<SwDemondOrderDetailModel> queryDemandOrderPage(SwDemondOrderDetailModel model, DefaultPage p) {
		return swDemondOrderDao.queryDemandOrderPage(model, p);
	}

	/**
	 * 订单明细查询
	 * 
	 * @param model
	 * @param p
	 * @return Lxh
	 */
	@Override
	public PageList<SwDemondOrderDetailModel> queryDemandDetailOrderPage(SwDemondOrderDetailModel model,
			DefaultPage p) {
		return swDemondOrderDao.queryDemandDetailOrderPage(model, p);
	}

	/**
	 * 订单批量下载
	 * 
	 * @param model
	 * @return Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> downloadDemondOrder(SwDemondOrderDetailModel model) {
		return swDemondOrderDao.downloadDemondOrder(model);
	}

	/**
	 * 订单选择下载
	 * 
	 * @param swDemondOrderDetailModel3
	 * @return Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> downloadDemondOrderByChoose(SwDemondOrderDetailModel model) {
		return swDemondOrderDao.downloadDemondOrderByChoose(model);
	}

	/**
	 * 明细更新
	 * 
	 * @param model
	 * @param p
	 * @return Lxh
	 */
	@Override
	public PageList<SwDemondOrderDetailModel> demondOrderDetailUpdateQuery(Map<String, Object> map, DefaultPage p) {
		return swDemondOrderDao.demondOrderDetailUpdateQuery(map, p);
	}

	/**
	 * 打印查询
	 * 
	 * @param list_q
	 * @return Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> queryDemondOrderPrintLabelList(SwDemondOrderDetailModel model) {
		return swDemondOrderDao.queryDemondOrderPrintLabelList(model);
	}

	/**
	 * 打印状态更新
	 * 
	 * @param swDemondOrderDetailModel
	 *            Lxh
	 */
	@Override
	public void updatePrintLabelStatus(SwDemondOrderDetailModel swDemondOrderDetailModel) {
		swDemondOrderDao.updatePrintLabelStatus(swDemondOrderDetailModel);

	}

	/**
	 * 订单明细查询
	 * 
	 * @param model_q
	 * @return Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> queryDemondOrderDetailList(SwDemondOrderDetailModel model_q) {
		return swDemondOrderDao.queryDemondOrderDetailList(model_q);
	}

	/**
	 * 打印更新
	 * 
	 * @param list_printInfo
	 *            Lxh
	 */
	@Override
	public void updatePrintInfo(List<SwDemondOrderDetailModel> list_printInfo) {
		swDemondOrderDao.updatePrintInfo(list_printInfo);

	}

	/**
	 * 下载更新
	 * 
	 * @param swDemondOrderDetailModel
	 *            Lxh
	 */
	@Override
	public void updateDowloadInfo(Map<String, Object> map) {
		swDemondOrderDao.updateDowloadInfo(map);

	}

	/**
	 * 批量下载更新
	 * 
	 * @param model
	 *            Lxh
	 */
	@Override
	public void updateDowloadInfoByBatch(SwDemondOrderDetailModel model) {
		swDemondOrderDao.updateDowloadInfoByBatch(model);

	}

	/**
	 * 托盘打印
	 * 
	 * @param model_q
	 * @return Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> demondOrderPrintTpLabel(SwDemondOrderDetailModel model_q) {
		return swDemondOrderDao.demondOrderPrintTpLabel(model_q);
	}

	/**
	 * 发货数更新
	 * 
	 * @param model
	 *            Lxh
	 */
	@Override
	public void updateDemandDetailForPrint(SwDemondOrderDetailModel model) {
		swDemondOrderDao.updateDemandDetailForPrint(model);       //修改数量
		swDemondOrderDao.updateDemandOrderDeliveryStatus(model); // 修改主表发货状态

	}

	/**
	 * 获取发货单号
	 * 
	 * @param m
	 * @return Lxh
	 */
	@Override
	public List<SwDemondOrderDetailModel> getDeliveryNo(SwDemondOrderDetailModel m) {
		return swDemondOrderDao.getDeliveryNo(m);
	}

	/**
	 * 
	 * @Description: 查询导出的数据
	 * @param @param
	 *            model
	 * @param @return
	 * @return PageList<SwDemondOrderDetailModel>
	 * @throws @author
	 *             luoxq
	 * @date 2019年1月15日 下午7:06:27
	 */
	@Override
	public List<SwDemondOrderDetailModel> queryDemandOrderDownload(SwDemondOrderDetailModel model) {

		return swDemondOrderDao.queryDemandOrderDownload(model);
	}

	@Override
	public List<SwDemondOrderDetailModel> downloadDemondOrderByChoose(String[] splitPurchaseNo) {

		return swDemondOrderDao.downloadDemondOrderByChoose(splitPurchaseNo);
	}

	@Override
	public Boolean isExistsByDetail(SwDemondOrderDetailModel m) {
		Integer num = swDemondOrderDao.isExistsByDetail(m);
		if (num != null && num > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 套打功能
	 */
	@Override
	public List<PubPrintOrderModel> queryOrderDetailList(SwDemondOrderDetailModel model_q) {
		return swDemondOrderDao.queryOrderDetailList(model_q);
	}

	/**
	 * 更新发货单数据(重写）
	 * 
	 * @param m
	 *            LinZhuo
	 * @throws Exception
	 */
	public void updateDeliveryStatus(String[] orderNoArr) throws Exception {
		// 查询发货单数据是否存在，存在就流水号+1，不存在从0开始
		for (String orderNo : orderNoArr) {
			SwDemondOrderDetailModel m = new SwDemondOrderDetailModel();
			String purchaseNo = swDemondOrderDao.getPurChaseNoByOrderNo(orderNo); // 通过订单号获取采购单号
			Integer lastFive = swDemondOrderDao.getLastFive(purchaseNo);
			if (lastFive == null) {
				lastFive = 0;
			}
			IUser user = ContextUtil.getCurrentUser();
			m.setUpdateUser(user.getAccount());
			m.setFactoryCode(user.getCurFactoryCode());
			m.setPurchaseNo(purchaseNo);
			m.setOrderNo(orderNo);
			if (StringUtil.isEmpty(purchaseNo)) {
				return ;
			}
			lastFive = lastFive + 1;
			m.setLastFive(lastFive + "");
			List<SwDemondOrderDetailModel> list = swDemondOrderDao.queryDemondOrderPrintLabelList(m);
			for (int k = 0; k < list.size(); k++) {
				m.setPurchaseRowNo(list.get(k).getPurchaseRowNo());
				m.setTempDelivQty(list.get(k).getTempDelivQty());
				m.setPartNo(list.get(k).getPartNo());
				/**
				 * 判断本次加累计发货是否超过订单数量
				 */
				String tempDelivQty = list.get(k).getTempDelivQty();
				String totalDelivQty = list.get(k).getTotalDelivQty();
				String orderQty = list.get(k).getOrderQty();
				int tempDelivQtyInt = Integer.parseInt(tempDelivQty);
				int totalDelivQtyInt = Integer.parseInt(totalDelivQty);
				int orderQtyInt = Integer.parseInt(orderQty);
				/**
				 * 需要屏蔽掉发货数量为0的情况
				 */
				if(tempDelivQtyInt == 0) {
					break;
				}else if (orderQtyInt < tempDelivQtyInt + totalDelivQtyInt ) {
					break ;
				}else {
					swDemondOrderDao.insertDeliveryDetail(m);// 插入发货明细
					swDemondOrderDao.updateDeliveryDetail(m);// 更新订单明细中的发货状态
				}
			}
			/**
			 * 发货状态不为全部发货才能更新发货数据
			 */
			if(!("2").equals(list.get(0).getDeliveryStatus())) {
				swDemondOrderDao.insertDelivery(m);// 插入发货数据
				swDemondOrderDao.updateDelivery(m);// 更新订单头表中的发货状态
			}
		}
		
	}

	@Override
	public List<SwDemondOrderDetailModel> queryLabelPrintList(
			SwDemondOrderDetailModel orderModel) {
		return swDemondOrderDao.queryLabelPrintList(orderModel);
	}

	@Override
	public void updateAllOrderObj(SwDemondOrderDetailModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("全部发货");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SW_ORDER_DETAIL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PURCHASE_NO");
		pkColumnVO.setColumnVal(model.getPurchaseNoArr());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		swDemondOrderDao.updateAllOrderObj(model);  //更新发货数
		swDemondOrderDao.updateOrderDeliveryStatus(model);  //更新发货状态为全部发货
	}

	@Override
	public void logBarCode(SwDemondOrderDetailModel swDemondOrderDetailModel_q, String ipAddr) {
		swDemondOrderDao.logBarCode(swDemondOrderDetailModel_q,ipAddr);
	}

}
