package com.hotent.form.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.sys.util.ContextUtil;


 /**
 * 
 * <pre> 
 * 描述：流程表单HTML设计历史记录 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:xianggang
 * 邮箱:xianggang.qq.com
 * 日期:2014-10-23 15:31:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmFormHistory extends AbstractModel<String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String id; /*ID*/
	protected String formId; /*对应表单ID*/
	protected String name; /*表单名称*/
	protected String desc; /*表单描述*/
	protected String formHtml; /*表单设计（HTML代码）*/
	protected String createUserId; /*创建人ID*/
	protected String createUserName; /*创建人Name*/
	protected java.util.Date createTime; /*创建时间*/
	
	public BpmFormHistory(){
		
	}
	public BpmFormHistory(BpmForm bpmFom){
		this.formId = bpmFom.getFormId(); /*对应表单ID*/
		this.name = bpmFom.getName(); /*表单名称*/
		this.desc = bpmFom.getDesc(); /*表单描述*/
		this.formHtml = bpmFom.getFormHtml(); /*表单设计（HTML代码）*/
		this.createUserId = ContextUtil.getCurrentUserId(); /*创建人ID*/
		this.createUserName = ContextUtil.getCurrentUser().getFullname(); /*创建人Name*/
		this.createTime = new Date(); /*创建时间*/
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ID
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setFormId(String formId) 
	{
		this.formId = formId;
	}
	/**
	 * 返回 对应表单ID
	 * @return
	 */
	public String getFormId() 
	{
		return this.formId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 表单名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
	/**
	 * 返回 表单描述
	 * @return
	 */
	public String getDesc() 
	{
		return this.desc;
	}
	public void setFormHtml(String formHtml) 
	{
		this.formHtml = formHtml;
	}
	/**
	 * 返回 表单设计（HTML代码）
	 * @return
	 */
	public String getFormHtml() 
	{
		return this.formHtml;
	}
	public void setCreateUserId(String createUserId) 
	{
		this.createUserId = createUserId;
	}
	/**
	 * 返回 创建人ID
	 * @return
	 */
	public String getCreateUserId() 
	{
		return this.createUserId;
	}
	public void setCreateUserName(String createUserName) 
	{
		this.createUserName = createUserName;
	}
	/**
	 * 返回 创建人Name
	 * @return
	 */
	public String getCreateUserName() 
	{
		return this.createUserName;
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
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("formId", this.formId) 
		.append("name", this.name) 
		.append("desc", this.desc) 
		.append("formHtml", this.formHtml) 
		.append("createUserId", this.createUserId) 
		.append("createUserName", this.createUserName) 
		.append("createTime", this.createTime) 
		.toString();
	}
}