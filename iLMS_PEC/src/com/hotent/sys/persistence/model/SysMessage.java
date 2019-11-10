package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：sys_msg 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 09:03:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysMessage extends AbstractModel<String>{
	
	public final static Short iS_REPLY_YES = 1; //可以回复
	public final static Short iS_REPLY_NO= 0;   //不可以回复
	
	
	public final static Short iS_PUBLIC_YES= 1;   //是公告
	public final static Short iS_PUBLIC_NO= 0;   //不是公告
	
	public final static String TYPE_BULLETIN="bulletin";//公告类型
	
	
	
	protected String id; /*主键*/
	protected String subject; /*主题*/
	protected String ownerId; /*发帖人ID*/
	protected String owner; /*发帖人*/
	protected String messageType; /*消息类型*/
	protected java.util.Date createTime; /*创建时间*/
	protected Short canReply; /*是否可以回复*/
	protected Short isPublic; /*是否公告*/
	protected String content; /*内容*/
	protected String fileMsg; /*附件信息*/
	protected String receiverName;/*接收人名称*/
	
	/*非数据库字段*/
	protected java.util.Date receiveTime;/*阅读时间*/
	protected String receiverId;/*接收人id*/
	protected String receiverOrgName;/*接受组织名称*/
	protected String receiverOrgId;/*接受组织Id*/
	protected String rid;//收信id
	protected String detailUrl;//详情地址	
	
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
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}
	/**
	 * 返回 主题
	 * @return
	 */
	public String getSubject() 
	{
		return this.subject;
	}
	public void setOwnerId(String ownerId) 
	{
		this.ownerId = ownerId;
	}
	/**
	 * 返回 发帖人ID
	 * @return
	 */
	public String getOwnerId() 
	{
		return this.ownerId;
	}
	public void setOwner(String owner) 
	{
		this.owner = owner;
	}
	/**
	 * 返回 发帖人
	 * @return
	 */
	public String getOwner() 
	{
		return this.owner;
	}
	public void setMessageType(String messageType) 
	{
		this.messageType = messageType;
	}
	/**
	 * 返回 消息类型
	 * @return
	 */
	public String getMessageType() 
	{
		return this.messageType;
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
	public void setCanReply(Short canReply) 
	{
		this.canReply = canReply;
	}
	/**
	 * 返回 是否可以回复
	 * @return
	 */
	public Short getCanReply() 
	{
		return this.canReply;
	}
	public void setIsPublic(Short isPublic) 
	{
		this.isPublic = isPublic;
	}
	/**
	 * 返回 是否公告
	 * @return
	 */
	public Short getIsPublic() 
	{
		return this.isPublic;
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
	public void setFileMsg(String fileMsg) 
	{
		this.fileMsg = fileMsg;
	}
	/**
	 * 返回 附件信息
	 * @return
	 */
	public String getFileMsg() 
	{
		return this.fileMsg;
	}
	public java.util.Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(java.util.Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverOrgName() {
		return receiverOrgName;
	}
	public void setReceiverOrgName(String receiverOrgName) {
		this.receiverOrgName = receiverOrgName;
	}
	
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverOrgId() {
		return receiverOrgId;
	}
	public void setReceiverOrgId(String receiverOrgId) {
		this.receiverOrgId = receiverOrgId;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("subject", this.subject) 
		.append("ownerId", this.ownerId) 
		.append("owner", this.owner) 
		.append("messageType", this.messageType) 
		.append("createTime", this.createTime) 
		.append("canReply", this.canReply) 
		.append("isPublic", this.isPublic) 
		.append("content", this.content) 
		.append("fileMsg", this.fileMsg) 
		.toString();
	}
}