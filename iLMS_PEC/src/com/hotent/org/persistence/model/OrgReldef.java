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
 * 描述：组织关系定义 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-29 18:00:43
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class OrgReldef extends AbstractModel<String> implements IGroup{
	
	/**
	* id_
	*/
	protected String id; 
	
	/**
	* 名称
	*/
	protected String name; 
	
	/**
	* 编码
	*/
	protected String code; 
	
	/**
	* 职务级别
	*/
	protected String postLevel; 
	
	/**
	* 描述
	*/
	protected String description; 
	
	
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
	 * 返回 名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 返回 编码
	 * @return
	 */
	public String getCode() {
		return this.code;
	}
	
	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}
	
	/**
	 * 返回 职务级别
	 * @return
	 */
	public String getPostLevel() {
		return this.postLevel;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("code", this.code) 
		.append("postLevel", this.postLevel) 
		.append("description", this.description) 
		.toString();
	}

	@Override
	public String getIdentityType() {
		return IdentityType.GROUP;
	}

	@Override
	public String getGroupId() {
		return this.id;
	}

	@Override
	public String getGroupCode() {
		return this.code;
	}

	@Override
	public Long getOrderNo() {
		return Long.valueOf(0);
	}

	@Override
	public String getGroupType() {
		return GroupTypeConstant.JOB.key();
	}

	@Override
	public GroupStructEnum getStruct() {
		return GroupStructEnum.PLAIN;
	}

	@Override
	public String getParentId() {
		return null;
	}

	@Override
	public String getPath() {
		return null;
	}

	@Override
	public Map<String, Object> getParams() {
		return null;
	}
}