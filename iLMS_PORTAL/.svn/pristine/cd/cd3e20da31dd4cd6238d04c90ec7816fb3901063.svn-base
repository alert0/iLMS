package com.hotent.bpmx.core.engine.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.DecideType;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.TaskType;
import com.hotent.bpmx.api.constant.VoteType;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.TaskTrans;
import com.hotent.bpmx.api.service.BpmOpinionService;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.api.service.TaskTransService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.core.util.MessageUtil;
import com.hotent.bpmx.natapi.inst.NatProInstanceService;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTransManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTransRecordManager;
import com.hotent.bpmx.persistence.manager.BpmTransReceiverManager;
import com.hotent.bpmx.persistence.model.BpmTaskTrans;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;
import com.hotent.bpmx.persistence.model.BpmTransReceiver;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;

import net.sf.json.JSONArray;

@Service
public class DefaultTaskTransService implements TaskTransService
{

	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmTaskTransManager bpmTaskTransManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	NatProInstanceService natProInstanceService;
	@Resource
	BpmTaskActionService bpmTaskActionService;
	@Resource
	BpmOpinionService bpmOpinionService;
	@Resource
	BpmTaskTransRecordManager bpmTaskTransRecordManager;
	@Resource
	BpmTransReceiverManager transReceiverManager;

	/**
	 * 结束流转任务。
	 * 
	 * <pre>
	 * 	1.删除本任务。
	 *  2.发送通知给发送人。
	 * 	2.根据父任务ID修改票数，同意和反对的票数。
	 * 	3.判断流程是否完成。
	 * 		如果完成执行是否返回或或提交。
	 * 		如果是再派生的。
	 * 			1.如果为返回修改这个父任务状态为trans。
	 * 			2.如果提交则根据提交的结果，对父任务进行投票。
	 *  4.如果未完成那么判断流程是否是并行，如果是串行，那么取得下一个执行人，并产生任务。
	 * 
	 * </pre>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param actionName
	 *            审批的意见同意或反对
	 * @param opinion
	 *            void
	 */
	@Override
	public void completeTask(String taskId, String actionName, String notifyType, String opinion)
	{
		List<DefaultBpmTask> list = getList(taskId);

		// 流转的那个任务,为意见归属
		DefaultBpmTask tranTask = bpmTaskManager.get(taskId);
		// 添加流转任务意见。
		OpinionStatus opinionStatus = DecideType.AGREE.getKey().equals(actionName) ? OpinionStatus.TRANS_AGREE : OpinionStatus.TRANS_OPPOSE;
		
		updOpinionComplete(taskId, opinionStatus, actionName,ContextUtil.getCurrentUser().getUserId(),opinion);
		
		//设置任务流转记录相关数据
		BpmTaskTransRecord transRecord = bpmTaskTransRecordManager.getByTaskId(tranTask.getParentId());
		dealWithTransRecord(opinionStatus,transRecord,tranTask,opinion);

		bpmTaskManager.remove(taskId);
		for (int i = 0; i < list.size(); i++){
			DefaultBpmTask bpmTask = list.get(i);
			String id = bpmTask.getId();
			BpmTaskTrans taskTrans = bpmTaskTransManager.getByTaskId(id);

			updTaskTrans(actionName, taskTrans);

			bpmTaskTransManager.update(taskTrans);
			// 计算投票结果。
			SignResult result = calcResult(taskTrans);
			// 是否完成。
			if (result.isComplete()){
				OpinionStatus resultOpinionStatus = DecideType.AGREE.equals(result.getDecideType()) ? OpinionStatus.SIGN_PASS_CANCEL : OpinionStatus.SIGN_NOPASS_CANCEL;
				//未记录意见的流转者
				List<DefaultBpmTask> tasks =bpmTaskManager.getChildsByTaskId(bpmTask.getId());
				for (DefaultBpmTask task : tasks) {
					updOpinionComplete(BeanUtils.isEmpty(task.getTaskId())?task.getId():task.getTaskId(), resultOpinionStatus, actionName ,null ,DecideType.AGREE.getKey().equals(actionName)?"流转自动同意[系统]":"流转自动反对[系统]");
				}
				
				boolean isStop = handComplete(taskTrans, bpmTask, list, result, i, opinion, notifyType, tranTask.getAssigneeId());
				if (isStop){
					//将流转结果作为意见记录
					//bpmTask.setCreateTime(new Date());
					//addCheckOpinion(bpmTask, resultOpinionStatus, "", "流转结束[系统]",true);
					
					//如果需要返回。将任务状态更新
					//如果不需要返回，则更新任务意见状态
					//updOpinionComplete(bpmTask, resultOpinionStatus,taskTrans.getAction());
					
					//如果返回则添加一条审批意见，否则不添加
					if(BpmTaskTrans.SIGN_ACTION_BACK.equals(taskTrans.getAction())){
						//bpmTask.setCreateTime(new Date());
						addCheckOpinion(bpmTask, OpinionStatus.AWAITING_CHECK, "", "",false);
					}
					break;
				}
			}
			// 任务未完成直接结束。
			else{
				handNotComplete(bpmTask, taskTrans, notifyType, opinion);
				break;
			}
		}
	}

	/**
	 * 获取任务列表。
	 * 
	 * @param taskId
	 * @return List&lt;DefaultBpmTask>
	 */
	private List<DefaultBpmTask> getList(String taskId){
		List<DefaultBpmTask> list = new ArrayList<DefaultBpmTask>();
		DefaultBpmTask bpmTask = bpmTaskManager.get(taskId);
		while (!bpmTask.isBpmnTask()){
			String parentId = bpmTask.getParentId();
			bpmTask = bpmTaskManager.get(parentId);
			list.add(bpmTask);
		}
		return list;
	}

	/**
	 * 如果任务没有完成。 此任务是串行任务，则取出执行人产生串签任务。
	 * 
	 * @param parentTask
	 * @param bpmTaskTrans
	 *            void
	 */
	private void handNotComplete(DefaultBpmTask parentTask, BpmTaskTrans bpmTaskTrans, String notifyType, String opinion){
		if (BpmTaskTrans.SIGN_TYPE_PARALLEL.equals(bpmTaskTrans.getSignType())) return;

		IUser user = bpmTaskTrans.getUserByIndex(bpmTaskTrans.getSeq());

		DefaultBpmTask task = BpmUtil.convertTask(parentTask, parentTask.getId(), TaskType.TRANSFORMED, user);
		bpmTaskManager.create(task);
		//生成审批意见
		addTranCheckOpinion(task, OpinionStatus.AWAITING_CHECK, user.getUserId(), "");

		// 发送通知。
		MessageUtil.notify(task, opinion, user, notifyType, TemplateConstants.TYPE_KEY.BPMN_TASK_TRANS);
	}

	/**
	 * 处理任务完成。
	 * 
	 * @param taskTrans
	 * @param bpmTask
	 * @param list
	 * @param result
	 * @param index
	 * @param transUser
	 *            此次流转处理人
	 * @return boolean
	 */
	private boolean handComplete(TaskTrans taskTrans, DefaultBpmTask bpmTask, List<DefaultBpmTask> list,
			SignResult result, int index, String opinion, String notifyType, String transUser){
		boolean isStop = false;
		String taskId = bpmTask.getId();

		bpmTaskTransManager.remove(taskTrans.getId());
		// 删除子任务。把子任务也记录意见
		bpmTaskManager.delByParentId(taskId);
		
		// 如果任务完成，需要返回
		if (BpmTaskTrans.SIGN_ACTION_BACK.equals(taskTrans.getAction()))
		{
			// 更新任务 状态，普通任务，
			String status = bpmTask.isBpmnTask() ? TaskType.NORMAL.name() : TaskType.TRANSFORMED.name();
			bpmTask.setStatus(status);
			bpmTaskManager.update(bpmTask);
			
			//更新流转记录状态
			BpmTaskTransRecord transRecord = bpmTaskTransRecordManager.getByTaskId(taskTrans.getTaskId());
			transRecord.setStatus((short)1);
			bpmTaskTransRecordManager.update(transRecord);

			// 发送通知。(反对时不发送通知)(暂不发送，没有消息模板)
			//IUser user = BpmUtil.getUser(bpmTask.getAssigneeId());
			//MessageUtil.notify(bpmTask, opinion, user, notifyType, TemplateConstants.TYPE_KEY.BPM_TRANS_FEEDBACK);
			

			isStop = true;
		}
		// 提交
		else{
			// 为流程引擎任务。
			if (bpmTask.isBpmnTask()){

				String actionName = result.getDecideType().equals(DecideType.AGREE) ? "agree" : "oppose";
				DefaultTaskFinishCmd cmd = new DefaultTaskFinishCmd();
				cmd.setTaskId(bpmTask.getId());
				cmd.setActionName(actionName);
				cmd.setApprovalOpinion(opinion);
				bpmTaskActionService.finishTask(cmd);
			}
			// 为流转任务
			else{
				// 删除任务ID,并更新下一个任务的状态数据。
				bpmTaskManager.remove(taskId);
				DefaultBpmTask nextBpmTask = list.get(index + 1);
				BpmTaskTrans nextTaskTrans = bpmTaskTransManager.getByTaskId(nextBpmTask.getId());
				changeVoteAmount(result, nextTaskTrans);
				bpmTaskTransManager.update(nextTaskTrans);

				// 发送反馈通知。
				IUser user = BpmUtil.getUser(nextBpmTask.getAssigneeId());
				MessageUtil.notify(nextBpmTask, opinion, user, notifyType, TemplateConstants.TYPE_KEY.BPM_TRANS_FEEDBACK);

				// 添加意见。
				OpinionStatus opinionStatus = result.getDecideType().equals(DecideType.AGREE) ? OpinionStatus.TRANS_AGREE : OpinionStatus.TRANS_OPPOSE;
				addCheckOpinion(bpmTask, opinionStatus, transUser, opinion,true);
			}
			
			//更新流转记录状态
			BpmTaskTransRecord transRecord = bpmTaskTransRecordManager.getByTaskId(taskTrans.getTaskId());
			transRecord.setStatus((short)1);
			bpmTaskTransRecordManager.update(transRecord);
		}
		return isStop;
	}
	
	//更新 任务的意见
	private void updOpinionComplete(String taskId, OpinionStatus opinionStatus,String trunsAction, String transUser, String opinion){
		DefaultBpmCheckOpinion checkOpinion = bpmCheckOpinionManager.getByTaskId(taskId);
		//返回的情况,将原来checkOpinion 设置为awaiting_check，再次处理任务才可以更新意见
		if(BpmTaskTrans.SIGN_ACTION_BACK.equals(trunsAction)){
			checkOpinion.setStatus(OpinionStatus.AWAITING_CHECK.getKey());
		}//普通更新意见
		else{
			if(!(opinionStatus.getKey().equals(OpinionStatus.SIGN_PASS_CANCEL.getKey())||opinionStatus.getKey().equals(OpinionStatus.SIGN_NOPASS_CANCEL.getKey()))){
				IUser user = BpmUtil.getUser(transUser);
				checkOpinion.setAuditor(user.getUserId());
				checkOpinion.setAuditorName(user.getFullname());
				checkOpinion.setOpinion(opinion);
				TaskFinishCmd cmd = (TaskFinishCmd) ContextThreadUtil.getActionCmd();
				checkOpinion.setFormName(cmd.getOpinionIdentity());
			}
			checkOpinion.setStatus(opinionStatus.getKey());
			checkOpinion.setCompleteTime(new Date());
			long durMs = (new Date()).getTime() - checkOpinion.getCreateTime().getTime();
			checkOpinion.setDurMs(durMs);
		}
		
		bpmCheckOpinionManager.update(checkOpinion);
	}

	/**
	 * 修改流程票数。
	 * 
	 * @param actionName
	 * @param bpmTaskTrans
	 *            void
	 */
	private void updTaskTrans(String actionName, BpmTaskTrans bpmTaskTrans){
		short agreeAmount = bpmTaskTrans.getAgreeAmount();
		short oppseAmount = bpmTaskTrans.getOpposeAmount();
		if (OpinionStatus.AGREE.getKey().equals(actionName)){
			agreeAmount++;
		} 
		else{
			oppseAmount++;
		}
		bpmTaskTrans.setAgreeAmount(agreeAmount);
		bpmTaskTrans.setOpposeAmount(oppseAmount);

		if (BpmTaskTrans.SIGN_TYPE_SEQ.equals(bpmTaskTrans.getSignType())){
			short seq = (short) (bpmTaskTrans.getSeq() + 1);
			bpmTaskTrans.setSeq(seq);
		}
		//修改流转记录中的数据
		updTransRecord(bpmTaskTrans);
	}

	private void changeVoteAmount(SignResult result, BpmTaskTrans bpmTaskTrans){
		short agreeAmount = bpmTaskTrans.getAgreeAmount();
		short oppseAmount = bpmTaskTrans.getOpposeAmount();
		if (OpinionStatus.AGREE.getKey().equals(result.getDecideType().getKey())){
			agreeAmount++;
		} 
		else{
			oppseAmount++;
		}
		bpmTaskTrans.setAgreeAmount(agreeAmount);
		bpmTaskTrans.setOpposeAmount(oppseAmount);
	}

	/**
	 * 计算投票结果。
	 * 
	 * @param bpmTaskTrans
	 * @return SignResult
	 */
	private SignResult calcResult(TaskTrans bpmTaskTrans){
		SignResult result = new SignResult();
		int totalAmount = bpmTaskTrans.getTotalAmount();
		// 投票次数。
		short voteAmount = bpmTaskTrans.getVoteAmount();
		// 决策类型 agree,oppose
		String decideType = bpmTaskTrans.getDecideType();
		// 投票类型
		String voteType = bpmTaskTrans.getVoteType();

		short agreeAmount = bpmTaskTrans.getAgreeAmount();
		short oppseAmount = bpmTaskTrans.getOpposeAmount();

		boolean isFinished = totalAmount == agreeAmount + oppseAmount;

		// 投票决策方式为通过。
		if (DecideType.AGREE.getKey().equals(decideType)){
			if (VoteType.PERCENT.getKey().equals(voteType)){
				agreeAmount = (short) ((float) agreeAmount / totalAmount * 100);
			}
			// 如果投票完成，但是同意票数没有达到设定票数，则认为不通过。
			if (agreeAmount >= voteAmount){
				result.setComplete(true);
				result.setDecideType(DecideType.AGREE);
			} 
			else if (isFinished){
				result.setComplete(true);
				result.setDecideType(DecideType.REFUSE);
			}
		}
		// 决策方式不通过
		else{
			if (VoteType.PERCENT.getKey().equals(decideType)){
				oppseAmount = (short) ((float) oppseAmount / totalAmount * 100);
			}
			// 如果投票完成，但是同意票数没有达到设定票数，则认为不通过。
			if (oppseAmount >= voteAmount){
				result.setComplete(true);
				result.setDecideType(DecideType.REFUSE);
			} 
			else if (isFinished){
				result.setComplete(true);
				result.setDecideType(DecideType.AGREE);
			}
		}
		//修改流转记录中的数据
		updTransRecord((BpmTaskTrans)bpmTaskTrans);
		return result;
	}

	@Override
	public void addTransTask(TaskTrans taskTrans, List<IUser> listUsers, String notifyType, String opinion)
	{
		DefaultBpmTask bpmTask = bpmTaskManager.get(taskTrans.getTaskId());

		BpmTaskTrans trans = (BpmTaskTrans) taskTrans;
		// 插入流转记录
		addTaskTrans(trans, bpmTask, listUsers);
		// 更新当前任务类型为流转类型。
		bpmTask.setStatus(TaskType.TRANSFORMING.name());
		bpmTaskManager.update(bpmTask);
		// 添加流转任务
		addTaskByUsers(trans.getTaskId(), bpmTask, listUsers, trans.getSignType(), opinion, notifyType, 
				TemplateConstants.TYPE_KEY.BPMN_TASK_TRANS);

		// 更新任务的审核意见状态为流转中。
		DefaultBpmCheckOpinion checkOpinion = bpmCheckOpinionManager.getByTaskId(taskTrans.getTaskId());
		checkOpinion.setStatus(OpinionStatus.TRANS_FORMING.getKey());
		checkOpinion.setOpinion(opinion);
		checkOpinion.setCompleteTime(new Date());
		checkOpinion.setDurMs(new Date().getTime() - checkOpinion.getCreateTime().getTime());
		checkOpinion.setAuditor(ContextUtil.getCurrentUser().getUserId());
		checkOpinion.setAuditorName(ContextUtil.getCurrentUser().getFullname());
		bpmCheckOpinionManager.update(checkOpinion);

		
	}

	/**
	 * 添加流转意见。
	 * 
	 * @param bpmTask
	 * @param opinionStatus
	 *            void
	 * @param transUser
	 *            处理流转任务的那个人
	 */
	private void addTranCheckOpinion(DefaultBpmTask bpmTask, OpinionStatus opinionStatus, String transUser, String opinion){
		String bpmnInstId = bpmTask.getBpmnInstId();
		String superInstId = (String) natProInstanceService.getSuperVariable(bpmnInstId, BpmConstants.PROCESS_INST_ID);

		// 如果是流转中的人添加意见，则办理人为那个人
		IUser user = BpmUtil.getUser(StringUtil.isEmpty(transUser) ? bpmTask.getAssigneeId() : transUser);

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
		checkOpinion.setTaskId(BeanUtils.isEmpty(bpmTask.getTaskId())?bpmTask.getId():bpmTask.getTaskId());
		checkOpinion.setTaskKey(bpmTask.getNodeId());
		checkOpinion.setTaskName(bpmTask.getName());
		checkOpinion.setStatus(opinionStatus.getKey());
		checkOpinion.setCreateTime(bpmTask.getCreateTime()); 
		checkOpinion.setQualfieds(BpmCheckOpinionUtil.getIdentityIds(identityList));
		checkOpinion.setQualfiedNames(user.getFullname());

		bpmCheckOpinionManager.create(checkOpinion);
	}

	/**
	 * 添加意见。
	 * 
	 * @param bpmTask
	 * @param opinionStatus
	 *            void
	 * @param transUser
	 *            处理流转任务的那个人
	 */
	private void addCheckOpinion(DefaultBpmTask bpmTask, OpinionStatus opinionStatus, String transUser,
			String opinion, boolean isCompleted){
		String bpmnInstId = bpmTask.getBpmnInstId();
		String superInstId = (String) natProInstanceService.getSuperVariable(bpmnInstId, BpmConstants.PROCESS_INST_ID);

		// 如果是流转中的人添加意见，则办理人为那个人
		IUser user = BpmUtil.getUser(StringUtil.isEmpty(transUser) ? bpmTask.getAssigneeId() : transUser);
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
		checkOpinion.setTaskId(bpmTask.getTaskId());
		checkOpinion.setTaskKey(bpmTask.getNodeId());
		checkOpinion.setTaskName(bpmTask.getName());
		checkOpinion.setStatus(opinionStatus.getKey());
		
		//Date transDate =bpmTask.getTransDate()==null?bpmTask.getCreateTime():bpmTask.getTransDate();
		checkOpinion.setCreateTime(new Date());
		
		checkOpinion.setQualfieds(BpmCheckOpinionUtil.getIdentityIds(identityList));
		checkOpinion.setQualfiedNames(user.getFullname());

		if (isCompleted){
			checkOpinion.setAuditor(user.getUserId());
			checkOpinion.setAuditorName(user.getFullname());
			checkOpinion.setCompleteTime(new Date());
			checkOpinion.setDurMs(new Date().getTime()-bpmTask.getCreateTime().getTime());
			checkOpinion.setOpinion(opinion);
		}
		bpmCheckOpinionManager.create(checkOpinion);
	}

	/**
	 * 添加流转任务。
	 * 
	 * @param parentTaskId
	 * @param bpmTask
	 * @param listUsers
	 *            void
	 */
	private void addTaskByUsers(String parentTaskId, DefaultBpmTask bpmTask, List<IUser> listUsers, 
			String signType, String opinion, String notifyType, String typeKey){
		// 并行处理任务。
		if (BpmTaskTrans.SIGN_TYPE_PARALLEL.equals(signType)){
			for (IUser user : listUsers){
				DefaultBpmTask task = BpmUtil.convertTask(bpmTask, parentTaskId, TaskType.TRANSFORMED, user);
				bpmTaskManager.create(task);
				//生成审批意见
				addTranCheckOpinion(task, OpinionStatus.AWAITING_CHECK, user.getUserId(), "");
				// 发送通知
				MessageUtil.notify(task, opinion, user, notifyType, typeKey);
			}
		} 
		else{
			IUser user = listUsers.get(0);
			DefaultBpmTask task = BpmUtil.convertTask(bpmTask, parentTaskId, TaskType.TRANSFORMED, user);
			bpmTaskManager.create(task);
			//生成审批意见
			addTranCheckOpinion(task, OpinionStatus.AWAITING_CHECK, user.getUserId(), "");
			// 发送通知。
			MessageUtil.notify(bpmTask, opinion, user, notifyType, typeKey);
		}

	}

	/**
	 * 添加流转配置。
	 **/
	private void addTaskTrans(BpmTaskTrans trans, DefaultBpmTask bpmTask, List<IUser> users){
		IUser user = ContextUtil.getCurrentUser();

		//先删除之前由我创建的流转（防止多次流转问题）
		BpmTaskTrans oleTrans=bpmTaskTransManager.getByTaskId(bpmTask.getId());
		if(oleTrans!=null)
			bpmTaskTransManager.remove(oleTrans.getId());
		
		trans.setId(UniqueIdUtil.getSuid());
		trans.setInstanceId(bpmTask.getProcInstId());
		trans.setTaskId(bpmTask.getId());

		// 如果当前任务办理人、为空，则设置当前用户
		if (StringUtil.isZeroEmpty(bpmTask.getAssigneeId())){
			bpmTask.setAssigneeId(user.getUserId());
		}

		trans.setCreatorId(user.getUserId());
		trans.setCreator(user.getFullname());
		trans.setCreateTime(new Date());
		trans.setTotalAmount((short) users.size());

		trans.setAgreeAmount((short) 0);
		trans.setOpposeAmount((short) 0);
		trans.setSeq((short) 0);
		short allowFormEdit = trans.getAllowFormEdit();
		trans.setAllowFormEdit(allowFormEdit);
		String signType = trans.getSignType();

		JSONArray jArray = JSONArray.fromObject(users);
		if (BpmTaskTrans.SIGN_TYPE_SEQ.equals(signType)){
			trans.setUserJson(jArray.toString());
		}
		bpmTaskTransManager.create(trans);
		//创建一天流转记录，记录流转
		createTransRecord(trans,bpmTask,users);
	}

	@Override
	public PageList<DefaultBpmTask> getMyTransTask(String userId, QueryFilter queryFilter){
		return (PageList<DefaultBpmTask>) bpmTaskManager.getMyTransTask(userId, queryFilter);
	}

	@Override
	public void withDraw(String taskId, String notifyType, String opinion){
		DefaultBpmTask bpmTask = bpmTaskManager.get(taskId);
		
		//更新审批历史状态
		List<DefaultBpmCheckOpinion> opinions = bpmCheckOpinionManager.getByInstNodeId(bpmTask.getProcInstId(), bpmTask.getNodeId());
		for(DefaultBpmCheckOpinion checkOpinion:opinions){
			if(checkOpinion.getStatus().equals(OpinionStatus.AWAITING_CHECK.getKey())){
				checkOpinion.setStatus(OpinionStatus.SIGN_RECOVER_CANCEL.getKey());
				checkOpinion.setCompleteTime(new Date());
				long durMs = (new Date()).getTime() - checkOpinion.getCreateTime().getTime();
				checkOpinion.setDurMs(durMs);
				checkOpinion.setOpinion(opinion);
				checkOpinion.setAuditor(ContextUtil.getCurrentUser().getUserId());
				checkOpinion.setAuditorName(ContextUtil.getCurrentUser().getFullname());
				bpmCheckOpinionManager.update(checkOpinion);
			}
		}
		
		// 增加撤消审批意见记录
		//addCheckOpinion(bpmTask, OpinionStatus.TRANS_REVOKER, "", opinion,true);

		// 添加撤消回到原任务的待审批意见记录
		addCheckOpinion(bpmTask, OpinionStatus.AWAITING_CHECK, "", opinion,false);
		// 更新当前任务状态。
		bpmTask.setStatus(bpmTask.isBpmnTask() ? TaskType.NORMAL.name() : TaskType.TRANSFORMED.name());
		bpmTaskManager.update(bpmTask);
		
		//更新流转记录数据
		BpmTaskTransRecord transRecord = bpmTaskTransRecordManager.getByTaskId(bpmTask.getTaskId());
		transRecord.setStatus((short)2);//撤销状态
		bpmTaskTransRecordManager.update(transRecord);

		IUserService userService = AppUtil.getBean(IUserService.class);
		// 获取该任务的子任务。
		List<DefaultBpmTask> childTasks = bpmTaskManager.getChildsByTaskId(taskId);
		// 发送通知。
		for (DefaultBpmTask task : childTasks){
			IUser receiver = userService.getUserById(task.getAssigneeId());
			// 发送通知。
			MessageUtil.notify(task, opinion, receiver, notifyType, TemplateConstants.TYPE_KEY.BPM_TRANS_CANCEL);
			// 删除
			bpmTaskManager.remove(task.getId());
		}
		
	}

	

	class SignResult{
		boolean isComplete = false;
		DecideType decideType = DecideType.AGREE;

		public SignResult()
		{
		}

		public SignResult(boolean isComplete, DecideType decideType){
			this.decideType = decideType;
			this.isComplete = isComplete;
		}

		public boolean isComplete(){
			return isComplete;
		}

		public void setComplete(boolean isComplete){
			this.isComplete = isComplete;
		}

		public DecideType getDecideType(){
			return decideType;
		}

		public void setDecideType(DecideType decideType){
			this.decideType = decideType;
		}

	}

	/*
	 * 获取流转信息实体
	 */
	@Override
	public BpmTaskTrans getTransTaskByTaskId(String taskId)
	{
		return bpmTaskTransManager.getByTaskId(taskId);
	}
	
	/**
	 * 新增一条流转记录，用于记录流转任务
	 * @param taskTrans 流转任务（流转源）位于bpm_task_trans中
	 * @param bpmTask bpm_task
	 * @param users 流转人员
	 */
	public void createTransRecord(BpmTaskTrans taskTrans, DefaultBpmTask bpmTask, List<IUser> users){
		IUser user = ContextUtil.getCurrentUser();
		BpmTaskTransRecord transRecord = new BpmTaskTransRecord();
		transRecord.setId(UniqueIdUtil.getSuid());
		transRecord.setCreator(user.getFullname());
		transRecord.setCreatorId(user.getUserId());
		transRecord.setCreateTime(new Date());
		transRecord.setTaskName(bpmTask.getName());
		transRecord.setTaskSubject(bpmTask.getSubject());
		transRecord.setStatus((short)0);//流转中
		transRecord.setTransOwner(bpmTask.getAssigneeId());//流转任务所属人
		transRecord.setTransTime(new Date());
		transRecord.setDefName(bpmTask.getProcDefName());
		transRecord.setProcInstId(bpmTask.getProcInstId());
		//复制流转任务中的一些数据
		transRecord.setAction(taskTrans.getAction());
		transRecord.setAgreeAmount(taskTrans.getAgreeAmount());
		transRecord.setDecideType(taskTrans.getDecideType());
		transRecord.setVoteAmount(taskTrans.getVoteAmount());
		transRecord.setVoteType(taskTrans.getVoteType());
		transRecord.setSignType(taskTrans.getSignType());
		transRecord.setTotalAmount(taskTrans.getTotalAmount());
		transRecord.setAgreeAmount(taskTrans.getAgreeAmount());
		transRecord.setOpposeAmount(taskTrans.getOpposeAmount());
		transRecord.setTaskId(taskTrans.getTaskId());
		String transUsers = "";
		String transUserIds = "";
		for(IUser u : users){
			transUsers = transUsers +"【"+u.getFullname()+"】 ";
			transUserIds = transUserIds +u.getUserId() +",";
			//接收人
			BpmTransReceiver transReceiver = new BpmTransReceiver();
			transReceiver.setId(UniqueIdUtil.getSuid());
			transReceiver.setTransRecordid(transRecord.getId());
			transReceiver.setReceiver(u.getFullname());
			transReceiver.setReceiverId(u.getUserId());
			transReceiver.setStatus((short)0);
			transReceiver.setCheckType((short)0);//尚未审批
			transReceiver.setReceiverTime(new Date());
			transReceiverManager.create(transReceiver);
		}
		transUserIds.substring(0, transUserIds.length()-1);
		transRecord.setTransUsers(transUsers);
		transRecord.setTransUserIds(transUserIds);
		bpmTaskTransRecordManager.create(transRecord);
	}
	
	public void updTransRecord(BpmTaskTrans taskTrans){
		BpmTaskTransRecord transRecord = bpmTaskTransRecordManager.getByTaskId(taskTrans.getTaskId());
		transRecord.setAgreeAmount(taskTrans.getAgreeAmount());
		transRecord.setDecideType(taskTrans.getDecideType());
		transRecord.setVoteAmount(taskTrans.getVoteAmount());
		transRecord.setVoteType(taskTrans.getVoteType());
		transRecord.setSignType(taskTrans.getSignType());
		transRecord.setTotalAmount(taskTrans.getTotalAmount());
		transRecord.setAgreeAmount(taskTrans.getAgreeAmount());
		transRecord.setOpposeAmount(taskTrans.getOpposeAmount());
		bpmTaskTransRecordManager.update(transRecord);
	}
	
	/**
	 * 设置任务流转记录相关数据
	 * @param opinionStatus
	 * @param transRecord
	 * @param tranTask
	 * @param opinion
	 */
	public void dealWithTransRecord(OpinionStatus opinionStatus,BpmTaskTransRecord transRecord,DefaultBpmTask tranTask,String opinion){
		String transOpinion = transRecord.getTransOpinion();
		IUser user = BpmUtil.getUser(tranTask.getAssigneeId());
		if(BeanUtils.isEmpty(transOpinion)){
			transOpinion = "";
		}
		//
		Map<String,String> params = new HashMap<String,String>();
		params.put("userId", user.getUserId());
		params.put("transRecordid", transRecord.getId());
		BpmTransReceiver transReceiver = transReceiverManager.getByTransRecordAndUserId(params);
		transReceiver.setCheckTime(new Date());
		transReceiver.setOpinion(opinion);
		transReceiver.setStatus((short)1);//已审核
		
		if(OpinionStatus.TRANS_AGREE.equals(opinionStatus)){//同意流转
			transOpinion +="【"+user.getFullname()+"】：同意；";
			transReceiver.setCheckType((short)1);
		}else if(OpinionStatus.TRANS_OPPOSE.equals(opinionStatus)){//反对流转
			transOpinion +="【"+user.getFullname()+"】：反对；";
			transReceiver.setCheckType((short)2);
		}
		transRecord.setTransOpinion(transOpinion);
		bpmTaskTransRecordManager.update(transRecord);
		transReceiverManager.update(transReceiver);
	}
}
