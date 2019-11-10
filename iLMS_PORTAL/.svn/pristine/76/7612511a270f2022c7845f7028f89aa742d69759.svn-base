package com.hotent.sys.api.jms;

import java.util.List;

import com.hotent.base.core.util.AppUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;

/**
 * 系统消息工具类。
 * <pre> 
 * 构建组：x5-sys-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-5-12-上午9:43:56
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class MessageUtil {
	
	
	/**
	 * 获取jms Handler列表，用于配置消息通知类型。
	 * @return 
	 * List&lt;JmsHandler&lt;JmsVo>>
	 */
	public static List< JmsHandler<JmsVo>>  getHanlerList(){
		return (List< JmsHandler<JmsVo>>)AppUtil.getBean("jmsHandList");
	}
	
	/**
	 * 判断通知类型是否支持html。
	 * @param notifyType	通知类型为字符串。
	 * @return
	 */
	public static boolean isSupportHtml(String notifyType){
		List<JmsHandler<JmsVo>> list=getHanlerList();
		for(JmsHandler<JmsVo> handler:list){
			if(handler.getType().equalsIgnoreCase(notifyType)){
				return handler.getSupportHtml();
			}
		}
		return false;
	}
	 
}
