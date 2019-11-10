package com.hotent.mini.ext.script;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.engine.script.IUserScript;
import com.hotent.base.core.scheduler.SchedulerService;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.util.time.DateUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.core.engine.task.service.DefaultBpmTaskService;
import com.hotent.bpmx.persistence.manager.impl.BpmTaskManagerImpl;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 流程相关的脚本
 * @author liyanggui
 *
 */
@Component
public class BpmScript implements IUserScript {
	
	@Resource
	DefaultBpmTaskService defaultBpmTaskService;
	@Resource
	BpmTaskManagerImpl bpmTaskManagerImpl;
	@Resource
	IUserService userService;
	@Resource
	SchedulerService schedulerService;
	
	/**
	 * 锁住任务  将任务锁定给超级管理员
	 * @param delegateTask
	 */
	public void lockTask(BpmDelegateTask delegateTask){
		BpmTask task = bpmTaskManagerImpl.getByRelateTaskId(delegateTask.getId());
		delegateTask.setVariable("assigneeId", task.getAssigneeId());
		IUser user = userService.getUserByAccount(SysPropertyUtil.getByAlias("admin.account", "admin"));
		defaultBpmTaskService.lockTask(task.getId(), user.getUserId());
	}
	
	
	/**
	 * 启动定时任务
	 * @throws SchedulerException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public void startUnlockTaskJob(BpmDelegateTask delegateTask, Object unlockTaskDate ){
		try{
			
			BpmTask task = bpmTaskManagerImpl.getByRelateTaskId(delegateTask.getId());
			JSONArray ja = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("name", "taskId"); // taskId 和 jobName， trigName 是一样的  
			jo.put("type", "string");
			jo.put("value", task.getId());
			ja.add(jo);
			
			jo = new JSONObject();
			jo.put("name", "userId");
			jo.put("type", "string");
			jo.put("value", delegateTask.getVariable("assigneeId"));
			ja.add(jo);
			
			Boolean isSuccess = schedulerService.addJob(task.getTaskId(), "com.hotent.portal.job.UnLockTaskJob", ja.toJSONString(), "解锁任务");
			
			if(isSuccess){
				
				String dateStr = unlockTaskDate.toString();
				if( unlockTaskDate instanceof Date  ){
					dateStr = DateFormatUtil.formaDatetTime((Date) unlockTaskDate);
				}
				
				JSONObject planJson = new JSONObject();
				planJson.put("type", 1);
				planJson.put("timeInterval", dateStr);
				schedulerService.addTrigger(task.getTaskId(), task.getTaskId(), planJson.toJSONString());
			}
			// 启动定时任务
			if(!schedulerService.isStarted()){
				schedulerService.start();
			}
		}catch(Exception e){
			throw new RuntimeException("启动定时任务失败");
		}
		
	}
	
}
