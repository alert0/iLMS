package com.hanthink.mp.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.mp.model.MpDemandForecastModel;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;


/**
 * 订购使用单车BOM
 * @author WY
 *
 */
public interface MpDemandForecastManager extends Manager<String, MpDemandForecastModel>{

	/**
	 * 生成月度预测数据
	 * @param model
	 * @param currentUser
	 * @return
	 */
	Integer genMonthDemandForcast(MpDemandForecastModel model, IUser currentUser);

	/**
	 * 获取版本信息
	 * @param map
	 * @return
	 */
	List<MpDemandForecastModel> getVersion(Map<String, String> map);

	/**
	 * 查询预测数据
	 * @param model
	 * @param p
	 * @return
	 */
	PageList<MpDemandForecastModel> queryDemandForeCastForPage(
			MpDemandForecastModel model, DefaultPage p);

	/**
	 * 导出预测数据
	 * @param model
	 * @return
	 */
	List<MpDemandForecastModel> queryDemandForeCastByKey(
			MpDemandForecastModel model);

	/**
	 * 批量删除需求预测数据
	 * @param aryIds
	 * @param ipAddr
	 */
	void batchRemoveDemandForcast(String[] aryIds, String ipAddr);

	/**
	 * 根据ID修改数量
	 * @param mpDemandForecastModel
	 * @param ipAddr
	 */
	void updateDemandForcast(MpDemandForecastModel mpDemandForecastModel,
			String ipAddr);

	/**
	 * 根据UUID删除导入临时数据
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
	 * 清除预测数据
	 * @param model
	 * @param currentUser
	 * @return
	 */
	void clearDemandForecast(MpDemandForecastModel model, IUser currentUser);

	/**
	 * 将EXCEL数据导入临时表
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @param user
	 * @return
	 */
	Map<String, Object> importMonthModel(MultipartFile file, String uuid,
			String ipAddr, IUser user);

	/**
	 * 查询导入的临时数据
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
	 * 确认导入月度预测数据
	 * @param paramMap
	 * @param ipAddr
	 */
	void insertMonthImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

	/**
	 * 发布月预测数据
	 * @param model
	 * @param currentUser
	 * @return
	 */
	Integer releaseDemandForcast(MpDemandForecastModel model, IUser currentUser);

	/**
	 * 判断是否已经发布
	 * @param model
	 * @return
	 */
	Integer queryIsRelease(MpDemandForecastModel model);

	/**
	 * 判断是否已经发布,根据ID数组查询
	 * @param aryIds
	 * @return
	 */
	Integer queryIsReleaseById(List<String> aryIds);

	Map<String, Object> importWeekModel(MultipartFile file, String uuid, String ipAddr, IUser user);

	/**
	 * 
	 * @Description: 生成一级件预测数据
	 * @param @param model
	 * @param @param currentUser
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月28日 下午1:55:25
	 */
	Integer genDemandPartIf(MpDemandForecastModel model);

	/**
	 * 
	 * @Description: 周预测一级件导入临时表
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月28日 下午6:12:01
	 */
	Map<String, Object> importWeekForecastModel(MultipartFile file, String uuid, String ipAddr, IUser user);

	/***
	 * 
	 * @Description: 周预测一级件导入正式表
	 * @param @param paramMap
	 * @param @param ipAddr   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月28日 下午6:11:42
	 */
	void insertWeekForecastImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

	/**
	 * 
	 * @Description: 分页查询生成的一级件周度预测数据
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<MpDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月5日 上午10:32:40
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
	 * @date 2019年5月5日 上午10:56:33
	 */
	List<MpDemandForecastModel> downloadMpDemandWeekForeCast(MpDemandForecastModel model);

	/**
	 * 
	 * @Description: 生效使用周预测数据
	 * @param @param model
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 上午11:48:38
	 */
	Integer effectDemand(MpDemandForecastModel model);

	/**
	 * 
	 * @Description: 判断当前版本是否已经存在业务表
	 * @param @param model
	 * @param @return   
	 * @return Boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 下午12:36:02
	 */
	Boolean isEffectVersion(MpDemandForecastModel model);

	/**
	 * 
	 * @Description: 周预测界面获取版本号
	 * @param @param map
	 * @param @return   
	 * @return List<MpDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月7日 下午2:47:16
	 */
	List<MpDemandForecastModel> getForeVersion(Map<String, String> map);

	/**
	 * 
	 * @Description: 判断版本号是否已存在
	 * @param @param model
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月8日 下午4:37:27
	 */
	boolean validateVersionExists(SwDemandForecastModel model);

	/**
	 * 
	 * @Description: 修改版本号
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月8日 下午4:37:41
	 */
	void submitVersion(SwDemandForecastModel model);
	
}
