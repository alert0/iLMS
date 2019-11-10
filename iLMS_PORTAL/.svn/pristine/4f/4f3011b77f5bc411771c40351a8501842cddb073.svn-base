package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwDemandForecastModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * 
* <p>Title: SwAnnViewManager</p>  
* <p>Description: 公告查看</p>  
* @author luoxq  
* @date 2018年10月16日 下午4:19:55
 */
public interface SwDemandForecastManager extends Manager<String, SwDemandForecastModel>{

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
	PageList<SwDemandForecastModel> queryJisoDemandPage(SwDemandForecastModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: publishDemandForecast</p>  
	 * <p>Description: 预测数据界面，发布功能</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午2:46:14
	 */
	void publishDemandForecast(SwDemandForecastModel model);

	/**
	 * 
	 * <p>Title: feedbackDemandForecast</p>  
	 * <p>Description: 预测数据界面，反馈功能</p>  
	 * @param ids  
	 * @param model 
	 * @authoer luoxq
	 * @time 2018年10月17日 下午3:02:56
	 */
	void feedbackDemandForecast(String[] ids, SwDemandForecastModel model);

	/**
	 * 
	 * <p>Title: querySwDemandForecastByKey</p>  
	 * <p>Description: 预测数据界面，导出功能</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午3:29:49
	 */
	List<SwDemandForecastModel> querySwDemandForecastByKey(SwDemandForecastModel model);

	/**
	 * 分页查询需求预测数据导出的数据
	 * @param startMonthStr 开始月份 如 2019-05 不能为空
	 * @param endMonthStr 结束月份 如2019-07 允许为空，为空时默认三个月区间
	 * @param supplierNo 供应商代码 允许为空
	 * @param p 分页对象
	 * @return
	 * @author ZUOSL	
	 * @param releaseStatus 
	 * @DATE	2018年11月12日 下午5:25:57
	 */
	PageList<Map<String, Object>> queryDemandForecastExportDataByPage(String startMonthStr, String endMonthStr,
			SwDemandForecastModel model, DefaultPage p);

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
	SwDemandForecastModel getUserType(SwDemandForecastModel model);

	/**
	 * 
	 * @param map 
	 * @Description: 获取搜索框发布版本下拉框值
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月25日 上午9:10:54
	 */
	List<SwDemandForecastModel> getVersion(Map<String, String> map);

	/**
	 * 
	 * @param map 
	 * @Description: 获取默认查询条件默认版本号
	 * @param @return   
	 * @return List<SwDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月25日 上午11:51:20
	 */
	List<SwDemandForecastModel> getDefaultVersion(Map<String, String> map);

	/**
	 * 
	 * @Description: 分页查询周度预测数据
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<SwDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月20日 下午3:26:53
	 */
	PageList<SwDemandForecastModel> queryDemandWeekPage(SwDemandForecastModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 通过版本获取对象周
	 * @param @param version
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月20日 下午10:03:01
	 */
	String getObjWeekByVersion(String version);

	/**
	 * 
	 * @Description: 查询出周预测中需要导出的数据
	 * @param @param startMonthStr
	 * @param @param endMonthStr
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<Map<String,Object>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月20日 下午11:42:14
	 */
	PageList<Map<String, Object>> queryDemandForecastWeekExportDataByPage(String startMonthStr, String endMonthStr,
			SwDemandForecastModel model, DefaultPage page);

	/**
	 * 
	 * @Description: 获取erp下发数据中的最大日期和最小日期
	 * @param @param version
	 * @param @return   
	 * @return SwDemandForecastModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月27日 下午3:12:43
	 */
	SwDemandForecastModel getMinAndMaxDate(Map<String, Object> map);

}
