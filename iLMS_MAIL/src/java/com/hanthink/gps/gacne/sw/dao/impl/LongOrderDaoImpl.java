package com.hanthink.gps.gacne.sw.dao.impl;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.gacne.sw.dao.LongOrderDao;
import com.hanthink.gps.gacne.sw.vo.LongOrderVo;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

public class LongOrderDaoImpl extends BaseDaoSupport implements LongOrderDao{

	@Override
	public List<LongOrderVo> getLongOrderSupplier(Map<String, Object> map) {
		
		return (List<LongOrderVo>) this.executeQueryForList("gacne_sw.getLongOrderSupplier", map);
	}

	@Override
	public void updateEmailFlag(Map<String, Object> map) {
		this.executeUpdate("gacne_sw.updateEmailFlag", map);
	}

	@Override
	public List<LongOrderVo> getWeekForecasetSupplier(Map<String, Object> map) {
		
		return (List<LongOrderVo>) this.executeQueryForList("gacne_sw.getWeekForecasetSupplier", map);
	}

	@Override
	public List<LongOrderVo> getMonthForecasetSupplier(Map<String, Object> map) {
		
		return (List<LongOrderVo>) this.executeQueryForList("gacne_sw.getMonthForecasetSupplier", map);
	}

	@Override
	public void updateMonthEmailFlag(Map<String, Object> map) {
		this.executeUpdate("gacne_sw.updateMonthEmailFlag", map);
	}

	@Override
	public void updateWeekEmailFlag(Map<String, Object> map) {
		this.executeUpdate("gacne_sw.updateWeekEmailFlag", map);
	}

	@Override
	public List<LongOrderVo> getExcepOrderSupplier(Map<String, Object> map) {
		
		return (List<LongOrderVo>) this.executeQueryForList("gacne_sw.getExcepOrderSupplier", map);
	}

	@Override
	public void updateExcepOrderMail(Map<String, Object> map) {
		this.executeUpdate("gacne_sw.updateExcepOrderMail", map);
	}

	@Override
	public List<LongOrderVo> getAfterOrderSupplier(Map<String, Object> map) {
		
		return (List<LongOrderVo>) this.executeQueryForList("gacne_sw.getAfterOrderSupplier", map);
	}

	@Override
	public void updateAfterOrderMail(Map<String, Object> map) {
		this.executeUpdate("gacne_sw.updateAfterOrderMail", map);
	}

	@Override
	public void updateNonOrderMail(Map<String, Object> map) {
		this.executeUpdate("gacne_sw.updateNonOrderMail", map);
	}

	@Override
	public List<LongOrderVo> getNonOrderSupplier(Map<String, Object> map) {
		
		return (List<LongOrderVo>) this.executeQueryForList("gacne_sw.getNonOrderSupplier", map);
	}

}
