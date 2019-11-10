package com.hotent.bpmx.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:堆栈任务执行人 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyg
 * 创建时间:2015-01-18 10:49:19
 */
public class BpmExeStackExecutor extends AbstractModel<String>{
	protected String  id; /*主键*/
	protected String  stackId; /*堆栈ID*/
	protected String  taskId; /*任务ID*/
	protected String  assigneeId; /*执行人*/
	protected java.util.Date  createTime; /*创建时间*/
	protected java.util.Date  endTime; /*结束时间*/
	protected Integer  status=0; /*状态(0,初始状态,1,完成)*/
	
	
	
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setStackId(String stackId) 
	{
		this.stackId = stackId;
	}
	/**
	 * 返回 堆栈ID
	 * @return
	 */
	public String getStackId() 
	{
		return this.stackId;
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
	public void setAssigneeId(String assigneeId) 
	{
		this.assigneeId = assigneeId;
	}
	/**
	 * 返回 执行人
	 * @return
	 */
	public String getAssigneeId() 
	{
		return this.assigneeId;
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
	public void setEndTime(java.util.Date endTime) 
	{
		this.endTime = endTime;
	}
	/**
	 * 返回 结束时间
	 * @return
	 */
	public java.util.Date getEndTime() 
	{
		return this.endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("stackId", this.stackId) 
		.append("taskId", this.taskId) 
		.append("assigneeId", this.assigneeId) 
		.append("createTime", this.createTime) 
		.append("endTime", this.endTime) 
		.toString();
	}
}