package com.hotent.bpmx.listener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.event.NotifyTaskModel;
import com.hotent.bpmx.api.event.TaskNotifyEvent;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.core.util.MessageUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.SysPropertyUtil;

@Service
public class TaskNotifyEventListener implements  ApplicationListener<TaskNotifyEvent>,Ordered{
	
	
	@Resource
	IUserService userServiceImpl;
	

	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public void onApplicationEvent(TaskNotifyEvent ev) {
		NotifyTaskModel model=(NotifyTaskModel) ev.getSource(); 
		
		ActionType actionType=model.getActionType();
		
		ActionCmd taskCmd= ContextThreadUtil.getActionCmd();
		
		//通知类型
		String notifyType=BpmUtil.getNotifyType((BpmProcessInstance) taskCmd.getTransitVars(BpmConstants.PROCESS_INST),model.getNodeId());
		//是否配置了通知类型。
		if(StringUtil.isEmpty(notifyType)) return;
		
		//获取基础的URL
		String baseUrl=SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
		
		model.addVars(TemplateConstants.TEMP_VAR.BASE_URL, baseUrl)
			.addVars(TemplateConstants.TEMP_VAR.TASK_SUBJECT, model.getSubject()) // 
			.addVars(TemplateConstants.TEMP_VAR.TASK_ID, model.getTaskId()) // 任务id
			.addVars(TemplateConstants.TEMP_VAR.CAUSE, model.getOpinion()) // 原因
			.addVars(TemplateConstants.TEMP_VAR.NODE_NAME, model.getNodeName())  // 节点名称
			.addVars(TemplateConstants.TEMP_VAR.AGENT, BeanUtils.isEmpty(model.getAgent())? "":model.getAgent().getFullname())// 代理人
			.addVars(TemplateConstants.TEMP_VAR.INST_SUBJECT,  model.getSubject())
			.addVars(TemplateConstants.TEMP_VAR.INST_ID, taskCmd.getInstId()); 
	
			
		if(ActionType.APPROVE.equals( model.getActionType())){
			//代理
			if(model.isAgent()){
				handAgent(model,notifyType);
			}
			//普通审批
			else{
				MessageUtil.send(model,notifyType,TemplateConstants.TYPE_KEY.BPMN_APPROVAL);
			}
		}
		//驳回时
		else if(ActionType.BACK.equals(actionType) || ActionType.BACK_TO_START.equals(actionType)){
			MessageUtil.send(model,notifyType,TemplateConstants.TYPE_KEY.BPMN_BACK);
		}
		//撤销
		else{
			MessageUtil.send(model,notifyType,TemplateConstants.TYPE_KEY.BPMN_RECOVER);
		}
	}
	
	
	
	
	/**
	 * 处理代理通知。
	 * @param model
	 * @param notifyType 
	 * void
	 */
	private void handAgent(NotifyTaskModel model,String notifyType){
		//代理人
		IUser agent=model.getAgent();
		
		IUser delegateUser=model.getDelegator();
		
		model.addVars("delegate", delegateUser.getFullname());
		model.addVars("agent", agent.getFullname());
		
		//发送给代理人。
		List<IUser> agentReceivers=new ArrayList<IUser>();
		agentReceivers.add(agent);
		model.setIdentitys(agentReceivers);
		MessageUtil.send(model, notifyType,TemplateConstants.TYPE_KEY.BPMN_AGENT);
		//发送给委托人
		List<IUser> delegateReceivers=new ArrayList<IUser>();
		delegateReceivers.add(delegateUser);
		model.setIdentitys(delegateReceivers);
		MessageUtil.send(model, notifyType,TemplateConstants.TYPE_KEY.BPMN_DELEGATE);
		
		
	}
	
	
	

}
