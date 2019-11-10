package com.hotent.bpmx.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hotent.base.core.engine.freemark.FreemarkEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.event.NotifyTaskModel;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.JmsProducer;
import com.hotent.sys.api.msg.model.DefaultMsgVo;
import com.hotent.sys.api.template.TemplateService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.api.template.model.TemplateVo;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
 



import freemarker.template.TemplateException;

public class MessageUtil {
	
	/**
	 * 发送通知。 
	 * @param bpmTask
	 * @param opinion
	 * @param receiver
	 * @param notifyType
	 * @param typeKey 
	 * void
	 */
	public static void notify(DefaultBpmTask bpmTask,String opinion,IUser receiver,String notifyType,String typeKey){
		NotifyTaskModel model=new NotifyTaskModel();
		String baseUrl=SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
		model.addVars(TemplateConstants.TEMP_VAR.BASE_URL, baseUrl)
		.addVars(TemplateConstants.TEMP_VAR.TASK_SUBJECT, bpmTask.getSubject())
		.addVars(TemplateConstants.TEMP_VAR.INST_SUBJECT, bpmTask.getSubject())
		.addVars(TemplateConstants.TEMP_VAR.TASK_ID, bpmTask.getId())
		.addVars(TemplateConstants.TEMP_VAR.NODE_NAME, bpmTask.getName())
		.addVars(TemplateConstants.TEMP_VAR.CAUSE, opinion)
		.addVars(TemplateConstants.TEMP_VAR.NODE_NAME, bpmTask.getName())
		.addVars(TemplateConstants.TEMP_VAR.RECEIVERID,receiver.getUserId())
		.addVars(TemplateConstants.TEMP_VAR.RECEIVER,receiver.getFullname());
		
		List<IUser> identitys = new ArrayList<IUser>();
		identitys.add(receiver);
		model.setIdentitys(identitys);
		
		MessageUtil.send(model, notifyType, typeKey);
		
	}
	
	
	
	
	/**
	 * 发送通知消息。
	 * @param model
	 * @param notifyType
	 * @param typeKey 
	 * void
	 */
	public static void send(NotifyTaskModel model,String notifyType,String typeKey){
		List<IUser> userList= model.getIdentitys();
		try {
			sendMsg(typeKey,notifyType,userList,model.getVars());
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送通知消息。
	 * @param typeKey			模版类型
	 * @param notifyType		通知类型
	 * @param recievers			接收人
	 * @param vars				变量
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static void sendMsg(String typeKey,String notifyType, List<IUser> recievers,Map<String, Object> vars) throws TemplateException, IOException{
		JmsProducer jmsProducer=AppUtil.getBean(JmsProducer.class);
		if(jmsProducer==null) return;
		
		TemplateService templateService=AppUtil.getBean(TemplateService.class);
		TemplateVo templateVo= templateService.getDefaultTemplate(typeKey);
		
		List<String> parmList=new ArrayList<String>();
		String plainStr= templateVo.getPlainTemplate();
		Pattern pattern = Pattern.compile("\\$[{]{1,}[0-9a-zA-z_]{1,}[}]");
	    Matcher matcher = pattern.matcher(plainStr); 
		while (matcher.find()) {
			String s=matcher.group(0); 
			parmList.add(s.substring(2, s.length()-1));
	    }
		
		FreemarkEngine freemarkEngine=AppUtil.getBean(FreemarkEngine.class);
		
		IUser sender = ContextUtil.getCurrentUser();
		vars.put(TemplateConstants.TEMP_VAR.SENDER, BeanUtils.isNotEmpty(sender)?sender.getFullname():"系统执行人"); 
		vars.put(TemplateConstants.TEMP_VAR.SENDERID, BeanUtils.isNotEmpty(sender)?sender.getUserId():"-1");
		if(recievers.size() == 1) vars.put(TemplateConstants.TEMP_VAR.RECEIVER,recievers.get(0).getFullname()); 
		
		
		String html=templateVo.getHtmlTemplate();
		String text=templateVo.getPlainTemplate();
		String subject=templateVo.getSubjectTemplate();
		
		html=freemarkEngine.parseByStringTemplate(html, vars);
		text=freemarkEngine.parseByStringTemplate(text, vars);
		subject=freemarkEngine.parseByStringTemplate(subject, vars);
		String[] aryType=notifyType.split(",");
		for(String type:aryType){
			if(!com.hotent.sys.api.jms.MessageUtil.isSupportHtml(type)){
				DefaultMsgVo msgVo = new DefaultMsgVo(subject,text,null,recievers,type);
				msgVo.setTemplateId(templateVo.getId());
				msgVo.setSmsTemplateNo(templateVo.getSmsTemplateNo());
				msgVo.setVoiceTemplateNo(templateVo.getVoiceTemplateNo());
				msgVo.setParms(parmList);
				msgVo.setExtendVars(vars);
				msgVo.setSender(ContextUtil.getCurrentUser());
				jmsProducer.sendToQueue(msgVo);
			}
			else{
				DefaultMsgVo msgVo = new DefaultMsgVo(subject,html,null,recievers,type);
				msgVo.setTemplateId(templateVo.getId());
				msgVo.setSmsTemplateNo(templateVo.getSmsTemplateNo());
				msgVo.setVoiceTemplateNo(templateVo.getVoiceTemplateNo());
				msgVo.setExtendVars(vars);
				msgVo.setParms(parmList);
				msgVo.setSender(ContextUtil.getCurrentUser());
				jmsProducer.sendToQueue(msgVo);
			}
		}
	}

}
