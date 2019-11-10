package com.hanthink.jiso.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jiso.dao.JisoReckonDao;
import com.hanthink.jiso.model.JisoInsManuDealModel;
import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoNetReqModel;
import com.hanthink.jiso.model.JisoOrderManuDealModel;
import com.hanthink.jiso.model.JisoPartgroupModel;
import com.hanthink.jiso.model.JisoVehQueueModel;
import com.hanthink.jit.model.JitInsModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JisoReckonDaoImpl
 * @Description: 厂外同步推算控制台
 * @author dtp
 * @date 2018年9月17日
 */
@Repository
public class JisoReckonDaoImpl extends MyBatisDaoImpl<String, JisoVehQueueModel> implements JisoReckonDao{

	@Override
	public String getNamespace() {
		return JisoVehQueueModel.class.getName();
	}

	/**
	 * @Description: 厂外同步推算控制台-查询过点车序    
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月15日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoVehQueueModel> queryJisoVehQueuePage(JisoVehQueueModel model, DefaultPage page) {
		return (PageList<JisoVehQueueModel>) this.getList("queryJisoVehQueuePage", model, page);
	}
	
	/**
	 * @Description: 查询待组票净需求-零件组列表   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: List<JisoPartgroupModel>   
	 * @author: dtp
	 * @date: 2018年9月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoPartgroupModel> queryJisoPartGroupPage(JisoPartgroupModel model, DefaultPage page) {
		return (List<JisoPartgroupModel>) this.getList("queryJisoPartGroupPage", model, page);
	}

	/**
	 * @Description: 厂外同步推算控制台-启停推算 
	 * @param: @param pubPlanCodeModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月12日
	 */
	@Override
	public int updateJisoExecState(PubPlanCodeModel pubPlanCodeModel) {
		return this.updateByKey("updateJisoExecState", pubPlanCodeModel);
	}

	/**
	 * @Description: 厂外同步推算控制台-手工组票
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月13日
	 */
	@Override
	public void insertBatchManuDealBill(List<JisoInsManuDealModel> list) {
		this.insertBatchByKey("insertBatchManuDealBill", list);
	}

	/**
	 * @Description: 查询未组票零件净需求 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoNetReqModel>   
	 * @author: dtp
	 * @date: 2018年9月13日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoNetReqModel> queryRemainByPartgroupNo(JisoNetReqModel model, DefaultPage page) {
		return (PageList<JisoNetReqModel>) this.getList("queryRemainByPartgroupNo", model, page);
	}

	/**
	 * @Description: 根据信息点获取推算状态 
	 * @param: @param model
	 * @param: @return    
	 * @return: PubPlanCodeModel   
	 * @author: dtp
	 * @date: 2018年9月13日
	 */
	@Override
	public PubPlanCodeModel queryReckonState(PubPlanCodeModel model) {
		return (PubPlanCodeModel) this.getOne("queryReckonState", model);
	}

	/**
	 * @Description: 待组单指示票-查询组单信息  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoOrderManuDealModel>   
	 * @author: dtp
	 * @date: 2018年9月14日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoInsModel> queryJisoGroupOrderPage(JisoInsModel model, DefaultPage page) {
		return (PageList<JisoInsModel>) this.getList("queryJisoGroupOrderPage", model, page);
	}

	/**
	 * @Description: 待组单指示票-查询未组单指示票列表     
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月15日o
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoInsModel> queryNotGroupBillPage(JisoInsModel model, DefaultPage page) {
		return (PageList<JisoInsModel>) this.getList("queryNotGroupBillPage", model, page);
	}

	/**
	 * @Description: 厂外同步推算控制台-查询过点车序导出  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月15日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoVehQueueModel> queryJisoVehQueueList(JisoVehQueueModel model) {
		return (List<JisoVehQueueModel>) this.getList("queryJisoVehQueuePage", model);
	}

	/**
	 * @Description: 获取厂外同步零件组下拉框 
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年10月2日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoInsModel> queryPartgroupComboData() {
		return (List<JisoInsModel>) this.getList("queryPartgroupComboData", new JisoInsModel());
	}

	/**
	 * @Description: 手工组单 
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月2日
	 */
	@Override
	public void updateManuDealOrder(List<JisoOrderManuDealModel> list) {
		this.updateByKey("updateManuDealOrderList", list);
	}

	/**
	 * @Description: 手工组单
	 * @param: @param jisoOrderManuDealModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月3日
	 */
	@Override
	public void updateManuDealOrder(JisoOrderManuDealModel model) {
		this.updateByKey("updateManuDealOrder", model);
	}

	/**
	 * @Description: 查询MM_JISO_INS_MANU_DEAL是否存在某零件组
	 * @param: @return    
	 * @return: List<JisoInsManuDealModel>   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoInsManuDealModel> queryJisoInsManuDealList(JisoInsManuDealModel model) {
		return (List<JisoInsManuDealModel>) this.getList("queryJisoInsManuDealList", model);
	}

	/**
	 * @Description: 手工组单更新MM_JISO_INS_MANU_DEAL
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	@Override
	public void updateManuDealBill(JisoInsManuDealModel model) {
		this.updateByKey("updateManuDealBill", model);
	}

	/**
	 * @Description: 手工组单MM_JISO_ORDER_MANU_DEAL 
	 * @param: @param jisoOrderManuDealModel
	 * @param: @return    
	 * @return: List<JisoOrderManuDealModel>   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoOrderManuDealModel> queryJisoOrderManuDealList(JisoOrderManuDealModel model) {
		return (List<JisoOrderManuDealModel>) this.getList("queryJisoOrderManuDealList", model);
	}

	/**
	 * @Description: 手工组单,存在更新,不存在新增   
	 * @param: @param jisoOrderManuDealModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	@Override
	public void insertManuDealOrder(JisoOrderManuDealModel jisoOrderManuDealModel) {
		this.insertByKey("insertManuDealOrder", jisoOrderManuDealModel);
	}



}
