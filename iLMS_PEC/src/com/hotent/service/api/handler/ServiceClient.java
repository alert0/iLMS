package com.hotent.service.api.handler;

import com.hotent.service.api.model.InvokeCmd;
import com.hotent.service.api.model.InvokeResult;

/**
 * 服务调用接口
 * @author heyifan
 * @version 创建时间: 2014-8-18
 */
public interface ServiceClient {
	InvokeResult invoke(InvokeCmd invokeCmd);
}
