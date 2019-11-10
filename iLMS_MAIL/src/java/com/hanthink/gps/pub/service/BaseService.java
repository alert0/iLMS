package com.hanthink.gps.pub.service;
public interface BaseService {
	
	/**
	 * 取message信息 
	 * @param key message id
	 * @param params 参数
	 * @return 
	 */
	public String getText(String key,Object...params);
}
