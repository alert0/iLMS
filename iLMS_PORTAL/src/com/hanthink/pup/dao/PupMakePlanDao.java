package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.PupMakePlanModel;
import com.hanthink.pup.model.PupMakePlanPageModel;
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
	List<PupMakePlanModel> queryForPage(PupMakePlanPageModel model,Page page)throws Exception;
	/**
	 * 取货计划生成持久层接口
	 * @param factoryCode 工厂代码
	 * @return int
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	Map<String, Object> makeLogisticsPlan(Map<String, Object> paramMap)throws Exception;
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
	 * @param factoryCode 当前用户登录的工厂
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryPlanDCSForExport(String factoryCode)throws Exception;
	/**
	 * 供应商备货数据导出持久层接口
	 * @param model 页面查询条件数据封装
	 * @return
	 * @author zmj
	 * @date 2018年7月27日
	 */
	List<PupMakePlanModel> querySupplierStockNumForExport(PupMakePlanPageModel model)throws Exception;
	/**
	 * 收货数据导出持久层接口
	 * @param factoryCode 当前用户登录工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryPickDataForExport(String factoryCode)throws Exception;
}
