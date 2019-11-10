package com.hotent.bpmx.plugin.task.reminders.plugin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.plugin.core.def.BpmTaskPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmTaskPluginSession;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmTaskReminderManager;
import com.hotent.bpmx.persistence.model.BpmTaskReminder;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmTaskPlugin;
import com.hotent.bpmx.plugin.task.reminders.def.Reminder;
import com.hotent.bpmx.plugin.task.reminders.def.RemindersPluginDef;
import com.hotent.bpmx.plugin.task.reminders.def.WarningSet;
import com.hotent.sys.api.calendar.ICalendarService;

/**
 * 催办节点插件运行时。
 * @author miaojf
 *
 */
public class RemindersPlugin extends AbstractBpmTaskPlugin{
	
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	@Resource
	BpmTaskReminderManager bpmTaskReminderManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Autowired(required=false)
	ICalendarService iCalendarService;

	@Override
	public Void execute(BpmTaskPluginSession pluginSession,BpmTaskPluginDef pluginDef) {
		//任务结束事件删除催办项
		if(pluginSession.getEventType()==EventType.TASK_COMPLETE_EVENT){
				bpmTaskReminderManager.deleteByTaskId(pluginSession.getBpmDelegateTask().getId());
				return null;
		}

		RemindersPluginDef reminderDef = (RemindersPluginDef) pluginDef;
		List<Reminder>  reminderList = reminderDef.getReminderList();
		for (Reminder reminder : reminderList) {
			createRminder(reminder,pluginSession);
		}
		
		return null;
	}
	
	
	//创建一条催办项
	private void createRminder(Reminder reminder, BpmTaskPluginSession pluginSession) {
		BpmDelegateTask task =pluginSession.getBpmDelegateTask();
		String condition = reminder.getCondition();
		//如果条件不通过则跳过
		if(StringUtil.isNotEmpty(condition)){
			Object object=	executeScript(pluginSession, condition);
				if(object instanceof Boolean) {
					if(!((Boolean) object)) return;
				}
		}
		
		BpmTaskReminder taskReminder =  new BpmTaskReminder();
		taskReminder.setName(reminder.getName());
		taskReminder.setTaskId(task.getId());
		taskReminder.setDueAction(reminder.getDueAction());
		taskReminder.setDueScript(reminder.getDueScript());
		
		Boolean isSendMsg= reminder.getIsSendMsg();
		int sendMsg=isSendMsg?1:0;
		
		taskReminder.setIsSendMsg(sendMsg);
		if(sendMsg==1){
			taskReminder.setHtmlMsg(reminder.getHtmlMsg());
			taskReminder.setPlainMsg(reminder.getPlainMsg());
			taskReminder.setMsgCount(reminder.getMsgCount());
			taskReminder.setMsgType(reminder.getMsgType());
			taskReminder.setMsgInterval(reminder.getMsgInterval());
		}
		
		boolean calcSuccess = calcReminderDates(task,reminder,taskReminder);
		if(!calcSuccess) return;
		
		bpmTaskReminderManager.create(taskReminder);
	}
	
	
	// 计算任务到期时间，催办开始时间，预警开始时间
	private boolean calcReminderDates(BpmDelegateTask task,Reminder reminder, BpmTaskReminder taskReminder) {
		Date relDate = null ;
		String relNodeId = reminder.getRelNodeId();
		String relNodeEvent =reminder.getRelNodeEvent();
		boolean isRelCreateEvent = Reminder.TASK_EVENT_CREATE.equals(relNodeEvent);
		
		/**获取相对时间,如果当前节点与相对节点相同则不需要从历史意见中获取*/
		if(task.getTaskDefinitionKey().equals(relNodeId)&& isRelCreateEvent){
			relDate = task.getCreateTime();
		}else{
			List<DefaultBpmCheckOpinion> dpcOpinions = bpmCheckOpinionManager.getByInstNodeId(ContextThreadUtil.getActionCmd().getInstId(), relNodeId);
			if(BeanUtils.isNotEmpty(dpcOpinions)){
				DefaultBpmCheckOpinion dpcOpinion = dpcOpinions.get(dpcOpinions.size()-1);
				if (isRelCreateEvent) {
					relDate = dpcOpinion.getCreateTime();
				} else {
					relDate = dpcOpinion.getCompleteTime();
				}
			}else{
				throw new RuntimeException("催办插件相对节点尚未处理。计算相对时间出现异常！");
			}
		}
		if(relDate== null || relDate.after(new Date())){
			throw new RuntimeException("催办插件相对时间计算出现异常");
		}

		/**计算到期时间，   日历日和工作日*/
		Date dueDate ,msgBeginDate = null;
		if(Reminder.TASK_TIME_TYPE_CALTIME.equals(reminder.getDateType())){
			//催办过期时间
			dueDate = new Date(TimeUtil.getNextTime(TimeUtil.MINUTE, reminder.getDueTime(),relDate.getTime()));
			//如果发送催办消息
			if(reminder.getIsSendMsg()){
				msgBeginDate = new Date(TimeUtil.getNextTime(TimeUtil.MINUTE, reminder.getMsgSendTime(),relDate.getTime()));
			}
		}else{
			// 获取第一个用户的工作日历
			//如果用户为空。我们不去执行催办
			List<BpmIdentity> list =task.getExecutors();
			if(BeanUtils.isNotEmpty(list)){
				dueDate = iCalendarService.getEndTimeByUser(list.get(0).getId(), relDate, relDate.getTime());
			}else{
				return false;
			}
			
			if(reminder.getIsSendMsg()){
				msgBeginDate = new Date(TimeUtil.getNextTime(TimeUtil.MINUTE, reminder.getMsgSendTime(),relDate.getTime()));
			}
		}
		//到期时间，和发送短信时间
		taskReminder.setDueDate(dueDate);
		taskReminder.setTriggerDate(dueDate);
		if(reminder.getIsSendMsg()){
			taskReminder.setMsgSendDate(msgBeginDate);
			//如果发消息时间更早。则发消息时间为下次triggerDate
			if(msgBeginDate.before(dueDate))taskReminder.setTriggerDate(msgBeginDate);
		}
		
		if(BeanUtils.isNotEmpty(reminder.getWarningSetList()))
			getWarningSet(reminder,task.getExecutors(),relDate,taskReminder);
		
		taskReminder.setRelDate(relDate);
		return true;
	}
	
	
	// 处理warningSet
	private void getWarningSet(Reminder reminder, List<BpmIdentity> executors, Date relDate, BpmTaskReminder taskReminder) {
		JSONArray warings = new JSONArray();
		
		for (WarningSet waringSet : reminder.getWarningSetList()) {
			JSONObject jsonObject = (JSONObject) JSON.toJSON(waringSet);
			Date warnDate=null;
			int warnTime = jsonObject.getIntValue("warnTime");
			if(Reminder.TASK_TIME_TYPE_CALTIME.equals(reminder.getDateType())){
				warnDate = new Date(TimeUtil.getNextTime(TimeUtil.MINUTE, warnTime,relDate.getTime()));
			}else{
				warnDate = iCalendarService.getEndTimeByUser(executors.get(0).getId(), relDate, warnTime);
			}
			// 修改最早triggerDate
			if(taskReminder.getTriggerDate()==null || warnDate.before(taskReminder.getTriggerDate())){
				taskReminder.setTriggerDate(warnDate);
			}
			
			jsonObject.put("warnDate", warnDate);
			warings.add(jsonObject);
		}
		
		taskReminder.setWarningset(warings.toJSONString());
	}

	//执行脚本
	private Object executeScript(BpmTaskPluginSession pluginSession,String script){
		Map<String, Object> vars=new HashMap<String, Object>();
		vars.putAll( pluginSession.getBpmDelegateTask().getVariables());
		
		Map<String,BoData> boDatas= BpmContextUtil.getBoFromContext();
		vars.putAll(boDatas);
		
		return groovyScriptEngine.executeObject(script, vars);
	}

}
