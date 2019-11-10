package com.hanthink.jiso.manager;

import java.util.List;

import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoOrderDetailModel;
import com.hanthink.jiso.model.JisoOrderModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: JisoOrderManager
 * @Description: 厂外同步订单magager
 * @author dtp
 * @date 2018年9月17日
 */
public interface JisoOrderManager extends Manager<String, JisoOrderModel>{

	/**
	 * @Description: 查询厂外同步订单  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	PageList<JisoOrderModel> queryJisoOrderPage(JisoOrderModel model, DefaultPage page);

	/**
	 * @Description: 根据订单号查询厂外同步零件明细   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoOrderDetailModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	PageList<JisoOrderDetailModel> queryJisoOrderDetailPageByOrderNo(JisoOrderModel model, DefaultPage page);

	/**
	 * @Description: 查询厂外同步订单 (导出excel)   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	List<JisoOrderModel> queryJisoOrderList(JisoOrderModel model);

	/**
	 * @Description: 厂外同步订单明细(导出excel)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoOrderDetailModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	List<JisoOrderDetailModel> downloadJisoOrderDetail(JisoOrderModel model);

	/**
	 * @Description: 根据订单号查询订单明细(订单打印)  
	 * @param: @param list
	 * @param: @return    
	 * @return: List<JisoOrderDetailModel>   
	 * @author: dtp
	 * @date: 2018年9月18日
	 */
	List<JisoOrderDetailModel> queryJisoOrderDetailList(List<JisoOrderModel> list);

	/**
	 * @Description: 根据订单号查询订单明细(订单打印)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月24日
	 */
	List<PubPrintOrderModel> queryJisoOrderDetailList(JisoOrderDetailModel model);

	/**
	 * @Description: 更新厂外同步订单打印信息 
	 * @param: @param orderNoArr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月4日
	 */
	void updatePrintInfo(String[] orderNoArr);

	/**
	 * @Description: 根据订单号查询指示票信息
	 * @param: @param orderNoArr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月25
	 */
	List<JisoInsModel> queryJisoInsList(String[] orderNoArr);

	/**
	 * 更新订单打印状态
	 * @Description:   
	 * @param: @param printInfo_List    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	void updatePrintInfoList(List<JisoOrderDetailModel> printInfo_List);
	
}
