package com.hanthink.jiso.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jiso.dao.JisoOrderDao;
import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoOrderDetailModel;
import com.hanthink.jiso.model.JisoOrderModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JisoOrderDaoImpl
 * @Description: 厂外同步订单
 * @author dtp
 * @date 2018年9月17日
 */
@Repository
public class JisoOrderDaoImpl extends MyBatisDaoImpl<String, JisoOrderModel> implements JisoOrderDao{

	@Override
	public String getNamespace() {
		return JisoOrderModel.class.getName();
	}

	/**
	 * @Description: 查询厂外同步订单  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoOrderModel> queryJisoOrderPage(JisoOrderModel model, DefaultPage page) {
		return (PageList<JisoOrderModel>) this.getList("queryJisoOrderPage", model, page);
	}

	/**
	 * @Description: 根据订单号查询厂外同步零件明细   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoOrderDetailModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoOrderDetailModel> queryJisoOrderDetailPageByOrderNo(JisoOrderModel model, DefaultPage page) {
		return (PageList<JisoOrderDetailModel>) this.getList("queryJisoOrderDetailPageByOrderNo", model, page);
	}

	/**
	 * @Description: 查询厂外同步订单 (导出excel)   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoOrderModel> queryJisoOrderList(JisoOrderModel model) {
		return (List<JisoOrderModel>) this.getList("queryJisoOrderPage", model);
	}

	/**
	 * @Description: 厂外同步订单明细(导出excel)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoOrderDetailModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoOrderDetailModel> downloadJisoOrderDetail(JisoOrderModel model) {
		return (List<JisoOrderDetailModel>) this.getList("downloadJisoOrderDetail", model);
	}

	/**
	 * @Description: 根据订单号查询订单明细(订单打印)  
	 * @param: @param list
	 * @param: @return    
	 * @return: List<JisoOrderDetailModel>   
	 * @author: dtp
	 * @date: 2018年9月18日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoOrderDetailModel> queryJisoOrderDetailList(List<JisoOrderModel> list) {
		return (List<JisoOrderDetailModel>) this.getList("queryJisoOrderDetailList", list);
	}

	/**
	 * @Description: 根据订单号查询订单明细(订单打印)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月24日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubPrintOrderModel> queryJisoOrderDetailList(JisoOrderDetailModel model) {
		return (List<PubPrintOrderModel>) this.getList("queryJisoOrderDetailList_by_model", model);
	}

	/**
	 * @Description: 更新厂外同步订单打印信息 
	 * @param: @param orderNoArr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月4日
	 */
	@Override
	public void updatePrintInfo(String[] orderNoArr) {
		updateByKey("updatePrintInfo", orderNoArr);
	}

	/**
	 * @Description: 根据订单号查询指示票信息
	 * @param: @param orderNoArr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoInsModel> queryJisoInsList(String[] orderNoArr) {
		return (List<JisoInsModel>) this.getList("queryJisoInsList", orderNoArr);
	}

	/**
	 * @Description: 更新订单打印状态
	 * @param: @param jisoOrderDetailModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public void updatePrintInfoModel(JisoOrderDetailModel jisoOrderDetailModel) {
		this.updateByKey("updatePrintInfoModel", jisoOrderDetailModel);
	}

}
