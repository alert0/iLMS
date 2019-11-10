package com.hotent.base.persistence.manager;

import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.AppUtil;

public class CacheUtil {
	
	public static final String RESOURCE_URL="RES_URL_";
	
	public static final String RESOURCE_RES="SYS_RES_";
	
	public static final String RESOURCE_USER_RES="SYS_USER_RES_";
	
	/** 请求资源  */
	public static final String RESOURCE_REQ_RES="SYS_REQ_RES_";
	/** 请求资源类型  */
	public static final String RESOURCE_REQ_RES_TYPE="SYS_REQ_RES_TYPE_";
	/** 请求用户资源 */
	public static final String RESOURCE_REQ_USER_RES="SYS_REQ_USER_RES_";
	
	/**
	 * 清理系统的资源和URL对应角色的缓存。
	 * @param systemId
	 */
	public static void cleanResCache(String systemId) {
		ICache iCache=AppUtil.getBean(ICache.class);
		String urlStr=RESOURCE_URL + systemId;
		String resStr=RESOURCE_RES + systemId;
		iCache.delByKey(urlStr);
		iCache.delByKey(resStr);
		
		iCache.delByKey(RESOURCE_REQ_RES + systemId);
		iCache.delByKey(RESOURCE_REQ_RES_TYPE + systemId);
	}
	
	public static void cleanCache(String key){
		ICache iCache=AppUtil.getBean(ICache.class);
		iCache.delByKey(key);
	}

}
