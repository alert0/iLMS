package com.hanthink.gps.system.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.system.dao.SysErrorDao;
import com.hanthink.gps.system.vo.ProErrorVO;

public class SysErrorDaoImpl extends BaseDaoSupport implements SysErrorDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProErrorVO> queryProErrorInfoList() {
		return (List<ProErrorVO>)this.executeQueryForList("system.select_queryProErrorInfoList");
	}

}
