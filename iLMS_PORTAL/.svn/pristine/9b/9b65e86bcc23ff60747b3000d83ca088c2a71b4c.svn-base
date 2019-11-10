package com.hotent.form.persistence.model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：BPM_FORM_RIGHT 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-19 14:22:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmFormRight extends AbstractModel<String>{
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 1L;

	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* form_key_
	*/
	@XmlAttribute(name = "formKey")
	protected String formKey; 
	
	/**
	* 流程key
	*/
	@XmlAttribute(name = "flowKey")
	protected String flowKey; 
	
	/**
	* 节点ID
	*/
	@XmlAttribute(name = "nodeId")
	protected String nodeId; 
	
	/**
	* 父流程定义
	*/
	@XmlAttribute(name = "parentFlowKey")
	protected String parentFlowKey; 
	
	/**
	* 权限字段
	*/
	@XmlElement(name = "permission")
	protected String permission; 
	
	/**
	 * 权限类型 1 流程权限，2 实例权限
	 */
	@XmlAttribute(name = "permissionType")
	protected int permissionType=0;
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	
	/**
	 * 返回 form_key_
	 * @return
	 */
	public String getFormKey() {
		return this.formKey;
	}
	
	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}
	
	/**
	 * 返回 流程key
	 * @return
	 */
	public String getFlowKey() {
		return this.flowKey;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	/**
	 * 返回 节点ID
	 * @return
	 */
	public String getNodeId() {
		return this.nodeId;
	}
	
	public void setParentFlowKey(String parentFlowKey) {
		this.parentFlowKey = parentFlowKey;
	}
	
	/**
	 * 返回 父流程定义
	 * @return
	 */
	public String getParentFlowKey() {
		return this.parentFlowKey;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	/**
	 * 返回 权限字段
	 * @return
	 */
	public String getPermission() {
		return this.permission;
	}
	public int getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("formKey", this.formKey) 
		.append("flowKey", this.flowKey) 
		.append("nodeId", this.nodeId) 
		.append("parentFlowKey", this.parentFlowKey) 
		.append("permission", this.permission) 
		.toString();
	}
}