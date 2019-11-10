package com.hanthink.sw.manager;

import java.util.List;

import com.hanthink.sw.model.SwPickupPlanModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SwPickupPlanManager extends Manager<String, SwPickupPlanModel>{

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
	PageList<SwSupplierGroupModel> queryJisoPickupPage(SwPickupPlanModel model, DefaultPage p);

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
	void removeAndLogByIds(String[] orderNoArr, String ipAddr);

	/**
	 * 
	 * <p>Title: feedbackPickupPlan</p>  
	 * <p>Description: 取货计划，供应商反馈功能</p>  
	 * @param ids
	 * @param feedbackStatus  
	 * @authoer luoxq
	 * @time 2018年10月19日 下午4:54:15
	 */
	void feedbackPickupPlan(String[] orerNos, String feedbackStatus);

	/**
	 * 取货计划导出功能
	 * @param model
	 * @return
	 */
	List<SwPickupPlanModel> querySwPickupPlanByKey(SwPickupPlanModel model);
	/**
	 * 订单详情查询
	 * @param modle
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月22日
	 */
	PageList<SwPickupPlanModel> queryOrderDetail(SwPickupPlanModel modle, Page page)throws Exception;
	/**
	 * 订单详情查询
	 * @param reqList
	 * @return
	 * @author zmj
	 * @date 2019年2月22日
	 */
	List<SwPickupPlanModel> exportExcelForOrderDetails(String[] purchaseNos)throws Exception;


}
