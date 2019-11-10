package com.hanthink.inv.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.model.InvZGJBenchMarkModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;


public interface InvZGJBenchMarkManager extends Manager<String, InvZGJBenchMarkModel>{

	/**
	 * 支给件W-1周库存
	 * @param model
	 * @param page
	 * @return
	 */
	PageList<InvZGJBenchMarkModel> queryBenchMarkForPage(
			InvZGJBenchMarkModel model, Page page);

	/**
	 * 查询数据用于导出
	 * @param model
	 * @return
	 */
	List<InvZGJBenchMarkModel> queryExportDataForExcel(InvZGJBenchMarkModel model);

	/**
	 * 修改W-1周库存数据
	 * @param model
	 * @param ipAddr
	 */
	void updateObj(InvZGJBenchMarkModel model, String ipAddr);

	/**
	 * 新增W-1周库存基准数据
	 * @param model
	 */
	Integer addObj(InvZGJBenchMarkModel model);

	/**
	 * 删除W-1周支给件库存基准数据
	 * @param list
	 * @param ipAddr
	 * @param currentUser
	 */
	void delBatchObj(List<InvZGJBenchMarkModel> list, String ipAddr,
			IUser currentUser);

	/**
	 * 生成W-1周支给件库存
	 * @param model
	 * @return
	 */
	Integer generateBenchMark(InvZGJBenchMarkModel model);

	
	/***导入开始****************************************************************/
	/**
	 * 
	 * @Description: 根据UUID删除上次数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:39:48
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @Description: 导入临时表
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:40:11
	 */
	Map<String, Object> imporModel(MultipartFile file, String uuid, String ipAddr, IUser user);

	/**
	 * 
	 * @Description: 分页查询导入临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:47:56
	 */
	PageList<InvZGJBenchMarkModel> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 导出  在导入时的数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:48:52
	 */
	List<InvZGJBenchMarkModel> queryTempDataForExport(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 临时表写入正式表
	 * @param @param paramMap
	 * @param @param ipAddr   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:49:21
	 */
	void insertImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

	/**
	 * 
	 * @Description: 确认处理
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月21日 下午5:39:13
	 */
	void dealStock(InvZGJBenchMarkModel model);

	
	/****支给件推算周维护开始***********************************************************************************/
	/**
	 * 
	 * @Description: 分页查询支给件推算周数据
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午3:30:50
	 */
	PageList<InvZGJBenchMarkModel> queryWeekCalForPage(InvZGJBenchMarkModel model, Page page);

	/**
	 * 
	 * @Description: 导出支给件推算周数据
	 * @param @param model
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午3:31:28
	 */
	List<InvZGJBenchMarkModel> queryExportWeekCalForExcel(InvZGJBenchMarkModel model);

	/**
	 * 
	 * @Description: 修改推算周数据
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午3:39:29
	 */
	void updateWeekCalObj(InvZGJBenchMarkModel model, String ipAddr);

	/**
	 * 
	 * @Description: 新增推算周数据
	 * @param @param model
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午3:39:40
	 */
	Integer addWeekCalObj(InvZGJBenchMarkModel model);

	/**
	 * 
	 * @Description: 批量删除支给件推算周数据
	 * @param @param list
	 * @param @param ipAddr
	 * @param @param currentUser   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午4:05:27
	 */
	void delBatchWeekCalObj(List<InvZGJBenchMarkModel> list, String ipAddr, IUser currentUser);

	/**
	 * 
	 * @Description: 新增弹窗获取
	 * @param @param map
	 * @param @return   
	 * @return List<Map<String,String>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月24日 下午5:03:26
	 */
	List<InvZGJBenchMarkModel> selectUnloadWare(Map<String, String> map);

	/**
	 * 
	 * @Description: 获取新增界面零件号弹窗
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月24日 下午5:55:53
	 */
	PageList<InvZGJBenchMarkModel> handleListPartNo(InvZGJBenchMarkModel model, DefaultPage p);

	
	/**支给件推算周导入开始**************************************************************/
	/**
	 * 
	 * @Description: 根据uuid删除上次导入的数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:10:22
	 */
	void deleteImportTempDataWeekCalByUUID(String uuid);

	/**
	 * 
	 * @Description: 将Excel文件导入临时表
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:10:46
	 */
	Map<String, Object> imporModelWeekCal(MultipartFile file, String uuid, String ipAddr, IUser user);

	/**
	 * 
	 * @Description: 查询导入临时表中的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:11:14
	 */
	PageList<InvZGJBenchMarkModel> queryImportTempDataWeekCal(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 查询导出导入临时表中的数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:11:35
	 */
	List<InvZGJBenchMarkModel> queryTempDataForExportWeekCal(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 临时表数据写入正式表
	 * @param @param paramMap
	 * @param @param ipAddr   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:12:51
	 */
	void insertImportDataWeekCal(Map<String, Object> paramMap, String ipAddr) throws Exception;

	
	/**支给件缺件查询开始**********************************/
	/**
	 * 
	 * @Description: 支给件缺件分页查询
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月30日 下午3:44:31
	 */
	PageList<InvZGJBenchMarkModel> queryWeekCalForPageDifference(InvZGJBenchMarkModel model, Page page);

	/**
	 * 
	 * @Description: 支给件缺件查询导出
	 * @param @param model
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月30日 下午3:45:21
	 */
	List<InvZGJBenchMarkModel> exportForExcelDifference(InvZGJBenchMarkModel model);

	/**
	 * 
	 * @Description: 生成支给件差异
	 * @param @param model
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月30日 下午5:32:52
	 */
	Integer getZGJDifference(InvZGJBenchMarkModel model);

	
}
