package com.hanthink.sps.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.sps.dao.SpsInsDao;
import com.hanthink.sps.model.SpsInsDetailModel;
import com.hanthink.sps.model.SpsInsModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsInsDaoImpl
 * @Description: SPS分拣指示票
 * @author dtp
 * @date 2018年10月16日
 */
@Repository
public class SpsInsDaoImpl extends MyBatisDaoImpl<String, SpsInsModel> implements SpsInsDao{

	@Override
	public String getNamespace() {
		return SpsInsModel.class.getName();
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
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsInsModel> querySpsInsPage(SpsInsModel model, DefaultPage page) {
		return (PageList<SpsInsModel>) this.getList("querySpsInsPage", model, page);
	}

	/**
	 * @Description: SPS分拣指示票查询(导出excel)
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsInsModel> querySpsInsList(SpsInsModel model) {
		return (List<SpsInsModel>) this.getList("querySpsInsPage", model);
	}

	/**
	 * @Description: SPS指示票明细查询
	 * @param: @param model
	 * @param: @return    
	 * @return: PageList<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsInsModel> querySpsInsDetailPage(SpsInsModel model) {
		return (List<SpsInsModel>) this.getList("querySpsInsDetailPage", model);
	}

	/**
	 * @Description: 加载打印机下拉框 
	 * @param: @return    
	 * @return: List<PubDataDictModel>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubDataDictModel> loadPrinterComboData() {
		return (List<PubDataDictModel>) this.getList("loadPrinterComboData", new PubDataDictModel());
	}

	/**
	 * @Description: SPS指示票明细查询 ----DELETE
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsInsDetailModel> querySpsInsDetailPage(SpsInsDetailModel model, DefaultPage page) {
		return (PageList<SpsInsDetailModel>) this.getList("querySpsInsDetailPage", model, page);
	}

	/**
	 * @Description: 查询SPS指示票打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<SpsInsDetailModel>   
	 * @author: dtp
	 * @date: 2018年12月10日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsInsDetailModel> querySpsInsDetailList(SpsInsModel model_q) {
		return (List<SpsInsDetailModel>) this.getList("querySpsInsDetailList", model_q);
	}

	/**
	 * @Description: 加载打印机下拉框 (根据打印机分组)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubDataDictModel>   
	 * @author: dtp
	 * @date: 2018年12月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubDataDictModel> loadPrinterComboDataByType(PubDataDictModel model) {
		return (List<PubDataDictModel>) this.getList("loadPrinterComboData", model);
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
		this.updateByKey("updatePrintInfo", list_printInfo);
	}

	/**
	 * @Description: 修改SPS指示票打印状态
	 * @param: @param spsInsModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月29日
	 */
	@Override
	public void updateSpsInsPrintStatus(SpsInsModel spsInsModel) {
		this.updateByKey("updateSpsInsPrintStatus", spsInsModel);
	}

	/**
	 * @Description: 修改打印状态 
	 * @param: @param spsInsModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月26日
	 */
	@Override
	public void updatePrintInfoModel(SpsInsModel spsInsModel) {
		this.updateByKey("updatePrintInfoModel", spsInsModel);
	}


}
