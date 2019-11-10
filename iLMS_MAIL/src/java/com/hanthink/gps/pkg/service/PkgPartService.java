package com.hanthink.gps.pkg.service;

import java.util.Map;


public interface PkgPartService {

	Integer getPkgPartCount(Map<String, Object> map);

	Integer getPkgProposalCount(Map<String, Object> map);

	void updateEmailFlag(Map<String, Object> map);

	void updateEmailFlagPro(Map<String, Object> map);


}
