package com.hanthink.mon.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.KbIpConfigModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：剩余量主数据 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface KbIpConfigDao extends Dao<String, KbIpConfigModel> {
	
	 /**
     * 分页查询零件剩余量主数据
     * @param model
	 * @param page 
     * @param page
     * @return
     */
    List<KbIpConfigModel> queryKbIpConfigForPage(KbIpConfigModel model, Page page);

	/**
	 * 导入数据写入到临时表
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:08:11
	 */
	void insertKbIpConfigImportTempData(List<KbIpConfigModel> importList);

	/**
	 * 检查导入到临时表数据准确性
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:08:35
	 */
	void checkKbIpConfigImportData(Map<String, String> ckParamMap);

	/**
	 * 查询导入的临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:29:41
	 */
	PageList<KbIpConfigModel> queryKbIpConfigImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 确定导入，将导入的临时数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:30:07
	 */
	void insertKbIpConfigImportData(Map<String, Object> paramMap);

	/**
	 * 导入临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:12
	 */
	void updateKbIpConfigImportDataImpStatus(Map<String, Object> paramMap);

	/**
	 * 根据导入的UUID删除临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	void deleteKbIpConfigImportTempDataByUUID(String uuid);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<KbIpConfigModel> queryKbIpConfigByKey(KbIpConfigModel model);

	/**
	 * 拿出临时表中的ID,根据ID查询出哪些数据要导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<String> queryUpdateDataFromKbIpConfigImp(Map<String, Object> paramMap);
	
	/**
	 * 更新导入的方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	void updateKbIpConfigImportData(Map<String, Object> paramMap);
	
	/**
     * 查询是否可以批量导入
     * @param uuid
     * @return
     */
    String queryKbIpConfigIsImportFlag(String uuid);
    
    /**
     * 临时数据导出
     * @param uuid
     * @return
     */
	List<KbIpConfigModel> queryKbIpConfigImportTempDataForExport(Map<String, String> paramMap);
	
	/**
	 * 查询可导入的数据
	 * <p>return: List<KbIpConfigModel></p>  
	 * <p>Description: KbIpConfigDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<KbIpConfigModel> queryForInsertList(Map<String, Object> paramMap);
	
	/**
	 * 批量删除数据
	 * <p>return: void</p>  
	 * <p>Description: KbIpConfigDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月30日
	 * @version 1.0
	 * @throws Exception 
	 */
	void deleteByIds(String[] aryIds) throws Exception;
	
	/**
	 * 判断主表主键冲突
	 * @param KbIpConfigModel
	 * @return
	 */
	Integer selectPrimaryKey(KbIpConfigModel KbIpConfigModel);
	
	/**
	 * 判断明细主键冲突
	 * <p>return: Integer</p>  
	 * <p>Description: KbIpConfigDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月19日
	 * @version 1.0
	 */
	Integer selectPrimaryKeyDetail(KbIpConfigModel kbIpConfigModel);
	
	/**
	 * 更新明细
	 * <p>return: void</p>  
	 * <p>Description: KbIpConfigDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月19日
	 * @version 1.0
	 */
	void updateDetail(KbIpConfigModel kbIpConfigModel);
	
	/**
	 * 新增明细
	 * <p>return: void</p>  
	 * <p>Description: KbIpConfigDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月19日
	 * @version 1.0
	 */
	void createDetail(KbIpConfigModel kbIpConfigModel);
	
	/**
	 * 删除明细
	 * <p>return: void</p>  
	 * <p>Description: KbIpConfigDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月19日
	 * @version 1.0
	 */
	void deleteByIdsDetail(String[] aryIds);
	List<KbIpConfigModel> queryKbList(String factoryCode)throws Exception;
	Integer getCurrBussId() throws Exception;
	boolean queryForMaxSortNum(KbIpConfigModel kbIpConfigModel)throws Exception;
	List<KbIpConfigModel> queryKbTypeForPage(KbIpConfigModel model, Page page)throws Exception;
	void saveBasicKbInfo(KbIpConfigModel kbIpConfigModel)throws Exception;
	void saveDetailInfo(KbIpConfigModel kbIpConfigModel)throws Exception;
	void updateKbType(KbIpConfigModel kbIpConfigModel)throws Exception;
	String getNextIdNum(String seqName)throws Exception;
	boolean combIpIsInusedJudge(KbIpConfigModel kbIpConfigModel)throws Exception;
	void updateKbConfigData(Map<String, String> paramMap)throws Exception;	
}
