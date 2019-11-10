package com.hanthink.gps.pkg.dao.impl;

import java.util.Map;

import com.hanthink.gps.pkg.dao.PkgPartDao;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

public class PkgPartDaoImpl extends BaseDaoSupport implements PkgPartDao{

	@Override
	public Integer getPkgPartCount(Map<String, Object> map) {
		
//		return (Integer) this.getSqlMapClientTemplate().queryForObject("pkg.select_getPkgPartCount", map);
		return (Integer) this.executeQueryForObject("pkg.select_getPkgPartCount", map);
	}

	@Override
	public void updatePkgPartEmailStatus(Map<String, Object> map) {
		this.executeUpdate("pkg.update_updatePkgPartEmailStatus", map);
	}

	@Override
	public Integer getPkgProposalCount(Map<String, Object> map) {
		
		return (Integer) this.executeQueryForObject("pkg.select_getPkgProposalCount", map);
	}

	@Override
	public void updatePkgProposalEmailStatus(Map<String, Object> map) {
		this.executeUpdate("pkg.update_updatePkgProposalEmailStatus", map);
	}

	@Override
	public void updateEmailFlag(Map<String, Object> map) {
		this.executeUpdate("pkg.update_updatePkgPartEmailStatus", map);
	}

	@Override
	public void updateEmailFlagPro(Map<String, Object> map) {
		
		this.executeUpdate("pkg.update_updatePkgProposalEmailStatus", map);
	}

}
