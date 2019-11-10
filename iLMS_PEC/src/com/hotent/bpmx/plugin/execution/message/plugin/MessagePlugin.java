package com.hotent.bpmx.plugin.execution.message.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hotent.base.core.engine.freemark.FreemarkEngine;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringPool;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.util.time.DateUtil;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.plugin.core.def.BpmExecutionPluginDef;
import com.hotent.bpmx.api.plugin.core.factory.BpmPluginSessionFactory;
import com.hotent.bpmx.api.plugin.core.session.BpmExecutionPluginSession;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmExecutionPlugin;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleQueryHelper;
import com.hotent.bpmx.plugin.execution.message.context.IExternalData;
import com.hotent.bpmx.plugin.execution.message.def.HtmlSetting;
import com.hotent.bpmx.plugin.execution.message.def.MessagePluginDef;
import com.hotent.bpmx.plugin.execution.message.def.PlainTextSetting;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.jms.JmsProducer;
import com.hotent.sys.api.msg.model.DefaultMsgVo;
import com.hotent.sys.util.ContextUtil;

import freemarker.template.TemplateException;

public class MessagePlugin extends AbstractBpmExecutionPlugin{
	
	private Log logger = LogFactory.getLog(GroovyScriptEngine.class);
	
	@Resource
	BpmPluginSessionFactory sessionFactory ;

	@Autowired(required=false)
	JmsProducer jmsProducer;
	
	@Resource
	private FreemarkEngine freemarkEngine;
	
	public Void execute(BpmExecutionPluginSession pluginSession,
			BpmExecutionPluginDef pluginDef) {
		
		BpmDelegateExecution delegateExecution= pluginSession.getBpmDelegateExecution();
		
		Map<String,Object> vars=delegateExecution.getVariables();
		
		BpmUserCalcPluginSession bpmUserCalcPluginSession = sessionFactory.buildBpmUserCalcPluginSession(vars);
		
		MessagePluginDef messageDef=(MessagePluginDef)pluginDef;
		
		//处理流程变量
		handFlowVars(vars, delegateExecution);
		
		//处理变量数据。
		handData(messageDef, vars,delegateExecution);
		
		PlainTextSetting plainSetting=messageDef.getPlainTextSetting();
		
		HtmlSetting htmlSetting=messageDef.getHtmlSetting();
		
		if(plainSetting!=null){
			//查询要通知的用户
			List<BpmIdentity> notifyIdentities =UserAssignRuleQueryHelper.queryExtract(plainSetting.getRuleList(),bpmUserCalcPluginSession);
			List<IUser> receivers= queryAndConvert(notifyIdentities, pluginSession.getOrgEngine().getUserService());
			String content=plainSetting.getContent();
			content=parse(content,vars);
			String notifyType=plainSetting.getMsgType();
			send("",content,receivers,notifyType);
			
		}
		
		if(htmlSetting!=null){
			//查询要通知的用户
			List<BpmIdentity> notifyIdentities =UserAssignRuleQueryHelper.queryExtract(htmlSetting.getRuleList(),bpmUserCalcPluginSession);
			List<IUser> receivers= queryAndConvert(notifyIdentities, pluginSession.getOrgEngine().getUserService());
			
			String subject=htmlSetting.getSubject();
			String content=htmlSetting.getContent();
			subject=parse(subject,vars);
			content=parse(content,vars);
			
			String notifyType=htmlSetting.getMsgType();

			send(subject,content,receivers,notifyType);
		}
		return null;
	}
	
	private  void send(String subject,String content,List<IUser> receivers,String notifyType){
		if(jmsProducer==null) return ;
		if(StringUtil.isEmpty(notifyType)) return;
		
		String[] aryType=notifyType.split(",");
		for(String type:aryType){
			DefaultMsgVo msgVo = new DefaultMsgVo(subject,content,ContextUtil.getCurrentUser(),receivers,type);	
			jmsProducer.sendToQueue(msgVo);
		}
	}
	
	/**
	 * 处理外部数据并添加到表单中。
	 * @param messageDef
	 * @param vars
	 * @param execution 
	 * void
	 */
	private void handData(MessagePluginDef messageDef,Map<String,Object> vars,BpmDelegateExecution execution){
		String externalClass=messageDef.getExternalClass();
		if(StringUtil.isEmpty(externalClass)) return;
		
		String instId=(String)vars.get(BpmConstants.PROCESS_INST_ID);
		String bpmnDefId=execution.getBpmnDefId();
		String bpmnInstId=execution.getBpmnInstId();
		String nodeId=execution.getNodeId();
		String executionId=execution.getId();
		
		try {
			Class cls= Class.forName(externalClass);
			IExternalData data=(IExternalData) cls.newInstance();
			Map<String,Object> varMap= data.getData(bpmnDefId,bpmnInstId, instId, nodeId, executionId);
			
			vars.putAll(varMap);
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (InstantiationException e) {
			logger.debug(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.debug(e.getMessage());
		}
		
	}
	
	private String parse(String template,Object obj){
		String temp="";
		try {
			temp=freemarkEngine.parseByStringTemplate(template,obj);
		} catch (TemplateException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return temp;
	}
	
	private List<IUser> queryAndConvert(List<BpmIdentity> bpmIdentities,IUserService userService){
		List<IUser> userList = new ArrayList<IUser>();
		for(BpmIdentity bpmIdentity:bpmIdentities){
			IUser user = userService.getUserById(bpmIdentity.getId());
			userList.add(user);
		}
		return userList;
	}
	
	/**
	 * 加入流程变量
	 * @param vars
	 * @param execution
	 */
	private void handFlowVars(Map<String,Object> vars,BpmDelegateExecution execution){
		try {
			ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
			if(BeanUtils.isNotEmpty(taskCmd)){
				BpmProcessInstance instance= (BpmProcessInstance) taskCmd.getTransitVars(BpmConstants.PROCESS_INST);
				if(BeanUtils.isNotEmpty(instance)){
					String userId = instance.getCreateBy();
					if(BeanUtils.isNotEmpty(userId)){
						IUserService userService = AppUtil.getBean(IUserService.class);
						if(BeanUtils.isNotEmpty(userService)){
							IUser user = userService.getUserById(userId);
							if(BeanUtils.isNotEmpty(user)){
								vars.put("startorName", user.getFullname());
							}
						}
					}
					vars.put("startDate",DateFormatUtil.format(instance.getCreateTime(), StringPool.DATE_FORMAT_DATETIME));
					vars.put("businessKey", taskCmd.getBusinessKey());
				}
				
			}
			
			Map<String,BoData> boMap=BpmContextUtil.getBoFromContext();
			if(BeanUtils.isNotEmpty(boMap)){
				Collection<BoData> dataObjects = boMap.values();
				for (BoData boData : dataObjects){
					BaseBoDef bodef=boData.getBoDef();
					String boName=bodef.getAlias();
					Map<String,Object> dataMap= boData.getData();
					for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
						vars.put(boName +"_" + entry.getKey(),  entry.getValue());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
