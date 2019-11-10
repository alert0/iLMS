package com.hanthink.pup.manager;

import java.util.List;

import com.hanthink.pup.model.PupMakePlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:取货计划生成业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月27日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupMakePlanManager extends Manager<String, PupMakePlanModel>{
	/**
	 * 取货计划生成查询业务业务层接口
	 * @param model 请求参数
	 * @param page 页面参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	PageList<PupMakePlanModel> queryForPage(PupMakePlanModel model,Page page)throws Exception;
	/**
	 * 发布物流计划到信息共享平台业务接口
	 * @param ipAddr 
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	void publishToPlatform(String ipAddr)throws Exception;
	/**
	 * 取货计划生成业务层接口
	 * @param factoryCode 工厂代码
	 * @return int
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	int makeLogisticsPlan(String factoryCode)throws Exception;
	/**
	 * 范围趟次表数据导出业务层接口
	 * @param factoryCode 工厂代码
	 * @return
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryTripTimesForExport(String factoryCode)throws Exception;
	/**
	 * 导出取货时间数据业务层接口
	 * @param factoryCode 工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryPickupTimesForExport(String factoryCode)throws Exception;
	/**
	 * 取货计划DCS导出业务层接口
	 * @param model 界面查询条件
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryPlanDCSForExport(PupMakePlanModel model)throws Exception;
	/**
	 * 供应商备货数据导出业务层接口
	 * @param model 请求参数
	 * @return	与查询结果匹配的数据信息
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> querySupplierStockNumForExport(PupMakePlanModel model)throws Exception;
	/**
	 * 取货数据导出业务层接口
	 * @param factoryCode 工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	List<PupMakePlanModel> queryPickDataForExport(String factoryCode)throws Exception;

	/**
	 * 导出箱种数据业务接口
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月27日
	 */
	List<PupMakePlanModel> exportBoxForExcel(String factoryCode)throws Exception;
	/**
	 * 获取当前订单信息的发布状态
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月27日
	 */
	Integer queryPublishFlag()throws Exception;
	/**
	 * 查询理论取货时间
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月15日
	 */
	List<PupMakePlanModel> queryForDelayTime()throws Exception;
}
