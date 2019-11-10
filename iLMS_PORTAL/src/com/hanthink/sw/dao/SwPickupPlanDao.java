package com.hanthink.sw.dao;

import java.util.List;

import com.hanthink.sw.model.SwPickupPlanModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SwPickupPlanDao extends Dao<String, SwPickupPlanModel>{

	/**
	 * 
	 * <p>Title: queryJisoPickupPage</p>  
	 * <p>Description: 取货计划管理界面，分页查询</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月19日 上午10:52:05
	 */
	PageList<SwSupplierGroupModel> queryJisoPickupPage(SwPickupPlanModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: removeByOrderAndPurchase</p>  
	 * <p>Description: 取货计划管理界面，批量删除</p>  
	 * @param orderNoArr
	 * @authoer luoxq
	 * @time 2018年10月19日 上午11:07:51
	 */
	void removeByOrderAndPurchase(String[] orderNoArr);

	/**
	 * 
	 * <p>Title: feedbackPickupPlan</p>  
	 * <p>Description: 取货计划，供应商反馈功能</p>  
	 * @param orderNos
	 * @param purchaseNo
	 * @authoer luoxq
	 * @time 2018年10月19日 下午5:06:51
	 */
	void feedbackPickupPlan(String[] orderNos, String feedbackStatus);

	/**
	 * 取货计划，导出功能
	 * @param model
	 * @return
	 */
	List<SwPickupPlanModel> querySwPickupPlanByKey(SwPickupPlanModel model);
	/**
	 * 查询订单详情
	 * @param modle
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月22日
	 */
	List<SwPickupPlanModel> queryOrderDetail(SwPickupPlanModel modle, Page page)throws Exception;
	/**
	 * 导出数据查询
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月22日
	 */
	List<SwPickupPlanModel> exportExcelForOrderDetails(List<SwPickupPlanModel> list)throws Exception;
			
}
