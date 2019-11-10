package com.hanthink.gps.dpm.service.impl;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.dpm.dao.DpmInsDao;
import com.hanthink.gps.dpm.service.DpmInsService;
import com.hanthink.gps.dpm.vo.DpmInsVo;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

public class DpmInsServiceImpl extends BaseServiceImpl implements
DpmInsService{
	
	private DpmInsDao dao;

	public DpmInsDao getDao() {
		return dao;
	}

	public void setDao(DpmInsDao dao) {
		this.dao = dao;
	}

	@Override
	public List<DpmInsVo>  getDpmUserMail(Map<String, Object> map) {
		
		return dao.getDpmUserMail(map);
	}

	@Override
	public List<DpmInsVo> getDpmNotSubmit(Map<String, Object> map) {
		
		return dao.getDpmNotSubmit(map);
	}

	
}
