package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：sys_msg_receiver 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class MessageReceiver extends AbstractModel<String>{
	
	public static String TYPE_USER="user";
	public static String TYPE_GROUP="group";
	
	
	protected String id; /*主键*/
	protected String msgId; /*消息ID*/
	protected String receiverType; /*接收者类型*/
	protected String receiverId; /*接收者ID*/
	protected String receiver; /*接收者*/
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
	public void setReceiverType(String receiverType) 
	{
		this.receiverType = receiverType;
	}
	/**
	 * 返回 接收者类型
	 * @return
	 */
	public String getReceiverType() 
	{
		return this.receiverType;
	}
	public void setReceiverId(String receiverId) 
	{
		this.receiverId = receiverId;
	}
	/**
	 * 返回 接收者ID
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
	 * 返回 接收者
	 * @return
	 */
	public String getReceiver() 
	{
		return this.receiver;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("msgId", this.msgId) 
		.append("receiverType", this.receiverType) 
		.append("receiverId", this.receiverId) 
		.append("receiver", this.receiver) 
		.toString();
	}
}