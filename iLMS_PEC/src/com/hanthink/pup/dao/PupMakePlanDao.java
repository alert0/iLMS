package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.PupMakePlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:取货计划生成持久层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月27日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupMakePlanDao extends Dao<String, PupMakePlanModel>{
	/**
	 * 取货计划生成查询业务持久层接口
	 * @param model 请求参数
	 * @param page 页面参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryForPage(PupMakePlanModel model,Page page)throws Exception;
	/**
	 * 将物流信息发布到信息共享平台持久层接口
	 * @param planList
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	void publishToPlatform(List<PupMakePlanModel> planList)throws Exception;
	/**
	 * 设置数据状态
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	void checkTempData(Map<String, String> paramMap)throws Exception;
	/**
	 * 获取可发布的正确数据的条数
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	Integer getCountForRightPlan(String uuid)throws Exception;
	/**
	 * 将数据更新至正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	void publishPlan(Map<String, String> paramMap)throws Exception;
	/**
	 *  取货计划生成持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	Integer makeLogisticsPlan(Map<String, Object> paramMap)throws Exception;
	/**
	 * 范围趟次表数据导出持久层接口
	 * @param factoryCode 工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	List<PupMakePlanModel> queryTripTimesForExport(String factoryCode)throws Exception;
	/**
	 * 导出取货时间数据持久层接口
	 * @param factoryCode 工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryPickupTimesForExport(String factoryCode)throws Exception;
	/**
	 * 取货计划DCS数据导出持久层接口
	 * @param model 当前用户登录的工厂
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryPlanDCSForExport(PupMakePlanModel model)throws Exception;
	/**
	 * 供应商备货数据导出持久层接口
	 * @param model 页面查询条件数据封装
	 * @return
	 * @author zmj
	 * @date 2018年7月27日
	 */
	List<PupMakePlanModel> querySupplierStockNumForExport(PupMakePlanModel model)throws Exception;
	List<PupMakePlanModel> querySupplierStockNumForExport1(PupMakePlanModel model)throws Exception;
	/**
	 * 收货数据导出持久层接口
	 * @param factoryCode 当前用户登录工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryPickDataForExport(String factoryCode)throws Exception;
	/**
	 * 箱种数据导出持久层接口
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryBoxForExport(Map<String, String> paramMap)throws Exception;
	/**
	 * 获取订单发布状态
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月27日
	 */
	Integer queryPublishFlag(String factoryCode)throws Exception;
	/**
	 * 将订单发布状态改为未发布
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月27日
	 */
	void updatePublishFlag()throws Exception;
	/**
	 * 将订单发布状态改为已发布
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月27日
	 */
	void updateCurrPublished()throws Exception;
	/**
	 * 记录发布版本信息
	 * @param map
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月13日
	 */
	void recordVersionMessage(Map<String, Object> map)throws Exception;
	/**
	 * 导出理论取货时间
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月15日
	 */
	List<PupMakePlanModel> queryForDelayTime(String factoryCode)throws Exception;
}
