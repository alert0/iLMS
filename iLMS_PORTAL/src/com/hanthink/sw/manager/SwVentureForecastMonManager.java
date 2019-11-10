package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.sw.model.SwVentureForecastModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;

public interface SwVentureForecastMonManager {

	/**
	 * 
	 * @Description: 删除临时表数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月2日 下午3:31:03
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @Description: Excel导入临时表
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月2日 下午3:31:24
	 */
	Map<String, Object> importModel(MultipartFile file, String uuid, String ipAddr, IUser user);

	/**
	 * 
	 * @Description: 分页查询临时表数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 上午10:42:08
	 */
	PageList<SwVentureForecastModel> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 导出临时表数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 上午10:46:13
	 */
	List<SwVentureForecastModel> queryTempDataForExport(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 导入正式表
	 * @param @param paramMap
	 * @param @param ipAddr   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 上午10:47:10
	 */
	void insertImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

	/**
	 * 
	 * @Description: 分页查询合资车预测数据
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午3:38:58
	 */
	PageList<SwVentureForecastModel> queryVentureForePage(SwVentureForecastModel model, Page page);

	/**
	 * 
	 * @Description: 删除版本
	 * @param @param map
	 * @param @return   
	 * @return int  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午4:17:51
	 */
	int deleteVentureVersion(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 导出合资车预测数据
	 * @param @param model
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午5:51:01
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
	 * @date 2019年8月14日 下午6:36:13
	 */
	int insertReleaseVersion(SwVentureForecastModel model);

	/**
	 * 
	 * @Description: 获取订购方版本号下拉框
	 * @param @param map
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月14日 下午10:35:29
	 */
	List<SwVentureForecastModel> getJvVersion(Map<String, String> map);

	/**
	 * 
	 * @Description: 获取ERP版本号下拉框
	 * @param @param map
	 * @param @return   
	 * @return List<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月16日 下午3:59:25
	 */
	List<SwVentureForecastModel> getErpVersion(Map<String, String> map);

	/**
	 * 
	 * @Description: 检索
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 上午11:44:54
	 */
	void checkDataRsult(SwVentureForecastModel model, Page page);

	/**
	 * 
	 * @Description: 检索数据写入临时表后，统计数量分页展示在界面
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午6:35:45
	 */
	PageList<SwVentureForecastModel> queryTotalQty(SwVentureForecastModel model, Page page);

	/**
	 * 
	 * @Description: 合并后查询导出数据
	 * @param @param startMonthStr
	 * @param @param endMonthStr
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<Map<String,Object>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午7:15:53
	 */
	PageList<Map<String, Object>> queryVentureForecastMonExportDataByPage(String startMonthStr, String endMonthStr,
			SwVentureForecastModel model, DefaultPage page);

	/**
	 * 
	 * @Description: 查询出erp下发的预测中最大日期，最小日期，对象周
	 * @param @param map
	 * @param @return   
	 * @return SwVentureForecastModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月20日 下午10:36:48
	 */
	SwVentureForecastModel getExportModeMsg(Map<String, Object> map);
}
