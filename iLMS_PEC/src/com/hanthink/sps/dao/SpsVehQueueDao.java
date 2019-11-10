package com.hanthink.sps.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hanthink.sps.model.SpsInsDetailModel;
import com.hanthink.sps.model.SpsInsModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hanthink.sps.model.SpsVehQueueModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsVehQueueDao
 * @Description: SPS过点车序查询
 * @author dtp
 * @date 2018年10月16日
 */
public interface SpsVehQueueDao extends Dao<String, SpsVehQueueModel>{
	
	/**
	 * @Description: SPS过点车序查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	PageList<SpsVehQueueModel> querySpsVehQueuePage(SpsVehQueueModel model, DefaultPage page);

	/**
	 * @Description: SPS过点车序查询(导出excel)
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	List<SpsVehQueueModel> querySpsVehQueueList(SpsVehQueueModel model);

	/***
	 * @Description:  加载SPS信息点下拉框      
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	List<PubPlanCodeModel> loadPlanCodeComboData(PubPlanCodeModel model);

	/**
	 * 加载票据模板下拉框
	 * @param model
	 * @return
	 */
	List<PubDataDictModel> loadSpsMouldComboData(SpsVehQueueModel model);

	/**
	 * @Description: 打印信息写入临时表  
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	Object insertTestInsTemp(List<SpsVehQueueModel> list);

	/**
	 * @Description:  生成sps指示票
	 * @param: @param map    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	void createSpsTestIns(Map<String, String> map);

	/**
	 * @Description: 根据uuid查询试打SPS指示票
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsInsDetailModel>   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	List<SpsInsModel> querySpsTestPrintInsNoList(SpsVehQueueModel model);

	/**
	 * @Description: 查询打印模板   
	 * @param: @param testPrintModel
	 * @param: @return    
	 * @return: SpsMouldModel   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	SpsMouldModel queryModelCode(SpsVehQueueModel testPrintModel);

	/**
	 * @Description:  //查询分拣单最大位置号,判断是否分页打印 
	 * @param: @param modelCodeModel
	 * @param: @return    
	 * @return: SpsMouldModel   
	 * @author: dtp
	 * @date: 2019年2月26日
	 */
	SpsMouldModel queryMaxPlaceByModelCode(SpsMouldModel modelCodeModel);

}
