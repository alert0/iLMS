package com.hotent.sys.api.model;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.api.model.Tree;
import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:总分类表。用于显示平级或树层次结构的分类，可以允许任何层次结构。 entity对象
 */
public class SysType extends AbstractModel<String> implements Tree{
	
	/** 分类类别-树型结构 */
	public static final short STRU_TYPE_TREE = 1;
	/** 分类类别-平铺结构 */
	public static final short STRU_TYPE_FLAT = 0;
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8063182143115527671L;
	protected String id; /*分类ID*/
	protected String typeGroupKey; /*所属分类组业务主键*/
	protected String name; /*分类名称*/
	protected String typeKey; /*节点的分类Key*/
	protected Short struType; /*类别。0=平铺结构；1=树型结构*/
	protected String parentId; /*父节点*/
	protected Integer depth; /*层次*/
	protected String path; /*路径*/
	protected char isLeaf = 'N'; /*是否叶子节点。Y=是；N=否*/
	protected String ownerId; /*所属人ID*/
	protected Integer sn; /*序号*/
	protected String createBy; /*创建人ID*/
	protected java.util.Date createTime; /*创建时间*/
	protected String createOrgId; /*创建者所属组织ID*/
	protected String updateBy; /*更新人ID*/
	protected java.util.Date updateTime; /*更新时间*/
	
	protected List<SysType> children = new ArrayList<SysType>();
	/**
	 * 总分类表
	 */
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 分类ID
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setTypeGroupKey(String typeGroupKey) 
	{
		this.typeGroupKey = typeGroupKey;
	}
	/**
	 * 返回 所属分类组业务主键
	 * @return
	 */
	public String getTypeGroupKey() 
	{
		return this.typeGroupKey;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 分类名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setTypeKey(String typeKey) 
	{
		this.typeKey = typeKey;
	}
	/**
	 * 返回 节点的分类Key
	 * @return
	 */
	public String getTypeKey() 
	{
		return this.typeKey;
	}
	public void setStruType(Short struType) 
	{
		this.struType = struType;
	}
	/**
	 * 返回 0=平铺结构；1=树型结构
	 * @return
	 */
	public Short getStruType() 
	{
		return this.struType;
	}
	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}
	/**
	 * 返回 父节点
	 * @return
	 */
	public String getParentId() 
	{
		return this.parentId;
	}
	public void setDepth(Integer depth) 
	{
		this.depth = depth;
	}
	/**
	 * 返回 层次
	 * @return
	 */
	public Integer getDepth() 
	{
		return this.depth;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}
	/**
	 * 返回 路径
	 * @return
	 */
	public String getPath() 
	{
		return this.path;
	}
	public void setIsLeaf(char isLeaf) 
	{
		this.isLeaf = isLeaf;
	}
	/**
	 * 返回 是否叶子节点。Y=是；N=否
	 * @return
	 */
	public char getIsLeaf() 
	{
		return this.isLeaf;
	}
	public void setOwnerId(String ownerId) 
	{
		this.ownerId = ownerId;
	}
	/**
	 * 返回 所属人ID
	 * @return
	 */
	public String getOwnerId() 
	{
		return this.ownerId;
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
	
	
	public List getChildren() {
		return children;
	}

	public void setChildren(List children){
		this.children = children;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("typeGroupKey", this.typeGroupKey) 
		.append("name", this.name) 
		.append("typeKey", this.typeKey) 
		.append("struType", this.struType) 
		.append("parentId", this.parentId) 
		.append("depth", this.depth) 
		.append("path", this.path) 
		.append("isLeaf", this.isLeaf) 
		.append("ownerId", this.ownerId) 
		.append("sn", this.sn) 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		.append("createOrgId", this.createOrgId) 
		.append("updateBy", this.updateBy) 
		.append("updateTime", this.updateTime) 
		.toString();
	}
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
}