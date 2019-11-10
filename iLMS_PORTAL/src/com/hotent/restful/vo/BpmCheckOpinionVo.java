package com.hotent.restful.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <pre>
 * 	对象功能:流程审批意见 entity对象
 *  开发公司:广州宏天软件有限公司 
 *  开发人员:liangqf
 *  创建时间:2017-09-12 17:15:23
 * </pre>
 */
public class BpmCheckOpinionVo {
	protected String procDefId; /* 流程定义ID */
	protected String supInstId = "0"; /* 父流程实例ID */
	protected String procInstId; /* proc_inst_id_ */
	protected String taskId; /* 任务ID */
	protected String taskKey; /* 任务定义Key */
	protected String taskName; /* 任务名称 */

	protected String auditorName; /* 执行人名 */
	protected String opinion; /* 审批意见 */

	/**
	 * 审批状态(@see {@link OpinionStatus})。
	 * 
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
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getSupInstId() {
		return supInstId;
	}
	public void setSupInstId(String supInstId) {
		this.supInstId = supInstId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskKey() {
		return taskKey;
	}
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getStatus() {
		String rtn = status + "——";
		if ("agree".equals(status)) {
			rtn += "同意";
		} else if ("end".equals(status)) {
			rtn += "结束流程";
		} else if ("awaiting_check".equals(status)) {
			rtn += "待审批";
		} else if ("start".equals(status)) {
			rtn += "发起流程";
		} else if ("against".equals(status)) {
			rtn += "反对";
		} else if ("return".equals(status)) {
			rtn += "驳回";
		} else if ("abandon".equals(status)) {
			rtn += "弃权";
		} else if ("retrieve".equals(status)) {
			rtn += "追回";
		}else if("backToStart".equals(status)){
			rtn +="驳回发起人";
		}else if("reSubmit".equals(status)){
			rtn += "重新提交";
		}else if("revoker".equals(status)){
			rtn += "撤回";
		}else if("transRevoker".equals(status)){
			rtn += "撤销流转";
		}else if("revoker_to_start".equals(status)){
			rtn += "撤回到发起人";
		}else if("signPass".equals(status)){
			rtn += "会签通过";
		}else if("signNotPass".equals(status)){
			rtn += "会签不通过";
		}else if("signBackCancel".equals(status)){
			rtn += "驳回取消";
		}else if("signRecoverCancel".equals(status)){
			rtn += "撤销取消";
		}else if("passCancel".equals(status)){
			rtn += "通过取消";
		}else if("notPassCancel".equals(status)){
			rtn += "不通过取消";
		}else if("transforming".equals(status)){
			rtn += "流转";
		}else if("deliverto".equals(status)){
			rtn += "转办任务";
		}else if("deliverto_cancel".equals(status)){
			rtn += "转办任务取消";
		}else if("transAgree".equals(status)){
			rtn += "流转同意";
		}else if("transOppose".equals(status)){
			rtn += "流转反对";
		}else if("skip".equals(status)){
			rtn += "跳过执行";
		}else if("manual_end".equals(status)){
			rtn += "人工终止";
		}
		
		return rtn;
	}
	public void setStatus(String status) {
		if("start".equals(status))
		this.status = status;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(java.util.Date assignTime) {
		this.assignTime = assignTime;
	}
	public java.util.Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(java.util.Date completeTime) {
		this.completeTime = completeTime;
	}
	public Long getDurMs() {
		return durMs;
	}
	public void setDurMs(Long durMs) {
		this.durMs = durMs;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
		.append("procDefId", this.procDefId) 
		.append("supInstId", this.supInstId) 
		.append("procInstId", this.procInstId) 
		.append("taskId", this.taskId) 
		.append("taskKey", this.taskKey) 
		.append("auditorName", this.auditorName) 
		.append("opinion", this.opinion) 
		.append("createTime", this.createTime) 
		.append("status", this.status) 
		.append("formName", this.formName) 
		.append("assignTime", this.assignTime) 
		.append("completeTime", this.completeTime) 
		.append("durMs", this.durMs) 
		.toString();
	}

}
