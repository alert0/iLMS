package com.hanthink.jiso.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jiso.dao.JisoReckonDao;
import com.hanthink.jiso.manager.JisoReckonManager;
import com.hanthink.jiso.model.JisoInsManuDealModel;
import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoNetReqModel;
import com.hanthink.jiso.model.JisoOrderManuDealModel;
import com.hanthink.jiso.model.JisoPartgroupModel;
import com.hanthink.jiso.model.JisoVehQueueModel;
import com.hanthink.jit.model.JitInsModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: JisoReckonManagerImpl
 * @Description: 厂外同步推算控制台
 * @author dtp
 * @date 2018年9月11日
 */
@Service("JisoReckonManager")
public class JisoReckonManagerImpl extends AbstractManagerImpl<String, JisoVehQueueModel> implements JisoReckonManager{

	@Resource
	private JisoReckonDao jisoReckonDao;
	
	@Override
	protected Dao<String, JisoVehQueueModel> getDao() {
		return jisoReckonDao;
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
	@Override
	public PageList<JisoVehQueueModel> queryJisoVehQueuePage(JisoVehQueueModel model, DefaultPage page) {
		return jisoReckonDao.queryJisoVehQueuePage(model, page);
	}

	/**
	 * @Description: 查询待组票净需求-零件组列表   
	 * @param: @param model
	 * @param: @param p
	 * @param: @return    
	 * @return: List<JisoPartgroupModel>   
	 * @author: dtp
	 * @date: 2018年9月12日
	 */
	@Override
	public List<JisoPartgroupModel> queryJisoPartGroupPage(JisoPartgroupModel model, DefaultPage page) {
		return jisoReckonDao.queryJisoPartGroupPage(model, page);
	}

	/**
	 * @Description: 厂外同步推算控制台-启停推算 
	 * @param: @param pubPlanCodeModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月12日
	 */
	@Override
	public int updateJisoExecState(PubPlanCodeModel pubPlanCodeModel, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("更新厂外同步推算状态");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_PLAN_CODE");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PLAN_CODE");
		pkColumnVO.setColumnVal("A_JISO_PAOFF");
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		return jisoReckonDao.updateJisoExecState(pubPlanCodeModel);
	}

	/**
	 * @Description: 厂外同步推算控制台-手工组票
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月13日
	 */
	@Override
	public void insertBatchManuDealBill(List<JisoInsManuDealModel> list, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		TableColumnVO pkColumnVO = new TableColumnVO();
		for (int i = 0; i < list.size(); i++) {
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("手工组票");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_INSERT);
			logVO.setTableName("MM_JISO_INS_MANU_DEAL");
			pkColumnVO.setColumnName("PLAN_CODE");
			pkColumnVO.setColumnVal(list.get(i).getPlanCode());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		}
		jisoReckonDao.insertBatchManuDealBill(list);
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
	@Override
	public PageList<JisoNetReqModel> queryRemainByPartgroupNo(JisoNetReqModel model, DefaultPage page) {
		return jisoReckonDao.queryRemainByPartgroupNo(model, page);
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
		return jisoReckonDao.queryReckonState(model);
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
	@Override
	public PageList<JisoInsModel> queryJisoGroupOrderPage(JisoInsModel model, DefaultPage page) {
		return jisoReckonDao.queryJisoGroupOrderPage(model, page);
	}

	/**
	 * @Description: 待组单指示票-查询未组单指示票列表     
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月15日
	 */
	@Override
	public PageList<JisoInsModel> queryNotGroupBillPage(JisoInsModel model, DefaultPage page) {
		return jisoReckonDao.queryNotGroupBillPage(model, page);
	}

	/**
	 * @Description: 厂外同步推算控制台-查询过点车序导出  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月15日
	 */
	@Override
	public List<JisoVehQueueModel> queryJisoVehQueueList(JisoVehQueueModel model) {
		return jisoReckonDao.queryJisoVehQueueList(model);
	}

	/**
	 * @Description: 获取厂外同步零件组下拉框 
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年10月2日
	 */
	@Override
	public List<JisoInsModel> queryPartgroupComboData() {
		return jisoReckonDao.queryPartgroupComboData();
	}

	/**
	 * @Description: 手工组单 
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月2日
	 */
	@Override
	public void updateManuDealOrder(List<JisoOrderManuDealModel> list, String ipAddr) {
		//手工组单,,存在更新,不存在,新增MM_JISO_ORDER_MANU_DEAL
		for (JisoOrderManuDealModel jisoOrderManuDealModel : list) {
			List<JisoOrderManuDealModel> jomdList = jisoReckonDao.queryJisoOrderManuDealList(jisoOrderManuDealModel);
			if(null != jomdList && jomdList.size() > 0) {
				/*TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr); 
				logVO.setFromName("手工组单");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
				logVO.setTableName("MM_JISO_ORDER_MANU_DEAL");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("PLAN_CODE");
				pkColumnVO.setColumnVal(jisoOrderManuDealModel.getPlanCode());
				this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);*/
				jisoReckonDao.updateManuDealOrder(jisoOrderManuDealModel);
			}else if(null != jomdList && jomdList.size() == 0 ) {
				/*TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr); 
				logVO.setFromName("手工组单");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_INSERT);
				logVO.setTableName("MM_JISO_ORDER_MANU_DEAL");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("PLAN_CODE");
				pkColumnVO.setColumnVal(jisoOrderManuDealModel.getPlanCode());
				this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);*/
				jisoReckonDao.insertManuDealOrder(jisoOrderManuDealModel);
			}
			
		}
		
	}

	/**
	 * @Description: 手工组票
	 * @param: @param list
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月13日
	 */
	@Override
	public void manuDealBillFn(List<JisoInsManuDealModel> list, String ipAddr) {
		//厂外同步指示票,判断零件组是否存在,有则更新,无则新增
		//批量新增list
		//List<JisoInsManuDealModel> batchManuDealBillList = new ArrayList<JisoInsManuDealModel>();
		for (JisoInsManuDealModel model : list) {
			List<JisoInsManuDealModel> jimdList = jisoReckonDao.queryJisoInsManuDealList(model);
			//存在,更新MM_JISO_INS_MANU_DEAL表 
			if(null != jimdList && jimdList.size() > 0) {
				TableOpeLogVO logVO = new TableOpeLogVO();
				TableColumnVO pkColumnVO = new TableColumnVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr); 
				logVO.setFromName("手工组票");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
				logVO.setTableName("MM_JISO_INS_MANU_DEAL");
				pkColumnVO.setColumnName("PLAN_CODE");
				pkColumnVO.setColumnVal(model.getPlanCode());
				this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
				//更新
				jisoReckonDao.updateManuDealBill(model);
			}else if(null != jimdList && jimdList.size() == 0) {
				List<JisoInsManuDealModel> batchManuDealBillList = new ArrayList<JisoInsManuDealModel>();
				TableOpeLogVO logVO = new TableOpeLogVO();
				TableColumnVO pkColumnVO = new TableColumnVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr); 
				logVO.setFromName("手工组票");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_INSERT);
				logVO.setTableName("MM_JISO_INS_MANU_DEAL");
				pkColumnVO.setColumnName("PLAN_CODE");
				pkColumnVO.setColumnVal(model.getPlanCode());
				this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
				batchManuDealBillList.add(model);
				jisoReckonDao.insertBatchManuDealBill(batchManuDealBillList);
			}
		}
		
	}


}
