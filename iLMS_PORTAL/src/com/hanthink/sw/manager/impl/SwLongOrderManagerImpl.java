package com.hanthink.sw.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sw.dao.SwLogictiscOrderDao;
import com.hanthink.sw.dao.SwLongOrderDao;
import com.hanthink.sw.manager.SwLongOrderManager;
import com.hanthink.sw.manager.SwMaterialOrderManager;
import com.hanthink.sw.model.SwDemondOrderDetailModel;
import com.hanthink.sw.model.SwLongOrderModel;
import com.hanthink.sw.model.SwMaterialOrderModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("SwLongOrderManager")
public class SwLongOrderManagerImpl extends AbstractManagerImpl<String, SwLongOrderModel> 
implements SwLongOrderManager{

	@Resource
	private SwLongOrderDao dao;
	@Override
	protected Dao<String, SwLongOrderModel> getDao() {
		
		return dao;
	}
	
	@Override
	public PageList<SwLongOrderModel> queryLongOrderPage(SwLongOrderModel model, DefaultPage p) {
		
		return dao.queryLongOrderPage(model,p);
	}

	@Override
	public List<SwLongOrderModel> queryLongOrderDownload(SwLongOrderModel model) {
		
		return dao.queryLongOrderDownload(model);
	}

	@Override
	public List<SwLongOrderModel> downloadLongOrderByChoose(List<Map<String, String>> paramList) {
		
		return dao.downloadLongOrderByChoose(paramList);
	}

	@Override
	public List<SwLongOrderModel> queryLongOrderDetailList(SwLongOrderModel model_q) {
		
		return dao.queryLongOrderDetailList(model_q);
	}

	@Override
	public void updateDelivery(SwLongOrderModel m, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SW_LONG_ORDER");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(m.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dao.updateDelivery(m);
	}

	@Override
	public List<SwLongOrderModel> queryLongOrderPrintLabelList(SwLongOrderModel model) {
		
		return dao.queryLongOrderPrintLabelList(model);
	}

	@Override
	public List<SwLongOrderModel> longOrderPrintTpLabel(SwLongOrderModel model_q) {
		
		return dao.longOrderPrintTpLabel(model_q);
	}

	@Override
	public List<SwLongOrderModel> getVersion() {
		
		return dao.getVersion();
	}

	@Override
	public void updateFeedbackLongOrder(List<SwLongOrderModel> list, SwLongOrderModel model) {
//		dao.updateFeedbackLongOrder(list,model);
		for (SwLongOrderModel swLongOrderModel : list) {
			model.setVersion(swLongOrderModel.getVersion());
			model.setOrderNo(swLongOrderModel.getOrderNo());
			model.setPartNo(swLongOrderModel.getPartNo());
			dao.updateFeedbackLongOrder(model);
		}
	}

	@Override
	public void deleteLongOrder(SwLongOrderModel model, String ipAddr) {


//		TableOpeLogVO logVO = new TableOpeLogVO();
//		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
//		logVO.setOpeIp(ipAddr); 
//		logVO.setFromName("界面更新");
//		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
//		logVO.setTableName("MM_SW_LONG_ORDER");
//		TableColumnVO pkColumnVO = new TableColumnVO();
//		pkColumnVO.setColumnName("VERSION");
//		pkColumnVO.setColumnVal(model.getVersion());
//		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
//		
//		dao.deleteLongOrder(model); //删除明细
//		dao.deleteLong(model);  //删除主表
		
	}

	@Override
	public void updateLongReceiveQty(SwLongOrderModel model, String ipAddr) {
		
		dao.updateLongReceiveQty(model);  
	}

	@Override
	public SwLongOrderModel getDefaultVersion(Map<String, String> map) {
		
		return dao.getDefaultVersion(map);
	}

}
