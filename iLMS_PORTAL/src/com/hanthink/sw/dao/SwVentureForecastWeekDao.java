package com.hanthink.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.sw.model.SwVentureForecastModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SwVentureForecastWeekDao extends Dao<String, SwVentureForecastModel>{

	/**
	 * 
	 * @Description: 删除临时表数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月2日 下午3:44:21
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @Description: Excel数据写入临时表
	 * @param @param importList   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月2日 下午3:45:38
	 */
	void insertImportTempData(List<SwVentureForecastModel> importList);

	/**
	 * 
	 * @Description: 调用存储校验导入临时表的数据
	 * @param @param ckParamMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月2日 下午3:46:04
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * 
	 * @Description: 查询是否可导入标识
	 * @param @param uuid
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月2日 下午3:51:59
	 */
	String queryIsImportFlag(String uuid);

	/**
	 * 
	 * @Description: 分页查询导入临时表数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 上午11:04:51
	 */
	PageList<SwVentureForecastModel> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 查询要导出数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 上午11:05:19
	 */
	List<SwVentureForecastModel> queryTempDataForExport(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 查询是否有可以插入正式表数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 上午11:05:39
	 */
	List<SwVentureForecastModel> queryForInsertList(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 数据插入正式表
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 上午11:06:31
	 */
	void insertImportData(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 导入正式表修改状态
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 上午11:06:51
	 */
	void updateVentureForecastImportDataImpStatus(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 分页查询合资车预测数据
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午3:39:57
	 */
	PageList<SwVentureForecastModel> queryVentureForePage(SwVentureForecastModel model, Page page);

	/**
	 * 
	 * @Description: 判断要删除的版本是否已经发布
	 * @param @param map
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午4:21:06
	 */
	List<SwVentureForecastModel> selectVentureByVersion(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 根据版本号删除数据
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午5:51:43
	 */
	void deleteVentureVersion(SwVentureForecastModel model);
	
	/**
	 * 
	 * @Description: 删除版本
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午9:44:56
	 */
	void deleteVentureVersionJv(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 导出合资车预测数据
	 * @param @param model
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午5:51:59
	 */
	List<SwVentureForecastModel> exportForExcelModel(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 合并发布
	 * @param @param model
	 * @param @return   
	 * @return int  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午6:39:47
	 */
	int insertReleaseVersion(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 根据目标版本号查询出相关数据
	 * @param @param model
	 * @param @return   
	 * @return SwVentureForecastModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午8:50:38
	 */
	SwVentureForecastModel selectDemandForecasetByTagVersion(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 判断要发布的版本是否已发布至目标版本
	 * @param @param model
	 * @param @return   
	 * @return int  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午9:09:55
	 */
	Integer selectIsReleaseVersion(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 获取订购版本号下拉框
	 * @param @param map
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午10:36:45
	 */
	List<SwVentureForecastModel> getJvVersion(Map<String, String> map);

	/**
	 * 
	 * @Description: 版本号写入MM_SW_FORECAST_VERSION_JV
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月16日 上午9:38:21
	 */
	void insertVersion(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 获取ERP版本号下拉框
	 * @param @param map
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月16日 下午4:01:00
	 */
	List<SwVentureForecastModel> getErpVersion(Map<String, String> map);

	/**
	 * 
	 * @Description: 订购方数据写入合并临时表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 上午11:51:42
	 */
	void insertReleaseImpJv(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: erp数据写入合并临时表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 上午11:52:15
	 */
	void insertReleaseImpErp(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 删除合并临时表数据
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午2:10:16
	 */
	void deleteReleaseImp(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 检索数据写入临时表，统计数量分页查询临时表数据
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午6:37:03
	 */
	PageList<SwVentureForecastModel> queryTotalQty(SwVentureForecastModel model, Page page);

	/**
	 * 
	 * @Description: 查询合并后导出数据
	 * @param @param startMonthStr
	 * @param @param endMonthStr
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<Map<String,Object>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午7:18:10
	 */
	PageList<Map<String, Object>> queryVentureForecastWeekExportDataByPage(String startMonthStr, String endMonthStr,
			SwVentureForecastModel model, DefaultPage page);

	/**
	 * 
	 * @Description: 获取erp预测数据中最大日期，最小日期，对象周
	 * @param @param map
	 * @param @return   
	 * @return SwVentureForecastModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午10:38:38
	 */
	SwVentureForecastModel getExportModeMsg(Map<String, Object> map);

	/**
	 * 
	 * @Description: 发布，数据写入发布记录表中
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月21日 下午1:53:21
	 */
	void insertForecastRecord(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 发布后修改合资车预测表发布状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月21日 下午1:53:50
	 */
	void updateJvReleaseStatus(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 修改预测数据发布状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月21日 下午7:44:26
	 */
	void updateErpReleaseStatus(SwVentureForecastModel model);

}
