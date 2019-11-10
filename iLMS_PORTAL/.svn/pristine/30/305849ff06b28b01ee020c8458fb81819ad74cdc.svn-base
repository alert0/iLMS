package com.hanthink.sw.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.sw.dao.SwZGJOrderDao;
import com.hanthink.sw.manager.SwZGJOrderManager;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hanthink.sw.model.SwZGJOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: SwZGJOrderManagerImpl
 * @Description: 支给件查询Manager
 * @author dtp
 * @date 2019年3月22日
 */
@Service("SwZGJOrderManager")
public class SwZGJOrderManagerImpl extends AbstractManagerImpl<String, SwZGJOrderModel> implements
		SwZGJOrderManager{

	@Resource
	private  SwZGJOrderDao  swZGJOrderDao;
	
	@Override
	protected Dao<String, SwZGJOrderModel> getDao() {
		return swZGJOrderDao;
	}

	/**
	 * @Description: 查询支给件订单   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@Override
	public PageList<SwZGJOrderModel> queryZGJOrderPage(SwZGJOrderModel model, DefaultPage page) {
		return swZGJOrderDao.queryZGJOrderPage(model, page);
	}

	/**
	 * @Description: 支给件订单导出    
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@Override
	public List<SwZGJOrderModel> queryZGJOrderList(SwZGJOrderModel model) {
		return swZGJOrderDao.queryZGJOrderList(model);
	}

	/**
	 * @Description: 查询打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@Override
	public List<PubPrintOrderModel> queryZGJOrderPrintDetailList(SwZGJOrderModel model_q) {
		return swZGJOrderDao.queryZGJOrderPrintDetailList(model_q);
	}

	/**
	 * @Description: 查询标签打印信息  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@Override
	public List<SwZGJOrderModel> querySwZGJOrderPrintLabelList(SwZGJOrderModel model) {
		return swZGJOrderDao.querySwZGJOrderPrintLabelList(model);
	}

	/**
	 * @Description:  选择导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月22日
	 */
	@Override
	public List<SwZGJOrderModel> querySelectZGJOrderList(SwZGJOrderModel model) {
		return swZGJOrderDao.querySelectZGJOrderList(model);
	}

	/**
	 * @Description: 更新本次发货数量  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	@Override
	public void updateZGJDetailForPrint(SwZGJOrderModel model) {
//		swZGJOrderDao.updateZGJDetailForPrint(model);
//		//根据订单号查询从表发货状态,全部发货,更新主表发货状态
//		SwZGJOrderModel model_q = new SwZGJOrderModel();
//		model_q.setPurchaseNo(model.getPurchaseNo());
//		model_q.setOrderNo(model.getOrderNo());
//		List<SwZGJOrderModel> list = swZGJOrderDao.queryZGJDetailList(model_q);
//		//0 未发货,1 部分发货,2全部发货
//		List<String> list_str = new ArrayList<String>();
//		for (SwZGJOrderModel swZGJOrderModel : list) {
//			list_str.add(swZGJOrderModel.getDeliveryStatus());
//		}
//		if(list_str.contains("1")) {
//			model_q.setDeliveryStatus("1");
//			swZGJOrderDao.updateOrderDeliveryStatus(model_q);
//		}else {
//			model_q.setDeliveryStatus("2");
//			swZGJOrderDao.updateOrderDeliveryStatus(model_q);
//		}
		swZGJOrderDao.updateZGJOrderForPrint(model);       //修改数量
		swZGJOrderDao.updateZGJOrderDeliveryStatus(model); // 修改主表发货状态
	}

	/**
	 * @Description: 查询订单明细  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZGJOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	@Override
	public PageList<SwZGJOrderModel> queryZGJOrderDetailPage(SwZGJOrderModel model, DefaultPage page) {
		return swZGJOrderDao.queryZGJOrderDetailPage(model, page);
	}

	/**
	 * @Description: 更新订单打印信息  
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月24日
	 */
	@Override
	public void updatePrintInfo(List<SwZGJOrderModel> list_printInfo) {
		for (SwZGJOrderModel model : list_printInfo) {
			swZGJOrderDao.updatePrintInfo(model);
		}
	}

	/**
	 * @Description: 更新下载状态 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月26日
	 */
	@Override
	public void updateDownloadStatus(SwZGJOrderModel model) {
		swZGJOrderDao.updateDownloadStatus(model);
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
		pkColumnVO.setColumnVal(model.getPurchaseNo());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		swZGJOrderDao.updateAllOrderObj(model);  //更新发货数
		swZGJOrderDao.updateOrderDeliveryStatus(model);  //更新发货状态为全部发货
	}

}
