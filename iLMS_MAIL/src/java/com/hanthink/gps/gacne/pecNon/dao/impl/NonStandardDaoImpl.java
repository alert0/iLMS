package com.hanthink.gps.gacne.pecNon.dao.impl;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.gacne.pecNon.dao.NonStandardDao;
import com.hanthink.gps.gacne.pecNon.vo.NonStandardVo;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

public class NonStandardDaoImpl extends BaseDaoSupport implements NonStandardDao{

	@Override
	public Integer getDateCount(String dateCount) {
		
		return (Integer) this.executeQueryForObject("pec_non.getDateCount", dateCount);
	}

	@Override
	public List<NonStandardVo> getNonStandardCount(Map<String, Object> map) {
		
		return (List<NonStandardVo>) this.executeQueryForList("pec_non.getNonStandardCount", map);
	}

	@Override
	public void updateEmailFlag(Map<String, Object> map) {
		this.executeUpdate("pec_non.updateEmailFlag", map);
	}

}
