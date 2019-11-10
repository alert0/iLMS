package com.hotent.base.core.mail.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <pre> 
 * 描述：邮件实体类
 * 构建组：x5-base-core
 * 作者：gjh
 * 邮箱:guojh@jee-soft.cn
 * 日期:2014-10-30-下午3:23:00
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class Mail implements Serializable{
	
	private static final long serialVersionUID = 4311266253309771066L;
	//已读
	public static Short Mail_IsRead=1;
	//未读
	public static Short Mail_IsNotRead=0;
	//已回复
	public static Short Mail_IsReplay=1;
	//未回复
	public static Short Mail_IsNotReplay=0;
	/**
	 * 收件箱
	 */
	public static Short Mail_InBox=1;
	/**
	 * 发件箱
	 */
	public static Short Mail_OutBox=2;
	/**
	 * 草稿箱
	 */
	public static Short Mail_DraftBox=3;
	/**
	 * 垃圾箱
	 */
	public static Short Mail_DumpBox=4;
	/**
	* 自增列
	*/
	protected String id; 
	/**
	 * 邮件主题
	 */
	protected String subject;
	/**
	 * 邮件内容
	 */
	protected String content;
	/**
	 * 发件人地址
	 */
	protected String senderAddress;
	/**
	 * 发件人地址别名
	 */
	protected String senderName ;
	/**
	 * 收件人地址
	 */
	protected String receiverAddresses;
	/**
	 * 收件人地址别名
	 */
	protected String receiverName ;
	/**
	 * 抄送人地址
	 */
	protected String copyToAddresses;
	/**
	 * 抄送人别名
	 */
	protected String copyToName ;
	/**
	 * 暗送人显示名
	 */
	protected String bccName ;
	/**
	 * 暗送人地址
	 */
	protected String bcCAddresses;
	/**
	 * 每种邮箱中，邮件的唯一ID
	 */
	protected String messageId ;
	
	/**
	* 邮件类型 1:收件箱;2:发件箱;3:草稿箱;4:垃圾箱
	*/
	protected Short type; 
	
	/**
	* 用户ID
	*/
	protected String userId; 
	
	/**
	* 是否回复
	*/
	protected Short isReply; 
	/**
	 * 发送时间
	 */
	protected Date sendDate ;
	
	/**
	* 附件ID
	*/
	protected String fileIds; 
	
	/**
	* 是否已读
	*/
	protected Short isRead; 
	
	/**
	* SET_ID_
	*/
	protected String setId; 
	
	
	/**
	 * 邮件附件
	 */
	protected List<MailAttachment> attachments = new ArrayList<MailAttachment>();
	
	
	// 首页工具使用
	protected String detailUrl;
	
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBcCAddresses() {
		return bcCAddresses;
	}
	public void setBcCAddresses(String bcCAddresses) {
		this.bcCAddresses = bcCAddresses;
	}
	public List<MailAttachment> getMailAttachments() {
		return attachments;
	}
	public void setMailAttachments(List<MailAttachment> attachments) {
		this.attachments = attachments;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getCopyToName() {
		return copyToName;
	}
	public void setCopyToName(String copyToName) {
		this.copyToName = copyToName;
	}
	public String getBccName() {
		return bccName;
	}
	public void setBccName(String bccName) {
		this.bccName = bccName;
	}
	public String getCopyToAddresses() {
		return copyToAddresses;
	}
	public void setCopyToAddresses(String copyToAddresses) {
		this.copyToAddresses = copyToAddresses;
	}
	public String getReceiverAddresses() {
		return receiverAddresses;
	}
	public void setReceiverAddresses(String receiverAddresses) {
		this.receiverAddresses = receiverAddresses;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Short getIsReply() {
		return isReply;
	}
	public void setIsReply(Short isReply) {
		this.isReply = isReply;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public Short getIsRead() {
		return isRead;
	}
	public void setIsRead(Short isRead) {
		this.isRead = isRead;
	}
	public String getSetId() {
		return setId;
	}
	public void setSetId(String setId) {
		this.setId = setId;
	}
	
	
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.subject) 
		.append(this.content) 
		.append(this.senderAddress) 
		.append(this.senderName) 
		.append(this.receiverAddresses) 
		.append(this.receiverName) 
		.append(this.copyToAddresses) 
		.append(this.copyToName) 
		.append(this.bcCAddresses) 
		.append(this.bccName) 
		.append(this.sendDate) 
		.append(this.fileIds) 
		.append(this.isRead) 
		.append(this.isReply) 
		.append(this.messageId) 
		.append(this.type) 
		.append(this.userId)
		.toHashCode();
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("subject", this.subject) 
		.append("content", this.content) 
		.append("senderAddress", this.senderAddress) 
		.append("senderName", this.senderName) 
		.append("receiverAddresses", this.receiverAddresses) 
		.append("receiverName", this.receiverName) 
		.append("copyToAddresses", this.copyToAddresses) 
		.append("bccName", this.bccName) 
		.append("bcCAddresses", this.bcCAddresses) 
		.append("copyToName", this.copyToName) 
		.append("messageId", this.messageId) 
		.append("type", this.type) 
		.append("userId", this.userId) 
		.append("isReply", this.isReply) 
		.append("sendDate", this.sendDate) 
		.append("fileIds", this.fileIds) 
		.append("isRead", this.isRead) 
		.append("setId", this.setId) 
		.toString();
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Mail)) 
		{
			return false;
		}
		Mail rhs = (Mail) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.subject, rhs.subject)
		.append(this.content, rhs.content)
		.append(this.senderAddress, rhs.senderAddress)
		.append(this.senderName, rhs.senderName)
		.append(this.receiverAddresses, rhs.receiverAddresses)
		.append(this.receiverName, rhs.receiverName)
		.append(this.copyToAddresses, rhs.copyToAddresses)
		.append(this.copyToName, rhs.copyToName)
		.append(this.bcCAddresses, rhs.bcCAddresses)
		.append(this.bccName, rhs.bccName)
		.append(this.sendDate, rhs.sendDate)
		.append(this.fileIds, rhs.fileIds)
		.append(this.isRead, rhs.isRead)
		.append(this.isReply, rhs.isReply)
		.append(this.messageId, rhs.messageId)
		.append(this.type, rhs.type)
		.append(this.userId, rhs.userId)
		.isEquals();
	}

	
	
}