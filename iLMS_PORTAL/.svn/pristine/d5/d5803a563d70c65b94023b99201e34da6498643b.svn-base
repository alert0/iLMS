package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.persistence.model.BpmExeStack;

public interface BpmExeStackManager extends Manager<String, BpmExeStack>{
	
	
	
	/**
	 * 获取发起的堆栈数据。
	 * @param instId
	 * @return  BpmExeStack
	 */
	BpmExeStack getInitStack(String instId);
	
	
	/**
	 * 获取堆栈数据。
	 * @param instId
	 * @param nodeId
	 * @param token
	 * @return BpmExeStack
	 */
	BpmExeStack getStack(String instId,String nodeId,String token);
	
	
	/**
	 * 获取上一步堆栈数据。
	 * <pre>
	 * 1.查询当前的堆栈数据。
	 * 2.查找上一个堆栈的数据。
	 * </pre>
	 * @param instId	实例ID
	 * @param nodeId	节点ID
	 * @param token 	token
	 * void
	 */
	BpmExeStack getPrevStack(String instId,String nodeId,String token);
	
	
	/**
	 * 退出堆栈。
	 * <pre>
	 * 	1.找到当前节点。
	 * 	2.如果目标节点为空，处理模式可以不管。
	 *  2.如果目标节点不为空，设置处理模式。
	 *  	1.处理模式为normal
	 *  		删除目标节点之后的堆栈数据，设置目标节点堆栈handleMode为normal.
	 *  	2.处理模式为direct
	 *  		堆栈不做修改，设置目标节点堆栈的handleMode为direct.
	 * </pre>
	 * @param instId
	 * @param currentNode
	 * @param token
	 * @param handleMode
	 * @param targetNode 
	 * void
	 */
	void popStack(String instId,String currentNode,String currentToken, String handleMode,String destinationNode,String destinationToken);
	
	
	/**
	 * 退出堆栈到开始节点。
	 * @param instId
	 * @param handleMode 
	 * void
	 */
	void popStartStack(String instId,String currrentNode, String handleMode);
	
	
	/**
	 * 加入堆栈。
	 * @param task 
	 * void
	 */
	void pushStack(BpmDelegateTask task);
	
	
	

	/***
	 * 获取某节点之前的堆栈信息列表
	 * @param procInstId
	 * @param nodeId
	 * @return
	 */
	List<BpmExeStack> getPreStacksByInstIdNodeId(String procInstId, String nodeId);
	
	
}
