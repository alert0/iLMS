package com.hotent.sys.api.msg.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.msg.model.MsgVo;

public class DefaultMsgVo implements MsgVo{
	private static final long serialVersionUID = 2196432461921825855L;
	private String templateId;
	private String type;
	private String templateAlias;
	private String subject;
	private String smsTemplateNo; /*短信模板*/
	private String voiceTemplateNo; /*语音模板*/
	private String content;
	private IUser sender;
	private List<IUser> receivers;
	//抄送用户
	private List<IUser> ccUsers;
	//密送用户
	private List<IUser> bccUsers;
	
	private List<String> parms;/*模板中${xxx}中的参数*/
	private Map<String, String> attachments;/*附近,key:文件名；value:文件绝对路径*/
	
	public DefaultMsgVo(String content,IUser sender,List<IUser> receivers,String type){
		this.subject = "";
		this.content = content;
		this.sender = sender;
		this.receivers = receivers;
		this.type = type;
	}
	public DefaultMsgVo(String content,IUser sender,List<IUser> receivers,String type,Map<String, String> attachments){
		this.subject = "";
		this.content = content;
		this.sender = sender;
		this.receivers = receivers;
		this.type = type;
		this.attachments=attachments;
	}
	public DefaultMsgVo(String subject,String content,IUser sender,List<IUser> receivers,String type){
		this.subject = subject;
		this.content = content;
		this.sender = sender;
		this.receivers = receivers;
		this.type = type;
	}
	public DefaultMsgVo(String subject,String content,IUser sender,List<IUser> receivers,String type,Map<String, String> attachments){
		this.subject = subject;
		this.content = content;
		this.sender = sender;
		this.receivers = receivers;
		this.type = type;
		this.attachments=attachments;
	}
	
	private Map<String, Object> extendVars = new HashMap<String, Object>();
	
	public Map<String, String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}
	
	public List<String> getParms() {
		return parms;
	}

	public void setParms(List<String> parms) {
		this.parms = parms;
	}

	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplateAlias() {
		return templateAlias;
	}
	public void setTemplateAlias(String templateAlias) {
		this.templateAlias = templateAlias;
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
	public IUser getSender() {
		return sender;
	}
	public void setSender(IUser sender) {
		this.sender = sender;
	}
	public List<IUser> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<IUser> receivers) {
		this.receivers = receivers;
	}	
	
	public Map<String, Object> getExtendVars() {
		return extendVars;
	}
	public void setExtendVars(Map<String, Object> extendVars) {
		this.extendVars = extendVars;
	}	
	@Override
	public String toString() {		
		return new ToStringBuilder(this)
				.append("type",getType())
				.append("templateAlias",templateAlias)
				.append("subject",subject)
				.append("content",content)
				.append("sender",sender)
				.append("receivers",receivers)
				.append("extendVars",extendVars)
				.toString();
	}
	

	
	public void setSmsTemplateNo(String smsTemplateNo) 
	{
		this.smsTemplateNo = smsTemplateNo;
	}

	public String getSmsTemplateNo() 
	{
		return this.smsTemplateNo;
	}
	
	public void setVoiceTemplateNo(String voiceTemplateNo) 
	{
		this.voiceTemplateNo = voiceTemplateNo;
	}
 
	public String getVoiceTemplateNo() 
	{
		return this.voiceTemplateNo;
	}
	public List<IUser> getCcUsers() {
		return ccUsers;
	}
	public void setCcUsers(List<IUser> ccUsers) {
		this.ccUsers = ccUsers;
	}
	public List<IUser> getBccUsers() {
		return bccUsers;
	}
	public void setBccUsers(List<IUser> bccUsers) {
		this.bccUsers = bccUsers;
	}
	
}
