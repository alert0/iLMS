package com.hotent.sys.util;

import com.hotent.base.core.util.AppUtil;
import com.hotent.sys.persistence.manager.SysDataSourceManager;

public class SysDataSourceUtil {
	
	/**
	 * 获取数据源的数据库类型。
	 * @param alias
	 * @return
	 */
	public static String getDbType(String alias){
		SysDataSourceManager manager=AppUtil.getBean(SysDataSourceManager.class);
		return manager.getDbType(alias);
	}
	
	

}
