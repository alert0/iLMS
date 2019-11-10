package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.PupProPlanPageModel;
import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupProPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

public interface PupProPlanDao extends Dao<String, PupProPlanModel>{
	
	/**
	 * 通过条件查询数据
	 *@author zmj
	 *@param plan 查询条件VO
	 *@param page 页面信息
	 *@return list
	 *@throws Exception
	 *@date 2018年9月15日 下午12:32:06
	 */
	List<PupProPlanModel> queryProPlanForPage(PupProPlanPageModel plan,Page page) throws Exception;
	/**
	 * 查询订单的已发标志
	 * 作者:zmj
	 * 日期:2018年10月4日下午12:42:39
	 * 版权:汉思信息技术有限公司
	 */
	DictVO querySendFlag()throws Exception;
	
	/**
	 * 查询导出数据
	 *@author zmj
	 *@param plan 查询条件VO
	 *@return
	 *@throws Exception
	 *@date 2018年9月15日 下午12:33:38
	 */
	List<PupProPlanModel> queryProPlanByKey(PupProPlanPageModel plan) throws Exception;
	/**
	 * 通过UUID删除数据
	 *@author zmj
	 *@param uuid
	 *@throws Exception
	 *@date 2018年9月15日 下午12:34:13
	 */
	void deleteProPlanTempDataByUUID(String uuid) throws Exception;
	/**
	 * 将导入数据插入临时数据表
	 *@author zmj
	 *@param plan 导入数据
	 *@throws Exception
	 *@date 2018年9月15日 下午12:34:42
	 */
	void insertProPlanIntoTempData(List<PupProPlanModel> plan) throws Exception;
	/**
	 * 拿出临时表中的ID,根据ID查询出哪些数据要导入
	 *@param paramMap
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月17日 上午9:54:32
	 */
	List<String> queryUpdateDataFromImportPlan(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 导入数据校验
	 *@author zmj
	 *@param map 检验的数据集合
	 *@throws Exception
	 *@date 2018年9月15日 下午12:35:20
	 */
	void checkProplanImportDataInformation(Map<String, String> map) throws Exception;
	/**
	 * 功能描述:
	 * 作者:zmj
	 * 日期:2018年9月15日 下午13:37:13
	 * 版权:汉思信息技术有限公司
	 */
	List<PupProPlanModel> queryImportInformationForPage(Map<String, String> paramMap, Page page)throws Exception;
	/**
	 * 查询数据导入标志
	 *@author zmj
	 *@param uuid
	 *@return 检验结果
	 *@throws Exception
	 *@date 2018年9月15日 下午12:35:59
	 */
	String queryProPlanImportFlag(String uuid) throws Exception;
	/**
	 * 修改导入数据
	 *@param paramMap
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月17日 上午10:18:41
	 */
	public void updateProPlanImportData(Map<String, Object> paramMap) throws Exception;
	/**
	 * 将临时表校验通过的数据写到正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月5日
	 */
	void insertTempDataToRegula(Map<String, String> paramMap)throws Exception;
	/**
	 * 修改已写入正式表的数据的导入状态
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月5日
	 */
	void updateImportStatus(Map<String, String> paramMap)throws Exception;
	/**
	 * 删除已导入临时表的数据
	 * @param paramMap
	 * @author zmj
	 * @date 2018年10月5日
	 */
	void deleteImportedDataFromTemp(Map<String, String> paramMap)throws Exception;
	/**
	 * 获取生产计划
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月5日
	 */
	void getProPlan(PupProPlanPageModel model)throws Exception;
	
}
