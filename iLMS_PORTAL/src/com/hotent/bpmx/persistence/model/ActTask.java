package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:act_ru_task entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-04-03 21:34:17
 */
public class ActTask extends AbstractModel<String> implements Cloneable{
	protected String  id; /*ID_*/
	protected Integer  rev; /*REV_*/
	protected String  executionId; /*EXECUTION_ID_*/
	protected String  procInstId; /*PROC_INST_ID_*/
	protected String  procDefId; /*PROC_DEF_ID_*/
	protected String  name; /*NAME_*/
	protected String  parentTaskId; /*PARENT_TASK_ID_*/
	protected String  description; /*DESCRIPTION_*/
	protected String  taskDefKey; /*TASK_DEF_KEY_*/
	protected String  owner; /*OWNER_*/
	protected String  assignee; /*ASSIGNEE_*/
	protected String  delegation; /*DELEGATION_*/
	protected Integer  priority; /*PRIORITY_*/
	protected Date  createTime; /*CREATE_TIME_*/
	protected java.util.Date  dueDate; /*DUE_DATE_*/
	protected Integer  suspensionState; /*SUSPENSION_STATE_*/
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ID_
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setRev(Integer rev) 
	{
		this.rev = rev;
	}
	/**
	 * 返回 REV_
	 * @return
	 */
	public Integer getRev() 
	{
		return this.rev;
	}
	public void setExecutionId(String executionId) 
	{
		this.executionId = executionId;
	}
	/**
	 * 返回 EXECUTION_ID_
	 * @return
	 */
	public String getExecutionId() 
	{
		return this.executionId;
	}
	public void setProcInstId(String procInstId) 
	{
		this.procInstId = procInstId;
	}
	/**
	 * 返回 PROC_INST_ID_
	 * @return
	 */
	public String getProcInstId() 
	{
		return this.procInstId;
	}
	public void setProcDefId(String procDefId) 
	{
		this.procDefId = procDefId;
	}
	/**
	 * 返回 PROC_DEF_ID_
	 * @return
	 */
	public String getProcDefId() 
	{
		return this.procDefId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 NAME_
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setParentTaskId(String parentTaskId) 
	{
		this.parentTaskId = parentTaskId;
	}
	/**
	 * 返回 PARENT_TASK_ID_
	 * @return
	 */
	public String getParentTaskId() 
	{
		return this.parentTaskId;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	/**
	 * 返回 DESCRIPTION_
	 * @return
	 */
	public String getDescription() 
	{
		return this.description;
	}
	public void setTaskDefKey(String taskDefKey) 
	{
		this.taskDefKey = taskDefKey;
	}
	/**
	 * 返回 TASK_DEF_KEY_
	 * @return
	 */
	public String getTaskDefKey() 
	{
		return this.taskDefKey;
	}
	public void setOwner(String owner) 
	{
		this.owner = owner;
	}
	/**
	 * 返回 OWNER_
	 * @return
	 */
	public String getOwner() 
	{
		return this.owner;
	}
	public void setAssignee(String assignee) 
	{
		this.assignee = assignee;
	}
	/**
	 * 返回 ASSIGNEE_
	 * @return
	 */
	public String getAssignee() 
	{
		return this.assignee;
	}
	public void setDelegation(String delegation) 
	{
		this.delegation = delegation;
	}
	/**
	 * 返回 DELEGATION_
	 * @return
	 */
	public String getDelegation() 
	{
		return this.delegation;
	}
	public void setPriority(Integer priority) 
	{
		this.priority = priority;
	}
	/**
	 * 返回 PRIORITY_
	 * @return
	 */
	public Integer getPriority() 
	{
		return this.priority;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}
	/**
	 * 返回 CREATE_TIME_
	 * @return
	 */
	public Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setDueDate(java.util.Date dueDate) 
	{
		this.dueDate = dueDate;
	}
	/**
	 * 返回 DUE_DATE_
	 * @return
	 */
	public java.util.Date getDueDate() 
	{
		return this.dueDate;
	}
	public void setSuspensionState(Integer suspensionState) 
	{
		this.suspensionState = suspensionState;
	}
	/**
	 * 返回 SUSPENSION_STATE_
	 * @return
	 */
	public Integer getSuspensionState() 
	{
		return this.suspensionState;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("rev", this.rev) 
		.append("executionId", this.executionId) 
		.append("procInstId", this.procInstId) 
		.append("procDefId", this.procDefId) 
		.append("name", this.name) 
		.append("parentTaskId", this.parentTaskId) 
		.append("description", this.description) 
		.append("taskDefKey", this.taskDefKey) 
		.append("owner", this.owner) 
		.append("assignee", this.assignee) 
		.append("delegation", this.delegation) 
		.append("priority", this.priority) 
		.append("createTime", this.createTime) 
		.append("dueDate", this.dueDate) 
		.append("suspensionState", this.suspensionState) 
		.toString();
	}
	
	@Override
	public Object clone(){
		 ActTask o = null;  
        try {  
            o = (ActTask) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
	    return o;  
	}
	
}