package com.hanthink.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwVentureOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SwVentureOrderDao extends Dao<String, SwVentureOrderModel>{

	List<SwVentureOrderModel> queryVentureOrderForPage(SwVentureOrderModel model, Page page);

	/**
	 * 
	 * @Description: 根据UUID删除临时表数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:36:18
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @Description: 数据写入到临时表
	 * @param @param importList   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:46:20
	 */
	void insertImportTempData(List<SwVentureOrderModel> importList);

	/**
	 * 
	 * @Description: 调用存储校验数据准确性
	 * @param @param ckParamMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:46:36
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * 
	 * @Description: 查询导入标识
	 * @param @param uuid
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:47:05
	 */
	String queryIsImportFlag(String uuid);

	/**
	 * 
	 * @Description: 分页查询临时表数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:47:22
	 */
	PageList<SwVentureOrderModel> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 导出临时表数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<SwVentureOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:47:44
	 */
	List<SwVentureOrderModel> queryTempDataForExport(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 查询是否有正确数据可以导入
	 * @param @param paramMap
	 * @param @return   
	 * @return List<SwVentureOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:48:34
	 */
	List<SwVentureOrderModel> queryForInsertList(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 导入正式表
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:49:11
	 */
	void insertImportData(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 修改导入状态
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:49:29
	 */
	void updateVentureOrderImportDataImpStatus(Map<String, Object> paramMap);

	void deleteAllPuchaeIsNull(Map<String, Object> paramMap);

	List<SwVentureOrderModel> queryForPuchaeIsNull(Map<String, Object> paramMap);

	void deleteAllHeader(List<SwVentureOrderModel> list);

	void deleteAllBody(List<SwVentureOrderModel> list);
	/**
	 * 订单界面数据导出
	 * @param model
	 * @return
	 * @author zmj
	 * @date 2019年8月24日
	 */
	List<SwVentureOrderModel> queryVentureOrderForExport(SwVentureOrderModel model);

	void orederRelease(Map<String, Object> paramMap);

}
