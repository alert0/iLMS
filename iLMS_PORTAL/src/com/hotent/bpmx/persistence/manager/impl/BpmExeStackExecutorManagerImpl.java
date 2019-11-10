package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.persistence.dao.BpmExeStackExecutorDao;
import com.hotent.bpmx.persistence.manager.BpmExeStackExecutorManager;
import com.hotent.bpmx.persistence.model.BpmExeStackExecutor;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
 

@Service("bpmExeStackExecutorManager")
public class BpmExeStackExecutorManagerImpl extends AbstractManagerImpl<String, BpmExeStackExecutor> implements BpmExeStackExecutorManager{
	@Resource
	BpmExeStackExecutorDao bpmExeStackExecutorDao;
 
	@Resource
	private IUserService userServiceImpl;
	@Override
	protected Dao<String, BpmExeStackExecutor> getDao() {
		return bpmExeStackExecutorDao;
	}
	
	
	@Override
	public BpmExeStackExecutor getByTaskId(String taskId) {
		return bpmExeStackExecutorDao.getByTaskId(taskId);
	}


	@Override
	public List<BpmExeStackExecutor> getByStackId(String exeStackId) {
		return bpmExeStackExecutorDao.getByStackId(exeStackId);
	}
	@Override
	public void deleteByStackId(String stackId){
		bpmExeStackExecutorDao.deleteByStackId(stackId);
	}
	
	@Override
	public void deleteByStackPath(String stackPath){
		bpmExeStackExecutorDao.deleteByStackPath(stackPath);
	}


	@Override
	public List<BpmIdentity> getBpmIdentitysByStackId(String exeStackId) {
		List<BpmExeStackExecutor> executors = getByStackId(exeStackId);
		
		if(BeanUtils.isNotEmpty(executors)){
			List<BpmIdentity> identitys = new ArrayList<BpmIdentity>();
			for(BpmExeStackExecutor executor : executors){
				if(StringUtil.isZeroEmpty(executor.getAssigneeId())) continue;
				String assigneeId = executor.getAssigneeId();
				String assigneeName = ""; 
				IUser user = userServiceImpl.getUserById(assigneeId);
				if(BeanUtils.isNotEmpty(user)){
					assigneeName = user.getFullname();
				}
				DefaultBpmIdentity bpmIdentity = new DefaultBpmIdentity(assigneeId, assigneeName, BpmIdentity.TYPE_USER);
				identitys.add(bpmIdentity);
			}
			return identitys;
			
		}
		return Collections.emptyList();
	}
	
	
}
