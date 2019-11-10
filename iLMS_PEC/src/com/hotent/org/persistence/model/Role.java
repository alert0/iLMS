package com.hotent.org.persistence.model;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.GroupStructEnum;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IdentityType;


 /**
 * 
 * <pre> 
 * 描述：角色管理 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:28:04
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class Role extends AbstractModel<String> implements IGroup{
	
	/**
	* id_
	*/
	protected String id; 
	
	/**
	* 角色名称
	*/
	protected String name; 
	
	/**
	* 角色别名
	*/
	protected String alias; 
	
	/**
	* 0：禁用，1：启用
	*/
	protected Integer enabled; 
	
	/**
	 * 角色描述
	 */
	protected String description="";
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 id_
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 角色名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * 返回 角色别名
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}
	
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * 返回 0：禁用，1：启用
	 * @return
	 */
	public Integer getEnabled() {
		return this.enabled;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("alias", this.alias) 
		.append("enabled", this.enabled) 
		.toString();
	}

	public String getIdentityType() {
		return IdentityType.GROUP;
	}

	public String getGroupId() {
		return this.id;
	}

	public String getGroupCode() {
	 
		return this.alias;
	}

	public Long getOrderNo() {
		return Long.valueOf(1);
	}

	public String getGroupType() {
		return GroupTypeConstant.ROLE.key();
	}

	public GroupStructEnum getStruct() {
		return GroupStructEnum.PLAIN;
	}

	public String getParentId() {
		return "";
	}

	public String getPath() {
		return this.name;
	}

	public Map<String, Object> getParams() {
	 
		return null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}