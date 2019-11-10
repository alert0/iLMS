package com.hanthink.gps.gacne.sw.service.impl;

import java.util.List;

import com.hanthink.gps.gacne.sw.dao.PickupPlanDao;
import com.hanthink.gps.gacne.sw.service.PickupPlanService;
import com.hanthink.gps.gacne.sw.vo.PickupPlanVo;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

public class PickupPlanServiceImpl extends BaseServiceImpl implements PickupPlanService {
	
	private PickupPlanDao dao;
	

		public PickupPlanDao getDao() {
		return dao;
	}

	public void setDao(PickupPlanDao dao) {
		this.dao = dao;
	}

	@Override
	public List<PickupPlanVo> queryPickupPlanInfo() {

		return dao.queryPickupPlanInfo();
	}

	@Override
	public List<PickupPlanVo> queryPickupPlanNum(String user) {
		
		return dao.queryPickupPlanNum(user);
	}

	
}
