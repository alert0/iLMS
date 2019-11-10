package com.hanthink.sw.dao.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwPickupPlanDao;
import com.hanthink.sw.model.SwPickupPlanModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class SwPickupPlanDaoImpl extends MyBatisDaoImpl<String, SwPickupPlanModel>
implements SwPickupPlanDao{

	@Override
	public String getNamespace() {
		
		return SwPickupPlanModel.class.getName();
	}

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
	@Override
	public PageList<SwSupplierGroupModel> queryJisoPickupPage(SwPickupPlanModel model, DefaultPage p) {
		
		return (PageList<SwSupplierGroupModel>) this.getList("queryJisoPickupPage", model, p);
	}

	/**
	 * 
	 * <p>Title: removeByOrderAndPurchase</p>  
	 * <p>Description: 取货计划管理界面，批量删除</p>  
	 * @param orderNoArr
	 * @param purchaseNoArr  
	 * @authoer luoxq
	 * @time 2018年10月19日 上午11:07:51
	 */
	@Override
	public void removeByOrderAndPurchase(String[] orderNoArr) {
//		Map< String, Object> map = new HashMap<>();
		SwPickupPlanModel model = new SwPickupPlanModel();
		for (String order : orderNoArr) {
			if(!order.isEmpty() ) {
				model.setOrderNo(order);
					this.deleteByKey("removeByOrderAndPurchase", model);
			}
		}
	}


	/**
	 * 
	 * <p>Title: feedbackPickupPlan</p>  
	 * <p>Description: 取货计划，供应商反馈功能</p>  
	 * @param orerNo
	 * @param purchaseNo
	 * @param feedbackStatus  
	 * @authoer luoxq
	 * @time 2018年10月19日 下午5:06:51
	 */
	@Override
	public void feedbackPickupPlan(String[] orderNos, String feedbackStatus) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orderNos", orderNos);
		map.put("feedbackStatus", feedbackStatus);
		this.updateByKey("feedbackPickupPlan", map);

	}

	@Override
	public List<SwPickupPlanModel> querySwPickupPlanByKey(SwPickupPlanModel model) {
		return (List<SwPickupPlanModel>) this.getList("queryJisoPickupPage", model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SwPickupPlanModel> queryOrderDetail(SwPickupPlanModel modle, Page page) throws Exception {
		List<SwPickupPlanModel> list = new ArrayList<SwPickupPlanModel>();
		list.add(modle);
		return this.getListByKey("queryOrderDetail", list, page);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SwPickupPlanModel> exportExcelForOrderDetails(List<SwPickupPlanModel> list) throws Exception {
		return (List<SwPickupPlanModel>) this.getList("queryOrderDetail", list);
	}

}
