package com.hanthink.sps.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.sps.dao.SpsInsDao;
import com.hanthink.sps.manager.SpsInsManager;
import com.hanthink.sps.model.SpsInsDetailModel;
import com.hanthink.sps.model.SpsInsModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: SpsInsManagerImpl
 * @Description: SPS分拣指示票
 * @author dtp
 * @date 2018年10月16日
 */
@Service("SpsInsManager")
public class SpsInsManagerImpl extends AbstractManagerImpl<String, SpsInsModel> implements SpsInsManager{
	
	@Resource
	private SpsInsDao spsInsDao;
	
	@Override
	protected Dao<String, SpsInsModel> getDao() {
		return spsInsDao;
	}

	/**
	 * @Description: SPS分拣指示票查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@Override
	public PageList<SpsInsModel> querySpsInsPage(SpsInsModel model, DefaultPage page) {
		return spsInsDao.querySpsInsPage(model, page);
	}

	/**
	 * @Description: SPS分拣指示票查询(导出excel)
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@Override
	public List<SpsInsModel> querySpsInsList(SpsInsModel model) {
		return spsInsDao.querySpsInsList(model);
	}

	/**
	 * @Description: SPS指示票明细查询
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	@Override
	public List<SpsInsModel> querySpsInsDetailPage(SpsInsModel model) {
		return spsInsDao.querySpsInsDetailPage(model);
	}

	/**
	 * @Description: 加载打印机下拉框 
	 * @param: @return    
	 * @return: List<PubDataDictModel>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	@Override
	public List<PubDataDictModel> loadPrinterComboData() {
		return spsInsDao.loadPrinterComboData();
	}

	/**
	 * @Description: SPS指示票明细查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsInsDetailModel>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	@Override
	public PageList<SpsInsDetailModel> querySpsInsDetailPage(SpsInsDetailModel model, DefaultPage page) {
		return spsInsDao.querySpsInsDetailPage(model, page);
	}

	/**
	 * @Description: 查询SPS指示票打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<SpsInsDetailModel>   
	 * @author: dtp
	 * @date: 2018年12月10日
	 */
	@Override
	public List<SpsInsDetailModel> querySpsInsDetailList(SpsInsModel model_q) {
		return spsInsDao.querySpsInsDetailList(model_q);
	}

	/**
	 * @Description: 加载打印机下拉框 (根据打印机分组)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubDataDictModel>   
	 * @author: dtp
	 * @date: 2018年12月12日
	 */
	@Override
	public List<PubDataDictModel> loadPrinterComboDataByType(PubDataDictModel model) {
		return spsInsDao.loadPrinterComboDataByType(model);
	}

	/**
	 * @Description: 更新sps指示票打印状态
	 * @param: @param List<SpsInsModel>
	 * @param: @return    
	 * @author: dtp
	 * @date: 2018年12月12日
	 */
	@Override
	public void updatePrintInfo(List<SpsInsModel> list_printInfo) {  
		for (SpsInsModel spsInsModel : list_printInfo) {
			spsInsDao.updatePrintInfoModel(spsInsModel);
		}
		
	}

	/**
	 * @Description: 修改SPS指示票打印状态  
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月29日
	 */
	@Override
	public void updateSpsInsPrintStatus(SpsInsModel[] models, String ipAddr) {
		for (int i = 0; i < models.length; i++) {
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("SPS指示票修改打印状态");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_SPS_INS");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("INS_NO");
			pkColumnVO.setColumnVal(models[i].getInsNo());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			spsInsDao.updateSpsInsPrintStatus(models[i]);
		}
		
	}

}
