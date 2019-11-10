package com.hotent.bpmx.persistence.manager.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.VoteResult;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.persistence.dao.ActExecutionDao;
import com.hotent.bpmx.persistence.dao.BpmSignDataDao;
import com.hotent.bpmx.persistence.manager.BpmSignDataManager;
import com.hotent.bpmx.persistence.model.BpmSignData;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("bpmSignDataManager")
public class BpmSignDataManagerImpl extends AbstractManagerImpl<String, BpmSignData> implements BpmSignDataManager{
	@Resource
	BpmSignDataDao bpmSignDataDao;
	@Resource
	ActExecutionDao actExecutionDao;
	
	@Override
	protected Dao<String, BpmSignData> getDao() {
		return bpmSignDataDao;
	}
	
	@Override
	public List<BpmSignData> getVoteByExecuteNode(String executeId, String nodeId,Integer isActive) {
		return bpmSignDataDao.getVoteByExecuteNode(executeId, nodeId,isActive);
	}
	
	/**
	 * 获取会签对象。
	 * @param bpmTask
	 * @param taskExecutor
	 * @param batch
	 * @return   BpmSignData
	 */
	public BpmSignData getSignData(BpmTask bpmTask,String executeId, BpmIdentity bpmIdentity) {
		
		
		BpmSignData signData=new BpmSignData();
		signData.setId(UniqueIdUtil.getSuid());
		signData.setDefId(bpmTask.getProcDefId());
		signData.setInstId(bpmTask.getProcInstId());
		//获取线程ID
		signData.setExecuteId(executeId);
		signData.setActInstId(bpmTask.getBpmnInstId());
		signData.setNodeId(bpmTask.getNodeId());
		signData.setTaskId(bpmTask.getTaskId());
		signData.setQualifiedId(bpmIdentity.getId());
		signData.setQualifiedName(bpmIdentity.getName());
		signData.setCreateTime(new Date());
		signData.setVoteResult(VoteResult.NO.getKey());
		signData.setIsActive(1);
		return signData;
	}
	
	@Override
	public void addSignData(String defId, String instId, String actInstId,String executeId,
			String nodeId, String taskId, String qualifiedId,
			String qualifiedName,Short index,String signType) {
		BpmSignData signData=new BpmSignData();
		signData.setId(UniqueIdUtil.getSuid());
		signData.setDefId(defId);
		signData.setInstId(instId);
		signData.setExecuteId(executeId);
		signData.setActInstId(actInstId);
		signData.setNodeId(nodeId);
		signData.setTaskId(taskId);
		signData.setQualifiedId(qualifiedId);
		signData.setQualifiedName(qualifiedName);
		signData.setCreateTime(new Date());
		signData.setVoteResult(VoteResult.NO.getKey());
		signData.setIndex(index);
		signData.setIsActive(1);
		signData.setType(signType);
		bpmSignDataDao.create(signData);
	}
	

	@Override
	public void delByInstList(List<String> instList) {
		bpmSignDataDao.delByInstList(instList);
	}

	

	@Override
	public void vote(String executeId, String nodeId, Short index,
			String actionName) {
		
		BpmSignData signData= bpmSignDataDao.getByExeNodeIndex(executeId, nodeId, index);
		IUser user=ContextUtil.getCurrentUser();
		String userId=BpmConstants.SYSTEM_USER_ID;
		String userName=BpmConstants.SYSTEM_USER_NAME;
		if(user!=null){
			userId=user.getUserId();
			userName=user.getFullname();
		}
		signData.setVoteResult(actionName);
		signData.setVoteId(userId);
		signData.setVoter(userName);
		signData.setVoteTime(new Date());
		//更新会签数据。
		bpmSignDataDao.update(signData);
		
	}

	@Override
	public void updByNotActive(String actExecuteId, String nodeId) {
		bpmSignDataDao.updByNotActive(actExecuteId, nodeId);
		
	}

	@Override
	public void removeByNotActive(String actExecuteId, String nodeId) {
		bpmSignDataDao.removeByNotActive(actExecuteId, nodeId);
	}

	@Override
	public BpmSignData getByInstanIdAndUserId(String instancId, String userId) {
		return bpmSignDataDao.getByInstanIdAndUserId(instancId, userId);
	}
	
}
