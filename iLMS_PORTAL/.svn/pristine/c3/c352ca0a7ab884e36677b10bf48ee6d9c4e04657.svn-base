package com.hotent.bpmx.persistence.util;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.entity.SubProcessStartOrEndEventModel;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SubProcessNodeDef;
import com.hotent.bpmx.api.model.process.task.NodeDefTransient;
import com.hotent.bpmx.persistence.dao.ActExecutionDao;
import com.hotent.bpmx.persistence.dao.ActTaskDao;
import com.hotent.bpmx.persistence.dao.BpmExeStackDao;
import com.hotent.bpmx.persistence.dao.BpmExeStackRelationDao;
import com.hotent.bpmx.persistence.dao.BpmProcessInstanceDao;
import com.hotent.bpmx.persistence.model.ActExecution;
import com.hotent.bpmx.persistence.model.ActTask;
import com.hotent.bpmx.persistence.model.BpmExeStack;
import com.hotent.bpmx.persistence.model.BpmExeStackRelation;


/*
 * 驳回辅助工具
 */
public class BpmStackRelationUtil
{

	/**
	 * 从流程定义中获取指定节点来路网关定义
	 * 
	 * @param defId           流程定义ID
	 * @param nodeId            节点ID
	 * @param fromStack           节点的来路堆栈
	 * @return
	 */
	public static List<NodeDefTransient> getInComeGateway(String defId, String nodeId, BpmExeStack fromStack)
	{
		List<NodeDefTransient> listResult=new ArrayList<NodeDefTransient>();
		if (fromStack == null) return null;
		
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);
		
		// 判断当前是否存在子流程开始或结束结点的网关，优先判断子流程多实例网关
		ActionCmd cmd = ContextThreadUtil.getActionCmd();
		String currentEventType = cmd.getTransitVars("CurrentEventType") != null ? cmd.getTransitVars("CurrentEventType").toString() : null;
		String isSubProcessMultiStartOrEndEvent = cmd.getTransitVars("SubProcessMultiStartOrEndEvent") != null ? cmd.getTransitVars("SubProcessMultiStartOrEndEvent").toString() : null;

		if (isSubProcessMultiStartOrEndEvent != null){
			SubProcessStartOrEndEventModel model = (SubProcessStartOrEndEventModel) cmd.getTransitVars("SubProcessMultiStartOrEndEventModel");
		
			String nodeType = model.getNoteType();
			BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(fromStack.getPrcoDefId(), model.getNodeId());
			
			NodeDefTransient nodeDef=new NodeDefTransient(bpmNodeDef);
			
			nodeDef.setType(NodeType.fromKey(nodeType));
			listResult.add(nodeDef);
			return listResult;
		} 
		else if (currentEventType != null && currentEventType.equals("SubProcessStartOrEndEvent"))
		{
			SubProcessStartOrEndEventModel model = (SubProcessStartOrEndEventModel) cmd.getTransitVars("SubProcessStartOrEndEventModel");
		
			String nodeType = model.getNoteType();
			BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(fromStack.getPrcoDefId(), model.getNodeId());
			
			NodeDefTransient nodeDef=new NodeDefTransient(bpmNodeDef);
			
			nodeDef.setType(NodeType.fromKey(nodeType));
			listResult.add(nodeDef);
			return listResult;
		} 

		NodeDefTransient nodeDef = null;
		List<NodeDefTransient> histSearchNodeList=new ArrayList<NodeDefTransient>();
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<BpmNodeDef> inComeList = bpmNodeDef.getIncomeNodes();
		
		String parentNodeId = fromStack.getNodeId();
		// 找出实例来路的网关或者内嵌子流程开始节点或子流程结束节点
		for (BpmNodeDef node : inComeList){
			if (nodeDef != null) break;
			NodeType noteType = node.getType();
			if (noteType.equals(NodeType.EXCLUSIVEGATEWAY) || noteType.equals(NodeType.PARALLELGATEWAY) || noteType.equals(NodeType.INCLUSIVEGATEWAY)){
				histSearchNodeList.add(new NodeDefTransient(node));
				nodeDef=getInComeDateWay(node,parentNodeId,histSearchNodeList);
			}
		}
		return histSearchNodeList;
	}

	//递归找出由节点任务发送来时中间经过的来路网关
	//histSearchNodeList:当前历史的节点,注意列表中是反向的，索引值为0是接近目标节点的网关，列表索引值最大的接近来向节点
	private static NodeDefTransient getInComeDateWay(BpmNodeDef node,String parentNodeId,List<NodeDefTransient> histSearchNodeList) {
		
		NodeDefTransient resultNode=null;
		List<BpmNodeDef> inList = node.getIncomeNodes();
		for (BpmNodeDef theNode : inList){
			if (theNode.getNodeId().equals(parentNodeId)){
				resultNode=new NodeDefTransient(node);
				break;
			}
			NodeType noteType = theNode.getType();
			if (noteType.equals(NodeType.EXCLUSIVEGATEWAY) || noteType.equals(NodeType.PARALLELGATEWAY) || noteType.equals(NodeType.INCLUSIVEGATEWAY)){
				histSearchNodeList.add(new NodeDefTransient(theNode));
				resultNode=getInComeDateWay(theNode,parentNodeId,histSearchNodeList);
			}
		}
	     if(BeanUtils.isNotEmpty(histSearchNodeList)&&resultNode==null){
	    	 //清除本次遍历的网关
	    	 for (NodeDefTransient item : histSearchNodeList) {
				if(item.getNodeId().equals(node.getNodeId())){
					histSearchNodeList.remove(item);
					break;
					
				}
			}
		}
	    if(resultNode!=null&&histSearchNodeList!=null&&histSearchNodeList.size()>0){
	    	//取回目标节点最近的那个网关
	    	resultNode=histSearchNodeList.get(0);
	    }
		return resultNode;
	}
	

	/**
	 * 判断指定实例节点是否有走过前置网关（包括子流程开始节点和结束结点这两个特殊的网关）
	 * 
	 * @param bpmProcInstId
	 * @param ondeId
	 * @param direction
	 *            方向，前pre，后after
	 * @return
	 */
	public static boolean isHaveAndOrGateway(String bpmProcInstId, String ondeId, String direction)
	{
		BpmExeStackRelationDao relationDao = AppUtil.getBean(BpmExeStackRelationDao.class);
		List<BpmExeStackRelation> list = relationDao.getListByProcInstId(bpmProcInstId);
		return isHaveAndOrGateway( bpmProcInstId,  ondeId,  direction, list);
	}
	
	public static boolean isHaveAndOrGateway(String bpmProcInstId, String nodeId, String direction,List<BpmExeStackRelation> list)
	{
		BpmProcessInstanceDao instanceDao = AppUtil.getBean(BpmProcessInstanceDao.class);
				
		BpmExeStackRelation relation = null;
		for (BpmExeStackRelation bpmExeStackRelation : list){
			String fromNodeId = bpmExeStackRelation.getFromNodeId();
			String toNodeId = bpmExeStackRelation.getToNodeId();
			//在内部子流程中，开始节点后面的那个节点可能存在两条堆栈关系的记录，其中有一条是 fromNodeId 和  toNodeId 相同的，这里忽略这条记录
			if(fromNodeId.equals(toNodeId)) continue;
			if("pre".equals(direction)){
				// 向前：谁给我的，那么自己就是在To的位置
				if(toNodeId.equals(nodeId)){
					relation = bpmExeStackRelation;
					break;
				}
			}
			else if("after".equals(direction)){
				// 向后：我给了谁，那么自己就是在From的位置
				if(fromNodeId.equals(nodeId)){
					relation = bpmExeStackRelation;
					break;
				}
			}
		}
		if (relation == null) return false;
		// 向后找
		String noteType = relation.getToNodeType();
		String rnodeType = relation.getFromNodeType();
		if ("after".equals(direction)){
			noteType = relation.getFromNodeType();
			rnodeType = relation.getToNodeType();
		}
		if (noteType.equals(NodeType.PARALLELGATEWAY.getKey()) || noteType.equals(NodeType.INCLUSIVEGATEWAY.getKey()) 
				|| noteType.equals(NodeType.SUBSTARTGATEWAY.getKey()) || noteType.equals(NodeType.SUBENDGATEWAY.getKey()) 
				|| (noteType.equals(NodeType.SUBMULTISTARTGATEWAY.getKey())&&!rnodeType.equals(NodeType.SIGNTASK.getKey()))){
			return true;
		}
		BpmProcessInstance instance = instanceDao.get(bpmProcInstId);
		String defId = instance.getProcDefId();
		BpmNodeDef bpmNodeDef = direction.equals("pre") ? getPreParallelBpmNodeDef(list, relation, defId) : getAfterParallelBpmNodeDef(list, relation, defId);
		return bpmNodeDef != null;
	}
	
	

	/**
	 * 创建堆栈关系
	 * 
	 * @param procInstId
	 *            流程实例
	 * @param fromBpmExeStack
	 *            来向堆栈
	 * @param toBpmExeStack
	 *            到达堆栈
	 * @throws Exception
	 */
	public static void createBpmExeStackRelation(String procInstId, BpmExeStack fromBpmExeStack, BpmExeStack toBpmExeStack){
		if (fromBpmExeStack == null) return;
		BpmExeStackRelationDao bpmExeStackRelationDao = AppUtil.getBean(BpmExeStackRelationDao.class);
		
		BpmExeStackRelation entity = new BpmExeStackRelation();
		entity.setRelationId(UniqueIdUtil.getSuid());
		entity.setFromStackId(fromBpmExeStack.getId());
		entity.setToStackId(toBpmExeStack.getId());
		entity.setToNodeId(toBpmExeStack.getNodeId());
		entity.setToNodeType(toBpmExeStack.getNodeType());
		entity.setFromNodeId(fromBpmExeStack.getNodeId());
		String fromNodeType = fromBpmExeStack.getNodeType();
		entity.setFromNodeType(fromNodeType);
		entity.setProcInstId(procInstId);
		bpmExeStackRelationDao.create(entity);
	}

	/**
	 * 获取指定节点在堆栈中的前继节点实例中的And OR网关 子流程开始网关，子流程结束网关
	 * 
	 * @param list
	 * @param relation
	 * @param defId
	 * @return
	 */
	private static BpmNodeDef getPreParallelBpmNodeDef(List<BpmExeStackRelation> list, BpmExeStackRelation relation, String defId)
	{
		if (relation == null) 	return null;
		BpmNodeDef bpmNodeDef = null;
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);
		String noteType = relation.getFromNodeType();

		if (noteType.equals(NodeType.PARALLELGATEWAY.getKey()) || noteType.equals(NodeType.INCLUSIVEGATEWAY.getKey()) 
				|| noteType.equals(NodeType.SUBSTARTGATEWAY.getKey()) || noteType.equals(NodeType.SUBENDGATEWAY.getKey()) 
				|| noteType.equals(NodeType.SUBMULTISTARTGATEWAY.getKey()))
		{
			bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, relation.getToNodeId());
			return bpmNodeDef;
		}

		List<BpmExeStackRelation> listNewBpmExeStacks = new ArrayList<BpmExeStackRelation>();
		String fromStackId = relation.getFromStackId();
		// 收集父迁移关系
		for (BpmExeStackRelation bpmExeStackRelation : list){
			if (!bpmExeStackRelation.getFromNodeId().equals(bpmExeStackRelation.getToNodeId()) && bpmExeStackRelation.getToStackId().equals(fromStackId)&&!listNewBpmExeStacks.contains(bpmExeStackRelation)){
				listNewBpmExeStacks.add(bpmExeStackRelation);
			}
		}
		for (BpmExeStackRelation bpmExeStackRelation : listNewBpmExeStacks){
			bpmNodeDef = getPreParallelBpmNodeDef(list, bpmExeStackRelation, defId);
			if (bpmNodeDef != null) break;
		}
		return bpmNodeDef;
	}

	/**
	 * 获取指定节点在堆栈中的后续节点实例中的And OR网关
	 * 
	 * @param list
	 * @param relation
	 * @param defId
	 * @return
	 */
	private static BpmNodeDef getAfterParallelBpmNodeDef(List<BpmExeStackRelation> list, BpmExeStackRelation relation, String defId){
		if (relation == null) return null;
		BpmNodeDef bpmNodeDef = null;
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);
		String noteType = relation.getToNodeType();

		if (noteType.equals(NodeType.PARALLELGATEWAY.getKey()) || noteType.equals(NodeType.INCLUSIVEGATEWAY.getKey()) 
				|| noteType.equals(NodeType.SUBSTARTGATEWAY.getKey()) || noteType.equals(NodeType.SUBENDGATEWAY.getKey()) 
				|| noteType.equals(NodeType.SUBMULTISTARTGATEWAY.getKey()))
		{
			bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, relation.getToNodeId());
			return bpmNodeDef;
		}

		List<BpmExeStackRelation> listNewBpmExeStacks = new ArrayList<BpmExeStackRelation>();
		String toStackId = relation.getToStackId();
		// 收集父迁移关系
		for (BpmExeStackRelation bpmExeStackRelation : list){
			if (bpmExeStackRelation.getFromStackId().equals(toStackId)&&!listNewBpmExeStacks.contains(bpmExeStackRelation)){
				listNewBpmExeStacks.add(bpmExeStackRelation);
			}
		}
		for (BpmExeStackRelation bpmExeStackRelation : listNewBpmExeStacks){
			bpmNodeDef = getAfterParallelBpmNodeDef(list, bpmExeStackRelation, defId);
			if (bpmNodeDef != null) break;
		}
		return bpmNodeDef;
	}

	public static List<BpmNodeDef> getHistoryListBpmNodeDef(String bpmProcInstId, String ondeId, String direction){
		List<BpmNodeDef> listResult = new ArrayList<BpmNodeDef>();
		BpmExeStackRelationDao relationDao = AppUtil.getBean(BpmExeStackRelationDao.class);
		BpmProcessInstanceDao instanceDao = AppUtil.getBean(BpmProcessInstanceDao.class);
		List<BpmExeStackRelation> list = relationDao.getListByProcInstId(bpmProcInstId);
		BpmExeStackRelation relation = null;
		for (BpmExeStackRelation bpmExeStackRelation : list){
			if ("pre".equals(direction) && bpmExeStackRelation.getToNodeId().equals(ondeId) &&
					!bpmExeStackRelation.getFromNodeId().equals(bpmExeStackRelation.getToNodeId()) ){
				relation = bpmExeStackRelation;
				break;
			}
			if ("after".equals(direction) &&  bpmExeStackRelation.getFromNodeId().equals(ondeId) &&
					!bpmExeStackRelation.getFromNodeId().equals(bpmExeStackRelation.getToNodeId()) ){
				relation = bpmExeStackRelation;
				break;
			}
		}
		if (relation == null) return listResult;
		BpmProcessInstance instance = instanceDao.get(bpmProcInstId);
		String defId = instance.getProcDefId();

		return listResult = getHistoryListBpmNodeDef(list, relation, defId, direction, listResult);
	}

	/**
	 * 收集指定节点的历史轨迹
	 * 
	 * @param list           实例对应的堆栈关系列表
	 * @param relation           关系对象
	 * @param defId          定义ID
	 * @param direction        方向pre前继，after后续
	 * @param resultList         结果集
	 * @return
	 */
	private static List<BpmNodeDef> getHistoryListBpmNodeDef(List<BpmExeStackRelation> list, BpmExeStackRelation relation,
			String defId, String direction, List<BpmNodeDef> resultList){
		
		BpmExeStackDao bpmExeStackDao = AppUtil.getBean(BpmExeStackDao.class);
		if (relation == null) return null;
		
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);

		String nodeId = relation.getToNodeId();
		if (direction.equals("pre"))
			nodeId = relation.getFromNodeId();
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		if (bpmNodeDef != null && !resultList.contains(bpmNodeDef))
			resultList.add(bpmNodeDef);

		List<BpmExeStackRelation> listNewBpmExeStacks = new ArrayList<BpmExeStackRelation>();
		String fromStackId = relation.getFromStackId();
		String toStackId = relation.getToStackId();
		 
		// 收集父迁移关系
		for (BpmExeStackRelation bpmExeStackRelation : list){
			if (direction.equals("pre")){
				if (bpmExeStackRelation.getToStackId().equals(fromStackId)
					&&!bpmExeStackRelation.getFromStackId().equals(bpmExeStackRelation.getToStackId())&&!listNewBpmExeStacks.contains(bpmExeStackRelation)){
					listNewBpmExeStacks.add(bpmExeStackRelation);
				}
			} 
			else{
				if (bpmExeStackRelation.getFromStackId().equals(toStackId)&&!listNewBpmExeStacks.contains(bpmExeStackRelation)){
					listNewBpmExeStacks.add(bpmExeStackRelation);
				}
			}

		}
		for (BpmExeStackRelation bpmExeStackRelation : listNewBpmExeStacks){
			if(listNewBpmExeStacks.size()>1){
				BpmExeStack fromStack = bpmExeStackDao.get(bpmExeStackRelation.getFromStackId());
				BpmExeStack toStack = bpmExeStackDao.get(bpmExeStackRelation.getToStackId());
				//如果是多实例的情况，可能会进入死循环。
				if(direction.equals("pre") && !fromStack.getNodeId().equals(toStack.getNodeId()) && fromStack!=null && toStack!=null && fromStack.getStartTime()!=null && toStack.getStartTime() != null){
					long time = DateUtil.getTime(fromStack.getStartTime(), toStack.getStartTime());
					if(time < 0){
						continue;
					}
				}
				
				//  （同一个节点的跳过）可以修复会签驳回，进入死循环问题 
				if(fromStack!=null && toStack!=null && fromStack.getStartTime()!=null && toStack.getStartTime() != null 
				&& fromStack.getProcInstId().equals(toStack.getProcInstId()) && fromStack.getNodeId().equals(toStack.getNodeId())  ){
					continue;
				}
				
				
			}
			getHistoryListBpmNodeDef(list, bpmExeStackRelation, defId, direction, resultList);

		}
		return resultList;
	}

	/**
	 * 判断两个节点之间是否有多实例子流程网关
	 * 
	 * @param bpmProcInstId
	 * @param startNodeId
	 * @param endNodeId
	 * @return
	 */
	public static boolean isHaveMultiGatewayByBetweenNode(String bpmProcInstId, String startNodeId, String endNodeId){
		// subMultiStartGateway
		List<BpmNodeDef> list = getHistoryListByBetweenNode(bpmProcInstId, startNodeId, endNodeId);
		for (BpmNodeDef bpmNodeDef : list){
 			if(SubProcessNodeDef.class.getName().equals(bpmNodeDef.getClass().getName())){
 				SubProcessNodeDef subNodeDef=(SubProcessNodeDef)bpmNodeDef;
 				if(subNodeDef.isParallel())return true;
			}
			String noteType = bpmNodeDef.getType().getKey();
			if (noteType.equals(NodeType.SUBMULTISTARTGATEWAY.getKey()) || noteType.equals(NodeType.SUBENDGATEWAY.getKey())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回两个节点之间的历史轨迹 startNodeId:为剑头起始的节点，endNodeId为剑头指向的结点
	 * 
	 * @param startNodeId
	 * @param endNodeId
	 * @return
	 */
	public static List<BpmNodeDef> getHistoryListByBetweenNode(String bpmProcInstId, String startNodeId, String endNodeId){
		List<BpmNodeDef> list = new ArrayList<BpmNodeDef>();
		// 取交集startNodeId:为剑头开始的节点，endNodeId为剑头结束的结点
		List<BpmNodeDef> listStartNodeIdPre = getHistoryListBpmNodeDef(bpmProcInstId, startNodeId, "after");
		List<BpmNodeDef> listEndNodeIdafter = getHistoryListBpmNodeDef(bpmProcInstId, endNodeId, "pre");
		for (BpmNodeDef startNode : listStartNodeIdPre){
			for (BpmNodeDef endNode : listEndNodeIdafter){
				if (startNode.getNodeId().equals(endNode.getNodeId())){
					list.add(startNode);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 直接返回执行的单实例同步网关驳回，对act_ru_execution进行调整
	 * 
	 * @param rejectSingleExecutionId
	 * @return
	 */
	public static boolean instancesRejectDirectAdjust(String rejectDirectExecutionId){
		ActExecutionDao actExecutionDao = AppUtil.getBean(ActExecutionDao.class);
		ActExecution currentExecution = actExecutionDao.get(rejectDirectExecutionId);
		// 任务完成数据
		TaskFinishCmd cmd = (TaskFinishCmd) ContextThreadUtil.getActionCmd();
		if(currentExecution==null)return true;
		Object rejectDirectParentId = cmd.getTransitVars("rejectDirectParentId");
		//如果同步网关中直来直往驳回时 ParentId有变化
		if(rejectDirectParentId!=null && !rejectDirectParentId.toString().equals(currentExecution.getParentId())){
			if(currentExecution.getParentId().equals(currentExecution.getProcInstId())){
				List<String> parentsId = actExecutionDao.getByParentsId(currentExecution.getParentId());
				//当出现多条记录的父ID为流程实例ID，修复流程轨迹
				if(BeanUtils.isNotEmpty(parentsId) && parentsId.size() > 1){
					for(String parentId:parentsId){
						if(!parentId.equals(currentExecution.getId())){
							currentExecution.setParentId(parentId);
							actExecutionDao.update(currentExecution);
							break;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	/**
	 * 直接返回执行的单实例同步网关退回，对act_ru_execution进行调整
	 * 
	 * @param rejectSingleExecutionId
	 * @return
	 */
	public static boolean parallelGatewayRejectDirectAdjust(String parentExecutionId){
		ActExecutionDao actExecutionDao = AppUtil.getBean(ActExecutionDao.class);
		ActExecution parentExecution = actExecutionDao.get(parentExecutionId);
		ActTaskDao actTaskDao = AppUtil.getBean(ActTaskDao.class);
		if(parentExecution==null)return true;
		
		String actProcInstanceId = parentExecution.getProcInstId();
		// 任务完成数据
		TaskFinishCmd cmd = (TaskFinishCmd) ContextThreadUtil.getActionCmd();
		// 退回时的目标节点
		String rejectTargetNodeId = cmd.getDestination();
		String actionName = cmd.getActionName() ;
		//如果是退回
		if(StringUtil.isNotEmpty(rejectTargetNodeId) && !("reject".equals(actionName) || "backToStart".equals(actionName))){
			// 取上级的执行线程
			List<String> parentsId = actExecutionDao.getByParentsId(parentExecution.getId());
			if(BeanUtils.isNotEmpty(parentsId)){
				ActExecution currentExecution = actExecutionDao.get(parentsId.get(0));
				//如果上一步执行存在且为并行且上一步执行的父ID不等于流程实例ID：则需修复
				if(parentExecution.getIsConcurrent().toString().equals("1") && !parentExecutionId.equals(parentExecution.getProcInstId())){
					parentExecution.setIsActive((short) 1);
					parentExecution.setActId(currentExecution.getActId());
					//更新父执行记录为当前记录
					actExecutionDao.update(parentExecution);
					
					List<ActTask> actTasks = actTaskDao.getByInstId(actProcInstanceId);
					if(BeanUtils.isNotEmpty(actTasks)){
						for(ActTask actTask:actTasks){
							if(actTask.getTaskDefKey()!=null && actTask.getTaskDefKey().equals(parentExecution.getActId())){
								actTask.setExecutionId(parentExecutionId);
								actTaskDao.update(actTask);
							}
						}
					}
					//删除驳回错误产生的执行记录
					actExecutionDao.remove(currentExecution.getId());
				}
			}
		}
		
		return true;
	}
	
	
	/**
	 * 按流程图执行的单实例退回，对act_ru_execution及任务进行调整
	 * 
	 * @param rejectSingleExecutionId
	 * @return
	 */
	public static boolean singleInstancesRejectAdjust(String rejectSingleExecutionId){
		ActExecutionDao actExecutionDao = AppUtil.getBean(ActExecutionDao.class);
		BpmExeStackDao bpmExeStackDao = AppUtil.getBean(BpmExeStackDao.class);
		ActTaskDao actTaskDao = AppUtil.getBean(ActTaskDao.class);
		
		// 1.修改act的任务表；2.修改act_ru_execution主实例线程
		ActExecution currentExecution = actExecutionDao.get(rejectSingleExecutionId);
		if(currentExecution==null)return true;
		String actProcInstanceId = currentExecution.getProcInstId();
		
		bpmExeStackDao.multipleInstancesRejectAdjustOnActTask(rejectSingleExecutionId);
		bpmExeStackDao.multipleInstancesRejectAdjustOnActExecution(actProcInstanceId);
		
		if(actExecutionDao.get(currentExecution.getId())==null){
			currentExecution.setParentId(actProcInstanceId);
			currentExecution.setIsScope((short) 1);
			currentExecution.setIsConcurrent((short) 0);
			actExecutionDao.create(currentExecution);
			
			List<ActTask> actTasks = actTaskDao.getByInstId(actProcInstanceId);
			if(BeanUtils.isNotEmpty(actTasks)){
				for(ActTask actTask:actTasks){
					actTask.setExecutionId(currentExecution.getId());
					actTaskDao.update(actTask);
				}
			}
		}
		
		return true;
	}
	
	
	/**
	 * 按流程图执行的多实例退回，对act_ru_execution及任务进行调整
	 * 
	 * @param rejectAfterExecutionId
	 * @return
	 */
	public static boolean multipleInstancesRejectAdjust(String rejectAfterExecutionId){
		ActExecutionDao actExecutionDao = AppUtil.getBean(ActExecutionDao.class);
		BpmExeStackDao bpmExeStackDao = AppUtil.getBean(BpmExeStackDao.class);
		// 1.修改BPM的任务表；2.修改act的任务表；3.修改act_ru_execution主实例线程
		ActExecution currentExecution = actExecutionDao.get(rejectAfterExecutionId);
		if(currentExecution==null)return true;
		String nodeId = currentExecution.getActId();
		String actProcInstanceId = currentExecution.getProcInstId();

		bpmExeStackDao.multipleInstancesRejectAdjustOnBpmTask(rejectAfterExecutionId);
		bpmExeStackDao.multipleInstancesRejectAdjustOnActTask(rejectAfterExecutionId);
		bpmExeStackDao.multipleInstancesRejectAdjustOnActExecution(actProcInstanceId);

		currentExecution = actExecutionDao.get(actProcInstanceId);
		currentExecution.setActId(nodeId);
		actExecutionDao.update(currentExecution);
		return true;
	}
	
	/**
	 *  获取指定节点的后续节点（如果节点后面是网关自动获取网关下一级的节点）
	 * @Title: getAfterListNode 
	 * @Description: TODO
	 * @param defId
	 * @param nodeId
	 * @return
	 * @return: List<BpmNodeDef>
	 */
	public static List<BpmNodeDef> getAfterListNode(String defId, String nodeId)
	{
		List<BpmNodeDef> listResult=new ArrayList<BpmNodeDef>();
		return getAfterListNode(defId,nodeId,listResult);
	}
	
	
	/**
	 * 获取指定节点的后续节点（如果节点后面是网关自动获取网关下一级的节点）
	 * @Title: getAfterListNode 
	 * @Description: TODO
	 * @param defId
	 * @param nodeId
	 * @param listResult
	 * @return
	 * @return: List<BpmNodeDef>
	 */
	private   static List<BpmNodeDef> getAfterListNode(String defId, String nodeId,List<BpmNodeDef> listResult) {
		BpmDefinitionAccessor bpmDefinitionAccessor = AppUtil.getBean(BpmDefinitionAccessor.class);
		BpmNodeDef nodeDef=bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<BpmNodeDef> listOut=nodeDef.getOutcomeNodes();
		for (BpmNodeDef outNode : listOut) {
			if(outNode.getType().getKey().equals(NodeType.USERTASK.getKey()))
			{
				listResult.add(outNode);
			}else {
				getAfterListNode(defId,outNode.getNodeId(),listResult);
			}
		}
		return listResult;
	}
	
	
	

}
