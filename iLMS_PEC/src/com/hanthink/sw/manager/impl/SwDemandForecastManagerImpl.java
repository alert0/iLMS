package com.hanthink.sw.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwDemandForecastDao;
import com.hanthink.sw.manager.SwDemandForecastManager;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/***
 * 
* <p>Title: SwAnnViewManagerImpl</p>  
* <p>Description: 公告查看</p>  
* @author luoxq  
* @date 2018年10月16日 下午4:20:05
 */
@Service("SwDemandForecastManager")
public class SwDemandForecastManagerImpl extends AbstractManagerImpl<String, SwDemandForecastModel>
implements SwDemandForecastManager{

	@Resource SwDemandForecastDao dao;
	
	@Override
	protected Dao<String, SwDemandForecastModel> getDao() {
		
		return dao;
	}

	/**
	 * 
	 * <p>Title: queryJisoDemandPage</p>  
	 * <p>Description: 预测数据界面分页查询</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午6:48:26
	 */
	@Override
	public PageList<SwDemandForecastModel> queryJisoDemandPage(SwDemandForecastModel model, DefaultPage p) {
		
		return dao.queryJisoDemandPage(model,p);
	}

	/**
	 * 
	 * <p>Title: publishDemandForecast</p>  
	 * <p>Description: 预测数据界面，发布功能</p>  
	 * @param ids  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午2:46:14
	 */
	@Override
	public void publishDemandForecast(SwDemandForecastModel model) {
			dao.publishDemandForecast(model);
	}

	/**
	 * 
	 * <p>Title: feedbackDemandForecast</p>  
	 * <p>Description: 预测数据界面，反馈功能</p>  
	 * @param ids  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午3:02:56
	 */
	@Override
	public void feedbackDemandForecast(String[] ids, SwDemandForecastModel model) {
			dao.feedbackDemandForecast(ids,model);
	}

	/**
	 * 
	 * <p>Title: querySwDemandForecastByKey</p>  
	 * <p>Description: 预测数据界面，导出功能</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午3:29:49
	 */
	@Override
	public List<SwDemandForecastModel> querySwDemandForecastByKey(SwDemandForecastModel model) {
		
		return dao.querySwDemandForecastByKey(model);
	}

	/**
	 * 分页查询需求预测数据导出的数据
	 * @param startMonthStr 开始月份 如 2019-05 不能为空
	 * @param endMonthStr 结束月份 如2019-07 允许为空，为空时默认三个月区间
	 * @param supplierNo 供应商代码 允许为空
	 * @param p 分页对象
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月12日 下午3:26:52
	 */
	@Override
	public PageList<Map<String, Object>> queryDemandForecastExportDataByPage(String startMonthStr, String endMonthStr,
			SwDemandForecastModel model, DefaultPage p) {
		return dao.queryDemandForecastExportDataByPage(startMonthStr, endMonthStr, model, p);
	}

	/**
	 * 
	 * @Description: 获取查询条件并进行判断
	 * @param @param model
	 * @param @return   
	 * @return SwDemandForecastModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月13日 上午10:23:44
	 */
	@Override
	public SwDemandForecastModel getUserType(SwDemandForecastModel model) {
		
		return dao.getUserType(model);
	}

	/**
	 * 
	 * @Description: 获取搜索框发布版本下拉框值
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月25日 上午9:10:54
	 */
	@Override
	public List<SwDemandForecastModel> getVersion(Map<String, String> map) {
		
		return dao.getVersion(map);
	}

	@Override
	public List<SwDemandForecastModel> getDefaultVersion(Map<String, String> map) {
		
		return dao.getDefaultVersion(map);
	}

	@Override
	public PageList<SwDemandForecastModel> queryDemandWeekPage(SwDemandForecastModel model, DefaultPage p) {
		
		return dao.queryDemandWeekPage(model,p);
	}

	@Override
	public String getObjWeekByVersion(String version) {
		
		return dao.getObjWeekByVersion(version);
	}

	@Override
	public PageList<Map<String, Object>> queryDemandForecastWeekExportDataByPage(String startMonthStr,
			String endMonthStr, SwDemandForecastModel model, DefaultPage page) {
		
		return dao.queryDemandForecastWeekExportDataByPage(startMonthStr,endMonthStr,model,page);
	}

	@Override
	public SwDemandForecastModel getMinAndMaxDate(Map<String, Object> map) {
		
		return dao.getMinAndMaxDate(map);
	}

}
