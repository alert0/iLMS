package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:系统分类组值表 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-05-08 14:12:26
 */
public class SysCategory extends AbstractModel<String>{
	
	/**
	 * 树型数据 type=1
	 */
	public final static Integer DATA_TYPE_TREE=1;
	/**
	 * 平铺数据 type=0
	 */
	public final static Integer DATA_TYPE_FLAT=0;
	/**
	 * 是否叶子(N否,Y是)
	 */
	public final static char IS_LEAF_N='N';
	public final static char IS_LEAF_Y='Y';
	
	/**
	 * 自编码生成方式(0	手工录入,1自动生成)
	 */
	public final static String NODE_CODE_TYPE_AUTO_N="0";
	public final static String NODE_CODE_TYPE_AUTO_Y="1";
	
	
	
	protected String id; /*主键ID*/
	protected String groupKey; /*分类组业务主键*/
	protected String name; /*分类名*/
	protected Integer flag; /*标识*/
	protected Integer sn; /*序号*/
	protected Short type; /*类别。0=平铺结构；1=树型结构*/
	protected String createBy; /*创建人ID*/
	protected java.util.Date createTime; /*创建时间*/
	protected String createOrgId; /*创建者所属组织ID*/
	protected String updateBy; /*更新人ID*/
	protected java.util.Date updateTime; /*更新时间*/
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
	public void setGroupKey(String groupKey) 
	{
		this.groupKey = groupKey;
	}
	/**
	 * 返回 分类组业务主键
	 * @return
	 */
	public String getGroupKey() 
	{
		return this.groupKey;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 分类名
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setFlag(Integer flag) 
	{
		this.flag = flag;
	}
	/**
	 * 返回 标识
	 * @return
	 */
	public Integer getFlag() 
	{
		return this.flag;
	}
	public void setSn(Integer sn) 
	{
		this.sn = sn;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public Integer getSn() 
	{
		return this.sn;
	}
	public void setType(Short type) 
	{
		this.type = type;
	}
	/**
	 * 返回 类别。0=平铺结构；1=树型结构
	 * @return
	 */
	public Short getType() 
	{
		return this.type;
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
		.append("groupKey", this.groupKey) 
		.append("name", this.name) 
		.append("flag", this.flag) 
		.append("sn", this.sn) 
		.append("type", this.type) 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		.append("createOrgId", this.createOrgId) 
		.append("updateBy", this.updateBy) 
		.append("updateTime", this.updateTime) 
		.toString();
	}
}