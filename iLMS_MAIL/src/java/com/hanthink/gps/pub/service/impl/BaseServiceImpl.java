package com.hanthink.gps.pub.service.impl;

import com.hanthink.gps.pub.service.BaseService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class BaseServiceImpl implements BaseService{
	/**
	 * 取message信息 
	 * @param key message id
	 * @param params 参数
	 * @return 
	 */
	public String getText(String key, Object... params) {
		String message = LocalizedTextUtil.findDefaultText(key, ActionContext.getContext().getLocale());
		if(params.length > 0){
			return String.format(message, params); 
		}
		return message;
	}
}
