package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.sw.model.SwVentureOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

public interface SwVentureOrderMananger extends Manager<String, SwVentureOrderModel>{

	PageList<SwVentureOrderModel> queryVentureOrderForPage(SwVentureOrderModel model, Page page);

	/**
	 * 
	 * @Description: 根据UUID删除临时表数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:27:47
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @Description: 文件导入临时表
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:28:11
	 */
	Map<String, Object> importModel(MultipartFile file, String uuid, String ipAddr, IUser user);

	/**
	 * 
	 * @Description: 分页查询导入临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<SwVentureOrderModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:28:57
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
	 * @date 2019年8月23日 上午10:30:31
	 */
	List<SwVentureOrderModel> queryTempDataForExport(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 导入正式表
	 * @param @param paramMap
	 * @param @param ipAddr   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年8月23日 上午10:31:00
	 */
	void insertImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;
	/**
	 * 删除未订购数据
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月24日
	 */
	void deleteAllPuchaeIsNull(Map<String, Object> paramMap) throws Exception;
	/**
	 * 订单界面数据导出
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月24日
	 */
	List<SwVentureOrderModel> queryVentureOrderForExport(SwVentureOrderModel model);

	void orederRelease(Map<String, Object> paramMap);

}
