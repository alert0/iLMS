package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：订单履历  DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpOrderRecordHisDao extends Dao<String, MpOrderRecordHisModel> {
	
	 /**
     * 分页查询订单履历
     * @param model
     * @param p
     * @return
     */
    List<MpOrderRecordHisModel> queryMpOrderRecordHisForPage(MpOrderRecordHisModel model, DefaultPage p);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpOrderRecordHisModel> queryMpOrderRecordHisByKey(MpOrderRecordHisModel model);

	/**
	 * 获取计算队列
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<DictVO> getUnloadPort();

	/**
	 * 导入数据写入到临时表
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:08:11
	 */
	void insertMpOrderRecordHisImportTempData(List<MpOrderRecordHisModel> importList);

	/**
	 * 检查导入到临时表数据准确性
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:08:35
	 */
	void checkMpOrderRecordHisImportData(Map<String, String> ckParamMap);

	/**
	 * 查询导入的临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:29:41
	 */
	PageList<MpOrderRecordHisModel> queryMpOrderRecordHisImportTempData(Map<String, String> paramMap, Page page);

	/**
	 * 导入临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:12
	 */
	void updateMpOrderRecordHisImportDataImpStatus(Map<String, Object> paramMap);

	/**
	 * 根据导入的UUID删除临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	void deleteMpOrderRecordHisImportTempDataByUUID(String uuid);
	
	/**
	 * 拿出临时表中的ID,根据ID查询出哪些数据要导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<String> queryUpdateDataFromMpOrderRecordHisImp(Map<String, Object> paramMap);
	
	/**
	 * 更新导入的方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	void updateMpOrderRecordHisImportData(Map<String, Object> paramMap);
	
	/**
     * 查询是否可以批量导入
     * @param uuid
     * @return
     */
    String queryMpOrderRecordHisIsImportFlag(String uuid);
    
    /**
     * 临时数据导出
     * @param uuid
     * @return
     */
	List<MpOrderRecordHisModel> queryMpOrderRecordHisImportTempDataForExport(Map<String, String> paramMap);
	
	/**
	 * 查询可导入的数据
	 * <p>return: List<MpOrderRecordHisModel></p>  
	 * <p>Description: MpOrderRecordHisDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
	List<MpOrderRecordHisModel> queryForInsertList(Map<String, Object> paramMap);

	/**
	 * 转换订单状态
	 * <p>return: String</p>  
	 * <p>Description: MpOrderRecordHisDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月31日
	 * @version 1.0
	 */
	String queryOrderStatus(String codeValueName);

	
	
}
