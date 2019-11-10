package com.hotent.bpmx.persistence.util;

import java.util.ArrayList;
import java.util.List;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.persistence.model.ActTask;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.sys.util.ContextUtil;

public class BpmCheckOpinionUtil {
	
	
	public static DefaultBpmCheckOpinion buildBpmCheckOpinion(BpmDelegateTask delegateTask,String procInstId){		
		DefaultBpmCheckOpinion bpmCheckOpinion = new DefaultBpmCheckOpinion();
		bpmCheckOpinion.setProcDefId(delegateTask.getBpmnDefId());
		String superInstId= (String) delegateTask.getSupperVariable(BpmConstants.PROCESS_INST_ID);
		if(StringUtil.isEmpty(superInstId)){
			superInstId="0";
		}
		bpmCheckOpinion.setSupInstId(superInstId);
		bpmCheckOpinion.setProcInstId(procInstId);
		bpmCheckOpinion.setTaskId(delegateTask.getId());
		bpmCheckOpinion.setTaskKey(delegateTask.getTaskDefinitionKey());
		bpmCheckOpinion.setTaskName(delegateTask.getName());
		bpmCheckOpinion.setStatus(OpinionStatus.AWAITING_CHECK.getKey());		
		bpmCheckOpinion.setCreateTime(delegateTask.getCreateTime());
		

		return bpmCheckOpinion;
	}
	/**
	 * 获取有资格审批人Json数据。
	 * 
	 * <pre>
	 * 	数据格式如下:
	 *  [{id:"",type:""},{id:"",type:""}]
	 * </pre>
	 * 
	 * @param identityList
	 * @return String
	 */
	public   static String getIdentityIds(List<BpmIdentity> identityList)
	{
		if (BeanUtils.isEmpty(identityList))
			return "";

		StringBuffer ids = new StringBuffer();
		ids.append("[");
		for (int i = 0; i < identityList.size(); i++)
		{
			BpmIdentity identity = identityList.get(i);
			if (i > 0)
			{
				ids.append(",");
			}
			;
			ids.append("{id:\"" + identity.getId() + "\",type:\"" + identity.getType() + "\",name:\"" + identity.getName() + "\"}");
		}
		ids.append("]");
		String result = ids.toString();
		return result;

	}

	/**
	 * 获取有资格审批人的名字,以逗号隔开。
	 * 
	 * @param identityList
	 * @return String
	 */
	public static String getIdentityNames(List<BpmIdentity> identityList)
	{
		if (BeanUtils.isEmpty(identityList))
			return "";

		StringBuffer names = new StringBuffer();
		for (BpmIdentity bpmIdentity : identityList)
		{
			names.append(bpmIdentity.getName());
			names.append(",");
		}
		String result = names.toString();
		return result.substring(0, result.length() - 1);

	}
	
	public static DefaultBpmCheckOpinion buildBpmCheckOpinion(ActTask actTask,String superInstId, String procInstId){	
		IUser user=ContextUtil.getCurrentUser();
		String id=UniqueIdUtil.getSuid();
		
		List<BpmIdentity> identityList =new ArrayList<BpmIdentity>();
		BpmIdentity bpmIdentity=new DefaultBpmIdentity();
		bpmIdentity.setType(IdentityType.USER);
		bpmIdentity.setId(user.getUserId());
		bpmIdentity.setName(user.getFullname());
		identityList.add(bpmIdentity);
		
		DefaultBpmCheckOpinion bpmCheckOpinion = new DefaultBpmCheckOpinion();
		bpmCheckOpinion.setId(id);
		bpmCheckOpinion.setProcDefId(actTask.getProcDefId());
		bpmCheckOpinion.setSupInstId(superInstId);
		bpmCheckOpinion.setProcInstId(procInstId);
		bpmCheckOpinion.setTaskId(actTask.getId());
		bpmCheckOpinion.setTaskKey(actTask.getTaskDefKey());
		bpmCheckOpinion.setTaskName(actTask.getName());
		bpmCheckOpinion.setStatus(OpinionStatus.AWAITING_CHECK.getKey());		
		bpmCheckOpinion.setCreateTime(actTask.getCreateTime());
		bpmCheckOpinion.setQualfieds(getIdentityIds(identityList));
		bpmCheckOpinion.setQualfiedNames(user.getFullname());
		
		return bpmCheckOpinion;
	}
	
	
	
	/**
	 * 获取发起节点或结束结点的审核意见
	 * @param delegateExecution
	 * @param procInstId
	 * @return
	 */
	public static DefaultBpmCheckOpinion buildBpmCheckOpinion(BpmDelegateExecution delegateExecution,String procInstId,Boolean isEndNote){	
		IUser user=ContextUtil.getCurrentUser();
		List<BpmIdentity> identityList =new ArrayList<BpmIdentity>();
		BpmIdentity bpmIdentity=new DefaultBpmIdentity();
		bpmIdentity.setType(IdentityType.USER);
		bpmIdentity.setId(user.getUserId());
		bpmIdentity.setName(user.getFullname());
		identityList.add(bpmIdentity);
		
		DefaultBpmCheckOpinion bpmCheckOpinion = new DefaultBpmCheckOpinion();
		bpmCheckOpinion.setProcDefId(delegateExecution.getBpmnDefId());
		String superInstId=(String) delegateExecution.getSupperVariable(BpmConstants.PROCESS_INST_ID);
		bpmCheckOpinion.setSupInstId(superInstId);
		bpmCheckOpinion.setProcInstId(procInstId);
		bpmCheckOpinion.setTaskId(null);
		bpmCheckOpinion.setTaskKey(delegateExecution.getNodeId());
		bpmCheckOpinion.setTaskName("发起节点");	
		if(isEndNote)
		{
			//对于从一个开始节点出发，可能会走向不同的结束节点，故需要记录具体的结束节点
			bpmCheckOpinion.setTaskKey(delegateExecution.getNodeId());
			bpmCheckOpinion.setTaskName(delegateExecution.getNodeName());	
			bpmCheckOpinion.setStatus("end");
			bpmCheckOpinion.setOpinion("结束流程");
		}
		bpmCheckOpinion.setCreateTime(new java.util.Date());
		bpmCheckOpinion.setCompleteTime(bpmCheckOpinion.getCreateTime());
		
		bpmCheckOpinion.setQualfieds(getIdentityIds(identityList));
		bpmCheckOpinion.setQualfiedNames(user.getFullname());
		bpmCheckOpinion.setAuditor(user.getUserId());
		bpmCheckOpinion.setAuditorName(user.getFullname());
		
		
		bpmCheckOpinion.setDurMs(0L);
		return bpmCheckOpinion;
	}
}
