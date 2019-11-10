package com.hotent.bpmx.plugin.task.tasknotify.helper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.bpmx.plugin.core.util.UserAssignRuleQueryHelper;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyItem;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.JmsProducerWithTemplate;
import com.hotent.sys.api.jms.constants.ContentType;
import com.hotent.sys.api.jms.constants.NeedSubject;
import com.hotent.sys.api.msg.constants.MsgType;
import com.hotent.sys.api.template.TemplateService;
import com.hotent.sys.api.template.model.TemplateVo;
import com.hotent.sys.util.ContextUtil;

public class NotifyHelper {
	@Resource
	private JmsProducerWithTemplate jmsProducerWithTemplate;
	@Resource
	private TemplateService templateService;	
	
	public void notify(NotifyItem notifyItem,String typeKey,Map<String,Object> vars){
		List<IUser> pluginUsers = UserAssignRuleQueryHelper.queryUsersWithExtract(notifyItem.getUserAssignRules(),vars);
		notify(pluginUsers, notifyItem.getMsgTypes(),typeKey , vars);
	}
	
	/**
	 * 向指定的接收人发送消息
	 * @param receiverUsers
	 * @param typeKey
	 * @param recevierType
	 * @param msgTypeKeys
	 * @param templateVars 
	 * void
	 */
	public void notify(List<IUser> receiverUsers,List<String> msgTypeKeys,String typeKey,Map<String,Object> vars){
		IUser sender = ContextUtil.getCurrentUser();
		TemplateVo templateVo = templateService.getDefaultTemplate(typeKey);
		for(String msgTypeKey:msgTypeKeys){			
			if(msgTypeKey.equals(MsgType.SMS.key())){
				jmsProducerWithTemplate.send(msgTypeKey,templateVo, vars,sender, receiverUsers, NeedSubject.NO, ContentType.PLAIN);
			}else{
				jmsProducerWithTemplate.send(msgTypeKey,templateVo, vars,sender, receiverUsers, NeedSubject.YES,ContentType.HTML);		
			}
		}
	}

}
