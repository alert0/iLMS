package com.hotent.sys.service.impl;

import org.springframework.stereotype.Service;

import com.hotent.sys.api.util.ISysCoreUtilService;
import com.hotent.sys.persistence.util.FilterJsonStructUtil;

/**
 * 
 * @author liyg 2014-04-09
 *
 */
@Service
public class DefaultSysCoreUtilService implements ISysCoreUtilService {

	@Override
	public String getSql(String filterJson, String dbType) {
		return FilterJsonStructUtil.getSql(filterJson, dbType);
	}
	
}
