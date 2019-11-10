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
 * 描述：组织关联关系 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class OrgRel extends AbstractModel<String> implements IGroup{
	
	/**
	* id_
	*/
	protected String id; 
	
	/**
	* org_id_
	*/
	protected String orgId; 
	
	/**
	* rel_def_id_
	*/
	protected String relDefId; 
	
	/**
	* rel_name_
	*/
	protected String relName; 
	
	/**
	* rel_code_
	*/
	protected String relCode; 
	protected String orgName; 
	protected String jobName; 
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * 返回 id_
	 * @return
	 */
	public String getOrgName() {
		return this.orgName;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	/**
	 * 返回 id_
	 * @return
	 */
	public String getJobName() {
		return this.jobName;
	}
	
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
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/**
	 * 返回 org_id_
	 * @return
	 */
	public String getOrgId() {
		return this.orgId;
	}
	
	public void setRelDefId(String relDefId) {
		this.relDefId = relDefId;
	}
	
	/**
	 * 返回 rel_def_id_
	 * @return
	 */
	public String getRelDefId() {
		return this.relDefId;
	}
	
	public void setRelName(String relName) {
		this.relName = relName;
	}
	
	/**
	 * 返回 rel_name_
	 * @return
	 */
	public String getRelName() {
		return this.relName;
	}
	
	public void setRelCode(String relCode) {
		this.relCode = relCode;
	}
	
	/**
	 * 返回 rel_code_
	 * @return
	 */
	public String getRelCode() {
		return this.relCode;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("orgId", this.orgId) 
		.append("relDefId", this.relDefId) 
		.append("relName", this.relName) 
		.append("relCode", this.relCode) 
		.toString();
	}

	public String getIdentityType() {
		return IdentityType.GROUP;
	}

	public String getGroupId() {
		return this.id;
	}

	public String getName() {
		return this.relName;
	}

	public String getGroupCode() {
		return this.relCode;
	}

	public Long getOrderNo() {
		return Long.valueOf(0);
	}

	public String getGroupType() {
		return GroupTypeConstant.POSITION.key();
	}

	public GroupStructEnum getStruct() {
		return GroupStructEnum.PLAIN;
	}

	/**
	 * 岗位的父ID返回组织ID
	 */
	public String getParentId() {
		return orgId;
	}

	public String getPath() {
		return null;
	}

	public Map<String, Object> getParams() {
		return null;
	}
}