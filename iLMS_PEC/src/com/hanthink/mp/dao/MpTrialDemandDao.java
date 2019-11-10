package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpTrialDemandModel;
import com.hanthink.mp.model.MpTrialDemandModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：新车型需求计算DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpTrialDemandDao extends Dao<String, MpTrialDemandModel> {
	
	 /**
     * 分页查询新车型需求计算
     * @param model
     * @param p
     * @return
     */
    List<MpTrialDemandModel> queryMpTrialDemandForPage(MpTrialDemandModel model, DefaultPage p);

	/**
	 * 导入数据写入到临时表
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:08:11
	 */
	void insertMpTrialDemandImportTempData(List<MpTrialDemandModelImport> importList);

	/**
	 * 检查导入到临时表数据准确性
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:08:35
	 */
	void checkMpTrialDemandImportData(Map<String, String> ckParamMap);

	/**
	 * 查询导入的临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:29:41
	 */
	PageList<MpTrialDemandModelImport> queryMpTrialDemandImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将导入的临时数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:30:07
	 */
	void insertMpTrialDemandImportData(Map<String, Object> paramMap);

	/**
	 * 导入临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:12
	 */
	void updateMpTrialDemandImportDataImpStatus(Map<String, Object> paramMap);

	/**
	 * 根据导入的UUID删除临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	void deleteMpTrialDemandImportTempDataByUUID(String uuid);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpTrialDemandModel> queryMpTrialDemandByKey(MpTrialDemandModel model);

	/**
	 * 拿出临时表中的ID,根据ID查询出哪些数据要导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<String> queryUpdateDataFromMpTrialDemandImp(Map<String, Object> paramMap);
	
	/**
	 * 更新导入的方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	void updateMpTrialDemandImportData(Map<String, Object> paramMap);
	
	/**
     * 查询是否可以批量导入
     * @param uuid
     * @return
     */
    String queryMpTrialDemandIsImportFlag(String uuid);
    
    /**
     * 临时数据导出
     * @param uuid
     * @return
     */
	List<MpTrialDemandModelImport> queryMpTrialDemandImportTempDataForExport(Map<String, String> paramMap);

	/**
	 * 需求计算生成
	 * <p>return: void</p>  
	 * <p>Description: MpTrialDemandDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer generateMpTrialDemand(String curFactoryCode);

	/**
	 * 需求计算发布
	 * <p>return: void</p>  
	 * <p>Description: MpTrialDemandDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer releaseMpTrialDemand(String curFactoryCode);

	/**
	 * 查询可导入的数据
	 * <p>return: List<MpTrialDemandModelImport></p>  
	 * <p>Description: MpTrialDemandDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<MpTrialDemandModelImport> queryForInsertList(Map<String, Object> paramMap);
}
