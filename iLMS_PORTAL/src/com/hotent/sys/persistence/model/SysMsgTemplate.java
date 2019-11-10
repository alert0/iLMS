package com.hotent.sys.persistence.model;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：消息模版 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-03-10 09:20:11
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysMsgTemplate extends AbstractModel<String>{
	
	public static Short IS_DEFAULT_YES = 1;
	public static Short IS_DEFAULT_NO = 0;
	
	/** 模板用途类型静态变量  **/
	/**
	 * 任务创建通知
	 */
	public static String TYPE_TASKCREATE = "taskCreate";
	/**
	 * 任务沟通
	 */
	public static String TYPE_BPMCOMMUSEND = "bpmCommuSend";
	/**
	 * 通知沟通人
	 */
	public static String TYPE_BPMCOMMUFEEDBACK = "bpmCommuFeedBack";
	/**
	 * 任务流转默认
	 */
	public static String TYPE_BPMNTASKTRANS = "bpmnTaskTrans";
	/**
	 * 任务转交通知
	 */
	public static String TYPE_BPMHANDTO = "bpmHandTo";
	/**
	 * 加签通知
	 */
	public static String TYPE_ADDSIGNTASK = "addSignTask";
	/**
	 * 任务完成通知
	 */
	public static String TYPE_TASKCOMPLETE = "taskComplete";
	/**
	 * 任务驳回通知
	 */
	public static String TYPE_TASKBACK = "taskBack";
	/**
	 * 流程结束通知
	 */
	public static String TYPE_PROCESSEND = "processEnd";
	/**
	 * 审批提醒
	 */
	public static String TYPE_BPMNAPPROVAL = "bpmnApproval";
	/**
	 * 驳回提醒
	 */
	public static String TYPE_BPMNBACK = "bpmnBack";
	/**
	 * 撤销提醒
	 */
	public static String TYPE_BPMNRECOVER = "bpmnRecover";
	/**
	 * 代理任务审批
	 */
	public static String TYPE_BPMNAGENT = "bpmnAgent";
	/**
	 * 通知被代理人
	 */
	public static String TYPE_BPMNDELEGATE = "bpmnDelegate";
	
	@XmlElement
	protected String id; /*主键*/
	@XmlElement
	protected String name; /*模版名称*/
	@XmlElement
	protected String key; /*模版业务键*/
	@XmlElement
	protected String typeKey; /*模板分类。可以按任务操作类型分类，也可以按其它方式分类。*/
	@XmlElement
	protected Short isDefault; /*是否默认模板。对于同一组（模板分类+二级分类）下的多个模板其中默认的一个。*/
	@XmlElement
	protected String subject; /*标题*/
	@XmlElement
	protected String plain; /*纯文本*/
	@XmlElement
	protected String html; /*模版体HTML*/
	protected String createBy; /*创建人ID*/
	protected java.util.Date createTime; /*创建时间*/
	protected String createOrgId; /*创建者所属组织ID*/
	protected String updateBy; /*更新人ID*/
	protected java.util.Date updateTime; /*更新时间*/
	
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
	public void setIsDefault(Short isDefault) 
	{
		this.isDefault = isDefault;
	}
	/**
	 * 返回 是否默认模板。对于同一组（模板分类+二级分类）下的多个模板其中默认的一个。
	 * @return
	 */
	public Short getIsDefault() 
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
}