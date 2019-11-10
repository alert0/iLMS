package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.mp.model.MpDemandForecastModel;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 订购使用单车BOM
 * @author WY
 *
 */
public interface MpDemandForecastDao extends Dao<String, MpDemandForecastModel> {

	
	/**
	 * 查询预测数据
	 * @param model
	 * @param p
	 * @return
	 */
	List<MpDemandForecastModel> queryDemandForeCastForPage(
			MpDemandForecastModel model, DefaultPage p);
	
	/**
	 * 生成月度预测数据
	 * @param model
	 */
	Integer genMonthDemandForcast(MpDemandForecastModel model);

	/**
	 * 获取版本信息
	 * @param map
	 * @return
	 */
	List<MpDemandForecastModel> getVersion(Map<String, String> map);

	/**
	 * 查询预测数据用于导出
	 * @param model
	 * @return
	 */
	List<MpDemandForecastModel> queryDemandForeCastByKey(
			MpDemandForecastModel model);

	/**
	 * 批量删除预测需求数据
	 * @param aryIds
	 */
	void batchRemoveDemandForcast(String aryIds);

	/**
	 * 更新预测数据
	 * @param mpDemandForecastModel
	 */
	void updateDemandForcast(MpDemandForecastModel mpDemandForecastModel);

	/**
	 * 删除预测临时表数据
	 * @param uuid
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 获取默认版本数据
	 * @param map
	 * @return
	 */
	List<MpDemandForecastModel> getDefaultVersion(Map<String, String> map);

	/**
	 * 清除需求预测数据
	 * @param model
	 */
	void clearDemandForecast(MpDemandForecastModel model);

	/**
	 * 将EXCEL数据导入临时表
	 * @param importList
	 */
	void insertImportMonthTempData(List<MpDemandForecastModel> importList);

	/**
	 * 校验导入月预测数据
	 * @param ckParamMap
	 */
	void checkImportMonthData(Map<String, String> ckParamMap);

	/**
	 * 查询是否可导入
	 * @param uuid
	 * @return
	 */
	String queryIsImportFlag(String uuid);

	/**
	 * 查询导入临时数据
	 * @param paramMap
	 * @param page
	 * @return
	 */
	PageList<MpDemandForecastModel> queryImportTempData(
			Map<String, String> paramMap, Page page);

	/**
	 * 查询导入校验结果数据
	 * @param paramMap
	 * @return
	 */
	List<MpDemandForecastModel> queryMonthTempDataForExport(
			Map<String, String> paramMap);

	/**
	 * 查询导入数据
	 * @param paramMap
	 * @return
	 */
	List<MpDemandForecastModel> queryForInsertMonthList(Map<String, Object> paramMap);

	/**
	 * 查询更新的数据
	 * @param paramMap
	 * @return
	 */
	List<String> queryUpdateDataFromMonthImp(Map<String, Object> paramMap);

	/**
	 * 导入更新
	 * @param paramMap
	 */
	void updateMonthImportData(Map<String, Object> paramMap);

	/**
	 * 导入新增
	 * @param paramMap
	 */
	void insertMonthImportData(Map<String, Object> paramMap);

	/**
	 * 更新导入状态
	 * @param paramMap
	 */
	void updateDemandForecastImportDataImpStatus(Map<String, Object> paramMap);

	/**
	 * 发布月预测版本数据
	 * @param model
	 * @return
	 */
	Integer releaseDemandForcast(MpDemandForecastModel model);

	/**
	 * 判断是否已发布
	 * @param model
	 * @return
	 */
	Integer queryIsRelease(MpDemandForecastModel model);

	/**
	 * 根据ID数组查询是否已发布
	 * @param aryIds
	 * @return
	 */
	Integer queryIsReleaseById(List<String> aryIds);

	/**
	 * 
	 * @Description: 生成一级件预测
	 * @param @param model
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月28日 下午1:58:05
	 */
	Integer genDemandPartIf(MpDemandForecastModel model);

	/**
	 * 
	 * @Description: TODO
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月28日 下午6:28:33
	 */
	void updateWeekForecastImportData(Map<String, Object> paramMap);

	void insertWeekForecastImportData(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 分页查询生成的一级件周度预测
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<MpDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月5日 上午10:34:08
	 */
	PageList<MpDemandForecastModel> queryDemandWeekForePage(MpDemandForecastModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 导出生成的一级件周度预测数据
	 * @param @param model
	 * @param @return   
	 * @return List<MpDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月5日 上午10:57:17
	 */
	List<MpDemandForecastModel> downloadMpDemandWeekForeCast(MpDemandForecastModel model);

	/***
	 * 
	 * @Description: 校验导入数据正确性
	 * @param @param ckParamMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月5日 下午3:44:00
	 */
	void checkImportMonthDataFore(Map<String, String> ckParamMap);

	/**
	 * 
	 * @Description: 修改生成的一级件数据版本为导入的版本
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月5日 下午3:44:15
	 */
	void updateWeekForecastVersion(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 生效使用周预测数据
	 * @param @param model
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 上午11:49:26
	 */
	Integer effectDemand(MpDemandForecastModel model);

	/**
	 * 
	 * @Description: 判断当前版本是否已经在业务表存在
	 * @param @param model
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 下午12:37:59
	 */
	Integer isEffectVersion(MpDemandForecastModel model);

	/**
	 * 
	 * @Description: 周预测计算界面获取版本号
	 * @param @param map
	 * @param @return   
	 * @return List<MpDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 下午2:47:57
	 */
	List<MpDemandForecastModel> getForeVersion(Map<String, String> map);

	/**
	 * 
	 * @Description: 判断用户自定义的版本是否已存在
	 * @param @param model
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月8日 下午4:40:08
	 */
	Integer validateVersionExists(SwDemandForecastModel model);

	/**
	 * 
	 * @Description: 修改版本号
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月8日 下午4:40:21
	 */
	void submitVersion(SwDemandForecastModel model);

	
}
