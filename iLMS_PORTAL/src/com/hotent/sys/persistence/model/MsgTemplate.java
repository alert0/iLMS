package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.sys.api.template.model.TemplateVo;

/**
 * 对象功能:消息模版 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-05-06 16:21:15
 */
public class MsgTemplate extends AbstractModel<String> implements TemplateVo{
	protected String  id; /*主键*/
	protected String  name; /*模版名称*/
	protected String  key; /*模版业务键*/
	protected String  typeKey; /*模板分类。可以按任务操作类型分类，也可以按其它方式分类。*/
	protected char  isDefault; /*是否默认模板。对于同一组（模板分类+接收者类型）下的多个模板其中默认的一个。*/
	protected String  subject; /*标题*/
	protected String  plain; /*纯文本*/
	protected String  html; /*模版体HTML*/
	protected String  createBy; /*创建人ID*/
	protected java.util.Date  createTime; /*创建时间*/
	protected String  createOrgId; /*创建者所属组织ID*/
	protected String  updateBy; /*更新人ID*/
	protected java.util.Date  updateTime; /*更新时间*/
	
	protected String smsTemplateNo; /*短信模板*/
	protected String voiceTemplateNo; /*语音模板*/
	
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
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 模版名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setKey(String key) 
	{
		this.key = key;
	}
	/**
	 * 返回 模版业务键
	 * @return
	 */
	public String getKey() 
	{
		return this.key;
	}
	
	public void setTypeKey(String typeKey) 
	{
		this.typeKey = typeKey;
	}
	/**
	 * 返回 模板分类。可以按任务操作类型分类，也可以按其它方式分类。
	 * @return
	 */
	public String getTypeKey() 
	{
		return this.typeKey;
	}
	
	public void setIsDefault(char isDefault) 
	{
		this.isDefault = isDefault;
	}
	/**
	 * 返回 是否默认模板。对于同一组（模板分类+接收者类型）下的多个模板其中默认的一个。
	 * @return
	 */
	public char getIsDefault() 
	{
		return this.isDefault;
	}
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}
	/**
	 * 返回 标题
	 * @return
	 */
	public String getSubject() 
	{
		return this.subject;
	}
	public void setPlain(String plain) 
	{
		this.plain = plain;
	}
	/**
	 * 返回 纯文本
	 * @return
	 */
	public String getPlain() 
	{
		return this.plain;
	}
	public void setHtml(String html) 
	{
		this.html = html;
	}
	/**
	 * 返回 模版体HTML
	 * @return
	 */
	public String getHtml() 
	{
		return this.html;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}
	/**
	 * 返回 创建人ID
	 * @return
	 */
	public String getCreateBy() 
	{
		return this.createBy;
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
	public void setCreateOrgId(String createOrgId) 
	{
		this.createOrgId = createOrgId;
	}
	/**
	 * 返回 创建者所属组织ID
	 * @return
	 */
	public String getCreateOrgId() 
	{
		return this.createOrgId;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}
	/**
	 * 返回 更新人ID
	 * @return
	 */
	public String getUpdateBy() 
	{
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date updateTime) 
	{
		this.updateTime = updateTime;
	}
	/**
	 * 返回 更新时间
	 * @return
	 */
	public java.util.Date getUpdateTime() 
	{
		return this.updateTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("key", this.key) 
		.append("typeKey", this.typeKey) 
		.append("isDefault", this.isDefault) 
		.append("subject", this.subject) 
		.append("plain", this.plain) 
		.append("html", this.html) 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		.append("createOrgId", this.createOrgId) 
		.append("updateBy", this.updateBy) 
		.append("updateTime", this.updateTime) 
		.toString();
	}
	
	@Override
	public String getSubjectTemplate() {
		return this.subject;
	}
	@Override
	public String getPlainTemplate() {
		
		return this.plain;
	}
	@Override
	public String getHtmlTemplate() {
		return this.html;
	}
	
}