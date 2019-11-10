package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.PupMakePlanDao;
import com.hanthink.pup.model.PupMakePlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:取货计划生成持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月27日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PupMakePlanDaoImpl extends MyBatisDaoImpl<String, PupMakePlanModel>
				implements PupMakePlanDao{

	@Override
	public String getNamespace() {
		return PupMakePlanModel.class.getName();
	}
	/**
	 * 取货计划生成查询业务持久层实现方法
	 * @param model 请求参数
	 * @param page 页面参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryForPage(PupMakePlanModel model,Page page) throws Exception {
		return this.getByKey("queryForPage", model,page);
	}
	/**
	 * 将物流信息发布到信息共享平台持久层实现方法
	 * @param list
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public void publishToPlatform(List<PupMakePlanModel> list) throws Exception {
		this.insertBatchByKey("publishToPlatform", list);
	}
	/**
	 * 设置数据状态
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public void checkTempData(Map<String, String> paramMap) throws Exception {
		this.sqlSessionTemplate.selectOne("checkPublishTempData",paramMap);
	}
	/**
	 * 获取可发布的正确数据的条数
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public Integer getCountForRightPlan(String uuid) throws Exception {
		return this.sqlSessionTemplate.selectOne("getCountForRightPlan", uuid);
	}
	
	/**
	 * 将数据更新至正式表
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public void publishPlan(Map<String, String> paramMap) throws Exception {
		this.sqlSessionTemplate.selectOne("publishPlan", paramMap);
	}
	/**
	 *  取货计划生成持久层实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public Integer makeLogisticsPlan(Map<String, Object> paramMap) throws Exception {
		this.sqlSessionTemplate.selectOne("makeLogisticsPlan", paramMap);
		return (Integer) paramMap.get("returnCode");
	}
	/**
	 * 范围趟次表数据导出持久层实现方法
	 * @param model 页面查询条件数据封装
	 * @return
	 * @author zmj
	 * @date 2018年7月27日
	 */
	@Override
	public List<PupMakePlanModel> queryTripTimesForExport(String factoryCode) throws Exception {
		return this.sqlSessionTemplate.selectList("queryTripTimes",factoryCode);
	}
	/**
	 * 导出取货时间数据持久层实现方法
	 * @param factoryCode 工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryPickupTimesForExport(String factoryCode) throws Exception {
		return this.sqlSessionTemplate.selectList("queryPickupTimes", factoryCode);
	}
	/**
	 * 取货计划DCS数据导出持久层实现方法
	 * @param factoryCode 当前用户登录的工厂
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryPlanDCSForExport(PupMakePlanModel model) throws Exception {
		return this.sqlSessionTemplate.selectList("queryPlanDCS", model);
	}
	/**
	 * 供应商备货数据导出持久层实现方法
	 * @param model 页面查询条件数据封装
	 * @return
	 * @author zmj
	 * @date 2018年7月27日
	 */
	@Override
	public List<PupMakePlanModel> querySupplierStockNumForExport(PupMakePlanModel model) {
		return this.sqlSessionTemplate.selectList("querySupplierStockNum", model);
	}
	@Override
	public List<PupMakePlanModel> querySupplierStockNumForExport1(PupMakePlanModel model) throws Exception {
		return this.sqlSessionTemplate.selectList("querySupplierStockNum1", model);
	}
	/**
	 * 收货数据导出持久层实现接口
	 * @param factoryCode 当前用户登录工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryPickDataForExport(String factoryCode) throws Exception {
		return this.sqlSessionTemplate.selectList("queryPickData", factoryCode);
	}
	/**
	 * 箱种数据导出持久层接口
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryBoxForExport(Map<String, String> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectList("queryBoxForExport", paramMap);
	}
	/**
	 * 获取订单发布状态
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月27日
	 */
	@Override
	public Integer queryPublishFlag(String factoryCode) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryPublishFlag", factoryCode);
	}
	/**
	 * 修改订单发布状态为未发布
	 */
	@Override
	public void updatePublishFlag() throws Exception {
		this.updateByKey("currUnpublished");
	}
	/**
	 * 修改订单发布状态为已发布
	 */
	@Override
	public void updateCurrPublished() throws Exception {
		this.updateByKey("currPublished");
	}
	/**
	 * 记录版本信息
	 */
	@Override
	public void recordVersionMessage(Map<String, Object> map) throws Exception {
		this.insertByKey("recordVersionMessage", map);
	}
	/**
	 * 导出理论取货时间
	 * @param factoryCode
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月15日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PupMakePlanModel> queryForDelayTime(String factoryCode) throws Exception {
		return (List<PupMakePlanModel>) this.getList("queryForDelayTime", factoryCode);
	}
}
