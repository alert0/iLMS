package com.hotent.sys.jms;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.JmsProducer;
import com.hotent.sys.api.jms.JmsProducerWithTemplate;
import com.hotent.sys.api.jms.constants.ContentType;
import com.hotent.sys.api.jms.constants.NeedSubject;
import com.hotent.sys.api.msg.model.DefaultMsgVo;
import com.hotent.sys.api.template.TemplateService;
import com.hotent.sys.api.template.model.TemplateVo;

@Service
public class DefaultJmsProducerWithTemplate implements JmsProducerWithTemplate{
	
	@Autowired(required=false)
	JmsProducer jmsProducer;
	
	@Autowired(required=false)
	TemplateService templateService;
	
	private final static Log logger = LogFactory.getLog(DefaultJmsProducerWithTemplate.class);

	@Override
	public void send(String msgType, TemplateVo templateVo,
			Map<String, Object> vars, IUser sender, List<IUser> recevierUsers,
			NeedSubject needSubject, ContentType contentType) {
			//无接收人则不处理
			if(recevierUsers==null || recevierUsers.size()==0)
				return;
			
			String subject = "";
			if(needSubject.equals(NeedSubject.YES)){
				subject = templateService.parseSubject(templateVo,vars);
			}		
			String content = "";
			//构造短信内容（无标题，使用无格式模板（Plain））
			if(contentType.equals(ContentType.PLAIN)){
				content = templateService.parsePlainContent(templateVo, vars);
			}else if(contentType.equals(ContentType.HTML)){
				content = templateService.parseHtmlContent(templateVo, vars);
			}					
			//构造短信消息实体，msgType通过构造函数设置进去（通过它来定位处理的handler）
			DefaultMsgVo msgVo = new DefaultMsgVo(subject,content,sender,recevierUsers,msgType);			
			if(jmsProducer==null){
				logger.debug("jmsProducer is null");
				return ;
			} 
			jmsProducer.sendToQueue(msgVo);
		
	}
	
	
}
