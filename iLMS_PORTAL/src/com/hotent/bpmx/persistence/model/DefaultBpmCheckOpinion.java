package com.hotent.bpmx.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;

/**
 * <pre>
 * 	对象功能:流程审批意见 entity对象
 *  开发公司:广州宏天软件有限公司 
 *  开发人员:zyp
 *  创建时间:2014-03-21 17:07:23
 *  </pre>
 */
public class DefaultBpmCheckOpinion extends AbstractModel<String> implements BpmTaskOpinion {
	/***  */
	private static final long serialVersionUID = 1L;

	public static final String OPINION_FLAG = "__form_opinion";
	protected String id; /* 意见ID */
	protected String procDefId; /* 流程定义ID */
	protected String supInstId="0"; /* 父流程实例ID */
	protected String procInstId; /* proc_inst_id_ */
	protected String taskId; /* 任务ID */
	protected String taskKey; /* 任务定义Key */
	protected String taskName; /* 任务名称 */
	protected String token; /* 任务令牌 */
	protected String qualfieds; /* 有审批资格用户ID串 */
	protected String qualfiedNames; /* 有审批资格用户名称串 */
	protected String auditor; /* 执行人ID */
	protected String auditorName; /* 执行人名 */
	protected String opinion; /* 审批意见 */

	protected String formDefId; /* 表单定义ID */
	/**
	 *  审批状态(@see {@link OpinionStatus})。
	 * <pre>
	 * start=发起流程；
	 * end=结束流程；
	 * awaiting_check=待审批；
	 * agree=同意；
	 * against=反对；
	 * return=驳回；
	 * abandon=弃权；
	 * retrieve=追回
	 * </pre>
	 **/
	protected String status; 
	protected String formName; /* 表单名 */
	protected java.util.Date createTime; /* 执行开始时间 */
	protected java.util.Date assignTime; /* 任务分配用户时间 */
	protected java.util.Date completeTime; /* 结束时间 */
	protected Long durMs; /* 持续时间(ms) */
	/**
	 * 可以使用JSON存储。 
	 * 附件存放格式。 [{fileId:"",name:""},{fileId:"",name:""}]
	 */
	protected String files = ""; /* 附件 */
	protected Integer interpose = 0; /* 是否干预 0,不干预,1干预 */

	protected String statusVal; 
	
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 意见ID
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	/**
	 * 返回 流程定义ID
	 * 
	 * @return
	 */
	public String getProcDefId() {
		return this.procDefId;
	}

	public void setSupInstId(String supInstId_) {
		if(StringUtil.isEmpty(supInstId_)){
			supInstId_="0";
		}
		this.supInstId = supInstId_;
	}

	/**
	 * 返回 父流程实例ID
	 * 
	 * @return
	 */
	public String getSupInstId() {
		return this.supInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	/**
	 * 返回 proc_inst_id_
	 * 
	 * @return
	 */
	public String getProcInstId() {
		return this.procInstId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 返回 任务ID
	 * 
	 * @return
	 */
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	/**
	 * 返回 任务定义Key
	 * 
	 * @return
	 */
	public String getTaskKey() {
		return this.taskKey;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * 返回 任务名称
	 * 
	 * @return
	 */
	public String getTaskName() {
		return this.taskName;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 返回 任务令牌
	 * 
	 * @return
	 */
	public String getToken() {
		return this.token;
	}

	public void setQualfieds(String qualfieds) {
		this.qualfieds = qualfieds;
	}

	/**
	 * 返回 有审批资格用户ID串
	 * 
	 * @return
	 */
	public String getQualfieds() {
		return this.qualfieds;
	}

	public void setQualfiedNames(String qualfiedNames) {
		this.qualfiedNames = qualfiedNames;
	}

	/**
	 * 返回 有审批资格用户名称串
	 * 
	 * @return
	 */
	public String getQualfiedNames() {
		return this.qualfiedNames;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	/**
	 * 返回 执行人ID
	 * 
	 * @return
	 */
	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	/**
	 * 返回 执行人名
	 * 
	 * @return
	 */
	public String getAuditorName() {
		return this.auditorName;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	/**
	 * 返回 审批意见
	 * 
	 * @return
	 */
	public String getOpinion() {
		return this.opinion;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 返回 审批状态。start=发起流程；awaiting_check=待审批；agree=同意；against=反对；return=驳回；
	 * abandon=弃权；retrieve=追回
	 * 
	 * @return
	 */
	public String getStatus() {
		return this.status;
	}

	public void setFormDefId(String formDefId) {
		this.formDefId = formDefId;
	}

	/**
	 * 返回 表单定义ID
	 * 
	 * @return
	 */
	public String getFormDefId() {
		return this.formDefId;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * 返回 表单名
	 * 
	 * @return
	 */
	public String getFormName() {
		return this.formName;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 返回 执行开始时间
	 * 
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setAssignTime(java.util.Date assignTime) {
		this.assignTime = assignTime;
	}

	/**
	 * 返回 任务分配用户时间
	 * 
	 * @return
	 */
	public java.util.Date getAssignTime() {
		return this.assignTime;
	}

	public void setCompleteTime(java.util.Date completeTime) {
		this.completeTime = completeTime;
	}

	/**
	 * 返回 结束时间
	 * 
	 * @return
	 */
	public java.util.Date getCompleteTime() {
		return this.completeTime;
	}

	public void setDurMs(Long durMs) {
		this.durMs = durMs;
	}

	/**
	 * 返回 持续时间(ms)
	 * 
	 * @return
	 */
	public Long getDurMs() {
		return this.durMs;
	}

	@Override
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public Integer getInterpose() {
		return interpose;
	}

	public void setInterpose(Integer interpose) {
		this.interpose = interpose;
	}
	
	public String getStatusVal() {
		if(this.status != null)
			statusVal = OpinionStatus.fromKey(this.status).getValue();
		return statusVal;
	}

	public void setStatusVal(String statusVal) {
		this.statusVal = statusVal;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("procDefId", this.procDefId)
				.append("supInstId", this.supInstId)
				.append("procInstId", this.procInstId)
				.append("taskId", this.taskId).append("taskKey", this.taskKey)
				.append("taskName", this.taskName).append("token", this.token)
				.append("qualfieds", this.qualfieds)
				.append("qualfiedNames", this.qualfiedNames)
				.append("auditor", this.auditor)
				.append("auditorName", this.auditorName)
				.append("opinion", this.opinion).append("status", this.status)
				.append("formDefId", this.formDefId)
				.append("formName", this.formName)
				.append("createTime", this.createTime)
				.append("assignTime", this.assignTime)
				.append("completeTime", this.completeTime)
				.append("durMs", this.durMs).toString();
	}
}