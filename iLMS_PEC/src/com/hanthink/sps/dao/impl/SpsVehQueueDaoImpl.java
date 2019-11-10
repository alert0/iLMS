package com.hanthink.sps.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hanthink.sps.dao.SpsVehQueueDao;
import com.hanthink.sps.model.SpsInsModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hanthink.sps.model.SpsVehQueueModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsVehQueueDaoImpl
 * @Description: SPS过点车序查询
 * @author dtp
 * @date 2018年10月16日
 */
@Repository
public class SpsVehQueueDaoImpl extends MyBatisDaoImpl<String, SpsVehQueueModel> implements SpsVehQueueDao{

	@Override
	public String getNamespace() {
		return SpsVehQueueModel.class.getName();
	}

	/**
	 * @Description: SPS过点车序查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsVehQueueModel> querySpsVehQueuePage(SpsVehQueueModel model, DefaultPage page) {
		return (PageList<SpsVehQueueModel>) this.getList("querySpsVehQueuePage", model, page);
	}

	/**
	 * @Description: SPS过点车序查询(导出excel)
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsVehQueueModel> querySpsVehQueueList(SpsVehQueueModel model) {
		return (List<SpsVehQueueModel>) this.getList("querySpsVehQueuePage", model);
	}

	/***
	 * @Description:  加载SPS信息点下拉框      
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubPlanCodeModel> loadPlanCodeComboData(PubPlanCodeModel model) {
		return (List<PubPlanCodeModel>) this.getList("loadPlanCodeComboData", model);
	}

	/**
	 * 加载票据模板下拉框
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubDataDictModel> loadSpsMouldComboData(SpsVehQueueModel model) {
		return (List<PubDataDictModel>) this.getList("loadSpsMouldComboData", model);
	}

	/**
	 * @Description: 打印信息写入临时表  
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	@Override
	public Object insertTestInsTemp(List<SpsVehQueueModel> list) {
		return this.insertBatchByKey("insertTestInsTemp", list);
	}

	/**
	 * @Description:  生成sps指示票
	 * @param: @param map    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	@Override
	public void createSpsTestIns(Map<String, String> paramMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".createSpsTestIns", paramMap);
	}

	/**
	 * @Description: 根据uuid查询试打SPS指示票
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsInsDetailModel>   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsInsModel> querySpsTestPrintInsNoList(SpsVehQueueModel model) {
		return (List<SpsInsModel>) this.getList("querySpsTestPrintInsNoList", model);
	}

	/**
	 * @Description: 查询打印模板   
	 * @param: @param testPrintModel
	 * @param: @return    
	 * @return: SpsMouldModel   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SpsMouldModel queryModelCode(SpsVehQueueModel testPrintModel) {
		List<SpsMouldModel> list = this.getListByKey("queryModelCode", testPrintModel);
		if(null != list && list.size() == 1) {
			return list.get(0);
		}else {
			return null;
		}
	}

	/**
	 * @Description:  查询分拣单最大位置号,判断是否分页打印 
	 * @param: @param modelCodeModel
	 * @param: @return    
	 * @return: SpsMouldModel   
	 * @author: dtp
	 * @date: 2019年2月26日
	 */
	@Override
	public SpsMouldModel queryMaxPlaceByModelCode(SpsMouldModel modelCodeModel) {
		@SuppressWarnings("unchecked")
		List<SpsMouldModel> list = (List<SpsMouldModel>) this.getListByKey("queryMaxPlaceByModelCode", modelCodeModel);
		if(null != list && list.size() == 1) {
			return list.get(0);
		}else {
			return null;
		}
	}

}
