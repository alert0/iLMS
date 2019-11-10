package com.hotent.sys.api.jms;

import java.util.List;
import java.util.Map;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.constants.ContentType;
import com.hotent.sys.api.jms.constants.NeedSubject;
import com.hotent.sys.api.template.model.TemplateVo;




public interface JmsProducerWithTemplate {
	
	
	
	
	public void send(String msgType,TemplateVo templateVo,Map<String, Object> vars,IUser sender,List<IUser> users,NeedSubject needSubject,ContentType contentType);
}
