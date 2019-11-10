package com.hotent.bpmx.core.engine.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.TaskType;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.task.BpmTaskTurn;
import com.hotent.bpmx.api.service.BpmAgentService;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.core.util.MessageUtil;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.dao.BpmAgentConditionDao;
import com.hotent.bpmx.persistence.dao.BpmTaskDao;
import com.hotent.bpmx.persistence.dao.BpmTaskTurnDao;
import com.hotent.bpmx.persistence.manager.BpmAgentConditionManager;
import com.hotent.bpmx.persistence.manager.BpmAgentSettingManager;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.TaskTurnAssignManager;
import com.hotent.bpmx.persistence.model.BpmAgentCondition;
import com.hotent.bpmx.persistence.model.BpmAgentSetting;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.bpmx.persistence.model.TaskTurnAssign;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;

public class DefaultBpmAgentService implements BpmAgentService {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultBpmAgentService.class);
	
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	
	@Resource
	BpmAgentSettingManager bpmAgentSettingManager  ;
	@Resource
	IUserService userServiceImpl;
	@Resource
	BpmAgentConditionDao bpmAgentConditionDao;
	
	@Resource
	BpmAgentConditionManager bpmAgentConditionManager;
	
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	
	@Resource
	BpmTaskDao bpmTaskDao;
	@Resource
	NatTaskService natTaskService;
	
	@Resource
	BpmTaskTurnDao	bpmTaskTurnDao;
	@Resource
	TaskTurnAssignManager taskTurnAssignManager;

	@Override
	public IUser getAgent(String userId, BpmDelegateTask delegateTask,
			Map<String, Object> vars) {
		BpmDefinition bpmDef= bpmDefinitionManager.getByBpmnDefId(delegateTask.getBpmnDefId());
		String defKey=bpmDef.getDefKey();
		
		IUser user=null;
		
		BpmAgentSetting setting= bpmAgentSettingManager.getSettingByFlowAndAuthidAndDate(userId, defKey);//会取出授权人授权该流程的所有的代理
		
		if(setting==null) return null;
		
		String agentId=setting.getAgentId();
		
		switch (setting.getType()) {//如果是全局代理的话直接根据代理人id取用户
			case 1:
			case 2:
				user=userServiceImpl.getUserById(agentId);
				break;
			case 3:
				user=getByCondition(delegateTask,setting.getId());
				break;
		}
		
		return user;
	}
	
	/**
	 * 条件检查。
	 * @param delegateTask 
	 * @param settingId
	 * @param vars 
	 * @param busData 
	 * @return  User
	 */
	@SuppressWarnings("unused")
	private IUser getByCondition(BpmDelegateTask delegateTask, String settingId){
		List<BpmAgentCondition> list=bpmAgentConditionDao.getBySettingId(settingId);
		HashSet<String> agentIds = new HashSet<String>();
		IUser user=null;
		for(BpmAgentCondition con:list){
			if(bpmAgentConditionManager.checkCondition(delegateTask,con)){
				user = userServiceImpl.getUserById(con.getAgentId());
				break;
			}
		}
		return user;
	}

	@Override
	public void retrieveTask(String taskId,String informType,String cause) {
		
		//BPM_TASK
		DefaultBpmTask bpmTask=bpmTaskDao.get(taskId);
		
		String bpmnTaskId=bpmTask.getTaskId();
		
		//ACT_RU_TASK
		//Task task= taskService.createTaskQuery().taskId(bpmnTaskId).singleResult();
		
		BpmDelegateTask delegateTask= natTaskService. getByTaskId(bpmnTaskId);
		
		//若是转办任务，新增一条取消转办的审批意见
		if(TaskType.DELIVERTO.getKey().equals(bpmTask.getStatus())){
			DefaultBpmCheckOpinion checkOpinion = bpmCheckOpinionManager.getByTaskIdStatus(taskId,OpinionStatus.AWAITING_CHECK.getKey());
			//修改原审批意见的状态
			checkOpinion.setStatus(OpinionStatus.DELIVERTO_CANCEL.getKey());
			checkOpinion.setCompleteTime(new Date());
			checkOpinion.setAuditor(ContextUtil.getCurrentUserId());
			checkOpinion.setAuditorName(ContextUtil.getCurrentUser().getFullname());
			checkOpinion.setOpinion("取消转办");
            long durMs = (new Date()).getTime() - checkOpinion.getCreateTime().getTime();
            checkOpinion.setDurMs(durMs);
            bpmCheckOpinionManager.update(checkOpinion);
            
            List<BpmIdentity> identityList = new ArrayList<BpmIdentity>();
            BpmIdentity bpmIdentity = new DefaultBpmIdentity();
            bpmIdentity.setType(IdentityType.USER);
            bpmIdentity.setId(ContextUtil.getCurrentUserId());
            bpmIdentity.setName(ContextUtil.getCurrentUser().getFullname());
            identityList.add(bpmIdentity);
			//新增一条审批意见
			checkOpinion.setId(UniqueIdUtil.getSuid());
			checkOpinion.setStatus(OpinionStatus.AWAITING_CHECK.getKey());
			checkOpinion.setCreateTime(new Date());
			checkOpinion.setCompleteTime(null);
			checkOpinion.setDurMs(0L);
			checkOpinion.setQualfieds(BpmCheckOpinionUtil.getIdentityIds(identityList));
			checkOpinion.setQualfiedNames(ContextUtil.getCurrentUser().getFullname());
			checkOpinion.setAuditor(null);
			checkOpinion.setAuditorName(null);
			checkOpinion.setOpinion("");
			bpmCheckOpinionManager.create(checkOpinion);
		}
		
		String ownerId=delegateTask.getOwner();
		delegateTask.setAssignee(ownerId);
		natTaskService.save(delegateTask);
		
		bpmTask.setAssigneeId(ownerId);
		bpmTask.setStatus(TaskType.NORMAL.name());
		bpmTaskDao.update(bpmTask);
		
		
		//BPM_TASK_TURN
		DefaultBpmTaskTurn taskTurn= bpmTaskTurnDao.getByTaskId(taskId);
		taskTurn.setAssigneeId(taskTurn.getOwnerId());
		taskTurn.setAssigneeName(taskTurn.getOwnerName());
		taskTurn.setStatus(BpmTaskTurn.STATUS_CANCEL);
		bpmTaskTurnDao.update(taskTurn);
		
		
		//通知获取最后一个人
		notifyUsers(taskTurn,informType,cause);
	}
	
	/**
	 * 通知相关人。
	 * @param taskTurn		转办对象
	 * @param informType	通知类型
	 * @param cause 		通知原因
	 * void
	 */
	private void notifyUsers(DefaultBpmTaskTurn taskTurn,String informType,String cause){
		TaskTurnAssign turnAssign=taskTurnAssignManager.getLastTaskTurn(taskTurn.getId());
		IUser user=BpmUtil.getUser(turnAssign.getReceiverId(), turnAssign.getReceiver());
		List<IUser> recievers=new ArrayList<IUser>();
		recievers.add(user);
		
		
		if(BeanUtils.isEmpty(recievers)) return;
		
		IUser sendUser=ContextUtil.getCurrentUser();
		
		Map<String,Object> vars=new HashMap<String, Object>();
		vars.put(TemplateConstants.TEMP_VAR.TASK_ID, taskTurn.getTaskId());
		vars.put(TemplateConstants.TEMP_VAR.TASK_SUBJECT, taskTurn.getTaskSubject());
		vars.put(TemplateConstants.TEMP_VAR.INST_SUBJECT, taskTurn.getTaskSubject());
		vars.put(TemplateConstants.TEMP_VAR.NODE_NAME, taskTurn.getTaskName());
		vars.put(TemplateConstants.TEMP_VAR.CAUSE, cause);
		vars.put(TemplateConstants.TEMP_VAR.SENDER, sendUser.getFullname());
		
		try {
			MessageUtil.sendMsg(TemplateConstants.TYPE_KEY.BPM_TURN_CANCEL, informType, recievers, vars);
		} catch (Exception e) {
			//记录日志
			LOGGER.debug(e.getMessage());
		}
	}

}
