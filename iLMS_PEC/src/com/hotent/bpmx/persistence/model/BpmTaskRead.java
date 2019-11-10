package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:?????? entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyg
 * 创建时间:2014-05-14 14:41:48
 */
public class BpmTaskRead extends AbstractModel<String>{
	protected String  id; /*??*/
	protected String  procDefId; /*????ID*/
	protected String  procInstId; /*????ID*/
	protected String  taskId; /*??ID*/
	protected String  nodeId; /*????Key*/
	protected java.util.Date  readTime; /*????*/
	protected String  userId; /*???ID*/
	protected String  userName; /*????*/
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ??
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setProcDefId(String procDefId) 
	{
		this.procDefId = procDefId;
	}
	/**
	 * 返回 ????ID
	 * @return
	 */
	public String getProcDefId() 
	{
		return this.procDefId;
	}
	public void setProcInstId(String procInstId) 
	{
		this.procInstId = procInstId;
	}
	/**
	 * 返回 ????ID
	 * @return
	 */
	public String getProcInstId() 
	{
		return this.procInstId;
	}
	public void setTaskId(String taskId) 
	{
		this.taskId = taskId;
	}
	/**
	 * 返回 ??ID
	 * @return
	 */
	public String getTaskId() 
	{
		return this.taskId;
	}
	public void setNodeId(String nodeId) 
	{
		this.nodeId = nodeId;
	}
	/**
	 * 返回 ????Key
	 * @return
	 */
	public String getNodeId() 
	{
		return this.nodeId;
	}
	public void setReadTime(java.util.Date readTime) 
	{
		this.readTime = readTime;
	}
	/**
	 * 返回 ????
	 * @return
	 */
	public java.util.Date getReadTime() 
	{
		return this.readTime;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 ???ID
	 * @return
	 */
	public String getUserId() 
	{
		return this.userId;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	/**
	 * 返回 ????
	 * @return
	 */
	public String getUserName() 
	{
		return this.userName;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("procDefId", this.procDefId) 
		.append("procInstId", this.procInstId) 
		.append("taskId", this.taskId) 
		.append("nodeId", this.nodeId) 
		.append("readTime", this.readTime) 
		.append("userId", this.userId) 
		.append("userName", this.userName) 
		.toString();
	}
}