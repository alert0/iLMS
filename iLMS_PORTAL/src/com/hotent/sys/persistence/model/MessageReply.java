package com.hotent.sys.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：sys_msg_reply 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 15:32:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class MessageReply extends AbstractModel<String>{
	protected String id; /*主键*/
	protected String msgId; /*消息ID*/
	protected String content; /*内容*/
	protected String replyId; /*回复人ID*/
	protected String reply; /*回复人*/
	protected java.util.Date replyTime; /*回复时间*/
	protected Short isPrivate; /*私信*/
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
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 内容
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setReplyId(String replyId) 
	{
		this.replyId = replyId;
	}
	/**
	 * 返回 回复人ID
	 * @return
	 */
	public String getReplyId() 
	{
		return this.replyId;
	}
	public void setReply(String reply) 
	{
		this.reply = reply;
	}
	/**
	 * 返回 回复人
	 * @return
	 */
	public String getReply() 
	{
		return this.reply;
	}
	public void setReplyTime(java.util.Date replyTime) 
	{
		this.replyTime = replyTime;
	}
	/**
	 * 返回 回复时间
	 * @return
	 */
	public java.util.Date getReplyTime() 
	{
		return this.replyTime;
	}
	public void setIsPrivate(Short isPrivate) 
	{
		this.isPrivate = isPrivate;
	}
	/**
	 * 返回 私信
	 * @return
	 */
	public Short getIsPrivate() 
	{
		return this.isPrivate;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("msgId", this.msgId) 
		.append("content", this.content) 
		.append("replyId", this.replyId) 
		.append("reply", this.reply) 
		.append("replyTime", this.replyTime) 
		.append("isPrivate", this.isPrivate) 
		.toString();
	}
}