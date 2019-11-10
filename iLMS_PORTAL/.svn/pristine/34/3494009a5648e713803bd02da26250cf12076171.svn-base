package com.hotent.biz.message.model;



import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：xq_message_type 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 15:26:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class MessageType extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* AUTHORIZE_ID
	*/
	protected String id; 
	
	/**
	* CLASSIFICATION_CODE 分类名
	*/
	protected String classificationCode; 
	
	/**
	* CREATE_BY   创建人
	*/
	protected String createBy; 
	
	/**
	* CREATE_TIME 创建时间
	*/
	protected java.util.Date createTime; 
	
	/**
	* IS_PENDING 是否需要審批
	*/
	protected Short ispending; 
	
	/**
	* 审批人id
	*/
	protected String pendingUserId; 
	
	/**
	* 审批人姓名
	*/
	protected String pendingUserName; 
	
		
	public Short getIspending() {
		return ispending;
	}

	public void setIspending(Short ispending) {
		this.ispending = ispending;
	}

	public String getPendingUserId() {
		return pendingUserId;
	}

	public void setPendingUserId(String pendingUserId) {
		this.pendingUserId = pendingUserId;
	}

	public String getPendingUserName() {
		return pendingUserName;
	}

	public void setPendingUserName(String pendingUserName) {
		this.pendingUserName = pendingUserName;
	}
	protected String ownerNameJson="[]";
	
	//授权流程名称(仅用于存放授权流程的JSON数据)
		
	
	
	
	/**
	 * 返回 ID
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public void setClassificationCode(String classificationCode) {
		this.classificationCode = classificationCode;
	}
	
	

	/**
	 * 返回 CLASSIFICATION_CODE
	 * @return
	 */
	public String getClassificationCode() {
		return this.classificationCode;
	}
	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	/**
	 * 返回 CREATE_BY
	 * @return
	 */
	public String getCreateBy() {
		return this.createBy;
	}
	
	public String getOwnerNameJson() {
		return ownerNameJson;
	}
	public void setOwnerNameJson(String ownerNameJson) {
		this.ownerNameJson = ownerNameJson;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 返回 CREATE_TIME
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("classificationCode", this.classificationCode) 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		
		.toString();
	}
}