package com.hotent.org.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：用户组织关系 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:27:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class OrgUser extends AbstractModel<String>{
	
	/**
	 * 主关系
	 */
	public static final Integer MASTER_YES=1;
	
	/**
	 * 非主关系
	 */
	public static final Integer MASTER_NO=0;
	
	
	/**
	* id_
	*/
	protected String id; 
	
	/**
	* org_id_
	*/
	protected String orgId; 
	
	/**
	* user_id_
	*/
	protected String userId; 
	
	/**
	* 0:非主部门，1：主部门
	*/
	protected Integer isMaster; 
	
	/**
	 * 0： 非负责人 ， 1: 负责人， 2 部门的主负责人
	 */
	protected Integer isCharge=0;
	
	/**
	* rel_id_
	*/
	protected String relId; 
	
	
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
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 user_id_
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	
	public void setIsMaster(Integer isMaster) {
		this.isMaster = isMaster;
	}
	
	/**
	 * 返回 0:非主部门，1：主部门
	 * @return
	 */
	public Integer getIsMaster() {
		return this.isMaster;
	}
	
	public Integer getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(Integer isCharge) {
		this.isCharge = isCharge;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}
	
	/**
	 * 返回 rel_id_
	 * @return
	 */
	public String getRelId() {
		return this.relId;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("orgId", this.orgId) 
		.append("userId", this.userId) 
		.append("isMaster", this.isMaster) 
		.append("relId", this.relId) 
		.toString();
	}
}