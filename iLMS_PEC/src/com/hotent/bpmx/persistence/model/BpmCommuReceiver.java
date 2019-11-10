package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:任务通知接收人 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyg
 * 创建时间:2014-08-05 17:47:38
 */
public class BpmCommuReceiver extends AbstractModel<String>{
	
	public static String COMMU_NO="no";
	public static String COMMU_RECEIVE="receive";
	public static String COMMU_FEEDBACK="feedback";
	
	protected String  id; /*主键*/
	protected String  commuId; /*通知ID*/
	protected String  receiverId; /*接收人ID*/
	protected String  receiver; /*接收人*/
	protected String  status=COMMU_NO; /*状态*/
	protected String  opinion; /*反馈意见*/
	protected java.util.Date  receiveTime; /*接收时间*/
	protected java.util.Date  feedbackTime; /*反馈时间*/
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
	public void setCommuId(String commuId) 
	{
		this.commuId = commuId;
	}
	/**
	 * 返回 通知ID
	 * @return
	 */
	public String getCommuId() 
	{
		return this.commuId;
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
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 状态
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}
	public void setOpinion(String opinion) 
	{
		this.opinion = opinion;
	}
	/**
	 * 返回 反馈意见
	 * @return
	 */
	public String getOpinion() 
	{
		return this.opinion;
	}
	public void setReceiveTime(java.util.Date receiveTime) 
	{
		this.receiveTime = receiveTime;
	}
	/**
	 * 返回 接收时间
	 * @return
	 */
	public java.util.Date getReceiveTime() 
	{
		return this.receiveTime;
	}
	public void setFeedbackTime(java.util.Date feedbackTime) 
	{
		this.feedbackTime = feedbackTime;
	}
	/**
	 * 返回 反馈时间
	 * @return
	 */
	public java.util.Date getFeedbackTime() 
	{
		return this.feedbackTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("commuId", this.commuId) 
		.append("receiverId", this.receiverId) 
		.append("receiver", this.receiver) 
		.append("status", this.status) 
		.append("opinion", this.opinion) 
		.append("receiveTime", this.receiveTime) 
		.append("feedbackTime", this.feedbackTime) 
		.toString();
	}
}