package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:抄送接收人 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyg
 * 创建时间:2014-10-13 22:24:23
 */
public class BpmCptoReceiver extends AbstractModel<String>{
	protected String  id; /*主键*/
	protected String  cptoId; /*关联ID*/
	protected String  receiverId; /*接收人ID*/
	protected String  receiver; /*接收人*/
	protected Short  isRead=0; /*已读*/
	protected java.util.Date  readTime; /*读取时间*/
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
	public void setCptoId(String cptoId) 
	{
		this.cptoId = cptoId;
	}
	/**
	 * 返回 关联ID
	 * @return
	 */
	public String getCptoId() 
	{
		return this.cptoId;
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
	public void setIsRead(Short isRead) 
	{
		this.isRead = isRead;
	}
	/**
	 * 返回 已读
	 * @return
	 */
	public Short getIsRead() 
	{
		return this.isRead;
	}
	public void setReadTime(java.util.Date readTime) 
	{
		this.readTime = readTime;
	}
	/**
	 * 返回 读取时间
	 * @return
	 */
	public java.util.Date getReadTime() 
	{
		return this.readTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("cptoId", this.cptoId) 
		.append("receiverId", this.receiverId) 
		.append("receiver", this.receiver) 
		.append("isRead", this.isRead) 
		.append("readTime", this.readTime) 
		.toString();
	}
}