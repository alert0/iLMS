package com.hotent.portal.persistence.model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
/**
 * 对象功能:我的布局 Model对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:hugh
 * 创建时间:2015-03-18 15:39:48
 */
public class SysIndexMyLayout extends AbstractModel<String>{
	
	/**
	 *  用于去设置到布局管理实体的memo属性中，目的是在切换布局的时候区分是否为我的布局
	 */
	public static final String MY_LAYOUT="MY_LAYOUT";
	public static final String MY_LAYOUT_NAME="我的首页布局";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 主键
	protected String  id;
	// 用户ID
	protected String  userId;
	// 模版内容
	protected String  templateHtml;
	// 设计模版
	protected String  designHtml;

	public void setId(String id){
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 返回 用户ID
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	public void setTemplateHtml(String templateHtml){
		this.templateHtml = templateHtml;
	}
	/**
	 * 返回 模版内容
	 * @return
	 */
	public String getTemplateHtml() {
		return this.templateHtml;
	}
	public void setDesignHtml(String designHtml){
		this.designHtml = designHtml;
	}
	/**
	 * 返回 设计模版
	 * @return
	 */
	public String getDesignHtml() {
		return this.designHtml;
	}

   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysIndexMyLayout)) 
		{
			return false;
		}
		SysIndexMyLayout rhs = (SysIndexMyLayout) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.userId, rhs.userId)
		.append(this.templateHtml, rhs.templateHtml)
		.append(this.designHtml, rhs.designHtml)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.userId) 
		.append(this.templateHtml) 
		.append(this.designHtml) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("userId", this.userId) 
		.append("templateHtml", this.templateHtml) 
		.append("designHtml", this.designHtml) 
		.toString();
	}
   
  

}