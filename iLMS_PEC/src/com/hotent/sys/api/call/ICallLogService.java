package com.hotent.sys.api.call;

import com.hotent.sys.api.model.SysCallLog;


/**
 * 接口调用日志接口类。
 * @author ray
 */
public interface ICallLogService {
	
	/**
	 * 创建日志。
	 * @param jobLog
	 */
	void create(SysCallLog callLog);

}
