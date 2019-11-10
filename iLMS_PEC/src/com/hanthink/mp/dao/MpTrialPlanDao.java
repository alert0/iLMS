package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpTrialPlanModel;
import com.hanthink.mp.model.MpTrialPlanModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：新车型计划维护 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpTrialPlanDao extends Dao<String, MpTrialPlanModel> {
	
	 /**
     * 分页查询新车型计划维护
     * @param model
     * @param p
     * @return
     */
    List<MpTrialPlanModel> queryMpTrialPlanForPage(MpTrialPlanModel model, DefaultPage p);

	/**
	 * 导入数据写入到临时表
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:08:11
	 */
	void insertMpTrialPlanImportTempData(List<MpTrialPlanModelImport> importList);

	/**
	 * 检查导入到临时表数据准确性
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:08:35
	 */
	void checkMpTrialPlanImportData(Map<String, String> ckParamMap);

	/**
	 * 查询导入的临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:29:41
	 */
	PageList<MpTrialPlanModelImport> queryMpTrialPlanImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将导入的临时数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:30:07
	 */
	void insertMpTrialPlanImportData(Map<String, Object> paramMap);

	/**
	 * 导入临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:12
	 */
	void updateMpTrialPlanImportDataImpStatus(Map<String, Object> paramMap);

	/**
	 * 根据导入的UUID删除临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	void deleteMpTrialPlanImportTempDataByUUID(String uuid);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpTrialPlanModel> queryMpTrialPlanByKey(MpTrialPlanModel model);
	
	/**
     * 查询是否可以批量导入
     * @param uuid
     * @return
     */
    String queryMpTrialPlanIsImportFlag(String uuid);
    
    /**
     * 临时数据导出
     * @param uuid
     * @return
     */
	List<MpTrialPlanModelImport> queryMpTrialPlanImportTempDataForExport(Map<String, String> paramMap);
	
	/**
	 * 查询未订购数据的SortId用于记录日志
	 * @param model
	 * @return
	 */
	List<String> querySortIdAndLogByCalStatus();
	
	/**
	 * 直接删除未订购数据
	 * @param model
	 * @return
	 */
	void removeAndLogByCalStatus(String[] aryIds,String ipAddr);

	/**
	 * 获取新车型计划
	 * <p>return: void</p>  
	 * <p>Description: MpTrialPlanDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer getMpTrialPlan(String curFactoryCode);

	/**
	 * 查询可导入的数据
	 * <p>return: List<MpTrialPlanModelImport></p>  
	 * <p>Description: MpTrialPlanDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<MpTrialPlanModelImport> queryForInsertList(Map<String, Object> paramMap);

	/**
	 * 查询出最大的sortId
	 * <p>return: Integer</p>  
	 * <p>Description: MpTrialPlanDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年11月19日
	 * @version 1.0
	 */
	Integer selectMaxSortId();
}
