package com.hanthink.gps.gacne.sw.dao.impl;

import java.util.List;

import com.hanthink.gps.gacne.sw.dao.PickupPlanDao;
import com.hanthink.gps.gacne.sw.vo.PickupPlanVo;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

public class PickupPlanDaoImpl extends BaseDaoSupport implements PickupPlanDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PickupPlanVo> queryPickupPlanInfo() {

		return (List<PickupPlanVo>) this.executeQueryForList("gacne_sw.select_queryPickupPlanInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PickupPlanVo> queryPickupPlanNum(String user) {
		
		return (List<PickupPlanVo>) this.executeQueryForList("gacne_sw.select_queryPickupPlanNum",user);
	}
	
}
