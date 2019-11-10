package com.hanthink.jit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitOrderDao;
import com.hanthink.jit.model.JitInsModel;
import com.hanthink.jit.model.JitLabelModel;
import com.hanthink.jit.model.JitOrderModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.pup.model.PupDcsModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitOrderDaoImpl
 * @Description: 拉动订单查询
 * @author dtp
 * @date 2018年9月28日
 */
@Repository
public class JitOrderDaoImpl extends MyBatisDaoImpl<String, JitOrderModel> implements JitOrderDao{

	@Override
	public String getNamespace() {
		return JitOrderModel.class.getName();
	}

	/**
	 * @Description: 拉动订单查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitOrderModel> queryJitOrderPage(JitOrderModel model, DefaultPage page) {
		return (PageList<JitOrderModel>) this.getList("queryJitOrderPage", model, page);
	}

	/**
	 * @Description: 拉动订单明细查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitOrderModel> queryJitOrderDetailPage(JitOrderModel model, DefaultPage page) {
		return (PageList<JitOrderModel>) this.getList("queryJitOrderDetailPage", model, page);
	}

	/**
	 * @Description: 拉动订单查询(excel导出) 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitOrderModel> queryJitOrderList(JitOrderModel model) {
		return (List<JitOrderModel>) this.getList("queryJitOrderPage", model);
	}

	/**
	 * @Description: 拉动订单明细导出excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitOrderModel> queryJitOrderDetailList(JitOrderModel model) {
		return (List<JitOrderModel>) this.getList("queryJitOrderDetailList", model);
	}

	/**
	 * @Description: 查询标签打印信息明细
	 * @param: @param list_q
	 * @param: @return    
	 * @return: List<JitLabelModel>   
	 * @author: dtp
	 * @date: 2018年10月4日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitLabelModel> queryJitOrderPrintLabelList(List<JitLabelModel> list_q) {
		return (List<JitLabelModel>) this.getList("queryJitOrderPrintLabelList", list_q);
	}

	/**
	 * @Description: 更新拉动订单打印信息 
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月4日
	 */
	@Override
	public void updatePrintInfo(List<JitOrderModel> list_printInfo) {
		this.updateByKey("updatePrintInfo", list_printInfo);
	}

	/**
	 * @Description: 更新打印状态
	 * @param: @param jitOrderModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@Override
	public void updatePrintState(JitOrderModel jitOrderModel) {
		this.updateByKey("updatePrintState", jitOrderModel);
	}

	/**
	 * @Description: 通过订单号查询配送单号
	 * @param: @param orderNoArr
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInsModel> queryInsNoByOrderNoArr(String[] orderNoArr) {
		return (List<JitInsModel>) this.getList("queryInsNoByOrderNoArr", orderNoArr);
	}

	/**
	 * @Description: 托盘标签打印
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年10月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitOrderModel> jitOrderPrintTpLabel(JitOrderModel model_q) {
		return (List<JitOrderModel>) this.getList("jitOrderPrintTpLabel", model_q);
	}

	/**
	 * @Description: 查询标签打印信息明细(model)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitLabelModel>   
	 * @author: dtp
	 * @date: 2018年10月27日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitLabelModel> queryJitOrderPrintLabelList(JitLabelModel model) {
		return (List<JitLabelModel>) this.getList("queryJitOrderPrintLabelList_model", model);
	}

	/**
	 * @Description: 查询订单打印明细 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2018年12月2日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubPrintOrderModel> queryJitOrderPrintDetailList(JitOrderModel model_q) {
		return (List<PubPrintOrderModel>) this.getList("queryJitOrderPrintDetailList", model_q);
	}

	/**
	 * @Description: 更新标签打印信息
	 * @param: @param List<JitLabelModel>    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月25日
	 */
	@Override
	public void updatePrintStatus(List<JitLabelModel> printInfo_List) {
		this.updateByKey("updatePrintStatus", printInfo_List);
	}

	/**
	 * @Description: 更新标签打印信息 
	 * @param: @param jitLabelModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public void updatePrintStatusModel(JitLabelModel jitLabelModel) {
		this.updateByKey("updatePrintStatusModel", jitLabelModel);
	}

	/**
	 * @Description: 查询订单DCS打印明细  
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2019年4月2日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubPrintOrderModel> queryJitOrderDCSDetailList(JitOrderModel model_q) {
		return (List<PubPrintOrderModel>) this.getList("queryJitOrderDCSDetailList", model_q);
	}

	/**
	 * @Description: 更新DCS任务的状态   
	 * @param: @param pupDcsModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月23日
	 */
	@Override
	public void updateDcsPrintStatus(PupDcsModel pupDcsModel) {
		this.updateByKey("updateDcsPrintStatus", pupDcsModel);
	}

}
