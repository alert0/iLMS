package com.hanthink.sw.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwLongOrderDao;
import com.hanthink.sw.model.SwLongOrderModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
@Repository
public class SwLongOrderDaoImpl extends MyBatisDaoImpl<String,SwLongOrderModel>
implements SwLongOrderDao{

	@Override
	public String getNamespace() {
		
		return SwLongOrderModel.class.getName();
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwLongOrderModel> queryLongOrderPage(SwLongOrderModel model, DefaultPage p) {
		
		return this.getListByKey("queryLongOrderPage", model, p);
	}

	@Override
	public List<SwLongOrderModel> queryLongOrderDownload(SwLongOrderModel model) {
		
		return this.getByKey("queryLongOrderPage", model);
	}

	@Override
	public List<SwLongOrderModel> downloadLongOrderByChoose(List<Map<String, String>> paramList) {
		
		return this.getByKey("downloadLongOrderByChoose", paramList);
	}

	@Override
	public List<SwLongOrderModel> queryLongOrderDetailList(SwLongOrderModel model_q) {
		
		return this.getByKey("queryLongOrderDetailList", model_q);
	}

	@Override
	public void updateDelivery(SwLongOrderModel m) {
		this.updateByKey("updateDelivery", m);
	}

	@Override
	public List<SwLongOrderModel> queryLongOrderPrintLabelList(SwLongOrderModel model) {
		return this.getByKey("queryLongOrderPrintLabelList", model);
	}

	@Override
	public List<SwLongOrderModel> longOrderPrintTpLabel(SwLongOrderModel model_q) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SwLongOrderModel> getVersion() {
		Map<String, Object> map = new HashMap<String, Object>();
		return this.getByKey("getVersion", map);
	}

	@Override
	public void updateFeedbackLongOrder(SwLongOrderModel model) {
		this.updateByKey("updateFeedbackLongOrder", model);
	}

	@Override
	public void deleteLongOrder(SwLongOrderModel model) {
		this.updateByKey("deleteLongOrder", model);
	}

	@Override
	public void deleteLong(SwLongOrderModel model) {
		this.updateByKey("deleteLong", model);
	}

	@Override
	public void updateLongReceiveQty(SwLongOrderModel model) {
		this.updateByKey("updateLongReceiveQty", model);
	}

	@Override
	public SwLongOrderModel getDefaultVersion(Map<String, String> map) {
		return this.getUnique("getDefaultVersion", map);
	}

}
