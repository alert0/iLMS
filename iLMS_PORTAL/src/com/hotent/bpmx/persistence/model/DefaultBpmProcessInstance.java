package com.hotent.bpmx.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;



/**
 * 对象功能:流程实例 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-03-07 15:35:54
 */
public class DefaultBpmProcessInstance extends AbstractModel<String> implements BpmProcessInstance,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7883744782862256060L;
	/*流程实例ID*/
	protected String  id; 
	/*流程实例标题*/
	protected String  subject; 
	/*流程定义ID*/
	protected String  procDefId; 
	/*BPMN流程定义ID*/
	protected String  bpmnDefId; 
	/*流程定义Key*/
	protected String  procDefKey; 
	protected String  procDefName; /*流程名称*/
	protected String  bizKey; /*关联数据业务主键*/
	protected String  sysCode; /*关联数据系统编码*/
	protected String  formKey; /*绑定的表单主键*/
	
	/**
	 * 实例状态
	 * running
	 * back
	 * end
	 */
	protected String  status; 
	protected java.util.Date  endTime; /*实例结束时间*/
	protected Long  duration; /*持续时间(ms)*/
	protected String  typeId; /*所属分类ID*/
	protected String  resultType; /*流程结束后的最终审批结果，agree=同意；refuse=拒绝*/
	protected String  bpmnInstId; /*BPMN流程实例ID*/
	protected String  createBy; /*创建人ID*/
	protected String  creator; /*创建人*/
	protected java.util.Date  createTime; /*创建时间*/
	protected String  createOrgId; /*创建者所属组织ID*/
	protected String  updateBy; /*更新人ID*/
	protected java.util.Date  updateTime; /*更新时间*/
	protected String  isFormmal=FORMAL_YES; /*是否正式数据*/
	protected String  parentInstId="0"; /*父实例Id*/
	protected  String superNodeId;/*父流程节点定义ID*/

	//是否禁止
	protected int isForbidden_=0;
	
	/**
	 * 支持手机表单。
	 */
	protected int supportMobile=0;
	
	/**
	 * 业务数据存储模式。
	 * pk:主键
	 * bo:业务对象
	 */
	protected String dataMode="";
	
  	protected JSONObject authorizeRight;  //流程分管授权权限对象
  	
  	/**
  	 * 任务名称，做查询使用。
  	 */
  	protected String taskName="";
	
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 流程实例ID
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setSuperNodeId(String superNodeId) 
	{
		this.superNodeId = superNodeId;
	}
 
	public String getSuperNodeId() 
	{
		return this.superNodeId;
	}
	
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}
	/**
	 * 返回 流程实例标题
	 * @return
	 */
	public String getSubject() 
	{
		return this.subject;
	}
	public void setProcDefId(String procDefId) 
	{
		this.procDefId = procDefId;
	}
	/**
	 * 返回 流程定义ID
	 * @return
	 */
	public String getProcDefId() 
	{
		return this.procDefId;
	}
	public void setBpmnDefId(String bpmnDefId) 
	{
		this.bpmnDefId = bpmnDefId;
	}
	/**
	 * 返回 BPMN流程定义ID
	 * @return
	 */
	public String getBpmnDefId() 
	{
		return this.bpmnDefId;
	}
	public void setProcDefKey(String procDefKey) 
	{
		this.procDefKey = procDefKey;
	}
	/**
	 * 返回 流程定义Key
	 * @return
	 */
	public String getProcDefKey() 
	{
		return this.procDefKey;
	}
	public void setProcDefName(String procDefName) 
	{
		this.procDefName = procDefName;
	}
	/**
	 * 返回 流程名称
	 * @return
	 */
	public String getProcDefName() 
	{
		return this.procDefName;
	}
	public void setBizKey(String bizKey) 
	{
		this.bizKey = bizKey;
	}
	/**
	 * 返回 关联数据业务主键
	 * @return
	 */
	public String getBizKey() 
	{
		return this.bizKey;
	}
	
	/**
	 * 返回业务系统编码
	 * @return
	 */
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	public void setFormKey(String formKey) 
	{
		this.formKey = formKey;
	}
	/**
	 * 返回 绑定的表单主键
	 * @return
	 */
	public String getFormKey() 
	{
		return this.formKey;
	}
	
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 实例状态
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}
	public void setEndTime(java.util.Date endTime) 
	{
		this.endTime = endTime;
	}
	/**
	 * 返回 实例结束时间
	 * @return
	 */
	public java.util.Date getEndTime() 
	{
		return this.endTime;
	}
	public void setDuration(Long duration) 
	{
		this.duration = duration;
	}
	/**
	 * 返回 持续时间(ms)
	 * @return
	 */
	public Long getDuration() 
	{
		return this.duration;
	}
	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}
	/**
	 * 返回 所属分类ID
	 * @return
	 */
	public String getTypeId() 
	{
		return this.typeId;
	}
	public void setResultType(String resultType) 
	{
		this.resultType = resultType;
	}
	/**
	 * 返回 流程结束后的最终审批结果，agree=同意；refuse=拒绝
	 * @return
	 */
	public String getResultType() 
	{
		return this.resultType;
	}
	public void setBpmnInstId(String bpmnInstId) 
	{
		this.bpmnInstId = bpmnInstId;
	}
	/**
	 * 返回 BPMN流程实例ID
	 * @return
	 */
	public String getBpmnInstId() 
	{
		return this.bpmnInstId;
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
	
	/**
	 * 返回 创建人
	 * @return
	 */
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
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
	public void setIsFormmal(String isFormmal) 
	{
		this.isFormmal = isFormmal;
	}
	/**
	 * 返回 是否正式数据
	 * @return
	 */
	public String getIsFormmal() 
	{
		return this.isFormmal;
	}
	public void setParentInstId(String parentInstId) 
	{
		this.parentInstId = parentInstId;
	}
	/**
	 * 返回 父实例Id
	 * @return
	 */
	public String getParentInstId() 
	{
		if(StringUtil.isEmpty(this.parentInstId)) return "0";
		return this.parentInstId;
	}
	
	public JSONObject getAuthorizeRight()
	{
		return authorizeRight;
	}
	public void setAuthorizeRight(JSONObject authorizeRight)
	{
		this.authorizeRight = authorizeRight;
	}
	
	void setIsForbidden(int isForbidden){
		this.isForbidden_=isForbidden;
	}
	
	@Override
	public int getIsForbidden() {
		return this.isForbidden_;
	}
	@Override
	public String getDataMode() {
		return this.dataMode;
	}
	
	public void setDataMode(String mode){
		this.dataMode=mode;
	}
	
	public int getSupportMobile() {
		return supportMobile;
	}
	public void setSupportMobile(int supportMobile) {
		this.supportMobile = supportMobile;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("subject", this.subject) 
		.append("procDefId", this.procDefId) 
		.append("bpmnDefId", this.bpmnDefId) 
		.append("procDefKey", this.procDefKey) 
		.append("procDefName", this.procDefName) 
		.append("bizKey", this.bizKey) 
		.append("sysCode", this.sysCode) 
		.append("formKey", this.formKey) 
		.append("status", this.status) 
		.append("endTime", this.endTime) 
		.append("duration", this.duration) 
		.append("typeId", this.typeId) 
		.append("resultType", this.resultType) 
		.append("bpmnInstId", this.bpmnInstId) 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		.append("createOrgId", this.createOrgId) 
		.append("updateBy", this.updateBy) 
		.append("updateTime", this.updateTime) 
		.append("isFormmal", this.isFormmal) 
		.append("parentInstId", this.parentInstId) 
		.toString();
	}
	
	
}