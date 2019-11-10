package com.hanthink.gps.gacne.sw.service.impl;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.gacne.sw.dao.LongOrderDao;
import com.hanthink.gps.gacne.sw.service.LongOrderService;
import com.hanthink.gps.gacne.sw.vo.LongOrderVo;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

public class LongOrderServiceImpl extends BaseServiceImpl implements LongOrderService{
	
	private LongOrderDao dao;
	

	public LongOrderDao getDao() {
		return dao;
	}

	public void setDao(LongOrderDao dao) {
		this.dao = dao;
	}

	@Override
	public List<LongOrderVo> getLongOrderSupplier(Map<String, Object> map) {
		
		return dao.getLongOrderSupplier(map);
	}

	@Override
	public void updateEmailFlag(Map<String, Object> map) {
		dao.updateEmailFlag(map);
	}

	@Override
	public List<LongOrderVo> getWeekForecasetSupplier(Map<String, Object> map) {
		
		return dao.getWeekForecasetSupplier(map);
	}

	@Override
	public List<LongOrderVo> getMonthForecasetSupplier(Map<String, Object> map) {
		
		return dao.getMonthForecasetSupplier(map);
	}

	@Override
	public void updateMonthEmailFlag(Map<String, Object> map) {
		dao.updateMonthEmailFlag(map);
	}

	@Override
	public void updateWeekEmailFlag(Map<String, Object> map) {
		dao.updateWeekEmailFlag(map);
	}

	@Override
	public List<LongOrderVo> getExcepOrderSupplier(Map<String, Object> map) {
		
		return dao.getExcepOrderSupplier(map);
	}

	@Override
	public void updateExcepOrderMail(Map<String, Object> map) {
		dao.updateExcepOrderMail(map);
	}

	@Override
	public List<LongOrderVo> getAfterOrderSupplier(Map<String, Object> map) {
		
		return dao.getAfterOrderSupplier(map);
	}

	@Override
	public void updateAfterOrderMail(Map<String, Object> map) {
		dao.updateAfterOrderMail(map);
	}

	@Override
	public void updateNonOrderMail(Map<String, Object> map) {
		dao.updateNonOrderMail(map);
	}

	@Override
	public List<LongOrderVo> getNonOrderSupplier(Map<String, Object> map) {
		
		return dao.getNonOrderSupplier(map);
	}

}
