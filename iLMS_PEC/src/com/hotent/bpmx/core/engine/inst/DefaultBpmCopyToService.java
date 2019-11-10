package com.hotent.bpmx.core.engine.inst;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.constant.CopyToType;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.service.BpmCopyToService;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.CopyToManager;
import com.hotent.bpmx.persistence.model.BpmCptoReceiver;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.org.api.model.IUser;

@Service
public class DefaultBpmCopyToService implements BpmCopyToService{

	@Resource
	BpmInstService bpmInstService;
	
	@Resource
	CopyToManager copyToManager;
	
	@Resource
	BpmTaskManager bpmTaskManager;
	
	/**
	 * 抄送给指定的用户。
	 */
	public void copyTo(List<IUser> receiverUsers,BpmProcessInstance bpmProcessInstance ,String nodeId){
		//调用接口做抄送处理
		copyTo(bpmProcessInstance, nodeId, 
							 CopyToType.COPYTO,
							receiverUsers);
	}

	private void copyTo(BpmProcessInstance instance, String nodeId,
			CopyToType copyToType,List<IUser> receiverUsers) {
		String copyId=UniqueIdUtil.getSuid();
		
		CopyTo copyTo = new CopyTo();
		copyTo.setId(copyId);
		copyTo.setInstId(instance.getId());
		copyTo.setBpmnInstId(instance.getBpmnInstId());
		copyTo.setNodeId(nodeId);
	
		copyTo.setSubject(instance.getSubject());
		
		copyTo.setCreateTime(new java.util.Date());
		copyTo.setType(copyToType.name().toLowerCase());
		copyTo.setStartorId(instance.getCreateBy());		
		copyTo.setStartor(instance.getCreator());
		copyTo.setTypeId(instance.getTypeId());
		
		for(IUser user:receiverUsers){
			String id=UniqueIdUtil.getSuid();
			BpmCptoReceiver receiver=new BpmCptoReceiver();
			receiver.setId(id);
			receiver.setCptoId(copyId);
			receiver.setReceiverId(user.getUserId());
			receiver.setReceiver(user.getFullname());
			copyTo.addReceiver(receiver);
		}
		
		copyToManager.create(copyTo);		
	}

}
