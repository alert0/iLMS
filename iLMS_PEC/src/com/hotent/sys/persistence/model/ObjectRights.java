package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：对象权限 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh zhuang
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-04-17 13:47:17
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class ObjectRights extends AbstractModel<String>{
	protected String id; /*主键ID*/
	protected String objType; /*对象类型 authorize_type_*/
	protected String objId; /*对象数据ID authorize_id_*/
	protected String ownerType; /*授权类型*/
	protected String ownerId; /*授权对象ID*/
	protected String ownerName; /*授权对象名称*/
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主键ID
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setObjType(String objType) 
	{
		this.objType = objType;
	}
	/**
	 * 返回 对象类型
	 * @return
	 */
	public String getObjType() 
	{
		return this.objType;
	}
	public void setObjId(String objId) 
	{
		this.objId = objId;
	}
	/**
	 * 返回 对象数据ID
	 * @return
	 */
	public String getObjId() 
	{
		return this.objId;
	}
	public void setOwnerType(String ownerType) 
	{
		this.ownerType = ownerType;
	}
	/**
	 * 返回 授权类型
	 * @return
	 */
	public String getOwnerType() 
	{
		return this.ownerType;
	}
	public void setOwnerId(String ownerId) 
	{
		this.ownerId = ownerId;
	}
	/**
	 * 返回 授权对象ID
	 * @return
	 */
	public String getOwnerId() 
	{
		return this.ownerId;
	}
	public void setOwnerName(String ownerName) 
	{
		this.ownerName = ownerName;
	}
	/**
	 * 返回 授权对象名称
	 * @return
	 */
	public String getOwnerName() 
	{
		return this.ownerName;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("objType", this.objType) 
		.append("objId", this.objId) 
		.append("ownerType", this.ownerType) 
		.append("ownerId", this.ownerId) 
		.append("ownerName", this.ownerName) 
		.toString();
	}
}