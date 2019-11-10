package com.hotent.sys.api.event;

import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件。
 * <pre> 
 * 构建组：x5-sys-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-10-28-下午3:08:02
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class LogEvent extends ApplicationEvent{

	/**
	 * 大模块，比如流程操作
	 */
	private LogModuleCons module;
	
	/**
	 * 小模块，比如流程定义
	 */
	private String subModule="";
	
	/**
	 * 操作
	 */
	private LogOperatorCons operator;
	
	public LogEvent(Object source) {
		super(source);
	}

	public LogEvent(Object source, LogModuleCons module, String subModule,
			LogOperatorCons operator) {
		super(source);
		this.module = module;
		this.subModule = subModule;
		this.operator = operator;
	}

	public LogModuleCons getModule() {
		return module;
	}

	public void setModule(LogModuleCons module) {
		this.module = module;
	}

	public String getSubModule() {
		return subModule;
	}

	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}

	public LogOperatorCons getOperator() {
		return operator;
	}

	public void setOperator(LogOperatorCons operator) {
		this.operator = operator;
	}
	

}
