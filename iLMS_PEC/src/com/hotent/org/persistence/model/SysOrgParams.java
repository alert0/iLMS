package com.hotent.org.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：组织参数 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-04 11:39:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysOrgParams extends AbstractModel<String>{
	
	private static final long serialVersionUID = 1L;

	/**
	* ID_
	*/
	protected String id; 
	
	/**
	* ORG_ID_
	*/
	protected String orgId; 
	
	/**
	* ALIAS_
	*/
	protected String alias; 
	
	/**
	* VALUE_
	*/
	protected String value; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 ID_
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/**
	 * 返回 ORG_ID_
	 * @return
	 */
	public String getOrgId() {
		return this.orgId;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * 返回 ALIAS_
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 返回 VALUE_
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("orgId", this.orgId) 
		.append("alias", this.alias) 
		.append("value", this.value) 
		.toString();
	}
}