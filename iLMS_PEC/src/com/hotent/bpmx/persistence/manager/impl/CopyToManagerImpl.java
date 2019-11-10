package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.core.util.MessageUtil;
import com.hotent.bpmx.persistence.dao.BpmCptoReceiverDao;
import com.hotent.bpmx.persistence.dao.CopyToDao;
import com.hotent.bpmx.persistence.manager.BpmCptoReceiverManager;
import com.hotent.bpmx.persistence.manager.CopyToManager;
import com.hotent.bpmx.persistence.model.BpmCptoReceiver;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

@Service("copyToManager")
public class CopyToManagerImpl extends AbstractManagerImpl<String, CopyTo> implements CopyToManager{
	@Resource
	CopyToDao copyToDao;
	@Resource
	BpmCptoReceiverDao bpmCptoReceiverDao;
	@Resource
	BpmProcessInstanceManagerImpl bpmProcessInstanceManager;
	@Resource
	BpmCptoReceiverManager bpmCptoReceiverManager;
	@Resource
	IUserService userServiceImpl;
	
	@Override
	protected Dao<String, CopyTo> getDao() {
		return copyToDao;
	}
	
	
	@Override
	public void delByInstList(List<String> instList) {
		copyToDao.delByInstList(instList);
	}
	
	
	@Override
	public void create(CopyTo copyTo) {
		copyToDao.create(copyTo);
		//添加接收人
		List<BpmCptoReceiver> receivers=copyTo.getReceivers();
		for(BpmCptoReceiver receiver:receivers){
			bpmCptoReceiverDao.create(receiver);
		}
	}


	@Override
	public PageList<CopyTo> getReceiverCopyTo(String userId,
			QueryFilter queryFilter) {
		PageList<CopyTo> pageList=copyToDao.getReceiverCopyTo(userId, queryFilter);
		return pageList;
	}


	@Override
	public PageList<CopyTo> getMyCopyTo(String userId, QueryFilter filter) {
		PageList<CopyTo> pageList=copyToDao.getMyCopyTo(userId, filter);
		return pageList;
	}


	private void trans(String instanceId, String userId, String messageType,String opinion,String copyToType) throws Exception{
		BpmProcessInstance bpmProcessInstance = bpmProcessInstanceManager.get(instanceId);
		IUser user = userServiceImpl.getUserById(userId);
		IUser currentUser = userServiceImpl.getUserById(ContextUtil.getCurrentUserId());
		//创建一条CopyTo
		CopyTo copyTo = new CopyTo();
		if(copyToType.equals("0")){
			copyTo.setType("copyto");
		}else{
			copyTo.setType("trans");
		}
		copyTo.setNodeId(ContextThreadUtil.getCommuVar("nodeId", "").toString());
		copyTo.setOpinion(opinion);
		copyTo.setId(UniqueIdUtil.getUId().toString());
		copyTo.setBpmnInstId(bpmProcessInstance.getBpmnInstId());
		copyTo.setInstId(bpmProcessInstance.getId());
		copyTo.setSubject(bpmProcessInstance.getSubject());
		copyTo.setStartor(currentUser.getFullname());
		copyTo.setStartorId(currentUser.getUserId());
		copyTo.setTypeId(bpmProcessInstance.getTypeId());
		copyToDao.create(copyTo);
		//创建接收人对象
		BpmCptoReceiver bpmCptoReceiver = new BpmCptoReceiver();
		bpmCptoReceiver.setId(UniqueIdUtil.getUId().toString());
		bpmCptoReceiver.setReceiverId(userId);
		bpmCptoReceiver.setReceiver(user.getFullname());
		bpmCptoReceiver.setCptoId(copyTo.getId());
		bpmCptoReceiverManager.create(bpmCptoReceiver);
		//消息接收人
		List<IUser> userList = new ArrayList<IUser>();
		userList.add(user);
		//发送消息通知相关人员
		Map<String,Object> vars = getVar(bpmProcessInstance, opinion);
		MessageUtil.sendMsg(copyToType.equals("1")?TemplateConstants.TYPE_KEY.BPM_HAND_TO:TemplateConstants.TYPE_KEY.COPY_TO, messageType, userList, vars);
	}
	
	@Override
	public void transToMore(String instanceId, List<String> userIds, String messageType,String opinion,String copyToType) throws Exception{
		for(String userId:userIds){
			trans(instanceId, userId, messageType, opinion,copyToType);
		}
	}
	
	private Map<String,Object> getVar(BpmProcessInstance bpmProcessInstance,String opinion){
		String baseUrl = SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
		IUser user = ContextUtil.getCurrentUser();
		Map<String, Object> map = new HashMap<String, Object>();
		// 转发人
		map.put(TemplateConstants.TEMP_VAR.DELEGATE, user.getFullname());
		// 任务标题
		map.put(TemplateConstants.TEMP_VAR.TASK_SUBJECT, bpmProcessInstance.getSubject());
		map.put(TemplateConstants.TEMP_VAR.BASE_URL, baseUrl);
		map.put(TemplateConstants.TEMP_VAR.TASK_ID, bpmProcessInstance.getId());
		// 意见
		map.put(TemplateConstants.TEMP_VAR.CAUSE, opinion);

		return map;
	}



}
