package com.hanthink.jiso.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.jiso.dao.JisoOrderDao;
import com.hanthink.jiso.manager.JisoOrderManager;
import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoOrderDetailModel;
import com.hanthink.jiso.model.JisoOrderModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JisoOrderManagerImpl
 * @Description: 厂外同步订单ManagerImpl
 * @author dtp
 * @date 2018年9月17日
 */
@Service("jisoOrderManager")
public class JisoOrderManagerImpl extends AbstractManagerImpl<String, JisoOrderModel> implements JisoOrderManager{

	@Resource 
	private JisoOrderDao jisoOrderDao;
	
	@Override
	protected Dao<String, JisoOrderModel> getDao() {
		return jisoOrderDao;
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
	@Override
	public PageList<JisoOrderModel> queryJisoOrderPage(JisoOrderModel model, DefaultPage page) {
		return jisoOrderDao.queryJisoOrderPage(model, page);
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
	@Override
	public PageList<JisoOrderDetailModel> queryJisoOrderDetailPageByOrderNo(JisoOrderModel model, DefaultPage page) {
		return jisoOrderDao.queryJisoOrderDetailPageByOrderNo(model, page);
	}

	/**
	 * @Description: 查询厂外同步订单 (导出excel)   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@Override
	public List<JisoOrderModel> queryJisoOrderList(JisoOrderModel model) {
		return jisoOrderDao.queryJisoOrderList(model);
	}

	/**
	 * @Description: 厂外同步订单明细(导出excel)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoOrderDetailModel>   
	 * @author: dtp
	 * @date: 2018年9月17日
	 */
	@Override
	public List<JisoOrderDetailModel> downloadJisoOrderDetail(JisoOrderModel model) {
		return jisoOrderDao.downloadJisoOrderDetail(model);
	}

	/**
	 * @Description: 根据订单号查询订单明细(订单打印)  
	 * @param: @param list
	 * @param: @return    
	 * @return: List<JisoOrderDetailModel>   
	 * @author: dtp
	 * @date: 2018年9月18日
	 */
	@Override
	public List<JisoOrderDetailModel> queryJisoOrderDetailList(List<JisoOrderModel> list) {
		return jisoOrderDao.queryJisoOrderDetailList(list);
	}

	/**
	 * @Description: 根据订单号查询订单明细(订单打印)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月24日
	 */
	@Override
	public List<PubPrintOrderModel> queryJisoOrderDetailList(JisoOrderDetailModel model) {
		return jisoOrderDao.queryJisoOrderDetailList(model);
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
		jisoOrderDao.updatePrintInfo(orderNoArr);
	}

	/**
	 * @Description: 根据订单号查询指示票信息
	 * @param: @param orderNoArr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月25
	 */
	@Override
	public List<JisoInsModel> queryJisoInsList(String[] orderNoArr) {
		return jisoOrderDao.queryJisoInsList(orderNoArr);  
	}

	/**
	 * 更新订单打印状态
	 * @Description:   
	 * @param: @param printInfo_List    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public void updatePrintInfoList(List<JisoOrderDetailModel> printInfo_List) {
		//jisoOrderDao.updatePrintInfoList(printInfo_List);
		for (JisoOrderDetailModel jisoOrderDetailModel : printInfo_List) {
			jisoOrderDao.updatePrintInfoModel(jisoOrderDetailModel);
		}
	}

}
