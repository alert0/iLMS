package com.hotent.bpmx.persistence.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.engine.freemark.FreemarkEngine;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.activiti.task.service.impl.TaskServiceImpl;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.service.BpmIdentityService;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.persistence.manager.BpmReminderHistoryManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskReminderManager;
import com.hotent.bpmx.persistence.model.BpmReminderHistory;
import com.hotent.bpmx.persistence.model.BpmTaskReminder;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.JmsProducer;
import com.hotent.sys.api.job.BaseJob;
import com.hotent.sys.api.msg.model.DefaultMsgVo;
/**
 * 任务催办定时计划
 * @author miao
 */
public class TaskReminderJob extends BaseJob{
	private BpmTaskReminderManager bpmTaskReminderManager = AppUtil.getBean(BpmTaskReminderManager.class);
	private BpmReminderHistoryManager bpmReminderHistoryManager = AppUtil.getBean(BpmReminderHistoryManager.class);
	private BpmTaskManager bpmTaskManager = AppUtil.getBean(BpmTaskManager.class);
	private BpmIdentityService bpmIdentityService = AppUtil.getBean(BpmIdentityService.class);
	private BpmIdentityExtractService bpmIdentityExtractService = AppUtil.getBean(BpmIdentityExtractService.class);
	
	@Override
	public void executeJob(JobExecutionContext context) {
		//获取当前时间要出发的所有催办项
		List<BpmTaskReminder> reminderList = bpmTaskReminderManager.getTriggerReminders();
		
		
		for (BpmTaskReminder reminder : reminderList) {
			DefaultBpmTask task = bpmTaskManager.get(reminder.getTaskId());
			
			if(BeanUtils.isEmpty(task)){
				bpmTaskReminderManager.remove(reminder.getId());
				continue;
			}
			
			task.setIdentityList(bpmIdentityService.searchByNode(task.getProcInstId(),task.getNodeId()));
			
			// 如果执行到期	动作任务被处理。则继续
			boolean taskComplate = executeDueAction(reminder,task);
			
			//发送催办
			if(reminder.getIsSendMsg()==1) sendMsg(reminder,task);
			
			if(taskComplate) continue;
			
			//处理预警
			handleWarning(reminder,task);
			
			// 如果预警为无动作，无催办，无预警。则删除改催办
			if(BpmTaskReminder.TASK_DUE_ACTION_NO_ACTION.equals(reminder.getDueAction())
					&& (reminder.getIsSendMsg()==0 || reminder.getMsgCount()<=0)
					&& StringUtil.isEmpty(reminder.getWarningset())){
				bpmTaskReminderManager.remove(reminder.getId());
				return;
			}
			// 更新催办
			bpmTaskReminderManager.update(reminder);
		}
	}
	
	// 处理预警
	private void handleWarning(BpmTaskReminder reminder, DefaultBpmTask task) {
		if(StringUtil.isEmpty(reminder.getWarningset())) return;
		JSONArray newWarningSet = new JSONArray();
		JSONArray array = JSON.parseArray(reminder.getWarningset());
		for (int i = 0; i < array.size(); i++) {
			JSONObject warn =array.getJSONObject(i);
			Date warningDate = warn.getDate("warnDate");
			//处理预警
			if(warningDate.before(new Date())){
				task.setPriority(warn.getLong("level"));
				bpmTaskManager.updateTaskPriority(task.getId(),warn.getLong("level"));
				createReminderHistory(reminder, "Warning", warn.getString("warnName"),task);
			}else{
				//如果未来要处理的待办早于 催办下次要触发的时间。则修改triggerDate
				if(warningDate.before(reminder.getTriggerDate())){
					reminder.setTriggerDate(warningDate);
				}
				//将未处理的预警设置到下次之中
				newWarningSet.add(warn);
			}
		}
		
		if(!newWarningSet.isEmpty()){
			reminder.setWarningset(newWarningSet.toJSONString());
		}else{
			reminder.setWarningset("");
		}
	}


	/**
	 * 如果无动作则返回 true
	 * 如果执行脚本。则修改为无动作
	 * 如果结束流程，完成任务则删除当前催办
	 * @return false 
	 */
	private boolean executeDueAction(BpmTaskReminder reminder, DefaultBpmTask task) {
		if(reminder.getDueDate().after(new Date())) {//任务还未到期
			reminder.setTriggerDate(reminder.getDueDate()); //尝试设置触发时间
			return false;
		}
		//无动作 //任务到期
		if(BpmTaskReminder.TASK_DUE_ACTION_NO_ACTION.equals(reminder.getDueAction())){
			return false;
		}
		String msg = "";
		//自动下一任务
		BpmTaskActionService bpmTaskActionService = AppUtil.getBean(BpmTaskActionService.class);
	   if(BpmTaskReminder.TASK_DUE_ACTION_AUTO_NEXT.equals(reminder.getDueAction())){
			DefaultTaskFinishCmd taskFinishCmd = new DefaultTaskFinishCmd();
			taskFinishCmd.setTaskId(reminder.getTaskId());
			taskFinishCmd.setActionName("agree");
			try {
				bpmTaskActionService.finishTask(taskFinishCmd);
				msg = "已经自动完成当前任务！";
				bpmTaskReminderManager.deleteByTaskId(reminder.getTaskId());//完成当前任务后，删除与该任务关联的催办
			} catch (Exception e) {
				msg = "自动完成当前任务失败！："+e.getMessage();
				e.printStackTrace();
			}
		}
		//结束掉流程
		else if(BpmTaskReminder.TASK_DUE_ACTION_END_PROCESS.equals(reminder.getDueAction())){
			try {
				bpmTaskActionService.endProcessByTaskId(reminder.getTaskId(), reminder.getMsgType(), "催办任务到期，自动结束流程！");
				//直接走结束方法似乎不会走插件了。所以。这里把催办全清掉。
				bpmTaskReminderManager.deleteByTaskId(reminder.getTaskId());
				msg = "已经结束当前流程！";
			} catch (Exception e) {
				msg = "自动结束当前流程失败！："+e.getMessage();
				e.printStackTrace();
			}
		}
		//执行脚本
		else if(BpmTaskReminder.TASK_DUE_ACTION_CALL_METHOD.equals(reminder.getDueAction())&&StringUtil.isNotEmpty(reminder.getDueScript())){
			Map<String, Object> vars = new HashMap<String, Object>();
			TaskServiceImpl taskService = AppUtil.getBean(TaskServiceImpl.class);
			Map<String, Object> variables = taskService.getVariables( reminder.getTaskId());
			vars.putAll(variables);
			vars.put("task",task);
			GroovyScriptEngine groovyScriptEngine = AppUtil.getBean(GroovyScriptEngine.class);
			
			try {
				groovyScriptEngine.execute(reminder.getDueScript(), vars);
			} catch (Exception e) {
				createReminderHistory(reminder, reminder.getDueAction(), "执行脚本"+reminder.getDueScript()+"\n失败！"+e.getMessage(),task);
			}
			
			// 执行过一次脚本以后就无动作。直到自动被删除
			reminder.setDueAction(BpmTaskReminder.TASK_DUE_ACTION_NO_ACTION);
			createReminderHistory(reminder, reminder.getDueAction(), "执行脚本成功："+reminder.getDueScript(),task);
			return false;
		}
	   //自动完成任务类型则返回true，不再继续处理催办
		createReminderHistory(reminder, reminder.getDueAction(), msg,task);
		return true;
	}
	
	/** 
	 * 发送消息的处理
	 * 发消息开始时间 +时间间隔=下次发送消息开始时间，更新发消息开始时间
	 * 发消息次数减 1
	 * 如果消息发送完毕。则count为0；
	 */
	private void sendMsg(BpmTaskReminder reminder, DefaultBpmTask task) {
		Date  beginSend = reminder.getMsgSendDate();
		int count = reminder.getMsgCount();
		//如果还没有到发送时间,或者已经催办完毕
		if(beginSend.after(new Date()) || count<=0) return; 
		
		int interval = reminder.getMsgInterval();
		//每次 次数减1 ，开始时间加上一间隔。
		reminder.setMsgSendDate(new Date(TimeUtil.getNextTime(TimeUtil.MINUTE,interval,beginSend.getTime())));
		reminder.setMsgCount(count-1);
		
		// 如果消息触发时间早于其他则设置为下次触发时间
		if(reminder.getMsgSendDate().before(reminder.getTriggerDate())){
			reminder.setTriggerDate(reminder.getMsgSendDate()); 
		}
		
		JmsProducer jmsProducer=AppUtil.getBean(JmsProducer.class);
		if(jmsProducer==null){
			createReminderHistory(reminder, "sendMsg", "JMS 出现异常未能正常发送消息！", task);
			return;
		}
		
		FreemarkEngine freemarkEngine=AppUtil.getBean(FreemarkEngine.class);
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("title", task.getSubject());
		vars.put("time", reminder.getRelDate());
		vars.put("task", task);
		
		String html,text;
		try {
			 html=freemarkEngine.parseByStringTemplate(reminder.getHtmlMsg(), vars);
			 text=freemarkEngine.parseByStringTemplate(reminder.getPlainMsg(), vars);
		}catch(Exception e) {
			createReminderHistory(reminder, "sendMsg", "发送消息失败！"+e.getMessage(), task);
			return;
		}
		String userNames = "";
		//获取用户
		List<IUser> recievers = new ArrayList<IUser>();
		
		//将用户抽取出来。
		recievers= bpmIdentityExtractService.extractUser(bpmIdentityService.searchByNode(task.getProcInstId(),task.getNodeId()));
		
		if(StringUtil.isNotZeroEmpty(task.getOwnerId())){
			userNames = task.getOwnerName();
		}else{
			for (BpmIdentity identity : task.getIdentityList()) {
				userNames +=identity.getName()+",";
			}
		}
		
		
		String[] aryType=reminder.getMsgType().split(",");
		String subject = "任务催办";
		for(String type:aryType){
			if(!com.hotent.sys.api.jms.MessageUtil.isSupportHtml(type)){
				DefaultMsgVo msgVo = new DefaultMsgVo(subject,text,null,recievers,type);	
				jmsProducer.sendToQueue(msgVo);
			}
			else{
				DefaultMsgVo msgVo = new DefaultMsgVo(subject,html,null,recievers,type);
				jmsProducer.sendToQueue(msgVo);
			}
		}
		
		createReminderHistory(reminder, "sendMsg", "向["+userNames+"]发送催办消息成功！", task);
	}
	
	
	
	
	//创建催办执行历史
	private void createReminderHistory(BpmTaskReminder reminder, String type, String msg, DefaultBpmTask task) {
		BpmReminderHistory history = new BpmReminderHistory();
		history.setExecuteDate(new Date());
		history.setNodeId(task.getNodeId());
		history.setNodeName(task.getName());
		history.setInstId(task.getProcInstId());
		history.setIsntName(task.getSubject());
		history.setRemindType(type);
		history.setNote(msg);
		history.setUserId(task.getOwnerId());
		history.setId(UniqueIdUtil.getSuid());
		history.setCreateTime(new Date());
		bpmReminderHistoryManager.create(history);
	}
	
}
