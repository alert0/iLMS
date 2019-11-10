package com.hotent.sys.api.event;

import com.hotent.base.core.util.AppUtil;


/**
 * 系统日志事件记录。
 * <pre> 
 * 构建组：x5-sys-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-10-29-上午11:23:39
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class LogEventUtil {
	
	/**
	 * 发布日志事件。
	 * @param content	事件内容
	 * @param module	模块
	 * @param subModule	子模块
	 * @param operator 	具体的操作，比如增加删除。
	 * void
	 */
	public static void publishEvent(String content,LogModuleCons module,String subModule,LogOperatorCons operator){
		LogEvent logEvent = new LogEvent(content);
		logEvent.setModule(module);
		logEvent.setSubModule(subModule);
		logEvent.setOperator(operator);
		
		AppUtil.publishEvent(logEvent);
	}

}
