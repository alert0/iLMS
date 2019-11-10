package com.hotent.bpmx.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:任务指派人员 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:miao
 * 创建时间:2014-10-11 15:27:06
 */
public class TaskTurnAssign extends AbstractModel<String>{
	protected String id; /*主键*/
	protected String taskTurnId; /*转交ID和task_turn表主键关联*/
	protected String fromUserId; /*任务转交人ID*/
	protected String fromUser; /*任务转交人*/
	protected String receiverId; /*接收人ID*/
	protected String receiver; /*接收人*/
	protected java.util.Date createTime; /*创建时间*/
	protected String comment; /*备注*/
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
	public void setTaskTurnId(String taskTurnId) 
	{
		this.taskTurnId = taskTurnId;
	}
	/**
	 * 返回 转交ID和task_turn表主键关联
	 * @return
	 */
	public String getTaskTurnId() 
	{
		return this.taskTurnId;
	}
	public void setFromUserId(String fromUserId) 
	{
		this.fromUserId = fromUserId;
	}
	/**
	 * 返回 任务转交人ID
	 * @return
	 */
	public String getFromUserId() 
	{
		return this.fromUserId;
	}
	public void setFromUser(String fromUser) 
	{
		this.fromUser = fromUser;
	}
	/**
	 * 返回 任务转交人
	 * @return
	 */
	public String getFromUser() 
	{
		return this.fromUser;
	}
	public void setReceiverId(String receiverId) 
	{
		this.receiverId = receiverId;
	}
	/**
	 * 返回 接收人ID
	 * @return
	 */
	public String getReceiverId() 
	{
		return this.receiverId;
	}
	public void setReceiver(String receiver) 
	{
		this.receiver = receiver;
	}
	/**
	 * 返回 接收人
	 * @return
	 */
	public String getReceiver() 
	{
		return this.receiver;
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
	public void setComment(String comment) 
	{
		this.comment = comment;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getComment() 
	{
		return this.comment;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("taskTurnId", this.taskTurnId) 
		.append("fromUserId", this.fromUserId) 
		.append("fromUser", this.fromUser) 
		.append("receiverId", this.receiverId) 
		.append("receiver", this.receiver) 
		.append("createTime", this.createTime) 
		.append("comment", this.comment) 
		.toString();
	}
}