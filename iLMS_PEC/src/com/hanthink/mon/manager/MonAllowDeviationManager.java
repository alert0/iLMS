package com.hanthink.mon.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.mon.model.MonAllowDeviationModel;
import com.hanthink.mon.model.MonAllowDeviationModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

/**
 * @ClassName: DeliveryOrderManager
 * @Description: 允许误差查询
 * @author Midnight
 * @date 2018年11月03日
 */
public interface MonAllowDeviationManager extends Manager<String, MonAllowDeviationModel> {

	/**
	 * @Description: 允许误差查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return
	 * @return: PageList<AllowDeviationModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonAllowDeviationModel> queryAllowDeviationPage(MonAllowDeviationModel model, DefaultPage page);

	/**
	 * 更新数据并记录日志
	 * @param model
	 * @param opeIp
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	void updateAndLog(MonAllowDeviationModel model, String opeIp);
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author Midnight
	 * @date 2018年11月03日
	 * @throws Exception 
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception;
	
	
	/**
	 * @Description: 导出误差查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return
	 * @return: PageList<AllowDeviationModel>
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	List<MonAllowDeviationModel> queryAllowDeviationForExport(MonAllowDeviationModel model);
	
	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	void deleteAllowDeviationImportTempDataByUUID(String uuid);
	
	/**
	 * 导入Excel数据
	 * @param file
	 * @param uuid
	 * @return
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	Map<String, Object> importAllowDeviationTemp(MultipartFile file, String uuid,String ipAddr, IUser user);

	/**
	 * 查询导入的临时数据
	 * @param paramMap
	 * @return
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	PageList<MonAllowDeviationModelImport> queryAllowDeviationImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	void insertAllowDeviationImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @return
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	 List<MonAllowDeviationModelImport> queryAllowDeviationImportTempDataForExport(Map<String, String> paramMap);

	 /**
	  * @Description: 判断集货路线是否存在 
	  * @param: @param model
	  * @param: @return    
	  * @return: List<MonAllowDeviationModel>   
	  * @author: dtp
	  * @date: 2018年11月27日
	  */
	List<MonAllowDeviationModel> queryIsExist(MonAllowDeviationModel model);
}
