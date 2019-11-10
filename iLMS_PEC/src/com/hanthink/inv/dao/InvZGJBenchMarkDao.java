package com.hanthink.inv.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.inv.model.InvZGJBenchMarkModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 支给件W-1周库存
 * @author WY
 *
 */
public interface InvZGJBenchMarkDao extends Dao<String, InvZGJBenchMarkModel>{

	/**
	 * 查询W-1周库存数据
	 * @param model
	 * @param page
	 * @return
	 */
	PageList<InvZGJBenchMarkModel> queryBenchMarkForPage(InvZGJBenchMarkModel model, Page page);

	/**
	 * 查询支给件W-1周库存数据,用于导出
	 * @param model
	 * @return
	 */
	List<InvZGJBenchMarkModel> queryExportDataForExcel(InvZGJBenchMarkModel model);

	/**
	 * 更新W-1周库存数据
	 * @param model
	 */
	void updateObj(InvZGJBenchMarkModel model);

	/**
	 * 新增W-1周库存基准数据
	 * @param model
	 */
	void addObj(InvZGJBenchMarkModel model);

	/**
	 * 查询是否存在该零件和该仓库的关系
	 * @return
	 */
	Integer queryIsSupportPart(InvZGJBenchMarkModel model);

	/**
	 * 查询是否存在该W-1周库存基准数据
	 * @param model
	 * @return
	 */
	Integer queryIsExistsBenchMark(InvZGJBenchMarkModel model);

	/**
	 * 批量删除W-1周库存基准临时数据
	 * @param array
	 */
	void delBatchObj(String[] array);

	/**
	 * 生成W-1周库存基准
	 * @param model
	 * @return
	 */
	Integer generateBenchMark(InvZGJBenchMarkModel model);


	/**导入开始*******************************************/
	/**
	 * 
	 * @Description: 根据UUID删除上次临时表数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:14:06
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * 
	 * @Description: 数据写入临时表
	 * @param @param importList   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:15:00
	 */
	void insertImportTempData(List<InvZGJBenchMarkModel> importList);

	/**
	 * 
	 * @Description: 调用存储校验数据准确性
	 * @param @param ckParamMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:15:44
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * 
	 * @Description: 查询是否可以导入的标识
	 * @param @param uuid
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:16:22
	 */
	String queryIsImportFlag(String uuid);

	/**
	 * 
	 * @Description: 分页查询导入临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:16:43
	 */
	PageList<InvZGJBenchMarkModel> queryImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 导出临时表数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:17:36
	 */
	List<InvZGJBenchMarkModel> queryTempDataForExport(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 查询是否有可以导入的数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:29:13
	 */
	List<InvZGJBenchMarkModel> queryForInsertList(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 根据处理标识查询出要修改的数据id，修改并记录日志
	 * @param @param paramMap
	 * @param @return   
	 * @return List<String>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:29:43
	 */
	List<String> queryUpdateDataFromImp(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 修改数据
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:30:22
	 */
	void updateImportData(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 插入数据
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:32:11
	 */
	void insertImportData(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 更新导入状态
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午4:32:25
	 */
	void updateImportDataImpStatus(Map<String, Object> paramMap);

	void dealStockUpdateExists(InvZGJBenchMarkModel model);

	void dealStockNotUpdateExists(InvZGJBenchMarkModel model);

	void dealStockDeleteExists(InvZGJBenchMarkModel model);

	
	/****支给件推算周开始********************************************************************/
	/**
	 * 
	 * @Description: 分页查询支给件推算周维护数据
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午3:33:21
	 */
	PageList<InvZGJBenchMarkModel> queryWeekCalForPage(InvZGJBenchMarkModel model, Page page);

	/**
	 * 
	 * @Description: 导出支给件推算周维护数据
	 * @param @param model
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午3:33:44
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
	 * @date 2019年5月22日 下午3:43:32
	 */
	void updateWeekCalObj(InvZGJBenchMarkModel model, String ipAddr);

	/**
	 * 
	 * @Description: 新增推算周数据
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午3:44:05
	 */
	void addWeekCalObj(InvZGJBenchMarkModel model);

	/**
	 * 
	 * @Description: 批量删除支给件推算周数据
	 * @param @param array   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月22日 下午4:15:32
	 */
	void delBatchWeekCalObj(String[] array);

	/**
	 * 
	 * @Description: TODO
	 * @param @param map
	 * @param @return   
	 * @return List<Map<String,String>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月24日 下午5:08:13
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
	 * @date 2019年5月24日 下午5:56:38
	 */
	PageList<InvZGJBenchMarkModel> handleListPartNo(InvZGJBenchMarkModel model, DefaultPage p);

	
	/***支给件推算周导入*****************************************************/
	/**
	 * 
	 * @Description: 根据UUID删除上次导入临时表的数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:19:29
	 */
	void deleteImportTempDataWeekCalByUUID(String uuid);

	/**
	 * 
	 * @Description: 查询导入临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:20:13
	 */
	PageList<InvZGJBenchMarkModel> queryImportTempDataWeekCal(Map<String, String> paramMap, Page page);

	/**
	 * 
	 * @Description: 查询导出导入临时表的数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:25:52
	 */
	List<InvZGJBenchMarkModel> queryTempDataForExportWeekCal(Map<String, String> paramMap);

	/**
	 * 
	 * @Description: 数据导入到临时表
	 * @param @param importList   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:26:25
	 */
	void insertImportTempDataWeekCal(List<InvZGJBenchMarkModel> importList);

	/**
	 * 
	 * @Description: 查询是否可导入标识
	 * @param @param uuid
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:27:35
	 */
	String queryIsImportFlagWeekCal(String uuid);

	/**
	 * 
	 * @Description: 查询出要修改的数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<String>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:28:15
	 */
	List<String> queryUpdateDataFromImpWeekCal(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 导入修改
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:28:44
	 */
	void updateImportDataWeekCal(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 导入新增
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:29:02
	 */
	void insertImportDataWeekCal(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 修改导入标识
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:29:10
	 */
	void updateImportDataImpStatusWeekCal(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 查询可导入数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月28日 下午4:41:55
	 */
	List<InvZGJBenchMarkModel> queryForInsertListWeekCal(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 调用存储校验数据
	 * @param @param ckParamMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月30日 上午11:53:05
	 */
	void checkImportDataWeekCal(Map<String, String> ckParamMap);

	
	/**支给件缺件查询*****************************************************************/
	/**
	 * 
	 * @Description: 支给件缺件分页查询
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月30日 下午3:46:49
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
	 * @date 2019年5月30日 下午3:47:02
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
	 * @date 2019年5月30日 下午5:33:27
	 */
	Integer getZGJDifference(InvZGJBenchMarkModel model);

	
}
