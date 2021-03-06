package com.hotent.sys.api.jms.handler;

import com.hotent.sys.api.jms.model.JmsVo;
/**
 * 
 * <pre> 
 * 描述： 发送消息处理接口
 * 	 	需要实现这个接口并在x5-bpmx-jms.xml 配置处理消息的model及实现的类
 * 		目前实现了 1.发送邮件消息。 2.发送短消息。 3.发送内部消息。
 * 构建组：x5-bpmx-api
 * 作者：winston yan
 * 邮箱:yancm@jee-soft.cn
 * 日期:2014-4-17-下午4:18:37
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface JmsHandler<T extends JmsVo> {
	/**
	 * 返回该Handler对应的消息类型键。
	 * 系统自带的则从MsgType枚举中获得，扩展的可以加枚举，也可以直接写值。
	 * @return  String
	 */
	String getType();
	/**
	 * 标题。
	 * @return String
	 */
	String getTitle();
	/**
	 * 是否默认处理器
	 * @return   boolean
	 */
	boolean getIsDefault();
	/**
	 * 是否支持html。
	 * @return   boolean
	 */
	boolean getSupportHtml();
	/**
	 * 处理JmsVo，如发送、持久化等操作。
	 * @param JmsVo
	 * @return  boolean
	 */
	boolean send(T vo);
}
