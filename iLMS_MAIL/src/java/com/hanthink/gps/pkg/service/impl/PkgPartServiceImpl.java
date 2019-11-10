package com.hanthink.gps.pkg.service.impl;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.gacne.pecNon.vo.NonStandardVo;
import com.hanthink.gps.pkg.dao.PkgPartDao;
import com.hanthink.gps.pkg.service.PkgPartService;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

public class PkgPartServiceImpl extends BaseServiceImpl implements
PkgPartService{
	private PkgPartDao dao;
	
	
	@Override
	public Integer getPkgPartCount(Map<String, Object> map) {
		Integer count = dao.getPkgPartCount(map);

		return count;
	}
	public PkgPartDao getDao() {
		return dao;
	}
	public void setDao(PkgPartDao dao) {
		this.dao = dao;
	}
	@Override
	public Integer getPkgProposalCount(Map<String, Object> map) {
		Integer count = dao.getPkgProposalCount(map);
		
		
		return count;
	}
	@Override
	public void updateEmailFlag(Map<String, Object> map) {
		dao.updateEmailFlag(map);
	}
	@Override
	public void updateEmailFlagPro(Map<String, Object> map) {
		dao.updateEmailFlagPro(map);
	}

}
