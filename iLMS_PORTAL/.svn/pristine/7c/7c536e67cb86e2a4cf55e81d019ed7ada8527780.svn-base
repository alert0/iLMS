package com.hanthink.jit.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.jit.dao.JitLabelDao;
import com.hanthink.jit.dao.JitOrderDao;
import com.hanthink.jit.manager.JitOrderManager;
import com.hanthink.jit.model.JitInsModel;
import com.hanthink.jit.model.JitLabelModel;
import com.hanthink.jit.model.JitOrderModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitOrderManagerImpl
 * @Description: 拉动订单查询
 * @author dtp
 * @date 2018年9月28日
 */
@Service("jitOrderManager")
public class JitOrderManagerImpl extends AbstractManagerImpl<String, JitOrderModel> implements JitOrderManager{

	@Resource 
	private JitOrderDao jitOrderDao;
	
	@Resource
	private JitLabelDao jitLabelDao;
	
	@Override
	protected Dao<String, JitOrderModel> getDao() {
		return jitOrderDao;
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
	@Override
	public PageList<JitOrderModel> queryJitOrderPage(JitOrderModel model, DefaultPage page) {
		return jitOrderDao.queryJitOrderPage(model, page);
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
	@Override
	public PageList<JitOrderModel> queryJitOrderDetailPage(JitOrderModel model, DefaultPage page) {
		return jitOrderDao.queryJitOrderDetailPage(model, page);
	}

	/**
	 * @Description: 拉动订单查询 excel导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@Override
	public List<JitOrderModel> queryJitOrderList(JitOrderModel model) {
		return jitOrderDao.queryJitOrderList(model);
	}

	/**
	 * @Description: 拉动订单明细导出excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年9月28日
	 */
	@Override
	public List<JitOrderModel> queryJitOrderDetailList(JitOrderModel model) {
		return jitOrderDao.queryJitOrderDetailList(model);
	}

	/**
	 * @Description: 查询标签打印信息明细
	 * @param: @param list_q
	 * @param: @return    
	 * @return: List<JitLabelModel>   
	 * @author: dtp
	 * @date: 2018年10月4日
	 */
	@Override
	public List<JitLabelModel> queryJitOrderPrintLabelList(List<JitLabelModel> list_q) {
		return jitOrderDao.queryJitOrderPrintLabelList(list_q);
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
		//jitOrderDao.updatePrintInfo(list_printInfo);
		for (int i = 0; i < list_printInfo.size(); i++) {
			jitOrderDao.updatePrintState(list_printInfo.get(i));
		}
	}

	/**
	 * @Description: 通过订单号查询配送单号
	 * @param: @param orderNoArr
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@Override
	public List<JitInsModel> queryInsNoByOrderNoArr(String[] orderNoArr) {
		return jitOrderDao.queryInsNoByOrderNoArr(orderNoArr);
	}

	/**
	 * @Description: 托盘标签打印
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JitOrderModel>   
	 * @author: dtp
	 * @date: 2018年10月23日
	 */
	@Override
	public List<JitOrderModel> jitOrderPrintTpLabel(JitOrderModel model_q) {
		return jitOrderDao.jitOrderPrintTpLabel(model_q);
	}

	/**
	 * @Description: 查询标签打印信息明细(model)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitLabelModel>   
	 * @author: dtp
	 * @date: 2018年10月27日
	 */
	@Override
	public List<JitLabelModel> queryJitOrderPrintLabelList(JitLabelModel model) {
		return jitOrderDao.queryJitOrderPrintLabelList(model);
	}

	/**
	 * @Description: 查询订单打印明细 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<PubPrintOrderModel>   
	 * @author: dtp
	 * @date: 2018年12月2日
	 */
	@Override
	public List<PubPrintOrderModel> queryJitOrderPrintDetailList(JitOrderModel model_q) {
		return jitOrderDao.queryJitOrderPrintDetailList(model_q);
	}

	/**
	 * @Description: 更新标签打印信息
	 * @param: @param List<JitLabelModel>    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月25日
	 */
	@Override
	public void updatePrintStatus(List<JitLabelModel> printInfo_List, List<JitLabelModel> list) {
		//jitOrderDao.updatePrintStatus(printInfo_List);
		for (JitLabelModel jitLabelModel : printInfo_List) {
			jitOrderDao.updatePrintStatusModel(jitLabelModel);
		}
		for (JitLabelModel jitLabelModel : list) {
			jitLabelDao.updateLabelUUID(jitLabelModel);
		}
	}

}
