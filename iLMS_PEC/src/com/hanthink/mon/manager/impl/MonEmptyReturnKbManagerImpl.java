package com.hanthink.mon.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonEmptyReturnKbDao;
import com.hanthink.mon.manager.MonEmptyReturnKbManager;
import com.hanthink.mon.model.EmptyReturnKbModel;
import com.hotent.base.db.api.Dao;
import com.hotent.sys.util.ContextUtil;


/**
 * @Description: 空箱返还信息
 * @author luocc
 * @date 2018年11月3日
 */
@Service("MonEmptyReturnKbManager")
public class MonEmptyReturnKbManagerImpl extends AbstractManagerImpl<String,EmptyReturnKbModel> implements MonEmptyReturnKbManager{
	@Resource
	private MonEmptyReturnKbDao monEmptyReturnKbDao;
	
	@Override
	protected Dao<String, EmptyReturnKbModel> getDao() {
		return monEmptyReturnKbDao;
	}

    /**
     * 查询DCS单头
     */
	@Override
	public List<EmptyReturnKbModel> selectDCS(String retEmptyPlatform,String workCenter)throws Exception {
		return monEmptyReturnKbDao.selectDCS(retEmptyPlatform,workCenter);
	}

	/**
	 * 查询DCS单明细
	 */
	@Override
	public List<EmptyReturnKbModel> selectDCSDetail(EmptyReturnKbModel model)throws Exception {
		return monEmptyReturnKbDao.selectDCSDetail(model);
	}

	/**
	 * 若为取货订单
	 */
	@Override
	public List<EmptyReturnKbModel> selectOrderNoBySw(EmptyReturnKbModel emptyReturnKbModel)throws Exception {
		return monEmptyReturnKbDao.selectOrderNoBySw(emptyReturnKbModel);
	}

	/**
	 * 若为拉动订单
	 */
	@Override
	public List<EmptyReturnKbModel> selectOrderNoByJit(EmptyReturnKbModel emptyReturnKbModel)throws Exception {
		return monEmptyReturnKbDao.selectOrderNoByJit(emptyReturnKbModel);
	}

	/**
	 * 若为同步订单
	 */
	@Override
	public List<EmptyReturnKbModel> selectOrderNoByJiso(EmptyReturnKbModel emptyReturnKbModel)throws Exception{
		return monEmptyReturnKbDao.selectOrderNoByJiso(emptyReturnKbModel);
	}

	/**
	 * 根据IP获取站台
	 */
	@Override
	public EmptyReturnKbModel getShowMessage(Map<String, String> paramMap)throws Exception {
		EmptyReturnKbModel model = monEmptyReturnKbDao.getShowMessage(paramMap);
		return model;
	}

	/**
	 * 查询订单号
	 */
	@Override
	public List<EmptyReturnKbModel> selectOrderNoByNull() {
		return monEmptyReturnKbDao.selectOrderNoByNull();
	}

	/**
	 * 查询看板IP
	 */
	@Override
	public EmptyReturnKbModel selectIpByCombIp(String combIp) {
		return monEmptyReturnKbDao.selectIpByCombIp(combIp);
	}

	/**
	 * 更新DCS单状态
	 * @throws Exception 
	 */
	@Override
	public void updateDCS(Map<String, String> paramMap) throws Exception {
		List<EmptyReturnKbModel> modelList = monEmptyReturnKbDao.selectDCSUp(paramMap.get("retEmptyPlatform"));
		EmptyReturnKbModel model = new EmptyReturnKbModel();
		String planSheetNo  = modelList.get(0).getPlanSheetNo();
		model.setPlanSheetNo(planSheetNo);
		model.setOpeUser(paramMap.get("opeUser"));
		model.setFactoryCode(paramMap.get("factoryCode"));
		monEmptyReturnKbDao.updateDCS(model);
	}

	/**
	 * 获取各种时间
	 */
	@Override
	public List<EmptyReturnKbModel> selectPubSysParam() {
	   return monEmptyReturnKbDao.selectPubSysParam();
	}

	/**
	 * 根据IP获取车间
	 */
	@Override
	public String selectWorkCenterByIp(String ip) {
		return monEmptyReturnKbDao.selectWorkCenterByIp(ip);
	}

	/**
	 * 查询DCS单头信息
	 */
	@Override
	public List<EmptyReturnKbModel> selectDCSTop(String retEmptyPlatform,String workCenter) {
		return monEmptyReturnKbDao.selectDCSTop(retEmptyPlatform,workCenter);
	}
		
}
