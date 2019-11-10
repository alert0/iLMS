package com.hotent.sys.jms;

import javax.jms.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

import com.hotent.sys.api.jms.JmsProducer;
/**
 * 
 * <pre> 
 * 描述：JmsVo对象的生成者，将JmsVo对象加到消息队列中
 * 构建组：x5-bpmx-core
 * 作者：winston yan
 * 邮箱:yancm@jee-soft.cn
 * 日期:2014-4-17-下午4:24:36
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class DefaultJmsProducer implements JmsProducer{
    private static final Log logger=LogFactory.getLog(DefaultJmsProducer.class);	
	
	private Queue messageQueue;
	private JmsTemplate jmsTemplate;
	
	public void setMessageQueue(Queue messageQueue) {
		this.messageQueue = messageQueue;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendToQueue(Object object) {	
		logger.debug("&&& procduce the message");
		//产生邮件\短信\消息发送的消息，加到消息队列中去
				
		jmsTemplate.convertAndSend(messageQueue, object);	
	}

}
