package com.hanthink.sps.manager;

import java.util.List;

import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.sps.model.SpsInsDetailModel;
import com.hanthink.sps.model.SpsInsModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: SpsInsManager
 * @Description: SPS分拣指示票
 * @author dtp
 * @date 2018年10月16日
 */
public interface SpsInsManager extends Manager<String, SpsInsModel> {

	/**
	 * @Description: SPS分拣指示票查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	PageList<SpsInsModel> querySpsInsPage(SpsInsModel model, DefaultPage page);

	/**
	 * @Description: SPS分拣指示票查询(导出excel)
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	List<SpsInsModel> querySpsInsList(SpsInsModel model);

	/**
	 * @Description: SPS指示票明细查询
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsInsModel>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	List<SpsInsModel> querySpsInsDetailPage(SpsInsModel model);

	/**
	 * @Description: 加载打印机下拉框 
	 * @param: @return    
	 * @return: List<PubDataDictModel>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	List<PubDataDictModel> loadPrinterComboData();

	/**
	 * @Description: SPS指示票明细查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsInsDetailModel>   
	 * @author: dtp
	 * @date: 2018年10月19日
	 */
	PageList<SpsInsDetailModel> querySpsInsDetailPage(SpsInsDetailModel model, DefaultPage page);

	/**
	 * @Description: 查询SPS指示票打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<SpsInsDetailModel>   
	 * @author: dtp
	 * @date: 2018年12月10日
	 */
	List<SpsInsDetailModel> querySpsInsDetailList(SpsInsModel model_q); 

	/**
	 * @Description: 加载打印机下拉框 (根据打印机分组)  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubDataDictModel>   
	 * @author: dtp
	 * @date: 2018年12月12日
	 */
	List<PubDataDictModel> loadPrinterComboDataByType(PubDataDictModel model);

	/**
	 * @Description: 更新sps指示票打印状态
	 * @param: @param List<SpsInsModel>
	 * @param: @return    
	 * @author: dtp
	 * @date: 2018年12月12日
	 */
	void updatePrintInfo(List<SpsInsModel> list_printInfo);

	/**
	 * @Description: 修改SPS指示票打印状态    
	 * @param: @param models
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月29日
	 */
	void updateSpsInsPrintStatus(SpsInsModel[] models, String ipAddr);

}
