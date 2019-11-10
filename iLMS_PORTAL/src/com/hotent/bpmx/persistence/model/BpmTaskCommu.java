package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:任务通知 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyg
 * 创建时间:2014-08-05 17:47:38
 */
public class BpmTaskCommu extends AbstractModel<String>{
	protected String  id; /*主键*/
	protected String  instanceId; /*流程实例ID*/
	protected String  nodeName; /*节点名称*/
	protected String  nodeId; /*节点ID*/
	protected String  taskId; /*任务ID*/
	protected String  senderId; /*发送沟通人ID*/
	protected String  sender; /*发送沟通人*/
	protected java.util.Date  createtime; /*创建时间*/
	protected String  opinion; /*意见*/
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
	public void setInstanceId(String instanceId) 
	{
		this.instanceId = instanceId;
	}
	/**
	 * 返回 流程实例ID
	 * @return
	 */
	public String getInstanceId() 
	{
		return this.instanceId;
	}
	public void setNodeName(String nodeName) 
	{
		this.nodeName = nodeName;
	}
	/**
	 * 返回 节点名称
	 * @return
	 */
	public String getNodeName() 
	{
		return this.nodeName;
	}
	public void setNodeId(String nodeId) 
	{
		this.nodeId = nodeId;
	}
	/**
	 * 返回 节点ID
	 * @return
	 */
	public String getNodeId() 
	{
		return this.nodeId;
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
	public void setSenderId(String senderId) 
	{
		this.senderId = senderId;
	}
	/**
	 * 返回 发送沟通人ID
	 * @return
	 */
	public String getSenderId() 
	{
		return this.senderId;
	}
	public void setSender(String sender) 
	{
		this.sender = sender;
	}
	/**
	 * 返回 发送沟通人
	 * @return
	 */
	public String getSender() 
	{
		return this.sender;
	}
	public void setCreatetime(java.util.Date createtime) 
	{
		this.createtime = createtime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreatetime() 
	{
		return this.createtime;
	}
	public void setOpinion(String opinion) 
	{
		this.opinion = opinion;
	}
	/**
	 * 返回 意见
	 * @return
	 */
	public String getOpinion() 
	{
		return this.opinion;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("instanceId", this.instanceId) 
		.append("nodeName", this.nodeName) 
		.append("nodeId", this.nodeId) 
		.append("taskId", this.taskId) 
		.append("senderId", this.senderId) 
		.append("sender", this.sender) 
		.append("createtime", this.createtime) 
		.append("opinion", this.opinion) 
		.toString();
	}
}