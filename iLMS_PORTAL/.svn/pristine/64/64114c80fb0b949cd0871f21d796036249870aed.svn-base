package com.hotent.bpmx.persistence.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * 对象功能:流程代理设置 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-04-30 15:11:05
 * </pre>
 */
public class BpmAgentSetting extends AbstractModel<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -742735065460584216L;
	public static final Short TYPE_GLOBAL = 1;
	public static final Short TYPE_PART = 2;
	public static final Short TYPE_CONDITION = 3;

	protected String id = ""; /* 主键 */
	protected String subject; /* 标题 */
	protected String authId; /* 授权人ID */
	protected String authName; /* 授权人姓名 */
	protected java.util.Date startDate; /* 开始生效时间 */
	protected java.util.Date endDate; /* 结束日期 */
	protected String isEnabled = "Y"; /* 是否有效 */
	protected String agentId; /* 代理人ID */
	protected String agent; /* 代理人 */
	protected String flowKey; /* 流程定义KEY(条件代理有效) */
	protected Short type = 1; /* 代理类型(1,全权代理,2,部分代理,3.条件代理) */
	protected String createBy; /* 创建人ID */
	protected java.util.Date createTime; /* 创建时间 */
	protected String createOrgId; /* 创建者所属组织ID */
	protected String updateBy; /* 更新人ID */
	protected java.util.Date updateTime; /* 更新时间 */

	protected List<BpmAgentDef> defList = new ArrayList<BpmAgentDef>();
	protected List<BpmAgentCondition> conditionList = new ArrayList<BpmAgentCondition>();

	// 非数据库字段
	// 流程名称
	protected String flowName;

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 主键
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 返回 标题
	 * 
	 * @return
	 */
	public String getSubject() {
		return this.subject;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * 返回 授权人ID
	 * 
	 * @return
	 */
	public String getAuthId() {
		return this.authId;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	/**
	 * 返回 授权人姓名
	 * 
	 * @return
	 */
	public String getAuthName() {
		return this.authName;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 返回 开始生效时间
	 * 
	 * @return
	 */
	public java.util.Date getStartDate() {
		return this.startDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 返回 结束日期
	 * 
	 * @return
	 */
	public java.util.Date getEndDate() {
		return this.endDate;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 返回 是否有效
	 * 
	 * @return
	 */
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * 返回 代理人ID
	 * 
	 * @return
	 */
	public String getAgentId() {
		return this.agentId;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * 返回 代理人
	 * 
	 * @return
	 */
	public String getAgent() {
		return this.agent;
	}

	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	/**
	 * 返回 流程定义KEY
	 * 
	 * @return
	 */
	public String getFlowKey() {
		return this.flowKey;
	}

	public void setType(Short type) {
		this.type = type;
	}

	/**
	 * 返回 代理类型(1,全权代理,2,部分代理,3.条件代理)
	 * 
	 * @return
	 */
	public Short getType() {
		return this.type;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 返回 创建人ID
	 * 
	 * @return
	 */
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	/**
	 * 返回 创建者所属组织ID
	 * 
	 * @return
	 */
	public String getCreateOrgId() {
		return this.createOrgId;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 返回 更新人ID
	 * 
	 * @return
	 */
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 返回 更新时间
	 * 
	 * @return
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public List<BpmAgentDef> getDefList() {
		return defList;
	}

	public void setDefList(List<BpmAgentDef> defList) {
		this.defList = defList;
	}

	public List<BpmAgentCondition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<BpmAgentCondition> conditionList) {
		this.conditionList = conditionList;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("subject", this.subject).append("authId", this.authId)
				.append("authName", this.authName)
				.append("startDate", this.startDate)
				.append("endDate", this.endDate)
				.append("isEnabled", this.isEnabled)
				.append("agentId", this.agentId).append("agent", this.agent)
				.append("flowKey", this.flowKey).append("type", this.type)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("createOrgId", this.createOrgId)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}
}