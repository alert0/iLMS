package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.PupMakePlanDao;
import com.hanthink.pup.model.PupMakePlanModel;
import com.hanthink.pup.model.PupMakePlanPageModel;
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
	public List<PupMakePlanModel> queryForPage(PupMakePlanPageModel model,Page page) throws Exception {
		return this.getByKey("queryForPage", model,page);
	}
	/**
	 * 取货计划生成持久层实现方法
	 * @param factoryCode 工厂代码
	 * @return Map<String,Object>
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public Map<String, Object> makeLogisticsPlan(Map<String, Object> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("makeLogisticsPlan", paramMap);
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
	public List<PupMakePlanModel> queryPlanDCSForExport(String factoryCode) throws Exception {
		return this.sqlSessionTemplate.selectList("queryPlanDCS", factoryCode);
	}
	/**
	 * 供应商备货数据导出持久层实现方法
	 * @param model 页面查询条件数据封装
	 * @return
	 * @author zmj
	 * @date 2018年7月27日
	 */
	@Override
	public List<PupMakePlanModel> querySupplierStockNumForExport(PupMakePlanPageModel model) {
		return this.sqlSessionTemplate.selectList("querySupplierStockNum", model);
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

}
