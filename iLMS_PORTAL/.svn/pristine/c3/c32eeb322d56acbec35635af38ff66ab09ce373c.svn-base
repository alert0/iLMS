package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.persistence.dao.BpmTaskCandidateDao;
import com.hotent.bpmx.persistence.dao.BpmTaskDao;
import com.hotent.bpmx.persistence.manager.BpmTaskCandidateManager;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskCandidate;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;

@Service("bpmTaskCandidateManager")
public class BpmTaskCandidateManagerImpl extends AbstractManagerImpl<String, DefaultBpmTaskCandidate> implements BpmTaskCandidateManager{
	@Resource
	BpmTaskCandidateDao bpmTaskCandidateDao;
	
	
	@Resource
	IUserService userServiceImpl;
	
	@Resource
	BpmTaskDao bpmTaskDao;
	
	@Override
	protected Dao<String, DefaultBpmTaskCandidate> getDao() {
		return bpmTaskCandidateDao;
	}
	
	
	@Override
	public void addCandidate(BpmTask task, List<BpmIdentity> list) {
		String taskId=task.getId();
		String instId=task.getProcInstId();
		
		for(BpmIdentity identity:list){
			String type=identity.getType();
			//用户
			if(BpmIdentity.TYPE_USER.equals(type)){
				DefaultBpmTaskCandidate candidate=new DefaultBpmTaskCandidate(taskId,BpmIdentity.TYPE_USER,identity.getId(),instId);
				create(candidate);
			}
			//用户组合。
			else if(BpmIdentity.TYPE_GROUP_USER.equals(type)){
				String[] aryId=identity.getId().split(",");
				for(String userId:aryId){
					DefaultBpmTaskCandidate candidate=new DefaultBpmTaskCandidate(taskId,BpmIdentity.TYPE_USER,userId,instId);
					create(candidate);
				}
			}
			//组织
			else if(BpmIdentity.TYPE_GROUP.equals(type)){
				//抽取用户。
				if(ExtractType.EXACT_EXACT_USER.equals(identity.getExtractType())){
					List<IUser> userList= userServiceImpl.getUserListByGroup(identity.getGroupType(), identity.getId());
					for(IUser user:userList){
						DefaultBpmTaskCandidate candidate=new DefaultBpmTaskCandidate(taskId,BpmIdentity.TYPE_USER,user.getUserId(),instId);
						create(candidate);
					}
				}//延迟抽取
				else{
					DefaultBpmTaskCandidate candidate=new DefaultBpmTaskCandidate(taskId,identity.getGroupType(),identity.getId(),instId);
					create(candidate);
				}
			}
		}
	}
	@Override
	public void removeByTaskId(String taskId) {
		bpmTaskCandidateDao.removeByTaskId(taskId);
		
	}

	@Override
	public List<DefaultBpmTaskCandidate> queryByTaskId(String taskId) {
		return bpmTaskCandidateDao.queryByTaskId(taskId);
	}
	
	@Override
	public DefaultBpmTaskCandidate getByTaskIdExeIdType(String taskId,
			String executorId, String type) {
		return bpmTaskCandidateDao.getByTaskIdExeIdType(taskId, executorId, type);
	}

	@Override
	public void delByInstList(List<String> instList) {
		bpmTaskCandidateDao.delByInstList(instList);
	}

	@Override
	public List<DefaultBpmTaskCandidate> getByInstList(List<String> instList) {
		return bpmTaskCandidateDao.getByInstList(instList);
	}


	@Override
	public void addCandidate(String taskId, List<BpmIdentity> list) {
		BpmTask bpmTask=bpmTaskDao.get(taskId);
		addCandidate(bpmTask, list);
	}
	
}
