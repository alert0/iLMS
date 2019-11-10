package com.hotent.sys.log.listener;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.sys.api.event.LogEvent;
import com.hotent.sys.api.event.LogModuleCons;
import com.hotent.sys.api.event.LogOperatorCons;
import com.hotent.sys.persistence.manager.LogManager;
import com.hotent.sys.persistence.manager.LogModuleManager;
import com.hotent.sys.persistence.model.Log;
import com.hotent.sys.util.ContextUtil;

/**
 * 日志记录监听器。
 * 
 * <pre>
 * 构建组：x5-sys-core
 * 作者：ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-28-下午3:27:58
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class LogListener implements ApplicationListener<LogEvent> {
	
	@Resource
	private LogManager logManager; // 操作日志管理
	
	@Resource
	private LogModuleManager logModuleManager; // 日志模块管理
	
	@Override
	public void onApplicationEvent(LogEvent logEvent) {
		if (logEvent == null)
			return;
		Boolean isEnabled = this.isEnabledModule(logEvent.getModule());
		if (isEnabled) {
			Log log = this.getLogByLogEvent(logEvent);
			logManager.create(log);
		}
	}

	/**
	 * 判断该模块是否是启动的，默认是不启动的
	 * 
	 * @param subModule 子模块为标准
	 * @return
	 */
	private Boolean isEnabledModule(LogModuleCons moduleEnum) {
		String module = moduleEnum.value(); // 模块名称
		return logModuleManager.isEnabled(module);
	}

	/**
	 * 获得持久化log对象
	 * 
	 * @param logEvent
	 * @return
	 */
	private Log getLogByLogEvent(LogEvent logEvent) {
		String id = UniqueIdUtil.getSuid();
		LogModuleCons moduleEnum = logEvent.getModule(); // 模块枚举
		String module = moduleEnum.value(); // 模块名称
		String subModule = logEvent.getSubModule(); // 子模块
		String content = (String) logEvent.getSource(); // 事件内容
		String operatorId = ContextUtil.getCurrentUser().getUserId(); // 操作人id
		LogOperatorCons operatorEnum = logEvent.getOperator(); // 具体的操作枚举
		String operator = operatorEnum.value(); // 具体的操作名称
		Date createTime = new Date();
		return new Log(id, module, subModule, content, operatorId, operator,
				createTime);
	}

}
