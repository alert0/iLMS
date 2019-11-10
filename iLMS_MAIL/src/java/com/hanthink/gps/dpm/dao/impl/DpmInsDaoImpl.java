package com.hanthink.gps.dpm.dao.impl;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.dpm.dao.DpmInsDao;
import com.hanthink.gps.dpm.vo.DpmInsVo;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

public class DpmInsDaoImpl extends BaseDaoSupport implements DpmInsDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<DpmInsVo>  getDpmUserMail(Map<String, Object> map) {
		
		return (List<DpmInsVo>) this.executeQueryForList("dpm.select_getDpmUserMail", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DpmInsVo> getDpmNotSubmit(Map<String, Object> map) {
		
		return (List<DpmInsVo>) this.executeQueryForList("dpm.select_getDpmNotSubmit", map);
	}

}
