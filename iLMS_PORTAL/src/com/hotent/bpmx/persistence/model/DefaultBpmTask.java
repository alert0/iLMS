package com.hotent.bpmx.persistence.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.SkipResult;

/**
 * <pre>
 * 对象功能:流程任务 entity对象 
 * 开发公司:广州宏天软件有限公司 
 * 开发人员:zyp 
 * 创建时间:2014-02-12 18:04:14
 * </pre>
 */
public class DefaultBpmTask extends AbstractModel<String> implements BpmTask,
		Cloneable {
	/***/
	private static final long serialVersionUID = 2240144872175803135L;

	protected String id; /* 任务ID */
	protected String name; /* 任务名称 */
	protected String subject; /* 待办事项标题 */
	protected String taskId = ""; /* 关联的任务ID */
	protected String execId; /* 关联 - 节点执行ID */
	protected String nodeId; /* 关联 - 任务节点ID */
	protected String procInstId; /* 关联 - 流程实例ID */
	protected String procDefId; /* 关联 - 流程定义ID */
	protected String instIsForbidden; /* 关联 - 流程实例是否禁止 */
	protected String procDefKey; /* 关联 - 流程定义KEY */
	protected String procDefName; /* 关联 - 流程名称 */
	protected String ownerId = "0"; /* 任务所属人ID */
	protected String assigneeId = "0"; /* 任务执行人ID */
	protected String status; /* 任务状态 */
	protected Long priority; /* 任务优先级 */
	protected java.util.Date dueTime; /* 任务到期时间 */
	protected Short suspendState; /* 是否挂起(0正常,1挂起) */
	protected String parentId = "";
	// 流程实例ID
	protected String bpmnInstId = "";

	protected String bpmnDefId = "";
	/**
	 * 类型ID
	 */
	protected String typeId = "";
	
	/**
	 * 流转日期。
	 */
	protected Date transDate;

	protected AuthorizeRight authorizeRight; // 流程分管授权权限对象

	/**
	 * 任务执行人是否为空，这个不保存到数据库。
	 */
	protected transient boolean isIdentityEmpty = false;
	// 任务候选人
	protected transient List<BpmIdentity> identityList = null;

	protected transient SkipResult skipResult = new SkipResult();
	// 任务执行人人
	protected  String assigneeName = "";
	// 任务所属人
	protected  String ownerName = "";
	/* 流程创建时间 */
	protected  transient java.util.Date createDate; 
	/* 流程创建人ID */
	protected  String creatorId; 
	/* 流程创建人名称 */
	protected  String creator; 
	/* 流程状态 */
	protected  String instStatus;
	
	/**
	 * 支持手机表单。
	 */
	protected int supportMobile=0;
	
	protected boolean isGateway_=false;
	
	/* 计算时间的方式  worktime 工作日  caltime日历日 */
	protected String dueDateType;
	
	/* 任务到期时间 */
	protected Date dueExpDate;
	
	/* 任务用时 */
	protected int dueTaskTime = 0;
	
	/* 任务已用时间 */
	protected int dueUseTaskTime = 0;
	
	/* dueStatus 是否进行过延迟申请  */
	protected int dueStatus;
	
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 任务ID
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 任务名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 返回 待办事项标题
	 * 
	 * @return
	 */
	public String getSubject() {
		return this.subject;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 返回 关联的任务ID
	 * 
	 * @return
	 */
	public String getTaskId() {
		return this.taskId;
	}

	public void setExecId(String execId) {
		this.execId = execId;
	}

	/**
	 * 返回 关联 - 节点执行ID
	 * 
	 * @return
	 */
	public String getExecId() {
		return this.execId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * 返回 关联 - 任务节点ID
	 * 
	 * @return
	 */
	public String getNodeId() {
		return this.nodeId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	/**
	 * 返回 关联 - 流程实例ID
	 * 
	 * @return
	 */
	public String getProcInstId() {
		return this.procInstId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	/**
	 * 返回 关联 - 流程定义ID
	 * 
	 * @return
	 */
	public String getProcDefId() {
		return this.procDefId;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	/**
	 * 返回  流程定义的Key
	 * 
	 * @return
	 */
	public String getProcDefKey() {
		return this.procDefKey;
	}

	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}

	/**
	 * 返回 关联 - 流程名称
	 * 
	 * @return
	 */
	public String getProcDefName() {
		return this.procDefName;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * 返回 任务所属人ID
	 * 
	 * @return
	 */
	public String getOwnerId() {
		return this.ownerId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	/**
	 * 返回 任务执行人ID
	 * 
	 * @return
	 */
	public String getAssigneeId() {
		return this.assigneeId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 返回 任务状态
	 * 
	 * @return
	 */
	public String getStatus() {
		return this.status;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	/**
	 * 返回 任务优先级
	 * 
	 * @return
	 */
	public Long getPriority() {
		return this.priority;
	}

	public void setDueTime(java.util.Date dueTime) {
		this.dueTime = dueTime;
	}

	/**
	 * 返回 任务到期时间
	 * 
	 * @return
	 */
	public java.util.Date getDueTime() {
		return this.dueTime;
	}

	public void setSuspendState(Short suspendState) {
		this.suspendState = suspendState;
	}

	/**
	 * 返回 是否挂起(0正常,1挂起)
	 * 
	 * @return
	 */
	@Override
	public Short getSuspendState() {
		return this.suspendState;
	}

	/**
	 * 返回父ID
	 */
	@Override
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getBpmnInstId() {
		return bpmnInstId;
	}

	public void setBpmnInstId(String bpmnInstId) {
		this.bpmnInstId = bpmnInstId;
	}

	public String getBpmnDefId() {
		return bpmnDefId;
	}

	public void setBpmnDefId(String bpmnDefId) {
		this.bpmnDefId = bpmnDefId;
	}

	/**
	 * 判断任务是否为流程引擎产生的任务。
	 * 
	 * @return boolean
	 */
	public boolean isBpmnTask() {
		String execId = this.execId;
		return StringUtil.isNotEmpty(execId);
	}

	
	public String getDueDateType() {
		return dueDateType;
	}

	public void setDueDateType(String dueDateType) {
		this.dueDateType = dueDateType;
	}

	public Date getDueExpDate() {
		return dueExpDate;
	}

	public void setDueExpDate(Date dueExpDate) {
		this.dueExpDate = dueExpDate;
	}

	public int getDueTaskTime() {
		return dueTaskTime;
	}

	public void setDueTaskTime(int dueTaskTime) {
		this.dueTaskTime = dueTaskTime;
	}

	public int getDueUseTaskTime() {
		return dueUseTaskTime;
	}

	public void setDueUseTaskTime(int dueUseTaskTime) {
		this.dueUseTaskTime = dueUseTaskTime;
	}

	public int getDueStatus() {
		return dueStatus;
	}

	public void setDueStatus(int dueStatus) {
		this.dueStatus = dueStatus;
	}

	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name).append("subject", this.subject)
				.append("taskId", this.taskId).append("execId", this.execId)
				.append("nodeId", this.nodeId)
				.append("procInstId", this.procInstId)
				.append("procDefId", this.procDefId)
				.append("procDefKey", this.procDefKey)
				.append("procDefName", this.procDefName)
				.append("ownerId", this.ownerId)
				.append("assigneeId", this.assigneeId)
				.append("status", this.status)
				.append("priority", this.priority)
				.append("createTime", this.createTime)
				.append("createDate", this.createDate)
				.append("creator", this.creator)
				.append("createTime", this.createTime)
				.append("dueTime", this.dueTime)
				.append("suspendState", this.suspendState).toString();
	}

	@Override
	public boolean equals(Object obj) {
		DefaultBpmTask task = (DefaultBpmTask) obj;
		if (this.id.equals(task.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public Object clone() {
		DefaultBpmTask o = null;
		try {
			o = (DefaultBpmTask) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public List<BpmIdentity> getIdentityList() {
		return this.identityList;
	}

	public void setIdentityList(List<BpmIdentity> identityList) {
		this.identityList = identityList;
	}

	@Override
	public void setSkipResult(SkipResult skipResult) {
		this.skipResult = skipResult;
	}

	@Override
	public SkipResult getSkipResult() {
		return this.skipResult;
	}

	public AuthorizeRight getAuthorizeRight() {
		return authorizeRight;
	}

	public void setAuthorizeRight(AuthorizeRight authorizeRight) {
		this.authorizeRight = authorizeRight;
	}

	public boolean isIdentityEmpty() {
		return isIdentityEmpty;
	}

	public void setIdentityEmpty(boolean isIdentityEmpty) {
		this.isIdentityEmpty = isIdentityEmpty;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getInstStatus() {
		return instStatus;
	}

	public void setInstStatus(String instStatus) {
		this.instStatus = instStatus;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public int getSupportMobile() {
		return supportMobile;
	}

	public void setSupportMobile(int supportMobile) {
		this.supportMobile = supportMobile;
	}

	@Override
	public void setIsGateWay(boolean isGateway) {
		this.isGateway_=isGateway;
	}

	@Override
	public boolean isGateWay() {
		return isGateway_;
	}

	public String getInstIsForbidden() {
		return instIsForbidden;
	}

	public void setInstIsForbidden(String instIsForbidden) {
		this.instIsForbidden = instIsForbidden;
	}
	

}