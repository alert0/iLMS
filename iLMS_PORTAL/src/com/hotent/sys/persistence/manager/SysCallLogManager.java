package com.hotent.sys.persistence.manager;

import java.io.Serializable;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.api.call.ICallLogService;
import com.hotent.sys.api.model.SysCallLog;

/**
 * 
 * <pre> 
 * 描述：sys_call_log 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:zhangxw
 * 邮箱:zhangxw@jee-soft.cn
 * 日期:2017-10-26 11:40:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysCallLogManager <PK extends Serializable, SysCallLog> extends Manager<String,SysCallLog> ,ICallLogService{
	
}
