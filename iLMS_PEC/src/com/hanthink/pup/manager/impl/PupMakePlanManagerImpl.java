package com.hanthink.pup.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.InvEmptyOutDao;
import com.hanthink.pup.dao.PupMakePlanDao;
import com.hanthink.pup.dao.PupPlanDao;
import com.hanthink.pup.manager.PupMakePlanManager;
import com.hanthink.pup.model.PupMakePlanModel;
import com.hanthink.pup.util.PupUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.identity.IdentityService;
import com.hotent.sys.util.ContextUtil;

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
	
	@Resource
	private PupPlanDao planDao;	
	
	@Resource
	private InvEmptyOutDao emptyOutDao;
	
	@Resource
	private IdentityService service;
	
	private final String PROPOSAL_STATUS = "5";
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
	public PageList<PupMakePlanModel> queryForPage(PupMakePlanModel model,Page page) throws Exception {
		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setPickDateEnd(PupUtil.getTargetDate(model.getPickDateEnd(), "yyyy-MM-dd", 1));
		
		List<PupMakePlanModel> list = makePlanDao.queryForPage(model,page);
		
		return new PageList<PupMakePlanModel>(list);
	}
	/**
	 * 发布物流计划到信息共享平台业务实现方法
	 * @param planList
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月27日
	 */
	@Override
	public void publishToPlatform(String ipAddr) throws Exception {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		IUser user = ContextUtil.getCurrentUser();
		String userName = user.getAccount();
		String factoryCode = user.getCurFactoryCode();
		PupMakePlanModel model = new PupMakePlanModel();
		model.setFactoryCode(factoryCode);
		List<PupMakePlanModel> planList = makePlanDao.querySupplierStockNumForExport1(model);
		if (planList.size() < 1) {
			throw new Exception("没有可发布订单信息");
		}
		for (PupMakePlanModel pupMakePlanModel : planList) {
			pupMakePlanModel.setId(UniqueIdUtil.getSuid());
			pupMakePlanModel.setUuid(uuid);
			pupMakePlanModel.setCreationUser(userName);
			pupMakePlanModel.setFactoryCode(factoryCode);
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		for (PupMakePlanModel pupMakePlanModel : planList) {
			if (StringUtil.isEmpty(pupMakePlanModel.getPickTime()) || StringUtil.isEmpty(pupMakePlanModel.getArriveTime())) {
				throw new Exception("请检查数据正确性");
			}
		}
		try {
			//将数据写入临时数据表
			makePlanDao.publishToPlatform(planList);
			//调用存储校要数据
			paramMap.put("opeIp", ipAddr);
			paramMap.put("uuid", uuid);
			paramMap.put("userName", userName);
			paramMap.put("errorFlag", "");
			paramMap.put("errorMsg", "");
			makePlanDao.checkTempData(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		//获取正确数据的条数
		Integer countRightPlan = makePlanDao.getCountForRightPlan(uuid);
		if (countRightPlan < 1) {
			throw new Exception("没有可发布订单信息");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", uuid);
		Integer countAllImport = planDao.getCountAllImport(map);
		if (countRightPlan.intValue() != countAllImport.intValue()) {
			throw new Exception("请检查数据正确性");
		}
		
		try {
			//将数据更新至正式数据表
			makePlanDao.publishPlan(paramMap);
			//生成空容器
			paramMap.put("resultCode", "0");
			emptyOutDao.makeEmptyContainer(paramMap);
			//修改订单发布状态为已发布
			makePlanDao.updateCurrPublished();
			//记录版本信息
			String verSionNo = service.genNextNo("versionNumber");
			map.put("versionNo", verSionNo);
			map.put("factoryCode", factoryCode);
			makePlanDao.recordVersionMessage(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}		
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
		
		paramMap.put("factoryCode", factoryCode);
		paramMap.put("returnCode", null);
		Integer resultCode = makePlanDao.makeLogisticsPlan(paramMap);
		//生成物流计划成功修改订单的发布状态
		if(resultCode == 1) {
			makePlanDao.updatePublishFlag();
		}
		return resultCode;
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
		List<PupMakePlanModel> list = null;
		try {
			list = makePlanDao.queryTripTimesForExport(factoryCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		if(list.size() < 1) {
			throw new Exception("没有可导出数据");
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
		List<PupMakePlanModel> list = null;
		try {
			list = makePlanDao.queryPickupTimesForExport(factoryCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		if (list.size() < 1) {
			throw new Exception("没有可导出数据");
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
	public List<PupMakePlanModel> queryPlanDCSForExport(PupMakePlanModel model) throws Exception {
		List<PupMakePlanModel> list = null;
		try {
			model.setPickDateEnd(PupUtil.getTargetDate(model.getPickDateEnd(), "yyyy-MM-dd", 1));
			list = makePlanDao.queryPlanDCSForExport(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		if (list.size() < 1) {
			throw new Exception("没有可导出数据");
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
	public List<PupMakePlanModel> querySupplierStockNumForExport(PupMakePlanModel model) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<PupMakePlanModel> list= null;
		try {
			list = makePlanDao.querySupplierStockNumForExport(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		if(list.size() < 1) {
			throw new Exception("没有可导出数据");
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
		List<PupMakePlanModel> list = null;
		try {
			 list = makePlanDao.queryPickDataForExport(factoryCode);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		if (list.size() < 1) {
			throw new Exception("没有可导出数据");
		}
		return list;
	}
	
	@Override
	public List<PupMakePlanModel> exportBoxForExcel(String factoryCode) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("factoryCode", factoryCode);
		paramMap.put("proposalStatus", PROPOSAL_STATUS);
		List<PupMakePlanModel> list = null;
		try {
			list = makePlanDao.queryBoxForExport(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		if (list.size() < 1) {
			throw new Exception("没有数据可导出");
		}
		int flag = 0;
		for (int i = flag;i < list.size() - 1;i++) {
			boolean routeBoolean = list.get(flag).getRouteCode().equals(list.get(i+1).getRouteCode());
			boolean totalBoolean = list.get(flag).getTotalNo().equals(list.get(i+1).getTotalNo());
			if (routeBoolean && totalBoolean) {
				list.get(i+1).setPickupType(null);
				list.get(i+1).setArea(null);
				list.get(i+1).setCarType(null);
				list.get(i+1).setRouteCode(null);
				list.get(i+1).setTotalNo(null);
				list.get(i+1).setMergeNo(null);
				list.get(i+1).setSupFactory(null);
				list.get(i+1).setSupplierName(null);
				list.get(i+1).setOrderNo(null);
				list.get(i+1).setPurchaseNo(null);
			}else {
				flag = i+1;
			}
		}
		return list;
	}
	/**
	 * 获取当前订单信息的发布状态
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月27日
	 */
	@Override
	public Integer queryPublishFlag() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return makePlanDao.queryPublishFlag(factoryCode);
	}
	/**
	 * 导出理论取货时间
	 * @param request
	 * @param response
	 * @author zmj
	 * @date 2019年2月15日
	 */
	@Override
	public List<PupMakePlanModel> queryForDelayTime() throws Exception {
		List<PupMakePlanModel> list = null;
		try {
			String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
			list = makePlanDao.queryForDelayTime(factoryCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		return list;
	}
}
