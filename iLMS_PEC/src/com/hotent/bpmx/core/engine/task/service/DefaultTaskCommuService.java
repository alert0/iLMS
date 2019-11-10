package com.hotent.bpmx.core.engine.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.TaskType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.IGlobalRestfulPluginDef;
import com.hotent.bpmx.api.model.process.def.Restful;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmExecutionPluginDef;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.factory.BpmPluginFactory;
import com.hotent.bpmx.api.plugin.core.runtime.BpmExecutionPlugin;
import com.hotent.bpmx.api.service.RestfulService;
import com.hotent.bpmx.api.service.TaskCommuService;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.core.util.MessageUtil; 
import com.hotent.bpmx.natapi.inst.NatProInstanceService;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmCommuReceiverManager;
import com.hotent.bpmx.persistence.manager.BpmTaskCommuManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.BpmCommuReceiver;
import com.hotent.bpmx.persistence.model.BpmTaskCommu;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;
@Service
public class DefaultTaskCommuService implements TaskCommuService{
	
	@Resource
	BpmTaskCommuManager bpmTaskCommuManager;
	@Resource
	BpmCommuReceiverManager bpmCommuReceiverManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	NatProInstanceService natProInstanceService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmPluginFactory bpmPluginFactory;
	@Resource
	RestfulService restfulService;

	/**
	 * 添加沟通任务，给每一位通知者发送沟通消息。
	 */
	@Override
	public void addCommuTask(String taskId, String notifyType, String opinion,
			List<IUser> users) {
		DefaultBpmTask bpmTask=bpmTaskManager.get(taskId);
		
		BpmTaskCommu commu=getByTask(bpmTask,opinion);

		String commuId=commu.getId();
		//将发起沟通消息加入审批历史
		addTranCheckOpinion(bpmTask, OpinionStatus.START_COMMU, ContextUtil.getCurrentUserId(), opinion);
		for(IUser user:users){
			if(bpmCommuReceiverManager.checkHasCommued(commuId,user.getUserId())) continue; 
			//添加沟通任务
			DefaultBpmTask task=BpmUtil.convertTask(bpmTask,bpmTask.getId(), TaskType.COMMU, user);
			bpmTaskManager.create(task);
			//添加沟通人
			BpmCommuReceiver receiver= getCommuReceiver(commuId,user);
			bpmCommuReceiverManager.create(receiver);
			//将沟通消息加入审批历史
			addTranCheckOpinion(task, OpinionStatus.AWAITING_FEEDBACK, user.getUserId(), "");
			//发送沟通消息
			MessageUtil.notify(task, opinion, user, notifyType, TemplateConstants.TYPE_KEY.BPM_COMMU_SEND);
			
			//触发流程全局事件中的任务创建事件
			restfulPluginExecut(task ,EventType.TASK_CREATE_EVENT);
		}
	}
	
	/**
	 * 获取沟通人。
	 * @param commuId
	 * @param user
	 * @return 
	 * BpmCommuReceiver
	 */
	private BpmCommuReceiver getCommuReceiver(String commuId,IUser user){
		BpmCommuReceiver receiver=new BpmCommuReceiver();
		
		receiver.setId(UniqueIdUtil.getSuid());
		receiver.setCommuId(commuId);
		receiver.setReceiver(user.getFullname());
		receiver.setReceiverId(user.getUserId());
		receiver.setStatus(BpmCommuReceiver.COMMU_NO);
		
		return receiver;
	}
	
	/**
	 * 获取任务通知对象。
	 * @param task
	 * @param opinion
	 * @return 
	 * BpmTaskCommu
	 */
	private BpmTaskCommu getByTask(DefaultBpmTask task,String opinion){
		IUser user=ContextUtil.getCurrentUser();
		
		BpmTaskCommu taskCommu=bpmTaskCommuManager.getByTaskId(task.getId());
		if(taskCommu!=null) return taskCommu;
		
		taskCommu=new BpmTaskCommu();
		
		taskCommu.setId(UniqueIdUtil.getSuid());
		taskCommu.setInstanceId(task.getProcInstId());
		taskCommu.setNodeId(task.getNodeId());
		taskCommu.setNodeName(task.getName());
		taskCommu.setTaskId(task.getId());
		
		taskCommu.setSenderId(user.getUserId());
		taskCommu.setSender(user.getFullname());
		taskCommu.setCreatetime(new Date());
		
		taskCommu.setOpinion(opinion);
		
		bpmTaskCommuManager.create(taskCommu);
		return taskCommu;
	}
	
	
	
	@Override
	public void completeTask(String taskId, String notifyType, String opinion) {
		//删除沟通任务
		DefaultBpmTask defaultBpmTask=bpmTaskManager.get(taskId);
		
		BpmTaskCommu taskCommu = bpmTaskCommuManager.getByTaskId(defaultBpmTask.getParentId());
		//通讯接收人。
		BpmCommuReceiver commuReceiver = bpmCommuReceiverManager.getByCommuUser(taskCommu.getId(), defaultBpmTask.getAssigneeId());
		
		commuReceiver.setStatus(BpmCommuReceiver.COMMU_FEEDBACK);
		
		if(commuReceiver.getReceiveTime()==null){
			commuReceiver.setReceiveTime(new Date());
		}
		commuReceiver.setFeedbackTime(new Date());
		
		commuReceiver.setOpinion(opinion);
		
		bpmCommuReceiverManager.update(commuReceiver);
		//将沟通反馈信息加入审批历史
		updOpinionComplete(taskId, OpinionStatus.FEEDBACK,ContextUtil.getCurrentUserId(), opinion);
		/**
		 * 发送通知。
		 */
		IUser user=BpmUtil.getUser(taskCommu.getSenderId(), taskCommu.getSender());
		
		MessageUtil.notify(defaultBpmTask, opinion, user, notifyType, TemplateConstants.TYPE_KEY.BPM_COMMU_FEEDBACK);
		
		//触发流程全局事件中的任务结束事件
		defaultBpmTask.setAssigneeId(ContextUtil.getCurrentUserId());
		restfulPluginExecut(defaultBpmTask,EventType.TASK_COMPLETE_EVENT);
		
		bpmTaskManager.remove(taskId);
		
	}

	/**
	 * 删除沟通任务。
	 */
	@Override
	public void finishTask(String parentId) {
		bpmTaskManager.delByParentId(parentId);
	}
	
	/**
	 * 添加流转意见。
	 * 
	 * @param bpmTask
	 * @param opinionStatus
	 *            void
	 * @param commuUser
	 *            处理流转任务的那个人
	 */
	private void addTranCheckOpinion(DefaultBpmTask bpmTask, OpinionStatus opinionStatus, String commuUser, String opinion){
		String bpmnInstId = bpmTask.getBpmnInstId();
		String superInstId = (String) natProInstanceService.getSuperVariable(bpmnInstId, BpmConstants.PROCESS_INST_ID);

		// 如果是流转中的人添加意见，则办理人为那个人
		IUser user = BpmUtil.getUser(StringUtil.isEmpty(commuUser) ? bpmTask.getAssigneeId() : commuUser);

		List<BpmIdentity> identityList = new ArrayList<BpmIdentity>();
		BpmIdentity bpmIdentity = new DefaultBpmIdentity();
		bpmIdentity.setType(IdentityType.USER);
		bpmIdentity.setId(user.getUserId());
		bpmIdentity.setName(user.getFullname());
		identityList.add(bpmIdentity);

		DefaultBpmCheckOpinion checkOpinion = new DefaultBpmCheckOpinion();
		checkOpinion.setId(UniqueIdUtil.getSuid());
		checkOpinion.setProcDefId(bpmTask.getBpmnDefId());
		checkOpinion.setSupInstId(superInstId);
		checkOpinion.setProcInstId(bpmTask.getProcInstId());
		checkOpinion.setTaskId(bpmTask.getId());
		checkOpinion.setTaskKey(bpmTask.getNodeId());
		checkOpinion.setTaskName(bpmTask.getName());
		checkOpinion.setStatus(opinionStatus.getKey());
		checkOpinion.setCreateTime(bpmTask.getCreateTime()); 
		checkOpinion.setQualfieds(BpmCheckOpinionUtil.getIdentityIds(identityList));
		checkOpinion.setQualfiedNames(user.getFullname());
		checkOpinion.setOpinion(opinion);
		if(opinionStatus.equals(OpinionStatus.START_COMMU)){
			checkOpinion.setCompleteTime(new Date());
			long durMs = (new Date()).getTime() - checkOpinion.getCreateTime().getTime();
			checkOpinion.setDurMs(durMs);
			checkOpinion.setAuditor(user.getUserId());
			checkOpinion.setAuditorName(user.getFullname());
		}

		bpmCheckOpinionManager.create(checkOpinion);
	}
	
	//更新 任务的意见
	private void updOpinionComplete(String taskId, OpinionStatus opinionStatus, String commuUser, String opinion){
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("task_id_", taskId, QueryOP.EQUAL);
		queryFilter.addFilter("status_", OpinionStatus.AWAITING_FEEDBACK, QueryOP.EQUAL);
		List<DefaultBpmCheckOpinion> opinions = bpmCheckOpinionManager.query(queryFilter);
		if(BeanUtils.isNotEmpty(opinions)){
			DefaultBpmCheckOpinion checkOpinion = opinions.get(0);
			IUser user = BpmUtil.getUser(commuUser);
			checkOpinion.setAuditor(user.getUserId());
			checkOpinion.setAuditorName(user.getFullname());
			checkOpinion.setOpinion(opinion);
			checkOpinion.setStatus(opinionStatus.getKey());
			checkOpinion.setCompleteTime(new Date());
			long durMs = (new Date()).getTime() - checkOpinion.getCreateTime().getTime();
			checkOpinion.setDurMs(durMs);
			bpmCheckOpinionManager.update(checkOpinion);
		}
	}
	
	private void restfulPluginExecut(DefaultBpmTask task,EventType eventType){
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(task.getProcDefId());
		List<BpmPluginContext> pluginContextList=bpmProcessDef.getProcessDefExt().getBpmPluginContexts();
		if(BeanUtils.isNotEmpty(pluginContextList)){
			
			for(BpmPluginContext bpmPluginContext:pluginContextList){
				BpmPluginDef bpmPluginDef =bpmPluginContext.getBpmPluginDef();
				if(bpmPluginDef instanceof BpmExecutionPluginDef){
					BpmExecutionPluginDef bpmExecutionPluginDef = (BpmExecutionPluginDef)bpmPluginDef;
					BpmExecutionPlugin bpmExecutionPlugin = bpmPluginFactory.buildExecutionPlugin(bpmPluginContext, eventType);
					if(bpmExecutionPlugin!=null){
						if(bpmPluginContext.getEventTypes().contains(eventType)){
							if(bpmExecutionPluginDef instanceof IGlobalRestfulPluginDef){
								IGlobalRestfulPluginDef restfulPluginDef = (IGlobalRestfulPluginDef) bpmExecutionPluginDef;
								List<Restful> restfuls = restfulPluginDef.getRestfulList();
								if(BeanUtils.isNotEmpty(restfuls)){
									restfulService.outTaskPluginExecute(task, restfuls,eventType);
								}
							}
						}
					}	
				}
			}
		}
	}

}
