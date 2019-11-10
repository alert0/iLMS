package com.hotent.bpmx.persistence.model;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.bpmx.api.model.process.task.BpmTaskTurn;

/**
 * 对象功能:任务的执行更改 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-05-04 16:06:19
 */
public class DefaultBpmTaskTurn extends AbstractModel<String> implements BpmTaskTurn{
	protected String  id; /*更改ID*/
	protected String  taskId; /*任务ID*/
	protected String  taskName; /*任务名称*/
	protected String  taskSubject; /*待办事项标题*/
	protected String  nodeId; /*关联 - 任务节点ID*/
	protected String  procInstId; /*关联 - 流程实例ID*/
	protected String  ownerId; /*任务所属人ID*/
	protected String  ownerName; /*任务所属人姓名*/
	//protected String  execUserId; /*任务执行人ID*/
	//protected String  execUserName; /*任务执行人姓名*/
	protected String  assigneeId; /*任务承接人ID*/
	protected String  assigneeName; /*任务承接人姓名*/
	protected String  status; /*状态。running 正在运行；finish 完成；cancel 取消。*/
	protected String  turnType; /*更改类型。agent 代理；turn 转办。*/ 
	protected String  typeId; /*所属分类ID*/
	protected java.util.Date  createTime; /*创建时间*/
	protected java.util.Date  finishTime; /*任务完成时间*/
	protected int supportMobile=0;/*是否支持手机端*/
	
	protected List<TaskTurnAssign> turnAssignList;
	
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * 返回 更改ID
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setTaskId(String taskId) 
	{
		this.taskId = taskId;
	}
	/**
	 * 返回 任务ID
	 * @return
	 */
	public String getTaskId() 
	{
		return this.taskId;
	}
	public void setTaskName(String taskName) 
	{
		this.taskName = taskName;
	}
	/**
	 * 返回 任务名称
	 * @return
	 */
	public String getTaskName() 
	{
		return this.taskName;
	}
	public void setTaskSubject(String taskSubject) 
	{
		this.taskSubject = taskSubject;
	}
	/**
	 * 返回 待办事项标题
	 * @return
	 */
	public String getTaskSubject() 
	{
		return this.taskSubject;
	}
	public void setNodeId(String nodeId) 
	{
		this.nodeId = nodeId;
	}
	/**
	 * 返回 关联 - 任务节点ID
	 * @return
	 */
	public String getNodeId() 
	{
		return this.nodeId;
	}
	public void setProcInstId(String procInstId) 
	{
		this.procInstId = procInstId;
	}
	/**
	 * 返回 关联 - 流程实例ID
	 * @return
	 */
	public String getProcInstId() 
	{
		return this.procInstId;
	}
	public void setOwnerId(String ownerId) 
	{
		this.ownerId = ownerId;
	}
	/**
	 * 返回 任务所属人ID
	 * @return
	 */
	public String getOwnerId() 
	{
		return this.ownerId;
	}
	public void setOwnerName(String ownerName) 
	{
		this.ownerName = ownerName;
	}
	/**
	 * 返回 任务所属人姓名
	 * @return
	 */
	public String getOwnerName() 
	{
		return this.ownerName;
	}
	
	
	public void setAssigneeId(String assigneeId) 
	{
		this.assigneeId = assigneeId;
	}
	/**
	 * 返回 任务承接人ID
	 * @return
	 */
	public String getAssigneeId() 
	{
		return this.assigneeId;
	}
	public void setAssigneeName(String assigneeName) 
	{
		this.assigneeName = assigneeName;
	}
	/**
	 * 返回 任务承接人姓名
	 * @return
	 */
	public String getAssigneeName() 
	{
		return this.assigneeName;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 状态。running 正在运行；finish 完成；cancel 取消。
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}
	public void setTurnType(String turnType) 
	{
		this.turnType = turnType;
	}
	/**
	 * 返回 更改类型。agent 代理；turn 转办。
	 * @return
	 */
	public String getTurnType() 
	{
		return this.turnType;
	}
	public int getSupportMobile() {
		return supportMobile;
	}
	public void setSupportMobile(int supportMobile) {
		this.supportMobile = supportMobile;
	}
	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}
	/**
	 * 返回 所属分类ID
	 * @return
	 */
	public String getTypeId() 
	{
		return this.typeId;
	}
	public void setCreateTime(java.util.Date createTime) 
	{
		this.createTime = createTime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setFinishTime(java.util.Date finishTime) 
	{
		this.finishTime = finishTime;
	}
	/**
	 * 返回 任务完成时间
	 * @return
	 */
	public java.util.Date getFinishTime() 
	{
		return this.finishTime;
	}
	public List<TaskTurnAssign> getTurnAssignList() {
		return turnAssignList;
	}

	public void setTurnAssignList(List<TaskTurnAssign> turnAssignList) {
		this.turnAssignList = turnAssignList;
	}
		

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("taskId", this.taskId) 
		.append("taskName", this.taskName) 
		.append("taskSubject", this.taskSubject) 
		.append("nodeId", this.nodeId) 
		.append("procInstId", this.procInstId) 
		.append("ownerId", this.ownerId) 
		.append("ownerName", this.ownerName) 
		.append("assigneeId", this.assigneeId) 
		.append("assigneeName", this.assigneeName) 
		.append("status", this.status) 
		.append("trunType", this.turnType) 
		.append("typeId", this.typeId) 
		.append("createTime", this.createTime) 
		.append("finishTime", this.finishTime) 
		.toString();
	}
}