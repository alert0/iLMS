package com.hotent.bpmx.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：常用语管理 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-03 10:56:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmApprovalItem extends AbstractModel<String>{
	
	//全局
	public final static Short TYPE_GLOBAL = 1; 
	//对于流程分类
	public final static Short TYPE_FLOWTYPE = 2; 
	//对于流程
	public final static Short TYPE_FLOW = 3;
	//对于个人的常用语
	public final static Short TYPE_USER =4;
	
	protected String id; /*ID*/
	protected String userId; /*用户ID*/
	protected String defKey; /*流程定义KEY*/
	protected String defName; /*流程定义Name*/
	
	protected String typeId; /*流程分类ID*/
	protected Short type; /*常用语类型*/
	protected String expression; /*常用语*/
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
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 用户ID
	 * @return
	 */
	public String getUserId() 
	{
		return this.userId;
	}
	public void setDefKey(String defKey) 
	{
		this.defKey = defKey;
	}
	/**
	 * 返回 流程定义KEY
	 * @return
	 */
	public String getDefKey() 
	{
		return this.defKey;
	}
	
	public void setDefName(String defName) 
	{
		this.defName = defName;
	}
	/**
	 * 返回 流程定义KEY
	 * @return
	 */
	public String getDefName() 
	{
		return this.defName;
	}
	
	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}
	/**
	 * 返回 流程分类ID
	 * @return
	 */
	public String getTypeId() 
	{
		return this.typeId;
	}
	public void setType(Short type) 
	{
		this.type = type;
	}
	/**
	 * 返回 常用语类型
	 * @return
	 */
	public Short getType() 
	{
		return this.type;
	}
	public void setExpression(String expression) 
	{
		this.expression = expression;
	}
	/**
	 * 返回 流程项名项
	 * @return
	 */
	public String getExpression() 
	{
		return this.expression;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("userId", this.userId) 
		.append("defKey", this.defKey) 
		.append("typeId", this.typeId) 
		.append("type", this.type) 
		.append("expression", this.expression) 
		.toString();
	}
}