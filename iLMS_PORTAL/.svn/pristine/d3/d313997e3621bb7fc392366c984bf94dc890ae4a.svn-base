package com.hanthink.sw.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sw.dao.SwPickupPlanDao;
import com.hanthink.sw.manager.SwPickupPlanManager;
import com.hanthink.sw.model.SwPickupPlanModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
* <p>Title: SwPickupPlanManagerImpl</p>  
* <p>Description: </p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月19日 上午10:45:50
 */
@Service("SwPickupPlanManager")
public class SwPickupPlanManagerImpl extends AbstractManagerImpl<String, SwPickupPlanModel>
implements SwPickupPlanManager{
	
	@Resource
	private SwPickupPlanDao dao;

	@Override
	protected Dao<String, SwPickupPlanModel> getDao() {
		
		return dao;
	}

	/**
	 * 
	 * <p>Title: queryJisoPickupPage</p>  
	 * <p>Description: 取货计划管理界面，分页查询</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月19日 上午10:51:17
	 */
	@Override
	public PageList<SwSupplierGroupModel> queryJisoPickupPage(SwPickupPlanModel model, DefaultPage p) {
		
		return dao.queryJisoPickupPage(model,p);
	}

	/**
	 * 
	 * <p>Title: removeAndLogByIds</p>  
	 * <p>Description: 取货计划管理界面，批量删除</p>  
	 * @param orderNos
	 * @param purchaseNos
	 * @param ipAddr  
	 * @authoer luoxq
	 * @time 2018年10月19日 上午11:00:45
	 */
	@Override
	public void removeAndLogByIds(String[] orderNoArr,  String ipAddr) {
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_SW_PICKUP_PLAN");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ORDER_NO");
		pkColumnVO.setColumnValArr(orderNoArr);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
						
		dao.removeByOrderAndPurchase(orderNoArr);
		
		
	}

	/**
	 * 
	 * <p>Title: feedbackPickupPlan</p>  
	 * <p>Description: 取货计划，供应商反馈功能</p>  
	 * @param ids
	 * @param feedbackStatus  
	 * @authoer luoxq
	 * @time 2018年10月19日 下午4:54:15
	 */
	@Override
	public void feedbackPickupPlan(String[] orderNos, String feedbackStatus) {
		dao.feedbackPickupPlan(orderNos,feedbackStatus);
	}

	/**
	 * 取货计划导出功能
	 * @param model
	 * @return
	 */
	@Override
	public List<SwPickupPlanModel> querySwPickupPlanByKey(SwPickupPlanModel model) {
		return dao.querySwPickupPlanByKey(model);
	}
	/**
	 * 查询订单详情
	 */
	@Override
	public PageList<SwPickupPlanModel> queryOrderDetail(SwPickupPlanModel modle, Page page) throws Exception {
		modle.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<SwPickupPlanModel> list = dao.queryOrderDetail(modle,page);
		return new PageList<SwPickupPlanModel>(list);
	}

	@Override
	public List<SwPickupPlanModel> exportExcelForOrderDetails(String[] purchaseNos)throws Exception {
		List<SwPickupPlanModel> list = new ArrayList<SwPickupPlanModel>();
		for (int i = 0; i < purchaseNos.length; i++) {
			SwPickupPlanModel pickupPlanModel = new SwPickupPlanModel();
			pickupPlanModel.setPurchaseNo(purchaseNos[i]);
			list.add(pickupPlanModel);
		}
		list = dao.exportExcelForOrderDetails(list);
//		int flag = 0;
//		for (int i = flag; i < list.size()-1; i++) {
//			boolean firstDiffFlag = list.get(flag).getPurchaseNo().equals(list.get(i+1).getPurchaseNo());
//			if (firstDiffFlag) {
//				list.get(i+1).setPurchaseNo(null);
//				list.get(i+1).setArea(null);
//				list.get(i+1).setRouteCode(null);
//				list.get(i+1).setSupplierNo(null);
//				list.get(i+1).setCarType(null);
//				list.get(i+1).setPlanPickupTime(null);
//				list.get(i+1).setPlanArrTime(null);
//				list.get(i+1).setPlanAssembleTime(null);
//				list.get(i+1).setOrderType(null);
//				list.get(i+1).setOrderUnit(null);
//			}else {
//				flag = i + 1;
//			}
//		}
		return list;
	}

}
