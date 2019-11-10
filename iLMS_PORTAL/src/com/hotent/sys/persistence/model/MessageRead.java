package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：sys_msg_read 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class MessageRead extends AbstractModel<String>{
	protected String id; /*主键*/
	protected String msgId; /*消息ID*/
	protected String receiverId; /*消息接收人ID*/
	protected String receiver; /*消息接收人*/
	protected java.util.Date receiverTime; /*接收时间*/
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
	public void setMsgId(String msgId) 
	{
		this.msgId = msgId;
	}
	/**
	 * 返回 消息ID
	 * @return
	 */
	public String getMsgId() 
	{
		return this.msgId;
	}
	public void setReceiverId(String receiverId) 
	{
		this.receiverId = receiverId;
	}
	/**
	 * 返回 消息接收人ID
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
	 * 返回 消息接收人
	 * @return
	 */
	public String getReceiver() 
	{
		return this.receiver;
	}
	public void setReceiverTime(java.util.Date receiverTime) 
	{
		this.receiverTime = receiverTime;
	}
	/**
	 * 返回 接收时间
	 * @return
	 */
	public java.util.Date getReceiverTime() 
	{
		return this.receiverTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("msgId", this.msgId) 
		.append("receiverId", this.receiverId) 
		.append("receiver", this.receiver) 
		.append("receiverTime", this.receiverTime) 
		.toString();
	}
}