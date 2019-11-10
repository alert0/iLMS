package com.hanthink.pup.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pup.dao.PupMakePlanDao;
import com.hanthink.pup.manager.PupMakePlanManager;
import com.hanthink.pup.model.PupMakePlanModel;
import com.hanthink.pup.model.PupMakePlanPageModel;
import com.hanthink.pup.util.PupUtil;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * <pre> 
 * 功能描述:取货计划生成业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月27日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("makePlan")
public class PupMakePlanManagerImpl extends AbstractManagerImpl<String, PupMakePlanModel>
						implements PupMakePlanManager{
	@Resource
	PupMakePlanDao makePlanDao;
	
	@Override
	protected Dao<String, PupMakePlanModel> getDao() {
		return makePlanDao;
	}
	/**
	 * 取货计划生成查询业务业务层实现方法
	 * @param model 请求参数
	 * @param page 页面参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public PageList<PupMakePlanModel> queryForPage(PupMakePlanPageModel model,Page page) throws Exception {
		List<PupMakePlanModel> list = new ArrayList<>();
		
		if(!PupUtil.isAllFieldNull(model)) {
			list = makePlanDao.queryForPage(model,page);
		}
		
		return new PageList<PupMakePlanModel>(list);
	}
	/**
	 * 取货计划生成业务层实现方法
	 * @param factoryCode 工厂代码
	 * @return int
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public int makeLogisticsPlan(String factoryCode) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		
		paramMap.put("factoryCode", factoryCode);
		paramMap.put("returnCode", 0);
		resultMap = makePlanDao.makeLogisticsPlan(paramMap);
		
		return (int) resultMap.get("returnCode");
	}
	/**
	 * 范围趟次表数据导出业务实现方法
	 * @param model 请求参数
	 * @return	与查询结果匹配的数据信息
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryTripTimesForExport(String factoryCode) throws Exception {
		List<PupMakePlanModel> list = makePlanDao.queryTripTimesForExport(factoryCode);
		if(list.size() < 1) {
			throw new Exception("没有数据");
		}
		return list;
	}
	/**
	 * 导出取货时间数据业务实现方法
	 * @param factoryCode 工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryPickupTimesForExport(String factoryCode) throws Exception {
		List<PupMakePlanModel> list = makePlanDao.queryPickupTimesForExport(factoryCode);
		if (list.size() < 1) {
			throw new Exception("没有数据");
		}
		return list;
	}
	/**
	 * 取货计划DCS导出业务实现方法
	 * @param factoryCode 当前用户登录的工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryPlanDCSForExport(String factoryCode) throws Exception {
		List<PupMakePlanModel> list = makePlanDao.queryPlanDCSForExport(factoryCode);
		if (list.size() < 1) {
			throw new Exception("没有数据");
		}
		return list;
	}
	/**
	 * 供应商备货数据导出业务实现方法
	 * @param model 请求参数
	 * @return	与查询结果匹配的数据信息
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> querySupplierStockNumForExport(PupMakePlanPageModel model) throws Exception {
		List<PupMakePlanModel> list = new ArrayList<>();
		if (!PupUtil.isAllFieldNull(model)) {
			list = makePlanDao.querySupplierStockNumForExport(model);
			if(list.size() < 1) {
				throw new Exception("没有数据");
			}
		}
		return list;
	}
	/**
	 * 取货数据导出业务层实现方法
	 * @param factoryCode 工厂代码
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public List<PupMakePlanModel> queryPickDataForExport(String factoryCode) throws Exception {
		List<PupMakePlanModel> list  = makePlanDao.queryPickDataForExport(factoryCode);
		if (list.size() < 1) {
			throw new Exception("没有数据");
		}
		return list;
	}
}
