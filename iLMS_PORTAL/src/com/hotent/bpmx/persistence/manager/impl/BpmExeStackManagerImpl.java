package com.hotent.bpmx.persistence.manager.impl;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.MultiInstanceType;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.NodeDefTransient;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.natapi.inst.NatProInstanceService;
import com.hotent.bpmx.persistence.dao.BpmExeStackDao;
import com.hotent.bpmx.persistence.dao.BpmExeStackExecutorDao;
import com.hotent.bpmx.persistence.manager.BpmExeStackExecutorManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.model.BpmExeStack;
import com.hotent.bpmx.persistence.model.BpmExeStackExecutor;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.sys.util.ContextUtil;

@Service("bpmExeStackManager")
public class BpmExeStackManagerImpl extends AbstractManagerImpl<String, BpmExeStack> implements BpmExeStackManager
{
	@Resource
	BpmExeStackDao bpmExeStackDao;
	@Resource
	NatProInstanceService natProInstanceService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmExeStackExecutorDao bpmExeStackExecutorDao;
	@Resource
	BpmExeStackExecutorManager bpmExeStackExecutorManager;
	@Resource
	BpmExeStackManager bpmExeStackManager;

	@Override
	protected Dao<String, BpmExeStack> getDao()
	{
		return bpmExeStackDao;
	}

	/**
	 * 构建堆栈。
	 * 
	 * @param bpmTask
	 * @param parentStack
	 * @return BpmExeStack
	 */
	public BpmExeStack constructStack(BpmTask bpmTask, BpmExeStack parentStack)
	{
		Short interpose = 0;
		ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
		if (actionCmd instanceof TaskFinishCmd)
		{
			if (((TaskFinishCmd) actionCmd).isInterpose())
			{
				interpose = 1;
			}
		}

		String id = UniqueIdUtil.getSuid();

		BpmExeStack stack = new BpmExeStack();
		stack.setId(id);
		stack.setPrcoDefId(bpmTask.getProcDefId());
		stack.setNodeId(bpmTask.getNodeId());
		stack.setNodeName(bpmTask.getName());

		stack.setStartTime(new Date());
		stack.setProcInstId(bpmTask.getProcInstId());
		stack.setInterpose(interpose);

		if (parentStack == null)
		{
			stack.setNodePath(id + ".");
			stack.setParentId("0");
		} else
		{
			String nodePath = parentStack.getNodePath() + id + ".";
			stack.setNodePath(nodePath);
			stack.setParentId(parentStack.getId());
		}

		return stack;
	}

	public BpmExeStack constructStack(String prcoDefId,String procInstId,String nodeId,String nodeName,String nodeType, short isMulitiTask,BpmExeStack parentStack)
	{
		Short interpose = 0;
		ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
		if (actionCmd instanceof TaskFinishCmd)
		{
			if (((TaskFinishCmd) actionCmd).isInterpose())
			{
				interpose = 1;
			}
		}
		String id = UniqueIdUtil.getSuid();
		BpmExeStack stack = new BpmExeStack();
		stack.setId(id);
		stack.setPrcoDefId(prcoDefId);
		stack.setNodeId(nodeId);
		stack.setNodeName(nodeName);

		stack.setStartTime(new Date());
		stack.setProcInstId(procInstId);
		stack.setInterpose(interpose);
		stack.setIsMulitiTask(isMulitiTask);
		stack.setNodeType(nodeType);
		if (parentStack == null)
		{
			stack.setNodePath(id + ".");
			stack.setParentId("0");
		} else
		{
			String nodePath = parentStack.getNodePath() + id + ".";
			stack.setNodePath(nodePath);
			stack.setParentId(parentStack.getId());
		}

		return stack;
	}


	
	@Override
	public BpmExeStack getInitStack(String instId)
	{
		BpmExeStack stack = this.bpmExeStackDao.getInitStack(instId);
		return stack;
	}

	@Override
	public BpmExeStack getPrevStack(String instId, String nodeId, String token)
	{
		List<BpmExeStack> curStackList = bpmExeStackDao.getByInstNodeToken(instId, nodeId, token);
		if (BeanUtils.isEmpty(curStackList))
		{
			throw new RuntimeException("因旧数据的堆栈信息为空！暂不能用此方式驳回。<br>请选择驳回到发起人");
		}

		BpmExeStack curStack = curStackList.get(0);

		String parentStackId = curStack.getParentId();
		if (StringUtil.isZeroEmpty(parentStackId))
		{
			return null;
		}
		BpmExeStack prevStack = bpmExeStackDao.get(parentStackId);
		return prevStack;
	}

	/**
	 * 退出堆栈。
	 * 
	 * <pre>
	 * 	1.找到当前节点。
	 * 	2.如果目标节点为空，处理模式可以不管。
	 *  3.如果目标节点不为空，设置处理模式。
	 *  	1.处理模式为normal
	 *  		删除目标节点之后的堆栈数据，设置目标节点堆栈handleMode为normal.
	 *  	2.处理模式为direct
	 *  		堆栈不做修改，设置目标节点堆栈的handleMode为direct.
	 * </pre>
	 * 
	 * @param instId
	 * @param currentNode
	 * @param token
	 * @param handleMode
	 * @param targetNode
	 *            void
	 */
	@Override
	public void popStack(String instId, String currentNode, String currentToken, String handleMode, String destinationNode,String destinationToken)
	{

		// 目标节点不为空,如果处理模式传入为空，那么处理模式默认为直接跳转回来。
		if (StringUtil.isEmpty(handleMode))
			handleMode = BpmExeStack.HAND_MODE_DIRECT;
		// 获取目标节点的堆栈数据。
		BpmExeStack targetStack = null;
		List<BpmExeStack> targetList = bpmExeStackDao.getByInstNodeToken(instId, destinationNode, destinationToken);
		if (BeanUtils.isEmpty(targetList))
		{
			throw new RuntimeException("没有找到目标节点堆栈数据!");
		}
		if (targetList.size() > 1)
		{
			//throw new RuntimeException("目标节点有多条分支!");
			for (BpmExeStack stk : targetList) {
				if(stk.getEndTime()!=null)
				{
					targetStack=stk;
				}
			}
		}
		if(targetStack==null)
		   targetStack = targetList.get(0);
		
		
		/***********修复有分支任务且是直接返回时，处理任务完成后无法跳转到指定任务节点***************/
		if(targetStack!=null){
			BpmExeStack bpmExeStack = bpmExeStackManager.getStack(targetStack.getProcInstId(), targetStack.getNodeId(), null);
			if(BeanUtils.isNotEmpty(bpmExeStack)&&bpmExeStack.getId().trim().equals(targetStack.getParentId())){
				// 如果驳回处理完直接返回：2、目标节点堆栈设置targetNode和targetToken 3、将parentStack放入线程变量
				if (BpmExeStack.HAND_MODE_DIRECT.equals(handleMode))
				{
					handelTargetStackCurrentExecuter(bpmExeStack, currentNode,currentToken, instId);
				}
			}
		}

		// 如果驳回处理完直接返回：2、目标节点堆栈设置targetNode和targetToken 3、将parentStack放入线程变量
		if (BpmExeStack.HAND_MODE_DIRECT.equals(handleMode))
		{
			handelTargetStackCurrentExecuter(targetStack, currentNode,currentToken, instId);
		}
		// 否则按照流程图执行，删除目标节点之后所有堆栈信息
		else
		{
			// 临时存放在CMD中，当新的目标节点创建成功后删除之后所有任务
			ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
			actionCmd.addTransitVars(BpmExeStack.HAND_MODE_NORMAL_TARGET_NODE_PATH, targetStack.getNodePath());
			actionCmd.addTransitVars(BpmExeStack.HAND_MODE_NORMAL_IS_CANCLE_NODE_PATH_TASK, true);
			 
			// 当驳回节点任务完成后。为驳回目标节点那个任务创建事件提供parentstack
			if (StringUtil.isNotZeroEmpty(targetStack.getParentId()))
			{
				ActionCmd cmd = ContextThreadUtil.getActionCmd();
				BpmExeStack parentStack = bpmExeStackDao.get(targetStack.getParentId());
				if (cmd != null)
					cmd.addTransitVars(BpmConstants.PARENT_STACK, parentStack);
			}
		}

	}

	// 指定目标节点，然后返回该节点。此时目标节点堆栈信息修改targetNode。
	private void handelTargetStackCurrentExecuter(BpmExeStack targetStack, String currentNode, String currentToken, String instId)
	{
		targetStack.setTargetNode(currentNode);
		targetStack.setTargetToken(currentToken); 
		targetStack.setStartTime(new Date());
		targetStack.setEndTime(null);
		bpmExeStackDao.update(targetStack);

		BpmExeStack currentStack = getStack(instId, currentNode, currentToken);
		List<BpmExeStackExecutor> executors = bpmExeStackExecutorDao.getByStackId(currentStack.getId());
		if (executors.size() == 1)
		{
			BpmExeStackExecutor executor = executors.get(0);
			executor.setAssigneeId(ContextUtil.getCurrentUserId());
			bpmExeStackExecutorDao.update(executor);
		}

	}

	/***
	 * 驳回到发起人
	 */
	@Override
	public void popStartStack(String instId, String currentNode, String handleMode)
	{
		BpmExeStack targetStack = bpmExeStackDao.getInitStack(instId);
		// 目标节点不为空,如果处理模式传入为空，那么处理模式默认为直接跳转回来。
		if (StringUtil.isEmpty(handleMode))
			handleMode = BpmExeStack.HAND_MODE_DIRECT;

		if (BpmExeStack.HAND_MODE_DIRECT.equals(handleMode)){
			handelTargetStackCurrentExecuter(targetStack, currentNode,null, instId);
		} 
		else{
			
			//按流程图走
			// 临时存放在CMD中，当新的目标节点创建成功后删除之后所有任务
			ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
			if(actionCmd.getTransitVars("IsUnused")!=null&&(Boolean) actionCmd.getTransitVars("IsUnused"))
			{
				//撤回时的动作
				 
			}
			else {
				actionCmd.addTransitVars(BpmExeStack.HAND_MODE_NORMAL_TARGET_NODE_PATH, targetStack.getNodePath());
				actionCmd.addTransitVars(BpmExeStack.HAND_MODE_NORMAL_IS_CANCLE_NODE_PATH_TASK, true);
				actionCmd.addTransitVars("HAND_MODE_NORMAL_REJECT_START", true);
			}

			//bpmExeStackDao.removeByPath(instId, targetStack.getNodePath());
		}

	}

	@Override
	public BpmExeStack getStack(String instId, String nodeId, String token)
	{
		List<BpmExeStack> list = bpmExeStackDao.getByInstNodeToken(instId, nodeId, token);
		if (BeanUtils.isNotEmpty(list)){
			for (BpmExeStack stk : list) {
				if(!stk.getNodeType().equals(NodeType.SUBSTARTGATEWAY.getKey()))
				{
					return stk;
				}
			}
		}
		return null;
	}
	
	/**
	 * 判断当前是否是未聚合事件。
	 * @param cmd
	 * @return
	 */
	private boolean isGatewayJoinEvent(ActionCmd cmd){
		boolean isGatewayUnmetJoinEvent = false;
		if (cmd.getTransitVars("CurrentEventType") != null && cmd.getTransitVars("CurrentEventType").toString().equals("GatewayUnmetJoinEvent"))
			isGatewayUnmetJoinEvent = true;
		return isGatewayUnmetJoinEvent;
	}

	/****
	 * 创建任务的时候
	 */
	@Override
	public void pushStack(BpmDelegateTask task){
		// 获取上一级的堆栈。
		String defId = (String) task.getVariable(BpmConstants.PROCESS_DEF_ID);
		String token = task.getVariable(BpmConstants.TOKEN_NAME)!=null?task.getVariable(BpmConstants.TOKEN_NAME).toString():null;
		String instId = (String) task.getVariable(BpmConstants.PROCESS_INST_ID);
		String nodeId = task.getTaskDefinitionKey();
		
		

		ActionCmd cmd = ContextThreadUtil.getActionCmd();
		BpmExeStack parentStack = null;
		BpmExeStack toStack = null;
				

		if (cmd instanceof DefaultTaskFinishCmd){
			parentStack = (BpmExeStack) cmd.getTransitVars(BpmConstants.PARENT_STACK);
			if (parentStack == null){
				// 线程变量中不存在parentSatck且 已经存在堆栈信息。 说明是 【驳回后 直接返回】。
				// 如果不存在堆栈信息，说明是【驳回后按流程图执行】驳回到了开始节点。那么重新构建堆栈信息。
				List<BpmExeStack> stackList = bpmExeStackDao.getByInstNodeToken(instId, nodeId, token);
				if(stackList.size()<=0){
					//退回的目标节点堆栈
					stackList = bpmExeStackDao.getByInstNodeToken(instId, nodeId, null);
				}
					
				if (BeanUtils.isNotEmpty(stackList)){
					toStack = stackList.get(0);
					toStack.setStartTime(new Date());
					toStack.setEndTime(null);
					bpmExeStackDao.update(toStack);
					return;
				}
			}
			// 假如驳回到（任务2）后返回该节点（任务4）时。创建任务4的堆栈信息，线程中的parentStack 是（任务2），此时是错误的
			// 故需要获取真实的parentStack,然后处理。
			else if (StringUtil.isNotEmpty(parentStack.getTargetNode())){
				
				parentStack.setTargetNode("");
				parentStack.setTargetToken("");
				parentStack.setEndTime(new Date());
				bpmExeStackDao.update(parentStack);
				
				List<BpmExeStack> stacks = bpmExeStackDao.getByInstNodeToken(instId, nodeId, token);
				if(BeanUtils.isEmpty(stacks))
					stacks = bpmExeStackDao.getByInstNodeToken(instId, nodeId, null);
				if (BeanUtils.isNotEmpty(stacks)){
					toStack = stacks.get(0);
					toStack.setStartTime(new Date());
					toStack.setEndTime(null);
					bpmExeStackDao.update(toStack);
					return;
				}
			}

		}
		
		/**********任务完成后将TargetNode置空*************/
		if(parentStack!=null){
			BpmExeStack bpmExeStack = bpmExeStackManager.getStack(parentStack.getProcInstId(), parentStack.getNodeId(), null);
			if(BeanUtils.isNotEmpty(bpmExeStack)&&bpmExeStack.getId().trim().equals(parentStack.getParentId())){
				bpmExeStack.setTargetNode("");
				bpmExeStackDao.update(bpmExeStack);
			}
		}
		/********************************************/
		
		MultiInstanceType instType = task.multiInstanceType();
		
		BpmTask bpmTask = getByTaskId(instId, task.getId());
		
		// 是否聚合未完毕事件
		boolean isGatewayUnmetJoinEvent=isGatewayJoinEvent(cmd);
		if(isGatewayUnmetJoinEvent){
			nodeId=bpmTask.getNodeId();
		}
		// 添加网关
		// 目标节点
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		//中间可能会有多个网关
		 List<NodeDefTransient> listGateway = BpmStackRelationUtil.getInComeGateway(defId, nodeId, parentStack);
		if (BeanUtils.isNotEmpty(listGateway)&& cmd instanceof DefaultTaskFinishCmd){
			short isMulitiTask=(short)(MultiInstanceType.NO.equals(instType)?0:1);
			int n=listGateway.size();
			  //当中间有多个时，需要构建中间网关的关系 
			  for(int i=n-1;i>=0;i--){
					//网关间的堆栈关联起来
					NodeDefTransient fromGateNode=listGateway.get(i);
					BpmExeStack stack=buildStack(fromGateNode,bpmTask,isMulitiTask,parentStack);
					BpmStackRelationUtil.createBpmExeStackRelation(instId, parentStack, stack);
					parentStack=stack;
			  }
			toStack = constructStack(bpmTask, parentStack);
			toStack.setIsMulitiTask((short) isMulitiTask);
			toStack.setTaskToken(token);
			toStack.setNodeType(bpmNodeDef.getType().toString());
			if(getStack(instId, nodeId, token) == null){
			   bpmExeStackDao.create(toStack);
			}
			BpmStackRelationUtil.createBpmExeStackRelation(instId, parentStack, toStack);
			
		} 
		else{

			String targetNodeType = bpmNodeDef.getType().toString();
			
			// 如果是网关聚合未完毕事件而取线程中的变速节点类型
			if (isGatewayUnmetJoinEvent){
				targetNodeType = cmd.getTransitVars("GatewayUnmetNoteType").toString();
				nodeId=bpmTask.getNodeId();
				
			}
			if (MultiInstanceType.NO.equals(instType)){
				toStack = constructStack(bpmTask, parentStack);
				toStack.setIsMulitiTask((short) 0);
				toStack.setTaskToken(token);
				toStack.setNodeType(targetNodeType);
				
				if(isGatewayUnmetJoinEvent){
					List<BpmExeStack> dbGatewaysStacks = bpmExeStackDao.getByInstNodeToken(bpmTask.getProcInstId(), nodeId,"");
					//查询堆栈是否已存在
					if (BeanUtils.isNotEmpty(dbGatewaysStacks))
					{
						toStack.setId(dbGatewaysStacks.get(0).getId());
					}
					else{
						bpmExeStackDao.create(toStack);
					}
				}else{
					bpmExeStackDao.create(toStack);
				}
				
				
			} 
			else{
				toStack = getStack(instId, nodeId, token);
				if (toStack == null){
					toStack = constructStack(bpmTask, parentStack);
					toStack.setIsMulitiTask((short) 1);
					toStack.setTaskToken(token);
					toStack.setNodeType(targetNodeType);
					bpmExeStackDao.create(toStack);
				}
			}
			BpmStackRelationUtil.createBpmExeStackRelation(instId, parentStack, toStack);

		}
		if(isGatewayUnmetJoinEvent) return;
		// 添加任务执行人
		String stackId = toStack.getId();
		BpmExeStackExecutor executor = getBpmExeStackExecutor(stackId, bpmTask.getId(), bpmTask.getAssigneeId());
		bpmExeStackExecutorDao.create(executor);

	}

	//构建网关堆栈对象
	private BpmExeStack  buildStack(NodeDefTransient gatewayNode,BpmTask bpmTask,short isMulitiTask,BpmExeStack parentStack) {
		BpmExeStack stack = new BpmExeStack();
		stack = constructStack(bpmTask.getProcDefId(),bpmTask.getProcInstId(),gatewayNode.getNodeId(),gatewayNode.getName(),gatewayNode.getType().toString(), isMulitiTask,  parentStack);
		// 从数据库中取网关堆栈是否已存在
		List<BpmExeStack> dbGatewaysStacks = bpmExeStackDao.getByInstNodeToken(bpmTask.getProcInstId(), gatewayNode.getNodeId(),"");
		if (BeanUtils.isNotEmpty(dbGatewaysStacks))
		{
			gatewayNode.setBpmGatewayStackId(dbGatewaysStacks.get(0).getId());
			//TODO 待验证:连续的会签环节 逐个驳回时会导致死循环
			stack.setId(dbGatewaysStacks.get(0).getId());
			stack.setNodePath(dbGatewaysStacks.get(0).getNodePath());
			//stack.setParentId(parentStack.getId());
		}
		else {
			gatewayNode.setBpmGatewayStackId(stack.getId());
			bpmExeStackDao.create(stack);
		}
		return stack;
	}
	
	/**
	 * 获取执行人。
	 * 
	 * @param stackId
	 * @param taskId
	 * @param assignee
	 * @return BpmExeStackExecutor
	 */
	private BpmExeStackExecutor getBpmExeStackExecutor(String stackId, String taskId, String assignee){
		String id = UniqueIdUtil.getSuid();
		BpmExeStackExecutor executor = new BpmExeStackExecutor();
		executor.setCreateOrgId(id);
		executor.setStackId(stackId);
		executor.setTaskId(taskId);
		if (StringUtil.isEmpty(assignee)){
			assignee = "0";
		}
		executor.setAssigneeId(assignee);
		executor.setStatus(0);
		executor.setCreateTime(new Date());

		return executor;
	}

	private BpmTask getByTaskId(String instId, String taskId){
		BpmTask bpmTask = null;
		Set<BpmTask> taskSet = ContextThreadUtil.getByInstId(instId);
		for (Iterator<BpmTask> it = taskSet.iterator(); it.hasNext();){
			bpmTask = it.next();
			if (bpmTask.getTaskId().equals(taskId))
				break;
		}
		return bpmTask;
	}

	

	@Override
	public List<BpmExeStack> getPreStacksByInstIdNodeId(String procInstId, String nodeId)
	{
		List<BpmExeStack> exeStacks = bpmExeStackDao.getByInstNodeToken(procInstId, nodeId, null);
		if (BeanUtils.isEmpty(exeStacks))
			return Collections.emptyList();
		String path = exeStacks.get(0).getNodePath();
		path = path.replace(exeStacks.get(0).getId() + ".", "");

		String[] ids = path.split("\\.");
		if (ids.length == 0)
			return Collections.emptyList();

		return bpmExeStackDao.getByIds(ids);
	}
}
