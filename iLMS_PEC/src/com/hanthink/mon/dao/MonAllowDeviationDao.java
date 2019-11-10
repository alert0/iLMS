package com.hanthink.mon.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.MonAllowDeviationModel;
import com.hanthink.mon.model.MonAllowDeviationModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface MonAllowDeviationDao extends Dao<String, MonAllowDeviationModel> {
	/**
	 * @Description: 允许误差查询
	 * @param: @param
	 *             model
	 * @param: @param
	 *             page
	 * @param: @return
	 * @return: PageList<AllowDeviation>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	PageList<MonAllowDeviationModel> queryAllowDeviationPage(MonAllowDeviationModel model, DefaultPage page);

	/**
	 * 批量删除数据
	 * <p>
	 * return: void
	 * </p>
	 * <p>
	 * Description: allowDeviationDao.java
	 * </p>
	 * 
	 * @author linzhuo
	 * @author Midnight
	 * @date 2018年11月03日
	 * @throws Exception
	 */
	void deleteByIds(String[] aryIds) throws Exception;

	/**
	 * @Description: 允许误差导出查询
	 * @param:  model
	 * @param: page
	 * @param: @return
	 * @return: PageList<AllowDeviation>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	List<MonAllowDeviationModel> queryAllowDeviationForExport(MonAllowDeviationModel model);

	/**
	 * 根据UUID删除临时数据
	 * 
	 * @param uuid
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	void deleteAllowDeviationImportTempDataByUUID(String uuid);

	/**
	 * 导入数据到临时表
	 * 
	 * @param importList
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	void insertMonAllowDeviationImportTempData(List<MonAllowDeviationModelImport> importList);

	/**
	 * 检查临时表数据准确性
	 * 
	 * @param ckParamMap
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	void checkMonAllowDeviationImportData(Map<String, String> ckParamMap);

	/**
	 * 查询是否可以批量导入
	 * 
	 * @param uuid
	 * @return true or false
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	String queryMonAllowDeviationImportFlag(String uuid);

	/**
	 * 查询导入的临时数据信息
	 * 
	 * @param paramMap
	 * @return
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	PageList<MonAllowDeviationModelImport> queryMonAllowDeviationImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 查询可导入的数据
	 * @return: List<MonAllowDeviationModelImport>
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	List<MonAllowDeviationModelImport> queryForInsertList(Map<String, Object> paramMap);

	/**
	 * 查询需要更新的数据
	 * 
	 * @param model
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	List<String> queryUpdateDataFromMonAllowDeviationImp(Map<String, Object> paramMap);

	/**
	 * 将临时表更新至正式表
	 * 
	 * @param model
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	void updateMonAllowDeviationImportData(Map<String, Object> paramMap);

	/**
	 * 将临时数据写入到正式表
	 * 
	 * @param paramMap
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	void insertMonAllowDeviationImportData(Map<String, Object> paramMap);

	/**
	 * 更新临时表导入状态
	 * 
	 * @param paramMap
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	void updateMonAllowDeviationImportDataImpStatus(Map<String, Object> paramMap);
	
    /**
     * 临时数据导出
     * @param uuid
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
