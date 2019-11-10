package com.hanthink.gps.pkg.dao;

import java.util.Map;


public interface PkgPartDao {

	Integer getPkgPartCount(Map<String, Object> map);

	void updatePkgPartEmailStatus(Map<String, Object> map);

	Integer getPkgProposalCount(Map<String, Object> map);

	void updatePkgProposalEmailStatus(Map<String, Object> map);

	void updateEmailFlag(Map<String, Object> map);

	void updateEmailFlagPro(Map<String, Object> map);



}
