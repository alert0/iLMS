package com.hanthink.jiso.manager;

import java.util.List;

import com.hanthink.jiso.model.JisoInsManuDealModel;
import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoNetReqModel;
import com.hanthink.jiso.model.JisoOrderManuDealModel;
import com.hanthink.jiso.model.JisoPartgroupModel;
import com.hanthink.jiso.model.JisoVehQueueModel;
import com.hanthink.jit.model.JitInsModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: JisoReckonManager
 * @Description: 厂外同步推算控制台
 * @author dtp
 * @date 2018年9月11日
 */
public interface JisoReckonManager extends Manager<String, JisoVehQueueModel>{

	/**
	 * @Description: 厂外同步推算控制台-查询过点车序  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月15日
	 */
	public PageList<JisoVehQueueModel> queryJisoVehQueuePage(JisoVehQueueModel model, DefaultPage page);

	/**
	 * @Description: 查询待组票净需求-零件组列表   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: List<JisoPartgroupModel>   
	 * @author: dtp
	 * @date: 2018年9月12日
	 */
	public List<JisoPartgroupModel> queryJisoPartGroupPage(JisoPartgroupModel model, DefaultPage page);

	/**
	 * @Description: 厂外同步推算控制台-启停推算 
	 * @param: @param pubPlanCodeModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月12日
	 */
	public int updateJisoExecState(PubPlanCodeModel pubPlanCodeModel, String ipAddr);

	/**
	 * 
	 * @Description: 厂外同步推算控制台-手工组票
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月13日
	 */
	public void insertBatchManuDealBill(List<JisoInsManuDealModel> list, String ipAddr);

	/**
	 * @Description: 查询未组票零件净需求 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoNetReqModel>   
	 * @author: dtp
	 * @date: 2018年9月13日
	 */
	public PageList<JisoNetReqModel> queryRemainByPartgroupNo(JisoNetReqModel model, DefaultPage page);

	/**
	 * @Description: 根据信息点获取推算状态 
	 * @param: @param model
	 * @param: @return    
	 * @return: PubPlanCodeModel   
	 * @author: dtp
	 * @date: 2018年9月13日
	 */
	public PubPlanCodeModel queryReckonState(PubPlanCodeModel model);

	/**
	 * @Description: 待组单指示票-查询组单信息    
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoOrderManuDealModel>   
	 * @author: dtp
	 * @date: 2018年9月14日
	 */
	public PageList<JisoInsModel> queryJisoGroupOrderPage(JisoInsModel model, DefaultPage page);

	/**
	 * @Description: 待组单指示票-查询未组单指示票列表     
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月15日
	 */
	public PageList<JisoInsModel> queryNotGroupBillPage(JisoInsModel model, DefaultPage page);

	/**
	 * @Description: 厂外同步推算控制台-查询过点车序导出  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月15日
	 */
	public List<JisoVehQueueModel> queryJisoVehQueueList(JisoVehQueueModel model);

	/**
	 * @Description: 获取厂外同步零件组下拉框 
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年10月2日
	 */
	public List<JisoInsModel> queryPartgroupComboData();

	/**
	 * @Description: 手工组单   
	 * @param: @param list
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月24日
	 */
	public void updateManuDealOrder(List<JisoOrderManuDealModel> list, String ipAddr);

	/**
	 * @Description: 手工组票
	 * @param: @param list
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月13日
	 */
	public void manuDealBillFn(List<JisoInsManuDealModel> list, String ipAddr);

	
}
