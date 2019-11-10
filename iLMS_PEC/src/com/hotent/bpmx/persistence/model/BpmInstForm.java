package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：bpm_inst_form 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-07-04 15:19:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmInstForm extends AbstractModel<String>{
	
	/**
	* id_
	*/
	protected String id; 
	
	/**
	* 流程定义id
	*/
	protected String defId; 
	
	/**
	* 实例id
	*/
	protected String instId; 
	
	/**
	* 表单 inner 记录id  frame 记录formValue
	*/
	protected String formValue; 
	
	/**
	* 节点id  instId == nodeId 一样时， 存储的是流程的实例表单
	*/
	protected String nodeId; 
	
	/**
	* 表单类型 pc mobile
	*/
	protected String formType; 
	
	/**
	* url表单 frame  在线表单 inner
	*/
	protected String formCategory; 
	
	
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
	
	public void setDefId(String defId) {
		this.defId = defId;
	}
	
	/**
	 * 返回 流程定义id
	 * @return
	 */
	public String getDefId() {
		return this.defId;
	}
	
	public void setInstId(String instId) {
		this.instId = instId;
	}
	
	/**
	 * 返回 实例id
	 * @return
	 */
	public String getInstId() {
		return this.instId;
	}
	
	public void setFormValue(String formValue) {
		this.formValue = formValue;
	}
	
	/**
	 * 返回 表单 inner 记录id  frame 记录formValue
	 * @return
	 */
	public String getFormValue() {
		return this.formValue;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	/**
	 * 返回 节点id
	 * @return
	 */
	public String getNodeId() {
		return this.nodeId;
	}
	
	public void setFormType(String formType) {
		this.formType = formType;
	}
	
	/**
	 * 返回 表单类型 pc mobile
	 * @return
	 */
	public String getFormType() {
		return this.formType;
	}
	
	public void setFormCategory(String formCategory) {
		this.formCategory = formCategory;
	}
	
	/**
	 * 返回 url表单 frame  在线表单 inner
	 * @return
	 */
	public String getFormCategory() {
		return this.formCategory;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("defId", this.defId) 
		.append("instId", this.instId) 
		.append("formValue", this.formValue) 
		.append("nodeId", this.nodeId) 
		.append("formType", this.formType) 
		.append("formCategory", this.formCategory) 
		.toString();
	}
}