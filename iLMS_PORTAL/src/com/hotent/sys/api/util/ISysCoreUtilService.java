package com.hotent.sys.api.util;


public interface ISysCoreUtilService {
	
	/**
	 * FilterJsonStructUtil.getSql(filterJson,dbType);
	 * @param filterJson
	 * @param dbType
	 * @return
	 */
	public String getSql(String filterJson, String dbType);
	
}
