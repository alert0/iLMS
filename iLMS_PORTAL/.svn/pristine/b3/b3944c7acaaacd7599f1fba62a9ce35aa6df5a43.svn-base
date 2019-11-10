package com.hanthink.sw.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwDeliveryDao;
import com.hanthink.sw.model.SwDeliveryModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
@Repository
public class SwDeliveryDaoImpl extends MyBatisDaoImpl<String, SwDeliveryModel>
implements SwDeliveryDao{

	@Override
	public String getNamespace() {
		
		return SwDeliveryModel.class.getName();
	}

	/**
	 * 
	 * <p>Title: queryJisoDeliveryPage</p>  
	 * <p>Description: 发布数据管理界面，分页查询功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:09:49
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwDeliveryModel> queryJisoDeliveryPage(SwDeliveryModel model, DefaultPage p) {
		
		return (PageList<SwDeliveryModel>) this.getList("queryJisoDeliveryPage", model, p);
	}

	/**
	 * 
	 * <p>Title: queryJisoDeliveryDetailPage</p>  
	 * <p>Description: 发货数据管理界面，明细查看功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:14:02
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwDeliveryModel> queryJisoDeliveryDetailPage(SwDeliveryModel model, DefaultPage p) {
		
		return (PageList<SwDeliveryModel>) this.getList("queryJisoDeliveryDetailPage", model, p);
	}

	/**
	 * 
	 * @Description: 查询需要打印的信息
	 * @param @param model_q
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月26日 下午3:08:45
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwDeliveryModel> queryDeliveryOrderDetailList(SwDeliveryModel model_q) {
		
		return (List<SwDeliveryModel>) this.getList("queryDeliveryOrderDetailList", model_q);
	}

	/**
	 * 
	 * <p>Title: queryJisoDeliveryDetailPage</p>  
	 * <p>Description: 发货数据管理界面，明细查看功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:14:02
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwDeliveryModel> deliveryOrderPrintTpLabel(SwDeliveryModel model_q) {
		
		return (List<SwDeliveryModel>) this.getList("deliveryOrderPrintTpLabel", model_q);
	}

	/**
	 * 
	 * @Description: 查询标签打印需要的信息
	 * @param @param list_q
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月27日 上午9:58:26
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwDeliveryModel> querydeliveryOrderPrintLabelList(List<SwDeliveryModel> list_q) {
		
		return (List<SwDeliveryModel>) this.getList("querydeliveryOrderPrintLabelList", list_q);
	}

	/**
	 * 
	 * @Description: 查询标签打印需要的信息
	 * @param @param model
	 * @param @return   
	 * @return List<SwDeliveryModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月20日 下午11:43:53
	 */
	@Override
	public List<SwDeliveryModel> querydeliveryOrderPrintLabelList(SwDeliveryModel model) {
		
		return this.getByKey("querydeliveryOrderPrintLabelListModel", model);
	}

	@Override
	public List<SwDeliveryModel> queryDeliveryOrderPrintLabelList(SwDeliveryModel orderModel) {
		
		return this.getByKey("queryDeliveryOrderPrintLabelList", orderModel);
	}

}
