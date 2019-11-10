package com.hotent.form.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:表单模版 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xianggang
 * 创建时间:2014-08-27 09:42:44
 */
public class BpmFormTemplate extends AbstractModel<String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//模版类型
		//主表模版
		public static final String MAIN_TABLE="main";
		public static final String MOBILE_MAIN="mobileMain";
		//子表模版
		public static final String SUB_TABLE="subTable";
		public static final String Mobile_SUB="mobileSub";
		//宏模版
		public static final String MACRO="macro";
		//列表模版
		public static final String LIST="list";
		//明细模版
		public static final String DETAIL="detail";
		/**
		 *	表管理模板 
		 */
		public static final String TABLE_MANAGE="tableManage";
		/**
		 *	表管理模板 
		 */
		public static final String DATA_TEMPLATE="dataTemplate";
		
		/**
		 * 查询数据模块
		 */
		public static final String QUERY_DATA_TEMPLATE="queryDataTemplate";
		
	protected String templateId; /*模板id*/
	protected String templateName; /*模板名称*/
	protected String templateType; /*模板类型*/
	protected String macrotemplateAlias; /*模板所向*/
	protected String html; /*模板内容*/
	protected String templateDesc; /*模板描述*/
	protected Integer canedit; /*是否可以编辑*/
	protected String alias; /*别名*/
	@Override
	public void setId(String templateId) {
		this.templateId = templateId.toString();
	}
	@Override
	public String getId() {
		return templateId.toString();
	}	
	public void setTemplateId(String templateId) 
	{
		this.templateId = templateId;
	}
	/**
	 * 返回 模板id
	 * @return
	 */
	public String getTemplateId() 
	{
		return this.templateId;
	}
	public void setTemplateName(String templateName) 
	{
		this.templateName = templateName;
	}
	/**
	 * 返回 模板名称
	 * @return
	 */
	public String getTemplateName() 
	{
		return this.templateName;
	}
	public void setTemplateType(String templateType) 
	{
		this.templateType = templateType;
	}
	/**
	 * 返回 模板类型
	 * @return
	 */
	public String getTemplateType() 
	{
		return this.templateType;
	}
	public void setMacrotemplateAlias(String macrotemplateAlias) 
	{
		this.macrotemplateAlias = macrotemplateAlias;
	}
	/**
	 * 返回 模板所向
	 * @return
	 */
	public String getMacrotemplateAlias() 
	{
		return this.macrotemplateAlias;
	}
	public void setHtml(String html) 
	{
		this.html = html;
	}
	/**
	 * 返回 模板内容
	 * @return
	 */
	public String getHtml() 
	{
		return this.html;
	}
	public void setTemplateDesc(String templateDesc) 
	{
		this.templateDesc = templateDesc;
	}
	/**
	 * 返回 模板描述
	 * @return
	 */
	public String getTemplateDesc() 
	{
		return this.templateDesc;
	}
	public void setCanedit(Integer canedit) 
	{
		this.canedit = canedit;
	}
	/**
	 * 返回 是否可以编辑
	 * @return
	 */
	public Integer getCanedit() 
	{
		return this.canedit;
	}
	public void setAlias(String alias) 
	{
		this.alias = alias;
	}
	/**
	 * 返回 别名
	 * @return
	 */
	public String getAlias() 
	{
		return this.alias;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("templateId", this.templateId) 
		.append("templateName", this.templateName) 
		.append("templateType", this.templateType) 
		.append("macrotemplateAlias", this.macrotemplateAlias) 
		.append("html", this.html) 
		.append("templateDesc", this.templateDesc) 
		.append("canedit", this.canedit) 
		.append("alias", this.alias) 
		.toString();
	}
}