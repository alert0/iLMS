package com.hanthink.gps.gacne.pecNon.service.impl;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.gacne.pecNon.dao.NonStandardDao;
import com.hanthink.gps.gacne.pecNon.service.NonStandardService;
import com.hanthink.gps.gacne.pecNon.vo.NonStandardVo;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

public class NonStandardServiceImpl extends  BaseServiceImpl implements
NonStandardService{
	
	private NonStandardDao dao;
	
	@Override
	public Integer getDateCount(String dateCount) {
		
		return dao.getDateCount(dateCount);
	}

	@Override
	public List<NonStandardVo> getNonStandardCount(Map<String, Object> map) {
		
		return dao.getNonStandardCount(map);
	}
	
	@Override
	public void updateEmailFlag(Map<String, Object> map) {
		dao.updateEmailFlag(map);
	}
	
	public NonStandardDao getDao() {
		return dao;
	}

	public void setDao(NonStandardDao dao) {
		this.dao = dao;
	}



}
