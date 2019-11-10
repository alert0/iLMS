package com.hanthink.jit.manager;

import java.util.List;

import com.hanthink.jit.model.JitInsModel;
import com.hanthink.jit.model.JitLabelModel;
import com.hanthink.jit.model.JitOrderModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: JitOrderManager
 * @Description: 拉动订单查询
 * @author dtp
 * @date 2018年9月28日
 */
public interface JitOrderManager extends Manager<String, JitOrderModel>{

	/**
	 * @Description: 拉动订单查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	PageList<JitOrderModel> queryJitOrderPage(JitOrderModel model, DefaultPage page);

	/**
	 * @Description: 拉动订单明细查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	PageList<JitOrderModel> queryJitOrderDetailPage(JitOrderModel model, DefaultPage page);

	/**
	 * @Description: 拉动订单查询 excel导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	List<JitOrderModel> queryJitOrderList(JitOrderModel model);

	/**
	 * @Description: 拉动订单明细导出excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	List<JitOrderModel> queryJitOrderDetailList(JitOrderModel model);

	/**
	 * @Description: 查询标签打印信息明细
	 * @param: @param list_q
	 * @param: @return    
	 * @return: List<JitLabelModel>   
	 * @author: dtp
	 * @date: 2018年10月4日
	 */
	List<JitLabelModel> queryJitOrderPrintLabelList(List<JitLabelModel> list_q);

	/**
	 * @Description: 更新拉动订单打印信息 
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月4日
	 */
	void updatePrintInfo(List<JitOrderModel> list_printInfo);

	/**
	 * @Description: 通过订单号查询配送单号
	 * @param: @param orderNoArr
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	List<JitInsModel> queryInsNoByOrderNoArr(String[] orderNoArr);

	/**
	 * @Description: 托盘标签打印
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年10月23日
	 */
	List<JitOrderModel> jitOrderPrintTpLabel(JitOrderModel model_q);

	/**
	 * @Description: 查询标签打印信息明细(model)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitLabelModel>   
	 * @author: dtp
	 * @date: 2018年10月27日
	 */
	List<JitLabelModel> queryJitOrderPrintLabelList(JitLabelModel model);

	/**
	 * @Description: 查询订单打印明细 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2018年12月2日
	 */
	List<PubPrintOrderModel> queryJitOrderPrintDetailList(JitOrderModel model_q);
	
	/**
	 * @Description: 更新标签打印信息
	 * @param: @param List<JitLabelModel>    
	 * @return: void   
	 * @author: dtp
	 * @param list 
	 * @date: 2018年12月25日
	 */
	void updatePrintStatus(List<JitLabelModel> printInfo_List, List<JitLabelModel> list);

}
